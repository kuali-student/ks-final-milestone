/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author nwright
 */
public class SwingDisplayHomePage extends SwingJFrameContainer
 implements KSDisplayHomePage
{

 private KSContext context;

 public SwingDisplayHomePage (KSContext context)
 {
  super (new JFrame ("Kuali Home Menu"));
  this.context = context;
  configure ();
 }

 private void configure ()
 {
  getJFrame ().addWindowListener (new java.awt.event.WindowAdapter ()
  {

   @Override
   public void windowClosing (WindowEvent winEvt)
   {
    callback.onDone ();
   }

  });

  SwingJLabelField label = new SwingJLabelField (new JLabel ("Hello World"));
  this.add (label);
  getJFrame ().pack ();
  Dimension dim = Toolkit.getDefaultToolkit ().getScreenSize ();
  int w = getJFrame ().getSize ().width;
  int h = getJFrame ().getSize ().height;
  int x = (dim.width - w) / 2;
  int y = (dim.height - h) / 2;
  getJFrame ().setLocation (x, y);
 }

 private KSDisplayHomePage.Callback callback;

 @Override
 public void display (final KSDisplayHomePage.Callback callback)
 {
  this.callback = callback;
  getJFrame ().setVisible (true);

//  KSSelectProposalType func =
//   (KSSelectProposalType) context.getInstance (KSSelectProposalType.class);
//  func.execute (new KSSelectProposalType.Callback (this)
//  {
//
//   @Override
//   public void onCancel ()
//   {
//    return;
//   }
//
//   @Override
//   public void onDone (ProposalTypeKey selection)
//   {
//    System.out.println ("Next steps is to Create proposal to " + selection.
//     getKey ());
//    return;
//   }
//
//   @Override
//   public void onError (Exception ex)
//   {
//    throw new RuntimeException (ex);
//   }
//
//   @Override
//   public void onExit ()
//   {
//    return;
//   }
//
//  });
 }

}
