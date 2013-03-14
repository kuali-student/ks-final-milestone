package org.kuali.student.myplan.plan.dataobject;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * kmuthu Don't forget to add comment
 *
 * @Author kmuthu
 * Date: 3/4/13
 */
public class PlannedCourseSummary {

 // Plan related information
    private transient List<PlanItemDataObject> plannedList;
    private transient List<PlanItemDataObject> backupList;
    private List<AcademicRecordDataObject> acadRecList;
    private List<String> academicTerms;
    private transient String savedItemId;
    private String savedItemDateCreated;


   public List<PlanItemDataObject> getPlannedList() {
        if (plannedList == null) {
            plannedList = new ArrayList<PlanItemDataObject>();
        }
        return plannedList;
    }

    public void setPlannedList(List<PlanItemDataObject> plannedList) {
        this.plannedList = plannedList;
    }

    public List<PlanItemDataObject> getBackupList() {
        if (backupList == null) {
            backupList = new ArrayList<PlanItemDataObject>();
        }
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
        if (acadRecList == null) {
            acadRecList = new ArrayList<AcademicRecordDataObject>();
        }
        return acadRecList;
    }

   public void setAcadRecList(List<AcademicRecordDataObject> acadRecList) {
        this.acadRecList = acadRecList;
    }

    @JsonIgnore
    public boolean getInPlannedCourseList() {
        return (plannedList != null && plannedList.size() > 1) ? true : false;
    }

    @JsonIgnore
    public boolean getInSavedCourseList() {
        return (StringUtils.hasText(savedItemId)) ? true : false;
    }


    public List<String> getAcademicTerms() {
        if (academicTerms == null) {
            academicTerms = new ArrayList<String>();
        }
        return academicTerms;
    }

    public void setAcademicTerms(List<String> academicTerms) {
        this.academicTerms = academicTerms;
    }

}
