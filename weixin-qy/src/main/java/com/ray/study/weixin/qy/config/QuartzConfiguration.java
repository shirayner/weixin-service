package com.ray.study.weixin.qy.config;

import com.ray.study.weixin.common.constant.RedisCons;
import com.ray.study.weixin.qy.job.TokenSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author : shira
 * @date : 2018/4/18
 * @time : 12:43
 * @desc :
 **/
@Configuration
public class QuartzConfiguration {

    // JobDetail
    @Bean
    public JobDetail tokenSyncJobDetail() {
        return JobBuilder.newJob(TokenSyncJob.class).withIdentity("tokenSyncJob")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger tokenSyncJobTrigger() {

        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(RedisCons.TIME_OUT_TWO_HOUR).repeatForever();

        return TriggerBuilder.newTrigger().forJob(tokenSyncJobDetail())
                .withIdentity("tokenSyncJobTrigger").withSchedule(schedBuilder).build();
    }
}
