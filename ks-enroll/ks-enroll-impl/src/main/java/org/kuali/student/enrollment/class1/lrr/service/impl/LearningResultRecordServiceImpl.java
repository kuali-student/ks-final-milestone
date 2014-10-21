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
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.enrollment.lrr.service.LearningResultRecordService", serviceName = "LearningResultRecordService", portName = "LearningResultRecordService", targetNamespace = "http://student.kuali.org/wsdl/lrr")
@Transactional(readOnly = true, noRollbackFor = {org.kuali.student.r2.common.exceptions.DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LearningResultRecordServiceImpl implements LearningResultRecordService {

    private LrrDao lrrDao;
    private ResultSourceDao resultSourceDao;

    @Override
    public LearningResultRecordInfo getLearningResultRecord(
            String learningResultRecordId,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(String lprId)
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
            List<String> lprIds,  ContextInfo context)
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
            List<String> sourceIds,  ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented.");
    }

    @Override
    @Transactional(readOnly = false)
    public LearningResultRecordInfo createLearningResultRecord(
            LearningResultRecordInfo learningResultRecord,
             ContextInfo context) throws AlreadyExistsException,
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

            try {
                newLrr = lrrDao.merge(newLrr);
            } catch (VersionMismatchException e) {
                throw new OperationFailedException("version mismatch exception", e);
            }
            
            lrrDao.getEm().flush();
            
            return newLrr.toDto();
        }

    }

    @Override
    @Transactional(readOnly = false)
    public LearningResultRecordInfo updateLearningResultRecord(
            String learningResultRecordId,
            LearningResultRecordInfo learningResultRecordInfo,
             ContextInfo context) throws DataValidationErrorException,
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

        modifiedLrr = lrrDao.merge(modifiedLrr);
        
        lrrDao.getEm().flush();

        return modifiedLrr.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLearningResultRecord(
            String learningResultRecordId,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LearningResultRecordEntity lrr = lrrDao.find(learningResultRecordId);

        if (lrr == null) {
            throw new DoesNotExistException(learningResultRecordId);
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        lrr.setLrrState(LrrServiceConstants.RESULT_RECORD_DELETED_STATE_KEY);
       
        try {
            lrr = lrrDao.merge(lrr);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("version mismatch exception", e);
        }
        
        lrrDao.getEm().flush();

        return status;

    }

    @Override
    public List<ValidationResultInfo> validateLearningResultRecord(
            String validationType,
            LearningResultRecordInfo learningResultRecordInfo,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    public ResultSourceInfo getResultSource(String resultSourceId,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByIds(
            List<String> resultSourceIds,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    public List<ResultSourceInfo> getResultSourcesByType(
            String resultSourceTypeKey,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    @Transactional(readOnly = false)
    public ResultSourceInfo createResultSource(ResultSourceInfo sourceInfo,
             ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    @Transactional(readOnly = false)
    public ResultSourceInfo updateResultSource(String resultSourceId,
            ResultSourceInfo resultSourceInfo,
             ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteResultSource(String resultSourceId,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); 
    }

    @Override
    public List<ValidationResultInfo> validateResultSource(String validationType,
            ResultSourceInfo resultSourceInfo,
             ContextInfo context) throws DoesNotExistException, InvalidParameterException,
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

	@Override
	public StatusInfo changeLearningResultRecordState(
			String learningResultRecordId, String nextStateKey,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO KSENROLL-8713
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public StatusInfo changeResultSourceState(String resultSourceId,
			String nextStateKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO KSENROLL-8713
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsByIds(
			List<String> learningResultRecordIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}
    
    

}
