package org.kuali.student.cm.proposal.service;

import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.student.cm.maintenance.CMMaintainable;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

import java.util.List;

/**
 * Interface class for common Proposal methods
 */
public interface ProposalMaintainable extends CMMaintainable {

    public ProposalInfo getProposalInfo();

    public List<CollaboratorWrapper> getCollaboratorWrappersSuggest(String textBoxName);

    /**
     * Updates the objects for the Review Page (if needed) for the Proposal
     *
     * NOTE: Will force a refresh of remote data elements (such as Collaborator data)
     */
    public void updateReview();

    public void populateSupportingDocBytes(SupportingDocumentInfoWrapper supportingDoc);

    public abstract String getProposalReferenceType();

    /**
     * This method builds a proposal copy and returns the wrapper object complete with related objects.
     *
     * @param sourceProposalId
     * @return
     * @throws Exception
     */
    public ProposalElementsWrapper copyProposal(String sourceProposalId) throws Exception;

    public void doActionTaken(ActionTakenEvent actionTakenEvent) throws Exception;

    public void doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) throws Exception;

    public void doRouteStatusChange(DocumentRouteStatusChange documentRouteStatusChange) throws Exception;

    public String findDocumentTypeName(String docId);
}
