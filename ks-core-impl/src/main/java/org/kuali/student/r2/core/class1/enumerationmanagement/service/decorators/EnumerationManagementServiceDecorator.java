package org.kuali.student.r2.core.class1.enumerationmanagement.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

public class EnumerationManagementServiceDecorator implements EnumerationManagementService{

    private EnumerationManagementService nextDecorator;

    public EnumerationManagementService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

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

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

        return getNextDecorator().getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException, InvalidParameterException {

        return getNextDecorator().search(searchRequestInfo, contextInfo);
    }
}
