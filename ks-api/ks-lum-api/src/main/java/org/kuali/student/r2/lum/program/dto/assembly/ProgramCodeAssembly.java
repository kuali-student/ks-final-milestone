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
package org.kuali.student.r2.lum.program.dto.assembly;

/**
 * R1 implementation Compatibility
 * @author nwright
 * @deprecated1
 */
@Deprecated
public interface ProgramCodeAssembly extends ProgramCommonAssembly {

    public String getCip2000Code();

    public void setCip2000Code(String cip2000Code);

    public String getCip2010Code();

    public void setCip2010Code(String cip2010Code);

    public String getHegisCode();

    public void setHegisCode(String hegisCode);

    public String getUniversityClassification();

    public void setUniversityClassification(String universityClassification);

    public String getSelectiveEnrollmentCode();

    public void setSelectiveEnrollmentCode(String selectiveEnrollmentCode);
}
