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
 * This class is a term resolver for retrieving a value from the GES
 * service.
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Return an integer value from the GES service based on:
 * -- A GES parameter (parm)
 * -- The context info (prereq)
 * -- The person id (prereq)
 * -- The atp id (prereq)
 * -- The effective date (prereq)
 *
 * @author Kuali Student Team
 */
public class GesMaxRepeatabilityTermResolver implements TermResolver<String> {

    public final static String MAX_REPEATABILITY_ERROR = "kuali.max.repeatability.error";
    public final static String MAX_REPEATABILITY_WARNING = "kuali.max.repeatability.warning";
    public final static String MAX_REPEATABILITY_SUCCESS = "kuali.max.repeatability.success";

    private GesService gesService;

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_GES_MAX_REPEATABILITY;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(5);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.ATP_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.AS_OF_DATE_TERM.getName());
        prereqs.add(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_TOTAL_ATTEMPTS);

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String gesParameterKey = GesServiceConstants.PARAMETER_KEY_MAX_REPEATABLE;

        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        String atpId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());
        Date asOfDate = (Date) resolvedPrereqs.get(RulesExecutionConstants.AS_OF_DATE_TERM.getName());
        Integer totalAttempts = (Integer) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_TOTAL_ATTEMPTS);

        GesCriteriaInfo criteria = new GesCriteriaInfo();
        criteria.setPersonId(personId);
        criteria.setAtpId(atpId);
        ValueInfo value;
        try {
            value = gesService.evaluateValueOnDate(gesParameterKey,
                    criteria,
                    asOfDate,
                    contextInfo);
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            value = null;
        }

        Integer maxRepeats = null;

        if (value != null) {
            maxRepeats = value.getDecimalValue().intValue();
        }

        String errorLevel = MAX_REPEATABILITY_SUCCESS;

        if (totalAttempts != null && maxRepeats != null) {
            if (totalAttempts >= maxRepeats) {
                errorLevel = MAX_REPEATABILITY_ERROR;
            } else if (maxRepeats > 1 && totalAttempts >= maxRepeats - 1) {
                errorLevel = MAX_REPEATABILITY_WARNING;
            }
        }

        return errorLevel;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }
}
