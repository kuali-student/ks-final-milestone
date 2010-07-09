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

/**
 * These are the constants that are used in the programs.
 *
 * They should match the definitions defined in the wiki
 * See https://test.kuali.org/confluence/display/KULSTG/Types+Used+by+Programs
 * 
 * @author nwright
 */
public class ProgramAssemblerConstants {



 	public static final String BACCALAUREATE_PROGRAM = "kuali.lu.type.credential.Baccalaureate";
		public static final String MASTERS_PROGRAM = "kuali.lu.type.credential.Masters";
		public static final String PROFESSIONAL_PROGRAM = "kuali.lu.type.credential.Professional";
		public static final String DOCOTORAL_PROGRAM = "kuali.lu.type.credential.Doctoral";
		public static final String UNDERGRADUATE_CERTIFICATE = "kuali.lu.type.credential.UndergraduateCertificate";
		public static final String GRADUATE_CERTIFICATE = "kuali.lu.type.credential.GraduateCertificate";

  	public static final String[] CREDENTIAL_PROGRAM =
		     {BACCALAUREATE_PROGRAM
		     ,MASTERS_PROGRAM
		     ,PROFESSIONAL_PROGRAM
		     ,DOCOTORAL_PROGRAM
		     ,UNDERGRADUATE_CERTIFICATE
		     ,GRADUATE_CERTIFICATE
		     };

		public static final String MAJOR_DISCIPLINE = "kuali.lu.type.MajorDiscipline";
		public static final String PROGRAM_VARIATION = "kuali.lu.type.Variation";
		public static final String MINOR_DISCIPLINE = "kuali.lu.type.MinorDiscipline";
		public static final String CORE_PROGRAM = "kuali.lu.type.CoreProgram";
		public static final String HONORS_PROGRAM = "kuali.lu.type.Honors";
		public static final String PROGRAM_REQUIREMENT = "kuali.lu.type.Requirement";

		public static final String TRANSCRIPT = "kuali.lu.type.program.identifier.transcript";
		public static final String DIPLOMA = "kuali.lu.type.program.identifier.diploma";

  public static final String HAS_CORE_PROGRAM = "kuali.lu.lu.relation.type.hasCoreProgram";
		public static final String HAS_PROGRAM_VARIATION = "kuali.lu.lu.relation.type.hasVariationProgram";
		public static final String HAS_MAJOR_PROGRAM = "kuali.lu.lu.relation.type.hasMajorProgram";
		public static final String HAS_PROGRAM_REQUIREMENT = "kuali.lu.lu.relation.type.hasProgramRequirement";

 	public static final String CIP_2000 = "kuali.lu.code.CIP2000";
 	public static final String CIP_2010 = "kuali.lu.code.CIP2010";
		public static final String HEGIS = "kuali.lu.code.HEGIS";
}
