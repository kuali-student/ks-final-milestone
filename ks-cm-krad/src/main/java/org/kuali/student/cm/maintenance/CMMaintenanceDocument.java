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
 * Created by venkat on 3/13/14
 */
package org.kuali.student.cm.maintenance;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.rules.rule.event.BlanketApproveDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.SaveEvent;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;

/**
 * Document class for all CM maintenance documents which skips the xml serialization of <class>MaintainableImpl</class>
 * Also, on document save, the data will be stored in KS tables instead of serializing the data to KRNS_MAINT_DOC_T.
 * As we skipped the serialization, on retrieve, this class should take care of loading the data from KS tables
 * whenever we load the maintenace document. This happens at <method>processAfterRetrieve()</method> which should
 * be calling <method>CMMaintainable.retrieveDataObject()</method> to load the data.
 *
 * @author Kuali Student Team
 */

@Entity
public class CMMaintenanceDocument extends MaintenanceDocumentBase {

    private static final long serialVersionUID = -505085142412593315L;
    private static final Logger LOG = LoggerFactory.getLogger(CMMaintenanceDocument.class);

    public CMMaintenanceDocument() {
        super();
    }

    public CMMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

    @Override
    public void processAfterRetrieve() {

        if (documentHeader == null || !documentHeader.hasWorkflowDocument()) {
            throw new RuntimeException("Document Header or workflow document is null");
        }

        String documentTypeName = documentHeader.getWorkflowDocument().getDocumentTypeName();

        Class clazz = getDocumentDictionaryService().getMaintainableClass(documentTypeName);

        if (!CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }

        try {

            Class<?> dataObjectClazz = getDocumentDictionaryService().getMaintenanceDataObjectClass(documentTypeName);

            /**
             * Null check needed here as DocumentServiceImpl.validateAndPersistDocument() calls this method after
             * save. In that case, it's not needed to create a new instance.
             */
            if (oldMaintainableObject == null) {
                oldMaintainableObject = (CMMaintainable) clazz.newInstance();
                oldMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                oldMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

            if (newMaintainableObject == null) {
                newMaintainableObject = (CMMaintainable) clazz.newInstance();
                newMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                newMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        }

        super.processAfterRetrieve();

        ((CMMaintainable) newMaintainableObject).retrieveDataObject();

    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        super.prepareForSave(event);

        if ( (event instanceof SaveEvent) || (event instanceof BlanketApproveDocumentEvent) ) {
            // set the application document id to the proposal id since this is needed for some KEW and KIM processing related to collaborators
            if (getDocumentHeader() != null && getDocumentHeader().getWorkflowDocument() != null && getNewMaintainableObject().getDataObject() != null) {
                if (ProposalMaintainable.class.isAssignableFrom(getNewMaintainableObject().getClass())) {
                    ProposalInfo proposalInfo = ((ProposalMaintainable)getNewMaintainableObject()).getProposalInfo();
                    if (proposalInfo != null) {
                        getDocumentHeader().getWorkflowDocument().setApplicationDocumentId(proposalInfo.getId());
                    } else {
                        LOG.warn("No ProposalInfo object found for document with id: " + getDocumentHeader().getWorkflowDocument().getDocumentId());
                    }
                }
            }
            getNewMaintainableObject().saveDataObject();
        }
    }

    /**
     * These methods deal with (de)serialization of the MaintainableImpl.
     */
    @Override
    public void populateXmlDocumentContentsFromMaintainables() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    @Override
    public void populateMaintainablesFromXmlDocumentContents() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    @Override
    public void doActionTaken(ActionTakenEvent actionTakenEvent) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !ProposalMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of ProposalMaintainable type");
        }
        try {
            ((ProposalMaintainable)getNewMaintainableObject()).doActionTaken(actionTakenEvent);
        } catch (Exception e) {
            LOG.error("Error caught operating on action taken", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !ProposalMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of ProposalMaintainable type");
        }
        try {
            ((ProposalMaintainable)getNewMaintainableObject()).doRouteLevelChange(documentRouteLevelChange);
        } catch (Exception e) {
            LOG.error("Error caught performing route level change", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange documentRouteStatusChange) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !ProposalMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of ProposalMaintainable type");
        }
        try {
            ((ProposalMaintainable)getNewMaintainableObject()).doRouteStatusChange(documentRouteStatusChange);
        } catch (Exception e) {
            LOG.error("Error caught performing route status change", e);
            throw new RuntimeException(e);
        }
    }

}
