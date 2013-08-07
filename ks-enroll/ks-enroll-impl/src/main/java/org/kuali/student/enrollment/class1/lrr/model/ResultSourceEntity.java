package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_LRR_RES_SOURCE")
public class ResultSourceEntity extends MetaEntity implements AttributeOwner<ResultSourceAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private ResultSourceRichTextEntity descr;

    @Column(name = "TYPE_ID")
    private String type;

    @Column(name = "ARTICULATE_ID")
    private String articulationId;

    @Column(name = "RES_TRANS_ID")
    private String resultTransformationId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<ResultSourceAttributeEntity> attributes;

    public ResultSourceEntity(){

    }

    public ResultSourceEntity(ResultSourceInfo dto){
        super(dto);
        setName(dto.getName());
        setId(dto.getId());
        setArticulationId(dto.getArticulationId());
        setResultTransformationId(dto.getResultTransformationId());
        if(dto.getDescr() != null){
	        this.setDescr(new ResultSourceRichTextEntity(dto.getDescr()));
        }

        this.setAttributes(new HashSet<ResultSourceAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new ResultSourceAttributeEntity(att, this));
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultSourceRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(ResultSourceRichTextEntity descr) {
        this.descr = descr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArticulationId() {
        return articulationId;
    }

    public void setArticulationId(String articulationId) {
        this.articulationId = articulationId;
    }

    public String getResultTransformationId() {
        return resultTransformationId;
    }

    public void setResultTransformationId(String resultTransformationId) {
        this.resultTransformationId = resultTransformationId;
    }

    @Override
    public void setAttributes(Set<ResultSourceAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<ResultSourceAttributeEntity> getAttributes() {
        return attributes;
    }

    public ResultSourceInfo toDto() {
        ResultSourceInfo dto = new ResultSourceInfo();
        dto.setId(getId());
        dto.setName(getName());

        dto.setArticulationId(getArticulationId());
        dto.setResultTransformationId(getResultTransformationId());

        if (getDescr() != null){
            dto.setDescr(getDescr().toDto());
        }

        if (getType() != null){
            dto.setTypeKey(getType());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (ResultSourceAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        dto.setAttributes(atts);
        dto.setMeta(super.toDTO());

        return dto;
    }
}
