package org.kuali.student.krms.test;

import java.util.List;

import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import org.kuali.student.lum.lrc.service.LrcService;

public class DummyLrcService implements LrcService {

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String arg0) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public SearchResult search(SearchRequest arg0) throws MissingParameterException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String arg0) {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getObjectTypes() {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CredentialTypeInfo> getCredentialTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CredentialTypeInfo getCredentialType(String credentialTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CreditTypeInfo> getCreditTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CreditTypeInfo getCreditType(String creditTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<GradeTypeInfo> getGradeTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public GradeTypeInfo getGradeType(String gradeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ResultComponentTypeInfo> getResultComponentTypes() throws OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ResultComponentTypeInfo getResultComponentType(String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CredentialInfo getCredential(String credentialKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CredentialInfo> getCredentialsByKeyList(List<String> credentialKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getCredentialKeysByCredentialType(String credentialTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CreditInfo getCredit(String creditKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getCreditKeysByCreditType(String creditTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ScaleInfo getScale(String scaleKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public GradeInfo getGrade(String gradeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        
        if(gradeKey.equals(Constants.PASS_GRADE_ID)) {
            GradeInfo info = new GradeInfo();
            info.setName("Pass");
            info.setRank("1");
            info.setValue("pass");
            info.setId(Constants.PASS_GRADE_ID);
            
            return info;
        }
        else if(gradeKey.equals(Constants.FAIL_GRADE_ID)) {
            GradeInfo info = new GradeInfo();
            info.setName("Fail");
            info.setRank("2");
            info.setValue("fail");
            info.setId(Constants.FAIL_GRADE_ID);
            
            return info;
        }
        else {
            throw new DoesNotExistException();
        }
    }

    @Override
    public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getGradeKeysByGradeType(String gradeTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<GradeInfo> getGradesByScale(String scale) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<GradeInfo> translateGrade(String gradeKey, String scaleKey, String translateScaleKey) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String compareGrades(String gradeKey, String scaleKey, String compareGradeKey, String compareScaleKey) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ResultComponentInfo getResultComponent(String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getResultComponentIdsByResultComponentType(String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getResultComponentIdsByResult(String resultValueId, String resultComponentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ResultComponentInfo createResultComponent(String resultComponentTypeKey, ResultComponentInfo resultComponentInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public ResultComponentInfo updateResultComponent(String resultComponentId, ResultComponentInfo resultComponentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteResultComponent(String resultComponentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
