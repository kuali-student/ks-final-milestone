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

package org.kuali.student.lum.lu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LUI")
@NamedQueries({
	@NamedQuery(name="Lui.getLuisByIdList", query="SELECT l FROM Lui l WHERE l.id IN (:luiIdList)"),
	@NamedQuery(name="Lui.getLuiIdsByCluId", query="SELECT l.id FROM Lui l WHERE l.clu.id = :cluId"),
	@NamedQuery(name="Lui.getLuiIdsInAtpByCluId", query="SELECT l.id FROM Lui l WHERE l.clu.id = :cluId AND l.atpId = :atpKey")
})
public class Lui extends MetaEntity implements AttributeOwner<LuiAttribute> {

	@Id
	@Column(name = "ID")
	private String id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuiAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

	@Column(name = "ATP_ID")
	private String atpId; // Foreign Service Key

	@Column(name = "LUI_CODE")
	private String luiCode;

	@Column(name = "MAX_SEATS")
	private Integer maxSeats;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXP_DT")
	private Date expirationDate;

	@Column(name = "ST")
	private String state;

	@Override
	protected void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	@Override
	public List<LuiAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<LuiAttribute> attributes) {
		this.attributes = attributes;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

	public String getAtpId() {
		return atpId;
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLuiCode() {
		return luiCode;
	}

	public void setLuiCode(String luiCode) {
		this.luiCode = luiCode;
	}

	public Integer getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(Integer maxSeats) {
		this.maxSeats = maxSeats;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
