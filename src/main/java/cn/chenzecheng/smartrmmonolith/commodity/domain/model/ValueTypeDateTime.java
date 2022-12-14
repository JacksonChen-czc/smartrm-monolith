package cn.chenzecheng.smartrmmonolith.commodity.domain.model;

import java.time.LocalDateTime;

public class ValueTypeDateTime implements ValueType<LocalDateTime> {

    @Override
    public ValueTypeCode getType() {
        return ValueTypeCode.DATETIME;
    }

    @Override
    public Class<LocalDateTime> getValueClass() {
        return LocalDateTime.class;
    }

}
