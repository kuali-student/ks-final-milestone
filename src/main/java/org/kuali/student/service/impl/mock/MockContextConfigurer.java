/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.impl.mock;

import org.kuali.student.comp.impl.GetAuthenticatedUserFromHttpRequest;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSGetAuthenticatedUser;
import org.kuali.student.comp.infc.KSSelectProposalType;
import org.kuali.student.comp.infc.KSContextConfigurer;
import org.kuali.student.comp.*;
import org.kuali.student.comp.impl.swing.SwingSelectProposalType;
import org.kuali.student.service.dto.ProposalTypeKey;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.kuali.student.comp.impl.swing.SwingDisplayHomePage;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class MockContextConfigurer implements KSContextConfigurer
{

 @Override
 public void configure (KSContext context)
 {
  context.register (KSDisplayHomePage.class, new SwingDisplayHomePage (context));
  context.register (ProposalService.class, new MockProposalService (context));
  context.register (HttpServletRequest.class, new MockHttpServletRequest ());
  context.register (LuService.class, new MockLuService (context));
  context.register (KSGetAuthenticatedUser.class, new GetAuthenticatedUserFromHttpRequest (context));
  List<ProposalTypeKey> types = new ArrayList ();
  types.add (new ProposalTypeKey ("kuali.new.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.modify.credit.course.proposal"));
  types.add (new ProposalTypeKey ("kuali.retire.credit.course.proposal"));
  KSSelectProposalType selectPropType = new SwingSelectProposalType (context);
  selectPropType.setOptions (types);
  context.register (KSSelectProposalType.class, selectPropType);

 }

}
