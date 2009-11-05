/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.comp;

import javax.servlet.http.HttpServletRequest;
import org.kuali.rice.kim.service.AuthenticationService;


/**
 *
 * @author nwright
 */
public class MockAuthenticationService implements AuthenticationService
{

 
 @Override
 public String getPrincipalName (HttpServletRequest request)
 {
  return request.getRemoteUser ();
 }

}
