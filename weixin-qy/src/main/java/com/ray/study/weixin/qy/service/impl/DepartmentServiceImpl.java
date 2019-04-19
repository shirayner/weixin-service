package com.ray.study.weixin.qy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ray.study.weixin.common.constant.WeiXinCons;
import com.ray.study.weixin.common.http.HttpHelper;
import com.ray.study.weixin.common.service.AuthService;
import com.ray.study.weixin.qy.service.DepartmentService;
import com.ray.study.weixin.qy.vo.contact.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/11/27
 */
@Service
@Slf4j
public class DepartmentServiceImpl  implements DepartmentService {


	@Autowired
	HttpHelper httpHelper;

	@Autowired
	AuthService authService;


	/** 1.创建部门 */
	private static final String CREATE_DEPARTMENT_URL= WeiXinCons.API_GATEWAY_QY+"/cgi-bin/department/create?access_token=";

	/** 2.更新部门 */
	private static final String UPDATE_DEPARTMENT_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/department/update?access_token=";

	/** 3.删除部门 */
	private static final String DELETE_DEPARTMENT_URL=WeiXinCons.API_GATEWAY_QY+"/department/delete?access_token=";

	/** 4.获取部门列表 */
	private static final String LIST_DEPARTMENT_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/department/list?access_token=";





	@Override
	public boolean create(DepartmentDTO departmentDTO) {
		return false;
	}

	@Override
	public boolean update(DepartmentDTO departmentDTO) {
		return false;
	}

	@Override
	public boolean delete(Long id) {
		return false;
	}

	@Override
	public List<DepartmentDTO> list(Long id) {

		String accessToken=authService.getContactsAccessToken();

		String url = LIST_DEPARTMENT_URL+accessToken;

		if(id != null){
			url = url + "&id=" + id;
		}

		JSONObject jsonObject=httpHelper.get( url );

		if(jsonObject.getInteger("errcode") == 0){
			List<DepartmentDTO> departmentDTOList= new ArrayList<>();

			JSONArray departmentJSONArray=jsonObject.getJSONArray("department");

			for (int i = 0; i < departmentJSONArray.size(); i++) {

				DepartmentDTO departmentDTO  =  departmentJSONArray.getObject(i, DepartmentDTO.class);

				departmentDTOList.add(departmentDTO);
			}

			return  departmentDTOList;
		}

		return Collections.emptyList();
	}


	@Override
	public List<DepartmentDTO> list() {
		return list(null);
	}
}
