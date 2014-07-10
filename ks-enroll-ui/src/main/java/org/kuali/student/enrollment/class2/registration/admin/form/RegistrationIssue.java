package org.kuali.student.enrollment.class2.registration.admin.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SW Genis on 2014/06/19.
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
