package org.kuali.student.core.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.dto.SortDirection;
import org.springmodules.orm.ojb.PersistenceBrokerTemplate;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public abstract class SearchableOjbDaoImpl implements SearchableDao {
	
	@Override
	public SearchResult search(SearchRequest searchRequest,
			Map<String, String> queryMap, SearchTypeInfo searchTypeInfo) {
		
		SearchResult result = new SearchResult();

		//build the criteria
		Criteria criteria = buildCriteria(searchRequest, queryMap);
		//determine the entity class to use
		Class<?> entityClass = resolveEntityClassForSearch(searchRequest, queryMap);
		//determine the result columns to use
		String[] attributes = resolveResultColumnAttributes(searchRequest, queryMap);
		
		//Create a new report query
		ReportQueryByCriteria q = QueryFactory.newReportQuery(entityClass, criteria);
		q.setAttributes(attributes);
		
		//Set sorting
		if(searchRequest.getSortColumn()!=null){
			String sortFieldName = resolveSortField(attributes,searchRequest,searchTypeInfo);
			boolean sortAscending = !SortDirection.DESC.equals(searchRequest.getSortDirection());
			q.addOrderBy(sortFieldName, sortAscending);
		}
		
		//If we need total results, then execute that query
		if(searchRequest.getNeededTotalResults()!=null && searchRequest.getNeededTotalResults()){
			result.setTotalResults(getPersistenceBrokerTemplate().getCount(q));
		}
		
		//If paging is required, set the start and end results
		if(searchRequest.getMaxResults()!=null&&searchRequest.getStartAt()!=null){
			q.setStartAtIndex(searchRequest.getStartAt());
			q.setEndAtIndex(searchRequest.getStartAt()+searchRequest.getMaxResults()-1);
		}
		
		//Perform the query
		Iterator resultIter = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(q);
		
		//Map the query results to search result rows
		result.setRows(convertToSearchResultRows(resultIter,searchTypeInfo));
		
		return result;
	}

	private PersistenceBrokerTemplate getPersistenceBrokerTemplate() {
		return ((PersistenceBrokerDaoSupport)GlobalResourceLoader.getService("businessObjectDaoOjb")).getPersistenceBrokerTemplate();
	}

	protected String resolveSortField(String[] attributes,
			SearchRequest searchRequest, SearchTypeInfo searchTypeInfo) {
		int i = 0;
		for(ResultColumnInfo results : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()){
			if(results.getKey().equals(searchRequest.getSortColumn())){
				return attributes[i];
			}
			i++;
		}
		throw new RuntimeException("Could not resolve sort field: "+searchRequest.getSortColumn());
	}

	protected String[] resolveResultColumnAttributes(SearchRequest searchRequest,
			Map<String, String> queryMap) {
		String searchKey = searchRequest.getSearchKey();
		String queryString = queryMap.get(searchKey);
		String className = queryString.replaceFirst(".*\\s+[Ff][Rr][Oo][Mm]\\s+([^\\s]+).*", "$1");
		String classAlias = queryString.replaceFirst(".*\\s+"+className+"\\s+([^\\s]+).*","$1");
		String selectColumns = queryString.replaceFirst("\\s*[Ss][Ee][Ll][Ee][Cc][Tt]\\s+(.*)\\s+[Ff][Rr][Oo][Mm]\\s+.*", "$1");
		String[] columns = selectColumns.split("[\\s,]+");
		String[] attributes = new String[columns.length];
		for(int i = 0; i < columns.length; i++){
			attributes[i] = columns[i].substring(classAlias.length()+1);
		}
		return attributes;
	}

	abstract protected Class<?> resolveEntityClassForSearch(SearchRequest searchRequest, Map<String, String> queryMap);

	abstract protected Criteria buildCriteria(SearchRequest searchRequest, Map<String, String> queryMap);

	protected List<SearchResultRow> convertToSearchResultRows(
			Iterator resultIter, SearchTypeInfo searchTypeInfo) {
		List<SearchResultRow> resultRows = new ArrayList<SearchResultRow>();
		while(resultIter.hasNext()){
			Object[] row = (Object[]) resultIter.next();
			SearchResultRow searchResultRow = new SearchResultRow();
			int i = 0;
			for(ResultColumnInfo column: searchTypeInfo.getSearchResultTypeInfo().getResultColumns()){
				SearchResultCell cell = new SearchResultCell();
				cell.setKey(column.getKey());
				cell.setValue(row[i].toString());
				searchResultRow.getCells().add(cell);
				i++;
			}
			resultRows.add(searchResultRow); 
		}
		return resultRows;
	}

}
