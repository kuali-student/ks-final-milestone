/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplaySelectFromUserTaskList;
import org.kuali.student.comp.infc.KSUIComponent.Callback;

/**
 *
 * @author nwright
 */
public class GwtDisplaySelectFromUserTaskList extends AbstractGwtUIContainer
 implements KSDisplaySelectFromUserTaskList
{

 private KSContext context;

 public GwtDisplaySelectFromUserTaskList (KSContext context)
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
  getPanel ().setTitle ("User's Task List");
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  //TODO: get user and get her list of tasks
  List<String> tasks = new ArrayList ();
  getPanel ().add (new KSLabel (tasks.size () + " outstanding task(s)"));
  for (String task : tasks)
  {
   getPanel ().add (new KSLabel (task));
  }
  getPanel ().setVisible (true);
 }

}
