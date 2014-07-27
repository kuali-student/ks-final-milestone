/*
 * Copyright 2014 The Kuali Foundation
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
package org.kuali.student.enrollment.academicrecord.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.kuali.student.r2.common.dto.StatusInfo;

@WebService(name = "AcademicRecordSubscrptionService", targetNamespace = AcademicRecordSubscriptionServiceNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicRecordSubscrptionService {

    /**
     * Subscribe a callback to listen for new StudentCourseRecords for any course.
     *
     * @param action action to listen for
     * @param academicRecordCallbackService callback executable code to be invoked when the change event executes.
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback subscription id that can be used to explicitly remove subscription of the listener.
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String subscribeToStudentCourseRecords(
            @WebParam(name = "action") SubscriptionActionEnum action,
            @WebParam(name = "academicRecordCallbackService") AcademicRecordCallbackService academicRecordCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Subscribe a callback to listen for new StudentCourseRecords for a given term.
     *
     * @param action action to listen for
     * @param termId the identifier for the Term to be retrieved.
     * @param academicRecordCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback subscription id that can be used to explicitly remove subscription of the listener.
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String subscribeToStudentCourseRecordsByTerm(
            @WebParam(name = "action") SubscriptionActionEnum action,
            @WebParam(name = "termId") String termId,
            @WebParam(name = "academicRecordCallbackService") AcademicRecordCallbackService academicRecordCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Subscribe a callback to listen for new StudentCourseRecords for a given course.
     *
     * @param action action to listen for
     * @param courseCode the identifier for the Course to be retrieved.
     * @param academicRecordCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback subscription id that can be used to explicitly remove subscription of the listener.
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String subscribeToStudentCourseRecordsByCourse(
            @WebParam(name = "action") SubscriptionActionEnum action,
            @WebParam(name = "courseCode") String courseCode,
            @WebParam(name = "academicRecordCallbackService") AcademicRecordCallbackService academicRecordCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Subscribe a callback to listen for new StudentCourseRecords for a given type.
     *
     * @param action action to listen for
     * @param studentCourseRecordTypeKey the identifier for the student course record type to be retrieved.
     * @param academicRecordCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback subscription id that can be used to explicitly remove subscription of the listener.
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String subscribeToStudentCourseRecordsByType(
            @WebParam(name = "action") SubscriptionActionEnum action,
            @WebParam(name = "studentCourseRecordTypeKey") String studentCourseRecordTypeKey,
            @WebParam(name = "academicRecordCallbackService") AcademicRecordCallbackService academicRecordCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Subscribe a callback to listen for a particular StudentCourseRecord being updated.
     *
     * @param action action to listen for
     * @param studentCourseRecordId the identifier for the student course record that is updated.
     * @param academicRecordCallbackService callback executable code to be invoked when the change event executes
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return callback subscription id that can be used to explicitly remove subscription of the listener.
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String subscribeToStudentCourseRecord(
            @WebParam(name = "action") SubscriptionActionEnum action,
            @WebParam(name = "studentCourseRecordId") String studentCourseRecordId,
            @WebParam(name = "academicRecordCallbackService") AcademicRecordCallbackService academicRecordCallbackService,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Remove the subscription of callback to stop listening.
     *
     * @param subscriptionId the identifier for the subscription
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return status indicating if it it unsubscribed
     * @throws DoesNotExistException courseRegistrationId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseRegistrationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo removeSubscription(
            @WebParam(name = "subscriptionId") String subscriptionId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
