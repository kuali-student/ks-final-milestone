/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.program.service.assembler;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * These are the constants that are used in the programs.
 * 
 * They should match the definitions defined in the wiki See
 * https://test.kuali.org/confluence/display/KULSTG/Types+Used+by+Programs
 * 
 * @author nwright
 */
public class ProgramAssemblerConstants {

	// clu types
	public static final String BACCALAUREATE_PROGRAM = "kuali.lu.type.credential.Baccalaureate";
	public static final String MASTERS_PROGRAM = "kuali.lu.type.credential.Masters";
	public static final String PROFESSIONAL_PROGRAM = "kuali.lu.type.credential.Professional";
    public static final String DOCTORAL_PROGRAM = "kuali.lu.type.credential.Doctoral";
	public static final String UNDERGRADUATE_CERTIFICATE = "kuali.lu.type.credential.UndergraduateCertificate";
	public static final String GRADUATE_CERTIFICATE = "kuali.lu.type.credential.GraduateCertificate";

	public static final String[] CREDENTIAL_PROGRAM = { BACCALAUREATE_PROGRAM,
								MASTERS_PROGRAM, PROFESSIONAL_PROGRAM, DOCTORAL_PROGRAM,
								UNDERGRADUATE_CERTIFICATE, GRADUATE_CERTIFICATE };

	// used for testing credentialProgramType
    public static final Set<String> CREDENTIAL_PROGRAM_TYPES = new TreeSet<String>(Arrays.asList(CREDENTIAL_PROGRAM));

	public static final String UNDERGRAD_PROGRAM_LEVEL = "kuali.lu.program.level.UnderGraduate";
	public static final String GRADUATE_PROGRAM_LEVEL = "kuali.lu.program.level.Graduate";
	
	public static final String MAJOR_DISCIPLINE = "kuali.lu.type.MajorDiscipline";
	public static final String PROGRAM_VARIATION = "kuali.lu.type.Variation";
	public static final String MINOR_DISCIPLINE = "kuali.lu.type.MinorDiscipline";
	public static final String CORE_PROGRAM = "kuali.lu.type.CoreProgram";
	public static final String HONORS_PROGRAM = "kuali.lu.type.Honors";
	public static final String PROGRAM_REQUIREMENT = "kuali.lu.type.Requirement";

	// clu states
	public static final String DRAFT = "draft";
	public static final String ACTIVE = "active"; // also use for identifiers
													// and relations
	public static final String SUPERSEDED = "superseded";
	public static final String SUSPENDED = "suspended";
	public static final String RETIRED = "retired";
	public static final String[] LATEST_STATES = { ACTIVE, SUSPENDED, RETIRED };

	// clu identifier types
	public static final String OFFICIAL = "kuali.lu.type.program.identifier.official";
	public static final String TRANSCRIPT = "kuali.lu.type.program.identifier.transcript";
	public static final String DIPLOMA = "kuali.lu.type.program.identifier.diploma";

	// clu-clu relation types
	public static final String HAS_CORE_PROGRAM = "kuali.lu.lu.relation.type.hasCoreProgram";
	public static final String HAS_PROGRAM_VARIATION = "kuali.lu.lu.relation.type.hasVariationProgram";
	public static final String HAS_MAJOR_PROGRAM = "kuali.lu.lu.relation.type.hasMajorProgram";
	public static final String HAS_PROGRAM_REQUIREMENT = "kuali.lu.lu.relation.type.hasProgramRequirement";

	// lucode types
	public static final String CIP_2000 = "kuali.lu.code.CIP2000";
	public static final String CIP_2010 = "kuali.lu.code.CIP2010";
	public static final String HEGIS = "kuali.lu.code.HEGIS";
	public static final String UNIVERSITY_CLASSIFICATION = "kuali.lu.code.UniversityClassification";
	public static final String SELECTIVE_ENROLLMENT = "kuali.lu.code.SelectiveEnrollment";

	// publication types
	public static final String CATALOG = "kuali,lu.publication.Catalog";

    // adminorg types
	public static final String CONTENT_OWNER_DIVISION = "kuali.adminOrg.type.ContentOwnerDivision";
    public static final String CURRICULUM_OVERSIGHT_DIVISION = "kuali.adminOrg.type.CurriculumOversightDivision";
    public static final String STUDENT_OVERSIGHT_DIVISION = "kuali.adminOrg.type.StudentOversightDivision";
    public static final String DEPLOYMENT_DIVISION = "kuali.adminOrg.type.DeploymentDivision";
    public static final String FINANCIAL_RESOURCES_DIVISION = "kuali.adminOrg.type.FinancialResourcesDivision";
    public static final String FINANCIAL_OVERSIGHT_DIVISION = "kuali.adminOrg.type.FinancialOversightDivision";
    public static final String FINANCIAL_CONTROL_DIVISION = "kuali.adminOrg.type.FinancialControlDivision";
    public static final String CURRICULUM_OVERSIGHT_UNIT = "kuali.adminOrg.type.CurriculumOversightUnit";
    public static final String STUDENT_OVERSIGHT_UNIT = "kuali.adminOrg.type.StudentOversightUnit";
    public static final String DEPLOYMENT_UNIT = "kuali.adminOrg.type.DeploymentUnit";
    public static final String FINANCIAL_RESOURCES_UNIT = "kuali.adminOrg.type.FinancialResourcesUnit";
    public static final String FINANCIAL_CONTROL_UNIT = "kuali.adminOrg.type.FinancialControlUnit";
    public static final String INSTITUTION = "kuali.adminOrg.type.Institution";
    public static final String CONTENT_OWNER_UNIT = "kuali.adminOrg.type.ContentOwnerUnit";
    public static final String FINANCIAL_OVERSIGHT_UNIT = "kuali.adminOrg.type.FinancialOversightUnit";

    // cluResult Types
    public static final String DEGREE_RESULTS = "kuali.resultType.degree";
    public static final String CERTIFICATE_RESULTS = "kuali.resultType.certificate";
    public static final String ANNOTATION_RESULTS = "kuali.resultType.annotation";
    public static final String COMPLETION_RESULTS = "kuali.resultType.completion";

    // dynamic attributes the UI needs to retrieve
    public static final String PROGRAM_LEVEL = "programLevel";
}
