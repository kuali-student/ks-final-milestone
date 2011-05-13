package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupTypeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;

public abstract class LRCServiceDecorator implements LRCService {

	protected LRCService nextDecorator;

	public LRCService getNextDecorator() {
		return nextDecorator;
	}

	public void setNextDecorator(LRCService nextDecorator) {
		this.nextDecorator = nextDecorator;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		return getNextDecorator().getDataDictionaryEntryKeys(context);
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		return getNextDecorator().getDataDictionaryEntry(entryKey, context);
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getType(typeKey, context);
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getAllowedTypesForType(ownerTypeKey,
				relatedRefObjectURI, context);
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey,
				relationTypeKey, context);
	}

	@Override
	public List<String> getProcessKeys(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getProcessKeys(typeKey, context);
	}

	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getState(processKey, stateKey, context);
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getStatesByProcess(processKey, context);
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getInitialValidStates(processKey, context);
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getNextHappyState(processKey,
				currentStateKey, context);
	}

	@Override
	public List<ResultValueGroupTypeInfo> getResultValueGroupTypes()
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupTypes();
	}

	@Override
	public ResultValueGroupTypeInfo getResultValueGroupType(
			String resultValueGroupTypeKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupType(
				resultValueGroupTypeKey);
	}

	@Override
	public ResultValueGroupInfo getResultValueGroup(String resultValueGroupKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroup(resultValueGroupKey);
	}

	@Override
	public List<ResultValueGroupInfo> getResultValueGroupList(
			List<String> resultValueGroupKeyList)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupList(
				resultValueGroupKeyList);
	}

	@Override
	public List<ResultValueGroupInfo> getResultValueGroupByResultValue(
			String resultValueKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupByResultValue(
				resultValueKey);
	}

	@Override
	public List<String> getResultValueKeysByResultType(String resultTypeKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueKeysByResultType(resultTypeKey);
	}

	@Override
	public ResultValueGroupInfo getResultValueGroupByKey(String resultGroupKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupByKey(resultGroupKey);
	}

	@Override
	public ResultValueGroupInfo getResultValueGroupById(String resultGroupId)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupById(resultGroupId);
	}

	@Override
	public List<String> getResultValueGroupIdsByResultGroupType(
			String resultValueGroupTypeKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupIdsByResultGroupType(
				resultValueGroupTypeKey);
	}

	@Override
	public List<String> getResultValueGroupIdsByResultValue(
			String resultValueId, String resultGroupTypeKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupIdsByResultValue(
				resultValueId, resultGroupTypeKey);
	}

	@Override
	public List<ResultValueGroupTypeInfo> getResultValueRangesForResultValueGroupById(
			String resultValueGroupId)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueRangesForResultValueGroupById(
				resultValueGroupId);
	}

	@Override
	public List<ResultValueGroupTypeInfo> getResultValueRangesForResultValueGroup(
			String resultValueGroupKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueRangesForResultValueGroup(
				resultValueGroupKey);
	}

	@Override
	public ResultValueGroupInfo createResultValueGroup(
			String resultValueGroupKey,
			ResultValueGroupInfo resultValueGroupInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().createResultValueGroup(resultValueGroupKey,
				resultValueGroupInfo);
	}

	@Override
	public ResultValueGroupInfo updateResultValueGroup(
			String resultValueGroupId, ResultValueGroupInfo resultValueGroupInfo)
			throws DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException,
			VersionMismatchException {
		return getNextDecorator().updateResultValueGroup(resultValueGroupId,
				resultValueGroupInfo);
	}

	@Override
	public StatusInfo deleteResultValueGroup(String resultValueGroupId)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().deleteResultValueGroup(resultValueGroupId);
	}

	@Override
	public List<ResultValueInfo> getResultValuesForResultValueGroupById(
			String resultValueGroupId)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException {
		return getNextDecorator().getResultValuesForResultValueGroupById(
				resultValueGroupId);
	}

	@Override
	public List<ResultValueInfo> getResultValuesForResultValueGroup(
			String resultValueGroupKey)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException {
		return getNextDecorator().getResultValuesForResultValueGroupById(
				resultValueGroupKey);
	}

	@Override
	public ResultValueInfo createResultValue(String resultValueKey,
			ResultValueInfo resultValueInfo) throws AlreadyExistsException,
			DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().createResultValue(resultValueKey,
				resultValueInfo);
	}

	@Override
	public ResultValueInfo updateResultValue(String resultValueId,
			ResultValueInfo resultValueInfo)
			throws DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException,
			VersionMismatchException {
		return getNextDecorator().updateResultValue(resultValueId,
				resultValueInfo);
	}

	@Override
	public StatusInfo deleteResultValue(String resultValueId)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().deleteResultValue(resultValueId);
	}

}
