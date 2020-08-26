package com.sdw.base.dao;

import com.vogue.circle.database.dao.BaseDao;
import com.sdw.base.utils.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BBaseDao<T, PK extends Serializable> extends BaseDao<T, PK> {
    /**
     * 根据查询条件返回一条对象
     */
    public T get(Map<String, Object> map);

    /**
     * 根据查询条件返回一条对象,是否开启查询缓存
     */
    public T get(Map<String, Object> map, boolean cacheable);

    /**
     * 根据查询条件返回对象列表
     */
    public List<T> getList(Map<String, Object> map);

    /**
     * 根据查询条件返回对象列表,是否开启查询缓存
     */
    public List<T> getList(Map<String, Object> map, boolean cacheable);

    /**
     * 分页查询
     */
    public Page<T> pageList(Page<T> page);

}
