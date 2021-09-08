package org.emall;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.diwayou.fsm.annotation.EnableStateMachine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gaopeng 2021/1/15
 */
@SpringBootApplication
@EnableStateMachine
@EnableDubbo(scanBasePackages = {"org.emall.**.api","org.emall.**.thirdparty"})
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
