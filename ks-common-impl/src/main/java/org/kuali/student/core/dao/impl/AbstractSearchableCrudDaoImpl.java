/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.search.dto.QueryParamInfo;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.dto.SortDirection;

public class AbstractSearchableCrudDaoImpl extends AbstractCrudDaoImpl
		implements SearchableDao {
	final Logger LOG = Logger.getLogger(AbstractSearchableCrudDaoImpl.class);
    private static SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");

	@Override
	public SearchResult search(SearchRequest searchRequest,
			Map<String, String> queryMap, SearchTypeInfo searchTypeInfo) {
		String searchKey = searchRequest.getSearchKey();
		
		boolean isNative = false;
		
		//retrieve the SELECT statement from search type definition
		String queryString = queryMap.get(searchKey);
		String optionalQueryString = "";
		if(null==queryString){
			LOG.error("No SQL query was found for searchKey:"+searchKey);
		}
		if(queryString.toUpperCase().startsWith("NATIVE:")){
			queryString = queryString.substring("NATIVE:".length());
			isNative = true;
		}
		
		//add in optional
		List<SearchParam> searchParamsTemp = new ArrayList<SearchParam>(searchRequest.getParams());
		for(SearchParam searchParam : searchParamsTemp){
			for(QueryParamInfo queryParam:searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams()){
				if(queryParam.isOptional()&&queryParam.getKey().equals(searchParam.getKey())){
					if(!optionalQueryString.isEmpty()){
						optionalQueryString += " AND ";
					}
					
					//if optional query parameter has only a column name then create proper search expression
					String condition = queryMap.get(searchParam.getKey());
					if (condition.trim().contains(":")) {
						optionalQueryString += queryMap.get(searchParam.getKey());
					} else {
						//comparison should be case insensitive and include wild card such that we match beginning of a text 
						//and each word within text
						//FIXME SQL injection can occur here - or NOT if we need to assemble SQL to cover various ways one can compare criteria to a text
						optionalQueryString += 
							"(LOWER(" + queryMap.get(searchParam.getKey()) + ") LIKE LOWER('" + searchParam.getValue() + "') || '%' OR " +
							"LOWER(" + queryMap.get(searchParam.getKey()) + ") LIKE '% ' || LOWER('" + searchParam.getValue() + "') || '%')"; 
						searchRequest.getParams().remove(searchParam);
					}
				}
			}
		}
		
		//Add in the where clause or And clause if needed for the optional criteria
		if(!optionalQueryString.isEmpty()){
			if(!queryString.toUpperCase().contains(" WHERE ")){
				queryString += " WHERE ";
			}
			else {
				queryString += " AND ";
			}
		}
		
		//Do ordering
		String orderByClause = "";		
		if(!queryString.toUpperCase().contains("ORDER BY")&&searchRequest.getSortColumn()!=null){
			//make sure the sort column is a real result column
			int i = 0;
			
			//Get an array of the jpql results
			int selectIndex = queryString.toLowerCase().indexOf("select")+"select".length();
			int fromIndex = queryString.toLowerCase().indexOf("from");
			String[] jpqlResultColumns = queryString.substring(selectIndex, fromIndex).replaceAll("\\s", "").split(",");
			
			for(ResultColumnInfo results : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()){
				if(results.getKey().equals(searchRequest.getSortColumn())){
					orderByClause = " ORDER BY "+jpqlResultColumns[i]+" ";
					if(searchRequest.getSortDirection()!=null && searchRequest.getSortDirection()==SortDirection.DESC){
						orderByClause += "DESC ";
					}else{
						orderByClause += "ASC ";
					}
				}
				i++;
			}
		}
		
		//Create the query
		String finalQueryString = queryString + optionalQueryString + orderByClause;
		
		//remove special characters and extra spaces
		//finalQueryString = finalQueryString.replaceAll("[\n\r\t]", " ");
		//finalQueryString = finalQueryString.replaceAll("\\s+", " ");
		
		Query query;
		if(isNative){
			LOG.info("Native Query:"+finalQueryString);
			query = em.createNativeQuery(finalQueryString);
		}else{
			LOG.info("JPQL Query:"+finalQueryString);
			query = em.createQuery(finalQueryString);
		}
		
		//Set the pagination information (eg. only return 25 rows starting at row 100)
		if(searchRequest.getStartAt()!=null){
			query.setFirstResult(searchRequest.getStartAt().intValue());
		}
		if(searchRequest.getMaxResults()!=null){
			query.setMaxResults(searchRequest.getMaxResults().intValue());
		}
		
		//replace all the "." notation with "_" since the "."s in the ids of the queries will cause problems with the jpql  
		if(searchRequest.getParams()!=null){
			for (SearchParam searchParam : searchRequest.getParams()) {
			    List<QueryParamInfo> queryParams = searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams();
			    String paramDataType;
			    Object queryParamValue = null;
			    paramDataType = null;
			    if (queryParams != null) {
			        for (QueryParamInfo queryParam : queryParams) {
			            if (queryParam.getKey() != null && queryParam.getKey().equals(searchParam.getKey())) {
			                paramDataType = queryParam.getFieldDescriptor().getDataType();
			            }
			        }
			    }
			    if ("date".equals(paramDataType) && searchParam.getValue() instanceof String) {
			        try {
                        queryParamValue = df.parse((String)searchParam.getValue());
                    } catch (ParseException e) {
                        throw new RuntimeException("Failed to parse date value " + searchParam.getValue());
                    }
			    } else {
			        queryParamValue = searchParam.getValue();
			    }
			    query.setParameter(searchParam.getKey().replace(".", "_"), queryParamValue);
			}
		}

		// Turn into results
		List<SearchResultRow> results = convertToResults(query.getResultList(),searchTypeInfo);

		SearchResult searchResult = new SearchResult();
		searchResult.setRows(results);
		searchResult.setSortColumn(searchRequest.getSortColumn());
		searchResult.setSortDirection(searchRequest.getSortDirection());
		searchResult.setStartAt(searchRequest.getStartAt());
		if(searchRequest.getNeededTotalResults()!=null && searchRequest.getNeededTotalResults()){
			//Get count of total rows if needed
			String regex = "^[Ss][Ee][Ll][Ee][Cc][Tt]\\s*([^,\\s]+).*?[Ff][Rr][Oo][Mm]";
			String replacement = "SELECT COUNT($1) FROM";
			String countQueryString = (queryString + optionalQueryString).replaceAll(regex, replacement);
			System.out.println("Executing query: "+countQueryString);
			Query countQuery;
			if(isNative){
				countQuery = em.createNativeQuery(countQueryString);
			}else{
				countQuery = em.createQuery(countQueryString);
			}
			if(searchRequest.getParams()!=null){
				for (SearchParam searchParam : searchRequest.getParams()) {
					countQuery.setParameter(searchParam.getKey().replace(".", "_"), searchParam
							.getValue());
				}
			}
			Long totalResults = (Long) countQuery.getSingleResult();
			searchResult.setTotalResults(totalResults.intValue());
		}

		return searchResult;
	}

	private List<SearchResultRow> convertToResults(List<?> queryResults,
			SearchTypeInfo searchTypeInfo) {
		List<SearchResultRow> results = new ArrayList<SearchResultRow>();

		if(queryResults!=null){
			//Copy the query results to a Result object
			for(Object queryResult:queryResults){
				SearchResultRow result = new SearchResultRow();
				int i=0;
				for (ResultColumnInfo resultColumn : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()) {
			
					SearchResultCell resultCell = new SearchResultCell();
					resultCell.setKey(resultColumn.getKey());
					
					try {
						Object queryResultCell = null;
						if(queryResult.getClass().isArray()){
							queryResultCell = ((Object[])queryResult)[i];
							
						}else{
							queryResultCell = queryResult;
						}
						
						if (queryResultCell != null) {
							resultCell.setValue(queryResultCell.toString());							
						}
					
					} catch (Exception e) {
						throw new RuntimeException("Error copying results from " + queryResult.toString(),e);
					}
					
					result.getCells().add(resultCell);
					i++;
				}
				results.add(result);
			}
		}
		return results;
	}

}
