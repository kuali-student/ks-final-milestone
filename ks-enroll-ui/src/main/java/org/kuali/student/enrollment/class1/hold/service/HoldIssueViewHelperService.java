package org.kuali.student.enrollment.class1.hold.service;

import org.kuali.student.enrollment.class1.hold.dto.HoldIssueInfoWrapper;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueManagementForm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public interface HoldIssueViewHelperService {

    public  List<HoldIssueInfoWrapper> searchHolds(HoldIssueManagementForm holdIssueForm);


}
