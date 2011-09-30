package org.kuali.student.enrollment.class1.lrr.service.impl;

import org.kuali.student.enrollment.class1.lrr.dao.LrrDao;
import org.kuali.student.enrollment.class1.lrr.dao.LrrTypeDao;
import org.kuali.student.enrollment.class1.lrr.model.LearningResultRecordEntity;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dao.StateDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;

import javax.jws.WebParam;
import java.util.List;

public class LearningResultRecordServiceImpl implements LearningResultRecordService {

    private LrrDao lrrDao;
    private LrrTypeDao lrrTypeDao;

    private StateService stateService;

    @Override
    public LearningResultRecordInfo getLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(@WebParam(name = "lprId") String lprId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprIdList(@WebParam(name = "lprIdList") List<String> lprIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(@WebParam(name = "lprIdList") List<String> sourceIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public LearningResultRecordInfo createLearningResultRecord(@WebParam(name = "learningResultRecord") LearningResultRecordInfo learningResultRecord, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public LearningResultRecordInfo updateLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "learningResultRecordInfo") LearningResultRecordInfo learningResultRecordInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null){
            throw new DoesNotExistException(learningResultRecordId);
        }

        LearningResultRecordEntity modifiedLrr = new LearningResultRecordEntity(learningResultRecordInfo);
        if (learningResultRecordInfo.getStateKey() != null)
            modifiedLrr.setLrrState(findState("kuali.assessment.process.course.grading", learningResultRecordInfo.getStateKey(), context));
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
    public StatusInfo deleteLearningResultRecord(@WebParam(name = "learningResultRecordId") String learningResultRecordId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
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
    public ResultSourceInfo createResultSource(@WebParam(name = "resultSourceInfo") ResultSourceInfo sourceInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultSourceInfo updateResultSource(@WebParam(name = "resultSourceId") String resultSourceId, @WebParam(name = "resultSourceInfo") ResultSourceInfo resultSourceInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
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

}
