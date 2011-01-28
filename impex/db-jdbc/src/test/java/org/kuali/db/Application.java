package org.kuali.db;

import org.kuali.db.jdbc.DatabaseManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(final String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("org/kuali/db/jdbc-context.xml");
            DatabaseManager dbm = (DatabaseManager) context.getBean("databaseManager");
            dbm.dropDatabase();
            dbm.createDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
