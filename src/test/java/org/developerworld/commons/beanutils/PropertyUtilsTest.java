package org.developerworld.commons.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.junit.Test;

public class PropertyUtilsTest {

	public static class A {

		private Integer id;
		private String name;
		private Date now;

		public A() {

		}

		public A(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public A(Integer id, String name, Date now) {
			super();
			this.id = id;
			this.name = name;
			this.now = now;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getNow() {
			return now;
		}

		public void setNow(Date now) {
			this.now = now;
		}

	}
	
	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
		
		System.out.println(1);
		Map data=PropertyUtils.describeIncludeFields(new A(1,"abc",new Date()),new String[]{"id","name","now"});
		System.out.println(data.get("id").getClass());
		System.out.println(data.get("name").getClass());
		System.out.println(data.get("now").getClass());
	}
}
