package org.kuali.student.r2.core.class1.type.service;

import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import static junit.framework.Assert.*;

/**
 * Unit tester for the type service impl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:type-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestTypeServiceImpl {

    @Resource
    private TypeService typeService;

    @Test
    public void testCRUD() throws DoesNotExistException,
            AlreadyExistsException,
            OperationFailedException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testUser1");
        context.setCurrentDate(new Date());

        TypeInfo orig = new TypeInfo();
        orig.setKey("kuali.atp.type.TestAtp");
        orig.setName("ATP test create");
        orig.setEffectiveDate(new Date());
        orig.setDescr(new RichTextHelper().fromPlain("my decription"));
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        orig.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);

        TypeInfo info = typeService.createType(orig.getKey(), orig, context);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getRefObjectUri(), info.getRefObjectUri());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertNotNull(info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotNull(info.getMeta().getVersionInd());

        orig = info;
        info = typeService.getType(orig.getKey(), context);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getRefObjectUri(), info.getRefObjectUri());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getId(), info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotSame(orig.getMeta().getVersionInd(), info.getMeta().getVersionInd());

        orig = info;
        List<TypeInfo> types = typeService.getTypesByRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP, context);
        assertEquals(1, types.size());
        info = types.get(0);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getRefObjectUri(), info.getRefObjectUri());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getId(), info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotSame(orig.getMeta().getVersionInd(), info.getMeta().getVersionInd());

        orig = info;
        orig.setName("new name");
        orig.setDescr(new RichTextHelper().fromPlain("new description"));
        orig.setEffectiveDate(new Date(new Date().getTime() + 1000));
        orig.getAttributes().get(0).setValue("new value");
        info = typeService.updateType(orig.getKey(), orig, context);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getRefObjectUri(), info.getRefObjectUri());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getId(), info.getAttributes().get(0).getId());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotSame(orig.getMeta().getVersionInd(), info.getMeta().getVersionInd());

        List<String> refObjectUris = typeService.getRefObjectUris(context);
        assertNotNull(refObjectUris);
        assertEquals(1, refObjectUris.size());
        assertEquals(AtpServiceConstants.REF_OBJECT_URI_ATP, refObjectUris.get(0));

        StatusInfo status = typeService.deleteType(orig.getKey(), context);
        try {
            typeService.getType(orig.getKey(), context);
            fail("should have been deleted");
        } catch (DoesNotExistException ex) {
            // expected
        }
        refObjectUris = typeService.getRefObjectUris(context);
        assertNotNull(refObjectUris);
        assertEquals(0, refObjectUris.size());

        TypeInfo type1 = new TypeInfo();
        type1.setKey("kuali.atp.type.TestAtp1");
        type1.setName("ATP test create1");
        type1.setDescr(new RichTextHelper().fromPlain("ATP description 1"));
        type1.setEffectiveDate(new Date());
        type1.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        info = typeService.createType(type1.getKey(), type1, context);

        TypeInfo type2 = new TypeInfo();
        type2.setKey("kuali.atp.type.TestAtp2");
        type2.setName("ATP test create2");
        type2.setDescr(new RichTextHelper().fromPlain("ATP description 2"));
        type2.setEffectiveDate(new Date());
        type2.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        info = typeService.createType(type2.getKey(), type2, context);

        TypeInfo type3 = new TypeInfo();
        type3.setKey("kuali.atp.type.TestAtp3");
        type3.setName("ATP test create3");
        type3.setDescr(new RichTextHelper().fromPlain("ATP description 3"));
        type3.setEffectiveDate(new Date());
        type3.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        info = typeService.createType(type3.getKey(), type3, context);

        // double check that the list returned does a distinct on (i.e. set)
        refObjectUris = typeService.getRefObjectUris(context);
        assertNotNull(refObjectUris);
        assertEquals(1, refObjectUris.size());
        assertEquals(AtpServiceConstants.REF_OBJECT_URI_ATP, refObjectUris.get(0));

        // test create relation
        TypeTypeRelationInfo origRel = new TypeTypeRelationInfo();
        origRel.setEffectiveDate(new Date());
        origRel.setTypeKey(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY);
        origRel.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        origRel.setRank(1);
        origRel.setOwnerTypeKey(type1.getKey());
        origRel.setRelatedTypeKey(type2.getKey());
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origRel.getAttributes().add(attr);
        TypeTypeRelationInfo infoRel = typeService.createTypeTypeRelation(origRel.getTypeKey(),
                origRel.getOwnerTypeKey(),
                origRel.getRelatedTypeKey(),
                origRel,
                context);
        assertNotNull(infoRel);
        assertEquals(origRel.getEffectiveDate(), infoRel.getEffectiveDate());
        assertEquals(origRel.getExpirationDate(), infoRel.getExpirationDate());
        assertEquals(origRel.getTypeKey(), infoRel.getTypeKey());
        assertEquals(origRel.getStateKey(), infoRel.getStateKey());
        assertEquals(origRel.getRank(), infoRel.getRank());
        assertEquals(origRel.getOwnerTypeKey(), infoRel.getOwnerTypeKey());
        assertEquals(origRel.getRelatedTypeKey(), infoRel.getRelatedTypeKey());
        assertEquals(origRel.getAttributes().size(), infoRel.getAttributes().size());
        assertNotNull(infoRel.getAttributes().get(0).getId());
        assertEquals(origRel.getAttributes().get(0).getKey(), infoRel.getAttributes().get(0).getKey());
        assertEquals(origRel.getAttributes().get(0).getValue(), infoRel.getAttributes().get(0).getValue());
        assertNotNull(infoRel.getMeta());
        assertNotNull(infoRel.getMeta().getCreateId());
        assertNotNull(infoRel.getMeta().getCreateTime());
        assertNotNull(infoRel.getMeta().getUpdateId());
        assertNotNull(infoRel.getMeta().getUpdateTime());
        assertNotNull(infoRel.getMeta().getVersionInd());

        // test get relation
        origRel = infoRel;
        infoRel = typeService.getTypeTypeRelation(origRel.getId(), context);
        assertNotNull(infoRel);
        assertEquals(origRel.getEffectiveDate(), infoRel.getEffectiveDate());
        assertEquals(origRel.getExpirationDate(), infoRel.getExpirationDate());
        assertEquals(origRel.getTypeKey(), infoRel.getTypeKey());
        assertEquals(origRel.getStateKey(), infoRel.getStateKey());
        assertEquals(origRel.getRank(), infoRel.getRank());
        assertEquals(origRel.getOwnerTypeKey(), infoRel.getOwnerTypeKey());
        assertEquals(origRel.getRelatedTypeKey(), infoRel.getRelatedTypeKey());
        assertEquals(origRel.getAttributes().size(), infoRel.getAttributes().size());
        assertNotNull(infoRel.getAttributes().get(0).getId());
        assertEquals(origRel.getAttributes().get(0).getKey(), infoRel.getAttributes().get(0).getKey());
        assertEquals(origRel.getAttributes().get(0).getValue(), infoRel.getAttributes().get(0).getValue());
        assertNotNull(infoRel.getMeta());
        assertNotNull(infoRel.getMeta().getCreateId());
        assertNotNull(infoRel.getMeta().getCreateTime());
        assertNotNull(infoRel.getMeta().getUpdateId());
        assertNotNull(infoRel.getMeta().getUpdateTime());
        assertNotNull(infoRel.getMeta().getVersionInd());

        // test update relation
        origRel = infoRel;
        origRel.setRank(2);
        origRel.setEffectiveDate(new Date(new Date().getTime() - 1000));
        infoRel = typeService.updateTypeTypeRelation(origRel.getId(), origRel, context);
        assertNotNull(infoRel);
        assertEquals(origRel.getEffectiveDate(), infoRel.getEffectiveDate());
        assertEquals(origRel.getExpirationDate(), infoRel.getExpirationDate());
        assertEquals(origRel.getTypeKey(), infoRel.getTypeKey());
        assertEquals(origRel.getStateKey(), infoRel.getStateKey());
        assertEquals(origRel.getRank(), infoRel.getRank());
        assertEquals(origRel.getOwnerTypeKey(), infoRel.getOwnerTypeKey());
        assertEquals(origRel.getRelatedTypeKey(), infoRel.getRelatedTypeKey());
        assertEquals(origRel.getAttributes().size(), infoRel.getAttributes().size());
        assertNotNull(infoRel.getAttributes().get(0).getId());
        assertEquals(origRel.getAttributes().get(0).getKey(), infoRel.getAttributes().get(0).getKey());
        assertEquals(origRel.getAttributes().get(0).getValue(), infoRel.getAttributes().get(0).getValue());
        assertNotNull(infoRel.getMeta());
        assertNotNull(infoRel.getMeta().getCreateId());
        assertNotNull(infoRel.getMeta().getCreateTime());
        assertNotNull(infoRel.getMeta().getUpdateId());
        assertNotNull(infoRel.getMeta().getUpdateTime());
        assertNotNull(infoRel.getMeta().getVersionInd());


        origRel = new TypeTypeRelationInfo();
        origRel.setEffectiveDate(new Date());
        origRel.setTypeKey(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY);
        origRel.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        origRel.setRank(1);
        origRel.setOwnerTypeKey(type3.getKey());
        origRel.setRelatedTypeKey(type1.getKey());
        infoRel = typeService.createTypeTypeRelation(origRel.getTypeKey(),
                origRel.getOwnerTypeKey(),
                origRel.getRelatedTypeKey(),
                origRel,
                context);

        origRel = new TypeTypeRelationInfo();
        origRel.setEffectiveDate(new Date());
        origRel.setTypeKey(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY);
        origRel.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        origRel.setRank(1);
        origRel.setOwnerTypeKey(type3.getKey());
        origRel.setRelatedTypeKey(type2.getKey());
        infoRel = typeService.createTypeTypeRelation(origRel.getTypeKey(),
                origRel.getOwnerTypeKey(),
                origRel.getRelatedTypeKey(),
                origRel,
                context);

        // should be the current state of relationships
        // owner related
        // 1        2 allowed
        // 3        1 group
        // 3        2 group
        types = typeService.getAllowedTypesForType(type1.getKey(), context);
        assertNotNull(types);
        assertEquals(1, types.size());
        assertEquals(type2.getKey(), types.get(0).getKey());

        types = typeService.getTypesForGroupType(type3.getKey(), context);
        assertNotNull(types);
        assertEquals(2, types.size());
        TypeInfo found1 = null;
        TypeInfo found2 = null;
        for (TypeInfo ti : types) {
            if (ti.getKey().equals(type1.getKey())) {
                found1 = ti;
            } else if (ti.getKey().equals(type2.getKey())) {
                found2 = ti;
            } else {
                fail("unexpected type " + ti.getKey());
            }
        }
        assertNotNull(found1);
        assertNotNull(found2);
        
        // https://jira.kuali.org/browse/KSENROLL-821
        // test reverse lookup
        List<TypeTypeRelationInfo> rels =
                typeService.getTypeTypeRelationsByRelatedTypeAndType(type2.getKey(),
                TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, context);
        assertEquals(1, rels.size());
        assertEquals(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, rels.get(0).getTypeKey());
        assertEquals(type1.getKey(), rels.get(0).getOwnerTypeKey());
        assertEquals(type2.getKey(), rels.get(0).getRelatedTypeKey());

        rels = typeService.getTypeTypeRelationsByRelatedTypeAndType(type2.getKey(),
                TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        assertEquals(1, rels.size());
        assertEquals(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, rels.get(0).getTypeKey());
        assertEquals(type3.getKey(), rels.get(0).getOwnerTypeKey());
        assertEquals(type2.getKey(), rels.get(0).getRelatedTypeKey());


    }
}
