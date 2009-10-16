/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import java.util.List;

/**
 *
 * @author nwright
 */
public interface KSUIContainer <I> extends KSUIComponent
{

 @Override
 public I getImpl ();

 public void add (KSUIComponent component);

 public boolean remove (KSUIComponent component);

 public void clear ();

 public List<KSUIComponent> getComponents ();

}
