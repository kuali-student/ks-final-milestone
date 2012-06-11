package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
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
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroup(resultValuesGroupKey, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> resultValuesGroupKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByKeys(resultValuesGroupKeys, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultValue(resultValueKey, context);
    }

    @Override
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupKeysByType(resultValuesGroupTypeKey, context);
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValuesGroup(gradeValuesGroupInfo, context);
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
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultValuesGroup(resultValuesGroupKey, context);
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValue(resultValueKey, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIds(List<String> resultValueKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesByIds(resultValueKeys, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroup(resultValuesGroupKey, context);
    }

    @Override
    public ResultValueInfo createResultValue(ResultValueInfo resultValueInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValue(resultValueInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueKey, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValue(resultValueKey, resultValueInfo, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteResultValue(resultValueKey, context);
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
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(List<String> resultValuesGroupKeys, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroups(resultValuesGroupKeys, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateResultValueWithinRange(String resultValue, String resultValuesGroupKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateResultValueWithinRange(resultValue, resultValuesGroupKey, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin, String creditValueMax, String creditValueIncrement, String scaleId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateRangeCreditResultValuesGroup(creditValueMin, creditValueMax, creditValueIncrement, scaleId, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues, String scaleId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateMultipleCreditResultValuesGroup(creditValues, scaleId, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue, String scaleId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCreateFixedCreditResultValuesGroup(creditValue, scaleId, context);
    }
    
    
}
