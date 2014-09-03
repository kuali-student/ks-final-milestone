package org.kuali.student.enrollment.class1.lui.service.impl;

import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LuiServiceDataLoader {

    public static String RG_ID_1 = "RG-1";

    public LuiServiceDataLoader() {
    }

    public LuiServiceDataLoader(LuiService luiService) {
        this.luiService = luiService;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    private LuiService luiService;
    private static String principalId = LuiTestDataLoader.class.getSimpleName();

    public void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException,
            AlreadyExistsException, CircularRelationshipException {
        loadLui("Lui-1", "Lui one", "cluId1", "atpId1", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-2", "Lui rwo", "cluId2", "atpId2", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 201</p>", "Lui Desc 201", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-3", "Lui three", "cluId3", "atpId3", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 301</p>", "Lui Desc 301 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-4", "Lui four", "cluId4", "atpId4", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 401</p>", "Lui Desc 401 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-5", "Lui five", "cluId5", "atpId5", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-Lab2", "Lui Lab2", "cluId5", "atpId5", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-6", "Lui six", "cluId6", "20122", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 601</p>", "Lui Desc 601", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-7", "Lui seven", "cluId7", "20122", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-8", "Lui eight", "cluId8", "atpId8", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-9", "Lui nine", "cluId9", "atpId9", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 901</p>", "Lui Desc 901", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-10", "Lui ten", "cluId10", "atpId10", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 1001</p>", "Lui Desc 1001", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-EO-1", "Lui EO one", "cluId10", "examPeriod100", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, "kuali.lui.exam.offering.state.draft", "<p>Lui Desc 901</p>", "Lui Desc 901", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");

        loadLui("Lui-11", "Lui one", "cluId1", "atpId1", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-12", "Lui rwo", "cluId2", "atpId2", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 201</p>", "Lui Desc 201", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-13", "Lui three", "cluId3", "atpId3", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 301</p>", "Lui Desc 301 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-14", "Lui four", "cluId4", "atpId4", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 401</p>", "Lui Desc 401 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-15", "Lui five", "cluId5", "atpId5", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-Lab3", "Lui Lab2", "cluId5", "atpId5", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-16", "Lui six", "cluId6", "20122", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 601</p>", "Lui Desc 601", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-17", "Lui seven", "cluId7", "20122", LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-18", "Lui eight", "cluId8", "atpId8", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-19", "Lui nine", "cluId9", "atpId9", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 901</p>", "Lui Desc 901", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-20", "Lui ten", "cluId10", "atpId10", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, "<p>Lui Desc 1001</p>", "Lui Desc 1001", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-EO-2", "Lui EO two", "cluIdEO2", "examPeriod100", ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, "kuali.lui.exam.offering.state.draft", "<p>Lui Desc 901</p>", "Lui Desc 901", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");


        
        loadLuiLuiRel("LUILUIREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-1 Plain", "Lui-1", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-2");
        loadLuiLuiRel("LUILUIREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-2 Plain", "Lui-3", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-4");
        loadLuiLuiRel("LUILUIREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-3 Plain", "Lui-5", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-2");
        loadLuiLuiRel("LUILUIREL-4", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-4 Formatted</p>", "LUILUIREL-4 Plain", "Lui-6", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-2");
        loadLuiLuiRel("LUILUIREL-5", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-5 Formatted</p>", "LUILUIREL-5 Plain", "Lui-1", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, "Lui-6");
        loadLuiLuiRel("LUILUIREL-6", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-6", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-5");
//        loadLuiLuiRel("LUILUIREL-7", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-6", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-Lab2");
        loadLuiLuiRel("LUILUIREL-8", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-7", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-Lab2");
        loadLuiLuiRel("LUILUIREL-9", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-1", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, "Lui-7");
        loadLuiLuiRel("LUILUIREL-10", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-10 Formatted</p>", "LUILUIREL-10 Plain", "Lui-6", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-8");
        loadLui("RG-1", "1001", "cluId10", "examPeriod100", LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY, "<p>REG GROUP DESC</p>", "REG GROUP DESC", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");

        loadLuiLuiRel("LUILUIREL-11", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-11 Plain", "Lui-11", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-12");
        loadLuiLuiRel("LUILUIREL-12", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-12 Plain", "Lui-13", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-14");
        loadLuiLuiRel("LUILUIREL-13", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-13 Plain", "Lui-15", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-12");
        loadLuiLuiRel("LUILUIREL-14", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-4 Formatted</p>", "LUILUIREL-14 Plain", "Lui-16", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-12");
        loadLuiLuiRel("LUILUIREL-15", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-5 Formatted</p>", "LUILUIREL-15 Plain", "Lui-11", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, "Lui-16");
        loadLuiLuiRel("LUILUIREL-16", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-16 Plain", "Lui-16", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-15");
        loadLuiLuiRel("LUILUIREL-18", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-16 Plain", "Lui-17", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-Lab3");
        loadLuiLuiRel("LUILUIREL-19", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-16 Plain", "Lui-11", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, "Lui-17");
        loadLuiLuiRel("LUILUIREL-20", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-10 Formatted</p>", "LUILUIREL-20 Plain", "Lui-16", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-18");

        loadLuiLuiRel("LUILUIREL-21", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-11 Formatted</p>", "LUILUIREL-11 Plain", "RG-1", LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, "Lui-11");
        loadLuiLuiRel("LUILUIREL-22", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-12 Formatted</p>", "LUILUIREL-12 Plain", "Lui-16", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, "RG-1");


//        loadLuiLuiRel("LUILUIREL-11", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-10 Formatted</p>", "LUILUIREL-10 Plain", "Lui-7", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui-8");
    }

    private void loadLui(String id,
                         String name,
                         String cluId,
                         String atpId,
                         String type,
                         String state,
                         String descrFormatted,
                         String descrPlain,
                         String effectiveDate,
                         String expirationDate,
                         Integer maxSeats,
                         Integer minSeats,
                         String refUrl)
            throws DoesNotExistException, InvalidParameterException,
                MissingParameterException, OperationFailedException, PermissionDeniedException,
                DataValidationErrorException, ReadOnlyException, AlreadyExistsException {
        LuiInfo luiInfo = new LuiInfo();
        luiInfo.setId(id);
        luiInfo.setCluId(cluId);
        luiInfo.setAtpId(atpId);
        luiInfo.setName(name);
        luiInfo.setTypeKey(type);
        luiInfo.setStateKey(state);
        luiInfo.setEffectiveDate(str2Date(effectiveDate, id));
        luiInfo.setExpirationDate(str2Date(expirationDate, id));
        luiInfo.setMaximumEnrollment(maxSeats);
        luiInfo.setMinimumEnrollment(minSeats);
        luiInfo.setReferenceURL(refUrl);
        luiInfo.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));
        luiInfo.setOfficialIdentifier(this.getOfficialIdentifier(id));
        luiInfo.setAlternateIdentifiers(this.getAlternateIdentifiers(id));

        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        luiService.createLui(luiInfo.getCluId(), luiInfo.getAtpId(), luiInfo.getTypeKey(), luiInfo, context);
    }

    private LuiIdentifierInfo getOfficialIdentifier(String luiId) {
        loadIdentifiers();
        if (!identifiers.containsKey(luiId)) {
            return null;
        }
        for (LuiIdentifierInfo info : identifiers.get(luiId)) {
            if (info.getTypeKey().equals(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY)) {
                return info;
            }
        }
        return null;
    }

    private List<LuiIdentifierInfo> getAlternateIdentifiers(String luiId) {
        loadIdentifiers();
        List<LuiIdentifierInfo> list = new ArrayList<LuiIdentifierInfo>();
        if (!identifiers.containsKey(luiId)) {
            return list;
        }
        for (LuiIdentifierInfo info : identifiers.get(luiId)) {
            if (!info.getTypeKey().equals(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY)) {
                list.add(info);
            }
        }
        return list;
    }

    private Map<String, List<LuiIdentifierInfo>> identifiers = null;

    private void loadIdentifiers() {
        if (identifiers != null) {
            return;
        }
        identifiers = new LinkedHashMap<String, List<LuiIdentifierInfo>>();
        addIdentifier("LUI-IDENT-1", "CHEM123", "CHEM", "123", "Chemistry 123", "Chem 123", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-1");
        addIdentifier("LUI-IDENT-2", "CHEM456", "CHEM", "456", "Chemistry 456", "Chem 456", LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY, null, "Lui-1");
        addIdentifier("LUI-IDENT-3", "BIO123", "BIO", "123", "Biology 123", "Bio 123", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-2");
        addIdentifier("LUI-IDENT-4", "BIO456", "BIO", "456", "Biology 456", "Bio 456", LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY, null, "Lui-2");
        addIdentifier("LUI-IDENT-5", "GEOG123", "GEOG", "123", "Geography 123", "Geog 123", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-3");
        addIdentifier("LUI-IDENT-6", "MATH123", "MATH", "123", "Mathematics 123", "Math 123", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-4");
        addIdentifier("LUI-IDENT-7", "MATH123", "MATH", "456", "Mathematics 456", "Math 456", LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY, null, "Lui-4");
        addIdentifier("LUI-IDENT-9", "EXAMOFFERING9", "MATH", "123", "Exam Offering 9", "Exam Offering 9", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-9");
        addIdentifier("LUI-IDENT-10", "EXAMOFFERING1", "MATH", "456", "Exam Offering 1", "Exam Offering 1", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-EO-1");
        addIdentifier("LUI-IDENT-11", "EXAMOFFERING2", "MATH", "466", "Exam Offering 2", "Exam Offering 2", LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY, null, "Lui-EO-2");
    }

    private void addIdentifier(String id,
                               String code,
                               String division,
                               String suffixCode,
                               String longName,
                               String shortName,
                               String type,
                               String variation,
                               String luiId) {
        LuiIdentifierInfo info = new LuiIdentifierInfo();
        info.setId(id);
        info.setCode(code);
        info.setDivision(division);
        info.setSuffixCode(suffixCode);
        info.setLongName(longName);
        info.setShortName(shortName);
        info.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        info.setTypeKey(type);
        info.setVariation(variation);
        if (!identifiers.containsKey(luiId)) {
            identifiers.put(luiId, new ArrayList<LuiIdentifierInfo>());
        }
        identifiers.get(luiId).add(info);
    }

    private void loadLuiLuiRel(String id,
                               String effectiveDate,
                               String expirationDate,
                               String state,
                               String descrFormatted,
                               String descrPlain,
                               String luiId,
                               String type,
                               String relatedLuiId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, AlreadyExistsException,
            CircularRelationshipException {
        LuiLuiRelationInfo info = new LuiLuiRelationInfo();
        info.setId(id);
        info.setLuiId(luiId);
        info.setRelatedLuiId(relatedLuiId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setEffectiveDate(str2Date(effectiveDate, id));
        info.setExpirationDate(str2Date(expirationDate, id));
        info.setDescr(new RichTextHelper().toRichTextInfo(descrPlain, descrFormatted));

        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        luiService.createLuiLuiRelation(info.getLuiId(), info.getRelatedLuiId(), info.getTypeKey(), info, context);
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
