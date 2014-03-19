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
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.krad.bo.PersistableAttachment;
import org.kuali.rice.krad.bo.PersistableAttachmentList;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.SaveEvent;
import org.kuali.student.common.collection.KSCollectionUtils;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */

@Entity
public class CMMaintenanceDocument extends MaintenanceDocumentBase {

    private static final long serialVersionUID = -505085142412593315L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CMMaintenanceDocument.class);

    public CMMaintenanceDocument() {
        super();
    }

    public CMMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

    @Override
    public void processAfterRetrieve() {

        if (documentHeader == null || !documentHeader.hasWorkflowDocument()){
            LOG.error("Document Header or workflow document is null");
            throw new RuntimeException("Document Header or workflow document is null");
        }

        String documentTypeName = documentHeader.getWorkflowDocument().getDocumentTypeName();

        Class clazz = getDocumentDictionaryService().getMaintainableClass(documentTypeName);

        if (!CMMaintainable.class.isAssignableFrom(clazz)) {
            LOG.error("Maintainable should be of CMMaintainable type");
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }

        try {
            oldMaintainableObject = (CMMaintainable) clazz.newInstance();
            newMaintainableObject = (CMMaintainable) clazz.newInstance();

            // initialize maintainable with a data object
            Class<?> dataObjectClazz = getDocumentDictionaryService().getMaintenanceDataObjectClass(documentTypeName);
            oldMaintainableObject.setDataObject(dataObjectClazz.newInstance());
            oldMaintainableObject.setDataObjectClass(dataObjectClazz);
            newMaintainableObject.setDataObject(dataObjectClazz.newInstance());
            newMaintainableObject.setDataObjectClass(dataObjectClazz);
        } catch (InstantiationException e) {
            LOG.error("Unable to initialize maintainables of type " + clazz.getName());
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName());
        } catch (IllegalAccessException e) {
            LOG.error("Unable to initialize maintainables of type " + clazz.getName());
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName());
        }

        super.processAfterRetrieve();

//        Map<String,String> dataObjectDetails = getDataObjectDetailsDeSerialization();

        ((CMMaintainable)newMaintainableObject).retrieveDataObject();

    }

    /*@Override
    public void prepareForSave(KualiDocumentEvent event) {

        super.prepareForSave(event);

        ((CMMaintainable)newMaintainableObject).saveDataObject(this);
        Map<String,String> dataObjectDetails = ((CMMaintainable)newMaintainableObject).prepareDataObjectKeys();

        populateDataObjectDetailsSerialization(dataObjectDetails);
    }*/


    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        super.prepareForSave(event);

        if (event instanceof SaveEvent){
            getNewMaintainableObject().saveDataObject();
        }
    }

    /*protected void populateDataObjectDetailsSerialization(Map<String,String> dataObjectDetails){
        StringBuilder dataObjectBuffer = new StringBuilder();
        if (dataObjectDetails != null){
            Iterator iterator = dataObjectDetails.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                dataObjectBuffer.append(mapEntry.getKey() + "=" + mapEntry.getValue() + ",");
            }
            xmlDocumentContents = StringUtils.stripEnd(dataObjectBuffer.toString(),",");
        }
    }

    protected Map<String,String> getDataObjectDetailsDeSerialization(){
        Map<String,String> dataObjectDetails = new HashMap<String, String>();
        if (!StringUtils.isEmpty(xmlDocumentContents)) {
            String[] xmlData = StringUtils.split(xmlDocumentContents,",");
            for (String data : xmlData){
                String[] keyValue = StringUtils.split(data, "=");
                dataObjectDetails.put(keyValue[0],keyValue[1]);
            }
        }
        return dataObjectDetails;
    }*/

    /**
     * @see org.kuali.rice.krad.document.DocumentBase#doRouteStatusChange(org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange)
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
