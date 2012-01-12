package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.enrollment.class1.lrc.model.ResultValueEntity;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.infc.LearningResultRecord;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KSEN_LRR")
public class LearningResultRecordEntity extends MetaEntity implements AttributeOwner<LrrAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LrrRichTextEntity descr;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LrrTypeEntity lrrType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity lrrState;

    @Column(name = "LPR_ID")
    private String lprId;

    @Column(name = "RESULT_VALUE_ID")
    private String resultValueId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_LRR_RES_SRC_RELTN", joinColumns = @JoinColumn(name = "LRR_ID"), inverseJoinColumns = @JoinColumn(name = "RES_SRC_ID"))
    private List<ResultSourceEntity> resultSourceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LrrAttributeEntity> attributes;

    public LearningResultRecordEntity() {

    }

    public LearningResultRecordEntity(LearningResultRecord dto) {
        super(dto);

        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setLprId(dto.getLprId());
        this.setResultValueId(dto.getResultValueKey());

        if(dto.getDescr() != null){
	        this.setDescr(new LrrRichTextEntity(dto.getDescr()));
        }

        this.setAttributes(new ArrayList<LrrAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new LrrAttributeEntity(att));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LrrRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(LrrRichTextEntity descr) {
        this.descr = descr;
    }

    public LrrTypeEntity getLrrType() {
        return lrrType;
    }

    public void setLrrType(LrrTypeEntity lrrType) {
        this.lrrType = lrrType;
    }

    public StateEntity getLrrState() {
        return lrrState;
    }

    public void setLrrState(StateEntity lrrState) {
        this.lrrState = lrrState;
    }

    public String getLprId() {
        return lprId;
    }

    public void setLprId(String lprId) {
        this.lprId = lprId;
    }

    public String getResultValueId() {
        return resultValueId;
    }

    public void setResultValueId(String resultValueId) {
        this.resultValueId = resultValueId;
    }

    @Override
    public void setAttributes(List<LrrAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<LrrAttributeEntity> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<LrrAttributeEntity>();
        }
        return attributes;
    }

    public List<ResultSourceEntity> getResultSourceList() {
        return resultSourceList;
    }

    public void setResultSourceList(List<ResultSourceEntity> resultSourceList) {
        this.resultSourceList = resultSourceList;
    }

    public LearningResultRecordInfo toDto() {

        LearningResultRecordInfo info = new LearningResultRecordInfo();
        info.setId(getId());
        info.setLprId(lprId);
        info.setName(getName());

        if (getDescr() != null){
            info.setDescr(getDescr().toDto());
        }

        info.setResultValueKey(getResultValueId());

        List<String> resSource = new ArrayList();
        for(ResultSourceEntity resultSourceEntity : getResultSourceList()){
            resSource.add(resultSourceEntity.getId());
        }
        info.setResultSourceIdList(resSource);

        if (getLrrState() != null){
            info.setStateKey(getLrrState().getId());
        }

        if (getLrrType() != null){
            info.setTypeKey(getLrrType().getId());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LrrAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        info.setMeta(super.toDTO());

        return info;
    }
}
