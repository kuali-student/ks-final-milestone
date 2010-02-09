/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.core.ksperson.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.ksperson.service.KsPersonService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.newdto.SearchParam;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.search.newdto.SearchResultCell;
import org.kuali.student.core.search.newdto.SearchResultRow;
/**
 * Proxy service to the rice PersonService that adds primitive support for the search() and searchForResult()
 * search methods. Used by the GWT widgets
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.ksperson.service.KsPersonService", serviceName = "KsPersonService", portName = "KsPersonService", targetNamespace = "http://student.kuali.org/wsdl/ksperson")
public class KsPersonServiceImpl implements KsPersonService {
    protected static final Logger LOG = Logger.getLogger(KsPersonServiceImpl.class);
        
    private interface MySearch {
        public SearchResult search(SearchRequest searchRequest);

        public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

        public static Map<String, MySearch> searchOperations = new HashMap<String, MySearch>() {
            {
                put("person.search.personQuickViewByGivenName", new MySearch() {
                    final private String nameParam = "person.queryParam.personGivenName";
                    final private String affiliationParam = "person.queryParam.personAffiliation";
                    final private String idResult = "person.resultColumn.PersonId";
                    final private String nameResult = "person.resultColumn.GivenName";
                    final private String KIM_PERSON_FIRST_NAME = "firstName";
                    final private String KIM_PERSON_MIDDLE_NAME = "middleName";
                    final private String KIM_PERSON_LAST_NAME = "lastName";
                    final private String KIM_PERSON_AFFILIATION_TYPE_CODE = "affiliationTypeCode";

                    private List<Person> findPersons(final SearchRequest searchRequest) {
                        String nameSearch = null;
                        String affilSearch = null;
                        for (SearchParam param : searchRequest.getParams()) {
                            String value = (String) param.getValue();
                            if (!value.isEmpty()) {
                                if (value.indexOf("%") == -1 && value.indexOf("*") == -1) {
                                    value = "*" + value + "*";
                                }
                                if (nameParam.equals(param.getKey())) {
                                    if (nameSearch != null) {
                                        nameSearch += "&" + value;
                                    } else {
                                        nameSearch = value;
                                    }
                                } else if (affiliationParam.equals(param.getKey())) {
                                    if (affilSearch != null) {
                                        affilSearch += "&" + value;
                                    } else {
                                        affilSearch = value;
                                    }
                                }
                            }
                        }
                        final List<Map<String, String>> searches = new ArrayList<Map<String, String>>();
                        if (nameSearch != null) {
                            Map<String, String> criteria1 = new HashMap<String, String>();
                            criteria1.put(KIM_PERSON_FIRST_NAME, nameSearch);
                            searches.add(criteria1);

                            Map<String, String> criteria2 = new HashMap<String, String>();
                            criteria2.put(KIM_PERSON_MIDDLE_NAME, nameSearch);
                            searches.add(criteria2);

                            Map<String, String> criteria3 = new HashMap<String, String>();
                            criteria3.put(KIM_PERSON_LAST_NAME, nameSearch);
                            searches.add(criteria3);
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
                        
                        //TODO: Inject this instead
                        //final PersonService<?> ps = KIMServiceLocator.getPersonService();
                        final PersonService<?> ps = (PersonService<?>)GlobalResourceLoader.getService("personService");
                        
                        final List<Person> persons = new ArrayList<Person>();
                        for (Map<String, String> criteria : searches) {
                            final List<? extends Person> hits = ps.findPeople(criteria);
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
                    public SearchResult search(final SearchRequest searchRequest) {
                        final SearchResult result = new SearchResult();

                        for (Person person : findPersons(searchRequest)) {
                            final SearchResultRow resultRow = new SearchResultRow();
                            resultRow.setCells(new ArrayList<SearchResultCell>(2));
                            final SearchResultCell idCell = new SearchResultCell();
                            idCell.setKey(idResult);
                            idCell.setValue(person.getPrincipalId());
                            resultRow.getCells().add(idCell);
                            final SearchResultCell nameCell = new SearchResultCell();
                            nameCell.setKey(nameResult);
                            nameCell.setValue(person.getName());
                            resultRow.getCells().add(nameCell);
                            result.getRows().add(resultRow);
                        }
                        result.setStartAt(1);
                        result.setTotalResults(result.getRows().size());
                        return result;
                    }

                    @Override
                    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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

                        for (Person principal : findPersons(request)) {
                            final Result result = new Result();
                            final ResultCell idCell = new ResultCell();
                            idCell.setKey(idResult);
                            idCell.setValue(principal.getPrincipalId());
                            result.getResultCells().add(idCell);
                            final ResultCell nameCell = new ResultCell();
                            nameCell.setKey(nameResult);
                            nameCell.setValue(principal.getName());
                            result.getResultCells().add(nameCell);
                            results.add(result);
                        }

                        return results;
                    }

                });
            }
        };
    };

    public KsPersonServiceImpl() {

    }
    /**
     * Return the list of searches we recognize
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypes()
     */
    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        final List<SearchTypeInfo> searchTypes =  new ArrayList<SearchTypeInfo>(MySearch.searchOperations.size());
        for (String searchKey : MySearch.searchOperations.keySet()) {
            final SearchTypeInfo searchType = new SearchTypeInfo();
            searchType.setKey(searchKey);
            searchTypes.add(searchType);
        }
        return searchTypes;
    }


    @Override
    public SearchResult search(SearchRequest searchRequest) {
        final MySearch search = MySearch.searchOperations.get(searchRequest.getSearchKey());
        if (search != null) {
            return search.search(searchRequest);
        }
        return null;
    }


    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#searchForResults(java.lang.String, java.util.List)
     */
    @Override
    public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        final MySearch search = MySearch.searchOperations.get(searchTypeKey);
        if (search != null) {
            return search.searchForResults(searchTypeKey, queryParamValues);
        }
        return null;

    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
       throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultTypes()
     */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchType(java.lang.String)
     */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByResult(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
       throw new RuntimeException("Not implemented yet");
    }
}
