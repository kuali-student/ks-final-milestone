/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Adi Rajesh on 6/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class OrganizationInfoLookupableImpl extends LookupableImpl {
    private OrganizationService organizationService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<OrgInfo> results = new ArrayList<OrgInfo>();


        String shortName = fieldValues.get("shortName");
        String longName = fieldValues.get("longName");

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(longName) && !longName.isEmpty()) {
            if (StringUtils.isNotBlank(shortName) && !shortName.isEmpty()){
                qBuilder.setPredicates(PredicateFactory.or(
                        PredicateFactory.like("longName","*"+ fieldValues.get("longName")+ "*"),
                        PredicateFactory.like("shortName","*" + fieldValues.get("shortName") + "*")));
            }
            else {
                qBuilder.setPredicates(PredicateFactory.like("longName","*"+ fieldValues.get("longName")+ "*"));
            }
        }else if (StringUtils.isNotBlank(shortName) && !shortName.isEmpty()){
            qBuilder.setPredicates(PredicateFactory.like("shortName","*" + fieldValues.get("shortName") + "*"));
        }
        try {
            QueryByCriteria query = qBuilder.build();

            OrganizationService  organizationService = getOrganizationService();

            List<OrgInfo> orgInfos = organizationService.searchForOrgs(query, ContextUtils.createDefaultContextInfo());
            if (!orgInfos.isEmpty()){
                results.addAll(orgInfos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e); //To change body of catch statement use File | Settings | File Templates.
        }
        return results;
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;

    }
}
