/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.core.process.evaluator.ProcessProposition;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nwright
 */
public class CourseRegistrationServiceProcessCheckDecorator
        extends CourseRegistrationServiceDecorator {

    private CourseWaitListService waitlistService;
    private CourseOfferingService courseOfferingService;
    private KRMSEvaluator krmsEvaluator;

    // this comparator is used to sort the reg req items in the order they are displayed on the screen.
    // allows us to validate in order.
    protected static final Comparator<RegistrationRequestItem> REG_REQ_ITEM_CREATE_DATE = new Comparator<RegistrationRequestItem>() {

        @Override
        public int compare(RegistrationRequestItem o1, RegistrationRequestItem o2) {
            int ret;
            try {
                ret = o1.getMeta().getCreateTime().compareTo(o2.getMeta().getCreateTime());
            } catch (NullPointerException ex) {
                throw new RuntimeException("Something Bad Happened while sorting regRequestItems", ex);
            }
            return ret;
        }
    };

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        Map<String, Object> executionFacts = new LinkedHashMap<>();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        if (ex != null) {
            throw new OperationFailedException("Unexpected exception while executing rules", ex);
        }
        return KRMSEvaluator.extractValidationResults(engineResults);
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String personId, String termId, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM);
        Map<String, Object> executionFacts = new LinkedHashMap<>();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        executionFacts.put(RulesExecutionConstants.ATP_ID_TERM.getName(), termId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        if (ex != null) {
            throw new OperationFailedException("Unexpected exception while executing rules", ex);
        }
        return KRMSEvaluator.extractValidationResults(engineResults);
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId,
                                                                              ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO: implement in phase II
        return new ArrayList<>();
    }


    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES);
        Map<String, Object> executionFacts = new LinkedHashMap<>();

        RegistrationRequestInfo registrationRequest = getRegistrationRequest(registrationRequestId, contextInfo);

        //Get the person id for this request (we will assume it's the same for each reg request item)
        String personId = null;
        for (RegistrationRequestItemInfo requestItem : registrationRequest.getRegistrationRequestItems()) {
            if (personId != null && !personId.equals(requestItem.getPersonId())) {
                throw new RuntimeException("Was expecting all items in the request to be for the same personID");
            }
            personId = requestItem.getPersonId();
        }

        //Look up the existing courses currently registered for
        List<CourseRegistrationInfo> existingCourses = getCourseRegistrationsByStudentAndTerm(personId, registrationRequest.getTermId(), contextInfo);
        List<CourseRegistrationInfo> waitListedCourses = waitlistService.getCourseWaitListRegistrationsByStudentAndTerm(personId, registrationRequest.getTermId(), contextInfo);
        List<CourseRegistrationInfo> simulatedCourses = new ArrayList<>();

        //Add some facts
        executionFacts.put(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName(), existingCourses);
        executionFacts.put(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName(), waitListedCourses);
        executionFacts.put(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName(), simulatedCourses);
        executionFacts.put(RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName(), registrationRequest);
        executionFacts.put(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM.getName(), registrationRequestId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);

        List<ValidationResultInfo> allValidationResults = new ArrayList<>();

        //Sort the requests so that everything is processed in the same order (using date)
        Collections.sort(registrationRequest.getRegistrationRequestItems(), Collections.reverseOrder(REG_REQ_ITEM_CREATE_DATE));

        //Make a separate validation for each course in the cart
        for (RegistrationRequestItemInfo requestItem : registrationRequest.getRegistrationRequestItems()) {
            //Look up some reg group info
            RegistrationGroupInfo registrationGroupInfo = courseOfferingService.getRegistrationGroup(requestItem.getRegistrationGroupId(), contextInfo);

            //Put In facts that are needed for each reg request
            executionFacts.put(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName(), registrationGroupInfo);
            executionFacts.put(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName(), requestItem);
            executionFacts.put(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_ID_TERM.getName(), requestItem.getId());

            //Perform the rules execution
            EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
            Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
            if (ex != null) {
                throw new OperationFailedException("Unexpected exception while executing rules", ex);
            }

            //Get the validation results
            List<ValidationResultInfo> itemValidationResults = KRMSEvaluator.extractValidationResults(engineResults);
            allValidationResults.addAll(itemValidationResults);

            //If there are no errors add this request item to the list of simulated "successful" items
            if(!ValidationUtils.checkForErrors(itemValidationResults)){
                simulatedCourses.add(convertRequestItemToCourseRegistration(requestItem, registrationGroupInfo));
            }
        }

        return allValidationResults;
    }

    private CourseRegistrationInfo convertRequestItemToCourseRegistration(RegistrationRequestItemInfo requestItem, RegistrationGroupInfo registrationGroupInfo) {
        CourseRegistrationInfo courseRegistrationInfo = new CourseRegistrationInfo();
        courseRegistrationInfo.setGradingOptionId(requestItem.getGradingOptionId());
        courseRegistrationInfo.setRegistrationGroupId(requestItem.getRegistrationGroupId());
        courseRegistrationInfo.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        courseRegistrationInfo.setPersonId(requestItem.getPersonId());
        courseRegistrationInfo.setCredits(requestItem.getCredits());
        courseRegistrationInfo.setCourseOfferingId(registrationGroupInfo.getCourseOfferingId());
        courseRegistrationInfo.setPersonId(requestItem.getPersonId());
        courseRegistrationInfo.setTermId(registrationGroupInfo.getTermId());
        courseRegistrationInfo.setId(UUIDHelper.genStringUUID());
        return courseRegistrationInfo;
    }

    public void setWaitlistService(CourseWaitListService waitlistService) {
        this.waitlistService = waitlistService;
    }

    public CourseRegistrationServiceProcessCheckDecorator() {
    }

    public CourseRegistrationServiceProcessCheckDecorator(CourseRegistrationService nextDecorator) {
        setNextDecorator(nextDecorator);
    }

    public void setKrmsEvaluator(KRMSEvaluator krmsEvaluator) {
        this.krmsEvaluator = krmsEvaluator;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

}
