package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name="KSLU_REQ_COM_TYPE")
public class ReqComponentType implements AttributeOwner<ReqComponentTypeAttribute> {

    @Id
    @Column(name = "ID")
    private String id;
	
    @Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String desc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;    
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<ReqComponentTypeAttribute> attributes;
    
    @Column(name="REQ_COM_TYPE_KEY", unique=true, nullable=false)
    private String key;

    @ManyToMany
    @JoinTable(name = "KSLU_REQCOMTYP_JN_REQCOMFLDTYP", joinColumns = @JoinColumn(name = "REQ_COM_FIELD_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COM_TYPE_ID"))
    private List<ReqComponentFieldType> reqCompFieldTypes;

    /**
     * AutoGenerate the Id
     */
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
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

    public List<ReqComponentTypeAttribute> getAttributes() {
        if(null == attributes) {
            attributes = new ArrayList<ReqComponentTypeAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<ReqComponentTypeAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ReqComponentFieldType> getReqCompFieldTypes() {
        return reqCompFieldTypes;
    }

    public void setReqCompFieldTypes(List<ReqComponentFieldType> reqCompFieldTypes) {
        this.reqCompFieldTypes = reqCompFieldTypes;
    }	
}
