package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor;

import cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueType;

/**
 * @author: yoda
 * @description:
 */
public interface PropertyConvertor {

    CommodityPropertyParser parser(ValueType type);

    CommodityPropertyDumper dumper(ValueType type);

}
