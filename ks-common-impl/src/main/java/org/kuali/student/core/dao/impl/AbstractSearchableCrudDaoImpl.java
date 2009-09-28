package org.kuali.student.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

public class AbstractSearchableCrudDaoImpl extends AbstractCrudDaoImpl
		implements SearchableDao {



	@Override
	public List<Result> searchForResults(String queryString,
			SearchTypeInfo searchTypeInfo,
			List<QueryParamValue> queryParamValues) {

		Query query = em.createQuery(queryString);
		
		//replace all the "." notation with "_" since the "."s in the ids of the queries will cause problems with the jpql  
		if(queryParamValues!=null){
			for (QueryParamValue queryParamValue : queryParamValues) {
				query.setParameter(queryParamValue.getKey().replace(".", "_"), queryParamValue
						.getValue());
			}
		}

		// Turn into results
		List<Result> results = new ArrayList<Result>();
		
		List<?> queryResults = query.getResultList();
		
		if(queryResults!=null){
			//Copy the query results to a Result object
			for(Object queryResult:queryResults){
				Result result = new Result();
				int i=0;
				for (ResultColumnInfo resultColumn : searchTypeInfo.getSearchResultTypeInfo().getResultColumns()) {
			
					ResultCell resultCell = new ResultCell();
					resultCell.setKey(resultColumn.getKey());
					
					if(queryResult.getClass().isArray()){
						resultCell.setValue(((Object[])queryResult)[i].toString());
					}else{
						resultCell.setValue(queryResult.toString());
					}
					
					result.getResultCells().add(resultCell);
					i++;
				}
				results.add(result);
			}
		
		}
		return results;
	}

}
