package com.flame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描 mybatis mapper 包路径
@MapperScan(basePackages = "com/flame/mapper")
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"com.flame.**", "org.n3r.idworker","com.flame.service"})
//@EnableConfigurationProperties
public class PaymentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
}
