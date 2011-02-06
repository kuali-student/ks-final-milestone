package org.kuali.db;

import org.kuali.db.jdbc.ConnectionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("impex-context.xml");
			ConnectionHandler ch = (ConnectionHandler) context.getBean("impexDbaConnectionHandler");
			System.out.println(ch.getCredentials().getUsername());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
