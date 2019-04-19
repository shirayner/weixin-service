package com.ray.study.weixin.qy.vo.contact;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : shira
 * @date : 2018/4/20
 * @time : 13:14
 * @desc :
 **/
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO  extends BaseVO {

	//是，成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节
	private String userid;
	//是，成员名称。长度为1~64个字符
	private String name;
	//是，成员所属部门id列表,不超过20个
	private int[] department;
	private int[] order;
	private String position;
	private String mobile;
	private String gender;
	private String email;
	private int isleader;
	private String avatar;
	private String telephone;
	private int enable;
	private String alias;
	private String status;



}
