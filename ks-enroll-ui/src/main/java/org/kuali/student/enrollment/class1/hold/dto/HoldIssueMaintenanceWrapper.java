/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by dietrich on 2014/08/05
 */
package org.kuali.student.enrollment.class1.hold.dto;

import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class HoldIssueMaintenanceWrapper implements Serializable {

    private TypeInfo type;
    private String id;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String organizationId;
    private String orgName;
    private String orgAddress;
    private String code;
    private String baseType;
    private String firstTerm;
    private String lastTerm;
    private Date firstDate;
    private Date lastDate;
    private String holdHistory;
    private String authorization;

    private boolean isSaveSuccess;


    private HoldIssueInfo holdIssueInfo;

    private List<HoldIssueInfo> holdIssueInfoList;

    private List<AuthorizingOrgWrapper> organizationNames = new ArrayList<AuthorizingOrgWrapper>();

    private List<ProcessInfo> processNames = new ArrayList<ProcessInfo>();

    public HoldIssueMaintenanceWrapper() {
        super();
        type = new TypeInfo();
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

    public TypeInfo getType() {
        return type;
    }

    public void setType(TypeInfo type) {
        this.type = type;
    }
    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getLastTerm() {
        return lastTerm;
    }

    public void setLastTerm(String lastTerm) {
        this.lastTerm = lastTerm;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public List<AuthorizingOrgWrapper> getOrganizationNames() {
        return organizationNames;
    }

    public void setOrganizationNames(List<AuthorizingOrgWrapper> organizationNames) {
        this.organizationNames = organizationNames;
    }

    public boolean isSaveSuccess() {
        return isSaveSuccess;
    }

    public void setSaveSuccess(boolean saveSuccess) {
        isSaveSuccess = saveSuccess;
    }

    public HoldIssueInfo getHoldIssueInfo() {
        return holdIssueInfo;
    }

    public void setHoldIssueInfo(HoldIssueInfo holdIssueInfo) {
        this.holdIssueInfo = holdIssueInfo;
    }

    public List<HoldIssueInfo> getHoldIssueInfoList() {
        return holdIssueInfoList;
    }

    public void setHoldIssueInfoList(List<HoldIssueInfo> holdIssueInfoList) {
        this.holdIssueInfoList = holdIssueInfoList;
    }

    public String getHoldHistory() {
        return holdHistory;
    }

    public void setHoldHistory(String holdHistory) {
        this.holdHistory = holdHistory;
    }

    public List<ProcessInfo> getProcessNames() {
        return processNames;
    }

    public void setProcessNames(List<ProcessInfo> processNames) {
        this.processNames = processNames;
    }

    public Map<String, String> getAdminOrg() {
        Map<String, String> adminOrgMap = new HashMap<String, String>();
        if (organizationNames != null && !organizationNames.isEmpty()) {
            StringBuilder orgIDs = new StringBuilder("");
            for (AuthorizingOrgWrapper organizationName : organizationNames) {
                orgIDs.append(organizationName.getId()).append(",");
            }
            if (orgIDs.length() > 0) {
                adminOrgMap.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length() - 1));
            }
        }
        return adminOrgMap;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
