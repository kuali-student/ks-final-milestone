/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

/**
 *
 * @author nwright
 */
public interface KSUIField<I, V> extends KSUIComponent
{

 @Override
 public I getImpl ();

 public void setLabel (String label);

 public String getLabel ();

 public void setError (String error);

 public String getError ();

 public void setValue (V value);

 public V getValue ();

 public void setHelp (String help);

 public String getHelp ();

}
