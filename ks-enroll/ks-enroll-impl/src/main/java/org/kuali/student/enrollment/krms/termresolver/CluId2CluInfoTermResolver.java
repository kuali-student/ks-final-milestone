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
 * Created by jmorris on 8/20/14
 */
package org.kuali.student.enrollment.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class consumes a Clu ID and outputs a CluInfo using the CluService.
 *
 * @author Kuali Student Team
 */
public class CluId2CluInfoTermResolver implements TermResolver<CluInfo> {

    private final static Set<String> prereqs;

    static {
        Set<String> temp = new HashSet<>(2);
        temp.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        temp.add(RulesExecutionConstants.CLU_ID_TERM.getName());
        prereqs = Collections.unmodifiableSet(temp);
    }

    private CluService cluService;

    @Override
    public Set<String> getPrerequisites() {
        return prereqs;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.CLU_INFO_TERM.getName();
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
    public CluInfo resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String cluId = (String) resolvedPrereqs.get(RulesExecutionConstants.CLU_ID_TERM.getName());

        CluInfo cluInfo = null;
        try {
            cluInfo = getCluService().getClu(cluId, contextInfo);
        } catch (DoesNotExistException | InvalidParameterException | MissingParameterException | OperationFailedException | PermissionDeniedException e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return cluInfo;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

}
