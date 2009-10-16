/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;


import org.kuali.student.service.dto.PrincipalId;
import org.kuali.student.core.exceptions.OperationFailedException;


/**
 *
 * @author nwright
 */
public interface KSGetAuthenticatedUser
{

 public PrincipalId getAuthenticatedUser ()
  throws OperationFailedException;
}
