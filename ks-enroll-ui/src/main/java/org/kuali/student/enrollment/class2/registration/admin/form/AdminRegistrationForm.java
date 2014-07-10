/*
 * Copyright 2006-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class2.registration.admin.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brian on 6/17/14.
 */
public class AdminRegistrationForm extends UifFormBase implements Serializable {

    private String studentId;
    private String studentName;
    private String program;
    private String standing;
    private String credits;
    private String major;
    private String department;
    private String dob;

    private String termCode;
    private TermInfo termInfo;
    private String termName;

    private RegistrationCourse pendingDropCourse;

    private int editRegisteredIndex;
    private int editWaitlistedIndex;

    private RegistrationCourse tempRegCourseEdit;
    private RegistrationCourse tempWaitlistCourseEdit;

    private List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();
    private List<RegistrationCourse> waitlistedCourses = new ArrayList<RegistrationCourse>();
    private List<RegistrationCourse> pendingCourses = new ArrayList<RegistrationCourse>();
    private List<RegistrationIssue> registrationIssues = new ArrayList<RegistrationIssue>();

    private List<RegistrationCourse> coursesInProcess = new ArrayList<RegistrationCourse>();

    public AdminRegistrationForm(){
        RegistrationCourse course1 =
                new RegistrationCourse("CHEM 237", "1001", "The Chemistry of Stuff", 3, "Regular", new Date());
        List<RegistrationActivity> activities1 = new ArrayList<RegistrationActivity>();
        activities1.add(new RegistrationActivity("Lec", "MWF 01:00pm - 02:30pm", "Steve Capriani", "PTX 2391"));
        activities1.add(new RegistrationActivity("Lab", "MWF 02:30pm - 03:30pm", "Steve Capriani", "PTX 2391"));
        course1.setActivities(activities1);

        registeredCourses.add(course1);

        RegistrationCourse course2 =
                new RegistrationCourse("ENGL 233", "1001", "The World of Shakespeare", 3, "Audit", new Date());
        List<RegistrationActivity> activities2 = new ArrayList<RegistrationActivity>();
        activities2.add(new RegistrationActivity("Lec", "MWF 01:00pm - 02:30pm", "Someone", "PTX 1111"));
        course2.setActivities(activities2);

        registeredCourses.add(course2);

        RegistrationCourse course3 =
                new RegistrationCourse("ENGL 640", "1001", "Light and Motion", 3, "Pass/Fail", new Date());
        List<RegistrationActivity> activities3 = new ArrayList<RegistrationActivity>();
        activities3.add(new RegistrationActivity("Lec", "MWF 01:00pm - 02:30pm", "Someone", "PTX 1200"));
        course3.setActivities(activities3);

        registeredCourses.add(course3);

        RegistrationCourse course4 =
                new RegistrationCourse("CHEM 237", "1001", "The Chemistry of Stuff", 3, "Regular", new Date());
        List<RegistrationActivity> activities4 = new ArrayList<RegistrationActivity>();
        activities4.add(new RegistrationActivity("Lec", "MWF 04:00pm - 05:30pm", "Steve Capriani", "PTX 2391"));
        activities4.add(new RegistrationActivity("Lab", "MWF 05:30pm - 06:30pm", "Steve Capriani", "PTX 2391"));
        course4.setActivities(activities4);

        waitlistedCourses.add(course4);

        pendingCourses.add(new RegistrationCourse());
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStanding() {
        return standing;
    }

    public void setStanding(String standing) {
        this.standing = standing;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName( String termName) {
        this.termName = termName;
    }

    public List<RegistrationCourse> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<RegistrationCourse> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public List<RegistrationCourse> getWaitlistedCourses() {
        return waitlistedCourses;
    }

    public void setWaitlistedCourses(List<RegistrationCourse> waitlistedCourses) {
        this.waitlistedCourses = waitlistedCourses;
    }

    public int getRegisteredCredits() {
        int credits = 0;
        for (RegistrationCourse course: registeredCourses) {
            credits += course.getCredits();
        }

        return credits;
    }

    public int getWaitlistedCredits() {
        int credits = 0;
        for (RegistrationCourse course: waitlistedCourses) {
            credits += course.getCredits();
        }

        return credits;
    }

    public List<RegistrationCourse> getPendingCourses() {
        return pendingCourses;
    }

    public void setPendingCourses(List<RegistrationCourse> pendingCourses) {
        this.pendingCourses = pendingCourses;
    }

    public List<RegistrationIssue> getRegistrationIssues() {
        return registrationIssues;
    }

    public void setRegistrationIssues(List<RegistrationIssue> registrationIssues) {
        this.registrationIssues = registrationIssues;
    }

    public List<RegistrationCourse> getCoursesInProcess() {
        return coursesInProcess;
    }

    public void setCoursesInProcess(List<RegistrationCourse> coursesInProcess) {
        this.coursesInProcess = coursesInProcess;
    }

    public RegistrationCourse getPendingDropCourse() {
        return pendingDropCourse;
    }

    public void setPendingDropCourse(RegistrationCourse pendingDropCourse) {
        this.pendingDropCourse = pendingDropCourse;
    }

    public int getEditRegisteredIndex() {
        return editRegisteredIndex;
    }

    public void setEditRegisteredIndex(int editRegisteredIndex) {
        this.editRegisteredIndex = editRegisteredIndex;
    }

    public int getEditWaitlistedIndex() {
        return editWaitlistedIndex;
    }

    public void setEditWaitlistedIndex(int editWaitlistedIndex) {
        this.editWaitlistedIndex = editWaitlistedIndex;
    }

    public RegistrationCourse getTempRegCourseEdit() {
        return tempRegCourseEdit;
    }

    public void setTempRegCourseEdit(RegistrationCourse tempRegCourseEdit) {
        this.tempRegCourseEdit = tempRegCourseEdit;
    }

    public RegistrationCourse getTempWaitlistCourseEdit() {
        return tempWaitlistCourseEdit;
    }

    public void setTempWaitlistCourseEdit(RegistrationCourse tempWaitlistCourseEdit) {
        this.tempWaitlistCourseEdit = tempWaitlistCourseEdit;
    }
}
