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
		List<String> orgTypes = new ArrayList<String>();
		orgTypes.add("kuali.org.Department");
		orgTypes.add("kuali.org.College");
		orgTypes.add("kuali.org.Division");
		orgTypes.add("kuali.org.Office");
		orgTypes.add("kuali.org.School");
		searchRequest.addParam("org.queryParam.orgOptionalType", orgTypes);
		searchRequest.setSortColumn("org.resultColumn.orgOptionalLongName");
		try {
			SearchResult results = getOrganizationService().search(searchRequest);

			for (SearchResultRow result : results.getRows()) {
				String orgId = null;
				String orgLongName = null;
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgOptionalLongName".equals(resultCell.getKey())) {
						orgLongName = resultCell.getValue();
					}					
				}
	            orgEntities.add(new KeyLabelPair(orgId, orgLongName));
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}