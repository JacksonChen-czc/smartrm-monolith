package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Property;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author: yoda
 * @description:
 */
public class MapPropertyJsonDumper extends PropertyJsonDumper<Map> {

    final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void dump(Property<Map> from) {
        JsonNode node = mapper.valueToTree(from);
        if (from.getMaxRepeat() > 1) {
            root.withArray(from.getName()).add(node);
        } else {
            root.set(from.getName(), node);
        }
    }
}
