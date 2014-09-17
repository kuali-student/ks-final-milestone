/*
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
 */

package org.kuali.student.ap.framework.context.decorators;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CourseHelperCacheDecorator extends CourseHelperDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(TermHelperCacheDecorator.class);

    private static final String COURSE_HELPER_CACHE = "ksapCourseHelperCache";
    private static final String COURSE_HELPER_COURSE_PREFIX = "ksapcourse";

    // Cache manager used in storing search results for sqls
    private CacheManager cacheManager;

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Course getCurrentVersionOfCourse(String courseId) {
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "course", courseId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), courseId);
            result = getNextDecorator().getCurrentVersionOfCourse(courseId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), courseId);
            result = cachedResult.getValue();
        }

        return (Course) result;
    }

    @Override
    public List<String> getScheduledTermsForCourse(Course course) {
        String courseId = course.getId();
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "scheduledterms", courseId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), courseId);
            result = getNextDecorator().getScheduledTermsForCourse(course);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), courseId);
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    /*
     *  - Only display Last Offered if it has been offered within the last 10 years.
     *  - If the course has not been offered within the last 10 years, return null
     */
    @Override
    public Term getLastOfferedTermForCourse(Course course) {
        String courseId = course.getId();
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "lastofferedterm", courseId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), courseId);
            result = getNextDecorator().getLastOfferedTermForCourse(course);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), courseId);
            result = cachedResult.getValue();
        }

        return (Term) result;
    }

    @Override
    public Course getCurrentVersionOfCourseByVersionIndependentId(String versionIndependentId) {
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "currentversionofcoursebyversionIndId", versionIndependentId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), versionIndependentId);
            result = getNextDecorator().getCurrentVersionOfCourseByVersionIndependentId(versionIndependentId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), versionIndependentId);
            result = cachedResult.getValue();
        }

        return (Course) result;
    }

    @Override
    public List<String> getAllCourseIdsByVersionIndependentId(String versionIndependentId) {
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "courseidsbyversionindid", versionIndependentId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), versionIndependentId);
            result = getNextDecorator().getAllCourseIdsByVersionIndependentId(versionIndependentId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), versionIndependentId);
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public String getCourseCdFromActivityId(String activityId) {
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "coursecdfromactivityid", activityId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), activityId);
            result = getNextDecorator().getCourseCdFromActivityId(activityId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), activityId);
            result = cachedResult.getValue();
        }

        return (String) result;
    }

    @Override
    public List<String> getProjectedTermsForCourse(Course course){
        String courseId = course.getId();
        MultiKey cacheKey = new MultiKey(COURSE_HELPER_COURSE_PREFIX + "projectedtermsforcourse", courseId);
        Cache cache = getCacheManager().getCache(COURSE_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), courseId);
            result = getNextDecorator().getProjectedTermsForCourse(course);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), courseId);
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }
}
