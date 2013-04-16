/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lui.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;


/**
 * Detailed information about a LUI Identifier.
 */
public interface LuiIdentifier extends IdNamelessEntity {

    /**
     * The composite string that is used to officially reference or
     * publish the LUI. Note it may have an internal structure that
     * each Institution may want to enforce. This structure may be
     * composed from the other parts of the structure such as Level
     * amp; Division, but may include items such as luiType.
     *
     * @name Code
     */
    public String getCode();

    /**
     * Abbreviated name of the LUI, commonly used on transcripts.
     *
     * @name Short Name
     */
    public String getShortName();

    /**
     * The Long Name of the LUI.
     *
     * @name Long Name
     */
    public String getLongName();

    /**
     * A code that indicates whether this is introductory, advanced, etc.
     *
     * @name Level
     */
    public String getLevel();

    /**
     * A code that indicates what school, program, major, subject
     * area, etc. Examples: "Chem", "18".
     *
     * @name Division
     */
    public String getDivision();
    
    /*
     * The "extra" portion of the code, which usually corresponds with
     * the most detailed part of the number.
     *
     * @name Suffix Code
     */    
    public String getSuffixCode();
    
    /**
     * A number that indicates the sequence or order of versions in
     * cases where several different Clus have the same offical
     * Identifier.
     *
     * @name Variation
     */
    public String getVariation();

    /*
     * The identifier of the organization associated with this luiIdentifier.
     * @name Org Id
     * @readOnly
     */
    public String getOrgId();
}