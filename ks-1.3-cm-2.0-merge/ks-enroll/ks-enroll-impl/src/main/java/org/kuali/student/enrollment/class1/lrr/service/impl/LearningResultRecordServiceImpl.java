package org.kuali.student.enrollment.class1.lrr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.enrollment.class1.lrr.dao.LrrDao;
import org.kuali.student.enrollment.class1.lrr.dao.ResultSourceDao;
import org.kuali.student.enrollment.class1.lrr.model.LearningResultRecordEntity;
import org.kuali.student.enrollment.class1.lrr.model.ResultSourceEntity;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LrrServiceConstants;
import org.kuali.student.r2.common.state.dto.StateInfo;
import org.kuali.student.r2.common.state.service.StateService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.enrollment.lrr.service.LearningResultRecordService", serviceName = "LearningResultRecordService", portName = "LearningResultRecordService", targetNamespace = "http://student.kuali.org/wsdl/lrr")
@Transactional(readOnly = true, noRollbackFor = {org.kuali.student.r2.common.exceptions.DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LearningResultRecordServiceImpl implements LearningResultRecordService {

    private LrrDao lrrDao;
    private ResultSourceDao resultSourceDao;

    @Override
    public LearningResultRecordInfo getLearningResultRecord(
            @WebParam(name = "learningResultRecordId") String learningResultRecordId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(@WebParam(name = "lprId") String lprId)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<LearningResultRecordInfo> dtos = new ArrayList<LearningResultRecordInfo>();
        List<LearningResultRecordEntity> lrrs = lrrDao.getLearningResultRecordsForLpr(lprId);
        for (LearningResultRecordEntity lrr : lrrs) {
            LearningResultRecordInfo dto = lrr.toDto();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprIds(
            @WebParam(name = "lprIds") List<String> lprIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<LearningResultRecordInfo> dtos = new ArrayList<LearningResultRecordInfo>();
        List<LearningResultRecordEntity> lrrs = lrrDao.getLearningResultRecordsForLprIds(lprIds);
        for (LearningResultRecordEntity lrr : lrrs) {
            LearningResultRecordInfo dto = lrr.toDto();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(
            @WebParam(name = "lprIds") List<String> sourceIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly = false)
    public LearningResultRecordInfo createLearningResultRecord(
            @WebParam(name = "learningResultRecord") LearningResultRecordInfo learningResultRecord,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecord.getId());
        if (lrr != null) {
            throw new AlreadyExistsException("Record already exists for " + learningResultRecord.getId());
        } else {
            LearningResultRecordEntity newLrr = new LearningResultRecordEntity(learningResultRecord);

            List<ResultSourceEntity> resultSourceEntities = new ArrayList();

            if (learningResultRecord.getResultSourceIds() != null
                    && !learningResultRecord.getResultSourceIds().isEmpty()) {
                try{
                resultSourceEntities = resultSourceDao.findByIds(learningResultRecord.getResultSourceIds());
                }catch (DoesNotExistException doesNotExist){
                        throw  new OperationFailedException( doesNotExist.getMessage(), doesNotExist);
                }
            }
            newLrr.setResultSourceList(resultSourceEntities);

            lrrDao.merge(newLrr);
            return lrrDao.find(newLrr.getId()).toDto();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public LearningResultRecordInfo updateLearningResultRecord(
            @WebParam(name = "learningResultRecordId") String learningResultRecordId,
            @WebParam(name = "learningResultRecordInfo") LearningResultRecordInfo learningResultRecordInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {

        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null) {
            throw new DoesNotExistException(learningResultRecordId);
        }

        LearningResultRecordEntity modifiedLrr = new LearningResultRecordEntity(learningResultRecordInfo);
        List<ResultSourceEntity> resultSourceEntities = new ArrayList();
        if (learningResultRecordInfo.getResultSourceIds() != null
                && !learningResultRecordInfo.getResultSourceIds().isEmpty()) {
            resultSourceEntities = resultSourceDao.findByIds(learningResultRecordInfo.getResultSourceIds());
        }
        modifiedLrr.setResultSourceList(resultSourceEntities);

        lrrDao.merge(modifiedLrr);

        return lrrDao.find(modifiedLrr.getId()).toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLearningResultRecord(
            @WebParam(name = "learningResultRecordId") String learningResultRecordId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null) {
            throw new DoesNotExistException(learningResultRecordId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        lrr.setLrrState(LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY);
        lrrDao.merge(lrr);

        return status;

    }

    @Override
    public List<ValidationResultInfo> validateLearningResultRecord(
            @WebParam(name = "validationType") String validationType,
            @WebParam(name = "learningResultRecordInfo") LearningResultRecordInfo learningResultRecordInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultSourceInfo getResultSource(@WebParam(name = "resultSourceId") String resultSourceId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByIds(
            @WebParam(name = "resultSourceIds") List<String> resultSourceIds,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByType(
            @WebParam(name = "resultSourceTypeKey") String resultSourceTypeKey,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly = false)
    public ResultSourceInfo createResultSource(@WebParam(name = "resultSourceInfo") ResultSourceInfo sourceInfo,
            @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly = false)
    public ResultSourceInfo updateResultSource(@WebParam(name = "resultSourceId") String resultSourceId,
            @WebParam(name = "resultSourceInfo") ResultSourceInfo resultSourceInfo,
            @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteResultSource(@WebParam(name = "deleteResultSourceId") String resultSourceId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultSource(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "resultSourceInfo") ResultSourceInfo resultSourceInfo,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    public LrrDao getLrrDao() {
        return lrrDao;
    }

    public void setLrrDao(LrrDao lrrDao) {
        this.lrrDao = lrrDao;
    }

    public ResultSourceDao getResultSourceDao() {
        return resultSourceDao;
    }

    public void setResultSourceDao(ResultSourceDao resultSourceDao) {
        this.resultSourceDao = resultSourceDao;
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprAndType(String lprId, String lrrType)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return new ArrayList<LearningResultRecordInfo>();
    }

}
