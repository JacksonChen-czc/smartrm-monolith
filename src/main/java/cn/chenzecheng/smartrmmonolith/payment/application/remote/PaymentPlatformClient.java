package cn.chenzecheng.smartrmmonolith.payment.application.remote;

import cn.chenzecheng.smartrmmonolith.payment.application.dto.PaymentQrCodeDto;
import cn.chenzecheng.smartrmmonolith.payment.domain.Payment;

/**
 * @author: yoda
 * @description:
 */
public interface PaymentPlatformClient {

    PaymentQrCodeDto queryQrCode(Payment payment);

    void requestForDeduction(Payment payment);

    void requestForRefund(Payment payment);

}
