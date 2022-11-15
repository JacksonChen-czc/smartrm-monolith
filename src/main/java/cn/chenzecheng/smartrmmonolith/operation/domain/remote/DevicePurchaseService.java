package cn.chenzecheng.smartrmmonolith.operation.domain.remote;

import cn.chenzecheng.smartrmmonolith.operation.domain.VendingMachineInstallOrder;

/**
 * @author: yoda
 * @description: 售卖机投放（采购）服务
 */
public interface DevicePurchaseService {

    void placeInstallOrder(VendingMachineInstallOrder order);

}
