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

package org.kuali.student.enrollment.class1.lrr.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.infc.LearningResultRecord;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Term resolver class to find all completed courses for a student
 *
 * @author alubbers
 */
public class CompletedCoursesResolver implements TermResolver<Collection<String>> {

    private LearningResultRecordService lrrService;

    private LprService lprService;

    private LuiService luiService;

    private final static Set<String> prerequisites = new HashSet<String>(2);

    static {
        prerequisites.add(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        prerequisites.add(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    public void setLrrService(LearningResultRecordService lrrService) {
        this.lrrService = lrrService; 
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.STUDENT_COMPLETED_COURSE_IDS_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        // TODO analyze actual cost
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME).toString();
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);

        Collection<String> results = null;

        try {
            List<LprInfo> lprs = lprService.getLprsByPerson(studentId, context);

            Map<String, String> lprIdToCluId = new HashMap<String, String>();
            List<String> lprIds = new ArrayList<String>(lprs.size());

            for(LprInfo lpr : lprs) {
                String luiId = lpr.getLuiId();
                LuiInfo lui = luiService.getLui(luiId, context);
                lprIdToCluId.put(lpr.getId(), lui.getCluId());

                lprIds.add(lpr.getId());
            }

            List<LearningResultRecordInfo> lrrs = lrrService.getLearningResultRecordsForLprIds(lprIds, context);

            results = new ArrayList<String>();

            for(LearningResultRecord lrr : lrrs) {
                results.add(lprIdToCluId.get(lrr.getLprId()));
            }

        } catch (DoesNotExistException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        }catch (InvalidParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (MissingParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (OperationFailedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        }

        return results;
        
    }

}
