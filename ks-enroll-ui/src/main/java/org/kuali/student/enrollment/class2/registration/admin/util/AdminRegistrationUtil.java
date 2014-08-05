package org.kuali.student.enrollment.class2.registration.admin.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.text.MessageFormat;
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
        registrationRequestItem.setRequestedEffectiveDate(registrationCourse.getEffectiveDate());
        registrationRequestItem.setPersonId(personId);
        registrationRequestItem.setCredits(new KualiDecimal(registrationCourse.getCredits()));
        registrationRequestItem.setGradingOptionId(registrationCourse.getGradingOptionId());
        registrationRequestItem.setOkToWaitlist(Boolean.TRUE);
        return registrationRequestItem;
    }

    /**
     * This method returns the message for the key used.
     *
     */
    public static String getMessageForKey(String key, String ... parameters){
        MessageService messageService = KRADServiceLocatorWeb.getMessageService();
        String message = messageService.getMessageText(null, null, key);

        if (message == null) {
            message = StringUtils.EMPTY;
        }

        if(parameters != null){
            message = MessageFormat.format(message, parameters);
        }
        return message;
    }
}
