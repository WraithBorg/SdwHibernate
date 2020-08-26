package com.sdw.base.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
* <p>Description: 锁的中断响应</p>
*
* @date 2017-1-24下午2:26:47
*/
public class IntLock implements Runnable {
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	int lock;
	/**
	 * 控制加锁顺序，方便构造死锁
	 * @param lock
	 */
	public IntLock(int lock){
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			if(lock == 1){
				lock1.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				lock2.lockInterruptibly();
			}else{
				lock2.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				lock1.lockInterruptibly();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(lock1.isHeldByCurrentThread()){//如果当前线程已经拥有该锁,此方法将立即返回。
				lock1.unlock();
			}
			if(lock2.isHeldByCurrentThread()){//如果当前线程已经拥有该锁,此方法将立即返回。
				lock2.unlock();
			}
			System.out.println(Thread.currentThread().getId()+" :线程退出");
		}

	}
	public static void main(String[] args) throws InterruptedException {
		IntLock r1 = new IntLock(1);
		IntLock r2 = new IntLock(2);

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		t1.start();t2.start();
		Thread.sleep(1000);
		//中断其中一个线程
		t2.interrupt();
	}
}
