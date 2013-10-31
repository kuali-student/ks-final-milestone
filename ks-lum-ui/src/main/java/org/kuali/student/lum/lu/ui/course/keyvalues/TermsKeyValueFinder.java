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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 * 
 */
public class TermsKeyValueFinder extends UifKeyValuesFinderBase {

    private TypeService typeService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<String> typeKeys = new ArrayList<String>();

        typeKeys.add(AtpServiceConstants.ATP_TERM_ALL_TYPE_KEY);
        typeKeys.add(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        typeKeys.add(AtpServiceConstants.ATP_SPRING_TYPE_KEY);
        typeKeys.add(AtpServiceConstants.ATP_SUMMER_TYPE_KEY);
        List<TypeInfo> typeInfos = null;
        try {
            typeInfos = this.getTypeService().getTypesByKeys(typeKeys, ContextUtils.getContextInfo());

            for (TypeInfo result : typeInfos) {
                keyValues.add(new ConcreteKeyValue(result.getKey(), result.getName()));
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve APT season types: "+ e);
        } 
        return keyValues;
    }

    private TypeService getTypeService() {
        if (typeService == null)
        {
            QName qname = new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART);
            typeService = (TypeService) GlobalResourceLoader.getService(qname);
        }
        return typeService;
    }

}
