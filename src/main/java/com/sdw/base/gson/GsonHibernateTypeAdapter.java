package com.sdw.base.gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Stack;
import javax.persistence.Entity;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.vogue.circle.database.entity.BaseEntity;
/**
 * 序列化适配器
 */
public class GsonHibernateTypeAdapter<T> extends TypeAdapter<T> {

	public static TypeAdapterFactory FACTORY;

	static {
		FACTORY = new TypeAdapterFactory() {
			@Override
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				if (type.getRawType().isAnnotationPresent(Entity.class)) {
					return new GsonHibernateTypeAdapter<T>(gson);
				} else {
					return null;
				}
			}
		};
	}

	private final Gson context;

	private GsonHibernateTypeAdapter(Gson context) {
		this.context = context;
	}

	@Override
	public T read(JsonReader in) throws IOException {
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public void write(JsonWriter out, T object) throws IOException {
		if (object == null) {
			out.nullValue();
			return;
		}
		Stack<Object> stack = GsonUtil.GsonStack.getLocalStack();
		if (stack.contains(object)) {
			out.nullValue();
		} else {
			stack.push(object);
			out.beginObject();
			processFields(out, object, object.getClass());
			out.endObject();
			stack.pop();
		}
	}

	private void processFields(JsonWriter out, Object object, Class<? extends Object> clazz) throws IOException {
		if (clazz !=null && clazz != BaseEntity.class) {
			processFields(out, object, clazz.getSuperclass());
		}
		Stack<Object> stack = GsonUtil.GsonStack.getLocalStack();
		Field[] fields = clazz.getDeclaredFields();
		String name = null;
		Object value = null;
		LazyInitializer li = null;
		PersistentCollection pc = null;
		for (Field field : fields) {
			name = field.getName();
			if (name.equals("serialVersionUID") || field.getType() == SimpleDateFormat.class) continue;
			out.name(name);
			value = getValue(object, field);
			if (value instanceof PersistentCollection) {
				pc = (PersistentCollection)value;
				if (!pc.wasInitialized() || stack.contains(value)) {
					out.nullValue();
				} else {
					this.context.toJson(pc.getValue(), field.getType(), out);
				}
			} else if (value instanceof HibernateProxy) {
				li = ((HibernateProxy)value).getHibernateLazyInitializer();
				if (li.isUninitialized() || stack.contains(value)) {
					out.nullValue();
				} else {
					this.context.toJson(li.getImplementation(), field.getType(), out);
				}
			} else if (stack.contains(value)){
				out.nullValue();
			} else {
				this.context.toJson(value, field.getType(), out);
			}
		}
	}

	private Object getValue(Object target, Field field) {
		Object value = null;
		boolean accessible = field.isAccessible();
		try {
			if (!accessible) field.setAccessible(true);
			value = field.get(target);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		if (field.isAccessible() != accessible) field.setAccessible(accessible);
		return value;
	}

}
