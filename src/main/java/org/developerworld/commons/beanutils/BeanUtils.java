package org.developerworld.commons.beanutils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

/**
 * bean处理工具类
 * 
 * @author Roy Huang
 * @version 20120213
 * 
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

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
		// setProperty(dest, fieldName, getProperty(orig, fieldName));
		if (dest != null && orig != null)
			if (PropertyUtils.isReadable(orig, fieldName)
					&& PropertyUtils.isWriteable(dest, fieldName))
				PropertyUtils.setProperty(dest, fieldName,
						PropertyUtils.getProperty(orig, fieldName));
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
	 * 克隆一个bean，并只注入指定属性
	 * 
	 * @param bean
	 * @param fieldNames
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 */
	public static Object cloneBeanIncludeFields(Object bean, String[] fieldNames)
			throws IllegalAccessException, InvocationTargetException,
			InstantiationException, NoSuchMethodException {
		Object rst = null;
		if (bean != null) {
			rst = bean.getClass().newInstance();
			copyPropertiesIncludeFields(rst, bean, fieldNames);
		}
		return rst;
	}

	/**
	 * 克隆一个bean，并不注入指定属性
	 * 
	 * @param bean
	 * @param fieldNames
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public static Object cloneBeanExcludeFields(Object bean, String[] fieldNames)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object rst = null;
		if (bean != null) {
			Class _class = bean.getClass();
			rst = _class.newInstance();
			copyPropertiesExcludeFields(rst, bean, fieldNames);
		}
		return rst;
	}

	/**
	 * 克隆一批bean，并只注入指定属性
	 * 
	 * @param beans
	 * @param fieldNames
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection cloneBeanCollectionIncludeFields(Collection beans,
			String[] fieldNames) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Collection rst = null;
		if (beans != null) {
			rst = beans.getClass().newInstance();
			for (Object bean : beans)
				rst.add(cloneBeanIncludeFields(bean, fieldNames));
		}
		return rst;
	}

	/**
	 * 克隆一批bean，并不注入指定属性
	 * 
	 * @param beans
	 * @param fieldNames
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection cloneBeanCollectionExcludeFields(Collection beans,
			String[] fieldNames) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Collection rst = null;
		if (beans != null) {
			rst = beans.getClass().newInstance();
			for (Object bean : beans)
				rst.add(cloneBeanExcludeFields(bean, fieldNames));
		}
		return rst;
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
		return describe(cloneBeanIncludeFields(bean, fieldNames));
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
		return describe(cloneBeanExcludeFields(bean, fieldNames));
	}

}
