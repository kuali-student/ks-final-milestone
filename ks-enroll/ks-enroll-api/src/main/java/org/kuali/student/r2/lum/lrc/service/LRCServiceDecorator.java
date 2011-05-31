package org.kuali.student.r2.lum.lrc.service;

import java.util.List;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
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
    public ResultComponentInfo getResultComponent(String resultComponentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultComponent(resultComponentId, context);
    }

    @Override
    public List<ResultComponentInfo> getResultComponentsByIdList(List<String> resultComponentIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultComponentsByIdList(resultComponentIdList, context);
    }

    @Override
    public List<ResultComponentInfo> getResultComponentsByResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultComponentsByResultValue(resultValueId, context);
    }

    @Override
    public List<ResultComponentInfo> getResultComponentsByType(String resultComponentTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultComponentsByType(resultComponentTypeKey, context);
    }

    @Override
    public ResultComponentInfo createResultComponent(ResultComponentInfo resultComponentInfo, ContextInfo context) throws org.kuali.student.r2.common.exceptions.AlreadyExistsException, org.kuali.student.r2.common.exceptions.DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().createResultComponent(resultComponentInfo, context);
    }

    @Override
    public ResultComponentInfo updateResultComponent(String resultComponentKey, ResultComponentInfo resultComponentInfo, ContextInfo context) throws org.kuali.student.r2.common.exceptions.DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, org.kuali.student.r2.common.exceptions.VersionMismatchException {
        return getNextDecorator().updateResultComponent(resultComponentKey, resultComponentInfo, context);
    }

    @Override
    public StatusInfo deleteResultComponent(String resultComponentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().deleteResultComponent(resultComponentId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIdList(List<String> resultValueIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultValuesByIdList(resultValueIdList, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultComponent(String resultComponentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultComponent(resultComponentId, context);
    }

    @Override
    public ResultValueInfo createResultValue(ResultValueInfo resultValueInfo, ContextInfo context) throws org.kuali.student.r2.common.exceptions.AlreadyExistsException, org.kuali.student.r2.common.exceptions.DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().createResultValue(resultValueInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId, ResultValueInfo resultValueInfo, ContextInfo context) throws org.kuali.student.r2.common.exceptions.DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException, org.kuali.student.r2.common.exceptions.VersionMismatchException {
        return getNextDecorator().updateResultValue(resultValueId, resultValueInfo, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().deleteResultValue(resultValueId, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultValue(validationType, resultValueInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultComponent(String validationType, ResultComponentInfo resultComponentInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultComponent(validationType, resultComponentInfo, context);
    }

    @Override
    public ResultScaleInfo getResultScale(String resultScaleId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, org.kuali.student.common.exceptions.PermissionDeniedException {
        return getNextDecorator().getResultScale(resultScaleId, context);
    }
}
