package org.kuali.student.core.personsearch.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SortDirection;


public final class QuickViewByGivenName extends PersonSearch implements SearchOperation {
    final private String NAME_PARAM = "person.queryParam.personGivenName";
    final private String AFFILIATION_PARAM = "person.queryParam.personAffiliation";
    final private String ID_RESULT = "person.resultColumn.PersonId";
    final private String NAME_RESULT = "person.resultColumn.GivenName";
    final private String KIM_PERSON_AFFILIATION_TYPE_CODE = "affiliationTypeCode";

    final private String KIM_PRINCIPALS_PRINCIPALNAME = "principals.principalName";


    private List<Person> findPersons(final IdentityService identityService, final SearchRequest searchRequest) {
        String nameSearch = null;
        String affilSearch = null;
        for (SearchParam param : searchRequest.getParams()) {
            String value = (String) param.getValue();
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
                } else if (AFFILIATION_PARAM.equals(param.getKey())) {
                    if (affilSearch != null) {
                        affilSearch += "|" + value;
                    } else {
                        affilSearch = value;
                    }
                }
            }
        }
        final List<Map<String, String>> searches = new ArrayList<Map<String, String>>();
        if (nameSearch != null) {
        	Map<String, String> criteria1 = new HashMap<String, String>();
        	criteria1.put(KIM_PRINCIPALS_PRINCIPALNAME, nameSearch);
	        searches.add(criteria1);
//            Map<String, String> criteria1 = new HashMap<String, String>();
//            criteria1.put(KIM_PERSON_FIRST_NAME, nameSearch);
//            searches.add(criteria1);
//
//            Map<String, String> criteria2 = new HashMap<String, String>();
//            criteria2.put(KIM_PERSON_MIDDLE_NAME, nameSearch);
//            searches.add(criteria2);
//
//            Map<String, String> criteria3 = new HashMap<String, String>();
//            criteria3.put(KIM_PERSON_LAST_NAME, nameSearch);
//            searches.add(criteria3);
        }

        if (affilSearch != null) {
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

        final List<Person> persons = new ArrayList<Person>();
        for (Map<String, String> criteria : searches) {
            criteria.putAll(PersonSearchServiceImpl.PERSON_CRITERIA);
            final List<? extends Person> hits = findPeopleInternal(identityService, criteria, false);
            nextPerson: for (Person newPerson : hits) {
                for (Person person : persons) {
                    if (person.getPrincipalId().equals(newPerson.getPrincipalId())) {
                        break nextPerson;
                    }
                }
                persons.add(newPerson);
            }
        }
        return persons;
    }

    @Override
    public SearchResult search(final IdentityService identityService, final SearchRequest searchRequest) {
        final SearchResult result = new SearchResult();

        List<Person> persons = findPersons(identityService, searchRequest);
        if (searchRequest.getSortDirection() != null) {
            final int direction = (searchRequest.getSortDirection().equals(SortDirection.ASC) ? 1 : -1);
            Collections.sort(persons, new Comparator<Person>() {

                @Override
                public int compare(Person o1, Person o2) {
                    if (NAME_RESULT.equals(searchRequest.getSortColumn())) {
                        return o1.getName().compareToIgnoreCase(o2.getName()) * direction;
                    } else if (ID_RESULT.equals(searchRequest.getSortColumn())) {
                        return o1.getPrincipalId().compareToIgnoreCase(o2.getPrincipalId()) * direction;
                    } else {
                        // TODO Recognize alternate sort columns
                        return 0;
                    }
                }
            });
        }

        for (Person person : persons) {
            final SearchResultRow resultRow = new SearchResultRow();
            resultRow.setCells(new ArrayList<SearchResultCell>(2));
            final SearchResultCell idCell = new SearchResultCell();
            idCell.setKey(ID_RESULT);
            idCell.setValue(person.getPrincipalId());
            resultRow.getCells().add(idCell);
            final SearchResultCell nameCell = new SearchResultCell();
            nameCell.setKey(NAME_RESULT);
            nameCell.setValue(person.getName());
            resultRow.getCells().add(nameCell);
            result.getRows().add(resultRow);
        }
        result.setStartAt(1); // TODO fix this
        result.setTotalResults(result.getRows().size()); // TODO fix this
        return result;
    }

    @Override
    public List<Result> searchForResults(IdentityService identityService, String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        final SearchRequest request = new SearchRequest();
        request.setSearchKey(searchTypeKey);
        if (queryParamValues != null) {
            for (QueryParamValue param : queryParamValues) {
                final SearchParam newParam = new SearchParam();
                newParam.setKey(param.getKey());
                newParam.setValue((String) param.getValue());
                request.getParams().add(newParam);
            }
        }
        final List<Result> results = new ArrayList<Result>();

        for (Person principal : findPersons(identityService, request)) {
            final Result result = new Result();
            final ResultCell idCell = new ResultCell();
            idCell.setKey(ID_RESULT);
            idCell.setValue(principal.getPrincipalId());
            result.getResultCells().add(idCell);
            final ResultCell nameCell = new ResultCell();
            nameCell.setKey(NAME_RESULT);
            nameCell.setValue(principal.getName());
            result.getResultCells().add(nameCell);
            results.add(result);
        }

        return results;
    }
}
