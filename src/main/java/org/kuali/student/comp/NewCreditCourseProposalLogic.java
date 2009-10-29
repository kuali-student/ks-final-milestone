/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 *
 * @author nwright
 */
public class NewCreditCourseProposalLogic
{

 public enum OptionForCreating
 {

  FROM_BLANK,
  FROM_TEMPLATE,
  BY_COPYING_EXISTING_COURSE,
  BY_COPYING_EXISTING_PROPOSAL;
 }

 public List<OptionForCreating> getPossibleOptionsForCreating ()
 {
  List<OptionForCreating> list = new ArrayList ();
  list.add (OptionForCreating.FROM_BLANK);
  list.add (OptionForCreating.FROM_TEMPLATE);
  // TODO: implement copying from from an existing course and proposal
  //list.add (OptionForCreating.BY_COPYING_EXISTING_COURSE);
  //list.add (OptionForCreating.BY_COPYING_EXISTING_PROPOSAL);
  return list;
 }

 protected Defaulter defaulter = new Defaulter ();

 public class Defaulter
 {

  public static final String CREATE_CREDIT_COURSE_TYPE =
   "kuali.proposal.type.course.create";

  public void apply (NewCreditCourseProposal newProposal)
  {
   newProposal.proposal.setType (CREATE_CREDIT_COURSE_TYPE);
   LogicContext.get ().getCreditCourseProposalLogic ().getDefaulter ().apply (newProposal);
  }

 }

 public void setDefaulter (Defaulter defaulter)
 {
  this.defaulter = defaulter;
 }

 public Defaulter getDefaulter ()
 {
  return this.defaulter;
 }

 public NewCreditCourseProposal createEmpty ()
 {
  NewCreditCourseProposal newProposal =
   new NewCreditCourseProposal (new CreditCourse (new CluInfo ()), new ProposalInfo ());
  // TODO: worry if we should invoke a defaulter here too
  return newProposal;
 }

 

 public NewCreditCourseProposal createFromTemplate (CreditCourseTemplate template)
 {
  NewCreditCourseProposal newProposal = createEmpty ();
  defaulter.apply (newProposal);
  LogicContext.get ().getCreditCourseLogic ().getToNewFromTemplateCopier ().copy (template, newProposal.course);
  return newProposal;
 }

 public class Copier
 {

  private CreditCourseProposalLogic.Copier courseProposalCopier;

  public CreditCourseProposalLogic.Copier getCourseProposalCopier ()
  {
   return courseProposalCopier;
  }

  public void setCourseProposalCopier (CreditCourseProposalLogic.Copier copier)
  {
   this.courseProposalCopier = copier;
  }

  public void copy (NewCreditCourseProposal source,
                    NewCreditCourseProposal destination)
  {

   // nothing to copy at the NEW proposal level
   // so just copy the course proposal
   courseProposalCopier.copy (source, destination);

  }

 }
 private Copier fromTemplateCopier = new Copier ();

 public Copier getFromTemplateCopier ()
 {

  fromTemplateCopier.setCourseProposalCopier (LogicContext.get ().
   getCreditCourseProposalLogic ().getToNewFromTemplateCopier ());
  return fromTemplateCopier;
 }

 public void setFromTemplateCopier (Copier fromTemplateCopier)
 {
  this.fromTemplateCopier = fromTemplateCopier;
 }

  private Copier toNewFromExistingCourseCopier = new Copier ();

 public Copier getToNewFromExistingCourseCopier ()
 {
  toNewFromExistingCourseCopier.setCourseProposalCopier (LogicContext.get ().
   getCreditCourseProposalLogic ().getToNewFromExistingCourseCopier ());
  return toNewFromExistingCourseCopier;
 }

 public void setToNewFromExistingCourseCopier (Copier copier)
 {
  this.toNewFromExistingCourseCopier = copier;
 }

 private Copier toNewFromExistingProposalCopier = new Copier ();

 public Copier getToNewFromExistingProposalCopier ()
 {
  toNewFromExistingProposalCopier.setCourseProposalCopier (LogicContext.get ().
   getCreditCourseProposalLogic ().getToNewFromExistingProposalCopier ());
  return toNewFromExistingProposalCopier;
 }

 public void setToNewFromExistingProposalCopier (Copier copier)
 {
  this.toNewFromExistingProposalCopier = copier;
 }

 private Copier modifyExistingCourseCopier = new Copier ();

 public Copier getModifyExistingCourseCopier ()
 {
  modifyExistingCourseCopier.setCourseProposalCopier (LogicContext.get ().
   getCreditCourseProposalLogic ().getModifyExistingCourseCopier ());
  return modifyExistingCourseCopier;
 }

 public void setModifyExistingCourseCopier (Copier copier)
 {
  this.modifyExistingCourseCopier = copier;
 }


 public NewCreditCourseProposal createFromRequiredFields (
  NewCreditCourseProposal.RequiredFields requiredFields)
 {
  NewCreditCourseProposal newProposal = createEmpty ();
  newProposal.setRequiredFields (requiredFields);
  getDefaulter ().apply (newProposal);

  return newProposal;
 }

}
