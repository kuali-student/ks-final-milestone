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

import org.kuali.student.r2.common.dto.HasAttributesInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;
import org.kuali.student.r2.lum.clu.infc.CluAccounting;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluAccountingInfo", propOrder = {"id", "descr", "affiliatedOrgs", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class CluAccountingInfo extends HasAttributesInfo implements CluAccounting, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String id;
    @XmlElement
    private RichTextInfo descr;
    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public CluAccountingInfo() {

    }

    public CluAccountingInfo(CluAccounting cluAccounting) {
        super(cluAccounting);
        if (null != cluAccounting) {
            this.id = cluAccounting.getId();
            this.affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
            for (AffiliatedOrg affiliatedOrg : cluAccounting.getAffiliatedOrgs()) {
                this.affiliatedOrgs.add(new AffiliatedOrgInfo(affiliatedOrg));
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }


    @Override
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

}
