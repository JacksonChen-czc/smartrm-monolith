package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.CurrencyPropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.DatePropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.DateTimePropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.FloatPropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.ImageUrlPropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.IntegerPropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.MapPropertyTextParser;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext.StringPropertyTextParser;
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
 * @description:
 */
@Component
public class PlainTextPropertyConvertor implements PropertyConvertor {

    private static Map<ValueTypeCode, Pair<CommodityPropertyParser, CommodityPropertyDumper>> adapterMap = ImmutableMap
            .<ValueTypeCode, Pair<CommodityPropertyParser, CommodityPropertyDumper>>builder()
            .put(STRING, Pair.of(new StringPropertyTextParser(), null))
            .put(INTEGER, Pair.of(new IntegerPropertyTextParser(), null))
            .put(FLOAT, Pair.of(new FloatPropertyTextParser(), null))
            .put(CURRENCY, Pair.of(new CurrencyPropertyTextParser(), null))
            .put(DATE, Pair.of(new DatePropertyTextParser(), null))
            .put(DATETIME, Pair.of(new DateTimePropertyTextParser(), null))
            .put(IMAGE_URL, Pair.of(new ImageUrlPropertyTextParser(null), null))
            .put(MAP, Pair.of(new MapPropertyTextParser(), null))
            .build();

    @Override
    public CommodityPropertyParser parser(ValueType type) {
        return adapterMap.get(type.getType()).getLeft();
    }

    @Override
    public CommodityPropertyDumper dumper(ValueType type) {
        return null;
    }
}
