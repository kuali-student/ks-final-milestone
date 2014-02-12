package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingLocationTimeResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationOptionResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleLocationResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleTimeResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.transaction.annotation.Transactional;

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
    private LprService lprService;
    private AtpService atpService;

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
    public Response addCourseToCartRS(String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = addCourseToCart(cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);
            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART, cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Exception e) {
            LOGGER.warn("Error adding to cart", e);
            response = Response.serverError().entity(e.getMessage());
            response.header("Access-Control-Allow-Header", "Content-Type");
            response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.header("Access-Control-Allow-Origin", "*");
        }

        return response.build();
    }

    protected Link buildDeleteLink(String uriBase, String cartId, String cartItemId, String gradingOptionId, String credits) {
        String action = "removeItemFromCart";
        String uri = uriBase + "/removeItemFromCart?cartId=%s&cartItemId=%s&gradingOptionId=%s&credits=%s";
        uri = String.format(uri, cartId, cartItemId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    protected Link buildAddLink(String uriBase, String cartId, String regGroupId, String gradingOptionId, String credits) {
        String action = "addCourseToCart";
        String uri = uriBase + "/addCourseToCart?cartId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        uri = String.format(uri, cartId, regGroupId, gradingOptionId, credits);

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
        for (int i = 0; i < cart.getRegistrationRequestItems().size(); i++) {
            if (cart.getRegistrationRequestItems().get(i).getId().equals(cartItemId)) {
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

    @Transactional
    protected CartItemResult addCourseToCart(String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // getting cart
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Save all the existing ids
        List<String> registrationRequestIds = new ArrayList<String>();
        for (RegistrationRequestItemInfo item : cart.getRegistrationRequestItems()) {
            registrationRequestIds.add(item.getId());
        }

        // get the regGroup
        RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(cart.getTermId(), null, courseCode, regGroupCode, regGroupId, contextInfo);

        CartItemResult optionsCart = new CartItemResult();
        Map<String, CartItemResult> optionsMap = new HashMap<String, CartItemResult>();
        optionsMap.put(rg.getCourseOfferingId(), optionsCart);
        populateOptions(optionsMap, contextInfo);

        //Default values and check if options are allowed for the course.
        //Check with BAs about how to default.
        if (StringUtils.isEmpty(credits)) {
            credits = KSCollectionUtils.getOptionalZeroElement(optionsCart.getCreditOptions());
        } else if (!optionsCart.getCreditOptions().contains(credits)) {
            throw new InvalidParameterException("Credit option " + credits + " is not valid for this course: " + courseCode + "(" + regGroupCode + ")");
        }
        if (StringUtils.isEmpty(gradingOptionId)) {
            if(optionsCart.getGradingOptions().containsKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER)){
                gradingOptionId = LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER;
            }else{
                gradingOptionId = optionsCart.getGradingOptions().keySet().iterator().next();
            }
        } else if (!optionsCart.getGradingOptions().containsKey(gradingOptionId)) {
            throw new InvalidParameterException("Grading option " + gradingOptionId + " is not valid for this course: " + courseCode + "(" + regGroupCode + ")");
        }

        // Create new reg request item and add it to the cart
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(cart.getRequestorId(), rg.getRegGroupId(), credits, gradingOptionId);
        registrationRequestItem.setMeta(new MetaInfo());
        registrationRequestItem.getMeta().setCreateId(cart.getMeta().getCreateId());//TODO KSENROLL-11755 we need a  better way to handle userIds (add as param in RS)
        cart.getRegistrationRequestItems().add(registrationRequestItem);
        //Persist the new cart with the newly added item
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

        // looking for new item
        String newCartItemId = null;
        for (RegistrationRequestItemInfo cartItem : cartRegistrationRequest.getRegistrationRequestItems()) {
            if (!registrationRequestIds.contains(cartItem.getId())) {
                newCartItemId = cartItem.getId();
            }
        }
        if (StringUtils.isEmpty(newCartItemId)) {
            throw new OperationFailedException("Can't find the newly added cart item.");
        }
        //Get the cart result with the item id to trim it down
        CartResult cartResult = getCartForUserAndTerm(cart.getRequestorId(), cart.getTermId(), newCartItemId, false, contextInfo);
        CartItemResult cartItemResult = KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());

        //populate the options we have already calculated
        cartItemResult.setGradingOptions(optionsCart.getGradingOptions());
        cartItemResult.setCreditOptions(optionsCart.getCreditOptions());

        //Return just the item
        return cartItemResult;
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
        Map<String, ActivityOfferingScheduleResult> hmSchedules = new HashMap<String, ActivityOfferingScheduleResult>();

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

    private CartItemResult setRegistrationRequestCreditsGradingOptions(CartItemResult cartItemInfo, CourseOfferingInfo courseOfferingInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        int firstValue = 0;

        // checking grading option. If null - just keep it that way
        if (!courseOfferingInfo.getStudentRegistrationGradingOptions().isEmpty()) {
            HashMap<String, String> hmGradingOptions = new HashMap<String, String>();
            for (String gradingOptionKey : courseOfferingInfo.getStudentRegistrationGradingOptions()) {
                String gradingOptionName = translateGradingOptionKeyToName(gradingOptionKey);
                if (!StringUtils.isEmpty(gradingOptionName)) {
                    hmGradingOptions.put(gradingOptionKey, gradingOptionName);
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
                    for (int i = minValue; i <= maxValue; i++) {
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

    @Override
    public RegistrationOptionResult getStudentRegistrationOptions(String courseCode, String termId, String regGroupId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        List<CourseSearchResult> courses = CourseRegistrationAndScheduleOfClassesUtil.getScheduleOfClassesService().searchForCourseOfferingsByTermIdAndCourse(termId, courseCode);
        CourseSearchResult exactMatch = new CourseSearchResult();
        for (CourseSearchResult courseSearchResult : courses) {
            if (courseSearchResult.getCourseOfferingCode().equalsIgnoreCase(courseCode)) {
                exactMatch = courseSearchResult;
            }
        }

        CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(exactMatch.getCourseOfferingId(), courseCode, termId, CourseRegistrationAndScheduleOfClassesUtil.getAtpService().getAtp(termId, context).getCode());
        RegistrationOptionResult registrationOptionResult = new RegistrationOptionResult();
        registrationOptionResult.setCourseCode(courseCode);
        registrationOptionResult.setTermId(termId);
        registrationOptionResult.setRegGroupId(regGroupId);
        registrationOptionResult.setGradingOptions(courseOfferingInfo.getStudentRegistrationGradingOptions());
        registrationOptionResult.setCredits(courseOfferingInfo.getCreditOptionId());

        return registrationOptionResult;


    }


    private String translateGradingOptionKeyToName(String gradingOptionKey) {
        if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
            return "Audit";
        } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER)) {
            return "Letter";
        } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
            return "Pass/Fail";
        }
        return null;
    }

    @Override
    public Response searchForCartRS(String userId, String termId) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(searchForCart(userId, termId));
        } catch (Exception e) {
            LOGGER.warn("Error", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    @Transactional
    public CartResult searchForCart(String userId, String termId) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        if (termId == null) {
            throw new InvalidParameterException("Term Id cannot be null.");
        }

        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Verify that the Atp exists.
        getAtpService().getAtp(termId, contextInfo);

        CartResult cartResult = getCartForUserAndTerm(userId, termId, null, true, contextInfo);

        if (cartResult == null) {
            String cartId = searchForCartId(userId, termId, contextInfo);
            cartResult = new CartResult();
            cartResult.setCartId(cartId);
            cartResult.setTermId(termId);
            if (cartId == null) {
                RegistrationRequestInfo request = createCart(userId, termId, contextInfo);
                cartResult.setCartId(request.getId());
            }

        }

        return cartResult;
    }

    private String searchForCartId(String userId, String termId, ContextInfo contextInfo) throws OperationFailedException, DoesNotExistException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_KEY_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, userId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.TYPE_KEY, LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of cart keys failed: ", e);
        }

        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String cartId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_TRANS_ID);
            return cartId;

        }
        return null;
    }

    private CartResult getCartForUserAndTerm(String userId, String termId, String cartItemIdParam, boolean populateOptions, ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.REG_CART_BY_PERSON_TERM_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, userId);
        if (!StringUtils.isEmpty(cartItemIdParam)) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.CART_ITEM_ID, cartItemIdParam);
        }

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of activity offering schedules failed: ", e);
        }

        if (searchResult.getRows().isEmpty()) {
            return null;
        }

        String lastCartItemId = "";
        String lastAoName = "";
        String lastCartId = "";
        CartItemResult currentCartItem = new CartItemResult();
        ActivityOfferingScheduleResult aoSched = new ActivityOfferingScheduleResult();
        CartResult cartResult = new CartResult();
        Map<String, CartItemResult> luiIdToCartItem = new HashMap<String, CartItemResult>();
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String cartId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_ID);
            String cartItemId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_ITEM_ID);
            String courseCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_CODE);
            String courseId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_ID);
            String rgCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_CODE);
            String aoName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_NAME);
            String aoType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_TYPE);
            //String courseDescription = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC);
            String courseTitle = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME);
            String room = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE);
            String building = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE);
            String weekdays = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS);
            String startTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS);
            String endTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS);
            String credits = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS);
            String grading = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.GRADING);
            if (!lastCartItemId.equals(cartItemId)) {
                currentCartItem = new CartItemResult();
                currentCartItem.setCartItemId(cartItemId);
                currentCartItem.setCourseCode(courseCode);
                currentCartItem.setCourseTitle(courseTitle);
                currentCartItem.setCredits(StringUtils.substringAfterLast(credits, "kuali.result.value.credit.degree."));
                currentCartItem.setGrading(grading);
                currentCartItem.setRegGroupCode(rgCode);
                cartResult.getItems().add(currentCartItem);
                lastAoName = "";
                luiIdToCartItem.put(courseId, currentCartItem);
            }
            if (!lastAoName.equals(aoName)) {
                aoSched = new ActivityOfferingScheduleResult();
                aoSched.setActivityOfferingType(aoType.substring(aoType.lastIndexOf(".")+1,aoType.lastIndexOf(".")+4).toUpperCase());
                currentCartItem.getSchedule().add(aoSched);
            }
            ActivityOfferingLocationTimeResult locationTimeResult = new ActivityOfferingLocationTimeResult();

            ScheduleLocationResult locationResult = new ScheduleLocationResult();
            locationResult.setBuilding(building);
            locationResult.setRoom(room);
            locationTimeResult.setLocation(locationResult);

            ScheduleTimeResult scheduleTimeResult = new ScheduleTimeResult();
            scheduleTimeResult.setDays(weekdays);
            scheduleTimeResult.setStartTime(TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs))));
            scheduleTimeResult.setEndTime(TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs))));
            locationTimeResult.setTime(scheduleTimeResult);

            aoSched.getActivityOfferingLocationTime().add(locationTimeResult);

            lastAoName = aoName;
            lastCartId = cartId;
            lastCartItemId = cartItemId;
        }

        //Now we need grading options
        if (populateOptions) {
            populateOptions(luiIdToCartItem, contextInfo);
        }
        cartResult.setCartId(lastCartId);
        cartResult.setTermId(termId);

        return cartResult;
    }

    private void populateOptions(Map<String, CartItemResult> luiIdToCartItem, ContextInfo contextInfo) throws OperationFailedException {

        List<String> coIds = new ArrayList<String>(luiIdToCartItem.keySet());

        if (coIds.isEmpty()) {
            return;
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.RVGS_BY_LUI_IDS_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LUI_IDS, coIds);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of activity offering schedules failed: ", e);
        }

        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String coId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID);
            String rvgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RVG_ID);
            String rvgName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RVG_NAME);
            String rvgValue = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RVG_VALUE);

            if (rvgId.startsWith("kuali.creditType.credit.degree.")) {
                luiIdToCartItem.get(coId).getCreditOptions().add(rvgValue);
            } else {
                //rvgName is odd in the DB right now so doing a manual translation.
                luiIdToCartItem.get(coId).getGradingOptions().put(rvgId, translateGradingOptionKeyToName(rvgId));
            }
        }
    }

    private RegistrationRequestInfo createCart(String userId, String termId, ContextInfo contextInfo) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, ReadOnlyException {
        RegistrationRequestInfo registrationRequest = new RegistrationRequestInfo();
        registrationRequest.setTermId(termId);
        registrationRequest.setRequestorId(userId);
        registrationRequest.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        registrationRequest.setTypeKey(LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        RegistrationRequestInfo created = getCourseRegistrationService().createRegistrationRequest(registrationRequest.getTypeKey(), registrationRequest, contextInfo);
        return created;
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

    public LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public AtpService getAtpService() {
        if (atpService == null) {
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
}
