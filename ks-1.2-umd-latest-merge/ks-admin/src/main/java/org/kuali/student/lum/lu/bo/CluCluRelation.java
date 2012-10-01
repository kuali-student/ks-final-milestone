package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.bo.KsMetaInactivatableFromToBase;

public class CluCluRelation extends KsMetaInactivatableFromToBase {

    private static final long serialVersionUID = 8323054855041689038L;

    private String cluId;
    
    @ManyToOne
    @JoinColumn(name="CLU_ID")
    private Clu clu;
    
    private String relatedCluId;
    
    @ManyToOne
    @JoinColumn(name="RELATED_CLU_ID")
    private Clu relatedClu;
    
    private String luLuRelationTypeId;
    
    @ManyToOne
    @JoinColumn(name="LU_RELTN_TYPE_ID")
    private LuLuRelationType luLuRelationType;
    
    @Column(name = "CLU_RELTN_REQ")
    private boolean cluRelationRequired;

    @Column(name = "ST")
    private String state;

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public Clu getClu() {
        return clu;
    }

    public void setClu(Clu clu) {
        this.clu = clu;
    }

    public String getRelatedCluId() {
        return relatedCluId;
    }

    public void setRelatedCluId(String relatedCluId) {
        this.relatedCluId = relatedCluId;
    }

    public Clu getRelatedClu() {
        return relatedClu;
    }

    public void setRelatedClu(Clu relatedClu) {
        this.relatedClu = relatedClu;
    }

    public String getLuLuRelationTypeId() {
        return luLuRelationTypeId;
    }

    public void setLuLuRelationTypeId(String luLuRelationTypeId) {
        this.luLuRelationTypeId = luLuRelationTypeId;
    }

    public LuLuRelationType getLuLuRelationType() {
        return luLuRelationType;
    }

    public void setLuLuRelationType(LuLuRelationType luLuRelationType) {
        this.luLuRelationType = luLuRelationType;
    }

    public boolean isCluRelationRequired() {
        return cluRelationRequired;
    }

    public void setCluRelationRequired(boolean cluRelationRequired) {
        this.cluRelationRequired = cluRelationRequired;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
