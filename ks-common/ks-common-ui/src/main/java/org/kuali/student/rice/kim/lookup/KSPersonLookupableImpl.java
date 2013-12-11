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
package org.kuali.student.rice.kim.lookup;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliationContract;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.rice.kim.impl.identity.PersonLookupableImpl;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.rice.kim.impl.KSPersonImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class extends from {@link org.kuali.rice.kim.impl.identity.PersonLookupableImpl} to display
 * extra information at the person lookup in KS screens.
 *
 * @author Kuali Student Team
 */
public class KSPersonLookupableImpl extends PersonLookupableImpl{

    public static final class KSSearchParameters {
        public static final String NAME_SEARCH = "ksNameSearch";
    }

    @Override
    public List<?> performSearch(LookupForm form, Map<String, String> searchCriteria, boolean bounded) {

        String nameSearch = searchCriteria.get(KSSearchParameters.NAME_SEARCH);
        if (StringUtils.isNotBlank(nameSearch)) {
            searchCriteria.remove(KSSearchParameters.NAME_SEARCH);
            if (StringUtils.contains(nameSearch,",")){
                String lastName = StringUtils.substringBefore(nameSearch,",");
                String firstName = StringUtils.substringAfter(nameSearch,",");

                searchCriteria.put(KIMPropertyConstants.Person.LAST_NAME,lastName);
                searchCriteria.put(KIMPropertyConstants.Person.FIRST_NAME,firstName + "*");
            } else {
                searchCriteria.put(KIMPropertyConstants.Person.LAST_NAME,nameSearch);
            }
        }

        //Search result should be only PERSON, not any other types (SYSTEM etc.)
        searchCriteria.put(KIMPropertyConstants.Entity.ENTITY_TYPE_CODE, "PERSON");
        searchCriteria.put(KIMPropertyConstants.Person.ACTIVE,"Y");

        List<Person> persons = getPersonService().findPeople(searchCriteria, !bounded);

        List<KSPersonImpl> ksPersons = new ArrayList<KSPersonImpl>();
        for (Person person : persons) {
            KSPersonImpl ksPerson = new KSPersonImpl(person);
            if (person instanceof PersonImpl){
                ksPerson.setPersonAffiliationType(getAffiliationToDisplay((PersonImpl)person));
            }
            ksPersons.add(ksPerson);
        }

        return ksPersons;
    }

    /**
     * This default implementation returns the default active affiliation
     *
     * @param person
     * @return
     */
    protected String getAffiliationToDisplay(PersonImpl person){
        for (EntityAffiliationContract affiliation : ((PersonImpl) person).getAffiliations()){
            if (affiliation.isDefaultValue() && affiliation.isActive()){
                return affiliation.getAffiliationType().getName();
            }
        }
        return StringUtils.EMPTY;
    }

}
