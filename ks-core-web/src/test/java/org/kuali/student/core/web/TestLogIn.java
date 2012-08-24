/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.web;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

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
