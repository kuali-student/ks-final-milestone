package org.kuali.student.enrollment.lpr.infc;



public interface LprTransactionItemResult{

    /**
     * The status of the transaction.
     *
     * Returns true in case transaction item had a successful result, false otherwise.
     *
     * @name Status
     * @return
     */
    public Boolean getStatus();

    /**
	 * The resulting LPR for this LPR transaction item if its successful.
     *
     * Returns null if unsuccessful, valid lpr id in case of success
	 *
     * @name Resulting Lpr Id
	 * @return
	 */
	public String getResultingLprId();
	
	/**
	 * The message  for the transaction
     * In case of success, there may still be warning messages
	 *
     * @name Message
	 */
	public String getMessage();
	

}

