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

    /**
     * The Registration course that added or edited in the original registration request.
     */
    private RegistrationCourse course;

    /**
     * The level can be SUCCESS, WARNING or ERROR depending on the registration request state.
     */
    private String level;

    /**
     * The origin registration request id.
     */
    private String originRegRequestId;

    /**
     * This list of registration results items.
     */
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

    public String getOriginRegRequestId() {
        return originRegRequestId;
    }

    public void setOriginRegRequestId(String originRegRequestId) {
        this.originRegRequestId = originRegRequestId;
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
