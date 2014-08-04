/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.List;

public class FormatOfferingInfoWrapper {
    private String formatOfferingName;
    private String formatOfferingId;
    private boolean selected;
    private String termId;
    private String courseOfferingCode;
    private boolean validFormat;
    private boolean variableCredit;

    private List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers;

    private String activityFormatOrder;

    public FormatOfferingInfoWrapper(FormatOfferingInfo formatOfferingInfo, String courseOfferingCode) {
        formatOfferingName = formatOfferingInfo.getName();
        formatOfferingId = formatOfferingInfo.getId();
        termId = formatOfferingInfo.getTermId();
        this.courseOfferingCode = courseOfferingCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public List<ActivityFormatDetailsWrapper> getActivityFormatDetailsWrappers() {
        return activityFormatDetailsWrappers;
    }

    public void setActivityFormatDetailsWrappers(List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers) {
        this.activityFormatDetailsWrappers = activityFormatDetailsWrappers;
    }

    public boolean isValidFormat() {
        return validFormat;
    }

    public void setValidFormat(boolean validFormat) {
        this.validFormat = validFormat;
    }

    public boolean isVariableCredit() {
        return variableCredit;
    }

    public void setVariableCredit(boolean variableCredit) {
        this.variableCredit = variableCredit;
    }

    public String getActivityFormatOrder() {
        if(activityFormatOrder == null){
            StringBuilder sb = new StringBuilder();
            for(ActivityFormatDetailsWrapper wrapper : getActivityFormatDetailsWrappers()){
                if(sb.length()==0){
                    sb.append(wrapper.getFormatName());
                }else{
                    sb.append("->"+wrapper.getFormatName());
                }
            }
            activityFormatOrder=sb.toString();
        }
        return activityFormatOrder;
    }

    public void setActivityFormatOrder(String activityFormatOrder) {
        this.activityFormatOrder = activityFormatOrder;
    }
}
