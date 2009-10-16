/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayAuthenticatedUser;
import org.kuali.student.comp.*;
import org.kuali.student.core.exceptions.OperationFailedException;

/**
 *
 * @author nwright
 */
public class SwingDisplayAuthenticatedUser implements KSDisplayAuthenticatedUser
{
 private KSContext context;

 public SwingDisplayAuthenticatedUser (KSContext context)
 {
  this.context = context;
 }

 @Override
 public void execute ()
  throws OperationFailedException
 {
  
 }


}
