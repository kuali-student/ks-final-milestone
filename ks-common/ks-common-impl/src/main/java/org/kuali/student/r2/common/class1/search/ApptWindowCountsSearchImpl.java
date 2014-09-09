/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * Created by Daniel on 4/26/12
 */
package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.core.search.util.SearchResultCreatorHelper;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class implements the search service by hardwiring in the JPQL to get appointment window counts
 *
 * @author Kuali Student Team
 */
public class ApptWindowCountsSearchImpl
        extends SearchServiceAbstractHardwiredImpl {

    public static final String CREATE_TIME = "createTime";
    public static final String FIRST_SLOT = "firstSlot";
    public static final String LAST_SLOT = "lastSlot";
    public static final String NUM_APPTS = "numAppts";
    public static final String NUM_SLOTS = "numSlots";
    
    /**
     * The search type for this search
     */
    public static final TypeInfo SEARCH_TYPE;

    static {
        TypeInfo info = new TypeInfo();
        info.setKey("kuali.search.type.appt.appointmentCountsForWindowId");
        info.setName("Appointment Counts for Window Id");
        info.setDescr(new RichTextHelper().fromPlain("Gets summary counts fo slots and appointmetns for an appointment window"));
        DateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
        try {
            info.setEffectiveDate(mmddyyyy.parse("01/01/2012"));
        } catch (ParseException ex) {
            throw new RuntimeException("bad code");
        }
        SEARCH_TYPE = info;
    }
    /**
     * The appointment window id parameter type
     */
    public static final TypeInfo APPT_WINDOW_ID;

    static {
        TypeInfo info = new TypeInfo();
        info.setKey("windowId");
        info.setName("Appointment Window Id");
        info.setDescr(new RichTextHelper().fromPlain("The appointment window id for which you want to get the counts"));
        DateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
        try {
            info.setEffectiveDate(mmddyyyy.parse("01/01/2012"));
        } catch (ParseException ex) {
            throw new RuntimeException("bad code");
        }
        APPT_WINDOW_ID = info;
    }
    /**
     * The search parameters used by this search
     */
    public static final TypeInfo[] SEARCH_PARAMETERS = {APPT_WINDOW_ID};

    @Override
    public TypeInfo getSearchType() {
        return SEARCH_TYPE;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!searchRequestInfo.getSearchKey().equals(SEARCH_TYPE.getKey())) {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        //This is a hardwired search for AppointmentWindows
        //It gets the count of appointments for a given window Id
        String windowId = requestHelper.getParamAsString(APPT_WINDOW_ID.getKey());
        SearchResultCreatorHelper creatorHelper = new SearchResultCreatorHelper();
        creatorHelper.getHeaders().add(CREATE_TIME);
        creatorHelper.getHeaders().add(FIRST_SLOT);
        creatorHelper.getHeaders().add(LAST_SLOT);
        creatorHelper.getHeaders().add(NUM_APPTS);
        creatorHelper.getHeaders().add(NUM_SLOTS);
        Query query = this.getGenericEntityDao().getEm().createNamedQuery("AppointmentWindowEntity.appointmentWindowCounts");
        query.setParameter("windowId", windowId);
        creatorHelper.processQuery(query);
        return creatorHelper.getSearchResult();
    }
}
