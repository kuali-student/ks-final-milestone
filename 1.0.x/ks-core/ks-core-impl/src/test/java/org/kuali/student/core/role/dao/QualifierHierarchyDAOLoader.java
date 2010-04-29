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

package org.kuali.student.core.role.dao;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.test.spring.DaoLoader;
import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;

/**
 * This populates some data using the QualifierHierarchyDAO 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class QualifierHierarchyDAOLoader extends DaoLoader {

    QualifierHierarchyDAO qualifierHierarchyDAO;
    
    /**
     * @see org.kuali.student.common.test.spring.DaoLoader#run()
     */
    @Override
    public void run() {
        qualifierHierarchyDAO = (QualifierHierarchyDAO)this.getDao();
        populateCompositeQulifierData();
        populateSomeQualifierTreeData();
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
    
}
