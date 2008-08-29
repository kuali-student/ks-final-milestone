/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.authz.service;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.authz.dto.PermissionDTO;
import org.kuali.student.authz.dto.PrincipalDTO;
import org.kuali.student.authz.dto.QualifiedRoleDTO;
import org.kuali.student.authz.dto.QualifierDTO;
import org.kuali.student.authz.dto.QualifierHierarchyDTO;
import org.kuali.student.authz.dto.QualifierTypeDTO;
import org.kuali.student.authz.dto.RoleDTO;

@WebService(name = "AuthorizationService", targetNamespace = "http://student.kuali.org/authz")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AuthorizationService {
    // CLIENT API

    @WebMethod
    public boolean isPrincipalAuthorized(
            @WebParam(name = "principalId")String principalId, 
            @WebParam(name = "namespaceName")String namespaceName, 
            @WebParam(name = "permissionId")String permissionId);

    @WebMethod
    public boolean isPrincipalAuthorizedForQualifiedPermission(
            @WebParam(name = "principalId")String principalId, 
            @WebParam(name = "namespaceName")String namespaceName,
            @WebParam(name = "permissionId")String permissionId, 
            @WebParam(name = "qualifications")Map<String, String> qualifications);

    // EXTENDED CLIENT API

    @WebMethod
    public boolean principalHasRole(
            @WebParam(name = "principalId")String principalId, 
            @WebParam(name = "roleId")String roleId);

    @WebMethod
    public boolean principalHasQualifiedRole(
            @WebParam(name = "principalId")String principalId, 
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualifications")Map<String, String> qualifications);

    @WebMethod
    public List<RoleDTO> getRolesForPrincipal(@WebParam(name = "principalId")String principalId);

    @WebMethod
    public List<QualifiedRoleDTO> getQualifiedRolesForPrincipal(@WebParam(name = "principalId")String principalId);

    @WebMethod
    public List<String> getPrincipalIdsWithRole(@WebParam(name = "roleId")String roleId);

    @WebMethod
    public List<String> getPrincipalIdsWithQualifiedRole(
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualifications")Map<String, String> qualifications);
    
    @WebMethod
    public List<PrincipalDTO> getPrincipalsWithQualifierAndPermissions(
            @WebParam(name = "permissionId")String permissionId, 
            @WebParam(name = "qualifierId")String qualifierId);
    
    
    @WebMethod
    public List<String> getGroupIdsWithRole(@WebParam(name = "roleId")String roleId);

    @WebMethod
    public List<String> getGroupIdsWithQualifiedRole(
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualifications")Map<String, String> qualifications);

    @WebMethod
    public List<PermissionDTO> getPermissionsForRole(@WebParam(name = "roleId")String roleId);

    // KIM INTERNAL METHODS

    @WebMethod
    public RoleDTO getRole(@WebParam(name = "roleId")String roleId);

    @WebMethod
    public PermissionDTO getPermission(@WebParam(name = "permissionId")String permissionId);

    @WebMethod
    public List<PermissionDTO> getPermissions();
    
    @WebMethod
    public void saveRole(@WebParam(name = "role")RoleDTO role);

    @WebMethod
    public void savePermission(@WebParam(name = "permission")PermissionDTO permission);

    @WebMethod
    public void assignQualifiedRoleToPrincipal(
            @WebParam(name = "principalId")String principalId, 
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualifications")Map <String, String> qualifcations);

    @WebMethod
    public void assignQualifiedRoleToGroup(
            @WebParam(name = "groupId")String groupId, 
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualifications")Map <String, String> qualifications);

    
    @WebMethod
    public List<QualifierDTO> getQualifierRoots(
    		@WebParam(name = "qualifierHierarchyId")String qhId);

    @WebMethod
    public List<QualifierDTO> getQualifiersDirectDescendents(
    		@WebParam(name = "qualifierId")String qid,
			@WebParam(name = "qualifierHierarchyId")String qhId);
    
    @WebMethod
	public List<PrincipalDTO> getPrincipals();

    @WebMethod
	public PrincipalDTO getPrincipal(
			@WebParam(name = "groupId")String pid);
    
    @WebMethod
	public List<RoleDTO> getRoles();
    
    @WebMethod
	public void assignQualifiedRoleIdToPrincipal(            
			@WebParam(name = "principalId")String principalId, 
            @WebParam(name = "roleId")String roleId, 
            @WebParam(name = "qualificationIds")String qualifierId,
            @WebParam(name = "descendTree")boolean descendTree);

    @WebMethod
    public void saveQualifierType(
			@WebParam(name = "qualifierType")QualifierTypeDTO qualifierTypeDTO);

    @WebMethod
    public List<QualifierTypeDTO> getQualifierTypes();

    @WebMethod
    public QualifierTypeDTO getQualifierType(
    		@WebParam(name = "qualifierTypeId")String id);

    @WebMethod
    public void savePrincipal(
    		@WebParam(name = "principal")PrincipalDTO principal);

    @WebMethod
    public List<QualifierHierarchyDTO> getQualifierHierarchies();
    
    @WebMethod
	public String saveQualifierHierarchy(
			@WebParam(name = "qualifierHierarchy")QualifierHierarchyDTO qualifierHierarchy);

    @WebMethod
    public QualifierHierarchyDTO getQualifierHierarchy(
    		@WebParam(name = "id")String qhId);

    @WebMethod
    public QualifierDTO getQualifier(
    		@WebParam(name = "id")String id);

    @WebMethod
	public String saveQualifier(
			@WebParam(name = "qualifier")QualifierDTO q);

    /* TODO
    public List<RoleDTO> lookupRoles(Map<String,String> searchCriteria, Map<String, String> roleAttributes, Map<String,String> qualifications);

    public List<PermissionDTO> lookupPermissions(Map<String,String> searchCriteria);
    */
}

