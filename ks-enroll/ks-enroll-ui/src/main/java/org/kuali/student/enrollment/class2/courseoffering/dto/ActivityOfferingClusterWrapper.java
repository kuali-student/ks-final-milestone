package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingClusterWrapper implements Serializable {
    public static final String RG_MESSAGE_PARTIAL = "uif-rg-message-partial";
    public static final String RG_MESSAGE_ALL = "uif-rg-message-all";
    public static final String RG_MESSAGE_NONE = "uif-rg-message-none";

    private String activityOfferingClusterId;
    private ActivityOfferingClusterInfo aoCluster;
    private List<RegistrationGroupWrapper> rgWrapperList;
    private List<ActivityOfferingWrapper> aoWrapperList;
    private String rgStatus = "";
    private String rgMessageStyle = "";
    private String clusterNameForDisplay = "";
    private String formatNameForDisplay ="";
    private String requisite;
    private String commonRequisite;

    private String formatOfferingId;

    // notes by Bonnie
    // TODO: the following boolean should be removed after migrate to ARG
    /**
     * hasAllRegGroups=true means all RGs have been generated for
     * a group of AOs within a cluster and rgStatus="All Registration Groups Generated"
     * hasAllRegGroups=false means no RGs has been generated or
     * only some RGs are generated.
     * rgStatus = "No Registration Group Generated" or
     * rgStatus = "Only some Registration Groups Generated"
     * when  hasAllRegGroups=true, show "View Registration Groups" link
     * when  hasAllRegGroups=false, show "Generate Registration Groups" link
     */
    private boolean hasAllRegGroups;

    public ActivityOfferingClusterWrapper() {
        rgWrapperList = new ArrayList<RegistrationGroupWrapper>();
        aoWrapperList = new ArrayList<ActivityOfferingWrapper>();
        hasAllRegGroups = false;
        rgStatus = "No Registration Groups Generated";
        rgMessageStyle = RG_MESSAGE_NONE;
        clusterNameForDisplay = "";
    }
    
    public ActivityOfferingClusterWrapper(String activityOfferingClusterId, List<RegistrationGroupWrapper> rgWrapperList){
        this.activityOfferingClusterId = activityOfferingClusterId;
        this.rgWrapperList = rgWrapperList;
        if (rgWrapperList.isEmpty()) {
            hasAllRegGroups = false;
            rgStatus = "No Registration Groups Generated";
            rgMessageStyle = RG_MESSAGE_NONE;
        }
    }

    public String getActivityOfferingClusterId() {
        return activityOfferingClusterId;
    }

    public void setActivityOfferingClusterId(String activityOfferingClusterId) {
        this.activityOfferingClusterId = activityOfferingClusterId;
    }

    public ActivityOfferingClusterInfo getAoCluster() {
        return aoCluster;
    }

    public void setAoCluster(ActivityOfferingClusterInfo aoCluster) {
        this.aoCluster = aoCluster;
    }

    public List<RegistrationGroupWrapper> getRgWrapperList() {
        return rgWrapperList;
    }

    public void setRgWrapperList(List<RegistrationGroupWrapper> rgWrapperList) {
        this.rgWrapperList = rgWrapperList;
        if (rgWrapperList.isEmpty()) {
            hasAllRegGroups = false;
            rgStatus = "No Registration Groups Generated";
            rgMessageStyle = RG_MESSAGE_NONE;
        }
    }

    public List<ActivityOfferingWrapper> getAoWrapperList() {
        return aoWrapperList;
    }

    public void setAoWrapperList(List<ActivityOfferingWrapper> aoWrapperList) {
        this.aoWrapperList = aoWrapperList;
    }

    public String getRgStatus() {
        return rgStatus;
    }

    public void setRgStatus(String rgStatus) {
        this.rgStatus = rgStatus;
    }

    public String getRgMessageStyle() {
        return rgMessageStyle;
    }

    public void setRgMessageStyle(String rgMessageStyle) {
        this.rgMessageStyle = rgMessageStyle;
    }

    public boolean isHasAllRegGroups() {
        return hasAllRegGroups;
    }

    public void setHasAllRegGroups(boolean hasAllRegGroups) {
        this.hasAllRegGroups = hasAllRegGroups;
    }

    public String getClusterNameForDisplay() {
        return clusterNameForDisplay;
    }

    public void setClusterNameForDisplay(String clusterNameForDisplay) {
        if(aoCluster == null){
            this.clusterNameForDisplay = clusterNameForDisplay;
        }
        else {
            String pubName=aoCluster.getName();
            if (pubName != null && !pubName.isEmpty()) {
                this.clusterNameForDisplay = "Cluster: "+aoCluster.getPrivateName()+" ("+pubName+")";
            }
            else{
                this.clusterNameForDisplay = "Cluster: "+aoCluster.getPrivateName();
            }
        }
    }

    public String getRequisite(){
        return requisite;
    }

    public void setRequisite(String requisite){
        this.requisite = requisite;
    }

    public String getCommonRequisite(){
        return commonRequisite;
    }

    public void setCommonRequisite(String commonRequisite){
        this.commonRequisite = commonRequisite;
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
}
