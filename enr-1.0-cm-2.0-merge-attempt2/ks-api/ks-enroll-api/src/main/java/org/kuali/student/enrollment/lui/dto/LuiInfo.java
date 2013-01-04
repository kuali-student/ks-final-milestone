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
    "cluCluRelationIds", "atpId", "campusLocations", "scheduleId", "luiCodes", 
    "maximumEnrollment", "minimumEnrollment", "referenceURL",
    "unitsContentOwner", "unitsDeployment", "resultValuesGroupKeys",
    "relatedLuiTypes",
    "meta", "attributes", "_futureElements"})

public class LuiInfo 
    extends IdEntityInfo 
    implements Serializable, Lui {

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
    private List<String> campusLocations;

    @XmlElement
    private String scheduleId;

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

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     *  Constructs a new LuiInfo.
     */
    public LuiInfo() {
    }

    
    /**
     *  Constructs a new LuiInfo from another Lui.
     *
     *  @param lui the LUI to copy
     */
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
        
        if (lui.getCampusLocations() != null) {
            this.campusLocations = new ArrayList(lui.getCampusLocations());
        }

        this.scheduleId = lui.getScheduleId();
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
    public List<String> getCampusLocations() {
        if (campusLocations == null) {
            campusLocations = new ArrayList<String>();
        }

        return campusLocations;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
    }

    @Override
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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
        if (unitsDeployment == null) {
            unitsDeployment = new ArrayList<String>();
        }
        return unitsDeployment;
    }

    public void setUnitsDeployment(List<String> unitsDeployment) {
        this.unitsDeployment = unitsDeployment;
    }

    @Override
    public List<String> getUnitsContentOwner() {
        if (unitsContentOwner == null) {
            unitsContentOwner = new ArrayList<String>();
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
}