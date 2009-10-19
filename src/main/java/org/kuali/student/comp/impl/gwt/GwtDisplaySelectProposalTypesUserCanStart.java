/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplaySelectProposalTypesUserCanStart;

/**
 *
 * @author nwright
 */
public class GwtDisplaySelectProposalTypesUserCanStart extends AbstractGwtUIContainer
 implements KSDisplaySelectProposalTypesUserCanStart
{

 private KSContext context;

 public GwtDisplaySelectProposalTypesUserCanStart (KSContext context)
 {
  super (new VerticalPanel ());
  this.context = context;
  init ();
 }

 private VerticalPanel getPanel ()
 {
  return (VerticalPanel) getContainer ();
 }


 private void init ()
 {
  getPanel ().setTitle ("Start a proposal...");
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  //TODO: get user and get her list of tasks
  List<String> tasks = new ArrayList ();
  getPanel ().add (new KSLabel (tasks.size () +
   " type(s) of proposals user can start"));
  for (String task : tasks)
  {
   getPanel ().add (new KSLabel (task));
  }
  getPanel ().setVisible (true);
 }

}
