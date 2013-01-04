package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitlistService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class CourseWaitlistServiceImpl implements CourseWaitlistService {

    private LprService lprService;
    private CourseOfferingService courseOfferingService;
    private StatementService statementService;

    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public StatementService getStatementService() {
        return statementService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    @Override
    public Integer getAvailableSeatsForCourseOffering(String courseOfferingId, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public Integer getAvailableSeatsForRegGroup(String regGroupId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public Integer getAvailableSeatsForStudentInRegGroup(String studentId, String regGroupId, ContextInfo context) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");

    }

    @Override
    public Integer getAvailableSeatsInSeatpool(String seatpoolId, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo updateCourseWaitlistEntry(String courseWaitlistEntryId, CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo reorderCourseWaitlistEntries(List<String> courseWaitlistEntryIds, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo insertCourseWaitlistEntryAtPosition(String courseWaitlistEntryId, Integer position, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo removeCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo validateCourseWaitlistEntry(String validateTypeKey, CourseWaitlistEntryInfo courseWaitlistEntryInfo, ContextInfo context) throws
            DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public RegistrationResponseInfo registerStudentFromWaitlist(String courseWaitlistEntryId, ContextInfo context) throws
            AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(String courseOfferingId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(String regGroupId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(String courseOfferingId, String studentId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(String regGroupId, String studentId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(String studentId, String termKey, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(QueryByCriteria criteria, ContextInfo context) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public List<String> searchForCourseWaitlistEntryIds(QueryByCriteria criteria, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public StatusInfo deleteCourseWaitlistEntry(String courseWaitlistEntryId, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }
}
