package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.CurrencyPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.CurrencyPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.DatePropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.DatePropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.DateTimePropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.DateTimePropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.FloatPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.FloatPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.ImageUrlPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.ImageUrlPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.IntegerPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.IntegerPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.MapPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.MapPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.StringPropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.StringPropertyJsonParser;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueType;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.CURRENCY;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.DATE;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.DATETIME;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.FLOAT;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.IMAGE_URL;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.INTEGER;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.MAP;
import static cn.chenzecheng.smartrmmonolith.commodity.domain.model.ValueTypeCode.STRING;

/**
 * @author: yoda
 * @description: 适配器，从商品模型到Json对象
 */
@Component
public class JsonPropertyConvertor implements PropertyConvertor {

    private static Map<ValueTypeCode, Pair<CommodityPropertyParser, CommodityPropertyDumper>> adapterMap = ImmutableMap
            .<ValueTypeCode, Pair<CommodityPropertyParser, CommodityPropertyDumper>>builder()
            .put(STRING, Pair.of(new StringPropertyJsonParser(), new StringPropertyJsonDumper()))
            .put(INTEGER, Pair.of(new IntegerPropertyJsonParser(), new IntegerPropertyJsonDumper()))
            .put(FLOAT, Pair.of(new FloatPropertyJsonParser(), new FloatPropertyJsonDumper()))
            .put(CURRENCY, Pair.of(new CurrencyPropertyJsonParser(), new CurrencyPropertyJsonDumper()))
            .put(DATE, Pair.of(new DatePropertyJsonParser(), new DatePropertyJsonDumper()))
            .put(DATETIME, Pair.of(new DateTimePropertyJsonParser(), new DateTimePropertyJsonDumper()))
            .put(IMAGE_URL, Pair.of(new ImageUrlPropertyJsonParser(), new ImageUrlPropertyJsonDumper()))
            .put(MAP, Pair.of(new MapPropertyJsonParser(), new MapPropertyJsonDumper()))
            .build();

    @Override
    public CommodityPropertyParser parser(ValueType type) {
        return adapterMap.get(type.getType()).getLeft();
    }

    @Override
    public CommodityPropertyDumper dumper(ValueType type) {
        return adapterMap.get(type.getType()).getRight();
    }

}
