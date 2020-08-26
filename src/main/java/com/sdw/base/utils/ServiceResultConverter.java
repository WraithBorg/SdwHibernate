package com.sdw.base.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
/**
 * 描述：消息转换类<br>
 */
public class ServiceResultConverter implements HttpMessageConverter<ServiceResult<?>>{

	private List<MediaType> supportedMediaTypes = Collections.singletonList(new MediaType("application", "json"));

	private String charset = "utf-8";
	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return ServiceResult.class.isAssignableFrom(clazz);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return supportedMediaTypes;
	}

	@Override
	public ServiceResult<?> read(Class<? extends ServiceResult<?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return null;
	}

	@Override
	public void write(ServiceResult<?> t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		outputMessage.getHeaders().add("Content-Type", "application/json;charset=" + charset);
		String s = t.toJson();
		outputMessage.getBody().write(s.getBytes(charset));
	}

}
