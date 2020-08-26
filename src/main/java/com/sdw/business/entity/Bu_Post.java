package com.sdw.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sdw.base.entity.BEntity;
import com.sdw.model.entity.Mo_Board;
import com.sdw.model.entity.Mo_Topic;
import com.sdw.model.entity.Mo_User;

/**
 * <p>
 * Description: 回贴
 * </p>
 *
 *
 * @date 2016-12-16下午12:49:23
 */
@Entity
@Table(name = "Bu_Post")
public class Bu_Post extends BEntity {
	private static final long serialVersionUID = 2368773610062240284L;
	private int postType;
	private String postTitle;
	private String postText;
	/** 论坛版块 */
	private Mo_Board board;
	/** 发帖人 */
	private Mo_User user;
	/** 主题帖 */
	private Mo_Topic topic;

	@Column(name = "postType", columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getPostType() {
		return postType;
	}

	@Column(name = "postTitle", nullable = true, length = 50)
	public String getPostTitle() {
		return postTitle;
	}

	@Column(name = "postText", nullable = true, length = 50)
	public String getPostText() {
		return postText;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardId", nullable = false)
	public Mo_Board getBoard() {
		return board;
	}

	@OneToOne
	@JoinColumn(name = "userId")
	public Mo_User getUser() {
		return user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicId", nullable = false)
	public Mo_Topic getTopic() {
		return topic;
	}

	/*---*/
	public void setPostType(int postType) {
		this.postType = postType;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}


	public void setBoard(Mo_Board board) {
		this.board = board;
	}

	public void setUser(Mo_User user) {
		this.user = user;
	}

	public void setTopic(Mo_Topic topic) {
		this.topic = topic;
	}

}
