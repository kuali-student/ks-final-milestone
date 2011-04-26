/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.classI.lui.infc;


import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasState;
import org.kuali.student.r2.common.infc.HasType;
import org.kuali.student.r2.common.infc.IdEntity;

public interface Lui
        extends IdEntity,
        HasEffectiveDates {

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Code identifier/name for the LUI. This is typically used
     * human readable form (e.g. ENGL 100 section 123).
     */
    public String getLuiCode();

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for a Canonical Learning Unit (CLU).
     */
    public String getCluId();

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Unique identifier for an Academic Time Period (ATP).
     */
    public String getAtpKey();

    /**
     * Get ????
     * <p/>
     * Type: Integer
     * <p/>
     * Maximum number of "seats" that the LUI will hold for registration.
     */
    public Integer getMaxSeats();

}

