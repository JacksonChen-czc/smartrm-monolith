package cn.chenzecheng.smartrmmonolith.payment.adapter.api.controller;

import cn.chenzecheng.smartrmmonolith.infracore.api.CommonResponse;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.ChargeCommandDto;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.PaymentQrCodeDto;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.StartQrCodePayCommandDto;
import cn.chenzecheng.smartrmmonolith.payment.application.service.PayService;
import cn.chenzecheng.smartrmmonolith.payment.domain.AccountInfo;
import cn.chenzecheng.smartrmmonolith.payment.domain.OrderInfo;
import cn.chenzecheng.smartrmmonolith.trade.domain.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yoda
 * @description:
 */
@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    PayService payService;

    @RequestMapping(value = "/startQrcodePay", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse<PaymentQrCodeDto> startQrCodePayForOrder(
            @RequestBody StartQrCodePayCommandDto request) {
        OrderInfo orderInfo = OrderInfo.Builder().orderId(request.getOrderId())
                .machineId(request.getMachineId())
                .type(OrderType.SlotQrScanePaid)
                .totalAmount(request.getTotalAmount()).build();
        return CommonResponse.success(
                payService.startQrCodePayForOrder(request.getPlatformType(), orderInfo));
    }

    @RequestMapping(value = "/chargeForOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse chargeForOrder(@RequestBody ChargeCommandDto request) {
        OrderInfo orderInfo = OrderInfo.Builder().orderId(request.getOrderId())
                .machineId(request.getMachineId())
                .type(OrderType.CabinetAutoDeduction)
                .totalAmount(request.getTotalAmount()).build();
        AccountInfo accountInfo = new AccountInfo(request.getAccountId(), request.getContractId());
        payService.chargeForOrder(orderInfo, accountInfo);
        return CommonResponse.success();
    }

}
