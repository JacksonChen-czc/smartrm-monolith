package cn.chenzecheng.smartrmmonolith.trade.application;

import cn.chenzecheng.smartrmmonolith.device.domain.VendingMachineType;
import cn.chenzecheng.smartrmmonolith.device.domain.event.CabinetVendingMachineLockedEvent;
import cn.chenzecheng.smartrmmonolith.device.domain.event.DeviceFailureEvent;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventBus;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import cn.chenzecheng.smartrmmonolith.infracore.scheduler.ExecutorJobScheduler;
import cn.chenzecheng.smartrmmonolith.payment.domain.OrderInfo;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentState;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentStateChangeEvent;
import cn.chenzecheng.smartrmmonolith.trade.application.dto.SelectCommodityCmdDto;
import cn.chenzecheng.smartrmmonolith.trade.application.dto.VendingMachineCommodityListDto;
import cn.chenzecheng.smartrmmonolith.trade.application.executor.DeviceFailureExecutor;
import cn.chenzecheng.smartrmmonolith.trade.application.executor.PoppingExpireExecutor;
import cn.chenzecheng.smartrmmonolith.trade.application.executor.TradeExpireExecutor;
import cn.chenzecheng.smartrmmonolith.trade.domain.CabinetVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.Order;
import cn.chenzecheng.smartrmmonolith.trade.domain.OrderType;
import cn.chenzecheng.smartrmmonolith.trade.domain.PaymentQrCode;
import cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.StockedCommodity;
import cn.chenzecheng.smartrmmonolith.trade.domain.event.OrderCreatedEvent;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.CommodityInfo;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.TradeCommodityService;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.TradeDeviceService;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.TradePayService;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.TradeUserService;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.UserInfo;
import cn.chenzecheng.smartrmmonolith.trade.domain.repository.OrderRepository;
import cn.chenzecheng.smartrmmonolith.trade.domain.repository.VendingMachineRepository;
import cn.chenzecheng.smartrmmonolith.trade.infrastructure.TradeError;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author: yoda
 * @description: ?????????????????????
 */
@Service
public class AppTradeService {

    private static Logger LOGGER = LoggerFactory.getLogger(AppTradeService.class);

    private static int DEVICE_FAILURE_PROCESS_DELAY = 1000;

    private static int TRADE_EXPIRE_SECS = 30;

    @Autowired
    TradePayService payService;

    @Autowired
    TradeUserService userService;

    @Autowired
    TradeDeviceService deviceService;

    @Autowired
    TradeCommodityService commodityService;

    @Autowired
    VendingMachineRepository machineRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DomainEventBus eventBus;

    @Autowired
    ExecutorJobScheduler scheduler;

    public VendingMachineCommodityListDto queryCommodityList(long machineId) {
        SlotVendingMachine machine = machineRepository.getSlotVendingMachineById(machineId);
        if (machine == null) {
            LOGGER.warn("vending machine not found:{}", machineId);
            throw new DomainException(TradeError.VendingMachineNotFound);
        }
        return new VendingMachineCommodityListDto(
                machine.getCommodityList(deviceService, commodityService), machine.getState());
    }

    @Transactional
    public PaymentQrCode selectCommodity(SelectCommodityCmdDto cmd) {
        SlotVendingMachine machine = machineRepository.getSlotVendingMachineById(cmd.getMachineId());
        if (machine == null) {
            LOGGER.warn("vending machine not found:{}", cmd.getMachineId());
            throw new DomainException(TradeError.VendingMachineNotFound);
        }
        CommodityInfo commodityInfo = commodityService.getCommodityDetail(cmd.getCommodityId());
        if (commodityInfo == null) {
            LOGGER.warn("commodity not exist:{}", cmd.getCommodityId());
            throw new DomainException(TradeError.CommodityNotExist);
        }
        StockedCommodity commodity = new StockedCommodity(
                commodityInfo.getCommodityId(),
                commodityInfo.getCommodityName(),
                commodityInfo.getImageUrl(),
                commodityInfo.getPrice(),
                1
        );
        PaymentQrCode code = machine
                .selectCommodity(Lists.newArrayList(commodity), deviceService, payService,
                        cmd.getPlatformType());
        Map<String, Object> params = Maps.newHashMap();
        params.put("orderId", machine.getCurOrder().getOrderId());
        params.put("machineId", machine.getMachineId());
        scheduler.scheduleRetry(TradeExpireExecutor.class, params, 30 * 1000, 1000);
        return code;
    }

    @Transactional
    public void onPaymentStateChange(PaymentStateChangeEvent event) {
        OrderInfo orderInfo = event.getOrderInfo();
        if (event.getCurState() == PaymentState.Success) {
            if (event.getOrderInfo().getType() == OrderType.SlotQrScanePaid) {
                // ??????????????????????????????????????????????????????
                SlotVendingMachine machine = machineRepository.getSlotVendingMachineById(orderInfo.getMachineId());
                try {
                    machine.finishOrder(orderInfo.getOrderId(), deviceService);
                } catch (Exception e) {
                    //?????????????????????????????????????????????????????????
                    LOGGER.error("fail to finish order.", e);
                    machine.cancelOrder();
                }
                //????????????????????????
                Map<String, Object> params = Maps.newHashMap();
                params.put("orderId", event.getOrderInfo().getOrderId());
                params.put("machineId", machine.getMachineId());
                scheduler.scheduleRetry(PoppingExpireExecutor.class, params, 10 * 1000, 1000);
            } else {
                // ????????????????????????????????????????????????
                Order order = orderRepository.getOrderById(event.getOrderInfo().getOrderId());
                order.succeed();
            }
        }
    }

    @PreAuthorize("hasAuthority('OpenCabinet')")
    @Transactional
    public void openCabinetVendingMachine(long machineId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserOpenId = (String) authentication.getPrincipal();
        CabinetVendingMachine cabinet = machineRepository.getCabinetVendingMachineById(machineId);
        cabinet.open(curUserOpenId, deviceService);
//    machineRepository.updateCabinetVendingMachine(cabinet);
    }

    /**
     * ????????????????????????????????????????????????
     */
    @Transactional
    public void onCabinetLocked(CabinetVendingMachineLockedEvent event) {
        CabinetVendingMachine cabinet = machineRepository.getCabinetVendingMachineById(event.getMachineId());
        try {
            Order order = cabinet.onLocked(event, commodityService);
            orderRepository.addOrder(order);
//    machineRepository.updateCabinetVendingMachine(cabinet);
            UserInfo userInfo = userService.getUserInfo(cabinet.getCurUserOpenId());
            // ????????????
            payService.chargeForOrder(
                    OrderInfo.Builder()
                            .machineId(order.getMachineId())
                            .orderId(order.getOrderId())
                            .type(order.getType())
                            .totalAmount(order.totalAmount())       // ????????????????????????????????????
                            .build(),
                    userInfo
            );
            eventBus.post(new OrderCreatedEvent(cabinet.getMachineId(), order));
        } catch (DomainException e) {
            if (e.getErrCode() != TradeError.OrderAmountZero) {
                throw e;
            }
        }
    }

    public void onDeviceFailure(DeviceFailureEvent event) {
        if (event.getMachineType() == VendingMachineType.SLOT) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("event", event);
            scheduler.scheduleRetry(DeviceFailureExecutor.class, params, 0, 1000);
        }
    }

}
