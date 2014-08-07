package org.kuali.student.enrollment.class1.hold.service.impl;

import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.hold.service.HoldIssueViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueResourceLoader;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * <p/>
 * Implementation of the HoldIssueViewHelperService that contains helper methods that support the Hold Issue Maintenance Controller.
 */
public class HoldIssueViewHelperServiceImpl extends KSViewHelperServiceImpl implements HoldIssueViewHelperService {

    public HoldIssueInfo createHoldIssue(HoldIssueInfo holdIssue){
        HoldIssueInfo createHoldIssueInfo = null;
        try {
            createHoldIssueInfo = HoldIssueResourceLoader.getHoldService().createHoldIssue(holdIssue.getTypeKey(), holdIssue, createContextInfo());
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return createHoldIssueInfo;
    }
}
