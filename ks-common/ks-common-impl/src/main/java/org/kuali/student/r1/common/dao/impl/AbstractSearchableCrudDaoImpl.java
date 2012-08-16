/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.common.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dao.SearchableDao;
import org.kuali.student.r1.common.search.dto.QueryParamInfo;
import org.kuali.student.r1.common.search.dto.ResultColumnInfo;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.dto.SortDirection;

public class AbstractSearchableCrudDaoImpl extends AbstractCrudDaoImpl
		implements SearchableDao {
	final Logger LOG = Logger.getLogger(AbstractSearchableCrudDaoImpl.class);
	
	private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
		}
	};

	@Override
	public SearchResult search(SearchRequest searchRequest,	Map<String, String> queryMap, SearchTypeInfo searchTypeInfo) {
		String searchKey = searchRequest.getSearchKey();
		
		boolean isNative = false;
		
		//retrieve the SELECT statement from search type definition
		String queryString = queryMap.get(searchKey);
		String optionalQueryString = "";
		if(null == queryString){
			LOG.error("No SQL query was found for searchKey:"+searchKey);
		}
		
		if(queryString.toUpperCase().startsWith("NATIVE:")){
			queryString = queryString.substring("NATIVE:".length());
			isNative = true;
		}
		
		//add in optional
		List<SearchParam> searchParamsTemp = new ArrayList<SearchParam>(searchRequest.getParams());
		// internalQueryParms is used only internally to know which parameters have to be set in the query
		List<SearchParam> internalQueryParms = new ArrayList<SearchParam>(searchRequest.getParams());
		for(SearchParam searchParam : searchParamsTemp){
			for(QueryParamInfo queryParam:searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams()){
				// check to see if optional param has any values set.
				if(queryParam.isOptional()&&queryParam.getKey().equals(searchParam.getKey())&&searchParam.getValue()!=null){
					if(!optionalQueryString.isEmpty()){
						optionalQueryString += " AND ";
					}
					
					//if optional query parameter has only a column name then create proper search expression
					String condition = queryMap.get(searchParam.getKey());
					if(condition==null){
						throw new RuntimeException("Optional Param "+searchParam.getKey()+" must have a queryMap definition");
					}
					if (condition.trim().startsWith("!!")) {
					    String substitutionType = condition.trim().substring("!!".length());
					    // to detect queryMap value in the form of !!____ ___
					    if (condition.contains(" ")) {
					        substitutionType = condition.substring("!!".length(), condition.indexOf(" "));
					    }
					    
					    if (substitutionType != null && substitutionType.equals("NUMBER_RANGE")) {
					        String realCondition = condition.substring("!!".length() + substitutionType.length()).trim();
					        String queryValue = (String)searchParam.getValue();
					        // if the query value is of the form n1 - n2
					        if (queryValue != null && queryValue.trim().contains("-")) {
					            StringTokenizer strTokenizer = new StringTokenizer(queryValue.trim(),"-");
					            if (strTokenizer.hasMoreElements()) {
					                String strNum1 = strTokenizer.nextToken().trim();
					                String strNum2 = strTokenizer.nextToken().trim();
					                optionalQueryString += 
					                    realCondition +
					                    " BETWEEN " + "'" + strNum1 + "'" + " AND " + "'" + strNum2 + "'";
					                internalQueryParms.remove(searchParam);
					            }
					        } else {
					            // the value is just one number
					            optionalQueryString += realCondition + " = '" + queryValue + "'";
					            internalQueryParms.remove(searchParam);
					        }
					    }
					} else if (condition.trim().contains(":")) {
					    //this parameter is not entered by end user but rather it is set with a default context value	
					    String dataType = queryParam.getFieldDescriptor().getDataType();
					    if ((dataType != null) && "boolean".equals(dataType)) {
					        optionalQueryString += queryMap.get(searchParam.getKey()).replace(":" + searchParam.getKey().replace(".", "_"), searchParam.getValue().toString());
					        internalQueryParms.remove(searchParam);
					    } else {					    
					        optionalQueryString += queryMap.get(searchParam.getKey());
					    }						
					} else {
						//comparison should be case insensitive and should include wild card such that we match beginning of a text 
						//and each word within text
						//FIXME SQL injection can occur here - or NOT if we need to assemble SQL to cover various ways one can compare criteria to a text
						optionalQueryString += 
                                "(LOWER(" + queryMap.get(searchParam.getKey()) + ") LIKE LOWER(:"
                                        + searchParam.getKey().replace(".", "_") + ") || '%' OR " +
                                        "LOWER(" + queryMap.get(searchParam.getKey()) + ") LIKE '% ' || LOWER(:"
                                        + searchParam.getKey().replace(".", "_") + ") || '%')";
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
			int fromIndex = queryString.toLowerCase().indexOf(" from ");
			
			if (selectIndex >= 0 && fromIndex > selectIndex){
				String[] jpqlResultColumns = queryString.substring(selectIndex, fromIndex).replaceAll("\\s", "").split(",");
				for(ResultColumnInfo results : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()){
					if(results.getKey().equals(searchRequest.getSortColumn())){
                        if(results.getDataType()!=null && "string".equals(results.getDataType().toLowerCase())){
                        	orderByClause = " ORDER BY LOWER(" + jpqlResultColumns[i] + ") ";
                        }else{
                        	//Don't sort dates or numbers in alphabetic order or weirdness happens
                        	orderByClause = " ORDER BY " + jpqlResultColumns[i] + " ";
                        }
						if(searchRequest.getSortDirection()!=null && searchRequest.getSortDirection()==SortDirection.DESC){
							orderByClause += "DESC ";
						}else{
							orderByClause += "ASC ";
						}
					}
					i++;
				}
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
		for (SearchParam searchParam : internalQueryParms) {
			// check to see if optional param has any values set.
			if (searchParam.getValue() != null) {
			    List<QueryParamInfo> queryParams = searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams();
			    String paramDataType = null;
			    if (queryParams != null) {
			        for (QueryParamInfo queryParam : queryParams) {
			            if (queryParam.getKey() != null && queryParam.getKey().equals(searchParam.getKey())) {
			                paramDataType = queryParam.getFieldDescriptor().getDataType();
			            }
			        }
			    }
			    
                Object queryParamValue = null;
			    if ("date".equals(paramDataType) && searchParam.getValue() instanceof String) {
			        try {
                        queryParamValue = df.get().parse((String)searchParam.getValue());
                    } catch (ParseException e) {
                        throw new RuntimeException("Failed to parse date value " + searchParam.getValue(),e);
                    }
			    } if ("long".equals(paramDataType)){
			    	if(searchParam.getValue() instanceof String) {
			            try{
				        	queryParamValue = Long.valueOf((String)searchParam.getValue());
		                } catch (NumberFormatException e) {
		                    throw new RuntimeException("Failed to parse date value " + searchParam.getValue(),e);
		                }
			    	}else if(searchParam.getValue() instanceof Collection){
			    		try{
			    			List<Long> longList = new ArrayList<Long>();
			    			if(searchParam.getValue()!=null){
			    				for(String value:(Collection<String>)searchParam.getValue()){
			    					longList.add(Long.parseLong(value));
			    				}
			    			}
				        	queryParamValue = longList;
		                } catch (NumberFormatException e) {
		                    throw new RuntimeException("Failed to parse date value " + searchParam.getValue(),e);
		                }
			    	}
			    } else {
			        queryParamValue = searchParam.getValue();
			    }
			    //Needed to get around Hibernate not supporting IN(:var) where var is null or an empty collection
			    if((queryParamValue==null||queryParamValue instanceof Collection && ((Collection<?>)queryParamValue).isEmpty())&&"list".equals(paramDataType)){
			    	queryParamValue = "";
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
            String regex = "^\\s*[Ss][Ee][Ll][Ee][Cc][Tt]\\s+([^,\\s]+)(.|[\r\n])*?\\s+[Ff][Rr][Oo][Mm]\\s+";
            String replacement = "SELECT COUNT(DISTINCT $1) FROM ";
            queryString = queryString.replaceAll("([Dd][Ii][Ss][Tt][Ii][Nn][Cc][Tt])", "");
			String countQueryString = (queryString + optionalQueryString).replaceFirst(regex, replacement);

			LOG.info("Executing query: "+countQueryString);
			Query countQuery;
			if(isNative){
				countQuery = em.createNativeQuery(countQueryString);
			}else{
				countQuery = em.createQuery(countQueryString);
			}
			for (SearchParam searchParam : internalQueryParms) {
				countQuery.setParameter(searchParam.getKey().replace(".", "_"), searchParam.getValue());
			}
            Integer totalRecords = 0;
            Object resultObject = countQuery.getSingleResult();
            if (resultObject instanceof BigDecimal) {
                totalRecords = ((BigDecimal) resultObject).intValue();
            } else if (resultObject instanceof Long) {
                totalRecords = ((Long) resultObject).intValue();
            }
            searchResult.setTotalResults(totalRecords);
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
