/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 7/7/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.apache.commons.lang3.StringUtils;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.*;

/**
 * This class checks that the 'as of date' falls within a term's key date range.
 *
 * This check is generic to the specific key date milestone being checked. The
 * milestone should be configured to be passed in as a term parameter.
 *
 * @author Kuali Student Team
 */
public class DateInKeyDateRangeTermResolver implements TermResolver<Boolean> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(4);
        prereqs.add(RulesExecutionConstants.AS_OF_DATE_TERM.getName());
        prereqs.add(RulesExecutionConstants.ATP_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.ATP_SERVICE_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_DATEINTERMKEYDATE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<String>(1);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        // Milestone type is configured by the db and passed in through the parameters.
        String milestoneType = null;
        if (parameters.containsKey(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY)) {
            milestoneType = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY);
        }

        if (StringUtils.isEmpty(milestoneType)) {
            // The term parameters have been poorly configured. No milestone type has been set to check against.
            return false;
        }

        try {
            // Get contextInfo & termId passed in from reg service
            ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
            String termId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());

            // Milestones store start and end date information. All KeyDates are Milestones
            AtpService atpService = (AtpService) resolvedPrereqs.get(RulesExecutionConstants.ATP_SERVICE_TERM.getName());
            List<MilestoneInfo> mstones = atpService.getMilestonesByTypeForAtp(termId, milestoneType, context);
            if (mstones.isEmpty()) {
                // The configured milestone was not found, thus, the date is not in the KeyDate range.
                return false;
            }


            Date userActionDate = (Date) resolvedPrereqs.get(RulesExecutionConstants.AS_OF_DATE_TERM.getName());
            for (MilestoneInfo mstone : mstones) {
                // A milestone doesn't have to have an end date. Only check it if it exist.
                if ((mstone.getStartDate() != null && mstone.getStartDate().after(userActionDate))
                    || (mstone.getEndDate() != null || mstone.getEndDate().before(userActionDate))) {
                    // The date does not fall within the configured date range of this milestone.
                    return false;
                }
            }

        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return true;
    }

}
