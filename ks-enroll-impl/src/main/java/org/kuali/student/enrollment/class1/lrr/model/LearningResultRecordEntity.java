package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.infc.LearningResultRecord;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_LRR")
public class LearningResultRecordEntity extends MetaEntity implements AttributeOwner<LrrAttributeEntity> {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LrrRichTextEntity descr;

    @Column(name = "TYPE_ID")
    private String lrrType;

    @Column(name = "STATE_ID")
    private String lrrState;

    @Column(name = "LPR_ID")
    private String lprId;

    @Column(name = "RESULT_VALUE_ID")
    private String resultValueId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KSEN_LRR_RES_SRC_RELTN", joinColumns = @JoinColumn(name = "LRR_ID"), inverseJoinColumns = @JoinColumn(name = "RES_SRC_ID"))
    private List<ResultSourceEntity> resultSourceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LrrAttributeEntity> attributes;

    public LearningResultRecordEntity() {

    }

    public LearningResultRecordEntity(LearningResultRecord dto) {
        super(dto);

        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setLprId(dto.getLprId());
        this.setResultValueId(dto.getResultValueKey());
        this.setLrrState(dto.getStateKey());
        this.setLrrType(dto.getTypeKey());
        if(dto.getDescr() != null){
	        this.setDescr(new LrrRichTextEntity(dto.getDescr()));
        }

        this.setAttributes(new HashSet<LrrAttributeEntity>());
        if (null != dto.getAttributes()) {
            for (Attribute att : dto.getAttributes()) {
                this.getAttributes().add(new LrrAttributeEntity(att, this));
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

    public String getLrrType() {
        return lrrType;
    }

    public void setLrrType(String lrrType) {
        this.lrrType = lrrType;
    }

    public String getLrrState() {
        return lrrState;
    }

    public void setLrrState(String lrrState) {
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
    public void setAttributes(Set<LrrAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LrrAttributeEntity> getAttributes() {
        if (attributes == null) {
            attributes = new HashSet<LrrAttributeEntity>();
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
        info.setResultSourceIds(resSource);

        if (getLrrState() != null){
            info.setStateKey(getLrrState());
        }

        if (getLrrType() != null){
            info.setTypeKey(getLrrType());
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
