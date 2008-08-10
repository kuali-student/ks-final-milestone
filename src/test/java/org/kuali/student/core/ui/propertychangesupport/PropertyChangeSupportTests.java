package org.kuali.student.core.ui.propertychangesupport;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PropertyChangeSupportTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.kuali.student.core.ui.propertychangesupport");
		//$JUnit-BEGIN$

		suite.addTest(new BasicPropertyChangeSupportTest());
		
		//$JUnit-END$
		return suite;
	}

}
