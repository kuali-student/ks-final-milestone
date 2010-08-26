/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.wsdl.course.AdminOrgInfo;


/**
 *
 * @author nwright
 */
public class AdminOrgInfoHelper
{
 public AdminOrgInfo get (String type, String orgName)
 {
  if (orgName == null)
  {
   return null;
  }
  AdminOrgInfo info = new AdminOrgInfo ();
  info.setType (type);
  info.setOrgId (orgName);
  return info;
 }


 private static final Map<String, String> orgNamesToId = new HashMap ();

 public String findOrgId (String orgName)
 {
  if (orgName == null)
  {
   return null;
  }
  if  (orgNamesToId.containsKey (orgName))
  {
   return orgNamesToId.get (orgName);
  }
  String id = findOrgIdInternal (orgName);
  if (id == null)
  {
   return null;
  }
  orgNamesToId.put (orgName, id);
  return id;
  }

 public static final String DEPARTMENT = "kuali.org.Department";
 public static final String OFFICE = "kuali.org.Office";
 public String findOrgIdInternal (String orgName)
 {
  List<String> types = new ArrayList ();
  types.add (DEPARTMENT);
  types.add (OFFICE);
  return new OrgFinder ().find (orgName, types);
 }
}
