package cn.chenzecheng.smartrmmonolith.commodity.adapter;

import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Commodity;

import java.io.IOException;

/**
 * @author: yoda
 * @description:
 */
public interface CommodityProcessor {

    public void onCommodity(Commodity commodity) throws IOException;

}
