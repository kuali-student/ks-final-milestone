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
package org.kuali.student.enrollment.class1.process.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

import java.util.List;

/**
 * This class provides a form for Process objects
 *
 * @author Kuali Student Team
 */
public class ProcessInfoForm extends UifFormBase {

    private static final long serialVersionUID = 4898118410378641665L;


    private String key;
    private String name;
    private String typeKey;
    private String stateKey;
    private String descr;
    private String ownerOrgId;
    private String orgName;
    private String dialogStateKey;

    private boolean isSaveSuccess;
    private boolean isInstructionActive;

    private List<ProcessInfo> processInfos;
    private List<InstructionInfo> instructionInfoList;

    private ProcessInfo processInfo;

    public ProcessInfoForm() {
        super();
        processInfo = new ProcessInfo();

    }

    public List<ProcessInfo> getProcessInfos() {
        return processInfos;
    }

    public void setProcessInfos(List<ProcessInfo> processInfos) {
        this.processInfos = processInfos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(String ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessInfo processInfo) {
        this.processInfo = processInfo;
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

    public boolean getIsInstructionActive() {
        return isInstructionActive;
    }

    public void setIsInstructionActive(boolean instructionActive) {
        isInstructionActive = instructionActive;
    }

    public List<InstructionInfo> getInstructionInfoList() {
        return instructionInfoList;
    }

    public void setInstructionInfoList(List<InstructionInfo> instructionInfoList) {
        this.instructionInfoList = instructionInfoList;
    }

    public String getDialogStateKey() {
        return dialogStateKey;
    }

    public void setDialogStateKey(String dialogStateKey) {
        this.dialogStateKey = dialogStateKey;
    }
}
