package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyParser;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.DateParser;
import cn.chenzecheng.smartrmmonolith.commodity.infrastructure.CommodityError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author: yoda
 * @description:
 */
public class DateTimePropertyJsonParser implements
        CommodityPropertyParser<LocalDateTime, JsonNode> {

    @Override
    public LocalDateTime parse(JsonNode fieldNode) {
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

        LocalDateTime date = DateParser.asLocalDateTime(content);
        if (date == null) {
            // 支持 Unix Timestamp （单位秒）
            long timestamp = NumberUtils.toLong(content);
            if (timestamp > 0) {
                date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            if (date == null) {
                throw new DomainException(CommodityError.CommodityParseError);
            }
        }
        return date;
    }
}
