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
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r1.common.validator.old.Validator;
import org.kuali.student.r1.core.organization.dao.OrganizationDao;
import org.kuali.student.r1.core.organization.entity.Org;
import org.kuali.student.r1.core.organization.entity.OrgHierarchy;
import org.kuali.student.r1.core.organization.entity.OrgOrgRelation;
import org.kuali.student.r1.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.r1.core.organization.entity.OrgPersonRelation;
import org.kuali.student.r1.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.r1.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.r1.core.organization.entity.OrgType;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.organization.dao.ExtendedOrgDao;
import org.kuali.student.r2.core.organization.dto.*;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.service.util.AssemblerHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebService(endpointInterface = "org.kuali.student.r2.core.organization.service.OrganizationService", serviceName = "OrganizationService", portName = "OrganizationService", targetNamespace = "http://student.kuali.org/wsdl/organization")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class OrganizationServiceImpl implements OrganizationService {

    final Logger logger = Logger.getLogger(OrganizationServiceImpl.class);

    private OrganizationDao organizationDao;
    private ExtendedOrgDao extendedOrgDao;
    private DictionaryService dictionaryServiceDelegate;
    private SearchManager searchManager;
    private Validator validator;
    private CriteriaLookupService criteriaLookupService;

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     *            name
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
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

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
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

        try {
            return OrganizationAssembler.toOrgHierarchyInfo(organizationDao.fetch(OrgHierarchy.class, orgHierarchyId));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException();
        }
    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchiesByIds(List<String> orgHierarchyIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyIds, "orgHierarchyIds");

        List<OrgHierarchy> orgHierarchies = extendedOrgDao.getOrgHierarchiesByIds(orgHierarchyIds);
        return OrganizationAssembler.toOrgHierarchyInfos(orgHierarchies);
    }

    @Override
    public List<String> getOrgHierarchyIdsByType(String orgHierarchyTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyTypeKey, "orgHierarchyTypeKey");

        List<String> orgHierarchyIds = new ArrayList<String>();
        List<OrgHierarchy> orgHierarchies = extendedOrgDao.getOrgHierarchiesByType(orgHierarchyTypeKey);

        if (orgHierarchies != null){
            for (OrgHierarchy orgHierarchy : orgHierarchies){
                orgHierarchyIds.add(orgHierarchy.getId());
            }
        }

        return orgHierarchyIds;
    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return OrganizationAssembler.toOrgHierarchyInfos(organizationDao.find(OrgHierarchy.class));
    }

    @Override
    public List<TypeInfo> getOrgTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return AssemblerHelper.toGenericTypeInfoList(organizationDao.find(OrgType.class));
    }

    @Override
    public OrgInfo getOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        try {
            return OrganizationAssembler.toOrgInfo(organizationDao.fetch(Org.class, orgId));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException();
        }
    }

    @Override
    public List<OrgInfo> getOrgsByIds(List<String> orgIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgIds, "orgIds");

        List<Org> orgs = this.organizationDao.getOrganizationsByIdList(orgIds);
        return OrganizationAssembler.toOrgInfos(orgs);
    }

    @Override
    public List<String> getOrgIdsByType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");

        List<String> orgIds = new ArrayList<String>();
        List<Org> orgs = extendedOrgDao.getOrgsByType(orgTypeKey);

        if (orgs != null){
            for (Org org : orgs){
                orgIds.add(org.getId());
            }
        }

        return orgIds;
    }

    @Override
    public List<String> searchForOrgIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgIds = new ArrayList<String>();
        List<OrgInfo> orgs = this.searchForOrgs(criteria, contextInfo);

        if (orgs != null) {
            for (OrgInfo org : orgs) {
                orgIds.add(org.getId());
            }
        }

        return orgIds;
    }

    @Override
    public List<OrgInfo> searchForOrgs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgInfo> orgInfos = new ArrayList<OrgInfo>();
        GenericQueryResults<Org> results = criteriaLookupService.lookup(Org.class, criteria);

        if (null != results && results.getResults().size() > 0) {
            for (Org org : results.getResults()) {
                orgInfos.add(OrganizationAssembler.toOrgInfo(org));
            }
        }

        return orgInfos;
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

        Org org = null;

        // Create a new persistence entity from the orgInfo
        try {
            org = OrganizationAssembler.toOrg(false, orgInfo, organizationDao);
        } catch (DoesNotExistException e) {
            // // OrgAssembler should not be throwing this exception for create!
            logger.info(e.getMessage(), e);
            throw new OperationFailedException(e.getMessage(), e);
        } catch (VersionMismatchException e) {
            // // OrgAssembler should not be throwing this exception for create!
            logger.info(e.getMessage(), e);
            throw new OperationFailedException(e.getMessage(), e);
        }

        // Persist the org
        organizationDao.create(org);

        // Copy back to an orgInfo and return
        OrgInfo createdOrgInfo = OrganizationAssembler.toOrgInfo(org);
        return createdOrgInfo;
    }

    @Override
    public OrgInfo updateOrg(String orgId, OrgInfo orgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgInfo, "orgInfo");

        // Set all the values on orgInfo
        orgInfo.setId(orgId);

        Org org = null;

        // Update persistence entity from the orgInfo
        org = OrganizationAssembler.toOrg(true, orgInfo, organizationDao);

        // Update the org
        Org updatedOrg = organizationDao.update(org);

        // Copy back to an orgInfo and return
        OrgInfo updatedOrgInfo = OrganizationAssembler.toOrgInfo(updatedOrg);
        return updatedOrgInfo;
    }

    @Override
    public StatusInfo deleteOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        Org org = null;
        try {
            org = organizationDao.fetch(Org.class, orgId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }

        if (org == null) {
            throw new DoesNotExistException("Org does not exist for id: " + orgId);
        }

        organizationDao.delete(org);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.find(OrgOrgRelationType.class);
        return AssemblerHelper.toGenericTypeInfoList(orgOrgRelationTypes);
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");

        List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgType(orgTypeKey);
        return AssemblerHelper.toGenericTypeInfoList(orgOrgRelationTypes);
    }

    @Override
    @Deprecated
    public TypeInfo getOrgOrgRelationTypeForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");
        List<TypeInfo> infos = getOrgOrgRelationTypesForOrgType(orgTypeKey,contextInfo );

        return (infos.size() >0 ? infos.get(0) : new TypeInfo());
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgHierarchy(orgHierarchyId);
        return AssemblerHelper.toGenericTypeInfoList(orgOrgRelationTypes);
    }

    @Override
    public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // Check Missing params
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(comparisonOrgId, "comparisonOrgId");
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        boolean result = organizationDao.hasOrgOrgRelation(orgId, comparisonOrgId, orgOrgRelationTypeKey);
        return Boolean.valueOf(result);
    }

    @Override
    public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        OrgOrgRelationInfo orgOrgRelationInfo = null;
        try {
            orgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(organizationDao.fetch(OrgOrgRelation.class, orgOrgRelationId));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return orgOrgRelationInfo;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByIds(List<String> orgOrgRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationIds, "orgOrgRelationIds");

        List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByIdList(orgOrgRelationIds);
        return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
    }

    @Override
    public List<String> getOrgOrgRelationIdsByType(String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        List<String> orgOrgRelationIds = new ArrayList<String>();
        List<OrgOrgRelation> orgOrgRelations = extendedOrgDao.getOrgOrgRelationsByType(orgOrgRelationTypeKey);

        if (orgOrgRelations != null){
            for(OrgOrgRelation orgOrgRelation : orgOrgRelations){
                orgOrgRelationIds.add(orgOrgRelation.getId());
            }
        }

        return orgOrgRelationIds;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByOrg(orgId);
        return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrgs(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getOrgOrgRelationsByOrg(orgId, contextInfo);
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByTypeAndOrg(String orgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        List<OrgOrgRelation> orgOrgRelations = extendedOrgDao.getOrgOrgRelationsByTypeAndOrg(orgId, orgOrgRelationTypeKey);
        return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
    }

    @Override
    public List<String> searchForOrgOrgRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgOrgRelationIds = new ArrayList<String>();
        List<OrgOrgRelationInfo> orgOrgRelations = this.searchForOrgOrgRelations(criteria, contextInfo);

        if (orgOrgRelations != null) {
            for (OrgOrgRelationInfo orgOrgRelation : orgOrgRelations) {
                orgOrgRelationIds.add(orgOrgRelation.getId());
            }
        }

        return orgOrgRelationIds;
    }

    @Override
    public List<OrgOrgRelationInfo> searchForOrgOrgRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgOrgRelationInfo> OrgOrgRelationInfos = new ArrayList<OrgOrgRelationInfo>();
        GenericQueryResults<OrgOrgRelation> results = criteriaLookupService.lookup(OrgOrgRelation.class, criteria);

        if (null != results && results.getResults().size() > 0) {
            for (OrgOrgRelation orgOrgRelation : results.getResults()) {
                OrgOrgRelationInfos.add(OrganizationAssembler.toOrgOrgRelationInfo(orgOrgRelation));
            }
        }

        return OrgOrgRelationInfos;
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

        OrgOrgRelation orgOrgRelation = null;

        // Create a new persistence entity from the orgInfo
        try {
            orgOrgRelation = OrganizationAssembler.toOrgOrgRelation(false, orgOrgRelationInfo, organizationDao);
        } catch (VersionMismatchException e) {}

        // Persist the orgOrgRelation
        organizationDao.create(orgOrgRelation);

        // Copy back to an OrgOrgRelationInfo and return
        OrgOrgRelationInfo createdOrgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(orgOrgRelation);
        return createdOrgOrgRelationInfo;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        // Set all the values on OrgOrgRelationInfo
        orgOrgRelationInfo.setId(orgOrgRelationId);

        OrgOrgRelation orgOrgRelation = null;

        // Update the persistence entity from the Info
        orgOrgRelation = OrganizationAssembler.toOrgOrgRelation(true, orgOrgRelationInfo, organizationDao);

        // Update the orgOrgRelation
        OrgOrgRelation updatedOrgOrgRelation = organizationDao.update(orgOrgRelation);

        // Copy back to an OrgOrgRelationInfo and return
        OrgOrgRelationInfo updatedOrgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(updatedOrgOrgRelation);
        return updatedOrgOrgRelationInfo;
    }

    @Override
    public StatusInfo deleteOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        try {
            organizationDao.delete(OrgOrgRelation.class, orgOrgRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return new StatusInfo();
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationType> oprts = organizationDao.find(OrgPersonRelationType.class);
        return AssemblerHelper.toGenericTypeInfoList(oprts);
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgTypeKey, "orgTypeKey");

        List<OrgPersonRelationType> oprts = organizationDao.getOrgPersonRelationTypesForOrgType(orgTypeKey);
        return AssemblerHelper.toGenericTypeInfoList(oprts);
    }

    @Override
    public Boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        return organizationDao.hasOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey);
    }

    @Override
    public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

        OrgPersonRelation opr = null;
        try {
            opr = organizationDao.fetch(OrgPersonRelation.class, orgPersonRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return OrganizationAssembler.toOrgPersonRelationInfo(opr);
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByIds(List<String> orgPersonRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationIds, "orgPersonRelationIds");

        List<OrgPersonRelation> oprts = organizationDao.getOrgPersonRelationsByIdList(orgPersonRelationIds);
        return OrganizationAssembler.toOrgPersonRelationInfos(oprts);
    }

    @Override
    public List<String> getOrgPersonRelationIdsByType(String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        List<String> orgPersonRelationIds = new ArrayList<String>();
        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByType(orgPersonRelationTypeKey);
        if (orgPersonRelations != null) {
            for (OrgPersonRelation orgPersonRelation : orgPersonRelations) {
                orgPersonRelationIds.add(orgPersonRelation.getId());
            }
        }

        return orgPersonRelationIds;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<OrgPersonRelation> relations = organizationDao.getAllOrgPersonRelationsByOrg(orgId);
        return OrganizationAssembler.toOrgPersonRelationInfos(relations);
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrg(String orgPersonRelationTypeKey, String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(orgId, "orgId");

        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByTypeAndOrg(orgPersonRelationTypeKey, orgId);
        return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelations);
    }

    @Override
    @Deprecated
    public OrgPersonRelationInfo getOrgPersonRelationByTypeAndOrg(String orgPersonRelationTypeKey, String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> infos = getOrgPersonRelationsByTypeAndOrg(orgPersonRelationTypeKey, orgId, contextInfo);
        return (infos.size() >0 ? infos.get(0) : new OrgPersonRelationInfo());
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByPerson(personId);
        return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelations);
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndPerson(String orgPersonRelationTypeKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByTypeAndPerson(orgPersonRelationTypeKey, personId);
        return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelations);
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrgAndPerson(String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByOrgAndPerson(orgId, personId);
        return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelations);
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrgAndPerson(String orgPersonRelationTypeKey, String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(personId, "personId");

        List<OrgPersonRelation> orgPersonRelations = extendedOrgDao.getOrgPersonRelationsByTypeAndOrgAndPerson(orgPersonRelationTypeKey, orgId, personId);
        return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelations);
    }

    @Override
    public List<String> searchForOrgPersonRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgPersonRelationIds = new ArrayList<String>();
        List<OrgPersonRelationInfo> orgPersonRelations = this.searchForOrgPersonRelations(criteria, contextInfo);

        if (orgPersonRelations != null) {
            for (OrgPersonRelationInfo orgPersonRelation : orgPersonRelations) {
                orgPersonRelationIds.add(orgPersonRelation.getId());
            }
        }

        return orgPersonRelationIds;
    }

    @Override
    public List<OrgPersonRelationInfo> searchForOrgPersonRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPersonRelationInfo> orgPersonRelationInfos = new ArrayList<OrgPersonRelationInfo>();
        GenericQueryResults<OrgPersonRelation> results = criteriaLookupService.lookup(OrgPersonRelation.class, criteria);

        if (null != results && results.getResults().size() > 0) {
            for (OrgPersonRelation orgPersonRelation : results.getResults()) {
                orgPersonRelationInfos.add(OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation));
            }
        }

        return orgPersonRelationInfos;
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

        // Make sure that only valid org person relations are done
        if (!organizationDao.validatePositionRestriction(orgId, orgPersonRelationTypeKey)) {
            throw new InvalidParameterException("There is no Position for this relationship");
        }

        // Set all the values on OrgOrgRelationInfo
        orgPersonRelationInfo.setOrgId(orgId);
        orgPersonRelationInfo.setPersonId(personId);
        orgPersonRelationInfo.setTypeKey(orgPersonRelationTypeKey);

        OrgPersonRelation orgPersonRelation = null;

        // Create a new persistence entity from the orgInfo
        try {
            orgPersonRelation = OrganizationAssembler.toOrgPersonRelation(false, orgPersonRelationInfo, organizationDao);
        } catch (VersionMismatchException e) {}

        // Persist the orgPersonRelation
        organizationDao.create(orgPersonRelation);

        // Copy back to an orgPersonRelationInfo and return
        OrgPersonRelationInfo createdOrgPersonRelationInfo = OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation);
        return createdOrgPersonRelationInfo;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");
        checkForMissingParameter(orgPersonRelationInfo, "orgPersonRelationInfo");

        // Make sure that only valid org person relations are done
        if (!organizationDao.validatePositionRestriction(orgPersonRelationInfo.getOrgId(), orgPersonRelationInfo.getTypeKey())) {
            throw new InvalidParameterException("There is no Position for this relationship");
        }

        // Set all the values on OrgPersonRelationInfo
        orgPersonRelationInfo.setId(orgPersonRelationId);

        OrgPersonRelation orgPersonRelation = null;

        // Update persistence entity from the orgInfo
        orgPersonRelation = OrganizationAssembler.toOrgPersonRelation(true, orgPersonRelationInfo, organizationDao);

        // Update the orgPersonRelation
        orgPersonRelation = organizationDao.update(orgPersonRelation);

        // Copy back to an orgPersonRelationInfo and return
        OrgPersonRelationInfo createdOrgPersonRelationInfo = OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation);
        return createdOrgPersonRelationInfo;
    }

    @Override
    public StatusInfo deleteOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

        try {
            organizationDao.delete(OrgPersonRelation.class, orgPersonRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return new StatusInfo();
    }

    @Override
    public OrgPositionRestrictionInfo getOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");

        OrgPositionRestriction orgPositionRestriction;
        try {
            orgPositionRestriction = organizationDao.fetch(OrgPositionRestriction.class, orgPositionRestrictionId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return OrganizationAssembler.toOrgPositionRestrictionInfo(orgPositionRestriction);
    }

    @Override
    public List<OrgPositionRestrictionInfo> getOrgPositionRestrictionsByIds(List<String> orgPositionRestrictionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionIds, "orgPositionRestrictionIds");

        List<OrgPositionRestriction> orgPositionRestrictions = extendedOrgDao.getOrgPositionRestrictionsByIds(orgPositionRestrictionIds);
        return OrganizationAssembler.toOrgPositionRestrictionInfos(orgPositionRestrictions);
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByType(String orgPositionRestrictionTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionTypeKey, "orgPositionRestrictionTypeKey");

        List<String> orgPositionRestrictionIds = new ArrayList<String>();
        List<OrgPositionRestriction> orgPositionRestrictions = extendedOrgDao.getOrgPositionRestrictionsByType(orgPositionRestrictionTypeKey);

        if (orgPositionRestrictions != null){
            for (OrgPositionRestriction orgPositionRestriction : orgPositionRestrictions){
                orgPositionRestrictionIds.add(orgPositionRestriction.getId());
            }
        }

        return orgPositionRestrictionIds;
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");

        List<OrgPositionRestriction> restrictions = organizationDao.getPositionRestrictionsByOrg(orgId);

        List<String> restrictionIds = new ArrayList<String>(restrictions.size());
        for (OrgPositionRestriction restriction : restrictions) {
            restrictionIds.add(restriction.getId());
        }
        return restrictionIds;
    }

    @Override
    public List<String> searchForOrgPositionRestrictionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> orgPositionRestrictionIds = new ArrayList<String>();
        List<OrgPositionRestrictionInfo> orgPositionRestrictionInfos = this.searchForOrgPositionRestrictions(criteria, contextInfo);

        if (orgPositionRestrictionInfos != null) {
            for (OrgPositionRestrictionInfo orgPositionRestrictionInfo : orgPositionRestrictionInfos) {
                orgPositionRestrictionIds.add(orgPositionRestrictionInfo.getId());
            }
        }
        return orgPositionRestrictionIds;
    }

    @Override
    public List<OrgPositionRestrictionInfo> searchForOrgPositionRestrictions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<OrgPositionRestrictionInfo> orgPositionRestrictionInfos = new ArrayList<OrgPositionRestrictionInfo>();
        GenericQueryResults<OrgPositionRestriction> results = criteriaLookupService.lookup(OrgPositionRestriction.class, criteria);

        if (null != results && results.getResults().size() > 0) {
            for (OrgPositionRestriction orgPositionRestriction : results.getResults()) {
                orgPositionRestrictionInfos.add(OrganizationAssembler.toOrgPositionRestrictionInfo(orgPositionRestriction));
            }
        }

        return orgPositionRestrictionInfos;
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

        // Set all the values on OrgOrgRelationInfo
        orgPositionRestrictionInfo.setOrgId(orgId);
        orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPositionRestrictionTypeKey);

        OrgPositionRestriction orgPositionRestriction = null;

        // Create a new persistence entity from the Info
        try {
            orgPositionRestriction = OrganizationAssembler.toOrgPositionRestriction(false, orgPositionRestrictionInfo, organizationDao);
        } catch (VersionMismatchException e) {}

        // Persist the positionRestriction
        organizationDao.create(orgPositionRestriction);

        // Copy back to an OrgOrgRelationInfo and return
        OrgPositionRestrictionInfo createdOrgPositionRestrictionInfo = OrganizationAssembler.toOrgPositionRestrictionInfo(orgPositionRestriction);
        return createdOrgPositionRestrictionInfo;
    }

    @Override
    public OrgPositionRestrictionInfo updateOrgPositionRestriction(String orgPositionRestrictionId, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");
        checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        // Set all the values on OrgOrgRelationInfo
        // orgPositionRestrictionInfo.setOrgId(orgId);
        // orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPersonRelationTypeKey);

        OrgPositionRestriction orgPositionRestriction = null;

        // Update persistence entity from the Info
        try {
            orgPositionRestriction = OrganizationAssembler.toOrgPositionRestriction(true, orgPositionRestrictionInfo, organizationDao);
        } catch (VersionMismatchException e) {}

        // Update the positionRestriction
        OrgPositionRestriction updated = organizationDao.update(orgPositionRestriction);

        // Copy back to an OrgOrgRelationInfo and return
        OrgPositionRestrictionInfo updatedOrgPositionRestrictionInfo = OrganizationAssembler.toOrgPositionRestrictionInfo(updated);
        return updatedOrgPositionRestrictionInfo;
    }

    @Override
    public StatusInfo deleteOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgPositionRestrictionId, "orgPositionRestrictionId");

        try {
            organizationDao.delete(OrgPositionRestriction.class, orgPositionRestrictionId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException(e.getMessage());
        }
        return new StatusInfo();
    }

    @Override
    public Boolean isDescendant(String orgId, String descendantOrgId, String orgHierarchyId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(descendantOrgId, "descendantOrgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        // get ancestors of the descendant, as it will be more efficient in most cases
        List<String> ancestors = organizationDao.getAllAncestors(descendantOrgId, orgHierarchyId);
        boolean result = ancestors.contains(orgId);
        return Boolean.valueOf(result);
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        List<String> descendants = this.organizationDao.getAllDescendants(orgId, orgHierarchyId);
        return descendants;
    }

    @Override
    public List<String> getAllAncestors(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(orgId, "orgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        List<String> ancestors = this.organizationDao.getAllAncestors(orgId, orgHierarchyId);
        return ancestors;
    }

    @Override
    public List<OrgTreeInfo> getOrgTree(String rootOrgId, String orgHierarchyId, int maxLevels, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(rootOrgId, "rootOrgId");
        checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        Set<OrgTreeInfo> results = new HashSet<OrgTreeInfo>();
        try {
            Org rootOrg = organizationDao.fetch(Org.class, rootOrgId);

            OrgTreeInfo root = new OrgTreeInfo();
            root.setOrgId(rootOrgId);
            root.setParentId(null);
            root.setDisplayName(rootOrg.getLongName());
            root.setPositions(this.organizationDao.getOrgMembershipCount(root.getOrgId()));
            root.setOrgHierarchyId(orgHierarchyId);
            results.add(root);
            if (maxLevels >= 0) {
                results.addAll(parseOrgTree(rootOrgId, orgHierarchyId, maxLevels, 0));
            }

        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException();
        }

        return new ArrayList<OrgTreeInfo>(results);
    }

    private List<OrgTreeInfo> parseOrgTree(String rootOrgId,
                                           String orgHierarchyId, int maxLevels, int currentLevel) {
        List<OrgTreeInfo> results = new ArrayList<OrgTreeInfo>();
        if(maxLevels==0||currentLevel<maxLevels){
            List<OrgTreeInfo> orgTreeInfos = this.organizationDao.getOrgTreeInfo(rootOrgId,orgHierarchyId);
            for(OrgTreeInfo orgTreeInfo:orgTreeInfos){
                orgTreeInfo.setPositions(this.organizationDao.getOrgMembershipCount(orgTreeInfo.getOrgId()));
                results.addAll(parseOrgTree(orgTreeInfo.getOrgId(),orgHierarchyId, maxLevels, currentLevel+1));
            }
            results.addAll(orgTreeInfos);
        }
        return results;
    }

    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, organizationDao);
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
            throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
            throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
            throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }
}
