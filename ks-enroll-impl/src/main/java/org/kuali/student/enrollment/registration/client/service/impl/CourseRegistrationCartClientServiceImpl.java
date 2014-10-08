package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.AddCourseToCartRequestInfo;
import org.kuali.student.enrollment.registration.client.service.dto.AddCourseToCartResponseResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ResultValueGroupCourseOptions;
import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;
import org.kuali.student.enrollment.registration.client.service.exception.CourseDoesNotExistException;
import org.kuali.student.enrollment.registration.client.service.exception.GenericUserException;
import org.kuali.student.enrollment.registration.client.service.exception.InvalidOptionException;
import org.kuali.student.enrollment.registration.client.service.exception.MissingOptionException;
import org.kuali.student.enrollment.registration.client.service.exception.RegGroupNotOfferedException;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class CourseRegistrationCartClientServiceImpl extends CourseRegistrationCartServiceImpl implements CourseRegistrationCartClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationCartClientServiceImpl.class);

    @Override
    public Response submitCartRS(String termId) {
        Response.ResponseBuilder response;
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            CartResult cart = searchForCart(contextInfo, termId);

            RegistrationRequestInfo info = submitCart(contextInfo, cart.getCartId());
            response = Response.ok(info);
        } catch (Exception e) {
            LOGGER.warn("Error submitting cart for term {} for {}", termId, contextInfo.getPrincipalId(), e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error submitting cart");
            String technicalInfo = String.format("Technical Info: (termId:[%s])",
                    termId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public Response addCourseToCartRS(String termId, String regGroupId, String courseCode, String regGroupCode, String gradingOptionId, String credits) {
        Response.ResponseBuilder response;

        CartItemResult cartItemResult = new CartItemResult();
        cartItemResult.setCourseCode(courseCode);
        cartItemResult.setCredits(credits);
        cartItemResult.setGrading(gradingOptionId);
        cartItemResult.setRegGroupCode(regGroupCode);
        cartItemResult.setRegGroupId(regGroupId);
        cartItemResult.setTermId(termId);

        // Init the response object & default the response cart item to be the incoming one. It will be set to the result if successful.
        AddCourseToCartResponseResult responseResult = new AddCourseToCartResponseResult(cartItemResult);

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            cartItemResult = addCourseToCartLocal(cartItemResult, contextInfo);

            // This will need to be changed to the cartItemResponse object in the future!
            responseResult.setCartItem(cartItemResult);
            responseResult.setSuccess();

            response = Response.ok(responseResult);

        } catch (CourseDoesNotExistException e) { // Reg group was not found
            String technicalInfo = String.format("Unable to add item to cart. CourseDoesNotExistException. Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartItemResult.getTermId(), cartItemResult.getCourseCode(), cartItemResult.getRegGroupCode(), cartItemResult.getRegGroupId(), cartItemResult.getGrading(), cartItemResult.getCredits());
            LOGGER.debug(technicalInfo, e);

            UserMessageResult message = new UserMessageResult("Unable to add item to cart");
            message.setMessageKey(e.getMessageKey());
            message.setConsoleMessage(technicalInfo);
            message.setType(UserMessageResult.MessageTypes.ERROR);

            responseResult.addMessage(message);
            responseResult.setError();

            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, responseResult);
        } catch (MissingOptionException e) {
            String technicalInfo = String.format("Unable to add item to cart. MissingOptionException. Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartItemResult.getTermId(), cartItemResult.getCourseCode(), cartItemResult.getRegGroupCode(), cartItemResult.getRegGroupId(), cartItemResult.getGrading(), cartItemResult.getCredits());
            LOGGER.debug(technicalInfo, e);

            UserMessageResult message = new UserMessageResult("Unable to add item to cart. Missing Options.");
            message.setMessageKey(e.getMessageKey());
            message.setConsoleMessage(technicalInfo);
            message.setType(UserMessageResult.MessageTypes.ERROR);

            responseResult.addMessage(message);
            responseResult.setError();

            response = getResponse(Response.Status.BAD_REQUEST, responseResult);
        } catch (RegGroupNotOfferedException | InvalidOptionException e) {
            String technicalInfo = String.format("Unable to add item to cart. %s. Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    e.getClass().getSimpleName(), cartItemResult.getTermId(), cartItemResult.getCourseCode(), cartItemResult.getRegGroupCode(), cartItemResult.getRegGroupId(), cartItemResult.getGrading(), cartItemResult.getCredits());
            LOGGER.debug(technicalInfo, e);

            UserMessageResult message = new UserMessageResult("Unable to add item to cart");
            message.setConsoleMessage(technicalInfo);
            message.setType(UserMessageResult.MessageTypes.ERROR);

            if (e instanceof RegGroupNotOfferedException) {
                message.setMessageKey(((RegGroupNotOfferedException) e).getMessageKey());
                message.getData().put("state", ((RegGroupNotOfferedException) e).getState()); // Put the RG state into the data object
            } else if (e instanceof InvalidOptionException) {
                message.setMessageKey(((InvalidOptionException) e).getMessageKey());
                message.getData().put("option", ((InvalidOptionException) e).getOption()); // Put the invalid option value into the data object
            }

            responseResult.addMessage(message);
            responseResult.setError();

            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, responseResult);
        } catch (GenericUserException e) {
            String technicalInfo = String.format("Unable to add item to cart. GenericUserException. Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartItemResult.getTermId(), cartItemResult.getCourseCode(), cartItemResult.getRegGroupCode(), cartItemResult.getRegGroupId(), cartItemResult.getGrading(), cartItemResult.getCredits());
            LOGGER.error(technicalInfo, e);

            responseResult.addMessage(e.getUserMessage());
            responseResult.setError();

            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, responseResult);
        }

        return response.build();
    }

    @Override
    public Response addCoursesToCartRS(String termId, List<AddCourseToCartRequestInfo> cartRequestItems) {
        List<AddCourseToCartResponseResult> results = new ArrayList<>();

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        String cartId = null;

        for (AddCourseToCartRequestInfo cartItemRequest : cartRequestItems) {
            // Set in the termId if it's not empty (optional QueryParam)
            if (StringUtils.isNotEmpty(termId)) {
                cartItemRequest.setTermId(termId);
            }

            CartItemResult cartItemResult = buildCartItemResultFromRequest(cartItemRequest);

            // Set the cartId if it's been resolved
            if (StringUtils.isNotEmpty(cartId)) {
                cartItemResult.setCartId(cartId);
            }

            // Init the response object & default the response cart item to be the incoming one. It will be set to the result if successful.
            AddCourseToCartResponseResult responseResult = new AddCourseToCartResponseResult(cartItemResult);

            try {
                cartItemResult = addCourseToCartLocal(cartItemResult, contextInfo);
                cartId = cartItemResult.getCartId(); // Store off the resolved cartId so we don't have to fetch it for every course

                responseResult.setCartItem(cartItemResult);
                responseResult.setSuccess();

            } catch (CourseDoesNotExistException | RegGroupNotOfferedException | MissingOptionException |
                    InvalidOptionException | GenericUserException e) {
                String technicalInfo = String.format("Unable to add item to cart. %s. Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                        e.getClass().getSimpleName(), cartItemRequest.getTermId(), cartItemRequest.getCourseCode(), cartItemRequest.getRegGroupCode(), cartItemRequest.getRegGroupId(), cartItemRequest.getGradingOptionId(), cartItemRequest.getCredits());
                LOGGER.debug(technicalInfo, e);

                UserMessageResult message = new UserMessageResult("Unable to add item to cart");

                if (e instanceof CourseDoesNotExistException) {
                    message.setMessageKey(((CourseDoesNotExistException) e).getMessageKey());
                } else if (e instanceof RegGroupNotOfferedException) {
                    message.setMessageKey(((RegGroupNotOfferedException) e).getMessageKey());
                    message.getData().put("state", ((RegGroupNotOfferedException) e).getState()); // Put the RG state into the data object
                } else if (e instanceof MissingOptionException) {
                    message.setMessageKey(((MissingOptionException) e).getMessageKey());
                } else if (e instanceof InvalidOptionException) {
                    message.setMessageKey(((InvalidOptionException) e).getMessageKey());
                    message.getData().put("option", ((InvalidOptionException) e).getOption()); // Put the invalid option value into the data object
                } else if (e instanceof GenericUserException) {
                    message = ((GenericUserException) e).getUserMessage();
                }

                message.setConsoleMessage(technicalInfo);
                message.setType(UserMessageResult.MessageTypes.ERROR);

                responseResult.addMessage(message);
                responseResult.setError();
            }

            results.add(responseResult);
        }

        // Always return OK since each result item will contain its own results
        return Response.ok(results).build();
    }

    protected CartItemResult addCourseToCartLocal(CartItemResult cartItem, ContextInfo contextInfo)
            throws CourseDoesNotExistException, RegGroupNotOfferedException, MissingOptionException, InvalidOptionException, GenericUserException {

        CartItemResult result;

        try {
            // Make sure the cartId has been set into the cart item
            if (StringUtils.isEmpty(cartItem.getCartId())) {
                CartResult cart = searchForCart(contextInfo, cartItem.getTermId());
                cartItem.setCartId(cart.getCartId());
            }

            // Resolve the reg group based on the data available
            RegGroupSearchResult rg = resolveRegGroup(cartItem.getTermId(), null,
                    cartItem.getCourseCode(), cartItem.getRegGroupCode(), cartItem.getRegGroupId(), contextInfo);

            // will throw error if RG is not in proper state
            validateRegGroupSearchResult(rg);

            cartItem.setRegGroupId(rg.getRegGroupId());
            cartItem.setRegGroupCode(rg.getRegGroupName());

            // get the credit and grading options for this course
            ResultValueGroupCourseOptions rvgCourseOptions = getScheduleOfClassesService().getCreditAndGradingOptions(rg.getCourseOfferingId(), contextInfo);
            cartItem.setCreditOptions(new ArrayList<>(rvgCourseOptions.getCreditOptions().values()));
            cartItem.setGradingOptions(new HashMap<>(rvgCourseOptions.getGradingOptions()));
            cartItem.setGradingOptionCount(cartItem.getGradingOptions().size());

            // lets try to set the credit and grading options if they don't exist and the rvg have only single options.
            if (StringUtils.isEmpty(cartItem.getCredits())) {
                cartItem.setCredits(getSingleCreditValue(rvgCourseOptions));
            }
            if (StringUtils.isEmpty(cartItem.getGrading())) {
                cartItem.setGrading(getSingleGradingOptionsValue(rvgCourseOptions));
            }

            validateCartItemOptions(cartItem, rvgCourseOptions);

            result = addCourseToCart(cartItem.getCartId(), cartItem.getRegGroupId(), cartItem.getGrading(), cartItem.getCredits(), rvgCourseOptions, cartItem.getCourseCode(), contextInfo);

            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(result));

        } catch (GenericUserException e) {
            if (e.getUserMessage() != null && StringUtils.isEmpty(e.getUserMessage().getMessageKey())) {
                e.getUserMessage().setMessageKey(
                        CourseRegistrationCartClientServiceConstants.CartMessages.ADD_TO_CART_UNKNOWN_EXCEPTION);
            }

            throw e;
        } catch (InvalidParameterException | MissingParameterException | OperationFailedException |
                PermissionDeniedException | DoesNotExistException | DataValidationErrorException | ReadOnlyException |
                VersionMismatchException | LoginException e) {
            String technicalInfo = String.format("Technical Info:(termId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartItem.getTermId(), cartItem.getCourseCode(), cartItem.getRegGroupCode(), cartItem.getRegGroupId(), cartItem.getGrading(), cartItem.getCredits());

            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult("Unable to add item to cart. Exception.");
            userMessage.setMessageKey(CourseRegistrationCartClientServiceConstants.CartMessages.ADD_TO_CART_UNKNOWN_EXCEPTION);
            userMessage.setConsoleMessage(technicalInfo);
            userMessage.setType(UserMessageResult.MessageTypes.ERROR);
            LOGGER.error(technicalInfo, e);

            throw new GenericUserException(userMessage, e);
        }

        return result;
    }

    @Override
    public Response removeItemFromCartRS(String termId, String cartItemId) {
        Response.ResponseBuilder response;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            CartResult cart = searchForCart(contextInfo, termId);

            CartItemResult result = removeItemFromCart(cart.getCartId(), cartItemId);
            // build the link to add this item.
            result.getActionLinks().add(buildUndoLink(result.getTermId(), result.getRegGroupId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Exception e) {
            LOGGER.warn("Error with removing item from cart", e);
            // Convert the generic user message into something useful to the UI.
            String technicalInfo = String.format("Technical Info:(termId:[%s] cartItemId[%s])", termId, cartItemId);

            UserMessageResult userMessage = new UserMessageResult("Error removing item from cart.");
            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public Response updateCartItemRS(String termId, String cartItemId, String credits, String gradingOptionId) {
        Response.ResponseBuilder response;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            CartResult cart = searchForCart(contextInfo, termId);

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(updateCartItem(contextInfo, cart.getCartId(), cartItemId, credits, gradingOptionId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            // Convert the generic user message into something useful to the UI.
            String technicalInfo = String.format("Technical Info:(termId:[%s] cartItemId[%s] gradingOptionId[%s] credits:[%s] )",
                    termId, cartItemId, gradingOptionId, credits);

            UserMessageResult userMessage = new UserMessageResult("Unable to update item in cart");
            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public Response undoDeleteCourseRS(String termId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) {
        return addCourseToCartRS(termId, regGroupId, courseCode, regGroupCode, gradingOptionId, credits);
    }

    @Override
    public Response searchForCartRS(String termId) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(searchForCart(ContextUtils.createDefaultContextInfo(), termId));
        } catch (Exception e) {
            LOGGER.warn("Error while searching for cart: searchForCartRS", e);
            // Convert the generic user message into something useful to the UI.
            String technicalInfo = String.format("Technical Info:(termId:[%s])", termId);

            UserMessageResult userMessage = new UserMessageResult("Error while searching for Cart.");
            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public Response clearCartRS(String termId, String termCode) {
        Response.ResponseBuilder response;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            // get termId
            termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);

            if(contextInfo.getPrincipalId() == null || contextInfo.getPrincipalId().isEmpty()
                    || termId == null || termId.isEmpty()){
                String technicalInfo = String.format("Technical Info:(principalId:[%s] termId:[%s])", contextInfo.getPrincipalId(), termId);

                UserMessageResult userMessage = new UserMessageResult("Error clearing cart");
                userMessage.setConsoleMessage(technicalInfo);
                response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
            } else {
                super.clearCartByPerson(contextInfo.getPrincipalId(),termId,contextInfo);
                response = Response.noContent();
            }
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response clearCartAliasRS(String termId, String termCode) {
        return clearCartRS(termId, termCode);
    }

    @Override
    public Response searchForCartAliasRS(String termId) {
        return searchForCartRS(termId);
    }

    @Override
    public Response submitCartAliasRS(String cartId) {
        Response.ResponseBuilder response;
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            RegistrationRequestInfo info = submitCart(contextInfo, cartId);
            response = Response.ok(info);
        } catch (Exception e) {
            LOGGER.warn("Error submitting cart id {} for {}", cartId, contextInfo.getPrincipalId(), e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error submitting cart");
            String technicalInfo = String.format("Technical Info: (cartId:[%s])",cartId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
    }

    @Override
    public Response addCourseToCartAliasRS(String termId, String regGroupId, String courseCode, String regGroupCode, String gradingOptionId, String credits) {
        return addCourseToCartRS(termId, regGroupId, courseCode, regGroupCode, gradingOptionId, credits);
    }

    private CartItemResult buildCartItemResultFromRequest(AddCourseToCartRequestInfo request) {
        CartItemResult result = new CartItemResult();
        result.setCartItemId(request.getCartItemId());
        result.setCourseCode(request.getCourseCode());
        result.setCredits(request.getCredits());
        result.setGrading(request.getGradingOptionId());
        result.setRegGroupCode(request.getRegGroupCode());
        result.setRegGroupId(request.getRegGroupId());
        result.setTermId(request.getTermId());

        return result;
    }

    /*
     * Resolve reg group information from the user input. In order to take advantage of caching we must use the
     * scheduleOfClassesService getRegGroup methods.
     */
    protected RegGroupSearchResult resolveRegGroup(String termId, String termCode, String courseCode, String regGroupCode, String regGroupId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, CourseDoesNotExistException {
        RegGroupSearchResult rg = null;

        if (!StringUtils.isEmpty(regGroupId)) {
            rg = getScheduleOfClassesService().getRegGroup(regGroupId, contextInfo);
        }

        if (rg == null) {
            if (StringUtils.isEmpty(courseCode)) {
                throw new CourseDoesNotExistException(CourseRegistrationCartClientServiceConstants.CartMessages.COURSE_CODE_REQUIRED,
                        "Course Code cannot be empty");
            }

            if (StringUtils.isEmpty(regGroupCode)) {
                throw new CourseDoesNotExistException(CourseRegistrationCartClientServiceConstants.CartMessages.REG_GROUP_CODE_REQUIRED,
                        "Section cannot be empty");
            }

            // Couldn't find by rgId, try looking up by term, course code, and reg group code
            rg = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termId, termCode, courseCode, regGroupCode, contextInfo);
        }

        if (rg == null) {
            throw new CourseDoesNotExistException(CourseRegistrationCartClientServiceConstants.CartMessages.COURSE_DOES_NOT_EXIST,
                    "Cannot find the course " + courseCode + " in the selected term");
        }

        return rg;
    }

    /*
     * Validates that the cart item has all of the required options selected and that those selections are valid for the course.
     */
    protected void validateCartItemOptions(CartItemResult cartItem, ResultValueGroupCourseOptions rvgCourseOptions) throws MissingOptionException, InvalidOptionException {
        if (isMissingOptions(cartItem.getCredits(), cartItem.getGrading(), rvgCourseOptions)) {
            // Return the cartItem to the user populated with the possible options the user can select
            throw new MissingOptionException(
                    CourseRegistrationCartClientServiceConstants.CartMessages.CREDIT_OR_GRADING_OPTIONS_MISSING);
        }

        if (!isCreditValueValid(cartItem.getCredits(), rvgCourseOptions)) {
            throw new InvalidOptionException(
                    CourseRegistrationCartClientServiceConstants.CartMessages.CREDIT_OPTION_INVALID, cartItem.getCredits());
        }

        if (!isGradingOptionValid(cartItem.getGrading(), rvgCourseOptions)) {
            throw new InvalidOptionException(
                    CourseRegistrationCartClientServiceConstants.CartMessages.GRADING_OPTION_INVALID, cartItem.getGrading());
        }
    }

    /*
     * Validates that the reg gorup is in the proper state. If not populate with the proper error messages.
     */
    protected void validateRegGroupSearchResult(RegGroupSearchResult regGroupSearchResult) throws RegGroupNotOfferedException {
        if (!LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY.equals(regGroupSearchResult.getRegGroupState())) {
            throw new RegGroupNotOfferedException(CourseRegistrationCartClientServiceConstants.CartMessages.REG_GROUP_NOT_OFFERED,
                    regGroupSearchResult.getRegGroupState());
        }
    }

    protected Link buildUndoLink(String termId, String regGroupId, String gradingOptionId, String credits) {
        String action = CourseRegistrationCartClientServiceConstants.ACTION_LINKS.UNDO_DELETE_COURSE.getAction();
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART +
                "/%s?termId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat, action, termId, regGroupId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    private Response.ResponseBuilder getResponse(Response.Status status, Object entity) {
        Response.ResponseBuilder response = Response.status(status).entity(entity);
        response.header("Access-Control-Allow-Header", "Content-Type");
        response.header("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        return response;
    }
}
