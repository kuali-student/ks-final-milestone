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
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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

import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class EnumerationManagementServiceDecorator implements EnumerationManagementService {

    private EnumerationManagementService nextDecorator;

    public EnumerationManagementService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(EnumerationManagementService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<EnumerationInfo> getEnumerations(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumerations(contextInfo);
    }

    @Override
    public EnumerationInfo getEnumeration(String enumerationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumeration(enumerationKey, contextInfo);
    }

    @Override
    public List<EnumeratedValueInfo> getEnumeratedValues(String enumerationKey, String contextTypeKey, String contextValue, Date contextDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getEnumeratedValues(enumerationKey, contextTypeKey, contextValue, contextDate, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateEnumeratedValue(String validationTypeKey, String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateEnumeratedValue(validationTypeKey, enumerationKey, code, enumeratedValueInfo, contextInfo);
    }

    @Override
    public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateEnumeratedValue(enumerationKey, code, enumeratedValueInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteEnumeratedValue(String enumerationKey, String code, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteEnumeratedValue(enumerationKey, code, contextInfo);
    }

    @Override
    public EnumeratedValueInfo addEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().addEnumeratedValue(enumerationKey, code, enumeratedValueInfo, contextInfo);
    }
}
