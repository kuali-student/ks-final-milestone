package org.kuali.student.enrollment.registration.client.service.impl;


import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.Link;
import org.kuali.student.enrollment.registration.client.service.dto.UserMessageResult;
import org.kuali.student.enrollment.registration.client.service.exception.GenericUserException;
import org.kuali.student.enrollment.registration.client.service.exception.MissingOptionException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

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
    public Response addCourseToCartRS(String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = addCourseToCart(ContextUtils.createDefaultContextInfo(), cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);
            // build the link to delete this item.
            result.getActionLinks().add(buildDeleteLink(cartId, result.getCartItemId(), result.getGrading(), result.getCredits()));

            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(result);
        } catch (MissingOptionException e) {
            String technicalInfo = String.format("Unable to add item to cart. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            response = getResponse(Response.Status.BAD_REQUEST, e.getCartItemOptions());
        } catch (DoesNotExistException e) {
            String technicalInfo = String.format("Unable to add item to cart. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.warn(technicalInfo,e);
            //The reg request does not exist (HTTP status 404 Not Found)
            response = getResponse(Response.Status.NOT_FOUND, e.getMessage());
        } catch (GenericUserException e) {
            String technicalInfo = String.format("Unable to add item to cart. Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);
            LOGGER.error(technicalInfo,e);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getUserMessage());
        } catch (Exception e) {
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to add item to cart.");
            String technicalInfo = String.format("Technical Info:(cartId:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s] gradingOptionId:[%s] credits:[%s] )",
                    cartId, courseCode, regGroupCode, regGroupId, gradingOptionId, credits);

            userMessage.setConsoleMessage(technicalInfo);
            userMessage.setType(UserMessageResult.MessageTypes.ERROR);
            response = getResponse(Response.Status.INTERNAL_SERVER_ERROR, userMessage);
            LOGGER.error(technicalInfo,e);
        }

        return response.build();
    }

    @Override
    public Response undoDeleteCourseRS(String cartId, String courseCode, String regGroupId, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        return addCourseToCartRS(cartId, courseCode, regGroupId, regGroupCode, gradingOptionId, credits);
    }

    private Response.ResponseBuilder getResponse(Response.Status status, Object entity) {
        //The request needs additional options (HTTP status 400 Bad Request)
        Response.ResponseBuilder response = Response.status(status).entity(entity);
        response.header("Access-Control-Allow-Header", "Content-Type");
        response.header("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET, OPTIONS");
        response.header("Access-Control-Allow-Origin", "*");
        return response;
    }

    protected Link buildUndoLink(String cartId, String regGroupId, String gradingOptionId, String credits) {
        String action = "undoDeleteCourse";
        String uriFormat = CourseRegistrationCartClientServiceConstants.SERVICE_NAME_LOCAL_PART + "/undoDeleteCourse?cartId=%s&regGroupId=%s&gradingOptionId=%s&credits=%s";
        String uri = String.format(uriFormat, cartId, regGroupId, gradingOptionId, credits);

        return new Link(action, uri);
    }

    @Override
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId, @QueryParam("cartItemId") String cartItemId) {
        Response.ResponseBuilder response;

        try {
            CartItemResult result = removeItemFromCart(cartId, cartItemId);
            // build the link to add this item.
            result.getActionLinks().add(buildUndoLink(cartId, result.getRegGroupId(), result.getGrading(), result.getCredits()));

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
    public Response getStudentRegistrationOptionsRS(String courseCode, String termId, String regGroupId) {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(getStudentRegistrationOptions(courseCode, termId, regGroupId));
        } catch (Exception e) {
            LOGGER.warn("Exception occurred", e);
            // Convert the generic user message into something useful to the UI.
            UserMessageResult userMessage = new UserMessageResult();
            userMessage.setGenericMessage("Unable to get student registration options.");
            String technicalInfo = String.format("Technical Info:(termId:[%s] courseCode[%s] regGroupId[%s] )", termId, courseCode, regGroupId);

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
}
