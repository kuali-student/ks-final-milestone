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
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.io.Serializable;

/**
 * This class holds the properties needed for displaying the colocated activities
 *
 * @author Kuali Student Team
 */
public class ColocatedActivity implements Serializable {

    private String courseOfferingCode;
    private String activityOfferingCode;
    private String coId;
    private String aoId;
    private int maxEnrollmentCount;

//  TODOSSR  private ColocatedOfferingSetInfo coloSetInfo;
    private ActivityOfferingInfo activityOfferingInfo;

    private EditRenderHelper editRenderHelper;

    private boolean isAlreadyPersisted;

    public ColocatedActivity(){
        editRenderHelper = new EditRenderHelper();
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = StringUtils.upperCase(courseOfferingCode);
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = StringUtils.upperCase(activityOfferingCode);
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

    /**
     * @see #setMaxEnrollmentCount(int)
     * @return
     */
    @SuppressWarnings("unused")
    public int getMaxEnrollmentCount() {
        return maxEnrollmentCount;
    }

    /**
     * Sets the maximum enrollment count to be shared across all the colocated activities
     *
     * @param maxEnrollmentCount
     */
    public void setMaxEnrollmentCount(int maxEnrollmentCount) {
        this.maxEnrollmentCount = maxEnrollmentCount;
    }

    /**
     * Returns the render helper for Edit AO view
     * @return
     */
    public EditRenderHelper getEditRenderHelper() {
        return editRenderHelper;
    }

    /*TODOSSR public ColocatedOfferingSetInfo getColoSetInfo() {
        return coloSetInfo;
    }

    public void setColoSetInfo(ColocatedOfferingSetInfo coloSetInfo) {
        this.coloSetInfo = coloSetInfo;
    }*/

    public ActivityOfferingInfo getActivityOfferingInfo() {
        return activityOfferingInfo;
    }

    public void setActivityOfferingInfo(ActivityOfferingInfo activityOfferingInfo) {
        this.activityOfferingInfo = activityOfferingInfo;
    }

    public boolean isAlreadyPersisted() {
        return isAlreadyPersisted;
    }

    public void setAlreadyPersisted(boolean alreadyPersisted) {
        isAlreadyPersisted = alreadyPersisted;
    }

    /**
     * A helper class which holds all the extra properties needed in rendering the
     * colocated ativities in Edit AO view
     *
     */
    public class EditRenderHelper implements Serializable{

        private String termId;
        private boolean allowEnrollmentEdit;

        /**
         * Returns the Course Offering + Activity Offering Code for display purpose
         *
         * @return
         */
        public String getCode(){
            return getCourseOfferingCode() + " " + getActivityOfferingCode();
        }

        /**
         * @see #setTermId(String)
         * @return
         */
        public String getTermId(){
            return termId;
        }

        /**
         * Sets the term id for the current. This is needed to maintain seperately as we're having binding issues
         * to pass the term id to the Activity offerring lookup.
         *
         * @param termId
         */
        public void setTermId(String termId){
            this.termId = termId;
        }

        /**
         * @see #setAllowEnrollmentEdit(boolean)
         * @return
         */
        @SuppressWarnings("unused")
        public boolean isAllowEnrollmentEdit() {
            return allowEnrollmentEdit;
        }

        /**
         * This flag is to allow the user to enter the seat count for the current AO. (All the other AOs in the
         * cross list set are read only)
         *
         * @param allowEnrollmentEdit
         */
        public void setAllowEnrollmentEdit(boolean allowEnrollmentEdit) {
            this.allowEnrollmentEdit = allowEnrollmentEdit;
        }
    }

}
