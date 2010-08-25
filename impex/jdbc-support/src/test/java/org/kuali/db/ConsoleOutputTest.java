package org.kuali.db;

import junit.framework.TestCase;

public class ConsoleOutputTest extends TestCase {

	public void test1() {
		try {
			String backspace = '\b' + "";
			System.out.println("foo" + backspace);
			System.out.print('\b');
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
