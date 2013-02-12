/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.krms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.student.class3.krms.termresolver.AtpEndDateFieldTermResolver;
import org.kuali.student.class3.krms.termresolver.AtpStartDateFieldTermResolver;
import org.kuali.student.class3.krms.termresolver.AtpTermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.krms.KRMSConstants;
import org.kuali.student.krms.data.KRMSEnrollmentEligibilityDataLoader;
import org.kuali.student.krms.mock.KRMSEngineService;
import org.kuali.student.krms.proposition.CompletedCourseBetweenTermsProposition;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test using an in memory implementation of services
 * that proposition type kuali.krms.proposition.type.success.compl.course.between.terms can be evaluated.
 * 
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ks-krms-enrollment-eligibility-mock-context.xml")
public class TestKRMSEnrollmentEligibility {

	@Resource
	private KRMSEngineService krmsServices;
	
	@Resource
	private KRMSEnrollmentEligibilityDataLoader dataLoader;
	
	@Resource
	private AtpService atpService;
	
	@Resource
	private AcademicRecordService recordService;
	
	
	/**
	 * 
	 */
	public TestKRMSEnrollmentEligibility() {
		
	}
	

	@Before
	public void setup() throws Exception {
	
		dataLoader.beforeTest();
		
		krmsServices.initialize();
	}
	
	@After
	public void afterTest() throws Exception {
	    
		dataLoader.afterTest();
		
		
	}


	@Test
	public void testCompletedCourseBetweenTermsProposition() throws DoesNotExistException, OperationFailedException {
	    
	    // setup the test data
        StudentCourseRecordInfo courseRecord = dataLoader.createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(), "ENGL123", "Comparative English Literature");
        
        dataLoader.storeStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(), KRMSEnrollmentEligibilityDataLoader.FAKE_COURSE_ID, courseRecord);
	    
		
		AgendaTree agendaTree = new BasicAgendaTree(new BasicAgendaTreeEntry(new BasicRule(new CompletedCourseBetweenTermsProposition(atpService, recordService), null)));
		
		Map<String, Object> executionFacts = new LinkedHashMap<String, Object>();
		
		// set the student
		executionFacts.put(KRMSConstants.STUDENT_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
		
		// set the course
		executionFacts.put(KRMSConstants.COURSE_ID, KRMSEnrollmentEligibilityDataLoader.FAKE_COURSE_ID);
		
		// set the start date
		executionFacts.put(KRMSConstants.START_DATE, KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);
		
		// set the end date
		executionFacts.put(KRMSConstants.END_DATE, KRMSEnrollmentEligibilityDataLoader.END_FALL_TERM_DATE);
		
		EngineResults agendaResults = krmsServices.executeAgenda(new BasicAgenda(new LinkedHashMap<String, String>(), agendaTree), executionFacts);
		
		List<ResultEvent> resultEvents = agendaResults.getAllResults();
		
		for (ResultEvent event : resultEvents) {
        
		    Boolean result = event.getResult();
        
		    Assert.assertNotNull(result);
		    Assert.assertTrue(result);
		    
		}
	}
	
	@Test
    public void testCompletedCourseBetweenTermsPropositionWithTermResolvers() throws DoesNotExistException, OperationFailedException {
        
        // setup the test data
        StudentCourseRecordInfo courseRecord = dataLoader.createStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(), "ENGL123", "Comparative English Literature");
        
        dataLoader.storeStudentCourseRecord(KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID, dataLoader.getFallTermId(), KRMSEnrollmentEligibilityDataLoader.FAKE_COURSE_ID, courseRecord);
        
        
        AgendaTree agendaTree = new BasicAgendaTree(new BasicAgendaTreeEntry(new BasicRule(new CompletedCourseBetweenTermsProposition(atpService, recordService), null)));
        
        Map<String, Object> executionFacts = new LinkedHashMap<String, Object>();
        
        // set the student
        executionFacts.put(KRMSConstants.STUDENT_ID, KRMSEnrollmentEligibilityDataLoader.STUDENT_ONE_ID);
        
        // set the course
        executionFacts.put(KRMSConstants.COURSE_ID, KRMSEnrollmentEligibilityDataLoader.FAKE_COURSE_ID);
//        
//        // set the start date
//        executionFacts.put(KRMSConstants.START_DATE, KRMSEnrollmentEligibilityDataLoader.START_FALL_TERM_DATE);
//        
//        // set the end date
//        executionFacts.put(KRMSConstants.END_DATE, KRMSEnrollmentEligibilityDataLoader.END_FALL_TERM_DATE);
        
        List<TermResolver<?>> termResolvers = new ArrayList<TermResolver<?>>();
        
        /*
         * Create the term resolver for fall term.
         */
        
        AtpTermResolver fallTermResolver = new AtpTermResolver(KRMSConstants.CURRENT_TERM_ID, "testUser1", dataLoader.getFallTermId(), atpService);    
        
        termResolvers.add(fallTermResolver);
        
        /*
         * From term resolver
         */
        AtpStartDateFieldTermResolver fromResolver = new AtpStartDateFieldTermResolver(KRMSConstants.START_DATE, "testUser1");
        
        termResolvers.add(fromResolver);
        /*
         * End term resolver
         */
        AtpEndDateFieldTermResolver toResolver = new AtpEndDateFieldTermResolver(KRMSConstants.END_DATE, "testUser1");
        
        
        termResolvers.add(toResolver);
        
        krmsServices.setTermResolvers(termResolvers);
        
        EngineResults agendaResults = krmsServices.executeAgenda(new BasicAgenda(new LinkedHashMap<String, String>(), agendaTree), executionFacts);
        
        List<ResultEvent> resultEvents = agendaResults.getAllResults();
        
        for (ResultEvent event : resultEvents) {
        
            Boolean result = event.getResult();
        
            Assert.assertNotNull(result);
            Assert.assertTrue(result);
            
        }
    }
	

}
