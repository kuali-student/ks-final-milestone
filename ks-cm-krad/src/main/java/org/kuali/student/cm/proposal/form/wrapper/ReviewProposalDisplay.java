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
 * Created by delyea on 8/31/14
 */
package org.kuali.student.cm.proposal.form.wrapper;

import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Display data for review page for proposals
 */
public class ReviewProposalDisplay {
    private CollaboratorSectionWrapper collaboratorSection;
    private SupportingDocumentsSectionWrapper supportingDocumentsSection;

    private String returnToPreviousNodeName;
    private boolean showUnknownErrors;

    /**
     * If any unknown error occurs when the Controller calls the KS Service Layer validation methods
     * then we show those errors to user, otherwise by default the page level error messages are disabled
     *
     * @return True if unknown error occurs. Otherwise, false.
     */
    public boolean isShowUnknownErrors() {
        return showUnknownErrors;
    }

    public void setShowUnknownErrors(boolean showUnknownErrors) {
        this.showUnknownErrors = showUnknownErrors;
    }

    public String getReturnToPreviousNodeName() {
        return returnToPreviousNodeName;
    }

    public void setReturnToPreviousNodeName(String returnToPreviousNodeName) {
        this.returnToPreviousNodeName = returnToPreviousNodeName;
    }

    public CollaboratorSectionWrapper getCollaboratorSection() {
        if (this.collaboratorSection == null) {
            collaboratorSection = new CollaboratorSectionWrapper();
        }
        return collaboratorSection;
    }

    public SupportingDocumentsSectionWrapper getSupportingDocumentsSection() {
        if (this.supportingDocumentsSection == null) {
            supportingDocumentsSection = new SupportingDocumentsSectionWrapper();
        }
        return supportingDocumentsSection;

    }

    public class SupportingDocumentsSectionWrapper {

        protected List<SupportingDocumentInfoWrapper> supportingDocuments;

        public List<SupportingDocumentInfoWrapper> getSupportingDocuments() {
            if (supportingDocuments == null){
                supportingDocuments = new ArrayList<SupportingDocumentInfoWrapper>();
            }
            return supportingDocuments;
        }

        public void setSupportingDocuments(List<SupportingDocumentInfoWrapper> supportingDocuments) {
            this.supportingDocuments = supportingDocuments;
        }
    }

    public class CollaboratorSectionWrapper {

        private List<CollaboratorWrapper> collaboratorWrappers;

        public List<CollaboratorWrapper> getCollaboratorWrappers() {
            if (collaboratorWrappers == null) {
                collaboratorWrappers = new ArrayList<CollaboratorWrapper>();
            }
            return collaboratorWrappers;
        }

        public void setCollaboratorWrappers(List<CollaboratorWrapper> collaboratorWrappers) {
            this.collaboratorWrappers = collaboratorWrappers;
        }
        /**
         * This is for the authors and collaborators "empty collection" input field. KRAD needs a property name to bind the constraints.
         * Using the same property name for two input fields causes the constraints to be overwritten. Simply setting a
         * KRAD component render=false doesn't prevent it from being considered during validation, so if authors and collaborators are
         * defined there needs to be some text in the input field.
         *
         * @return An empty String if no authors and collaborators have been defined. Otherwise, returns some text.
         */
        public String getEmptyStringAuthorAndCollaborator() {
            if (collaboratorWrappers == null) {
                collaboratorWrappers = new ArrayList<CollaboratorWrapper>();
            }
            return collaboratorWrappers.isEmpty() ? "" : "Has Authors and Collaborators";
        }

    }

}
