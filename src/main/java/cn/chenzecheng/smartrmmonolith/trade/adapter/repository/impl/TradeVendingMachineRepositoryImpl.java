package cn.chenzecheng.smartrmmonolith.trade.adapter.repository.impl;

import cn.chenzecheng.smartrmmonolith.device.domain.cabinet.CabinetDoorState;
import cn.chenzecheng.smartrmmonolith.infracore.api.CommonError;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventBus;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import cn.chenzecheng.smartrmmonolith.trade.domain.CabinetVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.Order;
import cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachineState;
import cn.chenzecheng.smartrmmonolith.trade.domain.repository.OrderRepository;
import cn.chenzecheng.smartrmmonolith.trade.domain.repository.VendingMachineRepository;
import cn.chenzecheng.smartrmmonolith.trade.infrastructure.dataobject.TradeCabinetVendingMachineDo;
import cn.chenzecheng.smartrmmonolith.trade.infrastructure.dataobject.TradeSlotVendingMachineDo;
import cn.chenzecheng.smartrmmonolith.trade.infrastructure.mapper.TradeCabinetVendingMachineMapper;
import cn.chenzecheng.smartrmmonolith.trade.infrastructure.mapper.TradeSlotVendingMachineMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author: yoda
 * @description: 交易上下文售卖机资源库
 */
@Repository
public class TradeVendingMachineRepositoryImpl implements VendingMachineRepository {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TradeVendingMachineRepositoryImpl.class);
    @Autowired
    TradeSlotVendingMachineMapper slotVendingMachineMapper;

    @Autowired
    TradeCabinetVendingMachineMapper cabinetVendingMachineMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DomainEventBus eventBus;

    @Override
    public SlotVendingMachine getSlotVendingMachineById(long id) {
        TradeSlotVendingMachineDo slotVendingMachineDo = slotVendingMachineMapper.selectOne(id);
        Order curOrder = null;
        if (slotVendingMachineDo.getState() != SlotVendingMachineState.Ready.code()) {
            curOrder = orderRepository.getOrderById(slotVendingMachineDo.getCurOrderId());
        }
        SlotVendingMachine machine = SlotVendingMachine.Builder()
                .machineId(slotVendingMachineDo.getMachineId())
                .state(SlotVendingMachineState.of(slotVendingMachineDo.getState()))
                .curOrder(curOrder)
                .eventBus(eventBus)
                .version(slotVendingMachineDo.getVersion()).build();
        return machine;
    }

    @Override
    public CabinetVendingMachine getCabinetVendingMachineById(long id) {
        TradeCabinetVendingMachineDo machineDo = cabinetVendingMachineMapper.selectOne(id);
        CabinetVendingMachine machine = CabinetVendingMachine.Builder().machineId(id)
                .state(CabinetDoorState.of(machineDo.getState()))
                .curUserOpenId(machineDo.getCurUserOpenId())
                .build();

        return machine;
    }

    public void updateSlotVendingMachine(SlotVendingMachine machine) {
        if (!machine.isVersionInc()) {
            //版本号未改变时直接跳过
            return;
        }
        TradeSlotVendingMachineDo machineDo = new TradeSlotVendingMachineDo();
        machineDo.setMachineId(machine.getMachineId());
        machineDo.setState(machine.getState().code());
        machineDo.setCurOrderId(machine.getCurOrder() != null ? machine.getCurOrder().getOrderId() : 0);
        machineDo.setVersion(machine.getVersion());
        if (machine.getCurOrder() != null) {
            orderRepository.addOrUpdate(machine.getCurOrder());         // 聚合与聚合之间适用于最终一致性
        }
        int updated = slotVendingMachineMapper.update(machineDo);
        if (updated == 0) {
            LOGGER.error("fail to update slot machine, version:" + machineDo.getVersion());
            throw new DomainException(CommonError.ConcurrencyConflict);
        }
        LOGGER.info("update slot machine, version:" + machineDo.getVersion());
    }

    public void updateCabinetVendingMachine(CabinetVendingMachine machine) {
        TradeCabinetVendingMachineDo machineDo = new TradeCabinetVendingMachineDo();
        machineDo.setMachineId(machine.getMachineId());
        machineDo.setState(machine.getState().code());
        machineDo.setCurUserOpenId(machine.getCurUserOpenId());
        cabinetVendingMachineMapper.update(machineDo);
    }

    /**
     * @author: yoda
     * @description:
     */
    @Aspect
    @Component
    public static class TradeVendingMachineRepositoryAspect implements ApplicationContextAware {

        private static VendingMachineRepository repository;

        @Pointcut(
                "execution(* cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine.selectCommodity(..)) "
                        + " || execution(* cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine.finishOrder(..))"
                        + " || execution(* cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine.cancelOrder(..)) "
                        + " || execution(* cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine.ready(..))")
        private void slotUpdate() {
        }

        @Pointcut(
                "execution(* cn.chenzecheng.smartrmmonolith.trade.domain.CabinetVendingMachine.open(..)) "
                        + "|| execution(* cn.chenzecheng.smartrmmonolith.trade.domain.CabinetVendingMachine.onLocked(..))")
        private void cabinetUpdate() {
        }

        @After("slotUpdate()")
        public void saveSlot(JoinPoint point) {
//            SlotVendingMachine machine = (SlotVendingMachine) point.getTarget();
//      machine.incVersion();
            repository.updateSlotVendingMachine((SlotVendingMachine) point.getTarget());
        }

        @After("cabinetUpdate()")
        public void saveCabinet(JoinPoint point) {
            repository.updateCabinetVendingMachine((CabinetVendingMachine) point.getTarget());
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            repository = applicationContext.getBean(VendingMachineRepository.class);
        }
    }
}
