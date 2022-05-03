package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
/**
 * 启用定时任务
 * # 注意一定要吧项目中其他地方的@EnableScheduling 注解全都去掉，否则此配置不起作用
 */
@EnableScheduling
//配置文件读取是否启用此配置
@ConditionalOnProperty(prefix="scheduled",name = "enable", havingValue = "true")
public class SchedulerConfig implements SchedulingConfigurer {

    private final int POOL_SIZE = 5;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
