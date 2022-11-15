package cn.chenzecheng.smartrmmonolith.trade.adapter.eventhandler;

import cn.chenzecheng.smartrmmonolith.device.domain.event.PopSuccessEvent;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventHandler;
import cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.repository.VendingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yoda
 * @description: 商品弹出成功事件处理
 */
@Component
public class PopSuccessEventHandler implements DomainEventHandler<PopSuccessEvent> {

    @Autowired
    private VendingMachineRepository machineRepository;

    @Override
    public void onApplicationEvent(PopSuccessEvent event) {
        long machineId = event.getMachineId();
        SlotVendingMachine machine = machineRepository.getSlotVendingMachineById(machineId);
        if (machine != null) {
            machine.onPopSuccess(event);
        }
    }
}
