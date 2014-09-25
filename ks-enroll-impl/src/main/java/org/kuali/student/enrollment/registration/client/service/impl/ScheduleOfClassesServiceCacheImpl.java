package org.kuali.student.enrollment.registration.client.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingDetailsSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * There are some methods on the ScheduleOfClassesImpl that could / should be backed by a cache. In this case
 * ehcache
 */
public class ScheduleOfClassesServiceCacheImpl extends ScheduleOfClassesServiceImpl {

    //Caching
    private static final String COURSE_DETAILS_CACHE_NAME = "courseDetailsCache";
    private static final String INSTRUCTOR_INFO_CACHE_NAME = "instructorCache";
    private static final String AO_TIMESLOT_CACHE_NAME = "aoTimeslot";
    private CacheManager cacheManager;

    @Override
    public Map<String, List<TimeSlotInfo>> getAoTimeSlotMap(List<String> aoIds, ContextInfo contextInfo) {
        Map<String, List<TimeSlotInfo>> mRet = new HashMap<>();
        if(aoIds == null || aoIds.isEmpty()) return null;

        // anything not in the cache should be added to this list and be queried later
        List<String> aoIdsToQuery = new ArrayList<>();

        for(String aoId : aoIds) {
            Element cachedResult = getCacheManager().getCache(AO_TIMESLOT_CACHE_NAME).get(aoId);
            if (cachedResult == null) {
                aoIdsToQuery.add(aoId);
            } else {
                //Get cached data
                mRet.put(aoId,(List<TimeSlotInfo>) cachedResult.getValue());
            }

            if(!aoIdsToQuery.isEmpty()){
                //Use the normal service calls to get live data
                Map<String, List<TimeSlotInfo>> aoTimeslots = super.getAoTimeSlotMap(aoIdsToQuery, contextInfo);
                if(aoTimeslots != null && !aoTimeslots.isEmpty()) {
                    for (final Iterator iter = aoTimeslots.entrySet().iterator(); iter.hasNext();) {
                        Map.Entry entry = (Map.Entry)iter.next();
                        final String aoIdKey = (String)entry.getKey();
                        final List<TimeSlotInfo> value = (List<TimeSlotInfo>)entry.getValue();
                        getCacheManager().getCache(AO_TIMESLOT_CACHE_NAME).put(new Element(aoIdKey, value));
                        mRet.put(aoIdKey, value);
                    }
                }
            }
        }
        return mRet;
    }

    @Override
    public CourseOfferingDetailsSearchResult searchForCourseOfferingDetails(String courseOfferingId, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        CourseOfferingDetailsSearchResult courseOfferingSearchResults;

        Element cachedResult = getCacheManager().getCache(COURSE_DETAILS_CACHE_NAME).get(courseOfferingId);
        if (cachedResult == null || !StringUtils.equals(((CourseOfferingDetailsSearchResult) cachedResult.getValue()).getCourseOfferingCode(), courseCode)) {
            //Use the normal service calls to get live data
            courseOfferingSearchResults = super.searchForCourseOfferingDetails(courseOfferingId, courseCode, contextInfo);
            getCacheManager().getCache(COURSE_DETAILS_CACHE_NAME).put(new Element(courseOfferingId, courseOfferingSearchResults));
        } else {
            //Get cached data and update the seatcounts with live data
            courseOfferingSearchResults = (CourseOfferingDetailsSearchResult) cachedResult.getValue();
            updateSeatcounts(courseOfferingSearchResults, ContextUtils.createDefaultContextInfo());
        }
        return courseOfferingSearchResults;
    }

    @Override
    public List<InstructorSearchResult> getInstructorListByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<InstructorSearchResult> lRet = new ArrayList<>();
        if(aoIds == null || aoIds.isEmpty()) return lRet;

        // anything not in the cache should be added to this list and be queried later
        List<String> aoIdsToQuery = new ArrayList<>();

        for(String aoId : aoIds) {
            Element cachedResult = getCacheManager().getCache(INSTRUCTOR_INFO_CACHE_NAME).get(aoId);
            if (cachedResult == null) {
                aoIdsToQuery.add(aoId);
            } else {
                //Get cached data
                lRet.add((InstructorSearchResult) cachedResult.getValue());
            }

            if(!aoIdsToQuery.isEmpty()){
                //Use the normal service calls to get live data
                List<InstructorSearchResult> instructors = super.getInstructorListByAoIds(aoIdsToQuery, contextInfo);
                for(InstructorSearchResult instructor : instructors){
                    getCacheManager().getCache(INSTRUCTOR_INFO_CACHE_NAME).put(new Element(instructor.getActivityOfferingId(), instructor));
                }
                lRet.addAll(instructors);
            }
        }
        return lRet;

    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
