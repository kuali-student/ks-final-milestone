package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

import javax.xml.namespace.QName;

/**
 * Created by paulrichardson on 5/27/14.
 */
public class ScheduleOfClassesClientServiceConstants {

    public static final String  NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduleOfClassesClient";
    public static final String  SERVICE_NAME_LOCAL_PART = ScheduleOfClassesClientService.class.getSimpleName();
    public static final QName   QNAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);
}
