package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluAtpTypeKey extends KsBusinessObjectBase {

    private static final long serialVersionUID = -993027818102112799L;

    @ManyToOne
    @JoinColumn(name = "CLU_ID")
    private String cluId;
    
    @Column(name = "ATP_TYPE_KEY")
    private String atpTypeKey;

    
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getAtpTypeKey() {
        return atpTypeKey;
    }

    public void setAtpTypeKey(String atpTypeKey) {
        this.atpTypeKey = atpTypeKey;
    }

}
