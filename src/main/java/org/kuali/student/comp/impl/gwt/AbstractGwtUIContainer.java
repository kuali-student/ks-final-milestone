/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;


import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.comp.infc.KSUIContainer;
import org.kuali.student.comp.infc.KSUIComponent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nwright
 */
public abstract class AbstractGwtUIContainer
 implements KSUIContainer
{

 private HasWidgets container;
 
 public AbstractGwtUIContainer (HasWidgets container)
 {
  super ();
  this.container = container;
 }

 protected HasWidgets getContainer ()
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
  container.add ((Widget) component.getImpl ());
 }

 @Override
 public void clear ()
 {
  for (KSUIComponent component : components)
  {
   component.setParent (null);
  }
  components.clear ();
  container.clear ();
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
  Iterator <Widget> it = container.iterator ();

  while (it.hasNext ())
  {
   Widget widget = it.next ();
   if (widget == component.getImpl ())
   {
    container.remove (widget);
    assert removed;  // implementation not in sync with KS structure!
    component.setParent (null);
    return true;
   }
  }
  assert  ! removed;  // implementation not in sync with KS structure!
  return false;
 }

 @Override
 public HasWidgets getImpl ()
 {
  return container;
 }

 @Override
 public void setImpl (Object impl)
 {
  container = (HasWidgets) impl;
 }
}
