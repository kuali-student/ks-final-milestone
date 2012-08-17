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
import org.kuali.student.r2.lum.program.infc.CommonWithProgramVariation;
import org.kuali.student.r2.lum.program.infc.ProgramVariation;

@SuppressWarnings("serial")
@XmlTransient
public class CommonWithProgramVariationInfo extends CommonWithCoreProgramInfo implements ProgramVariation, Serializable {

    @XmlElement
    private String intensity;
    @XmlElement
    private String cip2000Code;
    @XmlElement
    private String cip2010Code;
    @XmlElement
    private String hegisCode;
    @XmlElement
    private String selectiveEnrollmentCode;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private String diplomaTitle;
    @XmlElement
    private List<String> campusLocations;
    @XmlElement
    private List<String> resultOptions;
    @XmlElement
    private TimeAmountInfo stdDuration;
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

    public CommonWithProgramVariationInfo() {
        super();
    }

    public CommonWithProgramVariationInfo(CommonWithProgramVariation input) {
        super (input);
        this.intensity = input.getIntensity();
        this.cip2000Code = input.getCip2000Code();
        this.cip2010Code = input.getCip2010Code();
        this.hegisCode = input.getHegisCode();
        this.selectiveEnrollmentCode = input.getSelectiveEnrollmentCode();
        if (effectiveDate != null) {
            this.effectiveDate = new Date(input.getEffectiveDate().getTime());
        }
        this.diplomaTitle = input.getDiplomaTitle();
        if (input.getCampusLocations() != null) {
            this.campusLocations = new ArrayList<String>(input.getCampusLocations());
        }
        if (input.getResultOptions() != null) {
            this.resultOptions = new ArrayList<String>(input.getResultOptions());
        }
        if (input.getStdDuration() != null) {
            this.stdDuration = new TimeAmountInfo(input.getStdDuration());
        }
        if (input.getDivisionsDeployment() != null) {
            this.divisionsDeployment = new ArrayList<String>(input.getDivisionsDeployment());
        }
        if (input.getDivisionsFinancialResources() != null) {
            this.divisionsFinancialResources = new ArrayList<String>(input.getDivisionsFinancialResources());
        }
        if (input.getDivisionsFinancialControl() != null) {
            this.divisionsFinancialControl = new ArrayList<String>(input.getDivisionsFinancialControl());
        }
        if (input.getUnitsDeployment() != null) {
            this.unitsDeployment = new ArrayList<String>(input.getUnitsDeployment());
        }
        if (input.getUnitsFinancialResources() != null) {
            this.unitsFinancialResources = new ArrayList<String>(input.getUnitsFinancialResources());
        }
        if (input.getUnitsFinancialControl() != null) {
            this.unitsFinancialResources = new ArrayList<String>(input.getUnitsFinancialControl());
        }
    }

    @Override
    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
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
    public String getDiplomaTitle() {
        return diplomaTitle;
    }

    public void setDiplomaTitle(String diplomaTitle) {
        this.diplomaTitle = diplomaTitle;
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

    @Override
    public String getCip2000Code() {
        return cip2000Code;
    }

    public void setCip2000Code(String cip2000Code) {
        this.cip2000Code = cip2000Code;
    }

    @Override
    public String getCip2010Code() {
        return cip2010Code;
    }

    public void setCip2010Code(String cip2010Code) {
        this.cip2010Code = cip2010Code;
    }

    @Override
    public String getHegisCode() {
        return hegisCode;
    }

    public void setHegisCode(String hegisCode) {
        this.hegisCode = hegisCode;
    }

    @Override
    public String getSelectiveEnrollmentCode() {
        return selectiveEnrollmentCode;
    }

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode) {
        this.selectiveEnrollmentCode = selectiveEnrollmentCode;
    }
}
