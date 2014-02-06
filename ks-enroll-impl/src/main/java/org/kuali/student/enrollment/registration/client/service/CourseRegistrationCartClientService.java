package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
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
    public Response submitCartRS(@QueryParam("userId") String userId,
                                 @QueryParam("cartId") String cartId);

    /**
     * This method takes a cart and calls submit on the course registration service
     *
     * @param userId override of principal ID
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
    public RegistrationResponseInfo submitCart(String userId, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException;


    /**
     * The REST version of updateCartItem
     *
     * @param userId     override of principal ID
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId ID of the specific item being updated
     * @param credits    The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @param grading    the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @return Response containing the cart item that was updated or a server error response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/updateCartItem")
    public Response updateCartItemRS(@QueryParam("userId") String userId,
                                     @QueryParam("cartItem") String cartId,
                                     @QueryParam("cartItemId") String cartItemId,
                                     @QueryParam("credits") String credits,
                                     @QueryParam("grading") String grading);

    /**
     * This method allows users to set credit and grading options on an item in their cart using the course registration
     * service's update method.
     *
     * @param userId     override of principal ID
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
    public RegistrationRequestItemInfo updateCartItem(String userId, String cartId, String cartItemId, String credits, String grading) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException;

}
