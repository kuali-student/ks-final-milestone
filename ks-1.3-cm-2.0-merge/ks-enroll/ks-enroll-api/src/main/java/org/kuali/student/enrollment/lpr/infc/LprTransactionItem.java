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
     * The type of the transaction item.
     * 
     * The types of LPR transaction item  are things like ADD, UPDATE, DROP,
     * and SWAP
     * 
     * @name Type Key
     * @required
     * @readOnly
     */
    @Override
    public String getTypeKey();

    /**
     * The state of this transaction item
     * 
     * The states of the LPR transaction item are things like DRAFT, SUBMITTED,
     * and FAILED. etc
     * 
     * @name State Key
     * @required
     *
     */
    @Override
    public String getStateKey();

    /**
     * The person id for whom this request is to generate or update the lpr
     * 
     * @name Person Id
     * @required
     */
    public String getPersonId();

    /**
     * The identifier of the transaction that contains this transaction item.
     * @name Transaction Id
     * @required
     * @readOnly
     */
    public String getTransactionId ();

    /**
     * The LUI id for a new relation request.
     * 
     * @name New LUI Id
     */
    public String getNewLuiId();

    /**
     * The existing LUI id for an existing relation remove or change requests.
     * 
     * @Existing LUI Id
     */
    public String getExistingLuiId();

    /**
     * Specify the various request (or registration ) options for creating this
     * relationship.
     * 
     * @name Request Options
     */
    public List<? extends LprTransactionItemRequestOption> getRequestOptions();

    /**
     * Returns the result for this transaction item once it has been processed.
     * 
     * @name LPR Transaction Item Result
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

}
