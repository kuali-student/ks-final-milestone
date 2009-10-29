/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

/**
 *
 * @author nwright
 */
public class CourseFormatLogic
{

 private Copier copier = new Copier ();

 public Copier getCopier ()
 {
  return copier;
 }

 public void setCopier (Copier copier)
 {
  this.copier = copier;
 }

 public class Copier
 {

  private CourseActivityLogic.Copier activityCopier;

  public void setActivityCopier (
   CourseActivityLogic.Copier activityCopier)
  {
   this.activityCopier = activityCopier;
  }

  public CourseActivityLogic.Copier getActivityCopier ()
  {
   return activityCopier;
  }

  public void copy (CourseFormat source, CourseFormat destination)
  {
   destination.setSequence (source.getSequence ());
   for (CourseActivity sourceActivity : source.getCourseActivities ())
   {
    CourseActivity destActivity = destination.addCourseActivity ();
    activityCopier.copy (sourceActivity, destActivity);
   }
  }

 }
 private Copier toNewFromTemplateCopier = new Copier ();

 public Copier getToNewFromTemplateCopier ()
 {
  toNewFromTemplateCopier.setActivityCopier (LogicContext.get ().
   getCourseActivityLogic ().getToNewFromTemplateCopier ());
  return toNewFromTemplateCopier;
 }

 public void setToNewFromTemplateCopier (Copier copier)
 {
  this.toNewFromTemplateCopier = copier;
 }

 private Copier toNewFromExistingCourseCopier = new Copier ();

 public Copier getToNewFromExistingCourseCopier ()
 {
  toNewFromExistingCourseCopier.setActivityCopier (LogicContext.get ().
   getCourseActivityLogic ().getToNewFromExistingCourseCopier ());
  return toNewFromExistingCourseCopier;
 }

 public void setToNewFromExistingCourseCopier (Copier copier)
 {
  this.toNewFromExistingCourseCopier = copier;
 }

 private Copier toNewFromExistingProposalCopier = new Copier ();

 public Copier getToNewFromExistingProposalCopier ()
 {
  toNewFromExistingProposalCopier.setActivityCopier (LogicContext.get ().
   getCourseActivityLogic ().getToNewFromExistingProposalCopier ());
  return toNewFromExistingProposalCopier;
 }

 public void setToNewFromExistingProposalCopier (Copier copier)
 {
  this.toNewFromExistingProposalCopier = copier;
 }

 private Copier modifyExistingCourseCopier = new Copier ();

 public Copier getModifyExistingCourseCopier ()
 {
  modifyExistingCourseCopier.setActivityCopier (LogicContext.get ().
   getCourseActivityLogic ().getModifyExistingCourseCopier ());
  return modifyExistingCourseCopier;
 }

 public void setModifyExistingCourseCopier (Copier copier)
 {
  this.modifyExistingCourseCopier = copier;
 }

 public class Defaulter
 {

  public void apply (CourseFormat format)
  {
   format.setSequence (format.getParentCourse ().getCourseFormats ().size ());
   format.getCluInfo ().setState (format.getParentCourse ().getCluInfo ().
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
