package org.kuali.student.r2.lum.course.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;

import java.util.Date;
import java.util.List;

public class CourseServiceDecorator implements CourseService {

    private CourseService nextDecorator;

    public CourseService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseStatement(courseId, statementTreeViewInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateCourse(validationType, courseInfo, contextInfo);
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String statementId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        return getNextDecorator().updateCourseStatement(courseId, statementId, statementTreeViewInfo, contextInfo);
    }

    @Override
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        return getNextDecorator().updateCourse(courseId, courseInfo, contextInfo);
    }

    @Override
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return getNextDecorator().setCurrentCourseVersion(courseVersionId, currentVersionStart, contextInfo);
    }

    @Override
    public List<CourseInfo> searchForCourses(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourses(criteria, contextInfo);
    }

    @Override
    public List<String> searchForCourseIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseIds(criteria, contextInfo);
    }

    @Override
    public List<CourseInfo> getCoursesByIds(List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCoursesByIds(courseIds, contextInfo);
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseStatements(courseId, nlUsageTypeKey, language, contextInfo);
    }

    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseLearningObjectivesByCourse(courseId, contextInfo);
    }

    @Override
    public List<FormatInfo> getCourseFormatsByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseFormatsByCourse(courseId, contextInfo);
    }

    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(String formatId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseActivitiesByCourseFormat(formatId, contextInfo);
    }

    @Override
    public CourseInfo getCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourse(courseId, contextInfo);
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseStatement(courseId, statementTreeViewInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        return getNextDecorator().deleteCourse(courseId, contextInfo);
    }

    @Override
    public CourseInfo createNewCourseVersion(String courseId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        return getNextDecorator().createNewCourseVersion(courseId, versionComment, contextInfo);
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return getNextDecorator().createCourseStatement(courseId, statementTreeViewInfo, contextInfo);
    }

    @Override
    public CourseInfo createCourse(CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().createCourse(courseInfo, contextInfo);
    }

    @Deprecated
    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Deprecated
    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        try {
            return getNextDecorator().getObjectStructure(objectTypeKey);
        } catch (OperationFailedException e) {
            return null;
        }

    }

    @Override
    public List<String> getObjectTypes() {
        try {
            return getNextDecorator().getObjectTypes();
        } catch (OperationFailedException e) {
            return null;
        }
    }

}
