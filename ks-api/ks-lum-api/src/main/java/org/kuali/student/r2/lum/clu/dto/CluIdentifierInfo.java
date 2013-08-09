/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.clu.infc.CluIdentifier;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Detailed information about the human readable form of a CLU Identifier
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluIdentifierInfo", propOrder = {"id", "typeKey", "stateKey", "code", "shortName", "longName", "level", "division", "variation", "suffixCode", "orgId", "meta", "attributes" , "_futureElements" }) 
public class CluIdentifierInfo extends IdNamelessEntityInfo implements CluIdentifier, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String shortName;

    @XmlElement
    private String longName;

    @XmlElement
    private String level;

    @XmlElement
    private String division;

    @XmlElement
    private String variation;

    @XmlElement
    private String suffixCode;

    @XmlElement
    private String orgId;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CluIdentifierInfo() {

    }

    public CluIdentifierInfo(CluIdentifier cluIdentifier) {
        super(cluIdentifier);
        if (null != cluIdentifier) {
            this.code = cluIdentifier.getCode();
            this.shortName = cluIdentifier.getShortName();
            this.longName = cluIdentifier.getLongName();
            this.level = cluIdentifier.getLevel();
            this.division = cluIdentifier.getDivision();
            this.variation = cluIdentifier.getVariation();
            this.suffixCode = cluIdentifier.getSuffixCode();
            this.orgId = cluIdentifier.getOrgId();
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String getSuffixCode() {
        return suffixCode;
    }

    public void setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
    }

    @Override
    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
