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
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 *
 * Main Form class used on the Admin Registration screen.
 */
public class AdminRegistrationForm extends UifFormBase implements Serializable {

    /**
     * ClientState tracks the state of the screen in the browser. This is NOT the state of the registration but instead
     * it is used to check which components should be validated at what time and which components should be inactive.
     *
     * @see AdminRegConstants.ClientStates for available states.
     */
    private String clientState;

    /**
     * Contains the personal information for the selected student.
     */
    private PersonInfo person;

    private String program;
    private String standing;
    private String credits;
    private String major;
    private String department;

    /**
     * Contains the term information for the selected term code.
     */
    private TermInfo term;

    /**
     * Pending Courses are course codes and sections added in the input section but not yet move registered or
     * added to the registration cart.
     */
    private List<RegistrationCourse> pendingCourses = new ArrayList<RegistrationCourse>();

    /**
     * Courses In Process are courses that has been sent to the registration engine but the registration
     * is not yet completed.
     *
     * This list is not instantiated becuase it will always be set with the current pendingCourses list when
     * user register is performed.
     */
    private List<RegistrationCourse> coursesInProcess;
    private String regRequestId;
    private List<String> confirmationIssues = new ArrayList<String>();

    /**
     * Registration Results contain registration results and courses that did not pass the course eligibility checks.
     * Administrative users has the option to allow these courses to be registered.
     */
    private List<RegistrationResult> registrationResults = new ArrayList<RegistrationResult>();

    /**
     * Term Issues contain issues that did not pass the Term eligibility checks. Administrative
     * users has the option to continue or cancel.
     */
    private List<ValidationResultInfo> termIssues = new ArrayList<ValidationResultInfo>();

    /**
     * Registered Courses contain courses that the student is currently enrolled for.
     */
    private List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();

    /**
     * Waitlisted Courses contains courses that the student was added to the waitlist for.
     */
    private List<RegistrationCourse> waitlistedCourses = new ArrayList<RegistrationCourse>();
    private RegistrationCourse tempWaitlistCourseEdit;

    private RegistrationCourse pendingDropCourse = new RegistrationCourse();

    private boolean termEligible = false;

    private boolean displayRegistrationTabs = false;

    /**
     * courses In Edit contain course that is been edited.
     */
    private List<RegistrationCourse> coursesInEdit = new ArrayList<RegistrationCourse>();
    private List<String> editingIssues = new ArrayList<String>();

    public AdminRegistrationForm(){
        this.clientState = AdminRegConstants.ClientStates.OPEN;
        this.person = new PersonInfo();
        this.term = new TermInfo();
        this.resetPendingCourseValues();
    }

    public void clear() {
        this.person = new PersonInfo();
        this.clearTermValues();
    }

    public void clearTermValues() {
        this.term = new TermInfo();
        this.termEligible = false;
        this.displayRegistrationTabs = false;
        this.clearCourseRegistrationValues();
    }

    public void clearCourseRegistrationValues() {
        this.resetPendingCourseValues();
        this.registrationResults.clear();
        this.registeredCourses.clear();
        this.waitlistedCourses.clear();
        this.termIssues.clear();
        this.confirmationIssues.clear();
    }

    public void resetPendingCourseValues(){
        this.pendingCourses = new ArrayList<>(); //create a new pendingcourses list.
        this.pendingCourses.add(new RegistrationCourse());
    }

    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    public PersonInfo getPerson() {
        return person;
    }

    public void setPerson(PersonInfo person) {
        this.person = person;
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

    public TermInfo getTerm() {
        return term;
    }

    public void setTerm(TermInfo term) {
        this.term = term;
    }

    public List<RegistrationCourse> getPendingCourses() {
        return pendingCourses;
    }

    public void setPendingCourses(List<RegistrationCourse> pendingCourses) {
        this.pendingCourses = pendingCourses;
    }

    public List<RegistrationCourse> getCoursesInProcess() {
        return coursesInProcess;
    }

    public void setCoursesInProcess(List<RegistrationCourse> coursesInProcess) {
        this.coursesInProcess = coursesInProcess;
    }

    public String getRegRequestId() {
        return regRequestId;
    }

    public void setRegRequestId(String regRequestId) {
        this.regRequestId = regRequestId;
    }

    public List<String> getConfirmationIssues() {
        return confirmationIssues;
    }

    public void setConfirmationIssues(List<String> confirmationIssues) {
        this.confirmationIssues = confirmationIssues;
    }

    public List<RegistrationResult> getRegistrationResults() {
        return registrationResults;
    }

    public void setRegistrationResults(List<RegistrationResult> registrationResults) {
        this.registrationResults = registrationResults;
    }

    public List<ValidationResultInfo>  getTermIssues() {
        return termIssues;
    }

    public void setTermIssues(List<ValidationResultInfo> termIssues) {
        this.termIssues = termIssues;
    }

    public List<RegistrationCourse> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<RegistrationCourse> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    /**
     * @return the total number of credits for all registered courses.
     */
    public String getRegisteredCredits() {
        double credits = 0;
        for (RegistrationCourse course: registeredCourses) {
            credits += Double.parseDouble(course.getCredits());
        }

        return String.valueOf(credits);
    }

    public List<RegistrationCourse> getWaitlistedCourses() {
        return waitlistedCourses;
    }

    public void setWaitlistedCourses(List<RegistrationCourse> waitlistedCourses) {
        this.waitlistedCourses = waitlistedCourses;
    }

    /**
     * @return The total number of credits for waitlisted courses.
     */
    public String getWaitlistedCredits() {
        double credits = 0;
        for (RegistrationCourse course: waitlistedCourses) {
            credits += Double.parseDouble(course.getCredits());
        }

        return String.valueOf(credits);
    }

    public RegistrationCourse getTempWaitlistCourseEdit() {
        return tempWaitlistCourseEdit;
    }

    public void setTempWaitlistCourseEdit(RegistrationCourse tempWaitlistCourseEdit) {
        this.tempWaitlistCourseEdit = tempWaitlistCourseEdit;
    }

    public RegistrationCourse getPendingDropCourse() {
        return pendingDropCourse;
    }

    public void setPendingDropCourse(RegistrationCourse pendingDropCourse) {
        this.pendingDropCourse = pendingDropCourse;
    }

    public boolean isTermEligible() {
        return termEligible;
    }

    public void setTermEligible(boolean termEligible) {
        this.termEligible = termEligible;
    }

    public boolean isDisplayRegistrationTabs() {
        return displayRegistrationTabs;
    }

    public void setDisplayRegistrationTabs(boolean displayRegistrationTabs) {
        this.displayRegistrationTabs = displayRegistrationTabs;
    }

    public List<RegistrationCourse> getCoursesInEdit() {
        return coursesInEdit;
    }

    public void setCoursesInEdit(List<RegistrationCourse> coursesInEdit) {
        this.coursesInEdit = coursesInEdit;
    }

    public List<String> getEditingIssues() {
        return editingIssues;
    }

    public void setEditingIssues(List<String> editingIssues) {
        this.editingIssues = editingIssues;
    }
}
