package cn.chenzecheng.smartrmmonolith.device.domain.slot;

import cn.chenzecheng.smartrmmonolith.device.domain.VendingMachine;
import cn.chenzecheng.smartrmmonolith.device.infrastructure.DeviceError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;

/**
 * @author: yoda
 * @description:
 */
public abstract class SlotVendingMachine extends VendingMachine {

    public abstract void popCommodity(String commodityId, Long orderId) throws Exception;

    public boolean checkInventory(String commodityId, int count) {
        if (!inventory.containsKey(commodityId) || inventory.get(commodityId) <= count) {
            return false;
        }
        return true;
    }

    public void changeInventory(String commodityId, int count) {
        int cur = inventory.get(commodityId);
        if (cur + count < 0) {
            throw new DomainException(DeviceError.InventoryNotCorrect);
        } else if (cur + count == 0) {
            inventory.remove(commodityId);
        } else {
            inventory.put(commodityId, cur + count);
        }
    }

}
