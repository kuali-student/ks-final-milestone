package org.kuali.student.core.web;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class TestLogIn {
	
	Page page;
	
	@Test
	public void loadPage() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		page = new Page("http://localhost:8181/ks-core-web/spring_security_login");
		assertTrue(page.getPage().getTitleText().equals("Login Page"));
	}
	
	@Test
	public void testLogIn() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		Page toLogIn = new Page("http://localhost:8181/ks-core-web/spring_security_login");
		toLogIn.logIn("same", "same");
		assertTrue(toLogIn.getPage().getTitleText().contains("Kuali Student: Organization"));
	}

}
