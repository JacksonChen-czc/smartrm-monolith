package cn.chenzecheng.smartrmmonolith.commodity.application.service.impl;

import cn.chenzecheng.smartrmmonolith.commodity.application.dto.CommodityInfoDto;
import cn.chenzecheng.smartrmmonolith.commodity.application.service.CommodityService;
import cn.chenzecheng.smartrmmonolith.commodity.domain.model.Commodity;
import cn.chenzecheng.smartrmmonolith.commodity.domain.repository.CommodityRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yoda
 * @description: 商品服务实现
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public CommodityInfoDto getCommodityDetail(String commodityId) {
        Commodity commodity = commodityRepository.findById(commodityId);
        if (commodity == null) {
            return null;
        }
        CommodityInfoDto commodityInfoDto = new CommodityInfoDto(commodity);
        return commodityInfoDto;
    }

    public List<CommodityInfoDto> getCommodityList(List<String> commodityId) {
        List<CommodityInfoDto> ret = Lists.newArrayList();
        List<Commodity> commodities = commodityRepository.findBatchByIds(commodityId);
        for (Commodity commodity : commodities) {
            ret.add(new CommodityInfoDto(commodity));
        }
        return ret;
    }
}
