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
@Table(name = "KSLU_LU_STMT_TYPE")
public class LuStatementType implements AttributeOwner<LuStatementTypeAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToMany
	@JoinTable(name = "KSLU_LU_STMT_TYPE_JN_LU_TYPE", joinColumns = @JoinColumn(name = "LU_STMT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID"))
	private List<LuType> luTypes;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String desc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DT")
    private Date expirationDate;    
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<LuStatementTypeAttribute> attributes;
    
    @Column(name="STMT_TYPE_KEY", unique=true, nullable=false)
    private String key;
    
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

	public List<LuType> getLuTypes() {
		return luTypes;
	}

	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
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

    public List<LuStatementTypeAttribute> getAttributes() {
        if(attributes==null){
            attributes = new ArrayList<LuStatementTypeAttribute>();
        }        
        return attributes;
    }

    public void setAttributes(List<LuStatementTypeAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }	    
}
