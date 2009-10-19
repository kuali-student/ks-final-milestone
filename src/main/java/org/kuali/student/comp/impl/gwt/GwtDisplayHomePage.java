/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.comp.infc.KSDisplayAuthenticatedUser;
import org.kuali.student.comp.infc.KSDisplaySelectFromUserTaskList;
import org.kuali.student.comp.infc.KSDisplaySelectProposalTypesUserCanStart;

/**
 *
 * @author nwright
 */
public class GwtDisplayHomePage extends AbstractGwtUIContainer
 implements KSDisplayHomePage
{

 private KSContext context;

 public GwtDisplayHomePage (KSContext context)
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
  getPanel ().setTitle ("KS Home Page");
  getPanel ().add (new KSLabel ("Welcome to Kuali Student"));
 }

 private KSDisplayAuthenticatedUser displayUser;

 @Override
 public void setDisplayAuthenticatedUser (KSDisplayAuthenticatedUser displayUser)
 {
  this.displayUser = displayUser;
 }

 private KSDisplaySelectFromUserTaskList displayTaskList;

 @Override
 public void setDisplaySelectFromUserTaskList (
  KSDisplaySelectFromUserTaskList displayTaskList)
 {
  this.displayTaskList = displayTaskList;
 }

 private KSDisplaySelectProposalTypesUserCanStart displayProposalTypes;

 @Override
 public void setDisplaySelectProposalTypesUserCanStart (
  KSDisplaySelectProposalTypesUserCanStart proposalTypes)
 {
  this.displayProposalTypes = proposalTypes;
 }

 private Callback callback;

 protected Callback getCallback ()
 {
  return callback;
 }

 @Override
 public void display (Callback callback)
 {
  this.callback = callback;
  this.add (displayUser);
  this.add (displayTaskList);
  this.add (displayProposalTypes);
  displayUser.display (callback);
  displayTaskList.display (callback);
  displayProposalTypes.display (callback);
  getPanel ().setVisible (true);
 }

}
