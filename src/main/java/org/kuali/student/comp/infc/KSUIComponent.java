/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

/**
 *
 * @author nwright
 */
public interface KSUIComponent<I>
{

 public KSUIComponent getParent ();

 public void setParent (KSUIComponent parent);

 public I getImpl ();

 public void setImpl (I impl);

 public abstract class Callback<T>
 {

  private T caller;

  public Callback (T caller)
  {
   this.caller = caller;
  }

  public abstract void onDone ();

  public abstract void onError (Exception ex);

 }

 public void display (Callback callback);

}
