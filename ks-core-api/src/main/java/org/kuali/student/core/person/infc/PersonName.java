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
 * A person's name
 */
public interface PersonName extends IdNamelessEntity {
    
    
    /**
     * The id of the person to whom this name is attached.
     * 
     * @name Person Id
     * @required
     * @readOnly
     * @impl Entity.id
     */
    public String getPersonId();
    
    /**
     * A person's first or familiar name.
     * 
     * The first name of the person, usually given at birth,baptism, during another ceremony, or through legal change
     * 
     * In some countries the famiilar name is typically add the end so "first" may be a misnomer.
     *
     * @name First Name
     * @impl EntityName.firstName
     */
    public String getFirstName();

    /**
     * The last name or surname or family name by which a person is legally known
     * 
     * @name Last Name
     * @impl EntityName.lastName
     */
    public String getLastName();

    /**
     * A secondary Name of the person,usually given at birth,baptism,or during another naming ceremony or through legal change
     * 
     * If a person has multiple middle names they should all be included here.
     * Basically this should hold any parts of the name that is not assigned to the first or last fields.
     * 
     * @name Middle name
     * @impl EntityName.middleName
     */
    public String getMiddleName();

    /**
     * The date that this person's name was changed.
     * 
     * This is used to track multiple versions of a name with the same type.
     * 
     * This should be the date when the name was officially changed, if known, and not simply the date the change was made.
     *
     * @name Name Change Date
     * @impl EntityName.nameChangedDate
     */
    public Date getNameChangedDate();

    /**
     * An honorific, title or form of address that precedes the name.
     * 
     * Examples include Mr., Ms., Miss, Mrs. Dr., Honorable, Sir, Captain, etc.
     * 
     * @name Name Prefix
     * @impl EntityName.namePrefix
     */
    public String getNamePrefix();

    /**
     * A suffix added to a person's name.
     * The expression of an individual's generation within a family. 
     * 
     * For example Jr., Sr., I, II, III, 
     * 
     * @name Description
     * @impl EntityName.nameSuffix
     */
    public String getNameSuffix();

    /**
     * The rank, title or credential that follows the name.
     * For example: PhD or Esq. for an attorney
     *
     * @name Name Title
     * @impl EntityName.nameTitle
     */
    public String getNameTitle();  

}
