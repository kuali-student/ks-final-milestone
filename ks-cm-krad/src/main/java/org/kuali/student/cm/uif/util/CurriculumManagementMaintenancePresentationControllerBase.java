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
 * Created by delyea on 8/4/14
 */
package org.kuali.student.cm.uif.util;

import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceViewPresentationControllerBase;

/**
 * This class is an override so that users will be allowed to 'save' proposal edits while the proposals are ENROUTE
 *
 * @author Kuali Student Team
 */
public class CurriculumManagementMaintenancePresentationControllerBase extends MaintenanceViewPresentationControllerBase {

    /**
     * The parent class only allows saving when the document is not ENROUTE. We need to be able to save
     * while in ENROUTE status.
     */
    @Override
    public boolean canSave(Document document) {
        return getDocumentPresentationController().canSave(document);
    }

    /**
     * The parent class only allows blanket approval when the document is not ENROUTE. We need to be
     * able to blanket approve while in ENROUTE status.
     */
    @Override
    public boolean canBlanketApprove(Document document) {
        WorkflowDocument workflowDocument = document.getDocumentHeader().getWorkflowDocument();

        return (!workflowDocument.isException()) && getDocumentPresentationController().canBlanketApprove(document);
    }

}
