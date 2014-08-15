package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper;
import org.kuali.student.enrollment.class1.hold.service.HoldIssueViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldIssueResourceLoader;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * <p/>
 * Implementation of the HoldIssueViewHelperService that contains helper methods that support the Hold Issue Maintenance Controller.
 */
public class HoldIssueViewHelperServiceImpl extends KSViewHelperServiceImpl implements HoldIssueViewHelperService {

    public HoldIssueInfo createHoldIssue(HoldIssueInfo holdIssue){
        HoldIssueInfo createHoldIssueInfo = null;
        try {
            createHoldIssueInfo = HoldIssueResourceLoader.getHoldService().createHoldIssue(holdIssue.getTypeKey(), holdIssue, createContextInfo());
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return createHoldIssueInfo;
    }

    @Override
    public List<HoldIssueInfo> searchHolds(HoldIssueMaintenanceWrapper holdIssueWrapper) {

        List<HoldIssueInfo> results = new ArrayList<HoldIssueInfo>();

        try {
            QueryByCriteria.Builder query = buildQueryByCriteria(holdIssueWrapper.getName(),holdIssueWrapper.getTypeKey(),holdIssueWrapper.getStateKey(),holdIssueWrapper.getOrganizationId(),holdIssueWrapper.getDescr());
            results = HoldIssueResourceLoader.getHoldService().searchForHoldIssues(query.build(), createContextInfo());
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return results;
    }


    private static QueryByCriteria.Builder buildQueryByCriteria(String name, String type,String state, String orgId, String descr){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

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
        return qBuilder;
    }

}
