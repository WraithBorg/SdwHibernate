package com.sdw.base.thread.local;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * Description: 讨论 : 多线程先产生一个随机数的问题 mode = 0: 多线程共享一个 Random() mode = 1:
 * 多个线程各分配一个 Random()
 * </p>
 *
 *
 * @date 2017-1-25上午11:49:29
 */
public class RandomTest {
	public static final int GET_COUNT = 10000000;// 每个线程要产生的随机数数量
	public static final int THREAD_COUNT = 4; // 参与工作的线程数量
	static ExecutorService exe = Executors.newFixedThreadPool(THREAD_COUNT);// 线程池
	public static Random rnd = new Random(123);// 被共享的Random实例

	public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {
		protected Random initialValue() {
			return new Random(123);
		};
	};

	public static class RndTask implements Callable<Long> {
		private int mode = 0;

		public RndTask(int mode) {
			this.mode = mode;
		}

		public Random getRandom() {
			if (mode == 0) {
				return rnd;
			} else if (mode == 1) {
				return tRnd.get();
			} else {
				return null;
			}
		}

		@Override
		public Long call() throws Exception {
			Long b = System.currentTimeMillis();
			for (int i = 0; i < GET_COUNT; i++) {
				getRandom().nextInt();
			}
			Long e = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName() + "spend " + (e-b) + " ms");
			return e-b;
		}
		//main
	}
}
