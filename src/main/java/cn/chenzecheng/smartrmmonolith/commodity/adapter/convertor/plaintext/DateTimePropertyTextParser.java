package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.plaintext;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyParser;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.DateParser;
import cn.chenzecheng.smartrmmonolith.commodity.infrastructure.CommodityError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author: yoda
 * @description:
 */
public class DateTimePropertyTextParser implements
        CommodityPropertyParser<LocalDateTime, String> {

    @Override
    public LocalDateTime parse(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        LocalDateTime dateTime = DateParser.asLocalDateTime(content);
        if (dateTime == null) {
            throw new DomainException(CommodityError.CommodityParseError);
        }
        return dateTime;
    }
}
