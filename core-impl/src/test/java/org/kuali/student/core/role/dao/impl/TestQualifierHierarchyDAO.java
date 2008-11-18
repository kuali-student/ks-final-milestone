package org.kuali.student.core.role.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;
import org.kuali.student.core.role.dao.QualifierHierarchyDAO;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.springframework.test.annotation.Rollback;

@PersistenceFileLocation("classpath:META-INF/role-persistence.xml")
public class TestQualifierHierarchyDAO extends AbstractTransactionalDaoTest {
    
    @Dao(value = "org.kuali.student.core.role.dao.impl.QualifierHierarchyDAOImpl")
    public QualifierHierarchyDAO qualifierHierarchyDAO;
    
    
    // if you want to commit the data, set rollback to false, and uncomment @Rollback(false) below.
    public boolean rollback = true;
    //public boolean rollback = false;
    
    @Before
    public void onSetUp() {
        System.out.println("\n\n\n calling before method"); 
        
        if(rollback){
            populateSomeQualifierTreeData();
            populateCompositeQulifierData();
        }
    }
    
    //@Rollback(false)
    @Test
    public void aSetupTestToCommitDataToDB(){
        System.out.println("\n\n\n calling commit data method");
        
        if(!rollback){
            populateSomeQualifierTreeData();
            populateCompositeQulifierData();
        }
    }
    
    public void populateSomeQualifierTreeData(){
        QualifierHierarchy trmQH = new QualifierHierarchy();
        trmQH.setId("qh.term");
        trmQH.setName("QH-TERM");
        qualifierHierarchyDAO.createQualifierHierarchy(trmQH);
        
        QualifierType rootQT = new QualifierType();
        rootQT.setId("qt.term.root-type");
        rootQT.setName("TERM ROOT TYPE");
        rootQT.setComposite(false);
        qualifierHierarchyDAO.createQualifierType(rootQT);
        
        Qualifier trmRoot = new Qualifier();
        trmRoot.setId("qf.term.root");
        trmRoot.setName("TERM-ROOT");
        trmRoot.setQualifierHierarchy(trmQH);
        trmRoot.setQualifierType(rootQT);
        
        QualifierType termTypeQT = new QualifierType();
        termTypeQT.setId("qt.term.term-type");
        termTypeQT.setName("Term Type");
        termTypeQT.setComposite(false);
        qualifierHierarchyDAO.createQualifierType(termTypeQT);
        
        QualifierType termQT = new QualifierType();
        termQT.setId("qf.term.term");
        termQT.setName("Term");
        termQT.setComposite(false);
        qualifierHierarchyDAO.createQualifierType(termQT);

        trmQH.getQualifierTypes().add(termTypeQT);
        trmQH.getQualifierTypes().add(termQT);
        
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
            termTypeQlf.setId("qf.term." + termType.toLowerCase() + "-type");
            termTypeQlf.setName(termType);
            termTypeQlf.setParent(trmRoot);
            termTypeQlf.setQualifierType(termTypeQT);
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
                term.setId("qf.term." + termValue);
                term.setName(termValue);
                term.setParent(termTypeQlf);
                term.setQualifierType(termQT);
                termTypeQlf.getQualifiers().add(term);
            }
        }
        qualifierHierarchyDAO.createQualifierTree(trmRoot);
    }
    
    public void populateCompositeQulifierData(){
        // The hierarchy
        QualifierHierarchy compositeQH = new QualifierHierarchy();
        compositeQH.setId("qh.composite");
        compositeQH.setName("QH-Composite");
        qualifierHierarchyDAO.createQualifierHierarchy(compositeQH);
        
        // A Composite Qualifier Type
        QualifierType compositeQT = new QualifierType();
        compositeQT.setId("qt.my-term-composite");
        compositeQT.setName("My-term-composite-qualifier-type");
        compositeQT.setComposite(true);
        
        compositeQH.getQualifierTypes().add(compositeQT);
        
        // The composite key type
        QualifierType pk1QT = new QualifierType();
        pk1QT.setId("qt.term-001-for-my-cqt");
        pk1QT.setName("term-001-for-my-cqt");
        pk1QT.setComposite(true);
        pk1QT.setCompositeQualifierType(compositeQT);
        
        QualifierType pk2QT = new QualifierType();
        pk2QT.setId("qt.course-001-for-my-cqt");
        pk2QT.setName("course-001-for-my-cqt");
        pk2QT.setComposite(true);
        pk2QT.setCompositeQualifierType(compositeQT);
        
        QualifierType pk3QT = new QualifierType();
        pk3QT.setId("qt.section-001-for-my-cqt");
        pk3QT.setName("section-001-for-my-cqt");
        pk3QT.setComposite(true);
        pk3QT.setCompositeQualifierType(compositeQT);
        
        List<QualifierType> pkQualifierTypes = new ArrayList<QualifierType>();
        pkQualifierTypes.add(pk1QT);
        pkQualifierTypes.add(pk2QT);
        pkQualifierTypes.add(pk3QT);
        
        compositeQT.setPkQualifierTypes(pkQualifierTypes);
        qualifierHierarchyDAO.createQualifierType(compositeQT);
        
        Qualifier compositeRoot = new Qualifier();
        compositeRoot.setId("qf.composite-root");
        compositeRoot.setName("Composite-ROOT");
        compositeRoot.setQualifierHierarchy(compositeQH);
        compositeRoot.setQualifierType(compositeQT);
        
        // The composite qualifiers for the above CQ Type
        for (int i=0; i<5; i++){
            Qualifier compositeQ = new Qualifier();
            compositeQ.setId("qf.my-term-composite-qualifier"+i);
            compositeQ.setName("My-term-composite-qualifier"+i);
            compositeQ.setParent(compositeRoot);
            compositeQ.setQualifierType(compositeQT);
            
            // The composite qualifier keys
            Qualifier pk1Q = new Qualifier();
            pk1Q.setId("qf.summer-for-cq"+i);
            pk1Q.setName("summer-for-cq"+i);
            pk1Q.setQualifierType(pk1QT);
            pk1Q.setCompositeQualifier(compositeQ);
            
            Qualifier pk2Q = new Qualifier();
            pk2Q.setId("qf.phys800-for-cq"+i);
            pk2Q.setName("phys800-for-cq"+i);
            pk2Q.setQualifierType(pk2QT);
            pk2Q.setCompositeQualifier(compositeQ);
            
            Qualifier pk3Q = new Qualifier();
            pk3Q.setId("qf.sec009-for-cq"+i);
            pk3Q.setName("sec009-for-cq"+i);
            pk3Q.setQualifierType(pk3QT);
            pk3Q.setCompositeQualifier(compositeQ);
            
            List<Qualifier> pkQualifiers = new ArrayList<Qualifier>();
            pkQualifiers.add(pk1Q);
            pkQualifiers.add(pk2Q);
            pkQualifiers.add(pk3Q);
            
            if (i==4){
                pk1Q.setCompositeQualifier(compositeRoot);
                pk2Q.setCompositeQualifier(compositeRoot);
                pk3Q.setCompositeQualifier(compositeRoot);
                compositeRoot.setPkQualifiers(pkQualifiers);
            } else {
                compositeQ.setPkQualifiers(pkQualifiers);
                compositeRoot.getQualifiers().add(compositeQ);
            }
            
            /*compositeQ.setPkQualifiers(pkQualifiers);
            
            //qualifierHierarchyDAO.createQualifier(compositeQ);
            compositeRoot.getQualifiers().add(compositeQ);*/
        }
        qualifierHierarchyDAO.createQualifierTree(compositeRoot);
    }
    

    @Test
    public void testLoadedData() {
        System.out.println("\n\n\n calling test loaded data");
        
        Qualifier term200807 = qualifierHierarchyDAO.findQualifierByType("Term", "200807");
        
        List<Qualifier> lqf = qualifierHierarchyDAO.findQualifiersAtOrAboveQualifier(term200807);
        for(Qualifier q : lqf){
            System.out.println("ret tree qualifier : " + q.getName());
        }
        
        
        Qualifier fall = qualifierHierarchyDAO.findQualifierByType("Term Type", "Fall");
        
        List<Qualifier> lqf2 = qualifierHierarchyDAO.findQualifiersAtOrBelowQualifier(fall);
        for(Qualifier q : lqf2){
          System.out.println("ret tree qualifier : " + q.getName());
        }
    }
    
    @Test
    public void testCompositeQualifier(){
            
        HashMap<String, String> qualifiersMap = new HashMap<String, String>();
        qualifiersMap.put("term-001-for-my-cqt", "summer-for-cq0");
        qualifiersMap.put("course-001-for-my-cqt", "phys800-for-cq0");
        qualifiersMap.put("section-001-for-my-cqt", "sec009-for-cq0");
            
        Qualifier retQ = qualifierHierarchyDAO.findCompositeQualifierByType(qualifiersMap);
        assertTrue(retQ.getName().equals("My-term-composite-qualifier0"));  
    }
    
    @Test
    public void testFetchQualifierHierarchyByName(){
        QualifierHierarchy qh = qualifierHierarchyDAO.fetchQualifierHierarchyByName("QH-TERM");
        assertTrue(qh.getName().equals("QH-TERM"));
    }
}
