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

import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.lum.program.infc.CredentialProgram;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "CredentialProgramInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "version",
    "descr",
    "code",
    "shortTitle",
    "longTitle",
    "transcriptTitle",
    "universityClassification",
    "startTerm",
    "endTerm",
    "endProgramEntryTerm",
    "divisionsContentOwner",
    "divisionsStudentOversight",
    "unitsContentOwner",
    "unitsStudentOversight",
    "learningObjectives",
    "programRequirements",
    "institution",
    "resultOptions",
    "programLevel",
    "coreProgramIds",
    "meta",
    "attributes" , "_futureElements" }) 
    
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialProgramInfo extends CommonWithCredentialProgramInfo implements CredentialProgram,
        ProgramCodeAssembly,
        ProgramIdentifierAssembly,
        ProgramAtpAssembly,
        ProgramBasicOrgAssembly,
        ProgramRequirementAssembly,
        Serializable {
    
    private static final long serialVersionUID = 1L;
    @XmlElement
    private AdminOrgInfo institution;
    @XmlElement
    private List<String> resultOptions;
    @XmlElement
    private String programLevel;
    @XmlElement
    private List<String> coreProgramIds;
    @XmlAnyElement
    private List<Object> _futureElements;  
     
    public CredentialProgramInfo() {
    }
    
    public CredentialProgramInfo(CredentialProgram credentialProgram) {
        super(credentialProgram);
        if (credentialProgram != null) {
            this.institution = new AdminOrgInfo(credentialProgram.getInstitution());
            this.resultOptions = credentialProgram.getResultOptions() != null
                    ? new ArrayList<String>(credentialProgram.getResultOptions())
                    : new ArrayList<String>();
            this.programLevel = credentialProgram.getProgramLevel();
            this.coreProgramIds = credentialProgram.getCoreProgramIds() != null
                    ? new ArrayList<String>(credentialProgram.getCoreProgramIds())
                    : new ArrayList<String>();
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
     * R1 Compatibility
     * @deprecated
     */
    @Deprecated
    public String getCredentialProgramType() {
        return this.getTypeKey();
    }

    /**
     * R1 Compatibility
     * @deprecated
     */
    @Deprecated
    public void setCredentialProgramType(String credentialProgramType) {
        this.setTypeKey(credentialProgramType);
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

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    //KSCM-313 Should be removed, see https://wiki.kuali.org/display/STUDENT/R1+to+R2+Change+Log 
    @Override
    public String getDiplomaTitle() {
        return null;
    }

    //KSCM-313 Should be removed, see https://wiki.kuali.org/display/STUDENT/R1+to+R2+Change+Log
    @Override
    public void setDiplomaTitle(String diplomaTitle) {
        
    }

    @Override
    public String getCip2000Code() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCip2000Code(String cip2000Code) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getCip2010Code() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCip2010Code(String cip2010Code) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getHegisCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setHegisCode(String hegisCode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getSelectiveEnrollmentCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        // TODO Auto-generated method stub
        
    }
    
}
