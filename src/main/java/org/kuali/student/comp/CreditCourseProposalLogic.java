/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.proposal.dto.ProposalInfo;

/**
 *
 * @author nwright
 */
public class CreditCourseProposalLogic
{

 public enum OptionForCreating
 {

  FROM_BLANK,
  FROM_TEMPLATE,
  BY_COPYING_EXISTING_COURSE,
  BY_COPYING_EXISTING_PROPOSAL;
 }

 public List<OptionForCreating> getSupportedOptionsForCreating ()
 {
  List<OptionForCreating> list = new ArrayList ();
  list.add (OptionForCreating.FROM_BLANK);
  list.add (OptionForCreating.FROM_TEMPLATE);
  list.add (OptionForCreating.BY_COPYING_EXISTING_COURSE);
  list.add (OptionForCreating.BY_COPYING_EXISTING_PROPOSAL);
  return list;
 }

 private Defaulter defaulter =
  new Defaulter ();

 public Defaulter getDefaulter ()
 {
  return defaulter;
 }

 public void setDefaulter (Defaulter defaulter)
 {
  this.defaulter = defaulter;
 }

 public class Defaulter
 {

  public static final String REFERENCE_TYPE_CLU = "kuali.referenceType.CLU";
  public static final String DRAFT_PUBLIC_STATE = "draft.public";

  public void apply (CreditCourseProposal newProposal)
  {
   LogicContext.get ().getCreditCourseLogic ().getDefaulter ().apply (newProposal.course);
   newProposal.proposal.setProposalReferenceType (REFERENCE_TYPE_CLU);
   newProposal.proposal.setState (DRAFT_PUBLIC_STATE);
  }

 }

 public CreditCourseProposal create (CreditCourseProposal prop)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  CreditCourse cc =
   LogicContext.get ().getCreditCourseLogic ().create (prop.course);

  List<String> cluIds = new ArrayList (1);
  cluIds.add (cc.getCluInfo ().getId ());
  prop.proposal.setProposalReference (cluIds);
  ProposalInfo proposal =
   ServiceContext.get ().getProposalService ().createProposal (prop.proposal.
   getType (), prop.proposal);
  CreditCourseProposal newProp = new CreditCourseProposal (cc, proposal);
  return newProp;
 }

 public class Copier
 {

  private CreditCourseLogic.Copier courseCopier;

  public void setCourseCopier (CreditCourseLogic.Copier copier)
  {
   this.courseCopier = copier;
  }

  public CreditCourseLogic.Copier getCourseCopier ()
  {
   return courseCopier;
  }

  public void copy (CreditCourseProposal source,
                    CreditCourseProposal destination)
  {
   destination.setProposalTitle (source.getProposalTitle ());
   destination.setRationale (source.getRationale ());
   courseCopier.copy (source.course, destination.course);
  }

 }
 private Copier copier = new Copier ();

 public Copier getCopier ()
 {
  return copier;
 }

 public void setCopier (Copier copier)
 {
  this.copier = copier;
 }

 private Copier toNewFromTemplateCopier = new Copier ();

 public Copier getToNewFromTemplateCopier ()
 {
  toNewFromTemplateCopier.setCourseCopier (LogicContext.get ().
   getCreditCourseLogic ().getToNewFromTemplateCopier ());
  return toNewFromTemplateCopier;
 }

 public void setToNewFromTemplateCopier (Copier copier)
 {
  this.toNewFromTemplateCopier = copier;
 }

 private Copier toNewFromExistingCourseCopier = new Copier ();

 public Copier getToNewFromExistingCourseCopier ()
 {
  toNewFromExistingCourseCopier.setCourseCopier (LogicContext.get ().
   getCreditCourseLogic ().getToNewFromExistingCourseCopier ());
  return toNewFromExistingCourseCopier;
 }

 public void setToNewFromExistingCourseCopier (Copier copier)
 {
  this.toNewFromExistingCourseCopier = copier;
 }

 private Copier toNewFromExistingProposalCopier = new Copier ();

 public Copier getToNewFromExistingProposalCopier ()
 {
  toNewFromExistingProposalCopier.setCourseCopier (LogicContext.get ().
   getCreditCourseLogic ().getToNewFromExistingProposalCopier ());
  return toNewFromExistingProposalCopier;
 }

 public void setToNewFromExistingProposalCopier (Copier copier)
 {
  this.toNewFromExistingProposalCopier = copier;
 }

 private Copier modifyExistingCourseCopier = new Copier ();

 public Copier getModifyExistingCourseCopier ()
 {
  modifyExistingCourseCopier.setCourseCopier (LogicContext.get ().
   getCreditCourseLogic ().getModifyExistingCourseCopier ());
  return modifyExistingCourseCopier;
 }

 public void setModifyExistingCourseCopier (Copier copier)
 {
  this.modifyExistingCourseCopier = copier;
 }



}
