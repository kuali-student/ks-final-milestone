package org.kuali.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.kuali.db.jdbc.DatabaseType;
import org.kuali.db.jdbc.JDBCConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DatabaseUtilsTest extends TestCase {
    public static void main(final String[] args) {
        TestRunner.run(DatabaseUtilsTest.class);
    }

    @SuppressWarnings("unchecked")
    public void test1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("org/kuali/db/jdbc-context.xml");
        List<JDBCConfiguration> databaseConfigs = (List<JDBCConfiguration>) ctx
                .getBean("org/kuali/db/jdbc-context.xml");
        assertNotNull(databaseConfigs);
    }

    @SuppressWarnings("unchecked")
    public void test2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("org/kuali/db/jdbc-context.xml");
        List<JDBCConfiguration> databaseConfigs = (List<JDBCConfiguration>) ctx
                .getBean("org/kuali/db/jdbc-context.xml");
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
