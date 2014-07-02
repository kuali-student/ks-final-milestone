package org.kuali.student.cm.rice;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.rice.krad.data.metadata.DataObjectMetadata;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.impl.KRADLegacyDataAdapterImpl;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.student.cm.course.form.CourseInfoWrapper;
import org.kuali.student.cm.course.service.impl.CourseInfoMaintainableImpl;
import org.kuali.student.cm.maintenance.CMMaintenanceDocument;
import org.springframework.beans.PropertyAccessorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of {@link org.kuali.rice.krad.service.impl.KRADLegacyDataAdapterImpl} to work around rice bug
 * TODO: KSENROLL-12567 remove class KSKRADLegacyDataAdapterImpl
 *
 * Also, this file contains overrides to allow CM KRAD to work with a remote Rice.
 * TODO: KSCM-2322 - Come up with a cleaner way to accomplish this.
 *
 */
public class KSKRADLegacyDataAdapterImpl extends KRADLegacyDataAdapterImpl {
    @Override
    public Map<String, String> getInquiryParameters(Object dataObject, List<String> keys, String propertyName) {
        Map<String, String> inquiryParameters = new HashMap<String, String>();
        DataObjectMetadata objectMetadata =
                KRADServiceLocator.getDataObjectService().getMetadataRepository().getMetadata(dataObject.getClass());
        org.kuali.rice.krad.data.metadata.DataObjectRelationship dataObjectRelationship = null;
        // KS customization: added null check for objectMetadata
        if (objectMetadata != null) {
            objectMetadata.getRelationshipByLastAttributeInRelationship(propertyName);
        }
        for (String keyName : keys) {
            String keyConversion = keyName;
            if (dataObjectRelationship != null) {
                keyConversion = dataObjectRelationship.getParentAttributeNameRelatedToChildAttributeName(keyName);
            } else if (PropertyAccessorUtils.isNestedOrIndexedProperty(propertyName)) {
                String nestedAttributePrefix = KRADUtils.getNestedAttributePrefix(propertyName);
                keyConversion = nestedAttributePrefix + "." + keyName;
            }
            inquiryParameters.put(keyConversion, keyName);
        }
        return inquiryParameters;
    }

    @Override
    public <T extends org.kuali.rice.krad.document.Document> T findByDocumentHeaderId(Class<T> documentClass, String id) {
        if (documentClass.equals(CMMaintenanceDocument.class)) {
            /*
             *  Special handling for CMMaintenanceDocuments to get around problems with remote Rice.
             *
             *  Perform the query using MaintenanceDocumentBase then copy the important parts into a new
             *  CMMaintenanceDocument.
             *
             */
            Class c = MaintenanceDocumentBase.class;

            Document result = super.findByDocumentHeaderId(c, id);

            CMMaintenanceDocument newDoc = new CMMaintenanceDocument(CourseInfoMaintainableImpl.class.getName());
            newDoc.setDocumentNumber(result.getDocumentNumber());
            newDoc.setDocumentHeader(result.getDocumentHeader());
            newDoc.setVersionNumber(result.getVersionNumber());

            return (T) newDoc;
        }

        return super.findByDocumentHeaderId(documentClass, id);
    }

    @Override
    public <T extends org.kuali.rice.krad.document.Document> List<T> findByDocumentHeaderIds(Class<T> documentClass, List<String> ids) {
        if (documentClass.equals(CMMaintenanceDocument.class)) {
            /*
             *  Special handling for CMMaintenanceDocuments to get around problems with remote Rice.
             *
             *  Simply perform the query using MaintenanceDocumentBase.
             */
            Class c = MaintenanceDocumentBase.class;
            return super.findByDocumentHeaderIds(c, ids);
        }

        return super.findByDocumentHeaderIds(documentClass, ids);
    }

    @Override
    public <T> T saveDocument(T document) {
        if (document.getClass().equals(CMMaintenanceDocument.class)) {
            /*
             *  Special handling for CMMaintenanceDocuments to get around problems with remote Rice.
             *
             *  Create a new MaintenanceDocumentBase and copy the important parts from the document into
             *  the new class. Save the MDB, but return the original CMMaintenanceDocument.
             */
            MaintenanceDocumentBase newDoc = new MaintenanceDocumentBase(MaintainableImpl.class.getName());

            newDoc.setNewMaintainableObject(((MaintenanceDocumentBase) document).getNewMaintainableObject());
            newDoc.setOldMaintainableObject(((MaintenanceDocumentBase) document).getOldMaintainableObject());

            newDoc.setDocumentHeader(((MaintenanceDocumentBase) document).getDocumentHeader());
            newDoc.setDocumentNumber(((MaintenanceDocumentBase) document).getDocumentNumber());

            newDoc.setVersionNumber(((CMMaintenanceDocument) document).getVersionNumber());

            MaintenanceDocumentBase savedDoc = super.saveDocument(newDoc);

            ((CMMaintenanceDocument) document).setVersionNumber(savedDoc.getVersionNumber());

            return document;
        }
        return super.saveDocument(document);
    }
}
