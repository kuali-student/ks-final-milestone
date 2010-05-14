/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KSLU_CLU_RSLT")
@NamedQueries( {
	@NamedQuery(name = "CluResult.getCluResultByCluId", query = "SELECT cr FROM CluResult cr WHERE cr.clu.id = :cluId"),
	@NamedQuery(name = "CluResult.getCluIdByResultUsageType", query = "SELECT cr.clu.id FROM CluResult cr INNER JOIN cr.resultOptions res WHERE res.resultUsageType = :resultUsageType"),
	@NamedQuery(name = "CluResult.getCluIdByResultComponentId", query = "SELECT cr.clu.id FROM CluResult cr INNER JOIN cr.resultOptions res WHERE res.resultComponentId = :resultComponentId")
})
public class CluResult extends MetaEntity  {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText desc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

    @OneToMany(cascade=CascadeType.ALL)	
	@JoinTable(name = "KSLU_CLURES_JN_RESOPT", joinColumns = @JoinColumn(name = "CLU_RES_ID"), inverseJoinColumns = @JoinColumn(name = "RES_OPT_ID"))
	private List<ResultOption> resultOptions;

	@Column(name="TYPE")
	private String type;

	@Column(name = "ST")
    private String state;
	

	@Override
    public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public RichText getDesc() {
		return desc;
	}


	public void setDesc(RichText desc) {
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}	
		
}
