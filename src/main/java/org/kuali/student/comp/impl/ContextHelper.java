/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSGetAuthenticatedUser;
import org.kuali.student.service.dto.PrincipalId;
import javax.servlet.http.HttpServletRequest;
import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.comp.infc.KSUnrecoverableExceptionHandler;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class ContextHelper
{

 private KSContext context;

 public ContextHelper (KSContext context)
 {
  this.context = context;
 }

 public ProposalService getProposalService ()
 {
  return (ProposalService) context.getInstance (ProposalService.class);
 }

 public LuService getLuService ()
 {
  return (LuService) context.getInstance (LuService.class);
 }

 public AuthenticationService getAuthenticationService ()
 {
  return (AuthenticationService) context.getInstance (AuthenticationService.class);
 }

 public HttpServletRequest getRequest ()
 {
  return (HttpServletRequest) context.getInstance (HttpServletRequest.class);
 }

 public PrincipalId getAuthenticatedUser ()
  throws OperationFailedException
 {
  KSGetAuthenticatedUser func =
   (KSGetAuthenticatedUser) context.getInstance (KSGetAuthenticatedUser.class);
  return func.getAuthenticatedUser ();
 }

 public KSUnrecoverableExceptionHandler getUnrecoverableExceptionHandler ()
 {
  return (KSUnrecoverableExceptionHandler) context.getInstance (KSUnrecoverableExceptionHandler.class);
 }

 public void handleUnrecoverableException (String source, Exception ex)
 {
  getUnrecoverableExceptionHandler ().handle (source, ex);
 }
}
