/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

/**
 *
 * @author nwright
 */
public interface KSUIComponent <I>
{

 public I getImpl ();

  public abstract class Callback <T>
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
