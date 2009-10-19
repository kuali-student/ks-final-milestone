/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.comp.infc.KSUIField;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.comp.infc.KSUIComponent;

/**
 *
 * @author nwright
 */
public abstract class AbstractGwtUIIField
 implements KSUIField
{

 private Panel panel = new HorizontalPanel ();
 private KSLabel label = new KSLabel ();
 private KSLabel required = new KSLabel ();
 private Widget widget;
 private KSLabel error = new KSLabel ();
 private String help;

 public AbstractGwtUIIField (Widget widget)
 {
  super ();
  this.widget = widget;
  panel.add (label);
  panel.add (required);
  required.setText ("");
  panel.add (widget);
  panel.add (error);
 }

 protected Widget getWidget ()
 {
  return widget;
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
 public Panel getImpl ()
 {
  return panel;
 }

 @Override
 public void setImpl (Object impl)
 {
  this.panel = (Panel) impl;
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
  return help;
 }

 @Override
 public void setHelp (String help)
 {
  this.help = help;
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
