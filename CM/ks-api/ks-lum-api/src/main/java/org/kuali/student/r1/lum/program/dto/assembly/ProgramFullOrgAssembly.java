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

import org.kuali.student.r1.lum.program.dto.assembly.ProgramBasicOrgAssembly;

import java.util.List;

public interface ProgramFullOrgAssembly extends ProgramBasicOrgAssembly {

    public List<String> getDivisionsDeployment() ;
    public void setDivisionsDeployment(List<String> divisionsDeployment) ;

    public List<String> getDivisionsFinancialResources() ;
    public void setDivisionsFinancialResources(List<String> divisionsFinancialResources);

    public List<String> getDivisionsFinancialControl() ;
    public void setDivisionsFinancialControl(List<String> divisionsFinancialControl) ;

    public List<String> getUnitsDeployment() ;
    public void setUnitsDeployment(List<String> unitsDeployment);

    public List<String> getUnitsFinancialResources() ;
    public void setUnitsFinancialResources(List<String> unitsFinancialResources);

    public List<String> getUnitsFinancialControl() ;
    public void setUnitsFinancialControl(List<String> unitsFinancialControl);
}
