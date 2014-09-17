/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by delyea on 8/29/14
 */
package org.kuali.student.cm.proposal.form.wrapper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.bind.RequestAccessible;
import org.kuali.rice.krad.web.bind.RequestProtected;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleManagementWrapper;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all the wrappers that are specific to Proposal data that involve
 */
public abstract class ProposalElementsWrapper extends LURuleManagementWrapper implements Serializable {
    private static final String DEFAULT_REQUIRED_WORKFLOW_MODE = "Submit";

    private ProposalInfo proposalInfo = new ProposalInfo();
    private List<CollaboratorWrapper> collaboratorWrappers = new ArrayList<CollaboratorWrapper>();
    private List<String> deletedCollaboratorWrapperActionRequestIds = new ArrayList<String>();
    private List<SupportingDocumentInfoWrapper> supportingDocs = new ArrayList<SupportingDocumentInfoWrapper>();
    private List<SupportingDocumentInfoWrapper> supportingDocsToDelete = new ArrayList<SupportingDocumentInfoWrapper>();

    private String requiredWorkflowMode = DEFAULT_REQUIRED_WORKFLOW_MODE;
    private boolean missingRequiredFields;
    private boolean agendaDirty;

    private transient ProposalUIHelper uiHelper;

    public ProposalElementsWrapper(boolean curriculumSpecialistUser, CurriculumManagementConstants.UserInterfaceSections selectedSection) {
        uiHelper = new ProposalUIHelper(curriculumSpecialistUser, selectedSection);
    }

    public boolean isAgendaDirty() {
        return agendaDirty;
    }

    public void setAgendaDirty(boolean agendaDirty) {
        this.agendaDirty = agendaDirty;
    }

    public List<String> getDeletedCollaboratorWrapperActionRequestIds() {
        return deletedCollaboratorWrapperActionRequestIds;
    }

    public void setDeletedCollaboratorWrapperActionRequestIds(List<String> deletedCollaboratorWrapperActionRequestIds) {
        this.deletedCollaboratorWrapperActionRequestIds = deletedCollaboratorWrapperActionRequestIds;
    }

    /**
     * Flag used on the Review Proposal page to indicate whether the "yellow bar" should be displayed.
     *
     * @return True if the proposal is missing required fields for the next state or routing node. Otherwise, false.
     */
    public boolean isMissingRequiredFields() {
        return missingRequiredFields;
    }

    public void setMissingRequiredFields(boolean missingRequiredFields) {
        this.missingRequiredFields = missingRequiredFields;
    }

    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    public void setProposalInfo(ProposalInfo proposalInfo) {
        this.proposalInfo = proposalInfo;
    }

    public String getRequiredWorkflowMode() {
        return requiredWorkflowMode;
    }

    public void setRequiredWorkflowMode(String requiredWorkflowMode) {
        this.requiredWorkflowMode = requiredWorkflowMode;
    }

    public List<SupportingDocumentInfoWrapper> getSupportingDocs() {
        return supportingDocs;
    }

    public void setSupportingDocs(List<SupportingDocumentInfoWrapper> supportingDocs) {
        this.supportingDocs = supportingDocs;
    }

    public List<SupportingDocumentInfoWrapper> getSupportingDocsToDelete() {
        return supportingDocsToDelete;
    }

    public void setSupportingDocsToDelete(List<SupportingDocumentInfoWrapper> supportingDocsToDelete) {
        this.supportingDocsToDelete = supportingDocsToDelete;
    }

    public ProposalUIHelper getUiHelper() {
        return uiHelper;
    }

    public void setUiHelper(ProposalUIHelper uiHelper) {
        this.uiHelper = uiHelper;
    }

    public List<CollaboratorWrapper> getCollaboratorWrappers() {
        return collaboratorWrappers;
    }

    public void setCollaboratorWrappers(List<CollaboratorWrapper> collaboratorWrappers) {
        this.collaboratorWrappers = collaboratorWrappers;
    }

    public abstract ReviewProposalDisplay getReviewProposalDisplay();

    /**
     * A helper class which holds all the properties needed for display at the ui but not part of the model.
     * As {@link ProposalElementsWrapper} is just a wrapper for @{link ProposalInfo} and it's associated objects
     * and most of the properties are involved in interacting with services later, it's confusing to mix up the
     * ui only properties with those. This separation would help to easily identify which are the ui only properties.
     * Also, the same {@link ProposalElementsWrapper} class can be used at other views, we can have multiple ui helper
     * implementation if needed to support multiple ways to display the same data.
     * <p/>
     * For example, <method>getHeaderText</method> is used to display the header text in the UI view only and not
     * involved in data persistance.
     */
    public class ProposalUIHelper {

        // TODO: Remove this workaround class once KS has been updated to Rice 2.5 (https://jira.kuali.org/browse/KSCM-2560)
        public static final String DIALOG_EXPLANATIONS_PROPERTY = "dialogExplanations";

        private boolean showMessage;

        CurriculumManagementConstants.UserInterfaceSections selectedSection;

        // disallow the curriculumSpecialistUser property to be set by the request
        @RequestProtected
        boolean curriculumSpecialistUser;

        // disallow the useReviewProcess property to be set by the request
        @RequestProtected
        boolean useReviewProcess;

        private boolean modifyWithNewVersionProposal = false;

        // TODO: Remove this workaround class once KS has been updated to Rice 2.5 (https://jira.kuali.org/browse/KSCM-2560)
        @RequestAccessible
        Map<String,String> dialogExplanations;

        // Disallows any workflow action being taken against the document immediately after a workflow action has been performed
        boolean pendingWorkflowAction = false;

        public boolean isPendingWorkflowAction() {
            return pendingWorkflowAction;
        }

        public void setPendingWorkflowAction(boolean pendingWorkflowAction) {
            this.pendingWorkflowAction = pendingWorkflowAction;
        }

        public boolean isShowMessage() {
            return showMessage;
        }

        public void setShowMessage(boolean showMessage) {
            this.showMessage = showMessage;
        }

        public ProposalUIHelper(boolean curriculumSpecialistUser, CurriculumManagementConstants.UserInterfaceSections selectedSection) {
            this.curriculumSpecialistUser = curriculumSpecialistUser;
            this.selectedSection = selectedSection;
            dialogExplanations = new HashMap<>();
        }

        /**
         * A CS not using workflow gets an admin workflow document type. Some UI elements/behavior are conditional based on doc type.
         *
         * @return True if an admin doc type is being used and the current user is a CS user. Otherwise, false.
         */
        public boolean isAdminProposal() {
            return isCurriculumSpecialistUser() && !isUseReviewProcess();
        }

        /**
         * Returns true if the proposal is a modify which will create a new version of the proposed entity (e.g. course).
         */
        public boolean isModifyWithNewVersionProposal() {
            return modifyWithNewVersionProposal;
        }

        public void setModifyWithNewVersionProposal(boolean modifyWithNewVersionProposal) {
            this.modifyWithNewVersionProposal = modifyWithNewVersionProposal;
        }

        public Map<String, String> getDialogExplanations() {
            return dialogExplanations;
        }

        public void setDialogExplanations(Map<String, String> dialogExplanations) {
            this.dialogExplanations = dialogExplanations;
        }

        public void setCurriculumSpecialistUser(boolean curriculumSpecialistUser) {
            this.curriculumSpecialistUser = curriculumSpecialistUser;
        }

        public boolean isCurriculumSpecialistUser() {
            return curriculumSpecialistUser;
        }

        public String getProposalName() {
            return getProposalInfo() != null ? getProposalInfo().getName() : "";
        }

        public boolean isUseReviewProcess() {
            return useReviewProcess;
        }

        public void setUseReviewProcess(boolean useReviewProcess) {
            this.useReviewProcess = useReviewProcess;
        }

        public CurriculumManagementConstants.UserInterfaceSections getSelectedSection() {
            return selectedSection;
        }

        public void setSelectedSection(CurriculumManagementConstants.UserInterfaceSections selectedSection) {
            this.selectedSection = selectedSection;
        }

        public String getHeaderText() {
            String headerSuffixText;

            if (isUseReviewProcess()) {
                headerSuffixText = " (Proposal)";
            } else {
                headerSuffixText = " (Admin Proposal)";
            }

            if (proposalInfo != null && StringUtils.isNotBlank(proposalInfo.getName())) {
                return proposalInfo.getName() + headerSuffixText;
            } else {
                return "New Proposal" + headerSuffixText;
            }
        }

        public boolean isStringExistsInArray(String searchString, String[] stringsArray) {
            return ArrayUtils.contains(stringsArray, searchString);
        }
    }
}
