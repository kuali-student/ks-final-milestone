/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.infc.MeetingSchedule;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.kuali.student.r2.lum.clu.infc.Fee;
import org.kuali.student.r2.lum.clu.infc.LuCode;
import org.kuali.student.r2.lum.clu.infc.Revenue;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiInfo", propOrder = {"id", "typeKey", "stateKey", "name",
    "descr", "effectiveDate", "expirationDate",
    "officialIdentifier", "alternateIdentifiers", "cluId",
    "cluCluRelationIds", "atpId", "luiCodes",
    "maximumEnrollment", "minimumEnrollment", "referenceURL",
    "unitsContentOwner", "unitsDeployment", "resultValuesGroupKeys",
    "relatedLuiTypes",
    "fees", "revenues", "expenditure",
    "meta", "attributes", "meetingSchedules", "_futureElements"})
public class LuiInfo extends IdEntityInfo implements Serializable, Lui {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlElement
    private LuiIdentifierInfo officialIdentifier;
    @XmlElement
    private List<LuiIdentifierInfo> alternateIdentifiers;
    @XmlElement
    private String cluId;
    @XmlElement
    private List<String> cluCluRelationIds;
    @XmlElement
    private String atpId;
    @XmlElement
    private List<LuCodeInfo> luiCodes;
    @XmlElement
    private Integer maximumEnrollment;
    @XmlElement
    private Integer minimumEnrollment;
    @XmlElement
    private String referenceURL;
    @XmlElement
    private List<String> unitsContentOwner;
    @XmlElement
    private List<String> unitsDeployment;
    @XmlElement
    private List<String> resultValuesGroupKeys;
    @XmlElement
    private List<String> relatedLuiTypes;
    @XmlElement
    private List<FeeInfo> fees;
    @XmlElement
    private List<RevenueInfo> revenues;
    @XmlElement
    private ExpenditureInfo expenditure;
    @XmlElement
    private List<MeetingScheduleInfo> meetingSchedules;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiInfo() {
        alternateIdentifiers = new ArrayList<LuiIdentifierInfo>();
        cluCluRelationIds = new ArrayList<String>();
        luiCodes = new ArrayList<LuCodeInfo>();
        unitsDeployment = new ArrayList<String>();
        unitsContentOwner = new ArrayList<String>();
        resultValuesGroupKeys = new ArrayList<String>();
        relatedLuiTypes = new ArrayList<String>();
        fees = new ArrayList<FeeInfo>();
        revenues = new ArrayList<RevenueInfo>();
        this.meetingSchedules = new ArrayList<MeetingScheduleInfo>();
    }

    public LuiInfo(Lui lui) {
        super(lui);

        if (null == lui) {
            return;
        }

        this.effectiveDate = null != lui.getEffectiveDate() ? new Date(lui.getEffectiveDate().getTime()) : null;
        this.expirationDate = null != lui.getExpirationDate() ? new Date(lui.getExpirationDate().getTime()) : null;

        if (lui.getOfficialIdentifier() != null) {
            this.officialIdentifier = new LuiIdentifierInfo(lui.getOfficialIdentifier());
        }
        this.alternateIdentifiers = new ArrayList<LuiIdentifierInfo>();
        if (lui.getAlternateIdentifiers() != null) {
            for (LuiIdentifier li : lui.getAlternateIdentifiers()) {
                this.alternateIdentifiers.add(new LuiIdentifierInfo(li));
            }
        }

        this.cluId = lui.getCluId();
        this.cluCluRelationIds = (null != lui.getCluCluRelationIds() ? new ArrayList<String>(lui.getCluCluRelationIds()) : new ArrayList<String>());
        this.atpId = lui.getAtpId();

        this.luiCodes = new ArrayList<LuCodeInfo>();
        if (lui.getLuiCodes() != null) {
            for (LuCode code : lui.getLuiCodes()) {
                this.luiCodes.add(new LuCodeInfo(code));
            }
        }

        this.maximumEnrollment = lui.getMaximumEnrollment();
        this.minimumEnrollment = lui.getMinimumEnrollment();
        this.referenceURL = lui.getReferenceURL();
        if (lui.getUnitsContentOwner() != null) {
            this.unitsContentOwner = new ArrayList<String>(lui.getUnitsContentOwner());
        }
        this.unitsDeployment = new ArrayList<String>(lui.getUnitsDeployment());
        this.resultValuesGroupKeys = new ArrayList<String>(lui.getResultValuesGroupKeys());
        this.relatedLuiTypes = new ArrayList<String>(lui.getRelatedLuiTypes());

        this.fees = new ArrayList<FeeInfo>();
        if (lui.getFees() != null) {
            for (Fee fee : lui.getFees()) {
                this.fees.add(new FeeInfo(fee));
            }
        }

        this.revenues = new ArrayList<RevenueInfo>();
        if (lui.getRevenues() != null) {
            for (Revenue revenue : lui.getRevenues()) {
                this.revenues.add(new RevenueInfo(revenue));
            }
        }

        if (lui.getExpenditure() != null) {
            this.expenditure = new ExpenditureInfo(lui.getExpenditure());
        }

        this.meetingSchedules = new ArrayList<MeetingScheduleInfo>();
        if (null != lui.getMeetingSchedules()) {
            for (MeetingSchedule m : lui.getMeetingSchedules()) {
                this.meetingSchedules.add(new MeetingScheduleInfo(m));
            }
        }

        this._futureElements = null;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public LuiIdentifierInfo getOfficialIdentifier() {
        return officialIdentifier;
    }

    public void setOfficialIdentifier(LuiIdentifierInfo officialIdentifier) {
        this.officialIdentifier = officialIdentifier;
    }

    @Override
    public List<LuiIdentifierInfo> getAlternateIdentifiers() {
        if (alternateIdentifiers == null) {
            alternateIdentifiers = new ArrayList<LuiIdentifierInfo>();
        }
        return alternateIdentifiers;
    }

    public void setAlternateIdentifiers(List< LuiIdentifierInfo> alternateIdentifiers) {
        this.alternateIdentifiers = alternateIdentifiers;
    }

    @Override
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    @Override
    public List<String> getCluCluRelationIds() {
        if (cluCluRelationIds == null) {
            cluCluRelationIds = new ArrayList<String>();
        }
        return cluCluRelationIds;
    }

    public void setCluCluRelationIds(List<String> cluCluRelationIds) {
        this.cluCluRelationIds = cluCluRelationIds;
    }

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    @Override
    public List<LuCodeInfo> getLuiCodes() {
        if (luiCodes == null) {
            luiCodes = new ArrayList<LuCodeInfo>();
        }
        return luiCodes;
    }

    public void setLuiCodes(List<LuCodeInfo> luiCodes) {
        this.luiCodes = luiCodes;
    }

    @Override
    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    @Override
    public Integer getMinimumEnrollment() {
        return minimumEnrollment;
    }

    public void setMinimumEnrollment(Integer minimumEnrollment) {
        this.minimumEnrollment = minimumEnrollment;
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    @Override
    public List<String> getUnitsDeployment() {
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        if (unitsDeployment == null) {
            unitsDeployment = new ArrayList<String>();
        }
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<String> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    @Override
    public List<String> getResultValuesGroupKeys() {
        if (resultValuesGroupKeys == null) {
            resultValuesGroupKeys = new ArrayList<String>();
        }
        return resultValuesGroupKeys;
    }

    public void setResultValuesGroupKeys(List<String> resultValueGroupKeys) {
        this.resultValuesGroupKeys = resultValueGroupKeys;
    }

    @Override
    public List<String> getRelatedLuiTypes() {
        if (relatedLuiTypes == null) {
            relatedLuiTypes = new ArrayList<String>();
        }
        return relatedLuiTypes;
    }

    public void setRelatedLuiTypes(List<String> relatedLuiTypes) {
        this.relatedLuiTypes = relatedLuiTypes;
    }

    @Override
    public List<FeeInfo> getFees() {
        if (fees == null) {
            fees = new ArrayList<FeeInfo>();
        }
        return fees;
    }

    public void setFees(List<FeeInfo> fees) {
        this.fees = fees;
    }

    @Override
    public List<RevenueInfo> getRevenues() {
        if (revenues == null) {
            revenues = new ArrayList<RevenueInfo>();
        }
        return revenues;
    }

    public void setRevenues(List<RevenueInfo> revenues) {
        this.revenues = revenues;
    }

    @Override
    public ExpenditureInfo getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(ExpenditureInfo expenditure) {
        this.expenditure = expenditure;
    }

    @Override
    public List<MeetingScheduleInfo> getMeetingSchedules() {
        if (meetingSchedules == null) {
            meetingSchedules = new ArrayList<MeetingScheduleInfo>();
        }
        return this.meetingSchedules;
    }

    public void setMeetingSchedules(List<MeetingScheduleInfo> meetingSchedules) {
        this.meetingSchedules = meetingSchedules;
    }
}