/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplaySelectProposalTypesUserCanStart;

/**
 *
 * @author nwright
 */
public class SwingDisplaySelectProposalTypesUserCanStart extends AbstractSwingUIContainer
 implements KSDisplaySelectProposalTypesUserCanStart
{

 private KSContext context;

 public SwingDisplaySelectProposalTypesUserCanStart (KSContext context)
 {
  super (new JInternalFrame ());
  this.context = context;
  init ();
 }

 private JInternalFrame getFrame ()
 {
  return (JInternalFrame) getContainer ();
 }


 private void init ()
 {
  getFrame ().setContentPane (new Box (BoxLayout.Y_AXIS));
  getFrame ().setTitle ("Start a proposal...");
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  //TODO: get user and get her list of tasks
  List<String> tasks = new ArrayList ();
  getFrame ().getContentPane ().add (new JLabel (tasks.size () +
   " type(s) of proposals user can start"));
  for (String task : tasks)
  {
   getFrame ().getContentPane ().add (new JLabel (task));
  }
  getFrame ().pack ();
  getFrame ().setVisible (true);
 }

}
