/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.infc;

import javax.servlet.http.HttpServletRequest;
import org.kuali.rice.kim.service.AuthenticationService;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public interface KSContext
{

 public void register (Class infc, Object obj);

 public Object getInstance (Class infc);

}
