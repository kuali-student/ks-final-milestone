package org.kuali.student.enrollment.class2.registration.admin.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResult;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationResultItem;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationValidationConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationValidationResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 * <p/>
 * Class is used to make individual service calls for the Registration Application.
 */
public class AdminRegistrationUtil {

    /**
     * Builds a new RegistrationResult object based on the given course and messageKey with a success level.
     *
     * @param course
     * @param messageKey
     * @return
     */
    public static RegistrationResult buildSuccessResult(RegistrationCourse course, String messageKey){
        RegistrationResult regResult = new RegistrationResult();
        regResult.setCourse(course);
        regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_SUCCESS);

        String msg = AdminRegistrationUtil.getMessageForKey(messageKey);
        regResult.getItems().add(new RegistrationResultItem(msg));
        return regResult;
    }

    /**
     * Builds a new RegistrationResult object based on the given with a warning level.
     *
     * @param course
     * @return
     */
    public static RegistrationResult buildWarningResult(RegistrationCourse course, RegistrationRequestItemInfo item){
        RegistrationResult regResult = new RegistrationResult();
        regResult.setCourse(course);
        regResult.setLevel(AdminRegConstants.ResultLevels.RESULT_LEVEL_WARNING);
        regResult.setOriginRequestTypeKey(item.getTypeKey());
        regResult.getItems().addAll(createRegResultsFromValidationResults(item.getValidationResults()));
        return regResult;
    }

    /**
     * Create Registration Results from the returned validation results.
     *
     * @param results
     * @return
     */
    public static List<RegistrationResultItem> createRegResultsFromValidationResults(List<ValidationResultInfo> results) {
        List<RegistrationResultItem> issueItems = new ArrayList<RegistrationResultItem>();
        // Add the messages to the issue items list.
        for (ValidationResult validationResult : results) {
            Map<String, String> validationMap = RegistrationValidationResultsUtil.unmarshallResult(validationResult.getMessage());

            String message = StringUtils.EMPTY;
            if (validationMap.containsKey("conflictingCourses")){
                message = AdminRegistrationUtil.getMessageForKey(validationMap.get("messageKey"), validationMap.get("conflictingCourses"));
            } else if (validationMap.containsKey("messageKey")){
                message = AdminRegistrationUtil.getMessageForKey(validationMap.get("messageKey"));
            }

            issueItems.add(new RegistrationResultItem(message));
        }
        return issueItems;
    }

    /**
     * This method builds a registration request with item with given type
     *
     * @param personId
     * @param termId
     * @param registrationCourse
     * @param typeKey
     * @return
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
     * @param personId
     * @param termId
     * @return
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
     *
     * @param personId
     * @param typeKey
     * @param registrationCourse
     * @return
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
     *
     * @param key
     * @param parameters
     * @return
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

    /**
     * Check if this registration request was an administrative override.
     *
     * NOTE: This is just a temporary solution for the override until the exemptions are ready to be built in .
     *
     * @param regRequest
     * @return true if override attribute exist with a true value.
     */
    public static boolean hasAdminOverride(RegistrationRequestInfo regRequest) {
        // Check if eligibility was overridden for this request.
        for (Attribute attr : regRequest.getAttributes()) {
            if (attr.getKey().equals(CourseRegistrationServiceConstants.ELIGIBILITY_OVERRIDE_TYPE_KEY_ATTR)) {
                return Boolean.valueOf(attr.getValue());
            }
        }
        return false;
    }

    /**
     * Retrieve the corresponding result from the list for the given item.
     *
     * @param results
     * @param item
     * @return
     */
    public static RegistrationResult retrieveFromResultList(List<RegistrationResult> results, RegistrationRequestItemInfo item){
        for (RegistrationResult regResult : results) {
            if (compareCourseAndItem(regResult.getCourse(), item)) {
                results.remove(regResult);
                return regResult;
            }
        }
        return null;
    }

    /**
     * Retrieve the corresponding course from the list for the given item.
     *
     * @param courses
     * @param item
     * @return
     */
    public static RegistrationCourse retrieveFromCourseList(List<RegistrationCourse> courses, RegistrationRequestItemInfo item){
        for (RegistrationCourse regCourse : courses) {
            if (compareCourseAndItem(regCourse, item)) {
                courses.remove(regCourse);
                return regCourse;
            }
        }
        return null;
    }

    /**
     * Compare the course with the registration request item.
     *
     * @param course
     * @param item
     * @return
     */
    public static boolean compareCourseAndItem(RegistrationCourse course, RegistrationRequestItemInfo item) {
        if (item.getRegistrationGroupId().equals(course.getRegGroupId()) &&
                item.getRegistrationRequestId().equals(course.getCurrentRegRequestId())) {
            return true;
        }
        return false;
    }

    /**
     * This method retrieve the soc given a term id.
     *
     * @param termId   Term Id
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return return the soc if there is one and only one soc with the type kuali.soc.type.main. Return NULL if
     *         there are no socs, or no main soc, or more than one main soc given a term id.
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static SocInfo getMainSocForTermId(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        int mainSocCount = 0;
        SocInfo mainSoc = null;

        List<String> socIds = AdminRegResourceLoader.getSocService().getSocIdsByTerm(termId, contextInfo);

        if (socIds!=null && !socIds.isEmpty()) {
            List<SocInfo> socInfos = AdminRegResourceLoader.getSocService().getSocsByIds(socIds, contextInfo);

            for (SocInfo socInfo : socInfos) {
                if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    mainSoc = socInfo;
                    mainSocCount++;

                    //There shouldn't be more than one main SOC of a given term
                    if (mainSocCount > 1) {
                        return null;
                    }
                }
            }
            return mainSoc;
        }

        return null;
    }
}
