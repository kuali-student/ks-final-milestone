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
package org.kuali.student.enrollment.class1.hold.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HoldIssueInfoForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;

    private String id;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String organizationId;
    private String orgName;

    private boolean isSaveSuccess;

    private HoldIssueInfo holdIssueInfo;

    private List<HoldIssueInfo> holdIssueInfoList;

    public HoldIssueInfoForm(){
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

    public List<HoldIssueInfo> getHoldIssueInfoList() {
        return holdIssueInfoList;
    }

    public void setHoldIssueInfoList(List<HoldIssueInfo> holdIssueInfos) {
        this.holdIssueInfoList = holdIssueInfos;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean getIsSaveSuccess() {
        return isSaveSuccess;
    }

    public void setIsSaveSuccess(boolean saveSuccess) {
        isSaveSuccess = saveSuccess;
    }

    public HoldIssueInfo getHoldIssueInfo() {
        return holdIssueInfo;
    }

    public void setHoldIssueInfo(HoldIssueInfo holdIssueInfo) {
        this.holdIssueInfo = holdIssueInfo;
    }
}
