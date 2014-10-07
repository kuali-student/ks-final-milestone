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
 * Created by pauldanielrichardson on 10/7/14
 */
package org.kuali.student.enrollment.academicrecord.service.decorators;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;

/**
 * This class is a caching decorator for AcademicRecordService
 *
 * @author Kuali Student Team
 */
public class AcademicRecordServiceCachingDecorator extends AcademicRecordServiceDecorator {

    private AcademicRecordService nextDecorator;

    private CacheManager cacheManager;
    private Cache cache;

    private static final String ACADEMIC_RECORD_CACHE = "academicRecordCache";

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId,
                                                                          ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(personId, courseId);

        @SuppressWarnings("unchecked")
        List<StudentCourseRecordInfo> studentCourseRecords = (List<StudentCourseRecordInfo>) getCacheResult(cacheKey);

        if (studentCourseRecords == null) {
            studentCourseRecords = nextDecorator.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);
            putCacheResult(cacheKey, studentCourseRecords);
        }

        return studentCourseRecords;
    }

    private Object getCacheResult(MultiKey cacheKey) {
        Object cacheResult;
        Element element = getCache().get(cacheKey);
        if (element == null) {
            cacheResult = null;
        } else {
            cacheResult = element.getValue();
        }
        return cacheResult;
    }

    private void putCacheResult(MultiKey key, Object value) {
        getCache().put(new Element(key, value));
    }

    private Cache getCache() {
        if (cache == null) {
            cache = cacheManager.getCache(ACADEMIC_RECORD_CACHE);
        }
        return cache;
    }

    public void setNextDecorator(AcademicRecordService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
