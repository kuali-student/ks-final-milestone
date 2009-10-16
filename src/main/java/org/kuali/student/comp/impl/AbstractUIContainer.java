/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSUIContainer;
import org.kuali.student.comp.infc.KSUIComponent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public abstract class AbstractUIContainer<I> extends AbstractUIComponent
 implements KSUIContainer
{

 public AbstractUIContainer (I impl)
 {
  super (impl);
 }

 private List<KSUIComponent> components = new ArrayList ();

 @Override
 public void add (KSUIComponent component)
 {
  components.add (component);
 }

 @Override
 public void clear ()
 {
  components.clear ();
 }

 @Override
 public List<KSUIComponent> getComponents ()
 {
  return components;
 }

 @Override
 public boolean remove (KSUIComponent component)
 {
  return components.remove (component);
 }

}
