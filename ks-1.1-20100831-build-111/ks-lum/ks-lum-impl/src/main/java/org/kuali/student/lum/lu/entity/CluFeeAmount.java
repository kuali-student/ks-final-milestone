package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.CurrencyAmount;

@Entity
@Table(name = "KSLU_CLU_FEE_AMOUNT")
public class CluFeeAmount {

	@Id
	@Column(name = "ID")
	private String id;

	@Embedded
	private CurrencyAmount currencyAmount;
	
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CurrencyAmount getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(CurrencyAmount currencyAmount) {
		this.currencyAmount = currencyAmount;
	}
		
}
