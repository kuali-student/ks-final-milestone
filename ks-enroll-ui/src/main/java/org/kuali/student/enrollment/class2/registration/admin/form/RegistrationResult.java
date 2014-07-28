package org.kuali.student.enrollment.class2.registration.admin.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public class RegistrationResult {

    private RegistrationCourse course;
    private String level;
    private List<RegistrationResultItem> items = new ArrayList<RegistrationResultItem>();

    public RegistrationCourse getCourse() {
        return course;
    }

    public void setCourse(RegistrationCourse course) {
        this.course = course;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RegistrationResultItem> getItems() {
        return items;
    }

    public void setItems(List<RegistrationResultItem> items) {
        this.items = items;
    }

    public List<String> getItemDescriptions(){
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationResultItem item: items) {
            list.add(item.getDescription());
        }

        return list;
    }

}
