package com.hanggle.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hanggle"})
public class Application {

	public static void main(String[] args) throws Exception {
		//第一种启动方式
//		SpringApplication.run(Application.class, args);

		//第二种启动方式自定义配置
		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
