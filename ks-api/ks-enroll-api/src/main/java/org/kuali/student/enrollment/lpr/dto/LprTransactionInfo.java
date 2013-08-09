package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (sambit)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "requestingPersonId",
    "atpId",
    "lprTransactionItems",
    "meta",
    "attributes",
    "_futureElements"})
public class LprTransactionInfo extends IdEntityInfo implements LprTransaction, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String requestingPersonId;
    @XmlElement
    private String atpId;
    @XmlElement
    private List<LprTransactionItemInfo> lprTransactionItems;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LprTransactionInfo() {
    }

    public LprTransactionInfo(LprTransaction input) {
        super(input);
        if (input == null) {
            return;
        }
        this.requestingPersonId = input.getRequestingPersonId();
        this.atpId = input.getAtpId();
        this.lprTransactionItems = new ArrayList<LprTransactionItemInfo>();
        if (input.getLprTransactionItems() != null) {
            for (LprTransactionItem item : input.getLprTransactionItems()) {
                this.lprTransactionItems.add(new LprTransactionItemInfo(item));
            }
        }
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItems() {
        if (this.lprTransactionItems == null) {
            this.lprTransactionItems = new ArrayList<LprTransactionItemInfo>();
        }
        return lprTransactionItems;
    }

    public void setLprTransactionItems(List<LprTransactionItemInfo> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    @Override
    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionInfo [id()=");
		builder.append(getId());
		builder.append(", type()=");
		builder.append(getTypeKey());
		builder.append(", state()=");
		builder.append(getStateKey());
		builder.append(", requestingPersonId=");
		builder.append(requestingPersonId);
		builder.append(", atpId=");
		builder.append(atpId);
		builder.append("]");
		return builder.toString();
	}
    
    
}
