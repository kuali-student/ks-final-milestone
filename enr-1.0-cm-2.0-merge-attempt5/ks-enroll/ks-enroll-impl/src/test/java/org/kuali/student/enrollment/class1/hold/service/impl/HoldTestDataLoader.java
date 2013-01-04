package org.kuali.student.enrollment.class1.hold.service.impl;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoldTestDataLoader {

    public HoldTestDataLoader() {
    }

    public HoldTestDataLoader(HoldService holdService) {
        this.holdService = holdService;
    }

    public HoldService getHoldService() {
        return holdService;
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }

    private HoldService holdService;
    private static String principalId = HoldTestDataLoader.class.getSimpleName();
    private ContextInfo context;

    public void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException,
            AlreadyExistsException, CircularRelationshipException {

        context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);

        loadHoldIssue(HoldTestConstants.HOLD_ISSUE_1, "Test issue 1", HoldTestConstants.ISSUE_TYPE, HoldTestConstants.DRAFT_STATE, "Formatted", "Plain", HoldTestConstants.ORG_1);
        loadHoldIssue(HoldTestConstants.HOLD_ISSUE_2, "Test issue 2", HoldTestConstants.ISSUE_TYPE, HoldTestConstants.DRAFT_STATE, "Formatted", "Plain", HoldTestConstants.ORG_1);
        loadHoldIssue(HoldTestConstants.HOLD_ISSUE_3, "Test issue 3", HoldTestConstants.ISSUE_TYPE, HoldTestConstants.DRAFT_STATE, "Formatted", "Plain", HoldTestConstants.ORG_2);
        loadHoldIssue(HoldTestConstants.HOLD_ISSUE_4, "Test issue 4", HoldTestConstants.ISSUE_TYPE, HoldTestConstants.DRAFT_STATE, "Formatted", "Plain", HoldTestConstants.ORG_2);

        //holds can be added here later as required for testing
        //loadHold("kuali.hold.type.financialHold","kuali.hold.state.draft","Formatted","Plain","HOLDISSUE-1","testFakePerson1",str2Date("2011-01-01 00:00:00.0",context), new Date());
    }

    private void loadHoldIssue(String id, String name, String typeKey, String stateKey, String descrFormatted, String descrPlain, String orgID) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        HoldIssueInfo issueInfo = new HoldIssueInfo();
        issueInfo.setId(id);
        issueInfo.setStateKey(stateKey);
        issueInfo.setTypeKey(typeKey);
        issueInfo.setOrganizationId(orgID);
        issueInfo.setDescr(new RichTextInfo(descrPlain, descrFormatted));
        issueInfo.setName(name);
        holdService.createHoldIssue(issueInfo.getTypeKey(), issueInfo, context);
    }

    private void loadHold(String typeKey, String stateKey, String descrFormatted, String descrPlain, String issueID, String personID, Date effectiveDate, Date releaseDate) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        AppliedHoldInfo holdInfo = new AppliedHoldInfo();
        holdInfo.setTypeKey(typeKey);
        holdInfo.setStateKey(stateKey);
        holdInfo.setDescr(new RichTextInfo(descrPlain, descrFormatted));
        holdInfo.setHoldIssueId(issueID);
        holdInfo.setPersonId(personID);
        holdInfo.setEffectiveDate(effectiveDate);
        holdInfo.setReleasedDate(releaseDate);
        holdService.createAppliedHold(personID, issueID, typeKey, holdInfo, context);
    }

    public List<AttributeInfo> createAttributes() {
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        attributes.add((new AttributeInfo(HoldTestConstants.TEST_KEY_2, HoldTestConstants.TEST_VALUE_2)));
        attributes.add((new AttributeInfo(HoldTestConstants.TEST_KEY_2, HoldTestConstants.TEST_VALUE_2)));
        return attributes;
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        try {
            Date date = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(str);
            return date;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}
