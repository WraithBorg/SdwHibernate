package com.sdw.model.controller;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdw.base.controller.BController;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.entity.Mo_User;
import com.sdw.model.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController extends BController {

	@Autowired
	private IUserService userService;
	String str ="";
	private ThreadLocal<String> lo = new ThreadLocal<String>();

	private ConcurrentHashMap<String,Boolean> chm = new ConcurrentHashMap<>();


	/*
	 * 注册
	 */
	@RequestMapping(value = "/register")
	public ModelAndView loginCheck(HttpServletRequest request, Mo_User user) {
		try {
			System.out.println("当前时间：A---****" + System.currentTimeMillis());
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			userService.addUser(user);
			System.out.println("当前时间：B---****" + System.currentTimeMillis());
		}
		System.out.println("当前时间：C---****" + System.currentTimeMillis());
		request.getSession().setAttribute("user", user);
		return new ModelAndView("module/userData/main");
	}

	/*
	 * 登陆 request.getSession().setAttribute 和 request.setAttribute
	 */
	@RequestMapping(value = "/login")
	public ModelAndView registerUsers(HttpServletRequest request, Mo_User user) {
		HttpSession session = request.getSession();
		session.setAttribute("basePath", request.getContextPath());
		System.out.println("TODO : " + request.getContextPath());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}

	/*
	 * 分页查询用户
	 */
	@ResponseBody
	@RequestMapping(method = {RequestMethod.POST}, params = {"m=pageList"})
	public ServiceResult<Page<Mo_User>> pageList(Page<Mo_User> page, String zhao, int num) throws InterruptedException {
		ServiceResult<Page<Mo_User>> res = userService.pageList(page);
		return res;
	}
	/*
	 * 编辑
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView editUser(HttpServletRequest request, Mo_User user) {
		try {
			System.out.println("当前时间：A---****" + System.currentTimeMillis());
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			userService.editUser(user);
			System.out.println("当前时间：B---****" + System.currentTimeMillis());
		}
		System.out.println("当前时间：C---****" + System.currentTimeMillis());
		return new ModelAndView("module/userData/main");
	}

	/*
	 * 查询用户
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.GET }, params = { "m=selectById" })
	public ServiceResult<?> selectById(String id) {
		ServiceResult<?> res = new ServiceResult<>(true,
				userService.getById(id));
		return res;
	}
}
