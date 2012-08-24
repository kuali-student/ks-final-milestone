package org.kuali.student.enrollment.acal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Atp {
    private List<Atp> terms = new ArrayList<Atp>();
    private List<Atp> holidayCalendars = new ArrayList<Atp>();
    private List<Milestone> milestones = new ArrayList<Milestone>();

    private String id;
    private String name;
    private String type;
    private String adminOrgId;
    private String descriptionPlain;
    private String descriptionFormatted;
    private Date startDate;
    private Date endDate;
    private String atpCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Atp> getTerms() {
        return terms;
    }

    public void setTerms(List<Atp> terms) {
        this.terms = terms;
    }

    public List<Atp> getHolidayCalendars() {
        return holidayCalendars;
    }

    public void setHolidayCalendars(List<Atp> holidayCalendars) {
        this.holidayCalendars = holidayCalendars;
    }

    public void addMilestone(Milestone milestone) {
        if (milestone != null) {
            this.milestones.add(milestone);
        }
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAdminOrgId() {
        return adminOrgId;
    }

    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
    }

    public String getDescriptionFormatted() {
        return descriptionFormatted;
    }

    public void setDescriptionFormatted(String descriptionFormatted) {
        this.descriptionFormatted = descriptionFormatted;
    }

    public String getDescriptionPlain() {
        return descriptionPlain;
    }

    public void setDescriptionPlain(String descriptionPlain) {
        this.descriptionPlain = descriptionPlain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAtpCode() {
        return atpCode;
    }

    public void setAtpCode(String atpCode) {
        this.atpCode = atpCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append("\t");
        builder.append(name).append("\t");
        builder.append(type).append("\t");
        builder.append(adminOrgId).append("\t");
        builder.append(descriptionPlain).append("\t");
        builder.append(descriptionFormatted).append("\t");
        builder.append(startDate).append("\t");
        builder.append(endDate).append("\t");
        builder.append("\n");

        for (Milestone milestone : milestones) {
            builder.append(milestone);
            builder.append("\n");
        }

        for (Atp atp : terms) {
            builder.append(atp);
        }

        return builder.toString();
    }
}
