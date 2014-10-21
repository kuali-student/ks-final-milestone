package org.kuali.student.enrollment.class1.lui.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuCodeEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiAttributeEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiIdentifierEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LuiTestDataLoader {

    protected TypeService typeService = null;

    public LuiTestDataLoader(LuiDao luiDao, LuiLuiRelationDao luiLuiRelationDao) {
        this.luiDao = luiDao;
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    private LuiDao luiDao;

    private LuiLuiRelationDao luiLuiRelationDao;

    private String principalId = LuiTestDataLoader.class.getSimpleName();

    public void loadData(ContextInfo callContext) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException,
            AlreadyExistsException, CircularRelationshipException {
        try {
            createType(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui Lui association type",
                    "Lui Lui association type",
                    "http://student.kuali.org/wsdl/lui/LuiLuiRelationInfo",callContext);
        } catch (Exception e) {
            throw new OperationFailedException("unexpected exception creating lui lui  type-type type: " +
                    ""+LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
        }

        loadLui("Lui-1", "Lui one", "cluId1", "atpId1", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, "Lui Desc 101", 200, 50, "ref.url", "LUI-IDENT-2", "lui_one_official", "Chem 123", "attr1", "attr2");
        loadLui("Lui-2", "Lui rwo", "cluId2", "atpId2", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, "Lui Desc 201", 200, 50, "ref.url", "LUI-IDENT-3", "lui_two_official", "Phy 123");
        loadLui("Lui-3", "Lui three", "cluId3", "atpId3", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, "Lui Desc 301 for deletion", 200, 50, "ref.url", "lui_three_additional", "lui_three_official", "Bio 123");
        loadLui("Lui-4", "Lui four", "cluId4", "atpId4", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, "Lui Desc 401 for deletion", 200, 50, "ref.url", "lui_four_additional", "lui_four_official", "Phil 123");
        loadLui("Lui-5", "Lui five", "cluId5", "atpId5", LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, "Lui Desc 501", 200, 50, "ref.url", "lui_five_additional", "lui_five_official", "XX");

        loadLuiLuiRel("LUILUIREL-1", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-1 Formatted</p>", "LUILUIREL-1 Plain", "Lui-1", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-2");
        loadLuiLuiRel("LUILUIREL-2", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-2 Formatted</p>", "LUILUIREL-2 Plain", "Lui-3", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-4");
        loadLuiLuiRel("LUILUIREL-3", "2011-01-01 00:00:00.0", "2100-01-01 00:00:00.0", LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, "<p>LUILUIREL-3 Formatted</p>", "LUILUIREL-3 Plain", "Lui-5", LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui-2");

    }

    private void loadLui(String id,
                         String name,
                         String cluId,
                         String atpId,
                         String type,
                         String state,
                         String descrPlain,
                         Integer maxSeats,
                         Integer minSeats,
                         String refUrl, String additionIden, String officialIdentifier, String officialIdentName, String... luiAttributes)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, AlreadyExistsException {
        LuiEntity luiEntity = new LuiEntity();
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
        luiIdent.setCreateTime(new Date());

        LuiIdentifierEntity luiOfficialIdent = new LuiIdentifierEntity();
        luiOfficialIdent.setLui(luiEntity);
        luiOfficialIdent.setId(officialIdentifier);
        luiOfficialIdent.setShortName(officialIdentName);
        luiOfficialIdent.setType(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        luiOfficialIdent.setCreateId("TESTDATALOADER");
        luiOfficialIdent.setCreateTime(new Date());
        List<LuiIdentifierEntity> luiIdents = new ArrayList<LuiIdentifierEntity>();

        luiIdents.add(luiIdent);
        luiIdents.add(luiOfficialIdent);
        luiEntity.setIdentifiers(luiIdents);

        //Lu Code
        LuCodeEntity luCode = new LuCodeEntity();
        luCode.setId("Lu-Code-" + id);
        luCode.setCreateId(principalId);
        luCode.setCreateTime(new Date());
        luCode.setLui(luiEntity);
        
        ArrayList<LuCodeEntity> luCodes = new ArrayList<LuCodeEntity>();
        luCodes.add(luCode);
        luiEntity.setLuiCodes(luCodes);

        //Result Value Group Key
        ArrayList<String> resultValueGroupKeys = new ArrayList<String>();
        resultValueGroupKeys.add("Lu-Rvgr-" + id);
        luiEntity.setResultValuesGroupKeys(resultValueGroupKeys);

        //Attributes
        if (luiAttributes != null && luiAttributes.length > 0) {
            for (String attr : luiAttributes) {
                LuiAttributeEntity luiAttr = new LuiAttributeEntity(new AttributeInfo(attr, attr), luiEntity);
                luiEntity.getAttributes().add(luiAttr);
            }
        }

        luiDao.persist(luiEntity);
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
        LuiLuiRelationEntity entity = new LuiLuiRelationEntity();
        entity.setId(id);
        entity.setLui(luiDao.find(luiId));
        entity.setRelatedLui(luiDao.find(relatedLuiId));
        entity.setLuiLuiRelationType(type);
        entity.setLuiLuiRelationState(state);
        entity.setEffectiveDate(str2Date(effectiveDate));
        entity.setExpirationDate(str2Date(expirationDate));
        entity.setDescrPlain(descrPlain);
        entity.setDescrPlain(descrFormatted);

        entity.setCreateId(principalId);
        Date time;
        entity.setCreateTime(time = new Date());

        entity.setUpdateId(principalId);
        entity.setUpdateTime(time);

        luiLuiRelationDao.persist(entity);

    }

    private Date str2Date(String str) {
        if (str == null) {
            return null;
        }
        return DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(str);
    }
    void createType(String typeKey, String typeName, String typeDescription, String refObjectUri,
            ContextInfo callContext) throws Exception {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(typeDescription));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new Date());
        getTypeService().createType(type.getKey(), type, callContext);
    }
    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(
                    new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

}
