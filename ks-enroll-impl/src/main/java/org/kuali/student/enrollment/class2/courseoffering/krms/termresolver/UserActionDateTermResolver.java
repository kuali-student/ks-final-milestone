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
 * Created by Paul Richardson on 9/3/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Returns the user action date (the current date for the registering student). Date is a
 * formatted String in the format mm/dd/yyyy
 *
 * Rule statement example:
 * 1) Student action date (when he clicked on register for specific course(s)/term should be
 * within start and end date of the Registration Adjustment Period
 *
 * @author Kuali Student Team
 */
public class UserActionDateTermResolver implements TermResolver<String> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(1);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.USER_ACTION_DATE_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        // getting contextInfo
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());

        // Date user performed the action (e.g. submitted registration request)
        Date userActionDate = contextInfo.getCurrentDate();

        KSDateTimeFormatter dateFormatter = DateFormatters.MONTH_NOZERO_DAY_YEAR_DATE_FORMATTER;

        return dateFormatter.format(userActionDate);
    }

}
