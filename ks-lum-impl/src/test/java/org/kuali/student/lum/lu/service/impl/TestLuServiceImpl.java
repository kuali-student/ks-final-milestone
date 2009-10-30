/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.service.LuService;

import edu.emory.mathcs.backport.java.util.Collections;

@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl",testSqlFile="classpath:ks-lu.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181",additionalContextFile="classpath:lu-additional-context.xml")
    public LuService client;
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void testClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // getClu
        CluInfo clu = client.getClu("CLU-1");
        assertNotNull(clu);
        assertEquals(clu.getId(), "CLU-1");

        try {
            clu = client.getClu("CLX-1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            clu = client.getClu(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getClusByIdList
        List<String> ids = new ArrayList<String>(1);
        ids.add("CLU-2");
        List<CluInfo> clus = client.getClusByIdList(ids);
        assertNotNull(clus);
        assertEquals(1, clus.size());

        ids.clear();
        ids.add("CLX-42");
        clus = client.getClusByIdList(ids);
        assertTrue(clus == null || clus.size() == 0);

        try {
            clus = client.getClusByIdList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluIdsByLuType
        ids = client.getCluIdsByLuType("luType.shell.course", "STATE1");
        assertTrue(null != ids);
        assertEquals(2, ids.size());
        assertEquals("CLU-1", ids.get(0));


        ids = client.getCluIdsByLuType("LUTYPE-1X", "STATE1");
        assertTrue(ids == null || ids.size() == 0);
        ids = client.getCluIdsByLuType("luType.shell.course", "STATE1X");
        assertTrue(ids == null || ids.size() == 0);

        try {
            ids = client.getCluIdsByLuType(null, "STATE1");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            ids = client.getCluIdsByLuType("luType.shell.course", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getClusByLuType
        clus = client.getClusByLuType("luType.shell.course", "STATE1");
        assertTrue(null != clus);
        assertEquals(2, clus.size());
        assertEquals("CLU-1", clus.get(0).getId());

        clus = client.getClusByLuType("LUTYPE-1X", "STATE1");
        assertTrue(clus == null || clus.size() == 0);
        clus = client.getClusByLuType("luType.shell.course", "STATE1X");
        assertTrue(clus == null || clus.size() == 0);

        try {
            clus = client.getClusByLuType(null, "STATE1");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            clus = client.getClusByLuType("luType.shell.course", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCluSet() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        // getCluSetInfo
        CluSetInfo csi = client.getCluSetInfo("CLUSET-2");
        assertNotNull(csi);

        csi = client.getCluSetInfo("CLUSET-1");
        assertNotNull(csi);

        try {
            csi = client.getCluSetInfo("CLUSETXX-42");
            assertTrue(false);
        } catch (DoesNotExistException e1) {
            assertTrue(true);
        }

        try {
            csi = client.getCluSetInfo(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluSetInfoByIdList
        List<String> ids = new ArrayList<String>(1);
        ids.add("CLUSET-2");
        List<CluSetInfo> cluSets = client.getCluSetInfoByIdList(ids);
        assertEquals(1, cluSets.size());

        ids.clear();
        ids.add("CLUSETXXX-42");
        cluSets = client.getCluSetInfoByIdList(ids);
        assertTrue(cluSets == null || cluSets.size() == 0);

        try {
            cluSets = client.getCluSetInfoByIdList(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluIdsFromCluSet
        ids = client.getCluIdsFromCluSet("CLUSET-2");
        assertEquals(2, ids.size());
        assertEquals("CLU-1", ids.get(0));

        try {
            ids = client.getCluIdsFromCluSet("CLUSETXXX-42");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ids = client.getCluIdsFromCluSet(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getAllClusInCluSet
        List<CluInfo> clus = client.getClusFromCluSet("CLUSET-2");
        assertEquals(2, clus.size());
        assertEquals("CLU-1", clus.get(0).getId());

        try {
            clus = client.getClusFromCluSet("CLUSETXXX-42");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            clus = client.getClusFromCluSet(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        clus = client.getAllClusInCluSet("CLUSET-4");
        assertEquals(2, clus.size());

        try {
            ids = client.getAllCluIdsInCluSet(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        clus = client.getAllClusInCluSet("CLUSET-2");
        assertEquals(3, clus.size());

        // isCluInCluSet
        Boolean inSet = client.isCluInCluSet("CLU-2", "CLUSET-4");
        assertTrue(inSet);

        inSet = client.isCluInCluSet("CLU-3", "CLUSET-4");
        assertTrue(inSet);

        inSet = client.isCluInCluSet("CLUXX-42", "CLUSET-4");
        assertFalse(inSet);

        inSet = client.isCluInCluSet("CLU-2", "CLUSETXX-42");
        assertFalse(inSet);

        try {
            inSet = client.isCluInCluSet(null, "CLUSET-4");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            inSet = client.isCluInCluSet("CLU-2", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCluSetCrud() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ParseException, VersionMismatchException, CircularReferenceException, UnsupportedActionException{
        CluSetInfo cluSetInfo = new CluSetInfo();

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>Formatted Desc</p>");
        desc.setPlain("plain");
        cluSetInfo.setDesc(desc);
        cluSetInfo.setEffectiveDate(DF.parse("20080101"));
        cluSetInfo.setExpirationDate(DF.parse("20180101"));
        cluSetInfo.setName("");
        cluSetInfo.getCluIds().add("CLU-1");
        cluSetInfo.getCluIds().add("CLU-2");
        cluSetInfo.getCluSetIds().add("CLUSET-1");
        cluSetInfo.getCluSetIds().add("CLUSET-2");
        cluSetInfo.getAttributes().put("cluSet1ArrtKey1", "cluSet1ArrtValue1");
        cluSetInfo.getAttributes().put("cluSet1ArrtKey2", "cluSet1ArrtValue2");

        CluSetInfo createdSet1 = client.createEnumeratedCluSet("testCluSet1", cluSetInfo);

        assertEquals("<p>Formatted Desc</p>",createdSet1.getDesc().getFormatted());
        assertEquals("plain",createdSet1.getDesc().getPlain());
        assertEquals(DF.parse("20080101"), createdSet1.getEffectiveDate());
        assertEquals(DF.parse("20180101"), createdSet1.getExpirationDate());
        assertEquals("testCluSet1",createdSet1.getName());
        assertEquals("CLU-1",createdSet1.getCluIds().get(0));
        assertEquals("CLU-2",createdSet1.getCluIds().get(1));
        assertEquals("CLUSET-1",createdSet1.getCluSetIds().get(0));
        assertEquals("CLUSET-2",createdSet1.getCluSetIds().get(1));
        assertEquals("cluSet1ArrtValue1",createdSet1.getAttributes().get("cluSet1ArrtKey1"));
        assertEquals("cluSet1ArrtValue2",createdSet1.getAttributes().get("cluSet1ArrtKey2"));
        assertNotNull(createdSet1.getMetaInfo().getCreateTime());
        assertNotNull(createdSet1.getMetaInfo().getUpdateTime());
        assertNotNull(createdSet1.getId());

        createdSet1.getDesc().setFormatted("UP<p>Formatted Desc</p>");
        createdSet1.getDesc().setPlain("UPplain");
        createdSet1.setEffectiveDate(DF.parse("20090101"));
        createdSet1.setExpirationDate(DF.parse("20190101"));
        createdSet1.setName("UPtestCluSet1");
        createdSet1.getCluIds().remove(1);
        createdSet1.getCluIds().add("CLU-3");
        createdSet1.getCluSetIds().remove(1);
        createdSet1.getCluSetIds().add("CLUSET-3");
        createdSet1.getAttributes().put("cluSet1ArrtKey1", "UPcluSet1ArrtValue1");
        createdSet1.getAttributes().remove("cluSet1ArrtKey2");
        createdSet1.getAttributes().put("cluSet1ArrtKey3", "cluSet1ArrtValue3");

        CluSetInfo updatedSet1 = client.updateCluSet(createdSet1.getId(), createdSet1);

        assertEquals("UP<p>Formatted Desc</p>",updatedSet1.getDesc().getFormatted());
        assertEquals("UPplain",updatedSet1.getDesc().getPlain());
        assertEquals(DF.parse("20090101"), updatedSet1.getEffectiveDate());
        assertEquals(DF.parse("20190101"), updatedSet1.getExpirationDate());
        assertEquals("UPtestCluSet1",updatedSet1.getName());
        assertEquals("CLU-1",updatedSet1.getCluIds().get(0));
        assertEquals("CLU-3",updatedSet1.getCluIds().get(1));
        assertEquals(2,updatedSet1.getCluIds().size());
        assertEquals("CLUSET-1",updatedSet1.getCluSetIds().get(0));
        assertEquals("CLUSET-3",updatedSet1.getCluSetIds().get(1));
        assertEquals(2,updatedSet1.getCluSetIds().size());
        assertEquals("UPcluSet1ArrtValue1",updatedSet1.getAttributes().get("cluSet1ArrtKey1"));
        assertEquals("cluSet1ArrtValue3",updatedSet1.getAttributes().get("cluSet1ArrtKey3"));
        assertEquals(2,updatedSet1.getAttributes().size());
        assertNotNull(updatedSet1.getMetaInfo().getUpdateTime());

        //Test Adds and removes
        StatusInfo status = client.addCluSetToCluSet(createdSet1.getId(), "CLUSET-4");
        assertTrue(status.getSuccess());
        status = client.addCluToCluSet("CLU-4",createdSet1.getId());
        assertTrue(status.getSuccess());

        CluSetInfo updatedSet2 = client.getCluSetInfo(createdSet1.getId());
        assertTrue(updatedSet2.getCluSetIds().contains("CLUSET-1"));
        assertTrue(updatedSet2.getCluSetIds().contains("CLUSET-3"));
        assertTrue(updatedSet2.getCluSetIds().contains("CLUSET-4"));
        assertEquals(3,updatedSet2.getCluSetIds().size());
        assertTrue(updatedSet2.getCluIds().contains("CLU-1"));
        assertTrue(updatedSet2.getCluIds().contains("CLU-3"));
        assertTrue(updatedSet2.getCluIds().contains("CLU-4"));
        assertEquals(3,updatedSet2.getCluIds().size());

        status = client.removeCluSetFromCluSet(createdSet1.getId(), "CLUSET-4");
        assertTrue(status.getSuccess());
        status = client.removeCluFromCluSet("CLU-4",createdSet1.getId());
        assertTrue(status.getSuccess());
        updatedSet2 = client.getCluSetInfo(createdSet1.getId());
        assertTrue(updatedSet2.getCluSetIds().contains("CLUSET-1"));
        assertTrue(updatedSet2.getCluSetIds().contains("CLUSET-3"));
        assertEquals(2,updatedSet2.getCluSetIds().size());
        assertTrue(updatedSet2.getCluIds().contains("CLU-1"));
        assertTrue(updatedSet2.getCluIds().contains("CLU-3"));
        assertEquals(2,updatedSet2.getCluIds().size());

        //Test circular check for set add
        try{
            client.addCluSetToCluSet("CLUSET-1", updatedSet2.getId());
            fail("Should have thrown circular reference exception.");
        }catch(CircularReferenceException e){
        }


        //test opt locking, gets and deletes
        try{
            updatedSet1 = client.updateCluSet(createdSet1.getId(), createdSet1);
            fail("Should have thrown VersionMismatchException.");
        }catch(VersionMismatchException e){
        }

        status = client.deleteCluSet(createdSet1.getId());
        assertTrue(status.getSuccess());

        try{
            client.getCluSetInfo(createdSet1.getId());
            fail("Should have thrown DoesNotExistException");
        }catch(DoesNotExistException e){

        }

    }

    @Test
    public void testCluCluRelation() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {
        List<CluCluRelationInfo> ccrs = client.getCluCluRelationsByClu("CLU-1");
        assertNotNull(ccrs);
        assertEquals(2, ccrs.size());

        ccrs = client.getCluCluRelationsByClu("CLUXX-42");
        assertNotNull(ccrs);
        assertEquals(0, ccrs.size());

        try {
            ccrs = client.getCluCluRelationsByClu(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testCluCrud() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException{

        CluInfo clu = new CluInfo();

        CluAccountingInfo accountingInfo = new CluAccountingInfo();
        accountingInfo.getAttributes().put("AccountingAttrKey1", "AccountingAttrValue1");
        accountingInfo.getAttributes().put("AccountingAttrKey2", "AccountingAttrValue2");
        clu.setAccountingInfo(accountingInfo);

        CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
        officialIdentifier.setCode("offId_code");
        officialIdentifier.setDivision("offId_division");
        officialIdentifier.setLevel("offId_level");
        officialIdentifier.setLongName("offId_longName");
        officialIdentifier.setShortName("offId_shortName");
        officialIdentifier.setState("offId_state");
        officialIdentifier.setType("offId_type");
        officialIdentifier.setVariation("offId_variation");
        officialIdentifier.setSuffixCode("offId_suffixcode");
        officialIdentifier.setOrgId("offId_orgid");
        clu.setOfficialIdentifier(officialIdentifier);

        CluIdentifierInfo cluId1 = new CluIdentifierInfo();
        cluId1.setCode("cluId1_code");
        cluId1.setDivision("cluId1_division");
        cluId1.setLevel("cluId1_level");
        cluId1.setLongName("cluId1_longName");
        cluId1.setShortName("cluId1_shortName");
        cluId1.setState("cluId1_state");
        cluId1.setType("cluId1_type");
        cluId1.setVariation("cluId1_variation");
        cluId1.setSuffixCode("cluId1_suffixcode");
        cluId1.setOrgId("cluId1_orgid");
        clu.getAlternateIdentifiers().add(cluId1);

        CluIdentifierInfo cluId2 = new CluIdentifierInfo();
        cluId2.setCode("cluId2_code");
        cluId2.setDivision("cluId2_division");
        cluId2.setLevel("cluId2_level");
        cluId2.setLongName("cluId2_longName");
        cluId2.setShortName("cluId2_shortName");
        cluId2.setState("cluId2_state");
        cluId2.setType("cluId2_type");
        cluId2.setVariation("cluId2_variation");
        cluId2.setSuffixCode("cluId2_suffixcode");
        cluId2.setOrgId("cluId2_orgid");
        clu.getAlternateIdentifiers().add(cluId2);

        clu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
        clu.getAttributes().put("cluAttrKey2", "cluAttrValue2");

        clu.setCanCreateLui(true);

        clu.setDefaultEnrollmentEstimate(545);
        clu.setDefaultMaximumEnrollment(999);

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>DESC FORMATTED</p>");
        desc.setPlain("DESC PLAIN");
        clu.setDesc(desc);

        clu.setEffectiveDate(DF.parse("20100203"));
        clu.setExpirationDate(DF.parse("21001231"));

        clu.setEnrollable(true);

        CluFeeInfo feeInfo = new CluFeeInfo();
        feeInfo.getAttributes().put("FeeAttrKey1", "FeeAttrValue1");
        feeInfo.getAttributes().put("FeeAttrKey2", "FeeAttrValue2");
        clu.setFeeInfo(feeInfo);

        clu.setHasEarlyDropDeadline(true);

        clu.setHazardousForDisabledStudents(true);

        CluInstructorInfo primaryInstructor = new CluInstructorInfo();
        primaryInstructor.setOrgId("EXT_orgId_1");
        primaryInstructor.setPersonId("EXT_personId_1");
        primaryInstructor.getAttributes().put("PrimaryInstAttrKey1", "PrimaryInstAttrValue1");
        primaryInstructor.getAttributes().put("PrimaryInstAttrKey2", "PrimaryInstAttrValue2");
        clu.setPrimaryInstructor(primaryInstructor);

        CluInstructorInfo instructor1 = new CluInstructorInfo();
        instructor1.setOrgId("EXT_orgId_2");
        instructor1.setPersonId("EXT_personId_2");
        instructor1.getAttributes().put("Inst1AttrKey1", "Inst1AttrValue1");
        instructor1.getAttributes().put("Inst1AttrKey2", "Inst1AttrValue2");
        clu.getInstructors().add(instructor1);

        CluInstructorInfo instructor2 = new CluInstructorInfo();
        instructor2.setOrgId("EXT_orgId_3");
        instructor2.setPersonId("EXT_personId_3");
        instructor2.getAttributes().put("Inst2AttrKey1", "Inst2AttrValue1");
        instructor2.getAttributes().put("Inst2AttrKey2", "Inst2AttrValue2");
        clu.getInstructors().add(instructor2);

        LuCodeInfo luCode1 = new LuCodeInfo();
        luCode1.setId("luCode1.key");
        luCode1.setDesc("luCode1_desc");
        luCode1.setValue("luCode1_value");
        luCode1.getAttributes().put("luCode1AttrKey1", "luCode1AttrValue1");
        luCode1.getAttributes().put("luCode1AttrKey2", "luCode1AttrValue2");
        clu.getLuCodes().add(luCode1);

        LuCodeInfo luCode2 = new LuCodeInfo();
        luCode2.setId("luCode2.key");
        luCode2.setDesc("luCode2_desc");
        luCode2.setValue("luCode2_value");
        luCode2.getAttributes().put("luCode2AttrKey1", "luCode2AttrValue1");
        luCode2.getAttributes().put("luCode2AttrKey2", "luCode2AttrValue2");
        clu.getLuCodes().add(luCode2);

        RichTextInfo marketingDesc = new RichTextInfo();
        marketingDesc.setFormatted("<p>marketingDesc FORMATTED</p>");
        marketingDesc.setPlain("marketingDesc PLAIN");
        clu.setMarketingDesc(marketingDesc);

        clu.setNextReviewPeriod("nextReviewPeriod");

        clu.getOfferedAtpTypes().add("offeredAtpType1");
        clu.getOfferedAtpTypes().add("offeredAtpType2");

        CluPublishingInfo publishingInfo = new CluPublishingInfo();
        publishingInfo.setEndCycle("endCycle");
        publishingInfo.setStartCycle("startCycle");
        publishingInfo.setState("state");
        publishingInfo.setType("type");
        publishingInfo.getAttributes().put("publishingInfoAttrKey1", "publishingInfoAttrValue1");
        publishingInfo.getAttributes().put("publishingInfoAttrKey2", "publishingInfoAttrValue2");

        CluInstructorInfo pubPrimaryInstructor = new CluInstructorInfo();
        pubPrimaryInstructor.setOrgId("EXT_orgId_234");
        pubPrimaryInstructor.setPersonId("EXT_personId_2451");
        pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey1", "PubPrimaryInstAttrValue1");
        pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey2", "PubPrimaryInstAttrValue2");
        publishingInfo.setPrimaryInstructor(pubPrimaryInstructor);

        CluInstructorInfo pubInstructor1 = new CluInstructorInfo();
        pubInstructor1.setOrgId("EXT_orgId_2");
        pubInstructor1.setPersonId("EXT_personId_2");
        pubInstructor1.getAttributes().put("PubInst1AttrKey1", "PubInst1AttrValue1");
        pubInstructor1.getAttributes().put("PubInst1AttrKey2", "PubInst1AttrValue2");
        publishingInfo.getInstructors().add(pubInstructor1);

        CluInstructorInfo pubInstructor2 = new CluInstructorInfo();
        pubInstructor2.setOrgId("EXT_orgId_3");
        pubInstructor2.setPersonId("EXT_personId_3");
        pubInstructor2.getAttributes().put("PubInst2AttrKey1", "PubInst2AttrValue1");
        pubInstructor2.getAttributes().put("PubInst2AttrKey2", "PubInst2AttrValue2");
        publishingInfo.getInstructors().add(pubInstructor2);

        clu.setPublishingInfo(publishingInfo);

        clu.setReferenceURL("http://student.kuali.org/clus");

        clu.setState("Clu state");

        TimeAmountInfo stdDuration = new TimeAmountInfo();
        stdDuration.setAtpDurationTypeKey("EXT_stdDuration_Id1");
        stdDuration.setTimeQuantity(new Integer(7867));
        clu.setStdDuration(stdDuration);

        clu.setType("");

        createAcademicSubjectOrgs(clu);

        createCampusLocationList(clu);

        createIntensity(clu);

        createAccreditationList(clu);

        createAdminOrgs(clu);

        //Do the actual create call
        CluInfo createdClu = client.createClu("luType.shell.course", clu);
        createdClu = client.getClu(createdClu.getId());
        //Validate Results
        assertNotNull(createdClu);

        assertEquals("AccountingAttrValue1", createdClu.getAccountingInfo().getAttributes().get("AccountingAttrKey1"));
        assertEquals("AccountingAttrValue2", createdClu.getAccountingInfo().getAttributes().get("AccountingAttrKey2"));

        assertEquals("offId_code",createdClu.getOfficialIdentifier().getCode());
        assertEquals("offId_division",createdClu.getOfficialIdentifier().getDivision());
        assertEquals("offId_level",createdClu.getOfficialIdentifier().getLevel());
        assertEquals("offId_longName",createdClu.getOfficialIdentifier().getLongName());
        assertEquals("offId_shortName",createdClu.getOfficialIdentifier().getShortName());
        assertEquals("offId_state",createdClu.getOfficialIdentifier().getState());
        assertEquals("offId_type",createdClu.getOfficialIdentifier().getType());
        assertEquals("offId_variation",createdClu.getOfficialIdentifier().getVariation());
        assertEquals("offId_suffixcode",createdClu.getOfficialIdentifier().getSuffixCode());
        assertEquals("offId_orgid",createdClu.getOfficialIdentifier().getOrgId());

        assertEquals("cluId1_code",createdClu.getAlternateIdentifiers().get(0).getCode());
        assertEquals("cluId1_division",createdClu.getAlternateIdentifiers().get(0).getDivision());
        assertEquals("cluId1_level",createdClu.getAlternateIdentifiers().get(0).getLevel());
        assertEquals("cluId1_longName",createdClu.getAlternateIdentifiers().get(0).getLongName());
        assertEquals("cluId1_shortName",createdClu.getAlternateIdentifiers().get(0).getShortName());
        assertEquals("cluId1_state",createdClu.getAlternateIdentifiers().get(0).getState());
        assertEquals("cluId1_type",createdClu.getAlternateIdentifiers().get(0).getType());
        assertEquals("cluId1_variation",createdClu.getAlternateIdentifiers().get(0).getVariation());
        assertEquals("cluId1_suffixcode",createdClu.getAlternateIdentifiers().get(0).getSuffixCode());
        assertEquals("cluId1_orgid",createdClu.getAlternateIdentifiers().get(0).getOrgId());

        assertEquals("cluId2_code",createdClu.getAlternateIdentifiers().get(1).getCode());
        assertEquals("cluId2_division",createdClu.getAlternateIdentifiers().get(1).getDivision());
        assertEquals("cluId2_level",createdClu.getAlternateIdentifiers().get(1).getLevel());
        assertEquals("cluId2_longName",createdClu.getAlternateIdentifiers().get(1).getLongName());
        assertEquals("cluId2_shortName",createdClu.getAlternateIdentifiers().get(1).getShortName());
        assertEquals("cluId2_state",createdClu.getAlternateIdentifiers().get(1).getState());
        assertEquals("cluId2_type",createdClu.getAlternateIdentifiers().get(1).getType());
        assertEquals("cluId2_variation",createdClu.getAlternateIdentifiers().get(1).getVariation());
        assertEquals("cluId2_suffixcode",createdClu.getAlternateIdentifiers().get(1).getSuffixCode());
        assertEquals("cluId2_orgid",createdClu.getAlternateIdentifiers().get(1).getOrgId());

        assertEquals("cluAttrValue1",createdClu.getAttributes().get("cluAttrKey1"));
        assertEquals("cluAttrValue2",createdClu.getAttributes().get("cluAttrKey2"));

        assertTrue(createdClu.isCanCreateLui());

        assertEquals(545,createdClu.getDefaultEnrollmentEstimate());
        assertEquals(999,createdClu.getDefaultMaximumEnrollment());

        assertEquals("<p>DESC FORMATTED</p>",createdClu.getDesc().getFormatted());
        assertEquals("DESC PLAIN",createdClu.getDesc().getPlain());

        assertEquals(DF.parse("20100203"),createdClu.getEffectiveDate());
        assertEquals(DF.parse("21001231"),createdClu.getExpirationDate());

        assertTrue(createdClu.isEnrollable());

        assertEquals("FeeAttrValue1",createdClu.getFeeInfo().getAttributes().get("FeeAttrKey1"));
        assertEquals("FeeAttrValue2",createdClu.getFeeInfo().getAttributes().get("FeeAttrKey2"));

        assertTrue(createdClu.isHasEarlyDropDeadline());
        assertTrue(createdClu.isHazardousForDisabledStudents());

        assertEquals("EXT_orgId_1",createdClu.getPrimaryInstructor().getOrgId());
        assertEquals("EXT_personId_1",createdClu.getPrimaryInstructor().getPersonId());
        assertEquals("PrimaryInstAttrValue1",createdClu.getPrimaryInstructor().getAttributes().get("PrimaryInstAttrKey1"));
        assertEquals("PrimaryInstAttrValue2",createdClu.getPrimaryInstructor().getAttributes().get("PrimaryInstAttrKey2"));

        assertEquals("EXT_orgId_2",createdClu.getInstructors().get(0).getOrgId());
        assertEquals("EXT_personId_2",createdClu.getInstructors().get(0).getPersonId());
        assertEquals("Inst1AttrValue1",createdClu.getInstructors().get(0).getAttributes().get("Inst1AttrKey1"));
        assertEquals("Inst1AttrValue2",createdClu.getInstructors().get(0).getAttributes().get("Inst1AttrKey2"));

        assertEquals("EXT_orgId_3",createdClu.getInstructors().get(1).getOrgId());
        assertEquals("EXT_personId_3",createdClu.getInstructors().get(1).getPersonId());
        assertEquals("Inst2AttrValue1",createdClu.getInstructors().get(1).getAttributes().get("Inst2AttrKey1"));
        assertEquals("Inst2AttrValue2",createdClu.getInstructors().get(1).getAttributes().get("Inst2AttrKey2"));

        assertEquals("luCode1.key",createdClu.getLuCodes().get(0).getId());
        assertEquals("luCode1_desc",createdClu.getLuCodes().get(0).getDesc());
        assertEquals("luCode1_value",createdClu.getLuCodes().get(0).getValue());
        assertEquals("luCode1AttrValue1",createdClu.getLuCodes().get(0).getAttributes().get("luCode1AttrKey1"));
        assertEquals("luCode1AttrValue2",createdClu.getLuCodes().get(0).getAttributes().get("luCode1AttrKey2"));
        assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo());
        assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo().getVersionInd());
        assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo().getCreateTime());

        assertEquals("luCode2.key",createdClu.getLuCodes().get(1).getId());
        assertEquals("luCode2_desc",createdClu.getLuCodes().get(1).getDesc());
        assertEquals("luCode2_value",createdClu.getLuCodes().get(1).getValue());
        assertEquals("luCode2AttrValue1",createdClu.getLuCodes().get(1).getAttributes().get("luCode2AttrKey1"));
        assertEquals("luCode2AttrValue2",createdClu.getLuCodes().get(1).getAttributes().get("luCode2AttrKey2"));
        assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo());
        assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo().getVersionInd());
        assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo().getCreateTime());

        assertEquals("<p>marketingDesc FORMATTED</p>",createdClu.getMarketingDesc().getFormatted());
        assertEquals("marketingDesc PLAIN",createdClu.getMarketingDesc().getPlain());

        assertEquals("nextReviewPeriod",createdClu.getNextReviewPeriod());

        assertEquals("offeredAtpType1",createdClu.getOfferedAtpTypes().get(0));
        assertEquals("offeredAtpType2",createdClu.getOfferedAtpTypes().get(1));

        assertEquals("endCycle",createdClu.getPublishingInfo().getEndCycle());
        assertEquals("startCycle",createdClu.getPublishingInfo().getStartCycle());
        assertEquals("state",createdClu.getPublishingInfo().getState());
        assertEquals("type",createdClu.getPublishingInfo().getType());
        assertEquals("publishingInfoAttrValue1",createdClu.getPublishingInfo().getAttributes().get("publishingInfoAttrKey1"));
        assertEquals("publishingInfoAttrValue2",createdClu.getPublishingInfo().getAttributes().get("publishingInfoAttrKey2"));

        assertEquals("EXT_orgId_234",createdClu.getPublishingInfo().getPrimaryInstructor().getOrgId());
        assertEquals("EXT_personId_2451",createdClu.getPublishingInfo().getPrimaryInstructor().getPersonId());
        assertEquals("PubPrimaryInstAttrValue1",createdClu.getPublishingInfo().getPrimaryInstructor().getAttributes().get("PubPrimaryInstAttrKey1"));
        assertEquals("PubPrimaryInstAttrValue2",createdClu.getPublishingInfo().getPrimaryInstructor().getAttributes().get("PubPrimaryInstAttrKey2"));

        assertEquals("EXT_orgId_2",createdClu.getPublishingInfo().getInstructors().get(0).getOrgId());
        assertEquals("EXT_personId_2",createdClu.getPublishingInfo().getInstructors().get(0).getPersonId());
        assertEquals("PubInst1AttrValue1",createdClu.getPublishingInfo().getInstructors().get(0).getAttributes().get("PubInst1AttrKey1"));
        assertEquals("PubInst1AttrValue2",createdClu.getPublishingInfo().getInstructors().get(0).getAttributes().get("PubInst1AttrKey2"));

        assertEquals("EXT_orgId_3",createdClu.getPublishingInfo().getInstructors().get(1).getOrgId());
        assertEquals("EXT_personId_3",createdClu.getPublishingInfo().getInstructors().get(1).getPersonId());
        assertEquals("PubInst2AttrValue1",createdClu.getPublishingInfo().getInstructors().get(1).getAttributes().get("PubInst2AttrKey1"));
        assertEquals("PubInst2AttrValue2",createdClu.getPublishingInfo().getInstructors().get(1).getAttributes().get("PubInst2AttrKey2"));


        assertEquals("http://student.kuali.org/clus",createdClu.getReferenceURL());

        assertEquals("Clu state",createdClu.getState());

        assertEquals("EXT_stdDuration_Id1",createdClu.getStdDuration().getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(7867),createdClu.getStdDuration().getTimeQuantity());


        assertEquals("luType.shell.course",createdClu.getType());

        assertNotNull(createdClu.getMetaInfo());
        assertNotNull(createdClu.getMetaInfo().getVersionInd());
        assertNotNull(createdClu.getMetaInfo().getCreateTime());

        assertNotNull(createdClu.getId());

        checkAcademicSubjectOrgsCreate(createdClu);

        checkCampusLocationCreate(createdClu);

        checkIntensityCreate(createdClu);

        checkAccreditationListCreate(createdClu);

        checkAdminOrgsCreate(createdClu);

        //Now Update the Clu!
        createdClu.getAccountingInfo().getAttributes().put("AccountingAttrKey1", "AccountingAttrValue1");
        createdClu.getAccountingInfo().getAttributes().remove("AccountingAttrKey2");
        createdClu.getAccountingInfo().getAttributes().put("AccountingAttrKey3", "AccountingAttrValue3");

        createdClu.getOfficialIdentifier().setCode("UPoffId_code");
        createdClu.getOfficialIdentifier().setDivision("UPoffId_division");
        createdClu.getOfficialIdentifier().setLevel("UPoffId_level");
        createdClu.getOfficialIdentifier().setLongName("UPoffId_longName");
        createdClu.getOfficialIdentifier().setShortName("UPoffId_shortName");
        createdClu.getOfficialIdentifier().setState("UPoffId_state");
        createdClu.getOfficialIdentifier().setType("UPoffId_type");
        createdClu.getOfficialIdentifier().setVariation("UPoffId_variation");

        createdClu.getAlternateIdentifiers().get(0).setCode("UPcluId1_code");
        createdClu.getAlternateIdentifiers().get(0).setDivision("UPcluId1_division");
        createdClu.getAlternateIdentifiers().get(0).setLevel("UPcluId1_level");
        createdClu.getAlternateIdentifiers().get(0).setLongName("UPcluId1_longName");
        createdClu.getAlternateIdentifiers().get(0).setShortName("UPcluId1_shortName");
        createdClu.getAlternateIdentifiers().get(0).setState("UPcluId1_state");
        createdClu.getAlternateIdentifiers().get(0).setType("UPcluId1_type");
        createdClu.getAlternateIdentifiers().get(0).setVariation("UPcluId1_variation");

        createdClu.getAlternateIdentifiers().remove(1);

        CluIdentifierInfo cluId3 = new CluIdentifierInfo();
        cluId3.setCode("cluId3_code");
        cluId3.setDivision("cluId3_division");
        cluId3.setLevel("cluId3_level");
        cluId3.setLongName("cluId3_longName");
        cluId3.setShortName("cluId3_shortName");
        cluId3.setState("cluId3_state");
        cluId3.setType("cluId3_type");
        cluId3.setVariation("cluId3_variation");
        createdClu.getAlternateIdentifiers().add(cluId3);

        createdClu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
        createdClu.getAttributes().remove("cluAttrKey2");
        createdClu.getAttributes().put("cluAttrKey3", "cluAttrValue3");

        createdClu.setCanCreateLui(false);

        createdClu.setDefaultEnrollmentEstimate(9545);
        createdClu.setDefaultMaximumEnrollment(9999);

        createdClu.getDesc().setFormatted("UP<p>DESC FORMATTED</p>");
        createdClu.getDesc().setPlain("UPDESC PLAIN");

        createdClu.setEffectiveDate(DF.parse("20190203"));
        createdClu.setExpirationDate(DF.parse("21091231"));

        createdClu.setEnrollable(false);

        createdClu.getFeeInfo().getAttributes().put("FeeAttrKey1", "FeeAttrValue1");
        createdClu.getFeeInfo().getAttributes().remove("FeeAttrKey2");
        createdClu.getFeeInfo().getAttributes().put("FeeAttrKey3", "FeeAttrValue3");

        createdClu.setHasEarlyDropDeadline(false);

        createdClu.setHazardousForDisabledStudents(false);

        createdClu.getPrimaryInstructor().setOrgId("UPEXT_orgId_1");
        createdClu.getPrimaryInstructor().setPersonId("UPEXT_personId_1");
        createdClu.getPrimaryInstructor().getAttributes().put("PrimaryInstAttrKey1", "PrimaryInstAttrValue1");
        createdClu.getPrimaryInstructor().getAttributes().remove("PrimaryInstAttrKey2");
        createdClu.getPrimaryInstructor().getAttributes().put("PrimaryInstAttrKey3", "PrimaryInstAttrValue3");

        createdClu.getInstructors().get(0).setOrgId("UPEXT_orgId_2");
        createdClu.getInstructors().get(0).setPersonId("UPEXT_personId_2");
        createdClu.getInstructors().get(0).getAttributes().put("Inst1AttrKey1", "Inst1AttrValue1");
        createdClu.getInstructors().get(0).getAttributes().remove("Inst1AttrKey2");
        createdClu.getInstructors().get(0).getAttributes().put("Inst1AttrKey3", "Inst1AttrValue3");

        createdClu.getInstructors().remove(1);

        CluInstructorInfo instructor3 = new CluInstructorInfo();
        instructor3.setOrgId("EXT_orgId_3");
        instructor3.setPersonId("EXT_personId_3");
        instructor3.getAttributes().put("Inst3AttrKey1", "Inst3AttrValue1");
        instructor3.getAttributes().put("Inst3AttrKey2", "Inst3AttrValue2");
        createdClu.getInstructors().add(instructor3);

        createdClu.getLuCodes().get(0).setDesc("UPluCode1_desc");
        createdClu.getLuCodes().get(0).setValue("UPluCode1_value");
        createdClu.getLuCodes().get(0).getAttributes().put("luCode1AttrKey1", "luCode1AttrValue1");
        createdClu.getLuCodes().get(0).getAttributes().remove("luCode1AttrKey2");
        createdClu.getLuCodes().get(0).getAttributes().put("luCode1AttrKey3", "luCode1AttrValue3");

        createdClu.getLuCodes().remove(1);

        LuCodeInfo luCode3 = new LuCodeInfo();
        luCode3.setId("luCode3.key");
        luCode3.setDesc("luCode3_desc");
        luCode3.setValue("luCode3_value");
        luCode3.getAttributes().put("luCode3AttrKey1", "luCode3AttrValue1");
        luCode3.getAttributes().put("luCode3AttrKey2", "luCode3AttrValue2");
        createdClu.getLuCodes().add(luCode3);

        createdClu.getMarketingDesc().setFormatted("UP<p>marketingDesc FORMATTED</p>");
        createdClu.getMarketingDesc().setPlain("UPmarketingDesc PLAIN");

        createdClu.setNextReviewPeriod("UPnextReviewPeriod");

        createdClu.getOfferedAtpTypes().remove(1);
        createdClu.getOfferedAtpTypes().add("offeredAtpType3");

        createdClu.getPublishingInfo().setEndCycle("UPendCycle");
        createdClu.getPublishingInfo().setStartCycle("UPstartCycle");
        createdClu.getPublishingInfo().setState("UPstate");
        createdClu.getPublishingInfo().setType("UPtype");
        createdClu.getPublishingInfo().getAttributes().put("publishingInfoAttrKey1", "publishingInfoAttrValue1");
        createdClu.getPublishingInfo().getAttributes().remove("publishingInfoAttrKey2");
        createdClu.getPublishingInfo().getAttributes().put("publishingInfoAttrKey3", "publishingInfoAttrValue3");

        createdClu.getPublishingInfo().getPrimaryInstructor().setOrgId("UPEXT_orgId_234");
        createdClu.getPublishingInfo().getPrimaryInstructor().setPersonId("UPEXT_personId_2451");
        createdClu.getPublishingInfo().getPrimaryInstructor().getAttributes().put("PubPrimaryInstAttrKey1", "PubPrimaryInstAttrValue1");
        createdClu.getPublishingInfo().getPrimaryInstructor().getAttributes().remove("PubPrimaryInstAttrKey2");
        createdClu.getPublishingInfo().getPrimaryInstructor().getAttributes().put("PubPrimaryInstAttrKey3", "PubPrimaryInstAttrValue3");

        createdClu.getPublishingInfo().getInstructors().get(0).setOrgId("UPEXT_orgId_2");
        createdClu.getPublishingInfo().getInstructors().get(0).setPersonId("UPEXT_personId_2");
        createdClu.getPublishingInfo().getInstructors().get(0).getAttributes().put("PubInst1AttrKey1", "PubInst1AttrValue1");
        createdClu.getPublishingInfo().getInstructors().get(0).getAttributes().remove("PubInst1AttrKey2");
        createdClu.getPublishingInfo().getInstructors().get(0).getAttributes().put("PubInst1AttrKey3", "PubInst1AttrValue3");

        createdClu.getPublishingInfo().getInstructors().remove(1);

        CluInstructorInfo pubInstructor3 = new CluInstructorInfo();
        pubInstructor3.setOrgId("EXT_orgId_3");
        pubInstructor3.setPersonId("EXT_personId_3");
        pubInstructor3.getAttributes().put("PubInst3AttrKey1", "PubInst3AttrValue1");
        pubInstructor3.getAttributes().put("PubInst3AttrKey2", "PubInst3AttrValue2");
        createdClu.getPublishingInfo().getInstructors().add(pubInstructor3);

        createdClu.setReferenceURL("UPhttp://student.kuali.org/clus");

        createdClu.setState("UPClu state");

        createdClu.getStdDuration().setAtpDurationTypeKey("UPEXT_stdDuration_Id1");
        createdClu.getStdDuration().setTimeQuantity(new Integer(97867));

        createdClu.setType("luType.shell.program");

        updateAdminOrgs(createdClu);

        updateAcademicSubjectOrgs(createdClu);

        updateAccreditationList(createdClu);

        updateIntensity(createdClu);

        updateCampusLocationList(createdClu);

        //Do Update
        CluInfo updatedClu = client.updateClu(createdClu.getId(), createdClu);

        //Validate Results
        assertNotNull(updatedClu);

        assertEquals("AccountingAttrValue1", updatedClu.getAccountingInfo().getAttributes().get("AccountingAttrKey1"));
        assertEquals("AccountingAttrValue3", updatedClu.getAccountingInfo().getAttributes().get("AccountingAttrKey3"));
        assertEquals(2,updatedClu.getAccountingInfo().getAttributes().size());

        assertEquals("UPoffId_code",updatedClu.getOfficialIdentifier().getCode());
        assertEquals("UPoffId_division",updatedClu.getOfficialIdentifier().getDivision());
        assertEquals("UPoffId_level",updatedClu.getOfficialIdentifier().getLevel());
        assertEquals("UPoffId_longName",updatedClu.getOfficialIdentifier().getLongName());
        assertEquals("UPoffId_shortName",updatedClu.getOfficialIdentifier().getShortName());
        assertEquals("UPoffId_state",updatedClu.getOfficialIdentifier().getState());
        assertEquals("UPoffId_type",updatedClu.getOfficialIdentifier().getType());
        assertEquals("UPoffId_variation",updatedClu.getOfficialIdentifier().getVariation());

        assertEquals("UPcluId1_code",updatedClu.getAlternateIdentifiers().get(0).getCode());
        assertEquals("UPcluId1_division",updatedClu.getAlternateIdentifiers().get(0).getDivision());
        assertEquals("UPcluId1_level",updatedClu.getAlternateIdentifiers().get(0).getLevel());
        assertEquals("UPcluId1_longName",updatedClu.getAlternateIdentifiers().get(0).getLongName());
        assertEquals("UPcluId1_shortName",updatedClu.getAlternateIdentifiers().get(0).getShortName());
        assertEquals("UPcluId1_state",updatedClu.getAlternateIdentifiers().get(0).getState());
        assertEquals("UPcluId1_type",updatedClu.getAlternateIdentifiers().get(0).getType());
        assertEquals("UPcluId1_variation",updatedClu.getAlternateIdentifiers().get(0).getVariation());

        assertEquals("cluId3_code",updatedClu.getAlternateIdentifiers().get(1).getCode());
        assertEquals("cluId3_division",updatedClu.getAlternateIdentifiers().get(1).getDivision());
        assertEquals("cluId3_level",updatedClu.getAlternateIdentifiers().get(1).getLevel());
        assertEquals("cluId3_longName",updatedClu.getAlternateIdentifiers().get(1).getLongName());
        assertEquals("cluId3_shortName",updatedClu.getAlternateIdentifiers().get(1).getShortName());
        assertEquals("cluId3_state",updatedClu.getAlternateIdentifiers().get(1).getState());
        assertEquals("cluId3_type",updatedClu.getAlternateIdentifiers().get(1).getType());
        assertEquals("cluId3_variation",updatedClu.getAlternateIdentifiers().get(1).getVariation());

        assertEquals(2,updatedClu.getAlternateIdentifiers().size());

        assertEquals("cluAttrValue1",updatedClu.getAttributes().get("cluAttrKey1"));
        assertEquals("cluAttrValue3",updatedClu.getAttributes().get("cluAttrKey3"));
        assertEquals(2,updatedClu.getAttributes().size());

        assertFalse(updatedClu.isCanCreateLui());

        assertEquals(9545,updatedClu.getDefaultEnrollmentEstimate());
        assertEquals(9999,updatedClu.getDefaultMaximumEnrollment());

        assertEquals("UP<p>DESC FORMATTED</p>",updatedClu.getDesc().getFormatted());
        assertEquals("UPDESC PLAIN",updatedClu.getDesc().getPlain());

        assertEquals(DF.parse("20190203"),updatedClu.getEffectiveDate());
        assertEquals(DF.parse("21091231"),updatedClu.getExpirationDate());

        assertFalse(updatedClu.isEnrollable());

        assertEquals("FeeAttrValue1",updatedClu.getFeeInfo().getAttributes().get("FeeAttrKey1"));
        assertEquals("FeeAttrValue3",updatedClu.getFeeInfo().getAttributes().get("FeeAttrKey3"));
        assertEquals(2,updatedClu.getFeeInfo().getAttributes().size());

        assertFalse(updatedClu.isHasEarlyDropDeadline());
        assertFalse(updatedClu.isHazardousForDisabledStudents());

        assertEquals("UPEXT_orgId_1",updatedClu.getPrimaryInstructor().getOrgId());
        assertEquals("UPEXT_personId_1",updatedClu.getPrimaryInstructor().getPersonId());
        assertEquals("PrimaryInstAttrValue1",updatedClu.getPrimaryInstructor().getAttributes().get("PrimaryInstAttrKey1"));
        assertEquals("PrimaryInstAttrValue3",updatedClu.getPrimaryInstructor().getAttributes().get("PrimaryInstAttrKey3"));
        assertEquals(2,updatedClu.getPrimaryInstructor().getAttributes().size());

        assertEquals(2,updatedClu.getInstructors().size());

        assertEquals("UPEXT_orgId_2",updatedClu.getInstructors().get(0).getOrgId());
        assertEquals("UPEXT_personId_2",updatedClu.getInstructors().get(0).getPersonId());
        assertEquals("Inst1AttrValue1",updatedClu.getInstructors().get(0).getAttributes().get("Inst1AttrKey1"));
        assertEquals("Inst1AttrValue3",updatedClu.getInstructors().get(0).getAttributes().get("Inst1AttrKey3"));
        assertEquals(2,updatedClu.getInstructors().get(0).getAttributes().size());

        assertEquals("EXT_orgId_3",updatedClu.getInstructors().get(1).getOrgId());
        assertEquals("EXT_personId_3",updatedClu.getInstructors().get(1).getPersonId());
        assertEquals("Inst3AttrValue1",updatedClu.getInstructors().get(1).getAttributes().get("Inst3AttrKey1"));
        assertEquals("Inst3AttrValue2",updatedClu.getInstructors().get(1).getAttributes().get("Inst3AttrKey2"));
        assertEquals(2,updatedClu.getInstructors().get(1).getAttributes().size());

        assertEquals(2,updatedClu.getLuCodes().size());

        assertEquals("luCode1.key",updatedClu.getLuCodes().get(0).getId());
        assertEquals("UPluCode1_desc",updatedClu.getLuCodes().get(0).getDesc());
        assertEquals("UPluCode1_value",updatedClu.getLuCodes().get(0).getValue());
        assertEquals("luCode1AttrValue1",updatedClu.getLuCodes().get(0).getAttributes().get("luCode1AttrKey1"));
        assertEquals("luCode1AttrValue3",updatedClu.getLuCodes().get(0).getAttributes().get("luCode1AttrKey3"));
        assertEquals(2, updatedClu.getLuCodes().get(0).getAttributes().size());
        assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo());
        assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo().getVersionInd());
        assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo().getCreateTime());
        assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo().getUpdateTime());

        assertEquals("luCode3.key",updatedClu.getLuCodes().get(1).getId());
        assertEquals("luCode3_desc",updatedClu.getLuCodes().get(1).getDesc());
        assertEquals("luCode3_value",updatedClu.getLuCodes().get(1).getValue());
        assertEquals("luCode3AttrValue1",updatedClu.getLuCodes().get(1).getAttributes().get("luCode3AttrKey1"));
        assertEquals("luCode3AttrValue2",updatedClu.getLuCodes().get(1).getAttributes().get("luCode3AttrKey2"));
        assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo());
        assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo().getVersionInd());
        assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo().getCreateTime());
        assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo().getUpdateTime());

        assertEquals("UP<p>marketingDesc FORMATTED</p>",updatedClu.getMarketingDesc().getFormatted());
        assertEquals("UPmarketingDesc PLAIN",updatedClu.getMarketingDesc().getPlain());

        assertEquals("UPnextReviewPeriod",updatedClu.getNextReviewPeriod());

        assertEquals("offeredAtpType1",updatedClu.getOfferedAtpTypes().get(0));
        assertEquals("offeredAtpType3",updatedClu.getOfferedAtpTypes().get(1));
        assertEquals(2,updatedClu.getOfferedAtpTypes().size());

        assertEquals("UPendCycle",updatedClu.getPublishingInfo().getEndCycle());
        assertEquals("UPstartCycle",updatedClu.getPublishingInfo().getStartCycle());
        assertEquals("UPstate",updatedClu.getPublishingInfo().getState());
        assertEquals("UPtype",updatedClu.getPublishingInfo().getType());
        assertEquals("publishingInfoAttrValue1",updatedClu.getPublishingInfo().getAttributes().get("publishingInfoAttrKey1"));
        assertEquals("publishingInfoAttrValue3",updatedClu.getPublishingInfo().getAttributes().get("publishingInfoAttrKey3"));
        assertEquals(2,updatedClu.getPublishingInfo().getAttributes().size());

        assertEquals("UPEXT_orgId_234",updatedClu.getPublishingInfo().getPrimaryInstructor().getOrgId());
        assertEquals("UPEXT_personId_2451",updatedClu.getPublishingInfo().getPrimaryInstructor().getPersonId());
        assertEquals("PubPrimaryInstAttrValue1",updatedClu.getPublishingInfo().getPrimaryInstructor().getAttributes().get("PubPrimaryInstAttrKey1"));
        assertEquals("PubPrimaryInstAttrValue3",updatedClu.getPublishingInfo().getPrimaryInstructor().getAttributes().get("PubPrimaryInstAttrKey3"));
        assertEquals(2,updatedClu.getPublishingInfo().getPrimaryInstructor().getAttributes().size());

        assertEquals("UPEXT_orgId_2",updatedClu.getPublishingInfo().getInstructors().get(0).getOrgId());
        assertEquals("UPEXT_personId_2",updatedClu.getPublishingInfo().getInstructors().get(0).getPersonId());
        assertEquals("PubInst1AttrValue1",updatedClu.getPublishingInfo().getInstructors().get(0).getAttributes().get("PubInst1AttrKey1"));
        assertEquals("PubInst1AttrValue3",updatedClu.getPublishingInfo().getInstructors().get(0).getAttributes().get("PubInst1AttrKey3"));
        assertEquals(2,updatedClu.getPublishingInfo().getInstructors().get(0).getAttributes().size());

        assertEquals("EXT_orgId_3",updatedClu.getPublishingInfo().getInstructors().get(1).getOrgId());
        assertEquals("EXT_personId_3",updatedClu.getPublishingInfo().getInstructors().get(1).getPersonId());
        assertEquals("PubInst3AttrValue1",updatedClu.getPublishingInfo().getInstructors().get(1).getAttributes().get("PubInst3AttrKey1"));
        assertEquals("PubInst3AttrValue2",updatedClu.getPublishingInfo().getInstructors().get(1).getAttributes().get("PubInst3AttrKey2"));
        assertEquals(2,updatedClu.getPublishingInfo().getInstructors().get(1).getAttributes().size());

        assertEquals("UPhttp://student.kuali.org/clus",updatedClu.getReferenceURL());

        assertEquals("UPClu state",updatedClu.getState());

        assertEquals("UPEXT_stdDuration_Id1",updatedClu.getStdDuration().getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(97867),updatedClu.getStdDuration().getTimeQuantity());

        assertEquals("luType.shell.program",updatedClu.getType());

        assertNotNull(updatedClu.getMetaInfo());
        assertNotNull(updatedClu.getMetaInfo().getVersionInd());
        assertNotNull(updatedClu.getMetaInfo().getCreateTime());
        assertNotNull(updatedClu.getMetaInfo().getUpdateTime());

        assertEquals(createdClu.getId(), updatedClu.getId());

        checkAdminOrgUpdate(updatedClu);

        checkAcademicSubjectOrgsUpdate(updatedClu);

        checkAccreditationListUpdate(updatedClu);

        checkCampusLocationUpdate(updatedClu);

        checkIntensityUpdate(updatedClu);

        //Test Optimistic locking
        try{
            updatedClu = client.updateClu(createdClu.getId(), createdClu);
            fail("Should have thrown VersionMismatchException");
        }catch(VersionMismatchException e){

        }

        //Test Delete
        try{
            client.getClu(createdClu.getId());
        }catch(DoesNotExistException e){
            fail("Should not have thrown DoesNotExistException");
        }

        StatusInfo status = client.deleteClu(createdClu.getId());
        assertTrue(status.getSuccess());

        try{
            client.getClu(createdClu.getId());
            fail("Should have thrown DoesNotExistException");
        }catch(DoesNotExistException e){

        }

    }

    @Test
    public void testCluCluRelationCrud() throws Exception {

        final CluCluRelationInfo cluCluRelationInfo = new CluCluRelationInfo();

        final Date effectiveDate = DF.parse("20080101"), expirationDate = DF.parse("20100101");
        cluCluRelationInfo.setEffectiveDate(effectiveDate);
        cluCluRelationInfo.setExpirationDate(expirationDate);
        cluCluRelationInfo.setIsCluRelationRequired(true);
        cluCluRelationInfo.setState("hello");
        cluCluRelationInfo.getAttributes().put("clucluAttrKey1", "clucluAttrValue1");
        cluCluRelationInfo.getAttributes().put("clucluAttrKey2", "clucluAttrValue2");
        cluCluRelationInfo.getAttributes().put("clucluAttrKey3", "clucluAttrValue3");

        CluCluRelationInfo created = client.createCluCluRelation("CLU-1", "CLU-2", "luLuType.type1", cluCluRelationInfo);

        assertEquals(effectiveDate, created.getEffectiveDate());
        assertEquals(expirationDate, created.getExpirationDate());
        assertEquals(true, created.getIsCluRelationRequired());
        assertEquals("hello", created.getState());
        assertEquals("CLU-1", created.getCluId());
        assertEquals("CLU-2", created.getRelatedCluId());
        assertEquals("luLuType.type1", created.getType());
        assertEquals("clucluAttrValue1", created.getAttributes().get("clucluAttrKey1"));
        assertEquals("clucluAttrValue2", created.getAttributes().get("clucluAttrKey2"));
        assertEquals("clucluAttrValue3", created.getAttributes().get("clucluAttrKey3"));
        assertNotNull(created.getId());
        assertNotNull(created.getMetaInfo().getCreateTime());
        assertNotNull(created.getMetaInfo().getVersionInd());

        created.getAttributes().remove("clucluAttrKey2");
        created.getAttributes().put("clucluAttrKey3", "clucluAttrValue3-A");
        created.getAttributes().put("clucluAttrKey4", "clucluAttrValue4");
        created.setCluId("CLU-2");
        created.setEffectiveDate(expirationDate);
        created.setExpirationDate(effectiveDate);
        created.setIsCluRelationRequired(false);
        created.setRelatedCluId("CLU-3");
        created.setState("updated hello");
        created.setType("luLuType.type2");

        CluCluRelationInfo updated = client.updateCluCluRelation(created.getId(), created);

        assertEquals(expirationDate, updated.getEffectiveDate());
        assertEquals(effectiveDate, updated.getExpirationDate());
        assertEquals(false, updated.getIsCluRelationRequired());
        assertEquals("updated hello", updated.getState());
        assertEquals("CLU-2", updated.getCluId());
        assertEquals("CLU-3", updated.getRelatedCluId());
        assertEquals("luLuType.type2", updated.getType());
        assertEquals("clucluAttrValue1", updated.getAttributes().get("clucluAttrKey1"));
        assertNull(updated.getAttributes().get("clucluAttrKey2"));
        assertEquals("clucluAttrValue3-A", updated.getAttributes().get("clucluAttrKey3"));
        assertEquals("clucluAttrValue4", updated.getAttributes().get("clucluAttrKey4"));
        assertNotNull(created.getId());
        assertNotNull(created.getMetaInfo().getCreateTime());
        assertNotNull(created.getMetaInfo().getVersionInd());

        //Test Delete
        try{
            client.getCluCluRelation(created.getId());
        }catch(DoesNotExistException e){
            fail("Should not have thrown DoesNotExistException");
        }

        StatusInfo status = client.deleteCluCluRelation(created.getId());
        assertTrue(status.getSuccess());

        try{
            client.getCluCluRelation(created.getId());
            fail("Should have thrown DoesNotExistException");
        }catch(DoesNotExistException e){

        }

        List<String> relatedCluIdsByCluId = client.getRelatedCluIdsByCluId("CLU-1", "luLuType.type1");

        assertEquals(2,relatedCluIdsByCluId.size());
        assertTrue(relatedCluIdsByCluId.contains("CLU-2"));
        assertTrue(relatedCluIdsByCluId.contains("CLU-3"));

        List<CluInfo> relatedClusByCluId = client.getRelatedClusByCluId("CLU-1", "luLuType.type1");
        assertEquals(2,relatedClusByCluId.size());
    }

    @Test
    public void testLuiLuiRelationCrud() throws Exception {

        LuiLuiRelationInfo luiLuiRelationInfo = new LuiLuiRelationInfo();

        luiLuiRelationInfo.setEffectiveDate(DF.parse("20080101"));
        luiLuiRelationInfo.setExpirationDate(DF.parse("20100101"));
        luiLuiRelationInfo.setState("hello");
        luiLuiRelationInfo.setType("goodbye");
        luiLuiRelationInfo.getAttributes().put("luiluiAttrKey1", "luiluiAttrValue1");
        luiLuiRelationInfo.getAttributes().put("luiluiAttrKey2", "luiluiAttrValue2");

        LuiLuiRelationInfo created = client.createLuiLuiRelation("LUI-1", "LUI-2", "luLuType.type1", luiLuiRelationInfo);

        assertEquals(DF.parse("20080101"),created.getEffectiveDate());
        assertEquals(DF.parse("20100101"),created.getExpirationDate());
        assertEquals("hello",created.getState());
        assertEquals("luLuType.type1",created.getType());
        assertEquals("LUI-1", created.getLuiId());
        assertEquals("LUI-2", created.getRelatedLuiId());
        assertEquals("luiluiAttrValue1", created.getAttributes().get("luiluiAttrKey1"));
        assertEquals("luiluiAttrValue2", created.getAttributes().get("luiluiAttrKey2"));
        assertNotNull(created.getId());
        assertNotNull(created.getMetaInfo().getCreateTime());
        assertNotNull(created.getMetaInfo().getVersionInd());

        created.setEffectiveDate(DF.parse("20980101"));
        created.setExpirationDate(DF.parse("20190101"));
        created.setState("sawyer");
        created.setType("luLuType.type2");
        created.setLuiId("LUI-2");
        created.setRelatedLuiId("LUI-3");
        created.getAttributes().put("luiluiAttrKey1", "UPluiluiAttrValue1");
        created.getAttributes().remove("luiluiAttrKey2");
        created.getAttributes().put("luiluiAttrKey3", "luiluiAttrValue3");

        LuiLuiRelationInfo updated = client.updateLuiLuiRelation(created.getId(), created);

        assertEquals(DF.parse("20980101"),updated.getEffectiveDate());
        assertEquals(DF.parse("20190101"),updated.getExpirationDate());
        assertEquals("sawyer",updated.getState());
        assertEquals("luLuType.type2",updated.getType());
        assertEquals("LUI-2", updated.getLuiId());
        assertEquals("LUI-3", updated.getRelatedLuiId());
        assertEquals("UPluiluiAttrValue1", updated.getAttributes().get("luiluiAttrKey1"));
        assertEquals("luiluiAttrValue3", updated.getAttributes().get("luiluiAttrKey3"));
        assertEquals(2,updated.getAttributes().size());
        assertEquals(created.getId(),updated.getId());
        assertNotNull(updated.getMetaInfo().getUpdateTime());

        try{
            updated = client.updateLuiLuiRelation(created.getId(), created);
            fail("Should have thrown VersionMismatchException");
        }catch(VersionMismatchException e){
        }

        try{
            client.getLuiLuiRelation(created.getId());
        }catch(DoesNotExistException e){
            fail("Should not have thrown DoesNotExistException");
        }

        StatusInfo status = client.deleteLuiLuiRelation(updated.getId());

        assertTrue(status.getSuccess());

        try{
            client.getLuiLuiRelation(created.getId());
            fail("Should have thrown DoesNotExistException");
        }catch(DoesNotExistException e){

        }

        //TestFind
        List<LuiLuiRelationInfo> relations = client.getLuiLuiRelations("LUI-1");
        assertEquals(2,relations.size());
        relations = client.getLuiLuiRelations("LUI-2");
        assertEquals(1,relations.size());
        relations = client.getLuiLuiRelations("LUI-3");
        assertEquals(0,relations.size());


        List<String> relatedLuiIdsByLuiId = client.getRelatedLuiIdsByLuiId("LUI-1", "luLuType.type1");
        assertEquals(2,relatedLuiIdsByLuiId.size());
        assertTrue(relatedLuiIdsByLuiId.contains("LUI-2"));
        assertTrue(relatedLuiIdsByLuiId.contains("LUI-3"));

        List<LuiInfo> relatedLuisByLuiId = client.getRelatedLuisByLuiId("LUI-1", "luLuType.type1");
        assertEquals(2,relatedLuisByLuiId.size());

    }

    @Test
    public void testGetLuisByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
        List<LuiInfo> luiInfos;
        try {
            luiInfos = client.getLuisByIdList(null);
            fail("LuService.getLuiByIdList() did not throw MissingParameterException for null Lui ID");
        } catch (MissingParameterException mpe) {
        } catch (Exception e) {
            fail("LuService.getLuiByIdList() threw unexpected " + e.getClass().getSimpleName() + " for null Lui ID");
        }
        luiInfos = client.getLuisByIdList(Arrays.asList("Not a LUI ID", "Another one that ain't"));
        assertTrue(luiInfos == null || luiInfos.size() == 0);

        luiInfos = client.getLuisByIdList(Arrays.asList("LUI-1", "LUI-4"));
		Collections.sort(luiInfos, new Comparator<LuiInfo>() {
			public int compare(LuiInfo o1, LuiInfo o2) {
				return o1.getId().compareTo(o2.getId());
			}});
        assertEquals("CLU-1", luiInfos.get(0).getCluId());
        assertEquals("CLU-2", luiInfos.get(1).getCluId());
    }

    @Test
    public void testLuiCrud() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, DependentObjectsExistException {

        LuiInfo luiInfo;

        // Read
        try {
            luiInfo = client.getLui("notARealLui");
            fail("LuService.getLui() did not throw DoesNotExistException for non-existent Lui");
        } catch (DoesNotExistException dnee) {
        } catch (Exception e) {
            fail("LuService.getLui() threw unexpected " + e.getClass().getSimpleName() + " for null Lui ID");
        }
        try {
            luiInfo = client.getLui(null);
            fail("LuService.getLui() did not throw MissingParameterException for null Lui ID");
        } catch (MissingParameterException mpe) {
        }
        luiInfo = client.getLui("LUI-1");
        assertEquals("CLU-1", luiInfo.getCluId());

        // Create
        luiInfo = new LuiInfo();

        luiInfo.setLuiCode("LUI Test Code");
        luiInfo.setMaxSeats(100);
        luiInfo.setState("Test Lui State");
        luiInfo.setEffectiveDate(DF.parse("20101203"));
        luiInfo.setExpirationDate(DF.parse("20801231"));
        luiInfo.getAttributes().put("luiAttrKey1", "luiAttrValue1");
        luiInfo.getAttributes().put("luiAttrKey2", "luiAttrValue2");

        LuiInfo createdLui = client.createLui("CLU-2", "ATP-3", luiInfo);

        assertEquals("ATP-3", createdLui.getAtpId());
        assertEquals("LUI Test Code", createdLui.getLuiCode());
        assertEquals(100L, (long) createdLui.getMaxSeats());
        assertEquals(DF.parse("20101203"), luiInfo.getEffectiveDate());
        assertEquals(DF.parse("20801231"), luiInfo.getExpirationDate());
        assertEquals("CLU-2", createdLui.getCluId());
        assertEquals(2, createdLui.getAttributes().size());
        assertEquals("luiAttrValue1", createdLui.getAttributes().get("luiAttrKey1"));
        assertEquals("luiAttrValue2", createdLui.getAttributes().get("luiAttrKey2"));

        // update
        createdLui.setAtpId("ATP-2");
        createdLui.setCluId("CLU-1");
        createdLui.setLuiCode("LUI Test Code Update");
        createdLui.setMaxSeats(75);
        createdLui.setState("Test Lui State Update");
        createdLui.setEffectiveDate(DF.parse("20111203"));
        createdLui.setExpirationDate(DF.parse("20811231"));
        createdLui.getAttributes().put("luiAttrKey1", "luiAttrValue1Updated");
        createdLui.getAttributes().put("luiAttrKey2", "luiAttrValue2Updated");

        LuiInfo updatedLui = null;
        try {
            updatedLui = client.updateLui(createdLui.getId(), createdLui);
        } catch (VersionMismatchException vme) {
            fail("LuService.updateLui() threw unexpected VersionMismatchException");
        }

        // confirm update worked
        assertTrue(null != updatedLui);
        assertEquals("ATP-2", updatedLui.getAtpId());
        assertEquals("CLU-1", updatedLui.getCluId());
        assertEquals("LUI Test Code Update", updatedLui.getLuiCode());
        assertEquals(75L, (long) createdLui.getMaxSeats());
        assertEquals(DF.parse("20111203"), updatedLui.getEffectiveDate());
        assertEquals(DF.parse("20811231"), updatedLui.getExpirationDate());
        assertEquals(2, updatedLui.getAttributes().size());
        assertEquals("luiAttrValue1Updated", updatedLui.getAttributes().get("luiAttrKey1"));
        assertEquals("luiAttrValue2Updated", updatedLui.getAttributes().get("luiAttrKey2"));



        // optimistic locking working?
        try{
            client.updateLui(createdLui.getId(), createdLui);
            fail("LuService.updateLui did not throw expected VersionMismatchException");
        }catch(VersionMismatchException e){
        }

        // delete what we created
        client.deleteLui(createdLui.getId());
        // and try it again
        try {
            client.deleteLui(createdLui.getId());
            fail("LuService.deleteLui() of previously-delete Lui did not throw expected DoesNotExistException");
        } catch (DoesNotExistException dnee) {
        }
    }

    @Test
    public void testGetLuiIdsByCluId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<String> luiIds = null;
        try {
            luiIds = client.getLuiIdsByCluId(null);
            fail("LuService.getLuiIdsByCluId() did not throw MissingParameterException for null Clu ID");
        } catch (MissingParameterException e) {
        }
        luiIds = client.getLuiIdsByCluId("CLU-1");
        
		Collections.sort(luiIds);
        
        assertTrue(null != luiIds);
        assertEquals(3, luiIds.size());
        
        assertEquals("LUI-1", luiIds.get(0));
        assertEquals("LUI-3", luiIds.get(2));
        luiIds = client.getLuiIdsByCluId("CLU-2");
        assertEquals(1, luiIds.size());
        luiIds = client.getLuiIdsByCluId("Non-existent Clu");
        assertTrue(null != luiIds);
        assertEquals(0, luiIds.size());
    }

    @Test
    public void testGetLuiIdsInAtpByCluId() throws DoesNotExistException, InvalidParameterException, OperationFailedException, MissingParameterException
    {
        List<String> luiIds = null;
        try {
            luiIds = client.getLuiIdsInAtpByCluId(null, "ATP-1");
            fail("LuService.getLuiIdsInAtpByCluId() did not throw MissingParameterException for null Clu ID");
        } catch (MissingParameterException e) {
        }
        try {
            luiIds = client.getLuiIdsInAtpByCluId("CLU-1", null);
            fail("LuService.getLuiIdsInAtpByCluId() did not throw MissingParameterException for null AtpKey");
        } catch (MissingParameterException e) {
        }
        luiIds = client.getLuiIdsInAtpByCluId("CLU-1", "ATP-2");
		Collections.sort(luiIds);
        assertTrue(null != luiIds);
        assertEquals(2, luiIds.size());
        assertEquals("LUI-2", luiIds.get(0));
        assertEquals("LUI-3", luiIds.get(1));
        luiIds = client.getLuiIdsInAtpByCluId("CLU-1", "ATP-1");
        assertEquals(1, luiIds.size());
        luiIds = client.getLuiIdsInAtpByCluId("Non-existent Clu", "ATP-2");
        assertTrue(null != luiIds);
        assertEquals(0, luiIds.size());
        luiIds = client.getLuiIdsInAtpByCluId("CLU-2", "Non-existent ATP");
        assertTrue(null != luiIds);
        assertEquals(0, luiIds.size());
    }

    @Test
    public void testGetLuLuRelationTypeInfo() throws OperationFailedException, DoesNotExistException, MissingParameterException
    {
        LuLuRelationTypeInfo luLuRelTypeInfo;

        try {
            luLuRelTypeInfo = client.getLuLuRelationTypeInfo(null);
            fail("LuService.getLuLuRelationTypeInfo() did not throw MissingParameterException for null LuLuRelationType key");
        } catch (MissingParameterException e) {
        }
        luLuRelTypeInfo = client.getLuLuRelationTypeInfo("luLuType.type1");
        assertEquals("bob", luLuRelTypeInfo.getName());
        luLuRelTypeInfo = client.getLuLuRelationTypeInfo("luLuType.type2");
        assertEquals("my desc2", luLuRelTypeInfo.getDesc());
        assertEquals("rev name2", luLuRelTypeInfo.getRevName());
        assertEquals("rev desc2", luLuRelTypeInfo.getRevDesc());
        try {
            client.getLuLuRelationTypeInfo("Non-existent LuLuRelationType");
            fail("LuService.getLuLuRelationTypeInfo() did not throw DoesNotExistException when retrieving non-existent LuLuRelationType");
        } catch (DoesNotExistException dnee) {
        }

    }

    @Test
    public void testGetLuLuRelationTypeInfos() throws OperationFailedException, DoesNotExistException, MissingParameterException
    {
        List<LuLuRelationTypeInfo> luLuRelTypeInfos;
        luLuRelTypeInfos = client.getLuLuRelationTypeInfos();
		Collections.sort(luLuRelTypeInfos, new Comparator<LuLuRelationTypeInfo>() {
			public int compare(LuLuRelationTypeInfo o1, LuLuRelationTypeInfo o2) {
				return o1.getId().compareTo(o2.getId());
			}});
        assertEquals(3, luLuRelTypeInfos.size());
        assertEquals("luLuType.type1", luLuRelTypeInfos.get(0).getId());
        assertEquals("manolin", luLuRelTypeInfos.get(2).getName());
    }

    @Test
    public void testUpdateLuiState() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, OperationFailedException, PermissionDeniedException, ParseException, AlreadyExistsException, MissingParameterException, DependentObjectsExistException
    {
        try {
            client.updateLuiState(null, "Inactive");
            fail("LuService.updateLuiState() did not throw MissingParameterException for null Lui ID");
        } catch (MissingParameterException e) {
        }
        try {
            client.updateLuiState("LUI-1", null);
            fail("LuService.updateLuiState() did not throw MissingParameterException for null state");
        } catch (MissingParameterException e) {
        }

        // create a Lui whose state we'll update. Create a new one so its MetaInfo gets created in prePersist()
        LuiInfo luiInfo = new LuiInfo();

        luiInfo.setLuiCode("LUI Test Code");
        luiInfo.setMaxSeats(100);
        luiInfo.setState("Approved");
        luiInfo.setEffectiveDate(DF.parse("20101203"));
        luiInfo.setExpirationDate(DF.parse("20801231"));
        luiInfo.getAttributes().put("luiAttrKey1", "luiAttrValue1");
        luiInfo.getAttributes().put("luiAttrKey2", "luiAttrValue2");

        LuiInfo createdLui = client.createLui("CLU-2", "ATP-3", luiInfo);
        // make sure the db's in the state we expect
        assertEquals("Approved", createdLui.getState());

        // update and confirm it was updated
        LuiInfo updatedLui = client.updateLuiState(createdLui.getId(), "Activated");
        assertEquals("Activated", updatedLui.getState());

        // and now explicitly retrieve it without a call to updateLuiState and confirm same
        updatedLui = client.getLui(createdLui.getId());
        assertEquals("Activated", updatedLui.getState());

        // and delete it to keep db consistent for other tests
        client.deleteLui(updatedLui.getId());
    }

    @Test
    public void testGetLuisByRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<LuiInfo> luis = client.getLuisByRelation("LUI-1", "luLuType.type1");
        assertTrue(luis == null || luis.size() == 0);
        luis = client.getLuisByRelation("LUI-2", "luLuType.type1");
		Collections.sort(luis, new Comparator<LuiInfo>() {
			public int compare(LuiInfo o1, LuiInfo o2) {
				return o1.getId().compareTo(o2.getId());
			}});
        assertEquals(1, luis.size());
        assertEquals("LUI-1", luis.get(0).getId());
    }

    @Test
    public void testGetLuiIdsByRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
        List<String> luis = client.getLuiIdsByRelation("LUI-1", "luLuType.type1");
        assertTrue(luis == null || luis.size() == 0);
        luis = client.getLuiIdsByRelation("LUI-2", "luLuType.type1");
		Collections.sort(luis);
        assertEquals(1, luis.size());
        assertEquals("LUI-1", luis.get(0));
    }

    @Test
    public void testLuDocRelation() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, VersionMismatchException{

        LuDocRelationInfo luDocRelationInfo = new LuDocRelationInfo();
        luDocRelationInfo.setType("");
        luDocRelationInfo.setCluId("");
        luDocRelationInfo.setDocumentId("");
        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>Formatted Desc</p>");
        desc.setPlain("plain");
        luDocRelationInfo.setDesc(desc);
        luDocRelationInfo.setEffectiveDate(DF.parse("20090101"));
        luDocRelationInfo.setExpirationDate(DF.parse("20190101"));
        luDocRelationInfo.setState("state");
        luDocRelationInfo.setTitle("title");
        luDocRelationInfo.getAttributes().put("DocRelAttrKey1","DocRelAttrValue1");
        luDocRelationInfo.getAttributes().put("DocRelAttrKey2","DocRelAttrValue2");

        LuDocRelationInfo created = client.createLuDocRelationForClu("luDocRelationType.doctype1", "MY-EXTERNAL-DOC-ID", "CLU-1", luDocRelationInfo);

        assertEquals("luDocRelationType.doctype1",created.getType());
        assertEquals("CLU-1",created.getCluId());
        assertEquals("MY-EXTERNAL-DOC-ID",created.getDocumentId());
        assertEquals("<p>Formatted Desc</p>",created.getDesc().getFormatted());
        assertEquals("plain",created.getDesc().getPlain());
        assertEquals(DF.parse("20090101"),created.getEffectiveDate());
        assertEquals(DF.parse("20190101"),created.getExpirationDate());
        assertEquals("state",created.getState());
        assertEquals("title",created.getTitle());
        assertEquals("DocRelAttrValue1",created.getAttributes().get("DocRelAttrKey1"));
        assertEquals("DocRelAttrValue2",created.getAttributes().get("DocRelAttrKey2"));
        assertNotNull(created.getId());
        assertNotNull(created.getMetaInfo().getCreateTime());
        assertNotNull(created.getMetaInfo().getUpdateTime());

        created.setType("luDocRelationType.doctype2");
        created.setCluId("CLU-2");
        created.setDocumentId("NEW-DOC-ID");
        created.getDesc().setFormatted("UP<p>Formatted Desc</p>");
        created.getDesc().setPlain("UPplain");
        created.setEffectiveDate(DF.parse("20190101"));
        created.setExpirationDate(DF.parse("20290101"));
        created.setState("UPstate");
        created.setTitle("UPtitle");
        created.getAttributes().put("DocRelAttrKey1","UPDocRelAttrValue1");

        LuDocRelationInfo updated = client.updateLuDocRelation(created.getId(), created);

        assertEquals("luDocRelationType.doctype2",updated.getType());
        assertEquals("CLU-2",updated.getCluId());
        assertEquals("NEW-DOC-ID",updated.getDocumentId());
        assertEquals("UP<p>Formatted Desc</p>",updated.getDesc().getFormatted());
        assertEquals("UPplain",updated.getDesc().getPlain());
        assertEquals(DF.parse("20190101"),updated.getEffectiveDate());
        assertEquals(DF.parse("20290101"),updated.getExpirationDate());
        assertEquals("UPstate",updated.getState());
        assertEquals("UPtitle",updated.getTitle());
        assertEquals("UPDocRelAttrValue1",updated.getAttributes().get("DocRelAttrKey1"));

        // optimistic locking working?
        try{
            client.updateLuDocRelation(created.getId(), created);
            fail("LuService.updateLuDocRelation did not throw expected VersionMismatchException");
        }catch(VersionMismatchException e){
        }

        // delete what we created
        client.deleteLuDocRelation(updated.getId());

        // and try it again
        try {
            client.deleteLuDocRelation(updated.getId());
            fail("LuService.deleteLuDocRelation() of previously-delete Lui did not throw expected DoesNotExistException");
        } catch (DoesNotExistException dnee) {
        }

        //Test other gets
        LuDocRelationInfo getLuDocRelation =  client.getLuDocRelation("LUDOCREL-1");
        assertEquals("CLU-1",getLuDocRelation.getCluId());

        List<LuDocRelationInfo> getLuDocRelationsByClu =  client.getLuDocRelationsByClu("CLU-1");
        assertEquals(2,getLuDocRelationsByClu.size());

        List<LuDocRelationInfo> getLuDocRelationsByDocument = client.getLuDocRelationsByDocument("DOC-1");
        assertEquals(2,getLuDocRelationsByDocument.size());

        List<String> luDocRelationIdList = new ArrayList<String>(2);
        luDocRelationIdList.add("LUDOCREL-1");
        luDocRelationIdList.add("LUDOCREL-2");
        List<LuDocRelationInfo> getLuDocRelationsByIdList = client.getLuDocRelationsByIdList(luDocRelationIdList);
        assertEquals(2,getLuDocRelationsByIdList.size());

        List<LuDocRelationInfo> getLuDocRelationsByType = client.getLuDocRelationsByType("luDocRelationType.doctype1");
        assertEquals(3,getLuDocRelationsByType.size());

        assertEquals("DT1",client.getLuDocRelationType("luDocRelationType.doctype1").getName());
        assertEquals(2,client.getLuDocRelationTypes().size());


    }
    @Test
    public void testOutcomeLO() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        StatusInfo status = client.addOutcomeLoToClu("LO-1", "CLU-1");
        assertTrue(status.getSuccess());

        List<String> ids = client.getLoIdsByClu("CLU-1");
		Collections.sort(ids);
        assertEquals(1, ids.size());
        assertEquals("LO-1", ids.get(0));

        ids = client.getCluIdsByLoId("LO-1");
		Collections.sort(ids);
		assertEquals(1, ids.size());
        assertEquals("CLU-1", ids.get(0));

        status = client.addOutcomeLoToClu("LO-3", "CLU-1");
        assertTrue(status.getSuccess());

        ids = client.getLoIdsByClu("CLU-1");
        assertEquals(2, ids.size());
        assertTrue(ids.contains("LO-3"));

        status = client.removeOutcomeLoFromClu("LO-1", "CLU-1");
        assertTrue(status.getSuccess());

        status = client.removeOutcomeLoFromClu("LO-2", "CLU-1");
        assertFalse(status.getSuccess());

    }

    @Test
    public void testLrType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
        LrTypeInfo lrType = client.getLrType("lrType.finalGrade");
        assertEquals("Final Grade", lrType.getName());
        List<LrTypeInfo> lrTypes = client.getLrTypes();
        assertEquals(2,lrTypes.size());

    }

    @Test
    public void  testGetClusByRelation() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, VersionMismatchException{
        List<CluInfo> clus = client.getClusByRelation("CLU-2", "luLuType.type1");
        assertNotNull(clus);
        assertEquals(1, clus.size());

        clus = client.getClusByRelation("CLUXX-2", "luLuType.type1");
        assertNotNull(clus);
        assertEquals(0, clus.size());

        clus = client.getClusByRelation("CLU-2", "luLuType.type1XX");
        assertNotNull(clus);
        assertEquals(0, clus.size());

        try {
            clus = client.getClusByRelation(null, "luLuType.type1XX");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            clus = client.getClusByRelation("CLU-2", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        List<String> ids = client.getCluIdsByRelation("CLU-2", "luLuType.type1");
        assertNotNull(ids);
        assertEquals(1, ids.size());

        ids = client.getCluIdsByRelation("CLUXX-2", "luLuType.type1");
        assertNotNull(ids);
        assertEquals(0, ids.size());

        ids = client.getCluIdsByRelation("CLU-2", "luLuType.type1XX");
        assertNotNull(ids);
        assertEquals(0, ids.size());

        try {
            ids = client.getCluIdsByRelation(null, "luLuType.type1XX");
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            ids = client.getCluIdsByRelation("CLU-2", null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void  testSearchForResults() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, VersionMismatchException{
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);
        List<Result> clus = client.searchForResults("lu.search.clus", queryParamValues);
		Collections.sort(clus, new Comparator<Result>() {
			public int compare(Result o1, Result o2) {
				return o1.getResultCells().get(0).getValue().compareTo(o2.getResultCells().get(0).getValue());
			}});
        assertNotNull(clus);
        assertEquals(108, clus.size());
        Result result = clus.get(0);
        assertNotNull(result);

        List<ResultCell> resultCells = result.getResultCells();
        assertNotNull(resultCells);
        assertEquals(2, resultCells.size());

        ResultCell resultCell = resultCells.get(0);
        assertEquals("lu.resultColumn.cluId", resultCell.getKey());
        assertEquals("00000000-a389-4fd0-b349-1e649c20fd08", resultCell.getValue());
        resultCell = resultCells.get(1);
        assertEquals("lu.resultColumn.cluOfficialIdentifier.longName", resultCell.getKey());
        assertEquals("Advanced Applied Linear Algebra", resultCell.getValue());
    }

    private void createAcademicSubjectOrgs(CluInfo clu) {
        clu.getAcademicSubjectOrgs().add("EXT_Academic_Subject_ORG_ID1");
        clu.getAcademicSubjectOrgs().add("EXT_Academic_Subject_ORG_ID2");
    }

    private void checkAcademicSubjectOrgsCreate(CluInfo createdClu) {
        assertEquals("EXT_Academic_Subject_ORG_ID1",createdClu.getAcademicSubjectOrgs().get(0));
        assertEquals("EXT_Academic_Subject_ORG_ID2",createdClu.getAcademicSubjectOrgs().get(1));
    }

    private void updateAcademicSubjectOrgs(CluInfo clu) {
        clu.getAcademicSubjectOrgs().remove(1);
        clu.getAcademicSubjectOrgs().add("EXT_Academic_Subject_ORG_ID3");
        clu.getAcademicSubjectOrgs().add("EXT_Academic_Subject_ORG_ID4");
    }

    private void checkAcademicSubjectOrgsUpdate(CluInfo updatedClu) {
        assertEquals(3, updatedClu.getAcademicSubjectOrgs().size());
        assertEquals("EXT_Academic_Subject_ORG_ID1",updatedClu.getAcademicSubjectOrgs().get(0));
        assertEquals("EXT_Academic_Subject_ORG_ID3",updatedClu.getAcademicSubjectOrgs().get(1));
        assertEquals("EXT_Academic_Subject_ORG_ID4",updatedClu.getAcademicSubjectOrgs().get(2));
    }

    private void createAdminOrgs(CluInfo clu) {
        AdminOrgInfo primaryAdminOrg = new AdminOrgInfo();
        primaryAdminOrg.setOrgId("PRIMARY_ADMIN_ORG_ID");
        primaryAdminOrg.getAttributes().put("PrimaryAdminOrgAttrKey1", "PrimaryAdminOrgAttrValue1");
        primaryAdminOrg.getAttributes().put("PrimaryAdminOrgAttrKey2", "PrimaryAdminOrgAttrValue2");
        clu.setPrimaryAdminOrg(primaryAdminOrg);

        AdminOrgInfo altAdminOrg1 = new AdminOrgInfo();
        altAdminOrg1.setOrgId("ALT_ADMIN_ORG_ID1");
        altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey1", "AltAdminOrg1AttrValue1");
        altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey2", "AltAdminOrg1AttrValue2");
        altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey3", "AltAdminOrg1AttrValue3");

        AdminOrgInfo altAdminOrg2 = new AdminOrgInfo();
        altAdminOrg2.setOrgId("ALT_ADMIN_ORG_ID2");
        altAdminOrg2.getAttributes().put("AltAdminOrg2AttrKey1", "AltAdminOrg2AttrValue1");
        altAdminOrg2.getAttributes().put("AltAdminOrg2AttrKey2", "AltAdminOrg2AttrValue2");

        clu.getAlternateAdminOrgs().add(altAdminOrg1);
        clu.getAlternateAdminOrgs().add(altAdminOrg2);

    }

    private void checkAdminOrgsCreate(CluInfo clu) {
        assertEquals("PRIMARY_ADMIN_ORG_ID",clu.getPrimaryAdminOrg().getOrgId());
        assertEquals(2, clu.getPrimaryAdminOrg().getAttributes().size());
        assertEquals("PrimaryAdminOrgAttrValue1",clu.getPrimaryAdminOrg().getAttributes().get("PrimaryAdminOrgAttrKey1"));
        assertEquals("PrimaryAdminOrgAttrValue2",clu.getPrimaryAdminOrg().getAttributes().get("PrimaryAdminOrgAttrKey2"));

        assertEquals("ALT_ADMIN_ORG_ID1",clu.getAlternateAdminOrgs().get(0).getOrgId());
        assertEquals(3, clu.getAlternateAdminOrgs().get(0).getAttributes().size());
        assertEquals("AltAdminOrg1AttrValue1",clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey1"));
        assertEquals("AltAdminOrg1AttrValue2",clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey2"));
        assertEquals("AltAdminOrg1AttrValue3",clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey3"));

        assertEquals("ALT_ADMIN_ORG_ID2",clu.getAlternateAdminOrgs().get(1).getOrgId());
        assertEquals(2, clu.getAlternateAdminOrgs().get(1).getAttributes().size());
        assertEquals("AltAdminOrg2AttrValue1",clu.getAlternateAdminOrgs().get(1).getAttributes().get("AltAdminOrg2AttrKey1"));
        assertEquals("AltAdminOrg2AttrValue2",clu.getAlternateAdminOrgs().get(1).getAttributes().get("AltAdminOrg2AttrKey2"));
    }

    private void updateAdminOrgs(CluInfo clu) {
        clu.getPrimaryAdminOrg().setOrgId("UPD_PRIMARY_ADMIN_ORG_ID");
        clu.getPrimaryAdminOrg().getAttributes().put("PrimaryAdminOrgAttrKey3", "PrimaryAdminOrgAttrValue3");
        clu.getPrimaryAdminOrg().getAttributes().remove("PrimaryAdminOrgAttrKey2");
        clu.getPrimaryAdminOrg().getAttributes().put("PrimaryAdminOrgAttrKey4", "PrimaryAdminOrgAttrValue4");

        AdminOrgInfo altAdminOrg3 = new AdminOrgInfo();
        altAdminOrg3.setOrgId("ALT_ADMIN_ORG_ID3");
        altAdminOrg3.getAttributes().put("AltAdminOrg3AttrKey1", "AltAdminOrg3AttrValue1");
        altAdminOrg3.getAttributes().put("AltAdminOrg3AttrKey2", "AltAdminOrg3AttrValue2");

        clu.getAlternateAdminOrgs().get(1).getAttributes().put("AltAdminOrg1AttrKey4", "AltAdminOrg1AttrKey4");
        clu.getAlternateAdminOrgs().remove(1);
        clu.getAlternateAdminOrgs().add(altAdminOrg3);
    }

    private void checkAdminOrgUpdate(CluInfo clu) {
        assertEquals("UPD_PRIMARY_ADMIN_ORG_ID", clu.getPrimaryAdminOrg().getOrgId());
        assertEquals(3, clu.getPrimaryAdminOrg().getAttributes().size());
        assertEquals("PrimaryAdminOrgAttrValue4", clu.getPrimaryAdminOrg().getAttributes().get("PrimaryAdminOrgAttrKey4"));
        assertNull(clu.getPrimaryAdminOrg().getAttributes().get("PrimaryAdminOrgAttrKey2"));

        assertEquals(2, clu.getAlternateAdminOrgs().size());
        assertEquals("ALT_ADMIN_ORG_ID1", clu.getAlternateAdminOrgs().get(0).getOrgId());
        assertEquals(3, clu.getAlternateAdminOrgs().get(0).getAttributes().size());
        assertEquals("AltAdminOrg1AttrValue1", clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey1"));
        assertEquals("AltAdminOrg1AttrValue2", clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey2"));
        assertEquals("AltAdminOrg1AttrValue3", clu.getAlternateAdminOrgs().get(0).getAttributes().get("AltAdminOrg1AttrKey3"));
        assertEquals("ALT_ADMIN_ORG_ID3", clu.getAlternateAdminOrgs().get(1).getOrgId());
        assertEquals(2, clu.getAlternateAdminOrgs().get(1).getAttributes().size());
        assertEquals("AltAdminOrg3AttrValue2", clu.getAlternateAdminOrgs().get(1).getAttributes().get("AltAdminOrg3AttrKey2"));
        assertEquals("AltAdminOrg3AttrValue1", clu.getAlternateAdminOrgs().get(1).getAttributes().get("AltAdminOrg3AttrKey1"));
    }


    private void createAccreditationList( CluInfo clu) throws ParseException {
        AccreditationInfo accreditationOrg1 = new AccreditationInfo();
        accreditationOrg1.setOrgId("EXT_orgId_1");
        accreditationOrg1.setEffectiveDate(DF.parse("20100203"));
        accreditationOrg1.setExpirationDate(DF.parse("21001231"));
        accreditationOrg1.getAttributes().put("Accred1AttrKey1", "Accred1AttrValue1");
        accreditationOrg1.getAttributes().put("Accred1AttrKey2", "Accred1AttrValue2");

        AccreditationInfo accreditationOrg2 = new AccreditationInfo();
        accreditationOrg2.setOrgId("EXT_orgId_2");
        accreditationOrg2.setEffectiveDate(DF.parse("20110203"));
        accreditationOrg2.setExpirationDate(DF.parse("21011231"));
        accreditationOrg2.getAttributes().put("Accred2AttrKey1", "Accred2AttrValue1");
        accreditationOrg2.getAttributes().put("Accred2AttrKey2", "Accred2AttrValue2");

        clu.getAccreditationList().add(accreditationOrg1);
        clu.getAccreditationList().add(accreditationOrg2);
    }

    private void checkAccreditationListCreate( CluInfo clu) throws ParseException {

        assertEquals(2, clu.getAccreditationList().size());

        assertEquals("EXT_orgId_1",clu.getAccreditationList().get(0).getOrgId());
        assertEquals(DF.parse("20100203"),clu.getAccreditationList().get(0).getEffectiveDate());
        assertEquals(DF.parse("21001231"),clu.getAccreditationList().get(0).getExpirationDate());
        assertEquals(2, clu.getAccreditationList().get(0).getAttributes().size());
        assertEquals("Accred1AttrValue1",clu.getAccreditationList().get(0).getAttributes().get("Accred1AttrKey1"));
        assertEquals("Accred1AttrValue2",clu.getAccreditationList().get(0).getAttributes().get("Accred1AttrKey2"));

        assertEquals("EXT_orgId_2",clu.getAccreditationList().get(1).getOrgId());
        assertEquals(DF.parse("20110203"),clu.getAccreditationList().get(1).getEffectiveDate());
        assertEquals(DF.parse("21011231"),clu.getAccreditationList().get(1).getExpirationDate());
        assertEquals(2, clu.getAccreditationList().get(1).getAttributes().size());
        assertEquals("Accred2AttrValue1",clu.getAccreditationList().get(1).getAttributes().get("Accred2AttrKey1"));
        assertEquals("Accred2AttrValue2",clu.getAccreditationList().get(1).getAttributes().get("Accred2AttrKey2"));
    }

    private void updateAccreditationList( CluInfo clu) throws ParseException {

        AccreditationInfo accreditationOrg3 = new AccreditationInfo();
        accreditationOrg3.setOrgId("EXT_orgId_3");
        accreditationOrg3.setEffectiveDate(DF.parse("20120203"));
        accreditationOrg3.setExpirationDate(DF.parse("21021231"));

        AccreditationInfo accreditationOrg4 = new AccreditationInfo();
        accreditationOrg4.setOrgId("EXT_orgId_4");
        accreditationOrg4.setEffectiveDate(DF.parse("20130203"));
        accreditationOrg4.setExpirationDate(DF.parse("21031231"));
        accreditationOrg4.getAttributes().put("Accred4AttrKey1", "Accred4AttrValue1");

        clu.getAccreditationList().add(accreditationOrg3);
        clu.getAccreditationList().add(accreditationOrg4);
        
        clu.getAccreditationList().get(0).getAttributes().remove("Accred1AttrKey2");
        clu.getAccreditationList().get(0).getAttributes().put("Accred1AttrKey1", "Accred1AttrValue1_UPD");
        clu.getAccreditationList().remove(1);
    }

    private void checkAccreditationListUpdate( CluInfo clu) throws ParseException {

        assertEquals(3, clu.getAccreditationList().size());

        assertEquals("EXT_orgId_1",clu.getAccreditationList().get(0).getOrgId());
        assertEquals(1, clu.getAccreditationList().get(0).getAttributes().size());
        assertEquals("Accred1AttrValue1_UPD",clu.getAccreditationList().get(0).getAttributes().get("Accred1AttrKey1"));
        
        assertEquals("EXT_orgId_3",clu.getAccreditationList().get(1).getOrgId());
        assertEquals(DF.parse("20120203"),clu.getAccreditationList().get(1).getEffectiveDate());
        assertEquals(DF.parse("21021231"),clu.getAccreditationList().get(1).getExpirationDate());
        assertEquals(0, clu.getAccreditationList().get(1).getAttributes().size());

        assertEquals("EXT_orgId_4",clu.getAccreditationList().get(2).getOrgId());
        assertEquals(DF.parse("20130203"),clu.getAccreditationList().get(2).getEffectiveDate());
        assertEquals(DF.parse("21031231"),clu.getAccreditationList().get(2).getExpirationDate());
        assertEquals(1, clu.getAccreditationList().get(2).getAttributes().size());        
        assertEquals("Accred4AttrValue1",clu.getAccreditationList().get(2).getAttributes().get("Accred4AttrKey1"));

    }

    private void createIntensity(CluInfo clu) {
        TimeAmountInfo intensity = new TimeAmountInfo();
        intensity.setAtpDurationTypeKey("EXT_intensity_Id1");
        intensity.setTimeQuantity(123);
        clu.setIntensity(intensity);
    }

    private void checkIntensityCreate(CluInfo clu) {
        assertEquals("EXT_intensity_Id1",clu.getIntensity().getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(123),clu.getIntensity().getTimeQuantity());
    }

    private void updateIntensity(CluInfo clu) {
        clu.getIntensity().setAtpDurationTypeKey("UPD_intensity_Id1");
        clu.getIntensity().setTimeQuantity(456);
    }


    private void checkIntensityUpdate(CluInfo clu) {
        assertEquals("UPD_intensity_Id1",clu.getIntensity().getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(456),clu.getIntensity().getTimeQuantity());

    }

    private void createCampusLocationList(CluInfo clu) {
        clu.getCampusLocationList().add("EXT_Campus_Location_1");
        clu.getCampusLocationList().add("EXT_Campus_Location_2");
    }

    private void checkCampusLocationCreate(CluInfo clu) {
        assertEquals(2, clu.getCampusLocationList().size());
        assertEquals("EXT_Campus_Location_1",clu.getCampusLocationList().get(0));
        assertEquals("EXT_Campus_Location_2",clu.getCampusLocationList().get(1));
    }
    private void updateCampusLocationList(CluInfo clu) {
        clu.getCampusLocationList().add("EXT_Campus_Location_3");
        clu.getCampusLocationList().add("EXT_Campus_Location_4");
        clu.getCampusLocationList().remove(0);
    }

    private void checkCampusLocationUpdate(CluInfo clu) {
        assertEquals(3, clu.getCampusLocationList().size());
        assertEquals("EXT_Campus_Location_2",clu.getCampusLocationList().get(0));
        assertEquals("EXT_Campus_Location_3",clu.getCampusLocationList().get(1));
        assertEquals("EXT_Campus_Location_4",clu.getCampusLocationList().get(2));
    }




}
