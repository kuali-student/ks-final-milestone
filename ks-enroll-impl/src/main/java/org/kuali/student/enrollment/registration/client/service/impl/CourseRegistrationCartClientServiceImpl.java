package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingLocationTimeResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleLocationResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleTimeResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.security.auth.login.LoginException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CourseRegistrationCartClientServiceImpl implements CourseRegistrationCartClientService {

    public static final Logger LOGGER = Logger.getLogger(CourseRegistrationCartClientServiceImpl.class);
    private CourseRegistrationService courseRegistrationService;

    @Override
    public Response submitCartRS(String userId, String cartId) {
        Response.ResponseBuilder response;

        try {
            submitCart(userId, cartId);
            response = Response.ok(Boolean.TRUE);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public RegistrationResponseInfo submitCart(String userId, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Make sure that the user is the owner of the cart!
        CourseRegistrationInfo courseRegistrationInfo = getCourseRegistrationService().getCourseRegistration(cartId, contextInfo);
        if (!StringUtils.equals(courseRegistrationInfo.getPersonId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        RegistrationResponseInfo registrationResponse = getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);

        return registrationResponse;
    }

    @Override
    public Response addCourseToCartRS(String cartId, String regGroupId, String courseCode, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = addCourseToCart(cartId, regGroupId, courseCode, regGroupCode,gradingOptionId,credits);
            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART, cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    protected Link buildDeleteLink(String uriBase, String cartId, String cartItemId, String gradingOptionId, String credits){
        String action = "removeItemFromCart";
        String uri = uriBase + "/removeItemFromCart?cartId=%s&cartItemId=%s&gradingOptionId=$s&credits=%s";
        uri = String.format(uri, cartId, cartItemId, gradingOptionId, credits );

        return new Link(action, uri);
    }

    protected Link buildAddLink(String uriBase, String cartId, String regGroupId, String gradingOptionId, String credits){
        String action = "addCourseToCart";
        String uri = uriBase + "/addCourseToCart?cartId=%s&regGroupId=%s&gradingOptionId=$s&credits=%s";
        uri = String.format(uri, cartId, regGroupId, gradingOptionId, credits );

        return new Link(action, uri);
    }

    @Override
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId, @QueryParam("gradingOptionId") String gradingOptionId, @QueryParam("credits") String credits) {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = removeItemFromCart(cartId, cartItemId, gradingOptionId, credits);
            // build the link to add this item.
            result.getActionLinks().add(buildAddLink(CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART, cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    public CartItemResult removeItemFromCart(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId, @QueryParam("gradingOptionId") String gradingOptionId, @QueryParam("credits") String credits) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        RegistrationRequestItem removedItem = null;
        for(int i=0; i < cart.getRegistrationRequestItems().size(); i++){
            if(cart.getRegistrationRequestItems().get(i).getId().equals(cartItemId)){
                removedItem = cart.getRegistrationRequestItems().remove(i);
                break;
            }
        }

        getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

        CartItemResult cartItemInfo = new CartItemResult();
        cartItemInfo.setRegGroupId(removedItem.getRegistrationGroupId());
        cartItemInfo.setCredits(credits);
        cartItemInfo.setGrading(gradingOptionId);

        return cartItemInfo;

    }


    protected CartItemResult addCourseToCart(String cartId, String regGroupId, String courseCode, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // getting cart
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);
        List<String> registrationRequestIds = new ArrayList<String>();
        for (RegistrationRequestItemInfo item : cart.getRegistrationRequestItems()) {
            registrationRequestIds.add(item.getId());
        }

        // get the regGroup
        RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(cart.getTermId(), null, courseCode, regGroupCode, regGroupId, contextInfo);

        // Create new reg request item and add it to the cart
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(cart.getRequestorId(), rg.getRegGroupId(), credits, gradingOptionId);
        cart.getRegistrationRequestItems().add(registrationRequestItem);
        RegistrationRequestInfo info = getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

        CartItemResult cartItemInfo = getCartItemInfoResult(rg.getCourseOfferingId(), rg.getActivityOfferingIds(), contextInfo); // populates item with schedule info
        // looking for new item
        for (RegistrationRequestItemInfo item : info.getRegistrationRequestItems()) {
            if (!registrationRequestIds.contains(item.getId())) {
                cartItemInfo.setCartItemId(item.getId());
            }
        }
        cartItemInfo.setCourseCode(courseCode);
        cartItemInfo.setRegGroupId(rg.getRegGroupId());
        cartItemInfo.setRegGroupCode(regGroupCode);
        cartItemInfo.setCredits(credits);
        cartItemInfo.setGrading(gradingOptionId);

        return cartItemInfo;
    }

    @Override
    public Response updateCartItemRS(String userId, String cartId, String cartItemId, String credits, String grading) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(updateCartItem(userId, cartId, cartItemId, credits, grading));
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    //This will need to be changed to the cartItemResponse object in the future!
    public RegistrationRequestItemInfo updateCartItem(String userId, String cartId, String cartItemId, String credits, String grading) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Get the Cart from services
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Check that it is the users' cart
        if (!StringUtils.equals(cart.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to edit items on this registration cart");
        }

        //Find the matching cartItem id to edit the registration options
        for (RegistrationRequestItemInfo requestItem : cart.getRegistrationRequestItems()) {
            if (StringUtils.equals(cartItemId, requestItem.getId())) {
                //Set the Item registration options
                requestItem.setCredits(new KualiDecimal(credits));
                requestItem.setGradingOptionId(grading);
                //Save the newly updated cart
                RegistrationRequestInfo updatedCart = getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);
                //Look for the updated request item to return
                for (RegistrationRequestItemInfo updatedRequestItem : updatedCart.getRegistrationRequestItems()) {
                    if (StringUtils.equals(cartItemId, updatedRequestItem.getId())) {
                        return updatedRequestItem;
                    }
                }
            }
        }
        throw new DoesNotExistException("No matching cart item was found.");
    }

    private CartItemResult getCartItemInfoResult(String coId, List<String> aoIds, ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        CartItemResult cartItemInfo = new CartItemResult();
        cartItemInfo.setSchedule(new ArrayList<ActivityOfferingScheduleResult>());
        HashMap<String, ActivityOfferingScheduleResult> hmSchedules = new HashMap<String, ActivityOfferingScheduleResult>();

        CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
        courseOfferingInfo.setId(coId);
        courseOfferingInfo.setStudentRegistrationGradingOptions(new ArrayList<String>());

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_TYPE.getKey());
        List<String> luiIDs = new ArrayList<String>(aoIds);
        luiIDs.add(coId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LUI_IDS, luiIDs);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of activity offering schedules failed: ", e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            String luiId = "", luiName = "", luiLongName = "", resultValuesGroupKey = "",
                    roomCode = "", buildingCode = "", weekdays = "", startTimeMs = "", endTimeMs = "";
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID.equals(cellInfo.getKey())) {
                    luiId = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_NAME.equals(cellInfo.getKey())) {
                    luiName = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME.equals(cellInfo.getKey())) {
                    luiLongName = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.RES_VAL_GROUP_KEY.equals(cellInfo.getKey())) {
                    resultValuesGroupKey = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cellInfo.getKey())) {
                    roomCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE.equals(cellInfo.getKey())) {
                    buildingCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cellInfo.getKey())) {
                    weekdays = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS.equals(cellInfo.getKey())) {
                    startTimeMs = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS.equals(cellInfo.getKey())) {
                    endTimeMs = cellInfo.getValue();
                }
            }

                // Creating CO if it's the one
            if (StringUtils.equals(luiId, coId)) {
                cartItemInfo.setCourseTitle(luiLongName);
                if (resultValuesGroupKey != null && resultValuesGroupKey.startsWith("kuali.creditType.credit")) {
                    courseOfferingInfo.setCreditOptionId(resultValuesGroupKey);
                } else if (resultValuesGroupKey != null && ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValuesGroupKey)) {
                    courseOfferingInfo.getStudentRegistrationGradingOptions().add(resultValuesGroupKey);
                }
            }

            // setting location and time
            // AO location
            ScheduleLocationResult location = new ScheduleLocationResult();
            location.setBuilding(buildingCode);
            location.setRoom(roomCode);
            // AO times
            ScheduleTimeResult time = new ScheduleTimeResult();
            TimeOfDayInfo startTime = TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs));
            time.setStartTime(TimeOfDayHelper.formatTimeOfDay(startTime));
            TimeOfDayInfo endTime = TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs));
            time.setEndTime(TimeOfDayHelper.formatTimeOfDay(endTime));
            time.setDays(weekdays);
            // Combining location + time for AO
            ActivityOfferingLocationTimeResult aoLocationTime = new ActivityOfferingLocationTimeResult();
            aoLocationTime.setLocation(location);
            aoLocationTime.setTime(time);
            // now add location + time to the final result
            if (!hmSchedules.containsKey(luiId)) {
                ActivityOfferingScheduleResult aoSchedule = new ActivityOfferingScheduleResult();
                aoSchedule.setActivityOfferingType(luiName);
                List<ActivityOfferingLocationTimeResult> aoLocationTimes = new ArrayList<ActivityOfferingLocationTimeResult>();
                aoLocationTimes.add(aoLocationTime);
                aoSchedule.setActivityOfferingLocationTime(aoLocationTimes);
            } else {
                hmSchedules.get(luiId).getActivityOfferingLocationTime().add(aoLocationTime);
            }
        }

        // setting AO schedules
        for (Map.Entry<String, ActivityOfferingScheduleResult> pair : hmSchedules.entrySet()) {
            cartItemInfo.getSchedule().add(pair.getValue());
        }

        // Setting Grading Options and Credits (from CO)
        cartItemInfo = setRegistrationRequestCreditsGradingOptions(cartItemInfo, courseOfferingInfo, contextInfo);

        return cartItemInfo;
    }

    private CartItemResult setRegistrationRequestCreditsGradingOptions(CartItemResult cartItemInfo, CourseOfferingInfo courseOfferingInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,  DoesNotExistException {
        int firstValue = 0;

        // checking grading option. If null - just keep it that way
        if (!courseOfferingInfo.getStudentRegistrationGradingOptions().isEmpty()) {
            HashMap<String, String> hmGradingOptions = new HashMap<String, String>();
            for (String gradingOptionKey : courseOfferingInfo.getStudentRegistrationGradingOptions()) {
                if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                    hmGradingOptions.put(gradingOptionKey, "Audit");
                } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER)){
                    hmGradingOptions.put(gradingOptionKey, "Letter");
                } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                    hmGradingOptions.put(gradingOptionKey, "Pass/Fail");
                }
            }
            cartItemInfo.setGradingOptions(hmGradingOptions);
        }

        //Lookup the selected credit option and set from persisted values
        if (!courseOfferingInfo.getCreditOptionId().isEmpty()) {
            //Lookup the resultValueGroup Information
            ResultValuesGroupInfo resultValuesGroupInfo = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesGroup(courseOfferingInfo.getCreditOptionId(), contextInfo);
            String typeKey = resultValuesGroupInfo.getTypeKey();

            //Get the actual values
            List<ResultValueInfo> resultValueInfos = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

            if (!resultValueInfos.isEmpty()) {
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    cartItemInfo.getCreditOptions().add(resultValueInfos.get(firstValue).getValue()); // fixed credits
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {  // range
                    int minValue = Integer.parseInt(resultValuesGroupInfo.getResultValueRange().getMinValue());
                    int maxValue = Integer.parseInt(resultValuesGroupInfo.getResultValueRange().getMaxValue());
                    List<String> creditOptions = new ArrayList<String>();
                    for (int i = minValue; i <= maxValue; i++ ) {
                        creditOptions.add(Integer.toString(i));
                    }
                    cartItemInfo.setCreditOptions(creditOptions);
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {  // multiple
                    List<String> creditOptions = new ArrayList<String>();
                    for (ResultValueInfo resultValueInfo : resultValueInfos) {
                        creditOptions.add(resultValueInfo.getValue());
                    }
                    cartItemInfo.setCreditOptions(creditOptions);
                }
            }
        }

        return cartItemInfo;
    }

    private ContextInfo getContextAndCheckLogin(String userId) throws LoginException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        }

        if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("User must be logged in to access this service");
        }

        return contextInfo;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
