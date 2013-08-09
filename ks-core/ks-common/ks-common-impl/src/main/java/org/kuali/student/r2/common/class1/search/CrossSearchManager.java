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

package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.search.dto.CrossSearchTypeInfo;
import org.kuali.student.r2.core.search.dto.JoinComparisonInfo;
import org.kuali.student.r2.core.search.dto.JoinComparisonInfo.ComparisonType;
import org.kuali.student.r2.core.search.dto.JoinCriteriaInfo;
import org.kuali.student.r2.core.search.dto.JoinCriteriaInfo.JoinType;
import org.kuali.student.r2.core.search.dto.JoinResultMappingInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SubSearchInfo;
import org.kuali.student.r2.core.search.dto.SubSearchParamMappingInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;

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
	private SearchService searchDispatcher;

	public SearchResultInfo doCrossSearch(SearchRequestInfo searchRequest, CrossSearchTypeInfo crossSearchType, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
		SearchResultInfo searchResult = new SearchResultInfo();
		
		Map<String,SearchResultInfo> subSearchResults = new HashMap<String,SearchResultInfo>();
		
		//First perform all the subsearches
		for(SubSearchInfo subSearch:crossSearchType.getSubSearches()){
			//Map the parameters to the subsearch
			SearchRequestInfo subSearchRequest = new SearchRequestInfo();
			
			subSearchRequest.setSearchKey(subSearch.getSearchkey());
			subSearchRequest.setParams(new ArrayList<SearchParamInfo>());
            subSearchRequest.setSortColumn(searchRequest.getSortColumn());
            subSearchRequest.setSortDirection(searchRequest.getSortDirection());
			
			//For each param mapping, map the paramvalue from the cross search to the sub search
			for(SubSearchParamMappingInfo paramMapping:subSearch.getSubSearchParamMappings()){
				for(SearchParamInfo crossSearchParam:searchRequest.getParams()){
					if(paramMapping.getCrossSearchParam().equals(crossSearchParam.getKey())){
						SearchParamInfo subSearchParam = new SearchParamInfo();
						subSearchParam.setKey(paramMapping.getSubSearchParam());
                        subSearchParam.setValues(crossSearchParam.getValues());
						subSearchRequest.getParams().add(subSearchParam);
					}
				}
			}
			SearchResultInfo subSearchResult = searchDispatcher.search(subSearchRequest, contextInfo);
			subSearchResults.put(subSearch.getKey(), subSearchResult);
		}
		
		//merge the subsearches together using the join rules
		if(crossSearchType.getJoinCriteria().getComparisons().isEmpty()){
			//If the root join has no criteria then do a simple union of rows
			for(Map.Entry<String,SearchResultInfo> subSearchResult:subSearchResults.entrySet()){
				for(SearchResultRowInfo row:subSearchResult.getValue().getRows()){
					SearchResultRowInfo mappedResult = mapResultRow(subSearchResult.getKey(),row,crossSearchType);
					searchResult.getRows().add(mappedResult);
				}
			}
		}else{
			//merge the subsearches together using the join rules (this is in o^2 time which is bad)
			List <Map<String,SearchResultRowInfo>> allPermutations = unionOfAllRows(subSearchResults);
	
			for(Map<String,SearchResultRowInfo> permutation:allPermutations){
				if(meetsCriteria(permutation,crossSearchType,crossSearchType.getJoinCriteria())){
					SearchResultRowInfo mappedResult = mapResultRow(permutation,crossSearchType);
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
	private SearchResultInfo metaFilter(SearchResultInfo searchResult,
		SearchRequestInfo searchRequest) {
		
        searchResult.setTotalResults(searchResult.getRows().size());

		searchResult.sortRows();		
		
		//Paginate if we need to
		if(searchRequest.getMaxResults()!=null){
			int fromIndex=0;
			if(searchRequest.getStartAt()!=null){
				fromIndex=searchRequest.getStartAt();
			}
			int toIndex = fromIndex+searchRequest.getMaxResults();
			SearchResultInfo pagedResult = new SearchResultInfo();
			for (int i=fromIndex; i <= toIndex; i++) {
				if (!(searchResult.getRows().size() < i+1)) {
					pagedResult.getRows().add(searchResult.getRows().get(i));
				}
			}
            pagedResult.setTotalResults(searchResult.getRows().size());
			searchResult = pagedResult;
		}
		return searchResult;
	}

	/**
	 * Maps results from multiple searches into a single result row
	 *
	 * @param permutation
	 * @param crossSearchType
	 * @return a mapped SearchResultRowInfo
	 */
	private SearchResultRowInfo mapResultRow(
			Map<String, SearchResultRowInfo> permutation,
			CrossSearchTypeInfo crossSearchType) {
		//FIXME this is pretty inefficient to loop through everything... a map structure for the cells might be better
		SearchResultRowInfo resultRow = new SearchResultRowInfo();
		for(JoinResultMappingInfo resultMapping: crossSearchType.getJoinResultMappings()){
			for(SearchResultCellInfo cell: permutation.get(resultMapping.getSubSearchKey()).getCells()){
				if(resultMapping.getSubSearchResultParam().equals(cell.getKey())){
					SearchResultCellInfo mappedCell = new SearchResultCellInfo();
					mappedCell.setKey(resultMapping.getResultParam());
					mappedCell.setValue(cell.getValue());
					resultRow.getCells().add(mappedCell);
					break;//FIXME breaks are bad... but there is no map in the cells
				}
			}
		}
		return resultRow;
		
	}

	private SearchResultRowInfo mapResultRow(
			String subSearchKey, SearchResultRowInfo row,
			CrossSearchTypeInfo crossSearchType) {
		SearchResultRowInfo resultRow = new SearchResultRowInfo();
		
		for(JoinResultMappingInfo resultMapping: crossSearchType.getJoinResultMappings()){
			if(subSearchKey.equals(resultMapping.getSubSearchKey())){
				for(SearchResultCellInfo cell: row.getCells()){
					if(resultMapping.getSubSearchResultParam().equals(cell.getKey())){
						SearchResultCellInfo mappedCell = new SearchResultCellInfo();
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
	private boolean meetsCriteria(Map<String, SearchResultRowInfo> permutation,
			CrossSearchTypeInfo crossSearchType, JoinCriteriaInfo joinCriteria) throws OperationFailedException {

		JoinType joinType = joinCriteria.getJoinType();
		
		//Check actual comparisons
		for(JoinComparisonInfo comparison:joinCriteria.getComparisons()){
			SearchResultRowInfo leftResultRow =  permutation.get(comparison.getLeftHandSide().getSubSearchKey());
			String leftResultValue = null;
			if(leftResultRow!=null){
				for(SearchResultCellInfo cell: leftResultRow.getCells()){
					if(comparison.getLeftHandSide().getParam().equals(cell.getKey())){
						leftResultValue = cell.getValue();
						break;//FIXME breaks are bad... but there is no map in the cells
					}
				}
			}
			
			SearchResultRowInfo rightResultRow =  permutation.get(comparison.getRightHandSide().getSubSearchKey());
			String rightResultValue = null;
			if(rightResultRow!=null){
				for(SearchResultCellInfo cell: rightResultRow.getCells()){
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
	private List <Map<String,SearchResultRowInfo>> unionOfAllRows(Map<String, SearchResultInfo> searchResults){
		List <Map<String,SearchResultRowInfo>> r = new ArrayList<Map<String,SearchResultRowInfo>>();
		for(Map.Entry<String,SearchResultInfo> x:searchResults.entrySet()){
			List<Map<String,SearchResultRowInfo>> t = new ArrayList<Map<String,SearchResultRowInfo>>();
			if(x.getValue()!=null&&x.getValue().getRows()!=null){
				for(SearchResultRowInfo y:x.getValue().getRows()){
					for(Map<String,SearchResultRowInfo> i:r){
						Map<String,SearchResultRowInfo> unions =  new HashMap<String,SearchResultRowInfo>();
						unions.putAll(i);
						unions.put(x.getKey(), y);
						t.add(unions);
					}
					if(r.size()==0){
						Map<String,SearchResultRowInfo> unions  =  new HashMap<String,SearchResultRowInfo>();
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
			ComparisonType type ) throws OperationFailedException {
		//FIXME Right now DataType is always null, needs to be addressed by fixing JIRA KSCOR-505
		try{
			Integer leftInteger = Integer.parseInt(left);
			Integer rightInteger = Integer.parseInt(right);
			return compare(leftInteger,rightInteger,type);
		}catch(NumberFormatException e){
		}


        if(left != null && right != null) {
            if(("true".equals(left.toLowerCase())||"false".equals(left.toLowerCase())) &&
                    ("true".equals(right.toLowerCase())||"false".equals(right.toLowerCase()))) {
                Boolean leftBoolean = Boolean.parseBoolean(left);
                Boolean rightBoolean = Boolean.parseBoolean(right);
                return compare(leftBoolean, rightBoolean, type);
            }
        }
        try{
            Date leftDate = null, rightDate = null;
            if(left != null) {
                leftDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(left);
            }
            if(right != null) {
                rightDate = DateFormatters.DEFAULT_DATE_FORMATTER.parse(right);
            }
            return compare(leftDate, rightDate, type);
        }catch(IllegalArgumentException e){
        }
		return compare(left, right, type);
	}
	
    private boolean compare(Comparable left, Comparable right, ComparisonType type) throws OperationFailedException {

        if(left == null || right == null) {
            if(type == ComparisonType.EQUALS) {
                return left == right;
            }
            else if(type == ComparisonType.NOTEQUALS) {
                return left != right;
            }
            else {
                throw new OperationFailedException("Comparison type " + type.toString() + " undefined for null values");
            }
        }

        switch (type) {
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
            default:
                throw new OperationFailedException("Unsupported ComparisonType: " + type);
        }
    }

	public void setSearchDispatcher(SearchService searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}

	public SearchService getSearchDispatcher() {
		return searchDispatcher;
	}
		
}
