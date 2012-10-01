package org.kuali.student.r2.core.class1.enumerationmanagement.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
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
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;

public class EnumerationManagementServiceValidationDecorator extends EnumerationManagementServiceDecorator implements HoldsValidator, HoldsDataDictionaryService {
    
    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
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
