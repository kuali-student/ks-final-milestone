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
package org.kuali.student.enrollment.class1.hold.dto;

import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.io.Serializable;

/**
 * This class provides a wrapper for Organization data
 *
 * @author Kuali Student Team
 */
public class AuthorizedOrgWrapper implements Serializable {

    private String id;
    private String name;
    private boolean authOrgApply;
    private boolean authOrgExpire;

    public AuthorizedOrgWrapper() {
    }

    public AuthorizedOrgWrapper(OrgInfo orgInfo) {
        this.id = orgInfo.getId();
        this.name = orgInfo.getShortName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAuthOrgApply() {
        return authOrgApply;
    }

    public void setAuthOrgApply(boolean authOrgApply) {
        this.authOrgApply = authOrgApply;
    }

    public boolean isAuthOrgExpire() {
        return authOrgExpire;
    }

    public void setAuthOrgExpire(boolean authOrgExpire) {
        this.authOrgExpire = authOrgExpire;
    }
}