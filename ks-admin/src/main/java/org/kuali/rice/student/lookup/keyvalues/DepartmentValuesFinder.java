package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.core.organization.service.OrganizationService;

public class DepartmentValuesFinder extends KeyValuesBase {
	private static OrganizationService organizationService;

	protected static OrganizationService getOrganizationService() {
		if (organizationService == null) {
			organizationService = (OrganizationService) GlobalResourceLoader
					.getService(new QName(
							"http://student.kuali.org/wsdl/organization",
							"OrganizationService"));
		}
		return organizationService;
	}

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> orgEntities = new ArrayList<KeyLabelPair>();

		SearchRequest searchRequest = new SearchRequest("org.search.generic");
		searchRequest.addParam("org.queryParam.orgOptionalType", "kuali.org.Department");

		try {
			SearchResult results = getOrganizationService().search(searchRequest);

			for (SearchResultRow result : results.getRows()) {
				String orgId = null;
				String orgShortName = null;
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					}					
				}
	            orgEntities.add(new KeyLabelPair(orgId, orgShortName));
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}