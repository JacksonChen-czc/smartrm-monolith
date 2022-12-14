package cn.chenzecheng.smartrmmonolith.commodity.adapter.file;

import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.CommodityDumper;
import cn.chenzecheng.smartrmmonolith.commodity.adapter.convertor.json.CommodityJsonDumper;
import cn.chenzecheng.smartrmmonolith.commodity.domain.repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: yoda
 * @description: 把资源库写入到json文件
 */
@Component
public class CommodityRepositoryJsonWriter {

    @Autowired
    private CommodityRepository commodityRepository;

    public void dumpRepository() throws IOException {
        CommodityDumper dumper = new CommodityJsonDumper(
                new FileOutputStream("commodity_repository_dump.json"));
        commodityRepository.traverse(dumper);
    }

}
