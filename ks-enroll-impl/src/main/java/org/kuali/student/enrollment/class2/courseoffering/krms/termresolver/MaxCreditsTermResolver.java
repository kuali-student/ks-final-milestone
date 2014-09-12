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
 * This class is a term resolver for retrieving max credits from the GES
 * service.
 *
 * Created by Paul Richardson on 9/12/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.GesTermResolverSupport;
import org.kuali.student.enrollment.krms.termresolver.BestEffortCreditLoadTermResolver;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Return an Float value for Max Credits from the GES service based on:
 * -- The context info (prereq)
 * -- The person id (prereq)
 * -- The atp id (prereq)
 * -- The effective date (prereq)
 *
 * @author Kuali Student Team
 */
public class MaxCreditsTermResolver extends GesTermResolverSupport<Float> {

    @Override
    public String getOutput() {
        return RulesExecutionConstants.MAX_CREDITS_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
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
        return 1;
    }

    @Override
    public Float resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String gesParameterKey = GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT;

        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        String atpId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());
        Date asOfDate = (Date) resolvedPrereqs.get(RulesExecutionConstants.AS_OF_DATE_TERM.getName());

        Float maxCredits;
        try {
            maxCredits = evaluateFloatOnDate(gesParameterKey, contextInfo, personId, atpId, asOfDate);
        }catch (DoesNotExistException e) {
            maxCredits = BestEffortCreditLoadTermResolver.NO_CREDIT_LIMIT;
        }catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            maxCredits = null;
        }
        
        return maxCredits;
    }

}
