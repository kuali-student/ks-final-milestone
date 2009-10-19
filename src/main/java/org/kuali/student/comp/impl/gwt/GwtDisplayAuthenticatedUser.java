/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.comp.impl.ContextHelper;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayAuthenticatedUser;
import org.kuali.student.comp.infc.KSUIComponent.Callback;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.service.dto.PrincipalId;

/**
 *
 * @author nwright
 */
public class GwtDisplayAuthenticatedUser extends AbstractGwtUIIField
 implements KSDisplayAuthenticatedUser
{

 private KSContext context;

 public GwtDisplayAuthenticatedUser (KSContext context)
 {
  super (new KSLabel ());
  this.context = context;
  this.setLabel ("Hello");
 }

 private KSLabel getKSLabel ()
 {
  return (KSLabel) getWidget ();
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  this.callback = callback;
  try
  {
   PrincipalId id =
    new ContextHelper (context).getAuthenticatedUser ();
   this.setValue (id.getId ());
  }
  catch (OperationFailedException ex)
  {
   new ContextHelper (context).handleUnrecoverableException (this.getClass ().
    getName (), ex);
   return;
  }
 }

 @Override
 public boolean getReadOnly ()
 {
  return true;
 }

 @Override
 public void setReadOnly (boolean readonly)
 {
  // ignore always read-only
 }

 @Override
 public String getValue ()
 {
  return getKSLabel ().getText ();
 }

 @Override
 public void setValue (Object value)
 {
  getKSLabel ().setText (value.toString ());
 }

}
