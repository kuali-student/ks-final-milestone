/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSSelectProposalType;
import org.kuali.student.comp.infc.KSContextConfigurer;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.service.dto.ProposalTypeKey;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.comp.infc.KSUnrecoverableExceptionHandler;


/**
 *
 * @author nwright
 */
public class SwingContextConfigurer implements KSContextConfigurer
{

 @Override
 public void configure (KSContext context)
 {
  SwingDisplayHomePage homePage =  new SwingDisplayHomePage (context);
  homePage.setDisplayAuthenticatedUser (new SwingDisplayAuthenticatedUser (context));
  homePage.setDisplaySelectFromUserTaskList (new SwingDisplaySelectFromUserTaskList (context));
  homePage.setDisplaySelectProposalTypesUserCanStart (new SwingDisplaySelectProposalTypesUserCanStart (context));

  context.register (KSDisplayHomePage.class, homePage);
  context.register (KSUnrecoverableExceptionHandler.class, new SwingUnrecoverableExceptionHandler ());
  List<ProposalTypeKey> types = new ArrayList ();
  types.add (new ProposalTypeKey ("kuali.new.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.modify.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.retire.credit.course.proposal"));
  KSSelectProposalType selectPropType = new SwingSelectProposalType (context);
  selectPropType.setOptions (types);
  context.register (KSSelectProposalType.class, selectPropType);

 }

}
