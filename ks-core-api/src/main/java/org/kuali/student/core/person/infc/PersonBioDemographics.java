/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.person.infc;

import java.util.Date;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * An identifier number or key that is used to identify the person.
 */
public interface PersonBioDemographics extends IdNamelessEntity {
    
    
    /**
     * The id of the person to whom this demographics are attached.
     * 
     * @name Person Id
     * @required
     * @readOnly
     * EntityBioDemographics.entityId
     */
    public String getPersonId();
    
    /**
     * Date of Birth
     * 
     *  
     * @name Birth Date
     * @required
     * @impl EntityBioDemographics.birthDate
     */
    public Date getBirthDate();

    /**
     * Date deceased if person is deceased and the date is known.
     * 
     * ??? What if we know someone is dead but don't know the date?
     * What if we know the approximate date?
     *  
     * @name Deceased Date
     * @required
     * @impl EntityBioDemographics.birthDate
     */
    public Date getDeceasedDate();

    /**
     * Gender or Sex of the person
     * Values include M, F and U for unreported.
     * 
     * @name gender code
     * @required
     * @impl EntityBioDemographics.genderCode
     */
    public String getGenderCode();

}
