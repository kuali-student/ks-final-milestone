/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.class1.lui.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiServiceImpl {

    @Resource(name = "luiService")
    private LuiService luiService;

    @Resource(name = "luiDao")
    private LuiDao luiDao;

    @Resource(name= "criteriaLookupService")
    private CriteriaLookupService criteriaLookupService;


    public LuiLuiRelationDao getLuiLuiRelationDao() {
        return luiLuiRelationDao;
    }

    public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    @Resource(name = "luiLuiRelationDao")
    private LuiLuiRelationDao luiLuiRelationDao;

    public LuiService getLuiService() {
        return  luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LuiDao getLuiDao() {
        return luiDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new LuiTestDataLoader(luiDao, luiLuiRelationDao).loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(luiService);
    }

    @Test
    public void testGetLui() throws Exception {

        try {
            luiService.getLui("Lui-blah", callContext);
            fail("should not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }

        LuiInfo obj = luiService.getLui("Lui-1", callContext);
        assertNotNull(obj);
        assertEquals("Lui one", obj.getName());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey());

        assertEquals("Lui Desc 101", obj.getDescr().getPlain());

        assertTrue(obj.getResultValuesGroupKeys().contains("Lu-Rvgr-Lui-1"));

        assertNotNull(obj.getOfficialIdentifier());
        assertEquals("Chem 123", obj.getOfficialIdentifier().getShortName());
        assertNotNull(obj.getAlternateIdentifiers());
        assertEquals("LUI-IDENT-2", obj.getAlternateIdentifiers().get(0).getId());

        assertNotNull(obj.getEffectiveDate());

        assertEquals("cluId1", obj.getCluId());
        assertEquals("atpId1", obj.getAtpId());
        assertEquals(1, obj.getLuiCodes().size());
        assertEquals("Lu-Code-Lui-1", obj.getLuiCodes().get(0).getId());

        assertNotNull(obj.getMaximumEnrollment());
        assertEquals(200, obj.getMaximumEnrollment().intValue());
        assertNotNull(obj.getMinimumEnrollment());
        assertEquals(50, obj.getMinimumEnrollment().intValue());
        assertEquals("ref.url", obj.getReferenceURL());

        //Attributes
        HashMap<String, String> attributes = new HashMap<String, String>();
        for (AttributeInfo attributeInfo:obj.getAttributes()){
            attributes.put(attributeInfo.getKey(), attributeInfo.getValue());
        }
        assertTrue(attributes.containsKey("attr1"));
        assertTrue(attributes.containsKey("attr2"));

//        assertNotNull(obj.getCluCluRelationIds());
//        assertEquals(2, obj.getCluCluRelationIds().size());
//        assertTrue(obj.getCluCluRelationIds().contains("CluClu-2"));
//        assertNotNull(obj.getUnitsContentOwnerOrgIds());
//        assertEquals(1, obj.getUnitsContentOwnerOrgIds().size());
//        assertTrue(obj.getUnitsContentOwnerOrgIds().contains("Org-2"));
//        assertNotNull(obj.getUnitsDeploymentOrgIds());
//        assertEquals(1, obj.getUnitsDeploymentOrgIds().size());
//        assertTrue(obj.getUnitsDeploymentOrgIds().contains("Org-1"));
//        assertNotNull(obj.getResultValuesGroupKeys());
//        assertEquals(3, obj.getResultValuesGroupKeys().size());
//        assertTrue(obj.getResultValuesGroupKeys().contains("Val-Group-3"));
//
//        assertNotNull(obj.getFeeIds());
//        assertEquals(3, obj.getFeeIds().size());
//        assertNotNull(obj.getRevenues());
//        assertEquals(2, obj.getRevenues().size());
//        assertNotNull(obj.getExpenditure());
//        assertEquals("LUI-Expen-1", obj.getExpenditure().getId());
    }

    @Test
    public void testGetLuiIdsByRelation() throws Exception {
        try {
            List<String> luiIds = luiService.getLuiIdsByRelatedLuiAndRelationType("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luiIds);
            assertEquals(2, luiIds.size());
            assertEquals("Lui-1", luiIds.get(0));
            assertEquals("Lui-5", luiIds.get(1));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
    public void testGetLuisByRelation() throws Exception {
        try {
            List<LuiInfo> luis = luiService.getLuisByRelatedLuiAndRelationType("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luis);
            assertEquals(2, luis.size());
            assertEquals("Lui-1", luis.get(0).getId());
            assertEquals("Lui-5", luis.get(1).getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    // TODO: KSENROLL-3677 fix this thing
    @Test
    public void testGenericLookup() throws Exception {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.like("luiType", "kuali.lui.type.course.offering"));
        QueryByCriteria criteria = qbcBuilder.build();

        try{
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("name");
            GenericQueryResults<List<String>> results = criteriaLookupService.genericLookup(LuiEntity.class, criteria, fields);
            Iterator it = results.getResults().iterator();
            while(it.hasNext()) {
                assertTrue(it.next() instanceof String);
            }

            fields.add("luiType");
            results = criteriaLookupService.genericLookup(LuiEntity.class, criteria, fields);
            it = results.getResults().iterator();
            while(it.hasNext()) {
                Object[] result = (Object[])it.next();
                int length = result.length;
                String[] resultsString = new String[length];
                for (int i = 0; i < length; i++) {
                    resultsString[i] = result[i].toString();
                }
                assertTrue(length==2);
            }
        }   catch (Exception e){
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testCreateLui() throws Exception {
        LuiInfo orig = new LuiInfo();
        orig.setName("Test lui one");
        orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui one description</p>");
        rtInfo.setPlain("Test lui one description");
        orig.setDescr(rtInfo);
        orig.setEffectiveDate(Calendar.getInstance().getTime());
        orig.setMaximumEnrollment(25);
        orig.setMinimumEnrollment(10);
        orig.setReferenceURL("ref.create.url");

        orig.getResultValuesGroupKeys().add("Rvgr-2");

        LuiIdentifierInfo identifier = new LuiIdentifierInfo();
        identifier.setShortName("identifier.shortname");
        identifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        identifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        orig.setOfficialIdentifier(identifier);

        LuiIdentifierInfo identifier2 = new LuiIdentifierInfo();
        identifier2.setShortName("alternate.identifier.shortname");
        identifier2.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
        identifier2.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        orig.getAlternateIdentifiers().add(identifier2);

        orig.setCluId("testCluId");
        orig.setAtpId("testAtpId1");

        List<String> unitsContentOwner = new ArrayList<String>();
        unitsContentOwner.add("Org-2");
        orig.setUnitsContentOwner(unitsContentOwner);

        List<String> unitsDeployment = new ArrayList<String>();
        unitsDeployment.add("Org-1");
        orig.setUnitsDeployment(unitsDeployment);

        List<String> resultValueGroupKeys = new ArrayList<String>();
        resultValueGroupKeys.add("Val-Group-3");
        orig.setResultValuesGroupKeys(resultValueGroupKeys);

        LuCodeInfo luCode = new LuCodeInfo();
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("fee.plain");
        rt.setFormatted("fee.formatted");
        luCode.setDescr(rt);
        orig.getLuiCodes().add(luCode);

        LuiInfo info = luiService.createLui(orig.getCluId(), orig.getAtpId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());

        assertNotNull(info.getOfficialIdentifier());
        assertEquals(orig.getOfficialIdentifier().getShortName(), info.getOfficialIdentifier().getShortName());
        assertEquals(orig.getAlternateIdentifiers().size(), info.getAlternateIdentifiers().size());
        assertEquals(orig.getAlternateIdentifiers().get(0).getShortName(), info.getAlternateIdentifiers().get(0).getShortName());

        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getCluId(), info.getCluId());
        assertEquals(orig.getAtpId(), info.getAtpId());
        assertEquals(orig.getReferenceURL(), info.getReferenceURL());

        assertTrue(info.getUnitsContentOwner().contains("Org-2"));
        assertTrue(info.getUnitsDeployment().contains("Org-1"));
        assertTrue(info.getResultValuesGroupKeys().contains("Val-Group-3"));

        assertEquals(1, info.getLuiCodes().size());

        info = luiService.getLui(info.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());

        assertNotNull(info.getOfficialIdentifier());
        assertEquals(orig.getOfficialIdentifier().getShortName(), info.getOfficialIdentifier().getShortName());
        assertEquals(orig.getAlternateIdentifiers().size(), info.getAlternateIdentifiers().size());
        assertEquals(orig.getAlternateIdentifiers().get(0).getShortName(), info.getAlternateIdentifiers().get(0).getShortName());

        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getCluId(), info.getCluId());
        assertEquals(orig.getAtpId(), info.getAtpId());
        assertEquals(orig.getReferenceURL(), info.getReferenceURL());
    }

    @Test
    public void  testGetLuisByAtpAndClu() throws Exception{
        List<LuiInfo> luis =  luiService.getLuisByAtpAndClu("cluId1", "atpId1", callContext)  ;
        assertTrue(luis.size()>0);
        assertNotNull(luis);
        assertEquals(1, luis.size());
        LuiInfo onlyLui = luis.get(0);
        assertNotNull(onlyLui);
        assertEquals("Lui-1",  onlyLui.getId() );
        assertEquals("Lui one", onlyLui.getName());
        assertEquals("cluId1", onlyLui.getCluId());
        assertEquals("atpId1", onlyLui.getAtpId());
    }

    @Test
    public void testUpdateLui() throws Exception {
        LuiInfo info = luiService.getLui("Lui-1", callContext);
        assertNotNull(info);
        assertEquals("Lui-1", info.getId());
        assertEquals("Lui one", info.getName());

        LuiInfo modified = new LuiInfo(info);
        modified.setName("Lui one modified");
        modified.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        modified.setMaximumEnrollment(25);
        modified.setMinimumEnrollment(10);
        assertNotNull(modified.getOfficialIdentifier());
        modified.getOfficialIdentifier().setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        modified.getOfficialIdentifier().setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        modified.getOfficialIdentifier().setShortName("identifier.shortname");

        modified.getAlternateIdentifiers().clear();
        LuiIdentifierInfo identifier2 = new LuiIdentifierInfo();
        identifier2.setShortName("alternate.identifier.shortname");
        identifier2.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
        identifier2.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        modified.getAlternateIdentifiers().add(identifier2);

        modified.setReferenceURL("ref.update.url");
        modified.setCluId("updateCluId");
        modified.setAtpId("updateAtpId1");

        modified.getResultValuesGroupKeys().add("Val-Group-11");

        //Attributes
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        for (AttributeInfo attrInfo:modified.getAttributes()){
            if (attrInfo.getKey().equals("attr2")){
                attributes.add(attrInfo);
            }
        }
        AttributeInfo modAttr1 = new AttributeInfo();
        modAttr1.setKey("modattr1");
        modAttr1.setValue("modattr1");
        attributes.add(modAttr1);

        AttributeInfo modAttr2 = new AttributeInfo();
        modAttr2.setKey("modattr2");
        modAttr2.setValue("modattr2");
        attributes.add(modAttr2);
        modified.setAttributes(attributes);

        LuCodeInfo luCode = new LuCodeInfo();
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("fee.plain");
        rt.setFormatted("fee.formatted");
        luCode.setDescr(rt);
        luCode.setId("Modified Lu Code");
        modified.getLuiCodes().clear();
        modified.getLuiCodes().add(luCode);

        modified.getUnitsContentOwner().add("Org-22");

        modified.getUnitsDeployment().add("Org-11");

        LuiInfo updated = luiService.updateLui("Lui-1", modified, callContext);

        assertNotNull(updated);
        assertEquals("Lui one modified", updated.getName());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, updated.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, updated.getTypeKey());
        assertEquals(Integer.valueOf(25), updated.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), updated.getMinimumEnrollment());
        assertEquals("ref.update.url", updated.getReferenceURL());
        assertNotNull(updated.getEffectiveDate());

        assertEquals(2, updated.getResultValuesGroupKeys().size());
        assertTrue(updated.getResultValuesGroupKeys().contains("Val-Group-11"));

        assertNotNull(updated.getOfficialIdentifier());
        assertEquals("identifier.shortname", updated.getOfficialIdentifier().getShortName());
        assertEquals(1, updated.getAlternateIdentifiers().size());
        assertEquals("alternate.identifier.shortname", updated.getAlternateIdentifiers().get(0).getShortName());

        //Attributes
        HashMap<String, String> updatedAttributes = new HashMap<String, String>();
        for (AttributeInfo attributeInfo:updated.getAttributes()){
            updatedAttributes.put(attributeInfo.getKey(), attributeInfo.getValue());
        }
        assertFalse(updatedAttributes.containsKey("attr1"));
        assertTrue(updatedAttributes.containsKey("attr2"));
        assertTrue(updatedAttributes.containsKey("modattr1"));
        assertTrue(updatedAttributes.containsKey("modattr2"));

        assertTrue(updated.getUnitsContentOwner().contains("Org-22"));
        assertTrue(updated.getUnitsDeployment().contains("Org-11"));

        assertEquals(1, info.getLuiCodes().size());
        assertEquals("Lu-Code-Lui-1", info.getLuiCodes().get(0).getId());
        assertEquals(1, updated.getLuiCodes().size());
        assertEquals("Modified Lu Code", updated.getLuiCodes().get(0).getId());
    }

    @Test
    public void testUpdateLuiLuiRelation() throws Exception{
        LuiLuiRelationInfo info = luiService.getLuiLuiRelation("LUILUIREL-2", callContext);
        assertNotNull(info);
        info.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
        luiService.updateLuiLuiRelation(info.getId(), info, callContext);
        assertEquals(info.getStateKey(), LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
    }

    @Test
    public void testGetLuiLuiRelation() throws Exception {
        try {
            LuiLuiRelationInfo obj = luiService.getLuiLuiRelation("LUILUIREL-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui-1", obj.getLuiId());
            assertEquals("Lui-2", obj.getRelatedLuiId());

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetLuiLuiRelationsByLui() throws Exception {

        luiService.getLuiLuiRelationsByLui("Lui-Lui-Blah", callContext);
        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());
    }

    @Test
    public void testCreateLuiLuiRelation() throws Exception{
        LuiInfo info = new LuiInfo();
        info.setCluId("testCluId");
        info.setAtpId("testAtpId1");
        info.setName("Test lui-Lui relation");
        info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        info.setTypeKey(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        info.setEffectiveDate(Calendar.getInstance().getTime());
        info.setMaximumEnrollment(25);
        info.setMinimumEnrollment(10);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui-Lui relation description</p>");
        rtInfo.setPlain("Test lui-Lui relation description");
        info.setDescr(rtInfo);

        LuiInfo newLui = luiService.createLui(info.getCluId(), info.getAtpId(), info.getTypeKey(), info, callContext);
        LuiLuiRelationInfo created = null;
        LuiLuiRelationInfo rel = new LuiLuiRelationInfo();
        rel.setLuiId("Lui-1");
        rel.setRelatedLuiId(newLui.getId());
        rel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
        rel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY);
        rel.setEffectiveDate(Calendar.getInstance().getTime());
        rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui-Lui relation description</p>");
        rtInfo.setPlain("Test lui-Lui relation description");
        rel.setDescr(rtInfo);
        created = luiService.createLuiLuiRelation("Lui-1", newLui.getId(), LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, rel, callContext);

        assertNotNull(created);
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, created.getTypeKey());



        LuiLuiRelationInfo retrieved = luiService.getLuiLuiRelation(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, retrieved.getStateKey());
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, retrieved.getTypeKey());

        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(2, objs.size());
        for (LuiLuiRelationInfo obj : objs) {
            assertTrue(obj.getRelatedLuiId().equals("Lui-2") || obj.getRelatedLuiId().equals(newLui.getId()));
        }
    }

    @Test
    public void testDeleteLui()
            throws Exception{
        LuiInfo info = luiService.getLui("Lui-3", callContext);
        assertNotNull(info);

        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-3", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());

        try {
            luiService.deleteLui("Lui-3-blah", callContext);
        } catch (DoesNotExistException ee) {
        }

        StatusInfo status = luiService.deleteLui("Lui-3", callContext);
        assertTrue(status.getIsSuccess());

        luiService.getLuiLuiRelationsByLui("Lui-3", callContext);

        try {
            luiService.getLui("Lui-3", callContext);
        } catch (DoesNotExistException ee) {
        }
    }

    @Test
    public void testGetLuisByIds() throws Exception {
       List<String> luiIds = new ArrayList<String>();
        luiIds.add("Lui-1");
        luiIds.add("Lui-2");
        luiIds.add("Lui-3");
        List<LuiInfo> luis =  luiService.getLuisByIds(luiIds, callContext);

        assertNotNull(luis);
        assertTrue(luis.size() == 3);

        try{
            luiIds.add("Lui-3b");
            luis =  luiService.getLuisByIds(luiIds, callContext);
        }catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
        }

    }



    @Test
    public void testGetLuiIdsByType() throws Exception {

        List<String> luis =  luiService.getLuiIdsByType("kuali.lui.type.course.offering", callContext);

        assertNotNull(luis);
        assertTrue(luis.size() == 2);

    }


    @Test
    public void testGetLuiIdsByAtpAndType() throws Exception{
        List<String> luiIds =  luiService.getLuiIdsByAtpAndType( "atpId1", "kuali.lui.type.course.offering", callContext);
        assertNotNull(luiIds);
        assertEquals(luiIds.size(), 1);
        assertEquals(luiIds.get(0) ,"Lui-1");
        List<String> luiIdsNonExistent =  luiService.getLuiIdsByAtpAndType( "atpId21", "kuali.lui.type.course.offering", callContext);
        assertNotNull(luiIdsNonExistent);
        assertEquals(luiIdsNonExistent.size(),0);

    }
    @Test
    public void testGetRelatedLuiIdsByLui() throws Exception{
        List<String> luiRelationIds =  luiService.getLuiIdsByLuiAndRelationType("Lui-1", "kuali.lui.lui.relation.associated", callContext);
        assertNotNull(luiRelationIds);
        assertEquals( 1, luiRelationIds.size());
        assertEquals("Lui-2", luiRelationIds.get(0) );

    }
    @Test
    public void testGetRelatedLuisByLui() throws Exception{
        List<LuiInfo> luiRelations =  luiService.getRelatedLuisByLuiAndRelationType("Lui-3", "kuali.lui.lui.relation.associated", callContext);
        assertNotNull(luiRelations);
        assertEquals( 1, luiRelations.size());
        assertEquals("Lui-4", luiRelations.get(0).getId() );

    }



}