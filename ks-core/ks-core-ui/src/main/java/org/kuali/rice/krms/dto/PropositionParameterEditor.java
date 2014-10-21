/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;

import java.io.Serializable;
import org.kuali.rice.krms.api.repository.term.TermDefinition;

/**
 * @author Kuali Student Team
 */
public class PropositionParameterEditor implements PropositionParameterContract, Serializable {

    private String id;
    private String propId;
    private String value;
    private TermDefinition termValue;
    private String parameterType;
    private Integer sequenceNumber;
    private Long versionNumber;

    public PropositionParameterEditor(){
        super();
    }

    public PropositionParameterEditor(String parameterType, Integer sequenceNumber){
        super();
        this.setParameterType(parameterType);
        this.setSequenceNumber(sequenceNumber);
        this.setVersionNumber(null);
        this.setValue(null);
        this.setTermValue(null);
    }

    /**
     * Converts a immutable object to it's mutable bo counterpart
     * @param definition immutable object
     * @return the mutable bo
     */
    public PropositionParameterEditor(PropositionParameterContract definition) {
        this.id = definition.getId();
        this.propId = definition.getPropId();
        this.value = definition.getValue();
        this.termValue = definition.getTermValue();
        this.parameterType = definition.getParameterType();
        this.sequenceNumber = definition.getSequenceNumber();
        this.versionNumber = definition.getVersionNumber();
    }

    public void clear() {
        this.value = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    @Override
    public String getPropId() {
        return propId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setTermValue(TermDefinition termValue) {
        this.termValue = termValue;
    }

    @Override
    public TermDefinition getTermValue() {
        return termValue;
    }

    
    
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public String getParameterType() {
        return parameterType;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

}
