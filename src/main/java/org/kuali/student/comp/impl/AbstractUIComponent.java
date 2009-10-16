/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSUIComponent;


/**
 *
 * @author nwright
 */
public abstract class AbstractUIComponent <I> implements KSUIComponent
{

 private I impl;

 public AbstractUIComponent (I impl)
 {
  this.impl = impl;
 }

 @Override
 public I getImpl ()
 {
  return impl;
 }
}
