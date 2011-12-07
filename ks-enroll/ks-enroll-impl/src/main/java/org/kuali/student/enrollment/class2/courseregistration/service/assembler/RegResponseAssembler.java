package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.OperationStatusInfo;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

public class RegResponseAssembler implements DTOAssembler<RegResponseInfo, LprTransactionInfo> {

    @Override
    public RegResponseInfo assemble(LprTransactionInfo baseDTO, ContextInfo context) throws AssemblyException {
        RegResponseInfo regResponse = new RegResponseInfo();
        regResponse.setRegRequestId(baseDTO.getId());
        List<RegResponseItemInfo> resgRespItems = new ArrayList<RegResponseItemInfo>();
        for (LprTransactionItemInfo lprTransItems : baseDTO.getLprTransactionItems()) {
            resgRespItems.add(assembleItem(lprTransItems, context));
        }
        regResponse.setRegResponseItems(resgRespItems);
        regResponse.setOperationStatus(createOverallOperationStatus(baseDTO));
        return regResponse;
    }

    @Override
    public LprTransactionInfo disassemble(RegResponseInfo businessDTO, ContextInfo context) throws AssemblyException {

        return null;
    }

    public RegResponseItemInfo assembleItem(LprTransactionItemInfo baseDTO, ContextInfo context) {
        RegResponseItemInfo regResponseItem = new RegResponseItemInfo();
        if (baseDTO.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TO_WAITLIST_TYPE_KEY)) {
            regResponseItem.setCourseWaitlistEntryId(baseDTO.getLprTransactionItemResult().getResultingLprId());
        } else {
            regResponseItem.setCourseRegistrationId(baseDTO.getLprTransactionItemResult().getResultingLprId());
        }
        regResponseItem.setRegRequestItemId(baseDTO.getId());
        OperationStatusInfo operationStatus = new OperationStatusInfo();
        operationStatus.setStatus(baseDTO.getLprTransactionItemResult().getStatus());
        operationStatus.setMessages(baseDTO.getLprTransactionItemResult().getMessages());

        regResponseItem.setOperationStatus(operationStatus);
        return regResponseItem;
    }

    // TODO use
    private OperationStatusInfo createOverallOperationStatus(LprTransactionInfo lprTransaction) {
        OperationStatusInfo opStatusInfo = new OperationStatusInfo();
        boolean isSuccessful = true;
        for (LprTransactionItemInfo transItem : lprTransaction.getLprTransactionItems()) {
            if (transItem.getLprTransactionItemResult() != null) {
                if (transItem.getLprTransactionItemResult().getStatus() != "SUCCESS") {
                    isSuccessful = false;
                }
            }
        }
        if (isSuccessful)
            opStatusInfo.setStatus("SUCCESS");
        else
            opStatusInfo.setStatus("FAILURE");

        return opStatusInfo;
    }

}
