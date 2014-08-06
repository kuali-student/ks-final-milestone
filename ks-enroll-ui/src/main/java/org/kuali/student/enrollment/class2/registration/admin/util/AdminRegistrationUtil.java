package org.kuali.student.enrollment.class2.registration.admin.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResult;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResultItem;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
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

    public static RegistrationResult buildSuccessRegistrationResult(RegistrationCourse course, String messageKey){
        RegistrationResult regResult = new RegistrationResult();
        regResult.setCourse(course);
        regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_SUCCESS);

        String msg = AdminRegistrationUtil.getMessageForKey(messageKey);
        regResult.getItems().add(new RegistrationResultItem(msg));
        return regResult;
    }

    public static RegistrationResult buildWarningRegistrationResult(RegistrationCourse course){
        RegistrationResult regResult = new RegistrationResult();
        regResult.setCourse(course);
        regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_WARNING);

        return regResult;
    }

    /**
     * This method builds a registration request with item with given type
     *
     * @return registration request
     */
    public static RegistrationRequestInfo buildRegistrationRequest(String personId, String termId, RegistrationCourse registrationCourse, String typeKey) {

        //Create the request object
        RegistrationRequestInfo regRequest = AdminRegistrationUtil.createRegistrationRequest(personId, termId);
        regRequest.getRegistrationRequestItems().add(AdminRegistrationUtil.createRegistrationRequestItem(personId, typeKey, registrationCourse));

        return regRequest;
    }

    /**
     * This method creates a registration request
     *
     * @return registration request
     */
    public static RegistrationRequestInfo createRegistrationRequest(String personId, String termId) {

        RegistrationRequestInfo regReqInfo = new RegistrationRequestInfo();
        regReqInfo.setRequestorId(personId);
        regReqInfo.setTermId(termId);
        regReqInfo.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);
        regReqInfo.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        return regReqInfo;
    }

    /**
     * This method creates a registration request for the add operation of a single registration group.
     */
    public static RegistrationRequestItemInfo createRegistrationRequestItem(String personId, String typeKey, RegistrationCourse registrationCourse) {

        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setPersonId(personId);
        registrationRequestItem.setTypeKey(typeKey);

        registrationRequestItem.setRegistrationGroupId(registrationCourse.getRegGroupId());
        registrationRequestItem.setExistingCourseRegistrationId(registrationCourse.getCourseRegistrationId());
        registrationRequestItem.setRequestedEffectiveDate(registrationCourse.getEffectiveDate());
        registrationRequestItem.setCredits(new KualiDecimal(registrationCourse.getCredits()));
        registrationRequestItem.setGradingOptionId(registrationCourse.getGradingOptionId());

        registrationRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        registrationRequestItem.setOkToWaitlist(Boolean.TRUE);
        return registrationRequestItem;
    }

    /**
     * This method returns the message for the key used.
     */
    public static String getMessageForKey(String key, String... parameters) {
        MessageService messageService = KRADServiceLocatorWeb.getMessageService();
        String message = messageService.getMessageText(null, null, key);

        if (message == null) {
            message = StringUtils.EMPTY;
        }

        if (parameters != null) {
            message = MessageFormat.format(message, parameters);
        }
        return message;
    }

    public static boolean hasAdminOverride(RegistrationRequestInfo regRequest) {
        // Check if eligibility was overridden for this request.
        for (Attribute attr : regRequest.getAttributes()) {
            if (attr.getKey().equals(CourseRegistrationServiceConstants.ELIGIBILITY_OVERRIDE_TYPE_KEY_ATTR)) {
                return Boolean.valueOf(attr.getValue());
            }
        }
        return false;
    }

    public static RegistrationCourse retrieveFromResultList(List<RegistrationResult> results, RegistrationRequestItemInfo item){
        for (RegistrationResult regResult : results) {
            if (compareCourseAndItem(regResult.getCourse(), item)) {
                results.remove(regResult);
                return regResult.getCourse();
            }
        }
        return null;
    }

    public static RegistrationCourse retrieveFromCourseList(List<RegistrationCourse> courses, RegistrationRequestItemInfo item){
        for (RegistrationCourse regCourse : courses) {
            if (compareCourseAndItem(regCourse, item)) {
                courses.remove(regCourse);
                return regCourse;
            }
        }
        return null;
    }

    public static boolean compareCourseAndItem(RegistrationCourse course, RegistrationRequestItemInfo item) {
        if (course.getRegGroupId().equals(item.getRegistrationGroupId())) {
            return true;
        }
        return false;
    }
}
