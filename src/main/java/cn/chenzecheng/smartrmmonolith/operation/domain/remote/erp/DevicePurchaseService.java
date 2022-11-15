package cn.chenzecheng.smartrmmonolith.operation.domain.remote.erp;

import cn.chenzecheng.smartrmmonolith.operation.domain.VendingMachineInstallOrder;

/**
 * @author: yoda
 * @description:
 */
public interface DevicePurchaseService {

    void placeInstallOrder(VendingMachineInstallOrder order);

}
