/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary.command.run;

/**
 *
 * @author nwright
 */
public interface RunConstants
{

 public static final String RESOURCES_DIRECTORY = "src/test/resources/";
 public static String SERVICE_METHODS_EXCEL_FILE = RESOURCES_DIRECTORY +
  "service methods.xls";
 public static String ORG_SEARCH_EXCEL_FILE = RESOURCES_DIRECTORY +
  "Organization Search Specification.xls";
 public static String ORG_SEARCH_EXCEL_FILE_BAD = RESOURCES_DIRECTORY +
  "Organization Search Specification Test For Errors.xls";
 //public static String DIRECTORY_TO_WRITE_SOURCE = "target/";
 public static final String COMPONENT_SANDBOX_ROOT_PACKAGE =
  "org.kuali.student.orchestration";
 public static final String LUM_UI_ROOT_PACKAGE =
  "org.kuali.student.lum.lu.assembly.data.client.refactorme";
 public static final String COMPONENT_SANDBOX_DIRECTORY_TO_WRITE_RESOURCES =
  "../../maven-component-sandbox/trunk/src/main/resources";
 public static final String COMPONENT_SANDBOX_DIRECTORY_TO_WRITE_JAVA =
  "../../maven-component-sandbox/trunk/src/main/java";
 public static final String LUM_UI_DIRECTORY_TO_WRITE_JAVA =
  "../../ks-lum-dev/ks-lum-ui/src/main/java";
 public static final String LUM_UI_DIRECTORY_TO_WRITE_RESOURCES =
  "../../ks-lum-dev/ks-lum-ui/src/main/resources";
 //public static String DIRECTORY_TO_WRITE_SOURCE = "C:/svn/kuali-student/maven-component-sandbox/trunk/src/main/java";
 public static final String TYPE_STATE_DICTIONARY_EXCEL_FILE =
  RESOURCES_DIRECTORY + "type-state configuration.xls";
 public static final String SERVICES_EXCEL_FILE =
  RESOURCES_DIRECTORY + "services.xls";
 public static final String ORCHESTRATION_DICTIONARY_EXCEL_FILE =
  RESOURCES_DIRECTORY + "orchestration-dictionary.xls";

 public static final String ATP_CONTRACT_PATH_ON_DISK =
  RESOURCES_DIRECTORY  + "Academic+Time+Period+Service.html";
 public static final String ATP_CONTRACT_PATH_ON_WIKI =
  "https://test.kuali.org/confluence/display/KULSTU/Academic+Time+Period+Service";
 public static final String SERVICE_REPOSITORY_PATH_ON_WIKI =
  "https://test.kuali.org/confluence/display/KULSTU/Service+Description+Repository";
public static final String SERVICE_REPOSITORY_PATH_ON_DISK =
  RESOURCES_DIRECTORY  + "Service+Description+Repository.html";
 public static final String ATP_DURATION_TYPE_CONTRACT_PATH_ON_WIKI =
  "https://test.kuali.org/confluence/display/KULSTU/atpDurationTypeInfo+Structure";
 public static final String ATP_DURATION_TYPE_KEY_CONTRACT_PATH_ON_WIKI =
  "https://test.kuali.org/confluence/display/KULSTU/atpDurationTypeKey+Structure";
 public static final String ORG_POSITION_RESTRICTION_CONTRACT_PATH_ON_WIKI =
 "https://test.kuali.org/confluence/display/KULSTU/orgPositionRestrictionInfo+Structure+v1.0-rc2";
 public static final String ATP_DURATION_TYPE_CONTRACT_PATH_ON_DISK =
  RESOURCES_DIRECTORY  + "atpDurationTypeInfo+Structure.html";
  public static final String ATP_DURATION_TYPE_SAVED_CONTRACT_PATH_ON_DISK =
  RESOURCES_DIRECTORY  + "atpDurationTypeInfo+Structure-Saved.html";
  public static final String ATP_DURATION_TYPE_BODY_CONTRACT_PATH_ON_DISK =
  RESOURCES_DIRECTORY  + "atpDurationTypeInfo+Structure-Body.html";
}
