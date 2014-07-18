/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.kuali.student.r2.core.constants.AtpServiceConstants;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestProcessServiceDataLoadingDecorator extends ProcessServiceDecorator {

    public ProcessIntegrationTestProcessServiceDataLoadingDecorator() {
    }

    public ProcessIntegrationTestProcessServiceDataLoadingDecorator(ProcessService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        init();
    }

    private boolean isInitalized() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("Test-Initializer");
        try {
            ProcessInfo info = this.getNextDecorator().getProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        } catch (DoesNotExistException ex) {
            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public void init() {
        if (isInitalized()) {
            return;
        }

        ContextInfo context = new ContextInfo();
        context.setPrincipalId("Test-Initializer");

        _createProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, "Basic Eligibility",
                "The process of checking a student's basic eligibility to register for courses.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "Eligibility for Term",
                "The process of checking a student's eligibility to register for a particular term.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "Holds Cleared",
                "The process of checking a student's eligibility to register for a particular term.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "Eligible for Course",
                "The process of checking a student's eligibility to register for a particular course.", context);
        _createProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "Eligible for Courses",
                "The process of checking a student's eligibility and ability to register for a proposed set of courses.", context);

        // create checks
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_IS_ALIVE, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "", "",
                "kuali.rule.is.alive", "", "is alive", "Checks if student is actually alive", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY,
                "kuali.hold.issue.library.book.overdue", "", "", "", "has overdue library book",
                "Checks if student has an overdue library book", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY,
                "kuali.hold.issue.financial.unpaid.tuition.prior.term", "", "", "", "has not paid bill from prior term",
                "Checks if student has an unpaid bill from a prior term", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_STUDENT_HAS_BASIC_ELIGIBILITY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY,
                "", "", "", "kuali.process.registration.basic.eligibility", "student has basic eligibility",
                "Checks all the checks defined in the basic eligibility process", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_REGISTRATION_PERIOD_IS_OPEN, ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY,
                "", "kuali.atp.milestone.RegistrationPeriod", "", "", "registration period is open",
                "Checks that the registration period is open", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED,
                ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY, "", "kuali.atp.milestone.RegistrationPeriod", "", "",
                "registration period is not closed", "Checks that the registration period is not yet closed", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_REGISTRATION_HOLDS_CLEARED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "",
                "", "", "kuali.process.registration.holds.cleared", "Registration Holds Cleared",
                "Checks that the checks in the registration holds process", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_FALSE, ProcessServiceConstants.ALWAYS_FALSE_CHECK_TYPE_KEY, "", "", "", "",
                "FALSE", "Always False", context);        
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_ELIGIBILITY_FOR_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "", "", "",
                "", "", "kuali.process.registration.eligibility.for.term", "Eligibility for Term",
                "Checks all the checks that the student is eligible for the term", context);

        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_DOES_NOT_EXCEED_CREDIT_LIMIT,
                ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, "", "", "", "kuali.rule.credit.load",
                "kuali.rule.credit.limit", "", "does not exceed credit limit",
                "Checks that the student has not exceeded her credit limit", context);
        _createCheck(ProcessIntegrationTestConstants.CHECK_ID_DOES_NOT_MEET_CREDIT_MINIMUM,
                ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, "", "", "", "kuali.rule.credit.load",
                "kuali.rule.credit.minimum", "", "does not meet credit minimum",
                "Checks that the student has enough credits to meet the minimum required for her student type", context);

        // create instructions
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, "kuali.population.everyone",
                "kuali.check.is.alive",
                "A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.",
                1, false, false, false, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "kuali.population.everyone",
                "kuali.check.has.overdue.library.book", "Please note: you have an overdue library book", 3, true, true, true,
                context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, "kuali.population.everyone",
                "kuali.check.has.not.paid.bill.from.prior.term",
                "You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter", 5,
                false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone",
                "kuali.check.student.has.basic.eligibility",
                "You failed one or more of the basic eligibility checks", 1, false, false,
                false, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone",
                "kuali.check.registration.period.is.open", "Registration period for this term has not yet begun", 3, false, true,
                true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone",
                "kuali.check.registration.period.is.not.closed", "Registration period for this term is closed", 4, false, true,
                true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.everyone",
                "kuali.check.registration.holds.cleared", "You have one or more holds that need to be cleared", 5, false, true,
                true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, "kuali.population.summer.only.student",
                Arrays.asList(AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY,
                AtpServiceConstants.ATP_SPRING_TYPE_KEY),
                "kuali.check.false", "Summer only students cannot register for fall, winter or spring terms", 9, false, true, true,
                context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "kuali.population.everyone",
                "kuali.check.eligibility.for.term", "", 1, false, false, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, "kuali.population.everyone",
                "kuali.check.has.the.necessary.prereq", "", 2, false, true, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "kuali.population.everyone",
                "kuali.check.eligibility.for.term", "You are not eligible to register for the term", 1, false, false, true,
                context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "kuali.population.everyone",
                "kuali.check.does.not.exceed.credit.limit", "You have exceeded your credit limit", 2, false, false, true, context);
        _createInstruction(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, "kuali.population.everyone",
                "kuali.check.does.not.meet.credit.minimum", "You have not registered for enough courses", 3, false, false, true,
                context);
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
        List<String> atpTypeKeys = Collections.EMPTY_LIST;
        this._createInstruction(processKey, populationKey, atpTypeKeys, checkKey, message, position, isWarning, continueOnFail,
                canBeExempted, context);
    }

    private void _createInstruction(String processKey,
            String populationKey,
            List<String> atpTypeKeys,
            String checkKey,
            String message,
            int position,
            boolean isWarning,
            boolean continueOnFail,
            boolean canBeExempted,
            ContextInfo context) {

        InstructionInfo info = new InstructionInfo();
        info.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY);
        info.setProcessKey(processKey);
        info.setAppliedPopulationId(populationKey);
        info.setAppliedAtpTypeKeys(atpTypeKeys);
        info.setCheckId(checkKey);
        info.setMessage(new RichTextHelper().fromPlain(message));
        info.setPosition(position);
        info.setContinueOnFail(continueOnFail);
        info.setIsWarning(isWarning);
        info.setIsExemptible(canBeExempted);
        try {
            info = this.createInstruction(info.getProcessKey(), info.getCheckId(), info.getTypeKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }

    }

    private ProcessInfo _createProcess(String key, String name, String descr, ContextInfo context) {
        ProcessInfo info = new ProcessInfo();
        info.setKey(key);
        info.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(descr));
        try {
            info = this.createProcess(info.getKey(), info.getTypeKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return info;
    }

    private CheckInfo _createCheck(String checkId, String type, String issueId, String milestoneTypeKey,
            String ruleId,
            String processKey,
            String name,
            String descr,
            ContextInfo context) {
        String leftComparisonRuleId = "";
        String rightComparisonRuleId = "";
        return this._createCheck(checkId, type, issueId, milestoneTypeKey, ruleId, leftComparisonRuleId, rightComparisonRuleId,
                processKey,
                name, descr, context);
    }

    private CheckInfo _createCheck(String checkId,
            String type,
            String issueId,
            String milestoneTypeKey,
            String ruleId,
            String leftComparisonRuleId,
            String rightComparisonRuleId,
            String processKey,
            String name,
            String descr,
            ContextInfo context) {
        CheckInfo info = new CheckInfo();

        info.setId(checkId);
        info.setTypeKey(type);
        info.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ACTIVE);
        info.setName(name);
        info.setHoldIssueId(_toNull(issueId));
        info.setMilestoneTypeKey(_toNull(milestoneTypeKey));
        info.setRuleId(_toNull(ruleId));
        info.setLeftComparisonRuleId(_toNull(leftComparisonRuleId));
        info.setRightComparisonRuleId(_toNull(rightComparisonRuleId));
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
