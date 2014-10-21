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
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueResult;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a form for HoldIssue objects
 *
 * @author Kuali Student Team
 */
public class HoldIssueManagementForm extends KSUifForm {

    private static final long serialVersionUID = 4898118410378641665L;

    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String organizationId;
    private String code;

    private List<HoldIssueResult> holdIssueResultList = new ArrayList<HoldIssueResult>();

    private boolean hasSearchBeenCalled;

    public HoldIssueManagementForm(){
        super();
        setHasSearchBeenCalled(false);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<HoldIssueResult> getHoldIssueResultList() {
        return holdIssueResultList;
    }

    public void setHoldIssueResultList(List<HoldIssueResult> holdIssueResultList) {
        this.holdIssueResultList = holdIssueResultList;
    }

    public boolean isHasSearchBeenCalled() {
        return hasSearchBeenCalled;
    }

    public void setHasSearchBeenCalled(boolean hasSearchBeenCalled) {
        this.hasSearchBeenCalled = hasSearchBeenCalled;
    }

}
