/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by vgadiyak on 9/14/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;

import java.util.ArrayList;
import java.util.List;

/* TODO : This class needs refactoring; should inherit from CourseOfferingWrapper; see https://jira.kuali.org/browse/KSENROLL-5931
 */

/**
 * This class defines a wrapper for Course Offering Display data
 *
 * @author Kuali Student Team
 */
public class CourseOfferingDisplayWrapper extends CourseOfferingListSectionWrapper {

    private String information;
    private String requisites;
    private List<ActivityOfferingClusterWrapper> aoClusterWrapperList;
    private List<ActivityOfferingWrapper> activityWrapperList;

    public CourseOfferingDisplayWrapper(){
        aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        activityWrapperList = new ArrayList<ActivityOfferingWrapper>();
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getRequisites() {
        return requisites;
    }

    public void setRequisites(String requisites) {
        this.requisites = requisites;
    }

    public List<ActivityOfferingWrapper> getActivityWrapperList() {
        return activityWrapperList;
    }

    public void setActivityWrapperList(List<ActivityOfferingWrapper> activityWrapperList) {
        this.activityWrapperList = activityWrapperList;
    }

    public List<ActivityOfferingClusterWrapper> getClusterResultList() {
        return aoClusterWrapperList;
    }

    public void setClusterResultList(List<ActivityOfferingClusterWrapper> clusterResultList) {
        this.aoClusterWrapperList = clusterResultList;
    }

}
