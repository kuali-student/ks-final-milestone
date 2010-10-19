package org.kuali.student.core.enumerationmanagement.dao.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.ojb.broker.query.Criteria;
import org.kuali.student.core.dao.impl.SearchableOjbDaoImpl;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;

public class EnumerationManagementDaoImpl extends SearchableOjbDaoImpl {
	
	private final String[] columnAttributes = new String[]{"id","code","abbrevValue","value","activeFromDate","activeToDate","sortKey"};
	
	@Override
	protected Class<?> resolveEntityClassForSearch(SearchRequest searchRequest,
			Map<String, String> queryMap) {
		if ("enumeration.management.search".equals(searchRequest.getSearchKey())) {
			return EnumeratedValue.class;
		}
		throw new RuntimeException(	"Could not resolve entity class for search key: " + searchRequest.getSearchKey());
	}
	
	@Override
	protected Criteria buildCriteria(SearchRequest searchRequest,
			Map<String, String> queryMap) {
		Criteria criteria = new Criteria();
		for(SearchParam param:searchRequest.getParams()){
			if("enumeration.queryParam.enumerationType".equals(param.getKey())){
				criteria.addEqualTo("enumeration.id", param.getValue());
			}else if("enumeration.queryParam.enumerationCode".equals(param.getKey())){
				criteria.addIn("code", (Collection) param.getValue());
			}else if("enumeration.queryParam.enumerationOptionalCode".equals(param.getKey())){
				criteria.addEqualTo("code", param.getValue());
			}else{
				throw new RuntimeException("Invalid search param");
			}
		}
		return criteria;
	}

	@Override
	protected String[] resolveResultColumnAttributes(
			SearchRequest searchRequest, Map<String, String> queryMap) {
		if("enumeration.management.search".equals(searchRequest.getSearchKey())){
			return columnAttributes;
		}
		throw new RuntimeException(	"Could not resolve column attributes for search key: " + searchRequest.getSearchKey());
	}
}
