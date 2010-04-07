package com.eviware.soapui.maven2;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 *
 * 
 */
public class URLUtilTest extends TestCase {
	URLUtil uu = new URLUtil();

	public static void main(String[] args) {
		TestRunner.run(URLUtilTest.class);
	}

	public void testReplaceHost() {
		System.out.println();
		String host = "test.kuali.org";
		String endpoint = "http://localhost/ks-embedded/services/LUService";
		String s = uu.replaceHost(endpoint, host);
		assertTrue(s.startsWith("http://test.kuali.org/"));
		System.out.println(s);
	}

	public void testReplaceContext1() {
		System.out.println();
		String context = "ks-stg";
		String endpoint = "http://localhost/ks-embedded/services/LUService";
		String s = uu.replaceContext(endpoint, context);
		assertTrue(s.startsWith("http://localhost/ks-stg/services"));
		System.out.println(s);
	}

	public void testReplaceContext2() {
		System.out.println();
		String context = "ks-stg";
		String endpoint = "http://localhost/services/LUService";
		String s = uu.replaceContext(endpoint, context);
		assertTrue(s.startsWith("http://localhost/ks-stg/services"));
		System.out.println(s);
	}

	public void testReplaceContext3() {
		System.out.println();
		String context = "ks-stg";
		String endpoint = "http://localhost";
		String s = uu.replaceContext(endpoint, context);
		assertTrue(s.startsWith("http://localhost/ks-stg"));
		System.out.println(s);
	}

	public void testGetUpdatedEndPoint1() {
		System.out.println();
		String endpoint = "http://localhost/ks-embedded/services/LUService";
		String baseURL = "https://test.kuali.org/ks-stg";
		String servicesContext = "services";
		String s = uu.getUpdatedEndPoint(endpoint, baseURL, servicesContext);
		System.out.println(s);
		assertTrue(s.startsWith("https://test.kuali.org/ks-stg/services/"));
	}

	public void testGetUpdatedEndPoint2() {
		System.out.println();
		String endpoint = "http://localhost/services/LUService";
		String baseURL = "https://test.kuali.org/";
		String servicesContext = "services";
		String s = uu.getUpdatedEndPoint(endpoint, baseURL, servicesContext);
		System.out.println(s);
		assertTrue(s.startsWith("https://test.kuali.org/services/"));
	}
}
