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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;

/**
 * 
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class OrganizationTypeValuesFinder extends UifKeyValuesFinderBase {
    
    private static final long serialVersionUID = -5288824839080816629L;
    
    private EnumerationManagementService enumerationManagementService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        final List<KeyValue> options = new ArrayList<KeyValue>();
        try {
            List<EnumeratedValueInfo> enumerationInfos = getEnumerationManagementService().getEnumeratedValues("kuali.lu.adminOrg", "kuali.lu.adminOrg.courseProgramCurriculumOrg", null, null, ContextUtils.createDefaultContextInfo());
            Collections.sort(enumerationInfos, new OrganizationTypeComparator());

            for(EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                options.add(new ConcreteKeyValue(enumerationInfo.getCode(), enumerationInfo.getAbbrevValue()));
            }
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No enumerated values found for type: 'kuali.lu.adminOrg'", e);
        } catch (Exception e) {
            throw new RuntimeException("Error looking up Organization types", e);
        }
        return options;
    }
    
    private static class OrganizationTypeComparator implements Comparator<EnumeratedValueInfo>, Serializable {

        @Override
        public int compare(EnumeratedValueInfo o1, EnumeratedValueInfo o2) {
            int result = o1.getSortKey().compareToIgnoreCase(o2.getSortKey());
            return result;
        }
        
    }
    
    protected EnumerationManagementService getEnumerationManagementService() {
        if(enumerationManagementService == null) {
            enumerationManagementService = GlobalResourceLoader.getService(new QName(EnumerationManagementServiceConstants.NAMESPACE, EnumerationManagementService.class.getSimpleName()));
        }
        return enumerationManagementService;
    }

}
