/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;


import org.kuali.student.comp.infc.KSGetAuthenticatedUser;
import org.kuali.student.service.dto.PrincipalId;
import org.kuali.student.core.exceptions.OperationFailedException;


/**
 *
 * @author nwright
 */
public class GetAuthenticatedUserHardCoded
 implements KSGetAuthenticatedUser
{

 @Override
 public PrincipalId getAuthenticatedUser ()
  throws OperationFailedException
 {
  return new PrincipalId ("Hard.Coded.User");
 }
}
