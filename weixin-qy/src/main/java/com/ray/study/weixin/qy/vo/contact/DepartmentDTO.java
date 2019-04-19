package com.ray.study.weixin.qy.vo.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @desc
 *
 * @author rui.shi@hand-china.com
 * @date 2018/11/27
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {

	private Long id;
	private String name;
	private Long parentid;
	private Long order;


}
