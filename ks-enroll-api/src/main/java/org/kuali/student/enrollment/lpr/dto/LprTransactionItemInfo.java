package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemResult;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionItemInfo", propOrder = {"id",
    "personId",
    "transactionId",
    "luiId",
    "existingLprId",
    "resultValuesGroupKeys",
    "requestOptions",
    "lprTransactionItemResult",
    "name",
    "descr",
    "typeKey",
    "stateKey",
    "meta",
    "attributes",
    "_futureElements"})
public class LprTransactionItemInfo extends IdEntityInfo implements LprTransactionItem, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String personId;
    @XmlElement
    private String transactionId;
    @XmlElement
    private String luiId;
    @XmlElement
    private String existingLprId;
    @XmlElement
    private List<String> resultValuesGroupKeys;
    @XmlElement
    private List<LprTransactionItemRequestOptionInfo> requestOptions;
    @XmlElement
    private LprTransactionItemResultInfo lprTransactionItemResult;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LprTransactionItemInfo() {
        super();
    }

    public LprTransactionItemInfo(LprTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (null != lprTransactionItem) {
            this.personId = lprTransactionItem.getPersonId();
            this.transactionId = lprTransactionItem.getTransactionId();
            this.luiId = lprTransactionItem.getLuiId();
            this.existingLprId = lprTransactionItem.getExistingLprId();
            
            this.requestOptions = new ArrayList<LprTransactionItemRequestOptionInfo>();
            if (null != lprTransactionItem.getRequestOptions()) {
                for (LprTransactionItemRequestOption reqOp : lprTransactionItem.getRequestOptions()) {
                    this.requestOptions.add(new LprTransactionItemRequestOptionInfo(reqOp));
                }
            }

            this.resultValuesGroupKeys = new ArrayList<String>();
            if (null != lprTransactionItem.getResultValuesGroupKeys()) {
                resultValuesGroupKeys.addAll(lprTransactionItem.getResultValuesGroupKeys());
            }
            LprTransactionItemResult result = lprTransactionItem.getLprTransactionItemResult();
            if (result != null) {
            	// only set the result if there is a result in the item.
            	this.lprTransactionItemResult = new LprTransactionItemResultInfo(result);	
            }

            this._futureElements = null;
        }
    }

    @Override
    public LprTransactionItemResultInfo getLprTransactionItemResult() {
        return lprTransactionItemResult;
    }

    public void setLprTransactionResult(LprTransactionItemResultInfo lprTransactionResult) {
        this.lprTransactionItemResult = lprTransactionResult;
    }

    @Override
    public List<LprTransactionItemRequestOptionInfo> getRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new ArrayList<LprTransactionItemRequestOptionInfo> ();
        }
        return requestOptions;
    }

    public void setRequestOptions(List<LprTransactionItemRequestOptionInfo> requestOptions) {
        this.requestOptions = requestOptions;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public void setExistingLprId(String existingLprId) {
        this.existingLprId = existingLprId;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public String getExistingLprId() {
        return existingLprId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public List<String> getResultValuesGroupKeys() {
        if (this.resultValuesGroupKeys == null) {
            this.resultValuesGroupKeys = new ArrayList<String> ();
        }
        return resultValuesGroupKeys;
    }

    public void setResultValuesGroupKeys(List<String> resultValuesGroupKeys) {
        this.resultValuesGroupKeys = resultValuesGroupKeys;
    }

    public void setLprTransactionItemResult(LprTransactionItemResultInfo lprTransactionItemResult) {
        this.lprTransactionItemResult = lprTransactionItemResult;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionItemInfo [id=");
		builder.append(getId());
		builder.append(", type=");
		builder.append(getTypeKey());
		builder.append(", state=");
		builder.append(getStateKey());
		builder.append(", personId=");
		builder.append(personId);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", luiId=");
		builder.append(luiId);
		builder.append(", existingLprId=");
		builder.append(existingLprId);
		builder.append("]");
		return builder.toString();
	}
    
    
}
