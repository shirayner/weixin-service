package com.ray.study.weixin.qy.controller;

import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.qy.annotation.IgnoreToken;
import com.ray.study.weixin.qy.service.DepartmentService;
import com.ray.study.weixin.qy.vo.contact.DepartmentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/11/27
 */
@Api("部门管理")
@RestController
@RequestMapping("/department")
@IgnoreToken
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;


	@ApiOperation("获取部门列表")
	@ApiImplicitParam(name = "id", value = "部门ID,非必须", dataType = "long")
	@GetMapping("/list")
	public ServerResponse<List<DepartmentDTO>> list( @RequestParam(value="id", required = false) Long id){
		return ServerResponse.createBySuccess(departmentService.list(id));
	}



}
