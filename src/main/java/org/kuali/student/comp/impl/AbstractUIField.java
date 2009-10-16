/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSUIField;

/**
 *
 * @author nwright
 */
public abstract class AbstractUIField<I, V> extends AbstractUIComponent
 implements KSUIField
{

 public AbstractUIField (I impl)
 {
  super (impl);
 }

 private String error = null;

 @Override
 public String getError ()
 {
  return error;
 }

 private String label = null;

 @Override
 public String getLabel ()
 {
  return label;
 }

 private V value = null;

 @Override
 public V getValue ()
 {
  return value;
 }

 @Override
 public void setError (String error)
 {
  this.error = error;
 }

 @Override
 public void setLabel (String label)
 {
  this.label = label;
 }

 @Override
 public void setValue (Object value)
 {
  this.value = (V) value;
 }

 private String help;

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

}
