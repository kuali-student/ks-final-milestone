package org.kuali.student.enrollment.class2.registration.admin.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public class RegistrationIssue {

    private RegistrationCourse course;
    private List<RegistrationIssueItem> items = new ArrayList<RegistrationIssueItem>();

    public RegistrationCourse getCourse() {
        return course;
    }

    public void setCourse(RegistrationCourse course) {
        this.course = course;
    }

    public List<RegistrationIssueItem> getItems() {
        return items;
    }

    public void setItems(List<RegistrationIssueItem> items) {
        this.items = items;
    }
}
