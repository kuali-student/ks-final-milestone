/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 6/9/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FinalExamOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient EnumerationManagementService enumerationManagementService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
            List<EnumeratedValueInfo> enumerationInfos = getEnumerationManagementService().getEnumeratedValues("kuali.lu.finalExam.status", null, null, null, ContextUtils.createDefaultContextInfo());
            Collections.sort(enumerationInfos, new FinalExamComparator());

            for(EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                if (enumerationInfo.getCode().equals("STD")) {
                    keyValues.add(new ConcreteKeyValue("STANDARD", enumerationInfo.getValue()));
                } else if (enumerationInfo.getCode().equals("ALT")) {
                    keyValues.add(new ConcreteKeyValue("ALTERNATE", enumerationInfo.getValue()));
                } else if (enumerationInfo.getCode().equals("None")) {
                    keyValues.add(new ConcreteKeyValue("NONE", enumerationInfo.getValue()));
                }
            }
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No subject areas found! There should be some in the database", e);
        } catch (Exception e) {
            throw new RuntimeException("Error looking up Subject Areas", e);
        }

        return keyValues;
    }

    protected EnumerationManagementService getEnumerationManagementService() {
        if(enumerationManagementService == null) {
            enumerationManagementService = (EnumerationManagementService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/enumerationmanagement", "EnumerationManagementService"));
        }
        return this.enumerationManagementService;
    }

    private static class FinalExamComparator implements Comparator, Serializable {

        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof EnumeratedValueInfo) || !(o2 instanceof EnumeratedValueInfo)) {
                throw new ClassCastException("Object not of type EnumeratedValueInfo.");
            }
            EnumeratedValueInfo enumeratedValue1 = (EnumeratedValueInfo) o1;
            EnumeratedValueInfo enumeratedValue2 = (EnumeratedValueInfo) o2;

            int result = enumeratedValue1.getSortKey().compareToIgnoreCase(enumeratedValue2.getSortKey());
            return result;
        }
    }
}