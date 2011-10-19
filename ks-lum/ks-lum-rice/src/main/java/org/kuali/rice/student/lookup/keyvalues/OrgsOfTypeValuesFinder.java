package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;

public abstract class OrgsOfTypeValuesFinder extends StudentKeyValuesBase {

	/**
	 * Find the Orgs with specified orgType.
	 * 
	 * @param orgType
	 * @return
	 */
	public static List<KeyLabelPair> findOrgs(String orgType) {
		List<KeyLabelPair> orgEntities = new ArrayList<KeyLabelPair>();

		SearchRequest searchRequest = new SearchRequest("org.search.generic");
		searchRequest.addParam("org.queryParam.orgOptionalType",orgType);

		try {
			SearchResult results = getOrganizationService().search(searchRequest);

			for (SearchResultRow result : results.getRows()) {
				String orgId = "";
				String orgShortName = "";
				String orgLongName = "";
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					} else if("org.resultColumn.orgLongName".equals(resultCell.getKey())){
						orgLongName = resultCell.getValue();
					}
				}
		        if (StringUtils.isBlank(orgLongName)) {
		           //use shortName when longName is blank
		            orgEntities.add(buildKeyLabelPair(orgId, orgShortName, null, null));
		        }
		        else {
		            /*
		             * The requirement is that in the RICE portal, when we add a principal to a role
		             * the drop-down list for Department or Division should display the full/long 
		             * names instead of short names.
		             */
		            orgEntities.add(new KeyLabelPair(orgId, orgLongName));
		        }
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
