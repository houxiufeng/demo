package com.example.demo.task;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author fitz
 */
@Component
//ConditionalOnProperty 可单个控制类，也可以方法级别控制
//@ConditionalOnProperty(prefix="scheduled",name = "enable", havingValue = "true")
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * fixedRate：固定速率执行。每5秒执行一次。
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTimeWithFixedRate() {
        logger.info("reportCurrentTimeWithFixedRate Current Thread : {}", Thread.currentThread().getName());
        logger.info("Fixed Rate Task : The time is now {}", DateUtil.now());
    }

    /**
     * fixedDelay：固定延迟执行。距离上一次调用成功后2秒才执。
     */
//    @Scheduled(fixedDelay = 2000)
    public void reportCurrentTimeWithFixedDelay() {
        logger.info("reportCurrentTimeWithFixedDelay Current Thread : {}", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(3);
            logger.info("Fixed Delay Task : The time is now {}", DateUtil.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialDelay:初始延迟。任务的第一次执行将延迟5秒，然后将以5秒的固定间隔执行。
     */
//    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void reportCurrentTimeWithInitialDelay() {
        logger.info("reportCurrentTimeWithInitialDelay Current Thread : {}", Thread.currentThread().getName());
        logger.info("Fixed Rate Task with Initial Delay : The time is now {}", DateUtil.now());
    }

    /**
     * cron：使用Cron表达式。　每分钟的1，2秒运行
     */
//    @Scheduled(cron = "1-2 * * * * ? ")
    public void reportCurrentTimeWithCronExpression() {
        logger.info("reportCurrentTimeWithCronExpression Current Thread : {}", Thread.currentThread().getName());
        logger.info("Cron Expression: The time is now {}", DateUtil.now());
    }
}

