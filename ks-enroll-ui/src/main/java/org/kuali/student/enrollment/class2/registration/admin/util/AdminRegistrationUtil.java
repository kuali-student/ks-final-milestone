package org.kuali.student.enrollment.class2.registration.admin.util;

import net.sf.ehcache.CacheManager;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.service.PersonServiceNamespace;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

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
public class AdminRegistrationUtil {

    /**
     * This method creates a registration request
     *
     * @return registration request
     */
    public static RegistrationRequestInfo createRegistrationRequest(String personId, String termId, List<RegistrationCourse> registrationCourses) {
        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(personId);
        regReqInfo.setTermId(termId);
        regReqInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);
        regReqInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        for(RegistrationCourse registrationCourse : registrationCourses) {
            RegistrationRequestItemInfo registrationRequestItem = createNewRegistrationRequestItem(personId, registrationCourse);
            regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);
        }

        return regReqInfo;
    }

    /**
     * This method creates a registration request for the add operation of a single registration group.
     *
     */
    public static RegistrationRequestItemInfo createNewRegistrationRequestItem(String personId, RegistrationCourse registrationCourse) {

        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        registrationRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        registrationRequestItem.setRegistrationGroupId(registrationCourse.getRegGroup().getId());
        //registrationRequestItem.setExistingCourseRegistrationId(); only doing add for now.
        registrationRequestItem.setPersonId(personId);
        registrationRequestItem.setCredits(new KualiDecimal(registrationCourse.getCredits()));
        //registrationRequestItem.setGradingOptionId(registrationCourse.getGradingOption()); make sure about what we set here.
        registrationRequestItem.setOkToWaitlist(Boolean.TRUE);
        return registrationRequestItem;
    }
}
