package xyz.ewis.websitemonitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author INK
 */
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"xyz.ewis.websitemonitor.dao"})
public class WebsiteMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteMonitorApplication.class, args);
    }

}
