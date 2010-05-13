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

package org.kuali.student.core.role.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.role.service.QualifierHierarchyRoleTypeService;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 */
@Daos( { @Dao(value = "org.kuali.student.core.role.dao.impl.QualifierHierarchyDAOImpl") })
@PersistenceFileLocation("classpath:META-INF/role-persistence.xml")
public class TestQualifierHierarchyRoleTypeService extends AbstractServiceTest {
	final Logger LOG = Logger.getLogger(TestQualifierHierarchyRoleTypeService.class);
    @Client(value = "org.kuali.student.core.role.service.impl.QualifierHierarchyRoleTypeServiceImpl")
    public QualifierHierarchyRoleTypeService qhRoleTypeService;
    
    AttributeSet qualification;
    AttributeSet roleQualifier;
    AttributeSet qualificationComposite;
    AttributeSet roleQualifierComposite;
    AttributeSet singleQualification;
    AttributeSet compositeQualification;
    
    @Before
    public void setUp(){        
        
        // for the does / doRoleQualifierMatchQualification
        qualification = new AttributeSet();
        qualification.put("Term", "200707");
        
        roleQualifier = new AttributeSet();
        roleQualifier.put("Term Type", "Summer");
        
        qualificationComposite = new AttributeSet();
        qualificationComposite.put("term-001-for-my-cqt", "summer-for-cq1");
        qualificationComposite.put("course-001-for-my-cqt", "phys800-for-cq1");
        qualificationComposite.put("section-001-for-my-cqt", "sec009-for-cq1");
        
        // this is Composite-ROOT
        roleQualifierComposite = new AttributeSet();
        roleQualifierComposite.put("term-001-for-my-cqt", "summer-for-cq4");
        roleQualifierComposite.put("course-001-for-my-cqt", "phys800-for-cq4");
        roleQualifierComposite.put("section-001-for-my-cqt", "sec009-for-cq4");
        
        
        // for the getAllImplied / Implying methods
        singleQualification = new AttributeSet();
        singleQualification.put("Term Type", "Fall");
        
        compositeQualification = new AttributeSet();
        compositeQualification.put("term-001-for-my-cqt", "summer-for-cq0");
        compositeQualification.put("course-001-for-my-cqt", "phys800-for-cq0");
        compositeQualification.put("section-001-for-my-cqt", "sec009-for-cq0");
    }
    
    @Test
    public void doesRoleQualifierMatchQualification(){
        
        boolean result = qhRoleTypeService.doesRoleQualifierMatchQualification(qualification, roleQualifier);
        assertTrue(result);   
    }
    
    @Test
    public void doesRoleQualifierMatchQualificationComposite(){
        
        boolean result = qhRoleTypeService.doesRoleQualifierMatchQualification(qualificationComposite, roleQualifierComposite);
        assertTrue(result);
    }
    
    @Test
    public void doRoleQualifiersMatchQualification(){  
        List<AttributeSet> roleQualifierList = new ArrayList<AttributeSet>();
        
        AttributeSet ai1 = new AttributeSet();
        ai1.put("Term Type", "Winter");
        
        AttributeSet ai2 = new AttributeSet();
        ai2.put("Term Type", "Fall");
        
        AttributeSet ai3 = new AttributeSet();
        ai3.put("Term Type", "Summer");
        
        roleQualifierList.add(ai1);
        roleQualifierList.add(ai2);
        roleQualifierList.add(ai3);
        
        boolean result = qhRoleTypeService.doRoleQualifiersMatchQualification(qualification, roleQualifierList);
        assertTrue(result);   
    }
    
    @Test
    public void getAllImpliedQualificationsSingle(){
        List<AttributeSet> result = qhRoleTypeService.getAllImpliedQualifications(singleQualification);
        LOG.warn("\n Test getAllImpliedQualificationsSingle() ");
        
        for(AttributeSet attr : result){ 
            for(String key : attr.keySet()){
            	LOG.warn("key : " + key + "   value : " + attr.get(key).toString());
            }
        }
        assertEquals(5, result.size());
    }
    
    @Test
    public void getAllImpliedQualificationsComposite(){
        List<AttributeSet> result = qhRoleTypeService.getAllImpliedQualifications(compositeQualification);
        LOG.warn("\n Test getAllImpliedQualificationsComposite() " + result.size());
        
        for(AttributeSet attr : result){ 
            for(String key : attr.keySet()){
            	LOG.warn("key : " + key + "   value : " + attr.get(key).toString());
            }
        }
        
        //Should this be size of 1?
        assertEquals(1, result.size());
    }
    
    @Ignore
    @Test
    public void getAllImplyingQualificationsSingle(){
        List<AttributeSet> result = qhRoleTypeService.getAllImplyingQualifications(singleQualification);
        LOG.warn("\n Test getAllImplyingQualificationsSingle() ");
        
        for(AttributeSet attr : result){ 
            for(String key : attr.keySet()){
            	LOG.warn("key : " + key + "   value : " + attr.get(key).toString());
            }
        }
        assertFalse(false);
    }
    
    @Ignore
    @Test
    public void getAllImplyingQualificationsComposite(){
        List<AttributeSet> result = qhRoleTypeService.getAllImplyingQualifications(compositeQualification);
        LOG.warn("\n Test getAllImplyingQualificationsComposite() ");
        
        for(AttributeSet attr : result){ 
            for(String key : attr.keySet()){
            	LOG.warn("key : " + key + "   value : " + attr.get(key).toString());
            }
        }
        assertFalse(false);
    }
    
    @Ignore
    @Test
    public void getAcceptedQualificationAttributeNames(){
        //((KimRoleTypeServiceImpl)kimRoleTypeService).setQualifierHierarchy("QH-TERM");
        
    /*
        List<String> attribueNames = kimRoleTypeService.getAcceptedQualificationAttributeNames();
        for(String attr : attribueNames){ 
            System.out.println("attribute : " + attr);
        }
        assertFalse(false);
     */        
    }
}
