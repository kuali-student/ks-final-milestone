package org.kuali.student.myplan.course.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hsqldb.lib.StringUtil;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.springframework.util.StringUtils;

/**
 * Course Details
 */
public class CourseDetails extends CourseSummaryDetails {

    private boolean summaryOnly;

    private String curriculumTitle;
    private String lastOffered;

    private List<String> campusLocations;
    private List<String> scheduledTerms;
    private List<String> requisites;
    private List<String> genEdRequirements;
    private List<String> abbrGenEdRequirements;

    // Plan related information
    private transient List<PlanItemDataObject> plannedList;
    private transient List<PlanItemDataObject> backupList;
    private List<AcademicRecordDataObject> acadRecList;
    private List<String> academicTerms = new ArrayList<String>();
    private transient String savedItemId;
    private String savedItemDateCreated;


    public String getLastOffered() {
        return lastOffered;
    }

    public void setLastOffered(String lastOffered) {
        this.lastOffered = lastOffered;
    }

    public CourseDetails() {
        genEdRequirements = new ArrayList<String>();
        requisites = new ArrayList<String>();
    }

    public String getCurriculumTitle() {
        return curriculumTitle;
    }

    public void setCurriculumTitle(String curriculumTitle) {
        this.curriculumTitle = curriculumTitle;
    }


    public List<String> getGenEdRequirements() {
        return genEdRequirements;
    }

    public void setGenEdRequirements(List<String> genEdRequirements) {
        this.genEdRequirements = genEdRequirements;
    }

    public List<String> getAbbrGenEdRequirements() {
        return abbrGenEdRequirements;
    }

    public void setAbbrGenEdRequirements(List<String> abbrGenEdRequirements) {
        this.abbrGenEdRequirements = abbrGenEdRequirements;
    }

    public List<String> getRequisites() {
        return requisites;
    }

    public void setRequisites(List<String> requisites) {
        this.requisites = requisites;
    }

    public List<String> getCampusLocations() {
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    public List<String> getScheduledTerms() {
        return scheduledTerms;
    }

    public void setScheduledTerms(List<String> scheduledTerms) {
        this.scheduledTerms = scheduledTerms;
    }

    public List<PlanItemDataObject> getPlannedList() {
        return plannedList;
    }

    public void setPlannedList(List<PlanItemDataObject> plannedList) {
        this.plannedList = plannedList;
    }

    public List<PlanItemDataObject> getBackupList() {
        return backupList;
    }

    public void setBackupList(List<PlanItemDataObject> backupList) {
        this.backupList = backupList;
    }

    public String getSavedItemId() {
        return savedItemId;
    }

    public void setSavedItemId(String savedItemId) {
        this.savedItemId = savedItemId;
    }

    public String getSavedItemDateCreated() {
        return savedItemDateCreated;
    }

    public void setSavedItemDateCreated(String savedItemDateCreated) {
        this.savedItemDateCreated = savedItemDateCreated;
    }

    public List<AcademicRecordDataObject> getAcadRecList() {
        return acadRecList;
    }

    public void setAcadRecList(List<AcademicRecordDataObject> acadRecList) {
        this.acadRecList = acadRecList;
    }

    public boolean isSummaryOnly() {
        return summaryOnly;
    }

    public void setSummaryOnly(boolean summaryOnly) {
        this.summaryOnly = summaryOnly;
    }

    @JsonIgnore
    public boolean getInPlannedCourseList() {
        if (isSummaryOnly()) {
            throw new IllegalArgumentException("Planned course check performed on Course Summary");
        }

        return (plannedList != null && plannedList.size() > 1) ? true : false;
    }

    @JsonIgnore
    public boolean getInSavedCourseList() {
        if (isSummaryOnly()) {
            throw new IllegalArgumentException("Saved course check performed on Course Summary");
        }

        return (StringUtils.hasText(savedItemId)) ? true : false;
    }


    public List<String> getAcademicTerms() {
        return academicTerms;
    }

    public void setAcademicTerms(List<String> academicTerms) {
        this.academicTerms = academicTerms;
    }


    //TODO: Review why we really need this
    //  It's because we need access to more than on property in one of the property editors.
    @JsonIgnore
    public CourseDetails getThis() {
        return this;
    }

    //  Using this as a property for the crudMessageMatrixFormatter property editor
    // because getThis() is already used for the timeschedule property editor
    // so it overides crudmessage with timeschedule if we use  the same property .
    @JsonIgnore
    public CourseDetails getDetails() {
        return this;
    }


}