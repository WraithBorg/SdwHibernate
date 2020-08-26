package com.sdw.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdw.base.service.impl.IZXServiceImpl;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.dao.ITopicDAO;
import com.sdw.model.entity.Mo_Topic;
import com.sdw.model.service.ITopicService;

@Service("topicService")
@Transactional
public class TopicServiceImpl extends IZXServiceImpl <Mo_Topic, String> implements ITopicService {
	@Autowired
	private ITopicDAO topicDao;

	@Override
	public ServiceResult<Mo_Topic> addTopic(Mo_Topic topic) {
		topicDao.save(topic);
		return new ServiceResult<Mo_Topic>(true,topic);
	}

	@Override
	public ServiceResult<Mo_Topic> editTopic(Mo_Topic topic) {
		topicDao.update(topic);
		return new ServiceResult<Mo_Topic>(true,topic);
	}

	@Override
	public ServiceResult<Mo_Topic> delTopic(Mo_Topic topic) {
		topicDao.delete(topic);
		return new ServiceResult<Mo_Topic>(true,topic);
	}

	@Override
	public ServiceResult<Page<Mo_Topic>> pageList(Page<Mo_Topic> page) {
		page.setSize(10);
		page.setStart(0);
		page.setLength(10);
		page = topicDao.pageList(page);
		//loadLazyByPage(page, new String[] { "shop" });
		return new ServiceResult<Page<Mo_Topic>>(true, page);
	}
}
