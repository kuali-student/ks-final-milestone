package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name="KSEN_LPR_TRANS_ITEM_RQST_OPT")
public class LprRequestOptionEntity extends MetaEntity {

    @Column(name = "OPTION_KEY")
    private String optionKey;

    @Column(name = "OPTION_VALUE")
    private String optionValue;

    @ManyToOne 
    @JoinColumn (name="LPR_TRANS_ITEM_ID")
    private LprTransactionItemEntity lprTransactionItem;
    
    public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

	public LprTransactionItemEntity getLprTransactionItem() {
		return lprTransactionItem;
	}

	public void setLprTransactionItem(LprTransactionItemEntity lprTransactionItem) {
		this.lprTransactionItem = lprTransactionItem;
	}

    
}
