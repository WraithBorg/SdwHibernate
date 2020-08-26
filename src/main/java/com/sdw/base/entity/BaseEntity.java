package com.sdw.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import com.google.gson.annotations.Expose;

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose
	private String id;

	@Expose
	private Date createDate = new Date();
	@Expose
	private Date modifyDate = new Date();

	private boolean selected;

	public static enum DelFlagEnum {
		normal(0),

		temp(1),

		done(2);

		private int tCode;

		private DelFlagEnum(int tCode) {
			this.tCode = tCode;
		}

		public String toString() {
			return String.valueOf(this.tCode);
		}
	}

	@Id
	@Column(name = "id", columnDefinition = "VARCHAR(32) NOT NULL COMMENT'主键'")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(updatable = true, columnDefinition = "DATETIME NULL COMMENT'创建日期   需记录日期时间'")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(updatable = true, columnDefinition = "DATETIME NULL COMMENT'修改日期   需记录日期时间'")
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int hashCode() {
		return this.id == null ? System.identityHashCode(this) : this.id
				.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (this.id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!this.id.equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Transient
	public boolean getSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Transient
	public List<Field> getEntFields() {
		List<Field> listfds = new ArrayList<Field>();
		if (getClass().getSuperclass() != null) {
			getSuperclassFields(getClass().getSuperclass(), listfds);
		}

		Field[] fds = getClass().getDeclaredFields();
		if (fds != null) {
			for (Field fd : fds) {
				if ((fd.getModifiers() & 0x8) != 8) {
					listfds.add(fd);
				}
			}
		}
		return listfds;
	}

	private void getSuperclassFields(Class<?> type, List<Field> returnList) {
		Field[] fds = type.getDeclaredFields();
		for (Field fd : fds) {
			if ((fd.getModifiers() & 0x8) != 8) {
				returnList.add(fd);
			}
		}
		if ((type != BaseEntity.class) && (type.getSuperclass() != null)) {
			getSuperclassFields(type.getSuperclass(), returnList);
		}
	}
}
