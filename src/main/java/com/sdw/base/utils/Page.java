package com.sdw.base.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.vogue.circle.database.util.IPage;

/**
 * 描述：分页类<br>
 */
public class Page<T> implements IPage<T> {

	private static final long serialVersionUID = 5053918223009575468L;
	private static Pattern numberPattern = Pattern.compile("^-?\\d+(?:\\.\\d+)?$");

	/**
	 * 当前页码
	 */
	private int draw = 0;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * 起始记录
	 */
	private int start = 0;

	@Override
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 每页记录数(缓存)
	 */
	private int length = 0;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 每页记录数(实际)
	 */
	private int size = 0;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 返回总记录数
	 */
	private long recordsTotal = 0;

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 *
	 */
	private long recordsFiltered = 0;

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * 用于保存返回数据
	 */
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 用于保存查询数据
	 */
	private T filter;

	public T getFilter() {
		return filter;
	}

	public void setFilter(T filter) {
		this.filter = filter;
	}

	/**
	 * 普通搜索 Search
	 */
	private Map<String, String> s;
	public Map<String, String> getS() {
		return s;
	}
	public void setS(Map<String, String> s) {
		this.s = s;
	}
	public String getSearch(String key) {
		if (s == null || StringUtils.isEmpty(key)) return null;
		if (s.containsKey(key)) return s.get(key);
		return null;
	}

	/**
	 * 自定义搜索 Custom
	 */
	private Map<String, String> c;
	public Map<String, String> getC() {
		return c;
	}
	public void setC(Map<String, String> c) {
		this.c = c;
	}
	public String getCustomSearch(String key) {
		if (c == null) return null;
		return c.get(key);
	}

	/**
	 * 模糊搜索 Like
	 */
	private Map<String, String> l;
	public Map<String, String> getL() {
		return l;
	}
	public void setL(Map<String, String> l) {
		this.l = l;
	}

	/**
	 * 范围搜索 Range
	 */
	private Map<String, List<String>> r;
	public Map<String, List<String>> getR() {
		return r;
	}
	public void setR(Map<String, List<String>> r) {
		this.r = r;
	}
	public List<String> getRangeSearch(String key) {
		if (r == null || StringUtils.isEmpty(key)) return null;
		if (r.containsKey(key)) return r.get(key);
		return null;
	}

	/**
	 * 多重查询 Multi
	 */
	private Map<String, String> u;
	public Map<String, String> getU() {
		return u;
	}
	public void setU(Map<String, String> u) {
		this.u = u;
	}
	public String getMultiSearch(String key) {
		if (u == null || StringUtils.isEmpty(key)) return null;
		if (u.containsKey(key)) return u.get(key);
		return null;
	}

	/**
	 * 排序方式 Order
	 */
	private Map<String, String> o;
	public Map<String, String> getO() {
		return o;
	}
	public void setO(Map<String, String> o) {
		this.o = o;
	}

	/**
	 * 自定义查询
	 */
	private String customCondition = "";
	public String getCustomCondition() {
		return customCondition;
	}
	public void setCustomCondition(String customCondition) {
		this.customCondition = customCondition;
	}

	private String getMultiCondition() {
		if (u == null) return "";
		String val = null;
		StringBuffer res = new StringBuffer();
		for (Entry<String, String> entry : u.entrySet()) {
			val = entry.getValue();
			if (StringUtils.isNotEmpty(val)) {
				res.append(" OR ").append(entry.getKey()).append(" LIKE '%").append(val).append("%'");
			}
		}
		return res.length() > 0 ? " AND (" + res.substring(4) + ")" : "";
	}

	private String getRangeCondition() {
		if (r == null) return "";
		List<String> val;
		String v1, v2, name;
		StringBuffer res = new StringBuffer();
		for (Entry<String, List<String>> entry : r.entrySet()) {
			name = entry.getKey();
			val = entry.getValue();
			v1 = val.get(0);
			v2 = val.get(1);
			if (StringUtils.isNotEmpty(v1) && StringUtils.isNotEmpty(v2)) {
				res.append(" AND ").append(name).append(" BETWEEN ").append(getRangeValue(v1)).append(" AND ").append(getRangeValue(v2));
			} else {
				if (StringUtils.isNotEmpty(v1)) {
					res.append(" AND ").append(name).append(" >= ").append(getRangeValue(v1));
				}
				if (StringUtils.isNotEmpty(v2)) {
					res.append(" AND ").append(name).append(" <= ").append(getRangeValue(v2));
				}
			}
		}
		return res.toString();
	}

	private String getSearchCondition() {
		if (s == null) return "";
		String val = null;
		StringBuffer res = new StringBuffer();
		for (Entry<String, String> entry : s.entrySet()) {
			val = entry.getValue();
			if (StringUtils.isNotEmpty(val)) {
				res.append(" AND ").append(entry.getKey()).append("='").append(val).append("'");
			}
		}
		return res.toString();
	}

	private String getLikeCondition() {
		if (l == null) return "";
		String value = null;
		StringBuffer res = new StringBuffer();
		for (Entry<String, String> entry : l.entrySet()) {
			value = entry.getValue();
			if (StringUtils.isNotEmpty(value)) {
				res.append(" AND ").append(entry.getKey()).append(" LIKE '%").append(value).append("%'");
			}
		}
		return res.toString();
	}

	@Override
	public String getCondition() {
		String where = getSearchCondition() + getRangeCondition() + getLikeCondition() + getMultiCondition() + getCustomCondition();
		return where.equals("") ? null : where.substring(5);
	}

	@Override
	public String getOrder() {
		if (o == null) return null;
		StringBuffer res = new StringBuffer();
		for (Entry<String, String> entry : o.entrySet()) {
			res.append(",").append(entry.getKey()).append(" ").append(entry.getValue());
		}
		return res.substring(1);
	}

	@Override
	public int getRows() {
		return getLength();
	}

	@Override
	public long getTotal() {
		return getRecordsTotal();
	}

	@Override
	public void setTotal(long total) {
		setRecordsTotal(total);
		setRecordsFiltered(total);
	}

	@Override
	public String getCondition(String alias) {
		System.out.print("未实现");
		return null;
	}

	@Override
	public String getConditionWhr(String s) {
		return null;
	}

	@Override
	public String getConditionHaving(String s) {
		return null;
	}

	@Override
	public String getSortType(String field) {
		System.out.print("未实现");
		return null;
	}

	@Override
	public void setCurrentData(List<T> data) {
		setData(data);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getCurrentData() {
		return (List<T>) getData();
	}

	@Override
	public String getValidcols() {
		System.out.print("未实现");
		return null;
	}

	@Override
	public Map<String, String> getAgg() {
		System.out.print("未实现");
		return null;
	}

	@Override
	public String toLazyJson(Class<?>... cls) {
		System.out.print("未实现");
		return null;
	}

	private String getRangeValue(String value) {
		Matcher matcher = numberPattern.matcher(value);
		return matcher.matches() ? value : "'" + value.replace("'", "") + "'";
	}

	@Override
	public String getSqlWhr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSqlWhr(String arg0) {
		// TODO Auto-generated method stub

	}

}
