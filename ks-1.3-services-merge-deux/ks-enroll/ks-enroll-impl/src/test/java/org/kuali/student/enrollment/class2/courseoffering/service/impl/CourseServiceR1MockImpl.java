/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularReferenceException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.common.versionmanagement.dto.VersionInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.service.LuServiceConstants;

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
    public CourseInfo createCourse(CourseInfo courseInfo) 
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException {
        if (courseInfo.getId() == null) {
            courseInfo.setId(courses.size() + "");
        }
        VersionInfo version = new VersionInfo ();
        version.setCurrentVersionStart(new Date ());
        version.setCurrentVersionEnd(null);
        version.setSequenceNumber(0l);
        version.setVersionComment("initial version");
        version.setVersionIndId(courseInfo.getId() + "ind");
        version.setVersionedFromId(courseInfo.getId());
        courseInfo.setVersionInfo(version);
        courses.put(courseInfo.getId(), courseInfo);
        return courseInfo;
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo createNewCourseVersion(String courseId, String versionComment) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteCourse(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        courses.remove(courseId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo getCourse(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        return courses.get(courseId);
    }

    @Override
    public List<ActivityInfo> getCourseActivities(String formatId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<FormatInfo> getCourseFormats(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<FormatInfo> list = new ArrayList<FormatInfo>();
        CourseInfo course = this.getCourse(courseId);
        for (FormatInfo format : course.getFormats()) {
            list.add(format);
        }
        return list;
    }

    @Override
    public List<LoDisplayInfo> getCourseLos(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo updateCourse(CourseInfo courseInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException {
        if (!courses.containsKey(courseInfo.getId())) {
            throw new DoesNotExistException(courseInfo.getId());
        }
        this.courses.put(courseInfo.getId(), courseInfo);
        return courseInfo;
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
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
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI, String refObjectId, Date date)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectTypeURI, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectTypeURI, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectTypeURI, String refObjectId, Long sequence) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
      
        // Note: the refObjectTypeURI should only be clus because that is the only object that is versioned
        List<VersionDisplayInfo> list = new ArrayList<VersionDisplayInfo> ();
        for (CourseInfo info : this.courses.values()) {
            // the refObjectid is the VERSION INDEPENDENT ID See LuDaoImpl from CM
            if (refObjectId.equals(info.getVersionInfo().getVersionIndId())) {
                VersionInfo vi = info.getVersionInfo();
                VersionDisplayInfo vd = new VersionDisplayInfo ();
                vd.setId(info.getId());
                vd.setObjectTypeURI(LuServiceConstants.CLU_NAMESPACE_URI);
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

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectTypeURI, String refObjectId, Date from, Date to) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
