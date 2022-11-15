package cn.chenzecheng.smartrmmonolith.device.domain.repository;

import cn.chenzecheng.smartrmmonolith.device.domain.VendingMachine;
import cn.chenzecheng.smartrmmonolith.device.domain.cabinet.CabinetVendingMachine;
import cn.chenzecheng.smartrmmonolith.device.domain.iot.IoTDeviceId;
import cn.chenzecheng.smartrmmonolith.device.domain.slot.SlotVendingMachine;

/**
 * @author: yoda
 * @description:
 */
public interface VendingMachineRepository {

    VendingMachine getVendingMachine(long machineId);

    SlotVendingMachine getSlotVendingMachineById(long machineId);

    SlotVendingMachine getSlotVendingMachineByDeviceId(IoTDeviceId deviceId);

    CabinetVendingMachine getCabinetVendingMachineById(long machineId);

    void add(VendingMachine vendingMachine);

    void updateInventory(VendingMachine machine);

    void updateCabinetVendingMachineState(CabinetVendingMachine machine);

    void clearInventorySnapshot(long machineId);

    void saveInventorySnapshot(CabinetVendingMachine machine);

}
