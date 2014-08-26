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
public class GesIntegerValueTermResolver implements TermResolver<Integer> {

    private GesService gesService;

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_GES_INTEGER_VALUE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<>(1);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GES_PARAMETER_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(4);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.ATP_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.AS_OF_DATE_TERM.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String gesParameterKey = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GES_PARAMETER_KEY);

        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        String atpId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());
        Date asOfDate = (Date) resolvedPrereqs.get(RulesExecutionConstants.AS_OF_DATE_TERM.getName());

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

        Integer integerValue;

        if (value != null) {
            integerValue = value.getDecimalValue().intValue();
        } else {
            integerValue = null;
        }

        return integerValue;
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }
}
