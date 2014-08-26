package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingLocationTimeResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ResultValueGroupCourseOptions;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleLocationResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleTimeResult;
import org.kuali.student.enrollment.registration.client.service.exception.GenericUserException;
import org.kuali.student.enrollment.registration.client.service.exception.MissingOptionException;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import javax.ws.rs.QueryParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * This class contains methods needed to manage the student registration cart.
 */
public class CourseRegistrationCartServiceImpl implements CourseRegistrationCartService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationCartServiceImpl.class);
    private CourseRegistrationService courseRegistrationService;
    private LprService lprService;
    private AtpService atpService;
    private ScheduleOfClassesService scheduleOfClassesService;


    @Override
    public RegistrationRequestInfo submitCart(ContextInfo contextInfo, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {

        //Make sure that the user is the owner of the cart!
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);
        if (!StringUtils.equals(cartRegistrationRequest.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        return getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);
    }


    @Transactional
    @Override
    public CartItemResult removeItemFromCart(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //Look up the courseRegistration request that represents this cart
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Loop through the request items in the cart and remove the item with the matching cartItem ID
        for (Iterator<RegistrationRequestItemInfo> cartItemIter = cart.getRegistrationRequestItems().iterator(); cartItemIter.hasNext(); ) {
            RegistrationRequestItemInfo requestItem = cartItemIter.next();
            if (requestItem.getId().equals(cartItemId)) {

                //Remove the cart Item
                cartItemIter.remove();

                //Persist the changes to the cart
                getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

                //Create a result as a return value of what was deleted
                CartItemResult cartItemInfo = new CartItemResult();
                cartItemInfo.setRegGroupId(requestItem.getRegistrationGroupId());
                cartItemInfo.setCredits(requestItem.getCredits() == null ? "" : requestItem.getCredits().bigDecimalValue().setScale(1).toPlainString());
                cartItemInfo.setGrading(requestItem.getGradingOptionId());
                cartItemInfo.setTermId(cart.getTermId());

                return cartItemInfo;
            }
        }

        throw new DoesNotExistException("Item can not be removed as it does not exist.");

    }


    /**
     * Transactional method that adds a request item to the cart. It needs to be transactions because we have to
     * get the cart, add the item, persis the cart. This method needs to be AS SMALL AS POSSIBLE for performance
     * reasons.
     *
     * @param cartId
     * @param registrationRequestItem
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     */
    @Transactional
    protected RegistrationRequestInfo addItemToRegRequest(String cartId, RegistrationRequestItemInfo registrationRequestItem, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {

        // getting cart
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        cart.getRegistrationRequestItems().add(registrationRequestItem);
        //Persist the new cart with the newly added item
        return getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);

    }

    protected RegistrationRequestInfo addCourseToRegRequest(String regRequestId, String regGroupId, String gradingOptionId, String credits, String courseCode, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException, LoginException, MissingOptionException, GenericUserException {

        // Create new reg request item and add it to the cart
        String entityId = CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().getEntityByPrincipalId(contextInfo.getPrincipalId()).getId();
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(entityId, regGroupId, null, credits, gradingOptionId, LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, courseCode, false, false);
        registrationRequestItem.setMeta(new MetaInfo());
        registrationRequestItem.getMeta().setCreateId(contextInfo.getPrincipalId());//TODO KSENROLL-11755 we need a  better way to handle userIds (add as param in RS)

        //Persist the new cart with the newly added item
        return addItemToRegRequest(regRequestId, registrationRequestItem, contextInfo);
    }

    /**
     * Helper method to get the newest (by createDate) reg request item.
     *
     * @param regRequestItems
     * @return
     */
    private RegistrationRequestItemInfo getNewestRegRequestItem(List<RegistrationRequestItemInfo> regRequestItems) {
        if (regRequestItems == null || regRequestItems.isEmpty()) return null;

        RegistrationRequestItemInfo newest = null;
        for (RegistrationRequestItemInfo reqItem : regRequestItems) {
            if (newest == null) {
                newest = reqItem;
            } else {
                if (reqItem.getMeta().getCreateTime().after(newest.getMeta().getCreateTime())) {
                    newest = reqItem;
                }
            }
        }
        return newest;
    }


    /**
     *
     * @param regReqId registration request id
     * @param regGroupId registration group id
     * @param gradingOptionId grading option id
     * @param credits         credit value
     * @param rvgCourseOptions valid list of grading and credit options for this item
     * @param courseCode  optional. course code user passed in. need for cross listing
     * @param contextInfo
     * @return
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     * @throws LoginException
     * @throws MissingOptionException
     * @throws GenericUserException
     */
    protected CartItemResult addCourseToCart(String regReqId, String regGroupId, String gradingOptionId, String credits, ResultValueGroupCourseOptions rvgCourseOptions, String courseCode, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException, LoginException, MissingOptionException, GenericUserException {

        RegistrationRequestInfo updatedRegReq = addCourseToRegRequest(regReqId, regGroupId, gradingOptionId, credits, courseCode, contextInfo);
        RegistrationRequestItemInfo  newRegReqItem = getNewestRegRequestItem(updatedRegReq.getRegistrationRequestItems());

        //Get the cart result with the item id to trim it down and no reg options since we know these already
        CartResult cartResult = getCartForUserAndTerm(contextInfo.getPrincipalId(), updatedRegReq.getTermId(), updatedRegReq.getId(), newRegReqItem.getId(), false, contextInfo);


        if(cartResult == null){
            // we were getting NPE on the next method when under heavy load. Adding additional debug.
            String technicalInfo = String.format("Error: cartResult == null. Technical Info:(cartRequestorId:[%s] cartTermId:[%s] newCartItemId:[%s] )",
                    contextInfo.getPrincipalId(), updatedRegReq.getTermId(), newRegReqItem.getId());
            throw new NullPointerException(technicalInfo);
        }
        CartItemResult cartItemResult = KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());

        //populate the options we have already calculated
        cartItemResult.setGradingOptions(rvgCourseOptions.getGradingOptions());
        cartItemResult.setCreditOptions(new ArrayList(rvgCourseOptions.getCreditOptions().values()));

        //If the user provided a course code, ensure it is the same one returned (for cross-listed courses).
        if (StringUtils.isNotEmpty(courseCode)) {
            cartItemResult.setCourseCode(courseCode);
        }

        //Return just the item
        return cartItemResult;



    }

    protected ValidationResultInfo validateRegGroupSearchResult(RegGroupSearchResult regGroupSearchResult, String courseCode, String regGroupCode) {

        ValidationResultInfo resultInfo = new ValidationResultInfo();
        if (!LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY.equals(regGroupSearchResult.getRegGroupState())) {
            resultInfo.setError("Course " + courseCode + " (" + regGroupCode + ") is not offered in the selected term");
        }

        return resultInfo;
    }

    protected static boolean isMissingOptions(String creditValue, String gradingOptionId, ResultValueGroupCourseOptions rvgCourseOptions){
        boolean bRet = false;
        List<String> creditOptions = new ArrayList<>(rvgCourseOptions.getCreditOptions().values());
        if (StringUtils.isEmpty(creditValue)) {
            if (creditOptions.size() != 1) {
                bRet = true;
            }
        }

        if (StringUtils.isEmpty(gradingOptionId)) {
            if (rvgCourseOptions.getGradingOptions().size() != 1) {
                bRet = true;
            }
        }
        return bRet;
    }

    protected static String getSingleCreditValue(ResultValueGroupCourseOptions rvgCourseOptions) throws OperationFailedException {
        String sRet = null;
        if(rvgCourseOptions.getCreditOptions().size() == 1){
            sRet = rvgCourseOptions.getCreditOptions().values().iterator().next();
        }
        return sRet;
    }

    protected static String getSingleGradingOptionsValue(ResultValueGroupCourseOptions rvgCourseOptions) throws OperationFailedException {
        String sRet = null;
        if(rvgCourseOptions.getGradingOptions().size() == 1){
            sRet = rvgCourseOptions.getGradingOptions().keySet().iterator().next();
        }
        return sRet;
    }

    protected static boolean isCreditValueValid(String creditValue, ResultValueGroupCourseOptions rvgCourseOptions){
        boolean bRet = true;
        List<String> creditOptions = new ArrayList<>(rvgCourseOptions.getCreditOptions().values());
        if (StringUtils.isEmpty(creditValue)) {
            bRet = false;
        }else if (!creditOptions.contains(creditValue)){
            bRet = false;
        }
        return bRet;
    }

    protected static boolean isGradingOptionValid(String gradingOptionId, ResultValueGroupCourseOptions rvgCourseOptions){
        boolean bRet = true;

        if (StringUtils.isEmpty(gradingOptionId)) {
            bRet = false;
        } else if (!rvgCourseOptions.getGradingOptions().containsKey(gradingOptionId)) {
            bRet = false;
        }
        return bRet;
    }

    @Override
    @Transactional
    //This will need to be changed to the cartItemResponse object in the future!
    public CartItemResult updateCartItem(ContextInfo contextInfo, String cartId, String cartItemId, String credits, String gradingOptionId) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        //Get the Cart from services
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Check that it is the users' cart
        String entityId = CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().getEntityByPrincipalId(contextInfo.getPrincipalId()).getId();
        if (!StringUtils.equals(cartRegistrationRequest.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to edit items on this registration cart");
        }

        //Find the matching cartItem id to edit the registration options
        for (RegistrationRequestItemInfo requestItem : cartRegistrationRequest.getRegistrationRequestItems()) {
            if (StringUtils.equals(cartItemId, requestItem.getId())) {
                //Set the Item registration options
                requestItem.setCredits(new KualiDecimal(credits));
                requestItem.setGradingOptionId(gradingOptionId);

                //Save the newly updated cart
                cartRegistrationRequest = getCourseRegistrationService().updateRegistrationRequest(cartRegistrationRequest.getId(), cartRegistrationRequest, contextInfo);

                //Look up the newly updated information
                CartResult cartResult = getCartForUserAndTerm(contextInfo.getPrincipalId(), cartRegistrationRequest.getTermId(), null, cartItemId, true, contextInfo);
                return KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());
            }
        }
        throw new DoesNotExistException("No matching cart item was found.");
    }

    public CartResult searchForCart(ContextInfo contextInfo, String termId) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        if (termId == null) {
            throw new InvalidParameterException("Term Id cannot be null.");
        }

        String userId = contextInfo.getPrincipalId();

        //Verify that the Atp exists.
        getAtpService().getAtp(termId, contextInfo);

        //Perform the actual search (this requires user id and term id)
        CartResult cartResult = getCartForUserAndTerm(userId, termId, null, null, true, contextInfo);

        //If nothing was found, this could mean there is an empty cart or no cart at all
        if (cartResult == null) {
            //Search for the cart ID to see if an empty cart already exists for the user and term
            String cartId = searchForCartId(userId, termId, contextInfo);
            cartResult = new CartResult();
            cartResult.setCartId(cartId);
            cartResult.setTermId(termId);
            if (cartId == null) {
                //If no cart was found we will create one
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
            String lprTransactionId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_TRANS_ID);
            if (lprTransactionId != null) {
                return lprTransactionId;
            }
        }
        return null;
    }

    protected Link buildDeleteLink(String cartId, String cartItemId, String gradingOptionId, String credits) {
        String action = "removeItemFromCart";
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/removeItemFromCart?cartId=%s&cartItemId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat, cartId, cartItemId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    /**
     * A search service-backed method that returns a cart and the associated item(s)
     *
     * @param userId          the student id who wons the cart
     * @param termId          the term for the cart
     * @param cartIdParam     An optional parameter if you are looking for a single reg request
     * @param cartItemIdParam An optional parameter if you are looking for schedule information for just one cart item
     * @param populateOptions flag if the credit/grading options should be populated
     * @param contextInfo     the context of the call
     * @return a Cart result
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     */
    private CartResult getCartForUserAndTerm(String userId, String termId, String cartIdParam, String cartItemIdParam, boolean populateOptions, ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException {
        //Create a search request that finds cart information for a given user and term, or alternatively for a single specific cart item.
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.REG_CART_BY_PERSON_TERM_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.PERSON_ID, userId);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LPRT_TYPE, LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        if (!StringUtils.isEmpty(cartItemIdParam)) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.CART_ITEM_ID, cartItemIdParam);
        }
        if (!StringUtils.isEmpty(cartIdParam)) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.CART_ID, cartIdParam);
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

        //Initialize variables that help organize the flat data into the object graph
        String lastCartItemId = "";
        String lastAoName = "";
        String lastCartId = "";
        String lastCartState = "";
        CartItemResult currentCartItem = new CartItemResult();
        ActivityOfferingScheduleResult aoSched = new ActivityOfferingScheduleResult();
        CartResult cartResult = new CartResult();
        Map<String, List<CartItemResult>> luiIdToCartItems = new HashMap<String, List<CartItemResult>>();
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            //Parse information from the flat search results
            String cartId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_ID);
            String cartItemId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_ITEM_ID);
            String cartState = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_STATE);
            String cartItemState = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CART_ITEM_STATE);
            String crossList = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CROSSLIST);
            String courseCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_CODE);
            String courseId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_ID);
            String rgCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_CODE);
            String rgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_ID);
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String aoName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_NAME);
            String aoType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_TYPE);
            //String courseDescription = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC);
            String courseTitle = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME);
            String room = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE);
            String building = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE);
            String isTBA = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.TBA_IND);
            String weekdays = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS);
            String startTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS);
            String endTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS);
            String credits = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS);
            String grading = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.GRADING);

            //Check if this is a new cart item and create a new object if so
            if (!lastCartItemId.equals(cartItemId)) {
                //If so, create the new cart item
                String creditsStr = StringUtils.substringAfterLast(credits, LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX);
                currentCartItem = new CartItemResult();
                currentCartItem.setCartItemId(cartItemId);
                if (!StringUtils.isEmpty(crossList) && !StringUtils.equals(crossList, courseCode)) {
                    currentCartItem.setCourseCode(crossList);
                } else {
                    currentCartItem.setCourseCode(courseCode);
                }
                currentCartItem.setCourseTitle(courseTitle);
                currentCartItem.setCredits(creditsStr);
                currentCartItem.setGrading(grading);
                currentCartItem.setRegGroupCode(rgCode);
                currentCartItem.setRegGroupId(rgId);
                currentCartItem.getActionLinks().add(buildDeleteLink(cartId, cartItemId, grading, creditsStr));
                currentCartItem.setState(cartItemState);
                currentCartItem.setCartId(cartId);
                currentCartItem.setTermId(termId);
                cartResult.getItems().add(currentCartItem);
                //Reset the lastAO Name
                lastAoName = "";
                if (!luiIdToCartItems.containsKey(courseId)) {
                    luiIdToCartItems.put(courseId, new ArrayList<CartItemResult>());
                }
                luiIdToCartItems.get(courseId).add(currentCartItem);
            }
            //Check if this is a new AO and create a new object if so
            if (!lastAoName.equals(aoName)) {
                aoSched = new ActivityOfferingScheduleResult();
                aoSched.setActivityOfferingId(aoId);
                String aoTypeName = aoType.substring(aoType.lastIndexOf(".") + 1);
                aoSched.setActivityOfferingTypeName(aoTypeName.length() >= 3 ? aoTypeName.substring(0, 1).toUpperCase() + aoTypeName.substring(1).toLowerCase() : "");
                aoSched.setActivityOfferingType(aoType);
                currentCartItem.getSchedule().add(aoSched);
            }

            //The rest of the information (schedule) is repeated for each AO on each cart Item.
            ActivityOfferingLocationTimeResult locationTimeResult = new ActivityOfferingLocationTimeResult();
            if (StringUtils.equals(isTBA, "1")) {
                locationTimeResult.setIsTBA(true);
            } else {
                locationTimeResult.setIsTBA(false);
            }

            ScheduleLocationResult locationResult = new ScheduleLocationResult();
            locationResult.setBuilding(building);
            locationResult.setRoom(room);
            locationTimeResult.setLocation(locationResult);

            ScheduleTimeResult scheduleTimeResult = new ScheduleTimeResult();
            scheduleTimeResult.setDays(StringUtils.isEmpty(weekdays) ? "" : CourseRegistrationAndScheduleOfClassesUtil.dayDisplayHelper(weekdays));
            String startTime = StringUtils.isEmpty(startTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs)));
            String endTime = StringUtils.isEmpty(endTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs)));
            scheduleTimeResult.setStartTime(startTime);
            scheduleTimeResult.setEndTime(endTime);
            if (!StringUtils.isEmpty(startTimeMs) && !StringUtils.isEmpty(endTimeMs)) {
                scheduleTimeResult.setDisplayTime(TimeOfDayHelper.makeFormattedTimeForAOScheduleComponent(startTimeMs, endTimeMs));
            }
            locationTimeResult.setTime(scheduleTimeResult);

            aoSched.getActivityOfferingLocationTime().add(locationTimeResult);

            lastAoName = aoName;
            lastCartId = cartId;
            lastCartItemId = cartItemId;
            lastCartState = cartState;
        }

        //Now we need grading and credit options in a new search
        if (populateOptions) {
            populateOptions(luiIdToCartItems, contextInfo);
        }
        cartResult.setCartId(lastCartId);
        cartResult.setTermId(termId);
        cartResult.setState(lastCartState);

        //Populating instructors for AOs
        for (CartItemResult cartItemResult : cartResult.getItems()) {
            List<String> aoIds = new ArrayList<String>();
            for (ActivityOfferingScheduleResult aoScheduleResult : cartItemResult.getSchedule()) {
                aoIds.add(aoScheduleResult.getActivityOfferingId());
            }
            Map<String, List<InstructorSearchResult>> hmAOInstructors = CourseRegistrationAndScheduleOfClassesUtil.searchForInstructorsByAoIds(aoIds, contextInfo);
            for (ActivityOfferingScheduleResult aoScheduleResult : cartItemResult.getSchedule()) {
                if (hmAOInstructors.containsKey(aoScheduleResult.getActivityOfferingId())) {
                    aoScheduleResult.setInstructors(hmAOInstructors.get(aoScheduleResult.getActivityOfferingId()));
                }
            }

            // sorting over AO types: should be lecture, lab, discussion order
            Map<String, List<ActivityOfferingScheduleResult>> hmAoSchedules = new HashMap<>();
            List<String> aoTypes = new ArrayList<>();
            for (ActivityOfferingScheduleResult aoScheduleResult : cartItemResult.getSchedule()) {
                if (!hmAoSchedules.containsKey(aoScheduleResult.getActivityOfferingType())) {
                    List<ActivityOfferingScheduleResult> aoSchedules = new ArrayList<>();
                    aoSchedules.add(aoScheduleResult);
                    hmAoSchedules.put(aoScheduleResult.getActivityOfferingType(), aoSchedules);
                    aoTypes.add(aoScheduleResult.getActivityOfferingType());
                } else {
                    hmAoSchedules.get(aoScheduleResult.getActivityOfferingType()).add(aoScheduleResult);
                }
            }
            CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingTypeKeyList(aoTypes, contextInfo);  // sort the activity offerings type keys by priority order
            List<ActivityOfferingScheduleResult> aoSchedules = new ArrayList<>();
            for (String key : aoTypes) {
                aoSchedules.addAll(hmAoSchedules.get(key));
            }
            cartItemResult.setSchedule(aoSchedules);
        }

        return cartResult;
    }


    /**
     * The method populates the credit and grading options for a cart item
     *
     * @param luiIdToCartItem a mapping of lui to cart items
     * @param contextInfo     the context of the call
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
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

            if (rvgId.startsWith(LrcServiceConstants.RESULT_VALUE_GROUP_KEY_CREDIT_DEGREE_PREFIX)) {
                for (CartItemResult item : luiIdToCartItem.get(coId)) {
                    item.getCreditOptions().add(rvgValue);
                }
            } else {
                //rvgName is odd in the DB right now so doing a manual translation.
                for (CartItemResult item : luiIdToCartItem.get(coId)) {
                    item.getGradingOptions().put(rvgId, CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(rvgId));
                }
            }
        }
    }

    protected RegistrationRequestInfo createCart(String userId, String termId, ContextInfo contextInfo) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, ReadOnlyException {
        RegistrationRequestInfo registrationRequest = new RegistrationRequestInfo();
        registrationRequest.setTermId(termId);
        registrationRequest.setRequestorId(userId);
        registrationRequest.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        registrationRequest.setTypeKey(LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        return getCourseRegistrationService().createRegistrationRequest(registrationRequest.getTypeKey(), registrationRequest, contextInfo);
    }

    /**
     * clears the cart for the passed in personId. Term is optional. If no term is passed in, all possible carts are cleared for this person.
     * @param personId
     * @param termId
     * @param contextInfo
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     */
    @Transactional
    protected void clearCartByPerson(String personId, String termId, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, VersionMismatchException {
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred;

        pred =  and(equal("requestingPersonId", personId),
                equal("lprTransType", LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY ),
                equal("lprTransState",LprServiceConstants.LPRTRANS_NEW_STATE_KEY ));

        if(termId != null && !termId.isEmpty()) {
          pred =  and(pred, equal("atpId", termId));
        }
        qBuilder.setPredicates(pred);

        List<LprTransactionInfo> carts = getLprService().searchForLprTransactions(qBuilder.build(), contextInfo);

        if(carts == null)return;

        for(LprTransactionInfo cart : carts) {
            if (cart != null && !cart.getLprTransactionItems().isEmpty()) {
                cart.setLprTransactionItems(null);
                getLprService().updateLprTransaction(cart.getId(), cart, contextInfo);
            }
        }

    }


    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public AtpService getAtpService() {
        if (atpService == null) {
            atpService = GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    protected ScheduleOfClassesService getScheduleOfClassesService() {
        if(scheduleOfClassesService == null){
            scheduleOfClassesService = GlobalResourceLoader.getService(ScheduleOfClassesServiceConstants.QNAME);
        }
        return scheduleOfClassesService;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }
}
