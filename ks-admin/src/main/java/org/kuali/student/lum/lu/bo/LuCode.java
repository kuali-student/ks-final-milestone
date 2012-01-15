package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.bo.KsMetaBusinessObjectBase;

public class LuCode extends KsMetaBusinessObjectBase {

    private static final long serialVersionUID = -4157117953758049897L;

    @Column(name = "DESCR")
    private String description;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name="CLU_ID")
    private String cluId;

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

}
