/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class CourseFormat
{

 private CluInfo cluInfo;
 private CreditCourse parentCourse;
 public CourseFormat (CreditCourse parentCourse, CluInfo cluInfo)
 {
  this.parentCourse = parentCourse;
  this.cluInfo = cluInfo;
 }

 protected CreditCourse getParentCourse ()
 {
  return parentCourse;
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

 public static final String COURSE_FORMAT_TITLE_PREFIX = "Course Format ";

 public int getSequence ()
 {
  return Integer.parseInt (cluInfo.getOfficialIdentifier ().getLongName ().
   substring (COURSE_FORMAT_TITLE_PREFIX.length ()));
 }

 public void setSequence (int sequence)
 {
  cluInfo.getOfficialIdentifier ().setLongName (COURSE_FORMAT_TITLE_PREFIX +
   sequence);
 }

 private List<CourseActivity> activities = new ArrayList ();

 public List<CourseActivity> getCourseActivities ()
 {
  return activities;
 }

 protected void setCourseActivities (List<CourseActivity> activities)
 {
  this.activities = activities;
 }

 public CourseActivity addCourseActivity ()
 {
  CourseActivity activity = new CourseActivity (this, new CluInfo ());
  activities.add (activity);
  LogicContext.get ().getCourseActivityLogic ().getDefaulter ().apply (activity);
  return activity;
 }

 
 protected void setState (String state)
 {
  this.cluInfo.setState (state);
  for (CourseActivity activity : activities)
  {
   activity.getCluInfo ().setState (state);
  }
 }


}
