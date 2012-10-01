/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.process.krms.termresolver;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 12/13/11
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationHoldsTermResolver implements TermResolver<List<AppliedHoldInfo>> {

    private HoldService holdService;

    private final static Set<String> prerequisites = new HashSet<String>(2);

    static {
        prerequisites.add(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        prerequisites.add(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.STUDENT_REGISTRATION_HOLDS_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(RulesExecutionConstants.ISSUE_KEY_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        // TODO Analyze
        return 0;
    }

    @Override
    public List<AppliedHoldInfo> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = (String) resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
        String issueId = parameters.get(RulesExecutionConstants.ISSUE_KEY_TERM_PROPERTY);

        List<AppliedHoldInfo> appliedHolds;

        // get all the active holds for the student and the given issue
        try {
            appliedHolds = holdService.getActiveAppliedHoldsByIssueAndPerson(issueId, studentId, context);
        } catch (InvalidParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (MissingParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (OperationFailedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        }

        return appliedHolds;
    }
}
