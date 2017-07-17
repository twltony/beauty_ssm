package com.tangwl.ssm.web;

import com.alibaba.fastjson.JSON;
import com.tangwl.ssm.entity.ZzRole;
import com.tangwl.ssm.service.ZzRoleService;
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
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ZzRoleService zzRoleService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ZzRole> list(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
		List<ZzRole> userLists = zzRoleService.getAllList();
		return userLists;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public int deleteUser(HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");
		String rId = request.getParameter("roleId");
		int result = zzRoleService.deleteByPrimaryKey(rId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public int insertUser(HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");
		String username=request.getParameter("rolename");
		String descriptions =request.getParameter("descriptions");
		ZzRole zzRole = new ZzRole();
		zzRole.setRoleName(username);
		zzRole.setDescriptions(descriptions);
		int result = zzRoleService.insert(zzRole);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public int updateUser(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println(request.getParameterNames());
		String role = request.getParameter("role");
		ZzRole roleobj = JSON.parseObject(role,ZzRole.class);
		return zzRoleService.updateByPrimaryKey(roleobj);
	}

}
