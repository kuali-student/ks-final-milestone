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

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class CreditCourseLoader
{

 public CreditCourseLoader ()
 {
 }

 private Iterator<CreditCourse> source;

 public Iterator<CreditCourse> getSource ()
 {
  return source;
 }

 public void setSource (Iterator<CreditCourse> source)
 {
  this.source = source;
 }

 private PrintWriter out;

 public PrintWriter getOut ()
 {
  return out;
 }

 public void setOut (PrintWriter out)
 {
  this.out = out;
 }

 public int write ()
 {
  out.println ("--");
  out.println ("-- this was automatically generated from data in a spreadsheet");
  out.println ("--");
  out.println ("set define off");
  out.println ("");
  out.println ("set echo on");
  out.println ("");
  out.println ("set autocommit off");
  out.println ("");
  out.println ("spool C:\\svn\\maven-dictionary-generator\\trunk\\src\\main\\resources\\krudata\\CourseInsert.log");
  out.println ("");
  int row = 0;
  int written = 0;
  while (source.hasNext ())
  {
   CreditCourse cc = source.next ();
   row ++;
   written ++;
   CluInfo info = new CreditCourseToCluConverter (cc).convert ();
   write (info);
  }
  out.print ("spool off");
  return written;
 }

 private void write (CluInfo info)
 {
  writeCluIdentifierRow (info.getOfficialIdentifier ());
  writeCluPrimaryAdminOrgRow (info.getPrimaryAdminOrg ());
  writeLuRichText ("Descr" + info.getId (), info.getDescr ());
  writeCluRow (info);
  writeOfferedAtpTypesRow (info.getId (), info.getOfferedAtpTypes ());
  out.println ("");
 }

 private void writeCluIdentifierRow (CluIdentifierInfo info)
 {
  out.print ("insert into kslu_clu_ident ");
  out.print ("(");
  out.print ("ID, ");
  out.print ("CD, ");
  out.print ("DIV, ");
  out.print ("LVL, ");
  out.print ("LNG_NAME, ");
  out.print ("ORG_ID, ");
  out.print ("SHRT_NAME, ");
  out.print ("SUFX_CD, ");
  out.print ("VARTN, ");
  out.print ("TYPE, ");
  out.print ("ST");
  out.print (") values (");
  out.print (asString (info.getId ()) + ", ");
  out.print (asString (info.getCode ()) + ", ");
  out.print (asString (info.getDivision ()) + ", ");
  out.print (asString (info.getLevel ()) + ", ");
  out.print (asString (info.getLongName ()) + ", ");
  out.print (asString (info.getOrgId ()) + ", ");
  out.print (asString (info.getShortName ()) + ", ");
  out.print (asString (info.getSuffixCode ()) + ", ");
  out.print (asString (info.getVariation ()) + ", ");
  out.print (asString (info.getType ()) + ", ");
  out.print (asString (info.getState ()));
  out.println (");");
 }

 private void writeLuRichText (String id, RichTextInfo info)
 {
  if (info == null)
  {
   return;
  }
  out.print ("insert into KSLU_RICH_TEXT_T ");
  out.print ("(");
  out.print ("ID, ");
  out.print ("FORMATTED, ");
  out.print ("PLAIN");
  out.print (") values (");
  out.print (asString (id) + ", ");
  out.print (asString (info.getFormatted ()) + ", ");
  out.print (asString (info.getPlain ()));
  out.println (");");
 }

 private void writeCluPrimaryAdminOrgRow (AdminOrgInfo info)
 {
  if (info == null)
  {
   return;
  }
  out.print ("insert into KSLU_CLU_ADMIN_ORG ");
  out.print ("(");
  out.print ("ID, ");
  out.print ("ORG_ID");
  out.print (") values (");
  out.print (asString (info.getId ()) + ", ");
  out.print (asString (info.getOrgId ()));
  out.println (");");
 }

 private void writeCluRow (CluInfo info)
 {
  out.print ("insert into kslu_clu ");
  out.print ("(id, ");
  out.print ("createid, ");
  out.print ("createtime, ");
  out.print ("updateid, ");
  out.print ("updatetime, ");
  out.print ("versionind, ");
  out.print ("can_create_lui, ");
  out.print ("EFF_DT, ");
  out.print ("expir_dt, ");
  out.print ("exp_first_atp, ");
  out.print ("has_early_drop_dedln, ");
  out.print ("CLU_INTSTY_QTY, ");
  out.print ("CLU_INTSTY_TYPE, ");
  out.print ("atpdurationTypekey, ");
  out.print ("timequantity, ");
  out.print ("RT_DESCR_ID, ");
  out.print ("DEF_ENRL_EST, ");
  out.print ("def_max_enrl, ");
  out.print ("IS_ENRL, ");
  out.print ("IS_HAZR_DISBLD_STU, ");
  out.print ("lutype_id, ");
  out.print ("st, ");
  out.print ("offic_clu_id, ");
  out.print ("pri_admin_org_id)");
  out.print (" values ");
  out.print ("(" + asString (info.getId ()) + ", ");
  out.print (asString (info.getMetaInfo ().getCreateId ()) + ", ");
  out.print (asDate (info.getMetaInfo ().getCreateTime ()) + ", ");
  out.print (asString (info.getMetaInfo ().getUpdateId ()) + ", ");
  out.print (asDate (info.getMetaInfo ().getUpdateTime ()) + ", ");
  out.print (asString (info.getMetaInfo ().getVersionInd ()) + ", ");
  out.print (asBoolean (info.isCanCreateLui ()) + ", ");
  out.print (asDate (info.getEffectiveDate ()) + ", ");
  out.print (asDate (info.getExpirationDate ()) + ", ");
  out.print (asString (info.getExpectedFirstAtp ()) + ", ");
  out.print (asBoolean (info.isHasEarlyDropDeadline ()) + ", ");
  out.print (asString (info.getIntensity ().getUnitQuantity ()) + ", ");
  out.print (asString (info.getIntensity ().getUnitType ()) + ", ");
  out.print (asString (info.getStdDuration ().getAtpDurationTypeKey ()) + ", ");
  out.print (asNumber (info.getStdDuration ().getTimeQuantity ()) + ", ");
  if (info.getDescr () != null)
  {
   out.print (asString ("Descr" + info.getId ()) + ", ");
  }
  else
  {
   out.print ("null" + ", ");
  }
  out.print (asNumber (info.getDefaultEnrollmentEstimate ()) + ", ");
  out.print (asNumber (info.getDefaultMaximumEnrollment ()) + ", ");
  out.print (asBoolean (info.isEnrollable ()) + ", ");
  out.print (asBoolean (info.isHazardousForDisabledStudents ()) + ", ");
  out.print (asString (info.getType ()) + ", ");
  out.print (asString (info.getState ()) + ", ");
  out.print (asString (info.getOfficialIdentifier ().getId ()) + ", ");
  if (info.getPrimaryAdminOrg () != null)
  {
   out.print (asString (info.getPrimaryAdminOrg ().getId ()));
  }
  else
  {
   out.print ("null");
  }
  out.println (");");
 }

 private void writeOfferedAtpTypesRow (String id, List<String> offeredAtpTypes)
 {
  if (offeredAtpTypes == null)
  {
   return;
  }
  for (String atpTypeKey: offeredAtpTypes)
  {
   out.print ("insert into KSLU_CLU_ATP_TYPE_KEY ");
   out.print ("(");
   out.print ("ID, ");
   out.print ("ATP_TYPE_KEY, ");
   out.print ("CLU_ID");
   out.print (") values (");
   out.print (asString (id + "." + atpTypeKey) + ", ");
   out.print (asString (atpTypeKey) + ", ");
   out.print (asString (id));
   out.println (");");
  }
 }

 private String asString (String value)
 {
  if (value == null)
  {
   return "null";
  }
  return escape (value.toString ());
 }

 private String escape (String value)
 {
  if (value == null)
  {
   return null;
  }
  if (value.indexOf ("'") == -1)
  {
   return "'" + value + "'";
  }
  StringBuilder builder = new StringBuilder (value.length () + 2);
  for (int i = 0; i < value.length (); i ++)
  {
   char c = value.charAt (i);
   if (c == '\'')
   {
    builder.append ('\'');
   }
   builder.append (c);
  }
  return "'" + builder.toString () + "'";
 }

 private String asDate (Date value)
 {
  if (value == null)
  {
   return "null";
  }
  SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
  return "to_date ('" + sdf.format (value) + "', 'YYYY-MM-DD')";
 }

 private String asNumber (String value)
 {
  if (value == null)
  {
   return "null";
  }
  int numb = Integer.parseInt (value);
  return numb + "";
 }

 private String asNumber (Number value)
 {
  if (value == null)
  {
   return "null";
  }
  return value.toString ();
 }

 private String asBoolean (Boolean value)
 {
  if (value == null)
  {
   return "null";
  }
  if (value)
  {
   return "1";
  }
  return "0";
 }

}
