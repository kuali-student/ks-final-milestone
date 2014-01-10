/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.course.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.jws.WebParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.r2.common.dto.MetaInfo;

/**
 *
 * @author nwright
 */
public class CourseServiceMapImpl implements CourseService, MockService, SearchService {

    private Map<String, CourseInfo> courses = new LinkedHashMap<String, CourseInfo>();

    @Override
    public void clear() {
        this.courses.clear();
    }

    @Override
    public CourseInfo createCourse(CourseInfo courseInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {

        CourseInfo copy = new CourseInfo(courseInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        } else {
            try {
                CourseInfo orig = this.getCourse(copy.getId(), contextInfo);
                throw new OperationFailedException("the supplied Id is already in use by a course" + copy.getId());
            } catch (DoesNotExistException ex) {
                // ok
            }
        }
        copy.setMeta(this.newMeta(contextInfo));
        for (FormatInfo format : copy.getFormats()) {
            if (format.getId() == null) {
                format.setId(UUIDHelper.genStringUUID());
            }
            format.setMeta(this.newMeta(contextInfo));
            for (ActivityInfo activity : format.getActivities()) {
                if (activity.getId() == null) {
                    activity.setId(UUIDHelper.genStringUUID());
                }
                activity.setMeta(this.newMeta(contextInfo));
            }
        }

        VersionInfo version = new VersionInfo();
        version.setCurrentVersionStart(new Date());
        version.setCurrentVersionEnd(null);
        version.setSequenceNumber(0l);
        version.setVersionComment("initial version");
        version.setVersionIndId(courseInfo.getId() + "ind");
        version.setVersionedFromId(courseInfo.getId());
        copy.setVersion(version);
        courses.put(copy.getId(), copy);
        return new CourseInfo(copy);
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DataValidationErrorException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo createNewCourseVersion(String courseId, String versionComment, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException,
            DataValidationErrorException,
            AlreadyExistsException,
            CircularRelationshipException,
            DependentObjectsExistException,
            UnsupportedActionException,
            UnsupportedOperationException,
            CircularReferenceException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        courses.remove(courseId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo getCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!courses.containsKey(courseId)) {
            throw new DoesNotExistException(courseId);
        }
        return new CourseInfo(courses.get(courseId));
    }

    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(String formatId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ActivityInfo> list = new ArrayList<ActivityInfo>();
        for (CourseInfo course : this.courses.values()) {
            for (FormatInfo format : course.getFormats()) {
                if (format.getId().equals(formatId)) {
                    for (ActivityInfo activity : format.getActivities()) {
                        list.add(new ActivityInfo(activity));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<FormatInfo> getCourseFormatsByCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<FormatInfo> list = new ArrayList<FormatInfo>();
        CourseInfo course = this.getCourse(courseId, null);
        for (FormatInfo format : course.getFormats()) {
            list.add(new FormatInfo(format));
        }
        return list;
    }

    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(String courseId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo setCurrentCourseVersion(String courseVersionId, Date currentVersionStart, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            IllegalVersionSequencingException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            VersionMismatchException,
            OperationFailedException,
            PermissionDeniedException,
            UnsupportedActionException,
            DependentObjectsExistException,
            AlreadyExistsException,
            CircularRelationshipException,
            CircularReferenceException,
            ReadOnlyException {
        CourseInfo orig = this.getCourse(courseId, contextInfo);
        if (!courseInfo.getMeta().getVersionInd().equals(orig.getMeta().getVersionInd())) {
            throw new VersionMismatchException(orig.getMeta().getVersionInd());
        }
        CourseInfo copy = new CourseInfo(courseInfo);
        copy.setMeta(this.updateMeta(copy.getMeta(), contextInfo));
        // should I drill down and update the meta on each format and activity?
        this.courses.put(copy.getId(), copy);
        return new CourseInfo(courseInfo);
    }

    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String statementId,
            StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DataValidationErrorException,
            VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
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
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CourseInfo> getCoursesByIds(List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForCourseIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CourseInfo> searchForCourses(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // Note: the refObjectTypeURI should only be clus because that is the only object that is versioned
        List<VersionDisplayInfo> list = new ArrayList<VersionDisplayInfo>();
        for (CourseInfo info : this.courses.values()) {
            // the refObjectid is the VERSION INDEPENDENT ID See LuDaoImpl from CM
            if (refObjectId.equals(info.getVersion().getVersionIndId())) {
                VersionInfo vi = info.getVersion();
                VersionDisplayInfo vd = new VersionDisplayInfo();
                vd.setId(info.getId());
                vd.setRefObjectUri(CluServiceConstants.CLU_NAMESPACE_URI);
                vd.setVersionedFromId(vi.getVersionedFromId());
                vd.setCurrentVersionStart(vi.getCurrentVersionStart());
                vd.setCurrentVersionEnd(vi.getCurrentVersionEnd());
                vd.setSequenceNumber(vi.getSequenceNumber());
                vd.setVersionComment(vi.getVersionComment());
                vd.setVersionIndId(vi.getVersionIndId());
                list.add(vd);
            }
        }
        return list;
    }
    static TypeInfo typeInfo;

    static {
        typeInfo = new TypeInfo();
        typeInfo.setKey("lu.search.relatedTypes");
    }

    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(typeInfo);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        if (typeInfo.getKey().equals(searchTypeKey)) {
            return typeInfo;
        }
        throw new DoesNotExistException("Search Not Defined");
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException {

        if ("lu.search.relatedTypes".equals(searchRequestInfo.getSearchKey())) {
            String formatId = null;
            for (SearchParamInfo searchParam : searchRequestInfo.getParams()) {
                if ("lu.queryParam.cluId".equals(searchParam.getKey())) {
                    formatId = KSCollectionUtils.getRequiredZeroElement(searchParam.getValues());
                    break;
                }
            }
            if (formatId != null) {
                for (CourseInfo course : this.courses.values()) {
                    for (FormatInfo format : course.getFormats()) {
                        if (format.getId().equals(formatId)) {
                            SearchResultInfo searchResultInfo = new SearchResultInfo();
                            for (ActivityInfo activity : format.getActivities()) {
                                SearchResultRowInfo row = new SearchResultRowInfo();
                                row.addCell("lu.resultColumn.cluType", activity.getTypeKey());
                                searchResultInfo.getRows().add(row);
                            }
                            return searchResultInfo;
                        }
                    }
                }
            }
        }
        return null;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo successStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private StatusInfo failStatus() {
        StatusInfo status = new StatusInfo();
        status.setMessage("Operation Failed");
        status.setSuccess(Boolean.FALSE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
