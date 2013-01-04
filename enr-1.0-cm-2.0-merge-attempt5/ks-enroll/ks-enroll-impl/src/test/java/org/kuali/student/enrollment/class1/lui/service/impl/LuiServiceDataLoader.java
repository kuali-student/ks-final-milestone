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
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LuiServiceDataLoader {

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
        loadLui("Lui-1", "Lui one", "cluId1", "atpId1", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-2", "Lui rwo", "cluId2", "atpId2", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 201</p>", "Lui Desc 201", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-3", "Lui three", "cluId3", "atpId3", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 301</p>", "Lui Desc 301 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-4", "Lui four", "cluId4", "atpId4", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 401</p>", "Lui Desc 401 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-5", "Lui five", "cluId5", "atpId5", "kuali.lui.type.activity.offering.lab", "kuali.lui.state.draft", "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-Lab2", "Lui Lab2", "cluId5", "atpId5", "kuali.lui.type.activity.offering.lab", "kuali.lui.state.draft", "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-6", "Lui six", "cluId6", "20122", "kuali.lui.type.course.format.offering", "kuali.lui.state.draft", "<p>Lui Desc 601</p>", "Lui Desc 601", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-7", "Lui seven", "cluId7", "20122", "kuali.lui.type.course.format.offering", "kuali.lui.state.draft", "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");
        loadLui("Lui-8", "Lui eight", "cluId8", "atpId8", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 701</p>", "Lui Desc 701", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url");

        loadLuiLuiRel("LUILUIREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-1 Plain", "Lui-1", "kuali.lui.lui.relation.associated", "Lui-2");
        loadLuiLuiRel("LUILUIREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-2 Plain", "Lui-3", "kuali.lui.lui.relation.associated", "Lui-4");
        loadLuiLuiRel("LUILUIREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-3 Plain", "Lui-5", "kuali.lui.lui.relation.associated", "Lui-2");
        loadLuiLuiRel("LUILUIREL-4", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-4 Formatted</p>", "LUILUIREL-4 Plain", "Lui-6", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-2");
        loadLuiLuiRel("LUILUIREL-5", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-5 Formatted</p>", "LUILUIREL-5 Plain", "Lui-1", "kuali.lui.lui.relation.type.deliveredvia.co2fo", "Lui-6");
        loadLuiLuiRel("LUILUIREL-6", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-6", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-5");
        loadLuiLuiRel("LUILUIREL-7", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-6", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-Lab2");
        loadLuiLuiRel("LUILUIREL-8", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-7", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-Lab2");
        loadLuiLuiRel("LUILUIREL-9", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-6 Formatted</p>", "LUILUIREL-6 Plain", "Lui-1", "kuali.lui.lui.relation.type.deliveredvia.co2fo", "Lui-7");
        loadLuiLuiRel("LUILUIREL-10", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-10 Formatted</p>", "LUILUIREL-10 Plain", "Lui-6", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-8");
        loadLuiLuiRel("LUILUIREL-11", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-10 Formatted</p>", "LUILUIREL-10 Plain", "Lui-7", "kuali.lui.lui.relation.type.deliveredvia.fo2ao", "Lui-8");
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
        addIdentifier("LUI-IDENT-1", "CHEM123", "CHEM", "123", "Chemistry 123", "Chem 123", "kuali.lui.identifier.type.official", null, "Lui-1");
        addIdentifier("LUI-IDENT-2", "CHEM456", "CHEM", "456", "Chemistry 456", "Chem 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-1");
        addIdentifier("LUI-IDENT-3", "BIO123", "BIO", "123", "Biology 123", "Bio 123", "kuali.lui.identifier.type.official", null, "Lui-2");
        addIdentifier("LUI-IDENT-4", "BIO456", "BIO", "456", "Biology 456", "Bio 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-2");
        addIdentifier("LUI-IDENT-5", "GEOG123", "GEOG", "123", "Geography 123", "Geog 123", "kuali.lui.identifier.type.official", null, "Lui-3");
        addIdentifier("LUI-IDENT-6", "MATH123", "MATH", "123", "Mathematics 123", "Math 123", "kuali.lui.identifier.type.official", null, "Lui-4");
        addIdentifier("LUI-IDENT-7", "MATH123", "MATH", "456", "Mathematics 456", "Math 456", "kuali.lui.identifier.type.cross-listed", null, "Lui-4");
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
