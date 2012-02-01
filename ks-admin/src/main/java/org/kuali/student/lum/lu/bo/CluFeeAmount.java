package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluFeeAmount extends KsBusinessObjectBase {

    private static final long serialVersionUID = -6424787945578569434L;
    
    
    private String cluFeeRecordId;
    
    @Column(name="CURRENCY_TYPE")
    private String currencyTypeKey;

    @Column(name="CURRENCY_QUANT")
    private Integer currencyQuantity;

    
    public String getCluFeeRecordId() {
        return cluFeeRecordId;
    }

    public void setCluFeeRecordId(String cluFeeRecordId) {
        this.cluFeeRecordId = cluFeeRecordId;
    }

    public String getCurrencyTypeKey() {
        return currencyTypeKey;
    }

    public void setCurrencyTypeKey(String currencyTypeKey) {
        this.currencyTypeKey = currencyTypeKey;
    }

    public Integer getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(Integer currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }

}
