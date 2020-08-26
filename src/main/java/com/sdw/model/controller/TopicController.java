package com.sdw.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sdw.base.controller.BController;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.entity.Mo_Topic;
import com.sdw.model.service.ITopicService;


@Controller
@RequestMapping("/topic")
public class TopicController  extends BController{
	@Autowired
	private ITopicService topicService;
	@ResponseBody
	@RequestMapping(method={RequestMethod. POST}, params={"m=addTopic"})
	public ServiceResult<Mo_Topic> addHouse(Mo_Topic topic) {
		return topicService.addTopic(topic);
	}

	@ResponseBody
	@RequestMapping(method={RequestMethod. POST}, params={"m=editHouse"})
	public ServiceResult<Mo_Topic> editTopic(Mo_Topic topic) {
		return topicService.editTopic(topic);
	}

	@ResponseBody
	@RequestMapping(method={RequestMethod. POST}, params={"m=delHouse"})
	public ServiceResult<Mo_Topic> delHouse(Mo_Topic topic) {
		return topicService.delTopic(topic);
	}

	@ResponseBody
	@RequestMapping(method={RequestMethod. POST}, params={"m=pageList"})
	public ServiceResult<Page<Mo_Topic>> pageList(Page<Mo_Topic> page) {
		ServiceResult<Page<Mo_Topic>> res = topicService.pageList(page);
		return res;
	}
}
