package org.kuali.student.enrollment.class2.exam.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.List;


public class ExamServiceDecorator implements ExamService {
    private ExamService nextDecorator;

    public ExamService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(ExamService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public List<ValidationResultInfo> validateExam(String validationTypeKey, String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateExam(validationTypeKey, examTypeKey, examInfo, contextInfo);
    }

    @Override
    public ExamInfo createExam(String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createExam(examTypeKey, examInfo, contextInfo);
    }

    @Override
    public ExamInfo getExam(String examId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExam(examId, contextInfo);
    }

    @Override
    public ExamInfo updateExam(String examId, ExamInfo examInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateExam(examId, examInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteExam(String examId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteExam(examId, contextInfo);
    }

    @Override
    public List<ExamInfo> getExamsByIds(List<String> examIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamsByIds(examIds, contextInfo);
    }

    @Override
    public List<String> getExamIdsByType(String examTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getExamIdsByType(examTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForExamIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExamIds(criteria, contextInfo);
    }

    @Override
    public List<ExamInfo> searchForExams(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForExams(criteria, contextInfo);
    }
}
