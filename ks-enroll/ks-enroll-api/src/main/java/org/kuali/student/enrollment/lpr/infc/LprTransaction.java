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
     * The person who requested for this LPR - differenet from the person on the
     * relation.
     * 
     * @return
     */
    public String getRequestingPersonId();

    /**
     * Transaction item for this 
     * 
     * @return
     */
    List<? extends LPRTransactionItem> getLprTransactionItems();

}