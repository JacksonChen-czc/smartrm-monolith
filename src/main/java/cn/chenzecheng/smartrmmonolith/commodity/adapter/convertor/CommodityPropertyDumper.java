package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor;

import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Property;

/**
 * @author: yoda
 * @description:
 */
public interface CommodityPropertyDumper<T> {

    void dump(Property<T> from);

}
