/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;


/**
 * The Course Registration Callback Service allows programs to register to listen for changes that happen via the course
 * registration service.
 *
 * @version 0.0.7
 * @author Kuali Student Team (Norm)
 */
@WebService(name = "CourseRegistrationCallbackService", targetNamespace = CourseRegistrationCallbackRegistrationNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseRegistrationCallbackRegistrationService {

    /**
     * Register a callback to listen for changes to a registration request
     *
     * Use case: Registration program listens for status updates so it can report progress as the registration engine processes items.
     * 
     * @param registrationRequestId the identifier for the CourseRegistration to be retrieved
     * @param registrationRequestChangedCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback registration id that can be used to explicitly deregister the listener
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String registerCallbackForRegistrationRequestChanges(@WebParam(name = "registrationRequestId") String registrationRequestId,
            @WebParam(name = "registrationRequestChangedCallbackService") RegistrationRequestChangedCallbackService registrationRequestChangedCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    
    /**
     * Register a callback to listen for any changes to any registration
     * 
     * Use Case 1: Student Accounts Fee Service listens for registration events and calculates tuition for the student
     * Use Case 2: Invalidate the Academic Record cache for the student so her AR can be recomputed
     *
     * @param registrationChangedCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback registration id that can be used to explicitly deregister the listener
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String registerCallbackForAnyRegistrationChange(
            @WebParam(name = "registrationChangedCallbackService") RegistrationChangedCallbackService registrationChangedCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    
    /**
     * Register a callback to listen for changes that result in an increase in seats
     *
     * Use case: Waitlist processor registers itself to listen for any changes to any registrations for the AO it is attached to.
     * This can happen due to drops and swaps.
     * 
     * @param activityOfferingIds ids of activity offerings to be watched 
     * @param availableSeatsIncreasedCallbackService callback executable code to be invoked when the event occurs
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback registration id that can be used to explicitly deregister the listener
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String registerCallbackForIncreaseInAvailbleSeats(@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds,
            @WebParam(name = "availableSeatsIncreasedCallbackService") AvailableSeatsIncreasedCallbackService availableSeatsIncreasedCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    
    
    /**
     * Unregister a previously registered callback
     *
     * @param callbackRegistrationId id of a previously registered callback
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback registration id that can be used to explicitly deregister the listener
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String unregisterCallback(@WebParam(name = "callbackRegistrationId") String callbackRegistrationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
