package org.kuali.student.enrollment.class2.registration.admin.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.service.PersonServiceNamespace;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 *
 * Class is used to make individual service calls for the Registration Application.
 */
public class AdminRegResourceLoader {

    private static PersonService personService;
    private static AcademicCalendarService academicCalendarService;
    private static IdentityService identityService;
    private static CourseRegistrationService courseRegService;
    private static CourseOfferingService courseOfferingService;
    private static SchedulingService schedulingService;
    private static RoomService roomService;
    private static CourseWaitListService courseWaitListService;
    private static LuiService luiService;
    private static CourseOfferingSetService socService;

    public static PersonService getPersonService() {
        if (personService == null) {
            personService = GlobalResourceLoader.getService(new QName(PersonServiceNamespace.NAMESPACE, PersonServiceNamespace.SERVICE_NAME_LOCAL_PART));
        }
        return personService;
    }

    public static AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }

    public static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public static CourseRegistrationService getCourseRegistrationService() {
        if (courseRegService == null) {
            courseRegService = GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegService;
    }

    /**
     * For Admin Registration we use the Cached version of the Course Offering Service.
     * @return courseOfferingService
     */
    public static CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.CACHED_SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public static CourseWaitListService getCourseWaitlistService() {
        if (courseWaitListService == null) {
            courseWaitListService = GlobalResourceLoader.getService(new QName(CourseWaitListServiceConstants.NAMESPACE, CourseWaitListServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseWaitListService;
    }

    public static SchedulingService getSchedulingService() {
        if (schedulingService == null) {
            schedulingService = GlobalResourceLoader.getService(new QName(SchedulingServiceConstants.NAMESPACE, SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return schedulingService;
    }

    public static RoomService getRoomService() {
        if (roomService == null) {
            roomService = GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE, RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    public static LuiService getLuiService() {
        if (luiService == null) {
            luiService = GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public static CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

}
