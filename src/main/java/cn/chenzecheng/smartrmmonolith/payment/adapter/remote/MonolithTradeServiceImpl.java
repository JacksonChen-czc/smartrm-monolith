package cn.chenzecheng.smartrmmonolith.payment.adapter.remote;

import cn.chenzecheng.smartrmmonolith.payment.application.remote.TradeService;
import cn.chenzecheng.smartrmmonolith.payment.domain.PaymentStateChangeEvent;
import cn.chenzecheng.smartrmmonolith.trade.application.AppTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: yoda
 * @description:
 */
@Service
public class MonolithTradeServiceImpl implements TradeService {

    @Autowired
    private AppTradeService appTradeService;

    @Override
    public void onPaymentStateChange(PaymentStateChangeEvent event) {
        appTradeService.onPaymentStateChange(event);
    }
}
