package cn.chenzecheng.smartrmmonolith.user.application.client;

import cn.chenzecheng.smartrmmonolith.user.application.dto.WxCode2SessionResultDto;

/**
 * @author: yoda
 * @description:
 */
public interface WxPlatformClient {

    WxCode2SessionResultDto code2Session(String code);

}
