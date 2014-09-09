package org.kuali.student.enrollment.class1.hold.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueResult;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

/**
 * Created by helium on 2014/09/08.
 */
public class HoldsUtil {


    public static QueryByCriteria.Builder buildQueryByCriteria(String name, String type, String state, String orgId, String descr) {

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)) {
            pList.add(like(HoldsConstants.HOLD_ISSUE_NAME, "%" + name + "%"));
        }

        if (StringUtils.isNotBlank(type)) {
            pList.add(equal(HoldsConstants.HOLD_ISSUE_TYPE_KEY, type));
        }

        if (StringUtils.isNotBlank(state)) {
            pList.add(equal(HoldsConstants.HOLD_ISSUE_STATE_KEY, state));
        }

        if (StringUtils.isNotBlank(orgId)) {
            pList.add(equal(HoldsConstants.HOLD_ISSUE_ORG_ID, orgId));
        }

        if (StringUtils.isNotBlank(descr)) {
            pList.add(like(HoldsConstants.HOLD_ISSUE_DESCR_PLAIN, "%" + descr + "%"));
        }

        if (!pList.isEmpty()) {
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }
        return qBuilder;
    }

    public static List<HoldIssueResult> createHoldIssueResultList(List<HoldIssueInfo> holdIssueInfos) {

        List<HoldIssueResult> holdIssueResultList = new ArrayList<HoldIssueResult>();

        for (HoldIssueInfo holdIssueInfo : holdIssueInfos) {
            holdIssueResultList.add(getHoldIssueResult(holdIssueInfo));
        }
        return holdIssueResultList;
    }

    private static HoldIssueResult getHoldIssueResult(HoldIssueInfo holdIssueInfo) {
        HoldIssueResult holdIssueResult = new HoldIssueResult();
        holdIssueResult.setId(holdIssueInfo.getId());
        holdIssueResult.setName(holdIssueInfo.getName());
        holdIssueResult.setCode(holdIssueInfo.getHoldCode());
        holdIssueResult.setTypeKey(holdIssueInfo.getTypeKey());
        holdIssueResult.setDescr((holdIssueInfo.getDescr() != null ? holdIssueInfo.getDescr().getPlain() : StringUtils.EMPTY));
        holdIssueResult.setOrganizationId(holdIssueInfo.getOrganizationId());
        holdIssueResult.setFirstDate(holdIssueInfo.getFirstAppliedDate());
        holdIssueResult.setLastDate(holdIssueInfo.getLastAppliedDate());
        try {
            holdIssueResult.setState(HoldsResourceLoader.getStateService().getState(holdIssueInfo.getStateKey(), ContextUtils.createDefaultContextInfo()).getName());
        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e);
        }
        holdIssueResult.setStartTerm(holdIssueInfo.getFirstApplicationTermId());
        holdIssueResult.setEndTerm(holdIssueInfo.getLastApplicationTermId());
        holdIssueResult.setAuthorization("Authorization");
        return holdIssueResult;
    }
}
