package org.kuali.student.enrollment.acal;

import java.util.Date;

public class Milestone {

    private String id;
    private Date endDate;
    private boolean allDay;
    private boolean dateRange;
    private String name;
    private Date startDate;
    private String descriptionFormatted;
    private String descriptionPlain;
    private String type;
    private boolean relative;
    private String relativeMilestoneId;

    public Milestone() {
        
    }

    public Milestone(String id, String name, String type, String descriptionPlain, String descriptionFormatted, Date start, Date end, boolean allDay, boolean range) {
        this();
        setId(id);
        setName(name);
        setType(type);
        setDescriptionPlain(descriptionPlain);
        setDescriptionFormatted(descriptionFormatted);
        setStartDate(start);
        setEndDate(end);
        setAllDay(allDay);
        setDateRange(range);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public boolean isDateRange() {
        return dateRange;
    }

    public void setDateRange(boolean dateRange) {
        this.dateRange = dateRange;
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

    public boolean isRelative() {
        return relative;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    public String getRelativeMilestoneId() {
        return relativeMilestoneId;
    }

    public void setRelativeMilestoneId(String relativeMilestoneId) {
        this.relativeMilestoneId = relativeMilestoneId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id).append("\t");
        builder.append(name).append("\t");
        builder.append(type).append("\t");
        builder.append(descriptionPlain).append("\t");
        builder.append(descriptionFormatted).append("\t");
        builder.append(startDate).append("\t");
        builder.append(endDate).append("\t");
        builder.append(allDay).append("\t");
        builder.append(dateRange).append("\t");
        return builder.toString();
    }

}
