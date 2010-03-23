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
package org.kuali.student.brms.statement.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="KSSTMT_REQ_COM")
@NamedQueries( {
	@NamedQuery(name = "ReqComponent.getReqComponentsByType", query = "SELECT r FROM ReqComponent r WHERE r.requiredComponentType.id = :reqComponentTypeKey"), 
	@NamedQuery(name = "ReqComponent.getReqComponents", query = "SELECT r FROM ReqComponent r WHERE r.id IN (:reqComponentIdList)") 
})
public class ReqComponent extends MetaEntity {
	@Id
	@Column(name = "ID")
	private String id;
	
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private RichText descr;

    @Column(name="ST")
    private String state;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;    

    @ManyToOne
    @JoinColumn(name="REQ_COM_TYPE_ID")
    private ReqComponentType requiredComponentType;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "KSSTMT_RC_JN_RC_FIELD", joinColumns = @JoinColumn(name = "REQ_COM_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COM_FIELD_ID"))
    private List<ReqComponentField> reqCompField;
    
    /**
     * AutoGenerate the Id
     */
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

	public ReqComponentType getRequiredComponentType() {
		return requiredComponentType;
	}

	public void setRequiredComponentType(ReqComponentType requiredComponentType) {
		this.requiredComponentType = requiredComponentType;
	}

    public RichText getDescr() {
        return descr;
    }

    public void setDescr(RichText descr) {
        this.descr = descr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<ReqComponentField> getReqComponentFields() {
        return reqCompField;
    }

    public void setReqComponentFields(List<ReqComponentField> reqCompField) {
        this.reqCompField = reqCompField;
    }

	@Override
	public String toString() {
		return "ReqComponent[id=" + id + ", requiredComponentTypeId="
				+ (requiredComponentType == null ? "null" : requiredComponentType.getId()) + "]";
	}

}
