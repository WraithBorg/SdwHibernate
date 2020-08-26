package com.sdw.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdw.base.dao.impl.BBaseDaoImpl;
import com.sdw.model.dao.IUserDAO;
import com.sdw.model.entity.Mo_User;

@Repository("userDao")
public class UserDaoImpl extends BBaseDaoImpl<Mo_User, String>  implements IUserDAO {


}
