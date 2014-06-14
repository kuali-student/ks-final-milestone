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

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a form for the Schedule of Classes ui
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesSearchForm extends UifFormBase implements ActivityOfferingDisplayUI, CourseOfferingDisplayUI {

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
    private List<ActivityOfferingWrapper> aoWrapperList;
    private List<ActivityOfferingClusterWrapper> aoClusterWrapperList;
    private List<RegistrationGroupWrapper> rgResultList;
    private Map<String,FormatOfferingInfo> foId2aoTypeMap;
    private boolean hasMoreThanOneCluster = false;
    private String displayCoId;
    private String displayCoIdAdd;
    private AoDisplayFormat aoDisplayFormat;
    private boolean allowSelectableAoRendering;

//    /**
//     * This is used to display CO search result list
//     */
//    private List<CourseOfferingListSectionWrapper> courseOfferingResultList;

    /**
     * Valid display types of activities in schedule of classes.
     *
     * The application supports three display types.
     * 1. Display a flat list of activities for each CourseOffering
     * 2. Display activities grouped by activity clusters
     * 3. Display registration group and activities grouped by activity clusters.
     *
     * By default, it displays the flat type. If property kuali.ks.enrollment.schoc.options.default_ao_display_format is
     * configured, display the configured type.
     */
    public static enum AoDisplayFormat {
        /** Display a flat list of activities for each CourseOffering */
        FLAT( "Flat" ),
        /** Display activities grouped by activity clusters */
        CLUSTER( "By Cluster" ),
        /** Display registration group and activities grouped by activity clusters */
        REG_GROUP( "By Registration Group" );

        private String text;

        private AoDisplayFormat(String text){
            this.text = text;
        }

        public String getText(){
            return text;
        }
    };

    public ScheduleOfClassesSearchForm() {
        coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
        aoWrapperList = new ArrayList<ActivityOfferingWrapper>();
        rgResultList = new ArrayList<RegistrationGroupWrapper>();
        foId2aoTypeMap = new HashMap<String, FormatOfferingInfo>();
        aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        courseOfferingId = "";
        aoDisplayFormat = AoDisplayFormat.FLAT;
        allowSelectableAoRendering = false;
    }

    public List<CourseOfferingDisplayWrapper> getCoDisplayWrapperList() {
        return coDisplayWrapperList;
    }

    public void setCoDisplayWrapperList(List<CourseOfferingDisplayWrapper> coDisplayWrapperList) {
        this.coDisplayWrapperList = coDisplayWrapperList;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
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

    @Override
    public List<ActivityOfferingWrapper> getActivityWrapperList() {
        return aoWrapperList;
    }

    @Override
    public void setActivityWrapperList(List<ActivityOfferingWrapper> activityWrapperList) {
        this.aoWrapperList = activityWrapperList;
    }

    @Override
    public List<ActivityOfferingClusterWrapper> getClusterResultList() {
        return aoClusterWrapperList;
    }

    @Override
    public void setClusterResultList(List<ActivityOfferingClusterWrapper> clusterResultList) {
        this.aoClusterWrapperList = clusterResultList;
    }

    public void setCourseOfferingId(String courseOfferingId){
        this.courseOfferingId = courseOfferingId;
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

    public List<RegistrationGroupWrapper> getRgResultList() {
        return rgResultList;
    }

    public void setRgResultList(List<RegistrationGroupWrapper> rgResultList) {
        this.rgResultList = rgResultList;
    }

    public Map<String,FormatOfferingInfo> getFoId2aoTypeMap() {
        return foId2aoTypeMap;
    }

    public void setFoId2aoTypeMap(Map<String, FormatOfferingInfo> foId2aoTypeMap) {
        this.foId2aoTypeMap = foId2aoTypeMap;
    }

    public boolean isHasMoreThanOneCluster() {
        return hasMoreThanOneCluster;
    }

    public void setHasMoreThanOneCluster(boolean hasMoreThanOneCluster) {
        this.hasMoreThanOneCluster = hasMoreThanOneCluster;
    }

    /**
     * Returns the display type.
     * @return
     */
    public AoDisplayFormat getAoDisplayFormat() {
        return aoDisplayFormat;
    }

    /**
     * Set the display type
     *
     * @param aoDisplayFormat
     */
    public void setAoDisplayFormat(AoDisplayFormat aoDisplayFormat) {
        this.aoDisplayFormat = aoDisplayFormat;
    }

    /**
     * Set to display a control to change display format
     *
     * @param allowSelectableAoRendering
     */
    public void setAllowSelectableAoRendering( boolean allowSelectableAoRendering ) {
        this.allowSelectableAoRendering = allowSelectableAoRendering;
    }

    /**
     * Returns if the control is displayed to change the display format
     *
     * @return
     */
    public boolean getAllowSelectableAoRendering() {
        return this.allowSelectableAoRendering;
    }

}