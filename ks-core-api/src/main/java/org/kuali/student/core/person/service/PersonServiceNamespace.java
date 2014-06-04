package org.kuali.student.core.person.service;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class holds the constants used by the Person service
 */
public class PersonServiceNamespace {
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "person";
    public static final String REF_OBJECT_URI_PERSON = NAMESPACE + "/" + PersonInfo.class.getSimpleName();
    public static final String SERVICE_NAME_LOCAL_PART = "PersonService";
}
