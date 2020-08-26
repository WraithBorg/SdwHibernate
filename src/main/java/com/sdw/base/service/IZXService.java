package com.sdw.base.service;

import java.io.Serializable;
import com.vogue.circle.database.service.BaseService;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;

public interface IZXService<T, PK extends Serializable> extends
		BaseService<T, PK> {

	public ServiceResult<T> add(T bean);

	public ServiceResult<T> del(T bean);

	public ServiceResult<T> modify(T bean);

	public ServiceResult<T> page(Page<T> page);
}
