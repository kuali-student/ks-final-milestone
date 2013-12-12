package org.kuali.student.poc.rules.credit.limit;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;

/**
 * This represents a transaction that needs to be applied to a course registration and it's associated activity registrations.
 *
 * This represents the class II view of what happens when you merge a reg request with existing course registrations.
 *
 * Later these need to be de-blocked into their associated LPRs.
 */
public class CourseRegistrationTransaction {

    private ActionEnum action;
    private CourseRegistrationInfo registration;
    private List<ActivityRegistrationTransaction> activityRegistrationTransactions;

    public CourseRegistrationTransaction() {
    }

    public CourseRegistrationTransaction(ActionEnum action, 
            CourseRegistrationInfo registration,
            List<ActivityRegistrationTransaction> activityTrans) {
        this.action = action;
        this.registration = registration;
        this.activityRegistrationTransactions = activityTrans;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public CourseRegistrationInfo getRegistration() {
        return registration;
    }

    public void setRegistration(CourseRegistrationInfo registration) {
        this.registration = registration;
    }

    public List<ActivityRegistrationTransaction> getActivityRegistrationTransactions() {
        if (this.activityRegistrationTransactions == null) {
            this.activityRegistrationTransactions = new ArrayList<ActivityRegistrationTransaction>();
        }
        return activityRegistrationTransactions;
    }

    public void setActivityRegistrationTransactions(List<ActivityRegistrationTransaction> activityRegistrationTransactions) {
        this.activityRegistrationTransactions = activityRegistrationTransactions;
    }

    @Override
    public String toString() {
        return "CourseRegistrationTransaction{" + "action=" + action + ", registration=" + registration + ", activityRegistrationTransactions=" + activityRegistrationTransactions + '}';
    }
}
