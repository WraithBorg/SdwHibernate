package com.sdw.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdw.base.dao.impl.BBaseDaoImpl;
import com.sdw.model.dao.ITopicDAO;
import com.sdw.model.entity.Mo_Topic;

@Repository("topicDao")
public class TopicDaoImpl  extends BBaseDaoImpl<Mo_Topic, String>  implements ITopicDAO {

	public String getMaxSerialNumber() {

		String sql = "select max(code) from house where code REGEXP '[0-9]{4}' and code like '____' ";
		Object result =  getSession().createSQLQuery(sql).uniqueResult();
		if (result == null) {
			return "0000";
		} else {
			return result.toString();
		}
	}
}
