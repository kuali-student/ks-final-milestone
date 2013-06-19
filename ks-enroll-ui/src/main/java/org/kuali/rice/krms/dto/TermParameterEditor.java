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

import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;

import java.io.Serializable;

/**
 * @author Kuali Student Team
 */
public class TermParameterEditor implements TermParameterDefinitionContract, Serializable {

    private String id;
    private String termId;
    private String name;
    private String value;
    private Long versionNumber;

    public TermParameterEditor(){
        super();
    }

    public TermParameterEditor(String name, String value){
        super();
        this.setName(name);
        this.setValue(value);
    }

    public TermParameterEditor(TermParameterDefinitionContract contract){
        this.id = contract.getId();
        this.termId = contract.getTermId();
        this.name = contract.getName();
        this.value = contract.getValue();
        this.versionNumber = contract.getVersionNumber();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public String getTermId() {
        return this.termId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

}
