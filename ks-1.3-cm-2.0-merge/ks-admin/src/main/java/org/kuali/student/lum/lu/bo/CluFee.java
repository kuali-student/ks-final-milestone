package org.kuali.student.lum.lu.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.kuali.student.core.bo.KsMetaBusinessObjectBase;

public class CluFee extends KsMetaBusinessObjectBase {

	private static final long serialVersionUID = -6908845621145340914L;
	
	private String descriptionId;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuRichText description;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "KSLU_CLU_FEE_JN_CLU_FEE_REC", joinColumns = @JoinColumn(name = "CLU_FEE_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_FEE_REC_ID"))
	private List<CluFeeRecord> cluFeeRecords;
	
	public CluFee() {
	    cluFeeRecords = new ArrayList<CluFeeRecord>();
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

    public List<CluFeeRecord> getCluFeeRecords() {
        return cluFeeRecords;
    }

    public void setCluFeeRecords(List<CluFeeRecord> cluFeeRecords) {
        this.cluFeeRecords = cluFeeRecords;
    }

}
