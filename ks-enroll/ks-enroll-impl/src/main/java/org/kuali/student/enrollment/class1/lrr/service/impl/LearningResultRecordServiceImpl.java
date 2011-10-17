package org.kuali.student.enrollment.class1.lrr.service.impl;

import org.kuali.student.enrollment.class1.lrr.dao.LrrDao;
import org.kuali.student.enrollment.class1.lrr.dao.LrrTypeDao;
import org.kuali.student.enrollment.class1.lrr.dao.ResultSourceDao;
import org.kuali.student.enrollment.class1.lrr.model.LearningResultRecordEntity;
import org.kuali.student.enrollment.class1.lrr.model.ResultSourceEntity;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LrrServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "org.kuali.student.enrollment.lrr.service.LearningResultRecordService", serviceName = "LearningResultRecordService", portName = "LearningResultRecordService", targetNamespace = "http://student.kuali.org/wsdl/lrr")
@Transactional(readOnly=true,noRollbackFor={org.kuali.student.common.exceptions.DoesNotExistException.class},rollbackFor={Throwable.class})
public class LearningResultRecordServiceImpl implements LearningResultRecordService {

    private LrrDao lrrDao;
    private LrrTypeDao lrrTypeDao;
    private ResultSourceDao resultSourceDao;
    private StateService stateService;

    @Override
    public LearningResultRecordInfo getLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(@WebParam(name = "lprId") String lprId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LearningResultRecordInfo> dtos = new ArrayList<LearningResultRecordInfo>();
        List<LearningResultRecordEntity> lrrs = lrrDao.getLearningResultRecordsForLpr(lprId);
        for (LearningResultRecordEntity lrr : lrrs) {
            LearningResultRecordInfo dto = lrr.toDto();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprIdList(@WebParam(name = "lprIdList") List<String> lprIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<LearningResultRecordInfo> dtos = new ArrayList<LearningResultRecordInfo>();
        List<LearningResultRecordEntity> lrrs = lrrDao.getLearningResultRecordsForLprIdList(lprIdList);
        for (LearningResultRecordEntity lrr : lrrs) {
            LearningResultRecordInfo dto = lrr.toDto();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(@WebParam(name = "lprIdList") List<String> sourceIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly=false)
    public LearningResultRecordInfo createLearningResultRecord(@WebParam(name = "learningResultRecord") LearningResultRecordInfo learningResultRecord, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecord.getId());
        if (lrr != null){
            throw new AlreadyExistsException("Record already exists for " + learningResultRecord.getId());
        }else{
            LearningResultRecordEntity newLrr = new LearningResultRecordEntity(learningResultRecord);

            List<ResultSourceEntity> resultSourceEntities = new ArrayList();
            if (learningResultRecord.getResultSourceIdList() != null && !learningResultRecord.getResultSourceIdList().isEmpty()){
                resultSourceEntities = resultSourceDao.findByIds(learningResultRecord.getResultSourceIdList());
            }
            newLrr.setResultSourceList(resultSourceEntities);

            if (learningResultRecord.getStateKey() != null)  {
                newLrr.setLrrState(findState(LrrServiceConstants.COURSE_FINAL_GRADING_LIFECYCLE_KEY, learningResultRecord.getStateKey(), context));
            }

            if (learningResultRecord.getTypeKey() != null) {
                newLrr.setLrrType(lrrTypeDao.find(learningResultRecord.getTypeKey()));
            }

            lrrDao.merge(newLrr);
            return lrrDao.find(newLrr.getId()).toDto();
        }

    }

    @Override
    @Transactional(readOnly=false)
    public LearningResultRecordInfo updateLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "learningResultRecordInfo") LearningResultRecordInfo learningResultRecordInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null){
            throw new DoesNotExistException(learningResultRecordId);
        }

        LearningResultRecordEntity modifiedLrr = new LearningResultRecordEntity(learningResultRecordInfo);
        List<ResultSourceEntity> resultSourceEntities = new ArrayList();
        if (learningResultRecordInfo.getResultSourceIdList() != null && !learningResultRecordInfo.getResultSourceIdList().isEmpty()){
            resultSourceEntities = resultSourceDao.findByIds(learningResultRecordInfo.getResultSourceIdList());
        }
        modifiedLrr.setResultSourceList(resultSourceEntities);

        if (learningResultRecordInfo.getStateKey() != null)
            modifiedLrr.setLrrState(findState(LrrServiceConstants.COURSE_FINAL_GRADING_LIFECYCLE_KEY, learningResultRecordInfo.getStateKey(), context));
        if (learningResultRecordInfo.getTypeKey() != null)
            modifiedLrr.setLrrType(lrrTypeDao.find(learningResultRecordInfo.getTypeKey()));
        lrrDao.merge(modifiedLrr);

        return lrrDao.find(modifiedLrr.getId()).toDto();
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

    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        StateInfo stateInfo = stateService.getState(processKey, stateKey, context);
        return stateInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null) {
            throw new DoesNotExistException(learningResultRecordId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        StateEntity state = findState(LrrServiceConstants.COURSE_FINAL_GRADING_LIFECYCLE_KEY,LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY,context);

        if (state == null){
            throw new DoesNotExistException(LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY + " state not found");
        }

        lrr.setLrrState(state);
        lrrDao.merge(lrr);

        return status;

    }

    @Override
    public List<ValidationResultInfo> validateLearningResultRecord(@WebParam(name = "validationType") String validationType, @WebParam(name = "learningResultRecordInfo") LearningResultRecordInfo learningResultRecordInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultSourceInfo getResultSource(@WebParam(name = "resultSourceId") String resultSourceId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByIdList(@WebParam(name = "resultSourceIdList") List<String> resultSourceIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByType(@WebParam(name = "resultSourceTypeKey") String resultSourceTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly=false)
    public ResultSourceInfo createResultSource(@WebParam(name = "resultSourceInfo") ResultSourceInfo sourceInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly=false)
    public ResultSourceInfo updateResultSource(@WebParam(name = "resultSourceId") String resultSourceId, @WebParam(name = "resultSourceInfo") ResultSourceInfo resultSourceInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly=false)
    public StatusInfo deleteResultSource(@WebParam(name = "deleteResultSourceId") String resultSourceId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultSource(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultSourceInfo") ResultSourceInfo resultSourceInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public LrrDao getLrrDao() {
        return lrrDao;
    }

    public void setLrrDao(LrrDao lrrDao) {
        this.lrrDao = lrrDao;
    }

    public LrrTypeDao getLrrTypeDao() {
        return lrrTypeDao;
    }

    public void setLrrTypeDao(LrrTypeDao lrrTypeDao) {
        this.lrrTypeDao = lrrTypeDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public ResultSourceDao getResultSourceDao() {
        return resultSourceDao;
    }

    public void setResultSourceDao(ResultSourceDao resultSourceDao) {
        this.resultSourceDao = resultSourceDao;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprAndType(String lprId, String lrrType) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<LearningResultRecordInfo>();
    }

}
