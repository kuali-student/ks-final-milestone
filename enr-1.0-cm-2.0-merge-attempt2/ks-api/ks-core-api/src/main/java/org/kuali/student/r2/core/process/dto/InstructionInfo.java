/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.process.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.process.infc.Instruction;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstructionInfo", propOrder = { "id", "typeKey", "stateKey", 
                "effectiveDate", "expirationDate",
                "processKey", "checkId",
                "appliedPopulationId", "appliedAtpTypeKeys",
                "message", "position", "isWarning", 
                "continueOnFail", "isExemptible",
                "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class InstructionInfo 
    extends RelationshipInfo 
    implements Instruction, Serializable {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String processKey;

    @XmlElement 
    private String checkId;

    @XmlElement 
    private String appliedPopulationId;

    @XmlElement 
    private List<String> appliedAtpTypeKeys;

    @XmlElement 
    private RichTextInfo message;

    @XmlElement 
    private Integer position;

    @XmlElement 
    private Boolean isWarning;

    @XmlElement 
    private Boolean continueOnFail;

    @XmlElement 
    private Boolean isExemptible;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    /**
     * Constructs a new InstructionInfo.
     */
    public InstructionInfo() {
    }

    /**
     * Constructs a new InstructionInfo from another Instruction.
     * 
     * @param instruction the Instruction to copy
     */
    public InstructionInfo(Instruction instruction) {
        super(instruction);
        if (instruction != null) {
            this.processKey= instruction.getProcessKey();
            this.checkId = instruction.getCheckId();
            this.appliedPopulationId = instruction.getAppliedPopulationId();
            if (instruction.getAppliedAtpTypeKeys() != null) {
                this.appliedAtpTypeKeys = new ArrayList<String>(instruction.getAppliedAtpTypeKeys());
            }
            this.message = new RichTextInfo(instruction.getMessage());
            this.position = instruction.getPosition();
            this.isWarning = instruction.getIsWarning();
            this.continueOnFail = instruction.getContinueOnFail();
            this.isExemptible = instruction.getIsExemptible();
        }
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    @Override
    public String getProcessKey() {
        return this.processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    @Override
    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    @Override
    public String getAppliedPopulationId() {
        return this.appliedPopulationId;
    }

    public void setAppliedPopulationId(String appliedPopulationId) {
        this.appliedPopulationId = appliedPopulationId;
    }

    @Override
    public List<String> getAppliedAtpTypeKeys() {
        if (this.appliedAtpTypeKeys == null) {
            this.appliedAtpTypeKeys = new ArrayList<String>();
        }

        return this.appliedAtpTypeKeys;
    }

    public void setAppliedAtpTypeKeys(List<String> appliedAtpTypeKeys) {
        this.appliedAtpTypeKeys = appliedAtpTypeKeys;
    }

    @Override
    public RichTextInfo getMessage() {
        return this.message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

    @Override
    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Boolean getIsWarning() {
        return this.isWarning;
    }

    public void setIsWarning(Boolean isWarning) {
        this.isWarning = isWarning;
    }

    @Override
    public Boolean getContinueOnFail() {
        return this.continueOnFail;
    }

    public void setContinueOnFail(Boolean continueOnFail) {
        this.continueOnFail = continueOnFail;
    }

    @Override
    public Boolean getIsExemptible() {
        return this.isExemptible;
    }

    public void setIsExemptible(Boolean isExemptible) {
        this.isExemptible = isExemptible;
    }
}
