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

import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.program.infc.CredentialProgram;
import org.w3c.dom.Element;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "CredentialProgramInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "shortTitle", "longTitle", "transcriptTitle", "programLevel", "code", "universityClassification",
        "institution", "resultOptions", "startTermId", "endTermId", "endProgramEntryTermId", "divisionsContentOwner", "divisionsStudentOversight", "unitsContentOwner", "unitsStudentOversight",
        "learningObjectives", "coreProgramIds", "programRequirements", "catalogPublicationTargets", "catalogDescr", "credentialProgramType", "diplomaTitle", "selectiveEnrollmentCode", "hegisCode",
        "cip2000Code", "cip2010Code", "meta", "attributes", "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialProgramInfo extends ProgramAttributesInfo implements CredentialProgram, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String programLevel;

    @XmlElement
    private List<String> coreProgramIds;

    @XmlElement
    private String credentialProgramType;

    @XmlElement
    private ArrayList<String> resultOptions;

    @XmlElement
    private AdminOrgInfo institution;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CredentialProgramInfo() {

    }

    public CredentialProgramInfo(CredentialProgram credentialProgram) {
        super(credentialProgram);
        if (credentialProgram != null) {
            this.programLevel = credentialProgram.getProgramLevel();
            this.institution = new AdminOrgInfo(credentialProgram.getInstitution());
            this.resultOptions = credentialProgram.getResultOptions() != null ? new ArrayList<String>(credentialProgram.getResultOptions()) : new ArrayList<String>();
            List<LoDisplayInfo> learningObjectives = new ArrayList<LoDisplayInfo>();

            if (credentialProgram.getLearningObjectives() != null) {
                for (LoDisplay loDisplay : credentialProgram.getLearningObjectives()) {
                    LoDisplayInfo loDisplayInfo = new LoDisplayInfo(loDisplay);
                    learningObjectives.add(loDisplayInfo);
                }
            }

            this.coreProgramIds = credentialProgram.getCoreProgramIds() != null ? new ArrayList<String>(credentialProgram.getCoreProgramIds()) : new ArrayList<String>();
            this.credentialProgramType = credentialProgram.getCredentialProgramType();

        }
    }

    @Override
    public List<String> getCoreProgramIds() {
        return coreProgramIds;
    }

    public void setCoreProgramIds(List<String> coreProgramIds) {
        this.coreProgramIds = coreProgramIds;
    }

    /**
     * Unique identifier for a learning unit type. Once set at create time, this
     * field may not be updated.
     */
    @Override
    public String getCredentialProgramType() {
        return credentialProgramType;
    }

    public void setCredentialProgramType(String credentialProgramType) {
        this.credentialProgramType = credentialProgramType;
    }

    @Override
    public String getProgramLevel() {
        return programLevel;
    }

    public void setProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    @Override
    public AdminOrgInfo getInstitution() {
        return institution;
    }

    public void setInstitution(AdminOrgInfo institution) {
        this.institution = institution;
    }

    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

}