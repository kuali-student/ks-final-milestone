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
public class GetAuthenticatedUserByProxy
 implements KSGetAuthenticatedUser
{

 private String proxyId;

 public GetAuthenticatedUserByProxy (String proxyId)
 {
  this.proxyId = proxyId;
 }

 @Override
 public PrincipalId getAuthenticatedUser ()
  throws OperationFailedException
 {
  return new PrincipalId (proxyId);
 }
}
