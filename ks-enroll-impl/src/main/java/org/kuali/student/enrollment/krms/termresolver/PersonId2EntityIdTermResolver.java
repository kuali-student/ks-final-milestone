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
 * Created by paulrichardson on 8/27/14
 */
package org.kuali.student.enrollment.krms.termresolver;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class consumes a Person Id and produces an Entity Id using the Identity Service
 *
 * @author Kuali Student Team
 */
public class PersonId2EntityIdTermResolver implements TermResolver<String> {

    private final static Set<String> prereqs;

    static {
        Set<String> temp = new HashSet<>(1);
        temp.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs = Collections.unmodifiableSet(temp);
    }

    @Override
    public Set<String> getPrerequisites() {
        return prereqs;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.ENTITY_ID_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());

        String entityId = null;
        try {
            entityId = CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().getEntityByPrincipalId(personId).getId();
        } catch (RiceIllegalArgumentException | NullPointerException e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return entityId;
    }
}
