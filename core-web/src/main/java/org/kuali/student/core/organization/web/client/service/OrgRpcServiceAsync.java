package org.kuali.student.core.organization.web.client.service;

import java.util.List;

import javax.jws.WebParam;

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
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrgRpcServiceAsync {
    public void createOrganization(OrgInfo orgInfo, AsyncCallback<OrgInfo> callback);
    public void createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo, AsyncCallback<OrgOrgRelationInfo> callback);

    public void getOrgHierarchies(AsyncCallback<List<OrgHierarchyInfo>> callback); 
    public void getOrganization(String orgId, AsyncCallback<OrgInfo> callback);
    public void getOrganizationsByIdList(List<String> orgIdList, AsyncCallback<List<OrgInfo> > callback);
    public void getOrgOrgRelationsByOrg(String orgId, AsyncCallback<List<OrgOrgRelationInfo>> callback);
    public void getAllDescendants(String orgId, String orgHierarchy, AsyncCallback<List<String>> callback);
    public void getOrgOrgRelationTypes(AsyncCallback<List<OrgOrgRelationTypeInfo>> callback);
    public void getOrgPersonRelationTypes(AsyncCallback<List<OrgPersonRelationTypeInfo>> callback);
    public void getOrgTypes(AsyncCallback<List<OrgTypeInfo>> callback);
    public void getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels, AsyncCallback<String> callback);
   
    public void getPositionRestrictionsByOrg(String orgId, AsyncCallback<List<OrgPositionRestrictionInfo>> callback);

    public void addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo, AsyncCallback<OrgPositionRestrictionInfo> callback);

    public void updateOrganization(OrgInfo orgInfo, AsyncCallback<OrgInfo> callback);
    public void updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo, AsyncCallback<OrgPositionRestrictionInfo> callback);
    public void updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo, AsyncCallback<OrgOrgRelationInfo> callback);
}
