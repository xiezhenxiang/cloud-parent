package indi.shine.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Jackson util
 * 1、obj need private and set/get；
 * 2、do not support inner class；
 * @author xiezhenxiang 2019-12-25 18:02:56
 */
public class JacksonUtil {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	public static ObjectMapper getInstance() {
		return OBJECT_MAPPER;
	}
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// not serial null value
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * bean、array、List、Map --> json
	 */
	public static String toJsonString(Object obj) {
		try {
			return getInstance().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * string --> bean、Map、List(array)
	 */
	public static <T> T parseObject(String jsonStr, Class<T> clazz) {
		try {
			return getInstance().readValue(jsonStr, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * string --> List<Bean>...
	 */
	public static <T> T parseObject(String jsonStr, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			JavaType javaType = getInstance().getTypeFactory().constructParametricType(parametrized, parameterClasses);
			return getInstance().readValue(jsonStr, javaType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseObject(String jsonStr, TypeReference<T> type) {
		try {
			return getInstance().readValue(jsonStr, new TypeReference<T>() {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * obj --> bean、Map、List(array)
	 * @return obj
	 */
	public static <T> T convertObject(Object obj, Class<T> clazz) {
		return parseObject(toJsonString(obj), clazz);
	}
}