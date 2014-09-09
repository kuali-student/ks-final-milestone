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
package org.kuali.student.r2.lum.course.service.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.kuali.student.common.cache.KSCacheUtils;
import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseServiceDecorator;

/**
 * This class is for CourseService to add caching to Course service methods
 *
 * @author Kuali Student Team
 */
public class CourseServiceCacheDecorator extends CourseServiceDecorator {
    /* Caching the CourseInfo objects by id */
    private static String courseCacheName = "courseCache";
    private CacheManager cacheManager;
	private CourseInfoCopier courseInfoCopier = new CourseInfoCopier();

	
    @Override
	public CourseInfo createCourse(CourseInfo courseInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
    	
		CourseInfo result =  getNextDecorator().createCourse(courseInfo, contextInfo);
		
		KSCacheUtils.updateCacheElement(cacheManager.getCache(courseCacheName), courseInfo.getId(), result, courseInfoCopier);
        
        return courseInfoCopier.deepCopy(result);
		
	}

	@Override
    public CourseInfo updateCourse(String courseId, CourseInfo courseInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        CourseInfo result = getNextDecorator().updateCourse(courseId, courseInfo, contextInfo);
        
        KSCacheUtils.updateCacheElement(cacheManager.getCache(courseCacheName), courseId, result, courseInfoCopier);
        
        return courseInfoCopier.deepCopy(result);
    }

    @Override
    public CourseInfo getCourse(String courseId, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(courseCacheName);

        return KSCacheUtils.cacheAwareLoad(cache, courseId, courseInfoCopier, new KSCacheUtils.SingleCacheElementLoader<CourseInfo>() {

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
	public List<CourseInfo> getCoursesByIds(List<String> courseIds,
			final ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
    	
    	 Cache cache = getCacheManager().getCache(courseCacheName);

         return KSCacheUtils.cacheAwareBulkLoad(cache, courseIds, courseInfoCopier, new KSCacheUtils.BulkCacheElementLoader<CourseInfo>() {

             @Override
			public List<CourseInfo> load(List<String> cacheMissKeys)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				
                 return getNextDecorator().getCoursesByIds(cacheMissKeys, contextInfo);
             }

         });
	}

	@Override
    public StatusInfo deleteCourse(String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        StatusInfo result = getNextDecorator().deleteCourse(courseId, contextInfo);
        getCacheManager().getCache(courseCacheName).remove(courseId);
        return result;
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
