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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.*;


import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;
import org.w3c.dom.Element;

/**
 * Detailed information about major program variations
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "ProgramVariationInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "intensity", "referenceURL", "code", "cip2000Code", "cip2010Code", "hegisCode",
        "universityClassification", "selectiveEnrollmentCode", "resultOptions", "stdDuration", "startTermId", "endTermId", "endProgramEntryTermId", "effectiveDate", "shortTitle", "longTitle",
        "transcriptTitle", "diplomaTitle", "catalogDescr", "catalogPublicationTargets", "learningObjectives", "campusLocations", "programRequirements", "divisionsContentOwner",
        "divisionsStudentOversight", "divisionsDeployment", "divisionsFinancialResources", "divisionsFinancialControl", "unitsContentOwner", "unitsStudentOversight", "unitsDeployment",
        "unitsFinancialResources", "unitsFinancialControl", "meta", "attributes", "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramVariationInfo extends ProgramAttributesInfo implements ProgramVariation, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String intensity;

    @XmlElement
    private String referenceURL;

    @XmlElement
    private List<String> resultOptions;

    @XmlElement
    private TimeAmountInfo stdDuration;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private List<String> campusLocations;

    @XmlElement
    private List<String> divisionsDeployment;

    @XmlElement
    private List<String> divisionsFinancialResources;

    @XmlElement
    private List<String> divisionsFinancialControl;

    @XmlElement
    private List<String> unitsDeployment;

    @XmlElement
    private List<String> unitsFinancialResources;

    @XmlElement
    private List<String> unitsFinancialControl;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ProgramVariationInfo() {
        super();
    }

    public ProgramVariationInfo(ProgramVariation programVariation) {
        this.intensity = programVariation.getIntensity();
        this.referenceURL = programVariation.getReferenceURL();
        this.resultOptions = new ArrayList<String>(programVariation.getResultOptions());
        this.stdDuration = new TimeAmountInfo( programVariation.getStdDuration());
        this.effectiveDate = new Date(programVariation.getEffectiveDate().getTime());
        this.campusLocations = new ArrayList<String>(programVariation.getCampusLocations());
        this.divisionsDeployment = programVariation.getDivisionsDeployment();
        this.divisionsFinancialResources = new ArrayList<String>(programVariation.getDivisionsFinancialResources());
        this.divisionsFinancialControl = programVariation.getDivisionsFinancialControl();
        this.unitsDeployment = new ArrayList<String>( programVariation.getUnitsDeployment());
        this.unitsFinancialResources = new ArrayList<String>(programVariation.getUnitsFinancialResources());
        this.unitsFinancialResources = new ArrayList<String>(programVariation.getUnitsFinancialControl());
    }

    @Override
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    @Override
    public List<String> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<String> resultOptions) {
        this.resultOptions = resultOptions;
    }

    @Override
    public TimeAmountInfo getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(TimeAmountInfo stdDuration) {
        this.stdDuration = stdDuration;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>(0);
        }
        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public List<String> getDivisionsDeployment() {
        return divisionsDeployment;
    }

    public void setDivisionsDeployment(List<String> divisionsDeployment) {
        this.divisionsDeployment = divisionsDeployment;
    }

    @Override
    public List<String> getDivisionsFinancialResources() {
        return divisionsFinancialResources;
    }

    public void setDivisionsFinancialResources(List<String> divisionsFinancialResources) {
        this.divisionsFinancialResources = divisionsFinancialResources;
    }

    @Override
    public List<String> getDivisionsFinancialControl() {
        return divisionsFinancialControl;
    }

    public void setDivisionsFinancialControl(List<String> divisionsFinancialControl) {
        this.divisionsFinancialControl = divisionsFinancialControl;
    }

    @Override
    public List<String> getUnitsDeployment() {
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    @Override
    public List<String> getUnitsFinancialResources() {
        return unitsFinancialResources;
    }

    public void setUnitsFinancialResources(List<String> unitsFinancialResources) {
        this.unitsFinancialResources = unitsFinancialResources;
    }

    @Override
    public List<String> getUnitsFinancialControl() {
        return unitsFinancialControl;
    }

    public void setUnitsFinancialControl(List<String> unitsFinancialControl) {
        this.unitsFinancialControl = unitsFinancialControl;
    }

}
