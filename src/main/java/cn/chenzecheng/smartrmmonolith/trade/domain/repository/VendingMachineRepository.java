package cn.chenzecheng.smartrmmonolith.trade.domain.repository;

import cn.chenzecheng.smartrmmonolith.trade.domain.CabinetVendingMachine;
import cn.chenzecheng.smartrmmonolith.trade.domain.SlotVendingMachine;

/**
 * @author: yoda
 * @description:
 */
public interface VendingMachineRepository {

    SlotVendingMachine getSlotVendingMachineById(long id);

    CabinetVendingMachine getCabinetVendingMachineById(long id);

    void updateSlotVendingMachine(SlotVendingMachine machine);

    void updateCabinetVendingMachine(CabinetVendingMachine machine);

}
