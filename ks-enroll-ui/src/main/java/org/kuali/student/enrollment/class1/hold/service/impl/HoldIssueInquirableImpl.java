package org.kuali.student.enrollment.class1.hold.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueHelper;

import java.util.Map;

/**
 * Created by SW Genis on 2014-09-29.
 */
public class HoldIssueInquirableImpl extends InquirableImpl {

    public final static String ID = "id";
    public final static String HOLD_ISSUE_ID = "holdIssue.id";

    @Override
    public HoldIssueMaintenanceWrapper retrieveDataObject(Map<String, String> parameters) {

        String holdIssueId;
        if (parameters.containsKey(ID)) {
            holdIssueId = parameters.get(ID);
        } else {
            holdIssueId = parameters.get(HOLD_ISSUE_ID);
        }

        try {
            return HoldIssueHelper.constructHoldIssueWrapper(holdIssueId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to construct Hold Issue wrapper for " + holdIssueId + " : " + e.getMessage(), e);
        }
    }

}
