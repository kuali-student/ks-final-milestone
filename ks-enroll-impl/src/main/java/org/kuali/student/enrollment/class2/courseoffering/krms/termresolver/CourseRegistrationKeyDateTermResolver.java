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

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationKeyDateTermResolver implements TermResolver<Boolean> {

    private LuiService luiService;
    private AtpService atpService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        prereqs.add("userActionDate");
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_SCHEDULEADJUSTMENTPERIOD;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<String>(2);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationGroupInfo regGroupInfo = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        Date userActionDate = context.getCurrentDate();

        try {
            String termId;
            LuiInfo coLui = getLuiService().getLui(regGroupInfo.getCourseOfferingId(), context);
            List<LuiInfo> aoLuis = getLuiService().getLuisByIds(regGroupInfo.getActivityOfferingIds(), context);

            // We have co and ao terms.  There is special business logic to determine which term to use.
            // find out if all ao terms are the same, if so use the ao term, else use co term
            if( isAllAtpIdsTheSame(aoLuis)){
                termId = KSCollectionUtils.getRequiredZeroElement(aoLuis).getAtpId();
            } else {
                termId = coLui.getAtpId();
            }

            // Milestones store start and end date information. All KeyDates are Milestones
            List<MilestoneInfo> mstones = getAtpService().getMilestonesByTypeForAtp(termId, KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY, context);
            for (MilestoneInfo mstone : mstones) {
                if (mstone.getStartDate().compareTo(userActionDate) > 0 || mstone.getEndDate().compareTo(userActionDate) < 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    /**
     * helper method that takes in a list of luis and returns if all atpIds are the same
     * @param l
     * @return
     */
    private static boolean isAllAtpIdsTheSame(List<LuiInfo> l) {
        Set<String> set = new HashSet<String>(l.size());
        for (LuiInfo o : l) {
            if (set.isEmpty()) {
                set.add(o.getAtpId());
            } else {
                if (set.add(o.getAtpId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
}
