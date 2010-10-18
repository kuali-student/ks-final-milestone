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

package org.kuali.student.core.search.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.search.dto.CrossSearchTypeInfo;
import org.kuali.student.core.search.dto.JoinComparisonInfo;
import org.kuali.student.core.search.dto.JoinComparisonInfo.ComparisonType;
import org.kuali.student.core.search.dto.JoinCriteriaInfo;
import org.kuali.student.core.search.dto.JoinCriteriaInfo.JoinType;
import org.kuali.student.core.search.dto.JoinResultMappingInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SortDirection;
import org.kuali.student.core.search.dto.SubSearchInfo;
import org.kuali.student.core.search.dto.SubSearchParamMappingInfo;
import org.kuali.student.core.search.service.SearchDispatcher;

/**
 * This still needs a few things
 * 1 - no search meta(sort, pagination) is implemented
 * 2 - a way to do subselects should be implemented to reduce the processing and sheer size of the unions
 * (for example if searching for LO and the related CLU by the LO description, we need to match ALL CLUs 
 * with just the LOs that match, meaning if we had 1000 clus, and 10 LOs we would be comparing 10000 results)
 * 
 *
 */
/**
 * @author Daniel Epstein
 *
 */
public class CrossSearchManager {
	private SearchDispatcher searchDispatcher;

	public SearchResult doCrossSearch(SearchRequest searchRequest, CrossSearchTypeInfo crossSearchType) {
		SearchResult searchResult = new SearchResult();
		
		Map<String,SearchResult> subSearchResults = new HashMap<String,SearchResult>();
		
		//First perform all the subsearches
		for(SubSearchInfo subSearch:crossSearchType.getSubSearches()){
			//Map the parameters to the subsearch
			SearchRequest subSearchRequest = new SearchRequest();
			
			subSearchRequest.setSearchKey(subSearch.getSearchkey());
			subSearchRequest.setParams(new ArrayList<SearchParam>());
			
			//For each param mapping, map the paramvalue from the cross search to the sub search
			for(SubSearchParamMappingInfo paramMapping:subSearch.getSubSearchParamMappings()){
				for(SearchParam crossSearchParam:searchRequest.getParams()){
					if(paramMapping.getCrossSearchParam().equals(crossSearchParam.getKey())){
						SearchParam subSearchParam = new SearchParam();
						subSearchParam.setKey(paramMapping.getSubSearchParam());
						Object paramValue = crossSearchParam.getValue();
						if(paramValue instanceof String){
							subSearchParam.setValue((String)paramValue);
						}else if(paramValue instanceof List<?>){
							subSearchParam.setValue((List<String>)paramValue);
						}
						subSearchRequest.getParams().add(subSearchParam);
						break;
					}
				}
			}
			SearchResult subSearchResult = searchDispatcher.dispatchSearch(subSearchRequest);
			subSearchResults.put(subSearch.getKey(), subSearchResult);
		}
		
		//merge the subsearches together using the join rules
		if(crossSearchType.getJoinCriteria().getComparisons().isEmpty()){
			//If the root join has no criteria then do a simple union of rows
			for(Map.Entry<String,SearchResult> subSearchResult:subSearchResults.entrySet()){
				for(SearchResultRow row:subSearchResult.getValue().getRows()){
					SearchResultRow mappedResult = mapResultRow(subSearchResult.getKey(),row,crossSearchType);
					searchResult.getRows().add(mappedResult);
				}
			}
		}else{
			//merge the subsearches together using the join rules (this is in o^2 time which is bad)
			List <Map<String,SearchResultRow>> allPermutations = unionOfAllRows(subSearchResults);
	
			for(Map<String,SearchResultRow> permutation:allPermutations){
				if(meetsCriteria(permutation,crossSearchType,crossSearchType.getJoinCriteria())){
					SearchResultRow mappedResult = mapResultRow(permutation,crossSearchType);
					searchResult.getRows().add(mappedResult);
				}
			}
		}
		return metaFilter(searchResult,searchRequest);
	}
	
	
	
	
	/**
	 * @param searchResult
	 * @param searchRequest
	 * @return a sorted and paginated result
	 */
	private SearchResult metaFilter(SearchResult searchResult,
		SearchRequest searchRequest) {
		
		searchResult.setTotalResults(searchResult.getRows().size());
		final String sortColumn = searchRequest.getSortColumn();
		final SortDirection sortDirection = searchRequest.getSortDirection();
		
		//Sort if we need to
		if(sortColumn!=null){
			Collections.sort(searchResult.getRows(), new SearchResultRowComparator(sortColumn,sortDirection));
		}
		
		
		
		//Paginate if we need to
		if(searchRequest.getMaxResults()!=null){
			int fromIndex=0;
			if(searchRequest.getStartAt()!=null){
				fromIndex=searchRequest.getStartAt();
			}
			int toIndex = fromIndex+searchRequest.getMaxResults();
			SearchResult pagedResult = new SearchResult();
			for (int i=fromIndex; i <= toIndex; i++) {
				if (!(searchResult.getRows().size() < i+1)) {
					pagedResult.getRows().add(searchResult.getRows().get(i));
				}
			}
			
			searchResult = pagedResult;
		}
		return searchResult;
	}

	
	/**
	 * Compares two SearchResultRow rows with a given sort direction and column
	 *
	 */
	private static class SearchResultRowComparator implements Comparator<SearchResultRow> {
		private String sortColumn;
		private SortDirection sortDirection;
		
		public SearchResultRowComparator(String sortColumn,
				SortDirection sortDirection) {
			super();
			this.sortColumn = sortColumn;
			this.sortDirection = sortDirection;
		}
		
		@Override
		public int compare(SearchResultRow r1, SearchResultRow r2) {
			int compareResult = 0;
			
			//Pares out the cell values to compare
			String v1=null;
			String v2=null;
			for(SearchResultCell c:r1.getCells()){
				if(sortColumn.equals(c.getKey())){
					v1=c.getValue();
					break;
				}
			}
			for(SearchResultCell c:r2.getCells()){
				if(sortColumn.equals(c.getKey())){
					v2=c.getValue();
					break;
				}
			}
			
			//Compare the values wiuth the right type (SHould be done more efficiently
			try{
				Integer v1Integer = Integer.parseInt(v1);
				Integer v2Integer = Integer.parseInt(v2);
				compareResult = v1Integer.compareTo(v2Integer);
			}catch(Exception e1){
				if(v1!=null&&v2!=null&&("true".equals(v1.toLowerCase())||"false".equals(v1.toLowerCase()))&&
				   ("true".equals(v2.toLowerCase())||"false".equals(v2.toLowerCase()))){
					Boolean v1Boolean = Boolean.parseBoolean(v1);
					Boolean v2Boolean = Boolean.parseBoolean(v2);
					compareResult = v1Boolean.compareTo(v2Boolean);
				}else{
					try{
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date v1Date = df.parse(v1);
						Date v2Date = df.parse(v2);
						compareResult = v1Date.compareTo(v2Date);
					}catch(Exception e){
						if(v1!=null){
							compareResult = v1.compareTo(v2);
						}else if(v2==null){
							compareResult = 0;
						}else{
							compareResult = -1; 
						}
					}
				}
			}
			
			//Sort reverse if order is descending
			if(SortDirection.DESC.equals(sortDirection)){
				return -1 * compareResult;
			}
			return compareResult;
		}
		
	}



	/**
	 * Maps results from multiple searches into a single result row
	 *
	 * @param permutation
	 * @param crossSearchType
	 * @return a mapped SearchResultRow
	 */
	private SearchResultRow mapResultRow(
			Map<String, SearchResultRow> permutation,
			CrossSearchTypeInfo crossSearchType) {
		//FIXME this is pretty inefficient to loop through everything... a map structure for the cells might be better
		SearchResultRow resultRow = new SearchResultRow();
		for(JoinResultMappingInfo resultMapping: crossSearchType.getJoinResultMappings()){
			for(SearchResultCell cell: permutation.get(resultMapping.getSubSearchKey()).getCells()){
				if(resultMapping.getSubSearchResultParam().equals(cell.getKey())){
					SearchResultCell mappedCell = new SearchResultCell();
					mappedCell.setKey(resultMapping.getResultParam());
					mappedCell.setValue(cell.getValue());
					resultRow.getCells().add(mappedCell);
					break;//FIXME breaks are bad... but there is no map in the cells
				}
			}
		}
		return resultRow;
		
	}

	private SearchResultRow mapResultRow(
			String subSearchKey, SearchResultRow row,
			CrossSearchTypeInfo crossSearchType) {
		SearchResultRow resultRow = new SearchResultRow();
		
		for(JoinResultMappingInfo resultMapping: crossSearchType.getJoinResultMappings()){
			if(subSearchKey.equals(resultMapping.getSubSearchKey())){
				for(SearchResultCell cell: row.getCells()){
					if(resultMapping.getSubSearchResultParam().equals(cell.getKey())){
						SearchResultCell mappedCell = new SearchResultCell();
						mappedCell.setKey(resultMapping.getResultParam());
						mappedCell.setValue(cell.getValue());
						resultRow.getCells().add(mappedCell);
						break;//FIXME breaks are bad... but there is no map in the cells
					}
				}
			}
		}
		return resultRow;
	}
	/**
	 * Checks each comparison of the join criteria and recursively checks through nested criteria.  
	 * Short circuits for false 'AND' joins and true 'OR' joins
	 * @param permutation
	 * @param crossSearchType
	 * @param joinCriteria
	 * @return whether the criteria is met
	 */
	private boolean meetsCriteria(Map<String, SearchResultRow> permutation,
			CrossSearchTypeInfo crossSearchType, JoinCriteriaInfo joinCriteria){

		JoinType joinType = joinCriteria.getJoinType();
		
		//Check actual comparisons
		for(JoinComparisonInfo comparison:joinCriteria.getComparisons()){
			SearchResultRow leftResultRow =  permutation.get(comparison.getLeftHandSide().getSubSearchKey());
			String leftResultValue = null;
			if(leftResultRow!=null){
				for(SearchResultCell cell: leftResultRow.getCells()){
					if(comparison.getLeftHandSide().getParam().equals(cell.getKey())){
						leftResultValue = cell.getValue();
						break;//FIXME breaks are bad... but there is no map in the cells
					}
				}
			}
			
			SearchResultRow rightResultRow =  permutation.get(comparison.getRightHandSide().getSubSearchKey());
			String rightResultValue = null;
			if(rightResultRow!=null){
				for(SearchResultCell cell: rightResultRow.getCells()){
					if(comparison.getRightHandSide().getParam().equals(cell.getKey())){
						rightResultValue = cell.getValue();
						break;//FIXME breaks are bad... but there is no map in the cells
					}
				}
			}			
			
			//Get the compare type for the 
			//TODO get the types for the params!
			if(leftResultValue==null||rightResultValue==null){
				int i=0;i++;
			}
			if(compare(null, leftResultValue,rightResultValue,comparison.getType())){
				if(JoinType.OR.equals(joinType)){
					return true;
				}
			}else{
				if(JoinType.AND.equals(joinType)){
					return false;
				}
			}
		}
		
		//Check all subcriteria next
		for(JoinCriteriaInfo subCriteria: joinCriteria.getJoinCriteria()){
			if(meetsCriteria(permutation, crossSearchType, subCriteria)){
				if(JoinType.OR.equals(joinType)){
					return true;
				}
			}else{
				if(JoinType.AND.equals(joinType)){
					return false;
				}
			}
		}
		
		if(JoinType.AND.equals(joinType)){
			return true;
		}
		if(JoinType.OR.equals(joinType)){
			return false;
		}
		
		return false;
	}

	/**
	 * @param searchResults
	 * @return a list of all possible combinations of rows
	 */
	private List <Map<String,SearchResultRow>> unionOfAllRows(Map<String, SearchResult> searchResults){
		List <Map<String,SearchResultRow>> r = new ArrayList<Map<String,SearchResultRow>>();
		for(Map.Entry<String,SearchResult> x:searchResults.entrySet()){
			List<Map<String,SearchResultRow>> t = new ArrayList<Map<String,SearchResultRow>>();
			if(x.getValue()!=null&&x.getValue().getRows()!=null){
				for(SearchResultRow y:x.getValue().getRows()){
					for(Map<String,SearchResultRow> i:r){
						Map<String,SearchResultRow> unions =  new HashMap<String,SearchResultRow>();
						unions.putAll(i);
						unions.put(x.getKey(), y);
						t.add(unions);
					}
					if(r.size()==0){
						Map<String,SearchResultRow> unions  =  new HashMap<String,SearchResultRow>();
						unions.put(x.getKey(), y);
						t.add(unions);
					}
				}
			}
			r = t;
		}
		return r;
	}	
	
	private enum DataType{STRING,INT,BOOLEAN,DATE}
	


	private boolean compare(DataType dataType, String left, String right,
			ComparisonType type ){
		//FIXME needs a handle to the result params data types here
		try{
			Integer leftInteger = Integer.parseInt(left);
			Integer rightInteger = Integer.parseInt(right);
			return compareInt(leftInteger,rightInteger,type);
		}catch(Exception e){
		}
		try{
			if(("true".equals(left.toLowerCase())||"false".equals(left.toLowerCase()))&&
			   ("true".equals(right.toLowerCase())||"false".equals(right.toLowerCase()))){
				Boolean leftBoolean = Boolean.parseBoolean(left);
				Boolean rightBoolean = Boolean.parseBoolean(right);
				return compareBoolean(leftBoolean,rightBoolean,type);
			}
		}catch(Exception e){
		}
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date leftDate = df.parse(left);
			Date rightDate = df.parse(right);
			return compareDate(leftDate,rightDate,type);
		}catch(Exception e){
		}
		return compareString(left,right,type);
//		switch(dataType){
//			case BOOLEAN:
//				Boolean leftBoolean = new Boolean(left);
//				Boolean rightBoolean = new Boolean(right);
//				return compareBoolean(leftBoolean,rightBoolean,type);
//			case DATE:
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				Date leftDate = df.parse(left);
//				Date rightDate = df.parse(right);
//				return compareDate(leftDate,rightDate,type);
//			case INT:
//				Integer leftInteger = Integer.getInteger(left);
//				Integer rightInteger = Integer.getInteger(right);
//				return compareInt(leftInteger,rightInteger,type);
//			case STRING:
//				return compareString(left,right,type);
//		}
//		return false;
	}
	
	private boolean compareString(String left, String right, ComparisonType type) {
		switch(type){
		case EQUALS:
			return left.equals(right);
		case GREATERTHAN:
			return left.compareTo(right) > 0;
		case GREATERTHANEQUALS:
			return left.compareTo(right) >= 0;
		case LESSTHAN:
			return left.compareTo(right) < 0;
		case LESSTHANEQUALS:
			return left.compareTo(right) <= 0;
		case NOTEQUALS:
			return !left.equals(right);
		}
		return false;
	}

	private boolean compareInt(Integer left, Integer right, ComparisonType type) {
		switch(type){
		case EQUALS:
			return left.equals(right);
		case GREATERTHAN:
			return left.compareTo(right) > 0;
		case GREATERTHANEQUALS:
			return left.compareTo(right) >= 0;
		case LESSTHAN:
			return left.compareTo(right) < 0;
		case LESSTHANEQUALS:
			return left.compareTo(right) <= 0;
		case NOTEQUALS:
			return !left.equals(right);
		}
		return false;
	}

	private boolean compareDate(Date left, Date right, ComparisonType type) {
		switch(type){
		case EQUALS:
			return left.equals(right);
		case GREATERTHAN:
			return left.compareTo(right) > 0;
		case GREATERTHANEQUALS:
			return left.compareTo(right) >= 0;
		case LESSTHAN:
			return left.compareTo(right) < 0;
		case LESSTHANEQUALS:
			return left.compareTo(right) <= 0;
		case NOTEQUALS:
			return !left.equals(right);
		}
		return false;
	}

	private boolean compareBoolean(Boolean left, Boolean right,
			ComparisonType type) {
		switch(type){
		case EQUALS:
			return left.equals(right);
		case GREATERTHAN:
			return left.compareTo(right) > 0;
		case GREATERTHANEQUALS:
			return left.compareTo(right) >= 0;
		case LESSTHAN:
			return left.compareTo(right) < 0;
		case LESSTHANEQUALS:
			return left.compareTo(right) <= 0;
		case NOTEQUALS:
			return !left.equals(right);
		}
		return false;
	}
	public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}


}
