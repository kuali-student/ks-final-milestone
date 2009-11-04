/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class CourseActivity
{

 private CluInfo cluInfo;
 private CourseFormat parentFormat;

 public CourseActivity (CourseFormat parentFormat, CluInfo cluInfo)
 {
  this.parentFormat = parentFormat;
  this.cluInfo = cluInfo;
 }

 protected CourseFormat getParentFormat ()
 {
  return parentFormat;
 }
 
 protected CluInfo getCluInfo ()
 {
  return cluInfo;
 }

 boolean isDeleted = false;

 public void delete ()
 {
  // TODO: worry about renumbering the others sequences when this is deleted.
  this.isDeleted = true;
 }

 public String getActivityType ()
 {
  return cluInfo.getType ();
 }

 protected String activityToDeleteId = null;
 protected String activityToDeleteType = null;

 public void setActivityType (String activityType)
 {
  if (cluInfo.getId () == null)
  {
   if (activityType.equals (activityToDeleteType))
   {
    cluInfo.setId (activityToDeleteId);
   }
   cluInfo.setType (activityType);
   return;
  }
  activityToDeleteId = cluInfo.getId ();
  activityToDeleteType = cluInfo.getType ();
  cluInfo.setId (null);
  cluInfo.setType (activityType);
  return;
 }

 public static final String COURSE_ACTIVITY_TITLE_PREFIX = "Activity ";

 public int getSequence ()
 {
  return Integer.parseInt (cluInfo.getOfficialIdentifier ().getLongName ().
   substring (COURSE_ACTIVITY_TITLE_PREFIX.length ()));
 }

 public void setSequence (int sequence)
 {
  cluInfo.getOfficialIdentifier ().setLongName (COURSE_ACTIVITY_TITLE_PREFIX +
   sequence);
 }

 protected TimeAmountInfo getIntensity ()
 {
  if (cluInfo.getIntensity () == null)
  {
   cluInfo.setIntensity (new TimeAmountInfo ());
   LogicContext.get ().getCourseActivityLogic ().getIntensityDefaulter ().
    apply (cluInfo);
  }

  return cluInfo.getIntensity ();
 }

 public Integer getContactHours ()
 {
  if (cluInfo.getIntensity () == null)
  {
   return null;
  }
  return getIntensity ().getTimeQuantity ();
 }

 public void setContactHours (Integer hrs)
 {
  if (hrs == null)
  {
   cluInfo.setIntensity (null);
   return;
  }
  getIntensity ().setTimeQuantity (hrs);
 }

 public String getContactHoursPer ()
 {
  if (cluInfo.getIntensity () == null)
  {
   return null;
  }
  return getIntensity ().getAtpDurationTypeKey ();
 }

 public void setContactHoursPer (String per)
 {
  if (per == null)
  {
   cluInfo.setIntensity (null);
   return;
  }
  getIntensity ().setAtpDurationTypeKey (per);
 }

}
