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
 * Created by Adi Rajesh on 4/12/12
 */
package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SlotRuleForAppWindowKeyValuesFinder extends UifKeyValuesFinderBase implements Serializable {
    private transient EnumerationManagementService enumerationService;

    private String enumerationKey;

    //this method returns the enumeration values as key value pairs to the UI for appoint rule type which is passed as enumerationKey to the
    // enumeration management service.
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
            List<EnumeratedValueInfo> slotRuleTypeCodes = getEnumerationService().getEnumeratedValues(getEnumerationKey(),null,null,null, ContextUtils.getContextInfo());
            if(slotRuleTypeCodes!=null)  {
                for (EnumeratedValueInfo slotRuleTypeCode : slotRuleTypeCodes) {
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(slotRuleTypeCode.getValue());
                    keyValue.setValue(slotRuleTypeCode.getAbbrevValue());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String enumerationKey){
        this.enumerationKey = enumerationKey;
    }

    //returns the enumerationmangement service
    public EnumerationManagementService getEnumerationService() {
        if(enumerationService == null) {
            enumerationService = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX+"enumerationmanagement", "EnumerationManagementService"));
        }
        return this.enumerationService;
    }
}
