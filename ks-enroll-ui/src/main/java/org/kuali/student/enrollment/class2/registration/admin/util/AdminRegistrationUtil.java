package org.kuali.student.enrollment.class2.registration.admin.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.service.PersonServiceNamespace;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
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
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 * <p/>
 * Class is used to make individual service calls for the Registration Application.
 */
public class AdminRegistrationUtil {

    public static List<String> getCourseOfferingCreditOptionValues(String creditOptionId)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        int firstValue = 0;
        List<String> creditOptions = new java.util.ArrayList<String>();

        //Lookup the selected credit option and set from persisted values
        if (!creditOptionId.isEmpty()) {
            //Lookup the resultValueGroup Information
            ResultValuesGroupInfo resultValuesGroupInfo = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesGroup(creditOptionId, ContextUtils.getContextInfo());
            String typeKey = resultValuesGroupInfo.getTypeKey();

            //Get the actual values
            List<ResultValueInfo> resultValueInfos = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesByKeys(
                    resultValuesGroupInfo.getResultValueKeys(), ContextUtils.getContextInfo());

            if (!resultValueInfos.isEmpty()) {
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    creditOptions.add(resultValueInfos.get(firstValue).getValue()); // fixed credits
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) ||
                        typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {  // multiple or range
                    for (ResultValueInfo resultValueInfo : resultValueInfos) {
                        creditOptions.add(resultValueInfo.getValue());
                    }
                }
            }
        }

        return creditOptions;
    }

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

        for (RegistrationCourse registrationCourse : registrationCourses) {
            RegistrationRequestItemInfo registrationRequestItem = createNewRegistrationRequestItem(personId, registrationCourse);
            regReqInfo.getRegistrationRequestItems().add(registrationRequestItem);
        }

        return regReqInfo;
    }

    /**
     * This method creates a registration request for the add operation of a single registration group.
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
