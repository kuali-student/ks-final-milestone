/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwright
 */
public class CourseServiceR1MockImpl implements CourseService, MockService {

    private Map<String, CourseInfo> courses = new LinkedHashMap<String, CourseInfo>();

    @Override
    public void clear() {
        this.courses.clear();
    }

    @Override
    public CourseInfo createCourse(CourseInfo courseInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        if (courseInfo.getId() == null) {
            courseInfo.setId(UUIDHelper.genStringUUID());
        }
        VersionInfo version = new VersionInfo ();
        version.setCurrentVersionStart(new Date ());
        version.setCurrentVersionEnd(null);
        version.setSequenceNumber(0l);
        version.setVersionComment("initial version");
        version.setVersionIndId(courseInfo.getId() + "ind");
        version.setVersionedFromId(courseInfo.getId());
        courseInfo.setVersion(version);
        courses.put(courseInfo.getId(), courseInfo);
        return courseInfo;
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo createNewCourseVersion(String courseId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        courses.remove(courseId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo getCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        return courses.get(courseId);
    }

    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(String formatId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityInfo> list = new ArrayList<ActivityInfo>();
        for (CourseInfo course : this.courses.values()) {
            for (FormatInfo format : course.getFormats()) {
                if (format.getId().equals(formatId)) {
                    for (ActivityInfo activity : format.getActivities()) {
                        list.add(activity);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<FormatInfo> getCourseFormatsByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<FormatInfo> list = new ArrayList<FormatInfo>();
        CourseInfo course = this.getCourse(courseId, null);
        for (FormatInfo format : course.getFormats()) {
            list.add(format);
        }
        return list;
    }

    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        if (!courses.containsKey(courseInfo.getId())) {
            throw new DoesNotExistException(courseInfo.getId());
        }
        this.courses.put(courseInfo.getId(), courseInfo);
        return courseInfo;
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String statementId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getObjectTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public List<CourseInfo> getCoursesByIds(List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForCourseIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CourseInfo> searchForCourses(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Note: the refObjectTypeURI should only be clus because that is the only object that is versioned
        List<VersionDisplayInfo> list = new ArrayList<VersionDisplayInfo> ();
        for (CourseInfo info : this.courses.values()) {
            // the refObjectid is the VERSION INDEPENDENT ID See LuDaoImpl from CM
            if (refObjectId.equals(info.getVersion().getVersionIndId())) {
                VersionInfo vi = info.getVersion();
                VersionDisplayInfo vd = new VersionDisplayInfo ();
                vd.setId(info.getId());
                vd.setRefObjectUri(CluServiceConstants.CLU_NAMESPACE_URI);
                vd.setVersionedFromId(vi.getVersionedFromId());
                vd.setCurrentVersionStart(vi.getCurrentVersionStart());
                vd.setCurrentVersionEnd(vi.getCurrentVersionEnd());
                vd.setSequenceNumber(vi.getSequenceNumber());
                vd.setVersionComment(vi.getVersionComment());
                vd.setVersionIndId(vi.getVersionIndId());
                list.add (vd);
            }
        }
        return list;
    }
}
