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
 */
package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HoldIssueInfoLookupableImpl extends LookupableImpl {

    private HoldService holdService;
    private ContextInfo contextInfo;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();

        String id = fieldValues.get("id");
        String name = fieldValues.get("name");
        String type = fieldValues.get("typeKey");
        String state = fieldValues.get("stateKey");
        String orgId = fieldValues.get("organizationId");

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(id) && !id.isEmpty()) {
            if (StringUtils.isNotBlank(name) && !name.isEmpty()) {
                if (StringUtils.isNotBlank(type) && !type.isEmpty()) {
                    if (StringUtils.isNotBlank(state) && !state.isEmpty()) {
                        if (StringUtils.isNotBlank(orgId) && !orgId.isEmpty()){
                            qBuilder.setPredicates(PredicateFactory.or(
                                PredicateFactory.equal("id", id),
                                PredicateFactory.equal("name",name),
                                PredicateFactory.equal("typeKey",type),
                                PredicateFactory.equal("stateKey",state),
                                PredicateFactory.equal("organizationId",orgId)));
                        } else {
                            qBuilder.setPredicates(PredicateFactory.or(
                                PredicateFactory.equal("id", id),
                                PredicateFactory.equal("name",name),
                                PredicateFactory.equal("typeKey",type),
                                PredicateFactory.equal("stateKey",state)));
                        }
                    } else {
                        qBuilder.setPredicates(PredicateFactory.or(
                                PredicateFactory.equal("id", id),
                                PredicateFactory.equal("name",name),
                                PredicateFactory.equal("typeKey",type)));
                    }
                } else {
                    qBuilder.setPredicates(PredicateFactory.or(
                            PredicateFactory.equal("id", id),
                            PredicateFactory.equal("name",name)));
                }
            } else {
                qBuilder.setPredicates(PredicateFactory.equal("id",id));
            }
        } else if (StringUtils.isNotBlank(name) && !name.isEmpty()){
            qBuilder.setPredicates(PredicateFactory.equal("name",name));
        }
        try {
            QueryByCriteria query = qBuilder.build();

            holdService = getHoldService();


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

    public HoldService getHoldService() {
        if(holdService == null)
            holdService = (HoldService) GlobalResourceLoader.getService(new QName(HoldServiceConstants.NAMESPACE, HoldServiceConstants.SERVICE_NAME_LOCAL_PART));
        return holdService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
