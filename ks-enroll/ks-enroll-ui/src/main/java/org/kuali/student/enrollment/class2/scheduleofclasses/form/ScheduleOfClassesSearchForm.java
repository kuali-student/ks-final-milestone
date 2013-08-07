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
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
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
    private String departmentName;
    private String instructor;
    private String instructorName;
    private String titleDesc;
    private String information;

    private List<CourseOfferingDisplayWrapper> coDisplayWrapperList; //The core info (name+description+meta)
    private String termName;
    private String searchParameter;

    // For AJAX purpose
    private String courseOfferingId;
    private List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList;
    private List<ActivityOfferingDisplayWrapper> aoDisplayWrapperAddList;
    // Temporal solution to display 2 AO lists simultaneously.
    private String displayCoId;
    private String displayCoIdAdd;

    public ScheduleOfClassesSearchForm (){
        coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
        aoDisplayWrapperList = new ArrayList<ActivityOfferingDisplayWrapper>();
        aoDisplayWrapperAddList = new ArrayList<ActivityOfferingDisplayWrapper>();
        courseOfferingId = "";
    }

    public List<CourseOfferingDisplayWrapper> getCoDisplayWrapperList() {
        return coDisplayWrapperList;
    }

    public void setCoDisplayWrapperList(List<CourseOfferingDisplayWrapper> coDisplayWrapperList) {
        this.coDisplayWrapperList = coDisplayWrapperList;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
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

    public String getSearchParameter(){
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter){
        this.searchParameter = searchParameter;
    }

    public String getCourseOfferingId(){
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId){
        this.courseOfferingId = courseOfferingId;
    }

    public List<ActivityOfferingDisplayWrapper> getAoDisplayWrapperList() {
        return aoDisplayWrapperList;
    }

    public void setAoDisplayWrapperList(List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList) {
        this.aoDisplayWrapperList = aoDisplayWrapperList;
    }

    public List<ActivityOfferingDisplayWrapper> getAoDisplayWrapperAddList() {
        return aoDisplayWrapperAddList;
    }

    public void setAoDisplayWrapperAddList(List<ActivityOfferingDisplayWrapper> aoDisplayWrapperAddList) {
        this.aoDisplayWrapperAddList = aoDisplayWrapperAddList;
    }

    public String getDisplayCoId() {
        return displayCoId;
    }

    public void setDisplayCoId(String displayCoId) {
        this.displayCoId = displayCoId;
    }

    public String getDisplayCoIdAdd() {
        return displayCoIdAdd;
    }

    public void setDisplayCoIdAdd(String displayCoIdAdd) {
        this.displayCoIdAdd = displayCoIdAdd;
    }
}