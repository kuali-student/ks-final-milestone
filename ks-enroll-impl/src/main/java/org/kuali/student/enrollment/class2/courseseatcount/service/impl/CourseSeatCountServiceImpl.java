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
 * Created by Charles on 2/11/14
 */
package org.kuali.student.enrollment.class2.courseseatcount.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseseatcount.dto.SeatCountInfo;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of CourseSeatCountService
 *
 * @author Kuali Student Team
 */
public class CourseSeatCountServiceImpl implements CourseSeatCountService {
    @Override
    @Transactional(readOnly = true)
    public SeatCount getSeatCountForActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // Lean on the bulk version to do the work
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOfferingId);
        List<SeatCount> list = getSeatCountsForActivityOfferings(aoIds, context);
        return KSCollectionUtils.getRequiredZeroElement(list);
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * Note this implementation assumes that there is one course waitlist per AO.
     */
    public List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        //We might want to validate that these are AO ids in the future?
        //validateAoIds(activityOfferingIds, context);

        //Search for seat count info based on the ao ids (max seats, used seats, max waitlist, total waitlist)
        SearchRequestInfo searchRequest =
                new SearchRequestInfo(CourseRegistrationSearchServiceImpl.SEAT_COUNT_INFO_BY_AOIDS_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS,
                activityOfferingIds);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, context);
        } catch (Exception e) {
            throw new OperationFailedException("computeAoIdToMaxSeats failed", e);
        }

        //Date for time of query
        Date timestamp = new Date();

        List<SeatCount> seatCounts = new ArrayList<SeatCount>();

        //PArse results
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String aoType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_TYPE);
            String maxSeats = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS);
            String maxWaitlistSize = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CWL_MAX_SIZE);
            String cwlId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CWL_ID);
            String seatCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_IDS_ACTUAL_COUNT);
            String waitlistCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_WAITLIST_COUNT);

            SeatCountInfo seatCountInfo = new SeatCountInfo();
            seatCountInfo.setLuiId(aoId);
            seatCountInfo.setLuiTypeKey(aoType);
            seatCountInfo.setTimestamp(timestamp);

            //Calculate total and used seats
            seatCountInfo.setTotalSeats(maxSeats == null ? null : Integer.parseInt(maxSeats));
            seatCountInfo.setUsedSeats(seatCount == null ? null : Integer.parseInt(seatCount));
            if (seatCountInfo.getTotalSeats() != null && seatCountInfo.getUsedSeats() != null) {
                seatCountInfo.setAvailableSeats(seatCountInfo.getTotalSeats() - seatCountInfo.getUsedSeats());
            }
            seatCountInfo.setMaxWaitListSize(maxWaitlistSize == null ? null : Integer.parseInt(maxWaitlistSize));
            seatCountInfo.setWaitListSize(waitlistCount == null ? null : Integer.parseInt(waitlistCount));
            seatCountInfo.setHasWaitList(!StringUtils.isEmpty(cwlId));
            seatCounts.add(seatCountInfo);
        }

        return seatCounts;
    }


    /**
     * Checks to see if all the ids are actually activityOfferingIds.  If not, throws
     * InvalidParameterException or OperationFailedException
     *
     * @param activityOfferingIds A list of activity offering ids.
     * @param context             The context info
     */
    private void validateAoIds(List<String> activityOfferingIds, ContextInfo context)
            throws InvalidParameterException, OperationFailedException {
        SearchRequestInfo searchRequest =
                new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AOIDS_COUNT_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, activityOfferingIds);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, context);
        } catch (Exception e) {
            throw new OperationFailedException("validateAoIds failed", e);
        }
        // Should get back one result
        SearchResultRowInfo checkRow = KSCollectionUtils.getRequiredZeroElement(searchResult.getRows());
        int actual = -1, expected = -1;

        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            actual =
                    Integer.parseInt(row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_IDS_ACTUAL_COUNT));
            expected =
                    Integer.parseInt(row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_IDS_EXPECTED_COUNT));
        }
        if (actual == -1 || expected == -1) {
            // Result should have set these two values to non-negative
            throw new OperationFailedException("validateAoIds failed (2)");
        }
        if (actual != expected) {
            // Some of those AO ids weren't AO ids after all!
            throw new InvalidParameterException("This list of IDs are not all AOs");
        }
    }
}
