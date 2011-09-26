package org.kuali.student.enrollment.class1.lrr.service.impl;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import java.util.List;

public class LearningResultRecordServiceImpl implements LearningResultRecordService {

    private LearningResultRecordServiceImpl lrrServiceImpl;

    public LearningResultRecordServiceImpl getLrrServiceImpl() {
        return lrrServiceImpl;
    }

    public void setLrrServiceImpl(LearningResultRecordServiceImpl lrrServiceImpl) {
        this.lrrServiceImpl = lrrServiceImpl;
    }

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
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
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
}
