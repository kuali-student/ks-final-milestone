package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

import javax.xml.namespace.QName;

/**
 * Created by swedev on 2/11/14.
 */
public class CourseRegistrationCartClientServiceConstants {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseRegistrationCartClient";
    public static final String SERVICE_NAME_LOCAL_PART = CourseRegistrationCartClientService.class.getSimpleName();
    public static final QName QNAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);
}
