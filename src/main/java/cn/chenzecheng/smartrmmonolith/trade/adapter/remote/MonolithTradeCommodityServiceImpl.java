package cn.chenzecheng.smartrmmonolith.trade.adapter.remote;

import cn.chenzecheng.smartrmmonolith.commodity.application.dto.CommodityInfoDto;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.CommodityInfo;
import cn.chenzecheng.smartrmmonolith.trade.domain.remote.TradeCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yoda
 * @description:
 */
@Service
public class MonolithTradeCommodityServiceImpl implements TradeCommodityService {

    @Autowired
    cn.chenzecheng.smartrmmonolith.commodity.application.service.CommodityService remoteService;

    @Override
    public CommodityInfo getCommodityDetail(String commodityId) {
        CommodityInfoDto dto = remoteService.getCommodityDetail(commodityId);
        return new CommodityInfo(dto.getCommodityId(), dto.getCommodityName(), dto.getImageUrl(),
                dto.getPrice());
    }

    @Override
    public List<CommodityInfo> getCommodityList(List<String> commodityIds) {
        List<CommodityInfoDto> dtos = remoteService.getCommodityList(commodityIds);
        return dtos.stream().map(
                dto -> new CommodityInfo(dto.getCommodityId(), dto.getCommodityName(), dto.getImageUrl(),
                        dto.getPrice())).collect(Collectors.toList());
    }
}
