/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.List;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class CreditCourse
{

 private CluInfo cluInfo;

 public CreditCourse (CluInfo cluInfo)
 {
  this.cluInfo = cluInfo;
 }

 protected CluInfo getCluInfo ()
 {
  return cluInfo;
 }

 private List<CourseFormat> formats;

 public List<CourseFormat> getCourseFormats ()
 {
  return formats;
 }

 protected void setCourseFormats (List<CourseFormat> formats)
 {
  this.formats = formats;
 }

 public CourseFormat addCourseFormat ()
 {
  CourseFormat format = new CourseFormat (this, new CluInfo ());
  formats.add (format);
  LogicContext.get ().getCourseFormatLogic ().getDefaulter ().apply (format);
  return format;
 }

 public void setState (String state)
 {
  this.cluInfo.setState (state);
  for (CourseFormat format : formats)
  {
   format.setState (state);
  }
 }

 public List<String> getTermssOffered ()
 {
  return cluInfo.getOfferedAtpTypes ();
 }

 public void setTermsOffered (List<String> terms)
 {
  cluInfo.setOfferedAtpTypes (terms);
 }

 protected TimeAmountInfo getStdDuration ()
 {
  if (cluInfo.getStdDuration () == null)
  {
   TimeAmountInfo info = new TimeAmountInfo ();
   cluInfo.setStdDuration (info);
   LogicContext.get ().getCreditCourseLogic ().getStdDurationDefaulter ().
    apply (this);
  }
  return cluInfo.getStdDuration ();
 }

 public String getDuration ()
 {
  if (cluInfo.getStdDuration () == null)
  {
   return null;
  }
  return getStdDuration ().getAtpDurationTypeKey ();
 }

 public void setDuration (String duration)
 {
  if (duration == null)
  {
   cluInfo.setStdDuration (null);
   return;
  }
  getStdDuration ().setAtpDurationTypeKey (duration);
 }

 protected CluIdentifierInfo getOfficialIdentifier ()
 {
  if (this.cluInfo.getOfficialIdentifier () != null)
  {
   return this.cluInfo.getOfficialIdentifier ();
  }
  CluIdentifierInfo idInfo = new CluIdentifierInfo ();
  this.cluInfo.setOfficialIdentifier (idInfo);
  LogicContext.get ().getCreditCourseLogic ().getOfficialIdentifierDefaulter ().
   apply (this);
  return idInfo;
 }

 public void setTranscriptTitle (String title)
 {
  this.getOfficialIdentifier ().setShortName (title);
 }

 public String getTranscriptTitle ()
 {
  return this.getOfficialIdentifier ().getShortName ();
 }

 public void setCourseTitle (String title)
 {
  this.getOfficialIdentifier ().setLongName (title);
 }

 public String getCourseTitle ()
 {
  return this.getOfficialIdentifier ().getLongName ();
 }

 public RichTextInfo getDescription ()
 {
  return cluInfo.getDesc ();
 }

 public void setDescription (RichTextInfo description)
 {
  cluInfo.setDesc (description);
 }

 protected AdminOrgInfo getPrimaryAdminOrg ()
 {
  if (cluInfo.getPrimaryAdminOrg () == null)
  {
   AdminOrgInfo orgInfo = new AdminOrgInfo ();
   this.getCluInfo ().setPrimaryAdminOrg (orgInfo);
   LogicContext.get ().getCreditCourseLogic ().getPrimaryAdminOrgDefaulter ().apply (this);
  }
  return cluInfo.getPrimaryAdminOrg ();
 }

 public void setDepartment (String orgId)
 {
  if (orgId == null)
  {
   cluInfo.setPrimaryAdminOrg (null);
   return;
  }
  getPrimaryAdminOrg ().setOrgId (orgId);
 }

 public String getDepartment ()
 {
  if (cluInfo.getPrimaryAdminOrg () == null)
  {
   return null;
  }
  return getPrimaryAdminOrg ().getOrgId ();
 }

 public String getCourseNumber ()
 {
  return getOfficialIdentifier ().getCode ();
 }

 public String getCourseNumberSubjectArea ()
 {
  return this.getOfficialIdentifier ().getDivision ();
 }

 public void setCourseNumberSubjectArea (String subjectArea)
 {
  this.getOfficialIdentifier ().setDivision (subjectArea);
  LogicContext.get ().getCreditCourseLogic ().
   getOfficialIdentifierCalculator ().apply (this);
 }

 public String getCourseNumberSuffix ()
 {
  return this.getOfficialIdentifier ().getSuffixCode ();

 }

 public void setCourseNumberSuffix (String suffix)
 {
  this.getOfficialIdentifier ().setSuffixCode (suffix);
  LogicContext.get ().getCreditCourseLogic ().
   getOfficialIdentifierCalculator ().apply (this);
 }

 

}
