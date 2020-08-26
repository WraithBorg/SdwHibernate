package com.sdw.base.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vogue.circle.web.BaseController;


public class BController extends BaseController{
//	/**
//	 * 描述：向客户端输出 ajax<br>
//	 */
//	protected void responseAjax(HttpServletResponse response, ServiceResult<?> result) {
//		response.setContentType("application/json;charset=utf-8");
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			out.write(result.toJson());
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 描述：异常控制台输出<br>
	 */
	@Override
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(Exception e, HttpServletResponse response, HttpServletRequest request) throws IOException{
		e.printStackTrace();
	}
}
