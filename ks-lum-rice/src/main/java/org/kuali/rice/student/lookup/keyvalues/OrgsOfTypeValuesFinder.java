package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SortDirection;

public abstract class OrgsOfTypeValuesFinder extends StudentKeyValuesBase {

	/**
	 * Find the Orgs with specified orgType.
	 * 
	 * @param orgType
	 * @return
	 */
	public static List<KeyValue> findOrgs(String orgType) {
		List<KeyValue> orgEntities = new ArrayList<KeyValue>();

		SearchRequest searchRequest = new SearchRequest("org.search.generic");
		searchRequest.addParam("org.queryParam.orgOptionalType",orgType);
		searchRequest.setSortColumn("org.resultColumn.orgOptionalLongName");
		searchRequest.setSortDirection(SortDirection.ASC);
		try {
			SearchResult results = null;
			results = getOrganizationService().search(searchRequest);

			for (SearchResultRow result : results.getRows()) {
				String orgId = "";
				String orgLongName = "";
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
						orgId = resultCell.getValue();
					} else if("org.resultColumn.orgOptionalLongName".equals(resultCell.getKey())){
						orgLongName = resultCell.getValue();
					}
				}
	            /*
	             * The requirement is that in the RICE portal, when we add a principal to a role
	             * the drop-down list for Department or Division should display the full/long 
	             * names instead of short names.
	             */
	            orgEntities.add(new ConcreteKeyValue(orgId, orgLongName));
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
