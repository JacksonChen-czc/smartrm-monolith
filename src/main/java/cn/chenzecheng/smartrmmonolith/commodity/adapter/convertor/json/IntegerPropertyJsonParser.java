package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyParser;
import cn.chenzecheng.smartrmmonolith.commodity.infrastructure.CommodityError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author: yoda
 * @description:
 */
public class IntegerPropertyJsonParser implements
        CommodityPropertyParser<Long, JsonNode> {

    @Override
    public Long parse(JsonNode fieldNode) {
        if (fieldNode == null || fieldNode.isNull()) {
            return null;
        }

        if (!fieldNode.isValueNode()) {
            throw new DomainException(CommodityError.CommodityParseError);
        }

        String content = fieldNode.asText();
        if (StringUtils.isBlank(content)) {
            return null;
        }

        try {
            return NumberUtils.createLong(content);
        } catch (NumberFormatException e) {
            throw new DomainException(CommodityError.CommodityParseError);
        }
    }
}
