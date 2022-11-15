package cn.chenzecheng.smartrmmonolith.payment.adapter.eventhandler;

import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventHandler;
import cn.chenzecheng.smartrmmonolith.payment.application.service.PayService;
import cn.chenzecheng.smartrmmonolith.payment.domain.OrderInfo;
import cn.chenzecheng.smartrmmonolith.trade.domain.event.OrderCanceledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yoda
 * @description:
 */
@Component
public class OrderCanceledEventHandler implements DomainEventHandler<OrderCanceledEvent> {

    @Autowired
    private PayService payService;

    @Override
    public void onApplicationEvent(OrderCanceledEvent event) {
        payService.onOrderCanceled(
                OrderInfo.Builder().orderId(event.getOrderId())
                        .type(event.getType())
                        .machineId(event.getMachineId())
                        .totalAmount(event.getTotalAmount())
                        .build()
        );
    }
}
