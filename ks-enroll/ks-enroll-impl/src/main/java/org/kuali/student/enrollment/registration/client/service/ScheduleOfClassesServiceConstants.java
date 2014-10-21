package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import javax.xml.namespace.QName;

/**
 * Created by swedev on 1/3/14.
 */
public class ScheduleOfClassesServiceConstants {

    public static final String  NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduleOfClasses";
    public static final String  SERVICE_NAME_LOCAL_PART = ScheduleOfClassesService.class.getSimpleName();
    public static final QName   QNAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);
}
