/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl;

import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.service.impl.mock.MockContextConfigurer;

/**
 *
 * @author nwright
 */
public class HomeMenu implements Runnable
{

 private KSContext context;

 public HomeMenu ()
 {
  context = new Context ();
  new MockContextConfigurer ().configure (context);
 }

 @Override
 public void run ()
 {
  KSDisplayHomePage home =
   (KSDisplayHomePage) context.getInstance (KSDisplayHomePage.class);
  home.display (new KSDisplayHomePage.Callback (this)
  {

   @Override
   public void onDone ()
   {
    System.exit (0);
    return;
   }

   @Override
   public void onError (Exception ex)
   {
    throw new RuntimeException (ex);
   }

  });
 }

 public static void main (String[] args)
 {
  HomeMenu menu = new HomeMenu ();
  menu.run ();
 }

}
