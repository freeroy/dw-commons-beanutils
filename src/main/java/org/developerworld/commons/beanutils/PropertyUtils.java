package org.developerworld.commons.beanutils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

/**
 * Property 工具类
 * 
 * @author Roy Huang
 * @version 20130825
 * 
 */
public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {

	/**
	 * 复制对象字段属性
	 * 
	 * @param dest
	 * @param orig
	 * @param fieldName
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void copyProperty(Object dest, Object orig, String fieldName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		setProperty(dest, fieldName, getProperty(orig, fieldName));
	}

	/**
	 * 把一个对象指定字段的值复制到令一个对象
	 * 
	 * @param dest
	 * @param orig
	 * @param fieldNames
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void copyPropertiesIncludeFields(Object dest, Object orig,
			String[] fieldNames) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (dest == null || orig == null)
			return;
		if (fieldNames != null) {
			for (String fieldName : fieldNames)
				copyProperty(dest, orig, fieldName);
		}
	}

	/**
	 * 复制排除指定字段的字段
	 * 
	 * @param dest
	 * @param orig
	 * @param fieldNames
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void copyPropertiesExcludeFields(Object dest, Object orig,
			String[] fieldNames) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (dest == null || orig == null)
			return;
		PropertyDescriptor[] pros = PropertyUtils.getPropertyDescriptors(orig);
		for (int i = 0; i < pros.length; i++) {
			PropertyDescriptor pro = pros[i];
			String name = pro.getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			boolean exclude = false;
			if (fieldNames != null) {
				for (String fieldName : fieldNames) {
					if (fieldName.equals(name)) {
						exclude = true;
						break;
					}
				}
			}
			if (exclude)
				continue;
			else
				copyProperty(dest, orig, name);
		}
	}
	
	/**
	 * 转化一批bean，并只注入指定属性
	 * 
	 * @param beans
	 * @param fieldNames
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection describeCollectionIncludeFields(Collection beans,
			String[] fieldNames) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Collection rst = null;
		if (beans != null) {
			rst = beans.getClass().newInstance();
			for (Object bean : beans)
				rst.add(describeIncludeFields(bean, fieldNames));
		}
		return rst;
	}

	/**
	 * 转化一批bean，并不注入指定属性
	 * 
	 * @param beans
	 * @param fieldNames
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection describeCollectionExcludeFields(Collection beans,
			String[] fieldNames) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Collection rst = null;
		if (beans != null) {
			rst = beans.getClass().newInstance();
			for (Object bean : beans)
				rst.add(describeExcludeFields(bean, fieldNames));
		}
		return rst;
	}

	/**
	 * 转化一个bean，并只注入指定属性
	 * 
	 * @param bean
	 * @param fieldNames
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 */
	public static Map describeIncludeFields(Object bean, String[] fieldNames)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		return describe(BeanUtils.cloneBeanIncludeFields(bean, fieldNames));
	}

	/**
	 * 转化一个bean，并不注入指定属性
	 * 
	 * @param bean
	 * @param fieldNames
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 */
	public static Map describeExcludeFields(Object bean, String[] fieldNames)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		return describe(BeanUtils.cloneBeanExcludeFields(bean, fieldNames));
	}
}
