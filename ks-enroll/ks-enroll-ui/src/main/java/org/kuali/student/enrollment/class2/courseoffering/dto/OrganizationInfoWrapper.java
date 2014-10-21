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
 * Created by Adi Rajesh on 6/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.io.Serializable;

/**
 * This class provides a wrapper for Organization data
 *
 * @author Kuali Student Team
 */
public class OrganizationInfoWrapper implements Serializable {
    private String organizationName;
    private String id;

    public OrganizationInfoWrapper() {
    }

    public OrganizationInfoWrapper(OrgInfo orgInfo) {
        this.id = orgInfo.getId();
        this.organizationName = orgInfo.getLongName();
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}