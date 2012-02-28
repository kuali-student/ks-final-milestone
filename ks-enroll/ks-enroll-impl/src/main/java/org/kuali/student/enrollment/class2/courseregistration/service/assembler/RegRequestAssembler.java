package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegResponseItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemResultInfo;
import org.kuali.student.enrollment.lpr.dto.RequestOptionInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.assembler.EntityDTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.GradingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.List;

public class RegRequestAssembler implements DTOAssembler<RegRequestInfo, LprTransactionInfo> {

    private LRCService lrcService;

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    @Override
    public RegRequestInfo assemble(LprTransactionInfo baseDTO, ContextInfo context) throws AssemblyException {
        RegRequestInfo regRequestInfo = new RegRequestInfo();
        EntityDTOAssembler<LprTransactionInfo, RegRequestInfo> commonAssembler = new EntityDTOAssembler<LprTransactionInfo, RegRequestInfo>();
        regRequestInfo = commonAssembler.assemble(baseDTO, regRequestInfo, context);
        regRequestInfo.setRequestorId(baseDTO.getRequestingPersonId());

        List<RegRequestItemInfo> regRequestItems = new ArrayList<RegRequestItemInfo>();
        for (LprTransactionItemInfo lprTransaction : baseDTO.getLprTransactionItems()) {
            regRequestItems.add(assembleItem(lprTransaction, context));

        }
        regRequestInfo.setRegRequestItems(regRequestItems);
        regRequestInfo.setTypeKey(baseDTO.getTypeKey());
        regRequestInfo.setStateKey(baseDTO.getStateKey());
        regRequestInfo.setId(baseDTO.getId());
        return regRequestInfo;
    }

    public RegRequestItemInfo assembleItem(LprTransactionItemInfo baseDTO, ContextInfo context) {
        RegRequestItemInfo regRequestItemInfo = new RegRequestItemInfo();
        EntityDTOAssembler<LprTransactionItemInfo, RegRequestItemInfo> commonAssembler = new EntityDTOAssembler<LprTransactionItemInfo, RegRequestItemInfo>();
        regRequestItemInfo = commonAssembler.assemble(baseDTO, regRequestItemInfo, context);
        regRequestItemInfo.setId(baseDTO.getId());
/*        regRequestItemInfo.setTypeKey(baseDTO.getTypeKey());
        regRequestItemInfo.setStateKey(baseDTO.getStateKey());*/

        // TODO - sambit . Post Core Slice, move this logic out of assembler and handle exception properly
        try {
            if (baseDTO.getResultValuesGroupKeys() != null) {

                for (String key : baseDTO.getResultValuesGroupKeys()) {
                    ResultValueInfo resultValue = lrcService.getResultValue(key, context);
                    if (resultValue.getTypeKey().equals(GradingServiceConstants.RESULT_VALUE_LETTER_GRADE_TYPE)
                            || resultValue.getTypeKey().equals(GradingServiceConstants.RESULT_VALUE_NUMBER_GRADE_TYPE)) {
                        regRequestItemInfo.setGradingOptionKey(resultValue.getKey());
                    }
                    if (resultValue.getTypeKey().equals(GradingServiceConstants.RESULT_VALUE_CREDIT_TYPE)) {
                        regRequestItemInfo.setCreditOptionKey(resultValue.getKey());

                    }
                }
            }
        } catch (Exception ex) {
            // Log exception
        }

        regRequestItemInfo.setCreditOptionKey(null);

        regRequestItemInfo.setStudentId(baseDTO.getPersonId());
        regRequestItemInfo.setExistingRegGroupId(baseDTO.getExistingLuiId());
        regRequestItemInfo.setNewRegGroupId(baseDTO.getNewLuiId());
        for (RequestOptionInfo option : baseDTO.getRequestOptions()) {
            if (option.getOptionKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_HOLD_UNTIL_LIST_OPTION_KEY))
                regRequestItemInfo.setOkToHoldList(Boolean.valueOf(option.getOptionValue()));

            else if (option.getOptionKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM__WAITLIST_OPTION_KEY))
                regRequestItemInfo.setOkToWaitlist(Boolean.valueOf(option.getOptionValue()));

            else
                break;

        }
        return regRequestItemInfo;
    }

    public List<LprTransactionInfo> disassembleList(List<RegRequestInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public List<RegRequestInfo> assembleList(List<LprTransactionInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LprTransactionInfo disassemble(RegRequestInfo businessDTO, ContextInfo context) throws AssemblyException {
        LprTransactionInfo lprTransactionInfo = new LprTransactionInfo();
        EntityDTOAssembler<RegRequestInfo, LprTransactionInfo> commonAssembler = new EntityDTOAssembler<RegRequestInfo, LprTransactionInfo>();
        lprTransactionInfo = commonAssembler.assemble(businessDTO, lprTransactionInfo, context);
        lprTransactionInfo.setId(businessDTO.getId());
        lprTransactionInfo.setRequestingPersonId(businessDTO.getRequestorId());
        List<LprTransactionItemInfo> lprtTransItems = new ArrayList<LprTransactionItemInfo>();
        for (RegRequestItemInfo item : businessDTO.getRegRequestItems()) {
            lprtTransItems.add(disassembleItem(item, null, context));
        }
        lprTransactionInfo.setLprTransactionItems(lprtTransItems);
        return lprTransactionInfo;

    }

    public LprTransactionItemInfo disassembleItem(RegRequestItemInfo regRequestItem, RegResponseItemInfo responseItem, ContextInfo context) {

        LprTransactionItemInfo lprTransactionItemInfo = new LprTransactionItemInfo();
        EntityDTOAssembler<RegRequestItemInfo, LprTransactionItemInfo> commonAssembler = new EntityDTOAssembler<RegRequestItemInfo, LprTransactionItemInfo>();
        lprTransactionItemInfo = commonAssembler.assemble(regRequestItem, lprTransactionItemInfo, context);
        lprTransactionItemInfo.setId(regRequestItem.getId());
        lprTransactionItemInfo.setExistingLuiId(regRequestItem.getExistingRegGroupId());
        lprTransactionItemInfo.setNewLuiId(regRequestItem.getNewRegGroupId());
        lprTransactionItemInfo.setName(regRequestItem.getName());
        lprTransactionItemInfo.setPersonId(regRequestItem.getStudentId());
        lprTransactionItemInfo.setGroupId(regRequestItem.getId());
        if (responseItem != null) {
            lprTransactionItemInfo.setLprTransactionResult(disassembleTransItemResult(responseItem));
        }

        return lprTransactionItemInfo;
    }

    public LprTransactionItemResultInfo disassembleTransItemResult(RegResponseItemInfo responseItem) {
        return new LprTransactionItemResultInfo();
    }
}
