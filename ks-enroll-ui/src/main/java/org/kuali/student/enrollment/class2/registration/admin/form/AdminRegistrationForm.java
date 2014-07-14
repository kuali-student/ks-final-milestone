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

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;
import java.util.ArrayList;
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

    private String termCode;
    private TermInfo termInfo;
    private String termName;

    private RegistrationCourse pendingDropCourse = new RegistrationCourse();

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

    public void clear() {
        this.studentName= null;
        this.termCode = null;

        clearTermAndCourseRegistrationInfo();
    }

    public void clearTermAndCourseRegistrationInfo() {
        this.termInfo = null;
        this.termName = null;
        this.registeredCourses = new ArrayList<RegistrationCourse>();
        this.waitlistedCourses = new ArrayList<RegistrationCourse>();
    }
}
