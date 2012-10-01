package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsInactivatableFromToBase;

public class AffiliatedOrg extends KsInactivatableFromToBase {

    private static final long serialVersionUID = 1310490374592949951L;

    @Column(name = "ORG_ID")
    private String orgId; // External service key

    @Column(name = "PERCT")
    private Long percentage;

    
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

}
