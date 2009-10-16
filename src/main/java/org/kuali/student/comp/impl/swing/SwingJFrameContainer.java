/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.impl.AbstractUIContainer;
import org.kuali.student.comp.infc.KSUIContainer;
import org.kuali.student.comp.infc.KSUIComponent;
import java.awt.Component;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author nwright
 */
public abstract class SwingJFrameContainer extends AbstractUIContainer
 implements KSUIContainer
{

 public SwingJFrameContainer (JFrame frame)
 {
  super (frame);

 }

 protected JFrame getJFrame ()
 {
  return (JFrame) super.getImpl ();
 }

 @Override
 public void add (KSUIComponent component)
 {
  super.add (component);
  getJFrame ().getContentPane ().add ((Component) component.getImpl ());
 }

 @Override
 public void clear ()
 {
  super.clear ();
  getJFrame ().getContentPane ().removeAll ();
 }

 @Override
 public List<KSUIComponent> getComponents ()
 {
  return super.getComponents ();
 }

 @Override
 public boolean remove (KSUIComponent component)
 {
  boolean removed = super.remove (component);
  for (Component comp : getJFrame ().getContentPane ().getComponents ())
  {
   if (comp == component.getImpl ())
   {
    getJFrame ().getContentPane ().remove (comp);
    assert removed;  // implementation not in sync with KS structure!
    return true;
   }
  }
  assert  ! removed;  // implementation not in sync with KS structure!
  return false;
 }

}
