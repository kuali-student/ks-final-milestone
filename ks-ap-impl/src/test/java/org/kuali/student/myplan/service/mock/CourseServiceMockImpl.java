package org.kuali.student.myplan.service.mock;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jws.WebParam;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

/**
 * Mock CourseService to be use with AcademicPlanServiceImpl.
 */
public class CourseServiceMockImpl implements CourseService {

    private Set<String> validCourseIds;

    /**
     *  Allow the test context to set a list valid/existing course Ids.
     *
     * @param validCourseIds
     */
    public void setValidCourses(Set<String> validCourseIds) {
        this.validCourseIds = validCourseIds;
    }

    @Override
    public List<String> getObjectTypes() {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
	public CourseInfo getCourse(String courseId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if ( ! validCourseIds.contains(courseId)) {
            throw new DoesNotExistException();
        }
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(courseId);
        RichTextInfo richTextInfo=new RichTextInfo();
        courseInfo.setDescr(richTextInfo);
        return courseInfo;
	}

	@Override
	public List<CourseInfo> getCoursesByIds(List<String> courseIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<String> searchForCourseIds(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<CourseInfo> searchForCourses(QueryByCriteria criteria,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public CourseInfo createCourse(CourseInfo courseInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public CourseInfo updateCourse(String courseId, CourseInfo courseInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, VersionMismatchException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException, DependentObjectsExistException,
			AlreadyExistsException, CircularRelationshipException,
			CircularReferenceException, ReadOnlyException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException,
			DataValidationErrorException, AlreadyExistsException,
			UnsupportedActionException, DependentObjectsExistException,
			CircularRelationshipException, CircularReferenceException,
			ReadOnlyException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<ValidationResultInfo> validateCourse(String validationType,
			CourseInfo courseInfo, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<FormatInfo> getCourseFormatsByCourse(String courseId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<ActivityInfo> getCourseActivitiesByCourseFormat(
			String formatId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(
			String courseId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo> getCourseStatements(
			String courseId, String nlUsageTypeKey, String language,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        List<org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo> statementTreeViewInfos=new ArrayList<org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo>();
        return statementTreeViewInfos;
	}

	@SuppressWarnings("deprecation")
	@Override
	public org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo createCourseStatement(
			String courseId,
			org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo statementTreeViewInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException {
        throw new RuntimeException("Not implemented.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo updateCourseStatement(
			String courseId,
			String statementId,
			org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo statementTreeViewInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, VersionMismatchException {
        throw new RuntimeException("Not implemented.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public StatusInfo deleteCourseStatement(
			String courseId,
			org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo statementTreeViewInfo,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ValidationResultInfo> validateCourseStatement(
			String courseId,
			org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo statementTreeViewInfo,
			ContextInfo contextInfo) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public CourseInfo createNewCourseVersion(String courseId,
			String versionComment, ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException, ReadOnlyException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public StatusInfo setCurrentCourseVersion(String courseVersionId,
			Date currentVersionStart, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, IllegalVersionSequencingException,
			DataValidationErrorException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
			String refObjectId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}

	@Override
	public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
			String refObjectId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        throw new RuntimeException("Not implemented.");
	}
}
