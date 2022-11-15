package cn.chenzecheng.smartrmmonolith.trade.domain;

import cn.chenzecheng.smartrmmonolith.trade.domain.remote.UserInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: yoda
 * @description:
 */
@Service
public class ActivityService {

    public BigDecimal caculateOrderAmount(Order order, UserInfo userInfo) {
        // caculate order amount with activity
        return BigDecimal.ZERO;
    }
}
