package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.messages.dto.MessageInfo;


/**
 * This class holds the constants used by the Comment service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class MessageServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "message";
    public static final String REF_OBJECT_URI_MESSAGE = NAMESPACE + "/" + MessageInfo.class.getSimpleName();

}
