/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.organization.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.common.validator.old.Validator;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.organization.dao.ExtendedOrgDao;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrganizationServiceMockImpl implements OrganizationService {

    final Logger logger = Logger.getLogger(OrganizationServiceMockImpl.class);

    private OrganizationDao organizationDao;
    private ExtendedOrgDao extendedOrgDao;
    private DictionaryService dictionaryServiceDelegate;
    private Validator validator;
    private CriteriaLookupService criteriaLookupService;

    /* Mock Datastructures */
    private Map<String, OrgHierarchyInfo> orgHierarchyInfoMap = new LinkedHashMap<String, OrgHierarchyInfo>();
    private Map<String, OrgInfo> orgInfoMap = new LinkedHashMap<String, OrgInfo>();
    private Map<String, OrgOrgRelationInfo> orgOrgRelationInfoMap = new LinkedHashMap<String, OrgOrgRelationInfo>();
    private Map<String, OrgPersonRelationInfo> orgPersonRelationInfoMap = new LinkedHashMap<String, OrgPersonRelationInfo>();
    private Map<String, OrgPositionRestrictionInfo> orgPositionRestrictionInfoMap = new LinkedHashMap<String, OrgPositionRestrictionInfo>();

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName name
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public ExtendedOrgDao getExtendedOrgDao() {
        return extendedOrgDao;
    }

    public void setExtendedOrgDao(ExtendedOrgDao extendedOrgDao) {
        this.extendedOrgDao = extendedOrgDao;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    @Override
    public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");
        if (orgHierarchyInfoMap.containsKey(orgHierarchyId)) {
            return orgHierarchyInfoMap.get(orgHierarchyId);
        } else {
            throw new DoesNotExistException();
        }

    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchiesByIds(List<String> orgHierarchyIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyIds, "orgHierarchyIds");
        List<OrgHierarchyInfo> results = new ArrayList<OrgHierarchyInfo>();
        for (String orgHierarchyId : orgHierarchyIds) {
            if (orgHierarchyInfoMap.containsKey(orgHierarchyId)) {
                results.add(orgHierarchyInfoMap.get(orgHierarchyId));
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgHierarchyIdsByType(String orgHierarchyTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyTypeKey, "orgHierarchyTypeKey");

        List<String> orgHierarchyIds = new ArrayList<String>();
        List<OrgHierarchyInfo> orgHierarchies = new ArrayList(orgHierarchyInfoMap.values());

        for (OrgHierarchyInfo orgHierarchy : orgHierarchies) {
            orgHierarchyIds.add(orgHierarchy.getId());
        }
        return orgHierarchyIds;
    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList(orgHierarchyInfoMap.values());
    }

    @Override
    public List<TypeInfo> getOrgTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getOrgTypes(ContextInfo) is not mocked yet!");
    }

    @Override
    public OrgInfo getOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        if (orgInfoMap.containsKey(orgId)) {
            return orgInfoMap.get(orgId);
        } else {
            throw new DoesNotExistException();
        }
    }

    @Override
    public List<OrgInfo> getOrgsByIds(List<String> orgIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgIds, "orgIds");

        List<OrgInfo> results = new ArrayList<OrgInfo>();
        for (String orgId : orgIds) {
            if (orgInfoMap.containsKey(orgId)) {
                results.add(orgInfoMap.get(orgId));
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgIdsByType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");

        List<String> orgIds = new ArrayList<String>();
        List<OrgInfo> orgs = new ArrayList(orgInfoMap.values());

        for (OrgInfo org : orgs) {
            orgIds.add(org.getId());
        }
        return orgIds;
    }

    @Override
    public List<String> searchForOrgIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgIds(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<OrgInfo> searchForOrgs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgs(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<ValidationResultInfo> validateOrg(String validationTypeKey, String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(orgInfo, "orgInfo");

        // FIXME redo validation here and for all calls to create/update
        // return validator.validateTypeStateObject(orgInfo, getObjectStructure("orgInfo"));

        return new ArrayList<ValidationResultInfo>(0);
    }

    @Override
    public OrgInfo createOrg(String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check Missing params
        checkForMissingParameter(orgTypeKey, "orgTypeKey");
        checkForMissingParameter(orgInfo, "orgInfo");

        // Set all the values on orgInfo
        orgInfo.setTypeKey(orgTypeKey);

        try {
            List<ValidationResultInfo> validations = validateOrg("", "", orgInfo, contextInfo);
            for (ValidationResultInfo validationResult : validations) {
                if (validationResult.isError())
                    throw new DataValidationErrorException(validationResult.toString());
            }
        } catch (DoesNotExistException e1) {
            logger.error("Exception occured: ", e1);
        }
        if (orgInfo.getId() == null) {
            orgInfo.setId(String.valueOf(orgInfoMap.size() + 1));//warning here be dragons!
        }
        if (orgInfoMap.containsKey(orgInfo.getId())) {
            throw new PermissionDeniedException("duplicate org key already exists. Try update?");
        } else {
            orgInfoMap.put(orgInfo.getId(), orgInfo);
        }
        return orgInfo;
    }

    @Override
    public OrgInfo updateOrg(String orgId, OrgInfo orgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgInfo, "orgInfo");

        // Set all the values on orgInfo
        orgInfo.setId(orgId);

        if (orgInfoMap.containsKey(orgInfo.getId())) {
            orgInfoMap.put(orgInfo.getId(), orgInfo);
        } else {
            throw new DoesNotExistException("org with id: " + orgInfo.getId() + " does not exist yet. Try creating it first.");
        }

        return orgInfo;
    }

    @Override
    public StatusInfo deleteOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        if (orgInfoMap.containsKey(orgId)) {
            orgInfoMap.remove(orgId);
        } else {
            throw new DoesNotExistException("Org does not exist for id: " + orgId);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getOrgOrgRelationTypes(ContextInfo) is not mocked yet!");
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getOrgOrgRelationTypesForOrgType(String, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getOrgOrgRelationTypesForOrgHierarchy(String, ContextInfo) is not mocked yet!");
    }

    @Override
    public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(comparisonOrgId, "comparisonOrgId");
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        boolean result = false;
        List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList(orgOrgRelationInfoMap.values());
        for (OrgOrgRelationInfo orgOrgRelation : orgOrgRelations) {
            if (orgOrgRelation.getOrgId().equals(orgId) && orgOrgRelation.getRelatedOrgId().equals(comparisonOrgId) && orgOrgRelation.getTypeKey().equals(orgOrgRelationTypeKey)) {
                result = true;
                break;
            }
        }

        return Boolean.valueOf(result);
    }

    @Override
    public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        if (orgOrgRelationInfoMap.containsKey(orgOrgRelationId)) {
                return orgOrgRelationInfoMap.get(orgOrgRelationId);
        }
        throw new DoesNotExistException();
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByIds(List<String> orgOrgRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationIds, "orgOrgRelationIds");

        List<OrgOrgRelationInfo> results = new ArrayList<OrgOrgRelationInfo>();
        for (String orgRelId : orgOrgRelationIds) {
            if (orgOrgRelationInfoMap.containsKey(orgRelId)) {
                results.add(orgOrgRelationInfoMap.get(orgRelId));
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgOrgRelationIdsByType(String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        List<String> orgOrgRelationIds = new ArrayList<String>();
        List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList(orgOrgRelationInfoMap.values());

        for (OrgOrgRelationInfo orgRel : orgOrgRelations) {
            if (orgRel.getTypeKey().equals(orgOrgRelationTypeKey)) {
                orgOrgRelationIds.add(orgRel.getId());
            }
        }
        return orgOrgRelationIds;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<OrgOrgRelationInfo> results = new ArrayList<OrgOrgRelationInfo>();
        List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList(orgOrgRelationInfoMap.values());

        for (OrgOrgRelationInfo orgRel : orgOrgRelations) {
            if (orgRel.getOrgId().equals(orgId)) {
                results.add(orgRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrgs(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getOrgOrgRelationsByOrg(orgId, contextInfo);
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByTypeAndOrg(String orgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        List<OrgOrgRelationInfo> results = new ArrayList<OrgOrgRelationInfo>();
        List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList(orgOrgRelationInfoMap.values());

        for (OrgOrgRelationInfo orgRel : orgOrgRelations) {
            if (orgRel.getOrgId().equals(orgId) && orgRel.getTypeKey().equals(orgOrgRelationTypeKey)) {
                results.add(orgRel);
            }
        }
        return results;
    }

    @Override
    public List<String> searchForOrgOrgRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgOrgRelationIds(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<OrgOrgRelationInfo> searchForOrgOrgRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgOrgRelations(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<ValidationResultInfo> validateOrgOrgRelation(String validationTypeKey, String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(orgOrgRelationInfo, "orgOrgRelationInfo");

        // List<ValidationResultInfo> valResults = validator.validateTypeStateObject(orgOrgRelationInfo,
        // getObjectStructure("orgOrgRelationInfo"));
        // return valResults;
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgPeerId, "orgPeerId");
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");
        checkForMissingParameter(orgOrgRelationInfo, "orgOrgRelationInfo");

        // Set all the values on OrgOrgRelationInfo
        orgOrgRelationInfo.setOrgId(orgId);
        orgOrgRelationInfo.setRelatedOrgId(orgPeerId);
        orgOrgRelationInfo.setTypeKey(orgOrgRelationTypeKey);
        orgOrgRelationInfo.setId(UUIDHelper.genStringUUID(orgOrgRelationInfo.getId()));

        // Persist the orgOrgRelation
        orgOrgRelationInfoMap.put(orgOrgRelationInfo.getId(),orgOrgRelationInfo);

        return orgOrgRelationInfo;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        // Set all the values on OrgOrgRelationInfo
        orgOrgRelationInfo.setId(orgOrgRelationId);

        if (orgOrgRelationInfoMap.containsKey(orgOrgRelationId)) {
            orgOrgRelationInfoMap.put(orgOrgRelationId, orgOrgRelationInfo);
        } else {
            throw new DoesNotExistException("orgOrgRelation with id: " + orgOrgRelationId + " does not exist yet. Try creating it first.");
        }

        return orgOrgRelationInfo;
    }

    @Override
    public StatusInfo deleteOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        if (orgOrgRelationInfoMap.containsKey(orgOrgRelationId)) {
            orgOrgRelationInfoMap.remove(orgOrgRelationId);
        } else {
            throw new DoesNotExistException("orgOrgRelation does not exist for id: " + orgOrgRelationId);
        }

        return new StatusInfo();
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getOrgPersonRelationTypes(ContextInfo) is not mocked yet!");
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");

        throw new OperationFailedException("getOrgPersonRelationTypesForOrgType(String, ContextInfo) is not mocked yet!");
    }

    @Override
    public Boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        boolean result = false;
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRelation : orgPersonRelations) {
            if (orgPersonRelation.getOrgId().equals(orgId) && orgPersonRelation.getPersonId().equals(personId) && orgPersonRelation.getTypeKey().equals(orgPersonRelationTypeKey)) {
                result = true;
                break;
            }
        }

        return Boolean.valueOf(result);
    }

    @Override
    public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

        if (orgPersonRelationInfoMap.containsKey(orgPersonRelationId)) {
            return orgPersonRelationInfoMap.get(orgPersonRelationId);
        } else {
            throw new DoesNotExistException("orgPersonRelation does not exist for id: " + orgPersonRelationId);
        }
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByIds(List<String> orgPersonRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationIds, "orgPersonRelationIds");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        for (String orgRelId : orgPersonRelationIds) {
            if (orgPersonRelationInfoMap.containsKey(orgRelId)) {
                results.add(orgPersonRelationInfoMap.get(orgRelId));
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgPersonRelationIdsByType(String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        List<String> results = new ArrayList<String>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getTypeKey().equals(orgPersonRelationTypeKey)) {
                results.add(orgPersonRel.getId());
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getOrgId().equals(orgId)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrg(String orgPersonRelationTypeKey, String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(orgId, "orgId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getOrgId().equals(orgId) && orgPersonRel.getTypeKey().equals(orgPersonRelationTypeKey)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getPersonId().equals(personId)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndPerson(String orgPersonRelationTypeKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getPersonId().equals(personId) && orgPersonRel.getTypeKey().equals(orgPersonRelationTypeKey)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrgAndPerson(String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getPersonId().equals(personId) && orgPersonRel.getOrgId().equals(orgId)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrgAndPerson(String orgPersonRelationTypeKey, String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelationInfo> results = new ArrayList<OrgPersonRelationInfo>();
        List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList(orgPersonRelationInfoMap.values());
        for (OrgPersonRelationInfo orgPersonRel : orgPersonRelations) {
            if (orgPersonRel.getPersonId().equals(personId) && orgPersonRel.getOrgId().equals(orgId) && orgPersonRel.getTypeKey().equals(orgPersonRelationTypeKey)) {
                results.add(orgPersonRel);
            }
        }
        return results;
    }

    @Override
    public List<String> searchForOrgPersonRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgPersonRelationIds(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<OrgPersonRelationInfo> searchForOrgPersonRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgPersonRelations(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<ValidationResultInfo> validateOrgPersonRelation(String validationTypeKey, String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationTypeKey");

        // List<ValidationResultInfo> valResults = validator.validateTypeStateObject(orgPersonRelationInfo,
        // getObjectStructure("orgPersonRelationInfo"));
        // return valResults;
        return null;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        // Set all the values on OrgOrgRelationInfo
        orgPersonRelationInfo.setOrgId(orgId);
        orgPersonRelationInfo.setPersonId(personId);
        orgPersonRelationInfo.setTypeKey(orgPersonRelationTypeKey);
        orgPersonRelationInfo.setId(UUIDHelper.genStringUUID(orgPersonRelationInfo.getId()));

        // Persist the orgPersonRelation
        orgPersonRelationInfoMap.put(orgPersonRelationInfo.getId(),orgPersonRelationInfo);

        return orgPersonRelationInfo;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");
        checkForMissingParameter(orgPersonRelationInfo, "orgPersonRelationInfo");

        // Set all the values on OrgPersonRelationInfo
        orgPersonRelationInfo.setId(orgPersonRelationId);

        if (orgPersonRelationInfoMap.containsKey(orgPersonRelationId)) {
            orgPersonRelationInfoMap.put(orgPersonRelationId, orgPersonRelationInfo);
        } else {
            throw new DoesNotExistException("orgPersonRelation with id: " + orgPersonRelationId + " does not exist yet. Try creating it first.");
        }

        return orgPersonRelationInfo;
    }

    @Override
    public StatusInfo deleteOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

        if (orgPersonRelationInfoMap.containsKey(orgPersonRelationId)) {
            orgPersonRelationInfoMap.remove(orgPersonRelationId);
        } else {
            throw new DoesNotExistException("orgPersonRelation does not exist for id: " + orgPersonRelationId);
        }

        return new StatusInfo();
    }

    @Override
    public OrgPositionRestrictionInfo getOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");

        if (orgPositionRestrictionInfoMap.containsKey(orgPositionRestrictionId)) {
            return orgPositionRestrictionInfoMap.get(orgPositionRestrictionId);
        } else {
            throw new DoesNotExistException("orgPositionRestriction does not exist for id: " + orgPositionRestrictionId);
        }
    }

    @Override
    public List<OrgPositionRestrictionInfo> getOrgPositionRestrictionsByIds(List<String> orgPositionRestrictionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionIds, "orgPositionRestrictionIds");

        List<OrgPositionRestrictionInfo> results = new ArrayList<OrgPositionRestrictionInfo>();
        for (String Id : orgPositionRestrictionIds) {
            if (orgPositionRestrictionInfoMap.containsKey(Id)) {
                results.add(orgPositionRestrictionInfoMap.get(Id));
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByType(String orgPositionRestrictionTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionTypeKey, "orgPositionRestrictionTypeKey");

        List<String> results = new ArrayList<String>();
        List<OrgPositionRestrictionInfo> orgPositionRestrictions = new ArrayList(orgPositionRestrictionInfoMap.values());
        for (OrgPositionRestrictionInfo orgPositionRest : orgPositionRestrictions) {
            if (orgPositionRest.getOrgPersonRelationTypeKey().equals(orgPositionRestrictionTypeKey)) {
                results.add(orgPositionRest.getId());
            }
        }
        return results;
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<String> results = new ArrayList<String>();
        List<OrgPositionRestrictionInfo> orgPositionRestrictions = new ArrayList(orgPositionRestrictionInfoMap.values());
        for (OrgPositionRestrictionInfo orgPositionRest : orgPositionRestrictions) {
            if (orgPositionRest.getOrgId().equals(orgId)) {
                results.add(orgPositionRest.getId());
            }
        }
        return results;
    }

    @Override
    public List<String> searchForOrgPositionRestrictionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgPositionRestrictionIds(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<OrgPositionRestrictionInfo> searchForOrgPositionRestrictions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForOrgPositionRestrictions(QueryByCriteria, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<ValidationResultInfo> validateOrgPositionRestriction(String validationTypeKey, String orgId, String orgPositionRestrictionTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        return null;
    }

    @Override
    public OrgPositionRestrictionInfo createOrgPositionRestriction(String orgId, String orgPositionRestrictionTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgPositionRestrictionTypeKey, "orgPositionRestrictionTypeKey");
        checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        // Set all the values on OrgPositionRestrictionInfo
        orgPositionRestrictionInfo.setOrgId(orgId);
        orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPositionRestrictionTypeKey);
        orgPositionRestrictionInfo.setId(UUIDHelper.genStringUUID(orgPositionRestrictionInfo.getId()));

        // Persist the OrgPositionRestriction
        orgPositionRestrictionInfoMap.put(orgPositionRestrictionInfo.getId(),orgPositionRestrictionInfo);

        return orgPositionRestrictionInfo;
    }

    @Override
    public OrgPositionRestrictionInfo updateOrgPositionRestriction(String orgPositionRestrictionId, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");
        checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        // Set all the values on OrgOrgRelationInfo
        // orgPositionRestrictionInfo.setOrgId(orgId);
        // orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPersonRelationTypeKey);

        if (orgPositionRestrictionInfoMap.containsKey(orgPositionRestrictionId)) {
            orgPositionRestrictionInfoMap.put(orgPositionRestrictionId, orgPositionRestrictionInfo);
        } else {
            throw new DoesNotExistException("OrgPositionRestriction with id: " + orgPositionRestrictionId + " does not exist yet. Try creating it first.");
        }

        return orgPositionRestrictionInfo;
    }

    @Override
    public StatusInfo deleteOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");

        if (orgPositionRestrictionInfoMap.containsKey(orgPositionRestrictionId)) {
            orgPositionRestrictionInfoMap.remove(orgPositionRestrictionId);
        } else {
            throw new DoesNotExistException("OrgPositionRestriction does not exist for id: " + orgPositionRestrictionId);
        }

        return new StatusInfo();
    }

    @Override
    public Boolean isDescendant(String orgId, String descendantOrgId, String orgHierarchyId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(descendantOrgId, "descendantOrgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        throw new OperationFailedException("isDescendant(String, String, String, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        throw new OperationFailedException("getAllDescendants(String, String, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<String> getAllAncestors(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        throw new OperationFailedException("getAllAncestors(String, String, ContextInfo) is not mocked yet!");
    }

    @Override
    public List<OrgTreeInfo> getOrgTree(String rootOrgId, String orgHierarchyId, int maxLevels, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(rootOrgId, "rootOrgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        throw new OperationFailedException("getOrgTree(String, String, int, ContextInfo) is not mocked yet!");
    }

    private List<OrgTreeInfo> parseOrgTree(String rootOrgId, String orgHierarchyId, int maxLevels, int currentLevel) {
        List<OrgTreeInfo> results = new ArrayList<OrgTreeInfo>();

        if (maxLevels == 0 || currentLevel < maxLevels) {
            List<org.kuali.student.core.organization.dto.OrgTreeInfo> orgTreeInfos = this.organizationDao.getOrgTreeInfo(rootOrgId, orgHierarchyId);
            for (org.kuali.student.core.organization.dto.OrgTreeInfo orgTreeInfo : orgTreeInfos) {

                OrgTreeInfo treeInfo = new OrgTreeInfo();
                treeInfo.setOrgId(orgTreeInfo.getOrgId());
                treeInfo.setDisplayName(orgTreeInfo.getDisplayName());
                treeInfo.setOrgHierarchyId(orgTreeInfo.getOrgHierarchyId());
                treeInfo.setParentId(orgTreeInfo.getParentId());
                treeInfo.setPersonId(orgTreeInfo.getPersonId());
                treeInfo.setPositionId(orgTreeInfo.getPositionId());
                treeInfo.setRelationTypeKey(orgTreeInfo.getRelationType());
                treeInfo.setPositions(this.organizationDao.getOrgMemebershipCount(orgTreeInfo.getOrgId()));

                results.add(treeInfo);

                results.addAll(parseOrgTree(orgTreeInfo.getOrgId(), orgHierarchyId, maxLevels, currentLevel + 1));
            }
        }

        return results;
    }

    public org.kuali.student.common.dictionary.dto.ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }
}
