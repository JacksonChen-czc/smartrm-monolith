package cn.chenzecheng.smartrmmonolith.payment.adapter.simulator;

import cn.chenzecheng.smartrmmonolith.infracore.api.CommonResponse;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.PlatformPaymentResultDto;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.PlatformResultCode;
import cn.chenzecheng.smartrmmonolith.payment.application.service.PayService;
import cn.chenzecheng.smartrmmonolith.payment.domain.PlatformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yoda
 * @description:
 */
@RestController
@RequestMapping("/mock/payment")
public class PaymentSimulatorController {

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/pay/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse finishPay(@PathVariable Long orderId) {
        PlatformPaymentResultDto result = new PlatformPaymentResultDto();
        result.setPlatformType(PlatformType.Wechat);
        result.setOrderId(orderId);
        result.setResultCode(PlatformResultCode.Success);
        payService.onPaymentResult(result);
        return CommonResponse.success();
    }

    @RequestMapping(value = "/refund/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse finishRefund(@PathVariable Long orderId) {
        PlatformPaymentResultDto result = new PlatformPaymentResultDto();
        result.setOrderId(orderId);
        result.setPlatformType(PlatformType.Wechat);
        result.setResultCode(PlatformResultCode.Success);
        payService.onRefundResult(result);
        return CommonResponse.success();
    }

}
