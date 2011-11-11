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
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.program.infc.MinorDiscipline;

/**
 * This is a description of what this class does - sambit don't forget to fill
 * this in.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MinorDisciplineInfo extends IdEntityInfo implements MinorDiscipline, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String credentialProgramId;

    @XmlElement
    private List<String> programRequirements;

   public MinorDisciplineInfo(){
       
   }
    public MinorDisciplineInfo(MinorDiscipline minorDiscipline) {
        super(minorDiscipline);
        if (minorDiscipline != null) {
            this.credentialProgramId = minorDiscipline.getCredentialProgramId();
            this.programRequirements = new ArrayList<String>(minorDiscipline.getProgramRequirements());
        }
    }

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
            programRequirements = new ArrayList<String>();
        }
        return programRequirements;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
    }

}