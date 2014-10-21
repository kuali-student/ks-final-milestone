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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.service.StatementService;
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
	 * Requirement component type: kuali.reqComponent.type.program.programset.completed.nof
	 */
	@Test
	public void testTranslateReqComponent_Completed1ofProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-101", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 1 program from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.completed.nof
	 */
	@Test
	public void testTranslateReqComponent_Completed2ofProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-105", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 2 programs from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.notcompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_NotCompletedAnyOfProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-102", "KUALI.RULE", "en");
    	assertEquals("Must not have successfully completed any of (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.notcompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_NotCompletedProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-108", "KUALI.RULE", "en");
    	assertEquals("Must not have successfully completed Sociology program", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.completed.all
	 */
	@Test
	public void testTranslateReqComponent_CompletedAllProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-103", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed all of (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.completed.all
	 */
	@Test
	public void testTranslateReqComponent_CompletedSingleProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-107", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed Sociology program", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_1CourseCompletedFromPrograms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-104", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 1 course from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_2CourseCompletedFromPrograms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-106", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 2 courses from (Sociology, Geology) programs", nl);
	}

	/**
	 * Requirement component type: kuali.reqComponent.type.program.programset.coursecompleted.nof
	 */
	@Test
	public void testTranslateReqComponent_2CourseCompletedFrom1Program() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-109", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 2 courses from Sociology program", nl);
	}
	
    /**
     * Requirement component type: kuali.reqComponent.type.program.admitted.credits
     */
    @Test
    public void testTranslateReqComponent_admittedCreditsProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-110", "KUALI.RULE", "en");
        assertEquals("Must be admitted to program prior to earning 5 credits", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.credits.min
     */
    @Test
    public void testTranslateReqComponent_minTotalCredits_1Credit() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-111", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum of 1 total credit", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.credits.min
     */
    @Test
    public void testTranslateReqComponent_minTotalCredits_120Credits() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-112", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum of 120 total credits", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.credits.max
     */
    @Test
    public void testTranslateReqComponent_maxTotalCredits_130Credits() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-113", "KUALI.RULE", "en");
        assertEquals("Must not have earned more than 130 credits", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.completion.duration
     */
    @Test
    public void testTranslateReqComponent_CompletionDuration_10Credits() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-114", "KUALI.RULE", "en");
        assertEquals("Must not exceed 10 semesters without completing program", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.candidate.status.duration
     */
    @Test
    public void testTranslateReqComponent_CandidateStatusDuration_3Credits() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-115", "KUALI.RULE", "en");
        assertEquals("Must attain candidate status within 3 semesters after program entry term", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.completion.duration.afterentry
     */
    @Test
    public void testTranslateReqComponent_CompletionDurationAfterEntry_3Credits() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-116", "KUALI.RULE", "en");
        assertEquals("Must complete program within 10 semesters after program entry term", nl);
    }	

    /**
     * Statement type: kuali.statement.type.program.entrance 
     */
    @Test
    public void testTranslateStatementTree() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForStatement("STMT-TV-1", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed all courses from (MATH152, MATH180) " +
        		"and Must have earned a minimum GPA of 3.5 in (MATH152, MATH180) " +
        		"and (Must have successfully completed a minimum of 1 course from (MATH152, MATH180) " +
        		"or Permission of instructor required)", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.residence.credits.final
     */
    @Test
    public void testTranslateReqComponent_kuali_reqComponent_type_program_residence_credits_final() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-117", "KUALI.RULE", "en");
        assertEquals("Sociology students must take their final 30 credits in residence", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.enrolled.credits.final
     */
    @Test
    public void testTranslateReqComponent_kuali_reqComponent_type_program_enrolled_credits_final() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-118", "KUALI.RULE", "en");
        assertEquals("Sociology students must be enrolled in their graduation major for the final 30 credits taken", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.minors.nof
     */
    @Test
    public void testTranslateReqComponent_kuali_reqComponent_type_program_minors_nof() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-119", "KUALI.RULE", "en");
        assertEquals("Must earn no more than 2 minors as part of a program", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.program.minor.admitted.classstanding
     */
    @Test
    public void testTranslateReqComponent_kuali_reqComponent_type_program_minor_admitted_classstanding() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-120", "KUALI.RULE", "en");
        assertEquals("Must be admitted to a minor program only if they have junior or senior class standing", nl);
    }	

    /**
     * Requirement component type: kuali.reqComponent.type.course.courseset.completed.max
     */
    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_courseset_completed_max() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-121", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed no more than 2 courses from (MATH152, MATH180)", nl);
    }

    /**
     * Requirement component type: kuali.reqComponent.type.program.cumulative.gpa.min
     */
    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_program_cumulative_gpa_min() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.program.cumulative.gpa.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-122", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum cumulative GPA of 2.5", nl);
    }

    /**
     * Requirement component type: kuali.reqComponent.type.program.duration.gpa.min
     */
    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_program_duration_gpa_min() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.program.duration.gpa.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-NL-123", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum semester GPA of 3.0", nl);
    }
}
