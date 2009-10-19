/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.service.impl.mock;

import javax.servlet.http.HttpServletRequest;
import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.comp.infc.KSContext;

/**
 *
 * @author nwright
 */
public class MockAuthenticationService implements AuthenticationService
{

 private KSContext context;
 
 public MockAuthenticationService (KSContext context)
 {
  this.context = context;
 }
 @Override
 public String getPrincipalName (HttpServletRequest request)
 {
  return request.getRemoteUser ();
 }

}
