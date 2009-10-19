/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplaySelectFromUserTaskList;
import org.kuali.student.comp.infc.KSUIComponent.Callback;

/**
 *
 * @author nwright
 */
public class SwingDisplaySelectFromUserTaskList extends AbstractSwingUIContainer
 implements KSDisplaySelectFromUserTaskList
{

 private KSContext context;

 public SwingDisplaySelectFromUserTaskList (KSContext context)
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
  getFrame ().setTitle ("User's Task List");
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  //TODO: get user and get her list of tasks
  List<String> tasks = new ArrayList ();
  getFrame ().getContentPane ().add (new JLabel (tasks.size () + " outstanding task(s)"));
  for (String task : tasks)
  {
   getFrame ().getContentPane ().add (new JLabel (task));
  }
  getFrame ().setBounds (50, 0, 100, 200);
  getFrame ().setVisible (true);
 }

}
