package com.tangwl.ssm.service;

import java.util.List;

import com.tangwl.ssm.entity.User;

public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
