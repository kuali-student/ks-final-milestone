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

package org.kuali.student.common.test.spring;

import org.junit.runner.RunWith;

/**
 * This class will start a Jetty server and deploy an exploded war file 
 * defined in &#064;IntegrationServer annotation. 
 * <p>
 * <b>&#064;IntegrationServer</b> annotation takes the following parameters:
 * </p>
 * <ul>
 *   <li><b>port</b> - Server port to deploy web application on e.g. 9090</li>
 *   <li><b>webAppPath</b> - Webapp path of exploded war file e.g. brms-ws-1.0.0.war</li>
 *   <li><b>contextPath</b> - Servlet context path e.g. brms-ws-1.0.0</li>
 * </ul>
 * <p>
 * The &#064;SystemProperties and &#064;Property annotation is used to set 
 * System properties before starting the Jetty server. For instance, if you 
 * want to set the catalina.base path or the URL or port of a service that is 
 * different from what is specified in the file CXF configuration in the war 
 * file.
 * </p>
 * <p>
 * <b>&#064;SystemProperties</b> takes the following parameter:
 * </p>
 * <ul>
 *   <li><b>properties</b> - List of <code>Property</code> to set</li>
 * </ul>
 * <p>
 * <b>&#064;Property</b> takes the following parameter:
 * </p>
 * <ul>
 *   <li><b>key</b> - Property key</li>
 *   <li><b>value</b> - Property value</li>
 * </ul>
 * <p>
 * <b>Example 1:</b> Setup an integration test on port 8080 which is the same 
 * port as specified in the application.properties file in the war file.
 * </p>
 * <pre>
 * &#064;IntegrationServer(
 *     port=8080, 
 *     webAppPath="../../../brms-ws/target/brms-ws-0.1.0-SNAPSHOT", 
 *     contextPath="/brms-ws-0.1.0-SNAPSHOT")
 * </pre>
 * 
 * <b>Example 2:</b> Setup an integration test on port 9090 which is different 
 * from what is specified in the application.properties file in the war file.
 * </p>
 * <pre>
 * &#064;IntegrationServer(
 *     port=9090, 
 *     webAppPath="../../../brms-ws/target/brms-ws-0.1.0-SNAPSHOT", 
 *     contextPath="/brms-ws-0.1.0-SNAPSHOT")
 *     
 * &#064;SystemProperties(properties={
 *     &#064;Property(
 *         key="ks.servicelocation.RuleManagementService", 
 *         value="http://localhost:9090/brms-ws-0.1.0-SNAPSHOT/services/RuleManagementService")
 *  })
 * </pre>
 * 
 * <b>Example 3:</b> Integration test.
 * <pre>
 * <b>package</b> org.kuali.student.rules.integration;
 * // The following system properties are only needed if you want to specify a
 * // server port (port 9000) other than 8080 as defined in
 * // brms-ws/src/main/resources/application.properties, 
 * // application-ruleexecution.properties and application-rulerepository.properties
 * <b>&#064;IntegrationServer</b>(port=9000, webappPath="../../../brms-ws/target/brms-ws-0.1.0-SNAPSHOT", contextPath="/brms-ws-0.1.0-SNAPSHOT")
 * <b>&#064;SystemProperties</b>(properties={
 *     <b>&#064;Property</b>(key="ks.servicelocation.RuleManagementService", value="http://localhost:9000/brms-ws-0.1.0-SNAPSHOT/services/RuleManagementService"),
 * })
 * <b>public class</b> IntegrationTest <b>extends</b> AbstractIntegrationServiceTest {
 *     private final static String HOST = "http://localhost:9000/brms-ws-0.1.0-SNAPSHOT";
 * 
 *     private static String ruleManagementServiceURL = HOST+"/services/RuleManagementService";
 *     private static String ruleManagementNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement";
 *     private static String ruleManagementServiceName = "RuleManagementService";
 *     private static String ruleManagementServiceInterface = RuleManagementService.class.getName();
 * 
 *     private static RuleManagementService ruleManagementService;
 * 
 *     <b>&#064;BeforeClass</b>
 *     public static void setUpOnce() throws Exception {
 *         ruleManagementService = (RuleManagementService) ServiceFactory.getPort(
 *             ruleManagementServiceURL + "?wsdl", 
 *             ruleManagementNamespace, 
 *             ruleManagementServiceName, 
 *             ruleManagementServiceInterface, 
 *             ruleManagementServiceURL);
 *
 *     }
 * 	
 *     <b>&#064;AfterClass</b>
 *     public static void tearDownOnce() throws Exception { }
 *
 *     <b>&#064;Before</b>
 *     public void setUp() throws Exception { }
 * 	
 *     <b>&#064;After</b>
 *     public void tearDown() throws Exception { }
 *     
 *     <b>&#064;Test</b>
 *     public void testFindBusinessRuleTypesFromTestBeans() throws Exception {
 *         List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
 *         Assert.assertNotNull(businessRuleIdList1);
 *         List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");
 *         Assert.assertNotNull(businessRuleIdList2);
 *     }
 * }
 * </pre>
 */
@Deprecated
@RunWith(IntegrationServiceTestClassRunner.class)
public abstract class AbstractIntegrationServiceTest {

}
