package org.kuali.student.r2.lum.lrc.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import javax.jws.WebParam;
import java.util.List;

public class LRCServiceDecorator implements LRCService {

    private LRCService nextDecorator;
    
    public LRCService getNextDecorator()
			throws OperationFailedException {
		if (null == nextDecorator) {
			throw new OperationFailedException("Misconfigured application: nextDecorator is null");
		}
        return nextDecorator;
    }
    public void setNextDecorator(LRCService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> resultValuesGroupIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByKeys(resultValuesGroupIds, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultValue(resultValueId, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultScale(resultScaleKey, contextInfo);
    }

    @Override
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupKeysByType(resultValuesGroupTypeKey, context);
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(String resultScaleKey, String resultValuesGroupTypeKey,ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValuesGroup(gradeValuesGroupInfo.getResultScaleKey(), gradeValuesGroupInfo.getTypeKey(), gradeValuesGroupInfo, context);
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(String resultValuesGroupKey,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValuesGroup(resultValuesGroupKey, gradeValuesGroupInfo, context);
    }

    @Override
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValue(resultValueId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByKeys(List<String> resultValueIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesByKeys(resultValueIds, context);
    }

    @Override
    public List<String> getResultValueKeysByType(@WebParam(name = "resultValueTypeKey") String resultValueTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValueKeysByType(resultValueTypeKey, contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public ResultValueInfo createResultValue(String resultScaleKey,
                                             String resultValueTypeKey,ResultValueInfo resultValueInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValue(resultValueInfo.getResultScaleKey(), resultValueInfo.getTypeKey(), resultValueInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValue(resultValueId, resultValueInfo, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException {
        return getNextDecorator().deleteResultValue(resultValueId, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateResultValue(validationType, resultValueInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultValuesGroup(validationType, gradeValuesGroupInfo, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(@WebParam(name = "creditValue") String creditValue, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateFixedCreditResultValuesGroup(creditValue, scaleKey, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(@WebParam(name = "creditValueMin") String creditValueMin, @WebParam(name = "creditValueMax") String creditValueMax, @WebParam(name = "creditValueIncrement") String creditValueIncrement, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateRangeCreditResultValuesGroup(creditValueMin, creditValueMax, creditValueIncrement, scaleKey, contextInfo);
    }

    @Override
    public ResultScaleInfo getResultScale(String resultScaleId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getResultScale(resultScaleId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
		   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForScale(resultScaleKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultScale(@WebParam(name = "validationType") String validationType, @WebParam(name = "gradeScaleInfo") ResultScaleInfo gradeScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultScale(validationType,gradeScaleInfo,contextInfo);
    }

    @Override
    public ResultValueInfo getResultValueForScaleAndValue(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "value") String value, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValueForScaleAndValue(resultScaleKey,value,contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(@WebParam(name = "resultValuesGroupKeys") List<String> resultValuesGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroups(resultValuesGroupKeys,contextInfo);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultScaleType(resultScaleTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForResultScaleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultScaleIds(criteria,context);
    }

    @Override
    public List<ResultScaleInfo> searchForResultScales(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultScales(criteria,context);
    }

    @Override
    public List<String> searchForResultValueIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultValueIds(criteria,context);
    }

    @Override
    public List<ResultValueInfo> searchForResultValues(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultValues(criteria,context);
    }

    @Override
    public List<String> searchForResultValuesGroupIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultValuesGroupIds(criteria,context);
    }

    @Override
    public List<ResultValuesGroupInfo> searchForResultValuesGroups(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForResultValuesGroups(criteria,context);
    }

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "context") ContextInfo context)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return getNextDecorator().getSearchTypes(context);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchType(searchTypeKey, context);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo context) throws PermissionDeniedException, 
            InvalidParameterException,MissingParameterException {
        SearchResultInfo sr = null;
        try {
            sr =  getNextDecorator().search(searchRequest, context);
        } catch (OperationFailedException ox){
            throw new MissingParameterException(ox.getMessage());
        } 
        return sr;
    }

    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(@WebParam(name = "creditValues") List<String> creditValues, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateMultipleCreditResultValuesGroup(creditValues,scaleKey,contextInfo);
    }

    @Override
    public ResultScaleInfo createResultScale(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "resultGroupInfo") ResultScaleInfo resultScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createResultScale(resultScaleTypeKey,resultScaleInfo,contextInfo);
    }

    @Override
    public ResultScaleInfo updateResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "resultScaleInfo") ResultScaleInfo resultScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateResultScale(resultScaleKey,resultScaleInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultScale(resultScaleKey,contextInfo);
    }

    @Override
    public ResultValueInfo getCreateResultValueForScale(@WebParam(name = "resultValue") String resultValue, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateResultValueForScale(resultValue,scaleKey,contextInfo);
    }

    @Override
    public List<ResultScaleInfo> getResultScalesByKeys(@WebParam(name = "resultScaleKeys") List<String> resultScaleKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultScalesByKeys(resultScaleKeys,contextInfo);
    }

    @Override
    public List<String> getResultScaleKeysByType(@WebParam(name = "resultScaleTypeKey") String resultScaleTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultScaleKeysByType(resultScaleTypeKey,contextInfo);
    }

}
