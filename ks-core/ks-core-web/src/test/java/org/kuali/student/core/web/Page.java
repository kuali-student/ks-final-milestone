/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
}
