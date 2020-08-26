package com.sdw.model.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdw.base.entity.BEntity;


@Entity
@Table(name = "Fe_Board")
public class Mo_Board extends BEntity {
	private static final long serialVersionUID = 4013957359459518869L;
	private String name;
	private int topicNum;
	private Set<Mo_User> users = new HashSet<Mo_User>();
	private Set<Mo_Topic> topics = new HashSet<Mo_Topic>();

	@Column(name="name",nullable = true,length=50)
	public String getName() {
		return name;
	}
	@Column(name="topicNum",columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getTopicNum() {
		return topicNum;
	}
	@ManyToMany(mappedBy="boards",targetEntity = Mo_User.class,fetch = FetchType.LAZY)
	public Set<Mo_User> getUsers() {
		return users;
	}
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board", orphanRemoval = true) // 反转关系控制端
	public Set<Mo_Topic> getTopics() {
		return topics;
	}
	/*---*/
	public void setName(String name) {
		this.name = name;
	}
	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}
	public void setUsers(Set<Mo_User> users) {
		this.users = users;
	}
	public void setTopics(Set<Mo_Topic> topics) {
		this.topics = topics;
	}



}
