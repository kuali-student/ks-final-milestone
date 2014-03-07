package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationOptionResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Services for Registration Cart Operations
 */
@Path("/")
public interface CourseRegistrationCartClientService {

    /**
     * The REST version of submitCart
     *
     * @param userId override of principal ID
     * @param cartId ID of the registrationRequest representing the cart
     * @return A Response containing the Boolean value of TRUE or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/submitCart")
    public Response submitCartRS(                                 @QueryParam("cartId") String cartId);

    /**
     * This method takes a cart and calls submit on the course registration service
     *
     * @param contextInfo context of the call
     * @param cartId ID of the registrationRequest representing the cart
     * @return A Response with the Boolean value of TRUE
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws AlreadyExistsException
     * @throws LoginException
     */
    public RegistrationResponseInfo submitCart(ContextInfo contextInfo, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException;

    /**
     * The REST version of addCourseToCart
     *
     * @param cartId          ID of the registrationRequest representing the cart
     * @param courseCode      course offering to add to the cart
     * @param regGroupId      Optional. Will speed things up if passed instead of (courseCode, regGroupCode)
     * @param regGroupCode    reg group we want to add
     * @param gradingOptionId the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @param credits         The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @return Response containing the cart item that was updated or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/addCourseToCart")
    public Response addCourseToCartRS(
                                      @QueryParam("cartId") String cartId,
                                      @QueryParam("courseCode") String courseCode,
                                      @QueryParam("regGroupId") String regGroupId,
                                      @QueryParam("regGroupCode") String regGroupCode,
                                      @QueryParam("gradingOptionId") String gradingOptionId,
                                      @QueryParam("credits") String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;

    /**
     * The REST version of removeItemFromCart
     *
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId id of the item to delete from the cart.
     * @return Response(with add action link) containing the cart item that was updated or a server error response. As Well as action links to undo the removal.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/removeItemFromCart")
    public Response removeItemFromCartRS(@QueryParam("cartId") String cartId,
                                         @QueryParam("cartItemId") String cartItemId);

    /**
     * Removes item from cart
     *
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId id of the item to delete from the cart.
     * @return removed item so that the action can be undone
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     */
    public CartItemResult removeItemFromCart(String cartId, String cartItemId) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;


    /**
     * The REST version of updateCartItem
     *
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId ID of the specific item being updated
     * @param credits    The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @param grading    the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @return Response containing the cart item that was updated or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/updateCartItem")
    public Response updateCartItemRS(                                     @QueryParam("cartId") String cartId,
                                     @QueryParam("cartItemId") String cartItemId,
                                     @QueryParam("credits") String credits,
                                     @QueryParam("grading") String grading);

    /**
     * This method allows users to set credit and grading options on an item in their cart using the course registration
     * service's update method.
     *
     * @param contextInfo     context of the call
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId ID of the specific item being updated
     * @param credits    The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @param grading    the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @return The updated cartItem
     * @throws LoginException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     * @throws VersionMismatchException
     */
    public CartItemResult updateCartItem(ContextInfo contextInfo, String cartId, String cartItemId, String credits, String grading) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/searchForCart")
    public Response searchForCartRS(@QueryParam("termId") String termId);

    /**
     * Looks up cart information for the given user and term. if no cart exists, one is created
     *
     * @param contextInfo context of the call
     * @param termId term for the cart
     * @return a Cart Result that contains the courses a student is intending to enroll in for the given term.
     * @throws LoginException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     */
    public CartResult searchForCart(ContextInfo contextInfo, String termId) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;


    /**
     * Gets the registration options of the for the course term and reg group id
     *
     * @param courseCode course code
     * @param termId     term key
     * @param regGroupId registration group id
     * @return a registration result option
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getStudentRegistrationOptions")
    public RegistrationOptionResult getStudentRegistrationOptions(@QueryParam("courseCode") String courseCode,
                                                                  @QueryParam("termId") String termId,
                                                                  @QueryParam("regGroupId") String regGroupId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}


