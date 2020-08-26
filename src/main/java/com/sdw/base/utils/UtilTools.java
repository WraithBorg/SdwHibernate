package com.sdw.base.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vogue.circle.database.entity.BaseEntity;
import com.vogue.circle.util.SprContext;
import com.sdw.base.entity.BEntity;

public class UtilTools {

	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
	final static Logger logger = LoggerFactory.getLogger(UtilTools.class);
	public final static String START_DATE = "startDate";
	public final static String END_DATE = "endDate";
	public final static String START_NO = "beginNo";
	public final static String END_NO = "endNo";

	public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat DATETIME_FORMATTER_START = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	public static SimpleDateFormat DATETIME_FORMATTER_END = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	private static ThreadLocal<DateFormat> localDateTimeFormat = new ThreadLocal<DateFormat>();

	public static DateFormat getDateTimeFormat() {

		DateFormat df = localDateTimeFormat.get();
		if (df == null) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			localDateTimeFormat.set(df);
		}
		return df;
	}

	/**
	 * 返回当前日期与时间的字符串形式<br>
	 */
	public static String getStringDateTime() throws ParseException {

		return getStringDateTime(new Date());
	}

	/**
	 * 返回指定日期与时间的字符串形式<br>
	 *
	 * @param date
	 *            欲格式化的日期<br>
	 */
	public static String getStringDateTime(Date date) throws ParseException {

		return getDateTimeFormat().format(date);
	}

	/**
	 * 解析指定日期与时间字符串并返回<br>
	 *
	 * @param date
	 *            日期与时间的字符串<br>
	 * @return 日期对象<br>
	 */
	public static Date parse(String date) throws ParseException {

		return getDateTimeFormat().parse(date);
	}

	/**
	 * 以http访问某地址并返回内容
	 *
	 * @param url
	 * @param data
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static byte[] accessUrl(String url, Map<String, String> data, String charset) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		if (data != null) {
			Set<Entry<String, String>> set = data.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			while (it.hasNext()) {
				Entry<String, String> ent = it.next();
				formparams.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity httpent = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toByteArray(httpent);
			} else {
				logger.debug(" http error response is : " + response.getStatusLine().getStatusCode());
			}
		}
		return null;
	}

	public static String accessUrlrets(String url, Map<String, String> data, String charset) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		if (data != null) {
			Set<Entry<String, String>> set = data.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			while (it.hasNext()) {
				Entry<String, String> ent = it.next();
				formparams.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity httpent = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpent, charset);
			} else {
				logger.debug(" http error response is : " + response.getStatusLine().getStatusCode());
			}
		}
		return null;
	}

	/**
	 * 将json字符串转为map
	 *
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map jsonToMap(String jsonString) throws JSONException, ParseException {

		JSONObject jsonObject;
		Map result = new HashMap();
		jsonObject = new JSONObject(jsonString);
		Iterator iterator = jsonObject.keys();
		String key = null;
		Object value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.get(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 将json转为list
	 *
	 * @param jsonstr
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> jsonToList(String jsonstr) throws JSONException {

		JSONArray array;
		List<Map> list = new ArrayList<Map>();
		try {
			array = new JSONArray(jsonstr);

			int len = array.length();

			if (len > 0) {
				for (int i = 0; i < len; i++) {
					JSONObject jsobj = array.getJSONObject(i);
					String sobj = jsobj.toString();
					Map map = jsonToMap(sobj);
					list.add(map);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将map转为json串
	 *
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String map2Json(Map map) {

		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	/**
	 * 将list转为json数组字符串
	 *
	 * @param list
	 * @return
	 */
	public static String list2Json(List<?> list) {

		if (list != null) {
			String jsonlist = "[", fg = "";
			for (Object obj : list) {
				String json = obj2Json((BEntity) obj);
				jsonlist += fg + json;
				fg = ",";
			}
			jsonlist += "]";
			return jsonlist;
		}
		return null;
	}

	/**
	 * 将bean转为json串
	 *
	 * @param obj
	 * @return
	 */
	public static String obj2Json(BEntity obj) {

		JSONObject jsonObject = new JSONObject(obj2Map(obj));
		return jsonObject.toString();
	}

	/**
	 *
	 * @param o
	 * @return
	 */
	public static String obj2Json(Object o) {

		return gson.toJson(o);
	}

	/**
	 * 将bean转为map并且将键的下划线取消
	 *
	 * @param javaBean
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map obj2MapFmt(BaseEntity obj) {

		Map result = new HashMap();
		List<Field> fields = obj.getEntFields();
		if (fields != null) {
			for (Field fd : fields) {
				String fdnm = fd.getName();
				Class type = fd.getType();
				try {
					String get = "get";
					if (type == Boolean.class || type.getName().equals("boolean")) {
						get = "is";
					}
					Method method = obj.getClass().getMethod(get + upp1st(fdnm));
					Object value = method.invoke(obj, (Object[]) null);
					result.put(fmtField(fdnm), value);
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return result;
	}

	/**
	 * 将bean转为map
	 *
	 * @param javaBean
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map obj2Map(BEntity obj) {

		Map result = new HashMap();
		List<Field> fields = obj.getEntFields();
		if (fields != null) {
			for (Field fd : fields) {
				String fdnm = fd.getName();
				try {
					Class<?> type = fd.getType();
					String get = "get";
					if (type == Boolean.class || type.getName().equals("boolean")) {
						get = "is";
					}
					Method method = obj.getClass().getMethod(get + upp1st(fdnm));
					Object value = method.invoke(obj, (Object[]) null);
					result.put(fdnm, value);
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return result;
	}

	private static String fmtField(String field) {

		String ss[] = field.split("_");
		if (ss.length == 1) {
			return ss[0];
		} else {
			return ss[0] + upp1st(ss[1]);
		}
	}

	/**
	 * map转为bean
	 *
	 * @param <T>
	 *
	 * @param map
	 * @param cls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static Object map2Obj(Map map, Class cls) throws InstantiationException, IllegalAccessException {

		Object obj = cls.newInstance();
		List<Field> fields = ((BEntity) obj).getEntFields();
		if (fields != null) {
			for (Field fd : fields) {
				String fdnm = fd.getName();
				Object value = map.get(fdnm);
				try {
					if (value != null) {
						Method method = obj.getClass().getMethod("set" + upp1st(fdnm), value.toString().getClass());
						method.invoke(obj, value.toString());
					}
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return obj;
	}

	/**
	 * 将字符串首字母变大写
	 *
	 * @param str
	 * @return
	 */
	public static String upp1st(String str) {

		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/**
	 * 将字符串首字母变小写
	 *
	 * @param str
	 * @return
	 */
	public static String low1st(String str) {

		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}

	/**
	 * 对字符串做MD5加密
	 *
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = str.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str1[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str1[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str1[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String UUID() {

		String s = java.util.UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 格式日期
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format) {

		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format); // 按输入格式显示当前日期
		str = sdf.format(date.getTime());
		return str;
	}

	/**
	 * 将对象转为字节码
	 *
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] obj2bytes(Object obj) throws IOException {

		ByteArrayOutputStream bytesout = new ByteArrayOutputStream();
		//
		ObjectOutputStream objos = new ObjectOutputStream(bytesout);
		objos.writeObject(obj);
		objos.flush();
		return bytesout.toByteArray();
	}

	/**
	 * 将字节流转为对象
	 *
	 * @param bs
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object bytes2obj(byte[] bs) throws IOException, ClassNotFoundException {

		ByteArrayInputStream bytesout = new ByteArrayInputStream(bs);
		ObjectInputStream objis = new ObjectInputStream(bytesout);
		return objis.readObject();
	}

	/**
	 * 生成一个数值型的随机数
	 *
	 * @return
	 */
	public static Long radomNum() {

		Random rand = new Random();
		return rand.nextLong();
	}

	/**
	 * 将以逗号分隔的数值字符串连转为数值数组
	 *
	 * @param ss
	 * @return
	 */
	public static int[] strs2Ints(String ss) {

		String[] ses = ss.split(",");
		return strs2Ints(ses);
	}

	/**
	 * 将数值字符串数组转为数值数组
	 *
	 * @param ss
	 * @return
	 */
	public static int[] strs2Ints(String[] ss) {

		int[] ii = new int[ss.length];
		for (int i = 0; i < ii.length; i++) {
			ii[i] = Integer.parseInt(ss[i]);
		}
		return ii;
	}

	@SuppressWarnings("unused")
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '+', '/', };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
			5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
			27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
			-1, -1, -1 };

	/**
	 * 解密
	 *
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str) {

		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {
			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((b1 << 2) | ((b2 & 0x30) >>> 4));

			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2));

			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write(((b3 & 0x03) << 6) | b4);
		}
		return buf.toByteArray();
	}

	/**
	 * 得到 全拼
	 *
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {

		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 得到中文首字母
	 *
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		convert = convert.toUpperCase();
		return convert;
	}

	/*
	 * /Method1.5.4/*************************************************************
	 * *********************
	 */
	/**
	 * 描述：获取某个资源的中文名称 <br>
	 *************************************************************************************************/
	public static String getMessageSource(String key) {

		ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) SprContext.getInstance().getBean(
				"messageSource");
		// 当国际化失败时显示原始字符
		try {
			return messageSource.getMessage(key, null, Locale.SIMPLIFIED_CHINESE);
		} catch (NoSuchMessageException e) {
			return key;
		}
	}

	/*
	 * /Method1.5.4/*************************************************************
	 * *********************
	 */
	/**
	 * 描述：返回资源文件中的文字，并且替换中间的{n}样式的位置字符 <br>
	 * 参数：arrStr 要天替换的字符串数组 0索引替换 {0} <br>
	 * 返回值：处理过的字符串 <br>
	 *************************************************************************************************/
	public static String getMessageSource(String key, String[] arrStr) {

		ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) SprContext.getInstance().getBean(
				"messageSource");
		// 当国际化失败时显示原始字符
		try {
			return messageSource.getMessage(key, arrStr, Locale.SIMPLIFIED_CHINESE);
		} catch (NoSuchMessageException e) {
			return key;
		}
	}

	/*
	 * /Method1.5.4/*************************************************************
	 * *********************
	 */
	/**
	 * 描述：字段长度校验 <br>
	 * 参数：实体对象object，参数映射集合<"参数名", "异常信息"><br>
	 * 返回值：ServiceResult对象 <br>
	 *************************************************************************************************/
	@Deprecated
	public static ServiceResult<Object> fieldsLengthValidation(Object object, Map<String, String> paramMap) {

		Class<?> clsEntity = null;
		ServiceResult<Object> result = new ServiceResult<>(true);
		try {
			clsEntity = object.getClass();
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {

				Field field = clsEntity.getDeclaredField(entry.getKey().toLowerCase());
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clsEntity);
				Method getMethod = pd.getReadMethod();// 获得get方法
				// AnnotatedElement接口中的方法isAnnotationPresent()，判断传入的注解类型是否存在
				if (getMethod.isAnnotationPresent(Column.class)) {
					getMethod.invoke(object, new Object[] {});
					// AnnotatedElement接口中的方法getAnnotation(),获取传入注解类型的注解
					Column myAnnotation = getMethod.getAnnotation(Column.class);
					// 拿到对象的值
					Object o = getMethod.invoke(object);// 执行get方法返回一个Object
					// 拿到注解中的属性 myAnnotation.length()
					if (String.valueOf(o).length() > myAnnotation.length()) {
						result = new ServiceResult<>(false, entry.getValue(),
								new String[] { String.valueOf(myAnnotation.length()) });
						break;
					}
				}
			}

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchFieldException | IntrospectionException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * /Method1.5.4/*************************************************************
	 * *********************
	 */
	/**
	 * 描述：字段判空校验 <br>
	 * 参数：实体对象object，参数映射集合<"参数名", "异常信息"><br>
	 * 返回值：ServiceResult对象 <br>
	 *************************************************************************************************/
	@Deprecated
	public static ServiceResult<Object> fieldEmptyValidation(Object object, Map<String, String> paramMap) {

		Class<?> clsEntity = null;
		ServiceResult<Object> result = new ServiceResult<>(true);
		try {
			clsEntity = object.getClass();
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {

				Field field = clsEntity.getDeclaredField(entry.getKey().toLowerCase());
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clsEntity);
				Method getMethod = pd.getReadMethod();// 获得get方法
				// 拿到对象的值
				Object o = getMethod.invoke(object);// 执行get方法返回一个Object
				// 拿到注解中的属性 myAnnotation.length()

				if (StringUtils.isEmpty((String.valueOf(o)))) {
					result = new ServiceResult<>(false, entry.getValue());
					break;
				}
			}

		} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException | IntrospectionException
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}




    /**
     * 小数位数
     */
    public final static int scale = 8;
    /**
     * 小数4位数
     */
    public final static int scale4 = 4;


    //===========================金额中文转换begin===================================================================================
    /** 大写数字 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/** 整数部分的单位 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	/** 小数部分的单位 */
	private static final String[] DUNIT = { "角", "分", "厘", "毫" };

	/**
	 * 将金额数字转换为大写中文
	 */
	public static String moneyToChinese(String str) {
		str = str.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			System.out.println(str + ":超出处理能力");
			return str;
		}

		int[] integers = toArray(integerStr);// 整数部分数字
		boolean isMust5 = isMust5(integerStr);// 设置万单位
		int[] decimals = toArray(decimalStr);// 小数部分数字
		return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
	}

	/**
	 * 整数部分和小数部分转换为数组，从高位至低位
	 */
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}

	/**
	 * 得到中文金额的整数部分。
	 */
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
		}
		return chineseInteger.toString();
	}

	/**
	 * 得到中文金额的小数部分。
	 */
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// 舍去4位小数之后的
			if (i == 4)
				break;
			chineseDecimal.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}

	/**
	 * 判断第5位数字的单位"万"是否应加。
	 */
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}
	//===========================金额中文转换end===================================================================================

	/**
	 * 判断字符串是否是空字符串
	 */
	public static boolean isStringEmpty(String tmp) {
		if(tmp == null || "".equals(tmp) || "null".equals(tmp)) return true;
		return false;
	}

	/**
	 * set转换为list
	 */
	@SuppressWarnings("rawtypes")
	public static List set2list(Set set) {
		if(set == null || set.size() == 0) return null;
		List<Object> list = new ArrayList<Object>();
		for (Object o : set) {
			list.add(o);
		}
		return list;
	}

	/**
	 * list转换为set
	 */
	@SuppressWarnings("rawtypes")
	public static Set list2set(List list) {
		if(list == null || list.size() == 0) return null;
		Set<Object> set = new HashSet<Object>();
		for (Object o : list) {
			set.add(o);
		}
		return set;
	}

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 时间1 默认为较小时间
     * @param bdate  时间2 默认为较大时间
     * @return 相差天数  值计算天数差异不计算小时
     * @throws ParseException
     */
    public static int getDaysBetween (String smdate, String bdate) throws Exception{
    	Calendar d1 = Calendar.getInstance();
    	d1.clear();
    	d1.setTime(DATE_FORMATTER.parse(smdate));
    	Calendar d2 = Calendar.getInstance();
    	d2.clear();
    	d2.setTime(DATE_FORMATTER.parse(bdate));

        if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }


   /**
    * 判断当前日期是星期几<br>
    * <br>
    * @param pTime 要判断的时间<br>
    * @return dayForWeek 判断结果<br>
    * @Exception 发生异常<br>
    */
	public static int dayForWeek(String pTime) throws Exception {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(DATE_FORMATTER.parse(pTime));
		int dayForWeek = 0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	/**
	 * 判断是否为月末时间
	 * @param pTime 要判断的时间
	 * @return endMonth 判断结果
	 * @throws Exception 发生异常
	 */
	public static boolean isEndMonth(String pTime) throws Exception {
		boolean endMonth = false;
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(DATE_FORMATTER.parse(pTime));
		if(c.get(Calendar.DATE)==c.getActualMaximum(Calendar.DAY_OF_MONTH)){
			endMonth = true;
		}
		return endMonth;
	}

	/**
     * 处理post请求.
     * @param url  请求路径
     * @param params  参数
     * @return  json
     */
	public static String post(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters != null) {
				if (parameters.size() == 1) {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
					}
					params = sb.toString();
				} else {
					for (String name : parameters.keySet()) {
						sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
					}
					String temp_params = sb.toString();
					params = temp_params.substring(0, temp_params.length() - 1);
				}
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取配置文件中某个字段的值
	 * @param filename 配置文件名
	 * @param key 需要获取的字段名
	 * @return
	 */
	public String getProperties(String filename, String key) {
		Properties props = new Properties();
		InputStream is = null;
		is = this.getClass().getClassLoader().getResourceAsStream(filename);
		try {
			props.load(is);
			return props.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前日期的下一天
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextDate() throws ParseException{
		Date now= new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse(DATETIME_FORMATTER_START.format(now)));
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
		return calendar.getTime();
	}
}
