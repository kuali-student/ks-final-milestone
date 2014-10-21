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

package org.kuali.student.r2.common.dao.impl;

import org.kuali.student.r2.core.search.dto.QueryParamInfo;
import org.kuali.student.r2.core.search.dto.ResultColumnInfo;
import org.kuali.student.r2.core.search.dto.SearchTypeInfo;
import org.kuali.student.r2.core.search.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchableCrudDaoImpl {
	private static final Logger LOG = LoggerFactory.getLogger(SearchableCrudDaoImpl.class);

    protected EntityManager em;

    public SearchableCrudDaoImpl(){
        super();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
	
	private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
		}
	};

	public SearchResultInfo search(SearchRequestInfo searchRequest,	Map<String, String> queryMap, SearchTypeInfo searchTypeInfo) {
		String searchKey = searchRequest.getSearchKey();
		
		boolean isNative = false;
		
		//retrieve the SELECT statement from search type definition
		String queryString = queryMap.get(searchKey);
		StringBuilder optionalQueryString = new StringBuilder();
		if(null == queryString){
			LOG.error("No SQL query was found for searchKey: {}", searchKey);
		}
		
		if(queryString.toUpperCase().startsWith("NATIVE:")){
			queryString = queryString.substring("NATIVE:".length());
			isNative = true;
		}
		
		//add in optional
		List<SearchParamInfo> searchParamsTemp = new ArrayList<SearchParamInfo>(searchRequest.getParams());
		// internalQueryParms is used only internally to know which parameters have to be set in the query
		List<SearchParamInfo> internalQueryParms = new ArrayList<SearchParamInfo>(searchRequest.getParams());
		for(SearchParamInfo searchParam : searchParamsTemp){
			for(QueryParamInfo queryParam:searchTypeInfo.getSearchCriteriaTypeInfo().getQueryParams()){
				// check to see if optional param has any values set.
				if(queryParam.isOptional()&&queryParam.getKey().equals(searchParam.getKey())
                        &&searchParam.getValues()!=null&&searchParam.getValues().size()>0&&searchParam.getValues().get(0)!=null){
					if (optionalQueryString.length() != 0) {
						optionalQueryString.append(" AND ");
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
					        String queryValue = (String)searchParam.getValues().get(0);
					        // if the query value is of the form n1 - n2
					        if (queryValue != null && queryValue.trim().contains("-")) {
					            StringTokenizer strTokenizer = new StringTokenizer(queryValue.trim(),"-");
					            if (strTokenizer.hasMoreElements()) {
					                String strNum1 = strTokenizer.nextToken().trim();
					                String strNum2 = strTokenizer.nextToken().trim();
					                optionalQueryString
                                            .append(realCondition)
                                            .append(" BETWEEN '").append(strNum1).append("' AND '").append(strNum2).append("'");
					                internalQueryParms.remove(searchParam);
					            }
					        } else {
					            // the value is just one number
					            optionalQueryString.append(realCondition).append(" = '").append(queryValue).append( "'");
					            internalQueryParms.remove(searchParam);
					        }
					    }
					} else if (condition.trim().contains(":")) {
					    //this parameter is not entered by end user but rather it is set with a default context value	
					    String dataType = queryParam.getFieldDescriptor().getDataType();
					    if ((dataType != null) && "boolean".equals(dataType)) {
					        optionalQueryString.append(queryMap.get(searchParam.getKey()).replace(":" + searchParam.getKey().replace(".", "_"), searchParam.getValues().get(0)));
					        internalQueryParms.remove(searchParam);
					    } else {					    
					        optionalQueryString.append(queryMap.get(searchParam.getKey()));
					    }						
					} else {
						//comparison should be case insensitive and should include wild card such that we match beginning of a text 
						//and each word within text
						//FIXME SQL injection can occur here - or NOT if we need to assemble SQL to cover various ways one can compare criteria to a text
						optionalQueryString
                                .append("(LOWER(").append(queryMap.get(searchParam.getKey())).append(") LIKE LOWER(:")
                                .append(searchParam.getKey().replace(".", "_")).append(") || '%' OR ")
                                .append("LOWER(").append(queryMap.get(searchParam.getKey())).append(") LIKE '% ' || LOWER(:")
                                .append(searchParam.getKey().replace(".", "_")).append(") || '%')");
					}
				}
			}
		}
		
		//Add in the where clause or And clause if needed for the optional criteria
		if (optionalQueryString.length() != 0) {
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
            String lowercaseQueryString = queryString.toLowerCase();
			int selectIndex = lowercaseQueryString.indexOf("select")+"select".length();
            int distinctIndex = lowercaseQueryString.indexOf("distinct")+"distinct".length();//Filter out the "distinct"
			int fromIndex = lowercaseQueryString.indexOf(" from ");
            if(selectIndex < distinctIndex && distinctIndex < fromIndex){
                selectIndex = distinctIndex;
            }
			
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
			LOG.info("Native Query: {}", finalQueryString);
			query = em.createNativeQuery(finalQueryString);
		}else{
			LOG.info("JPQL Query: {}", finalQueryString);
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
		for (SearchParamInfo searchParam : internalQueryParms) {
			// check to see if optional param has any values set.
			if (searchParam.getValues() != null &&
                    searchParam.getValues().size() > 0 &&
                    searchParam.getValues().get(0) != null) {
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
			    if ("date".equals(paramDataType)) {
			        try {
                        queryParamValue = df.get().parse((String)searchParam.getValues().get(0));
                    } catch (ParseException e) {
                        throw new RuntimeException("Failed to parse date value " + searchParam.getValues().get(0),e);
                    }
			    } else if ("long".equals(paramDataType)){
			    	try{
			    		List<Long> longList = new ArrayList<Long>();
			    		if(searchParam.getValues()!=null){
			    			for(String value:searchParam.getValues()){
			    				longList.add(Long.parseLong(value));
			    			}
			    		}
				      	queryParamValue = longList;
		            } catch (NumberFormatException e) {
		                throw new RuntimeException("Failed to parse long value " + searchParam.getValues(),e);
		            }

			    } else if ("int".equals(paramDataType)){
                    try{
                        List<Integer> intList = new ArrayList<Integer>();
                        if(searchParam.getValues()!=null){
                            for(String value:searchParam.getValues()){
                                intList.add(Integer.parseInt(value));
                            }
                        }
                        queryParamValue = intList;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Failed to parse int value " + searchParam.getValues(),e);
                    }

                }  else {
			        queryParamValue = searchParam.getValues();
			    }
			    //Needed to get around Hibernate not supporting IN(:var) where var is null or an empty collection
			    if((queryParamValue==null||queryParamValue instanceof Collection && ((Collection<?>)queryParamValue).isEmpty())&&"list".equals(paramDataType)){
			    	queryParamValue = "";
			    }
			    query.setParameter(searchParam.getKey().replace(".", "_"), queryParamValue);
			}
		}

		// Turn into results
		List<SearchResultRowInfo> results = convertToResults(query.getResultList(),searchTypeInfo);

		SearchResultInfo searchResult = new SearchResultInfo();
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

			LOG.info("Executing query: {}", countQueryString);
			Query countQuery;
			if(isNative){
				countQuery = em.createNativeQuery(countQueryString);
			}else{
				countQuery = em.createQuery(countQueryString);
			}
			for (SearchParamInfo searchParam : internalQueryParms) {
			    // There is a bug here that happens with you pass a list of states in for lu_queryParam_luOptionalState
			    // When you pass in ('Approved' , 'Active' , 'Retired') the code below only grabbed the first value (it was
			    // hard coded with get(0). Thus, it would only count 'Approved' courses.  However, at UMD, there aren't even
			    // approved courses in the KSLU_CLU table (only 'Active') so we were getting an empty list back.
			    // Printout of searchParam.getValues():  [Approved, Active, Retired, null, null, null, null, null, null, null]
			    // You can see broken code only grabs the first value in the array using searchParam.getValues().get(0).
			    // select * from kslu_clu where st  IN ( 'Approved','Active', 'Retired' )
			    // Fix was to pass in the entire list of values when getValues() > 1
			  
			    if ( searchParam.getValues().size() == 1){
			        countQuery.setParameter(searchParam.getKey().replace(".", "_"), searchParam.getValues().get(0));
			    }
			    else {
			        countQuery.setParameter(searchParam.getKey().replace(".", "_"), searchParam.getValues());  
			    } 		
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
	
	private List<SearchResultRowInfo> convertToResults(List<?> queryResults,
			SearchTypeInfo searchTypeInfo) {
		List<SearchResultRowInfo> results = new ArrayList<SearchResultRowInfo>();

		if(queryResults!=null){
			//Copy the query results to a Result object
			for(Object queryResult:queryResults){
				SearchResultRowInfo result = new SearchResultRowInfo();
				int i=0;
				for (ResultColumnInfo resultColumn : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()) {
			
					SearchResultCellInfo resultCell = new SearchResultCellInfo();
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
