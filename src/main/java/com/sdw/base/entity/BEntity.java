package com.sdw.base.entity;

import javax.persistence.MappedSuperclass;

import com.vogue.circle.database.entity.BaseEntity;

@MappedSuperclass
public class BEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 功能类型枚举 */
	public enum FunTypeEnum {
		/** 模块 */
		MODULE(0),
		/** 功能 */
		FUNCTION(1),
		/** 操作 */
		AUTH(2);
		private int tCode;
		private FunTypeEnum(int tCode) {
			this.tCode = tCode;
		}
		public String toString() {
			return String.valueOf(this.tCode);
		}
	};
}
