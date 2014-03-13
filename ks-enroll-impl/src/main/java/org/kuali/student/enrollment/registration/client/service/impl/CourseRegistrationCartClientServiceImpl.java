package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
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
import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;
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
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CourseRegistrationCartClientServiceImpl implements CourseRegistrationCartClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationCartClientServiceImpl.class);
    private CourseRegistrationService courseRegistrationService;
    private LprService lprService;
    private AtpService atpService;

    @Override
    public Response submitCartRS(String cartId) {
        Response.ResponseBuilder response;

        try {
            submitCart(ContextUtils.createDefaultContextInfo(), cartId);
            response = Response.ok(Boolean.TRUE);
        } catch (Exception e) {
            LOGGER.warn("Error submitting cart", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error submitting cart");
            String technicalInfo = String.format("Technical Info:(cartId:[%s])",
                    cartId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public RegistrationResponseInfo submitCart(ContextInfo contextInfo, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {

        //Make sure that the user is the owner of the cart!
        RegistrationRequestInfo cartRegistrationRequest = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);
        if (!StringUtils.equals(cartRegistrationRequest.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        return getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);
    }

    @Override
    public Response addCourseToCartRS(String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = addCourseToCart(ContextUtils.createDefaultContextInfo(), cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);
            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (MissingOptionException e) {
            response = getResponse(Response.Status.BAD_REQUEST, e.getCartItemOptions());
        } catch (DoesNotExistException e) {
            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, e.getMessage());
        }catch (GenericUserException e) {
            LOGGER.warn("Error adding to cart", e);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getUserMessage());
        } catch (Exception e) {
            LOGGER.warn("Error adding to cart", e);

            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to add item to cart.");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] courseCode[%s] regGroupId[%s] gradingOptionId[%s] credits:[%s] )",
                    cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
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
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/removeItemFromCart?cartId=%s&cartItemId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat, cartId, cartItemId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    protected Link buildAddLink(String cartId, String regGroupId, String gradingOptionId, String credits) {
        String action = "addCourseToCart";
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/addCourseToCart?cartId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat, cartId, regGroupId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    @Override
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId) {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = removeItemFromCart(cartId, cartItemId);
            // build the link to add this item.
            result.getActionLinks().add(buildAddLink(cartId, result.getRegGroupId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Exception e) {
            LOGGER.warn("Error with removing item from cart", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error removing item from cart. ");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] cartItemId[%s])",cartId, cartItemId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
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

                return cartItemInfo;
            }
        }

        throw new DoesNotExistException("Item can not be removed as it does not exist.");

    }

    @Transactional
    protected CartItemResult addCourseToCart(ContextInfo contextInfo, String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException, LoginException, MissingOptionException, GenericUserException {

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

        ValidationResultInfo regGroupValidation = validateRegGroupSearchResult(rg);

        if(regGroupValidation.isError()){
            String technicalInfo = String.format("Technical Info:(term:[%s] id:[%s] state:[%s] )",
                    rg.getTermId(), rg.getRegGroupId(), rg.getRegGroupState());

            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to add item to cart.");
            userMessage.setDetailedMessage(regGroupValidation.getMessage());
            userMessage.setConsoleMessage(regGroupValidation.getMessage() + " " + technicalInfo);
            userMessage.setType(UserMessageResult.MessageTypes.ERROR);
            throw new GenericUserException(userMessage);
        }

        //Look up and validate the student grading/credit options
        CartItemResult optionsCartItem = getOptionsCartItem(rg, courseCode, credits, gradingOptionId, contextInfo);

        // Create new reg request item and add it to the cart
        RegistrationRequestItemInfo registrationRequestItem = CourseRegistrationAndScheduleOfClassesUtil.createNewRegistrationRequestItem(cart.getRequestorId(), rg.getRegGroupId(), null, optionsCartItem.getCredits(), optionsCartItem.getGrading(), LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
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
        CartResult cartResult = getCartForUserAndTerm(cart.getRequestorId(), cart.getTermId(), null,  newCartItemId, false, contextInfo);
        CartItemResult cartItemResult = KSCollectionUtils.getRequiredZeroElement(cartResult.getItems());

        //populate the options we have already calculated
        cartItemResult.setGradingOptions(optionsCartItem.getGradingOptions());
        cartItemResult.setCreditOptions(optionsCartItem.getCreditOptions());

        //Return just the item
        return cartItemResult;
    }

    protected ValidationResultInfo validateRegGroupSearchResult(RegGroupSearchResult regGroupSearchResult){

        ValidationResultInfo resultInfo = new ValidationResultInfo();
        if(!LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY.equals(regGroupSearchResult.getRegGroupState())){
            resultInfo.setError("The Registration Group you selected is not in an Offered State. You can only add offered registration groups to cart.");
        }

        return  resultInfo;
    }

    /**
     * Gets student registration options (grading/credits) of a given registration group
     * This performs validation against the set credit and grading options, and defaults values if there is only one option.
     *
     * @param regGroup        Registration group
     * @param courseCode      Code of the Course being searched for
     * @param credits         credits that the student has selected (or none)
     * @param gradingOptionId grading option that the student has selected (or none)
     * @param contextInfo     context of the call
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
    public Response updateCartItemRS(String cartId, String cartItemId, String credits, String gradingOptionId) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(updateCartItem(ContextUtils.createDefaultContextInfo(), cartId, cartItemId, credits, gradingOptionId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to update item in cart");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] cartItemId[%s] gradingOptionId[%s] credits:[%s] )",
                    cartId, cartItemId, gradingOptionId, credits);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    //This will need to be changed to the cartItemResponse object in the future!
    public CartItemResult updateCartItem(ContextInfo contextInfo, String cartId, String cartItemId, String credits, String gradingOptionId) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

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

    @Override
    public Response getStudentRegistrationOptionsRS(String courseCode, String termId, String regGroupId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(getStudentRegistrationOptions(courseCode, termId, regGroupId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to get student registration options.");
            String technicalInfo = String.format("Technical Info:(termId:[%s] courseCode[%s] regGroupId[%s] )",termId, courseCode, regGroupId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    public RegistrationOptionResult getStudentRegistrationOptions(String courseCode, String termId, String regGroupId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseSearchResult> courses = CourseRegistrationAndScheduleOfClassesUtil.getScheduleOfClassesService().searchForCourseOfferingsByTermIdAndCourse(termId, courseCode);
        CourseSearchResult exactMatch = new CourseSearchResult();
        for (CourseSearchResult courseSearchResult : courses) {
            if (courseSearchResult.getCourseOfferingCode().equalsIgnoreCase(courseCode)) {
                exactMatch = courseSearchResult;
            }
        }

        CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(exactMatch.getCourseOfferingId(), courseCode, termId, null);
        RegistrationOptionResult registrationOptionResult = new RegistrationOptionResult();
        registrationOptionResult.setCourseCode(courseCode);
        registrationOptionResult.setTermId(termId);
        registrationOptionResult.setRegGroupId(regGroupId);
        registrationOptionResult.setGradingOptions(courseOfferingInfo.getStudentRegistrationGradingOptions());
        registrationOptionResult.setCredits(courseOfferingInfo.getCreditOptionId());

        return registrationOptionResult;

    }

    @Override
    public Response searchForCartRS(String termId) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(searchForCart(ContextUtils.createDefaultContextInfo(), termId));
        } catch (Exception e) {
            LOGGER.warn("Error", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error while searching for Cart.");
            String technicalInfo = String.format("Technical Info:(termId:[%s])", termId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Transactional
    public CartResult searchForCart(ContextInfo contextInfo, String termId) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        if (termId == null) {
            throw new InvalidParameterException("Term Id cannot be null.");
        }

        String userId = contextInfo.getPrincipalId();

        //Verify that the Atp exists.
        getAtpService().getAtp(termId, contextInfo);

        //Perform the actual search (this requires user id and term id)
        CartResult cartResult = getCartForUserAndTerm(userId, termId, null,  null, true, contextInfo);

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
            return row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_TRANS_ID);
        }
        return null;
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
     * @throws OperationFailedException
     * @throws InvalidParameterException
     */
    private CartResult getCartForUserAndTerm(String userId, String termId, String cartIdParam, String cartItemIdParam, boolean populateOptions, ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException {
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
            String courseCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_CODE);
            String courseId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_ID);
            String rgCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_CODE);
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
                currentCartItem.setCourseCode(courseCode);
                currentCartItem.setCourseTitle(courseTitle);
                currentCartItem.setCredits(creditsStr);
                currentCartItem.setGrading(grading);
                currentCartItem.setRegGroupCode(rgCode);
                currentCartItem.getActionLinks().add(buildDeleteLink(cartId, cartItemId, grading, creditsStr));
                currentCartItem.setState(cartItemState);
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
                aoSched.setActivityOfferingType(aoType.substring(aoType.lastIndexOf(".") + 1, aoType.lastIndexOf(".") + 4).toUpperCase());
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
            scheduleTimeResult.setDays(weekdays);
            scheduleTimeResult.setStartTime(StringUtils.isEmpty(startTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs))));
            scheduleTimeResult.setEndTime(StringUtils.isEmpty(endTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs))));
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

        return cartResult;
    }

    /**
     * The method populates the credit and grading options for a cart item
     *
     * @param luiIdToCartItem a mapping of lui to cart items
     * @param contextInfo     the context of the call
     * @throws OperationFailedException
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

            if (rvgId.startsWith("kuali.creditType.credit.degree.")) {
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

    private RegistrationRequestInfo createCart(String userId, String termId, ContextInfo contextInfo) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, ReadOnlyException {
        RegistrationRequestInfo registrationRequest = new RegistrationRequestInfo();
        registrationRequest.setTermId(termId);
        registrationRequest.setRequestorId(userId);
        registrationRequest.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        registrationRequest.setTypeKey(LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY);
        RegistrationRequestInfo created = getCourseRegistrationService().createRegistrationRequest(registrationRequest.getTypeKey(), registrationRequest, contextInfo);
        return created;
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
