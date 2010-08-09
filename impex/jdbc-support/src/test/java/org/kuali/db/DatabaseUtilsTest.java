package org.kuali.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;
import junit.textui.TestRunner;

public class DatabaseUtilsTest extends TestCase {
	public static void main(String[] args) {
		TestRunner.run(DatabaseUtilsTest.class);
	}

	@SuppressWarnings("unchecked")
	public void test1() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("org/kuali/db/spring-config.xml");
		List<DatabasePojo> databaseConfigs = (List<DatabasePojo>) ctx.getBean("databaseConfigs");
		assertNotNull(databaseConfigs);
	}

	@SuppressWarnings("unchecked")
	public void test2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("org/kuali/db/spring-config.xml");
		List<DatabasePojo> databaseConfigs = (List<DatabasePojo>) ctx.getBean("databaseConfigs");
		assertNotNull(databaseConfigs);
		DatabaseType[] typesArray = DatabaseType.values();

		Set<DatabaseType> types = new HashSet<DatabaseType>();
		for (DatabaseType type : typesArray) {
			types.add(type);
		}

		for (DatabasePojo databaseConfig : databaseConfigs) {
			assertTrue(types.contains(databaseConfig.getType()));
		}

	}
}
