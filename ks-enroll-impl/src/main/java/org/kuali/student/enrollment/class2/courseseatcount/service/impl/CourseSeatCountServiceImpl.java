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

import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseseatcount.dto.SeatCountInfo;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
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
        SeatCount result = KSCollectionUtils.getRequiredZeroElement(list);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        validateAoIds(activityOfferingIds, context);
        // Get max seats map
        Map [] maps =
                computeAoIdToMaxSeats(activityOfferingIds, context);
        Map<String, Integer> aoIdToMaxSeats = (Map<String, Integer>) maps[0];
        Map<String, String> aoIdToAoType = (Map<String, String>) maps[1];
        Map<String, Integer> aoIdToRegisteredSeats =
                computeAoIdToRegisteredSeats(activityOfferingIds, context);
        Date timestamp = new Date();
        List<SeatCount> seatCounts = new ArrayList<SeatCount>();
        for (Map.Entry<String, Integer> entry: aoIdToMaxSeats.entrySet()) {
            SeatCountInfo seatCountInfo = new SeatCountInfo();
            String aoId = entry.getKey();
            Integer totalSeats = entry.getValue();
            seatCountInfo.setTotalSeats(totalSeats);
            Integer usedSeats = aoIdToRegisteredSeats.get(aoId);
            seatCountInfo.setUsedSeats(usedSeats);
            if (totalSeats != null && usedSeats != null) {
                Integer availableSeats = totalSeats - usedSeats;
                seatCountInfo.setAvailableSeats(availableSeats);
            }
            seatCountInfo.setTimestamp(timestamp);
            seatCountInfo.setLuiId(aoId);
            String aoType = aoIdToAoType.get(aoId);
            seatCountInfo.setLuiTypeKey(aoType);
            // Add the object to the container
            seatCounts.add(seatCountInfo);
        }

        return seatCounts;
    }

    private Map<String, Integer> computeAoIdToRegisteredSeats(List<String> activityOfferingIds, ContextInfo context)
            throws OperationFailedException {
        // Start the count
        SearchRequestInfo searchRequest =
                new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPRS_BY_AOIDS_LPR_STATE_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, activityOfferingIds);
        List<String> lprStates = new ArrayList<String>();
        lprStates.add(LprServiceConstants.PLANNED_STATE_KEY);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LPR_STATES, lprStates);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, context);
        } catch (Exception e) {
            throw new OperationFailedException("getSeatCountsForActivityOfferings", e);
        }
        Map<String, Integer> aoIdToRegisteredSeats = new HashMap<String, Integer>();
        for (SearchResultRowInfo row: searchResult.getRows()) {
            String aoId = null;
            Integer count = null;
            for (SearchResultCellInfo cell: row.getCells()) {
                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    // For now, assume if ao id exists, it represents a new student
                    aoId = cell.getValue();
                    break;
                }
            }
            if (aoId != null) {
                // Doesn't make sense to map if aoId never got set (but that should also never happen)
                if (!aoIdToRegisteredSeats.containsKey(aoId)) {
                    aoIdToRegisteredSeats.put(aoId, 0);
                }
                int value = aoIdToRegisteredSeats.get(aoId);
                aoIdToRegisteredSeats.put(aoId, value + 1);
            }
        }
        for (String aoId: activityOfferingIds) {
            if (!aoIdToRegisteredSeats.containsKey(aoId)) {
                aoIdToRegisteredSeats.put(aoId, 0);
            }
        }
        return aoIdToRegisteredSeats;
    }

    private Map[] computeAoIdToMaxSeats(List<String> activityOfferingIds, ContextInfo context)
            throws OperationFailedException {
        SearchRequestInfo searchRequest =
                new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AOIDS_TYPE_MAXSEATS_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS,
                activityOfferingIds);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, context);
        } catch (Exception e) {
            throw new OperationFailedException("computeAoIdToMaxSeats failed", e);
        }

        Map<String, Integer> aoIdToMaxSeats = new HashMap<String, Integer>();
        Map<String, String> aoIdToAoType = new HashMap<String, String>();
        for (SearchResultRowInfo row: searchResult.getRows()) {
            String aoId = null, aoType = null;
            Integer count = null;
            for (SearchResultCellInfo cell: row.getCells()) {
                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    aoId = cell.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS.equals(cell.getKey())) {
                    count = Integer.valueOf(cell.getValue());
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cell.getKey())) {
                    aoType = cell.getValue();
                }
            }
            if (aoId != null) {
                aoIdToMaxSeats.put(aoId, count);
                aoIdToAoType.put(aoId, aoType);
            }
        }
        // Make sure that this map has a key for each aoId
        for (String aoId: activityOfferingIds) {
            if (!aoIdToMaxSeats.containsKey(aoId)) {
                aoIdToMaxSeats.put(aoId, null);
                aoIdToAoType.put(aoId, null);
            }
        }
        Map[] maps = new Map[2];
        maps[0] = aoIdToMaxSeats;
        maps[1] = aoIdToAoType;
        return maps;
    }

    /**
     * Checks to see if all the ids are actually activityOfferingIds.  If not, throws
     * InvalidParameterException or OperationFailedException
     * @param activityOfferingIds A list of activity offering ids.
     * @param context The context info
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
        SearchResultRowInfo row = KSCollectionUtils.getRequiredZeroElement(searchResult.getRows());
        int actual = -1, expected = -1;
        for (SearchResultCellInfo cell: row.getCells()) {
            if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_IDS_ACTUAL_COUNT.equals(cell.getKey())) {
                actual = Integer.valueOf(cell.getValue());
            } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_IDS_EXPECTED_COUNT.equals(cell.getKey())) {
                expected = Integer.valueOf(cell.getValue());
            }
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
