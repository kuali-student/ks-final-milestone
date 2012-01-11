/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.enumerationmanagement.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class EnumerationManagementServiceDecorator {

    private EnumerationManagementService nextDecorator;

    public EnumerationManagementService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(EnumerationManagementService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    public List<EnumerationInfo> getEnumerations(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumerations(contextInfo);
    }

    public EnumerationInfo getEnumeration(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumeration(enumerationKey, contextInfo);
    }

    public List<EnumeratedValueInfo> getEnumeratedValues(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "contextTypeKey") String contextTypeKey, @WebParam(name = "contextValue") String contextValue, @WebParam(name = "contextDate") Date contextDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumeratedValues(enumerationKey, contextTypeKey, contextValue, contextDate, contextInfo);
    }

    public EnumeratedValueInfo updateEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateEnumeratedValue(enumerationKey, code, enumeratedValueInfo, contextInfo);
    }

    public StatusInfo deleteEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteEnumeratedValue(enumerationKey, code, contextInfo);
    }

    public EnumeratedValueInfo addEnumeratedValue(@WebParam(name = "enumerationKey") String enumerationKey, @WebParam(name = "enumeratedValueInfo") EnumeratedValueInfo enumeratedValueInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().addEnumeratedValue(enumerationKey, enumeratedValueInfo, contextInfo);
    }
}
