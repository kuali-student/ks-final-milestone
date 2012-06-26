package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * The LuiPersonRelationTransaction represents an object to capture an overall
 * transaction request. The requesting person ID is the person who creates this
 * overall request. There are multiple transaction items,
 * {@link LprTransactionItem}, in a single overall transaction.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface LprTransaction extends IdEntity {
    /**
     * The person who requested for this transaction
     * 
     * Note this does not have to be the same as the person on the transaction 
     * item. It could be, for example, an administrator creating registrations 
     * on behalf of the student.
     * 
     * @required
     * @name Requesting Person Id
     */
    public String getRequestingPersonId();

    
    /**
     * The ATP for which this transaction applies.
     * 
     * This constrains which lui's can be manipulated by this transaction's items. 
     * The items may only refer to this ATP or nested ATPs.
     * 
     * @required
     * @name ATP Id
     */
    public String getAtpId();
    
    /**
     * Transaction item for this 
     * 
     * @name LPR Transaction Items
     */
    List<? extends LprTransactionItem> getLprTransactionItems();

}