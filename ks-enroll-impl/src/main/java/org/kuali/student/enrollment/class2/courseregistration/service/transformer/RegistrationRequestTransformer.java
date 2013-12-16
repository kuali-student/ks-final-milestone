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
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import java.util.ArrayList;

/**
 * Converts Reg Request to LPR Transaction
 *
 * @author Kuali Student Team
 */
public class RegistrationRequestTransformer {
    public static LprTransactionInfo regRequest2LprTransaction(RegistrationRequestInfo request)
            throws OperationFailedException {
        LprTransactionInfo lprTransaction = new LprTransactionInfo();
        lprTransaction.setAtpId(request.getTermId());
        lprTransaction.setName(request.getName());
        lprTransaction.setDescr(request.getDescr());
        lprTransaction.setRequestingPersonId(request.getRequestorId());
        lprTransaction.setLprTransactionItems(new ArrayList<LprTransactionItemInfo>());
        for (RegistrationRequestItemInfo item: request.getRegistrationRequestItems()) {
            LprTransactionItemInfo lprItem = regRequestItem2LprTransactionItem(item);
            lprTransaction.getLprTransactionItems().add(lprItem);
        }
        return lprTransaction;
    }

    public static LprTransactionItemInfo regRequestItem2LprTransactionItem(RegistrationRequestItemInfo requestItem) throws OperationFailedException {
        LprTransactionItemInfo item = new LprTransactionItemInfo();
        item.setId(requestItem.getId());
        item.setStateKey(requestItem.getStateKey());
        item.setDescr(requestItem.getDescr());
        item.setPersonId(requestItem.getStudentId());
        if (requestItem.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
            requestItem.getCredits();

        } else {
            // Change once other types are supported
            throw new OperationFailedException("Unrecognized type");
        }
        // Credits and grading options (how to save?)

        return item;
    }
}
