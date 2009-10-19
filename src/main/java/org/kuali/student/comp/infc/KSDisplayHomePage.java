/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.comp.infc;



/**
 *
 * @author nwright
 */
public interface KSDisplayHomePage extends KSUIContainer
{
 public void setDisplayAuthenticatedUser (KSDisplayAuthenticatedUser displayUser);
 public void setDisplaySelectFromUserTaskList (KSDisplaySelectFromUserTaskList displayTaskList);
 public void setDisplaySelectProposalTypesUserCanStart (KSDisplaySelectProposalTypesUserCanStart proposalTypes);

}
