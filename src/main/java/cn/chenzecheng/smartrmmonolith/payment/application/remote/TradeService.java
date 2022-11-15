package cn.chenzecheng.smartrmmonolith.payment.application.remote;

import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentStateChangeEvent;

/**
 * @author: yoda
 * @description:
 */
public interface TradeService {

    void onPaymentStateChange(PaymentStateChangeEvent event);

}
