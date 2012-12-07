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
 * Created by David Yin on 8/1/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SeatPoolExpirationMilestoneTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private transient TypeService typeService;
    private static final Logger LOG = Logger.getLogger(SeatPoolExpirationMilestoneTypeKeyValues.class);
    private static final long serialVersionUID = 1L;
    @Override
    public List<KeyValue> getKeyValues(ViewModel viewModel) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        try {
            List<TypeInfo> typeInfos = getTypeService().getTypesForGroupType("kuali.milestone.type.group.seatpool", ContextUtils.createDefaultContextInfo());

            if (typeInfos != null) {
                for (TypeInfo typeInfo : typeInfos) {
                    keyValues.add(new ConcreteKeyValue(typeInfo.getKey(), typeInfo.getName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Seat Pool Expiration Milestone Types", e);
        }

        keyValues.add(new ConcreteKeyValue("NONE", "NONE"));
        return keyValues;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
