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

package org.kuali.student.lum.program.dto.assembly;

public interface ProgramAtpAssembly extends ProgramCommonAssembly {

    /**
      * The first academic time period that this Program would be effective. This may not reflect the first "real" academic time period for this Program.
      */
     public String getStartTerm() ;
     public void setStartTerm(String startTerm) ;

     /**
      * The last academic time period that this Program would be effective.
      */
     public String getEndTerm();
     public void setEndTerm(String endTerm);

     /**
      * The last academic time period that this Program would be available for enrollment. This may not reflect the last "real" academic time period for this Program.   
      */
     public String getEndProgramEntryTerm();
     public void setEndProgramEntryTerm(String endProgramEntryTerm);

}
