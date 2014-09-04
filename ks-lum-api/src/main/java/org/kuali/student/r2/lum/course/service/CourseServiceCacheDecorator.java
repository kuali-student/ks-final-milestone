/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by David Yin on 9/2/14
 */
package org.kuali.student.r2.lum.course.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.cache.KSCacheUtils;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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

import java.util.Date;
import java.util.List;

/**
 * This class is for CourseService to add caching to Course service methods
 *
 * @author Kuali Student Team
 */
public class CourseServiceCacheDecorator extends CourseServiceDecorator {
    /* Caching the CourseInfo objects by id */
    private static String courseCacheName = "courseCache";
    private CacheManager cacheManager;


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
        CourseInfo result = getNextDecorator().updateCourse(courseId, courseInfo, contextInfo);
        getCacheManager().getCache(courseCacheName).remove(courseId);
        return result;
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
    public CourseInfo getCourse(String courseId, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(courseCacheName);

        return KSCacheUtils.cacheAwareLoad(cache, courseId, new KSCacheUtils.SingleCacheElementLoader<CourseInfo>() {

            @Override
            public CourseInfo load(String id)
                    throws DoesNotExistException, OperationFailedException,
                    InvalidParameterException, MissingParameterException,
                    PermissionDeniedException {
                return getNextDecorator().getCourse(id, contextInfo);
            }

        });
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseStatement(courseId, statementTreeViewInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        StatusInfo result = getNextDecorator().deleteCourse(courseId, contextInfo);
        getCacheManager().getCache(courseCacheName).remove(courseId);
        return result;
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
        return getNextDecorator().getVersions(refObjectTypeURI, refObjectId, contextInfo);
    }

    @Deprecated
    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCurrentVersion(refObjectTypeURI, refObjectId, contextInfo);
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

    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
