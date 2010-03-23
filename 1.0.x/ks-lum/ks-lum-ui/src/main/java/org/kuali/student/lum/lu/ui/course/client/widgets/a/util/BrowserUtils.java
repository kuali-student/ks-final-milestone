/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.util;

/**
 * @author wilj
 *
 */
public class BrowserUtils {
	public static native String getOperatingSystem() /*-{
														if (navigator.appVersion.indexOf("Win")!=-1) return "Windows";
														if (navigator.appVersion.indexOf("Mac")!=-1) return OSName="MacOS";
														if (navigator.appVersion.indexOf("X11")!=-1) return OSName="UNIX";
														if (navigator.appVersion.indexOf("Linux")!=-1) return OSName="Linux";
														return "Unknown: " + navigator.appVersion;
														}-*/;

	public static native String getUserAgent() /*-{
												return navigator.userAgent.toLowerCase();
												}-*/;

	public static boolean isIE() {
		final String ua = BrowserUtils.getUserAgent();
		return (ua != null && ua.contains("msie"));
	}
}
