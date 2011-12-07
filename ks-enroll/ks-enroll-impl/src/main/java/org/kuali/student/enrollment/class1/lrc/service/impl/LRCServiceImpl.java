package org.kuali.student.enrollment.class1.lrc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class1.lrc.dao.LrcTypeDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultScaleDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultValueDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultValuesGroupDao;
import org.kuali.student.enrollment.class1.lrc.model.ResultScaleEntity;
import org.kuali.student.enrollment.class1.lrc.model.ResultValueEntity;
import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(name = "LrcService", serviceName = "LrcService", portName = "LrcService", targetNamespace = "http://student.kuali.org/wsdl/lrc")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LRCServiceImpl implements LRCService {

    private ResultValuesGroupDao resultValuesGroupDao;
    private ResultValueDao resultValueDao;
    private ResultScaleDao resultScaleDao;
    private StateService stateService;
    private LrcTypeDao lrcTypeDao;

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ResultValuesGroupEntity entity = resultValuesGroupDao.find(resultValuesGroupId);
        if (entity != null){
            return entity.toDto();
        }
        return null;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIdList(@WebParam(name = "resultValuesGroupIdList") List<String> resultValuesGroupIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ResultValuesGroupEntity> entities = resultValuesGroupDao.findByIds(resultValuesGroupIdList);
        List<ResultValuesGroupInfo> resultValuesGroupInfos = new ArrayList<ResultValuesGroupInfo>();
        for (ResultValuesGroupEntity entity : entities){
            resultValuesGroupInfos.add(entity.toDto());
        }
        return resultValuesGroupInfos;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getResultValuesGroupIdsByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ResultValuesGroupEntity entity = resultValuesGroupDao.find(gradeValuesGroupInfo.getKey());
        if (entity != null){
            throw new AlreadyExistsException(gradeValuesGroupInfo.getKey() + " already exists");
        }

        ResultValuesGroupEntity newEntity = new ResultValuesGroupEntity(gradeValuesGroupInfo);
        if (StringUtils.isNotBlank(gradeValuesGroupInfo.getStateKey())){
            newEntity.setState(findState(LrcServiceConstants.RESULT_VALUES_GROUP_PROCESS_KEY,gradeValuesGroupInfo.getStateKey(),context));
        }

        if (StringUtils.isNotBlank(gradeValuesGroupInfo.getTypeKey())){
            newEntity.setType(lrcTypeDao.find(gradeValuesGroupInfo.getTypeKey()));
        }

        if (gradeValuesGroupInfo.getResultValueKeys() != null){
             newEntity.setResultValues(resultValueDao.findByIds(gradeValuesGroupInfo.getResultValueKeys()));
        }

        resultValuesGroupDao.persist(newEntity);

        return resultValuesGroupDao.find(newEntity.getId()).toDto();
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
         ResultValueEntity entity = resultValueDao.find(resultValueId);
        if (entity != null){
            return entity.toDto();
        }
        return null;
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIdList(@WebParam(name = "resultValueIdList") List<String> resultValueIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ResultValueEntity> entities = resultValueDao.findByIds(resultValueIdList);
        List<ResultValueInfo> infos = new ArrayList<ResultValueInfo>();
        for (ResultValueEntity entity : entities){
            infos.add(entity.toDto());
        }
        return infos;
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ResultScaleEntity entity = resultScaleDao.find(resultScaleKey);
        if (entity != null){
            return entity.toDto();
        }
        return null;
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey, @WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateProcessInfo getProcessByKey(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getProcessByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StateInfo stateInfo = stateService.getState(processKey, stateKey, context);
        return stateInfo;
    }

    @Override
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    private StateEntity findState(String processKey, String stateKey, ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException{
		StateEntity state = null;
		try {
			StateInfo stInfo = getState(processKey, stateKey, context);
			if(stInfo != null){
				state = new StateEntity(stInfo);
				return state;
			}
			else
				throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
		}
    }

    public ResultValuesGroupDao getResultValuesGroupDao() {
        return resultValuesGroupDao;
    }

    public void setResultValuesGroupDao(ResultValuesGroupDao resultValuesGroupDao) {
        this.resultValuesGroupDao = resultValuesGroupDao;
    }

    public ResultValueDao getResultValueDao() {
        return resultValueDao;
    }

    public void setResultValueDao(ResultValueDao resultValueDao) {
        this.resultValueDao = resultValueDao;
    }

    public ResultScaleDao getResultScaleDao() {
        return resultScaleDao;
    }

    public void setResultScaleDao(ResultScaleDao resultScaleDao) {
        this.resultScaleDao = resultScaleDao;
    }

    public LrcTypeDao getLrcTypeDao() {
        return lrcTypeDao;
    }

    public void setLrcTypeDao(LrcTypeDao lrcTypeDao) {
        this.lrcTypeDao = lrcTypeDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

}
