package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.JsonPropertyConvertor;

import java.io.OutputStream;

/**
 * @author: yoda
 * @description:
 */
public class CommodityJsonDumper extends CommodityDumper {

    public CommodityJsonDumper(OutputStream ostream) {
        super(new JsonPropertyConvertor(), ostream);
    }

}
