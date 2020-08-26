package com.sdw.model.service;

import com.vogue.circle.database.service.BaseService;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.entity.Mo_User;

public interface IUserService extends BaseService<Mo_User, String>{
	public ServiceResult<Mo_User> addUser(Mo_User user);

	public ServiceResult<Mo_User> editUser(Mo_User user);

	public ServiceResult<Mo_User> delUser(Mo_User user);

	public ServiceResult<Page<Mo_User>> pageList(Page<Mo_User> page);

	public Mo_User getById(String id);

	public ServiceResult<Page<Mo_User>> update4Test(int num, String zhao);
}
