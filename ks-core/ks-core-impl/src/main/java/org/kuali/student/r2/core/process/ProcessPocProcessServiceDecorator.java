/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.kuali.student.r2.core.process.service.decorators.ProcessServiceDecorator;

import java.util.List;

/**
 *
 * @author nwright
 */
public class ProcessPocProcessServiceDecorator extends ProcessServiceDecorator {

    public ProcessPocProcessServiceDecorator(ProcessService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        initializeData();
    }

    private boolean isInitalized() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");
        try {
            ProcessInfo info = this.getNextDecorator().getProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        } catch (DoesNotExistException ex) {
            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    private void initializeData() {
        if (isInitalized()) {
            return;
        }

        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        _createProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, "Basic Eligibility", "The process of checking a student's basic eligibility to register for courses.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "Eligibility for Term", "The process of checking a student's eligibility to register for a particular term.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "Holds Cleared", "The process of checking a student's eligibility to register for a particular term.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "Eligible for Course", "The process of checking a student's eligibility to register for a particular course.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "Eligible for Courses", "The process of checking a student's eligibility and ability to register for a proposed set of courses.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_REGISTER_FOR_COURSES, "Register for Courses", "The process of checking a student's eligibility and actually register for a proposed set of courses.", context);

        _createCheck(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "", "", "kuali.agenda.is.alive", "", "is alive", "Checks if student is actually alive", context);
        _createCheck(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "kuali.hold.issue.library.book.overdue", "", "", "", "has overdue library book", "Checks if student has an overdue library book", context);
        _createCheck(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "kuali.hold.issue.financial.unpaid.tuition.prior.term", "", "", "", "has not paid bill from prior term", "Checks if student has an unpaid bill from a prior term", context);
        _createCheck(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "", "", "", "kuali.process.registration.basic.eligibility", "student has basic eligibility", "Checks all the checks defined in the basic eligibility process", context);
        _createCheck(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, "", "kuali.atp.milestone.RegistrationPeriod", "", "", "registration period is open", "Checks that the registration period is open", context);
        _createCheck(ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY, "", "kuali.atp.milestone.RegistrationPeriod", "", "", "registration period is not closed", "Checks that the registration period is not yet closed", context);
        _createCheck(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "", "", "", "kuali.process.registration.holds.cleared", "Registration Holds Cleared", "Checks that the checks in the registration holds process", context);
        _createCheck(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "", "", "kuali.agenda.is.not.summer.term", "", "is not Summer Term", "Checks that this is not the summer term", context);
        _createCheck(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "", "", "", "kuali.process.registration.eligibility.for.term", "Eligibility for Term", "Checks all the checks that the student is eligible for the term", context);
        _createCheck(ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY, "", "", "", "", "Has the necessary pre-req", "Checks that the student has all the necessary pee-requisites to take the course", context);
        _createCheck(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "", "", "", "kuali.process.registration.eligible.for.course", "student has eligibility for each course", "Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses", context);
        _createCheck(ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, "", "", "", "", "does not exceed credit limit", "Checks that the student has not exceeded her credit limit", context);

        _createInstruction(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, "kuali.population.everyone", "kuali.check.is.alive", "A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.", 1, false, false, false, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "kuali.population.everyone", "kuali.check.has.overdue.library.book", "Please note: you have an overdue library book", 3, true, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "kuali.population.everyone", "kuali.check.has.not.paid.bill.from.prior.term", "You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter", 5, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone", "kuali.check.student.has.basic.eligibility", "Something about you as a person or your relationship with this institution needs to be fixed", 1, false, false, false, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone", "kuali.check.registration.period.is.open", "Registration period for this term has not yet begun", 3, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone", "kuali.check.registration.period.is.not.closed", "Registration period for this term is closed", 4, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone", "kuali.check.registration.holds.cleared", "You have one or more holds that need to be cleared", 5, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.summer.only.student", "kuali.check.is.not.summer.term", "Summer only students cannot register for fall, winter or spring terms", 9, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "kuali.population.everyone", "kuali.check.eligibility.for.term", "", 1, false, false, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "kuali.population.everyone", "kuali.check.has.the.necessary.prereq", "", 2, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "kuali.population.everyone", "kuali.check.student.has.eligibility.for.each.course", "", 1, false, false, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "kuali.population.everyone", "kuali.check.does.not.exceed.credit.limit", "You are exceeding your credit limit", 2, false, false, true, context);
    }

    private void _createInstruction(String processKey,
            String populationKey,
            String checkKey,
            String message,
            int position,
            boolean isWarning,
            boolean continueOnFail,
            boolean canBeExempted,
            ContextInfo context) {

        InstructionInfo info = new InstructionInfo();
        info.setStateKey(ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY);
        info.setProcessKey(processKey);
        // info.setAppliedPopulationKeys(Arrays.asList(populationKey));
        info.setAppliedPopulationId(populationKey);
        info.setCheckId(checkKey);
        info.setMessage(new RichTextHelper().fromPlain(message));
        info.setPosition(position);
        info.setContinueOnFail(continueOnFail);
        info.setIsWarning(isWarning);
        info.setIsExemptible(canBeExempted);
        try {
            info = this.createInstruction(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, info.getProcessKey(), info.getCheckId(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }

    }

    private ProcessInfo _createProcess(String key, String name, String descr, ContextInfo context) {
        ProcessInfo info = new ProcessInfo();
        info.setKey(key);
        info.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(descr));
        try {
            info = this.createProcess(ProcessServiceConstants.PROCESS_TYPE_KEY, info.getKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return info;
    }

    private CheckInfo _createCheck(String type, String issueId, String milestoneTypeKey, String agendaId, String processKey, String name, String descr, ContextInfo context) {
        CheckInfo info = new CheckInfo();

        info.setTypeKey(type);
        info.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ACTIVE);
        info.setName(name);
        info.setHoldIssueId(_toNull(issueId));
        info.setMilestoneTypeKey(_toNull(milestoneTypeKey));
        info.setAgendaId(_toNull(agendaId));
        info.setChildProcessKey(_toNull(processKey));
        info.setDescr(new RichTextHelper().fromPlain(descr));

        try {
            info = this.createCheck(type, info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return info;
    }

    private String _toNull(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return str;
    }

    @Override
    public StatusInfo reorderInstructions(String processKey,
            List<String> instructionIds,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
