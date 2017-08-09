package com.tangwl.ssm.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangwl.ssm.entity.User;
import com.tangwl.ssm.entity.ZzAccess;
import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.UserService;
import com.tangwl.ssm.service.ZzAccessService;
import com.tangwl.ssm.service.ZzUserRoleService;
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
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ZzUserService zzUserService;
	@Autowired
	private ZzUserRoleService zzUserRoleService;
	@Autowired
	private ZzAccessService zzAccessService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ZzUser> list(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
		List<ZzUser> userLists = zzUserService.getAllList(1,100);
		return userLists;
	}

	@ResponseBody
	@RequestMapping(value = "/accessLog", method = RequestMethod.POST)
	public int accessLog(HttpServletResponse response, HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");
		String username = request.getParameter("username");
		ZzAccess access = new ZzAccess();
		access.setUsername(username);
		access.setAccesstime1(new Date());
		access.setDevicename(request.getParameter("platform")==null?"浏览器":request.getParameter("platform"));
		access.setImei(request.getParameter("uuid")==null?"0000":request.getParameter("uuid"));
		access.setSubject(request.getParameter("subject"));
		int result =  zzAccessService.insert(access);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/listAccessLogs", method = RequestMethod.GET)
	public List<ZzAccess> ListAccessLogs(HttpServletResponse response, HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin","*");

		return zzAccessService.selectAll();
	}

	@ResponseBody
	@RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
	public ZzUser getUserByName(HttpServletResponse response, HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userName = request.getParameter("userName");
		ZzUser user = zzUserService.selectByUserName(userName);
		if (user == null) {
			ZzUser nullUser = new ZzUser();
			nullUser.setuId(null);
			nullUser.setUsername(null);
			nullUser.setPassword(null);
			return nullUser;
		} else {
			return user;
		}
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

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String message = "";

		String user = request.getParameter("user");
		ZzUser userobj = JSON.parseObject(user,ZzUser.class);
		int updateRoleResult = 0;
		if(zzUserService.updateByPrimaryKey(userobj)==1){
			if(userobj.getRole().size()>0){

				zzUserRoleService.deleteByUid(userobj.getuId());
				for(int i=0; i<userobj.getRole().size();i++){
					updateRoleResult = zzUserRoleService.insertByUidAndRid(userobj.getuId(),userobj.getRole().get(i).getrId());
				}
			}
		}
		return message;
	}

}
