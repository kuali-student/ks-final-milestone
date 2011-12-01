package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.messages.dto.LocaleKeysInfo;
import org.kuali.student.r2.common.messages.dto.MessageGroupKeysInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.dto.MessagesInfo;

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
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "comment";
    public static final String REF_OBJECT_URI_MESSAGE = NAMESPACE + "/" + MessageInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MESSAGES = NAMESPACE + "/" + MessagesInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MESSAGE_GROUP_KEYS = NAMESPACE + "/" + MessageGroupKeysInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LOCALE_KEYS = NAMESPACE + "/" + LocaleKeysInfo.class.getSimpleName();

}
