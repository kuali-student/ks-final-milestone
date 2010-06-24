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

package org.kuali.student.lum.statement.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:statement-additional-context.xml"})
public class TestStatementServiceImpl {

	@Autowired
    public StatementService statementService;
	
	@BeforeClass
	public static void beforeClass() {
	}
	
    @Test
    public void testTranslateReqComponent_none1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-1", "KUALI.CATALOG", "en");
        assertEquals("Must not have successfully completed MATH152", nl);
    } 
    
    @Test
    public void testTranslateReqComponent_noneN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-2", "KUALI.CATALOG", "en");
        assertEquals("Must not have successfully completed any courses from (MATH152, MATH180)", nl);
    }           
	
    @Test
    public void testTranslateReqComponent_all1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.all'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-3", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed MATH152", nl);
    }
    
    @Test
    public void testTranslateReqComponent_allN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.all'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-4", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed all courses from (MATH152, MATH180)", nl);
    }    
    
    @Test
    public void testTranslateReqComponent_1of1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.nof '
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-5", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed MATH152", nl);
    }	
	
	@Test
	public void testTranslateReqComponent_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    //req. type: 'kuali.reqCompType.course.courseset.completed.nof '
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-6", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 1 course from (MATH152, MATH180)", nl);
	}

    @Test
    public void testTranslateReqComponent_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.nof '        
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-7", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed a minimum of 2 courses from (MATH152, MATH180)", nl);
    }   
        
    @Test
    public void testTranslateReqComponent_enroll_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.enrolled.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-8", "KUALI.CATALOG", "en");
        assertEquals("Must be concurrently enrolled in a minimum of 1 course from (MATH152, MATH180)", nl);
    }     
    
    @Test
    public void testTranslateReqComponent_enroll_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.enrolled.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-9", "KUALI.CATALOG", "en");
        assertEquals("Must be concurrently enrolled in a minimum of 2 courses from (MATH152, MATH180)", nl);
    }     
   
    @Test
    public void testTranslateReqComponent_credits_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.credits.completed.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-10", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed a minimum of 1 credit from (MATH152, MATH180)", nl);
    }     
    
    @Test
    public void testTranslateReqComponent_credits_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.credits.completed.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-11", "KUALI.CATALOG", "en");
        assertEquals("Must have successfully completed a minimum of 2 credits from (MATH152, MATH180)", nl);
    }    
    
    @Test
    public void testTranslateReqComponent_credits_none() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.credits.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-12", "KUALI.CATALOG", "en");
        assertEquals("Must not have successfully completed any credits from (MATH152, MATH180)", nl);
    }        
    
    @Test
    public void testTranslateReqComponent_gradecheck() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.gradecheck'        
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-13", "KUALI.CATALOG", "en");
        assertEquals("Student needs a minimum GPA of 3.5", nl);
    }	
	
    @Test
    public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqCompType.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-1", "KUALI.CATALOG", null);
        assertEquals("Must not have successfully completed MATH152", nl);
    }   
}
