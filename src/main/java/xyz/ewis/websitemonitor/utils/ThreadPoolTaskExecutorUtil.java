package xyz.ewis.websitemonitor.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolTaskExecutorUtil
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
public class ThreadPoolTaskExecutorUtil {
    private ThreadPoolTaskExecutorUtil() {
    }
    private static ThreadPoolTaskExecutor executor;
    static {
         executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("watchJob-schedule-");
        executor.setMaxPoolSize(20);
        executor.setCorePoolSize(15);
        executor.setQueueCapacity(0);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public static ThreadPoolTaskExecutor getExecutor(){
        return executor;
    }
}
