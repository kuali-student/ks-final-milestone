package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.common.util.ContextUtils;

public abstract class OrgsOfTypeValuesFinder extends StudentKeyValuesBase {

	/**
	 * Find the Orgs with specified orgType.
	 * 
	 * @param orgType
	 * @return
	 */
	public static List<KeyValue> findOrgs(String orgType) {
		List<KeyValue> orgEntities = new ArrayList<KeyValue>();

		SearchRequestInfo searchRequest = new SearchRequestInfo("org.search.generic");
		searchRequest.addParam("org.queryParam.orgOptionalType",orgType);
		searchRequest.setSortColumn("org.resultColumn.orgOptionalLongName");
		searchRequest.setSortDirection(SortDirection.ASC);
		try {
			SearchResultInfo results = null;
			results = getOrganizationService().search(searchRequest, ContextUtils.getContextInfo());

			for (SearchResultRowInfo result : results.getRows()) {
				String orgId = "";
				String orgLongName = "";
				for (SearchResultCellInfo resultCell : result.getCells()) {
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
