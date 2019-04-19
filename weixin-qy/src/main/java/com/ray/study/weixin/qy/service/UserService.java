package com.ray.study.weixin.qy.service;


import com.ray.study.weixin.qy.vo.contact.UserVO;

import java.util.List;

public interface UserService {

    boolean createUser(UserVO userVO);

    boolean deleteUser(String userId);

    boolean updateUser(UserVO userVO);

    UserVO getUserById(String userId);

    String getUserByCode(String code);


	/**
	 * 获取部门成员详情
	 * @param departmentId 部门id
	 * @param fetchChild 1/0：是否递归获取子部门下面的成员.非必须
	 * @return
	 */
    List<UserVO> listUserByDepartmentId(Long departmentId, Long fetchChild);

	/**
	 *  获取整个公司的成员信息（权限范围内的）
	 * @return
	 */
	List<UserVO> list();




}
