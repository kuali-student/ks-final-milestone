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
import java.util.List;

import javax.xml.bind.annotation.*;


import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCommonAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.lum.program.infc.CoreProgram;
import org.w3c.dom.Element;

@XmlType(name = "CoreProgramInfo", propOrder = {"id",
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
    "referenceURL",
    "catalogDescr",
    "catalogPublicationTargets",
    "meta",
    "attributes",
    "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CoreProgramInfo extends CommonWithCoreProgramInfo
        implements CoreProgram,
        ProgramCommonAssembly,
        ProgramBasicOrgAssembly,
        ProgramAtpAssembly,
        ProgramIdentifierAssembly,
        ProgramPublicationAssembly,
        ProgramRequirementAssembly,
        Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAnyElement
    private List<Element> _futureElements;

    public CoreProgramInfo() {
    }

    public CoreProgramInfo(CoreProgram input) {
        super(input);
    }
}