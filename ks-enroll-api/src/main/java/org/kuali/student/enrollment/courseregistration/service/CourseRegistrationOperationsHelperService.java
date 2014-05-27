package org.kuali.student.enrollment.courseregistration.service;


import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "CourseRegistrationService", targetNamespace = CourseRegistrationNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
interface CourseRegistrationOperationsHelperService {

    // CourseRegistration methods

    /**
     * Drops a student from a course.
     *
     * @param registrationRequestItem contains the registration request item.
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the RegistrationRequestItem
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException courseRegistrationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException courseRegistrationId or
     *         contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     */
     RegistrationRequestItem dropPersonFromCourseOffering(@WebParam(name = "registrationRequestItem") RegistrationRequestItem registrationRequestItem ,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
      throws DoesNotExistException,
             InvalidParameterException,
             MissingParameterException,
             OperationFailedException,
             PermissionDeniedException,
             VersionMismatchException,
             ReadOnlyException,
             DataValidationErrorException;

    // CourseRegistration methods

    /**
     * Register a student for a course.
     *
     * @param registrationRequestItem contains the registration request item.
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of the service
     *        operation
     * @return the RegistrationRequestInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException courseRegistrationId is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException courseRegistrationId or
     *         contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException an attempt at supplying information
     *         designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     */
     RegistrationRequestInfo registerPersonForCourseOffering(@WebParam(name = "registrationRequestItem") RegistrationRequestItem registrationRequestItem,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
      throws DoesNotExistException,
             InvalidParameterException,
             MissingParameterException,
             OperationFailedException,
             PermissionDeniedException,
             VersionMismatchException,
             ReadOnlyException,
             DataValidationErrorException;

}

