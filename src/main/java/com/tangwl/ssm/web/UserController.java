package com.tangwl.ssm.web;

import com.tangwl.ssm.entity.User;
import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.UserService;
import com.tangwl.ssm.service.ZzUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ZzUserService zzUserService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ZzUser> list(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
		List<ZzUser> userLists = zzUserService.getAllList(1,20);
		return userLists;
	}
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public int deleteUser(HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");
		String uId = request.getParameter("userId");
		int result = zzUserService.deleteByPrimaryKey(uId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public int insertUser(HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");
		String username=request.getParameter("username");
		String displayname =request.getParameter("displayname");
		Date date = new Date();
		Short isvalid = 0;
		if(request.getParameter("isvalid").equals("true")){
			isvalid = 1;
		}
		ZzUser zzUser = new ZzUser();
		zzUser.setUsername(username);
		zzUser.setVseusername(displayname);
		zzUser.setIsvalid(isvalid);
		zzUser.setCreateDate(date);
		zzUser.setTs(date);
		int result = zzUserService.insert(zzUser);
		return result;
	}

}
