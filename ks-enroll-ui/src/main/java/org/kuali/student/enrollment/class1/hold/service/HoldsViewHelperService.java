package org.kuali.student.enrollment.class1.hold.service;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldManagementForm;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldResult;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueManagementForm;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public interface HoldsViewHelperService {

    public  List<HoldIssueResult> searchHolds(HoldIssueManagementForm holdIssueForm);

    public PersonInfo getStudentById(String studentId);

    public List<AppliedHoldResult> searchAppliedHolds(AppliedHoldManagementForm holdFrom);

}
