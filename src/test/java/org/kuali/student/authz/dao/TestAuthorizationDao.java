package org.kuali.student.authz.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import org.kuali.student.authz.entity.Authorization;
import org.kuali.student.authz.entity.Permission;
import org.kuali.student.authz.entity.Principal;
import org.kuali.student.authz.entity.Qualifier;
import org.kuali.student.authz.entity.QualifierHierarchy;
import org.kuali.student.authz.entity.QualifierType;
import org.kuali.student.authz.entity.Role;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.springframework.test.annotation.Rollback;

@PersistenceFileLocation("classpath:META-INF/authz-persistence.xml")
public class TestAuthorizationDao extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.authz.dao.AuthorizationDAOImpl")
    public AuthorizationDAO authorizationDAO;
    
    public static Principal principal;
    public static Role role;
    public static Permission permission;
    public static Authorization authorization;
    
    public static Principal principal2;
    public static Role role2;
    public static Permission permission2;
    public static Authorization authorization2;
    
    // if you want to commit the data, set rollback to false, and uncomment @Rollback(false) below.
    public boolean rollback = true;
    //public boolean rollback = false;
    
    @Before
    public void onSetUp() throws Exception {
        System.out.println("\n\n\n calling before method"); 
        
        if(rollback){
            populateSomeQualifierTreeData();
            createQualifiedAuthorization();
        
            populateCompositeQulifierData();
            createCompositeQualifiedAuthorization();
        }
    }
    
    //@Rollback(false)
    @Test
    public void aSetupTestToCommitDataToDB(){
        System.out.println("\n\n\n calling commit data method");
        
        if(!rollback){
            populateSomeQualifierTreeData();
            createQualifiedAuthorization();
        
            populateCompositeQulifierData();
            createCompositeQualifiedAuthorization();
        }
    }
    
    private void populateSomeQualifierTreeData(){
        QualifierHierarchy trmQH = new QualifierHierarchy();
        trmQH.setName("QH-TERM");
        authorizationDAO.createQualifierHierarchy(trmQH);
        
        Qualifier trmRoot = new Qualifier();
        trmRoot.setName("TERM-ROOT");
        trmRoot.setQualifierHierarchy(trmQH);
        
        QualifierType termTypeQT = new QualifierType();
        termTypeQT.setName("Term Type");
        termTypeQT.setComposite(false);
        authorizationDAO.createQualifierType(termTypeQT);
        
        QualifierType termQT = new QualifierType();
        termQT.setName("Term");
        termQT.setComposite(false);
        authorizationDAO.createQualifierType(termQT);

        //Term Qualifier Tree
        for (int i=1; i <=4; i++){
            String termType = "";
            switch(i){
                case 1: termType = "Spring"; break;
                case 2: termType = "Summer"; break;
                case 3: termType = "Fall"; break;
                case 4: termType = "Winter"; break;
            }
            Qualifier termTypeQlf = new Qualifier();
            termTypeQlf.setName(termType);
            trmRoot.getQualifiers().add(termTypeQlf);
            for (int year=2005; year <= 2008; year++){
                String termValue = "";
                switch (i){
                    case 1: termValue = year + "01"; break;
                    case 2: termValue = year + "07"; break;
                    case 3: termValue = year + "08"; break;
                    case 4: termValue = year + "12"; break;
                }
                
                Qualifier term = new Qualifier();
                term.setName(termValue);
                term.setParent(termTypeQlf);
                term.setQualifierType(termQT);
                termTypeQlf.getQualifiers().add(term);
                termTypeQlf.setQualifierType(termTypeQT);
            }
        }
        authorizationDAO.createQualifierTree(trmRoot);
    }
    
    private void createQualifiedAuthorization(){
        principal = new Principal();
        principal.setName("Professor X");
        authorizationDAO.createPrincipal(principal);
        
        role = new Role();
        role.setName("Grader");
        
        permission = new Permission();
        permission.setName("Modify Grade");
        authorizationDAO.createPermission(permission);

        role.getPermissions().add(permission);
        permission.getRoles().add(role);
        authorizationDAO.createRole(role);
        
        Qualifier termSummer = authorizationDAO.findQualifierByType("Term Type", "Summer");
        
        authorization = new Authorization();
        authorization.setPrincipal(principal);
        authorization.setQualifier(termSummer); 
        authorization.setRole(role);
    
        authorizationDAO.createAuthorization(authorization);
    }
    
    private void populateCompositeQulifierData(){
        // A Composite Qualifier Type
        QualifierType compositeQT = new QualifierType();
        compositeQT.setName("My-term-composite-qualifier-type");
        compositeQT.setComposite(true);
        
        // The composite key type
        QualifierType pk1QT = new QualifierType();
        pk1QT.setName("term-001-for-my-cqt");
        pk1QT.setComposite(true);
        pk1QT.setCompositeQualifierType(compositeQT);
        
        QualifierType pk2QT = new QualifierType();
        pk2QT.setName("course-001-for-my-cqt");
        pk2QT.setComposite(true);
        pk2QT.setCompositeQualifierType(compositeQT);
        
        QualifierType pk3QT = new QualifierType();
        pk3QT.setName("section-001-for-my-cqt");
        pk3QT.setComposite(true);
        pk3QT.setCompositeQualifierType(compositeQT);
        
        List<QualifierType> pkQualifierTypes = new ArrayList<QualifierType>();
        pkQualifierTypes.add(pk1QT);
        pkQualifierTypes.add(pk2QT);
        pkQualifierTypes.add(pk3QT);
        
        compositeQT.setPkQualifierTypes(pkQualifierTypes);
        
        authorizationDAO.createQualifierType(compositeQT);
        
        // The composite qualifiers for the above CQ Type
        for (int i=0; i<2; i++){
            Qualifier compositeQ = new Qualifier();
            compositeQ.setName("My-term-composite-qualifier"+i);
            compositeQ.setQualifierType(compositeQT);
            
            // The composite qualifier keys
            Qualifier pk1Q = new Qualifier();
            pk1Q.setName("summer-for-cq"+i);
            pk1Q.setQualifierType(pk1QT);
            pk1Q.setCompositeQualifier(compositeQ);
            
            Qualifier pk2Q = new Qualifier();
            pk2Q.setName("phys800-for-cq"+i);
            pk2Q.setQualifierType(pk2QT);
            pk2Q.setCompositeQualifier(compositeQ);
            
            Qualifier pk3Q = new Qualifier();
            pk3Q.setName("sec009-for-cq"+i);
            pk3Q.setQualifierType(pk3QT);
            pk3Q.setCompositeQualifier(compositeQ);
            
            List<Qualifier> pkQualifiers = new ArrayList<Qualifier>();
            pkQualifiers.add(pk1Q);
            pkQualifiers.add(pk2Q);
            pkQualifiers.add(pk3Q);
            
            compositeQ.setPkQualifiers(pkQualifiers);
            
            authorizationDAO.createQualifier(compositeQ);
        }
    }
    
    private void createCompositeQualifiedAuthorization(){
        principal2 = new Principal();
        principal2.setName("Dean");
        authorizationDAO.createPrincipal(principal2);
        
        role2 = new Role();
        role2.setName("Executive");
        
        permission2 = new Permission();
        permission2.setName("Fire");
        authorizationDAO.createPermission(permission2);

        role2.getPermissions().add(permission2);
        permission2.getRoles().add(role2);
        authorizationDAO.createRole(role2);
        
        for (int i=0; i<2; i++){
            HashMap<String, String> qualifiersMap = new HashMap<String, String>();
            qualifiersMap.put("term-001-for-my-cqt", "summer-for-cq"+i);
            qualifiersMap.put("course-001-for-my-cqt", "phys800-for-cq"+i);
            qualifiersMap.put("section-001-for-my-cqt", "sec009-for-cq"+i);
                
            Qualifier cqt4Dean = authorizationDAO.findCompositeQualifierByType(qualifiersMap);
            
            authorization2 = new Authorization();
            authorization2.setPrincipal(principal2);
            authorization2.setQualifier(cqt4Dean); 
            authorization2.setRole(role2);
            authorization2.setDescendTree(false);
            authorizationDAO.createAuthorization(authorization2);
        }
    }
    
    @Test
    public void testLoadedData() {
        System.out.println("\n\n\n calling test loaded data");
        
        Qualifier term200807 = authorizationDAO.findQualifierByType("Term", "200807");
        assertTrue(authorizationDAO.hasPermission(term200807, principal.getId(), permission.getId()));
        
        Qualifier qualifier1 = authorizationDAO.findQualifierByType("Term", "200808");
        assertFalse(authorizationDAO.hasPermission(qualifier1, principal.getId(), permission.getId()));
    }
    
    @Test
    public void testCompositeQualifier(){
            
        HashMap<String, String> qualifiersMap = new HashMap<String, String>();
        qualifiersMap.put("term-001-for-my-cqt", "summer-for-cq0");
        qualifiersMap.put("course-001-for-my-cqt", "phys800-for-cq0");
        qualifiersMap.put("section-001-for-my-cqt", "sec009-for-cq0");
            
        Qualifier retQ = authorizationDAO.findCompositeQualifierByType(qualifiersMap);
        assertTrue(retQ.getName().equals("My-term-composite-qualifier0"));  
    }
    
    @Test
    public void testIsAuthorized(){
        HashMap<String, String> qualifications = new HashMap<String, String>();
        
        // The professor only has access to all summer terms, not winter terms
        qualifications.put("Term", "200512");
        assertFalse(authorizationDAO.isAuthorized(principal, permission, qualifications));
        
        // The professor is authorized for summer terms
        qualifications.clear();
        qualifications.put("Term", "200507");
        assertTrue(authorizationDAO.isAuthorized(principal, permission, qualifications));
        
        //Check for non-inherited qualifiers by not descending tree. The professor will only be authorized for the summer term type
        // not all summer terms.
        authorization.setDescendTree(false);
        authorizationDAO.updateAuthorization(authorization);
        assertFalse(authorizationDAO.isAuthorized(principal, permission, qualifications));
        
        // The dean has access to this CQ
        qualifications.clear();
        qualifications.put("term-001-for-my-cqt", "summer-for-cq1");
        qualifications.put("course-001-for-my-cqt", "phys800-for-cq1");
        qualifications.put("section-001-for-my-cqt", "sec009-for-cq1");
        assertTrue(authorizationDAO.isAuthorized(principal2, permission2, qualifications));
    }

    @Test
    public void findPricipalsWithPersmissionsOrQualifiers(){
        
        Qualifier qualifier = authorizationDAO.findQualifierByType("Term", "200807");
        
        // The professor does not have permission at this qualifier.
        List<Principal> principals = authorizationDAO.findPrincipalsAtQualifier(qualifier, role);
        assertTrue(principals.size() == 0);
        
        // The professor does however have permission at the qualifier above, Term Type - Summer.
        principals = authorizationDAO.findPrincipalsAtOrAboveQualifier(qualifier, role);
        for(Principal p : principals){
            assertTrue(p.getName().equals("Professor X"));
        }
        
        principals = authorizationDAO.findPrincipalsAtOrAboveQualifierWithPermissions(qualifier.getId(), permission.getId());
        for(Principal p : principals){
            assertTrue(p.getName().equals("Professor X"));
        }
    }
    
    @Test
    public void findPricipalsWithPersmissionsOrCompositeQualifiers(){
        HashMap<String, String> qualifiersMap = new HashMap<String, String>();
        qualifiersMap.put("term-001-for-my-cqt", "summer-for-cq0");
        qualifiersMap.put("course-001-for-my-cqt", "phys800-for-cq0");
        qualifiersMap.put("section-001-for-my-cqt", "sec009-for-cq0");
            
        Qualifier qualifier = authorizationDAO.findCompositeQualifierByType(qualifiersMap);
        
        // The Dean does have permission at this qualifier.
        List<Principal> principals = authorizationDAO.findPrincipalsAtQualifier(qualifier, role2);
        for(Principal p : principals){
            assertTrue(p.getName().equals("Dean"));
        }
        
        // The CQ is not in a tree so it makes no sense to use this call.
        // It works correctly since it returns a size of zero
        principals = authorizationDAO.findPrincipalsAtOrAboveQualifierWithPermissions(qualifier.getId(), permission2.getId());
        assertTrue(principals.size() == 0);
    }
    
/*    @Test
    public void findPermissionsByPrincipalIdTest(){
        // Professor has one auth
        List<Permission> permissions = authorizationDAO.findPermissionsByPrincipalId(principal.getId());
        
        for(Permission perm : permissions){
            System.out.println("\n\n\n The permsissions : " + perm.getName() );
        }
    }*/

}
