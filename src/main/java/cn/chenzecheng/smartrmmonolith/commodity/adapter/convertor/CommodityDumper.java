package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.CommodityProcessor;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.PropertyJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Commodity;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Property;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author: yoda
 * @description:
 */
public class CommodityDumper implements CommodityProcessor {

    private static byte[] SPLITER = "\n".getBytes(StandardCharsets.UTF_8);
    private PropertyConvertor convertor;
    private OutputStream ostream;
    private ObjectMapper mapper = new ObjectMapper();

    public CommodityDumper(PropertyConvertor convertor, OutputStream ostream) {
        this.convertor = convertor;
        this.ostream = ostream;
    }

    @Override
    public void onCommodity(Commodity commodity) throws IOException {
        ObjectNode root = mapper.createObjectNode();
        Collection<Property> properties = commodity.getAllProperties();
        for (Property p : properties) {
            ((PropertyJsonDumper) (convertor.dumper(p.getDataType()))).withRoot(root).dump(p);
        }
        ostream.write(root.toString().getBytes(StandardCharsets.UTF_8));
        ostream.write(SPLITER);
    }

}
