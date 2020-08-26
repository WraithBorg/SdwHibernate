package com.sdw.base.sync;

import java.util.concurrent.locks.ReentrantLock;

public class GoodLockOnInteger  implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static  Integer i = 0;
	static GoodLockOnInteger instance = new GoodLockOnInteger();
	@Override
	public void run() {
		for (int j = 0; j < 1000000; j++) {
			try {
				lock.lock(); // == synchronized(this)
				i++;
			} catch (Exception e) {
			}finally{
				lock.unlock();
			}
		}

	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
