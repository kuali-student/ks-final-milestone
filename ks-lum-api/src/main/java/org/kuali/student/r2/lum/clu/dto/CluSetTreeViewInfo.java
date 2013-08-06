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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.clu.infc.Clu;
import org.kuali.student.r2.lum.clu.infc.CluSetTreeView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluSetTreeViewInfo", propOrder = {"id", "descr", "stateKey", "typeKey", "name", "adminOrg", "isReusable", "isReferenceable", "cluSets", "clus",
        "effectiveDate", "expirationDate", "meta", "attributes" , "_futureElements" }) 
public class CluSetTreeViewInfo extends IdEntityInfo implements CluSetTreeView, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String adminOrg;

    @XmlElement
    private Boolean isReusable;

    @XmlElement
    private Boolean isReferenceable;

    @XmlElement
    private List<CluSetTreeViewInfo> cluSets;

    @XmlElement
    private List<CluInfo> clus;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CluSetTreeViewInfo() {

    }

    public CluSetTreeViewInfo(CluSetTreeView cluSetTreeView) {
        super(cluSetTreeView);
        if (null != cluSetTreeView) {
            this.adminOrg = cluSetTreeView.getAdminOrg();
            this.isReusable = cluSetTreeView.getIsReusable();
            this.isReferenceable = cluSetTreeView.getIsReferenceable();
            this.cluSets = new ArrayList<CluSetTreeViewInfo>();
            for (CluSetTreeView cluSetTreeView1 : cluSetTreeView.getCluSets()) {
                this.cluSets.add(new CluSetTreeViewInfo(cluSetTreeView1));
            }
            this.clus = new ArrayList<CluInfo>();
            for (Clu clu : cluSetTreeView.getClus()) {
                this.clus.add(new CluInfo(clu));
            }
            this.effectiveDate = (null != cluSetTreeView.getEffectiveDate()) ? new Date(cluSetTreeView.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != cluSetTreeView.getExpirationDate()) ? new Date(cluSetTreeView.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public String getAdminOrg() {
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg) {
        this.adminOrg = adminOrg;
    }

    @Override
    public Boolean getIsReusable() {
        return isReusable;
    }

    public void setIsReusable(Boolean isReusable) {
        this.isReusable = isReusable;
    }

    @Override
    public Boolean getIsReferenceable() {
        return isReferenceable;
    }

    public void setIsReferenceable(Boolean isReferenceable) {
        this.isReferenceable = isReferenceable;
    }

    @Override
    public List<CluSetTreeViewInfo> getCluSets() {
        return cluSets;
    }

    public void setCluSets(List<CluSetTreeViewInfo> cluSets) {
        this.cluSets = cluSets;
    }

    @Override
    public List<CluInfo> getClus() {
        return clus;
    }

    public void setClus(List<CluInfo> clus) {
        this.clus = clus;
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
}
