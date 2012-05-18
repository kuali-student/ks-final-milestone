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
 * Created by mharmath on 5/17/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import java.util.ArrayList;
import java.util.List;
/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingInfoForm extends UifFormBase {

    private String id;
    private String formatName;
    private String formatDesc;
    private String courseOfferingId;
    private String formatId;
    private String termId;
    private List<String> activityOfferingTypeKeys;
    private String gradeRosterLevelTypeKey;
    private String finalExamLevelTypeKey;

    private FormatOfferingInfo formatOfferingInfo = new FormatOfferingInfo();
    
    public FormatOfferingInfoForm(FormatOfferingInfo info){
        super();
        formatOfferingInfo = info;
    }
    
    public FormatOfferingInfo getFormatOfferingInfo() {
        return formatOfferingInfo;
    }

    public void setFormatOfferingInfo(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormatName() {
            return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatDesc() {
        return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
        this.formatDesc = formatDesc;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public List<String> getActivityOfferingTypeKeys() {
        return activityOfferingTypeKeys;
    }

    public void setActivityOfferingTypeKeys(List<String> activityOfferingTypeKeys) {
        this.activityOfferingTypeKeys = activityOfferingTypeKeys;
    }

    public String getGradeRosterLevelTypeKey() {
        return gradeRosterLevelTypeKey;
    }

    public void setGradeRosterLevelTypeKey(String gradeRosterLevelTypeKey) {
        this.gradeRosterLevelTypeKey = gradeRosterLevelTypeKey;
    }

    public String getFinalExamLevelTypeKey() {
        return finalExamLevelTypeKey;
    }

    public void setFinalExamLevelTypeKey(String finalExamLevelTypeKey) {
        this.finalExamLevelTypeKey = finalExamLevelTypeKey;
    }

}

    

    
    
    
    
    
    
    
    
    
    
    


