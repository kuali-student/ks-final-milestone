/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSGetAuthenticatedUser;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.service.dto.PrincipalId;
import javax.servlet.http.HttpServletRequest;
import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.core.exceptions.OperationFailedException;


/**
 *
 * @author nwright
 */
public class GetAuthenticatedUserFromHttpRequest
 implements KSGetAuthenticatedUser
{

 private KSContext context;

 public GetAuthenticatedUserFromHttpRequest (KSContext context)
 {
  this.context = context;
 }

 @Override
 public PrincipalId getAuthenticatedUser ()
  throws OperationFailedException
 {
  AuthenticationService authService = new ContextHelper (context).getAuthenticationService ();
  HttpServletRequest request = new ContextHelper (context).getRequest ();
  String id = authService.getPrincipalName (request);
  return new PrincipalId (id);
 }

}
