package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.model.AttributeEntity;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "KSEN_LUI")
public class LuiEntity extends MetaEntity implements AttributeOwner<AttributeEntity>{
    @Column(name = "NAME")
    private String name;
    
	@Column(name = "LUI_CODE")
    private String luiCode;

	//TODO: use CLU
//	@ManyToOne
//	@JoinColumn(name = "CLU_ID")
//	private Clu clu;
    
	@Column(name = "CLU_ID")
	private String cluId;
	
    @ManyToOne
    @JoinColumn(name="ATP_ID")
    private AtpEntity atp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuiRichTextEntity descr;   
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "TYPE_ID")
    private LuiTypeEntity luiType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity luiState;
    
	@Column(name = "MAX_SEATS")
	private Integer maxSeats;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXP_DT")
	private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AttributeEntity> attributes;
    
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

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtpEntity getAtp() {
		return atp;
	}

	public void setAtp(AtpEntity atp) {
		this.atp = atp;
	}

	public LuiRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(LuiRichTextEntity descr) {
		this.descr = descr;
	}

	public LuiTypeEntity getLuiType() {
		return luiType;
	}

	public void setLuiType(LuiTypeEntity luiType) {
		this.luiType = luiType;
	}

	public StateEntity getLuiState() {
		return luiState;
	}

	public void setLuiState(StateEntity luiState) {
		this.luiState = luiState;
	}

	@Override
	public void setAttributes(List<AttributeEntity> attributes) {
		this.attributes = attributes;			
	}

	@Override
	public List<AttributeEntity> getAttributes() {
		return attributes;
	}
    
    
}
