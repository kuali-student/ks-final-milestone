/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import javax.swing.Box;
import javax.swing.BoxLayout;
import org.kuali.student.comp.infc.KSUIField;
import javax.swing.JLabel;
import javax.swing.JComponent;
import org.kuali.student.comp.infc.KSUIComponent;

/**
 *
 * @author nwright
 */
public abstract class AbstractSwingUIIField
 implements KSUIField
{

 private Box box = new Box (BoxLayout.X_AXIS);
 private JLabel label = new JLabel ();
 private JLabel required = new JLabel ();
 private JComponent component;
 private JLabel error = new JLabel ();

 public AbstractSwingUIIField (JComponent component)
 {
  super ();
  this.component = component;
  box.add (label);
  box.add (required);
  required.setText ("");
  box.add (component);
  box.add (error);
 }

 protected JComponent getComponent ()
 {
  return component;
 }
 
 private KSUIComponent parent;

 @Override
 public KSUIComponent getParent ()
 {
  return parent;
 }

 @Override
 public void setParent (KSUIComponent parent)
 {
  this.parent = parent;
 }

 @Override
 public Box getImpl ()
 {
  return box;
 }

 @Override
 public void setImpl (Object impl)
 {
  this.box = (Box) impl;
 }

 @Override
 public String getLabel ()
 {
  return label.getText ();
 }

 @Override
 public void setLabel (String label)
 {
  this.label.setText (label);
 }

 @Override
 public String getError ()
 {
  return error.getText ();
 }

 @Override
 public void setError (String error)
 {
  this.error.setText (error);
 }

 @Override
 public String getHelp ()
 {
  return component.getToolTipText ();
 }

 @Override
 public void setHelp (String help)
 {
  component.setToolTipText (help);
 }

 @Override
 public boolean getRequired ()
 {
  return required.getText ().equals ("*");
 }

 @Override
 public void setRequired (boolean required)
 {
  this.required.setText ("*");
 }

 @Override
 public abstract boolean getReadOnly ();

 @Override
 public abstract void setReadOnly (boolean readonly);

 @Override
 public abstract Object getValue ();

 @Override
 public abstract void setValue (Object value);

}
