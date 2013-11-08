package org.kuali.student.poc.rules.credit.limit;

import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class CourseRegistrationAction {

    public enum Action {

        NO_CHANGE, CREATE, UPDATE
    };
    private Action action;
    private CourseRegistrationInfo registration;

    public CourseRegistrationAction() {
    }

    public CourseRegistrationAction(Action action, CourseRegistrationInfo registration) {
        this.action = action;
        this.registration = registration;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public CourseRegistrationInfo getRegistration() {
        return registration;
    }

    public void setRegistration(CourseRegistrationInfo registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "CourseRegistrationAction{" + "action=" + action + ", registration=" + registration + '}';
    }
}
