/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r1.core.personsearch.service.impl;

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchTypeInfo;

import java.util.*;

public final class QuickViewByGivenName extends PersonSearch implements SearchOperation {
    public static final String SEARCH_TYPE = "person.search.personQuickViewByGivenName";
    public static final String CRITERIA_TYPE = "person.search.personByGivenName";
    public static final String RESULT_TYPE = "person.search.personQuickView";
    final static public String NAME_PARAM = "person.queryParam.personGivenName";
    final static public String ID_PARAM = "person.queryParam.personId";
    final static public String PRINCIPAL_NAME_PARAM = "person.queryParam.personPrincipalName";
    final static public String AFFILIATION_PARAM = "person.queryParam.personAffiliation";
    final static public String EXCLUDED_USER_ID = "person.queryParam.excludedUserId";

    final static public String PERSON_ID_RESULT = "person.resultColumn.PersonId";
    final static public String ENTITY_ID_RESULT = "person.resultColumn.EntityId";
    final static public String DISPLAY_NAME_RESULT = "person.resultColumn.DisplayName";// Smith, John (jsmith)
    final static public String GIVEN_NAME_RESULT = "person.resultColumn.GivenName";// Smith, John
    final static public String PRINCIPAL_NAME_RESULT = "person.resultColumn.PrincipalName";

    final static private String KIM_PERSON_AFFILIATION_TYPE_CODE = "affiliationTypeCode";

    final static private String KIM_PRINCIPALS_PRINCIPALNAME = "principals.principalName";
    final static private String KIM_PRINCIPALS_PRINCIPALID = "principals.principalId";
    final static private String KIM_PERSON_FIRST_NAME = "names.firstName";
    final static private String KIM_PERSON_MIDDLE_NAME = "names.middleName";
    final static private String KIM_PERSON_LAST_NAME = "names.lastName";
    
    
    private List<Person> findPersons(final IdentityService identityService, final SearchRequestInfo searchRequest) {
        String nameSearch = null;
        String principalNameSearch = null;
        String affilSearch = null;
        String idSearch = null;
        String excludedUserId = null;
        for (SearchParamInfo param : searchRequest.getParams()) {
            String value = (String) param.getValues().get(0);
            if (!value.isEmpty()) {
                if (value.indexOf("%") == -1 && value.indexOf("*") == -1) {
                    value = "*" + value + "*";
                }
                if (NAME_PARAM.equals(param.getKey())) {
                    if (nameSearch != null) {
                        nameSearch += "|" + value;
                    } else {
                        nameSearch = value;
                    }
                } else if (PRINCIPAL_NAME_PARAM.equals(param.getKey())) {
                    if (principalNameSearch != null) {
                    	principalNameSearch += "|" + value;
                    } else {
                    	principalNameSearch = value;
                    }
                } else if (AFFILIATION_PARAM.equals(param.getKey())) {
                    if (affilSearch != null) {
                        affilSearch += "|" + value;
                    } else {
                        affilSearch = value;
                    }
                } else if (ID_PARAM.equals(param.getKey())) {
                    if (idSearch != null) {
                        idSearch += "|" + param.getValues().get(0);
                    } else {
                        idSearch = param.getValues().get(0).toString();
                    }
                } else if (EXCLUDED_USER_ID.equals(param.getKey())) {
                    excludedUserId = (String) param.getValues().get(0);
                }
            }
        }
        final List<Map<String, String>> searches = new ArrayList<Map<String, String>>();
        if (idSearch != null) {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put(KIM_PRINCIPALS_PRINCIPALID, idSearch);
            searches.add(criteria);
        } else if (principalNameSearch != null) {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put(KIM_PRINCIPALS_PRINCIPALNAME, principalNameSearch);
            searches.add(criteria);
        } else if (nameSearch != null) {
            Map<String, String> principalNameCriteria = new HashMap<String, String>();
            principalNameCriteria.put(KIM_PRINCIPALS_PRINCIPALNAME, nameSearch);
            searches.add(principalNameCriteria);

            Map<String, String> firstNameCriteria = new HashMap<String, String>();
            firstNameCriteria.put(KIM_PERSON_FIRST_NAME, nameSearch);
            searches.add(firstNameCriteria);

            Map<String, String> middleNameCriteria = new HashMap<String, String>();
            middleNameCriteria.put(KIM_PERSON_MIDDLE_NAME, nameSearch);
            searches.add(middleNameCriteria);

            Map<String, String> lastNameCriteria = new HashMap<String, String>();
            lastNameCriteria.put(KIM_PERSON_LAST_NAME, nameSearch);
            searches.add(lastNameCriteria);
        }

        if (affilSearch != null) {// TODO what is this search for?
            if (searches.isEmpty()) {
                searches.add(new HashMap<String, String>());
            }
            for (Map<String, String> search : searches) {
                search.put(KIM_PERSON_AFFILIATION_TYPE_CODE, affilSearch);
            }
        }

        if (searches.isEmpty()) {
            searches.add(new HashMap<String, String>());
        }

        final Map<String, Person> persons = new HashMap<String, Person>();
        for (Map<String, String> criteria : searches) {
            criteria.putAll(PersonSearchServiceImpl.PERSON_CRITERIA);
            final List<? extends Person> foundPeople = findPeopleInternal(identityService, criteria, false);
            for (Person person : foundPeople) {
                // Ignore the current user.
                if (excludedUserId != null && excludedUserId.equals(person.getPrincipalId())) {
                    continue;
                }
                persons.put(person.getEntityId(), person);
            }
        }
        return new ArrayList<Person>(persons.values());
    }

    @Override
    public SearchResultInfo search(final IdentityService identityService, final SearchRequestInfo searchRequest) {
        final SearchResultInfo result = new SearchResultInfo();
        searchRequest.setSortDirection(SortDirection.ASC);
        
        List<Person> persons = findPersons(identityService, searchRequest);
        // TODO finish sorting
        if (searchRequest.getSortDirection() != null) {
            final int direction = (searchRequest.getSortDirection().equals(SortDirection.ASC) ? 1 : -1);
            Collections.sort(persons, new Comparator<Person>() {

                @Override
                public int compare(Person o1, Person o2) {
                    if (DISPLAY_NAME_RESULT.equals(searchRequest.getSortColumn())) {
                        return o1.getName().compareToIgnoreCase(o2.getName()) * direction;
                    } else if (ENTITY_ID_RESULT.equals(searchRequest.getSortColumn())) {
                        return o1.getPrincipalId().compareToIgnoreCase(o2.getPrincipalId()) * direction;
                    } else {
                        // TODO Recognize alternate sort columns
                        return 0;
                    }
                }
            });
        }

        //Implement pagination.
        int startAt = (null != searchRequest && null != searchRequest.getStartAt()) ? searchRequest.getStartAt() : 0;
        int maxResult = (null != searchRequest && null != searchRequest.getMaxResults()) ? startAt
                + searchRequest.getMaxResults() : persons.size();
        for (int i = startAt; (i < persons.size() && i < maxResult); i++) {
            Person person = persons.get(i);
            final SearchResultRowInfo resultRow = new SearchResultRowInfo();
            resultRow.setCells(new ArrayList<SearchResultCellInfo>());

            SearchResultCellInfo cell = new SearchResultCellInfo();
            cell.setKey(ENTITY_ID_RESULT);
            cell.setValue(person.getEntityId());
            resultRow.getCells().add(cell);

            cell = new SearchResultCellInfo();
            cell.setKey(PERSON_ID_RESULT);
            cell.setValue(person.getPrincipalId());
            resultRow.getCells().add(cell);

            cell = new SearchResultCellInfo();
            cell.setKey(PRINCIPAL_NAME_RESULT);
            cell.setValue(person.getPrincipalName());
            resultRow.getCells().add(cell);

            cell = new SearchResultCellInfo();
            cell.setKey(GIVEN_NAME_RESULT);
            cell.setValue(person.getName());
            resultRow.getCells().add(cell);

            cell = new SearchResultCellInfo();
            cell.setKey(DISPLAY_NAME_RESULT);
            cell.setValue(person.getName() + " (" + person.getPrincipalName() + ")");
            resultRow.getCells().add(cell);

            result.getRows().add(resultRow);

        }

        result.setStartAt(searchRequest.getStartAt());
        if (searchRequest.getNeededTotalResults()) {
            result.setTotalResults(persons.size());
        } else {
            result.setTotalResults(result.getRows().size());
        }

        return result;
    }

    @Override
    public SearchTypeInfo getType() {
        return new QuickViewByGivenNameSearchTypeCreator().get();
    }

}
