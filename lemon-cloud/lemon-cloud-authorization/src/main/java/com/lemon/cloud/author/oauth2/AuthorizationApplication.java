package com.lemon.cloud.author.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @param
 * @author lemon
 * @description
 * @return
 * @date 2019-08-12 22:43
 */
@Slf4j
@SpringBootApplication
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuthorizationApplication.class);
        Environment env = app.run(args).getEnvironment();

        log.info("\n----------------------------------------------------------\n\t" +
                        "服务端:\t'{}' 启动完成! \n\t" +
                        "端  口:\t{}\n\t" +
                        "环  境:\t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }
}