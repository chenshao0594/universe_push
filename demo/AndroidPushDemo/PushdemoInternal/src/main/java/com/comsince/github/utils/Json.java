package com.comsince.github.utils;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 * @author tanyaowu
 * 2017年4月16日 上午11:36:53
 */
public class Json {


	public static <T> T toBean(String jsonString, Class<T> tt) {
		if (TextUtils.isEmpty(jsonString)) {
			return null;
		}
		T t = JSON.parseObject(jsonString, tt);
		return t;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toFormatedJson(Object bean) {
		return JSON.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toJson(Object bean) {
		return JSON.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 可以返回null的key值
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toJsonAboutNull(Object bean) {
		return JSON.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 
	 * @param bean
	 * @param serializeFilter
	 * @return
	 * @author tanyaowu
	 */
	public static String toJson(Object bean, SerializeFilter serializeFilter) {
		if (serializeFilter != null) {
			return JSON.toJSONString(bean,serializeFilter, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			return JSON.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect);
		}
	}
}
