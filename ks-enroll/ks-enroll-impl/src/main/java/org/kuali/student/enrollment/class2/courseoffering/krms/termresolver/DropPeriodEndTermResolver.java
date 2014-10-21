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
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.KeyDateTermResolverSupport;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Returns the last day that the student is allowed to drop for the given reg group.
 * Date is formatted as a String: mm/dd/yyyy
 *
 * @author Kuali Student Team
 */
public class DropPeriodEndTermResolver extends KeyDateTermResolverSupport<String> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.DROP_PERIOD_END_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        // getting contextInfo and regGroupInfo passed from Reg Cart
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationGroupInfo regGroupInfo = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        Date endDate = null;

        try {
            // First, get the schedule adjustment end date
            Date scheduleAdjustmentEndDate = getEndDateForKeydateType(context, regGroupInfo, AtpServiceConstants.MILESTONE_SCHEDULE_ADJUSTMENT_PERIOD_TYPE_KEY);
            // Next, get the drop period end date
            Date dropPeriodEndDate = getEndDateForKeydateType(context, regGroupInfo, AtpServiceConstants.MILESTONE_DROP_PERIOD_TYPE_KEY);
            // Determine the most recent end date
            if (dropPeriodEndDate.after(scheduleAdjustmentEndDate)) {
                endDate = dropPeriodEndDate;
            } else {
                endDate = scheduleAdjustmentEndDate;
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        String endDateString = null;

        if (endDate != null) {
            KSDateTimeFormatter dateFormatter = DateFormatters.MONTH_NOZERO_DAY_YEAR_DATE_FORMATTER;
            endDateString = dateFormatter.format(endDate);
        }

        return endDateString;
    }

}
