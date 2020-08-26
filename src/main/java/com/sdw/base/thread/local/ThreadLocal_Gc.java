package com.sdw.base.thread.local;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <p>Description:
* 		跟踪THreadLocal对象以及内部SimpleDateFormat对象的垃圾回收
* </p>
*
* @date 2017-1-25上午11:29:06
*/
public class ThreadLocal_Gc {
	static volatile ThreadLocal<SimpleDateFormat> t1 =new ThreadLocal<SimpleDateFormat>(){
		//看到对象被回收时的踪迹
		protected void finalize(){
			System.out.println(this.toString() + " is GC ");
		}
	};
	/*
	 * 	CountDownLatch
	 *  构造方法参数指定了计数的次数
	 *	countDown方法，当前线程调用此方法，则计数减一
	 *	await方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
	 */
	static volatile CountDownLatch cd = new CountDownLatch(10000);

	public static class ParseDate implements Runnable{
		int i = 0;
		public ParseDate(int i){
			this.i = i;
		}
		@Override
		public void run() {
			try {
				if(t1.get() == null ){
					t1.set(new SimpleDateFormat("yy-MM-dd HH:mm:ss"){
						//看到对象被回收时的踪迹： 圾回收器要回收对象的时候，首先要调用这个类的finalize方法
						protected void finalize(){
							System.out.println(this.toString() + " is GC ");
						}
					});
					System.out.println(Thread.currentThread().getId() + ": create SimpleDateFormat");
					Date t = t1.get().parse("2015-03-09 19:29:"+i%60);
					System.out.println(i+":"+t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				cd.countDown();
			}

		}
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		System.out.println("mission complete!!");
		t1 = null;
		System.gc();
		System.out.println("First GC complete!!");
		//在设置ThreadLocal时，会清除ThreadLocalMap中的无效对象
		t1 = new ThreadLocal<SimpleDateFormat>();
		cd = new CountDownLatch(10000);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		Thread.sleep(1000);
		System.gc();
		System.out.println("Second GC complete!!");
	}
}











