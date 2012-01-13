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


import org.kuali.student.r2.lum.program.infc.CoreProgram;
import org.w3c.dom.Element;

@XmlType(name = "CoreProgramInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "shortTitle", "longTitle", "transcriptTitle", "code", "universityClassification", "startTermId",
        "endTermId", "endProgramEntryTermId", "programRequirements", "divisionsContentOwner", "divisionsStudentOversight", "unitsContentOwner", "unitsStudentOversight", "referenceURL",
        "catalogDescr", "catalogPublicationTargets", "learningObjectives", "cip2000Code", "diplomaTitle", "hegisCode", "selectiveEnrollmentCode", "cip2010Code", "meta", "attributes",
        "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CoreProgramInfo extends ProgramAttributesInfo implements CoreProgram, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String referenceURL;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CoreProgramInfo() {

    }

    public CoreProgramInfo(CoreProgram coreProgram) {
        super(coreProgram);
        if (coreProgram != null) {
            this.referenceURL = coreProgram.getReferenceURL();

        }
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

}