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
 * Created by vgadiyak on 6/9/12
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
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 * 
 */

public class FinalExamKeyValueFinder extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient EnumerationManagementService enumerationManagementService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
            List<EnumeratedValueInfo> enumerationInfos = getEnumerationManagementService().getEnumeratedValues(
                    CluServiceConstants.FINAL_EXAM_STATUS_ENUM_KEY, null, null, null, ContextUtils.getContextInfo());
            Collections.sort(enumerationInfos, new FinalExamComparator());

            for (EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                if (enumerationInfo.getCode().equals(CourseServiceConstants.ALT_EXAM_FINAL_ENUM_KEY)) {
                    keyValues.add(new ConcreteKeyValue(CourseServiceConstants.ALTERNATE_STRING_EXAM_FINAL_ENUM, enumerationInfo.getValue()));
                } else if (enumerationInfo.getCode().equals(CourseServiceConstants.NONE_EXAM_ENUM_KEY)) {
                    keyValues.add(new ConcreteKeyValue(CourseServiceConstants.NONE_STRING_EXAM_ENUM, enumerationInfo.getValue()));
                } else if (enumerationInfo.getCode().equals(CourseServiceConstants.STD_EXAM_FINAL_ENUM_KEY)) {
                    keyValues.add(new ConcreteKeyValue(CourseServiceConstants.STANDARD_STRING_EXAM_ENUM, enumerationInfo.getValue()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error looking up Subject Areas", e);
        }

        return keyValues;
    }

    protected EnumerationManagementService getEnumerationManagementService() {
        if (enumerationManagementService == null) {
            enumerationManagementService = (EnumerationManagementService) GlobalResourceLoader.getService(new QName(
                    EnumerationManagementServiceConstants.NAMESPACE, EnumerationManagementServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.enumerationManagementService;
    }

    private static class FinalExamComparator implements Comparator<EnumeratedValueInfo> {

        @Override
        public int compare(EnumeratedValueInfo o1, EnumeratedValueInfo o2) {

            int result = o1.getSortKey().compareToIgnoreCase(o2.getSortKey());
            return result;
        }
    }
}