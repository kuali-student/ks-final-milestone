package org.kuali.student.lum.lu.entity;

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
@Table(name="KSLU_REQ_COM_TYPE")
public class ReqComponentType extends Type<ReqComponentTypeAttribute> {
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReqComponentTypeAttribute> attributes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ReqComponentTypeNLTemplate> nlUsageTemplates;	
	
    @ManyToMany
    @JoinTable(name = "KSLU_REQCOMTYP_JN_REQCOMFLDTYP", joinColumns = @JoinColumn(name = "REQ_COMP_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COMP_FIELD_TYPE_ID"))
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
