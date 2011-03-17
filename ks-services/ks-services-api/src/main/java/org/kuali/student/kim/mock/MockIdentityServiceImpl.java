/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.mock;


import java.util.List;
import java.util.Map;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNameInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNamePrincipalNameInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityPrivacyPreferencesInfo;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.reference.dto.AddressTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.AffiliationTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.CitizenshipStatusInfo;
import org.kuali.rice.kim.bo.reference.dto.EmailTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EmploymentStatusInfo;
import org.kuali.rice.kim.bo.reference.dto.EmploymentTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EntityNameTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EntityTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.ExternalIdentifierTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.PhoneTypeInfo;
import org.kuali.rice.kim.service.IdentityService;

/**
 * @author nwright
 */
public class MockIdentityServiceImpl implements IdentityService {

    @Override
    public AddressTypeInfo getAddressType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AffiliationTypeInfo getAffiliationType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CitizenshipStatusInfo getCitizenshipStatus(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, KimEntityNameInfo> getDefaultNamesForEntityIds(List<String> list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, KimEntityNamePrincipalNameInfo> getDefaultNamesForPrincipalIds(List<String> list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EmailTypeInfo getEmailType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EmploymentStatusInfo getEmploymentStatus(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EmploymentTypeInfo getEmploymentType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfo(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfoByPrincipalId(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfoByPrincipalName(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityInfo getEntityInfo(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityInfo getEntityInfoByPrincipalId(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityInfo getEntityInfoByPrincipalName(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityNameTypeInfo getEntityNameType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimEntityPrivacyPreferencesInfo getEntityPrivacyPreferences(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityTypeInfo getEntityType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExternalIdentifierTypeInfo getExternalIdentifierType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMatchingEntityCount(Map<String, String> map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PhoneTypeInfo getPhoneType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPrincipalInfo getPrincipal(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPrincipalInfo getPrincipalByPrincipalName(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPrincipalInfo getPrincipalByPrincipalNameAndPassword(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimEntityDefaultInfo> lookupEntityDefaultInfo(Map<String, String> map, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimEntityInfo> lookupEntityInfo(Map<String, String> map, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

