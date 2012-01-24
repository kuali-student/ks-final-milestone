/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.class1.organization.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.core.class1.organization.dao.OrgCodeDao;
import org.kuali.student.r2.core.class1.organization.dao.OrgDao;
import org.kuali.student.r2.core.class1.organization.dao.OrgHierarchyDao;
import org.kuali.student.r2.core.class1.organization.dao.OrgOrgRelationDao;
import org.kuali.student.r2.core.class1.organization.dao.OrgPersonRelationDao;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.organization.service.OrganizationService", serviceName = "OrganizationService", portName = "OrganizationService", targetNamespace = "http://student.kuali.org/wsdl/organization")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class OrganizationServiceImpl implements OrganizationService{
    
    private OrgCodeDao orgCodeDao;
    private OrgDao orgDao;
    private OrgHierarchyDao orgHierarchyDao;
    private OrgOrgRelationDao orgOrgRelationDao;
    private OrgPersonRelationDao orgPersonRelationDao;
    
    public OrgCodeDao getOrgCodeDao() {
        return orgCodeDao;
    }

    public void setOrgCodeDao(OrgCodeDao orgCodeDao) {
        this.orgCodeDao = orgCodeDao;
    }

    public OrgDao getOrgDao() {
        return orgDao;
    }

    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    public OrgHierarchyDao getOrgHierarchyDao() {
        return orgHierarchyDao;
    }

    public void setOrgHierarchyDao(OrgHierarchyDao orgHierarchyDao) {
        this.orgHierarchyDao = orgHierarchyDao;
    }

    public OrgOrgRelationDao getOrgOrgRelationDao() {
        return orgOrgRelationDao;
    }

    public void setOrgOrgRelationDao(OrgOrgRelationDao orgOrgRelationDao) {
        this.orgOrgRelationDao = orgOrgRelationDao;
    }

    public OrgPersonRelationDao getOrgPersonRelationDao() {
        return orgPersonRelationDao;
    }

    public void setOrgPersonRelationDao(OrgPersonRelationDao orgPersonRelationDao) {
        this.orgPersonRelationDao = orgPersonRelationDao;
    }

    @Override
    public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgHierarchyKey, "orgHierarchyKey");

        //return OrganizationAssembler.toOrgHierarchyInfo(organizationDao.fetch(OrgHierarchy.class, orgHierarchyKey));
        return null;
    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchiesByIds(List<String> orgHierarchyIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getOrgHierarchyIdsByType(String orgHierarchyTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // return OrganizationAssembler.toOrgHierarchyInfos(organizationDao.find(OrgHierarchy.class));
        return null;
    }

    @Override
    public List<TypeInfo> getOrgTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // return OrganizationAssembler.toOrgTypeInfos(organizationDao.find(OrgType.class));
        return null;
    }

    @Override
    public OrgInfo getOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");

        //return OrganizationAssembler.toOrgInfo(organizationDao.fetch(Org.class, orgId));
        return null;
    }

    @Override
    public List<OrgInfo> getOrgsByIds(List<String> orgIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgIdList, "orgIdList");

        //List<Org> orgs = this.organizationDao.getOrganizationsByIdList(orgIdList);
        //return OrganizationAssembler.toOrgInfos(orgs);
        return null;
    }

    @Override
    public List<String> getOrgIdsByType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForOrgIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgInfo> searchForOrgs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateOrg(String validationTypeKey, String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgInfo createOrg(String orgTypeKey, OrgInfo orgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // Check Missing params
        //checkForMissingParameter(orgTypeKey, "orgTypeKey");
        //checkForMissingParameter(orgInfo, "orgInfo");

        //Set all the values on orgInfo
        //orgInfo.setType(orgTypeKey);

        //try {
        //    List<ValidationResultInfo> validations = validateOrg("", orgInfo);
        //    for (ValidationResultInfo validationResult : validations) {
        //        if(validationResult.isError())
        //            throw new DataValidationErrorException(validationResult.toString());
        //    }
        //} catch (DoesNotExistException e1) {
        //    logger.error("Exception occured: ", e1);
        //}

        //Org org = null;

        //Create a new persistence entity from the orgInfo
        //try {
        //    org = OrganizationAssembler.toOrg(false, orgInfo, organizationDao);
        //} catch (DoesNotExistException e) {
        //    // OrgAssembler should not be throwing this exception for create!
        //    logger.info(e.getMessage(), e);
        //    throw new OperationFailedException(e.getMessage(), e);
        //} catch (VersionMismatchException e) {
        //    // OrgAssembler should not be throwing this exception for create!
        //    logger.info(e.getMessage(), e);
        //    throw new OperationFailedException(e.getMessage(), e);          
        //}

        //Persist the org
        //organizationDao.create(org);

        //Copy back to an orgInfo and return
        //OrgInfo createdOrgInfo = OrganizationAssembler.toOrgInfo(org);
        //return createdOrgInfo;
        return null;
    }

    @Override
    public OrgInfo updateOrg(String orgId, OrgInfo orgInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // checkForMissingParameter(orgId, "orgId");

        //Org org = organizationDao.fetch(Org.class, orgId);

        //if(org==null){
        //    throw new DoesNotExistException("Org does not exist for id: "+orgId);
        //}

        //organizationDao.delete(org);

        //StatusInfo statusInfo = new StatusInfo();
       // statusInfo.setSuccess(true);
        //return statusInfo;
        return null;
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
      //Check Missing params
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(comparisonOrgId, "comparisonOrgId");
        //checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

        //boolean result = organizationDao.hasOrgOrgRelation(orgId, comparisonOrgId,
        //        orgOrgRelationTypeKey);
        //return Boolean.valueOf(result);
        return null;
    }

    @Override
    public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByIds(List<String> orgOrgRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getOrgOrgRelationIdsByType(String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrgs(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByTypeAndOrg(String orgId, String orgOrgRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForOrgOrgRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgOrgRelationInfo> searchForOrgOrgRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateOrgOrgRelation(String validationTypeKey, String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(String orgId, String orgPeerId, String orgOrgRelationTypeKey, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId, OrgOrgRelationInfo orgOrgRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteOrgOrgRelation(String orgOrgRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

        //organizationDao.delete(OrgOrgRelation.class, orgOrgRelationId);
        //return new StatusInfo();
        return null;
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //List<OrgPersonRelationType> oprts = organizationDao.find(OrgPersonRelationType.class);
        //return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
        return null;
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(String orgTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgTypeKey, "orgTypeKey");

        //List<OrgPersonRelationType> oprts = organizationDao.getOrgPersonRelationTypesForOrgType(orgTypeKey);
        //return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
        return null;
    }

    @Override
    public Boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(personId, "personId");
        //checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

        //return organizationDao.hasOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey);
        return null;
    }

    @Override
    public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

        //OrgPersonRelation opr = organizationDao.fetch(OrgPersonRelation.class, orgPersonRelationId);
        //return OrganizationAssembler.toOrgPersonRelationInfo(opr);
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByIds(List<String> orgPersonRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getOrgPersonRelationIdsByType(String orgPersonRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrg(String orgPersonRelationTypeKey, String orgId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndPerson(String orgPersonRelationTypeKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrgAndPerson(String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByTypeAndOrgAndPerson(String orgPersonRelationTypeKey, String orgId, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForOrgPersonRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> searchForOrgPersonRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateOrgPersonRelation(String validationTypeKey, String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(String orgPersonRelationId, OrgPersonRelationInfo orgPersonRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteOrgPersonRelation(String orgPersonRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo getOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPositionRestrictionInfo> getOrgPositionRestrictionsByIds(List<String> orgPositionRestrictionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByType(String orgPositionRestrictionTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getOrgPositionRestrictionIdsByOrg(String orgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForOrgPositionRestrictionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<OrgPositionRestrictionInfo> searchForOrgPositionRestrictions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateOrgPositionRestriction(String validationTypeKey, String orgId, String orgPositionRestrictionTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(validationType, "validationType");
        //checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        //return null;
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo createOrgPositionRestriction(String orgId, String orgPositionRestrictionTypeKey, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
      //Check Missing params
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        //checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        //Set all the values on OrgOrgRelationInfo
        //orgPositionRestrictionInfo.setOrgId(orgId);
        //orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPersonRelationTypeKey);

        //OrgPositionRestriction orgPositionRestriction = null;

        //Create a new persistence entity from the Info
        //try {
        //    orgPositionRestriction = OrganizationAssembler.toOrgPositionRestriction(false, orgPositionRestrictionInfo, organizationDao);
        //} catch (VersionMismatchException e) {
        //}

        //Persist the positionRestriction
        //organizationDao.create(orgPositionRestriction);

        //Copy back to an OrgOrgRelationInfo and return
        //OrgPositionRestrictionInfo createdOrgPositionRestrictionInfo = OrganizationAssembler.toOrgPositionRestrictionInfo(orgPositionRestriction);
        //return createdOrgPositionRestrictionInfo;
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo updateOrgPositionRestriction(String orgPositionRestrictionId, OrgPositionRestrictionInfo orgPositionRestrictionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        //checkForMissingParameter(validationType, "validationType");
        //checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

        //return null;
        return null;
    }

    @Override
    public StatusInfo deleteOrgPositionRestriction(String orgPositionRestrictionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
        //OrgPositionRestriction opr = null;
        //try {
        //    opr = organizationDao.getPositionRestrictionByOrgAndPersonRelationTypeKey(orgId, orgPersonRelationTypeKey);
        //    if (opr == null) {
        //        throw new DoesNotExistException();
        //    }
        //} catch (NoResultException e) {
        //    throw new DoesNotExistException();
        //}
        //organizationDao.delete(opr);
        //return new StatusInfo();
        return null;
    }

    @Override
    public Boolean isDescendant(String orgId, String descendantOrgId, String orgHierarchyId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(descendantOrgId, "descendantOrgId");
        //checkForMissingParameter(orgHierarchy, "orgHierarchy");

        // get ancestors of the descendant, as it will be more efficient in most cases
        //List<String> ancestors = organizationDao.getAllAncestors(descendantOrgId, orgHierarchy);
        //boolean result = ancestors.contains(orgId);
        //return Boolean.valueOf(result);
        return null;
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(orgId, "orgHierarchy");

        //List<String> descendants = this.organizationDao.getAllDescendants(orgId, orgHierarchy);
        //return descendants;
        return null;
    }

    @Override
    public List<String> getAllAncestors(String orgId, String orgHierarchyId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(orgId, "orgId");
        //checkForMissingParameter(orgHierarchy, "orgHierarchy");

        //List<String> ancestors = this.organizationDao.getAllAncestors(orgId, orgHierarchy);
        //return ancestors;
        return null;
    }

    @Override
    public List<OrgTreeInfo> getOrgTree(String rootOrgId, String orgHierarchyId, int maxLevels, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //checkForMissingParameter(rootOrgId, "rootOrgId");
        //checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

        //Set<OrgTreeInfo> results = new HashSet<OrgTreeInfo>();
        //Org rootOrg = organizationDao.fetch(Org.class, rootOrgId);
        //OrgTreeInfo root = new OrgTreeInfo(rootOrgId,null,rootOrg.getLongName());
        //root.setPositions(this.organizationDao.getOrgMemebershipCount(root.getOrgId()));
        //root.setOrgHierarchyId(orgHierarchyId);
        //results.add(root);
        //if(maxLevels>=0){
        //    results.addAll(parseOrgTree(rootOrgId, orgHierarchyId, maxLevels,0));
        //}
        //return new ArrayList<OrgTreeInfo>(results);
        return null;
    }

}
