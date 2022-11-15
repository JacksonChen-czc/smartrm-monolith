package cn.chenzecheng.smartrmmonolith.payment.adapter.simulator;

import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventHandler;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.PlatformPaymentResultDto;
import cn.chenzecheng.smartrmmonolith.payment.application.dto.PlatformResultCode;
import cn.chenzecheng.smartrmmonolith.payment.application.service.PayService;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentState;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentStateChangeEvent;
import cn.chenzecheng.smartrmmonolith.payment.domain.PlatformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yoda
 * @description:
 */
@Component
public class PaymentEventHandler implements DomainEventHandler<PaymentStateChangeEvent> {

    @Autowired
    PayService payService;

    @Override
    public void onApplicationEvent(PaymentStateChangeEvent paymentStateChangeEvent) {
        if (paymentStateChangeEvent.getCurState() == PaymentState.WaitingForDeduction) {
            PlatformPaymentResultDto result = new PlatformPaymentResultDto();
            result.setPlatformType(PlatformType.Wechat);
            result.setOrderId(paymentStateChangeEvent.getOrderInfo().getOrderId());
            result.setResultCode(PlatformResultCode.Success);
            payService.onPaymentResult(result);
        }
    }
}
