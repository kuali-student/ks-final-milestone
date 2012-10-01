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


import org.kuali.student.r2.lum.program.infc.ProgramVariation;
import org.w3c.dom.Element;

/**
 * Detailed information about a variation to a major discipline
 * 
 * Also referred to as a Specialization or a Track
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@XmlType(name = "ProgramVariationInfo", propOrder = {"id",
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
    "intensity",
    "cip2000Code",
    "cip2010Code",
    "hegisCode",
    "selectiveEnrollmentCode",
    "effectiveDate",
    "diplomaTitle",
    "campusLocations",
    "resultOptions",
    "stdDuration",
    "divisionsDeployment",
    "divisionsFinancialResources",
    "divisionsFinancialControl",
    "unitsDeployment",
    "unitsFinancialResources",
    "unitsFinancialControl",
    "attributes",
    "meta",
    "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramVariationInfo extends CommonWithProgramVariationInfo implements ProgramVariation, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ProgramVariationInfo() {
        super();
    }

    public ProgramVariationInfo(ProgramVariation input) {
        super(input);
    }
}
