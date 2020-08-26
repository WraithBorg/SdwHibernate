package com.sdw.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.sdw.base.entity.BEntity;
import com.sdw.business.entity.Bu_Post;


@Entity
@Table(name = "Fe_Topic")
public class Mo_Topic extends BEntity  {

	private static final long serialVersionUID = 5128785653002460720L;
	private String title;
	private Date lastPost;
	private int views;
	private int replies;
	/**论坛摘要*/
	private int digest;
	/**论坛版块*/
	private Mo_Board board;
	/**发帖人*/
	private Mo_User user;
	/**回复贴*/
	private Set<Bu_Post> post = new HashSet<Bu_Post>();

	@Column(name = "title",nullable = true,length=50)
	public String getTitle() {
		return title;
	}
	@Column(name="lastPost",columnDefinition = "DATETIME DEFAULT NULL")
	public Date getLastPost() {
		return lastPost;
	}
	@Column(name="views",columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getViews() {
		return views;
	}
	@Column(name="replies",columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getReplies() {
		return replies;
	}
	@Column(name="digest",columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getDigest() {
		return digest;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardId", nullable = false)
	public Mo_Board getBoard() {
		return board;
	}
	@OneToOne
	@JoinColumn(name="userId")
	public Mo_User getUser() {
		return user;
	}
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "topic", orphanRemoval = true) // 反转关系控制端
	public Set<Bu_Post> getPost() {
		return post;
	}
	/*---*/
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public void setReplies(int replies) {
		this.replies = replies;
	}
	public void setDigest(int digest) {
		this.digest = digest;
	}
	public void setBoard(Mo_Board board) {
		this.board = board;
	}
	public void setUser(Mo_User user) {
		this.user = user;
	}
	public void setPost(Set<Bu_Post> post) {
		this.post = post;
	}

}
