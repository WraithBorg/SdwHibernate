package com.sdw.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdw.base.service.impl.IZXServiceImpl;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.dao.IUserDAO;
import com.sdw.model.entity.Mo_User;
import com.sdw.model.service.IUserService;

@Service("userService")
@Transactional
public class UserServiceImpl  extends IZXServiceImpl <Mo_User, String> implements IUserService {
	@Autowired
	private IUserDAO userDao;


	@Override
	public ServiceResult<Page<Mo_User>> pageList(Page<Mo_User> page) {
		page.setSize(10);
		page.setStart(0);
		page.setLength(10);
		page = userDao.pageList(page);
		return new ServiceResult<Page<Mo_User>>(true, page);
	}


	@Override
	public ServiceResult<Mo_User> addUser(Mo_User user) {
		userDao.save(user);
		return new ServiceResult<>(true,user);
	}


	@Override
	public ServiceResult<Mo_User> editUser(Mo_User user) {
		userDao.update(user);
		return new ServiceResult<>(true,user);
	}


	@Override
	public ServiceResult<Mo_User> delUser(Mo_User user) {
		userDao.delete(user);
		//userDao.delete(user.getId());
		return new ServiceResult<>(true,user);
	}


	@Override
	public Mo_User getById(String id) {
		return userDao.get(id);
	}


	@Override
	public ServiceResult<Page<Mo_User>> update4Test(int num,String zhao) {
		Mo_User user = getById("2c9e188159911ab30159911ca70c0002");
		user.setCredit(num);
		user.setName(zhao);
		userDao.update(user);
		return null;
	}
}
