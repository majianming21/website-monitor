package xyz.ewis.websitemonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import xyz.ewis.websitemonitor.utils.ThreadPoolTaskExecutorUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ExecutorConfig
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor watchJobExecutor() {
        return ThreadPoolTaskExecutorUtil.getExecutor();
    }
}