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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemRequestOptionInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts Reg Request to LPR Transaction
 *
 * @author Kuali Student Team
 */
public class RegistrationRequestTransformer {
    private static LRCService lrcService;

    public static final String OK_TO_WAITLIST = "kuali.lpr.trans.item.option.oktowaitlist";
    public static final String OK_TO_HOLD_UNTIL_LIST = "kuali.lpr.trans.item.option.oktoholduntillist";

    public static LprTransactionInfo regRequest2LprTransaction(RegistrationRequestInfo request, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException, InvalidParameterException, DoesNotExistException {
        LprTransactionInfo lprTransaction = new LprTransactionInfo();
        lprTransaction.setTypeKey(request.getTypeKey());
        lprTransaction.setStateKey(request.getStateKey());
        lprTransaction.setAtpId(request.getTermId());
        lprTransaction.setName(request.getName());
        lprTransaction.setDescr(request.getDescr());
        lprTransaction.setRequestingPersonId(request.getRequestorId());

        lprTransaction.setLprTransactionItems(new ArrayList<LprTransactionItemInfo>());
        for (RegistrationRequestItemInfo requestItem : request.getRegistrationRequestItems()) {
            LprTransactionItemInfo lprItem = regRequestItem2LprTransactionItem(requestItem, context);
            lprTransaction.getLprTransactionItems().add(lprItem);
        }
        return lprTransaction;
    }

    public static LprTransactionItemInfo regRequestItem2LprTransactionItem(RegistrationRequestItemInfo requestItem,
                                                                           ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        // Currently, attributes not saved.
        LprTransactionItemInfo item = new LprTransactionItemInfo();
        // Inherited fields
        item.setId(requestItem.getId());
        item.setStateKey(requestItem.getStateKey());
        item.setTypeKey(requestItem.getTypeKey());
        item.setDescr(requestItem.getDescr());
        item.setMeta(requestItem.getMeta());
        item.setName(requestItem.getName());
        // Fields in LPRTransactionItemInfo: personId, transactionId, newLuiId, existingLuiId,
        // resultValuesGroupKeys, requestOptions, lprTransactionItemResult
        item.setPersonId(requestItem.getPersonId());
        item.setTransactionId(requestItem.getRegistrationRequestId());
        item.setLuiId(requestItem.getRegistrationGroupId());
        item.setExistingLprId(requestItem.getExistingCourseRegistrationId());
        item.getResultValuesGroupKeys().clear();

        KualiDecimal credits = requestItem.getCredits(); // For now, assume it's an RVG option with a single credit
        if (credits != null) {
            String creditStr = credits.bigDecimalValue().setScale(1).toPlainString();

            ResultValueInfo resultValueInfo =
                    getLrcService().getResultValueForScaleAndValue(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE,
                            creditStr, context);
            // This gets more complex if it's actual credits
            item.getResultValuesGroupKeys().add(resultValueInfo.getKey());
        }
        String gradingOptionId = requestItem.getGradingOptionId();
        if (gradingOptionId != null) {
            item.getResultValuesGroupKeys().add(gradingOptionId);
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

    public static RegistrationRequestInfo  lprTransaction2RegRequest(LprTransactionInfo lprTransaction,
                                                                     ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            InvalidParameterException, DoesNotExistException {
        // Currently, saving of attributes not yet implemented.
        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setId(lprTransaction.getId());
        request.setTermId(lprTransaction.getAtpId());
        request.setName(lprTransaction.getName());
        request.setDescr(lprTransaction.getDescr());
        request.setRequestorId(lprTransaction.getRequestingPersonId());
        request.setTypeKey(lprTransaction.getTypeKey());
        request.setStateKey(lprTransaction.getStateKey());
        request.setMeta(lprTransaction.getMeta());

        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        for (LprTransactionItemInfo transactionItem : lprTransaction.getLprTransactionItems()) {
            RegistrationRequestItemInfo reqItem = lprTransactionItem2regRequestItem(transactionItem, context);
            request.getRegistrationRequestItems().add(reqItem);
        }
        return request;
    }

    public static RegistrationRequestItemInfo lprTransactionItem2regRequestItem(LprTransactionItemInfo item,
                                                                                ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        RegistrationRequestItemInfo requestItem = new RegistrationRequestItemInfo();
        // Inherited fields
        requestItem.setId(item.getId());
        requestItem.setStateKey(item.getStateKey());
        requestItem.setTypeKey(item.getTypeKey());
        requestItem.setDescr(item.getDescr());
        requestItem.setMeta(item.getMeta());
        requestItem.setName(item.getName());
        // Fields in LPRTransactionItemInfo: personId, transactionId, newLuiId, existingLuiId,
        // resultValuesGroupKeys, requestOptions, lprTransactionItemResult
        requestItem.setPersonId(item.getPersonId());
        requestItem.setRegistrationRequestId(item.getTransactionId());
        requestItem.setRegistrationGroupId(item.getLuiId());
        requestItem.setExistingCourseRegistrationId(item.getExistingLprId());

        // Admittedly, a hacky way of doing things, so open for better ways to do this
        for (String s : item.getResultValuesGroupKeys()) {
            if (s.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE)) { // "kuali.resultComponent.grade"
                requestItem.setGradingOptionId(s);
            } else if (s.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) { // "kuali.result.value.credit.degree"
            	// FIXME KSENROLL-11466
                // requestItem.setCredits(s);
                ResultValueInfo resultValueInfo = getLrcService().getResultValue(s, context);
                String creditStr = resultValueInfo.getNumericValue();
                KualiDecimal credit = new KualiDecimal(creditStr);
                requestItem.setCredits(credit);
            } else {
            	// added as a place holder until KSENROLL-11466 is worked on.
            	// the inverse will write just the decimal value into the rvg so unpack it here.
            	requestItem.setCredits(new KualiDecimal(s));
            }
        }

        for (LprTransactionItemRequestOptionInfo option : item.getRequestOptions()) {
            if (option.getOptionKey().equals(OK_TO_WAITLIST)) {
                requestItem.setOkToWaitlist(convertStringToBoolean(option.getOptionValue()));
            } else if (option.getOptionKey().equals(OK_TO_HOLD_UNTIL_LIST)) {
                requestItem.setOkToHoldUntilList(convertStringToBoolean(option.getOptionValue()));
            }
        }
        return requestItem;
    }

    protected static LprTransactionItemRequestOptionInfo findOptionByKey(String key,
                                                                         List<LprTransactionItemRequestOptionInfo> options) {
        for (LprTransactionItemRequestOptionInfo option : options) {
            if (option.getOptionKey().equals(key)) {
                return option;
            }
        }
        return null;
    }

    protected static Boolean convertStringToBoolean(String value) {
        if (value == null) {
            return null;
        } else if (value.equals(Boolean.TRUE.toString())) {
            return true;
        } else if (value.equals(Boolean.FALSE.toString())) {
            return true;
        }
        return null; // May not be the best thing to do, perhaps throw exception?
    }

    protected static String convertBooleanToString(Boolean value) {
        // This should eventually be moved elsewhere
        if (value == null) {
            return "null";
        } else if (value) {
            return Boolean.TRUE.toString();
        } else {
            return Boolean.FALSE.toString();
        }
    }


    protected static LRCService getLrcService() {
        if (lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE,
                    LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }
}
