/**
 *
 */
package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

/**
 * @author lindholm
 *
 */
public abstract class CocValuesFiinder extends KeyValuesBase {
	/**
	 * Find the curriculum committee Orgs of this orgType
	 *
	 * @param orgType
	 * @return
	 */
	public static List<KeyLabelPair> findCocOrgs(String orgType) {
		List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();

		OrganizationService orgService = (OrganizationService) GlobalResourceLoader
				.getService(new QName(
						"http://student.kuali.org/wsdl/organization",
						"OrganizationService"));

		List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(2);
		QueryParamValue qpOrgType = new QueryParamValue();
		qpOrgType.setKey("org.queryParam.relationType");
		qpOrgType.setValue("kuali.org.CurriculumChild");
		queryParamValues.add(qpOrgType);
		
		qpOrgType = new QueryParamValue();
		qpOrgType.setKey("org.queryParam.orgType");
		qpOrgType.setValue(orgType);
		queryParamValues.add(qpOrgType);
		
		qpOrgType = new QueryParamValue();
		qpOrgType.setKey("org.queryParam.relatedOrgType");
		qpOrgType.setValue("kuali.org.COC");
		queryParamValues.add(qpOrgType);

		try {
			List<Result> results = orgService.searchForResults("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgType",
					queryParamValues);

			for (Result result : results) {
				String orgId = "";
				String orgShortName = "";
				for (ResultCell resultCell : result.getResultCells()) {
					if ("org.resultColumn.orgId".equals(resultCell
							.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName"
							.equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					}
				}
				// departments.add(new KeyLabelPair(orgId, orgShortName));
				departments.add(new KeyLabelPair(orgShortName, orgShortName));
			}

			return departments;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
