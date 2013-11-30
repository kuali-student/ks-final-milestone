package org.kuali.student.poc.rules.credit.limit;

import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class ActivityRegistrationTransaction {

    public enum Action {

        NO_CHANGE, CREATE, UPDATE, DELETE
    };
    private Action action;
    private ActivityRegistrationInfo registration;

    public ActivityRegistrationTransaction() {
    }

    public ActivityRegistrationTransaction(Action action, ActivityRegistrationInfo registration) {
        this.action = action;
        this.registration = registration;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ActivityRegistrationInfo getRegistration() {
        return registration;
    }

    public void setRegistration(ActivityRegistrationInfo registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "ActivityRegistrationTransaction{" + "action=" + action + ", registration=" + registration + '}';
    }

    
}
