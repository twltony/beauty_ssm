package com.tangwl.ssm.dao;


import java.util.List;


import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.ZzUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tangwl.ssm.entity.User;

/**
 *
 * @author tangwl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class UserDaoTest {

    @Autowired
    private ZzUserService zzUserService;

	@Test
	public void testQueryById() {
		ZzUser user=zzUserService.selectByUserName("tangwl");
		System.out.println(user);
		System.out.println("--------------------------");
	}

//	@Test
//	public void testQueryAll() {
//		List<User> list=userDao.queryAll(0, 100);
//		for (User user : list) {
//			System.out.println(user);
//		}
//	}
//
//	@Test
//	public void testAddScore() {
//		userDao.addScore(10);
//		List<User> list=userDao.queryAll(0, 100);
//		for (User user : list) {
//			System.out.println(user);
//		}
//	}

}
