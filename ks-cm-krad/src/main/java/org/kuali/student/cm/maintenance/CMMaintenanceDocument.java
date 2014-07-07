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
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.SaveEvent;
import org.kuali.rice.krad.util.NoteType;

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

    public CMMaintenanceDocument() {
        super();
    }

    public CMMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

    @Override
    public void processAfterRetrieve() {

        if (documentHeader == null || !documentHeader.hasWorkflowDocument()){
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
            if (oldMaintainableObject == null){
                oldMaintainableObject = (CMMaintainable) clazz.newInstance();
                oldMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                oldMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

            if (newMaintainableObject == null){
                newMaintainableObject = (CMMaintainable) clazz.newInstance();
                newMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                newMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(),e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(),e);
        }

        super.processAfterRetrieve();

        ((CMMaintainable) newMaintainableObject).retrieveDataObject();

    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        super.prepareForSave(event);

        if (event instanceof SaveEvent){
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
}
