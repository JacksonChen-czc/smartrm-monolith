package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyParser;
import cn.chenzecheng.smartrmmonolith.commodity.infrastructure.CommodityError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author: yoda
 * @description:
 */
public class CurrencyPropertyJsonParser implements
        CommodityPropertyParser<BigDecimal, JsonNode> {

    @Override
    public BigDecimal parse(JsonNode content) {
        if (content == null || content.isNull()) {
            return null;
        }

        if (!content.isValueNode()) {
            throw new DomainException(CommodityError.CommodityParseError);
        }

        String str = content.asText();
        if (StringUtils.isBlank(str)) {
            return null;
        }

        try {
            return NumberUtils.createBigDecimal(str);
        } catch (NumberFormatException e) {
            throw new DomainException(CommodityError.CommodityParseError);
        }
    }
}
