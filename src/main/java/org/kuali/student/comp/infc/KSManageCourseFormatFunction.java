/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import java.util.List;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

/**
 *
 * @author nwright
 */
public interface KSManageCourseFormatFunction
{

 public MainCourse getMainCourse ();

 public void setMainCourse (MainCourse mainCourse);

 public interface MainCourse
 {

  public List<AtpDurationTypeInfo> getPossibleDurations ();

  public AtpDurationTypeInfo getDuration ();

  public void setDuration (AtpDurationTypeInfo duration);

  public List<AtpTypeInfo> getPossibleTerms ();

  public AtpTypeInfo getTerm ();

  public void setTerm (AtpTypeInfo term);

  public void addFormat (CourseFormat format);

  public void removeFormat (int sequence);

  public List<CourseFormat> getFormats ();

  public List<ValidationResultInfo> validate ();

  public void save ();

  public void refresh ();

 }

 public interface CourseFormat
 {

  public int getSequence ();

  public void addActivity (CourseActivity activity);

  public void removeActivity (int sequence);

  public List<CourseActivity> getActivities ();

  public List<ValidationResultInfo> validate ();

 }

 public interface CourseActivity
 {

  public int getSequence ();

  public LuTypeInfo getType ();

  public void setType (LuTypeInfo type);

  public int getContactHours ();

  public void setContactHours (int hours);

  public List<ValidationResultInfo> validate ();

 }
}
