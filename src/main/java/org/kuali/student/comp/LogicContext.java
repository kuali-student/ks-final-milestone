/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

/**
 *
 * @author nwright
 */
public class LogicContext
{

 public class Configurer
 {

  public void set (LogicContext context)
  {
   context.setCourseActivityLogic (new CourseActivityLogic ());
   context.setCourseFormatLogic (new CourseFormatLogic ());
   context.setCreditCourseLogic (new CreditCourseLogic ());
   context.setCreditCourseProposalLogic (new CreditCourseProposalLogic ());
   context.setNewCreditCourseProposalLogic (new NewCreditCourseProposalLogic ());
   context.setModifyCreditCourseProposalLogic (new ModifyCreditCourseProposalLogic ());
  }

 }
 private static LogicContext context;

 public static LogicContext get ()
 {
  if (context != null)
  {
   return context;
  }
  LogicContext ctx = new LogicContext ();

  return context;
 }

 private CreditCourseLogic creditCourseLogic;

 public CreditCourseLogic getCreditCourseLogic ()
 {
  return creditCourseLogic;
 }

 public void setCreditCourseLogic (CreditCourseLogic logic)
 {
  this.creditCourseLogic = logic;
 }

 private CourseActivityLogic courseActivityLogic;

 public CourseActivityLogic getCourseActivityLogic ()
 {
  return courseActivityLogic;
 }

 public void setCourseActivityLogic (CourseActivityLogic logic)
 {
  this.courseActivityLogic = logic;
 }

 private NewCreditCourseProposalLogic newCreditCourseProposalLogic;

 public NewCreditCourseProposalLogic getNewCreditCourseProposalLogic ()
 {
  return newCreditCourseProposalLogic;
 }

 public void setNewCreditCourseProposalLogic (NewCreditCourseProposalLogic logic)
 {
  this.newCreditCourseProposalLogic = logic;
 }

 private CreditCourseProposalLogic creditCourseProposalLogic;

 public CreditCourseProposalLogic getCreditCourseProposalLogic ()
 {
  return creditCourseProposalLogic;
 }

 public void setCreditCourseProposalLogic (CreditCourseProposalLogic logic)
 {
  this.creditCourseProposalLogic = logic;
 }

 private CourseFormatLogic courseFormatLogic;

 public CourseFormatLogic getCourseFormatLogic ()
 {
  return courseFormatLogic;
 }

 public void setCourseFormatLogic (CourseFormatLogic logic)
 {
  this.courseFormatLogic = logic;
 }

 private ModifyCreditCourseProposalLogic ModifyCreditCourseProposalLogic =
  new ModifyCreditCourseProposalLogic ();

 public ModifyCreditCourseProposalLogic getModifyCreditCourseProposalLogic ()
 {
  return ModifyCreditCourseProposalLogic;
 }

 public void setModifyCreditCourseProposalLogic (ModifyCreditCourseProposalLogic logic)
 {
  this.ModifyCreditCourseProposalLogic = logic;
 }

}
