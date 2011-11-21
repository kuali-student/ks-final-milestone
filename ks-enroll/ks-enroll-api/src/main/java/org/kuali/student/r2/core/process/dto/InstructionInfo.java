/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.process.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.process.infc.Instruction;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstructionInfo", propOrder = { "id", "typeKey", "stateKey", 
                "name", "descr", "processKey", "checkId", "populationIds", 
                "atpTypeKeys", "atpKeys", "message", "position", "isWarning", 
                "continueOnFail", "isExemptable", 
                "meta", "attributes",
		"_futureElements" })

public class InstructionInfo extends RelationshipInfo 
    implements Instruction, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String processKey;

    @XmlElement 
    private String checkId;

    @XmlElement 
    private List<String> populationIds;

    @XmlElement 
    private List<String> atpTypeKeys;

    @XmlElement 
    private List<String> atpKeys;

    @XmlElement 
    private RichTextInfo message;

    @XmlElement 
    private Integer position;

    @XmlElement 
    private Boolean isWarning;

    @XmlElement 
    private Boolean continueOnFail;

    @XmlElement 
    private Boolean isExemptable;

    @XmlAnyElement
    private List<Element> _futureElements;
    

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
            if (instruction.getPopulationIds() != null) {
                this.populationIds = new ArrayList<String>(instruction.getPopulationIds());
            }

            if (instruction.getAtpTypeKeys() != null) {
                this.atpTypeKeys = new ArrayList<String>(instruction.getAtpTypeKeys());
            }

            if (instruction.getAtpKeys() != null) {
                this.atpKeys = new ArrayList<String>(instruction.getAtpKeys());
            }

            this.message = new RichTextInfo(instruction.getMessage());
            this.position = instruction.getPosition();
            this.isWarning = instruction.getIsWarning();
            this.continueOnFail = instruction.getContinueOnFail();
            this.isExemptable = instruction.getIsExemptable();
        }
    }

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
    public List<String> getPopulationIds() {
        if (this.populationIds == null) {
            this.populationIds = new ArrayList<String>();
        }

        return this.populationIds;
    }

    public void setPopulationIds(List<String> populationIds) {
        this.populationIds = populationIds;
    }

    @Override
    public List<String> getAtpTypeKeys() {
        if (this.atpTypeKeys == null) {
            this.atpTypeKeys = new ArrayList<String>();
        }

        return this.atpTypeKeys;
    }

    public void setAtpTypeKeys(List<String> atpTypeKeys) {
        this.atpTypeKeys = atpTypeKeys;
    }

    @Override
    public List<String> getAtpKeys() {
        if (this.atpKeys == null) {
            this.atpKeys = new ArrayList<String>();
        }

        return this.atpKeys;
    }

    public void setAtpKeys(List<String> atpKeys) {
        this.atpKeys = atpKeys;
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
    public Boolean getIsExemptable() {
        return this.isExemptable;
    }

    public void setIsExemptable(Boolean isExemptable) {
        this.isExemptable = isExemptable;
    }
}
