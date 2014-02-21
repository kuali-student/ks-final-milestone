package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
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
import org.kuali.student.enrollment.registration.client.service.exception.MissingOptionException;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
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
        } catch (Exception e) {
            LOGGER.warn("Error submitting cart", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public RegistrationResponseInfo submitCart(String userId, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Make sure that the user is the owner of the cart!
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);
        if (!StringUtils.equals(cartRegistrationRequest.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        RegistrationResponseInfo registrationResponse = getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);

        return registrationResponse;
    }

    @Override
    public Response addCourseToCartRS(String userId, String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = addCourseToCart(userId, cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);
            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (MissingOptionException e) {
            response = getResponse(Response.Status.BAD_REQUEST, e.getCartItemOptions());
        } catch (DoesNotExistException e) {
            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Error adding to cart", e);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return response.build();
    }

    private Response.ResponseBuilder getResponse(Response.Status status, Object entity) {
        //The request needs additional options (HTTP status 400 Bad Request)
        Response.ResponseBuilder response = Response.status(status).entity(entity);
        response.header("Access-Control-Allow-Header", "Content-Type");
        response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        return response;
    }

    protected Link buildDeleteLink(String cartId, String cartItemId, String gradingOptionId, String credits) {
        String action = "removeItemFromCart";
        String uri = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/removeItemFromCart?cartId=%s&cartItemId=%s&gradingOptionId=%s&credits=%s";
        uri = String.format(uri, cartId, cartItemId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    protected Link buildAddLink(String cartId, String regGroupId, String gradingOptionId, String credits) {
        String action = "addCourseToCart";
        String uri = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/addCourseToCart?cartId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        uri = String.format(uri, cartId, regGroupId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    @Override
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId, @QueryParam("gradingOptionId") String gradingOptionId, @QueryParam("credits") String credits) {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = removeItemFromCart(cartId, cartItemId, gradingOptionId, credits);
            // build the link to add this item.
            result.getActionLinks().add(buildAddLink(cartId, result.getRegGroupId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Transactional
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

        if (removedItem == null) {
            throw new DoesNotExistException("Item can not be removed as it does not exist.");
        }

        getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

        CartItemResult cartItemInfo = new CartItemResult();
        cartItemInfo.setRegGroupId(removedItem.getRegistrationGroupId());
        cartItemInfo.setCredits(credits);
        cartItemInfo.setGrading(gradingOptionId);

        return cartItemInfo;

    }

    @Transactional
    protected CartItemResult addCourseToCart(String userId, String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException, LoginException, MissingOptionException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        RegGroupSearchResult rg = null;
        //If only the user and regGroupId was passed in, we need to look up the RG and find the term to get the cart ID
        if (StringUtils.isEmpty(cartId) && !StringUtils.isEmpty(regGroupId)) {
            rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, null, null, null, regGroupId, contextInfo);
            cartId = searchForCartId(contextInfo.getPrincipalId(), rg.getTermId(), contextInfo);
            if (cartId == null) {
                //If the cart does not yet exist we need to create it.
                RegistrationRequestInfo request = createCart(contextInfo.getPrincipalId(), rg.getTermId(), contextInfo);
                cartId = request.getId();
            }
        }

        // getting cart
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Save all the existing ids
        List<String> registrationRequestIds = new ArrayList<String>();
        for (RegistrationRequestItemInfo item : cart.getRegistrationRequestItems()) {
            registrationRequestIds.add(item.getId());
        }

        // get the regGroup if it was not already found
        if (rg == null) {
            rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(cart.getTermId(), null, courseCode, regGroupCode, regGroupId, contextInfo);
        }

        //Look up and validate the student grading/credit options
        CartItemResult optionsCartItem = getOptionsCartItem(rg, courseCode, credits, gradingOptionId, contextInfo);

        // Create new reg request item and add it to the cart
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(cart.getRequestorId(), rg.getRegGroupId(), optionsCartItem.getCredits(), optionsCartItem.getGrading());
        registrationRequestItem.setMeta(new MetaInfo());
        registrationRequestItem.getMeta().setCreateId(cart.getMeta().getCreateId());//TODO KSENROLL-11755 we need a  better way to handle userIds (add as param in RS)
        cart.getRegistrationRequestItems().add(registrationRequestItem);

        //Persist the new cart with the newly added item
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

        // looking for new item from the results
        String newCartItemId = null;
        for (RegistrationRequestItemInfo cartItem : cartRegistrationRequest.getRegistrationRequestItems()) {
            if (!registrationRequestIds.contains(cartItem.getId())) {
                newCartItemId = cartItem.getId();
            }
        }

        //Check that the item was created
        if (StringUtils.isEmpty(newCartItemId)) {
            throw new OperationFailedException("Can't find the newly added cart item.");
        }

        //Get the cart result with the item id to trim it down and no reg options since we know these already
        CartResult cartResult = getCartForUserAndTerm(cart.getRequestorId(), cart.getTermId(), newCartItemId, false, contextInfo);
        CartItemResult cartItemResult = KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());

        //populate the options we have already calculated
        cartItemResult.setGradingOptions(optionsCartItem.getGradingOptions());
        cartItemResult.setCreditOptions(optionsCartItem.getCreditOptions());

        //Return just the item
        return cartItemResult;
    }

    /**
     * Gets student registration options (grading/credits) of a given registration group
     * This performs validation against the set credit and grading options, and defaults values if there is only one option.
     * @param regGroup Registration group
     * @param courseCode Code of the Course being searched for
     * @param credits credits that the student has selected (or none)
     * @param gradingOptionId grading option that the student has selected (or none)
     * @param contextInfo context of the call
     * @return a cart item that has the student options for the given registration group
     * @throws OperationFailedException
     * @throws MissingOptionException
     * @throws InvalidParameterException
     */
    private CartItemResult getOptionsCartItem(RegGroupSearchResult regGroup, String courseCode, String credits, String gradingOptionId, ContextInfo contextInfo) throws OperationFailedException, MissingOptionException, InvalidParameterException {
        CartItemResult optionsCartItem = new CartItemResult();
        Map<String, List<CartItemResult>> optionsMap = new HashMap<String, List<CartItemResult>>();
        optionsMap.put(regGroup.getCourseOfferingId(), Arrays.asList(optionsCartItem));
        populateOptions(optionsMap, contextInfo);
        optionsCartItem.setCourseCode(courseCode);
        optionsCartItem.setRegGroupCode(regGroup.getRegGroupName());
        optionsCartItem.setRegGroupId(regGroup.getRegGroupId());
        optionsCartItem.setCredits(credits);
        optionsCartItem.setGrading(gradingOptionId);
        optionsCartItem.setGradingOptionCount(optionsCartItem.getGradingOptions().size());

        //Do Checks for credit/grading options. If there is more than one option available on the course and no
        //option set in the input, then throw a missing option exception
        //if no credits were set and there is only one option, use that option
        //if no credits and multiple options then error since they must return an option
        //credits and option is not valid, error with invalid option

        if (StringUtils.isEmpty(credits)) {
            if (optionsCartItem.getCreditOptions().size() == 1) {
                optionsCartItem.setCredits(KSCollectionUtils.getRequiredZeroElement(optionsCartItem.getCreditOptions()));
            } else {
                throw new MissingOptionException(optionsCartItem);
            }
        } else if (!optionsCartItem.getCreditOptions().contains(credits)) {
            throw new InvalidParameterException("Credit option " + credits + " is not valid for this course: " + courseCode + "(" + regGroup.getRegGroupName() + ")");
        }

        if (StringUtils.isEmpty(gradingOptionId)) {
            if (optionsCartItem.getGradingOptions().size() == 1) {
                optionsCartItem.setGrading(optionsCartItem.getGradingOptions().keySet().iterator().next());
            } else {
                throw new MissingOptionException(optionsCartItem);
            }
        } else if (!optionsCartItem.getGradingOptions().containsKey(gradingOptionId)) {
            throw new InvalidParameterException("Grading option " + gradingOptionId + " is not valid for this course: " + courseCode + "(" + regGroup.getRegGroupName() + ")");
        }

        return optionsCartItem;
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
    public CartItemResult updateCartItem(String userId, String cartId, String cartItemId, String credits, String grading) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Get the Cart from services
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Check that it is the users' cart
        if (!StringUtils.equals(cartRegistrationRequest.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to edit items on this registration cart");
        }

        //Find the matching cartItem id to edit the registration options
        for (RegistrationRequestItemInfo requestItem : cartRegistrationRequest.getRegistrationRequestItems()) {
            if (StringUtils.equals(cartItemId, requestItem.getId())) {
                //Set the Item registration options
                requestItem.setCredits(new KualiDecimal(credits));
                requestItem.setGradingOptionId(grading);

                //Save the newly updated cart
                cartRegistrationRequest = getCourseRegistrationService().updateRegistrationRequest(cartRegistrationRequest.getId(), cartRegistrationRequest, contextInfo);

                //Look up the newly updated information
                CartResult cartResult = getCartForUserAndTerm(userId, cartRegistrationRequest.getTermId(), cartItemId, true, contextInfo);
                return KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());
            }
        }
        throw new DoesNotExistException("No matching cart item was found.");
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
        Map<String, List<CartItemResult>> luiIdToCartItems = new HashMap<String, List<CartItemResult>>();
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
                String creditsStr = StringUtils.substringAfterLast(credits, LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX);
                currentCartItem = new CartItemResult();
                currentCartItem.setCartItemId(cartItemId);
                currentCartItem.setCourseCode(courseCode);
                currentCartItem.setCourseTitle(courseTitle);
                currentCartItem.setCredits(creditsStr);
                currentCartItem.setGrading(grading);
                currentCartItem.setRegGroupCode(rgCode);
                currentCartItem.getActionLinks().add(buildDeleteLink(cartId, cartItemId, grading, creditsStr));
                cartResult.getItems().add(currentCartItem);
                lastAoName = "";
                if (!luiIdToCartItems.containsKey(courseId)) {
                    luiIdToCartItems.put(courseId, new ArrayList<CartItemResult>());
                }
                luiIdToCartItems.get(courseId).add(currentCartItem);
            }
            if (!lastAoName.equals(aoName)) {
                aoSched = new ActivityOfferingScheduleResult();
                aoSched.setActivityOfferingType(aoType.substring(aoType.lastIndexOf(".") + 1, aoType.lastIndexOf(".") + 4).toUpperCase());
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
            populateOptions(luiIdToCartItems, contextInfo);
        }
        cartResult.setCartId(lastCartId);
        cartResult.setTermId(termId);

        return cartResult;
    }

    private void populateOptions(Map<String, List<CartItemResult>> luiIdToCartItem, ContextInfo contextInfo) throws OperationFailedException {

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
            String rvgValue = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RVG_VALUE);

            if (rvgId.startsWith("kuali.creditType.credit.degree.")) {
                for (CartItemResult item : luiIdToCartItem.get(coId)) {
                    item.getCreditOptions().add(rvgValue);
                }
            } else {
                //rvgName is odd in the DB right now so doing a manual translation.
                for (CartItemResult item : luiIdToCartItem.get(coId)) {
                    item.getGradingOptions().put(rvgId, translateGradingOptionKeyToName(rvgId));
                }
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
