package org.developerworld.commons.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class BeanUtilsTest {

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
	public void testDescribeCollectionExcludeFields()
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		List<A> as = new ArrayList<A>();
		as.add(new A(1, "abc"));
		as.add(new A(2, "efg"));
		List<Map> datas = (List<Map>) BeanUtils
				.describeCollectionIncludeFields(as, new String[] { "id" });
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			A sourceData = as.get(i);
			Assert.assertEquals(sourceData.getId()+"",data.get("id")+"");
		}
	}

	@Test
	public void testDescribeIncludeFields() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException {
		Map<String, Object> rst = BeanUtils.describeIncludeFields(new A(1,
				"aaa", new Date()), new String[] { "id", "name", "now" });
		System.out.println(rst.get("id").getClass());
		System.out.println(rst.get("name").getClass());
		System.out.println(rst.get("now").getClass());
	}
}
