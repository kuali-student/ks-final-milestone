/**
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.krms.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class created values for the term droplist in krms ui.
 *
 * @author Kuali Student Team
 */
public class ExistingMatrixKeyValueFinder extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private TypeService typeService;
    private RuleManagementService ruleManagementService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        String typeKey = null;
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
        Object dataObject = maintenanceForm.getDocument().getNewMaintainableObject().getDataObject();
        if (dataObject instanceof FERuleManagementWrapper) {
            typeKey = ((FERuleManagementWrapper) dataObject).getType().getKey();
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        try {

            keyValues.add(new ConcreteKeyValue("na", "Select Term Type"));

            for (TypeInfo type : getAcalTermTypes()) {

                //Ignore current type key.
                if (type.getKey().equals(typeKey)) {
                    continue;
                }
                if (ownsAgenda(type)) {
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error getting term key values.", e);
        }

        return keyValues;
    }

    private boolean ownsAgenda(TypeInfo type) {
        //Only add term types that already has a matrix.
        List<ReferenceObjectBinding> refObjects = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(TypeServiceConstants.REF_OBJECT_URI_TYPE, type.getKey());
        if (refObjects.size() > 0) {
            for (ReferenceObjectBinding refObject : refObjects) {
                AgendaDefinition agenda = this.getRuleManagementService().getAgenda(refObject.getKrmsObjectId());
                if (agenda.getAttributes().containsKey(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE)) {
                    String ownerTermType = agenda.getAttributes().get(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE);
                    if (type.getKey().equals(ownerTermType)) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    private List<TypeInfo> getAcalTermTypes() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        return getTypeService().getTypesForGroupType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, ContextUtils.createDefaultContextInfo());
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

}

