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
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class provides a Lookupable implementation for Organizations
 *
 * @author Kuali Student Team
 */
public class OrganizationInfoLookupableImpl extends LookupableImpl {

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List<OrgInfo> results = new ArrayList<OrgInfo>();


        String shortName = searchCriteria.get("shortName");
        String longName = searchCriteria.get("longName");

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(longName) && !longName.isEmpty()) {
            if (StringUtils.isNotBlank(shortName) && !shortName.isEmpty()){
                qBuilder.setPredicates(PredicateFactory.or(
                        PredicateFactory.like("longName","*"+ searchCriteria.get("longName")+ "*"),
                        PredicateFactory.like("shortName","*" + searchCriteria.get("shortName") + "*")));
            }
            else {
                qBuilder.setPredicates(PredicateFactory.like("longName","*"+ searchCriteria.get("longName")+ "*"));
            }
        }else if (StringUtils.isNotBlank(shortName) && !shortName.isEmpty()){
            qBuilder.setPredicates(PredicateFactory.like("shortName","*" + searchCriteria.get("shortName") + "*"));
        }
        try {
            QueryByCriteria query = qBuilder.build();

            List<OrgInfo> orgInfos = CourseOfferingManagementUtil.getOrganizationService().searchForOrgs(query, ContextUtils.createDefaultContextInfo());
            if (!orgInfos.isEmpty()){
                results.addAll(orgInfos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e); //To change body of catch statement use File | Settings | File Templates.
        }
        return results;
    }
}
