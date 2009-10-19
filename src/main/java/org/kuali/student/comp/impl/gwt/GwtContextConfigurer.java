/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.gwt;

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
public class GwtContextConfigurer implements KSContextConfigurer
{

 @Override
 public void configure (KSContext context)
 {
  GwtDisplayHomePage homePage =  new GwtDisplayHomePage (context);
  homePage.setDisplayAuthenticatedUser (new GwtDisplayAuthenticatedUser (context));
  homePage.setDisplaySelectFromUserTaskList (new GwtDisplaySelectFromUserTaskList (context));
  homePage.setDisplaySelectProposalTypesUserCanStart (new GwtDisplaySelectProposalTypesUserCanStart (context));

  context.register (KSDisplayHomePage.class, homePage);
  context.register (KSUnrecoverableExceptionHandler.class, new GwtUnrecoverableExceptionHandler ());
  List<ProposalTypeKey> types = new ArrayList ();
  types.add (new ProposalTypeKey ("kuali.new.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.modify.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.retire.credit.course.proposal"));
  KSSelectProposalType selectPropType = new GwtSelectProposalType (context);
  selectPropType.setOptions (types);
  context.register (KSSelectProposalType.class, selectPropType);

 }

}
