package org.kuali.student.r2.core.classI.atp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.infc.Atp;

@Entity
@Table(name = "KSEN_ATP")
public class AtpEntity extends MetaEntity implements AttributeOwner<AtpAttributeEntity>{
    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private AtpRichTextEntity descr;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DT")
    private Date endDate;
    
    //TODO: 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATP_TYPE_ID")
    private AtpTypeEntity atpType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATP_STATE_ID")
    private AtpStateEntity atpState;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<AtpAttributeEntity> attributes;
  
    
    public AtpEntity(){}
    
    public AtpEntity(Atp atp){
        try{
        this.setId(atp.getKey());
        this.setName(atp.getName());
        
        if (atp.getStartDate() != null)
            this.setStartDate(atp.getStartDate());
        if (atp.getEndDate() != null)
            this.setEndDate(atp.getEndDate());
        if(atp.getDescr() != null)
            this.setDescr(new AtpRichTextEntity(atp.getDescr()));
        
        this.setAttributes(new ArrayList<AtpAttributeEntity>());
        if (null != atp.getAttributes()) {
            for (Attribute att : atp.getAttributes()) {
                this.getAttributes().add(new AtpAttributeEntity(att));
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtpRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(AtpRichTextEntity descr) {
        this.descr = descr;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    public AtpTypeEntity getAtpType() {
        return atpType;
    }

    public void setAtpType(AtpTypeEntity atpType) {
        this.atpType = atpType;
    }

    public AtpStateEntity getAtpState() {
        return atpState;
    }

    public void setAtpState(AtpStateEntity atpState) {
        this.atpState = atpState;
    }

    @Override
    public void setAttributes(List<AtpAttributeEntity> attributes) {
       this.attributes = attributes;
        
    }

    @Override
    public List<AtpAttributeEntity> getAttributes() {
        return attributes;
    }

    public AtpInfo toDto() {
        AtpInfo atp = AtpInfo.newInstance();
        atp.setKey(getId());
        atp.setName(name);
        atp.setStartDate(startDate);
        atp.setEndDate(endDate);
        atp.setTypeKey(atpType.getId());
        atp.setStateKey(atpState.getId());
        atp.setMetaInfo(super.toDTO());
        atp.setDescr(descr.toDto());

        List<Attribute> atts = new ArrayList<Attribute>();
        for (AtpAttributeEntity att : getAttributes()) {
            Attribute attInfo = att.toDto();
            atts.add(attInfo);
        }
        atp.setAttributes(atts);
        
        return atp;
    }
}
