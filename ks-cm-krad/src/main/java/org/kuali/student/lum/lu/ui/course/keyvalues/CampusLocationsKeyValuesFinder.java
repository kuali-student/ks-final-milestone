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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import javax.xml.namespace.QName;

import static java.util.Collections.sort;

/**
 * Campus location values finder
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class CampusLocationsKeyValuesFinder extends UifKeyValuesFinderBase {
    protected static final String CAMPUS_LOCATION_ENUM_KEY = "kuali.lu.campusLocation";
    private static final long serialVersionUID = -1L;

    private transient EnumerationManagementService enumerationManagementService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        final List<KeyValue> options = new ArrayList<KeyValue>();
        try {
            final List<EnumeratedValueInfo> enumerationInfos = 
                getEnumerationManagementService().getEnumeratedValues
                (CAMPUS_LOCATION_ENUM_KEY, null, null, null, ContextUtils.createDefaultContextInfo());

            sort(enumerationInfos, new Comparator<EnumeratedValueInfo>() {
                    @Override
                    public int compare(final EnumeratedValueInfo o1, final EnumeratedValueInfo o2) {                        
                        return o1.getSortKey().compareToIgnoreCase(o2.getSortKey());
                    }
                });
            
            for(final EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                options.add(new ConcreteKeyValue(enumerationInfo.getCode(), enumerationInfo.getValue()));
            }
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No subject areas found! There should be some in the database", e);
        } catch (Exception e) {
            throw new RuntimeException("Error looking up Campus Locations", e);
        }
        
        return options;
    }

    protected EnumerationManagementService getEnumerationManagementService() {
        if(enumerationManagementService == null) {
            enumerationManagementService = (EnumerationManagementService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/enumerationmanagement", "EnumerationManagementService"));
        }
        return this.enumerationManagementService;
    }
}
