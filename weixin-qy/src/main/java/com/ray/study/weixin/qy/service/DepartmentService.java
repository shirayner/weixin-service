package com.ray.study.weixin.qy.service;

import com.ray.study.weixin.qy.vo.contact.DepartmentDTO;

import java.util.List;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/11/27
 */
public interface DepartmentService {

	boolean create(DepartmentDTO departmentDTO);

	boolean update(DepartmentDTO departmentDTO);

	boolean delete(Long id);

	/**
	 * 获取部门列表
	 * @param departmentId 部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
	 * @return
	 */
	List<DepartmentDTO> list(Long departmentId);


	/**
	 * 获取全量组织架构下的部门列表
	 * @return
	 */
	List<DepartmentDTO> list();


}
