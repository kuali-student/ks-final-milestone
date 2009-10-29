/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

/**
 *
 * @author nwright
 */
public class CourseActivityLogic
{

 public List<LuTypeInfo> getPossibleActivityTypes ()
  throws OperationFailedException
 {
  List<LuTypeInfo> list = new ArrayList ();
  for (LuTypeInfo info : ServiceContext.get ().getLuService ().getLuTypes ())
  {
   if (possibleActivityTypes.contains (info.getId ()))
   {
    list.add (info);
   }
  }
  return list;
 }

 public List<AtpDurationTypeInfo> getPossibleContactHoursPer ()
  throws OperationFailedException
 {
  List<AtpDurationTypeInfo> list = new ArrayList ();
  for (AtpDurationTypeInfo info : ServiceContext.get ().getAtpService ().
   getAtpDurationTypes ())
  {
   if (possibleContactHoursPer.contains (info.getId ()))
   {
    list.add (info);
   }
  }
  return list;
 }

 public static final String LECTURE_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Lecture";
 public static final String LAB_ACTIVITY_TYPE = "kuali.lu.type.activity.Lab";
 public static final String DISCUSSION_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Discussion";
 public static final String TUTORIAL_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Tutorial";
 public static final String STUDIO_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Studio";
 public static final String WEBLECTURE_ACTIVITY_TYPE =
  "kuali.lu.type.activity.WebLecture";
 public static final String WEBDISCUSS_ACTIVITY_TYPE =
  "kuali.lu.type.activity.WebDiscuss";
 public static final String CORRESPOND_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Correspond";
 public static final String ACTIVITY_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Activity";
 public static final String COLLOQUIUM_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Colloquium";
 public static final String DEMONSTRATION_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Demonstration";
 public static final String DIRECTED_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Directed";
 public static final String FIELD_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Field";
 public static final String HOMEWORK_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Homework";
 public static final String INDEPEND_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Independ";
 public static final String INTERNSHIP_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Internship";
 public static final String PRIVATE_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Private";
 public static final String RECITATION_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Recitation";
 public static final String RESEARCH_ACTIVITY_TYPE =
  "kuali.lu.type.activity.Research";
 public static final String SELFPACED_ACTIVITY_TYPE =
  "kuali.lu.type.activity.SelfPaced";
 public static final String COMPBASED_ACTIVITY_TYPE =
  "kuali.lu.type.activity.CompBased";
 public static final String VIDEOCONF_ACTIVITY_TYPE =
  "kuali.lu.type.activity.VideoConf";
 private Set<String> possibleActivityTypes = new HashSet ();


 {
  possibleActivityTypes.add (LECTURE_ACTIVITY_TYPE);
  possibleActivityTypes.add (LAB_ACTIVITY_TYPE);
  possibleActivityTypes.add (TUTORIAL_ACTIVITY_TYPE);
 }

 public void setPossibleActivityTypes (Set<String> activityTypeKeys)
 {
  this.possibleActivityTypes = activityTypeKeys;
 }

 public static final String WEEK_DURATION = "kuali.atp.duration.Week";
 public static final String MONTH_DURATION = "kuali.atp.duration.Month";
 public static final String HALF_SEMESTER_DURATION =
  "kuali.atp.duration.HalfSemester";
 public static final String SEMESTER_DURATION = "kuali.atp.duration.Semester";
 private Set<String> possibleContactHoursPer = new HashSet ();


 {
  possibleContactHoursPer.add (WEEK_DURATION);
  possibleContactHoursPer.add (MONTH_DURATION);
 }

 public void setPossibleContactHoursPer (Set<String> durationTypeKeys)
 {
  this.possibleContactHoursPer = durationTypeKeys;
 }

 public class IntensityDefaulter
 {

  public static final String WEEK_DURATION_TYPE = "kuali.atp.duration.Week";
  private String defaultPer = WEEK_DURATION_TYPE;

  public void apply (CluInfo info)
  {
   info.getIntensity ().setAtpDurationTypeKey (defaultPer);
  }

  /**
   * set this to change it from a week duration
   * @param defaultPer
   */
  public void setDefaultContactHoursPer (String defaultPer)
  {
   this.defaultPer = defaultPer;
  }

 }
 private IntensityDefaulter intensityDefaulter = new IntensityDefaulter ();

 public IntensityDefaulter getIntensityDefaulter ()
 {
  return intensityDefaulter;
 }

 public void setIntensityDefaulter (IntensityDefaulter defaulter)
 {
  intensityDefaulter = defaulter;
 }

 public class Copier
 {

  public void copy (CourseActivity source, CourseActivity destination)
  {
   destination.setSequence (source.getSequence ());
   destination.setActivityType (source.getActivityType ());
   destination.setContactHours (source.getContactHours ());
   destination.setContactHoursPer (source.getContactHoursPer ());
  }

 }
 private Copier toNewFromTemplateCopier = new Copier ();

 public Copier getToNewFromTemplateCopier ()
 {
  return toNewFromTemplateCopier;
 }

 public void setToNewFromTemplateCopier (Copier copier)
 {
  this.toNewFromTemplateCopier = copier;
 }

 private Copier toNewFromExistingCourseCopier = new Copier ();

 public Copier getToNewFromExistingCourseCopier ()
 {
  return toNewFromExistingCourseCopier;
 }

 public void setToNewFromExistingCourseCopier (Copier copier)
 {
  this.toNewFromExistingCourseCopier = copier;
 }

 private Copier toNewFromExistingProposalCopier = new Copier ();

 public Copier getToNewFromExistingProposalCopier ()
 {
  return toNewFromExistingProposalCopier;
 }

 public void setToNewFromExistingProposalCopier (
  Copier newFromExistingProposalCopier)
 {
  this.toNewFromExistingProposalCopier = newFromExistingProposalCopier;
 }

 private Copier modifyExistingCourseCopier = new Copier ();


 public Copier getModifyExistingCourseCopier ()
 {
  return modifyExistingCourseCopier;
 }

 public void setModifyExistingCourseCopier (Copier modifyExistingCourseCopier)
 {
  this.modifyExistingCourseCopier = modifyExistingCourseCopier;
 }

 public class Defaulter
 {

  public void apply (CourseActivity activity)
  {
   activity.setSequence (activity.getParentFormat ().getCourseActivities ().size ());
   activity.getCluInfo ().setState (activity.getParentFormat ().getCluInfo ().
    getState ());
  }

 }
 private Defaulter defaulter = new Defaulter ();

 public Defaulter getDefaulter ()
 {
  return defaulter;
 }

 public void setDefaulter (Defaulter defaulter)
 {
  this.defaulter = defaulter;
 }

}
