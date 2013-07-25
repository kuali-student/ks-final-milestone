/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 7/12/13
 */
package org.kuali.student.r2.core.class1.atp.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a workaround for now.  AcademicCalendarServiceFacade uses the Acal mock service, but it also
 * uses the AtpService.  The two aren't hooked up, so this creates issues doing search.
 *
 * @author Kuali Student Team
 */
public class AtpServiceHackMockImpl extends AtpServiceMockImpl {
    @Resource
    private AcademicCalendarService acalService;

    private Map<String, AtpInfo> atpCache = new HashMap<String, AtpInfo>();
    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
                   PermissionDeniedException {
        // Hack
        if (searchRequest.getSearchKey().equals("atp.search.relatedAtpIdsByAtpId")) {
            SearchResultInfo resultInfo = new SearchResultInfo();
            List<SearchResultRowInfo> rows = new ArrayList<SearchResultRowInfo>();
            String parentAtpId = searchRequest.getParams().get(0).getValues().get(0);
            try {
                List<TermInfo> terms = acalService.getIncludedTermsInTerm(parentAtpId, contextInfo);
                for (TermInfo term: terms) {
                    SearchResultRowInfo row = new SearchResultRowInfo();
                    row.addCell("atp.resultColumn.relatedAtpId", term.getId());
                    rows.add(row);
                }
                resultInfo.setRows(rows);
                return resultInfo;
            } catch (DoesNotExistException e) {
                throw new OperationFailedException("parentAtpId=" + parentAtpId + " does not exist");
            }
        }
        return null;
    }
}
