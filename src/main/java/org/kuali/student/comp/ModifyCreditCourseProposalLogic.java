/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.CluInfo;


/**
 *
 * @author nwright
 */
public class ModifyCreditCourseProposalLogic
{
 

 private Defaulter defaulter = new Defaulter ();

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
  public static final String MODIFY_CREDIT_COURSE_TYPE = "kuali.proposal.type.course.modify";
  
  public void apply (ModifyCreditCourseProposal modifyProposal)
  {
   LogicContext.get ().getCreditCourseProposalLogic ().getDefaulter().apply (modifyProposal);
   modifyProposal.proposal.setType (MODIFY_CREDIT_COURSE_TYPE);

  }

 }

 
}
