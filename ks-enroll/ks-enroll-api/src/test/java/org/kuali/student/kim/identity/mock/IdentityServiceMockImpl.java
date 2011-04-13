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
package org.kuali.student.kim.identity.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.entity.dto.KimEntityAddressInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityAffiliationInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityBioDemographicsInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityEmailInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityEmploymentInformationInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityEntityTypeDefaultInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityEntityTypeInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityExternalIdentifierInfo;
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
import org.kuali.student.enrollment.lpr.mock.CriteriaMatcherInMemory;

/**
 * @author nwright
 */
public class IdentityServiceMockImpl implements IdentityService {

    private int boundedSearchMaxResults = IdentityServiceConstants.BOUNDED_SEARCH_MAX_RESULTS;

    public int getBoundedSearchMaxResults() {
        return boundedSearchMaxResults;
    }

    public void setBoundedSearchMaxResults(int boundedSearchMaxResults) {
        this.boundedSearchMaxResults = boundedSearchMaxResults;
    }

    @Override
    public AddressTypeInfo getAddressType(String code) {
        for (AddressTypeEnum at : AddressTypeEnum.values()) {
            if (at.getCode().equals(code)) {
                AddressTypeInfo info = new AddressTypeInfo();
                info.setActive(at.isActive());
                info.setAddressTypeCode(at.getCode());
                info.setAddressTypeName(at.getName());
                info.setDisplaySortCode(at.getSort());
                info.setCode(at.getCode());
                info.setName(at.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    public AffiliationTypeInfo getAffiliationType(String code) {
        for (AffiliationTypeEnum aff : AffiliationTypeEnum.values()) {
            if (aff.getCode().equals(code)) {
                AffiliationTypeInfo info = new AffiliationTypeInfo();
                info.setActive(aff.isActive());
                info.setAffiliationTypeCode(aff.getCode());
                info.setAffiliationTypeName(aff.getName());
                info.setCode(aff.getCode());
                info.setName(aff.getName());
                info.setDisplaySortCode(aff.getSort());
                return info;
            }
        }
        return null;
    }

    @Override
    public CitizenshipStatusInfo getCitizenshipStatus(String code) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private KimEntityNameInfo toKimEntityNameInfo(PersonEnum pers) {
        KimEntityNameInfo info = new KimEntityNameInfo();
        info.setActive(pers.isActive());
        info.setDefault(true);
        info.setEntityNameId(pers.getEntityId());
        info.setFirstName(pers.getFirstName());
        info.setFirstNameUnmasked(pers.getFirstName());
        info.setFormattedName(pers.getFormattedName());
        info.setFormattedNameUnmasked(pers.getFormattedName());
        info.setLastName(pers.getLastName());
        info.setLastNameUnmasked(pers.getLastName());
        info.setMiddleName(pers.getMiddleName());
        info.setMiddleNameUnmasked(null);
        info.setNameTypeCode(EntityNameTypeEnum.PRIMARY.getCode());
        info.setSuffix(pers.getSuffix());
        info.setSuffixUnmasked(pers.getSuffix());
        info.setSuppressName(false);
        info.setTitle(pers.getTitle());
        info.setTitleUnmasked(pers.getTitle());
        return info;
    }

    private KimEntityAffiliationInfo toKimEntityAffiliationInfo(PersonEnum pers) {
        // TODO: deal with Campus Codes
        // TODO: deal with multiple affiliations
        KimEntityAffiliationInfo info = new KimEntityAffiliationInfo();
        info.setActive(true);
        info.setAffiliationTypeCode(pers.getAffiliation());
        info.setCampusCode(null);
        info.setDefault(true);
        info.setEntityAffiliationId(pers.getEntityId());
        return info;
    }

    private KimEntityEmailInfo toKimEntityEmailInfo(PersonEnum pers) {
        KimEntityEmailInfo info = new KimEntityEmailInfo();
        info.setActive(true);
        info.setDefault(true);
        info.setEmailAddress(pers.getPrincipalName());
        info.setEmailAddressUnmasked(pers.getPrincipalName());
        info.setEmailTypeCode(EmailTypeEnum.WORK.getCode());
        info.setEntityEmailId(pers.getEntityId());
        info.setEntityTypeCode(EntityTypeEnum.PERSON.getCode());
        info.setSuppressEmail(false);
        return info;
    }

    private KimEntityAddressInfo toKimEntityAddressInfo(PersonEnum pers) {
        KimEntityAddressInfo info = new KimEntityAddressInfo();
        info.setActive(true);
        info.setAddressTypeCode(AddressTypeEnum.HOME.getCode());
        info.setCityName(pers.getCity());
        info.setCityNameUnmasked(pers.getCity());
        info.setCountryCode("US");
        info.setCountryCodeUnmasked("US");
        info.setDefault(true);
        info.setEntityAddressId(pers.getEntityId());
        info.setEntityTypeCode(EntityTypeEnum.PERSON.getCode());
        info.setLine1(pers.getStreet());
        info.setLine1Unmasked(pers.getStreet());
        info.setLine2(null);
        info.setLine2Unmasked(null);
        info.setLine3(null);
        info.setLine3Unmasked(null);
        info.setPostalCode(pers.getZip());
        info.setPostalCodeUnmasked(pers.getZip());
        info.setStateCode(pers.getState());
        info.setStateCodeUnmasked(pers.getState());
        info.setSuppressAddress(false);
        return info;
    }

    private KimEntityEntityTypeDefaultInfo toKimEntityEntityTypeDefaultInfo(PersonEnum pers) {
        KimEntityEntityTypeDefaultInfo info = new KimEntityEntityTypeDefaultInfo();
        info.setDefaultEmailAddress(this.toKimEntityEmailInfo(pers));
        info.setDefaultAddress(this.toKimEntityAddressInfo(pers));
        info.setEntityTypeCode(EntityTypeEnum.PERSON.getCode());
        return info;
    }

    private KimEntityExternalIdentifierInfo toKimEntityExternalIdentifierInfo(PersonEnum pers) {
        if (pers.getSsn() == null || pers.getSsn().isEmpty()) {
            return null;
        }
        KimEntityExternalIdentifierInfo info = new KimEntityExternalIdentifierInfo();
        info.setEntityExternalIdentifierId(pers.getEntityId());
        info.setExternalId(pers.getSsn());
        info.setExternalIdentifierTypeCode(ExternalIdentifierTypeEnum.TAX.getCode());
        return info;
    }

    private KimEntityEmploymentInformationInfo toKimEntityEmploymentInformationInfo(PersonEnum pers) {
        // only facutly and staff have employment info
        if (!(pers.getAffiliation().equals(AffiliationTypeEnum.FACULTY.getCode())
                || pers.getAffiliation().equals(AffiliationTypeEnum.STAFF.getCode()))) {
            return null;
        }
        KimEntityEmploymentInformationInfo info = new KimEntityEmploymentInformationInfo();
        info.setActive(true);
        info.setBaseSalaryAmount(null);
        info.setEmployeeId(pers.getSsn());
        info.setEmployeeStatusCode(EmployeeStatusEnum.ACTIVE.getCode());
        info.setEmployeeTypeCode(EmployeeTypeEnum.PROFESSIONAL.getCode());
        info.setEmploymentRecordId(pers.getSsn());
        info.setEntityAffiliationId(pers.getEntityId());
        info.setEntityEmploymentId(pers.getEntityId());
        info.setPrimary(true);
        info.setPrimaryDepartmentCode(pers.getDept());
        return info;
    }

    private KimPrincipalInfo toKimPrincipalInfo(PersonEnum pers) {
        KimPrincipalInfo info = new KimPrincipalInfo();
        info.setActive(pers.isActive());
        info.setEntityId(pers.getEntityId());
        info.setPassword(pers.getPassword());
        info.setPrincipalId(pers.getPrincipalId());
        info.setPrincipalName(pers.getPrincipalName());
        return info;
    }

    private KimEntityPrivacyPreferencesInfo toKimEntityPrivacyPreferencesInfo(PersonEnum pers) {
        KimEntityPrivacyPreferencesInfo info = new KimEntityPrivacyPreferencesInfo();
        info.setSuppressAddress(false);
        info.setSuppressEmail(false);
        info.setSuppressName(false);
        info.setSuppressPhone(false);
        return info;
    }


    private KimEntityDefaultInfo toKimEntityDefaultInfo(PersonEnum pers) {
        KimEntityDefaultInfo info = new KimEntityDefaultInfo();
        info.setActive(pers.isActive());
        info.setEntityTypes(Arrays.asList(this.toKimEntityEntityTypeDefaultInfo(pers)));
        info.setDefaultAffiliation(this.toKimEntityAffiliationInfo(pers));
        info.setAffiliations(Arrays.asList(info.getDefaultAffiliation()));
        info.setDefaultName(toKimEntityNameInfo(pers));
        info.setEntityId(pers.getEntityId());
        info.setExternalIdentifiers(Arrays.asList(this.toKimEntityExternalIdentifierInfo(pers)));
        info.setPrimaryEmployment(this.toKimEntityEmploymentInformationInfo(pers));
        info.setPrincipals(Arrays.asList(this.toKimPrincipalInfo(pers)));
        info.setPrivacyPreferences(this.toKimEntityPrivacyPreferencesInfo(pers));
        return info;
    }

    @Override
    public Map<String, KimEntityNameInfo> getDefaultNamesForEntityIds(List<String> entityIds) {
        Map<String, KimEntityNameInfo> map = new HashMap<String, KimEntityNameInfo>();
        for (PersonEnum pers : PersonEnum.values()) {
            if (entityIds.contains(pers.getEntityId())) {
                map.put(pers.getPrincipalId(), toKimEntityNameInfo(pers));
            }
        }
        return map;
    }

    @Override
    public Map<String, KimEntityNamePrincipalNameInfo> getDefaultNamesForPrincipalIds(List<String> principalIds) {
        Map<String, KimEntityNamePrincipalNameInfo> map = new HashMap<String, KimEntityNamePrincipalNameInfo>();
        for (PersonEnum pers : PersonEnum.values()) {
            if (principalIds.contains(pers.getPrincipalId())) {
                KimEntityNamePrincipalNameInfo info = new KimEntityNamePrincipalNameInfo();
                info.setPrincipalName(pers.getPrincipalName());
                info.setDefaultEntityName(toKimEntityNameInfo(pers));
                map.put(pers.getPrincipalId(), info);
            }
        }
        return map;
    }

    @Override
    public EmailTypeInfo getEmailType(String code) {
        for (EmailTypeEnum at : EmailTypeEnum.values()) {
            if (at.getCode().equals(code)) {
                EmailTypeInfo info = new EmailTypeInfo();
                info.setActive(at.isActive());
                info.setEmailTypeCode(at.getCode());
                info.setEmailTypeName(at.getName());
                info.setDisplaySortCode(at.getSort());
                info.setCode(at.getCode());
                info.setName(at.getName());
                return info;
            }
        }
        return null;
    }

    private EmploymentStatusInfo toEmploymentStatusInfo(EmployeeStatusEnum st) {
        EmploymentStatusInfo info = new EmploymentStatusInfo();
        info.setActive(st.isActive());
        info.setCode(st.getCode());
        info.setDisplaySortCode(st.getSort());
        info.setEmploymentStatusCode(st.getCode());
        info.setEmploymentStatusName(st.getName());
        info.setName(st.getName());
        return info;
    }

    @Override
    public EmploymentStatusInfo getEmploymentStatus(String code) {
        for (EmployeeStatusEnum st : EmployeeStatusEnum.values()) {
            if (st.getCode().equals(code)) {
                return this.toEmploymentStatusInfo(st);
            }
        }
        return null;
    }

    private EmploymentTypeInfo toEmploymentTypeInfo(EmployeeTypeEnum typ) {
        EmploymentTypeInfo info = new EmploymentTypeInfo();
        info.setActive(typ.isActive());
        info.setCode(typ.getCode());
        info.setDisplaySortCode(typ.getSort());
        info.setEmploymentTypeCode(typ.getCode());
        info.setEmploymentTypeName(typ.getName());
        info.setName(typ.getName());
        return info;
    }

    @Override
    public EmploymentTypeInfo getEmploymentType(String code) {
        for (EmployeeTypeEnum st : EmployeeTypeEnum.values()) {
            if (st.getCode().equals(code)) {
                return this.toEmploymentTypeInfo(st);
            }
        }
        return null;
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfo(String entityId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getEntityId().equals(entityId)) {
                return this.toKimEntityDefaultInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfoByPrincipalId(String principalId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalId().equals(principalId)) {
                return this.toKimEntityDefaultInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimEntityDefaultInfo getEntityDefaultInfoByPrincipalName(String principalName) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalName().equals(principalName)) {
                return this.toKimEntityDefaultInfo(pers);
            }
        }
        return null;
    }

    private KimEntityEntityTypeInfo toKimEntityEntityTypeInfo(PersonEnum pers) {
        KimEntityEntityTypeInfo info = new KimEntityEntityTypeInfo();
        info.setActive(pers.isActive());
        info.setDefaultAddress(this.toKimEntityAddressInfo(pers));
        info.setAddresses(Arrays.asList(info.getDefaultAddress()));
        info.setDefaultEmailAddress(this.toKimEntityEmailInfo(pers));
        info.setDefaultPhoneNumber(null);
        info.setEmailAddresses(Arrays.asList(info.getDefaultEmailAddress()));
        info.setEntityType(this.getEntityType(EntityTypeEnum.PERSON.getCode()));
        info.setEntityTypeCode(EntityTypeEnum.PERSON.getCode());
        info.setPhoneNumbers(null);
        return info;
    }

    private KimEntityBioDemographicsInfo toKimEntityBioDemographicsInfo(PersonEnum pers) {
        KimEntityBioDemographicsInfo info = new KimEntityBioDemographicsInfo();
        info.setBirthDate(null);
        info.setBirthDateUnmasked(null);
        info.setBirthStateCode(null);
        info.setBirthStateCodeUnmasked(null);
        info.setCityOfBirth(null);
        info.setCityOfBirthUnmasked(null);
        info.setCountryOfBirthCode(null);
        info.setCountryOfBirthCodeUnmasked(null);
        info.setDeceasedDate(null);
        info.setEntityId(pers.getEntityId());
        info.setGenderCode(pers.getGender().substring(0, 1));
        info.setGenderCodeUnmasked(info.getGenderCode());
        info.setGenderCodeUnmaskedUnmasked(info.getGenderCode());
        info.setGeographicOrigin(null);
        info.setGeographicOriginUnmasked(null);
        info.setMaritalStatusCode(null);
        info.setMaritalStatusCodeUnmasked(null);
        info.setPrimaryLanguageCode(null);
        info.setPrimaryLanguageCodeUnmasked(null);
        info.setSecondaryLanguageCode(null);
        info.setSecondaryLanguageCodeUnmasked(null);
        info.setSuppressPersonal(false);
        return info;
    }

    private KimEntityInfo toKimEntityInfo(PersonEnum pers) {
        KimEntityInfo info = new KimEntityInfo();
        info.setActive(pers.isActive());
        info.setAffiliations(Arrays.asList(this.toKimEntityAffiliationInfo(pers)));
        info.setBioDemographics(this.toKimEntityBioDemographicsInfo(pers));
        info.setCitizenships(null);
        info.setEmploymentInformation(Arrays.asList(toKimEntityEmploymentInformationInfo(pers)));
        info.setEntityId(pers.getEntityId());
        info.setEntityTypes(Arrays.asList(this.toKimEntityEntityTypeInfo(pers)));
        info.setEthnicities(null);
        info.setExternalIdentifiers(Arrays.asList(this.toKimEntityExternalIdentifierInfo(pers)));
        info.setNames(Arrays.asList(this.toKimEntityNameInfo(pers)));
        info.setPrincipals(Arrays.asList(this.toKimPrincipalInfo(pers)));
        info.setPrivacyPreferences(this.toKimEntityPrivacyPreferencesInfo(pers));
        info.setResidencies(null);
        info.setVisas(null);
        return info;
    }

    @Override
    public KimEntityInfo getEntityInfo(String entityId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getEntityId().equals(entityId)) {
                return this.toKimEntityInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimEntityInfo getEntityInfoByPrincipalId(String principalId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalId().equals(principalId)) {
                return this.toKimEntityInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimEntityInfo getEntityInfoByPrincipalName(String principalName) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalName().equals(principalName)) {
                return this.toKimEntityInfo(pers);
            }
        }
        return null;
    }

    @Override
    public EntityNameTypeInfo getEntityNameType(String code) {
        for (EntityNameTypeEnum at : EntityNameTypeEnum.values()) {
            if (at.getCode().equals(code)) {
                EntityNameTypeInfo info = new EntityNameTypeInfo();
                info.setActive(at.isActive());
                info.setEntityNameTypeCode(at.getCode());
                info.setEntityNameTypeName(at.getName());
                info.setDisplaySortCode(at.getSort());
                info.setCode(at.getCode());
                info.setName(at.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    public KimEntityPrivacyPreferencesInfo getEntityPrivacyPreferences(String entityId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getEntityId().equals(entityId)) {
                return this.toKimEntityPrivacyPreferencesInfo(pers);
            }
        }
        return null;
    }

    @Override
    public EntityTypeInfo getEntityType(String code) {
        for (EntityTypeEnum typ : EntityTypeEnum.values()) {
            if (typ.getCode().equals(code)) {
                EntityTypeInfo info = new EntityTypeInfo();
                info.setActive(typ.isActive());
                info.setCode(typ.getCode());
                info.setDisplaySortCode(typ.getSort());
                info.setEntityTypeCode(typ.getCode());
                info.setEntityTypeName(typ.getName());
                info.setName(typ.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    public ExternalIdentifierTypeInfo getExternalIdentifierType(String code) {
        for (ExternalIdentifierTypeEnum typ : ExternalIdentifierTypeEnum.values()) {
            if (typ.getCode().equals(code)) {
                ExternalIdentifierTypeInfo info = new ExternalIdentifierTypeInfo();
                info.setActive(typ.isActive());
                info.setCode(typ.getCode());
                info.setDisplaySortCode(typ.getSort());
                info.setExternalIdentifierTypeCode(typ.getCode());
                info.setExternalIdentifierTypeName(typ.getName());
                info.setName(typ.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    public PhoneTypeInfo getPhoneType(String code) {
        for (PhoneTypeEnum typ : PhoneTypeEnum.values()) {
            if (typ.getCode().equals(code)) {
                PhoneTypeInfo info = new PhoneTypeInfo();
                info.setActive(typ.isActive());
                info.setCode(typ.getCode());
                info.setDisplaySortCode(typ.getSort());
                info.setPhoneTypeCode(typ.getCode());
                info.setPhoneTypeName(typ.getName());
                info.setName(typ.getName());
                return info;
            }
        }
        return null;
    }

    @Override
    public KimPrincipalInfo getPrincipal(String principalId) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalId().equals(principalId)) {
                return this.toKimPrincipalInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimPrincipalInfo getPrincipalByPrincipalName(String principalName) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalName().equals(principalName)) {
                return this.toKimPrincipalInfo(pers);
            }
        }
        return null;
    }

    @Override
    public KimPrincipalInfo getPrincipalByPrincipalNameAndPassword(String principalName,
            String password) {
        for (PersonEnum pers : PersonEnum.values()) {
            if (pers.getPrincipalName().equals(principalName)) {
                if (pers.getPassword().equals(password)) {
                    return this.toKimPrincipalInfo(pers);
                }
            }
        }
        return null;
    }

    private void validate(Map<String, String> searchCriteria)
            throws IllegalArgumentException {
        DataProviderForKimEntityInfoImpl provider = new DataProviderForKimEntityInfoImpl();
        for (String key : searchCriteria.keySet()) {
            if (!provider.supportsField(key)) {
                throw new IllegalArgumentException("Search criteria " + key + " is not supported");
            }
        }

    }

    @Override
    public List<KimEntityDefaultInfo> lookupEntityDefaultInfo(Map<String, String> searchCriteria,
            boolean unbounded) {
        this.validate(searchCriteria);
        List<KimEntityDefaultInfo> selected = new ArrayList<KimEntityDefaultInfo>();
        for (PersonEnum pers : PersonEnum.values()) {
            KimEntityInfo info = this.toKimEntityInfo(pers);
            if (matchesAll(searchCriteria, info)) {
                selected.add(this.toKimEntityDefaultInfo(pers));
                if (!unbounded) {
                    if (selected.size() >= this.boundedSearchMaxResults) {
                        return selected;
                    }
                }
            }
        }
        return selected;
    }

    private boolean matchesAll(Map<String, String> searchCriteria, KimEntityInfo info) {
        // implied AND between criteria
        for (String key : searchCriteria.keySet()) {
            if (!matches(key, searchCriteria.get(key), info)) {
                return false;
            }
        }
        return true;
    }

    private boolean matches(String key, String value, KimEntityInfo info) {
        DataProviderForKimEntityInfoImpl provider = new DataProviderForKimEntityInfoImpl();
        List<Object> dataValues = provider.getValues(info, key);
        return matches(key, value, dataValues);
    }
    
    private boolean matches(String key, String value, List<Object> dataValues) {
        // if multiple data values then implied or
        for (Object dataValue : dataValues) {
            if (matches(key, value, dataValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(String key, String value, Object dataValue) {
        if (value == dataValue) {
            return true;
        }
        if (value == null && dataValue == null) {
            return true;
        }
        if (value == null) {
            return false;
        }
        if (dataValue == null) {
            return false;
        }
        if (value.equals(dataValue)) {
            return true;
        }
        if (value.equalsIgnoreCase((String) dataValue)) {
            return true;
        }
        // rice uses *
        if (value.contains("*")) {
            value = value.replace('*', '%');
        }
        if (value.contains("%") || value.contains("_")) {
            return CriteriaMatcherInMemory.matchesLike(((String) dataValue).toLowerCase(), value.toLowerCase());
        }
        return false;
    }

    @Override
    public List<KimEntityInfo> lookupEntityInfo(Map<String, String> searchCriteria,
            boolean unbounded) {
        this.validate(searchCriteria);
        List<KimEntityInfo> selected = new ArrayList<KimEntityInfo>();
        for (PersonEnum pers : PersonEnum.values()) {
            KimEntityInfo info = this.toKimEntityInfo(pers);
            if (matchesAll(searchCriteria, info)) {
                selected.add(this.toKimEntityInfo(pers));
                if (!unbounded) {
                    if (selected.size() >= this.boundedSearchMaxResults) {
                        return selected;
                    }
                }
            }
        }
        return selected;
    }

    @Override
    public int getMatchingEntityCount(Map<String, String> searchCriteria) {
        this.validate(searchCriteria);
        List<KimEntityInfo> selected = new ArrayList<KimEntityInfo>();
        for (PersonEnum pers : PersonEnum.values()) {
            KimEntityInfo info = this.toKimEntityInfo(pers);
            if (matchesAll(searchCriteria, info)) {
                selected.add(this.toKimEntityInfo(pers));
            }
        }
        return selected.size();
    }
}

