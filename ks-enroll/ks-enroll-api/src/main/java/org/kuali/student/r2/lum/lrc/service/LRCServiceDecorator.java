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
import org.kuali.student.r2.lum.lrc.dto.ResultValueGroupInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

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
	public List<String> getAllResultValueGroupTypes(
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getAllResultValueGroupTypes( context);
	}

	@Override
	public String getResultValueGroupType(
			String resultValueGroupTypeKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroupType(resultValueGroupTypeKey,context);
	}

	@Override
	public ResultValueGroupInfo getResultValueGroup(String resultValueGroupKey,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		return getNextDecorator().getResultValueGroup(resultValueGroupKey,context);
	}

	@Override
	public List<ResultValueGroupInfo> getResultValueGroupList(
			List<String> resultValueGroupKeyList, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResultValueGroupInfo> getResultValueGroupByResultValue(
			String resultValueKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResultValueKeysByResultType(String resultTypeKey,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueGroupInfo getResultValueGroupByKey(String resultGroupKey,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueGroupInfo getResultValueGroupById(String resultGroupId,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResultValueGroupIdsByResultGroupType(
			String resultValueGroupTypeKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResultValueGroupIdsByResultValue(
			String resultValueId, String resultGroupTypeKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResultValueRangesForResultValueGroupById(
			String resultValueGroupId, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResultValueRangesForResultValueGroup(
			String resultValueGroupKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllResultValueRanges(
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueGroupInfo createResultValueGroup(
			String resultValueGroupKey,
			ResultValueGroupInfo resultValueGroupInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueGroupInfo updateResultValueGroup(
			String resultValueGroupId,
			ResultValueGroupInfo resultValueGroupInfo, ContextInfo context)
			throws DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteResultValueGroup(String resultValueGroupId,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResultValueInfo> getResultValuesForResultValueGroupById(
			String resultValueGroupId, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResultValueInfo> getResultValuesForResultValueGroup(
			String resultValueGroupKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueInfo createResultValue(String resultValueKey,
			ResultValueInfo resultValueInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueInfo updateResultValue(String resultValueId,
			ResultValueInfo resultValueInfo, ContextInfo context)
			throws DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteResultValue(String resultValueId,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResultValueRangeInfo> getAllResultValueRangesByRangeType(
			String resultValueRangeTypeKey, ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueInfo createResultValueRange(String resultValueRangeKey,
			ResultValueRangeInfo resultValueRangeInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultValueGroupInfo updateResultValueRange(
			String resultValueRangeInfoId,
			ResultValueRangeInfo resultValueRangeInfo, ContextInfo context)
			throws DataValidationErrorException,
			org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteResultValueRange(String resultValueRangeInfoId,
			ContextInfo context)
			throws org.kuali.student.common.exceptions.DoesNotExistException,
			org.kuali.student.common.exceptions.InvalidParameterException,
			org.kuali.student.common.exceptions.MissingParameterException,
			org.kuali.student.common.exceptions.OperationFailedException,
			org.kuali.student.common.exceptions.PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
