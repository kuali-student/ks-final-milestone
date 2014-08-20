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
 * Created by Charles on 7/21/14
 */
package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.OrderByField;
import org.kuali.rice.core.api.criteria.OrderDirection;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationAdminClientService;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.WaitlistEntryResult;
import org.kuali.student.enrollment.registration.client.service.dto.WaitlistPositionResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.statistics.RegEngineMqStatisticsGenerator;
import org.kuali.student.enrollment.registration.engine.util.MQPerformanceCounter;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class contains non-student facing methods for the registration system.
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationAdminClientServiceImpl extends CourseRegistrationCartServiceImpl implements CourseRegistrationAdminClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationAdminClientServiceImpl.class);

    private LprService lprService;

    // this comparator is used to sort the reg req items in the order they are displayed on the screen.
    // allows us to validate in order.
    protected static Comparator<LprInfo> LPR_INFO_CREATE_DATE = new Comparator<LprInfo>() {

        @Override
        public int compare(LprInfo o1, LprInfo o2) {
            int ret = 0;
            try {
                ret = o1.getMeta().getCreateTime().compareTo(o2.getMeta().getCreateTime());
            } catch (NullPointerException ex) {
                LOGGER.error("Error comparing reg request meta data", ex);
            }
            return ret;
        }
    };

    @Override
    public Response getRegEngineStats() {
        Response.ResponseBuilder response;

        try {
            Map<String, List> stats = getStatsFromRegEngine();
            response = Response.ok(stats);
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    private Map<String, List> getStatsFromRegEngine() throws JMSException {

        // define types of stats to collect
        List<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType> statTypesToRequest = new LinkedList<RegEngineMqStatisticsGenerator.RegistrationEngineStatsType>();
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.BROKER);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.INITIALIZATION_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.VERIFICATION_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.REQ_ITEM_SPLIT_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.LPR_ACTION_QUEUE);
        statTypesToRequest.add(RegEngineMqStatisticsGenerator.RegistrationEngineStatsType.REGISTRATION_ENGINE_STATS);

        // collect the stats
        RegEngineMqStatisticsGenerator generator = new RegEngineMqStatisticsGenerator();
        generator.initiateRequestForStats(statTypesToRequest);

        return generator.getStats();
    }

    @Override
    public Response clearRegEngineStats() {

        Response.ResponseBuilder response;

        try {
            // This might not be the best way to do this...
            // I would rather have one point of entry into a singleton but
            // this is incredibly easy.
            MQPerformanceCounter.INSTANCE.clearPerformanceStats();

            response = Response.fromResponse(getRegEngineStats());
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * Finds all LPRs for a given personId and deletes them. If term is passed - deletes LPRs only for that term.
     * Returns an empty List of StudentScheduleCourseResult.
     *
     * This method goes directly to the database, bypassing the registration engine.
     *
     * @param personId Principal ID
     * @param termId - optional
     * @param termCode - optional, human readable code representing the term. ex: 201208
     * @return Empty Response Object or Response object with Error text
     */
    @Override
    public Response clearLPRsByPersonRS(String personId, String termId, String termCode) {
        Response.ResponseBuilder response;
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            List<LprInfo> lprs = new ArrayList<LprInfo>();
            if (StringUtils.isEmpty(termId) && StringUtils.isEmpty(termCode)) {
                lprs = CourseRegistrationAndScheduleOfClassesUtil.getLprService().getLprsByPerson(personId, contextInfo);
            } else {
                termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
                lprs = CourseRegistrationAndScheduleOfClassesUtil.getLprService().getLprsByPersonAndAtp(personId, termId, contextInfo);
            }
            for (LprInfo lprInfo : lprs) {
                CourseRegistrationAndScheduleOfClassesUtil.getLprService().deleteLpr(lprInfo.getId(), contextInfo);
            }
            response = Response.noContent();
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response clearCartByPersonRS(String personId, String termId, String termCode){
        Response.ResponseBuilder response;
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            // get termId
            termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);

            super.clearCartByPerson(personId,termId,contextInfo);
            response = Response.noContent();
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response getWaitlistRoster(String termId, String termCode, String courseCode, String regGroupCode) {
        Response.ResponseBuilder response;

        try {
            List<WaitlistEntryResult> wlSearchResults = searchForWaitlistRosterLocal(termId, termCode, courseCode, regGroupCode, ContextUtils.createDefaultContextInfo());
            response = Response.ok(wlSearchResults);
        } catch (Exception e) {
            LOGGER.warn("Exception Thrown", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }


    private List<WaitlistEntryResult> searchForWaitlistRosterLocal(String termId, String termCode, String courseCode, String regGroupCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<WaitlistEntryResult>  results =   new ArrayList<WaitlistEntryResult>();
        RegGroupSearchResult regGroupSearchResult =  getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termId, termCode, courseCode, regGroupCode, contextInfo);
        String PRIMARY_WAITLIST_TYPE = LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY; // for this implementation the RG is the primary
        // array of valid waitlist types. We might want to pass this in to make it configurable?
        String[] wlTypes = {LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY,
                LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY,
                LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY};

        // LUI ID  -> Count
        Map<String, Integer> typeCountMap = new HashMap<String, Integer>();

        // userId, WaitlistEntry
        Map<String, WaitlistEntryResult> resultHelperMap = new HashMap<String, WaitlistEntryResult>(); // this is used to help populate result map. needed for performance.

        if (regGroupSearchResult != null) {

            // get all the waitlist lprs for this registration group
            // combine the rgId + aoIds to pass into search method
            List<String> luiIdsToFind = new ArrayList<String>(regGroupSearchResult.getActivityOfferingIds());
            luiIdsToFind.add(regGroupSearchResult.getRegGroupId());
            // they will be in process order
            List<LprInfo> lprInfos = getLprEntries(luiIdsToFind, contextInfo, wlTypes);

            // loop through the results in order. Because we're in order we can create an easy count object.
            for (LprInfo lprInfo : lprInfos) {
                // prime the counts
                if (!typeCountMap.containsKey(lprInfo.getLuiId())) {
                    typeCountMap.put(lprInfo.getLuiId(), 0);
                }

                // get / set the count
                int count = typeCountMap.get(lprInfo.getLuiId()) + 1; // increment count
                typeCountMap.put(lprInfo.getLuiId(), count); // update map

                // use the result helper to organize the results + more performant
                if (!resultHelperMap.containsKey(lprInfo.getPersonId())) {
                    WaitlistEntryResult wlEntry =  new WaitlistEntryResult();
                    wlEntry.setPersonId(lprInfo.getPersonId());

                    // use pass by ref to update both the ret list and the helper
                    resultHelperMap.put(lprInfo.getPersonId(), wlEntry);
                }

                // The primary and remaining need to be seperated.
                if (PRIMARY_WAITLIST_TYPE.equals(lprInfo.getTypeKey())) {
                    WaitlistEntryResult wlEntry = resultHelperMap.get(lprInfo.getPersonId());
                    wlEntry.setPosition(count);
                    wlEntry.setPrimaryActivityType(lprInfo.getTypeKey());
                    wlEntry.setPrimaryLprId(lprInfo.getMasterLprId()); // I believe this is the same as the lprId.
                    wlEntry.setPrimaryLuiId(lprInfo.getLuiId());

                    results.add(wlEntry);  // add entry to return list. Only happens here bc it's primary
                } else {
                    WaitlistPositionResult countResult = new WaitlistPositionResult();
                    countResult.setPosition(count);
                    countResult.setCountType(lprInfo.getTypeKey());
                    countResult.setLuiId(lprInfo.getLuiId());

                    resultHelperMap.get(lprInfo.getPersonId()).getAoWaitlistOrder().add(countResult);

                }
            }
        }
        return results;

    }


    /**
     * Return a list of LprInfo objects that are related to the luiIds passed in.
     *
     * This method looks up lprs based on the luiIds passed in. It filters based on the type.
     * It will only return items that DO NOT have an expirationDate
     * The results will be in Ascending Order based on the lpr createTime
     *
     * @param luiIds - list of luiIds to look up for the LPR table
     * @param contextInfo - context info
     * @param lprTypes list of valid lpr types to filter results. ie. LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY
     * @return A list of lprinfo objects related to the luiids passed in
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private List<LprInfo> getLprEntries(List<String> luiIds, ContextInfo contextInfo, String ... lprTypes) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {


        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(PredicateFactory.in("luiId", luiIds),
                PredicateFactory.in("personRelationTypeId", lprTypes),
                PredicateFactory.isNull("expirationDate")));  // all LPRs use effective dating

        OrderByField.Builder orderByFieldBuilder = OrderByField.Builder.create();
        orderByFieldBuilder.setFieldName("createTime");
        orderByFieldBuilder.setOrderDirection(OrderDirection.DESCENDING);
        qbcBuilder.setOrderByFields(orderByFieldBuilder.build());

        QueryByCriteria criteria = qbcBuilder.build();

        List<LprInfo> lprInfos =  getLprService().searchForLprs(criteria, contextInfo);

        sortLprsForWaitlistProcessing(lprInfos);

        return lprInfos;
    }

    /**
     * pass by ref sorting helper method. defaults to decending order by create date
     * @param lprInfos
     */
    protected void sortLprsForWaitlistProcessing(List<LprInfo> lprInfos){
        Collections.sort(lprInfos,LPR_INFO_CREATE_DATE);
    }



}
