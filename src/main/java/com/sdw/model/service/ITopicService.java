package com.sdw.model.service;

import com.vogue.circle.database.service.BaseService;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.entity.Mo_Topic;

public interface ITopicService extends BaseService<Mo_Topic, String> {

	public ServiceResult<Mo_Topic> addTopic(Mo_Topic topic);

	public ServiceResult<Mo_Topic> editTopic(Mo_Topic topic);

	public ServiceResult<Mo_Topic> delTopic(Mo_Topic topic);

	public ServiceResult<Page<Mo_Topic>> pageList(Page<Mo_Topic> page);

}
