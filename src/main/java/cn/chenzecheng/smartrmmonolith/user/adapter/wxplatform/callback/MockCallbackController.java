package cn.chenzecheng.smartrmmonolith.user.adapter.wxplatform.callback;

import cn.chenzecheng.smartrmmonolith.user.application.dto.PaymentContractInfoDto;
import cn.chenzecheng.smartrmmonolith.user.application.dto.WxContractSigningNotification;
import cn.chenzecheng.smartrmmonolith.user.application.dto.WxContractSigningResponse;
import cn.chenzecheng.smartrmmonolith.user.application.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yoda
 * @description:
 */
@RestController
@RequestMapping("/mock/user/callback")
public class MockCallbackController {

    @Autowired
    AppUserService appUserService;

    @RequestMapping(value = "/wxContractSign", method = RequestMethod.POST)
    @ResponseBody
    public WxContractSigningResponse notifyContractSigningResult(
            @RequestBody WxContractSigningNotification notification) {
        PaymentContractInfoDto contractInfoDto = new PaymentContractInfoDto();
        contractInfoDto.setContractId(notification.getContractId());
        contractInfoDto.setOpenId(notification.getOpenid());
        contractInfoDto.setContractCode(notification.getContractCode());
        appUserService.onPaymentContractSigned(contractInfoDto);
        WxContractSigningResponse response = new WxContractSigningResponse();
        response.setReturnCode("SUCCESS");
        response.setReturnMsg("OK");
        return response;
    }
}
