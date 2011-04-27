/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.kim.identity.mock;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.entity.dto.KimEntityAffiliationInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNameInfo;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kns.datadictionary.validation.DataType;

/**
 * Provides values to compare
 * @author nwright
 */
public class DataProviderForKimEntityInfoImpl implements DataProviderForKimEntityInfoInfc {

    final static Logger LOG = Logger.getLogger(DataProviderForKimEntityInfoImpl.class);

    public DataProviderForKimEntityInfoImpl() {
    }

    @Override
    public List<Object> getValues(KimEntityInfo obj, String fieldKey) {
        if (fieldKey.equals(IdentityServiceConstants.KIM_PERSON_FIRST_NAME)) {
            List<Object> dvs = new ArrayList();
            for (KimEntityNameInfo nameInfo : obj.getNames()) {
                dvs.add(nameInfo.getFirstName());
            }
            return dvs;
        }
        if (fieldKey.equals(IdentityServiceConstants.KIM_PERSON_LAST_NAME)) {
            List<Object> dvs = new ArrayList();
            for (KimEntityNameInfo nameInfo : obj.getNames()) {
                dvs.add(nameInfo.getLastName());
            }
            return dvs;
        }
        if (fieldKey.equals(IdentityServiceConstants.KIM_PERSON_MIDDLE_NAME)) {
            List<Object> dvs = new ArrayList();
            for (KimEntityNameInfo nameInfo : obj.getNames()) {
                dvs.add(nameInfo.getMiddleName());
            }
            return dvs;
        }
        if (fieldKey.equals(IdentityServiceConstants.KIM_PRINCIPALS_PRINCIPALNAME)) {
            List<Object> dvs = new ArrayList();
            for (KimPrincipalInfo prinInfo : obj.getPrincipals()) {
                dvs.add(prinInfo.getPrincipalName());
            }
            return dvs;
        }
        if (fieldKey.equals(IdentityServiceConstants.KIM_PRINCIPALS_PRINCIPALID)) {
            List<Object> dvs = new ArrayList();
            for (KimPrincipalInfo prinInfo : obj.getPrincipals()) {
                dvs.add(prinInfo.getPrincipalId());
            }
            return dvs;
        }
        if (fieldKey.equals(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE)) {
            List<Object> dvs = new ArrayList();
            for (KimEntityAffiliationInfo affInfo : obj.getAffiliations()) {
                dvs.add(affInfo.getAffiliationTypeCode());
            }
            return dvs;
        }
        throw new IllegalArgumentException("Search criteria " + fieldKey + " is not supported");
    }

    @Override
    public DataType getDataType(String fieldKey) {
        for (String supportedKey : IdentityServiceConstants.SUPPORTED_SEARCH_FIELD_KEYS) {
            if (supportedKey.equals(fieldKey)) {
                return DataType.STRING;
            }
        }
        throw new IllegalArgumentException("Search criteria " + fieldKey + " is not supported");
    }

    @Override
    public boolean supportsField(String fieldKey) {
        try {
            getDataType(fieldKey);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public Object parseValue(String fieldKey, String value) throws IllegalArgumentException {
        DataType dt = this.getDataType(fieldKey);
        
        if(dt.equals(DataType.STRING)){
            return value.toLowerCase();
        }else{
            throw new IllegalArgumentException("Search criteria " + fieldKey + " has a data type that is not supported");
        }
        
    }
}
