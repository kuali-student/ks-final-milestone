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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_FEE")
public class CluFee extends MetaEntity implements
		AttributeOwner<CluFeeAttribute> {

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "KSLU_CLU_FEE_JN_CLU_FEE_REC", joinColumns = @JoinColumn(name = "CLU_FEE_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_FEE_REC_ID"))
	private List<CluFeeRecord> cluFeeRecords;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText descr;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluFeeAttribute> attributes;

    @Override
    protected void onPrePersist() {
        super.onPrePersist();
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
	public List<CluFeeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<CluFeeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<CluFeeAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CluFeeRecord> getCluFeeRecords() {
		if(null == cluFeeRecords) {
			this.cluFeeRecords = new ArrayList<CluFeeRecord>();
		}
		return this.cluFeeRecords;
	}

	public void setCluFeeRecords(List<CluFeeRecord> cluFeeRecords) {
		this.cluFeeRecords = cluFeeRecords;
	}

	public LuRichText getDescr() {
		return descr;
	}

	public void setDescr(LuRichText descr) {
		this.descr = descr;
	}
		
}
