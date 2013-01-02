/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Daniel on 4/26/12
 */
package org.kuali.student.r2.core.class1.search;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SearchServiceHardwiredImpl implements SearchService {

    private GenericEntityDao enrollmentDao;

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(@WebParam(name = "searchResultTypeKey") String searchResultTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(@WebParam(name = "searchCriteriaTypeKey") String searchCriteriaTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        if("appt.search.appointmentCountForWindowId".equals(searchRequestInfo.getSearchKey())){
            //This is a hardwired search for AppointmentWindows
            //It gets the count of appointments for a given window Id
            String windowId =  searchRequestInfo.getParams().get(0).getValues().get(0);
            Object[] result = (Object[]) enrollmentDao.getEm().
                    createQuery("SELECT w.createTime, MIN(s.startDate), MAX(s.startDate), COUNT(*), COUNT(DISTINCT s.id) " +
                                "FROM AppointmentWindowEntity w, AppointmentSlotEntity s, AppointmentEntity a " +
                                "WHERE w.id= :windowId  AND s.apptWinEntity.id = w.id  AND a.slotEntity.id = s.id GROUP BY w.createTime").
                    setParameter("windowId", windowId).
                    getSingleResult();
            SearchResultInfo searchResult = new SearchResultInfo();
            SearchResultRowInfo row = new SearchResultRowInfo();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            row.addCell("createTime", formatter.format(result[0]));
            row.addCell("firstSlot",formatter.format(result[1]));
            row.addCell("lastSlot",formatter.format(result[2]));
            row.addCell("numAppts",result[3].toString());
            row.addCell("numSlots",result[4].toString());

            searchResult.getRows().add(row);
            return searchResult;
        }

        throw new OperationFailedException("The requested search key is not configured: "+searchRequestInfo.getSearchKey());
    }


    public void setEnrollmentDao(GenericEntityDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }
}
