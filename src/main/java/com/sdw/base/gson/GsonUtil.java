package com.sdw.base.gson;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Stack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 描述：gson 工具类<br>
 */
public class GsonUtil {
	private static Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd HH:mm")
			.registerTypeAdapterFactory(GsonHibernateTypeAdapter.FACTORY)
			.registerTypeAdapterFactory(GsonHibernateProxyAdapter.FACTORY)
			.registerTypeAdapter(BigDecimal.class, new DecimalTypeAdapter())
			.create();

	public static String toJson(Object o) {
		GsonStack.initialize();
		return gson.toJson(o);
	}

	public static class GsonStack {
		private static ThreadLocal<Stack<Object>> localStack = new ThreadLocal<Stack<Object>>();

		public static Stack<Object> getLocalStack() {
			Stack<Object> ls = localStack.get();
			if (ls == null) {
				ls = new Stack<Object>();
				localStack.set(ls);
			}
			return ls;
		}

		private static void initialize() {
			Stack<Object> ls = localStack.get();
			if (ls == null)
				return;
			ls.empty();
		}
	}

	private static class DecimalTypeAdapter implements
			JsonSerializer<BigDecimal> {

		@Override
		public JsonElement serialize(BigDecimal bigDec, Type type,
				JsonSerializationContext context) {

			DecimalFormat format = new DecimalFormat("0.0000");
			String temp = format.format(bigDec);
			return new JsonPrimitive(temp);
		}

	}

}
