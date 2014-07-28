package org.kuali.student.enrollment.class2.registration.admin.form;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public class RegistrationResultItem {

    private String description;

    public RegistrationResultItem(String description) {
        if(description==null){
            throw new IllegalArgumentException("Description can not be null.");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
