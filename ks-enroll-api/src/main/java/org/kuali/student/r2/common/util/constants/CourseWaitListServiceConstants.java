package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

public class CourseWaitListServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseWaitList";
    public static final String SERVICE_NAME_LOCAL_PART = "CourseWaitListService";
    public static final String REF_OBJECT_URI_WAIT_LIST = NAMESPACE + "/" + CourseWaitListInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_WAIT_LIST_ENTRY = NAMESPACE + "/" + CourseWaitListEntryInfo.class.getSimpleName();


    public static final String COURSE_WAIT_LIST_WAIT_TYPE_KEY = "kuali.course.waitlist.type.course.waitlist";


    public static final String COURSE_WAIT_LIST_ACTIVE_STATE_KEY = "kuali.course.waitlist.state.active";
    public static final String COURSE_WAIT_LIST_INACTIVE_STATE_KEY = "kuali.course.waitlist.state.inactive";

}
