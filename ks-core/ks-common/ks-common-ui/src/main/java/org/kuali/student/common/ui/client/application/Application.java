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

package org.kuali.student.common.ui.client.application;

import org.kuali.student.common.ui.client.mvc.history.HistoryManager;


/**
 * Application contains the instance of the ApplicationContext which contain information about the
 * current context, also a method that does the same thing as HistoryManager.navigate for convenience
 */
public class Application {
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
	    if(applicationContext == null){
	        applicationContext = new ApplicationContext();
	    }
		return applicationContext;
	}
	public static void setApplicationContext(ApplicationContext applicationContext) {
		Application.applicationContext = applicationContext;
	}
	
	public static void navigate(String viewPath){
		HistoryManager.navigate(viewPath);
	}
	
	public static void navigate(String viewPath, ViewContext context){
		HistoryManager.navigate(viewPath, context);
	}
	
}
