package org.kuali.db;

public class ConsoleOutputTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			char backspaceChar = '\b';
			String backspace = backspaceChar + "";
			System.out.println("foo" + backspace);
			System.out.print('\b');
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
