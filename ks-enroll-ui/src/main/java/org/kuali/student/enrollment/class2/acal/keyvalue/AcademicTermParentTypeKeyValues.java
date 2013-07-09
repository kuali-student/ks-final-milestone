/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 6/12/13
 */
package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by the Academic Calendar to display drop down of parent terms when subterm is selected as a term
 *
 * @author Kuali Student Team
 */
public class AcademicTermParentTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient TypeService typeService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        String childTermType = "";

        if (model instanceof AcademicCalendarForm){
            AcademicCalendarForm acalForm = (AcademicCalendarForm)model;

            AcademicTermWrapper termWrapper = (AcademicTermWrapper) acalForm.getNewCollectionLines().get("termWrapperList");

            if (termWrapper != null) {
                childTermType = termWrapper.getTermType();
            }
        }

        if (childTermType != null && !StringUtils.isBlank(childTermType)) {
            List<TypeInfo> types = new ArrayList<TypeInfo>();
            try {
                ContextInfo context = new ContextInfo();
                // check if child term is subterm or term and if it is (list is not empty) then add all parent terms to types
                List<TypeTypeRelationInfo> typeTypeRelationInfos = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(childTermType, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, context);
                for (TypeTypeRelationInfo typeTypeRelationInfo : typeTypeRelationInfos) {
                    types.add(getTypeService().getType(typeTypeRelationInfo.getOwnerTypeKey(), context));
                }

                for (TypeInfo type : types) {
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (keyValues.isEmpty()) {
            keyValues.add(new ConcreteKeyValue("", "No parent term"));
        }

        return keyValues;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

}
