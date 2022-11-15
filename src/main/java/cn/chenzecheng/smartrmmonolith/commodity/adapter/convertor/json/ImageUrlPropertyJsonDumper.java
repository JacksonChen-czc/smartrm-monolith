package cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json;

import cn.chenzecheng.smartrmmonolith.commodity.domain.model.ImageUrl;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Property;

/**
 * @author: yoda
 * @description:
 */
public class ImageUrlPropertyJsonDumper extends PropertyJsonDumper<ImageUrl> {

    @Override
    public void dump(Property<ImageUrl> from) {
        if (from.getMaxRepeat() > 1) {
            root.withArray(from.getName()).addPOJO(from.getValue());
        } else {
            root.putPOJO(from.getName(), from.getValue());
        }
    }

}
