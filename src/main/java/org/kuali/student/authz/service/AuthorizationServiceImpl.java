/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.authz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.kuali.student.authz.dao.AuthorizationDAO;
import org.kuali.student.authz.dto.PermissionDTO;
import org.kuali.student.authz.dto.PrincipalDTO;
import org.kuali.student.authz.dto.QualifiedRoleDTO;
import org.kuali.student.authz.dto.QualifierDTO;
import org.kuali.student.authz.dto.QualifierHierarchyDTO;
import org.kuali.student.authz.dto.QualifierTypeDTO;
import org.kuali.student.authz.dto.RoleDTO;
import org.kuali.student.authz.entity.Authorization;
import org.kuali.student.authz.entity.Permission;
import org.kuali.student.authz.entity.Principal;
import org.kuali.student.authz.entity.Qualifier;
import org.kuali.student.authz.entity.QualifierHierarchy;
import org.kuali.student.authz.entity.QualifierType;
import org.kuali.student.authz.entity.Role;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@WebService(endpointInterface = "org.kuali.student.authz.service.AuthorizationService", serviceName = "AuthorizationService", portName = "AuthorizationService", targetNamespace = "http://student.kuali.org/authz")
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

    private AuthorizationDAO authDAO;
    
    /**
     * This is for non-qualfied role.
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#isPrincipalAuthorized(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public boolean isPrincipalAuthorized(String principalId, String namespaceName, String permissionId) {
        return authDAO.hasPermission(principalId, permissionId);
    }

    /**
     * This method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#isPrincipalAuthorizedForQualifiedPermission(java.lang.String,
     *      java.lang.String, java.lang.String, org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public boolean isPrincipalAuthorizedForQualifiedPermission(String principalId, String namespaceName, String permissionId, Map<String,String> qualifications) {
        //TODO: namespace
        
        boolean isAuthorized = false;
        Set<String> keysQT = qualifications.keySet();
        
        // More than one key represents a Composite Key
        if (keysQT.size() == 1){
            String qType = keysQT.iterator().next();
            String qValue = qualifications.get(qType);
            Qualifier qualifier = authDAO.findQualifierByType(qType, qValue);
            isAuthorized = authDAO.hasPermission(qualifier, principalId, permissionId); 
        } else {
            Qualifier qualifier = authDAO.findCompositeQualifierByType(qualifications);
            isAuthorized = authDAO.hasPermission(qualifier, principalId, permissionId);
        }

        return isAuthorized;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#assignQualifiedRoleToGroup(java.lang.String,
     *      java.lang.String, org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public void assignQualifiedRoleToGroup(String groupId, String roleId, Map<String,String> qualifications) {
    // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS

    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#assignQualifiedRoleToPrincipal(java.lang.String,
     *      java.lang.String, org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public void assignQualifiedRoleToPrincipal(String principalId, String roleId, Map<String,String> qualifications) {
    // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS

    }

    @Override
    public void assignQualifiedRoleIdToPrincipal(String principalId,
            String roleId, String qualifierId, boolean descendTree) {

        Authorization authorization = new Authorization();
        
        Principal principal = authDAO.fetchPrincipal(principalId);
        principal.getAuthorizations().add(authorization);
        
        Role role = authDAO.fetchRole(roleId);
        role.getAuthorizations().add(authorization);
        

        Qualifier qualifier = authDAO.fetchQualifier(qualifierId);
        qualifier.getAuthorizations().add(authorization);
        
        authorization.setPrincipal(principal);
        authorization.setRole(role);
        authorization.setQualifier(qualifier);
        //Set the descends tree only if the role has a QH
        if(role.getQualifierHierarchy()!=null){
            authorization.setDescendTree(descendTree);
        }else{
            authorization.setDescendTree(false);
        }
        
        authDAO.createAuthorization(authorization);
    }
    
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getGroupIdsWithQualifiedRole(java.lang.String,
     *      org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public List<String> getGroupIdsWithQualifiedRole(String roleId, Map<String,String> qualifications) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getGroupIdsWithRole(java.lang.String)
     */
    @Override
    public List<String> getGroupIdsWithRole(String roleId) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getPermission(java.lang.String)
     */
    @Override
    public PermissionDTO getPermission(String permissionId) {       
        Permission permission = authDAO.fetchPermission(permissionId);
        
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setName(permission.getName());
        
        return permissionDTO;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getPermissionsForRole(java.lang.String)
     */
    @Override
    public List<PermissionDTO> getPermissionsForRole(String roleId) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getPrincipalIdsWithQualifiedRole(java.lang.String,
     *      org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public List<String> getPrincipalIdsWithQualifiedRole(String roleId, Map<String,String> qualifications) {

        
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getPrincipalIdsWithRole(java.lang.String)
     */
    @Override
    public List<String> getPrincipalIdsWithRole(String roleId) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getRole(java.lang.String)
     */
    @Override
    public RoleDTO getRole(String roleId) {
        
    	RoleDTO roleDTO = new RoleDTO();
        Role role = authDAO.fetchRole(roleId);

		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		
		for(Permission permission:role.getPermissions()){
			PermissionDTO permissionDTO = new PermissionDTO();
			permissionDTO.setId(permission.getId());
			permissionDTO.setName(permission.getName());
			roleDTO.getPermissions().add(permissionDTO);
		}
		
		QualifierHierarchyDTO qhDTO = new QualifierHierarchyDTO();
		QualifierHierarchy qh = role.getQualifierHierarchy();
		if(qh!=null){
			qhDTO.setId(qh.getId());
			qhDTO.setName(qh.getName());
			roleDTO.setQualifierHierarchy(qhDTO);
		}
		
		QualifierTypeDTO qtDTO = new QualifierTypeDTO();
		QualifierType qt = role.getQualifierType();
		qtDTO.setId(qt.getId());
		qtDTO.setName(qt.getName());
		
		for(QualifierType qtpk:qt.getPkQualifierTypes()){
			QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
			qtpkDTO.setId(qtpk.getId());
			qtpkDTO.setName(qtpk.getName());
			qtDTO.getPkQualifierTypes().add(qtpkDTO);
		}
		roleDTO.setQualifierType(qtDTO);
            
        return roleDTO;
    }

    /**
     * What qualified roles does this principal have
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#getRolesForPrincipal(java.lang.String)
     */
    @Override
    public List<QualifiedRoleDTO> getQualifiedRolesForPrincipal(String principalId) {
    	//TODO Not everything is copied here should this return a deep or shallow copy of the qualifier tree?
        List<QualifiedRoleDTO> qualifiedRoles = new ArrayList<QualifiedRoleDTO>();
        
        List<Authorization> authorizations = authDAO.findAuthorizationByPrincipalId(principalId);
        
        
        for (Authorization authz : authorizations){
            QualifiedRoleDTO qualifiedRoleDTO = new QualifiedRoleDTO();
            qualifiedRoleDTO.setName(authz.getRole().getName());          
            qualifiedRoleDTO.setDescendTree(authz.isDescendTree());
            
            Qualifier roleqlf = authz.getQualifier();
                //roleQualifiers.add(getQualifierTree(roleqlf)); //This looks at every qualifier under that node which is time consuming for high up nodes
	    	QualifierDTO qualifierDTO = new QualifierDTO();
	    	qualifierDTO.setId(roleqlf.getId());
	    	qualifierDTO.setName(roleqlf.getName());
	    	
	    	QualifierType qt = roleqlf.getQualifierType();
			QualifierTypeDTO qtDTO = new QualifierTypeDTO();
			qtDTO.setId(qt.getId());
			qtDTO.setName(qt.getName());
			
			for(QualifierType qtpk:qt.getPkQualifierTypes()){
				QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
				qtpkDTO.setId(qtpk.getId());
				qtpkDTO.setName(qtpk.getName());
				qtDTO.getPkQualifierTypes().add(qtpkDTO);
			}

	    	qualifierDTO.setQualifierType(qtDTO);
            
            qualifiedRoleDTO.setQualifier(qualifierDTO);
            
            qualifiedRoles.add(qualifiedRoleDTO);
        }
        
        return qualifiedRoles;
    }

    private QualifierDTO getQualifierTree(Qualifier rootQlf){        
        QualifierDTO qualifierDTO = new QualifierDTO();
        qualifierDTO.setName(rootQlf.getName());

        List<Qualifier> qualifiers = rootQlf.getQualifiers();
        List<QualifierDTO> qualifierDTOList = new ArrayList<QualifierDTO>();
        for (Qualifier qlf:qualifiers){
            qualifierDTOList.add(getQualifierTree(qlf));
        }
        
        return qualifierDTO;
    }
    
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#principalHasQualifiedRole(java.lang.String,
     *      java.lang.String, org.kuali.student.authz.dto.Map<String,String>)
     */
    @Override
    public boolean principalHasQualifiedRole(String principalId, String roleId, Map<String,String> qualifications) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#principalHasRole(java.lang.String, java.lang.String)
     */
    @Override
    public boolean principalHasRole(String principalId, String roleId) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return false;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#savePermission(org.kuali.student.authz.dto.PermissionDTO)
     */
    @Override
    public void savePermission(PermissionDTO permissionDTO) {
    // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
    	
    	Permission permission=new Permission();
    	permission.setName(permissionDTO.getName());
    	
    	authDAO.createPermission(permission);
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.authz.service.AuthorizationService#saveRole(org.kuali.student.authz.dto.QualifiedRoleDTO)
     */
    @Override
    public void saveRole(RoleDTO roleDTO) {
    	Role role = new Role();
    	role.setName(roleDTO.getName());
    	//Add Permissions
    	for(PermissionDTO permissionDTO:roleDTO.getPermissions()){
    		Permission permission = authDAO.fetchPermission(permissionDTO.getId());
    		permission.getRoles().add(role);
        	role.getPermissions().add(permission);   		
    	}
    	//Add QH
    	if(roleDTO.getQualifierHierarchy()!=null){
    		QualifierHierarchy qh = authDAO.fetchQualifierHierarchy(roleDTO.getQualifierHierarchy().getId());
    		role.setQualifierHierarchy(qh);
    	}
    	
    	//Add QT
    	QualifierType qt = authDAO.fetchQualifierType(roleDTO.getQualifierType().getId());
    	role.setQualifierType(qt);
    	
    	//Save
    	authDAO.createRole(role);
    }

    @Override
    public List<RoleDTO> getRolesForPrincipal(String principalId) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

	/**
	 * @return the authDAO
	 */
	public AuthorizationDAO getAuthDAO() {
		return authDAO;
	}

	/**
	 * @param authDAO the authDAO to set
	 */
	public void setAuthDAO(AuthorizationDAO authDAO) {
		this.authDAO = authDAO;
	}

	@Override
	public List<QualifierDTO> getQualifierRoots(String qhId) {
		List<QualifierDTO> results = new ArrayList<QualifierDTO>();
		for(Qualifier qualifier : authDAO.findRootQualifiers(qhId)){
			if(qualifier!=null){
				QualifierDTO q = new QualifierDTO();
				q.setName(qualifier.getName()==null?"null":qualifier.getName());
				q.setId(qualifier.getId());

				q.setLeftVisit(qualifier.getLeftVisit());
				q.setRightVisit(qualifier.getRightVisit());

				QualifierTypeDTO qtDTO = new QualifierTypeDTO();
				QualifierType qt = qualifier.getQualifierType();
				qtDTO.setId(qt.getId());
				qtDTO.setName(qt.getName());
				
				for(QualifierType qtpk:qt.getPkQualifierTypes()){
					QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
					qtpkDTO.setId(qtpk.getId());
					qtpkDTO.setName(qtpk.getName());
					qtDTO.getPkQualifierTypes().add(qtpkDTO);
				}
				q.setQualifierType(qtDTO);

				for(Qualifier pkq:qualifier.getPkQualifiers()){
					q.getPkQualifiers().put(pkq.getQualifierType().getName(), pkq.getName());
				}
				
				QualifierHierarchyDTO qh = new QualifierHierarchyDTO();
				qh.setId(qualifier.getQualifierHierarchy().getId());
				qh.setName(qualifier.getQualifierHierarchy().getName());
				q.setQualifierHierarchy(qh);
				
				results.add(q);
			}
		}
		return results;
	}

	@Override
	public List<QualifierDTO> getQualifiersDirectDescendents(String qid, String qhid) {
		List<QualifierDTO> results = new ArrayList<QualifierDTO>();
		for(Qualifier qualifier : authDAO.findQualifierDirectDescendents(qid, qhid)){
			if(qualifier!=null){
				QualifierDTO q = new QualifierDTO();
				q.setName(qualifier.getName()==null?"null":qualifier.getName());
				q.setId(qualifier.getId());

				q.setLeftVisit(qualifier.getLeftVisit());
				q.setRightVisit(qualifier.getRightVisit());
				
				QualifierTypeDTO qtDTO = new QualifierTypeDTO();
				QualifierType qt = qualifier.getQualifierType();
				qtDTO.setId(qt.getId());
				qtDTO.setName(qt.getName());
				
				for(QualifierType qtpk:qt.getPkQualifierTypes()){
					QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
					qtpkDTO.setId(qtpk.getId());
					qtpkDTO.setName(qtpk.getName());
					qtDTO.getPkQualifierTypes().add(qtpkDTO);
				}
				q.setQualifierType(qtDTO);
				
				for(Qualifier pkq:qualifier.getPkQualifiers()){
					q.getPkQualifiers().put(pkq.getQualifierType().getName(), pkq.getName());
				}
				
				QualifierHierarchyDTO qh = new QualifierHierarchyDTO();
				qh.setId(qualifier.getQualifierHierarchy().getId());
				qh.setName(qualifier.getQualifierHierarchy().getName());
				q.setQualifierHierarchy(qh);
				
				results.add(q);
			}
		}
		return results;
	}

	@Override
	public PrincipalDTO getPrincipal(String pid) {
		
		Principal principal = authDAO.fetchPrincipal(pid);
		
		PrincipalDTO principalDTO = new PrincipalDTO();
		principalDTO.setId(principal.getId());
		principalDTO.setName(principal.getName());
		
		return principalDTO;
	}

	@Override
	public List<PrincipalDTO> getPrincipals() {

		List<Principal> principals = authDAO.findPrinciplals();
		
		List<PrincipalDTO> principalDTOs = new ArrayList<PrincipalDTO>();
		for(Principal principal:principals){
			
			PrincipalDTO principalDTO = new PrincipalDTO();
			principalDTO.setId(principal.getId());
			principalDTO.setName(principal.getName());
			
			principalDTOs.add(principalDTO);
		}
		
		return principalDTOs;
	}

	@Override
	public List<RoleDTO> getRoles() {

		List<Role> roles = authDAO.findRoles();
		
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		for(Role role:roles){
			
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			
			for(Permission permission:role.getPermissions()){
				PermissionDTO permissionDTO = new PermissionDTO();
				permissionDTO.setId(permission.getId());
				permissionDTO.setName(permission.getName());
				roleDTO.getPermissions().add(permissionDTO);
			}
			
			QualifierHierarchyDTO qhDTO = new QualifierHierarchyDTO();
			QualifierHierarchy qh = role.getQualifierHierarchy();
			if(qh!=null){
				qhDTO.setId(qh.getId());
				qhDTO.setName(qh.getName());
				roleDTO.setQualifierHierarchy(qhDTO);
			}
			
			QualifierTypeDTO qtDTO = new QualifierTypeDTO();
			QualifierType qt = role.getQualifierType();
			qtDTO.setId(qt.getId());
			qtDTO.setName(qt.getName());
			
			for(QualifierType qtpk:qt.getPkQualifierTypes()){
				QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
				qtpkDTO.setId(qtpk.getId());
				qtpkDTO.setName(qtpk.getName());
				qtDTO.getPkQualifierTypes().add(qtpkDTO);
			}
			roleDTO.setQualifierType(qtDTO);
			
			roleDTOs.add(roleDTO);
		}
		
		return roleDTOs;
	}

	@Override
	public List<PermissionDTO> getPermissions() {

		List<Permission> permissions = authDAO.findPermissions();
		
		List<PermissionDTO> permissionDTOs = new ArrayList<PermissionDTO>();
		for(Permission permission:permissions){
			
			PermissionDTO permissionDTO = new PermissionDTO();
			permissionDTO.setId(permission.getId());
			permissionDTO.setName(permission.getName());
			
			permissionDTOs.add(permissionDTO);
		}
		
		return permissionDTOs;

	}

	@Override
	public QualifierTypeDTO getQualifierType(String id) {
		// TODO Auto-generated method stub
		QualifierType qt = authDAO.fetchQualifierType(id);
		QualifierTypeDTO qtDTO = new QualifierTypeDTO();
		qtDTO.setId(qt.getId());
		qtDTO.setName(qt.getName());
		
		for(QualifierType qtpk:qt.getPkQualifierTypes()){
			QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
			qtpkDTO.setId(qtpk.getId());
			qtpkDTO.setName(qtpk.getName());
			qtDTO.getPkQualifierTypes().add(qtpkDTO);
		}
		
		return qtDTO;
	}

	@Override
	public List<QualifierTypeDTO> getQualifierTypes() {
		// TODO Auto-generated method stub
		List<QualifierType> qts = authDAO.findQualifierTypes();
		List<QualifierTypeDTO> qtDTOs = new ArrayList<QualifierTypeDTO>();
		for(QualifierType qt:qts){
			//Only get qualifier types that do not have composite key parents
			if(qt.getCompositeQualifierType()==null){
				QualifierTypeDTO qtDTO = new QualifierTypeDTO();
				qtDTO.setId(qt.getId());
				qtDTO.setName(qt.getName());
				
				for(QualifierType qtpk:qt.getPkQualifierTypes()){
					QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
					qtpkDTO.setId(qtpk.getId());
					qtpkDTO.setName(qtpk.getName());
					qtDTO.getPkQualifierTypes().add(qtpkDTO);
				}
				qtDTOs.add(qtDTO);
			}
		}
		return qtDTOs;
	}

	@Override
	public void saveQualifierType(QualifierTypeDTO qualifierTypeDTO) {

		QualifierType qt = new QualifierType();
		
		qt.setName(qualifierTypeDTO.getName());
		
		for(QualifierTypeDTO qtpkDTO:qualifierTypeDTO.getPkQualifierTypes()){
			QualifierType qtpk;
			if(qtpkDTO.getId()!=null){
				qtpk = authDAO.fetchQualifierType(qtpkDTO.getId());
			}else{
				qtpk = new QualifierType();
				qtpk.setName(qtpkDTO.getName());
			}
			qtpk.setCompositeQualifierType(qt);
			qt.getPkQualifierTypes().add(qtpk);
		}
		
		authDAO.createQualifierType(qt);
	}

	@Override
	public void savePrincipal(PrincipalDTO principalDTO) {
		// TODO Auto-generated method stub
		Principal principal = new Principal();
		principal.setName(principalDTO.getName());
		authDAO.createPrincipal(principal);
	}

	@Override
	public List<QualifierHierarchyDTO> getQualifierHierarchies() {

		List<QualifierHierarchy> qhs = authDAO.findQualifierHierarchies();
		List<QualifierHierarchyDTO> qhDTOs = new ArrayList<QualifierHierarchyDTO>();
		
		//Copy all the qualifier types but not the qualifiers
		for(QualifierHierarchy qh:qhs){
			QualifierHierarchyDTO qhDTO = new QualifierHierarchyDTO();
			
			qhDTO.setId(qh.getId());
			qhDTO.setName(qh.getName());
			
			for(QualifierType qt:qh.getQualifierTypes()){
				QualifierTypeDTO qtDTO = new QualifierTypeDTO();
				qtDTO.setId(qt.getId());
				qtDTO.setName(qt.getName());
				
				for(QualifierType qtpk:qt.getPkQualifierTypes()){
					QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
					qtpkDTO.setId(qtpk.getId());
					qtpkDTO.setName(qtpk.getName());
					qtDTO.getPkQualifierTypes().add(qtpkDTO);
				}
				qhDTO.getQualifierTypes().add(qtDTO);
			}
			qhDTOs.add(qhDTO);
		}
		
		return qhDTOs;
	}

	@Override
	public String saveQualifierHierarchy(QualifierHierarchyDTO qhDTO) {
		QualifierHierarchy qh = new QualifierHierarchy();
		qh.setName(qhDTO.getName());
		for(QualifierTypeDTO qtDTO:qhDTO.getQualifierTypes()){
			QualifierType qt = authDAO.fetchQualifierType(qtDTO.getId());
			qt.getQualifierHierarchys().add(qh);
			qh.getQualifierTypes().add(qt);
		}
		return authDAO.createQualifierHierarchy(qh);
	}

	@Override
	public QualifierHierarchyDTO getQualifierHierarchy(String qhId) {
		
		QualifierHierarchy qh = authDAO.fetchQualifierHierarchy(qhId);
		QualifierHierarchyDTO qhDTO = new QualifierHierarchyDTO();
		
		if(qh==null){
			return null;
		}
		qhDTO.setId(qh.getId());
		qhDTO.setName(qh.getName());
		
		for(QualifierType qt:qh.getQualifierTypes()){
			QualifierTypeDTO qtDTO = new QualifierTypeDTO();
			qtDTO.setId(qt.getId());
			qtDTO.setName(qt.getName());
			
			for(QualifierType qtpk:qt.getPkQualifierTypes()){
				QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
				qtpkDTO.setId(qtpk.getId());
				qtpkDTO.setName(qtpk.getName());
				qtDTO.getPkQualifierTypes().add(qtpkDTO);
			}
			qhDTO.getQualifierTypes().add(qtDTO);
		}
		
		return qhDTO;
	}

	@Override
	public QualifierDTO getQualifier(String id) {
		Qualifier q = authDAO.fetchQualifier(id);
		
		QualifierDTO qDTO = new QualifierDTO();
		qDTO.setName(q.getName()==null?"null":q.getName());
		qDTO.setId(q.getId());

		q.setLeftVisit(q.getLeftVisit());
		q.setRightVisit(q.getRightVisit());
		
		QualifierTypeDTO qtDTO = new QualifierTypeDTO();
		QualifierType qt = q.getQualifierType();
		qtDTO.setId(qt.getId());
		qtDTO.setName(qt.getName());
		
		for(QualifierType qtpk:qt.getPkQualifierTypes()){
			QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
			qtpkDTO.setId(qtpk.getId());
			qtpkDTO.setName(qtpk.getName());
			qtDTO.getPkQualifierTypes().add(qtpkDTO);
		}
		qDTO.setQualifierType(qtDTO);
		
		QualifierHierarchyDTO qhDTO = new QualifierHierarchyDTO();
		QualifierHierarchy qh = q.getQualifierHierarchy();
		
		qhDTO.setId(qh.getId());
		qhDTO.setName(qh.getName());
		
		for(QualifierType qhQt:qh.getQualifierTypes()){
			QualifierTypeDTO qhQtDTO = new QualifierTypeDTO();
			qhQtDTO.setId(qhQt.getId());
			qhQtDTO.setName(qhQt.getName());
			
			for(QualifierType qtpk:qt.getPkQualifierTypes()){
				QualifierTypeDTO qtpkDTO = new QualifierTypeDTO();
				qtpkDTO.setId(qtpk.getId());
				qtpkDTO.setName(qtpk.getName());
				qhQtDTO.getPkQualifierTypes().add(qtpkDTO);
			}
			qhDTO.getQualifierTypes().add(qhQtDTO);
		}
		
		qDTO.setQualifierHierarchy(qhDTO);
		
		return qDTO;
	}

	@Override
	public String saveQualifier(QualifierDTO qDTO) {
		Qualifier q = doInsertOfQualifier(qDTO);
		updateNestingForQualifier(q);
		return q.getId();
	}
	
	/**
	 * Updates the nesting data for the given qualifier.  It assumes that the qualifier has already been persisted
	 * with a q.leftVisit = q.parent.rightVisit and q.rightVisit = q.leftVisit+1.
	 * It will add 2 to the nesting data qualifiers to the right of the qualifier.
	 *
	 * @param q
	 */
	private void updateNestingForQualifier(Qualifier q) {
		authDAO.updateNestingForQualifier(q);
	}

	/**
	 * Creates a qualifier and persists it.  The created qualifier has 
	 * q.leftVisit = q.parent.rightVisit and q.rightVisit = q.leftVisit+1.
	 * @param qDTO 
	 * @return qualifier that was inserted
	 */
	private Qualifier doInsertOfQualifier(QualifierDTO qDTO){
		Qualifier q = new Qualifier();
		
		//Set the name
		q.setName(qDTO.getName());
		
		//Add the parent if needed
		if(qDTO.getParentId()!=null){
			Qualifier parent = authDAO.fetchQualifier(qDTO.getParentId());
			if(parent!=null){
				q.setParent(parent);
				parent.getQualifiers().add(q);
			}
		}
		
		//Add the QH
		QualifierHierarchy qh = authDAO.fetchQualifierHierarchy(qDTO.getQualifierHierarchy().getId());
		q.setQualifierHierarchy(qh);
		
		//Add the QT
		QualifierType qt = authDAO.fetchQualifierType(qDTO.getQualifierType().getId());
		q.setQualifierType(qt);
		
		//Set any composite key qualifiers here
		//The map should be qualifierId->name
		for(Map.Entry<String,String> e:qDTO.getPkQualifiers().entrySet()){
			Qualifier pkq = new Qualifier();
			
			//Set the name
			pkq.setName(e.getValue());
			
			//Set the QT
			QualifierType pkqt = authDAO.fetchQualifierType(e.getKey());
			pkq.setQualifierType(pkqt);
			pkqt.getQualifiers().add(pkq);
			
			//Set the QH
			pkq.setQualifierHierarchy(qh);
			qh.getQualifiers().add(pkq);
			
			//Set the PK relationship
			pkq.setCompositeQualifier(q);
			q.getPkQualifiers().add(pkq);
		}
		
		authDAO.createQualifier(q);
		return q;
	}
	
	@Override
	public List<PrincipalDTO> getPrincipalsWithQualifierAndPermissions(
			String permissionId, String qualifierId) {
		
		List<PrincipalDTO> results = new ArrayList<PrincipalDTO>();
		List<Principal> principals = authDAO.findPrincipalsAtOrAboveQualifierWithPermissions(qualifierId, permissionId);
		for(Principal p:principals){
			PrincipalDTO principalDTO = new PrincipalDTO();
			principalDTO.setId(p.getId());
			principalDTO.setName(p.getName());
			results.add(principalDTO);
		}
		
		return results;
	}



}
