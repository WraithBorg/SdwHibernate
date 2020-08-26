package com.sdw.base.gson;

import java.io.IOException;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * 描述：gson 序列化适配器<br>
 */
public class GsonHibernateProxyAdapter<T> extends TypeAdapter<T> {

	public static TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			if (HibernateProxy.class.isAssignableFrom(type.getRawType())) {
				return new GsonHibernateProxyAdapter<T>(gson);
			} else {
				return null;
			}
		}
	};

	private final Gson context;

	private GsonHibernateProxyAdapter(Gson context) {
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
		LazyInitializer li = ((HibernateProxy) object)
				.getHibernateLazyInitializer();
		if (li.isUninitialized()) {
			out.nullValue();
		} else {
			Object o = li.getImplementation();
			this.context.toJson(o, o.getClass(), out);
		}
	}

}
