/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.program.infc.HonorsProgram;
//import org.w3c.dom.Element;


@XmlType(name = "HonorsProgramInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "credentialProgramId",
    "programRequirements",
    "meta",
    "attributes", "_futureElements" }) 
    
@XmlAccessorType(XmlAccessType.FIELD)
public class HonorsProgramInfo extends IdNamelessEntityInfo implements HonorsProgram, Serializable {
    
    private static final long serialVersionUID = 1L;
    @XmlElement
    private String credentialProgramId;
    @XmlElement
    private List<String> programRequirements;
    @XmlAnyElement
    private List<Object> _futureElements;  
    
    public HonorsProgramInfo() {
        this.programRequirements = new ArrayList<String>();
    }
    
    public HonorsProgramInfo(HonorsProgram honorsProgram) {
        super (honorsProgram);
        if (honorsProgram != null) {
            this.credentialProgramId = honorsProgram.getCredentialProgramId();
            this.programRequirements = honorsProgram.getProgramRequirements() != null 
                    ? new ArrayList<String>(honorsProgram.getProgramRequirements()) 
                    : new ArrayList<String>();
        }
    }

    /**
     * Identifier of the credential program under which the honors belongs
     */
    @Override
    public String getCredentialProgramId() {
        return credentialProgramId;
    }
    
    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }
    
    @Override
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
    }
    
    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
    }

}
