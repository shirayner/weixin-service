package com.ray.study.weixin.qy.job;


import com.ray.study.weixin.common.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author : shira
 * @date : 2018/4/18
 * @time : 12:45
 * @desc : 同步微信相关token
 *         accessToken、jsapiTicket、apiticket
 **/
@Slf4j
public class TokenSyncJob extends QuartzJobBean {

    @Autowired
    private AuthService authService;



    /* (non-Javadoc)
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		authService.syncToken();
    }







}