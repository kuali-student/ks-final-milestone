/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.kuali.student.comp.impl.Context;
import org.kuali.student.comp.impl.swing.SwingContextConfigurer;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.service.impl.mock.MockContextConfigurer;

/**
 *
 * @author nwright
 */
public class SwingMain implements Runnable
{

 protected KSContext context;

 public SwingMain ()
 {
  context = new Context ();
  new MockContextConfigurer ().configure (context);
  new SwingContextConfigurer ().configure (context);
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
    System.out.println ("Exiting SwingMain");
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
  JFrame.setDefaultLookAndFeelDecorated (true);
  SwingMain swing = new SwingMain ();
  SwingUtilities.invokeLater (swing);
 }

}
