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

import java.util.List;

public interface ProgramIdentifierAssembly extends ProgramCommonAssembly {

     /**
     * The composite string that is used to officially reference or publish the program. Note it may have an internal structure that each Institution may want to enforce. This structure may be composed from the other parts of the structure such as Level amp; Division, but may include items such as cluType.
     */
    public String getCode();
    public void setCode(String code);

        /**
     * Abbreviated name of the Program
     */
    public String getShortTitle() ;
    public void setShortTitle(String shortTitle) ;

    /**
     * Full name of the Program
     */
    public String getLongTitle();
    public void setLongTitle(String longTitle) ;

    /**
     * Information related to the official identification of the Program, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle() ;
    public void setTranscriptTitle(String transcriptTitle);
    

    public String getDiplomaTitle();
    public void setDiplomaTitle(String diplomaTitle) ;

    /**
     * Program Requirements.
     */
    public List<String> getProgramRequirements();
    public void setProgramRequirements(List<String> programRequirements);

}
