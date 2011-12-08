package org.kuali.student.r2.core.proposal.infc;

/**
 * Information about the proposal to document relation.
 * 
 * @author sambit
 * 
 */
public interface ProposalDocRelation {

	/**
	 * Unique identifier for a Proposal.
	 */
	public String getProposalId();

	/**
	 * Unique identifier for a document.
	 */
	public String getDocumentId();

	/**
	 * The title of the document usage in the context of the Proposal.
	 */
	public String getTitle();
}
