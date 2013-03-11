/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by venkat on 3/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ColocatedActivity implements Serializable {

    private String courseOfferingCode;
    private String activityOfferingCode;
    private String coId;
    private String aoId;
    private int maxEnrollmentCount;

    private EditRenderHelper editRenderHelper;

    public ColocatedActivity(){
        editRenderHelper = new EditRenderHelper();
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getAoId() {
        return aoId;
    }

    public void setAoId(String aoId) {
        this.aoId = aoId;
    }

    @SuppressWarnings("unused")
    public int getMaxEnrollmentCount() {
        return maxEnrollmentCount;
    }

    public void setMaxEnrollmentCount(int maxEnrollmentCount) {
        this.maxEnrollmentCount = maxEnrollmentCount;
    }

    public EditRenderHelper getEditRenderHelper() {
        return editRenderHelper;
    }

    public class EditRenderHelper implements Serializable{

        private TermInfo termInfo;
        private boolean allowEnrollmentEdit;

        public String getCode(){
            return getCourseOfferingCode() + " " + getActivityOfferingCode();
        }

        public String getTermId(){
            if (termInfo != null){
                return termInfo.getId();
            }
            return StringUtils.EMPTY;
        }

        public void setTermInfo(TermInfo termInfo){
            this.termInfo = termInfo;
        }

        public boolean isAllowEnrollmentEdit() {
            return allowEnrollmentEdit;
        }

        public void setAllowEnrollmentEdit(boolean allowEnrollmentEdit) {
            this.allowEnrollmentEdit = allowEnrollmentEdit;
        }
    }

}
