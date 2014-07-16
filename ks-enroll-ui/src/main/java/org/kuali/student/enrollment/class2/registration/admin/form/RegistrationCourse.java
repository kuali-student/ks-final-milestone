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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brian on 6/18/14.
 */
public class RegistrationCourse implements Serializable{

    private static final long serialVersionUID = 5236548204817229477L;
    private String code;
    private String section;
    private String title;

    private Integer credits;
    private String regOptions;

    private Date transactionalDate;
    private Date dropDate;
    private Date effectiveDate;

    private List<RegistrationActivity> activities = new ArrayList<RegistrationActivity>();
    private boolean subterm;

    public RegistrationCourse(){}

    public RegistrationCourse(String code, String section, String title, Integer credits, String regOptions,
                              Date transactionalDate) {
        this.code = code;
        this.section = section;
        this.title = title;
        this.credits = credits;
        this.regOptions = regOptions;
        this.transactionalDate = transactionalDate;
        this.effectiveDate = transactionalDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String course) {
        this.code = course;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getRegOptions() {
        return regOptions;
    }

    public void setRegOptions(String regOptions) {
        this.regOptions = regOptions;
    }

    public Date getTransactionalDate() {
        return transactionalDate;
    }

    public void setTransactionalDate(Date transactionalDate) {
        this.transactionalDate = transactionalDate;
    }

    public Date getDropDate() {
        return dropDate;
    }

    public void setDropDate(Date dropDate) {
        this.dropDate = dropDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<RegistrationActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<RegistrationActivity> activities) {
        this.activities = activities;
    }

    public boolean isSubterm() {
        return subterm;
    }

    public void setSubterm(boolean subterm) {
        this.subterm = subterm;
    }

    public List<String> getActivityTypes(){
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationActivity activity: activities) {
            list.add(activity.getType());
        }

        return list;
    }

    public List<String> getActivityDateTimes(){
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationActivity activity: activities) {
            list.add(activity.getDateTime());
        }

        return list;
    }

    public List<String> getActivityInstructors(){
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationActivity activity: activities) {
            list.add(activity.getInstructor());
        }

        return list;
    }

    public List<String> getActivityRooms(){
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationActivity activity: activities) {
            list.add(activity.getRoom());
        }

        return list;
    }

    public List<String> getActivityTypeDateTimes() {
        ArrayList<String> list = new ArrayList<String>();
        for (RegistrationActivity activity: activities) {
            list.add(activity.getType() + " " + activity.getDateTime());
        }

        return list;
    }

}
