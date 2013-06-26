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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_PUBL")
@NamedQueries( {
    @NamedQuery(name = "CluPublication.findCluPublicationsByType", 
    		   query = "SELECT cp FROM CluPublication cp WHERE cp.type.id = :luPublicationTypeKey"),
    @NamedQuery(name = "CluPublication.findPublicationsByCluId", 
    		   query = "SELECT cp FROM CluPublication cp WHERE cp.clu.id = :cluId")})
public class CluPublication extends MetaEntity implements AttributeOwner<CluPublicationAttribute> {

	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	Clu clu;
	
	@Column(name = "START_CYCLE")
	private String startCycle;

	@Column(name = "END_CYCLE")
	private String endCycle;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluPublicationAttribute> attributes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluPublicationVariant> variants;
	
    @ManyToOne
    @JoinColumn(name = "CLU_PUB_TYPE_ID")
    private CluPublicationType type;

	@Column(name = "ST")
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
	
	public String getStartCycle() {
		return startCycle;
	}

	public void setStartCycle(String startCycle) {
		this.startCycle = startCycle;
	}

	public String getEndCycle() {
		return endCycle;
	}

	public void setEndCycle(String endCycle) {
		this.endCycle = endCycle;
	}

	public List<CluPublicationAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CluPublicationAttribute> attributes) {
		this.attributes = attributes;
	}

	public CluPublicationType getType() {
		return type;
	}

	public void setType(CluPublicationType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setVariants(List<CluPublicationVariant> variants) {
		this.variants = variants;
	}

	public List<CluPublicationVariant> getVariants() {
		return variants;
	}
	
    public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
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
}
