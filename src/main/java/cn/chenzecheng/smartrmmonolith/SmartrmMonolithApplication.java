package cn.chenzecheng.smartrmmonolith;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "cn.chenzecheng.smartrmmonolith", exclude = {SecurityAutoConfiguration.class})
@MapperScan({"cn.chenzecheng.smartrmmonolith.device.infrastructure.mapper",
        "cn.chenzecheng.smartrmmonolith.user.infrastructure.mapper",
        "cn.chenzecheng.smartrmmonolith.payment.infrastructure.mapper",
        "cn.chenzecheng.smartrmmonolith.trade.infrastructure.mapper",
        "cn.chenzecheng.smartrmmonolith.infracore.idgenerator.impl.mapper",
        "cn.chenzecheng.smartrmmonolith.operation.infrastructure.mapper"})
public class SmartrmMonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartrmMonolithApplication.class, args);
    }

}
