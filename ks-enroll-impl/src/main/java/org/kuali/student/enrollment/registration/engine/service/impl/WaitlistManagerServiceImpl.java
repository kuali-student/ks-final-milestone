package org.kuali.student.enrollment.registration.engine.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.SearchServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handles processing of waitlists
 */
public class WaitlistManagerServiceImpl implements WaitlistManagerService {

    private SearchService searchService;

    /**
     * @param aoIds             list of aoIds that were in the RG that has free seats now.
     * @param aoid2openSeatsMap mapping of aoids to open seats. Use as a call-by-reference for seat counts per AO after
     *                          processing has completed
     * @param contextInfo       context of the call
     * @return a mapping of people ids to a list of RGs that they are currently on the waitlist for, and should now be
     * added to the course
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @Override
    public Map<String, List<String>> getPeopleToProcessFromWaitlist(List<String> aoIds, Map<String, Integer> aoid2openSeatsMap, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, List<String>> person2RgIdsResults = new HashMap<String, List<String>>();

        //Perform the search. It is assumed that the search returns results sorted by the waitlist order so that
        //A person who's RG is listed first in the results will have a higher priority than another person
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.WL_BY_AO_IDS_SEARCH_TYPE.getKey());
        searchRequest.getParams().add(new SearchParamInfo(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, aoIds));
        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);

        //Set up data structures used for processing
        if (aoid2openSeatsMap == null) {
            //If there is no reference to this map passed in, then make one here
            aoid2openSeatsMap = new HashMap<String, Integer>();
        }
        Map<String, Set<String>> rg2aoIds = new HashMap<String, Set<String>>();
        List<WaitlistInfo> waitlist = new ArrayList<WaitlistInfo>();

        //Loop through the search results
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String rgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_ID);
            String personId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.PERSON_ID);
            int seatCount = Integer.parseInt(row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.SEAT_COUNT));
            int maxSeats = Integer.parseInt(row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS));

            //Calculate the open seats for each AO that is on the waitlist
            Integer count = aoid2openSeatsMap.get(aoId);
            if (count == null) {
                aoid2openSeatsMap.put(aoId, maxSeats - seatCount);
            }

            //Store a mapping of each RG to each aoid that is on a waitlist
            Set<String> aoidsInRg = rg2aoIds.get(rgId);
            if (aoidsInRg == null) {
                aoidsInRg = new HashSet<String>();
                rg2aoIds.put(rgId, aoidsInRg);
            }
            aoidsInRg.add(aoId);

            //Store an ordered list of people and the rg they are waiting on
            waitlist.add(new WaitlistInfo(rgId, personId));
        }

        //Process the waitlist
        for (WaitlistInfo entry : waitlist) {
            String rgId = entry.rgId;
            String personId = entry.personId;

            //Check each ao in the reg group for the current person
            boolean seatAvailable = true;
            for (String aoId : rg2aoIds.get(rgId)) {
                //If there are no seats available for at least one waitlist AO then skip to the next entry
                if (aoid2openSeatsMap.get(aoId) <= 0) {
                    seatAvailable = false;
                    break;
                }
            }

            //If there is a seat available on all ao waitlist items
            if (seatAvailable) {
                for (String aoId : rg2aoIds.get(rgId)) {
                    //Decrement the open seats for each ao in the RG
                    aoid2openSeatsMap.put(aoId, aoid2openSeatsMap.get(aoId) - 1);
                }
                //Add a mapping of the person to the RG they should be added to from the waitlist
                List<String> rgIds = person2RgIdsResults.get(personId);
                if (rgIds == null) {
                    rgIds = new ArrayList<String>();
                    person2RgIdsResults.put(personId, rgIds);
                }
                rgIds.add(rgId);
            }

        }

        return person2RgIdsResults;
    }

    /**
     * Tuple to store waitlist information
     */
    private class WaitlistInfo {
        public WaitlistInfo(String rgId, String personId) {
            super();
            this.rgId = rgId;
            this.personId = personId;
        }

        public String rgId;
        public String personId;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = GlobalResourceLoader.getService(new QName(SearchServiceConstants.NAMESPACE, SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
