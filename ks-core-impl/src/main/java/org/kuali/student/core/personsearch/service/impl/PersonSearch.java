/*
 * Copyright 2010he Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.personsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityEntityTypeDefaultInfo;
import org.kuali.rice.kim.service.IdentityService;

/**
 * Utility methods for dealing with Person searches
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class PersonSearch {

    @SuppressWarnings("unchecked")
    protected List<Person> findPeopleInternal(IdentityService identityService, Map<String, String> criteria, boolean unbounded) {

        List<Person> people = new ArrayList<Person>();

        List<? extends KimEntityDefaultInfo> entities = identityService.lookupEntityDefaultInfo(criteria, unbounded);
        if (entities != null) {
            for (KimEntityDefaultInfo e : entities) {
                // get to get all principals for the entity as well
                for (KimPrincipal p : e.getPrincipals()) {
                    Person person = convertEntityToPerson(e, p);
                    if (person != null) {
                        people.add(person);
                    }
                }
            }
        }

//        if (entities instanceof CollectionIncomplete) {
//            return new CollectionIncomplete(people, ((CollectionIncomplete) entities).getActualSizeIfTruncated());
//        }
        return people;
    }

    protected Person convertEntityToPerson(KimEntityDefaultInfo entity, KimPrincipal principal) {
        try {
            // get the EntityEntityType for the EntityType corresponding to a Person
           KimEntityEntityTypeDefaultInfo entType = entity.getEntityType(PersonSearchServiceImpl.PERSON_ENTITY_TYPE);
            // if no "person" entity type present for the given principal, skip to the next type in the list
            if (entType == null) {
                return null;
            }
            // attach the principal and entity objects
            // PersonImpl has logic to pull the needed elements from the KimEntity-related classes
            return new KsPerson(entity, principal);

        } catch (Exception ex) {
            // allow runtime exceptions to pass through
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException("Problem building person object", ex);
            }
        }
    }


}
