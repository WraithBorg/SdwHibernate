package com.sdw.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sdw.base.entity.BEntity;
import com.sdw.model.entity.Mo_User;

/**
 * <p>
 * Description: 登陆日志
 * </p>
 */
@Entity
@Table(name = "Bu_LoginLog")
public class Bu_LoginLog extends BEntity {

	private static final long serialVersionUID = 1963512826702435895L;
	/** 登陆ip */
	private String ip;
	/** 登陆日期 */
	private Date loginDate;
	/** 登陆人 */
	private Mo_User user;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "ip", nullable = true, length = 20)
	public String getIp() {
		return ip;
	}

	@Column(name = "loginDate", columnDefinition = "DATETIME DEFAULT NULL")
	public Date getLoginDate() {
		return loginDate;
	}

	@OneToOne
	@JoinColumn(name = "userId")
	public Mo_User getUser() {
		return user;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public void setUser(Mo_User user) {
		this.user = user;
	}

}
