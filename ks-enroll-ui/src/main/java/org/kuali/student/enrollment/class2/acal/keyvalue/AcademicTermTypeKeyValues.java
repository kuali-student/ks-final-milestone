/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Keyfinder to display a list of available term types
 *
 * This filters out types already on the form as well as any subterms where the parent term is not on the form
 *
 * @author Kuali Student Team
 */
public class AcademicTermTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient TypeService typeService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //Grab a list of current term types
        List<String> currentTermTypes = new ArrayList<String>();
       // Added below if check to avoid class cast exception on Window registration page.
        if (model instanceof RegistrationWindowsManagementForm){
            for (AppointmentWindowWrapper termWrapper : ((RegistrationWindowsManagementForm) model).getAppointmentWindows()) {
                currentTermTypes.add(termWrapper.getTermType());
            }
        } else if (model instanceof AcademicCalendarForm){
            for (AcademicTermWrapper termWrapper : ((AcademicCalendarForm) model).getTermWrapperList()) {
                currentTermTypes.add(termWrapper.getTermType());
            }

        }

        try {
            //Get the subterm types
            List<String> subtermTypeKeys = getSubtermTypeKeys();

            keyValues.add(new ConcreteKeyValue("", "Select Term Type"));

            for (TypeInfo type : getAcalTermTypes()) {
                //Filter the keyvalues to terms that are not already in the form and if they are subterms,
                // the parent term must exist in form
                if (!currentTermTypes.contains(type.getKey()) &&
                        (!subtermTypeKeys.contains(type.getKey()) || parentExists(type.getKey(), currentTermTypes))) {
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

    /**
     * @return List of subterm type keys from the type service
     */
    private List<String> getSubtermTypeKeys() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> subtermTypeKeys = new ArrayList<String>();
        List<TypeInfo> subtermTypes = getTypeService().getTypesForGroupType(AtpServiceConstants.ATP_SUBTERM_GROUPING_TYPE_KEY, ContextUtils.createDefaultContextInfo());
        if (subtermTypes != null) {
            for (TypeInfo subtermType : subtermTypes) {
                subtermTypeKeys.add(subtermType.getKey());
            }
        }
        return subtermTypeKeys;
    }

    /**
     * Checks if the subterm key has a parent term from currentTermTypes
     * This is used to restrict subterm values that don't have existing parent terms
     *
     * @param subtermKey       subterm key you are check
     * @param currentTermTypes list of existing term types
     * @return true if the current TermTypes contains the parent Term of the subterm key
     */
    private boolean parentExists(String subtermKey, List<String> currentTermTypes) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        //Get a list of parent terms from the type service
        List<TypeTypeRelationInfo> subtermParentTypeRelations = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(subtermKey, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, ContextUtils.createDefaultContextInfo());
        if (subtermParentTypeRelations != null) {
            //Check that the parent term exists (a parent term will have a type relation to the subterm
            for (TypeTypeRelationInfo subtermParentTypeRelation : subtermParentTypeRelations) {
                if (currentTermTypes.contains(subtermParentTypeRelation.getOwnerTypeKey())) {
                    return true;
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
}