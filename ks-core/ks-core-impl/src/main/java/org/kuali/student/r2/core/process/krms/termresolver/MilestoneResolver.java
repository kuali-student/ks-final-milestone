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
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 12/21/11
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MilestoneResolver implements TermResolver<MilestoneInfo> {
    private AtpService atpService;

    @Override
    public Set<String> getPrerequisites() {
        return Collections.singleton(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.MILESTONE_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(RulesExecutionConstants.MILESTONE_ID_TERM.getName());
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public MilestoneInfo resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String milestoneId = parameters.get(RulesExecutionConstants.MILESTONE_ID_TERM.getName());
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());

        MilestoneInfo result = null;
        try {
            result = atpService.getMilestone(milestoneId, context);
        } catch (DoesNotExistException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters);
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
