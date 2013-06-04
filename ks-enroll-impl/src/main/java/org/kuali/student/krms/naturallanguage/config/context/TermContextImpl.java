/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.krms.naturallanguage.config.context;

import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.Map;


/**
 * This class creates the template context for Term.
 */
public class TermContextImpl extends BasicContextImpl {

    private AcademicCalendarService acalService;

	public final static String TERM_TOKEN = "term";

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }
    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

	private TermInfo getTerm(String termId, ContextInfo context)  {
		if (termId == null) {
			return null;
		}
		try {

			return  this.getAcalService().getTerm(termId, context);
		} catch (Exception e) {
                    throw new RiceIllegalStateException (e);
		}
	}

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        Map<String, Object> contextMap = super.createContextMap(parameters);

        String termId = (String) parameters.get(TermParameterTypes.TERM_KEY.getId());
        TermInfo termInfo = this.getTerm(termId, contextInfo);
        if( termInfo != null){
            contextMap.put(TERM_TOKEN, termInfo);
        }

        return contextMap;
    }
}
