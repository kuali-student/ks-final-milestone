package org.kuali.student.enrollment.lrr.service;

import java.util.List;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
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


public abstract class LearningResultRecordServiceDecorator implements LearningResultRecordService
{
	private LearningResultRecordService nextDecorator;
	
	public LearningResultRecordService getNextDecorator()
			throws OperationFailedException {
		if (null == nextDecorator) {
			throw new OperationFailedException("Misconfigured application: nextDecorator is null");
		}
		return nextDecorator;
	}
	public void setNextDecorator(LearningResultRecordService nextDecorator) {
		this.nextDecorator = nextDecorator;
	}
	
	
	@Override
	public LearningResultRecordInfo getLearningResultRecord(
			String learningResultRecordId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getLearningResultRecord(learningResultRecordId, context);
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(
			String lprId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().getLearningResultRecordsForLpr(lprId);
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsForLprIds(
			List<String> lprIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getLearningResultRecordsForLprIds(lprIds, context);
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(
			List<String> lprIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().getLearningResultRecordsBySourceId(lprIds, context);
	}

	@Override
	public LearningResultRecordInfo createLearningResultRecord(
			LearningResultRecordInfo learningResultRecord, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().createLearningResultRecord(learningResultRecord, context);
	}

	@Override
	public LearningResultRecordInfo updateLearningResultRecord(
			String learningResultRecordId,
			LearningResultRecordInfo learningResultRecordInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		return getNextDecorator().updateLearningResultRecord(learningResultRecordId, learningResultRecordInfo, context);
	}

	@Override
	public StatusInfo deleteLearningResultRecord(String learningResultRecordId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().deleteLearningResultRecord(learningResultRecordId, context);
	}

	@Override
	public List<ValidationResultInfo> validateLearningResultRecord(
			String validationType,
			LearningResultRecordInfo learningResultRecordInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().validateLearningResultRecord(validationType, learningResultRecordInfo, context);
	}

	@Override
	public ResultSourceInfo getResultSource(String resultSourceId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().getResultSource(resultSourceId, context);
	}

	@Override
	public List<ResultSourceInfo> getResultSourcesByIds(
			List<String> resultSourceIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getResultSourcesByIds(resultSourceIds, context);
	}

	@Override
	public List<ResultSourceInfo> getResultSourcesByType(
			String resultSourceTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getResultSourcesByType(resultSourceTypeKey, context);
	}

	@Override
	public ResultSourceInfo createResultSource(ResultSourceInfo sourceInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().createResultSource(sourceInfo, context);
	}

	@Override
	public ResultSourceInfo updateResultSource(String resultSourceId,
			ResultSourceInfo resultSourceInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return getNextDecorator().updateResultSource(resultSourceId, resultSourceInfo, context);
	}

	@Override
	public StatusInfo deleteResultSource(String resultSourceId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return getNextDecorator().deleteResultSource(resultSourceId, context);
	}

	@Override
	public List<ValidationResultInfo> validateResultSource(
			String validationType, ResultSourceInfo resultSourceInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return getNextDecorator().validateResultSource(validationType, resultSourceInfo, context);
	}
    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprAndType(
            String lprId, String lrrType) throws DoesNotExistException, 
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().getLearningResultRecordsForLprAndType(lprId, lrrType) ;
    }

}
