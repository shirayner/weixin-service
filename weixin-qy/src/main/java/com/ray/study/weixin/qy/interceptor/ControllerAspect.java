/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.ray.study.weixin.qy.interceptor;

import com.ray.study.weixin.common.constant.WeiXinCons;
import com.ray.study.weixin.common.http.ServerResponse;
import com.ray.study.weixin.qy.AgentAuthConfig;
import com.ray.study.weixin.qy.config.WeiXinQYAuthConfig;
import com.ray.study.weixin.qy.util.AgentAuthConfigLocalHelper;
import com.ray.study.weixin.qy.vo.BaseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ControllerAspect.
 * @author xiaoyu
 */
@Aspect
@Component
@Slf4j
public  class ControllerAspect {

	@Autowired
	WeiXinQYAuthConfig weiXinAuthConfig;

	@Pointcut("execution(* com.ray.study.weixin.qy.controller.*.*(..)) "
			+ "&& !@target(com.ray.study.weixin.qy.annotation.IgnoreToken) ")
    public void executeService() {

    }

    @Around("executeService()")
    public  Object setAgentAuthConfig(final ProceedingJoinPoint proceedingJoinPoint){

		Object[] args = proceedingJoinPoint.getArgs();
		// get state
		String state  = getPermissionFiled( args );

		Object result = null;
		if(state == null ){
			log.error("illegal argument ! there is no parameter named state,which is needed!" );
			result = ServerResponse.createByErrorMessage("illegal argument ! there is no parameter named state,which is needed!");
		}else{

			// save to Local
			boolean isStateValid = savaAgentAuthConfigToLocal(state);

			if( !isStateValid  ){
				log.error("illegal argument ! [state={}] the parameter named state is invalid", state);
				result = ServerResponse.createByErrorMessage("illegal argument ! the parameter named state is invalid");
			}else {
				try {
					result = proceedingJoinPoint.proceed();
				} catch (Throwable throwable) {
					log.error("set state failure" );
					throwable.printStackTrace();
				}
			}

		}
		return result;
	}



	private boolean savaAgentAuthConfigToLocal(String state){
		List<AgentAuthConfig> agentAuthConfigList = weiXinAuthConfig.getAgent();
		boolean isStateValid = false;
		for(AgentAuthConfig agentAuthConfig:agentAuthConfigList){
			if(state.equals(agentAuthConfig.getState())){
				isStateValid = true;
				AgentAuthConfigLocalHelper.getInstance().setAgentAuthConfig(agentAuthConfig);
			}
		}

		return isStateValid;
	}

	private String getPermissionFiled(Object[] args ){
		HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() ;
		String httpMethod = request.getMethod();

		//获取请求参数集合并进行遍历拼接
		String state = null;

		if(args.length>0){
			if("GET".equals(httpMethod)){
				state = request.getParameter(WeiXinCons.KEY_STATE);
			} else if("POST".equals(httpMethod)){
				Map<String, Object> paraMap = new HashMap<>();

				for (Object argItem : args) {
					if (argItem instanceof BaseVO) {
						paraMap = getFieldMap(paraMap, argItem);
					}
				}

				state = (String) paraMap.get(WeiXinCons.KEY_STATE);
			}
		}

		return state;
	}


	public  Map<String, Object> getFieldMap(Map map, Object obj) {

		// 得到类对象
		Class clazz = (Class) obj.getClass();
		/* 得到类中的所有属性集合 */
		Field[] fields = clazz.getDeclaredFields();
		map = getFieldMap(map, fields, obj);

		/** 处理父类字段**/
		Class<?> superClass = clazz.getSuperclass();
		Field[] superClassFields = superClass.getDeclaredFields();
		map = getFieldMap(map, superClassFields, obj);

		return map;
	}


	private static Map<String, Object> getFieldMap( Map FieldMap ,Field[] fields, Object obj){

		for (Field f : fields) {
			f.setAccessible(true); // 设置些属性是可以访问的
			Object val = new Object();
			try {
				// 得到此属性的值
				val = f.get(obj);

				FieldMap.put(f.getName(), val);// 设置键值
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}


		return  FieldMap;
	}






}
