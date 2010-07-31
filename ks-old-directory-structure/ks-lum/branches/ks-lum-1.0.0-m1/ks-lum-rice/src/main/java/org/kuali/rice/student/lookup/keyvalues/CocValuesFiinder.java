/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
