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

import org.kuali.student.r2.common.infc.Relationship;

/**
 * An affiliation the person has to some organization.
 * 
 * Most are affiliations to the overall institute that is tracked in many identity systems.
 * It is often used to filter results to a smaller subset of people than the entire population.
 * 
 * I.e. I only want students or I only want employees.
 * 
 * This is designed to be more flexible so that other organizational units such as the law school or even academic departments 
 * could track  students or faculty that are affiliated to them and then execute searches just within their specific group. 
 */
public interface PersonAffiliation extends Relationship {
    
    
    /**
     * The id of the person who is affiliated to the org.
     * 
     * @name Person Id
     * @required
     * @readOnly
     * EntityAffiliation.entityId
     */
    public String getPersonId();
    
    /**
     * An organization to which this person is affiliated
     * 
     * By default this is the the overall institution but it could also be to a particular sub-division such as the law school
     * or a particular campus or a particular department.
     * 
     * @name OrganizationId
     * @required
     * @impl EntityAffiliation.campusCode or some nailed up  org that represents the overall institution.
     */
    public String getOrganizationId();

  

}
