package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

public abstract class LRCServiceDecorator implements LRCService {

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
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroup(resultValuesGroupKey, contextInfo);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> resultValuesGroupKeys, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByKeys(resultValuesGroupKeys, contextInfo);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultValue(resultValueKey, contextInfo);
    }

    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(String resultScaleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultScale(resultScaleKey, contextInfo);
    }

    @Override
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupKeysByType(resultValuesGroupTypeKey, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(String resultScaleKey,
            String resultValuesGroupTypeKey,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValuesGroup(resultScaleKey, resultValuesGroupTypeKey, gradeValuesGroupInfo, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(String resultValuesGroupKey,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValuesGroup(resultValuesGroupKey, gradeValuesGroupInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultValuesGroup(resultValuesGroupKey, contextInfo);
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValue(resultValueKey, contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByKeys(List<String> resultValueKeys, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesByKeys(resultValueKeys, contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroup(resultValuesGroupKey, contextInfo);
    }

    @Override
    public ResultValueInfo createResultValue(String resultScaleKey,
            String resultValueTypeKey,
            ResultValueInfo resultValueInfo, ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValue(resultScaleKey, resultValueTypeKey, resultValueInfo, contextInfo);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueKey, ResultValueInfo resultValueInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValue(resultValueKey, resultValueInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueKey, ContextInfo contextInfo) throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteResultValue(resultValueKey, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateResultValue(validationType, resultValueInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultValuesGroup(validationType, gradeValuesGroupInfo, contextInfo);
    }

    @Override
    public ResultScaleInfo getResultScale(String resultScaleKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getResultScale(resultScaleKey, contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForScale(resultScaleKey, contextInfo);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(List<String> resultValuesGroupKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroups(resultValuesGroupKeys, contextInfo);
    }

    @Override
    public ResultValueInfo getCreateResultValueForScale(String resultValue, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateResultValueForScale(resultValue, scaleKey, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin, String creditValueMax, String creditValueIncrement, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateRangeCreditResultValuesGroup(creditValueMin, creditValueMax, creditValueIncrement, scaleKey, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateMultipleCreditResultValuesGroup(creditValues, scaleKey, contextInfo);
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateFixedCreditResultValuesGroup(creditValue, scaleKey, contextInfo);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(String resultScaleTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultScaleType(resultScaleTypeKey, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateResultScale(String validationType, ResultScaleInfo gradeScaleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultScale(validationType, gradeScaleInfo, contextInfo);
    }

    @Override
    public ResultScaleInfo updateResultScale(String resultScaleKey, ResultScaleInfo resultScaleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateResultScale(resultScaleKey, resultScaleInfo, contextInfo);
    }

    @Override
    public List<ResultScaleInfo> getResultScalesByKeys(List<String> resultScaleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultScalesByKeys(resultScaleKeys, contextInfo);
    }

    @Override
    public List<String> getResultScaleKeysByType(String resultScaleTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultScaleKeysByType(resultScaleTypeKey, contextInfo);
    }

    @Override
    public StatusInfo deleteResultScale(String resultScaleKey, ContextInfo contextInfo) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultScale(resultScaleKey, contextInfo);
    }

    @Override
    public ResultScaleInfo createResultScale(String resultScaleTypeKey, ResultScaleInfo resultScaleInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createResultScale(resultScaleTypeKey, resultScaleInfo, contextInfo);
    }

    @Override
    public ResultValueInfo getResultValueForScaleAndValue(String resultScaleKey, String value, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValueForScaleAndValue(resultScaleKey, value, contextInfo);
    }

    @Override
    public List<String> getResultValueKeysByType(String resultValueTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValueKeysByType(resultValueTypeKey, contextInfo);
    }
}
