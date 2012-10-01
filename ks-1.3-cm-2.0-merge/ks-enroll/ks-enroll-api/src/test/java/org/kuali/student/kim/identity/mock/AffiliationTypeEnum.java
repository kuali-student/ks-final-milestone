/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.identity.mock;

import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

/**
 * @author nwright
 */
public enum AffiliationTypeEnum {

    FACULTY(KimIdentityServiceConstants.FACULTY_AFFILIATION_TYPE_KEY, "Faculty", true, true, "b"),
    STAFF(KimIdentityServiceConstants.STAFF_AFFILIATION_TYPE_KEY, "Staff", true, true, "c"),
    STUDENT(KimIdentityServiceConstants.STUDENT_AFFILIATION_TYPE_KEY, "Student", true, true, "a"),
    AFFILIATE(KimIdentityServiceConstants.AFFILIATE_AFFILIATION_TYPE_KEY, "Affiliate", true, true, "c");
    private String code;
    private String name;
    private boolean employee;
    private boolean active;
    private String sort;

    private AffiliationTypeEnum(String code, String name, boolean employee, boolean active, String sort) {
        this.code = code;
        this.name = name;
        this.employee = employee;
        this.active = active;
        this.sort = sort;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

