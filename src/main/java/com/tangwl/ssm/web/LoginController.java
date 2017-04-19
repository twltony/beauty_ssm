package com.tangwl.ssm.web;

import com.tangwl.ssm.util.LDAPConnector;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());


	@ResponseBody
	@RequestMapping(value = "/loginauth")
	public Map<String,Object> LoginAuth(HttpServletResponse response,HttpServletRequest request) {

		response.setHeader("Access-Control-Allow-Origin","*");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println(username+"pwd"+password);

		Map<String,Object> resultMap = new HashedMap();
		boolean isLdapVaild = new LDAPConnector().Authenricate(username,password);
		if(isLdapVaild){
			resultMap.put("status","success");
			resultMap.put("username", username);
		}else{
			resultMap.put("status","error");
			resultMap.put("errMsg","AD域验证失败，请检查账号密码！");
		}

		return resultMap;
	}

}
