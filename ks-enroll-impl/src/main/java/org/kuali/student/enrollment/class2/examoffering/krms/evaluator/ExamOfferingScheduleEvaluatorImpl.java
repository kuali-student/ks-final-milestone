/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.class2.examoffering.krms.evaluator;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.messenger.Messenger;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.course.service.CourseService;


import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an implementation of the ExamOfferingScheduleEvaluator interface. It uses an abstract evaluator
 * class to execute the exam offering matrix that is saved as KRMS agendas.
 * <p/>
 * On a successful execution of a rule it will create the schedule request component and link it to the given
 * exam offering id.
 *
 * @author Kuali Student Team
 */
public class ExamOfferingScheduleEvaluatorImpl extends KRMSEvaluator implements ExamOfferingScheduleEvaluator {

    private List<TermResolver<?>> eoTermResolvers;

    private RuleManagementService ruleManagementService;
    private KrmsTypeRepositoryService krmsTypeRepositoryService;
    private TermRepositoryService termRepositoryService;

    private SchedulingService schedulingService;
    private CourseService courseService;

    private Messenger userMessenger;

    /**
     * @see ExamOfferingScheduleEvaluator
     */
    public void executeRuleForAOScheduling(ActivityOffering activityOffering, String examOfferingId, String termType,
                                           ContextInfo context) throws OperationFailedException {

        //Retrieve the matrix for the specific term type.
        KrmsTypeDefinition typeDefinition = this.getKrmsTypeRepositoryService().getTypeByName(
                PermissionServiceConstants.KS_SYS_NAMESPACE, KSKRMSServiceConstants.AGENDA_TYPE_FINAL_EXAM_AO_DRIVEN);
        Agenda agenda = getAgendaForRefObjectId(termType, typeDefinition);
        if (agenda == null) {
            userMessenger.sendWarningMessage(ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_NOT_FOUND, null, context);
            return;
        }

        //Retrieve the timeslots for the specific activity offering.
        List<TimeSlotInfo> timeSlotsForAO = this.getTimeSlotsForAO(activityOffering, context);
        if (timeSlotsForAO == null || timeSlotsForAO.isEmpty()) {
            userMessenger.sendWarningMessage(ExamOfferingServiceConstants.EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND, null, context);
            return;
        }

        //Execute the matrix.
        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, context);
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_TIMESLOTS, timeSlotsForAO);
        executeRuleForScheduling(agenda, typeDefinition.getId(), executionFacts, examOfferingId, context);

    }

    /**
     * By retrieving the timeslots at this stage, the termresolver can only use the existing values and therefore execute
     * much quicker.
     *
     * @param activityOffering
     * @param context
     * @return
     * @throws OperationFailedException
     */
    private List<TimeSlotInfo> getTimeSlotsForAO(ActivityOffering activityOffering, ContextInfo context) throws OperationFailedException {
        try {
            List<String> timeSlotIDs = new ArrayList<String>();
            if (!activityOffering.getScheduleIds().isEmpty()) {
                //Retrieve the ASI
                List<ScheduleInfo> schedules = this.getSchedulingService().getSchedulesByIds(activityOffering.getScheduleIds(), context);
                //populate the ASI timeslots
                for (ScheduleInfo schedule : schedules) {
                    for (ScheduleComponentInfo scheduleComponent : schedule.getScheduleComponents()) {
                        timeSlotIDs.addAll(scheduleComponent.getTimeSlotIds());
                    }
                }
            } else {
                //Retrieve the RSI
                List<ScheduleRequestInfo> scheduleRequestInfos = this.getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityOffering.getId(), context);
                //populate the RSI timeSlots
                for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos) {
                    for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {
                        timeSlotIDs.addAll(componentInfo.getTimeSlotIds());
                    }
                }
            }
            if (!timeSlotIDs.isEmpty()) {
                return this.getSchedulingService().getTimeSlotsByIds(timeSlotIDs, context);
            }
            return null;
        } catch (Exception e) {
            throw new OperationFailedException("Unable to retrieve timeslots for ao.", e);
        }

    }

    /**
     * @see ExamOfferingScheduleEvaluator
     */
    public void executeRuleForCOScheduling(CourseOffering courseOffering, String examOfferingId, String termType,
                                           ContextInfo context) throws OperationFailedException {

        KrmsTypeDefinition typeDefinition = this.getKrmsTypeRepositoryService().getTypeByName(
                PermissionServiceConstants.KS_SYS_NAMESPACE, KSKRMSServiceConstants.AGENDA_TYPE_FINAL_EXAM_CO_DRIVEN);
        Agenda agenda = getAgendaForRefObjectId(termType, typeDefinition);

        if (agenda != null) {
            Map<String, Object> executionFacts = new HashMap<String, Object>();
            executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, context);

            try {
                Course course = this.getCourseService().getCourse(courseOffering.getCourseId(), context);
                executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_COURSE_VERSIONINDID, course.getVersion().getVersionIndId());
            } catch (Exception e) {
                throw new OperationFailedException("Unable to retrieve course version independent id.", e);
            }

            executeRuleForScheduling(agenda, typeDefinition.getId(), executionFacts, examOfferingId, context);
        } else {
            userMessenger.sendWarningMessage(ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_NOT_FOUND, null, context);
        }

    }

    /**
     * Execute the rule for the given agenda.
     * <p/>
     * This method also perform the persisting of the schedule request and timeslot if the execution of the rule
     * was successful.
     *
     * @param agenda
     * @param typeId
     * @param executionFacts
     * @param examOfferingId
     * @param context
     */
    private void executeRuleForScheduling(Agenda agenda, String typeId, Map<String, Object> executionFacts,
                                          String examOfferingId, ContextInfo context) {

        Map<String, String> agendaQualifiers = new HashMap<String, String>();
        agendaQualifiers.put("typeId", typeId);
        EngineResults results = this.evaluateAgenda(agenda, executionFacts, agendaQualifiers);

        //Check if action inserted a schedule request component.
        ScheduleRequestComponentInfo componentInfo = (ScheduleRequestComponentInfo) results.getAttribute("scheduleRequestComponentInfo");
        if (componentInfo != null) {
            TimeSlotInfo timeslot = (TimeSlotInfo) results.getAttribute("timeslotInfo");
            createRDLForExamOffering(componentInfo, timeslot, examOfferingId, context);
        } else {
            userMessenger.sendWarningMessage(ExamOfferingServiceConstants.EXAM_OFFERING_MATRIX_MATCH_NOT_FOUND, null, context);
        }

    }

    /**
     * This method creates the schedule request for the exam offering. The ScheduleRequestComponentInfo and
     * TimeSlotInfo objects were already created by action and is only passed along.
     *
     * @param componentInfo
     * @param timeSlot
     * @param examOfferingId
     * @param context
     */
    private void createRDLForExamOffering(ScheduleRequestComponentInfo componentInfo, TimeSlotInfo timeSlot,
                                          String examOfferingId, ContextInfo context) {

        List<ScheduleRequestSetInfo> requestSetList = new ArrayList<ScheduleRequestSetInfo>();
        try {
            requestSetList = getSchedulingService().getScheduleRequestSetsByRefObject(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, examOfferingId, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       if(requestSetList.isEmpty() ||requestSetList == null){
        //Create new sch set for this eo.
        ScheduleRequestSetInfo requestSet = new ScheduleRequestSetInfo();
        requestSet.setRefObjectTypeKey(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING);

        requestSet.setName("Exam Schedule request set");
        requestSet.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        requestSet.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        requestSet.getRefObjectIds().add(examOfferingId);
        try {
            requestSet = getSchedulingService().createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                    ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, requestSet, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ScheduleRequestInfo scheduleRequest = new ScheduleRequestInfo();
        scheduleRequest.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        scheduleRequest.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        scheduleRequest.setScheduleRequestSetId(requestSet.getId());

        try {
            List<TimeSlotInfo> existingList = this.getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM,
                    timeSlot.getWeekdays(), timeSlot.getStartTime(), timeSlot.getEndTime(), context);
            if (existingList.isEmpty()) {
                TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(
                        SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM, timeSlot, context);
                componentInfo.getTimeSlotIds().add(createdTimeSlot.getId());
            } else {
                for (TimeSlotInfo existing : existingList) {
                    componentInfo.getTimeSlotIds().add(existing.getId());
                }
            }

            scheduleRequest.getScheduleRequestComponents().add(componentInfo);

            this.getSchedulingService().createScheduleRequest(
                    SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, scheduleRequest, context);
        } catch (Exception e) {
            throw new RuntimeException("Error creating timeslot: " + timeSlot, e);
        }
       }
    }

    /**
     * Retrieves the specific agenda linked to the given rebobjectid that also is also of the given type. By manualy
     * retrieving the agenda we only have to load a single agenda and is therefore much quicker.
     *
     * @param refObjectId
     * @param typeDefinition
     * @return
     */
    private Agenda getAgendaForRefObjectId(String refObjectId, KrmsTypeDefinition typeDefinition) {

        //Retrieve bindings for this reference object id.
        List<ReferenceObjectBinding> referenceObjectBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(
                TypeServiceConstants.REF_OBJECT_URI_TYPE, refObjectId);
        for (ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings) {

            //Retrieve the agenda and check the type.
            AgendaDefinition agendaDefinition = this.getRuleManagementService().getAgenda(referenceObjectBinding.getKrmsObjectId());
            if (agendaDefinition.getTypeId().equals(typeDefinition.getId())) {

                //Only a single agenda for a specific type should exist in the matrix.
                return KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator().translateAgendaDefinition(agendaDefinition);
            }
        }
        return null;
    }

    /**
     * This method return a specific list of termresolvers that are only used in the scheduling of final exams. This
     * is so that the list would be as short as possible as opposed to the all the term resolvers that exist within
     * the namespace.
     *
     * @return
     */
    public List<TermResolver<?>> getTermResolvers() {
        if (eoTermResolvers == null) {

            KrmsTypeDefinition typeDefinition = this.getKrmsTypeRepositoryService().getTypeByName(
                    PermissionServiceConstants.KS_SYS_NAMESPACE, KSKRMSServiceConstants.TERM_RESOLVER_TYPE_FINAL_EXAM);

            TermResolverTypeService termResolverTypeService = GlobalResourceLoader.getService(new QName(typeDefinition.getServiceName()));

            eoTermResolvers = new ArrayList<TermResolver<?>>();
            List<TermResolverDefinition> definitions = this.getTermRepositoryService().findTermResolversByNamespace(PermissionServiceConstants.KS_SYS_NAMESPACE);
            for (TermResolverDefinition definition : definitions) {
                if (definition.getTypeId().equals(typeDefinition.getId())) {
                    eoTermResolvers.add(termResolverTypeService.loadTermResolver(definition));
                }
            }
        }
        return eoTermResolvers;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    public void setKrmsTypeRepositoryService(KrmsTypeRepositoryService krmsTypeRepositoryService) {
        this.krmsTypeRepositoryService = krmsTypeRepositoryService;
    }

    public TermRepositoryService getTermRepositoryService() {
        if (termRepositoryService == null) {
            termRepositoryService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "termRepositoryService"));
        }
        return termRepositoryService;
    }

    public void setTermRepositoryService(TermRepositoryService termRepositoryService) {
        this.termRepositoryService = termRepositoryService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public Messenger getUserMessenger() {
        return userMessenger;
    }

    public void setUserMessenger(Messenger userMessenger) {
        this.userMessenger = userMessenger;
    }

}
