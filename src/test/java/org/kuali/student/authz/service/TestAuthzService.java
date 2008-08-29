package org.kuali.student.authz.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.authz.dto.PermissionDTO;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@Daos( { @Dao(value = "org.kuali.student.authz.dao.AuthorizationDAOImpl") })
@PersistenceFileLocation("classpath:META-INF/authz-persistence.xml")
public class TestAuthzService extends AbstractServiceTest {
	@Client(value = "org.kuali.student.authz.service.AuthorizationServiceImpl", port = "8181")
	public AuthorizationService authzService;

	@Test
	public void testQuick() {
	    PermissionDTO permission = new PermissionDTO();
	    permission.setName("service_test_1");
	    authzService.savePermission(permission);
	    
	    List<PermissionDTO> permissionList = authzService.getPermissions();
	    boolean test = false;
	    for(PermissionDTO p : permissionList) {
	        if(p.getName().equals("service_test_1")) {
	            test = true;
	            break;
	        }
	    }
	    
	    assertTrue(test);
	}
	
	@Test
	public void testIsAuth() {
	    Map<String,String> qualifications = new HashMap<String,String>();
	    qualifications.put("Course", "MUSC455");
	    assertTrue(authzService.isPrincipalAuthorizedForQualifiedPermission("princ-0001", "", "perm-002", qualifications));
	}
	
//	@Test
	public void testService() {
//		QualifierTypeDTO qt = authzService.getQualifierTypes().get(0);
//		
//		QualifierHierarchyDTO qh = new QualifierHierarchyDTO();
//		qh.getQualifierTypes().add(qt);
//		String qhId = authzService.saveQualifierHierarchy(qh);
//		qh.setId(qhId);
//		
//		QualifierDTO q1 = new QualifierDTO();
//		q1.setName("Root");
//		q1.setQualifierHierarchy(qh);
//		
//		authzService.saveQualifier(q1);
//		
//		
//		QualifierTypeDTO qt1 = new QualifierTypeDTO();
//		qt1.setName("ChildQT");
//		
//		QualifierTypeDTO qt2 = new QualifierTypeDTO();
//		qt2.setName("ParentQT");
//		
//		qt2.getPkQualifierTypes().add(qt1);
//		authzService.saveQualifierType(qt2);
//		
//		List<QualifierTypeDTO> qts = authzService.getQualifierTypes();
//		qts.size();
//		List<QualifierDTO> roots = authzService.getQualifierRoots();
//		QualifierDTO root0=null;
//    	for(QualifierDTO q:roots){
//        	if(q.getName()!=null&&q.getQualifierType()!=null&&q.getQualifierType()!=null){
//        		root0=q;
//        	}
//    	}
//    	List<QualifierDTO> descendents = authzService.getQualifiersDirectDescendents(root0.getId());
//    	
//		assertTrue(descendents.size()>0);
	}

}
