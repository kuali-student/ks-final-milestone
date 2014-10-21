/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfoDefault;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides conversion methods for OfferingInstructors and LprInfos
 *
 * @author Kuali Student Team
 */
public class OfferingInstructorTransformer {

    private static PersonService personService;
    private static IdentityService identityService;

    private static PersonImpl _convertEntityToPerson(EntityDefault entity, Principal principal) {
        try {
            // get the EntityEntityType for the EntityType corresponding to a Person

            EntityTypeContactInfoDefault entType = entity.getEntityType("PERSON");
            // if no "person" identity type present for the given principal, skip to the next type in the list
            if (entType == null) {
                return null;
            }
            // attach the principal and identity objects
            // PersonImpl has logic to pull the needed elements from the KimEntity-related classes
            return new PersonImpl(principal, entity, "PERSON");
        } catch (Exception ex) {
            throw new RuntimeException("Problem building person object", ex);
        }
    }

    public static List<OfferingInstructorInfo> lprs2Instructors(List<LprInfo> lprs) {
        List<OfferingInstructorInfo> results = new ArrayList<OfferingInstructorInfo>(lprs.size());

        for (LprInfo lpr : lprs) {
            OfferingInstructorInfo instructor = new OfferingInstructorInfo();
            instructor.setPersonId(lpr.getPersonId());
            if (lpr.getCommitmentPercent() != null) {
                instructor.setPercentageEffort(lpr.getCommitmentPercent().floatValue());
            }
            instructor.setId(lpr.getId());
            instructor.setTypeKey(lpr.getTypeKey());
            instructor.setStateKey(lpr.getStateKey());

            // Should be only one person found by person id
            List<Person> personList = getInstructorByPersonId(instructor.getPersonId());
            if (personList != null && !personList.isEmpty()) {
                int firstPerson = 0;
                instructor.setPersonName(personList.get(firstPerson).getName());
            }

            results.add(instructor);
        }

        return results;

    }

    /**
     * Transform a list of LprInfo into a list of OfferingInstructorInfo.
     * When retrieving the person name from KIM service, "in clause" search criteria is used to perform the bulking loading
     *
     * @param lprs List<LprInfo>
     * @return a list of OfferingInstructorInfo
     */
    public static List<OfferingInstructorInfo> lprs2InstructorsBulk(List<LprInfo> lprs) {
        List<OfferingInstructorInfo> results = new ArrayList<OfferingInstructorInfo>(lprs.size());

        if (lprs.isEmpty()){
            return results;
        }

        //Map with the key of principalId and value of List<Person>
        Map<String, List<Person>> lpr2PersonMap = new HashMap<String, List<Person>>(lprs.size());

        //Store all the person ids into a list
        List<String> personIds = new ArrayList<String>(lprs.size());
        for (LprInfo lpr : lprs) {
            personIds.add(lpr.getPersonId());
        }

        //Build the search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("id", personIds.toArray()),
                PredicateFactory.equalIgnoreCase("entityTypeContactInfos.active", "Y"),
                PredicateFactory.or(
                        PredicateFactory.equalIgnoreCase("entityTypeContactInfos.entityTypeCode", "PERSON"),
                        PredicateFactory.equalIgnoreCase("entityTypeContactInfos.entityTypeCode", "SYSTEM")));
        QueryByCriteria criteria = qbcBuilder.build();

        //retrieve all the default entities with the search criteria
        EntityDefaultQueryResults qr = getIdentityService().findEntityDefaults(criteria);
        List<Person> people = new ArrayList<Person>();
        for (EntityDefault e : qr.getResults()) {
            // get to get all principals for the identity as well
            for (Principal p : e.getPrincipals()) {
                people.add(_convertEntityToPerson(e, p));
            }
        }

        //construct the map of entityId to List<Person>
        for (Person person : people) {
            if (person != null) {
                List<Person> personList = lpr2PersonMap.get(person.getEntityId());
                if (personList == null) {
                    personList = new ArrayList<Person>();
                    lpr2PersonMap.put(person.getEntityId(), personList);
                }
                personList.add(person);
            }
        }

        //iterate the lpr list and transform them one by one into OfferingInstructorInfo
        //no service calls are made inside this loop
        int firstLprInfo = 0;
        for (LprInfo lpr : lprs) {
            OfferingInstructorInfo instructor = new OfferingInstructorInfo();
            instructor.setPersonId(lpr.getPersonId());
            if (lpr.getCommitmentPercent() != null) {
                instructor.setPercentageEffort(lpr.getCommitmentPercent().floatValue());
            }
            
            instructor.setId(lpr.getId());
            instructor.setTypeKey(lpr.getTypeKey());
            instructor.setStateKey(lpr.getStateKey());

            //retrieve the person name from lpr2PersonMap
            instructor.setPersonName(lpr2PersonMap.get(lpr.getPersonId()).get(firstLprInfo).getName());

            results.add(instructor);
        }

        return results;

    }

    public static List<Person> getInstructorByPersonId(String personId) {
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, personId);
        List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
        return lstPerson;
    }


    public static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }

        return identityService;
    }

    public static PersonService getPersonService() {
        if (personService == null) {
            personService = KimApiServiceLocator.getPersonService();
        }

        return personService;
    }

    /**
     * Transform a list of OfferingInstructorInfo into a list of LprInfo.
     *
     * @param luiInfo     the LuiInfo that the lprs are attached to
     * @param instructors List<OfferingInstructorInfo>
     * @return a list of LprInfo
     */
    public static List<LprInfo> instructors2Lprs(LuiInfo luiInfo, List<OfferingInstructorInfo> instructors) {

        List<LprInfo> results = new ArrayList<LprInfo>(instructors.size());

        for (OfferingInstructorInfo instructorInfo : instructors) {
            LprInfo lprInfo = new LprInfo();
            lprInfo.setId(instructorInfo.getId());

            Float cp = instructorInfo.getPercentageEffort();

            if (cp != null)
                lprInfo.setCommitmentPercent(new KualiDecimal(cp));
            else
                lprInfo.setCommitmentPercent(null);

            lprInfo.setLuiId(luiInfo.getId());
            lprInfo.setPersonId(instructorInfo.getPersonId());
            lprInfo.setEffectiveDate(new Date());
            lprInfo.setTypeKey(instructorInfo.getTypeKey());
            lprInfo.setStateKey(instructorInfo.getStateKey());

            results.add(lprInfo);
        }

        return results;
    }
}
