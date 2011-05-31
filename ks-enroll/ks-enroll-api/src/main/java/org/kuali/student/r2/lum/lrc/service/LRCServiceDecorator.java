package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupInfo;

import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

public class LRCServiceDecorator implements LRCService {

	protected LRCService nextDecorator;

	public LRCService getNextDecorator() {
		return nextDecorator;
	}

	public void setNextDecorator(LRCService nextDecorator) {
		this.nextDecorator = nextDecorator;
	}

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getStatesByProcess(processKey, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getState(processKey, stateKey, context);
    }

    @Override
    public List<String> getProcessKeys(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getProcessKeys(typeKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getNextHappyState(processKey, currentStateKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getInitialValidStates(processKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return nextDecorator.getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return nextDecorator.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateResultValue(validationType, resultValueInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultGroup(String validationType, ResultValueGroupInfo resultValueGroupInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.validateResultGroup(validationType, resultValueGroupInfo, context);
    }


    @Override
    public ResultValueGroupInfo updateResultValueGroup(String resultValueGroupKey, ResultValueGroupInfo resultValueGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateResultValueGroup(resultValueGroupKey, resultValueGroupInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId, ResultValueInfo resultValueInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return nextDecorator.updateResultValue(resultValueId, resultValueInfo, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesIdList(List<String> resultValueGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getResultValuesIdList(resultValueGroupId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValueGroup(String resultValueGroupKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return nextDecorator.getResultValuesForResultValueGroup(resultValueGroupKey, context);
    }

    @Override
    public List<ResultValueGroupInfo> getResultValueGroupsByType(String resultValueGroupType, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getResultValueGroupsByType(resultValueGroupType, context);
    }

    @Override
    public List<ResultValueGroupInfo> getResultValueGroupsByResultValue(String resultValueKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getResultValueGroupsByResultValue(resultValueKey, context);
    }

    @Override
    public List<ResultValueGroupInfo> getResultValueGroupListByResultGroupKeyList(List<String> resultValueGroupKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getResultValueGroupListByResultGroupKeyList(resultValueGroupKeyList, context);
    }

    @Override
    public ResultValueGroupInfo getResultValueGroupByKey(String resultValueGroupKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getResultValueGroupByKey(resultValueGroupKey, context);
    }

    @Override
    public List<String> getResultValueByResultType(ResultValueInfo resulValueType, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getResultValueByResultType(resulValueType, context);
    }

    @Override
    public StatusInfo deleteResultValueGroup(String resultValueGroupKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteResultValueGroup(resultValueGroupKey, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteResultValue(resultValueId, context);
    }

    @Override
    public ResultValueGroupInfo createResultValueGroup(String resultValueGroupKey, ResultValueGroupInfo resultValueGroupInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createResultValueGroup(resultValueGroupKey, resultValueGroupInfo, context);
    }

    @Override
    public ResultValueInfo createResultValue(ResultValueInfo resultValueInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.createResultValue(resultValueInfo, context);
    }



}
