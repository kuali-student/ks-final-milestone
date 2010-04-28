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
import java.util.Arrays;
import java.util.List;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class CreditCourseToCluConverter
{


 private CreditCourse course;

 public CreditCourseToCluConverter (CreditCourse course)
 {
  this.course = course;
 }

 public CluInfo convert ()
 {
  CluInfo cluInfo = new CluInfo ();
  cluInfo.setId (course.getId ());
  cluInfo.setPrimaryAdminOrg (new AdminOrgInfoHelper ().get ("Primary" + course.getId (), course.getPrimaryAdminOrgId ()));
  cluInfo.setDescr (new RichTextInfoHelper ().getFromPlain (course.getDesc ()));
  CluIdentifierInfo officialIdentifierInfo = new CluIdentifierInfo ();
  cluInfo.setOfficialIdentifier (officialIdentifierInfo);
  officialIdentifierInfo.setCode (course.getCode ());
  officialIdentifierInfo.setDivision (course.getDivision ());
  officialIdentifierInfo.setSuffixCode (course.getSuffixCode ());
  officialIdentifierInfo.setVariation (course.getVariation ());
  officialIdentifierInfo.setShortName (course.getShortName ());
  officialIdentifierInfo.setLongName (course.getLongName ());
  officialIdentifierInfo.setType ("kuali.lu.type.CreditCourse.identifier.official");
  officialIdentifierInfo.setState ("active");
  officialIdentifierInfo.setId ("official" + course.getId ());
  cluInfo.setOfferedAtpTypes (convertOfferedAtpTypes (course.getOfferedAtpTypes ()));
  cluInfo.setType ("kuali.lu.type.CreditCourse");
  cluInfo.setState ("activated");
  List<String> campuses = new ArrayList ();
  campuses.add ("North");
  cluInfo.setCampusLocations (campuses);
  cluInfo.setEnrollable (true);
  cluInfo.setIntensity (new AmountInfoHelper ().get ("1", "kuali.atp.duration.Semester"));
  cluInfo.setStdDuration (new TimeAmountInfoHelper ().get (1, "kuali.atp.duration.Semester"));
  cluInfo.setExpectedFirstAtp ("kuali.atp.FA2008-2009");
  cluInfo.setHasEarlyDropDeadline (false);
  cluInfo.setHazardousForDisabledStudents (false);
  cluInfo.setEnrollable (true);
  cluInfo.setCanCreateLui (true);
  cluInfo.setDefaultEnrollmentEstimate (0);
  cluInfo.setDefaultMaximumEnrollment (50);
  cluInfo.setEffectiveDate (new DateHelper ().asDate ("2010-01-01"));
  cluInfo.setMetaInfo (new MetaInfoHelper ().get ());
  return cluInfo;
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
