package org.kuali.student.ap.audit.service.model;

import java.util.ArrayList;

public class Section {
    public String caption = null;

    public String getCaption() { return caption; }

    public ArrayList<Requirement> requirementList = new ArrayList<Requirement>();

    public void addRequirement(Requirement requirement) {
        requirementList.add(requirement);
    }

    public ArrayList<Requirement> getRequirementList() {
        return requirementList;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean hasCaption() { return caption != null; }
}
