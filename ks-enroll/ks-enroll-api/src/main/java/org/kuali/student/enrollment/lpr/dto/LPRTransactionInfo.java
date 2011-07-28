package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LPRTransaction;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (sambit)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LPRTransactionInfo", propOrder = {"id", "typeKey", "stateKey", "name",
        "requestingPersonId", "descr", "lprTransactionItems", "meta", "attributes", "_futureElements"})
public class LPRTransactionInfo extends IdEntityInfo implements LPRTransaction,
        Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String requestingPersonId;

    @XmlElement
    private List<LPRTransactionItemInfo> lprTransactionItems;

    @XmlAnyElement
    private List<Element> _futureElements;

    @Override
    public List<LPRTransactionItemInfo> getLprTransactionItems() {
        return lprTransactionItems;
    }

    public void setLprTransactionItems(List<LPRTransactionItemInfo> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    @Override
    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

}
