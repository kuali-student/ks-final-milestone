/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.organization.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.organization.infc.Org;
import org.kuali.student.r2.core.organization.infc.OrgCode;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Detailed information about a single organization.
 *
 * @author tom
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgInfo", propOrder = {
    "id", "typeKey", "stateKey", "longName", "longDescr",
    "shortName", "sortName", "shortDescr", "orgCodes",
    "effectiveDate", "expirationDate",
    "meta", "attributes"/*, "_futureElements"*/})
public class OrgInfo
        extends IdNamelessEntityInfo
        implements Org, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String shortName;
    @XmlElement
    private String longName;
    @XmlElement
    private String sortName;
    @XmlElement
    private RichTextInfo longDescr;
    @XmlElement
    private RichTextInfo shortDescr;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlElement
    private List<OrgCodeInfo> orgCodes;
    //    TODO KSCM-372: Non-GWT translatable code
    //@XmlAnyElement
    //private List<Element> _futureElements;

    /**
     * Constructs a new OrgInfo.
     */
    public OrgInfo() {
    }

    /**
     * Constructs a new OrgInfo from an Org.
     *
     * @param org the Org to copy
     */
    public OrgInfo(Org org) {
        super(org);

        if (org != null) {
            this.shortName = org.getShortName();
            this.sortName = org.getSortName();
            this.longName = org.getLongName();
            if (org.getShortDescr() != null) {
                this.shortDescr = new RichTextInfo(org.getShortDescr());
            }
            if (org.getLongDescr() != null) {
                this.longDescr = new RichTextInfo(org.getLongDescr());
            }
            if (org.getEffectiveDate() != null) {
                this.effectiveDate = new Date(org.getEffectiveDate().getTime());
            }

            if (org.getExpirationDate() != null) {
                this.expirationDate = new Date(org.getExpirationDate().getTime());
            }

            if (org.getOrgCodes() != null) {
                this.orgCodes = new ArrayList<OrgCodeInfo>();
                for (OrgCode code : org.getOrgCodes()) {
                    this.orgCodes.add(new OrgCodeInfo(code));
                }
            }
        }
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
    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @Override
    public RichTextInfo getShortDescr() {
        return shortDescr;
    }

    public void setShortDescr(RichTextInfo shortDescr) {
        this.shortDescr = shortDescr;
    }

    @Override
    public RichTextInfo getLongDescr() {
        return longDescr;
    }

    public void setLongDescr(RichTextInfo longDescr) {
        this.longDescr = longDescr;
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
    public List<OrgCodeInfo> getOrgCodes() {
        if (orgCodes == null) {
            orgCodes = new ArrayList<OrgCodeInfo>();
        }
        return orgCodes;
    }

    public void setOrgCodes(List<OrgCodeInfo> orgCodes) {
        this.orgCodes = orgCodes;
    }
}
