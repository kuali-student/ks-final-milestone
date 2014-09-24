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

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.KeyDateTermResolverSupport;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.TermResolverPerformanceUtil;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return true if student tried to register when it is allowed to register.
 *
 * Rule statement example:
 * 1) Student action date (when he clicked on register for specific course(s)/term should be
 * within start and end date of the Registration Adjustment Period
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationKeyDateTermResolver extends KeyDateTermResolverSupport<Boolean> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_CURRENT_COURSE_WITHIN_KEYDATE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<>(2);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        DateTime startTime = new DateTime();

        // getting contextInfo and regGroupInfo passed from Reg Cart
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationGroupInfo regGroupInfo = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        // Date user did the action (tried to submit registration for the regGroupInfo)
        Date userActionDate = context.getCurrentDate();
        String keydateTypeParameter = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEYDATE_TYPE_KEY);
        boolean allowRegistration = true;
        try {
            // Have to check if user action date is within KeyDate (say, Registration Adjustment Period) start/end dates
            List<MilestoneInfo> mstones = getMilestones(context, regGroupInfo, keydateTypeParameter);
            for (MilestoneInfo mstone : mstones) {
                if ((mstone.getStartDate() != null && mstone.getStartDate().compareTo(userActionDate) > 0) ||
                        (mstone.getEndDate() != null && mstone.getEndDate().compareTo(userActionDate) < 0)) {
                    allowRegistration = false;
                    break;
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            allowRegistration = false;
        }

        DateTime endTime = new DateTime();
        TermResolverPerformanceUtil.putStatistics(getOutput(), startTime, endTime);

        return allowRegistration;
    }

}
