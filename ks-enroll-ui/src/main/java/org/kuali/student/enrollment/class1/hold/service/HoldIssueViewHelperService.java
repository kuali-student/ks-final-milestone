package org.kuali.student.enrollment.class1.hold.service;

import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public interface HoldIssueViewHelperService {


    public HoldIssueInfo createHoldIssue(HoldIssueInfo holdIssue);

    public HoldIssueInfo updateHoldIssue(HoldIssueInfo holdIssue);


    public  List<HoldIssueInfo> searchHolds(HoldIssueMaintenanceWrapper holdIssueWrapper);

}
