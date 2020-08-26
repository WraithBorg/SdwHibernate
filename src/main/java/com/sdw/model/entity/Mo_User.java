package com.sdw.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sdw.base.entity.BEntity;


@Entity
@Table(name = "Fe_User")
public class Mo_User extends BEntity {

	private static final long serialVersionUID = 1878589339518353849L;
	private String name;
	private String username;
	private String password;
	private long locked;
	private int credit;
	private Date lastVisit;
	private  String lastIp;
	private Set<Mo_Board> boards = new HashSet<Mo_Board>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name = "name",nullable = true,length=50)
	public String getName() {
		return name;
	}
	@Column(name = "username",nullable = true,length=50)
	public String getUsername() {
		return username;
	}
	@Column(name = "password",nullable = true,length=50)
	public String getPassword() {
		return password;
	}
	@Column(name = "locked",columnDefinition = "INTEGER NULL DEFAULT NULL")
	public long getLocked() {
		return locked;
	}
	@Column(name="credit",columnDefinition = "INT NOT NULL DEFAULT 0")
	public int getCredit() {
		return credit;
	}
	@Column(name="lastVisit",columnDefinition = "DATETIME DEFAULT NULL")
	public Date getLastVisit() {
		return lastVisit;
	}
	@Column(name="lastIp",nullable = true,length=50)
	public String getLastIp() {
		return lastIp;
	}

	@ManyToMany(targetEntity = Mo_Board.class,fetch = FetchType.LAZY)
	@JoinTable(name = "Fe_User_Board",joinColumns = @JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="boardId"))
	public Set<Mo_Board> getBoards() {
		return boards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLocked(long locked) {
		this.locked = locked;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public void setBoards(Set<Mo_Board> boards) {
		this.boards = boards;
	}

}
