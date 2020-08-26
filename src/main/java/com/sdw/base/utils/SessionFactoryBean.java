package com.sdw.base.utils;

import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.vogue.circle.util.SprContext;

public class SessionFactoryBean extends LocalSessionFactoryBean {

	SprContext context;

	public SprContext getContext() {
		return context;
	}

	public void setContext(SprContext context) {
		this.context = context;
	}
}
