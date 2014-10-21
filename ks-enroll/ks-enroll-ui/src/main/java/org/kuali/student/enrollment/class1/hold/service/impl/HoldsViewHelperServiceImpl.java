package org.kuali.student.enrollment.class1.hold.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.common.util.security.SecurityUtils;
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
import org.kuali.student.enrollment.class1.hold.util.HoldsUtil;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        try {
            Map<String, String> searchCriteria = new HashMap<String, String>();
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_NAME, holdIssueFrom.getName());
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_HOLD_CODE, holdIssueFrom.getCode());
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_TYPE_KEY, holdIssueFrom.getTypeKey());
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_STATE_KEY, holdIssueFrom.getState());
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_ORG_ID, holdIssueFrom.getOrganizationId());
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_DESCR_PLAIN, holdIssueFrom.getDescr());

            QueryByCriteria.Builder query = HoldsUtil.buildQueryByCriteria(searchCriteria);

            return HoldsUtil.createHoldIssueResultList(HoldsResourceLoader.getHoldService().searchForHoldIssues(query.build(), createContextInfo()));

        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return null;
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
     * Only released holds that have the maintain history option enabled will be displayed.
     * All active holds will be displayed regardless of the maintain history option.
     *
     * @param personId
     * @return List holdResultList
     */
    @Override
    public List<AppliedHoldResult> searchAppliedHoldsByPerson(String personId) {

        List<AppliedHoldResult> holdResultList = new ArrayList<AppliedHoldResult>();

        try {
            List<AppliedHoldInfo> appliedHoldInfoList = HoldsResourceLoader.getHoldService().getAppliedHoldsByPerson(personId, createContextInfo());

            for (AppliedHoldInfo appliedHoldInfo : appliedHoldInfoList) {

                HoldIssueInfo holdIssue = HoldsResourceLoader.getHoldService().getHoldIssue(appliedHoldInfo.getHoldIssueId(), createContextInfo());
                //Check if the hold has the Maintain History Option set to true when the hold is in released state
                if (!holdIssue.getMaintainHistoryOfApplicationOfHold() && appliedHoldInfo.getStateKey().matches(HoldServiceConstants.APPLIED_HOLD_EXPIRED_STATE_KEY)) {
                    continue;
                }
                if (appliedHoldInfo.getStateKey().matches(HoldServiceConstants.APPLIED_HOLD_DELETED_STATE_KEY)) {
                    continue;
                }

                AppliedHoldResult appliedHoldResult = new AppliedHoldResult();
                appliedHoldResult.setId(appliedHoldInfo.getId());
                appliedHoldResult.setHoldIssue(holdIssue);
                appliedHoldResult.setHoldName(holdIssue.getName());
                appliedHoldResult.setCode(holdIssue.getHoldCode());
                appliedHoldResult.setState(getStateInfo(appliedHoldInfo.getStateKey()).getName());
                appliedHoldResult.setTypeKey(holdIssue.getTypeKey());
                //Consequence lookup needed
                appliedHoldResult.setConsequence("");
                appliedHoldResult.setStartDate(appliedHoldInfo.getEffectiveDate());
                appliedHoldResult.setEndDate(appliedHoldInfo.getExpirationDate());
                appliedHoldResult.setStartTerm(getTermCodeForId(appliedHoldInfo.getApplicationEffectiveTermId()));
                appliedHoldResult.setEndTerm(getTermCodeForId(appliedHoldInfo.getApplicationExpirationTermId()));

                holdResultList.add(appliedHoldResult);
            }
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }
        return holdResultList;
    }

    @Override
    public boolean isAuthorized(String holdIssueId, String function) {
        return HoldsResourceLoader.getHoldIssueAuthorizingOrgFacade().canPerformFunction(SecurityUtils.getCurrentUserId(),
                holdIssueId, function);
    }

    @Override
    public HoldIssueInfo searchHoldIssueByCode(String holdCode) {

        try {
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(PredicateFactory.and(
                    PredicateFactory.in(HoldsConstants.HOLD_ISSUE_HOLD_CODE, holdCode)));

            List<HoldIssueInfo> holdIssueInfos = HoldsResourceLoader.getHoldService().searchForHoldIssues(query, createContextInfo());
            if (holdIssueInfos.size() == 0 || holdIssueInfos.isEmpty()) {
                GlobalVariables.getMessageMap().putError(HoldsConstants.APPLIED_HOLDS_PROP_NAME_CODE, HoldsConstants.APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_INVALID);
            } else {
                return KSCollectionUtils.getOptionalZeroElement(holdIssueInfos);
            }
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
        }
        return new HoldIssueInfo();
    }

    /**
     * This method is used for the hold code suggest field on the input section on the client.
     *
     * @param holdCode
     * @return List<String> holdIssueCodeList
     */
    public List<String> retrieveHoldCodes(String holdCode) {

        List<String> holdIssueCodeList = new ArrayList<String>();

        if (holdCode.length() >= 3) {
            try {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.like(HoldsConstants.HOLD_ISSUE_HOLD_CODE, "%" + holdCode + "%"));

                List<HoldIssueInfo> holdIssueInfos = HoldsResourceLoader.getHoldService().searchForHoldIssues(
                        qbcBuilder.build(), createContextInfo());

                for (HoldIssueInfo holdIssueInfo : holdIssueInfos) {
                    holdIssueCodeList.add(holdIssueInfo.getHoldCode());
                }
            } catch (Exception e) {
                convertServiceExceptionsToUI(e);
            }

            Collections.sort(holdIssueCodeList);
        }

        return holdIssueCodeList;
    }

    public String getTermCodeForId(String termId) {
        if (termId == null) {
            return StringUtils.EMPTY;
        }

        try {
            TermInfo term = HoldsResourceLoader.getAcademicCalendarService().getTerm(termId, ContextUtils.createDefaultContextInfo());
            return term.getCode();
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

        return StringUtils.EMPTY;
    }
}
