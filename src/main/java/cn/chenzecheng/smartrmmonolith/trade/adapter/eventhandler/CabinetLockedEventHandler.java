package cn.chenzecheng.smartrmmonolith.trade.adapter.eventhandler;

import cn.chenzecheng.smartrmmonolith.device.domain.event.CabinetVendingMachineLockedEvent;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventHandler;
import cn.chenzecheng.smartrmmonolith.trade.application.AppTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yoda
 * @description:
 */
@Component
public class CabinetLockedEventHandler implements DomainEventHandler<CabinetVendingMachineLockedEvent> {

    @Autowired
    AppTradeService tradeService;

    @Override
    public void onApplicationEvent(CabinetVendingMachineLockedEvent event) {
        tradeService.onCabinetLocked(event);
    }
}
