package com.sdw.base.thread.local;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <p>Description: 此类证明 SimpleDateFormat 是非线程安全的
* 报错 ： java.lang.NumberFormatException: For input string: ""
* </p>
*
* @date 2017-1-25上午10:48:17
*/
public class UnSafeSimpleDate {
	public static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>();
	public static class ParseDate implements Runnable{
		int i=0;
		public ParseDate(int i){
			this.i = i;
		}
		@Override
		public void run() {
			try {
				if(t1.get() == null){
					t1.set(new SimpleDateFormat("yy-MM-dd HH:mm:ss"));
				}
				Date t = t1.get().parse("2015-03-09 19:29:"+i%60);
				System.out.println(i+":"+t);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			es.execute(new ParseDate(i));
		}
	}
}
/*
 * ****************************************非线程安全代码****************************************
 *	public static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	public static class ParseDate implements Runnable{
		int i=0;
		public ParseDate(int i){
			this.i = i;
		}
		@Override
		public void run() {
			try {
				Date t = sdf.parse("2015-03-09 19:29:"+i%60);
				System.out.println(i+":"+t);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			es.execute(new ParseDate(i));
		}
	}
 */
/*	***************************************
 * *********************************
 *********************************************************** 线程安全的代码**************************************
 	public static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>();
	public static class ParseDate implements Runnable{
		int i=0;
		public ParseDate(int i){
			this.i = i;
		}
		@Override
		public void run() {
			try {
				if(t1.get() == null){
					t1.set(new SimpleDateFormat("yy-MM-dd HH:mm:ss"));
				}
				Date t = t1.get().parse("2015-03-09 19:29:"+i%60);
				System.out.println(i+":"+t);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			es.execute(new ParseDate(i));
		}
	}

 */
