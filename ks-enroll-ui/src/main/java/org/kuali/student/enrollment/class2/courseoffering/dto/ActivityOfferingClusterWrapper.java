package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 9/11/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityOfferingClusterWrapper implements Serializable {
    private String activityOfferingClusterId;
    private ActivityOfferingClusterInfo aoCluster;
    private List<RegistrationGroupWrapper> rgWrapperList;
    private List<ActivityOfferingWrapper> aoWrapperList;
    private String rgStatus;
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
    }
    
    public ActivityOfferingClusterWrapper(String activityOfferingClusterId, List<RegistrationGroupWrapper> rgWrapperList){
        this.activityOfferingClusterId = activityOfferingClusterId;
        this.rgWrapperList = rgWrapperList;
        if (rgWrapperList.isEmpty()) {
            hasAllRegGroups = false;
            rgStatus = "No Registration Groups Generated";
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

    public boolean isHasAllRegGroups() {
        return hasAllRegGroups;
    }

    public void setHasAllRegGroups(boolean hasAllRegGroups) {
        this.hasAllRegGroups = hasAllRegGroups;
    }
}
