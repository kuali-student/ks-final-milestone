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
package org.kuali.student.enrollment.class1.hold.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HoldIssueInfoLookupableImpl extends LookupableImpl {
    private HoldService holdService;
    ContextInfo contextInfo = new ContextInfo();

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        String type = fieldValues.get("typeKey");
        String name = fieldValues.get("name");
        String state = fieldValues.get("stateKey");
        String orgId = fieldValues.get("organizationId");
        String descr = fieldValues.get("descr.plain");
        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)){
            p = like("name", "%" + name + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(type)){
            p = like("holdIssueType", "%" + type + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(state)){
            p = equal("holdIssueState", state);
            pList.add(p);
        }

        if (StringUtils.isNotBlank(orgId)){
            p = equal("organizationId", orgId);
            pList.add(p);
        }

        if (StringUtils.isNotBlank(descr)){
            p = like("descrPlain", "%" + descr + "%");
            pList.add(p);
        }

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }

        try {
            QueryByCriteria query = qBuilder.build();

            HoldService holdService = getHoldService();


            List<HoldIssueInfo> holdIssueInfos = holdService.searchForHoldIssues(query, getContextInfo());
            if (!holdIssueInfos.isEmpty()){
                results.addAll(holdIssueInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Performing Search",e); //To change body of catch statement use File | Settings | File Templates.
        }
        return results;
    }


    protected HoldService getHoldService(){
        if(holdService == null) {
            holdService = (HoldService) GlobalResourceLoader.getService(new QName(HoldServiceConstants.NAMESPACE, HoldServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return holdService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  org.kuali.student.enrollment.common.util.ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
