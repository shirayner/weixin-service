package com.ray.study.weixin.qy.controller;


import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import com.ray.study.weixin.qy.service.UserService;
import com.ray.study.weixin.qy.vo.contact.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 22:29
 * @desc : 微信token相关接口
 **/
@Api("微信登录")
@RestController
public class WeiXinController {
    private final static Logger logger = LoggerFactory.getLogger(WeiXinController.class);

    @Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

    @Autowired
	UserService userService;


	/**
	 * 网页授权登录
	 * @param code
	 * @param state
	 * @return 用户信息
	 */
	@ApiOperation(value="登录", notes="网页授权登录：根据code以及state获取用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name="code",value="登录授权Code",required=true,paramType="String"),
			@ApiImplicitParam(name="state",value="根据state来验证接口访问合法性",required=true,paramType="String"),
	})
	@GetMapping("/login")
    public ServerResponse<UserVO> login(@RequestParam("code") String code,@RequestParam("state") String state){

        //1.根据code获取userId
        String userId=userService.getUserByCode(code);

        //2.根据userId获取用户信息
        UserVO userVO=userService.getUserById(userId);
        logger.info("登录时获取到的用户信息：{}",userVO);

        return ServerResponse.createBySuccess(userVO);
    }

	/**
	 * 二维码扫描
	 * @param model
	 * @return
	 */
	@ApiOperation(value="二维码扫描", notes="")
	@GetMapping("/qrcode")
    public ModelAndView qrcode(Model model){
        model.addAttribute("title", "二维码扫码");
        return new ModelAndView("weixin/qrcode", "userModel", model);
    }

}
