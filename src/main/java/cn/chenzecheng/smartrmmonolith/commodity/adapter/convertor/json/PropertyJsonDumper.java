package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyDumper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author: yoda
 * @description:
 */
public abstract class PropertyJsonDumper<T> implements CommodityPropertyDumper<T> {

    ObjectNode root;

    public PropertyJsonDumper<T> withRoot(ObjectNode root) {
        this.root = root;
        return this;
    }

}
