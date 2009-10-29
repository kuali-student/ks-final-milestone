/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class ServiceContext
{

 public class Configurer
 {

  public void set (ServiceContext context)
  {
  context.setLuService (new MockLuService ());
  context.setAtpService (new MockAtpService ());
  context.setAuthenticationService (new MockAuthenticationService ());
  context.setProposalService (new MockProposalService ());
  }

 }
 private static ServiceContext context;

 public static ServiceContext get ()
 {
  if (context != null)
  {
   return context;
  }
  ServiceContext ctx = new ServiceContext ();

  return context;
 }

 public ProposalService getProposalService ()
 {
  return proposalService;
 }

 public LuService getLuService ()
 {
  return luService;
 }

 public AuthenticationService getAuthenticationService ()
 {
  return authenticationService;
 }

 public String getAuthenticatedUser ()
  throws OperationFailedException
 {
  // TODO: get this some how.
  return "Mock-User";
 }

 public AtpService getAtpService ()
 {
  return atpService;
 }

 private AtpService atpService;

 public void setAtpService (AtpService service)
 {
  this.atpService = service;
 }

 private LuService luService;

 public void setLuService (LuService service)
 {
  this.luService = service;
 }

 private ProposalService proposalService;

 public void setProposalService (ProposalService service)
 {
  this.proposalService = service;
 }

 private AuthenticationService authenticationService;

 public void setAuthenticationService (
  AuthenticationService authenticationService)
 {
  this.authenticationService = authenticationService;
 }

 

}
