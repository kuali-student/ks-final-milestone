package org.kuali.student.enrollment.class2.acal.service.assembler;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.ArrayList;
import java.util.List;

public class AcademicCalendarAssembler implements DTOAssembler<AcademicCalendarInfo, AtpInfo> {

    private AtpService atpService;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    @Override
    public AcademicCalendarInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {

        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setId(atp.getId());
        acal.setName(atp.getName());
        acal.setDescr(atp.getDescr());
        acal.setStartDate(atp.getStartDate());
        acal.setEndDate(atp.getEndDate());
        acal.setTypeKey(atp.getTypeKey());
        acal.setStateKey(atp.getStateKey());
        acal.setMeta(atp.getMeta());
        acal.getAttributes().addAll(atp.getAttributes());
        acal.setAdminOrgId(atp.getAdminOrgId());
        acal.getHolidayCalendarIds().addAll(assembleHolidayCalendarIdsFromRelations(atp.getId(),
                AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY,
                context));
        return acal;
    }

    private List<String> assembleHolidayCalendarIdsFromRelations(String atpId,
            String relatedAtpType,
            ContextInfo context)
            throws AssemblyException {
        List<String> holidayCalendarIds = new ArrayList<String>();
        List<AtpAtpRelationInfo> atpRels;
        try {
            atpRels = atpService.getAtpAtpRelationsByAtp(atpId, context);
            for (AtpAtpRelationInfo atpRelInfo : atpRels) {
                if (atpRelInfo.getAtpId().equals(atpId)) {
                    if (atpRelInfo.getTypeKey().equals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY)) {
                        AtpInfo atp = atpService.getAtp(atpRelInfo.getRelatedAtpId(), context);
                        if (atp.getTypeKey().equals(relatedAtpType)) {
                            holidayCalendarIds.add(atpRelInfo.getRelatedAtpId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AssemblyException(e.getMessage());
        }
        return holidayCalendarIds;
    }

    @Override
    public AtpInfo disassemble(AcademicCalendarInfo acal, ContextInfo context) throws AssemblyException {
        AtpInfo atp = new AtpInfo();
        atp.setId(acal.getId());
        atp.setName(acal.getName());
        atp.setDescr(acal.getDescr());
        atp.setAdminOrgId(acal.getAdminOrgId());
        atp.setStartDate(acal.getStartDate());
        atp.setEndDate(acal.getEndDate());
        atp.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp.setStateKey(acal.getStateKey());
        atp.setMeta(acal.getMeta());
        atp.getAttributes().addAll(acal.getAttributes());

        return atp;
    }
}
