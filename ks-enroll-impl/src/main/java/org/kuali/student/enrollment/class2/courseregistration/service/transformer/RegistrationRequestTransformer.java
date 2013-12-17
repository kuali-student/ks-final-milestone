/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 12/13/13
 */
package org.kuali.student.enrollment.class2.courseregistration.service.transformer;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemRequestOptionInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Reg Request to LPR Transaction
 *
 * @author Kuali Student Team
 */
public class RegistrationRequestTransformer {
    public static final String OK_TO_WAITLIST = "kuali.lpr.trans.item.option.oktowaitlist";
    public static final String OK_TO_HOLD_UNTIL_LIST = "kuali.lpr.trans.item.option.oktoholduntillist";

    public static LprTransactionInfo regRequest2LprTransaction(RegistrationRequestInfo request)
            throws OperationFailedException {
        LprTransactionInfo lprTransaction = new LprTransactionInfo();
        lprTransaction.setAtpId(request.getTermId());
        lprTransaction.setName(request.getName());
        lprTransaction.setDescr(request.getDescr());
        lprTransaction.setRequestingPersonId(request.getRequestorId());
        lprTransaction.setLprTransactionItems(new ArrayList<LprTransactionItemInfo>());
        for (RegistrationRequestItemInfo requestItem: request.getRegistrationRequestItems()) {
            LprTransactionItemInfo lprItem = new LprTransactionItemInfo();
            regRequestItem2LprTransactionItem(requestItem, lprItem);
            lprTransaction.getLprTransactionItems().add(lprItem);
        }
        return lprTransaction;
    }

    public static LprTransactionItemInfo regRequestItem2LprTransactionItem(RegistrationRequestItemInfo requestItem,
                                                                           LprTransactionItemInfo item)
            throws OperationFailedException {
        // Inherited fields
        item.setId(requestItem.getId());
        item.setStateKey(requestItem.getStateKey());
        item.setTypeKey(requestItem.getTypeKey());
        item.setDescr(requestItem.getDescr());
        item.setMeta(requestItem.getMeta());
        item.setName(requestItem.getName());
        // Fields in LPRTransactionItemInfo: personId, transactionId, newLuiId, existingLuiId,
        // resultValuesGroupKeys, requestOptions, lprTransactionItemResult
        item.setPersonId(requestItem.getStudentId());
        item.setTransactionId(requestItem.getRegistrationRequestId());
        item.setNewLuiId(requestItem.getNewRegistrationGroupId());
        item.setExistingLuiId(requestItem.getExistingRegistrationGroupId());
        if (item.getResultValuesGroupKeys() != null) {
            item.getResultValuesGroupKeys().clear();
        } else {
            item.setResultValuesGroupKeys(new ArrayList<String>());
        }
        String creditsId = requestItem.getCredits(); // For now, assume it's an RVG option with a single credit
        if (creditsId != null) {
            // This gets more complex if it's actual credits
            item.getResultValuesGroupKeys().add(creditsId);
        }
        String gradingOptionId = requestItem.getGradingOptionId();
        if (gradingOptionId != null) {
            item.getResultValuesGroupKeys().add(gradingOptionId);
        }
        if (item.getRequestOptions() == null) {
            item.setRequestOptions(new ArrayList<LprTransactionItemRequestOptionInfo>());
        }
        Boolean okToWaitlist = requestItem.getOkToWaitlist();
        LprTransactionItemRequestOptionInfo okToWaitlistOption =
                findOptionByKey(OK_TO_WAITLIST, item.getRequestOptions());
        if (okToWaitlistOption == null) {
            okToWaitlistOption = new LprTransactionItemRequestOptionInfo();
            okToWaitlistOption.setOptionKey(OK_TO_WAITLIST);
            item.getRequestOptions().add(okToWaitlistOption);
        }
        okToWaitlistOption.setOptionValue(convertBooleanToString(okToWaitlist));

        Boolean okToHoldUntilList = requestItem.getOkToHoldUntilList();
        LprTransactionItemRequestOptionInfo okToHoldUntilListOption =
                findOptionByKey(OK_TO_HOLD_UNTIL_LIST, item.getRequestOptions());
        if (okToHoldUntilListOption == null) {
            okToHoldUntilListOption = new LprTransactionItemRequestOptionInfo();
            okToHoldUntilListOption.setOptionKey(OK_TO_HOLD_UNTIL_LIST);
            item.getRequestOptions().add(okToHoldUntilListOption);
        }
        okToWaitlistOption.setOptionValue(convertBooleanToString(okToHoldUntilList));
        return item;
    }

    protected static LprTransactionItemRequestOptionInfo findOptionByKey(String key,
                                                                         List<LprTransactionItemRequestOptionInfo> options) {
        for (LprTransactionItemRequestOptionInfo option: options) {
            if (option.getOptionKey().equals(key)) {
                return option;
            }
        }
        return null;
    }

    protected static String convertBooleanToString(Boolean value) {
        // This should eventually be moved elsewhere
        if (value == null) {
            return "null";
        } else if (value == true) {
            return Boolean.TRUE.toString();
        } else {
            return Boolean.FALSE.toString();
        }
    }
}
