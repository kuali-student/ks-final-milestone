/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.core.proposal.dto.ProposalInfo;

/**
 *
 * @author nwright
 */
public class ModifyCreditCourseProposal extends CreditCourseProposal
{

 public ModifyCreditCourseProposal (CreditCourse course, ProposalInfo proposal)
 {
  super (course, proposal);
 }

 private CreditCourse originalCourse;

 public CreditCourse getOriginalCourse ()
 {
  return originalCourse;
 }

 public void setOriginalCourse (CreditCourse originalCourse)
 {
  this.originalCourse = originalCourse;
 }


}
