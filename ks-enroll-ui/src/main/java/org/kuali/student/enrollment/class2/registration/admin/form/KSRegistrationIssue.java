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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 6/18/14.
 */
public class KSRegistrationIssue {

    private KSWorkshopCourse course;
    private List<RegistrationIssueItem> items = new ArrayList<RegistrationIssueItem>();

    public KSWorkshopCourse getCourse() {
        return course;
    }

    public void setCourse(KSWorkshopCourse course) {
        this.course = course;
    }

    public List<RegistrationIssueItem> getItems() {
        return items;
    }

    public void setItems(List<RegistrationIssueItem> items) {
        this.items = items;
    }

}
