package org.kuali.student.enrollment.class1.lui.service.impl;

import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.*;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LuiTestDataLoader {


    public LuiTestDataLoader(LuiDao luiDao, LuiLuiRelationDao luiLuiRelationDao) {
        this.luiDao = luiDao;
        this.luiLuiRelationDao =  luiLuiRelationDao;
    }
    private  LuiDao luiDao;

    private   LuiLuiRelationDao luiLuiRelationDao;

    private  String principalId = LuiTestDataLoader.class.getSimpleName();

    public  void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException,
            AlreadyExistsException, CircularRelationshipException {
        loadLui("Lui-1", "Lui one", "cluId1", "atpId1", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 101</p>", "Lui Desc 101", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url","LUI-IDENT-2", "lui_one_official", "Chem 123", "attr1", "attr2");
        loadLui("Lui-2", "Lui rwo", "cluId2", "atpId2", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 201</p>", "Lui Desc 201", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url","LUI-IDENT-3", "lui_two_official", "Phy 123");
        loadLui("Lui-3", "Lui three", "cluId3", "atpId3", "kuali.lui.type.course.offering", "kuali.lui.state.draft", "<p>Lui Desc 301</p>", "Lui Desc 301 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url", "lui_three_additional",  "lui_three_official", "Bio 123");
        loadLui("Lui-4", "Lui four", "cluId4", "atpId4", "kuali.lui.type.activity.offering.lecture", "kuali.lui.state.draft", "<p>Lui Desc 401</p>", "Lui Desc 401 for deletion", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url", "lui_four_additional", "lui_four_official", "Phil 123");
        loadLui("Lui-5", "Lui five", "cluId5", "atpId5", "kuali.lui.type.activity.offering.lab", "kuali.lui.state.draft", "<p>Lui Desc 501</p>", "Lui Desc 501", "2011-01-01 00:00:00.0", "2011-12-31 00:00:00.0", 200, 50, "ref.url", "lui_five_additional", "lui_five_official", "XX");

        loadLuiLuiRel("LUILUIREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-1 Plain", "Lui-1", "kuali.lui.lui.relation.associated", "Lui-2");
        loadLuiLuiRel("LUILUIREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-2 Plain", "Lui-3", "kuali.lui.lui.relation.associated", "Lui-4");
        loadLuiLuiRel("LUILUIREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", "kuali.lui.lui.relation.state.active", "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-3 Plain", "Lui-5", "kuali.lui.lui.relation.associated", "Lui-2");

    }

    private  void loadLui(String id,
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
                                 String refUrl, String additionIden, String officialIdentifier, String officialIdentName, String ... luiAttributes)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, AlreadyExistsException {
        LuiEntity luiEntity  = new LuiEntity();
        luiEntity.setId(id);
        luiEntity.setCluId(cluId);
        luiEntity.setAtpId(atpId);
        luiEntity.setName(name);
        luiEntity.setLuiType(type);
        luiEntity.setLuiState(state);
        luiEntity.setMaxSeats(maxSeats);
        luiEntity.setMinSeats(minSeats);
        luiEntity.setReferenceURL(refUrl);
        luiEntity.setPlain(descrPlain);
        luiEntity.setCreateId(principalId);
        Date time;
		luiEntity.setCreateTime(time = new Date());
        
        luiEntity.setUpdateId(principalId);
        luiEntity.setUpdateTime(time);
        
        luiEntity.setEffectiveDate(time);

        LuiIdentifierEntity luiIdent = new LuiIdentifierEntity();
        luiIdent.setLui(luiEntity);
        luiIdent.setId(additionIden);
        luiIdent.setCreateId("TESTDATALOADER");
        luiIdent.setCreateTime(new Date ());

        LuiIdentifierEntity luiOfficialIdent = new LuiIdentifierEntity();
        luiOfficialIdent.setLui(luiEntity);
        luiOfficialIdent.setId(officialIdentifier);
        luiOfficialIdent.setShortName(officialIdentName);
        luiOfficialIdent.setType(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);        
        luiOfficialIdent.setCreateId("TESTDATALOADER");
        luiOfficialIdent.setCreateTime(new Date ());
        List<LuiIdentifierEntity> luiIdents = new ArrayList<LuiIdentifierEntity>();

        luiIdents.add(luiIdent);
        luiIdents.add(luiOfficialIdent);
        luiEntity.setIdentifiers(luiIdents);

        //Lu Code
        LuCodeEntity luCode = new LuCodeEntity();
        luCode.setId("Lu-Code-" + id);
        luCode.setCreateId(principalId);
        luCode.setCreateTime(new Date());
        ArrayList<LuCodeEntity> luCodes = new ArrayList<LuCodeEntity>();
        luCodes.add(luCode);
        luiEntity.setLuiCodes(luCodes);

        //Result Value Group Key
        ArrayList<String> resultValueGroupKeys = new ArrayList<String>();
        resultValueGroupKeys.add("Lu-Rvgr-" + id);
        luiEntity.setResultValuesGroupKeys(resultValueGroupKeys);

        //Attributes
        if (luiAttributes != null && luiAttributes.length > 0){
            for (String attr:luiAttributes){
                LuiAttributeEntity luiAttr = new LuiAttributeEntity(new AttributeInfo(attr, attr), luiEntity);
                luiEntity.getAttributes().add(luiAttr);
            }
        }

        luiDao.persist(luiEntity);
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

    private  void loadLuiLuiRel(String id,
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
        LuiLuiRelationEntity entity = new LuiLuiRelationEntity();
        entity.setId(id);
        entity.setLui(luiDao.find(luiId));
        entity.setRelatedLui(luiDao.find(relatedLuiId));
        entity.setLuiLuiRelationType(type);
        entity.setLuiLuiRelationState(state);
        entity.setEffectiveDate(str2Date(effectiveDate, id));
        entity.setExpirationDate(str2Date(expirationDate, id));
        entity.setDescrPlain(descrPlain);
        entity.setDescrPlain(descrFormatted);

        entity.setCreateId(principalId);
        Date time;
		entity.setCreateTime(time = new Date());

		entity.setUpdateId(principalId);
		entity.setUpdateTime(time);

        luiLuiRelationDao.persist(entity);

    }

    private  Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}
