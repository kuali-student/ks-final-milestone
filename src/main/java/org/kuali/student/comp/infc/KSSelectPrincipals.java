/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import java.util.List;
import org.kuali.rice.kew.identity.PrincipalId;
import org.kuali.student.core.exceptions.OperationFailedException;


/**
 *
 * @author nwright
 */
public interface KSSelectPrincipals
{

 public void setMaxToSelect (int max);

 public void setPrincipals (List<PrincipalId> master);

 public List<PrincipalId>  selectPrincipals ()
  throws OperationFailedException;

}
