/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.organization.ui.server.gwt;

import java.util.List;

import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

public class OrgRpcGwtServlet extends BaseRpcGwtServletAbstract<OrganizationService> implements OrgRpcService{

	private static final long serialVersionUID = 1L;

    @Override
    public StatusInfo removePositionRestrictionFromOrg(String orgId, String orgPersonRelationTypeKey){
        try {
            return service.removePositionRestrictionFromOrg(orgId, orgPersonRelationTypeKey);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId){
        try {
            return service.removeOrgOrgRelation(orgOrgRelationId);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies() {
        try {
            return service.getOrgHierarchies();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;    
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
        // TODO Auto-generated method stub
        try {
            return service.getOrgOrgRelationsByOrg(orgId);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
        // TODO Auto-generated method stub
        try {
            return service.getOrganizationsByIdList(orgIdList);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchy) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.getAllDescendants(orgId, orgHierarchy);
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.addPositionRestrictionToOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgInfo createOrganization(OrgInfo orgInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.createOrganization(orgInfo.getType(), orgInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(),
                    orgOrgRelationInfo.getType(), orgOrgRelationInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes() {
        try {
            return service.getOrgPersonRelationTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } 
        return null;
    }

    public List<OrgTypeInfo> getOrgTypes() {
        try {
            return service.getOrgTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes() {
        try {
            return service.getOrgOrgRelationTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels) {
        try {
            return service.getOrgTree(orgId, orgHierarchy, maxLevels);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrgInfo getOrganization(String orgId) {
        try {
            return service.getOrganization(orgId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId) {
        try {
            return service.getPositionRestrictionsByOrg(orgId);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public OrgInfo updateOrganization(OrgInfo orgInfo) {
        try {
            return service.updateOrganization(orgInfo.getId(), orgInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        } 
        return null;
    }


    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
            return service.updatePositionRestrictionForOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
            return service.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
            String personId, String orgPersonRelationTypeKey,
            OrgPersonRelationInfo orgPersonRelationInfo) {

        try {
            return service.createOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
            String orgTypeKey) {
        try {
            return service.getOrgPersonRelationTypesForOrgType(orgTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId) {
        try {
            return service.getOrgPersonRelationsByOrg(orgId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId) {
        try {
            return service.removeOrgPersonRelation(orgPersonRelationId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(
            String orgPersonRelationId,
            OrgPersonRelationInfo orgPersonRelationInfo) {
        try {
            return service.updateOrgPersonRelation(orgPersonRelationId, orgPersonRelationInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        }
        return null;
    }	
}
