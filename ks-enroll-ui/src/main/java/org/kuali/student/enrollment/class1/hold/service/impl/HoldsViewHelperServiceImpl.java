package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.service.impl.PersonServiceConstants;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldManagementForm;
import org.kuali.student.enrollment.class1.hold.form.AppliedHoldResult;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueManagementForm;
import org.kuali.student.enrollment.class1.hold.form.HoldIssueResult;
import org.kuali.student.enrollment.class1.hold.service.HoldsViewHelperService;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
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
 * Implementation of the HoldIssueViewHelperService that contains helper methods that support the Hold Issue Management Controller.
 */
public class HoldsViewHelperServiceImpl extends KSViewHelperServiceImpl implements HoldsViewHelperService {

    /**
     * This method is used to search for hold issues and map them to HoldIssueResult
     *
     * @param holdIssueFrom
     * @return List holdIssueResultList
     */
    @Override
    public List<HoldIssueResult> searchHolds(HoldIssueManagementForm holdIssueFrom) {

        List<HoldIssueResult> holdIssueResultList = new ArrayList<HoldIssueResult>();
        List<HoldIssueInfo> holdIssueInfos = new ArrayList<HoldIssueInfo>();
        try {
            QueryByCriteria.Builder query = buildQueryByCriteria(holdIssueFrom.getName(), holdIssueFrom.getTypeKey(),
                    holdIssueFrom.getState(), holdIssueFrom.getOrganizationId(), holdIssueFrom.getDescr());
            holdIssueInfos = HoldsResourceLoader.getHoldService().searchForHoldIssues(query.build(), createContextInfo());

            for (HoldIssueInfo holdIssueInfo : holdIssueInfos) {
                HoldIssueResult holdIssueResult = new HoldIssueResult();
                holdIssueResult.setId(holdIssueInfo.getId());
                holdIssueResult.setName(holdIssueInfo.getName());
                holdIssueResult.setCode(holdIssueInfo.getHoldCode());
                holdIssueResult.setTypeKey(holdIssueInfo.getTypeKey());
                holdIssueResult.setDescr((holdIssueInfo.getDescr() != null ? holdIssueInfo.getDescr().getPlain() : StringUtils.EMPTY));
                holdIssueResult.setOrganizationId(holdIssueInfo.getOrganizationId());
                holdIssueResult.setFirstDate(holdIssueInfo.getFirstAppliedDate());
                holdIssueResult.setLastDate(holdIssueInfo.getLastAppliedDate());
                holdIssueResult.setAuthorization("Authorization");

                holdIssueResultList.add(holdIssueResult);
            }
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return holdIssueResultList;
    }


    private static QueryByCriteria.Builder buildQueryByCriteria(String name, String type, String state, String orgId, String descr) {

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

    @Override
    public PersonInfo getStudentById(String studentId) {

        if (StringUtils.isBlank(studentId)) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PERSON_ID, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_STUDENT_REQUIRED);
            return null;
        }

        try {
            PersonInfo personInfo = HoldsResourceLoader.getPersonService().getPerson(studentId.toUpperCase(), createContextInfo());

            Boolean validStudent = false;
            List<PersonAffiliationInfo> personAffiliationInfos = HoldsResourceLoader.getPersonService().getPersonAffiliationsByPerson(personInfo.getId(), createContextInfo());
            for (PersonAffiliationInfo personAffiliationInfo : personAffiliationInfos) {
                if (personAffiliationInfo.getTypeKey().equals(PersonServiceConstants.PERSON_AFFILIATION_STUDENT_TYPE_KEY)) {
                    validStudent = true;
                }
            }
            if (!validStudent) {
                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PERSON_ID, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_NO_STUDENT_AFFILIATION, studentId);
                return null;
            }
            return personInfo;
        } catch (DoesNotExistException dne) {
            GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PERSON_ID, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_INVALID_STUDENT, studentId);
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }

        return null;
    }

    /**
     * This method is used to search for holds applied to a person and map them to AppliedHoldResult
     *
     * @param holdFrom
     * @return List holdResultList
     */
    @Override
    public List<AppliedHoldResult> searchAppliedHolds(AppliedHoldManagementForm holdFrom) {

        List<AppliedHoldResult> holdResultList = new ArrayList<AppliedHoldResult>();
        List<AppliedHoldInfo> appliedHoldInfoList;

        try {
            appliedHoldInfoList = HoldsResourceLoader.getHoldService().getAppliedHoldsByPerson(holdFrom.getPerson().getId(), createContextInfo());

            for (AppliedHoldInfo appliedHoldInfo : appliedHoldInfoList) {

                AppliedHoldResult appliedHoldResult = new AppliedHoldResult();
                HoldIssueInfo holdIssue = HoldsResourceLoader.getHoldService().getHoldIssue(appliedHoldInfo.getHoldIssueId(), createContextInfo());
                //if(holdIssue.getMaintainHistoryOfApplicationOfHold()){
                    appliedHoldResult.setHoldIssue(holdIssue);
                    appliedHoldResult.setHoldName(holdIssue.getName());
                    appliedHoldResult.setCode(holdIssue.getHoldCode());
                    appliedHoldResult.setState(getStateInfo(appliedHoldInfo.getStateKey()).getName());
                    appliedHoldResult.setTypeKey(holdIssue.getTypeKey());
                    //Consequence lookup needed
                    appliedHoldResult.setConsequence("");
                    appliedHoldResult.setStartDate(appliedHoldInfo.getEffectiveDate());
                    appliedHoldResult.setEndDate(appliedHoldInfo.getExpirationDate());
                    appliedHoldResult.setStartTerm(holdIssue.getFirstApplicationTermId());
                    appliedHoldResult.setEndTerm(holdIssue.getLastApplicationTermId());

                    holdResultList.add(appliedHoldResult);
                //}
            }
        } catch (Exception e) {

            convertServiceExceptionsToUI(e);
        }
        return holdResultList;
    }

    public HoldIssueInfo searchHoldIssueByCode(String holdCode) {

        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                    PredicateFactory.in(HoldsConstants.HOLD_ISSUE_CODE, holdCode)));

            List<HoldIssueInfo> holdIssueInfos = HoldsResourceLoader.getHoldService().searchForHoldIssues(query, createContextInfo());
            return KSCollectionUtils.getOptionalZeroElement(holdIssueInfos);
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return null;
    }

}
