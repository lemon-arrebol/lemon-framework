package com.lemon.cloud.author.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

/**
 * @param
 * @author lemon
 * @description
 * @return
 * @date 2019-08-11 18:09
 */
@Slf4j
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthorizationApplication.class);
    }

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        WebApplicationContext webApplicationContext = super.run(application);
        Environment env = webApplicationContext.getEnvironment();

        log.info("\n----------------------------------------------------------\n\t" +
                        "服务端:\t'{}' 启动完成! \n\t" +
                        "端  口:\t{}\n\t" +
                        "环  境:\t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getActiveProfiles());

        return webApplicationContext;
    }
}
