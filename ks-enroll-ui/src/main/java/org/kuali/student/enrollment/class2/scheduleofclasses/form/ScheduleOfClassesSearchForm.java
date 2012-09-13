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
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesSearchForm extends UifFormBase {

    private String termCode;
    private String searchType;
    private String course;
    private String department;
    private String instructor;
    private String titleDesc;
    private String information;

    private List<CourseOfferingDisplayInfo> coDisplayInfoList; //The core info (name+description+meta)
    private String termName;

    public ScheduleOfClassesSearchForm (){
        coDisplayInfoList = new ArrayList<CourseOfferingDisplayInfo>();
    }

    public List<CourseOfferingDisplayInfo> getCoDisplayInfoList() {
        return coDisplayInfoList;
    }

    public void setCoDisplayInfoList(List<CourseOfferingDisplayInfo> coDisplayInfoList) {
        this.coDisplayInfoList = coDisplayInfoList;
    }

    public String getTermCode(){
        return termCode;
    }

    public void setTermCode(String termCode){
        this.termCode = termCode;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTermName(){
        return termName;
    }

    public void setTermName(String termName){
        this.termName = termName;
    }
}