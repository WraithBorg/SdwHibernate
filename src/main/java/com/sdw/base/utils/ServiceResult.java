package com.sdw.base.utils;
import com.sdw.base.gson.GsonUtil;

public class ServiceResult<T> {
	private T data;
	private boolean result;
	private String message = UtilTools.getMessageSource("global.success");

	public ServiceResult(boolean result) {
		this.result = result;
	}

	public ServiceResult(boolean result, T data) {
		this.data = data;
		this.result = result;
	}

	public ServiceResult(boolean result, String message) {
		this.result = result;
		setMessage(message);
	}

	public ServiceResult(boolean result, String message, String[] args) {
		this.result = result;
		setMessage(message, args);
	}

	public ServiceResult(boolean result, T data, String message) {
		this.data = data;
		this.result = result;
		setMessage(message);
	}

	public ServiceResult(boolean result, T data, String message, String[] args) {
		this.data = data;
		this.result = result;
		setMessage(message, args);
	}

	public void setMessage(String message) {
		this.message = UtilTools.getMessageSource(message);
	}

	public void setMessage(String message, String[] args) {
		this.message = UtilTools.getMessageSource(message, args);
	}

	public T getData() {
		return data;
	}

	public boolean getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public String toJson() {
		return GsonUtil.toJson(this);
	}

	public <E> ServiceResult<E> transform() {
		return new ServiceResult<E>(result, message);
	}

}
