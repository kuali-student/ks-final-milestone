package org.kuali.student.enrollment.registration.engine.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
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
    private CourseRegistrationService courseRegistrationService;

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
    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds, Map<String, Integer> aoid2openSeatsMap, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        List<WaitlistInfo> results = new ArrayList<WaitlistInfo>();

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
        Set<String> alreadyProcessedLprIds = new HashSet<String>();
        //Loop through the search results
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String rgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_ID);
            String masterLprId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.MASTER_LPR_ID);
            String personId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.PERSON_ID);
            String atpId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_ID);
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

            //Only add one WL record for each Master LPR otherwise we would have a record per AO
            if(!alreadyProcessedLprIds.contains(masterLprId)){
                //Store an ordered list of people and the rg they are waiting on
                waitlist.add(new WaitlistInfo(rgId, personId, masterLprId, atpId));
            }
            alreadyProcessedLprIds.add(masterLprId);
        }

        //Process the waitlist
        for (WaitlistInfo entry : waitlist) {

            //Check each ao in the reg group for the current person
            boolean seatAvailable = true;
            for (String aoId : rg2aoIds.get(entry.rgId)) {
                //If there are no seats available for at least one waitlist AO then skip to the next entry
                if (aoid2openSeatsMap.get(aoId) <= 0) {
                    seatAvailable = false;
                    break;
                }
            }

            //If there is a seat available on all ao waitlist items
            if (seatAvailable) {
                for (String aoId : rg2aoIds.get(entry.rgId)) {
                    //Decrement the open seats for each ao in the RG
                    aoid2openSeatsMap.put(aoId, aoid2openSeatsMap.get(aoId) - 1);
                }
                //Add a mapping of the person to the RG they should be added to from the waitlist
                results.add(entry);
            }

        }

        return results;
    }

    @Override
    public List<RegistrationRequest> processPeopleOffOfWaitlist(List<String> aoIds, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException, AlreadyExistsException {

        List<RegistrationRequest> createdRegRequests = new ArrayList<RegistrationRequest>();

        //Get an ordered list of people to process off of the waitlist
        List<WaitlistInfo> waitlistInfos = getPeopleToProcessFromWaitlist (aoIds, null, contextInfo);
        Map<String, RegistrationRequestInfo> person2RegRequest = new HashMap<String, RegistrationRequestInfo>();

        if(!waitlistInfos.isEmpty()){
            for(WaitlistInfo waitlistInfo : waitlistInfos){
                //Make a new reg request for each person being processed off of the waitlist
                RegistrationRequestInfo regRequest = person2RegRequest.get(waitlistInfo.personId);
                if(regRequest == null){
                    regRequest = new RegistrationRequestInfo();
                    regRequest.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
                    regRequest.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
                    regRequest.setTermId(waitlistInfo.atpId);
                    regRequest.setRequestorId(contextInfo.getPrincipalId());//Not sure if this is the correct id to set here
                    person2RegRequest.put(waitlistInfo.personId, regRequest);
                }

                //Add a reg request item to process the person off of the waitlist
                RegistrationRequestItemInfo item = new RegistrationRequestItemInfo();
                item.setExistingCourseRegistrationId(waitlistInfo.masterLprId);
                item.setRegistrationGroupId(waitlistInfo.rgId);
                item.setPersonId(waitlistInfo.personId);
                item.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_FROM_WAITLIST_TYPE_KEY);
                item.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
                regRequest.getRegistrationRequestItems().add(item);
            }

            //Use the CourseRegistrationService to create and submit the requests
            for(RegistrationRequestInfo regRequest:person2RegRequest.values()){
                RegistrationRequest createdRegRequest = getCourseRegistrationService().createRegistrationRequest(regRequest.getTypeKey(), regRequest, contextInfo);
                getCourseRegistrationService().submitRegistrationRequest(createdRegRequest.getId(), contextInfo);
                createdRegRequests.add(createdRegRequest);
            }
        }

        return createdRegRequests;
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

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

}
