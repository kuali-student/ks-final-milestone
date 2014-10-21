/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestHoldServiceDataLoadingDecorator extends HoldServiceDecorator {

    public ProcessIntegrationTestHoldServiceDataLoadingDecorator() {
    }

    
    public ProcessIntegrationTestHoldServiceDataLoadingDecorator(HoldService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        init();
    }

    public void init() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("Test-Initializer");

        HoldIssueInfo unpaidTuitionIssue = _createIssue(HoldServiceConstants.ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM,
                                                    "Unpaid tuition from last term", HoldServiceConstants.HOLD_ISSUE_FINANCIAL_TYPE_KEY, context);
        
        
        HoldIssueInfo overdueBookIssue = _createIssue(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE,
                                                  "Overdue Library Book", HoldServiceConstants.HOLD_ISSUE_STUDENT_RECORD_TYPE_KEY, context);
        
        this._createHold(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272, unpaidTuitionIssue, context);
        this._createHold(ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397, unpaidTuitionIssue, context);
        this._createHold(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, unpaidTuitionIssue, context);
        this._createHold(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005, overdueBookIssue, context);
        this._createHold(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, overdueBookIssue, context);
    }

    private HoldIssueInfo _createIssue(String key, String name, String type, ContextInfo context) {
        HoldIssueInfo issue = new HoldIssueInfo();
        issue.setId(key);
        issue.setName(name);
        issue.setTypeKey(type);
        issue.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        issue.setIsHoldIssueTermBased(false);
        try {
            issue = this.createHoldIssue(issue.getTypeKey(), issue, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }
        return issue;
    }

    private AppliedHoldInfo _createHold(String personId, HoldIssueInfo issue, ContextInfo context)  {
        AppliedHoldInfo hold = new AppliedHoldInfo();
        hold.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        hold.setStateKey(HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY);
        hold.setHoldIssueId(issue.getId());
        hold.setName(issue.getName());
        Date effDate = null;
        try {
            effDate = new SimpleDateFormat ("yyyy-MM-dd").parse ("2011-01-01");
        } catch (ParseException ex) {
            throw new RuntimeException (ex);
        }
        hold.setEffectiveDate(effDate);
        hold.setPersonId(personId);
        try {
            hold = this.createAppliedHold(hold.getPersonId(), hold.getHoldIssueId(), hold.getTypeKey(), hold, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }
        return hold;
    }
}
