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
 * Created by vgadiyak on 1/20/14
 */
package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleActivityOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.TimeOfDayFormattingEnum;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.infc.TypeTypeRelation;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.SearchServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is util class with the common methods to be used in CourseRegistrationClientService and ScheduleOfClassesService
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationAndScheduleOfClassesUtil {

    private static SearchService searchService;
    private static LprService lprService;
    private static IdentityService identityService;
    private static AtpService atpService;
    private static CourseService courseService;
    private static CourseOfferingService courseOfferingService;
    private static TypeService typeService;
    private static CourseOfferingSetService courseOfferingSetService;

    private static Map<String, Integer> activityPriorityMap = null;

    public static String getTermId(String termId, String termCode) throws Exception {
        if( !StringUtils.isEmpty(termId) ) {
            return termId;
        }

        return KSCollectionUtils.getRequiredZeroElement(getTermsByTermCode(termCode)).getTermId();
    }

    public static List<TermSearchResult> getTermsByTermCode(String termCode) throws Exception {
        List<AtpInfo> atpInfos = getAtpService().getAtpsByCode( termCode, ContextUtils.createDefaultContextInfo() );
        return getTermSearchResultsFromAtpInfos( atpInfos );
    }

    public static List<TermSearchResult> getTermSearchResultsFromAtpInfos(List<AtpInfo> atpInfos) {
        List<TermSearchResult> result = new ArrayList<TermSearchResult>();

        for( AtpInfo atpInfo : atpInfos ) {
            TermSearchResult ts = new TermSearchResult();
            ts.setTermId(atpInfo.getId());
            ts.setTermName(atpInfo.getName());
            ts.setTermCode(atpInfo.getCode());
            result.add(ts);
        }

        return result;
    }

    /**
     * This method takes in a list of activity offering type keys and sorts them in priority order. The priority
     * order comes from the priority established in the Activity Types.
     * @param typeKeys  list of activity offering type keys
     * @param contextInfo
     */
    public static void sortActivityOfferingTypeKeyList(List<String> typeKeys, final ContextInfo contextInfo) {
        Collections.sort(typeKeys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int val1 = 0;
                int val2 = 0;
                try {
                    val1 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o1).intValue();
                    val2 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o2).intValue();
                } catch (Exception ex) {
                    // I'm not sure if this is the correct thing to do here.
                    throw new RuntimeException("Failed to sort activity offering types", ex);
                }

                return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
            }
        });
    }

    /**
     * This method takes in a list of activity offering results and sorts them in priority order. The priority
     * order comes from the priority established in the Activity Types.
     * @param activityOfferingResults  list of StudentScheduleActivityOfferingResult
     * @param contextInfo
     */
    public static void sortActivityOfferingReslutList(List<StudentScheduleActivityOfferingResult> activityOfferingResults, final ContextInfo contextInfo) {
        Collections.sort(activityOfferingResults, new Comparator<StudentScheduleActivityOfferingResult>() {
            @Override
            public int compare(StudentScheduleActivityOfferingResult o1, StudentScheduleActivityOfferingResult o2) {
                int val1 = 0;
                int val2 = 0;
                try {
                    val1 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o1.getActiviyOfferingType()).intValue();
                    val2 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o2.getActiviyOfferingType()).intValue();
                } catch (Exception ex) {
                    // I'm not sure if this is the correct thing to do here.
                    throw new RuntimeException("Failed to sort activity offering types", ex);
                }

                return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
            }
        });
    }

    /* This method transforms start/end time from MS to user-friendly presentation, and set days of the week for
       schedule component for Activity Offering
     */
    public static ActivityOfferingScheduleComponentResult getActivityOfferingScheduleComponent(String roomCode, String buildingCode,
                                                                                         String weekdays, String startTimeMs, String endTimeMs) throws InvalidParameterException {
        ActivityOfferingScheduleComponentResult scheduleComponent = new ActivityOfferingScheduleComponentResult();
        scheduleComponent.setRoomCode(roomCode);
        scheduleComponent.setBuildingCode(buildingCode);

        List<TimeOfDayFormattingEnum> options = new ArrayList<TimeOfDayFormattingEnum>();
        options.add(TimeOfDayFormattingEnum.USE_MILITARY_TIME);
        if (!startTimeMs.isEmpty()) {
            TimeOfDayInfo startTime = TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs));
            scheduleComponent.setStartTime(TimeOfDayHelper.formatTimeOfDay(startTime, options));
        }
        if (!endTimeMs.isEmpty()) {
            TimeOfDayInfo endTime = TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs));
            scheduleComponent.setEndTime(TimeOfDayHelper.formatTimeOfDay(endTime, options));
        }

        if (!weekdays.isEmpty()) {
            if (StringUtils.contains(weekdays, "M")) {
                scheduleComponent.setMon(true);
            }
            if (StringUtils.contains(weekdays, "T")) {
                scheduleComponent.setTue(true);
            }
            if (StringUtils.contains(weekdays, "W")) {
                scheduleComponent.setWed(true);
            }
            if (StringUtils.contains(weekdays, "H")) {
                scheduleComponent.setThu(true);
            }
            if (StringUtils.contains(weekdays, "F")) {
                scheduleComponent.setFri(true);
            }
        }

        return scheduleComponent;
    }

    /**
     * This method grabs all kuali.lu.type.grouping.activity types. These types have attributes with priority values.
     * Since we are interested in the priority of the lui types, not the lu types we then need to get the type type
     * relation between lu and lui.
     *
     * Ex:
     * Lu.Lucture priority = 1; Lu.Lecture has a 1:1 relation with Lui.Lecture so we say
     * the Lui.Lecture.priority = Lu.Lecture.priority
     *
     * These values never change so I'm storing them in a map inside the service for performance reasons.
     *
     * So, if a course offering has Lec, Lab, Discussion and you want to know which is the Primary type, just get the
     * lowest priority of the ao type.
     *
     * @param contextInfo
     * @return  Map<ActivityOfferingTypeKey, priorityInt>
     * @throws Exception
     */
    public static Map<String, Integer> getActivityPriorityMap(ContextInfo contextInfo) throws Exception{
        if(activityPriorityMap == null){
            activityPriorityMap = new HashMap<String, Integer>();
            List<TypeInfo> activityTypes = getTypeService().getTypesForGroupType("kuali.lu.type.grouping.activity", contextInfo);

            for(TypeInfo typeInfo : activityTypes){
                List<AttributeInfo> attributes = typeInfo.getAttributes();
                if(attributes != null && !attributes.isEmpty()){
                    for(AttributeInfo attribute : attributes){
                        if(TypeServiceConstants.ACTIVITY_SELECTION_PRIORITY_ATTR.equals(attribute.getKey())){
                            TypeTypeRelation typeTypeRelation = KSCollectionUtils.getRequiredZeroElement(getTypeService().getTypeTypeRelationsByOwnerAndType(typeInfo.getKey(), TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, contextInfo));
                            activityPriorityMap.put(typeTypeRelation.getRelatedTypeKey(), new Integer(Integer.parseInt(attribute.getValue())));
                        }
                    }
                }
            }


        }

        return activityPriorityMap;
    }

    /** SERVICES **/
    public static SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(SearchServiceConstants.NAMESPACE, SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public static LprService getLprService() {
        if (lprService == null){
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public static AtpService getAtpService() {
        if (atpService == null){
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public CourseService getCourseService() {
        if (courseService == null) {
            QName qname = new QName(CourseServiceConstants.NAMESPACE,
                    CourseServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseService = GlobalResourceLoader.getService(qname);
        }
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public static CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null) {
            courseOfferingService =  GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public static TypeService getTypeService() {
        if(typeService == null) {
            typeService =  GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE,
                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public static CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null) {
            courseOfferingSetService =  GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public void setCourseOfferingSetService(CourseOfferingSetService courseOfferingSetService) {
        this.courseOfferingSetService = courseOfferingSetService;
    }
}
