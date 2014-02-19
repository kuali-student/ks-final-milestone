/*
 * Copyright 2014 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.studentterm.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.enrollment.studentterm.infc.StudentTermDetail;
import org.kuali.student.enrollment.studentterm.infc.StudentTermDetailDisplay;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import javax.jws.WebParam;


/**
 * Initial contract. NEEDS TO BE CHANGED TO DTOs.
 *
 * This service currently provides views of information managed in
 * otehr services. Supports only lookups by student and term at this
 * time but should be expanded to support various relationship lookups
 * in support of other uses.
 */

@WebService(name = "StudentTermService", targetNamespace = StudentTermServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface StudentTermService {

    /**
     * Retrieves StudentTermDetail records by Student and Term.
     *
     * @param personId the student Id
     * @param termId the term Id
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the list of StudentTermDetail records
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId, termId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTermDetail> getStudentTermDetailsByStudentAndTerm(@WebParam(name = "personId") String personId,
                                                                         @WebParam(name = "termId") String termId,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException,
               MissingParameterException,
               OperationFailedException,
               PermissionDeniedException;

    /**
     * Retrieves StudentTermDetailDisplay records by Student.
     *
     * @param personId the student Id
     * @param termId the term Id
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the list of StudentTermDetailView records
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException personId, termId, or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTermDetailDisplay> getStudentTermDetailDisplaysByStudentAndTerm(@WebParam(name = "personId") String personId,
                                                                                       @WebParam(name = "termId") String termId,
                                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException,
               MissingParameterException,
               OperationFailedException,
               PermissionDeniedException;
}
