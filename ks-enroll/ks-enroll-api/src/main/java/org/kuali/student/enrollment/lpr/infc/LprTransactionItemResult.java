package org.kuali.student.enrollment.lpr.infc;

import java.util.List;


public interface LprTransactionItemResult{
	
	/**
	 * 
	 * The resulting LPR for this LPR transaction item if its successful.
	 * 
	 * @return
	 */
	public String getResultingLprId();
	
	/**
	 * 
	 * The messages  for the transaction - success/failure/warning.
	 * 
	 * @return
	 */
	public List<String> getMessages();
	
	/**
	 * The status of the transaction.
	 * 
	 * @return
	 */
	public String getStatus();
	
}

