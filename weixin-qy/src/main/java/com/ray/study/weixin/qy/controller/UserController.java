package com.ray.study.weixin.qy.controller;


import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.qy.annotation.IgnoreToken;
import com.ray.study.weixin.qy.service.UserService;
import com.ray.study.weixin.qy.vo.contact.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 22:29
 * @desc : 微信token相关接口
 **/
@Api("用户管理")
@RestController
@RequestMapping("/user")
@IgnoreToken
public class UserController {

    @Autowired
	UserService userService;

	@ApiIgnore
	@ApiOperation("用户新增")
	@ApiImplicitParam(name = "userVO", value = "用户实体", required = true, dataType = "UserVO")
	@PostMapping("/add")
    public ServerResponse<UserVO> create( @RequestBody UserVO userVO){
        boolean isSuccess=userService.createUser(userVO);
        if(isSuccess){
            return ServerResponse.createBySuccessMessage("新增用户信息成功");
        }
        return ServerResponse.createByErrorMessage("新增用户信息失败");
    }

	@ApiIgnore
	@ApiOperation("用户删除")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
	@GetMapping("/delete")
    public ServerResponse<UserVO> delete( @RequestParam String userId){
        boolean isSuccess= userService.deleteUser(userId);
        if(isSuccess){
            return  ServerResponse.createBySuccessMessage("删除用户信息成功");
        }
        return ServerResponse.createByErrorMessage("删除用户信息失败");
    }

	@ApiIgnore
	@ApiOperation("用户更新")
	@ApiImplicitParam(name = "userVO", value = "用户实体", required = true, dataType = "UserVO")
	@PostMapping("/update")
    public ServerResponse<UserVO> update(@RequestBody UserVO userVO){
       boolean isSuccess= userService.updateUser(userVO);
       if(isSuccess){
          return ServerResponse.createBySuccessMessage("更新用户信息成功");
       }
        return ServerResponse.createByErrorMessage("更新用户信息失败");
    }

	@ApiOperation("根据用户ID获取用户信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
	@GetMapping("/get")
    public ServerResponse<UserVO> get(@RequestParam String userId){
        UserVO userVO=userService.getUserById(userId);
        return ServerResponse.createBySuccess(userVO);
    }

	@ApiOperation("根据部门ID获取用户信息")
	@GetMapping("/list/details")
    public ServerResponse<List<UserVO>> list( @RequestParam Long departmentId , @RequestParam(value="fetchChild", required = false) Long fetchChild){
		List<UserVO> userVOList = userService.listUserByDepartmentId(departmentId, fetchChild);
        return ServerResponse.createBySuccess(userVOList);
    }

	@ApiOperation("获取全量组织架构下的成员信息")
	@GetMapping("/list")
    public ServerResponse<List<UserVO>> list(){
        return ServerResponse.createBySuccess(userService.list());
    }

}
