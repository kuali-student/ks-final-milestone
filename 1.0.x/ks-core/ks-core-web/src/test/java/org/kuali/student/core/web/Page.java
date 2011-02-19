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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/*NOTES TO SELF
 * !moved htmlunit dependency from test pom to web pom
 * 
 * 1.try to update tomcat-maven-plugin with "http://jira.codehaus.org/browse/MTOMCAT-20"
 * 		- because forking seems to not be working (tests don't run when page deployed)
 * 		
 * 		1a. get source file, patch specific java file, and ...
 * 
 * 2.only login page is deployed in this version, actual page does not compile
 * 		- because of forking?
 * 		-something else?
 */

public class Page {
	
	private WebClient webClient = new WebClient();
	private HtmlPage page;
	
	public Page(){
	}
	
	public Page(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(10000);
	}
	
	public Page(URL url) throws FailingHttpStatusCodeException, IOException{
		page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(10000);
	}
	
	public void setPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(10000);
	}
	
	public void setPage(URL url) throws FailingHttpStatusCodeException, IOException{
		page = webClient.getPage(url);
		webClient.waitForBackgroundJavaScript(10000);
	}
	
	public HtmlPage getPage(){
		return page;
	}
	
	public void setWebClient(WebClient webClient){
		this.webClient = webClient;
	}
	
	public WebClient getWebClient(){
		return webClient;
	}
	
	public HtmlPage logIn(String user, String password) throws IOException{
		HtmlForm form = page.getElementByName("f");
		HtmlTextInput userInput = form.getInputByName("j_username");
		HtmlPasswordInput passwordInput = form.getInputByName("j_password");
		HtmlInput submit = form.getInputByName("submit");
		
		userInput.setValueAttribute(user);
		passwordInput.setValueAttribute(password);
		
		page = submit.click();
		
		webClient.waitForBackgroundJavaScript(10000);
		
		return page;
	}
	
	//use to automatically log in for now
	public HtmlPage bypass() throws IOException{
		return this.logIn("a", "a");
	}
	
	@Test
	public void defaultTest(){
		assertNotNull(this.webClient);
	}

}

