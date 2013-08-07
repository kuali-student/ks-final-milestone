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

package org.kuali.student.r2.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_RSLT")
@NamedQueries( {
        @NamedQuery(name = "CluResult.getCluResultByCluId", query = "SELECT cr FROM CluResult cr WHERE cr.clu.id = :cluId"),
        @NamedQuery(name = "CluResult.getCluResultsByCluIds", query = "SELECT cr FROM CluResult cr WHERE cr.clu.id in (:cluIds)"),
        @NamedQuery(name = "CluResult.getCluIdByResultUsageType", query = "SELECT cr.clu.id FROM CluResult cr INNER JOIN cr.resultOptions res WHERE res.resultUsageType = :resultUsageType"),
        @NamedQuery(name = "CluResult.getCluIdByResultComponentId", query = "SELECT cr.clu.id FROM CluResult cr INNER JOIN cr.resultOptions res WHERE res.resultComponentId = :resultComponentId")
})
public class CluResult extends MetaEntity  {

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText desc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "CLU_ID")
    private Clu clu;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLURES_JN_RESOPT", joinColumns = @JoinColumn(name = "CLU_RES_ID"), inverseJoinColumns = @JoinColumn(name = "RES_OPT_ID"))
    private List<ResultOption> resultOptions;

    @ManyToOne
    @JoinColumn(name="TYPE_KEY_ID")
    private CluResultType cluResultType;

    @Column(name = "ST")
    private String state;

    public LuRichText getDesc() {
        return desc;
    }

    public void setDesc(LuRichText desc) {
        this.desc = desc;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Clu getClu() {
        return clu;
    }

    public void setClu(Clu clu) {
        this.clu = clu;
    }

    public List<ResultOption> getResultOptions() {
        return resultOptions;
    }

    public void setResultOptions(List<ResultOption> resultOptions) {
        this.resultOptions = resultOptions;
    }

    public CluResultType getCluResultType() {
        return cluResultType;
    }

    public void setCluResultType(CluResultType type) {
        this.cluResultType = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
