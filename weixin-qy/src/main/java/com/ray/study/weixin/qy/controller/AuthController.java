package com.ray.study.weixin.qy.controller;


import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.common.service.AuthService;
import com.ray.study.weixin.common.vo.JsSdkConfigVO;
import com.ray.study.weixin.common.vo.TokenVO;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 22:29
 * @desc : 微信token相关接口
 **/
@Api("Token相关")
@RestController
@RequestMapping("/auth/")
@Slf4j
public class AuthController {

    @Autowired
	AuthService authService;

	@Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

	@ApiOperation("获取accessToken")
    @GetMapping("accessToken")
    public ServerResponse<TokenVO> getAccessToken(@RequestParam String state){

        TokenVO tokenVO=new TokenVO();
        tokenVO.setAccessToken(authService.getAccessToken());
        return ServerResponse.createBySuccess(tokenVO);
    }

	@ApiOperation("获取jsApiTicket")
    @GetMapping("jsApiTicket")
    public ServerResponse<TokenVO> getJsApiTicket(@RequestParam String state){
        TokenVO tokenVO=new TokenVO();
        tokenVO.setJsApiTicket(authService.getJsApiTicket());
        return ServerResponse.createBySuccess(tokenVO);
    }

	@ApiOperation("获取jsSdkConfig")
	@ApiImplicitParam(name = "requestUrl", value = "当前页面的url", required = true, dataType = "String")
    @GetMapping("jsSdkConfig")
    public ServerResponse<JsSdkConfigVO> getJsSdkConfig(@RequestParam String state, @RequestParam("requestUrl")String  url){
        return authService.getJsSdkConfig(url);
    }

}
