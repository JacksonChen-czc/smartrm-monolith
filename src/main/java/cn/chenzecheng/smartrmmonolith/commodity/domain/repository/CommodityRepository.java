package cn.chenzecheng.smartrmmonolith.commodity.domain.repository;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.CommodityProcessor;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Commodity;

import java.io.IOException;
import java.util.List;

/**
 * @author: yoda
 * @description: 商品资源库接口
 */
public interface CommodityRepository {

    Commodity findById(String commodityId);

    List<Commodity> findBatchByIds(List<String> ids);

    void put(Commodity commodity);

    void traverse(CommodityProcessor processor) throws IOException;

}
