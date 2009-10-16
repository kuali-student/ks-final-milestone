/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSManageCourseFormatFunction;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.service.dto.AtpTypeKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

/**
 *
 * @author nwright
 */
public class KSManageCourseFormatComponent
 implements KSManageCourseFormatFunction
{

 private KSContext context;

 public KSManageCourseFormatComponent (KSContext context)
 {
  this.context = context;
 }

 private KSManageCourseFormatFunction.MainCourse mainCourse;

 @Override
 public KSManageCourseFormatFunction.MainCourse getMainCourse ()
 {
  return mainCourse;
 }

 @Override
 public void setMainCourse (KSManageCourseFormatFunction.MainCourse mainCourse)
 {
  this.mainCourse = mainCourse;
 }

 public class MainCourse
  implements KSManageCourseFormatFunction.MainCourse
 {

  private CluInfo mainClu;

  public MainCourse (CluInfo mainClu)
  {
   this.mainClu = mainClu;
  }

  @Override
  public List<AtpDurationTypeInfo> getPossibleDurations ()
  {
   List<AtpDurationTypeInfo> list = new ArrayList ();
   // TODO: populate from some source or hard code
   return list;
  }

  private AtpDurationTypeInfo duration;

  @Override
  public AtpDurationTypeInfo getDuration ()
  {
   return duration;
  }

  @Override
  public void setDuration (AtpDurationTypeInfo duration)
  {
   this.duration = duration;
  }

  private AtpTypeInfo term;
  public Map<AtpTypeKey, AtpTypeInfo> terms;
  public static final String ANY_TERM = "ANY TERM";

  @Override
  public List<AtpTypeInfo> getPossibleTerms ()
  {
   List<AtpTypeInfo> list = new ArrayList ();
   AtpTypeInfo info = new AtpTypeInfo ();
   info.setId (ANY_TERM);
   info.setDesc ("Any of the possible terms");
   info.setName ("Any");
   list.add (info);
   // TODO: get other possible terms from the service
   return list;
  }

  @Override
  public AtpTypeInfo getTerm ()
  {
   return term;
  }

  @Override
  public void setTerm (AtpTypeInfo term)
  {
   this.term = term;
  }

  private List<KSManageCourseFormatFunction.CourseFormat> formats =
   new ArrayList ();

  @Override
  public void addFormat (KSManageCourseFormatFunction.CourseFormat format)
  {
   formats.add (format);
  }

  @Override
  public List<KSManageCourseFormatFunction.CourseFormat> getFormats ()
  {
   return formats;
  }

  @Override
  public void removeFormat (int sequence)
  {
   formats.remove (sequence);
  }

  @Override
  public void refresh ()
  {
   throw new UnsupportedOperationException ("Not supported yet.");
  }

  @Override
  public void save ()
  {
   throw new UnsupportedOperationException ("Not supported yet.");
  }

  @Override
  public List<ValidationResultInfo> validate ()
  {
   List<ValidationResultInfo> list = new ArrayList ();
   for (KSManageCourseFormatFunction.CourseFormat format : formats)
   {
    list.addAll (format.validate ());
   }
   return list;
  }

 }

 public class CourseFormat
  implements KSManageCourseFormatFunction.CourseFormat
 {

  private CluInfo formatClu;
  private int sequence;

  public CourseFormat ()
  {
   this (new CluInfo ());
  }

  public CourseFormat (CluInfo formatClu)
  {
   this.formatClu = formatClu;
  }

  private List<KSManageCourseFormatFunction.CourseActivity> activities =
   new ArrayList ();

  @Override
  public void addActivity (KSManageCourseFormatFunction.CourseActivity activity)
  {
   activities.add (activity);
  }

  @Override
  public List<KSManageCourseFormatFunction.CourseActivity> getActivities ()
  {
   return activities;
  }

  @Override
  public int getSequence ()
  {
   return sequence;
  }

  @Override
  public void removeActivity (int sequence)
  {
   activities.remove (sequence);
  }

  @Override
  public List<ValidationResultInfo> validate ()
  {
   List<ValidationResultInfo> list = new ArrayList ();
   for (KSManageCourseFormatFunction.CourseActivity activity : activities)
   {
    list.addAll (activity.validate ());
   }
   return list;
  }

 }

 public class CourseActivity implements
  KSManageCourseFormatFunction.CourseActivity
 {

  private CluInfo activityClu;

  public CourseActivity ()
  {
   this (new CluInfo ());
  }

  public CourseActivity (CluInfo activityClu)
  {
   this.activityClu = activityClu;
  }

  private int contactHours;

  @Override
  public int getContactHours ()
  {
   return contactHours;
  }

  private int sequence;

  @Override
  public int getSequence ()
  {
   return sequence;
  }

  private LuTypeInfo type;

  @Override
  public LuTypeInfo getType ()
  {
   return type;
  }

  @Override
  public void setContactHours (int hours)
  {
   this.contactHours = hours;
  }

  @Override
  public void setType (LuTypeInfo type)
  {
   this.type = type;
  }

  @Override
  public List<ValidationResultInfo> validate ()
  {
   List<ValidationResultInfo> list = new ArrayList ();
   return list;
  }

 }
}
