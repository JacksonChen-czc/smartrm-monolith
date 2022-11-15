package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityPropertyParser;
import cn.chenzecheng.smartrmmonolith.commodity.infrastructure.CommodityError;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author: yoda
 * @description:
 */
public class MapPropertyJsonParser implements CommodityPropertyParser<Map, JsonNode> {

    @Override
    public Map parse(JsonNode fieldNode) {

        if (fieldNode == null || fieldNode.isNull()) {
            return null;
        }

        if (!fieldNode.isObject()) {
            throw new DomainException(CommodityError.CommodityParseError);
        }

        Map<String, String> map = new HashMap<>();
        Iterator<Entry<String, JsonNode>> iterator = ((ObjectNode) fieldNode).fields();
        while (iterator.hasNext()) {
            Entry<String, JsonNode> next = iterator.next();
            JsonNode value = next.getValue();
            if (value.isTextual()) {
                map.put(next.getKey(), value.asText());
            }
        }

        return map;

    }

}
