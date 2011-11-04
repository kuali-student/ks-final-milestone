/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.HasTypeState;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.program.infc.MinorDiscipline;

/**
 * Detailed information about a single minor program
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:56:15 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/minorDisciplineInfo+Structure">MinorDisciplineInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MinorDisciplineInfo extends IdEntityInfo implements MinorDiscipline {

    private static final long serialVersionUID = 1L;


    @XmlElement
    private String credentialProgramId;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    private Map<String, String> attributes;

    /**
     * Identifier of the credential program under which the minor belongs
     */
    @Override
    public String getCredentialProgramId() {
        return credentialProgramId;
    }

    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }

    /**
     * Minor Discipline Program Requirements.
     */
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