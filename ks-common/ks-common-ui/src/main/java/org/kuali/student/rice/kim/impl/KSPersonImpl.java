/**
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
 *
 */
package org.kuali.student.rice.kim.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.student.rice.kim.KSPerson;

/**
 * This class is just a wrapper around {@link org.kuali.rice.kim.impl.identity.PersonImpl} to handle some of the extra
 * fields to be displayed at the search in KS Screens.
 *
 * In Course Offering screens, We need to display the person affiliation types for the
 * instructors at the lookup result, which is not possible with {@link org.kuali.rice.kim.impl.identity.PersonImpl}
 *
 * This class should be used only at person lookup in different KS views. This is not intended to override KIM class or KIM UI functionalities
 *
 * @author Kuali Student Team
 */
public class KSPersonImpl implements KSPerson{

    private Person person;
    private String personAffiliationType;
    private String ksNameSearch;

    public KSPersonImpl(){
    }

    /**
     * This is the only constructor should be using from lookups, passing in the rice Person object.
     * @param person
     */
    public KSPersonImpl(Person person){
        this.person = person;
    }

    /**
     * This returns the affilitation types as STAFF/STUDENT, which is needed at the result set.
     * @return
     */
    public String getPersonAffiliationType() {
        return personAffiliationType;
    }

    public void setPersonAffiliationType(String personAffiliationType) {
        this.personAffiliationType = personAffiliationType;
    }

    /**
     * Returns the {@link org.kuali.rice.kim.impl.identity.PersonImpl} object
     * @return
     */
    public Person getPerson() {
        return person;
    }

    public String getKsNameSearch() {
        return ksNameSearch;
    }

    /**
     * Name field displayed at the search
     *
     * @param ksNameSearch
     */
    public void setKsNameSearch(String ksNameSearch) {
        this.ksNameSearch = ksNameSearch;
    }

    public String getName(){
        if (person != null){
            return person.getName();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String getPrincipalId(){
        if (person != null){
            return person.getPrincipalId();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String getPrincipalName(){
        if (person != null){
            return person.getPrincipalName();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String getPrimaryDepartmentCode(){
        if (person != null){
            return person.getPrimaryDepartmentCode();
        } else {
            return StringUtils.EMPTY;
        }
    }

}
