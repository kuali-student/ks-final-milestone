package org.kuali.student.enrollment.class2.courseoffering.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.uif.util.KSGrowlMessenger;
import org.kuali.student.common.uif.util.Messenger;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.acal.dto.ExamPeriodWrapper;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingContext;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExamOfferingManagementUtil {

    private static ExamOfferingServiceFacade examOfferingServiceFacade;

    public static ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        if (examOfferingServiceFacade == null) {
            examOfferingServiceFacade = (ExamOfferingServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/examOfferingServiceFacade", "examOfferingServiceFacade"));
        }
        return examOfferingServiceFacade;
    }

    public static String examPeriodDaysDisplay(List<Integer> weekdaysList, ExamPeriodWrapper examPeriodWrapper) {
        StringBuilder result = new StringBuilder();
        List<Date> dates = new ArrayList<Date>();
        dates.addAll(examPeriodWrapper.getExamPeriodDates());
        for (Integer weekday : weekdaysList) {
            result.append("Day " + weekday);
            result.append(" - ");
            result.append(DateFormatters.EXAM_OFFERING_VIEW_EXAM_OFFERING_DATE_FORMATTER.format(dates.get(weekday - 1)));
        }

        return result.toString();
    }

    public static AttributeInfo createAttribute(String key, String value) {
        AttributeInfo newAttr = new AttributeInfo();
        newAttr.setKey(key);
        newAttr.setValue(value);
        return newAttr;
    }

    public static AttributeInfo getAttributeForKey(List<AttributeInfo> attributeInfos, String key) {
        for (AttributeInfo info : attributeInfos) {
            if (info.getKey().equals(key)) {
                return info;
            }
        }
        return null;
    }

    public static void processExamOfferingResultSet(ExamOfferingResult result) {

        Messenger messenger = (Messenger) GlobalVariables.getUserSession().retrieveObject(KSGrowlMessenger.MESSENGER_KEY);
        if (messenger == null) {
            messenger = new KSGrowlMessenger();
            GlobalVariables.getUserSession().addObject(KSGrowlMessenger.MESSENGER_KEY, messenger);
        }

        processExamOfferingResult(result, messenger);
    }

    public static void processExamOfferingResultSetForAO(ExamOfferingResult result) {
        if (ExamOfferingServiceConstants.EXAM_OFFERING_GENERATED_PER_CO.equals(result.getKey())) {
            Messenger messenger = (Messenger) GlobalVariables.getUserSession().retrieveObject(KSGrowlMessenger.MESSENGER_KEY);
            if (messenger == null) {
                messenger = new KSGrowlMessenger();
                GlobalVariables.getUserSession().addObject(KSGrowlMessenger.MESSENGER_KEY, messenger);
            }
            for (ExamOfferingResult examOfferingResult : result.getChildren()) {
                messenger.addSuccessMessage(ExamOfferingConstants.EXAM_OFFERING_CO_EDIT_SUCCESS, contextMapToParameters(examOfferingResult.getContext()));
                break;
            }
            return;
        }
        processExamOfferingResultSet(result);
    }

    public static String[] contextMapToParameters(Map<String, String> context){
        List<String> parameters = new ArrayList<String>();
        if(context.containsKey(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE)){
            parameters.add(context.get(CourseOfferingServiceConstants.CONTEXT_ELEMENT_COURSE_OFFERING_CODE));
        }
        if(context.containsKey(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE)){
            parameters.add(context.get(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE));
        }
        return parameters.toArray(new String[parameters.size()]);
    }

    public static void processExamOfferingResult(ExamOfferingResult result, Messenger messenger) {

        if (ExamOfferingServiceConstants.EXAM_OFFERING_CREATED.equals(result.getKey())) {
            if (!processExamOfferingSubResult(result.getChildren(), result, messenger)) {
                if(result.getContext().containsKey(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE)){
                    messenger.addSuccessMessage(ExamOfferingConstants.EXAM_OFFERING_AO_CREATE_SUCCESS, contextMapToParameters(result.getContext()));
                } else {
                    messenger.addSuccessMessage(ExamOfferingConstants.EXAM_OFFERING_CO_CREATE_SUCCESS, contextMapToParameters(result.getContext()));
                }
            }
            return;
        } else if (ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED.equals(result.getKey())) {
            if (processExamOfferingSubResult(result.getChildren(), result, messenger)) {
                if(result.getContext().containsKey(CourseOfferingServiceConstants.CONTEXT_ELEMENT_ACTIVITY_OFFERING_CODE)){
                    messenger.addSuccessMessage(ExamOfferingConstants.EXAM_OFFERING_AO_EDIT_SUCCESS, contextMapToParameters(result.getContext()));
                } else {
                    messenger.addSuccessMessage(ExamOfferingConstants.EXAM_OFFERING_CO_EDIT_SUCCESS, contextMapToParameters(result.getContext()));
                }
            }
            return;
        } else if (ExamOfferingServiceConstants.EXAM_OFFERING_UNCHANGED.equals(result.getKey())) {
            processExamOfferingSubResult(result.getChildren(), result, messenger);
        } else if (ExamOfferingServiceConstants.EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND.equals(result.getKey())) {
            messenger.addErrorMessage(ExamOfferingConstants.EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND, contextMapToParameters(result.getContext()));
        }

        for (ExamOfferingResult child : result.getChildren()) {
            processExamOfferingResult(child, messenger);
        }

    }

    public static boolean processExamOfferingSubResult(List<ExamOfferingResult> results, ExamOfferingResult parent, Messenger messenger) {
        String subResultKey = getExamOfferingSubResultKey(results, parent, messenger);
        if(subResultKey!=null){
            messenger.addWarningMessage(subResultKey, contextMapToParameters(parent.getContext()));
            return true;
        }
        return false;
    }

    public static String getExamOfferingSubResultKey(List<ExamOfferingResult> results, ExamOfferingResult parent, Messenger messenger) {

        for (ExamOfferingResult result : results) {
            if (ExamOfferingServiceConstants.EXAM_OFFERING_CREATED.equals(parent.getKey())) {
                if (ExamOfferingServiceConstants.EXAM_OFFERING_AO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_CREATED_AO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_CO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_CREATED_CO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_CREATED_MATRIX_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_CREATED_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND;
                }
            } else if (ExamOfferingServiceConstants.EXAM_OFFERING_UPDATED.equals(parent.getKey())) {
                if (ExamOfferingServiceConstants.EXAM_OFFERING_AO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_UPDATED_AO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_CO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_UPDATED_CO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_UPDATED_MATRIX_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_UPDATED_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND;
                }
            } else {
                if (ExamOfferingServiceConstants.EXAM_OFFERING_AO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_AO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_CO_MATRIX_MATCH_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_CO_MATRIX_MATCH_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_MATRIX_NOT_FOUND;
                } else if (ExamOfferingServiceConstants.EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND.equals(result.getKey())) {
                    return ExamOfferingConstants.EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND;
                }
            }

        }

        return null;

    }

    public static ExamOfferingContext createExamOfferingContext(ActivityOfferingInfo activityOffering)  throws Exception {
        CourseOfferingInfo courseOffering = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(
                activityOffering.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        ExamOfferingContext context = new ExamOfferingContext(courseOffering, activityOffering);
        context.setExamPeriodId(getExamOfferingServiceFacade().getExamPeriodId(context.getTermId(),
                ContextUtils.createDefaultContextInfo()));
        return context;
    }

    public static ExamOfferingContext createExamOfferingContext(CourseOfferingInfo courseOffering)  throws Exception {
        ExamOfferingContext context = new ExamOfferingContext(courseOffering);
        context.setExamPeriodId(getExamOfferingServiceFacade().getExamPeriodId(context.getTermId(),
                ContextUtils.createDefaultContextInfo()));
        return context;
    }

    public static ExamOfferingContext createExamOfferingContext(CourseOfferingInfo courseOffering, String examPeriodId)  throws Exception {
        ExamOfferingContext context = new ExamOfferingContext(courseOffering);
        context.setExamPeriodId(examPeriodId);
        return context;
    }

    public static ExamOfferingContext createExamOfferingContext(CourseOfferingInfo courseOffering, ActivityOfferingInfo activityOffering)  throws Exception {
        ExamOfferingContext context = new ExamOfferingContext(courseOffering, activityOffering);
        context.setExamPeriodId(getExamOfferingServiceFacade().getExamPeriodId(context.getTermId(),
                ContextUtils.createDefaultContextInfo()));
        return context;
    }

}
