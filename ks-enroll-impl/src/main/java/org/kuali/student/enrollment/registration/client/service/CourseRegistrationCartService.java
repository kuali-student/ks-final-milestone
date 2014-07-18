package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;
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
import javax.ws.rs.Path;

/**
 * Services for Registration Cart Operations
 */
@Path("/")
public interface CourseRegistrationCartService {


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
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     * @throws LoginException
     */
    public RegistrationRequestInfo submitCart(ContextInfo contextInfo, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException;

    /**
     * Removes item from cart
     *
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId id of the item to delete from the cart.
     * @return removed item so that the action can be undone
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    public CartItemResult removeItemFromCart(String cartId, String cartItemId) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;


    /**
     * This method allows users to set credit and grading options on an item in their cart using the course registration
     * service's update method.
     *
     * @param contextInfo     context of the call
     * @param cartId     ID of the registrationRequest representing the cart
     * @param cartItemId ID of the specific item being updated
     * @param credits    The numeric string value of credit student registration option. Must convert to KualiDecimal.
     * @param gradingOptionId    the RVG key of grading student registration option. (org.kuali.rvg.grading.PassFail, org.kuali.rvg.grading.Letter)
     * @return The updated cartItem
     * @throws javax.security.auth.login.LoginException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     */
    public CartItemResult updateCartItem(ContextInfo contextInfo, String cartId, String cartItemId, String credits, String gradingOptionId) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException;



    /**
     * Looks up cart information for the given user and term. if no cart exists, one is created
     *
     * @param contextInfo context of the call
     * @param termId term for the cart
     * @return a Cart Result that contains the courses a student is intending to enroll in for the given term.
     * @throws javax.security.auth.login.LoginException
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     */
    public CartResult searchForCart(ContextInfo contextInfo, String termId) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException;



}


