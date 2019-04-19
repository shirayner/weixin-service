package com.ray.study.weixin.qy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ray.study.weixin.common.constant.WeiXinCons;
import com.ray.study.weixin.common.http.HttpHelper;
import com.ray.study.weixin.common.service.AuthService;
import com.ray.study.weixin.qy.service.DepartmentService;
import com.ray.study.weixin.qy.service.UserService;
import com.ray.study.weixin.qy.vo.contact.DepartmentDTO;
import com.ray.study.weixin.qy.vo.contact.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : shira
 * @date : 2018/4/17
 * @time : 23:07
 * @desc :
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	HttpHelper httpHelper;

    @Autowired
	AuthService authService;

	@Autowired
	DepartmentService departmentService;

    /** 1.创建成员 */
    private static final String CREATE_USER_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/create?access_token=";

    /** 2.删除成员 */
    private static final String DELETE_USER_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/delete?access_token=";

    /** 3.更新成员 */
    private static final String UPDATE_USER_URL=WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/update?access_token=";

    /** 4.获取成员信息 */
    private static final String GET_USER_BY_ID_URL= WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/get?access_token=";

    /** 5.根据code 获取用户信息：网页授权时**/
    private static final String GET_USER_BY_CODE_URL= WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/getuserinfo?access_token=";


    /** 6.获取部门成员详情**/
    private static final String LIST_USER_BY_DEPARTMENT_ID_URL= WeiXinCons.API_GATEWAY_QY+"/cgi-bin/user/list?access_token=";




    @Override
    public boolean createUser(UserVO userVO) {

        String accessToken=authService.getContactsAccessToken();
        String url=CREATE_USER_URL+accessToken;

        JSONObject jsonObject = httpHelper.post(url, userVO);

        return httpHelper.isWXSuccess(jsonObject);
    }

    @Override
    public boolean deleteUser(String userId) {

        String accessToken=authService.getContactsAccessToken();
        String url=DELETE_USER_URL+accessToken+"&userid="+userId;

        JSONObject jsonObject = httpHelper.get(url);

        return httpHelper.isWXSuccess(jsonObject);
    }

    @Override
    public boolean updateUser(UserVO userVO) {

        String accessToken=authService.getContactsAccessToken();
        String url=UPDATE_USER_URL+accessToken;

        JSONObject jsonObject = httpHelper.post(url, userVO);

        return httpHelper.isWXSuccess(jsonObject);
    }



    /**
     * 2.获取成员信息
     * @param userId
     * @return
     */
    @Override
    public UserVO getUserById(String userId) {
        String userStr=doGetUserById( userId).toJSONString();
        UserVO userVo = JSON.parseObject(userStr, UserVO.class);
        return userVo;
    }

    /**
     *  2.1.  获取成员信息
     * @return
     */
    private JSONObject doGetUserById(String userId) {

        String accessToken=authService.getContactsAccessToken();
        String url=GET_USER_BY_ID_URL+accessToken+"&userid="+userId;

        JSONObject jsonObject=httpHelper.get( url);

        return jsonObject;
    }


    /**
     *  登录时根据code获取用户信息
     * @param code
     * @return
     */
    @Override
    public String getUserByCode(String code) {
       String userId= doGetUserByCode(code).getString(WeiXinCons.KEY_USER_ID);
        return userId;
    }

    /**
     *  2.1.  获取成员信息
     * @return
     */
    private JSONObject doGetUserByCode(String code) {

        String accessToken=authService.getAccessToken();
        String url=GET_USER_BY_CODE_URL+accessToken+"&code="+code;

        JSONObject jsonObject=httpHelper.get( url);

        return jsonObject;
    }

	@Override
	public List<UserVO> listUserByDepartmentId(Long departmentId, Long fetchChild) {
		String accessToken=authService.getContactsAccessToken();
		String url=LIST_USER_BY_DEPARTMENT_ID_URL+accessToken+"&department_id="+departmentId;

		if(fetchChild != null){
			url = url + "&fetch_child=" + fetchChild;
		}


		JSONObject jsonObject=httpHelper.get( url);

		if(jsonObject.getInteger("errcode") == 0){
			List<UserVO> userVOList= new ArrayList<>();

			JSONArray userJSONArray=jsonObject.getJSONArray("userlist");

			for (int i = 0; i < userJSONArray.size(); i++) {

				UserVO userVO  =  userJSONArray.getObject(i,UserVO.class);

				userVOList.add(userVO);
			}

			return  userVOList;
		}

		return Collections.emptyList();
	}

	@Override
	public List<UserVO> list() {
		List<UserVO> userVOList =  new ArrayList<>();

		List<DepartmentDTO>  departmentList =departmentService.list();

		for(DepartmentDTO  departmentDTO : departmentList){

			userVOList.addAll( listUserByDepartmentId( departmentDTO.getId(), 0L));
		}

		return userVOList;
	}
}
