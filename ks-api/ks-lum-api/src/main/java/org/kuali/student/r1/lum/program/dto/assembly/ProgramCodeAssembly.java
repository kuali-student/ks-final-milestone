/*
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

package org.kuali.student.r1.lum.program.dto.assembly;

public interface ProgramCodeAssembly extends ProgramCommonAssembly {
    /**
     * CIP 2000 Code for the Program
     */
    public String getCip2000Code() ;
    public void setCip2000Code(String cip2000Code);

    /**
     * CIP 2010 Code for the Program
     */
    public String getCip2010Code();
    public void setCip2010Code(String cip2010Code);

    /**
     * HEGIS Code for the Program
     */
    public String getHegisCode();
    public void setHegisCode(String hegisCode) ;

    /**
     * University specific classification e.g Major(Bacc), Specialization
     */
    public String getUniversityClassification();
    public void setUniversityClassification(String universityClassification);


    /**
     * Specifies if the Program is Selective Major, Limited Enrollment program or Selective Admissions
     */
    public String getSelectiveEnrollmentCode();
    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode);
}
