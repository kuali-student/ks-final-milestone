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

import java.util.Arrays;
import java.util.List;
import org.kuali.student.wsdl.course.AdminOrgInfo;
import org.kuali.student.wsdl.organization.OrgInfo;

/**
 *
 * @author nwright
 */
public class OrganizationToOrgInfoConverter
{


 private Organization org;

 public OrganizationToOrgInfoConverter (Organization org)
 {
  this.org = org;
 }

 public static final String ADMINISTRATION_ADMIN_ORG_TYPE = "kuali.adminOrg.type.Administration";
 public OrgInfo convert ()
 {
  OrgInfo orgInfo = new OrgInfo ();
  orgInfo.setId (null);
  orgInfo.setShortName (org.getShortName ());
  orgInfo.setLongName (org.getLongName ());
  orgInfo.setSortName (org.getSortName ());
  orgInfo.setShortDesc (org.getShortDesc ());
  orgInfo.setLongDesc (org.getLongDesc ());
  orgInfo.setType (org.getType ());
  orgInfo.setState (org.getState ());

  orgInfo.setEffectiveDate (new DateHelper ().asXmlDate (org.getEffectiveDate ()));
  orgInfo.setExpirationDate (new DateHelper ().asXmlDate (org.getExpirationDate ()));
  return orgInfo;
 }

// public static final int ID = 0;
// public static final int SHORT_NAME = 1;
// public static final int LONG_NAME = 2;
//
//
// private String findOrgId (String name)
// {
//  if (name == null)
//  {
//   return null;
//  }
//  List<QueryParamValue> values = null;
//  String type = null;
//  QueryParamValue qpv = null;
//  type = "kuali.org.search.nameContains";
//  values = new ArrayList ();
//  qpv = new QueryParamValueBean ();
//  values.add (qpv);
//  qpv.setKey ("kuali.org.queryParam.searchName");
//  qpv.setValue (name);
//  qpv = new QueryParamValueBean ();
//  values.add (qpv);
//  qpv.setKey ("kuali.org.queryParam.types");
//  qpv.setValue ("kuali.org.Department");
//  qpv = new QueryParamValueBean ();
//  values.add (qpv);
//  qpv.setKey ("kuali.org.queryParam.types");
//  qpv.setValue ("kuali.org.Office");
//  qpv = new QueryParamValueBean ();
//  values.add (qpv);
//  qpv.setKey ("kuali.org.queryParam.types");
//  qpv.setValue ("kuali.org.Section");
//  List<Result> results = null;
//  try
//  {
//   results = context.getSearchService ().searchForResults (type, values);
//  }
//  catch (Exception ex)
//  {
//   throw new IllegalArgumentException (ex);
//  }
//  if (results.size () == 0)
//  {
//   log.warning ("could not find a primary admin organization  for course "
//                                       + course.getCode () + " with org name="
//                                       + name);
//   return null;
//  }
//  if (results.size () == 1)
//  {
//   return results.get (0).getResultCells ().get (ID).getValue ();
//  }
//  StringBuilder builder = new StringBuilder ();
//  builder.append (results.size ());
//  builder.append (" organizations match the supplied criteria ");
//  builder.append (name);
//  for (Result result: results)
//  {
//   builder.append ("\n");
//   builder.append ("shortName=");
//   builder.append (result.getResultCells ().get (SHORT_NAME).getValue ());
//   builder.append (" longName=");
//   builder.append (result.getResultCells ().get (LONG_NAME).getValue ());
//  }
//  log.warning ("Skipping could not find primary admin organization for course "
//                                      + course.getCode () + " because " + builder.
//      toString ());
//  return null;
// }
 private List<String> convertOfferedAtpTypes (String offeredAtpTypes)
 {
  if (offeredAtpTypes == null)
  {
   return null;
  }
  return Arrays.asList (offeredAtpTypes.split (" "));
 }

}
