/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.impl.mock;

import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.comp.impl.GetAuthenticatedUserFromHttpRequest;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSGetAuthenticatedUser;
import org.kuali.student.comp.infc.KSContextConfigurer;
import javax.servlet.http.HttpServletRequest;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class MockContextConfigurer implements KSContextConfigurer
{

 @Override
 public void configure (KSContext context)
 {
  context.register (AuthenticationService.class, new MockAuthenticationService (context));
  context.register (ProposalService.class, new MockProposalService (context));
  context.register (HttpServletRequest.class, new MockHttpServletRequest ());
  context.register (LuService.class, new MockLuService (context));
  context.register (KSGetAuthenticatedUser.class, new GetAuthenticatedUserFromHttpRequest (context));

 }

}
