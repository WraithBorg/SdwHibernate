package com.sdw.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vogue.circle.database.service.impl.BaseServiceImpl;
import com.sdw.base.service.IZXService;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;

public abstract class IZXServiceImpl <T, PK extends Serializable> extends BaseServiceImpl<T, PK> implements IZXService<T, PK>{

	private static final Logger log = LoggerFactory.getLogger(IZXService.class);

	/**
	 * 激活分页对象的懒加载属性<br/>
	 */
	public Page<?> loadLazyByPage(Page<?> page, String[] fields) {
		List<?> list = page.getCurrentData();
		if (list != null && fields != null) for(Object item : list){
			loadLazyFields(item, fields);
		}
		return page;
	}

	@Override
	public ServiceResult<T> add(T bean) {
		log.warn("add 方法未实现");
		return null;
	}

	@Override
	public ServiceResult<T> del(T bean) {
		log.warn("del 方法未实现");
		return null;
	}

	@Override
	public ServiceResult<T> modify(T bean) {
		log.warn("modify 方法未实现");
		return null;
	}

	@Override
	public ServiceResult<T> page(Page<T> page) {
		log.warn("page 方法未实现");
		return null;
	}

}
