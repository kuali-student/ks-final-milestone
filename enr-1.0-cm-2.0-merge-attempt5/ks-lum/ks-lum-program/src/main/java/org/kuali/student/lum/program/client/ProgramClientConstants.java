/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.lum.program.client;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Constants needed for retrieval of different program types
 */
public class ProgramClientConstants {

    public static final String CREDENTIAL_PROGRAM_ID_TYPE = "credentialProgramIdType";

    public static final String CREDENTIAL_BACCALAUREATE_PROGRAM = "kuali.lu.type.credential.Baccalaureate";
    public static final String CREDENTIAL_MASTERS_PROGRAM = "kuali.lu.type.credential.Masters";
    public static final String CREDENTIAL_PROFESSIONAL_PROGRAM = "kuali.lu.type.credential.Professional";
    public static final String CREDENTIAL_DOCTORAL_PROGRAM = "kuali.lu.type.credential.Doctoral";
    public static final String CREDENTIAL_UNDERGRADUATE_CERTIFICATE = "kuali.lu.type.credential.UndergraduateCertificate";
    public static final String CREDENTIAL_GRADUATE_CERTIFICATE = "kuali.lu.type.credential.GraduateCertificate";

    public static final String[] CREDENTIAL_PROGRAM = { CREDENTIAL_BACCALAUREATE_PROGRAM, CREDENTIAL_MASTERS_PROGRAM,
                                                        CREDENTIAL_PROFESSIONAL_PROGRAM, CREDENTIAL_DOCTORAL_PROGRAM,
												        CREDENTIAL_UNDERGRADUATE_CERTIFICATE, CREDENTIAL_GRADUATE_CERTIFICATE };

    // used for testing credentialProgramType
    public static final Set<String> CREDENTIAL_PROGRAM_TYPES = new TreeSet<String>(Arrays.asList(CREDENTIAL_PROGRAM));

    public static final String CORE_PROGRAM = "kuali.lu.type.CoreProgram";
    public static final String MAJOR_PROGRAM = "kuali.lu.type.MajorDiscipline";
}
