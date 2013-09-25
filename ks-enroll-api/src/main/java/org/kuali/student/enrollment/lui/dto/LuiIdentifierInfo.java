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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiIdentifierInfo", propOrder = { "id", "typeKey", "stateKey",
        "code", "shortName", "longName", "level", "division", "variation", "suffixCode",
        "orgId", "meta", "attributes", "_futureElements" })
public class LuiIdentifierInfo 
    extends IdNamelessEntityInfo
    implements LuiIdentifier, Serializable {

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
    private List<Element> _futureElements;


    /**
     *  Constructs a new LuiIdentifierInfo.
     */
    public LuiIdentifierInfo() {
    }

    /**
     *  Constructs a new LuiIdentifierInfo from another LuiIdentifier.
     *
     *  @param luiIdentifier the LuiIdentifier to copy
     */
    public LuiIdentifierInfo(LuiIdentifier luiIdentifier) {
        super(luiIdentifier);

        if(null == luiIdentifier) {
            return;
        }
        this.code = luiIdentifier.getCode();
        this.shortName = luiIdentifier.getShortName();
        this.longName = luiIdentifier.getLongName();
        this.level = luiIdentifier.getLevel();
        this.division = luiIdentifier.getDivision();
        this.suffixCode = luiIdentifier.getSuffixCode();
        this.variation = luiIdentifier.getVariation();
        this.orgId = luiIdentifier.getOrgId();
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