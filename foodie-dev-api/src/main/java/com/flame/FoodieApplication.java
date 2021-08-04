package com.flame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com/flame/mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.flame.**", "org.n3r.idworker","com.flame.service"})
//@EnableTransactionManagement
@EnableScheduling       // 开启定时任务
public class FoodieApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodieApplication.class, args);
    }

}