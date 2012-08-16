package org.kuali.student.r2.core.class1.enumerationmanagement.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
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
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;

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
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
