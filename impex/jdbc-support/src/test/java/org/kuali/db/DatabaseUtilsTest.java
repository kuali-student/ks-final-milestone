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
		ApplicationContext ctx = new ClassPathXmlApplicationContext(JDBCUtils.JDBC_CONTEXT);
		List<JDBCConfiguration> databaseConfigs = (List<JDBCConfiguration>) ctx.getBean(JDBCUtils.JDBC_CONFIGURATIONS);
		assertNotNull(databaseConfigs);
	}

	@SuppressWarnings("unchecked")
	public void test2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(JDBCUtils.JDBC_CONTEXT);
		List<JDBCConfiguration> databaseConfigs = (List<JDBCConfiguration>) ctx.getBean(JDBCUtils.JDBC_CONFIGURATIONS);
		assertNotNull(databaseConfigs);
		DatabaseType[] typesArray = DatabaseType.values();

		Set<DatabaseType> types = new HashSet<DatabaseType>();
		for (DatabaseType type : typesArray) {
			types.add(type);
		}

		for (JDBCConfiguration databaseConfig : databaseConfigs) {
			assertTrue(types.contains(databaseConfig.getType()));
		}

	}
}
