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
public class CreditCourseProposal 
{
 protected CreditCourse course;
 protected ProposalInfo proposal;

 public CreditCourseProposal (CreditCourse course, ProposalInfo proposal)
 {
  this.course = course;
  this.proposal = proposal;
 }

  public String getProposalTitle ()
 {
  return proposal.getName ();
 }

 public void setProposalTitle (String title)
 {
  proposal.setName (title);
 }

  public String getRationale ()
 {
  return proposal.getRationale ();
 }

 public void setRationale (String rationale)
 {
  proposal.setRationale (rationale);
 }

 public class RequiredFields
 {

  private String title;

  public String getTitle ()
  {
   return title;
  }

  public void setTitle (String title)
  {
   this.title = title;
  }

  private String deptId;

  public String getDepartment ()
  {
   return deptId;
  }

  public void setDepartment (String deptId)
  {
   this.deptId = deptId;
  }

  private String proposerId;

  public String getProposer ()
  {
   return proposerId;
  }

  public void setProposer (String proposerId)
  {
   this.proposerId = proposerId;
  }

 }

 public void setRequiredFields (RequiredFields fields)
 {
  // set the title on both course and proposal for a new course
  this.course.setCourseTitle (fields.getTitle ());
  this.proposal.setName (fields.getTitle ());
  List<String> proposers = new ArrayList ();
  proposers.add (fields.getProposer ());
  this.proposal.setProposerPerson (proposers);
  this.course.setDepartment (fields.getDepartment ());
 }

}
