package org.kuali.student.lum.lu.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluAccounting extends KsBusinessObjectBase {

    private static final long serialVersionUID = -4881851636576178331L;
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLU_CLU_ACCT_JN_AFFIL_ORG", joinColumns = @JoinColumn(name = "CLU_ACCT_ID"), inverseJoinColumns = @JoinColumn(name = "AFFIL_ORG_ID"))
    private List<AffiliatedOrg> affiliatedOrgs;

    
    public CluAccounting() {
        affiliatedOrgs = new ArrayList<AffiliatedOrg>();
    }
    
    
    public List<AffiliatedOrg> getAffiliatedOrgs() {
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrg> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

}
