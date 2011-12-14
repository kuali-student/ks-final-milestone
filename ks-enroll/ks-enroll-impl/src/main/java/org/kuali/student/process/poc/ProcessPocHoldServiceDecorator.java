/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import java.util.Date;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

/**
 *
 * @author nwright
 */
public class ProcessPocHoldServiceDecorator extends HoldServiceDecorator {

    public ProcessPocHoldServiceDecorator(HoldService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        initializeData();
    }

    private void initializeData() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        IssueInfo unpaidTuitionIssue = new IssueInfo();
        unpaidTuitionIssue.setName("Unpaid tuition from last term");
        unpaidTuitionIssue.setTypeKey(HoldServiceConstants.FINANCIAL_ISSUE_TYPE_KEY);
        unpaidTuitionIssue.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        try {
            unpaidTuitionIssue = this.createIssue(unpaidTuitionIssue, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }

        IssueInfo overdueBookIssue = new IssueInfo();
        overdueBookIssue.setName("Overdue Library Issue");
        overdueBookIssue.setTypeKey(HoldServiceConstants.OVERDUE_LIBRARY_MATERIALS_ISSUE_TYPE_KEY);
        overdueBookIssue.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        try {
            overdueBookIssue = this.createIssue(overdueBookIssue, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }


        HoldInfo hold = new HoldInfo();
        hold.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        hold.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        hold.setName(unpaidTuitionIssue.getName());
        hold.setIsOverridable(true);
        hold.setIsWarning(false);
        hold.setEffectiveDate(new Date());
        hold.setIssueKey(unpaidTuitionIssue.getKey());
        hold.setPersonId(ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397);
        try {
            this.createHold(hold, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }
        hold.setPersonId(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166);
        try {
            this.createHold(hold, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }


        hold.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        hold.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        hold.setName(overdueBookIssue.getName());
        hold.setIsOverridable(true);
        hold.setIsWarning(false);
        hold.setEffectiveDate(new Date());
        hold.setIssueKey(overdueBookIssue.getKey());
        hold.setPersonId(ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005);
        try {
            this.createHold(hold, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }
        hold.setPersonId(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166);
        try {
            this.createHold(hold, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }

    }
}
