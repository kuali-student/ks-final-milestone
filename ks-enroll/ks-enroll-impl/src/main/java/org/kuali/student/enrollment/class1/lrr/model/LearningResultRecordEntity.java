package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.infc.LearningResultRecord;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.model.StateEntity;

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

    @Column(name = "RESULT_VALUE_KEY")
    private String resultvaluekey;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_LRR_LRC_RELTN", joinColumns = @JoinColumn(name = "LRR_ID"), inverseJoinColumns = @JoinColumn(name = "LRC_ID"))
    private List<String> resultSourceIdList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LrrAttributeEntity> attributes;

    public LearningResultRecordEntity() {

    }

    public LearningResultRecordEntity(LearningResultRecord dto) {
        super(dto);

        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setLprId(dto.getLprId());
        this.setResultvaluekey(dto.getResultValueKey());
        this.setResultSourceIdList(dto.getResultSourceIdList());

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

    public String getResultvaluekey() {
        return resultvaluekey;
    }

    public void setResultvaluekey(String resultvaluekey) {
        this.resultvaluekey = resultvaluekey;
    }

    public List<String> getResultSourceIdList() {
        return resultSourceIdList;
    }

    public void setResultSourceIdList(List<String> resultSourceIdList) {
        this.resultSourceIdList = resultSourceIdList;
    }

    @Override
    public void setAttributes(List<LrrAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<LrrAttributeEntity> getAttributes() {
        return attributes;
    }

    public LearningResultRecordInfo toDto() {

        LearningResultRecordInfo info = new LearningResultRecordInfo();
        info.setId(getId());
        info.setName(getName());

        if (getDescr() != null){
            info.setDescr(getDescr().toDto());
        }

        info.setResultValueKey(getResultvaluekey());
        info.setResultSourceIdList(getResultSourceIdList());

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
