package com.sdw.base.sync;

/**
* <p>Description:
* 	join 方法作用：
* 	将当前子线程嵌入到主线程中，只有当子线程结束，主线程才能继续执行。
* 	不写join 有可能先执行完主线程，再去执行子线程，导致
* </p>
* <p>注意:
* 	给i加锁 是不能保证同步的
* 	Integer 是不可变对象，如果是1就永远是1
* 	如果需要使它为2，则创建一个新的Integer对象，将他的引用赋给
* 	所以多个线程间并不能看到同一个Integer对象，两次线程锁可能加在了不同的对象实例上
* </p>
*
* @date 2017-1-24上午10:19:30
*/
public class BadLockOnInteger implements Runnable{
	public static Integer i = 0;
	static BadLockOnInteger instance = new BadLockOnInteger();
	@Override
	public void run() {
		for (int j = 0; j < 1000000; j++) {
			synchronized(i){	// 	synchronized(instance){ -- syso(20000000)
				i++;
			};
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















