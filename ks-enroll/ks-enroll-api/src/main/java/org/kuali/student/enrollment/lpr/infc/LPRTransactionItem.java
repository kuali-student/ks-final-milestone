package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.Entity;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * A transaction item represents a request for creating a new relation of a
 * person (student) to a LUI. The transaction item also handle removing,
 * updating, swapping out an old LUI for a new LUI for a person.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface LprTransactionItem extends Entity {

    /**
     * The person id for the relation request.
     * 
     * @return
     */
    public String getPersonId();

    /**
     * The  LUI id for a new relation request.
     * 
     * @return
     */
    public String getNewLuiId();

    /**
     * The existing LUI id for an existing relation remove or change requests.
     * 
     * @return
     */
    public String getExistingLuiId();

    /**
     * Specify the various request (or registration ) options for creating this
     * relationship.
     * 
     * @return
     */
    public List<RequestOption> getRequestOptions();

    /**
     * 
     * Returns the transaction item result for this transaction item.
     * 
     * @return
     */
    public LprTransactionItemResult getLprTransactionItemResult(); 
    
}
