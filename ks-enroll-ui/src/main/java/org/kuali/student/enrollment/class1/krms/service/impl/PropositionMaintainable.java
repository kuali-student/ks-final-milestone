/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.rice.krms.impl.util.KrmsRetriever;

import java.util.Map;

/**
 * {@link org.kuali.rice.krad.maintenance.Maintainable} for the {@link org.kuali.rice.krms.impl.ui.AgendaEditor}
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class PropositionMaintainable extends MaintainableImpl {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(PropositionMaintainable.class);

    public static final String NEW_AGENDA_EDITOR_DOCUMENT_TEXT = "New Agenda Editor Document";

    private transient SequenceAccessorService sequenceAccessorService;

    private transient KrmsRetriever krmsRetriever = new KrmsRetriever();

    /**
     * @return the boService
     */
    public BusinessObjectService getBoService() {
        return KRADServiceLocator.getBusinessObjectService();
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        return super.retrieveObjectForEditOrCopy(document, dataObjectKeys);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterNew(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription(NEW_AGENDA_EDITOR_DOCUMENT_TEXT);
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        super.processAfterEdit(document, requestParameters);
        document.getDocumentHeader().setDocumentDescription("Modify Agenda Editor Document");
    }

    @Override
    public void prepareForSave() {
        super.prepareForSave();
    }

    @Override
    public void saveDataObject() {
        super.saveDataObject();
    }

    @Override
    public boolean isOldDataObjectInDocument() {
        return super.isOldDataObjectInDocument();
    }

    // Since the dataObject is a wrapper class we need to return the agendaBo instead.
    @Override
    public Class getDataObjectClass() {
        return PropositionBo.class;
    }
}
