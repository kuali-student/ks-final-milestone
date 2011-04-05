package org.kuali.student.lum.lu.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.core.entity.BaseEntity;
import org.kuali.student.core.entity.CurrencyAmount;

@Entity
@Table(name = "KSLU_CLU_FEE_AMOUNT")
public class CluFeeAmount extends BaseEntity{

	@Embedded
	private CurrencyAmount currencyAmount;
	
	public CurrencyAmount getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(CurrencyAmount currencyAmount) {
		this.currencyAmount = currencyAmount;
	}
		
}
