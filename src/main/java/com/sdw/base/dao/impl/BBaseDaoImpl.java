package com.sdw.base.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.vogue.circle.database.dao.impl.BaseDaoImpl;
import com.sdw.base.dao.BBaseDao;
import com.sdw.base.utils.Page;


public class BBaseDaoImpl<T, PK extends Serializable> extends
		BaseDaoImpl<T, PK> implements BBaseDao<T, PK> {

	@Override
	@SuppressWarnings("unchecked")
	public T get(Map<String, Object> map) {
		Criteria c = getCriteria(map);
		c.setMaxResults(1);
		return (T) c.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(Map<String, Object> map, boolean cacheable) {
		Criteria c = getCriteria(map);
		c.setMaxResults(1);
		c.setCacheable(cacheable);
		return (T) c.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getList(Map<String, Object> map) {
		Criteria c = getCriteria(map);
		return (List<T>) c.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getList(Map<String, Object> map, boolean cacheable) {
		Criteria c = getCriteria(map);
		c.setCacheable(cacheable);
		return (List<T>) c.list();
	}

	@Override
	public Page<T> pageList(Page<T> page) {
		super.findByPage(page);
		page.setFilter(null);
		return page;
	}

	protected Criteria getCriteria() {
		return getSession().createCriteria(entityClass);
	}

	private Criteria getCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(entityClass);
		if (map.size() > 0) {
			Object[] keys = map.keySet().toArray();
			for (Object key : keys) {
				c.add(Restrictions.eq("" + key, map.get(key)));
			}
		}
		return c;
	}

}
