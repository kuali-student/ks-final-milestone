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

package org.kuali.student.core.statement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name="KSST_REQ_COM_TYPE")
public class ReqComponentType extends Type<ReqComponentTypeAttribute> {
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReqComponentTypeAttribute> attributes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReqComponentTypeNLTemplate> nlUsageTemplates;	
	
    @ManyToMany
    @JoinTable(name = "KSST_RCTYP_JN_RCFLDTYP", joinColumns = @JoinColumn(name = "REQ_COMP_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COMP_FIELD_TYPE_ID"))
    private List<ReqComponentFieldType> reqCompFieldTypes;

    public List<ReqComponentTypeAttribute> getAttributes() {
        if(null == attributes) {
            attributes = new ArrayList<ReqComponentTypeAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<ReqComponentTypeAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<ReqComponentFieldType> getReqCompFieldTypes() {
        return reqCompFieldTypes;
    }

    public void setReqCompFieldTypes(List<ReqComponentFieldType> reqCompFieldTypes) {
        this.reqCompFieldTypes = reqCompFieldTypes;
    }

    public List<ReqComponentTypeNLTemplate> getNlUsageTemplates() {
        return nlUsageTemplates;
    }

    public void setNlUsageTemplates(List<ReqComponentTypeNLTemplate> nlUsageTemplates) {
        this.nlUsageTemplates = nlUsageTemplates;
    }

	@Override
	public String toString() {
		return "ReqComponentType[id=" + getId() + "]";
	}
}
