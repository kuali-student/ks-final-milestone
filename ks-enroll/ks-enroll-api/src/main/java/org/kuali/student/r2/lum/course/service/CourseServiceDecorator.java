package org.kuali.student.r2.lum.course.service;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

public class CourseServiceDecorator implements CourseService {
    
    private CourseService nextDecorator;

    public CourseService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }
    
    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.nextDecorator.getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseInfo getCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseInfo> getCoursesByIds(List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForCourseIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<CourseInfo> searchForCourses(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseInfo createCourse(CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<FormatInfo> getCourseFormatsByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(String formatId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String statementId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public CourseInfo createNewCourseVersion(String courseId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
