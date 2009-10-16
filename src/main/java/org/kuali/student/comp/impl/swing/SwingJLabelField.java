/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.impl.AbstractUIField;
import org.kuali.student.comp.infc.KSUIField;
import javax.swing.JLabel;
import org.kuali.student.comp.infc.KSUIComponent;

/**
 *
 * @author nwright
 */
public class SwingJLabelField extends AbstractUIField <JLabel, String>
 implements KSUIField
{

 public SwingJLabelField (JLabel label)
 {
  super (label);
  super.setLabel (label.getText ());
 }

 protected JLabel getJLabel ()
 {
  return (JLabel) getImpl ();
 }

 @Override
 public String getLabel ()
 {
  return super.getLabel ();
 }

 @Override
 public void setLabel (String label)
 {
  super.setLabel (label);
  getJLabel ().setText (label);
 }

 @Override
 public void display (final KSUIComponent.Callback callback)
 {

  getJLabel ().setVisible (true);
  callback.onDone ();
 }

}
