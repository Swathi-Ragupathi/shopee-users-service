package com.learn.microservices.shopeeusersservice.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;

import com.learn.microservices.shopeeusersservice.annotation.IgnoreCopy;

public class CustomBeanUtils extends BeanUtils {

	public static void copyProperties(Object source, Object target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Field[] fields = source.getClass().getDeclaredFields();
		List<String> ignoreFieldList = new ArrayList<String>();

		for (Field field : fields) {
			if (field.isAnnotationPresent(IgnoreCopy.class)) {
				ignoreFieldList.add(field.getName());
			}

		}
		copyProperties(source, target, ignoreFieldList.toArray(new String[0]));

	}

}
