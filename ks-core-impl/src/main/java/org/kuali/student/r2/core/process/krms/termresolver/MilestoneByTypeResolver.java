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

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class resolves a milestone type into MilestoneInfos
 *
 * @author alubbers
 */
public class MilestoneByTypeResolver implements TermResolver<List<MilestoneInfo>> {

    private AtpService atpService;

    private final static Set<String> requiredParameterNames;

    static {
        Set<String> temp = new HashSet<String>(2);
        temp.add(RulesExecutionConstants.MILESTONE_ATP_KEY_TERM_PROPERTY);
        temp.add(RulesExecutionConstants.MILESTONE_TYPE_TERM_PROPERTY);

        requiredParameterNames = Collections.unmodifiableSet(temp);
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return Collections.singleton(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.MILESTONES_BY_TYPE_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return requiredParameterNames;
    }

    @Override
    public int getCost() {
        // TODO Analyze
        return 0;
    }

    @Override
    public List<MilestoneInfo> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String milestoneType = parameters.get(RulesExecutionConstants.MILESTONE_TYPE_TERM_PROPERTY);
        String atpKey = parameters.get(RulesExecutionConstants.MILESTONE_ATP_KEY_TERM_PROPERTY);
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);

        List<MilestoneInfo> result = null;
        try {
            result = atpService.getMilestonesByTypeForAtp(atpKey, milestoneType, context);
        } catch (InvalidParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (MissingParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (OperationFailedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
        }

        return result;
    }
}
