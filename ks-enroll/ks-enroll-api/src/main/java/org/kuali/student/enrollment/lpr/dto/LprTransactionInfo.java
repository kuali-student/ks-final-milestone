package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (sambit)
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionInfo", propOrder = {"id", "typeKey", "stateKey", "name", "requestingPersonId", "descr",
        "lprTransactionItems", "meta", "attributes", "_futureElements"})
public class LprTransactionInfo extends IdEntityInfo implements LprTransaction, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String requestingPersonId;

    @XmlElement
    private List<LprTransactionItemInfo> lprTransactionItems;

    @XmlAnyElement
    private List<Element> _futureElements;

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItems() {
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

}
