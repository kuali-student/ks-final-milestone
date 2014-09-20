package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseCodeValidationResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ResultValueGroupCourseOptions;
import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;
import org.kuali.student.enrollment.registration.client.service.exception.CourseDoesNotExistException;
import org.kuali.student.enrollment.registration.client.service.exception.GenericUserException;
import org.kuali.student.enrollment.registration.client.service.exception.MissingOptionException;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 *
 */
public class CourseRegistrationCartClientServiceImpl extends CourseRegistrationCartServiceImpl implements CourseRegistrationCartClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationCartClientServiceImpl.class);

    @Override
    public Response submitCartRS(String cartId) {
        Response.ResponseBuilder response;

        try {
            RegistrationRequestInfo info = submitCart(ContextUtils.createDefaultContextInfo(), cartId);
            response = Response.ok(info);
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
    public Response addCourseToCartRS(String termId, String cartId, String regGroupId, String courseCode,  String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();


            if(cartId == null || cartId.isEmpty()){
                RegistrationRequestInfo request = createCart(contextInfo.getPrincipalId(), termId, contextInfo);
                cartId = request.getId();
            }

            RegGroupSearchResult rg = resolveRegGroup(termId, null, courseCode, regGroupCode, regGroupId, contextInfo);

            // will throw error if RG is not in proper state
            processRegGroupSearchValidation(rg, courseCode, regGroupCode);

            // get the credit and grading options for this course
            ResultValueGroupCourseOptions rvgCourseOptions = getScheduleOfClassesService().getCreditAndGradingOptions(rg.getCourseOfferingId(), contextInfo);

            // lets try to set the credit and grading options if they don't exist and the rvg have only single options.
            if(credits == null || credits.isEmpty()){
                credits = getSingleCreditValue(rvgCourseOptions);
            }
            if(gradingOptionId == null || gradingOptionId.isEmpty()){
                gradingOptionId = getSingleGradingOptionsValue(rvgCourseOptions);
            }

            if(isMissingOptions(credits, gradingOptionId, rvgCourseOptions)){
                // build a fake cartItem to return to user. This will be populated with the possible options the user can select
                CartItemResult optionsCartItem = new CartItemResult();
                optionsCartItem.setCourseCode(courseCode);
                optionsCartItem.setRegGroupCode(rg.getRegGroupName());
                optionsCartItem.setRegGroupId(rg.getRegGroupId());
                optionsCartItem.setCredits(credits);
                optionsCartItem.setGrading(gradingOptionId);
                optionsCartItem.setGradingOptionCount(optionsCartItem.getGradingOptions().size());
                optionsCartItem.setTermId(termId);

                optionsCartItem.getCreditOptions().addAll(rvgCourseOptions.getCreditOptions().values());
                // need to do a copy for thread safety
                optionsCartItem.setGradingOptions(new HashMap<String, String>(rvgCourseOptions.getGradingOptions().size()));
                optionsCartItem.getGradingOptions().putAll(rvgCourseOptions.getGradingOptions());

                throw new MissingOptionException(optionsCartItem);
            }

            if(!isCreditValueValid(credits, rvgCourseOptions)){
                throw new InvalidParameterException("Credit option " + credits + " is not valid for this course: " + courseCode + "(" + rg.getRegGroupName() + ")");
            }

            if(!isGradingOptionValid(gradingOptionId, rvgCourseOptions)){
                throw new InvalidParameterException("Grading option " + gradingOptionId + " is not valid for this course: " + courseCode + "(" + rg.getRegGroupName() + ")");
            }

            CartItemResult result = addCourseToCart(cartId,rg.getRegGroupId(),gradingOptionId, credits,rvgCourseOptions, courseCode, contextInfo);

            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        }  catch(CourseDoesNotExistException e) {
            String technicalInfo = String.format("Unable to add item to cart. CourseDoesNotExistException. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.debug(technicalInfo, e);
            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, new CourseCodeValidationResult(e.getMessageKey(), e.getCourseCode()));
        } catch (MissingOptionException e) {
            String technicalInfo = String.format("Unable to add item to cart. MissingOptionException. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.debug(technicalInfo, e);
            response = getResponse(Response.Status.BAD_REQUEST, e.getCartItemOptions());
        } catch (DoesNotExistException e) {
            String technicalInfo = String.format("Unable to add item to cart. DoesNotExistException. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.debug(technicalInfo, e);
            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, e.getMessage());
        } catch (GenericUserException e) {
            String technicalInfo = String.format("Unable to add item to cart. GenericUserException. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.error(technicalInfo,e);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getUserMessage());
        } catch (Exception e) {
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to add item to cart. Exception.");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);

            userMessage.setConsoleMessage(technicalInfo);
            userMessage.setType(UserMessageResult.MessageTypes.ERROR);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
            LOGGER.error(technicalInfo,e);
        }

        return response.build();
    }

    /**
     * Resolve reg group information from the user input. In order to take advantage of caching we must use the
     * scheduleOfClassesService getRegGroup methods.
     * @param termId    termId or termCode are required if there is no regGroupId
     * @param termCode  termId or termCode are required if there is no regGroupId
     * @param courseCode required if no regGroupId
     * @param regGroupCode required if no regGroupId
     * @param regGroupId  if this is provided no other fields are needed
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    protected RegGroupSearchResult resolveRegGroup(String termId, String termCode, String courseCode, String regGroupCode, String regGroupId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        RegGroupSearchResult rg = null;

        if (!StringUtils.isEmpty(regGroupId)) {
            rg = getScheduleOfClassesService().getRegGroup(regGroupId, contextInfo);
        } else {
            if(courseCode == null || courseCode.isEmpty()){
                throw new DoesNotExistException("Course Code cannot be empty");
            }
            if(regGroupCode == null || regGroupCode.isEmpty()){
                throw new DoesNotExistException("Section cannot be empty");
            }
            // get the registration group
            rg = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termId, termCode, courseCode, regGroupCode, contextInfo);
        }

        if (rg == null) {
            if (courseCode != null && !StringUtils.isEmpty(courseCode)) {
                String technicalInfo = String.format("getRegGroup. Cannot find Course. Technical Info:(termId:[%s] termCode:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s])",
                        termId, termCode, courseCode, regGroupCode, regGroupId);
                LOGGER.warn(technicalInfo);
                throw new DoesNotExistException("Cannot find the course " + courseCode + " in the selected term");
            } else {
                throw new DoesNotExistException("Course Code cannot be empty");
            }
        }

        return rg;
    }


    /**
     * process reg group search validation for Rest Services
     *
     * @param rg             the reg group to be validated
     * @param courseCode     needed only for debug message
     * @param regGroupCode   needed only for debug message
     * @throws GenericUserException
     */
    protected void processRegGroupSearchValidation(RegGroupSearchResult rg, String courseCode, String regGroupCode) throws GenericUserException {
        ValidationResultInfo regGroupValidation = validateRegGroupSearchResult(rg, courseCode, regGroupCode);

        if (regGroupValidation.isError()) {
            String technicalInfo = String.format("Technical Info:(term:[%s] id:[%s] state:[%s] )",
                    rg.getTermId(), rg.getRegGroupId(), rg.getRegGroupState());

            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage(regGroupValidation.getMessage());
            userMessage.setDetailedMessage(regGroupValidation.getMessage());
            userMessage.setConsoleMessage(regGroupValidation.getMessage() + " " + technicalInfo);
            userMessage.setType(UserMessageResult.MessageTypes.ERROR);
            throw new GenericUserException(userMessage);
        }
    }

    /**
     * Validates that the reg gorup is in the proper state. If not populate with the proper error messages.
     * @param regGroupSearchResult
     * @param courseCode     needed only for debug message
     * @param regGroupCode   needed only for debug message
     * @return
     */
    protected ValidationResultInfo validateRegGroupSearchResult(RegGroupSearchResult regGroupSearchResult, String courseCode, String regGroupCode) {

        ValidationResultInfo resultInfo = new ValidationResultInfo();
        if (!LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY.equals(regGroupSearchResult.getRegGroupState())) {
            switch (regGroupSearchResult.getRegGroupState()) {
                case LuiServiceConstants.REGISTRATION_GROUP_CANCELED_STATE_KEY:
                    resultInfo.setError(courseCode + " (" + regGroupCode + ") is cancelled");
                    break;
                case LuiServiceConstants.REGISTRATION_GROUP_SUSPENDED_STATE_KEY:
                    resultInfo.setError(courseCode + " (" + regGroupCode + ") is suspended");
                    break;
                default:
                    resultInfo.setError(courseCode + " (" + regGroupCode + ") is not offered");
            }
        }

        return resultInfo;
    }

    @Override
    public Response undoDeleteCourseRS(String termId, String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        return  addCourseToCartRS(termId, cartId,regGroupId, courseCode, regGroupCode, gradingOptionId, credits);

    }

    private Response.ResponseBuilder getResponse(Response.Status status, Object entity) {
        //The request needs additional options (HTTP status 400 Bad Request)
        Response.ResponseBuilder response = Response.status(status).entity(entity);
        response.header("Access-Control-Allow-Header", "Content-Type");
        response.header("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        return response;
    }

    protected Link buildUndoLink(String termId, String cartId, String regGroupId, String gradingOptionId, String credits) {
        String action = "undoDeleteCourse";
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/undoDeleteCourse?termId=%s&cartId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat,termId, cartId, regGroupId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    @Override
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId) {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = removeItemFromCart(cartId, cartItemId);
            // build the link to add this item.
            result.getActionLinks().add(buildUndoLink(result.getTermId(), cartId, result.getRegGroupId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (Exception e) {
            LOGGER.warn("Error with removing item from cart", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error removing item from cart. ");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] cartItemId[%s])", cartId, cartItemId);

            userMessage.setConsoleMessage(technicalInfo);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
        }

        return response.build();
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
    public Response searchForCartRS(String termId) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(searchForCart(ContextUtils.createDefaultContextInfo(), termId));
        } catch (Exception e) {
            LOGGER.warn("Error while searching for cart: searchForCartRS", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Error while searching for Cart.");
            String technicalInfo = String.format("Technical Info:(termId:[%s])", termId);

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
                UserMessageResult userMessage = new UserMessageResult();
                userMessage.setGenericMessage("Error clearing cart");
                String technicalInfo = String.format("Technical Info:(principalId:[%s] termId:[%s])", contextInfo.getPrincipalId(), termId);

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


}
