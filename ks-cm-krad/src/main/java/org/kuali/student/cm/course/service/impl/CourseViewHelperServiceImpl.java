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
package org.kuali.student.cm.course.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.PredicateUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.KimConstants.EntityTypes;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.cm.course.form.CluInstructorInfoDisplay;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;

/**
 * This is the helper class for CourseView
 * 
 * @author Kuali Student Team
 */
public class CourseViewHelperServiceImpl extends ViewHelperServiceImpl {

	private static final long serialVersionUID = 1338662637708570500L;

	private IdentityService identityService;

	private List<Person> findPeople(String principalName) {
		List<Person> people = new ArrayList<Person>();

		String searchString = "";
		if (principalName != null) {
			if (principalName.indexOf("%") == -1
					&& principalName.indexOf("*") == -1) {
				searchString = "*" + principalName + "*";
			}
		}

		QueryByCriteria.Builder queryBuilder = QueryByCriteria.Builder.create();
		
		Predicate staticPredicate = PredicateFactory.and(PredicateFactory
				.equalIgnoreCase("entityTypeContactInfos.active", "Y"),
				PredicateFactory.equalIgnoreCase("principals.active", "Y"),
				PredicateFactory.equalIgnoreCase("active", "Y"),
				PredicateFactory.equalIgnoreCase("names.defaultValue", "Y"),
				PredicateFactory.equalIgnoreCase("names.active", "Y"),
				PredicateFactory.or(PredicateFactory.equalIgnoreCase(
						"entityTypeContactInfos.entityTypeCode", "PERSON"),
						PredicateFactory.equalIgnoreCase(
								"entityTypeContactInfos.entityTypeCode",
								"SYSTEM")));

		Predicate namePredicate = PredicateFactory
				.or(PredicateFactory.like("principals.principalName",
						searchString), PredicateFactory.like("names.firstName",
						searchString), PredicateFactory.like(
						"names.middleName", searchString), PredicateFactory
						.like("names.lastName", searchString));
		
		queryBuilder.setPredicates(staticPredicate, namePredicate);

		EntityDefaultQueryResults results = getIdentityService()
				.findEntityDefaults(queryBuilder.build());
		List<EntityDefault> entities = results.getResults();
		if (entities != null) {
			for (EntityDefault entity : entities) {
				for (Principal principal : entity.getPrincipals()) {
					PersonImpl personImpl = new PersonImpl(principal, entity,
							EntityTypes.PERSON);
					people.add(personImpl);
				}
			}
		}

		Collections.sort(people, new Comparator<Person>() {

			@Override
			public int compare(Person person1, Person person2) {
				return person1.getName().compareToIgnoreCase(person2.getName());
			}
		});

		return people;
	}

	public List<CluInstructorInfoDisplay> retrieveInstructors(
			String instructorName) {
		List<CluInstructorInfoDisplay> instructors = new ArrayList<CluInstructorInfoDisplay>();

		List<Person> people = findPeople(instructorName);
		if (!people.isEmpty()) {
			for (Person person : people) {
				CluInstructorInfoDisplay instructorDisplay = new CluInstructorInfoDisplay();
				CluInstructorInfo cluInstructorInfo = new CluInstructorInfo();
				cluInstructorInfo.setId(person.getEntityId());
				cluInstructorInfo.setPersonId(person.getPrincipalId());
				instructorDisplay.setCluInstructorInfo(cluInstructorInfo);
				instructorDisplay.setDisplayName(person.getName() + " ("
						+ person.getPrincipalName() + ")");
				instructors.add(instructorDisplay);
			}
		}

		return instructors;
	}

	private IdentityService getIdentityService() {
		if (identityService == null) {
			identityService = KimApiServiceLocator.getIdentityService();
		}
		return identityService;
	}

}
