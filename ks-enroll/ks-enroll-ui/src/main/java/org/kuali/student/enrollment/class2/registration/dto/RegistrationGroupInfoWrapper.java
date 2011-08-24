package org.kuali.student.enrollment.class2.registration.dto;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistrationGroupInfoWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private RegistrationGroupInfo registrationGroupInfo;

    private List<ActivityOfferingInfo> activityOfferingInfos;

    public RegistrationGroupInfoWrapper() {
        activityOfferingInfos = new ArrayList<ActivityOfferingInfo>();
    }

    public RegistrationGroupInfo getRegistrationGroupInfo() {
        return registrationGroupInfo;
    }

    public void setRegistrationGroupInfo(RegistrationGroupInfo registrationGroupInfo) {
        this.registrationGroupInfo = registrationGroupInfo;
    }

    public List<ActivityOfferingInfo> getActivityOfferingInfos() {
        return activityOfferingInfos;
    }

    public void setActivityOfferingInfos(List<ActivityOfferingInfo> activityOfferingInfos) {
        this.activityOfferingInfos = activityOfferingInfos;
    }
}
