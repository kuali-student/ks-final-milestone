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
public class TestStatementServiceImplForProgram {

	@Autowired
    public StatementService statementService;
	
	@BeforeClass
	public static void beforeClass() {
	}
	
	/**
	 * Requirement component type: kuali.reqCompType.program.programset.completed.nof
	 */
	@Test
	public void testTranslateReqComponent_Completed1ofProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-101", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 1 program from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.completed.nof
	 */
	@Test
	public void testTranslateReqComponent_Completed2ofProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-105", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 2 programs from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.notcompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_NotCompletedAnyOfProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-102", "KUALI.CATALOG", "en");
    	assertEquals("Must not have successfully completed any of (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.notcompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_NotCompletedProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-108", "KUALI.CATALOG", "en");
    	assertEquals("Must not have successfully completed Sociology program", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.completed.all
	 */
	@Test
	public void testTranslateReqComponent_CompletedAllProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-103", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed all of (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.completed.all
	 */
	@Test
	public void testTranslateReqComponent_CompletedSingleProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-107", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed Sociology program", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_1CourseCompletedFromPrograms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-104", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 1 course from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_2CourseCompletedFromPrograms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-106", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 2 courses from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqCompType.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_2CourseCompletedFrom1Program() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-109", "KUALI.CATALOG", "en");
    	assertEquals("Must have successfully completed a minimum of 2 courses from Sociology program", nl);
	}
	
    /**
     * Requirement component type: kuali.reqCompType.program.admitted.credits
     */
    @Test
    public void testTranslateReqComponent_admittedCreditsProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-110", "KUALI.CATALOG", "en");
        assertEquals("Must be admitted to program prior to earning 5 credits", nl);
    }	
}
