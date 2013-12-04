package org.kuali.student.enrollment.class2.examoffering.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.List;


public class ExamOfferingServiceDecorator implements ExamOfferingService {
    private ExamOfferingService nextDecorator;

    public ExamOfferingService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(ExamOfferingService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public ExamOfferingInfo getExamOffering( String examOfferingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOffering( examOfferingId,   contextInfo);
    }

    @Override
    public List<ExamOfferingInfo> getExamOfferingsByIds( List<String> examOfferingIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingsByIds(  examOfferingIds,   contextInfo);
    }

    @Override
    public List<String> getExamOfferingIdsByType( String examTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return getNextDecorator().getExamOfferingIdsByType(  examTypeKey,   contextInfo);
    }

    @Override
    public List<String> searchForExamOfferingIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExamOfferingIds(  criteria,   contextInfo);
    }

    @Override
    public List<ExamOfferingInfo> searchForExamOfferings( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExamOfferings(  criteria,   contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateExamOffering( String termId,  String examId,  String examTypeKey,  String validationTypeKey,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateExamOffering(  termId,   examId,   examTypeKey,   validationTypeKey,   examOfferingInfo,   contextInfo);
    }

    @Override
    public ExamOfferingInfo createExamOffering( String termId,  String examId,  String examTypeKey,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createExamOffering(  termId,   examId,   examTypeKey,   examOfferingInfo,   contextInfo);
    }

    @Override
    public ExamOfferingInfo updateExamOffering( String examOfferingId,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateExamOffering(  examOfferingId,   examOfferingInfo,   contextInfo);
    }

    @Override
    public StatusInfo deleteExamOffering( String examOfferingId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteExamOffering(  examOfferingId,   contextInfo);
    }

    @Override
    public StatusInfo changeExamOfferingState( String examOfferingId,  String stateKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeExamOfferingState(  examOfferingId,   stateKey,   contextInfo);
    }

    @Override
    public List<ExamOfferingInfo> getExamOfferingsByExamPeriod( String examPeriodId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingsByExamPeriod(  examPeriodId,   contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateExamOfferingRelation( String formatOfferingId,  String examOfferingId,  String examOfferingTypeKey,  String validationTypeKey,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateExamOfferingRelation(  formatOfferingId,   examOfferingId,   examOfferingTypeKey,   validationTypeKey,   examOfferingRelationInfo,   contextInfo);
    }

    @Override
    public ExamOfferingRelationInfo createExamOfferingRelation( String formatOfferingId,  String examOfferingId,  String examOfferingTypeKey,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createExamOfferingRelation(  formatOfferingId,   examOfferingId,   examOfferingTypeKey,   examOfferingRelationInfo,   contextInfo);
    }

    @Override
    public ExamOfferingRelationInfo updateExamOfferingRelation( String examOfferingRelationId,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateExamOfferingRelation(  examOfferingRelationId,   examOfferingRelationInfo,   contextInfo);
    }

    @Override
    public StatusInfo deleteExamOfferingRelation( String examOfferingRelationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteExamOfferingRelation(  examOfferingRelationId,   contextInfo);
    }

    @Override
    public ExamOfferingRelationInfo getExamOfferingRelation( String examOfferingRelationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelation(  examOfferingRelationId,   contextInfo);
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByIds( List<String> examOfferingRelationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelationsByIds(  examOfferingRelationIds,   contextInfo) ;
    }

    @Override
    public List<String> getExamOfferingRelationIdsByType( String relationshipTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelationIdsByType(  relationshipTypeKey,   contextInfo);
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByFormatOffering( String formatOfferingId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelationsByFormatOffering(  formatOfferingId,   contextInfo);
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByExamOffering( String examOfferingId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelationsByExamOffering(  examOfferingId,   contextInfo);
    }

    @Override
    public List<String> getExamOfferingRelationIdsByActivityOffering( String activityOfferingId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamOfferingRelationIdsByActivityOffering(  activityOfferingId,   contextInfo);
    }

    @Override
    public List<String> searchForExamOfferingRelationIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExamOfferingRelationIds(  criteria,   contextInfo);
    }

    @Override
    public List<ExamOfferingRelationInfo> searchForExamOfferingRelations( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExamOfferingRelations(  criteria,   contextInfo);
    }
}
