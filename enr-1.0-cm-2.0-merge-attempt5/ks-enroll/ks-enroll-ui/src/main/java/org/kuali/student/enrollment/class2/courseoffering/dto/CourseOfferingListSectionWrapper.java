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
 * Created by vgadiyak on 5/25/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;

/**
 * This class is a wrapper object used in the manage course offering page. It is meant to be small and only contain
 * items needed for the search results row.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingListSectionWrapper implements Serializable{

    String courseOfferingId;
    String courseOfferingCode;
    String courseOfferingDesc;
    String courseOfferingStateKey;
    String courseOfferingStateDisplay;
    String courseOfferingCreditOptionKey;
    String courseOfferingGradingOptionKey;
    String courseOfferingCreditOptionDisplay;
    String courseOfferingGradingOptionDisplay;
    String subjectArea;


    boolean isLegalToDelete = true;
    boolean isChecked = false;

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }


    public boolean isLegalToDelete() {

        if (this.getCourseOfferingStateKey() != null &&
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_DRAFT_STATE_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)) {
            return true;
        }

        return false;
    }

    public void setLegalToDelete(boolean legalToDelete) {
        isLegalToDelete = legalToDelete;
    }



    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingCreditOptionKey() {
        return courseOfferingCreditOptionKey;
    }

    public void setCourseOfferingCreditOptionKey(String courseOfferingCreditOptionKey) {
        this.courseOfferingCreditOptionKey = courseOfferingCreditOptionKey;
    }

    public String getCourseOfferingDesc() {
        return courseOfferingDesc;
    }

    public void setCourseOfferingDesc(String courseOfferingDesc) {
        this.courseOfferingDesc = courseOfferingDesc;
    }

    public String getCourseOfferingGradingOptionKey() {
        return courseOfferingGradingOptionKey;
    }

    public void setCourseOfferingGradingOptionKey(String courseOfferingGradingOptionKey) {
        this.courseOfferingGradingOptionKey = courseOfferingGradingOptionKey;
    }

    public String getCourseOfferingStateKey() {
        return courseOfferingStateKey;
    }

    public void setCourseOfferingStateKey(String courseOfferingStateKey) {
        this.courseOfferingStateKey = courseOfferingStateKey;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getCourseOfferingStateDisplay() {
        return courseOfferingStateDisplay;
    }

    public void setCourseOfferingStateDisplay(String courseOfferingStateDisplay) {
        this.courseOfferingStateDisplay = courseOfferingStateDisplay;
    }

    public String getCourseOfferingCreditOptionDisplay() {
        return courseOfferingCreditOptionDisplay;
    }

    public void setCourseOfferingCreditOptionDisplay(String courseOfferingCreditOptionDisplay) {
        this.courseOfferingCreditOptionDisplay = courseOfferingCreditOptionDisplay;
    }

    public String getCourseOfferingGradingOptionDisplay() {
        return courseOfferingGradingOptionDisplay;
    }

    public void setCourseOfferingGradingOptionDisplay(String courseOfferingGradingOptionDisplay) {
        this.courseOfferingGradingOptionDisplay = courseOfferingGradingOptionDisplay;
    }
}

