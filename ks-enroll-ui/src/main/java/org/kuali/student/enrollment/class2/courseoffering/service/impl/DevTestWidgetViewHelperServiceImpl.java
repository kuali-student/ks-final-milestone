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
 * Created by Charles on 9/26/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.form.DevTestWidgetForm;
import org.kuali.student.enrollment.class2.courseoffering.service.DevTestWidgetViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
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
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is being used primarily to test various service calls within the application as part
 * of development.
 *
 * @author Kuali Student Team
 */
public class DevTestWidgetViewHelperServiceImpl extends ViewHelperServiceImpl implements DevTestWidgetViewHelperService {
    private static final Logger LOGGER = Logger.getLogger(DevTestWidgetViewHelperServiceImpl.class);
    private static final String LETTER_GRADE = "kuali.resultComponent.grade.letter";
    private static final String SPRING_2012_TERM = "kuali.atp.2012Spring";

    private ContextInfo CONTEXT;
    private List<String> regRequestIds = new ArrayList<String>();

    public DevTestWidgetViewHelperServiceImpl() {
        CONTEXT = ContextUtils.createDefaultContextInfo();
        CONTEXT.setPrincipalId("admin");
        CONTEXT.setCurrentDate(new Date());
    }

    @Override
    public void runAlphaTest(DevTestWidgetForm form) throws Exception {
        LOGGER.info("Running Alpha test");
        persistRegRequests();
    }

    @Override
    public void runBetaTest(DevTestWidgetForm form) throws Exception {
        LOGGER.info("Running Beta test");
        submitRequests();
    }

    @Override
    public void runGammaTest(DevTestWidgetForm form) throws Exception {
        LOGGER.info("Running Gamma test");
        testSeatCountService();
    }

    @Override
    public void runDeltaTest(DevTestWidgetForm form) throws Exception {
        LOGGER.info("Running Delta test");
    }

    private void persistRegRequests() throws DataValidationErrorException, PermissionDeniedException,
            OperationFailedException, AlreadyExistsException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DoesNotExistException {
        CourseRegistrationService courseRegistrationService =
                CourseOfferingManagementUtil.getCourseRegistrationService();
        for (String regRequestId: regRequestIds) {
            courseRegistrationService.deleteRegistrationRequest(regRequestId, CONTEXT);
        }
        regRequestIds.clear();
        persistRegRequestsFor("admin");
        persistRegRequestsFor("carol");
        System.err.println("DONE");
    }

    private void persistRegRequestsFor(String personId)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException, AlreadyExistsException {
        RegistrationRequestItemInfo itemInfo = new RegistrationRequestItemInfo();
        itemInfo.setPersonId(personId);
        itemInfo.setRegistrationGroupId("647a2b36-af91-456a-8ff2-a4cd51fcdcb5");
        itemInfo.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        itemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        itemInfo.setCredits(new KualiDecimal("3.5"));
        itemInfo.setGradingOptionId(LETTER_GRADE); // Fill in

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setTermId(SPRING_2012_TERM);
        request.setRequestorId(personId);
        request.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        request.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        // Add the item
        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        request.getRegistrationRequestItems().add(itemInfo);

        // Now create and test
        CourseRegistrationService courseRegistrationService =
                CourseOfferingManagementUtil.getCourseRegistrationService();
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);
        regRequestIds.add(requestResult.getId());
        System.err.println("DONE");
    }

    private void submitRequests() throws InvalidParameterException, PermissionDeniedException,
            OperationFailedException, AlreadyExistsException, MissingParameterException, DoesNotExistException {
        CourseRegistrationService courseRegistrationService =
                CourseOfferingManagementUtil.getCourseRegistrationService();
        for (String regRequestId: regRequestIds) {
            RegistrationResponseInfo resp =
                courseRegistrationService.submitRegistrationRequest(regRequestId, CONTEXT);
        }
        System.err.println("DONE");
    }

    private void testSeatCountService()
            throws OperationFailedException, PermissionDeniedException, MissingParameterException,
            InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            AlreadyExistsException {
        CourseSeatCountService seatCountService =
                CourseOfferingManagementUtil.getCourseSeatCountService();
        String regGroupId = "647a2b36-af91-456a-8ff2-a4cd51fcdcb5";
        RegistrationGroupInfo rgInfo =
                CourseOfferingManagementUtil.getCourseOfferingService().getRegistrationGroup(regGroupId, CONTEXT);
        List<String> aoIds = rgInfo.getActivityOfferingIds();

        seatCountService.getSeatCountsForActivityOfferings(aoIds, CONTEXT);
        System.err.println("DONE");
    }


    private void testRegRequest() throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException, AlreadyExistsException {
        String regGroupId = "647a2b36-af91-456a-8ff2-a4cd51fcdcb5";

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_KEY_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, "admin");
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, SPRING_2012_TERM);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.TYPE_KEY,
                LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, CONTEXT);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lprTrans for person 'admin' and term '2012Spring' failed: ", e);
        }

        RegistrationRequestItemInfo itemInfo = new RegistrationRequestItemInfo();
        itemInfo.setPersonId("admin");
        itemInfo.setRegistrationGroupId("647a2b36-af91-456a-8ff2-a4cd51fcdcb5");
        itemInfo.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        itemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        itemInfo.setCredits(new KualiDecimal("3.5"));
        itemInfo.setGradingOptionId(LETTER_GRADE); // Fill in

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setTermId(SPRING_2012_TERM);
        request.setRequestorId("admin");
        request.setTypeKey(LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        request.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        // Add the item
        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        request.getRegistrationRequestItems().add(itemInfo);

        // Now create and test
        CourseRegistrationService courseRegistrationService =
                CourseOfferingManagementUtil.getCourseRegistrationService();
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);
        RegistrationResponseInfo resp =
                courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, CONTEXT);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lprTrans for person 'admin' and term '2012Spring' failed: ", e);
        }
        LOGGER.info("Done");
    }

    private void testFetchLprsByAoIds() throws OperationFailedException, PermissionDeniedException,
            MissingParameterException, InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, AlreadyExistsException {
        String regGroupId = "647a2b36-af91-456a-8ff2-a4cd51fcdcb5";
        RegistrationGroupInfo rgInfo =
                CourseOfferingManagementUtil.getCourseOfferingService().getRegistrationGroup(regGroupId, CONTEXT);
        List<String> aoIds = rgInfo.getActivityOfferingIds();

        RegistrationRequestItemInfo itemInfo = new RegistrationRequestItemInfo();
        itemInfo.setPersonId("admin");
        itemInfo.setRegistrationGroupId("647a2b36-af91-456a-8ff2-a4cd51fcdcb5");
        itemInfo.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        itemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        itemInfo.setCredits(new KualiDecimal("3.5"));
        itemInfo.setGradingOptionId(LETTER_GRADE); // Fill in

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setTermId(SPRING_2012_TERM);
        request.setRequestorId("admin");
        request.setTypeKey(LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        request.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        // Add the item
        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        request.getRegistrationRequestItems().add(itemInfo);

        // Now create and test
        CourseRegistrationService courseRegistrationService =
                CourseOfferingManagementUtil.getCourseRegistrationService();
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);
        RegistrationResponseInfo resp =
                courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPRS_BY_AOIDS_LPR_STATE_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, aoIds);
        List<String> lprStates = new ArrayList<String>();
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LPR_STATES, lprStates);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, CONTEXT);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lprTrans for person 'admin' and term '2012Spring' failed: ", e);
        }
        LOGGER.info("Done");
    }

    private void testAoCount() throws OperationFailedException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AOIDS_COUNT_SEARCH_TYPE.getKey());
        List<String> aoIds = new ArrayList<String>();
        aoIds.add("0037fe37-205a-4332-b822-030938d5dc80"); // lab
        aoIds.add("00390418-8263-43d8-87ca-2b7998692000"); // lecture
        aoIds.add("00384c9c-9961-41a5-ad4e-6431d27a9d9b"); // reg group
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, aoIds);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, CONTEXT);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lprTrans for person 'admin' and term '2012Spring' failed: ", e);
        }
    }

    private void testAoMaxSeats() throws OperationFailedException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AOIDS_TYPE_MAXSEATS_SEARCH_TYPE.getKey());
        List<String> aoIds = new ArrayList<String>();
        aoIds.add("0037fe37-205a-4332-b822-030938d5dc80"); // lab
        aoIds.add("00390418-8263-43d8-87ca-2b7998692000"); // lecture
        aoIds.add("00384c9c-9961-41a5-ad4e-6431d27a9d9b"); // reg group
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, aoIds);

        SearchResultInfo searchResult;
        try {
            SearchService searchService = CourseRegistrationAndScheduleOfClassesUtil.getSearchService();
            searchResult = searchService.search(searchRequest, CONTEXT);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lprTrans for person 'admin' and term '2012Spring' failed: ", e);
        }
    }
}

