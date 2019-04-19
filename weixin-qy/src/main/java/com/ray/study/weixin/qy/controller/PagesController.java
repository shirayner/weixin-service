package com.ray.study.weixin.qy.controller;

import com.ray.study.weixin.qy.annotation.IgnoreToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/11/7
 */
@Api("微信登录")
@Controller
@RequestMapping("/page")
@IgnoreToken
public class PagesController {
	/**
	 * js sdk demo
	 *
	 * @return
	 */
	@ApiOperation(value="打开系统默认浏览器", notes="")
	@GetMapping("/index")
	public String  test(){
		return "index";
	}

}
