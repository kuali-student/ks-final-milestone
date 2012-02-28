package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * A transaction item represents a request for creating a new relation of a
 * person (student) to a LUI. The transaction item also handle removing,
 * updating, swapping out an old LUI for a new LUI for a person.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface LprTransactionItem extends IdEntity {

    /**
     * The possible types of the LPR transaction item e.g. ADD, UPDATE, DROP,
     * SWAP
     * 
     * @see org.kuali.student.r2.common.infc.HasType#getTypeKey()
     */
    @Override
    public String getTypeKey();

    /**
     * The possible states of the LPR transaction item e.g. DRAFT, SUBMITTED,
     * FAILED etc
     * 
     * @see org.kuali.student.r2.common.infc.HasState#getStateKey()
     */
    @Override
    public String getStateKey();

    /**
     * The person id for the relation request.
     * 
     * @return
     */
    public String getPersonId();

    /**
     * The LUI id for a new relation request.
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
    public List<? extends RequestOption> getRequestOptions();

    /**
     * Returns the transaction item result for this transaction item.
     * 
     * @return
     */
    public LprTransactionItemResult getLprTransactionItemResult();

    /**
     * The keys of the result values groups to be applied to the LPR
     * once created.
     * 
     * For example, setting the grading option to pass/fail or the credits to 3
     * for a course.
     * 
     * @name Result Values Group Keys
     */
    public List<String> getResultValuesGroupKeys();

    /**
     * The transaction item group id to link the LPR transaction items of the
     * related LUIs (if any) created as part of a single transaction item.
     * 
     * @return
     */
    public String getGroupId();

}
