package com.tangwl.ssm.web;

import com.tangwl.ssm.entity.ZzRole;
import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.ZzRoleService;
import com.tangwl.ssm.service.ZzUserService;
import com.tangwl.ssm.util.LDAPConnector;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZzRoleService zzRoleService;
	@Autowired
	private ZzUserService zzUserService;

	@ResponseBody
	@RequestMapping(value = "/setCount", method = RequestMethod.POST)
	public void setCounts(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin","*");
		String username = request.getParameter("username");
		ZzUser user = zzUserService.selectByUserName(username);
		if(user.getCount() ==null){
			user.setCount(1);
			user.setLastLoginDate(new Date());
			zzUserService.updateByPrimaryKey(user);
		}else{
			user.setCount(user.getCount()+1) ;
			user.setLastLoginDate(new Date());
			zzUserService.updateByPrimaryKey(user);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loginauth")
	public Map<String,Object> LoginAuth(HttpServletResponse response,HttpServletRequest request) {

		response.setHeader("Access-Control-Allow-Origin","*");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

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
//	public int test(){
//		ZzRole zzRole = new ZzRole();
//		zzRole.setDescription("测试");
//		zzRole.setrId("99");
//		zzRole.setRouleName("测试");
//		int i = zzRoleService.insert(zzRole);
//		return i;
//	}
//
//	public static void main(String[] args) {
//
//		System.out.println(i);
//	}

}
