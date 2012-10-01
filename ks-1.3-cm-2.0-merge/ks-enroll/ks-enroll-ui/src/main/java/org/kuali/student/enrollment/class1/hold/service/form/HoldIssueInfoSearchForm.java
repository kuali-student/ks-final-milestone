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
 */
package org.kuali.student.enrollment.class1.hold.service.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HoldIssueInfoSearchForm extends UifFormBase {
    private String id;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String organizationId;

    private List<HoldIssueInfo> holdIssueInfo;

    public HoldIssueInfoSearchForm(){
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<HoldIssueInfo> getHoldIssueInfo() {
        return holdIssueInfo;
    }

    public void setHoldIssueInfo(List<HoldIssueInfo> holdIssueInfo) {
        this.holdIssueInfo = holdIssueInfo;
    }
}
