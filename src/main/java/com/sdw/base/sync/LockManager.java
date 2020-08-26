package com.sdw.base.sync;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>
 * Description: 多对象线程锁
 * </p>
 *
 * @date 2017-1-17下午4:38:31
 */
public class LockManager {
	private Map<String, Lock> locks = Collections.synchronizedMap(new WeakHashMap<String, Lock>());

	public static class Lock {
		private String id;
		public Lock(String id) {
			this.id = id;
		}
		public String getId() {
			return id;
		}
		private boolean locked = false;

		public synchronized void addLock() {
			locked = true;
		}
		public synchronized void release() {
			locked = false;
		}
		public boolean selectLockState() {
			return locked;
		}
	}

	public void lock(String key) {
		Lock lock = null;
		synchronized (locks) {
			lock = locks.get(key);
			if (lock == null) {
				lock = new Lock(key + ":" + (new Date()).getTime());
				locks.put(key, lock);
			}
		}
		lock.addLock();

	}

	public void unlock(String key) {
		Lock lock = null;
		synchronized (locks) {
			lock = locks.get(key);
			if (lock == null) {
				return;
			}
			locks.remove(key);
		}
		lock.release();
	}

	public boolean isLocking(String key) {
		if (!locks.containsKey(key)) {
			return false;
		} else {
			Lock lock = null;
			lock = locks.get(key);
			return lock.selectLockState();
		}

	}
}
