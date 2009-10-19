/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.infc.KSUIContainer;
import org.kuali.student.comp.infc.KSUIComponent;
import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nwright
 */
public abstract class AbstractSwingUIContainer
 implements KSUIContainer
{

 private Container container;
 
 public AbstractSwingUIContainer (Container container)
 {
  super ();
  this.container = container;
 }

 protected Container getContainer ()
 {
  return container;
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

 private List<KSUIComponent> components = new ArrayList ();

 @Override
 public void add (KSUIComponent component)
 {
  components.add (component);
  component.setParent (this);
  container.add ((Component) component.getImpl ());
 }

 @Override
 public void clear ()
 {
  for (KSUIComponent component : components)
  {
   component.setParent (null);
  }
  components.clear ();
  container.removeAll ();
 }

 @Override
 public List<KSUIComponent> getComponents ()
 {
  return components;
 }

 @Override
 public boolean remove (KSUIComponent component)
 {
  boolean removed = components.remove (component);
  for (Component comp : container.getComponents ())
  {
   if (comp == component.getImpl ())
   {
    container.remove (comp);
    assert removed;  // implementation not in sync with KS structure!
    component.setParent (null);
    return true;
   }
  }
  assert  ! removed;  // implementation not in sync with KS structure!
  return false;
 }

 @Override
 public Container getImpl ()
 {
  return container;
 }

 @Override
 public void setImpl (Object impl)
 {
  container = (Container) impl;
 }
}
