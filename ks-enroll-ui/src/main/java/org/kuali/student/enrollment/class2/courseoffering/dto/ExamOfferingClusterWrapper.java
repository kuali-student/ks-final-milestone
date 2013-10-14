package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamOfferingClusterWrapper implements Serializable {
    private String activityOfferingClusterId;
    private String clusterNameForDisplay = "";
    private String formatNameForDisplay ="";
    private String formatOfferingId;
    private List<ExamOfferingWrapper> eoWrapperList;


    public ExamOfferingClusterWrapper() {
        clusterNameForDisplay = "";
        eoWrapperList = new ArrayList<ExamOfferingWrapper>();
    }

    public ExamOfferingClusterWrapper(String activityOfferingClusterId, List<RegistrationGroupWrapper> rgWrapperList){
        this.activityOfferingClusterId = activityOfferingClusterId;
    }

    public String getActivityOfferingClusterId() {
        return activityOfferingClusterId;
    }

    public void setActivityOfferingClusterId(String activityOfferingClusterId) {
        this.activityOfferingClusterId = activityOfferingClusterId;
    }

    public String getClusterNameForDisplay() {
        return clusterNameForDisplay;
    }

    public void setClusterNameForDisplay(String clusterNameForDisplay) {
            this.clusterNameForDisplay = clusterNameForDisplay;
    }

    public String getFormatNameForDisplay() {
        return formatNameForDisplay;
    }

    public void setFormatNameForDisplay(String formatNameForDisplay) {
        this.formatNameForDisplay = formatNameForDisplay;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public List<ExamOfferingWrapper> getEoWrapperList() {
        return eoWrapperList;
    }

    public void setEoWrapperList(List<ExamOfferingWrapper> eoWrapperList) {
        this.eoWrapperList = eoWrapperList;
    }

}
