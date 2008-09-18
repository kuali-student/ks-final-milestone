package org.kuali.student.embedded.util;

import org.kuali.rice.kew.batch.KEWXmlDataLoader;

public class KEWConfigLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] data = {"classpath:users.xml"};
		for (String s : data) {
			try {
				// This doesn't work since it expects the XMLIngester service to be up and running
				KEWXmlDataLoader.loadXmlResource(s);	
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			
		}
	}

}
