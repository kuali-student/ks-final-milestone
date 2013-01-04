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
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.statement.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:statement-additional-context.xml"})
public class TestStatementServiceImplForCourse {

	@Autowired
    public StatementService statementService;

	@BeforeClass
	public static void beforeClass() {
	}

    @Test
    public void testGetNaturalLanguageForReqComponent_DefaultEnglish() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-1", "KUALI.RULE", null);
        assertEquals("Must not have successfully completed MATH152", nl);
    }	
	
    @Test
    public void testGetNaturalLanguageForReqComponent_none1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-1", "KUALI.RULE", "en");
        assertEquals("Must not have successfully completed MATH152", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_noneN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-2", "KUALI.RULE", "en");
        assertEquals("Must not have successfully completed any courses from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_all1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.all'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-3", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed MATH152", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_allN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.all'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-4", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed all courses from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_1of1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.nof '
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-5", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed MATH152", nl);
    }

	@Test
	public void testGetNaturalLanguageForReqComponent_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    //req. type: 'kuali.reqComponent.type.course.courseset.completed.nof '
		String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-6", "KUALI.RULE", "en");
    	assertEquals("Must have successfully completed a minimum of 1 course from (MATH152, MATH180)", nl);
	}

    @Test
    public void testGetNaturalLanguageForReqComponent_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.nof '
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-7", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed a minimum of 2 courses from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_enroll_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.enrolled.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-8", "KUALI.RULE", "en");
        assertEquals("Must be concurrently enrolled in a minimum of 1 course from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_enroll_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.enrolled.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-9", "KUALI.RULE", "en");
        assertEquals("Must be concurrently enrolled in a minimum of 2 courses from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_credits_1ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.credits.completed.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-10", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed a minimum of 1 credit from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_credits_2ofN() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.credits.completed.nof'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-11", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed a minimum of 2 credits from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_credits_none() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.credits.completed.none'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-12", "KUALI.RULE", "en");
        assertEquals("Must not have successfully completed any credits from (MATH152, MATH180)", nl);
    }
    
    @Test
    public void testGetNaturalLanguageForReqComponent_credits_max1() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.credits.completed.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-13", "KUALI.RULE", "en");
        assertEquals("Must successfully complete no more than 1 credit from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_credits_max2() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.credits.completed.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-14", "KUALI.RULE", "en");
        assertEquals("Must successfully complete no more than 2 credits from (MATH152, MATH180)", nl);
    }    

    @Test
    public void testGetNaturalLanguageForReqComponent_gradecheck() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.gpa.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-15", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum GPA of 3.5 in (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_grade_min_GradeType_Letter() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-16a", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum grade of letter B in (MATH152, MATH180)", nl);
    }   
    
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_min_GradeType_Percentage() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-16b", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum grade of percentage 70-79% in (MATH152, MATH180)", nl);
    }   
    
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_min_GradeType_PassFail() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-16c", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum grade of Pass in (MATH152, MATH180)", nl);
    }   
    
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_min_GradeType_CompletedNotation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-16d", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum grade of In-Progress in (MATH152, MATH180)", nl);
    }   
    
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_min_GradeType_Satisfactory() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-16e", "KUALI.RULE", "en");
        assertEquals("Must have earned a minimum grade of Satisfactory in (MATH152, MATH180)", nl);
    }   
    
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_max_GradeType_Letter() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-17a", "KUALI.RULE", "en");
        assertEquals("Must not have earned a maximum grade of letter C or higher in (MATH152, MATH180)", nl);
    }         
        
    @Test
    public void testGetNaturalLanguageForReqComponent_grade_max_GradeType_CompletedNotation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.grade.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-17b", "KUALI.RULE", "en");
        assertEquals("Must not have earned a maximum grade of Not-Satisfactory or higher in (MATH152, MATH180)", nl);
    }         
        
    @Test
    public void testGetNaturalLanguageForReqComponent_perm_org() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.permission.org.required'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-18", "KUALI.RULE", "en");
        assertEquals("Permission of English Dept required", nl);
    }
    
    @Test
    public void testGetNaturalLanguageForReqComponent_perm_instructor() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.permission.instructor.required'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-19", "KUALI.RULE", "en");
        assertEquals("Permission of instructor required", nl);
    }       
    
    @Test
    public void testGetNaturalLanguageForReqComponent_allN_CluSetOfClusets() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.completed.all'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-20", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed all courses from (MATH152, MATH221, MATH180, MATH200, MATH215)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_MinTestScoreOnTest() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.test.score.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-21", "KUALI.RULE", "en");
        assertEquals("Must have achieved a minimum score of 600 on SAT Critical Reading Exam", nl);
    }       
    
    @Test
    public void testGetNaturalLanguageForReqComponent_MaxTestScoreOnTest() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.test.score.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-22", "KUALI.RULE", "en");
        assertEquals("Must have achieved a score no higher than 900 on SAT Critical Reading Exam", nl);
    }       
    
    @Test
    public void testGetNaturalLanguageForReqComponent_MinCoursesMinGradeTest_GradeType_Letter() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.nof.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-23a", "KUALI.RULE", "en");
        assertEquals("Must successfully complete a minimum of 1 course with a minimum grade of letter B from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_MinCoursesMinGradeTest_GradeType_CompletedNotation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.courseset.nof.grade.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-23b", "KUALI.RULE", "en");
        assertEquals("Must successfully complete a minimum of 1 course with a minimum grade of Completed from (MATH152, MATH180)", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_admitted_org_duration_ProgramAsClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.admitted.org.duration'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-24a", "KUALI.RULE", "en");
        assertEquals("Students admitted to Sociology may take no more than 2 courses in the English Dept in 1 year", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_admitted_org_duration_ProgramAsCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.admitted.org.duration'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-24b", "KUALI.RULE", "en");
        assertEquals("Students admitted to Sociology may take no more than 2 courses in the English Dept in 1 year", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_org_duration_ProgramAsClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.notadmitted.org.duration'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-25a", "KUALI.RULE", "en");
        assertEquals("Students not admitted to Sociology may take no more than 3 courses in the Computer Science Dept in 1 year", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_org_duration_ProgramAsCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.notadmitted.org.duration'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-25b", "KUALI.RULE", "en");
        assertEquals("Students not admitted to Sociology may take no more than 3 courses in the Computer Science Dept in 1 year", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponent_kuali_reqComponent_type_course_org_program_admitted() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.org.program.admitted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-26", "KUALI.RULE", "en");
        assertEquals("Must be admitted to any program offered at the course campus location", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_ProgramAsClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.notadmitted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-27a", "KUALI.RULE", "en");
        assertEquals("Must not have been admitted to the Sociology program", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_ProgramAsCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.notadmitted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-27b", "KUALI.RULE", "en");
        assertEquals("Must not have been admitted to the Sociology program", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_credits_repeat_max() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.credits.repeat.max'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-28", "KUALI.RULE", "en");
        assertEquals("May be repeated for a maximum of 6 credits", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_org_credits_completed_min() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.org.credits.completed.min'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-29", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed a minimum of 30 credits from courses in the Computer Science Dept", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_admitted_ProgramAsClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.admitted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-30a", "KUALI.RULE", "en");
        assertEquals("Must have been admitted to the Sociology program", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_program_admitted_ProgramAsCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.program.admitted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-30b", "KUALI.RULE", "en");
        assertEquals("Must have been admitted to the Sociology program", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_completed() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.completed'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-31", "KUALI.RULE", "en");
        assertEquals("Must have successfully completed MATH152", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_enrolled() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.enrolled'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-32", "KUALI.RULE", "en");
        assertEquals("Must be concurrently enrolled in MATH152", nl);
    }

    @Test
    public void testGetNaturalLanguageForReqComponentType_kuali_reqComponent_type_course_notcompleted() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        //req. type: 'kuali.reqComponent.type.course.notcompleted'
        String nl = statementService.getNaturalLanguageForReqComponent("REQCOMP-33", "KUALI.RULE", "en");
        assertEquals("Must not have successfully completed MATH152", nl);
    }
}
