package cn.chenzecheng.smartrmmonolith.payment.adapter.eventhandler;

import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventHandler;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import cn.chenzecheng.smartrmmonolith.payment.application.remote.PaymentPlatformClient;
import cn.chenzecheng.smartrmmonolith.payment.domain.Payment;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentState;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentStateChangeEvent;
import cn.chenzecheng.smartrmmonolith.payment.domain.repository.PaymentRepository;
import cn.chenzecheng.smartrmmonolith.payment.infrastructure.PaymentError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yoda
 * @description:
 */
@Component
public class PaymentStateChangeEventHandler implements DomainEventHandler<PaymentStateChangeEvent> {

    private static Logger LOGGER = LoggerFactory.getLogger(PaymentStateChangeEventHandler.class);

    @Autowired
    PaymentPlatformClient paymentPlatformClient;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public void onApplicationEvent(PaymentStateChangeEvent paymentStateChangeEvent) {

        //处理退款
        if (paymentStateChangeEvent.getCurState() == PaymentState.WaitingForRefund) {
            Payment payment = paymentRepository
                    .getByOrderId(paymentStateChangeEvent.getOrderInfo().getOrderId());
            if (payment == null) {
                LOGGER.error(
                        "payment not exist, orderId:" + paymentStateChangeEvent.getOrderInfo().getOrderId());
                throw new DomainException(PaymentError.PaymentNotExist);
            }
            paymentPlatformClient.requestForDeduction(payment);
            payment.refundTried();
            paymentRepository.update(payment);
        }


    }
}
