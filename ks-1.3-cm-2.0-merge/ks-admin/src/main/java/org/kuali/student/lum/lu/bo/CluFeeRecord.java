package org.kuali.student.lum.lu.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.kuali.student.core.bo.KsMetaBusinessObjectBase;

public class CluFeeRecord extends KsMetaBusinessObjectBase {

	private static final long serialVersionUID = 2893683769874687020L;
	
	@Column(name = "FEE_TYPE")
	private String feeType;

	@Column(name = "RATE_TYPE")
	private String rateType;

	private String descriptionId;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText description;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name= "CLUE_FEE_REC_ID")
    private List<CluFeeAmount> feeAmounts;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "KSLU_CLU_FEEREC_JN_AFFIL_ORG", joinColumns = @JoinColumn(name = "CLU_FEE_REC_ID"), inverseJoinColumns = @JoinColumn(name = "AFFIL_ORG_ID"))
	private List<AffiliatedOrg> affiliatedOrgs;

	
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
    }

    public LuRichText getDescription() {
        return description;
    }

    public void setDescription(LuRichText description) {
        this.description = description;
    }

    public List<CluFeeAmount> getFeeAmounts() {
        return feeAmounts;
    }

    public void setFeeAmounts(List<CluFeeAmount> feeAmounts) {
        this.feeAmounts = feeAmounts;
    }

    public List<AffiliatedOrg> getAffiliatedOrgs() {
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrg> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

}
