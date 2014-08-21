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
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueInfoWrapper;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a form for HoldIssue objects
 *
 * @author Kuali Student Team
 */
public class HoldIssueManagementForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;

//    private String id;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String organizationId;
//    private String orgName;
//    private String orgAddress;
    private String code;
//    private String baseType;
//    private String lastTerm;
//    private String firstTerm;
//    private String firstDate;
//    private String lastDate;

    //    private boolean isSaveSuccess;
    private Boolean displayAddButton;

    private HoldIssueInfo holdIssueInfo;

    private List<HoldIssueInfoWrapper> holdIssueInfoList = new ArrayList<HoldIssueInfoWrapper>();

    public HoldIssueManagementForm(){
        super();
        displayAddButton = false;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
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

    public List<HoldIssueInfoWrapper> getHoldIssueInfoList() {
        return holdIssueInfoList;
    }

    public void setHoldIssueInfoList(List<HoldIssueInfoWrapper> holdIssueInfoList) {
        this.holdIssueInfoList = holdIssueInfoList;
    }

//    public String getOrgName() {
//        return orgName;
//    }
//
//    public void setOrgName(String orgName) {
//        this.orgName = orgName;
//    }

//    public boolean getIsSaveSuccess() {
//        return isSaveSuccess;
//    }
//
//    public void setIsSaveSuccess(boolean saveSuccess) {
//        isSaveSuccess = saveSuccess;
//    }

    public HoldIssueInfo getHoldIssueInfo() {
        return holdIssueInfo;
    }

    public void setHoldIssueInfo(HoldIssueInfo holdIssueInfo) {
        this.holdIssueInfo = holdIssueInfo;
    }

//    public String getOrgAddress() {
//        return orgAddress;
//    }
//
//    public void setOrgAddress(String orgAddress) {
//        this.orgAddress = orgAddress;
//    }
//
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
//
//    public String getBaseType() {
//        return baseType;
//    }
//
//    public void setBaseType(String baseType) {
//        this.baseType = baseType;
//    }
//
//    public String getLastTerm() {
//        return lastTerm;
//    }
//
//    public void setLastTerm(String lastTerm) {
//        this.lastTerm = lastTerm;
//    }
//
//    public String getFirstTerm() {
//        return firstTerm;
//    }
//
//    public void setFirstTerm(String firstTerm) {
//        this.firstTerm = firstTerm;
//    }
//
//    public String getFirstDate() {
//        return firstDate;
//    }
//
//    public void setFirstDate(String firstDate) {
//        this.firstDate = firstDate;
//    }
//
//    public String getLastDate() {
//        return lastDate;
//    }
//
//    public void setLastDate(String lastDate) {
//        this.lastDate = lastDate;
//    }

    public Boolean getDisplayAddButton() {
        return displayAddButton;
    }

    public void setDisplayAddButton(Boolean displayAddButton) {
        this.displayAddButton = displayAddButton;
    }
}
