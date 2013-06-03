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
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
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
import org.kuali.student.cm.course.form.CourseJointInfoDisplay;
import org.kuali.student.cm.course.form.SubjectCodeDisplay;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 * This is the helper class for CourseView
 * 
 * @author Kuali Student Team
 */
public class CourseViewHelperServiceImpl extends ViewHelperServiceImpl {

	private static final long serialVersionUID = 1338662637708570500L;

	private IdentityService identityService;

	private SubjectCodeService subjectCodeService;

	private CluService cluService;

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

	public List<CluInstructorInfoDisplay> getInstructorsForSuggest(
			String instructorName) {
		List<CluInstructorInfoDisplay> instructors = new ArrayList<CluInstructorInfoDisplay>();

		List<Person> people = findPeople(instructorName);
		if (!people.isEmpty()) {
			for (Person person : people) {
				CluInstructorInfoDisplay instructorDisplay = new CluInstructorInfoDisplay();
				instructorDisplay.setId(person.getEntityId());
				instructorDisplay.setPersonId(person.getPrincipalId());
				instructorDisplay.setDisplayName(person.getName() + " ("
						+ person.getPrincipalName() + ")");
				instructors.add(instructorDisplay);
			}
		}

		return instructors;
	}
	
	public List<SubjectCodeDisplay> getSubjectCodesForSuggest(String subjectCode) {
        List<SubjectCodeDisplay> retrievedCodes = new ArrayList<SubjectCodeDisplay>();

		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey("subjectCode.queryParam.code");
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(subjectCode);
        codeParam.setValues(codeValues);
        
        queryParamValueList.add(codeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.subjectCodeGeneric");
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSubjectCodeService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                	if ("subjectCode.resultColumn.id".equals(cell.getKey())) {
                		id = cell.getValue();
                	}
                	if ("subjectCode.resultColumn.code".equals(cell.getKey())) {
                		code = cell.getValue();
                	}
                }
                retrievedCodes.add(new SubjectCodeDisplay(id, code));
            }
        } catch (Exception e) {
            //do nothing
        }

        return retrievedCodes;
    }
	
	public List<CourseJointInfoDisplay> getJointOfferingCourseNumbersForSuggest(String courseNumber) {
		List<CourseJointInfoDisplay> courseJoints = new ArrayList<CourseJointInfoDisplay>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
		
		SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey("lu.queryParam.luOptionalCode");
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseNumber);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey("lu.queryParam.luOptionalType");
        List<String> typeValues = new ArrayList<String>();
        typeValues.add("kuali.lu.type.CreditCourse");
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current.quick");
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                	if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                		id = cell.getValue();
                	}
                	if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                		code = cell.getValue();
                	}
                }
                CourseJointInfoDisplay courseJointDisplay = new CourseJointInfoDisplay();
                courseJointDisplay.setCourseId(id);
                courseJointDisplay.setCourseCode(code);
                String subjectArea = code.replaceAll("\\d", "");
                String numberSuffix = code.replaceAll("\\D", "");
                courseJointDisplay.setSubjectArea(subjectArea);
                courseJointDisplay.setCourseNumberSuffix(numberSuffix);
                courseJoints.add(courseJointDisplay);
            }
        } catch (Exception e) {
            //do nothing
        }
		
		return courseJoints;
	}
	
	private IdentityService getIdentityService() {
		if (identityService == null) {
			identityService = KimApiServiceLocator.getIdentityService();
		}
		return identityService;
	}
	
	private SubjectCodeService getSubjectCodeService() {
		if (subjectCodeService == null) {
			subjectCodeService = GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/subjectCode", SubjectCodeService.class.getSimpleName()));
		}
		return subjectCodeService;
	}	
	
	private CluService getCluService() {
		if (cluService == null) {
			cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
		}
		return cluService;
	}
	

}
