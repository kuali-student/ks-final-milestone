/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Daos({@Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql")})
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestCluServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.r2.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:clu-additional-context.xml")
    public CluService client;
    final Logger LOG = Logger.getLogger(TestCluServiceImpl.class);

    @Test
    public void testClu() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // getClu
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluInfo clu = client.getClu("CLU-1", contextInfo);
        assertNotNull(clu);
        assertEquals(clu.getId(), "CLU-1");

        try {
            clu = client.getClu("CLX-1", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            clu = client.getClu(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getClusByIdList
        List<String> ids = new ArrayList<String>(1);
        ids.add("CLU-2");
        List<CluInfo> clus = client.getClusByIds(ids, contextInfo);
        assertNotNull(clus);
        assertEquals(1, clus.size());

        ids.clear();
        ids.add("CLX-42");
        clus = client.getClusByIds(ids, contextInfo);
        assertTrue(clus == null || clus.size() == 0);

        try {
            clus = client.getClusByIds(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluIdsByLuType
        ids = client.getCluIdsByLuType("luType.shell.program", "STATE2", contextInfo);
        assertTrue(null != ids);
        assertEquals(1, ids.size());
        assertEquals("CLU-2", ids.get(0));

        ids = client.getCluIdsByLuType("LUTYPE-1X", "STATE1", contextInfo);
        assertTrue(ids == null || ids.size() == 0);
        ids = client.getCluIdsByLuType("luType.shell.course", "STATE1X", contextInfo);
        assertTrue(ids == null || ids.size() == 0);

        try {
            ids = client.getCluIdsByLuType(null, "STATE1", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            ids = client.getCluIdsByLuType("luType.shell.course", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getClusByLuType
        clus = client.getClusByLuType("luType.shell.program", "STATE2", contextInfo);
        assertTrue(null != clus);
        assertEquals(1, clus.size());
        assertEquals("CLU-2", clus.get(0).getId());

        clus = client.getClusByLuType("LUTYPE-1X", "STATE1", contextInfo);
        assertTrue(clus == null || clus.size() == 0);
        clus = client.getClusByLuType("luType.shell.course", "STATE1X", contextInfo);
        assertTrue(clus == null || clus.size() == 0);

        try {
            clus = client.getClusByLuType(null, "STATE1", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        try {
            clus = client.getClusByLuType("luType.shell.course", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCluSet() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // getCluSetInfo
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluSetInfo csi = client.getCluSet("CLUSET-2", contextInfo);
        assertNotNull(csi);

        csi = client.getCluSet("CLUSET-1", contextInfo);
        assertNotNull(csi);

        try {
            csi = client.getCluSet("CLUSETXX-42", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e1) {
            assertTrue(true);
        }

        try {
            csi = client.getCluSet(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluSetInfoByIdList
        List<String> ids = new ArrayList<String>(1);
        ids.add("CLUSET-2");
        List<CluSetInfo> cluSets = client.getCluSetsByIds(ids, contextInfo);
        assertEquals(1, cluSets.size());

        ids.clear();
        ids.add("CLUSETXXX-42");
        cluSets = client.getCluSetsByIds(ids, contextInfo);
        assertTrue(cluSets == null || cluSets.size() == 0);

        try {
            cluSets = client.getCluSetsByIds(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getCluIdsFromCluSet
        ids = client.getCluIdsFromCluSet("CLUSET-2", contextInfo);
        assertEquals(2, ids.size());
        assertEquals("CLU-1", ids.get(0));

        try {
            ids = client.getCluIdsFromCluSet("CLUSETXXX-42", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            ids = client.getCluIdsFromCluSet(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        // getAllClusInCluSet
        List<CluInfo> clus = client.getClusFromCluSet("CLUSET-2", contextInfo);
        assertEquals(2, clus.size());
        assertEquals("CLU-1", clus.get(0).getId());

        try {
            clus = client.getClusFromCluSet("CLUSETXXX-42", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }

        try {
            clus = client.getClusFromCluSet(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        clus = client.getAllClusInCluSet("CLUSET-4", contextInfo);
        assertEquals(2, clus.size());

        try {
            ids = client.getAllCluIdsInCluSet(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        clus = client.getAllClusInCluSet("CLUSET-2", contextInfo);
        assertEquals(3, clus.size());

        // isCluInCluSet
        Boolean inSet = client.isCluInCluSet("CLU-2", "CLUSET-4", contextInfo);
        assertTrue(inSet);

        inSet = client.isCluInCluSet("CLU-3", "CLUSET-4", contextInfo);
        assertTrue(inSet);

        inSet = client.isCluInCluSet("CLUXX-42", "CLUSET-4", contextInfo);
        assertFalse(inSet);

        inSet = client.isCluInCluSet("CLU-2", "CLUSETXX-42", contextInfo);
        assertFalse(inSet);

        try {
            inSet = client.isCluInCluSet(null, "CLUSET-4", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            inSet = client.isCluInCluSet("CLU-2", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCluSetCrud() throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, ParseException,
            VersionMismatchException, UnsupportedActionException,
            CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluSetInfo cluSetInfo = new CluSetInfo();

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>Formatted Desc</p>");
        desc.setPlain("plain");
        cluSetInfo.setTypeKey("kuali.cluSet.type.CreditCourse");
        cluSetInfo.setStateKey("draft");
        cluSetInfo.setAdminOrg("uuid-1234");
        cluSetInfo.setDescr(desc);
        cluSetInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"));
        cluSetInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20180101"));
        cluSetInfo.setName("testCluSet1");
        cluSetInfo.getCluIds().add("CLU-1");
        cluSetInfo.getCluIds().add("CLU-2");
//		cluSetInfo.getCluSetIds().add("CLUSET-1");
//		cluSetInfo.getCluSetIds().add("CLUSET-2");

        cluSetInfo.getAttributes().add(new AttributeInfo("cluSet1ArrtKey1", "cluSet1ArrtValue1"));
        cluSetInfo.getAttributes().add(new AttributeInfo("cluSet1ArrtKey2", "cluSet1ArrtValue2"));

        CluSetInfo createdSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSetInfo, contextInfo);

        assertEquals("kuali.cluSet.type.CreditCourse", createdSet1.getTypeKey());
        assertEquals("uuid-1234", createdSet1.getAdminOrg());
        assertEquals("<p>Formatted Desc</p>", createdSet1.getDescr().getFormatted());
        assertEquals("plain", createdSet1.getDescr().getPlain());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"), createdSet1.getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20180101"), createdSet1.getExpirationDate());
        assertEquals("testCluSet1", createdSet1.getName());
        assertEquals("CLU-1", createdSet1.getCluIds().get(0));
        assertEquals("CLU-2", createdSet1.getCluIds().get(1));
//		assertEquals("CLUSET-1", createdSet1.getCluSetIds().get(0));
//		assertEquals("CLUSET-2", createdSet1.getCluSetIds().get(1));
        assertEquals("cluSet1ArrtValue1", createdSet1.getAttributeValue("cluSet1ArrtKey1"));
        assertEquals("cluSet1ArrtValue2", createdSet1.getAttributeValue("cluSet1ArrtKey2"));
        assertNotNull(createdSet1.getMeta().getCreateTime());
        assertNotNull(createdSet1.getMeta().getUpdateTime());
        assertNotNull(createdSet1.getId());

        createdSet1.setAdminOrg("uuid-1234-5678");
        createdSet1.getDescr().setFormatted("UP<p>Formatted Desc</p>");
        createdSet1.getDescr().setPlain("UPplain");
        createdSet1.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20090101"));
        createdSet1.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190101"));
        createdSet1.setName("UPtestCluSet1");
        createdSet1.getCluIds().remove(1);
        createdSet1.getCluIds().add("CLU-3");
//		createdSet1.getCluSetIds().remove(1);
//		createdSet1.getCluSetIds().add("CLUSET-3");
        createdSet1.getAttributes().set(0, new AttributeInfo("cluSet1ArrtKey1", "UPcluSet1ArrtValue1"));
        createdSet1.getAttributes().remove(1);
        createdSet1.getAttributes().add(new AttributeInfo("cluSet1ArrtKey3", "cluSet1ArrtValue3"));

        CluSetInfo updatedSet1 = client.updateCluSet(createdSet1.getId(), createdSet1, contextInfo);

        assertEquals("uuid-1234-5678", updatedSet1.getAdminOrg());
        assertEquals("UP<p>Formatted Desc</p>", updatedSet1.getDescr().getFormatted());
        assertEquals("UPplain", updatedSet1.getDescr().getPlain());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20090101"), updatedSet1.getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190101"), updatedSet1.getExpirationDate());
        assertEquals("UPtestCluSet1", updatedSet1.getName());
        assertEquals("CLU-1", updatedSet1.getCluIds().get(0));
        assertEquals("CLU-3", updatedSet1.getCluIds().get(1));
        assertEquals(2, updatedSet1.getCluIds().size());
//		assertEquals("CLUSET-1", updatedSet1.getCluSetIds().get(0));
//		assertEquals("CLUSET-3", updatedSet1.getCluSetIds().get(1));
//		assertEquals(2, updatedSet1.getCluSetIds().size());
        assertEquals("UPcluSet1ArrtValue1", updatedSet1.getAttributeValue("cluSet1ArrtKey1"));
        assertEquals("cluSet1ArrtValue3", updatedSet1.getAttributeValue("cluSet1ArrtKey3"));
        assertEquals(2, updatedSet1.getAttributes().size());
        assertNotNull(updatedSet1.getMeta().getUpdateTime());
    }

    @Test
    public void testRemoveCluFromCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluIds().add("CLU-1");
        createCluSet.getCluIds().add("CLU-2");
        createCluSet.getCluIds().add("CLU-3");

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(3, createdCluSet1.getCluIds().size());

        StatusInfo status = client.removeCluFromCluSet("CLU-2", createdCluSet1.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        createdCluSet1 = client.getCluSet(createdCluSet1.getId(), contextInfo);

        assertEquals(2, createdCluSet1.getCluIds().size());
        assertTrue(createdCluSet1.getCluIds().contains("CLU-1"));
        assertFalse(createdCluSet1.getCluIds().contains("CLU-2"));
        assertTrue(createdCluSet1.getCluIds().contains("CLU-3"));
    }

    @Test
    public void testRemoveCluSetFromCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluSetIds().add("CLUSET-1");
        createCluSet.getCluSetIds().add("CLUSET-2");
        createCluSet.getCluSetIds().add("CLUSET-3");

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(3, createdCluSet1.getCluSetIds().size());

        StatusInfo status = client.removeCluSetFromCluSet(createdCluSet1.getId(), "CLUSET-2", contextInfo);
        assertTrue(status.getIsSuccess());

        createdCluSet1 = client.getCluSet(createdCluSet1.getId(), contextInfo);

        assertEquals(2, createdCluSet1.getCluSetIds().size());
        assertTrue(createdCluSet1.getCluSetIds().contains("CLUSET-1"));
        assertFalse(createdCluSet1.getCluSetIds().contains("CLUSET-2"));
        assertTrue(createdCluSet1.getCluSetIds().contains("CLUSET-3"));
    }

    @Test
    public void testDeleteCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluSetInfo createCluSet = createCluSetInfo();

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);

        StatusInfo status = client.deleteCluSet(createdCluSet1.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        try {
            client.getCluSet(createdCluSet1.getId(), contextInfo);
            fail("Should have thrown DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCluCluRelation() throws ParseException,
            AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException,
            DependentObjectsExistException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<CluCluRelationInfo> ccrs = client.getCluCluRelationsByClu("CLU-1", contextInfo);
        assertNotNull(ccrs);
        assertEquals(2, ccrs.size());

        ccrs = client.getCluCluRelationsByClu("CLUXX-42", contextInfo);
        assertTrue(ccrs == null || ccrs.size() == 0);

        try {
            ccrs = client.getCluCluRelationsByClu(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

    }

    // KSCM-484 TODO CluAdminOrg missing parameter state
    @Test
    public void testCluCrud() throws ParseException, AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException, DependentObjectsExistException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        CluInfo clu = createCluInfo();

        clu.getOfficialIdentifier().setCode("offId-divisionoffId-suffixcode");
        clu.getOfficialIdentifier().getAttributes().add(new AttributeInfo("OfficialIdentKey", "OfficialIdentValue"));

        clu.getAlternateIdentifiers().get(0).setCode("cluId1-divisioncluId1-suffixcode");
        clu.getAlternateIdentifiers().get(0).getAttributes().add(new AttributeInfo("AltIdentKey", "AltIdentValue"));
        clu.getAlternateIdentifiers().get(1).setCode("cluId2-divisioncluId2-suffixcode");
        clu.getAlternateIdentifiers().get(1).getAttributes().add(new AttributeInfo("AltIdentKey", "AltIdentValue"));


        // Do the actual create call
        CluInfo createdClu = client.createClu("luType.shell.course", clu, contextInfo);
        createdClu = client.getClu(createdClu.getId(), contextInfo);
        // Validate Results
        assertNotNull(createdClu);

        assertEquals("AccountingAttrValue1", createdClu.getAccountingInfo().getAttributeValue("AccountingAttrKey1"));
        assertEquals("AccountingAttrValue2", createdClu.getAccountingInfo().getAttributeValue("AccountingAttrKey2"));

        assertEquals("offId-divisionoffId-suffixcode", createdClu.getOfficialIdentifier().getCode());
        assertEquals("offId-division", createdClu.getOfficialIdentifier().getDivision());
        assertEquals("offId-level", createdClu.getOfficialIdentifier().getLevel());
        assertEquals("offId-longName", createdClu.getOfficialIdentifier().getLongName());
        assertEquals("offId-shortName", createdClu.getOfficialIdentifier().getShortName());
        assertEquals("offId-state", createdClu.getOfficialIdentifier().getStateKey());
        assertEquals("offId-type", createdClu.getOfficialIdentifier().getTypeKey());
        assertEquals("offId-variation", createdClu.getOfficialIdentifier().getVariation());
        assertEquals("offId-suffixcode", createdClu.getOfficialIdentifier().getSuffixCode());
        assertEquals("offId-orgid", createdClu.getOfficialIdentifier().getOrgId());
        assertEquals("OfficialIdentValue", createdClu.getOfficialIdentifier().getAttributeValue("OfficialIdentKey"));

        assertEquals("cluId1-divisioncluId1-suffixcode", createdClu.getAlternateIdentifiers().get(0).getCode());
        assertEquals("cluId1-division", createdClu.getAlternateIdentifiers().get(0).getDivision());
        assertEquals("cluId1-level", createdClu.getAlternateIdentifiers().get(0).getLevel());
        assertEquals("cluId1-longName", createdClu.getAlternateIdentifiers().get(0).getLongName());
        assertEquals("cluId1-shortName", createdClu.getAlternateIdentifiers().get(0).getShortName());
        assertEquals("cluId1-state", createdClu.getAlternateIdentifiers().get(0).getStateKey());
        assertEquals("cluId1-type", createdClu.getAlternateIdentifiers().get(0).getTypeKey());
        assertEquals("cluId1-variation", createdClu.getAlternateIdentifiers().get(0).getVariation());
        assertEquals("cluId1-suffixcode", createdClu.getAlternateIdentifiers().get(0).getSuffixCode());
        assertEquals("cluId1-orgid", createdClu.getAlternateIdentifiers().get(0).getOrgId());
        assertEquals("AltIdentValue", createdClu.getAlternateIdentifiers().get(0).getAttributeValue("AltIdentKey"));

        assertEquals("cluId2-divisioncluId2-suffixcode", createdClu.getAlternateIdentifiers().get(1).getCode());
        assertEquals("cluId2-division", createdClu.getAlternateIdentifiers().get(1).getDivision());
        assertEquals("cluId2-level", createdClu.getAlternateIdentifiers().get(1).getLevel());
        assertEquals("cluId2-longName", createdClu.getAlternateIdentifiers()
                .get(1).getLongName());
        assertEquals("cluId2-shortName", createdClu.getAlternateIdentifiers()
                .get(1).getShortName());
        assertEquals("cluId2-state", createdClu.getAlternateIdentifiers()
                .get(1).getStateKey());
        assertEquals("cluId2-type", createdClu.getAlternateIdentifiers().get(1)
                .getTypeKey());
        assertEquals("cluId2-variation", createdClu.getAlternateIdentifiers()
                .get(1).getVariation());
        assertEquals("cluId2-suffixcode", createdClu.getAlternateIdentifiers()
                .get(1).getSuffixCode());
        assertEquals("cluId2-orgid", createdClu.getAlternateIdentifiers()
                .get(1).getOrgId());
        assertEquals("AltIdentValue", createdClu.getAlternateIdentifiers().get(1).getAttributeValue("AltIdentKey"));

        assertEquals("cluAttrValue1", createdClu.getAttributeValue("cluAttrKey1"));
        assertEquals("cluAttrValue2", createdClu.getAttributeValue("cluAttrKey2"));

        assertTrue(createdClu.getCanCreateLui());

        assertEquals(545, createdClu.getDefaultEnrollmentEstimate());
        assertEquals(999, createdClu.getDefaultMaximumEnrollment());

        assertEquals("<p>DESC FORMATTED</p>", createdClu.getDescr()
                .getFormatted());
        assertEquals("DESC PLAIN", createdClu.getDescr().getPlain());

        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100203"), createdClu.getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21001231"), createdClu.getExpirationDate());

        assertTrue(createdClu.getIsEnrollable());

        assertEquals("FeeAttrValue1", createdClu.getFeeInfo().getAttributeValue("FeeAttrKey1"));
        assertEquals("FeeAttrValue2", createdClu.getFeeInfo().getAttributeValue("FeeAttrKey2"));

        assertEquals("Clu Fee", createdClu.getFeeInfo().getDescr().getPlain());
        assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().size());
        assertEquals("FEE-TYPE-X", createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeType());
        assertEquals("RATE-TYPE-X", createdClu.getFeeInfo().getCluFeeRecords().get(0).getRateType());
        assertEquals("Clu Fee Record", createdClu.getFeeInfo().getCluFeeRecords().get(0).getDescr().getPlain());
        assertEquals(3, createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().size());
        assertEquals(Integer.valueOf(200), createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyQuantity());
        assertEquals(0, createdClu.getFeeInfo().getCluFeeRecords().get(1).getFeeAmounts().size());

        assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().size());
        assertEquals(35l, (long) createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getPercentage());

        assertTrue(createdClu.getIsHasEarlyDropDeadline());
        assertTrue(createdClu.getIsHazardousForDisabledStudents());

        assertEquals("EXT-orgId-1", createdClu.getPrimaryInstructor()
                .getOrgId());
        assertEquals("EXT-personId-1", createdClu.getPrimaryInstructor()
                .getPersonId());
        assertEquals("PrimaryInstAttrValue1", createdClu.getPrimaryInstructor().getAttributeValue("PrimaryInstAttrKey1"));
        assertEquals("PrimaryInstAttrValue2", createdClu.getPrimaryInstructor().getAttributeValue("PrimaryInstAttrKey2"));

        assertEquals("EXT-orgId-2", createdClu.getInstructors().get(0)
                .getOrgId());
        assertEquals("EXT-personId-2", createdClu.getInstructors().get(0)
                .getPersonId());
        assertEquals("Inst1AttrValue1", createdClu.getInstructors().get(0).getAttributeValue("Inst1AttrKey1"));
        assertEquals("Inst1AttrValue2", createdClu.getInstructors().get(0).getAttributeValue("Inst1AttrKey2"));

        assertEquals("EXT-orgId-3", createdClu.getInstructors().get(1)
                .getOrgId());
        assertEquals("EXT-personId-3", createdClu.getInstructors().get(1)
                .getPersonId());
        assertEquals("Inst2AttrValue1", createdClu.getInstructors().get(1).getAttributeValue("Inst2AttrKey1"));
        assertEquals("Inst2AttrValue2", createdClu.getInstructors().get(1).getAttributeValue("Inst2AttrKey2"));

        assertEquals("luCode1.key", createdClu.getLuCodes().get(0).getId());
        assertEquals("luCode1-desc", createdClu.getLuCodes().get(0).getDescr().getPlain());
        assertEquals("luCode1-value", createdClu.getLuCodes().get(0).getValue());
        assertEquals("luCode1AttrValue1", createdClu.getLuCodes().get(0).getAttributeValue("luCode1AttrKey1"));
        assertEquals("luCode1AttrValue2", createdClu.getLuCodes().get(0).getAttributeValue("luCode1AttrKey2"));
        assertNotNull(createdClu.getLuCodes().get(0).getMeta());
        assertNotNull(createdClu.getLuCodes().get(0).getMeta()
                .getVersionInd());
        assertNotNull(createdClu.getLuCodes().get(0).getMeta()
                .getCreateTime());

        assertEquals("luCode2.key", createdClu.getLuCodes().get(1).getId());
        assertEquals("luCode2-desc", createdClu.getLuCodes().get(1).getDescr().getPlain());
        assertEquals("luCode2-value", createdClu.getLuCodes().get(1).getValue());
        assertEquals("luCode2AttrValue1", createdClu.getLuCodes().get(1).getAttributeValue("luCode2AttrKey1"));
        assertEquals("luCode2AttrValue2", createdClu.getLuCodes().get(1).getAttributeValue("luCode2AttrKey2"));
        assertNotNull(createdClu.getLuCodes().get(1).getMeta());
        assertNotNull(createdClu.getLuCodes().get(1).getMeta()
                .getVersionInd());
        assertNotNull(createdClu.getLuCodes().get(1).getMeta()
                .getCreateTime());


        assertEquals("nextReviewPeriod", createdClu.getNextReviewPeriod());

        assertEquals("offeredAtpType1", createdClu.getOfferedAtpTypes().get(0));
        assertEquals("offeredAtpType2", createdClu.getOfferedAtpTypes().get(1));


        assertEquals("http://student.kuali.org/clus", createdClu
                .getReferenceURL());

        assertEquals("Clu-state", createdClu.getStateKey());

        assertEquals("EXT-stdDuration-Id1", createdClu.getStdDuration()
                .getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(7867), createdClu.getStdDuration()
                .getTimeQuantity());

        assertEquals("luType.shell.course", createdClu.getTypeKey());

        assertNotNull(createdClu.getMeta());
        assertNotNull(createdClu.getMeta().getVersionInd());
        assertNotNull(createdClu.getMeta().getCreateTime());

        assertNotNull(createdClu.getId());

        checkCampusLocationCreate(createdClu);

        checkIntensityCreate(createdClu);

        checkAccreditationListCreate(createdClu);

        checkAdminOrgsCreate(createdClu);

        // Now Update the Clu!
        for (int i = 0; i < createdClu.getAccountingInfo().getAttributes().size(); i++) {
            String attributeKey = createdClu.getAccountingInfo().getAttributes().get(i).getKey();
            if (attributeKey.equals("AccountingAttrKey1")) {
                createdClu.getAccountingInfo().getAttributes().set(i, new AttributeInfo("AccountingAttrKey1", "AccountingAttrValue1"));
            } else if (attributeKey.equals("AccountingAttrKey2")) {
                createdClu.getAccountingInfo().getAttributes().remove(i);
            }
        }
        createdClu.getAccountingInfo().getAttributes().add(new AttributeInfo("AccountingAttrKey3", "AccountingAttrValue3"));

        createdClu.getOfficialIdentifier().setCode("UPoffId-code");
        createdClu.getOfficialIdentifier().setDivision("UPoffId-division");
        createdClu.getOfficialIdentifier().setLevel("UPoffId-level");
        createdClu.getOfficialIdentifier().setSuffixCode("UPoffId-suffixcode");
        createdClu.getOfficialIdentifier().setLongName("UPoffId-longName");
        createdClu.getOfficialIdentifier().setShortName("UPoffId-shortName");
        createdClu.getOfficialIdentifier().setStateKey("UPoffId-state");
        createdClu.getOfficialIdentifier().setTypeKey("UPoffId-type");
        createdClu.getOfficialIdentifier().setVariation("UPoffId-variation");
        createdClu.getOfficialIdentifier().getAttributes().add(new AttributeInfo("OfficialIdentKeyUptd", "OfficialIdentValueUptd"));


        createdClu.getAlternateIdentifiers().get(0).setCode("UPcluId1-code");
        createdClu.getAlternateIdentifiers().get(0).setDivision("UPcluId1-division");
        createdClu.getAlternateIdentifiers().get(0).setLevel("UPcluId1-level");
        createdClu.getAlternateIdentifiers().get(0).setSuffixCode("UPcluId1-suffixcode");
        createdClu.getAlternateIdentifiers().get(0).setLongName("UPcluId1-longName");
        createdClu.getAlternateIdentifiers().get(0).setShortName("UPcluId1-shortName");
        createdClu.getAlternateIdentifiers().get(0).setStateKey("UPcluId1-state");
        createdClu.getAlternateIdentifiers().get(0).setTypeKey("UPcluId1-type");
        createdClu.getAlternateIdentifiers().get(0).setVariation("UPcluId1-variation");
        createdClu.getAlternateIdentifiers().get(0).getAttributes().add(new AttributeInfo("AltIdentKeyUptd", "AltIdentValueUptd"));

        createdClu.getAlternateIdentifiers().remove(1);

        CluIdentifierInfo cluId3 = new CluIdentifierInfo();
        cluId3.setCode("cluId3-code");
        cluId3.setDivision("cluId3-division");
        cluId3.setSuffixCode("cluId3-suffixcode");
        cluId3.setLevel("cluId3-level");
        cluId3.setLongName("cluId3-longName");
        cluId3.setShortName("cluId3-shortName");
        cluId3.setStateKey("cluId3-state");
        cluId3.setTypeKey("cluId3-type");
        cluId3.setVariation("cluId3-variation");


        createdClu.getAlternateIdentifiers().add(cluId3);

        for (int i = 0; i < createdClu.getAttributes().size(); i++) {
            String attributeKey = createdClu.getAttributes().get(i).getKey();
            if (attributeKey.equals("cluAttrKey1")) {
                createdClu.getAttributes().set(i, new AttributeInfo("cluAttrKey1", "cluAttrValue1"));
            } else if (attributeKey.equals("cluAttrKey2")) {
                createdClu.getAttributes().remove(i);
            }
        }
        createdClu.getAttributes().add(new AttributeInfo("cluAttrKey3", "cluAttrValue3"));

        createdClu.setCanCreateLui(false);

        createdClu.setDefaultEnrollmentEstimate(9545);
        createdClu.setDefaultMaximumEnrollment(9999);

        createdClu.getDescr().setFormatted("UP<p>DESC FORMATTED</p>");
        createdClu.getDescr().setPlain("UPDESC PLAIN");

        createdClu.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190203"));
        createdClu.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21091231"));

        createdClu.setIsEnrollable(false);

        for (int i = 0; i < createdClu.getFeeInfo().getAttributes().size(); i++) {
            String attributeKey = createdClu.getFeeInfo().getAttributes().get(i).getKey();
            if (attributeKey.equals("FeeAttrKey1")) {
                createdClu.getFeeInfo().getAttributes().set(i, new AttributeInfo("FeeAttrKey1", "FeeAttrValue1"));
            } else if (attributeKey.equals("FeeAttrKey2")) {
                createdClu.getFeeInfo().getAttributes().remove(i);
            }
        }
        createdClu.getFeeInfo().getAttributes().add(new AttributeInfo("FeeAttrKey3", "FeeAttrValue3"));

        createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().remove(0);
        createdClu.getFeeInfo().getCluFeeRecords().get(1).setFeeType("FEE-TYPE-Z");

        createdClu.setIsHasEarlyDropDeadline(false);

        createdClu.setIsHazardousForDisabledStudents(false);

        createdClu.getPrimaryInstructor().setOrgId("UPEXT-orgId-1");
        createdClu.getPrimaryInstructor().setPersonId("UPEXT-personId-1");
        for (int i = 0; i < createdClu.getPrimaryInstructor().getAttributes().size(); i++) {
            String attributeKey = createdClu.getPrimaryInstructor().getAttributes().get(i).getKey();
            if (attributeKey.equals("PrimaryInstAttrKey1")) {
                createdClu.getPrimaryInstructor().getAttributes().set(i, new AttributeInfo("PrimaryInstAttrKey1", "PrimaryInstAttrValue1"));
            } else if (attributeKey.equals("PrimaryInstAttrKey2")) {
                createdClu.getPrimaryInstructor().getAttributes().remove(i);
            }
        }
        createdClu.getPrimaryInstructor().getAttributes().add(new AttributeInfo("PrimaryInstAttrKey3", "PrimaryInstAttrValue3"));

        createdClu.getInstructors().get(0).setOrgId("UPEXT-orgId-2");
        createdClu.getInstructors().get(0).setPersonId("UPEXT-personId-2");
        for (int i = 0; i < createdClu.getInstructors().get(0).getAttributes().size(); i++) {
            String attributeKey = createdClu.getInstructors().get(0).getAttributes().get(i).getKey();
            if (attributeKey.equals("Inst1AttrKey1")) {
                createdClu.getInstructors().get(0).getAttributes().set(i, new AttributeInfo("Inst1AttrKey1", "Inst1AttrValue1"));
            } else if (attributeKey.equals("Inst1AttrKey2")) {
                createdClu.getInstructors().get(0).getAttributes().remove(i);
            }
        }
        createdClu.getInstructors().get(0).getAttributes().add(new AttributeInfo("Inst1AttrKey3", "Inst1AttrValue3"));

        createdClu.getInstructors().remove(1);

        CluInstructorInfo instructor3 = new CluInstructorInfo();
        instructor3.setOrgId("EXT-orgId-3");
        instructor3.setPersonId("EXT-personId-3");
        instructor3.getAttributes().add(new AttributeInfo("Inst3AttrKey1", "Inst3AttrValue1"));
        instructor3.getAttributes().add(new AttributeInfo("Inst3AttrKey2", "Inst3AttrValue2"));
        createdClu.getInstructors().add(instructor3);
        RichTextInfo UPLuCode1Desc = new RichTextInfo();
        UPLuCode1Desc.setPlain("luCodetwodesc");
        createdClu.getLuCodes().get(0).setDescr(UPLuCode1Desc);
        createdClu.getLuCodes().get(0).setValue("UPluCode1-value");
        for (int i = 0; i < createdClu.getLuCodes().get(0).getAttributes().size(); i++) {
            String attributeKey = createdClu.getLuCodes().get(0).getAttributes().get(i).getKey();
            if (attributeKey.equals("luCode1AttrKey1")) {
                createdClu.getLuCodes().get(0).getAttributes().set(i, new AttributeInfo("luCode1AttrKey1", "luCode1AttrValue1"));
            } else if (attributeKey.equals("luCode1AttrKey2")) {
                createdClu.getLuCodes().get(0).getAttributes().remove(i);
            }
        }
        createdClu.getLuCodes().get(0).getAttributes().add(new AttributeInfo("luCode1AttrKey3", "luCode1AttrValue3"));
        createdClu.getLuCodes().get(0).setTypeKey("updatedType");

        createdClu.getLuCodes().remove(1);

        LuCodeInfo luCode3 = new LuCodeInfo();
        luCode3.setId("luCode3.key");
        RichTextInfo luCode3Desc = new RichTextInfo();
        luCode3Desc.setPlain("luCode3-desc");
        luCode3.setDescr(luCode3Desc);
        luCode3.setValue("luCode3-value");
        luCode3.getAttributes().add(new AttributeInfo("luCode3AttrKey1", "luCode3AttrValue1"));
        luCode3.getAttributes().add(new AttributeInfo("luCode3AttrKey2", "luCode3AttrValue2"));
        luCode3.setTypeKey("updatedType");
        createdClu.getLuCodes().add(luCode3);


        createdClu.setNextReviewPeriod("UPnextReviewPeriod");

        createdClu.getOfferedAtpTypes().remove(1);
        createdClu.getOfferedAtpTypes().add("offeredAtpType3");


        CluInstructorInfo pubInstructor3 = new CluInstructorInfo();
        pubInstructor3.setOrgId("EXT-orgId-3");
        pubInstructor3.setPersonId("EXT-personId-3");
        pubInstructor3.getAttributes().add(new AttributeInfo("PubInst3AttrKey1", "PubInst3AttrValue1"));
        pubInstructor3.getAttributes().add(new AttributeInfo("PubInst3AttrKey2", "PubInst3AttrValue2"));

        createdClu.setReferenceURL("UPhttp://student.kuali.org/clus");

        createdClu.setStateKey("UPClu-state");

        createdClu.getStdDuration().setAtpDurationTypeKey(
                "UPEXT-stdDuration-Id1");
        createdClu.getStdDuration().setTimeQuantity(new Integer(97867));

        createdClu.setTypeKey("luType.shell.program");

        updateAdminOrgs(createdClu);

        updateAccreditationList(createdClu);

        updateIntensity(createdClu);

        updateCampusLocationList(createdClu);

        // Do Update
        CluInfo updatedClu = client.updateClu(createdClu.getId(), createdClu, contextInfo);

        // Validate Results
        assertNotNull(updatedClu);

        assertEquals("AccountingAttrValue1", updatedClu.getAccountingInfo().getAttributeValue("AccountingAttrKey1"));
        assertEquals("AccountingAttrValue3", updatedClu.getAccountingInfo().getAttributeValue("AccountingAttrKey3"));
        assertEquals(2, updatedClu.getAccountingInfo().getAttributes().size());

        assertEquals("UPoffId-code", updatedClu
                .getOfficialIdentifier().getCode());
        assertEquals("UPoffId-division", updatedClu.getOfficialIdentifier()
                .getDivision());
        assertEquals("UPoffId-level", updatedClu.getOfficialIdentifier()
                .getLevel());
        assertEquals("UPoffId-suffixcode", updatedClu.getOfficialIdentifier()
                .getSuffixCode());
        assertEquals("UPoffId-longName", updatedClu.getOfficialIdentifier()
                .getLongName());
        assertEquals("UPoffId-shortName", updatedClu.getOfficialIdentifier()
                .getShortName());
        assertEquals("UPoffId-state", updatedClu.getOfficialIdentifier()
                .getStateKey());
        assertEquals("UPoffId-type", updatedClu.getOfficialIdentifier()
                .getTypeKey());
        assertEquals("UPoffId-variation", updatedClu.getOfficialIdentifier()
                .getVariation());
        assertEquals(2, updatedClu.getOfficialIdentifier().getAttributes().size());
        assertEquals("OfficialIdentValueUptd", updatedClu.getOfficialIdentifier().getAttributeValue("OfficialIdentKeyUptd"));

        assertEquals("UPcluId1-code", updatedClu
                .getAlternateIdentifiers().get(0).getCode());
        assertEquals("UPcluId1-division", updatedClu.getAlternateIdentifiers()
                .get(0).getDivision());
        assertEquals("UPcluId1-level", updatedClu.getAlternateIdentifiers()
                .get(0).getLevel());
        assertEquals("UPcluId1-suffixcode", updatedClu
                .getAlternateIdentifiers().get(0).getSuffixCode());
        assertEquals("UPcluId1-longName", updatedClu.getAlternateIdentifiers()
                .get(0).getLongName());
        assertEquals("UPcluId1-shortName", updatedClu.getAlternateIdentifiers()
                .get(0).getShortName());
        assertEquals("UPcluId1-state", updatedClu.getAlternateIdentifiers()
                .get(0).getStateKey());
        assertEquals("UPcluId1-type", updatedClu.getAlternateIdentifiers().get(
                0).getTypeKey());
        assertEquals("UPcluId1-variation", updatedClu.getAlternateIdentifiers()
                .get(0).getVariation());
        assertEquals(2, updatedClu.getAlternateIdentifiers().get(0).getAttributes().size());
        assertEquals("AltIdentValueUptd", createdClu.getAlternateIdentifiers().get(0).getAttributeValue("AltIdentKeyUptd"));

        assertEquals("cluId3-code", updatedClu
                .getAlternateIdentifiers().get(1).getCode());
        assertEquals("cluId3-division", updatedClu.getAlternateIdentifiers()
                .get(1).getDivision());
        assertEquals("cluId3-level", updatedClu.getAlternateIdentifiers()
                .get(1).getLevel());
        assertEquals("cluId3-suffixcode", updatedClu.getAlternateIdentifiers()
                .get(1).getSuffixCode());
        assertEquals("cluId3-longName", updatedClu.getAlternateIdentifiers()
                .get(1).getLongName());
        assertEquals("cluId3-shortName", updatedClu.getAlternateIdentifiers()
                .get(1).getShortName());
        assertEquals("cluId3-state", updatedClu.getAlternateIdentifiers()
                .get(1).getStateKey());
        assertEquals("cluId3-type", updatedClu.getAlternateIdentifiers().get(1)
                .getTypeKey());
        assertEquals("cluId3-variation", updatedClu.getAlternateIdentifiers()
                .get(1).getVariation());

        assertEquals(2, updatedClu.getAlternateIdentifiers().size());

        assertEquals("cluAttrValue1", updatedClu.getAttributeValue("cluAttrKey1"));
        assertEquals("cluAttrValue3", updatedClu.getAttributeValue("cluAttrKey3"));
        assertEquals(2, updatedClu.getAttributes().size());

        assertFalse(updatedClu.getCanCreateLui());

        assertEquals(9545, updatedClu.getDefaultEnrollmentEstimate());
        assertEquals(9999, updatedClu.getDefaultMaximumEnrollment());

        assertEquals("UP<p>DESC FORMATTED</p>", updatedClu.getDescr()
                .getFormatted());
        assertEquals("UPDESC PLAIN", updatedClu.getDescr().getPlain());

        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190203"), updatedClu.getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21091231"), updatedClu.getExpirationDate());

        assertFalse(updatedClu.getIsEnrollable());

        assertEquals("FeeAttrValue1", updatedClu.getFeeInfo().getAttributeValue("FeeAttrKey1"));
        assertEquals("FeeAttrValue3", updatedClu.getFeeInfo().getAttributeValue("FeeAttrKey3"));
        assertEquals(2, updatedClu.getFeeInfo().getAttributes().size());

        assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().size());
        assertEquals("FEE-TYPE-Z", createdClu.getFeeInfo().getCluFeeRecords().get(1).getFeeType());

        assertEquals(1, createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().size());
        assertEquals(65l, (long) createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getPercentage());

        assertFalse(updatedClu.getIsHasEarlyDropDeadline());
        assertFalse(updatedClu.getIsHazardousForDisabledStudents());

        assertEquals("UPEXT-orgId-1", updatedClu.getPrimaryInstructor()
                .getOrgId());
        assertEquals("UPEXT-personId-1", updatedClu.getPrimaryInstructor()
                .getPersonId());
        assertEquals("PrimaryInstAttrValue1", updatedClu.getPrimaryInstructor().getAttributeValue("PrimaryInstAttrKey1"));
        assertEquals("PrimaryInstAttrValue3", updatedClu.getPrimaryInstructor().getAttributeValue("PrimaryInstAttrKey3"));
        assertEquals(2, updatedClu.getPrimaryInstructor().getAttributes()
                .size());

        assertEquals(2, updatedClu.getInstructors().size());

        assertEquals("UPEXT-orgId-2", updatedClu.getInstructors().get(0)
                .getOrgId());
        assertEquals("UPEXT-personId-2", updatedClu.getInstructors().get(0)
                .getPersonId());
        assertEquals("Inst1AttrValue1", updatedClu.getInstructors().get(0).getAttributeValue("Inst1AttrKey1"));
        assertEquals("Inst1AttrValue3", updatedClu.getInstructors().get(0).getAttributeValue("Inst1AttrKey3"));
        assertEquals(2, updatedClu.getInstructors().get(0).getAttributes()
                .size());

        assertEquals("EXT-orgId-3", updatedClu.getInstructors().get(1)
                .getOrgId());
        assertEquals("EXT-personId-3", updatedClu.getInstructors().get(1)
                .getPersonId());
        assertEquals("Inst3AttrValue1", updatedClu.getInstructors().get(1).getAttributeValue("Inst3AttrKey1"));
        assertEquals("Inst3AttrValue2", updatedClu.getInstructors().get(1).getAttributeValue("Inst3AttrKey2"));
        assertEquals(2, updatedClu.getInstructors().get(1).getAttributes()
                .size());

        assertEquals(2, updatedClu.getLuCodes().size());

        assertEquals("luCode1.key", updatedClu.getLuCodes().get(0).getId());
        assertEquals("luCodetwodesc", updatedClu.getLuCodes().get(0).getDescr().getPlain());
        assertEquals("UPluCode1-value", updatedClu.getLuCodes().get(0)
                .getValue());
        assertEquals("luCode1AttrValue1", updatedClu.getLuCodes().get(0).getAttributeValue("luCode1AttrKey1"));
        assertEquals("luCode1AttrValue3", updatedClu.getLuCodes().get(0).getAttributeValue("luCode1AttrKey3"));
        assertEquals(2, updatedClu.getLuCodes().get(0).getAttributes().size());
        assertNotNull(updatedClu.getLuCodes().get(0).getMeta());
        assertNotNull(updatedClu.getLuCodes().get(0).getMeta()
                .getVersionInd());
        assertNotNull(updatedClu.getLuCodes().get(0).getMeta()
                .getCreateTime());
        assertNotNull(updatedClu.getLuCodes().get(0).getMeta()
                .getUpdateTime());

        // id is nulled and subsequently set to a uuid
        //assertEquals("luCode3.key", updatedClu.getLuCodes().get(1).getId());
        assertEquals("luCode3-desc", updatedClu.getLuCodes().get(1).getDescr().getPlain());
        assertEquals("luCode3-value", updatedClu.getLuCodes().get(1).getValue());
        assertEquals("luCode3AttrValue1", updatedClu.getLuCodes().get(1).getAttributeValue("luCode3AttrKey1"));
        assertEquals("luCode3AttrValue2", updatedClu.getLuCodes().get(1).getAttributeValue("luCode3AttrKey2"));
        assertNotNull(updatedClu.getLuCodes().get(1).getMeta());
        assertNotNull(updatedClu.getLuCodes().get(1).getMeta()
                .getVersionInd());
        assertNotNull(updatedClu.getLuCodes().get(1).getMeta()
                .getCreateTime());
        assertNotNull(updatedClu.getLuCodes().get(1).getMeta()
                .getUpdateTime());

        assertEquals("UPnextReviewPeriod", updatedClu.getNextReviewPeriod());

        assertEquals("offeredAtpType1", updatedClu.getOfferedAtpTypes().get(0));
        assertEquals("offeredAtpType3", updatedClu.getOfferedAtpTypes().get(1));
        assertEquals(2, updatedClu.getOfferedAtpTypes().size());


        assertEquals("UPhttp://student.kuali.org/clus", updatedClu
                .getReferenceURL());

        assertEquals("UPClu-state", updatedClu.getStateKey());

        assertEquals("UPEXT-stdDuration-Id1", updatedClu.getStdDuration()
                .getAtpDurationTypeKey());
        assertEquals(Integer.valueOf(97867), updatedClu.getStdDuration()
                .getTimeQuantity());

        assertEquals("luType.shell.program", updatedClu.getTypeKey());

        assertEquals(false, updatedClu.getIsEnrollable());
        assertEquals(false, updatedClu.getIsHazardousForDisabledStudents());

        assertNotNull(updatedClu.getMeta());
        assertNotNull(updatedClu.getMeta().getVersionInd());
        assertNotNull(updatedClu.getMeta().getCreateTime());
        assertNotNull(updatedClu.getMeta().getUpdateTime());

        assertEquals(createdClu.getId(), updatedClu.getId());

        checkAdminOrgUpdate(updatedClu);

        checkAccreditationListUpdate(updatedClu);

        checkCampusLocationUpdate(updatedClu);

        checkIntensityUpdate(updatedClu);

        // Test Optimistic locking
        try {
            updatedClu = client.updateClu(createdClu.getId(), createdClu, contextInfo);
            fail("Should have thrown VersionMismatchException");
        } catch (VersionMismatchException e) {

        }

        // Test Delete
        try {
            client.getClu(createdClu.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            fail("Should not have thrown DoesNotExistException");
        }

        StatusInfo status = client.deleteClu(createdClu.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        try {
            client.getClu(createdClu.getId(), contextInfo);
            fail("Should have thrown DoesNotExistException");
        } catch (DoesNotExistException e) {

        }

    }

    @Test
    public void testCluCluRelationCrud() throws Exception {

        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        final CluCluRelationInfo cluCluRelationInfo = new CluCluRelationInfo();

        final Date effectiveDate = DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"),
                expirationDate = DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100101");
        cluCluRelationInfo.setEffectiveDate(effectiveDate);
        cluCluRelationInfo.setExpirationDate(expirationDate);
        cluCluRelationInfo.setIsCluRelationRequired(true);
        cluCluRelationInfo.setStateKey("hello");

        if (cluCluRelationInfo.getAttributes() == null) {
            cluCluRelationInfo.setAttributes(new ArrayList<AttributeInfo>());
        }
        cluCluRelationInfo.getAttributes().add(new AttributeInfo("clucluAttrKey1", "clucluAttrValue1"));
        cluCluRelationInfo.getAttributes().add(new AttributeInfo("clucluAttrKey2", "clucluAttrValue2"));
        cluCluRelationInfo.getAttributes().add(new AttributeInfo("clucluAttrKey3", "clucluAttrValue3"));

        CluCluRelationInfo created = client.createCluCluRelation("CLU-1",
                "CLU-2", "luLuType.type1", cluCluRelationInfo, contextInfo);

        assertEquals(effectiveDate, created.getEffectiveDate());
        assertEquals(expirationDate, created.getExpirationDate());
        assertEquals(true, created.getIsCluRelationRequired());
        assertEquals("hello", created.getStateKey());
        assertEquals("CLU-1", created.getCluId());
        assertEquals("CLU-2", created.getRelatedCluId());
        assertEquals("luLuType.type1", created.getTypeKey());
        assertEquals("clucluAttrValue1", created.getAttributeValue("clucluAttrKey1"));
        assertEquals("clucluAttrValue2", created.getAttributeValue("clucluAttrKey2"));
        assertEquals("clucluAttrValue3", created.getAttributeValue("clucluAttrKey3"));
        assertNotNull(created.getId());
        assertNotNull(created.getMeta().getCreateTime());
        assertNotNull(created.getMeta().getVersionInd());

        created.getAttributes().remove(1);
        created.getAttributes().set(1, new AttributeInfo("clucluAttrKey3", "clucluAttrValue3-A"));
        created.getAttributes().add(new AttributeInfo("clucluAttrKey4", "clucluAttrValue4"));
        created.setCluId("CLU-2");
        created.setEffectiveDate(expirationDate);
        created.setExpirationDate(effectiveDate);
        created.setIsCluRelationRequired(false);
        created.setRelatedCluId("CLU-3");
        created.setStateKey("updated-hello");
        created.setTypeKey("luLuType.type2");

        CluCluRelationInfo updated = client.updateCluCluRelation(created.getId(), created, contextInfo);

        assertEquals(expirationDate, updated.getEffectiveDate());
        assertEquals(effectiveDate, updated.getExpirationDate());
        assertEquals(false, updated.getIsCluRelationRequired());
        assertEquals("updated-hello", updated.getStateKey());
        assertEquals("CLU-2", updated.getCluId());
        assertEquals("CLU-3", updated.getRelatedCluId());
        assertEquals("luLuType.type2", updated.getTypeKey());
        assertEquals("clucluAttrValue1", updated.getAttributeValue("clucluAttrKey1"));
        assertNull(updated.getAttributeValue("clucluAttrKey2"));
        assertEquals("clucluAttrValue3-A", updated.getAttributeValue("clucluAttrKey3"));
        assertEquals("clucluAttrValue4", updated.getAttributeValue("clucluAttrKey4"));
        assertNotNull(created.getId());
        assertNotNull(created.getMeta().getCreateTime());
        assertNotNull(created.getMeta().getVersionInd());

        // Test Delete
        try {
            client.getCluCluRelation(created.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            fail("Should not have thrown DoesNotExistException");
        }

        StatusInfo status = client.deleteCluCluRelation(created.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        try {
            client.getCluCluRelation(created.getId(), contextInfo);
            fail("Should have thrown DoesNotExistException");
        } catch (DoesNotExistException e) {

        }

        List<String> relatedCluIdsByCluId = client.getRelatedCluIdsByCluAndRelationType(
                "CLU-1", "luLuType.type1", contextInfo);

        assertEquals(2, relatedCluIdsByCluId.size());
        assertTrue(relatedCluIdsByCluId.contains("CLU-2"));
        assertTrue(relatedCluIdsByCluId.contains("CLU-3"));

        List<CluInfo> relatedClusByCluId = client.getRelatedClusByCluAndRelationType(
                "CLU-1", "luLuType.type1", contextInfo);
        assertEquals(2, relatedClusByCluId.size());
    }

    /* LuiService not used in CM     @Test
     public void testLuiLuiRelationCrud() throws Exception {

         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

         LuiLuiRelationInfo luiLuiRelationInfo = new LuiLuiRelationInfo();

         luiLuiRelationInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"));
         luiLuiRelationInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100101"));
         luiLuiRelationInfo.setState("hello");
         luiLuiRelationInfo.setType("goodbye");
         luiLuiRelationInfo.getAttributes().add(new AttributeInfo("luiluiAttrKey1","luiluiAttrValue1"));
         luiLuiRelationInfo.getAttributes().add(new AttributeInfo("luiluiAttrKey2","luiluiAttrValue2"));

         LuiLuiRelationInfo created = atpService.createLuiLuiRelation("LUI-1",
                 "LUI-2", "luLuType.type1", luiLuiRelationInfo, contextInfo);

         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"), created.getEffectiveDate());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100101"), created.getExpirationDate());
         assertEquals("hello", created.getState());
         assertEquals("luLuType.type1", created.getType());
         assertEquals("LUI-1", created.getLuiId());
         assertEquals("LUI-2", created.getRelatedLuiId());
         assertEquals("luiluiAttrValue1", created.getAttributeValue("luiluiAttrKey1"));
         assertEquals("luiluiAttrValue2", created.getAttributeValue("luiluiAttrKey2"));
         assertNotNull(created.getId());
         assertNotNull(created.getMetaInfo().getCreateTime());
         assertNotNull(created.getMetaInfo().getVersionInd());

         created.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20980101"));
         created.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190101"));
         created.setState("sawyer");
         created.setType("luLuType.type2");
         created.setLuiId("LUI-2");
         created.setRelatedLuiId("LUI-3");
         created.getAttributes().add(new AttributeInfo("luiluiAttrKey1", "UPluiluiAttrValue1"));
         created.getAttributes().remove("luiluiAttrKey2");
         created.getAttributes().add(new AttributeInfo("luiluiAttrKey3", "luiluiAttrValue3"));

         LuiLuiRelationInfo updated = atpService.updateLuiLuiRelation(created
                 .getId(), created, contextInfo);

         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20980101"), updated.getEffectiveDate());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20190101"), updated.getExpirationDate());
         assertEquals("sawyer", updated.getState());
         assertEquals("luLuType.type2", updated.getType());
         assertEquals("LUI-2", updated.getLuiId());
         assertEquals("LUI-3", updated.getRelatedLuiId());
         assertEquals("UPluiluiAttrValue1", updated.getAttributeValue("luiluiAttrKey1"));
         assertEquals("luiluiAttrValue3", updated.getAttributeValue("luiluiAttrKey3"));
         assertEquals(2, updated.getAttributes().size());
         assertEquals(created.getId(), updated.getId());
         assertNotNull(updated.getMetaInfo().getUpdateTime());

         try {
             updated = atpService.updateLuiLuiRelation(created.getId(), created, contextInfo);
             fail("Should have thrown VersionMismatchException");
         } catch (VersionMismatchException e) {
         }

         try {
             atpService.getLuiLuiRelation(created.getId(), contextInfo);
         } catch (DoesNotExistException e) {
             fail("Should not have thrown DoesNotExistException");
         }

         StatusInfo status = R1R2ConverterUtil.convert(atpService.deleteLuiLuiRelation(updated.getId(), contextInfo), StatusInfo.class);

         assertTrue(status.getIsSuccess());

         try {
             atpService.getLuiLuiRelation(created.getId(), contextInfo);
             fail("Should have thrown DoesNotExistException");
         } catch (DoesNotExistException e) {

         }

         // TestFind
         List<LuiLuiRelationInfo> relations = atpService
                 .getLuiLuiRelationsByLui("LUI-1", contextInfo);
         assertEquals(2, relations.size());
         relations = atpService.getLuiLuiRelationsByLui("LUI-2", contextInfo);
         assertEquals(1, relations.size());
         relations = atpService.getLuiLuiRelationsByLui("LUI-3", contextInfo);
         assertTrue(relations == null || relations.size() == 0);

         List<String> relatedLuiIdsByLuiId = atpService.getRelatedLuiIdsByLuiId(
                 "LUI-1", "luLuType.type1", contextInfo);
         assertEquals(2, relatedLuiIdsByLuiId.size());
         assertTrue(relatedLuiIdsByLuiId.contains("LUI-2"));
         assertTrue(relatedLuiIdsByLuiId.contains("LUI-3"));

         List<LuiInfo> relatedLuisByLuiId = atpService.getRelatedLuisByLuiId(
                 "LUI-1", "luLuType.type1", contextInfo);
         assertEquals(2, relatedLuisByLuiId.size());

     }

     @Test
     public void testGetLuisByIdList() throws DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<LuiInfo> luiInfos;
         try {
             luiInfos = atpService.getLuisByIds(null, contextInfo);
             fail("CluService.getLuiByIdList() did not throw MissingParameterException for null Lui ID");
         } catch (MissingParameterException mpe) {
         } catch (Exception e) {
             fail("CluService.getLuiByIdList() threw unexpected "
                     + e.getClass().getSimpleName() + " for null Lui ID");
         }
         luiInfos = atpService.getLuisByIds(Arrays.asList("Not a LUI ID",
                 "Another one that ain't"), contextInfo);
         assertTrue(luiInfos == null || luiInfos.size() == 0);

         luiInfos = atpService.getLuisByIds(Arrays.asList("LUI-1", "LUI-4"), contextInfo);
         Collections.sort(luiInfos, new Comparator<LuiInfo>() {
             @Override
             public int compare(LuiInfo o1, LuiInfo o2) {
                 return o1.getId().compareTo(o2.getId());
             }
         });
         assertEquals("CLU-1", luiInfos.get(0).getCluId());
         assertEquals("CLU-2", luiInfos.get(1).getCluId());
     }

     @Test
     public void testLuiCrud() throws AlreadyExistsException,
             DataValidationErrorException, DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException, PermissionDeniedException,
             ParseException, DependentObjectsExistException {

         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

         LuiInfo luiInfo;

         // Read
         try {
             luiInfo = atpService.getLui("notARealLui", contextInfo);
             fail("CluService.getLui() did not throw DoesNotExistException for non-existent Lui");
         } catch (DoesNotExistException dnee) {
         } catch (Exception e) {
             fail("CluService.getLui() threw unexpected "
                     + e.getClass().getSimpleName() + " for null Lui ID");
         }
         try {
             luiInfo = atpService.getLui(null, contextInfo);
             fail("CluService.getLui() did not throw MissingParameterException for null Lui ID");
         } catch (MissingParameterException mpe) {
         }
         luiInfo = atpService.getLui("LUI-1", contextInfo);
         assertEquals("CLU-1", luiInfo.getCluId());

         // Create
         luiInfo = new LuiInfo();

         luiInfo.setLuiCode("LUI Test Code");
         luiInfo.setMaxSeats(100);
         luiInfo.setState("Test Lui State");
         luiInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20101203"));
         luiInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20801231"));
         luiInfo.getAttributes().add(new AttributeInfo("luiAttrKey1", "luiAttrValue1"));
         luiInfo.getAttributes().add(new AttributeInfo("luiAttrKey2", "luiAttrValue2"));

         LuiInfo createdLui = atpService.createLui("CLU-2", "ATP-3", luiInfo, contextInfo);

         assertEquals("ATP-3", createdLui.getAtpId());
         assertEquals("LUI Test Code", createdLui.getLuiCode());
         assertEquals(100L, (long) createdLui.getMaxSeats());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20101203"), luiInfo.getEffectiveDate());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20801231"), luiInfo.getExpirationDate());
         assertEquals("CLU-2", createdLui.getCluId());
         assertEquals(2, createdLui.getAttributes().size());
         assertEquals("luiAttrValue1", createdLui.getAttributeValue(createdLui.getAttributes(),"luiAttrKey1"));
         assertEquals("luiAttrValue2", createdLui.getAttributeValue(createdLui.getAttributes(),"luiAttrKey2"));

         // update
         createdLui.setAtpId("ATP-2");
         createdLui.setCluId("CLU-1");
         createdLui.setLuiCode("LUI Test Code Update");
         createdLui.setMaxSeats(75);
         createdLui.setState("Test Lui State Update");
         createdLui.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20111203"));
         createdLui.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20811231"));
         createdLui.getAttributes().add(new AttributeInfo("luiAttrKey1", "luiAttrValue1Updated"));
         createdLui.getAttributes().add(new AttributeInfo("luiAttrKey2", "luiAttrValue2Updated"));

         LuiInfo updatedLui = null;
         try {
             updatedLui = atpService.updateLui(createdLui.getId(), createdLui, contextInfo);
         } catch (VersionMismatchException vme) {
             fail("CluService.updateLui() threw unexpected VersionMismatchException");
         }

         // confirm update worked
         assertTrue(null != updatedLui);
         assertEquals("ATP-2", updatedLui.getAtpId());
         assertEquals("CLU-1", updatedLui.getCluId());
         assertEquals("LUI Test Code Update", updatedLui.getLuiCode());
         assertEquals(75L, (long) createdLui.getMaxSeats());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20111203"), updatedLui.getEffectiveDate());
         assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20811231"), updatedLui.getExpirationDate());
         assertEquals(2, updatedLui.getAttributes().size());
         assertEquals("luiAttrValue1Updated", updatedLui.getAttributeValue(updatedLui.getAttributes(),"luiAttrKey1"));
         assertEquals("luiAttrValue2Updated", updatedLui.getAttributeValue(updatedLui.getAttributes(),"luiAttrKey2"));

         // optimistic locking working?
         try {
             atpService.updateLui(createdLui.getId(), createdLui, contextInfo);
             fail("CluService.updateLui did not throw expected VersionMismatchException");
         } catch (VersionMismatchException e) {
         }

         // delete what we created
         atpService.deleteLui(createdLui.getId(), contextInfo);
         // and try it again
         try {
             atpService.deleteLui(createdLui.getId(), contextInfo);
             fail("CluService.deleteLui() of previously-delete Lui did not throw expected DoesNotExistException");
         } catch (DoesNotExistException dnee) {
         }
     }

     @Test
     public void testGetLuiIdsByCluId() throws DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<String> luiIds = null;
         try {
             luiIds = atpService.getLuiIdsByCluId(null, contextInfo);
             fail("CluService.getLuiIdsByCluId() did not throw MissingParameterException for null Clu ID");
         } catch (MissingParameterException e) {
         }
         luiIds = atpService.getLuiIdsByCluId("CLU-1", contextInfo);

         Collections.sort(luiIds);

         assertTrue(null != luiIds);
         assertEquals(3, luiIds.size());

         assertEquals("LUI-1", luiIds.get(0));
         assertEquals("LUI-3", luiIds.get(2));
         luiIds = atpService.getLuiIdsByCluId("CLU-2", contextInfo);
         assertEquals(1, luiIds.size());
         luiIds = atpService.getLuiIdsByCluId("Non-existent Clu", contextInfo);
         assertTrue(null == luiIds || luiIds.size() == 0);
     }

     @Test
     public void testGetLuiIdsInAtpByCluId() throws DoesNotExistException,
             InvalidParameterException, OperationFailedException,
             MissingParameterException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<String> luiIds = null;
         try {
             luiIds = atpService.getLuiIdsInAtpByCluId(null, "ATP-1", contextInfo);
             fail("CluService.getLuiIdsInAtpByCluId() did not throw MissingParameterException for null Clu ID");
         } catch (MissingParameterException e) {
         }
         try {
             luiIds = atpService.getLuiIdsInAtpByCluId("CLU-1", null, contextInfo);
             fail("CluService.getLuiIdsInAtpByCluId() did not throw MissingParameterException for null AtpKey");
         } catch (MissingParameterException e) {
         }
         luiIds = atpService.getLuiIdsInAtpByCluId("CLU-1", "ATP-2", contextInfo);
         Collections.sort(luiIds);
         assertTrue(null != luiIds);
         assertEquals(2, luiIds.size());
         assertEquals("LUI-2", luiIds.get(0));
         assertEquals("LUI-3", luiIds.get(1));
         luiIds = atpService.getLuiIdsInAtpByCluId("CLU-1", "ATP-1", contextInfo);
         assertEquals(1, luiIds.size());
         luiIds = atpService.getLuiIdsInAtpByCluId("Non-existent Clu", "ATP-2", contextInfo);
         assertTrue(null == luiIds || luiIds.size() == 0);
         luiIds = atpService.getLuiIdsInAtpByCluId("CLU-2", "Non-existent ATP", contextInfo);
         assertTrue(null == luiIds || luiIds.size() == 0);
     }*/

    /* KSCM Unsupported in KSCM     @Test
     public void testGetLuLuRelationTypeInfo() throws OperationFailedException,
             DoesNotExistException, MissingParameterException, InvalidParameterException, PermissionDeniedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         TypeInfo luLuRelTypeInfo;

         try {
             atpService.getLuLuRelationType(null, contextInfo);
             fail("CluService.getLuLuRelationTypeInfo() did not throw MissingParameterException for null LuLuRelationType key");
         } catch (MissingParameterException e) {
         }
         luLuRelTypeInfo = atpService.getLuLuRelationType("luLuType.type1", contextInfo);
         assertEquals("bob", luLuRelTypeInfo.getName());
         luLuRelTypeInfo = atpService.getLuLuRelationType("luLuType.type2", contextInfo);
         assertEquals("my desc2", luLuRelTypeInfo.getDescr());
         assertEquals("rev name2", luLuRelTypeInfo.getName());
         assertEquals("rev desc2", luLuRelTypeInfo.getDescr());
         try {
             atpService.getLuLuRelationType("Non-existent LuLuRelationType", contextInfo);
             fail("CluService.getLuLuRelationTypeInfo() did not throw DoesNotExistException when retrieving non-existent LuLuRelationType");
         } catch (DoesNotExistException dnee) {
         }
     }*/

    /* KSCM Unsupported in KSCM    @Test
     public void testGetLuLuRelationTypeInfos() throws OperationFailedException,
             DoesNotExistException, MissingParameterException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<TypeInfo> luLuRelTypeInfos;
         luLuRelTypeInfos = atpService.getLuLuRelationTypes(contextInfo);
         Collections.sort(luLuRelTypeInfos,
                 new Comparator<LuLuRelationTypeInfo>() {
                     @Override
                     public int compare(LuLuRelationTypeInfo o1,
                             LuLuRelationTypeInfo o2) {
                         return o1.getId().compareTo(o2.getId());
                     }
                 });
         assertEquals(13, luLuRelTypeInfos.size());
         assertEquals("kuali.lu.lu.relation.type.hasCoreProgram", luLuRelTypeInfos.get(0).getKey());
     }*/

    /* LuiService not used in CM      @Test
     public void testUpdateLuiState() throws DataValidationErrorException,
             DoesNotExistException, InvalidParameterException,
             OperationFailedException, PermissionDeniedException,
             ParseException, AlreadyExistsException, MissingParameterException,
             DependentObjectsExistException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         try {
             atpService.updateLuiState(null, "Suspended", contextInfo);
             fail("CluService.updateLuiState() did not throw MissingParameterException for null Lui ID");
         } catch (MissingParameterException e) {
         }
         try {
             atpService.updateLuiState("LUI-1", null, contextInfo);
             fail("CluService.updateLuiState() did not throw MissingParameterException for null state");
         } catch (MissingParameterException e) {
         }

         // create a Lui whose state we'll update. Create a new one so its
         // MetaInfo gets created in prePersist()
         LuiInfo luiInfo = new LuiInfo();

         luiInfo.setLuiCode("LUI Test Code");
         luiInfo.setMaxSeats(100);
         luiInfo.setState("Approved");
         luiInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20101203"));
         luiInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20801231"));
         luiInfo.getAttributes().add(new AttributeInfo("luiAttrKey1", "luiAttrValue1"));
         luiInfo.getAttributes().add(new AttributeInfo("luiAttrKey2", "luiAttrValue2"));

         LuiInfo createdLui = atpService.createLui("CLU-2", "ATP-3", luiInfo, contextInfo);
         // make sure the db's in the state we expect
         assertEquals("Approved", createdLui.getState());

         // update and confirm it was updated
         LuiInfo updatedLui = atpService.updateLuiState(createdLui.getId(),
                 "Active", contextInfo);
         assertEquals("Active", updatedLui.getState());

         // and now explicitly retrieve it without a call to updateLuiState and
         // confirm same
         updatedLui = atpService.getLui(createdLui.getId(), contextInfo);
         assertEquals("Active", updatedLui.getState());

         // and delete it to keep db consistent for other tests
         atpService.deleteLui(updatedLui.getId(), contextInfo);
     }

     @Test
     public void testGetLuisByRelation() throws DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<LuiInfo> luis = atpService.getLuisByRelation("LUI-1", "luLuType.type1", contextInfo);
         assertTrue(luis == null || luis.size() == 0);
         luis = atpService.getLuisByRelation("LUI-2", "luLuType.type1", contextInfo);
         Collections.sort(luis, new Comparator<LuiInfo>() {
             @Override
             public int compare(LuiInfo o1, LuiInfo o2) {
                 return o1.getId().compareTo(o2.getId());
             }
         });
         assertEquals(1, luis.size());
         assertEquals("LUI-1", luis.get(0).getId());
     }

     @Test
     public void testGetLuiIdsByRelation() throws DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         List<String> luis = atpService.getLuiIdsByRelation("LUI-1",	"luLuType.type1", contextInfo);
         assertTrue(luis == null || luis.size() == 0);
         luis = atpService.getLuiIdsByRelation("LUI-2", "luLuType.type1", contextInfo);
         Collections.sort(luis);
         assertEquals(1, luis.size());
         assertEquals("LUI-1", luis.get(0));
     }*/

    @Test
    public void testOutcomeLO() throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException,
            ParseException, DataValidationErrorException,
            VersionMismatchException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluLoRelationInfo reltnInfo = new CluLoRelationInfo();
        reltnInfo.setCluId("CLU-1");
        reltnInfo.setLoId("LO-1");
        reltnInfo.setStateKey("FINAL");
        reltnInfo.setTypeKey("kuali.lu.lo.relation.type.includes");
        reltnInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20101203"));
        reltnInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20801231"));

        CluLoRelationInfo crReltnInfo = client.createCluLoRelation("CLU-1",
                "LO-1", "kuali.lu.lo.relation.type.includes", reltnInfo, contextInfo);

        assertEquals(crReltnInfo.getCluId(), "CLU-1");
        assertEquals(crReltnInfo.getLoId(), "LO-1");
        assertEquals(crReltnInfo.getStateKey(), "FINAL");
        assertEquals(crReltnInfo.getTypeKey(), "kuali.lu.lo.relation.type.includes");

        try {
            reltnInfo.setCluId("MISSING CLU");
            client.createCluLoRelation("MISSING CLU", "LO-1", "kuali.lu.lo.relation.type.includes",
                    reltnInfo, contextInfo);
            fail("Should have thrown DoesNotExistException");
        } catch (DoesNotExistException e) {

        }

        CluLoRelationInfo gtReltnInfo = client.getCluLoRelation(crReltnInfo
                .getId(), contextInfo);

        assertEquals(gtReltnInfo.getCluId(), "CLU-1");
        assertEquals(gtReltnInfo.getLoId(), "LO-1");
        assertEquals(gtReltnInfo.getStateKey(), "FINAL");
        assertEquals(gtReltnInfo.getTypeKey(), "kuali.lu.lo.relation.type.includes");

        CluLoRelationInfo reltnInfo1 = new CluLoRelationInfo();
        reltnInfo1.setCluId("CLU-1");
        reltnInfo1.setLoId("LO-2");
        reltnInfo1.setStateKey("FINAL");
        reltnInfo1.setTypeKey("kuali.lu.lo.relation.type.includes");
        reltnInfo1.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20101203"));
        reltnInfo1.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20801231"));

        CluLoRelationInfo crReltnInfo1 = client.createCluLoRelation("CLU-1",
                "LO-2", "kuali.lu.lo.relation.type.includes", reltnInfo1, contextInfo);

        List<CluLoRelationInfo> reltns = client.getCluLoRelationsByClu("CLU-1", contextInfo);
        assertEquals(2, reltns.size());

        List<CluLoRelationInfo> reltns1 = client.getCluLoRelationsByLo("LO-1", contextInfo);
        assertEquals(1, reltns1.size());
        assertEquals(reltns1.get(0).getCluId(), "CLU-1");

        // Test update relation
        crReltnInfo1.setStateKey("NEW");
        CluLoRelationInfo updateReltn = client.updateCluLoRelation(crReltnInfo1
                .getId(), crReltnInfo1, contextInfo);
        assertEquals("NEW", updateReltn.getStateKey());

        StatusInfo status = client.deleteCluLoRelation(crReltnInfo.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        status = client.deleteCluLoRelation(updateReltn.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

    }

    /* KSCM Unsupported in KSCM     @Test
     public void testResultUsageType() throws DoesNotExistException,
             InvalidParameterException, MissingParameterException,
             OperationFailedException, PermissionDeniedException {
         ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
         TypeInfo lrType = atpService.getResultUsageType("lrType.finalGrade", contextInfo);
         assertEquals("Final Grade", lrType.getName());
         List<TypeInfo> lrTypes = atpService.getResultUsageTypes(contextInfo);
         assertEquals(2, lrTypes.size());

     }*/

    @Test
    public void testGetClusByRelation() throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ParseException, VersionMismatchException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        List<CluInfo> clus = client
                .getClusByRelatedCluAndRelationType("CLU-1", "luLuType.type1", contextInfo);
        assertNotNull(clus);
        assertEquals(2, clus.size());

        clus = client.getClusByRelatedCluAndRelationType("CLUXX-2", "luLuType.type1", contextInfo);
        assertTrue(clus == null || clus.size() == 0);

        clus = client.getClusByRelatedCluAndRelationType("CLU-2", "luLuType.type1XX", contextInfo);
        assertTrue(clus == null || clus.size() == 0);

        try {
            clus = client.getClusByRelatedCluAndRelationType(null, "luLuType.type1XX", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            clus = client.getClusByRelatedCluAndRelationType("CLU-2", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }

        List<String> ids = client.getCluIdsByRelatedCluAndRelationType("CLU-2", "luLuType.type1", contextInfo);
        assertNotNull(ids);
        assertEquals(1, ids.size());

        ids = client.getCluIdsByRelatedCluAndRelationType("CLUXX-2", "luLuType.type1", contextInfo);
        assertTrue(null == ids || ids.size() == 0);

        ids = client.getCluIdsByRelatedCluAndRelationType("CLU-2", "luLuType.type1XX", contextInfo);
        assertTrue(null == ids || ids.size() == 0);

        try {
            ids = client.getCluIdsByRelatedCluAndRelationType(null, "luLuType.type1XX", contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            ids = client.getCluIdsByRelatedCluAndRelationType("CLU-2", null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testSearchForResults() throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ParseException, VersionMismatchException {
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>(
                0);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.clus");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());
        Collections.sort(clus.getRows(), new Comparator<SearchResultRowInfo>() {
            @Override
            public int compare(SearchResultRowInfo o1, SearchResultRowInfo o2) {
                return o1.getCells().get(0).getValue().compareTo(
                        o2.getCells().get(0).getValue());
            }
        });
        assertNotNull(clus);
        assertEquals(111, clus.getRows().size());
        SearchResultRowInfo result = clus.getRows().get(0);
        assertNotNull(result);

        List<SearchResultCellInfo> resultCells = result.getCells();
        assertNotNull(resultCells);
        assertEquals(3, resultCells.size());

        SearchResultCellInfo resultCell = resultCells.get(0);
        assertEquals("lu.resultColumn.cluId", resultCell.getKey());
        assertEquals("00000000-a389-4fd0-b349-1e649c20fd08", resultCell
                .getValue());
        resultCell = resultCells.get(1);
        assertEquals("lu.resultColumn.cluOfficialIdentifier.longName",
                resultCell.getKey());
        assertEquals("Advanced Applied Linear Algebra", resultCell.getValue());
    }

    @Test
    public void testSearchForClus() throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ParseException, VersionMismatchException {

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo typeParm = new SearchParamInfo();
        typeParm.setKey("lu.queryParam.luOptionalType");
        typeParm.getValues().add("kuali.lu.type.CreditCourse");
        queryParamValueList.add(typeParm);
        SearchParamInfo stateParm = new SearchParamInfo();
        stateParm.setKey("lu.queryParam.luOptionalState");
        stateParm.getValues().add("Approved");
        stateParm.getValues().add("Active");
        stateParm.getValues().add("Retired");
        queryParamValueList.add(stateParm);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());
        assertEquals(98, clus.getRows().size());
    }

    @Test
    public void testSearchCourseLevelRanges() throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo courseLevelsParam = new SearchParamInfo();
        courseLevelsParam.setKey("lu.queryParam.luOptionalCrsNoRange");
        courseLevelsParam.getValues().add("100 -200");
        queryParamValueList.add(courseLevelsParam);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());
        Collections.sort(clus.getRows(), new Comparator<SearchResultRowInfo>() {
            @Override
            public int compare(SearchResultRowInfo o1, SearchResultRowInfo o2) {
                return o1.getCells().get(0).getValue().compareTo(
                        o2.getCells().get(0).getValue());
            }
        });
        assertNotNull(clus);
        LOG.warn(clus.getRows().size());
    }

    @Test
    public void testCluValidation() throws ParseException, AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException, DependentObjectsExistException {

        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluInfo clu = new CluInfo();

        CluAccountingInfo accountingInfo = new CluAccountingInfo();
        accountingInfo.getAttributes().add(new AttributeInfo("AccountingAttrKey1", "AccountingAttrValue1"));
        accountingInfo.getAttributes().add(new AttributeInfo("AccountingAttrKey2", "AccountingAttrValue2"));
        clu.setAccountingInfo(accountingInfo);

        CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
        officialIdentifier.setTypeKey("kuali.lu.type.CreditCourse.identifier.official");
        officialIdentifier.setStateKey("active");
        officialIdentifier.setCode("offIdcode");
        officialIdentifier.setDivision("offIddivision");
        officialIdentifier.setLevel("offIdlevel");
        officialIdentifier.setLongName("offIdlongName");
        // ERROR: Short name should be less than 20 chars
        officialIdentifier.setShortName("offId-shortName-should-be-longer-than-twenty-characters");
        officialIdentifier.setVariation("offIdvariation");
        officialIdentifier.setSuffixCode("offIdsuffixcode");
        officialIdentifier.setOrgId("offIdorgid");
        clu.setOfficialIdentifier(officialIdentifier);

        CluIdentifierInfo cluId1 = new CluIdentifierInfo();
        cluId1.setTypeKey("kuali.lu.type.CreditCourse.identifier.cross-listed");
        cluId1.setStateKey("Sctive");
        cluId1.setCode("cluIdonecode");
        cluId1.setDivision("cluIdonedivision");
        cluId1.setLevel("cluIdonelevel");
        cluId1.setLongName("cluIdonelongName");
        cluId1.setShortName("cluIdoneshortName");
        // ERROR: Min length 3
        // ERROR: Only numbers allowed
        cluId1.setSuffixCode("cl");
        cluId1.setOrgId("cluIdoneorgid");

        clu.getAlternateIdentifiers().add(cluId1);

        CluIdentifierInfo cluId2 = new CluIdentifierInfo();
        // Check for different type validations
        cluId2.setTypeKey("kuali.lu.type.CreditCourse.identifier.version");
        cluId2.setStateKey("Sctive");
        cluId2.setCode("cluIdtwocode");
        cluId2.setDivision("cluIdtwodivision");
        cluId2.setLevel("cluIdtwolevel");
        cluId2.setLongName("cluIdtwolongName");
        cluId2.setShortName("cluIdtwoshortName");
        // ERROR: Should be uppper case
        // ERROR: should be of size 1
        cluId2.setVariation("ab");
        cluId2.setSuffixCode("cluIdtwosuffixcode");
        // ERROR OrgId required
        // cluId.setOrgId();
        clu.getAlternateIdentifiers().add(cluId2);

        clu.getAttributes().add(new AttributeInfo("cluAttrKey1", "cluAttrValue1"));
        clu.getAttributes().add(new AttributeInfo("cluAttrKey2", "cluAttrValue2"));

        clu.setCanCreateLui(true);

        // ERROR non negative integer
        clu.setDefaultEnrollmentEstimate(-545);
        clu.setDefaultMaximumEnrollment(999);

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>DESC FORMATTED</p>");
        desc.setPlain("DESC PLAIN");
        clu.setDescr(desc);

        clu.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100203"));
        clu.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21001231"));

        clu.setIsEnrollable(true);

        AffiliatedOrgInfo aforg = new AffiliatedOrgInfo();
        aforg.setOrgId("AFFORGone");
        aforg.setPercentage(35l);

        AffiliatedOrgInfo aforg1 = new AffiliatedOrgInfo();
        aforg1.setOrgId("AFForgtwo");
        aforg1.setPercentage(65l);

        List<AffiliatedOrgInfo> affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        affiliatedOrgs.add(aforg);
        affiliatedOrgs.add(aforg1);

        CurrencyAmountInfo ca = new CurrencyAmountInfo();
        ca.setCurrencyQuantity(100);
        ca.setCurrencyTypeKey("DLLR");

        CurrencyAmountInfo ca1 = new CurrencyAmountInfo();
        ca.setCurrencyQuantity(200);
        ca.setCurrencyTypeKey("DLLR");

        List<CurrencyAmountInfo> caList = new ArrayList<CurrencyAmountInfo>();
        caList.add(ca);

        List<CurrencyAmountInfo> caList1 = new ArrayList<CurrencyAmountInfo>();
        caList.add(ca);
        caList.add(ca1);

        RichTextInfo cfRecDesc = new RichTextInfo();
        cfRecDesc.setPlain("Clu Fee Record");

        CluFeeRecordInfo feeRec = new CluFeeRecordInfo();
        feeRec.setAffiliatedOrgs(affiliatedOrgs);
        feeRec.setFeeAmounts(caList);
        feeRec.setFeeType("FEE-TYPE-X");
        feeRec.setRateType("RATE-TYPE-X");
        feeRec.setDescr(cfRecDesc);

        CluFeeRecordInfo feeRec1 = new CluFeeRecordInfo();
        feeRec1.setAffiliatedOrgs(affiliatedOrgs);
        feeRec1.setFeeAmounts(caList1);
        feeRec1.setFeeType("FEE-TYPE-Y");
        feeRec1.setRateType("RATE-TYPE-Y");

        List<CluFeeRecordInfo> feeRecList = new ArrayList<CluFeeRecordInfo>();
        feeRecList.add(feeRec);
        feeRecList.add(feeRec1);

        RichTextInfo cfDesc = new RichTextInfo();
        cfDesc.setPlain("Clu Fee");

        CluFeeInfo feeInfo = new CluFeeInfo();
        feeInfo.getAttributes().add(new AttributeInfo("FeeAttrKey1", "FeeAttrValue1"));
        feeInfo.getAttributes().add(new AttributeInfo("FeeAttrKey2", "FeeAttrValue2"));
        feeInfo.setCluFeeRecords(feeRecList);
        feeInfo.setDescr(cfDesc);
        clu.setFeeInfo(feeInfo);

        clu.setIsHasEarlyDropDeadline(true);

        clu.setIsHazardousForDisabledStudents(true);

        CluInstructorInfo primaryInstructor = new CluInstructorInfo();
        primaryInstructor.setOrgId("EXTorgIdone");
        primaryInstructor.setPersonId("EXTpersonIdone");
        primaryInstructor.getAttributes().add(new AttributeInfo("PrimaryInstAttrKey1", "PrimaryInstAttrValue1"));
        primaryInstructor.getAttributes().add(new AttributeInfo("PrimaryInstAttrKey2", "PrimaryInstAttrValue2"));
        clu.setPrimaryInstructor(primaryInstructor);

        CluInstructorInfo instructor1 = new CluInstructorInfo();
        instructor1.setOrgId("EXTorgIdtwo");
        instructor1.setPersonId("EXTpersonIdtwo");
        instructor1.getAttributes().add(new AttributeInfo("Inst1AttrKey1", "Inst1AttrValue1"));
        instructor1.getAttributes().add(new AttributeInfo("Inst1AttrKey2", "Inst1AttrValue2"));
        clu.getInstructors().add(instructor1);

        CluInstructorInfo instructor2 = new CluInstructorInfo();
        instructor2.setOrgId("EXTorgIdthree");
        instructor2.setPersonId("EXTpersonIdthree");
        instructor2.getAttributes().add(new AttributeInfo("Inst2AttrKey1", "Inst2AttrValue1"));
        instructor2.getAttributes().add(new AttributeInfo("Inst2AttrKey2", "Inst2AttrValue2"));
        clu.getInstructors().add(instructor2);

        LuCodeInfo luCode1 = new LuCodeInfo();
        luCode1.setId("luCode1.key");
        RichTextInfo luCode1Desc = new RichTextInfo();
        luCode1Desc.setPlain("luCode1desc");
        luCode1.setDescr(luCode1Desc);
        luCode1.setValue("luCode1value");
        luCode1.getAttributes().add(new AttributeInfo("luCode1AttrKey1", "luCode1AttrValue1"));
        luCode1.getAttributes().add(new AttributeInfo("luCode1AttrKey2", "luCode1AttrValue2"));
        clu.getLuCodes().add(luCode1);

        LuCodeInfo luCode2 = new LuCodeInfo();
        luCode2.setId("luCode2.key");
        RichTextInfo luCodetwodesc = new RichTextInfo();
        luCodetwodesc.setPlain("luCodetwodesc");
        luCode2.setDescr(luCodetwodesc);
        luCode2.setValue("luCodetwovalue");
        luCode2.getAttributes().add(new AttributeInfo("luCode2AttrKey1", "luCode2AttrValue1"));
        luCode2.getAttributes().add(new AttributeInfo("luCode2AttrKey2", "luCode2AttrValue2"));
        clu.getLuCodes().add(luCode2);

        RichTextInfo marketingDesc = new RichTextInfo();
        marketingDesc.setFormatted("<p>marketingDesc FORMATTED</p>");
        marketingDesc.setPlain("marketingDesc PLAIN");

        clu.setNextReviewPeriod("nextReviewPeriod");

        clu.getOfferedAtpTypes().add("offeredAtpType1");
        clu.getOfferedAtpTypes().add("offeredAtpType2");


        CluInstructorInfo pubPrimaryInstructor = new CluInstructorInfo();
        pubPrimaryInstructor.setOrgId("EXTorgId");
        pubPrimaryInstructor.setPersonId("EXTpersonId");
        pubPrimaryInstructor.getAttributes().add(new AttributeInfo("PubPrimaryInstAttrKey1", "PubPrimaryInstAttrValue1"));
        pubPrimaryInstructor.getAttributes().add(new AttributeInfo("PubPrimaryInstAttrKey2", "PubPrimaryInstAttrValue2"));

        CluInstructorInfo pubInstructor1 = new CluInstructorInfo();
        pubInstructor1.setOrgId("EXTorgIdtwo");
        pubInstructor1.setPersonId("EXT-personId-two");
        pubInstructor1.getAttributes().add(new AttributeInfo("PubInst1AttrKey1", "PubInst1AttrValue1"));
        pubInstructor1.getAttributes().add(new AttributeInfo("PubInst1AttrKey2", "PubInst1AttrValue2"));

        CluInstructorInfo pubInstructor2 = new CluInstructorInfo();
        pubInstructor2.setOrgId("EXTorgIdthree");
        pubInstructor2.setPersonId("EXTpersonIdthree");
        pubInstructor2.getAttributes().add(new AttributeInfo("PubInst2AttrKey1", "PubInst2AttrValue1"));
        pubInstructor2.getAttributes().add(new AttributeInfo("PubInst2AttrKey2", "PubInst2AttrValue2"));

        clu.setReferenceURL("http://student.kuali.org/clus");

        TimeAmountInfo stdDuration = new TimeAmountInfo();
        stdDuration.setAtpDurationTypeKey("EXTstdDurationId");
        stdDuration.setTimeQuantity(new Integer(7867));
        clu.setStdDuration(stdDuration);

        createCampusLocationList(clu);

        createIntensity(clu);

        createAccreditationList(clu);

        createAdminOrgs(clu);

        clu.setTypeKey("kuali.lu.type.CreditCourse");
        clu.setStateKey("template");

        List<ValidationResultInfo> valerros = client.validateClu("SYSTEM", clu, contextInfo);

        assertEquals(2, valerros.size());
    }

    @Test
    public void testAddCluToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        StatusInfo status = client.addCluToCluSet("CLU-1", createdCluSet.getId(), contextInfo);
        CluSetInfo getCluSetInfo = client.getCluSet(createdCluSet.getId(), contextInfo);

        assertTrue(status.getIsSuccess());
        assertEquals(1, getCluSetInfo.getCluIds().size());
        assertEquals("CLU-1", getCluSetInfo.getCluIds().get(0));
    }

    @Test
    public void testAddCluToCluSet_DuplicateCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        StatusInfo status = client.addCluToCluSet("CLU-1", createdCluSet.getId(), contextInfo);
        assertTrue(status.getIsSuccess());

        status = client.addCluToCluSet("CLU-1", createdCluSet.getId(), contextInfo);
        assertFalse(status.getIsSuccess());
    }

    @Test
    public void testAddClusToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        // Somehow cannot add 2 CLUs in sequence (JTA rollback exception) but adding a single CLU works
        //CluInfo clu1 = createCluInfo();
        //CluInfo clu2 = createCluInfo();
        //CluInfo createdClu1 = atpService.createClu("luType.shell.course", clu1);
        //CluInfo createdClu2 = atpService.createClu("luType.shell.course", clu2);
        //List<String> cluIdList = Arrays.asList(new String[] {createdClu1.getId(), createdClu2.getId()});

        List<String> cluIdList = Arrays.asList(new String[]{"CLU-1", "CLU-2", "CLU-3", "CLU-4"});

        StatusInfo status = client.addClusToCluSet(cluIdList, createdCluSet.getId(), contextInfo);
        CluSetInfo getCluSetInfo = client.getCluSet(createdCluSet.getId(), contextInfo);
        assertTrue(status.getIsSuccess());
        assertEquals(4, getCluSetInfo.getCluIds().size());
        assertTrue(getCluSetInfo.getCluIds().contains("CLU-1"));
        assertTrue(getCluSetInfo.getCluIds().contains("CLU-2"));
        assertTrue(getCluSetInfo.getCluIds().contains("CLU-3"));
        assertTrue(getCluSetInfo.getCluIds().contains("CLU-4"));
    }

    @Test
    public void testAddClusToCluSet_InvalidCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList = Arrays.asList(new String[]{"CLU-1", "CLU-2", "CLU-INVALID-ID", "CLU-4"});

        try {
            client.addClusToCluSet(cluIdList, createdCluSet.getId(), contextInfo);
            fail("Adding a non-existent CLU (id='CLU-INVALID-ID') to CluSet should have failed");
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddClusToCluSet_DuplicateCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList = Arrays.asList(new String[]{"CLU-1", "CLU-2", "CLU-2", "CLU-4"});

        StatusInfo status = client.addClusToCluSet(cluIdList, createdCluSet.getId(), contextInfo);
        assertEquals("CluSet already contains Clu (id='CLU-2')", status.getMessage());
    }

    @Test
    public void testAddClusToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        List<String> cluIdList = Arrays.asList(new String[]{"CLU-1", "CLU-2", "CLU-3", "CLU-4"});

        try {
            client.addClusToCluSet(cluIdList, "CLUSET-INVALID-ID", contextInfo);
            fail("Adding CLUs to a non-existent CluSet (id='CLUSET-INVALID-ID') should have failed");
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        StatusInfo status = client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1", contextInfo);
        CluSetInfo getCluSetInfo = client.getCluSet(createdCluSet.getId(), contextInfo);

        assertTrue(status.getIsSuccess());
        assertEquals(1, getCluSetInfo.getCluSetIds().size());
        assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-1"));
    }

    @Test
    public void testAddCluSetToCluSet_CircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        try {
            client.addCluSetToCluSet(createdCluSet.getId(), createdCluSet.getId(), contextInfo);
            fail("Adding a CluSet to itself should have failed");
        } catch (CircularRelationshipException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetToCluSet_NestedCircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);
        CluSetInfo createdCluSet2 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);
        CluSetInfo createdCluSet3 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        client.addCluSetToCluSet(createdCluSet3.getId(), createdCluSet1.getId(), contextInfo);
        client.addCluSetToCluSet(createdCluSet2.getId(), createdCluSet3.getId(), contextInfo);

        try {
            client.addCluSetToCluSet(createdCluSet1.getId(), createdCluSet2.getId(), contextInfo);
            fail("Adding CluSet should have thrown a CircularRelationshipException");
        } catch (CircularRelationshipException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetToCluSet_DuplicateCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        StatusInfo status = client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1", contextInfo);
        assertTrue(status.getIsSuccess());

        try {
            client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1", contextInfo);
            fail("Adding a duplicate CluSet (id='CLUSET-1') to CluSet should have failed");
        } catch (OperationFailedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        try {
            client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-INVALID-ID", contextInfo);
            fail("Adding a non-existent CluSet (id='CLUSET-INVALID-ID') to CluSet should have failed");
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetsToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList = Arrays.asList(new String[]{"CLUSET-1", "CLUSET-2", "CLUSET-3", "CLUSET-4"});

        StatusInfo status = client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList, contextInfo);
        CluSetInfo getCluSetInfo = client.getCluSet(createdCluSet.getId(), contextInfo);

        assertTrue(status.getIsSuccess());
        assertEquals(4, getCluSetInfo.getCluSetIds().size());
        assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-1"));
        assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-2"));
        assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-3"));
        assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-4"));
    }

    @Test
    public void testAddCluSetsToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList = Arrays.asList(new String[]{"CLUSET-1", "CLUSET-INVALID-ID", "CLUSET-3", "CLUSET-4"});

        try {
            client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList, contextInfo);
            fail("Adding a non-existent CluSet (id='CLUSET-INVALID-ID') to CluSet should have failed");
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetsToCluSet_DuplicateCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList = Arrays.asList(new String[]{"CLUSET-1", "CLUSET-2", "CLUSET-3", "CLUSET-2"});

        try {
            client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList, contextInfo);
            fail("Adding a duplicate CluSet (id='CLUSET-2') to CluSet should have failed");
        } catch (OperationFailedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetsToCluSet_CircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);
        // Adding createdCluSet to itself
        List<String> cluIdList = Arrays.asList(new String[]{"CLUSET-1", "CLUSET-2", createdCluSet.getId(), "CLUSET-4"});

        try {
            client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList, contextInfo);
            fail("Adding a CluSet to itself should have failed");
        } catch (CircularRelationshipException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddCluSetsToCluSet_NestedCircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);
        CluSetInfo createdCluSet2 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);
        CluSetInfo createdCluSet3 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSetInfo(), contextInfo);

        List<String> cluIdList1 = Arrays.asList(new String[]{"CLUSET-1", "CLUSET-2", createdCluSet1.getId()});
        client.addCluSetsToCluSet(createdCluSet3.getId(), cluIdList1, contextInfo);

        // Adding createdCluSet to itself
        List<String> cluIdList2 = Arrays.asList(new String[]{"CLUSET-1", createdCluSet3.getId()});
        client.addCluSetsToCluSet(createdCluSet2.getId(), cluIdList2, contextInfo);

        try {
            List<String> cluIdList3 = Arrays.asList(new String[]{createdCluSet2.getId(),});
            client.addCluSetsToCluSet(createdCluSet1.getId(), cluIdList3, contextInfo);
            fail("Adding CluSet should have thrown a CircularRelationshipException");
        } catch (CircularRelationshipException e) {
            assertTrue(true);
        }
    }


    @Test
    public void testCreateDynamicCluSet_Simple() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.clus");

        cluSet.setMembershipQuery(query);
        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);

        assertNotNull(createdCluSet);
        assertNotNull(createdCluSet.getMembershipQuery());
        assertNotNull(createdCluSet.getMembershipQuery().getId());
        assertNotNull(createdCluSet.getMembershipQuery().getSearchTypeKey());
        assertEquals(query.getSearchTypeKey(), createdCluSet.getMembershipQuery().getSearchTypeKey());
        assertNotNull(createdCluSet.getMembershipQuery().getQueryParamValues());
        assertNotNull(createdCluSet.getCluIds());
        assertEquals(107, createdCluSet.getCluIds().size());
    }

    private MembershipQueryInfo getMembershipQueryInfo() {
        List<org.kuali.student.r2.core.search.dto.SearchParamInfo> queryParamValueList = new ArrayList<org.kuali.student.r2.core.search.dto.SearchParamInfo>();
        org.kuali.student.r2.core.search.dto.SearchParamInfo sp1 = new org.kuali.student.r2.core.search.dto.SearchParamInfo();
        sp1.setKey("lu.queryParam.startsWith.cluCode");
        sp1.setValues(Arrays.asList("AAST"));
        queryParamValueList.add(sp1);
        org.kuali.student.r2.core.search.dto.SearchParamInfo sp2 = new org.kuali.student.r2.core.search.dto.SearchParamInfo();
        sp2.setKey("lu.queryParam.cluState");
        sp2.setValues(Arrays.asList("Active"));
        queryParamValueList.add(sp2);

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.cluByCodeAndState");
        query.setQueryParamValues(queryParamValueList);

        return query;
    }

    // KSCM-626 TODO SearchParamInfo mismatch between MembershipInfoQuery and SearchRequestInfo
    @Test
    public void testCreateDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();

        MembershipQueryInfo query = getMembershipQueryInfo();
        cluSet.setMembershipQuery(query);

        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);

        assertNotNull(createdCluSet);
        assertNotNull(createdCluSet.getMembershipQuery());
        assertNotNull(createdCluSet.getMembershipQuery().getId());
        assertNotNull(createdCluSet.getMembershipQuery().getSearchTypeKey());
        assertEquals(query.getSearchTypeKey(), createdCluSet.getMembershipQuery().getSearchTypeKey());
        assertNotNull(createdCluSet.getMembershipQuery().getQueryParamValues());
        assertEquals(query.getQueryParamValues().size(), createdCluSet.getMembershipQuery().getQueryParamValues().size());
        assertNotNull(createdCluSet.getCluIds());
        assertEquals(10, createdCluSet.getCluIds().size());

        CluSetInfo getCluSet = client.getCluSet(createdCluSet.getId(), contextInfo);

        assertNotNull(getCluSet);
        assertNotNull(getCluSet.getMembershipQuery());
        assertNotNull(getCluSet.getMembershipQuery().getId());
        assertNotNull(getCluSet.getMembershipQuery().getSearchTypeKey());
        assertEquals(query.getSearchTypeKey(), getCluSet.getMembershipQuery().getSearchTypeKey());
        assertNotNull(getCluSet.getMembershipQuery().getQueryParamValues());
        assertEquals(query.getQueryParamValues().size(), getCluSet.getMembershipQuery().getQueryParamValues().size());
        assertNotNull(getCluSet.getCluIds());
        assertEquals(10, getCluSet.getCluIds().size());
    }

    @Test
    public void testCreateCluSet_InvalidCluSet1() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();
        cluSet.getCluIds().add("CLU-1");

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.clus");

        cluSet.setMembershipQuery(query);

        try {
            client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
            fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CLUs and Dynamic CluSets into one CluSet");
        } catch (UnsupportedActionException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testCreateCluSet_InvalidCluSet2() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();
        cluSet.getCluSetIds().add("CLUSET-1");

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.clus");

        cluSet.setMembershipQuery(query);

        try {
            client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
            fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CluSets and Dynamic CluSets into one CluSet");
        } catch (UnsupportedActionException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testCreateCluSet_InvalidCluSet3() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();
        cluSet.getCluIds().add("CLU-1");
        cluSet.getCluSetIds().add("CLUSET-1");

        try {
            client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
            fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CLUs and CluSets into one CluSet");
        } catch (UnsupportedActionException e) {
            assertTrue(true);
        }

    }

    // KSCM-626 TODO SearchParamInfo mismatch between MembershipInfoQuery and SearchRequestInfo
    @Test
    public void testGetDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();

        MembershipQueryInfo query = getMembershipQueryInfo();
        cluSet.setMembershipQuery(query);
        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
        assertNotNull(createdCluSet);
        assertNotNull(createdCluSet.getCluIds());

        CluSetInfo getCluSet = client.getCluSet(createdCluSet.getId(), contextInfo);

        assertNotNull(getCluSet);
        assertNotNull(getCluSet.getMembershipQuery());
        assertNotNull(getCluSet.getMembershipQuery().getId());
        assertNotNull(getCluSet.getMembershipQuery().getSearchTypeKey());
        assertEquals(query.getSearchTypeKey(), getCluSet.getMembershipQuery().getSearchTypeKey());
        assertNotNull(getCluSet.getMembershipQuery().getQueryParamValues());
        assertEquals(query.getQueryParamValues().size(), getCluSet.getMembershipQuery().getQueryParamValues().size());
        assertEquals(createdCluSet.getCluIds().size(), getCluSet.getCluIds().size());
        assertNotNull(getCluSet.getCluIds());
        assertEquals(10, getCluSet.getCluIds().size());
    }

    @Test
    public void testGetDynamicCluSet_Simple() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.clus");

        cluSet.setMembershipQuery(query);
        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
        assertNotNull(createdCluSet);
        assertNotNull(createdCluSet.getCluIds());

        CluSetInfo getCluSet = client.getCluSet(createdCluSet.getId(), contextInfo);
        assertNotNull(getCluSet);
        assertNotNull(getCluSet.getCluIds());
        assertEquals(createdCluSet.getCluIds().size(), getCluSet.getCluIds().size());
    }

    @Test
    public void testGetCluSetTreeView() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetTreeViewInfo treeView = client.getCluSetTreeView("CLUSET-2", contextInfo);
        assertNotNull(treeView);
        assertEquals(2, treeView.getCluSets().size());

        CluSetTreeViewInfo cluSet = treeView.getCluSets().get(1);
        assertNotNull(cluSet);
        assertEquals("CLUSET-4", cluSet.getId());
        assertEquals(2, cluSet.getClus().size());

        CluInfo clu = cluSet.getClus().get(1);
        assertNotNull(clu);
        assertEquals("CLU-3", clu.getId());
    }

    @Test
    public void testGetCluSetTreeView_dynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DoesNotExistException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet = createCluSetInfo();

        MembershipQueryInfo query = new MembershipQueryInfo();
        query.setSearchTypeKey("lu.search.clus");

        cluSet.setMembershipQuery(query);
        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet, contextInfo);
        assertNotNull(createdCluSet);
        assertNotNull(createdCluSet.getCluIds());

        CluSetTreeViewInfo treeView = client.getCluSetTreeView(createdCluSet.getId(), contextInfo);
        assertNotNull(treeView);
        assertEquals(createdCluSet.getCluIds().size(), treeView.getClus().size());
    }

    @Test
    public void testGetCluSetTreeView_invalidCluSet()
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        try {
            client.getCluSetTreeView("CLUSET-XX", contextInfo);
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCluSetTreeView_nullCluSet()
            throws DoesNotExistException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        try {
            client.getCluSetTreeView(null, contextInfo);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
    }

    // KSCM-626 TODO SearchParamInfo mismatch between MembershipInfoQuery and SearchRequestInfo
    @Test
    public void testUpdateDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, UnsupportedActionException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo cluSet1 = createCluSetInfo();

        //Create clu set
        ArrayList<org.kuali.student.r2.core.search.dto.SearchParamInfo> queryParamValueList = new ArrayList<org.kuali.student.r2.core.search.dto.SearchParamInfo>();
        MembershipQueryInfo query1 = new MembershipQueryInfo();
        query1.setSearchTypeKey("lu.search.clus");
        query1.setQueryParamValues(queryParamValueList);

        cluSet1.setMembershipQuery(query1);
        // Version 0
        CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.CreditCourse", cluSet1, contextInfo);
        // createdCluSet should be version 1 but is 0

        //Update clu set
        MembershipQueryInfo query2 = getMembershipQueryInfo();

        createdCluSet.setMembershipQuery(query2);

        // Dynamic CluSet so we can't have any CluSet ids or Clu ids
        createdCluSet.getCluSetIds().clear();
        createdCluSet.getCluIds().clear();
        CluSetInfo updatedCluSet = client.updateCluSet(createdCluSet.getId(), createdCluSet, contextInfo);

        assertNotNull(updatedCluSet);
        assertNotNull(updatedCluSet.getCluIds());
        assertEquals(10, updatedCluSet.getCluIds().size());
        assertTrue(updatedCluSet.getCluSetIds().isEmpty());
    }

    @Test
    public void testUpdateCluSet_VersionMismatch() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        createdCluSet1.getCluSetIds().add("CLUSET-1");
        createdCluSet1.getCluSetIds().add("CLUSET-2");

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);
        assertEquals(2, updatedCluSet1.getCluSetIds().size());

        try {
            client.updateCluSet(updatedCluSet1.getId(), createdCluSet1, contextInfo);
            fail("Should have thrown VersionMismatchException.");
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testUpdateCluSet_ClearCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluSetIds().add("CLUSET-1");
        createCluSet.getCluSetIds().add("CLUSET-2");
        createCluSet.getCluSetIds().add("CLUSET-3");

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(3, createdCluSet1.getCluSetIds().size());

        createdCluSet1.getCluSetIds().clear();

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);
        assertEquals(0, updatedCluSet1.getCluSetIds().size());

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);
        assertEquals(0, getCluSet1.getCluSetIds().size());
    }

    @Test
    public void testUpdateCluSet_AddCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(0, createdCluSet1.getCluSetIds().size());

        createdCluSet1.getCluSetIds().add("CLUSET-1");
        createdCluSet1.getCluSetIds().add("CLUSET-2");

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);
        assertEquals(2, updatedCluSet1.getCluSetIds().size());

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);
        assertEquals(2, getCluSet1.getCluSetIds().size());
        assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-1"));
        assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-2"));
    }

    @Test
    public void testUpdateCluSet_removeCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluSetIds().add("CLUSET-1");
        createCluSet.getCluSetIds().add("CLUSET-2");
        createCluSet.getCluSetIds().add("CLUSET-3");

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(3, createdCluSet1.getCluSetIds().size());

        createdCluSet1.getCluSetIds().remove("CLUSET-2");

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);
        assertEquals(2, updatedCluSet1.getCluSetIds().size());

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);
        assertEquals(2, getCluSet1.getCluSetIds().size());
        assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-1"));
        assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-3"));
    }

    @Test
    public void testUpdateCluSet_ClearClus() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluIds().add("CLU-1");
        createCluSet.getCluIds().add("CLU-2");
        assertEquals(2, createCluSet.getCluIds().size());

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(2, createdCluSet1.getCluIds().size());

        assertNotNull(createdCluSet1);
        assertNotNull(createdCluSet1.getCluIds());
        assertEquals(2, createdCluSet1.getCluIds().size());

        // Remove all CLUs
        createdCluSet1.getCluIds().clear();

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);

        assertNotNull(updatedCluSet1);
        assertNotNull(updatedCluSet1.getCluIds());
        assertEquals(0, updatedCluSet1.getCluIds().size());

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);

        assertNotNull(getCluSet1);
        assertNotNull(getCluSet1.getCluIds());
        assertEquals(0, getCluSet1.getCluIds().size());
    }

    @Test
    public void testUpdateCluSet_AddClu() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluIds().add("CLU-1");
        createCluSet.getCluIds().add("CLU-2");
        assertEquals(2, createCluSet.getCluIds().size());

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(2, createdCluSet1.getCluIds().size());

        createdCluSet1.getCluIds().add("CLU-3");

        assertNotNull(createdCluSet1);
        assertNotNull(createdCluSet1.getCluIds());
        assertEquals(3, createdCluSet1.getCluIds().size());

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);

        assertNotNull(updatedCluSet1);
        assertNotNull(updatedCluSet1.getCluIds());
        assertEquals(3, updatedCluSet1.getCluIds().size());

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);

        assertNotNull(getCluSet1);
        assertNotNull(getCluSet1.getCluIds());
        assertEquals(3, getCluSet1.getCluIds().size());
    }

    @Test
    public void testUpdateCluSet_RemoveClu() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluSetInfo createCluSet = createCluSetInfo();
        createCluSet.getCluIds().add("CLU-1");
        createCluSet.getCluIds().add("CLU-2");
        assertEquals(2, createCluSet.getCluIds().size());

        CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.CreditCourse", createCluSet, contextInfo);
        assertEquals(2, createdCluSet1.getCluIds().size());

        createdCluSet1.getCluIds().remove("CLU-1");

        assertNotNull(createdCluSet1);
        assertNotNull(createdCluSet1.getCluIds());
        assertEquals(1, createdCluSet1.getCluIds().size());

        CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1, contextInfo);

        assertNotNull(updatedCluSet1);
        assertNotNull(updatedCluSet1.getCluIds());
        assertEquals(1, updatedCluSet1.getCluIds().size());
        assertTrue(updatedCluSet1.getCluIds().contains("CLU-2"));

        CluSetInfo getCluSet1 = client.getCluSet(updatedCluSet1.getId(), contextInfo);

        assertNotNull(getCluSet1);
        assertNotNull(getCluSet1.getCluIds());
        assertEquals(1, getCluSet1.getCluIds().size());
    }

    @Test
    public void testCreateCluResult() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        contextInfo.setPrincipalId("123");

        CluResultInfo dto = new CluResultInfo();
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("Plain description");
        dto.setDescr(desc);
        dto.setCluId("CLU-1");
        dto.setStateKey("active");
        dto.setTypeKey("kuali.resultType.gradeCourseResult");
        dto.setEffectiveDate(new Date());
        dto.setExpirationDate(new Date());

        CluResultInfo cluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto, contextInfo);

        assertNotNull(cluResult);
        assertNotNull(cluResult.getDescr());
        assertEquals(dto.getDescr().getPlain(), cluResult.getDescr().getPlain());
        assertNotNull(cluResult.getId());
        assertNotNull(cluResult.getCluId());
        assertEquals(dto.getCluId(), cluResult.getCluId());
        assertNotNull(cluResult.getTypeKey());
        assertEquals(dto.getTypeKey(), cluResult.getTypeKey());
        assertEquals(dto.getEffectiveDate(), cluResult.getEffectiveDate());
        assertEquals(dto.getExpirationDate(), cluResult.getExpirationDate());
    }

    @Test
    public void testUpdateCluResult() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        contextInfo.setPrincipalId("123");

        CluResultInfo dto = new CluResultInfo();
        RichTextInfo desc1 = new RichTextInfo();
        desc1.setPlain("Plain description");
        dto.setDescr(desc1);
        dto.setCluId("CLU-1");
        dto.setStateKey("Suspended");
        dto.setTypeKey("kuali.resultType.gradeCourseResult");
        dto.setEffectiveDate(new Date());
        dto.setExpirationDate(new Date());

        List<ResultOptionInfo> resultOptions = new ArrayList<ResultOptionInfo>();
        ResultOptionInfo option = new ResultOptionInfo();
        RichTextInfo desc2 = new RichTextInfo();
        desc2.setPlain("Plain description");
        option.setDescr(desc2);
        option.setEffectiveDate(new Date());
        option.setExpirationDate(new Date());
        option.setResultComponentId("kuali.resultComponent.grade.letter");
        option.setResultUsageTypeKey(null);
        option.setStateKey("Suspended");
        resultOptions.add(option);

        dto.setResultOptions(resultOptions);

        CluResultInfo createCluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto, contextInfo);
        createCluResult = client.getCluResult(createCluResult.getId(), contextInfo);

        assertNotNull(createCluResult);

        createCluResult.setCluId("CLU-2");
        RichTextInfo desc3 = new RichTextInfo();
        desc3.setPlain("Plain description again");
        createCluResult.setDescr(desc3);
        createCluResult.setEffectiveDate(new Date());
        createCluResult.setExpirationDate(new Date());
        createCluResult.setStateKey("active");
        createCluResult.setTypeKey("kuali.resultType.creditCourseResult");

        RichTextInfo desc4 = new RichTextInfo();
        desc4.setPlain("Some more plain description");
        createCluResult.getResultOptions().get(0).setDescr(desc4);
        createCluResult.getResultOptions().get(0).setEffectiveDate(new Date());
        createCluResult.getResultOptions().get(0).setExpirationDate(new Date());
        createCluResult.getResultOptions().get(0).setResultComponentId("kuali.resultComponent.grade.passFail");
        createCluResult.getResultOptions().get(0).setResultUsageTypeKey("lrType.finalGrade");
        createCluResult.getResultOptions().get(0).setStateKey("active");

        CluResultInfo updateCluResult = client.updateCluResult(createCluResult.getId(), createCluResult, contextInfo);
        updateCluResult = client.getCluResult(updateCluResult.getId(), contextInfo);

        assertNotNull(updateCluResult);
        assertEquals(createCluResult.getId(), updateCluResult.getId());
        assertEquals(createCluResult.getDescr().getPlain(), updateCluResult.getDescr().getPlain());
        assertEquals(createCluResult.getEffectiveDate(), updateCluResult.getEffectiveDate());
        assertEquals(createCluResult.getExpirationDate(), updateCluResult.getExpirationDate());
        assertEquals(createCluResult.getStateKey(), updateCluResult.getStateKey());
        assertEquals(createCluResult.getTypeKey(), updateCluResult.getTypeKey());
        assertEquals(createCluResult.getResultOptions().get(0).getId(), updateCluResult.getResultOptions().get(0).getId());
        assertEquals(createCluResult.getResultOptions().get(0).getDescr().getPlain(), updateCluResult.getResultOptions().get(0).getDescr().getPlain());
        assertEquals(createCluResult.getResultOptions().get(0).getEffectiveDate(), updateCluResult.getResultOptions().get(0).getEffectiveDate());
        assertEquals(createCluResult.getResultOptions().get(0).getExpirationDate(), updateCluResult.getResultOptions().get(0).getExpirationDate());
        assertEquals(createCluResult.getResultOptions().get(0).getResultComponentId(), updateCluResult.getResultOptions().get(0).getResultComponentId());
        assertEquals(createCluResult.getResultOptions().get(0).getResultUsageTypeKey(), updateCluResult.getResultOptions().get(0).getResultUsageTypeKey());
        assertEquals(createCluResult.getResultOptions().get(0).getStateKey(), updateCluResult.getResultOptions().get(0).getStateKey());
    }

    @Test
    public void testUpdateCluResult_RemoveAllCluResultOptions() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        contextInfo.setPrincipalId("123");

        CluResultInfo dto = new CluResultInfo();
        RichTextInfo desc1 = new RichTextInfo();
        desc1.setPlain("Plain description");
        dto.setDescr(desc1);
        dto.setCluId("CLU-1");
        dto.setStateKey("Suspended");
        dto.setTypeKey("kuali.resultType.gradeCourseResult");
        dto.setEffectiveDate(new Date());
        dto.setExpirationDate(new Date());

        List<ResultOptionInfo> resultOptions = new ArrayList<ResultOptionInfo>();
        ResultOptionInfo option = new ResultOptionInfo();
        RichTextInfo desc2 = new RichTextInfo();
        desc2.setPlain("Plain description");
        option.setDescr(desc2);
        option.setEffectiveDate(new Date());
        option.setExpirationDate(new Date());
        option.setResultComponentId("kuali.resultComponent.grade.letter");
        //option.setResultUsageTypeKey("lrType.finalGrade");
        option.setStateKey("Suspended");
        resultOptions.add(option);

        dto.setResultOptions(resultOptions);

        CluResultInfo createCluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto, contextInfo);
        createCluResult = client.getCluResult(createCluResult.getId(), contextInfo);

        assertNotNull(createCluResult);

        // Clear all cluResultOptions
        createCluResult.getResultOptions().clear();

        CluResultInfo updateCluResult = client.updateCluResult(createCluResult.getId(), createCluResult, contextInfo);
        updateCluResult = client.getCluResult(updateCluResult.getId(), contextInfo);

        assertNotNull(updateCluResult);
        assertEquals(createCluResult.getId(), updateCluResult.getId());
        assertEquals(createCluResult.getResultOptions().isEmpty(), updateCluResult.getResultOptions().isEmpty());
    }

    @Test
    public void testVersioning() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, IllegalVersionSequencingException, ReadOnlyException {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        CluInfo clu = createCluInfo();
        clu.setTypeKey("luType.shell.course");
        CluInfo cluV1 = client.createClu(clu.getTypeKey(), clu, contextInfo);

        try {
            //Try to set the start date in the past
            client.setCurrentCluVersion(cluV1.getId(),
                    DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("19000101"), contextInfo);
            assertTrue(false);
        } catch (Exception e) {
        }

        CluInfo justMadeCurrentClu = client.getClu(cluV1.getId(), contextInfo);
        assertTrue(justMadeCurrentClu.getVersion().getCurrentVersionStart().compareTo(new Date()) < 1);
        VersionDisplayInfo versionDisplayInfo = client.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, justMadeCurrentClu.getVersion().getVersionIndId(), contextInfo);
        //Try to make a new versions from the current version
        CluInfo cluV2 = client.createNewCluVersion(cluV1.getVersion().getVersionIndId(), "CommentA", contextInfo);
        CluInfo cluV3 = client.createNewCluVersion(cluV1.getVersion().getVersionIndId(), "CommentB", contextInfo);
        versionDisplayInfo = client.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, cluV1.getVersion().getVersionIndId(), contextInfo);
        assertEquals(cluV1.getId(), versionDisplayInfo.getId());
        assertEquals(cluV1.getVersion().getVersionIndId(), cluV2.getVersion().getVersionIndId());
        assertEquals(cluV1.getVersion().getVersionIndId(), cluV3.getVersion().getVersionIndId());
        client.setCurrentCluVersion(cluV3.getId(), null, contextInfo);
        versionDisplayInfo = client.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, cluV1.getVersion().getVersionIndId(), contextInfo);
        assertEquals(versionDisplayInfo.getId(), cluV3.getId());


        SearchRequestInfo searchRequest = new SearchRequestInfo();
        SearchParamInfo param = new SearchParamInfo();
        param.setKey("lu.queryParam.cluVersionIndId");
        param.getValues().add(versionDisplayInfo.getVersionIndId());
        searchRequest.getParams().add(param);
        searchRequest.setSearchKey("lu.search.clu.versions");
        SearchResultInfo searchResult = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());
        assertEquals(3, searchResult.getRows().size());

    }

    private CluSetInfo createCluSetInfo() throws ParseException {
        CluSetInfo cluSetInfo = new CluSetInfo();

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>Formatted Desc</p>");
        desc.setPlain("plain");
        cluSetInfo.setDescr(desc);
        cluSetInfo.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20080101"));
        cluSetInfo.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20180101"));
        cluSetInfo.setName("Clu set name");
        cluSetInfo.setStateKey("draft");
        cluSetInfo.setTypeKey("kuali.cluset.course");
        return cluSetInfo;
    }

    private CluInfo createCluInfo() throws ParseException {
        CluInfo clu = new CluInfo();

        CluAccountingInfo accountingInfo = new CluAccountingInfo();

        if (accountingInfo.getAttributes() == null) {
            accountingInfo.setAttributes(new ArrayList<AttributeInfo>());
        }
        accountingInfo.getAttributes().add(new AttributeInfo("AccountingAttrKey1", "AccountingAttrValue1"));
        accountingInfo.getAttributes().add(new AttributeInfo("AccountingAttrKey2", "AccountingAttrValue2"));
        clu.setAccountingInfo(accountingInfo);

        CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
        officialIdentifier.setCode("offId-code");
        officialIdentifier.setDivision("offId-division");
        officialIdentifier.setLevel("offId-level");
        officialIdentifier.setLongName("offId-longName");
        officialIdentifier.setShortName("offId-shortName");
        officialIdentifier.setStateKey("offId-state");
        officialIdentifier.setTypeKey("offId-type");
        officialIdentifier.setVariation("offId-variation");
        officialIdentifier.setSuffixCode("offId-suffixcode");
        officialIdentifier.setOrgId("offId-orgid");
        clu.setOfficialIdentifier(officialIdentifier);

        CluIdentifierInfo cluId1 = new CluIdentifierInfo();
        cluId1.setCode("cluId1-code");
        cluId1.setDivision("cluId1-division");
        cluId1.setLevel("cluId1-level");
        cluId1.setLongName("cluId1-longName");
        cluId1.setShortName("cluId1-shortName");
        cluId1.setStateKey("cluId1-state");
        cluId1.setTypeKey("cluId1-type");
        cluId1.setVariation("cluId1-variation");
        cluId1.setSuffixCode("cluId1-suffixcode");
        cluId1.setOrgId("cluId1-orgid");
        clu.getAlternateIdentifiers().add(cluId1);

        CluIdentifierInfo cluId2 = new CluIdentifierInfo();
        cluId2.setCode("cluId2-code");
        cluId2.setDivision("cluId2-division");
        cluId2.setLevel("cluId2-level");
        cluId2.setLongName("cluId2-longName");
        cluId2.setShortName("cluId2-shortName");
        cluId2.setStateKey("cluId2-state");
        cluId2.setTypeKey("cluId2-type");
        cluId2.setVariation("cluId2-variation");
        cluId2.setSuffixCode("cluId2-suffixcode");
        cluId2.setOrgId("cluId2-orgid");
        clu.getAlternateIdentifiers().add(cluId2);

        if (clu.getAttributes() == null) {
            clu.setAttributes(new ArrayList<AttributeInfo>());
        }
        clu.getAttributes().add(new AttributeInfo("cluAttrKey1", "cluAttrValue1"));
        clu.getAttributes().add(new AttributeInfo("cluAttrKey2", "cluAttrValue2"));

        clu.setCanCreateLui(true);

        clu.setDefaultEnrollmentEstimate(545);
        clu.setDefaultMaximumEnrollment(999);

        RichTextInfo desc = new RichTextInfo();
        desc.setFormatted("<p>DESC FORMATTED</p>");
        desc.setPlain("DESC PLAIN");
        clu.setDescr(desc);

        clu.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100203"));
        clu.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21001231"));

        clu.setIsEnrollable(true);
        clu.setIsHazardousForDisabledStudents(true);

        AffiliatedOrgInfo aforg = new AffiliatedOrgInfo();
        aforg.setOrgId("AFF-ORG1");
        aforg.setPercentage(35l);

        AffiliatedOrgInfo aforg1 = new AffiliatedOrgInfo();
        aforg1.setOrgId("AFF-ORG2");
        aforg1.setPercentage(65l);

        List<AffiliatedOrgInfo> affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
        affiliatedOrgs.add(aforg);
        affiliatedOrgs.add(aforg1);

        CurrencyAmountInfo ca = new CurrencyAmountInfo();
        ca.setCurrencyQuantity(100);
        ca.setCurrencyTypeKey("DLLR");

        CurrencyAmountInfo ca1 = new CurrencyAmountInfo();
        ca.setCurrencyQuantity(200);
        ca.setCurrencyTypeKey("DLLR");

        List<CurrencyAmountInfo> caList = new ArrayList<CurrencyAmountInfo>();
        caList.add(ca);

        List<CurrencyAmountInfo> caList1 = new ArrayList<CurrencyAmountInfo>();
        caList.add(ca);
        caList.add(ca1);

        RichTextInfo cfRecDesc = new RichTextInfo();
        cfRecDesc.setPlain("Clu Fee Record");

        CluFeeRecordInfo feeRec = new CluFeeRecordInfo();
        feeRec.setAffiliatedOrgs(affiliatedOrgs);
        feeRec.setFeeAmounts(caList);
        feeRec.setFeeType("FEE-TYPE-X");
        feeRec.setRateType("RATE-TYPE-X");
        feeRec.setDescr(cfRecDesc);

        CluFeeRecordInfo feeRec1 = new CluFeeRecordInfo();
        feeRec1.setAffiliatedOrgs(affiliatedOrgs);
        feeRec1.setFeeAmounts(caList1);
        feeRec1.setFeeType("FEE-TYPE-Y");
        feeRec1.setRateType("RATE-TYPE-Y");

        List<CluFeeRecordInfo> feeRecList = new ArrayList<CluFeeRecordInfo>();
        feeRecList.add(feeRec);
        feeRecList.add(feeRec1);

        RichTextInfo cfDesc = new RichTextInfo();
        cfDesc.setPlain("Clu Fee");

        CluFeeInfo feeInfo = new CluFeeInfo();
        feeInfo.getAttributes().add(new AttributeInfo("FeeAttrKey1", "FeeAttrValue1"));
        feeInfo.getAttributes().add(new AttributeInfo("FeeAttrKey2", "FeeAttrValue2"));
        feeInfo.setCluFeeRecords(feeRecList);
        feeInfo.setDescr(cfDesc);
        clu.setFeeInfo(feeInfo);

        clu.setIsHasEarlyDropDeadline(true);

        clu.setIsHazardousForDisabledStudents(true);

        CluInstructorInfo primaryInstructor = new CluInstructorInfo();
        primaryInstructor.setOrgId("EXT-orgId-1");
        primaryInstructor.setPersonId("EXT-personId-1");
        primaryInstructor.getAttributes().add(new AttributeInfo("PrimaryInstAttrKey1", "PrimaryInstAttrValue1"));
        primaryInstructor.getAttributes().add(new AttributeInfo("PrimaryInstAttrKey2", "PrimaryInstAttrValue2"));
        clu.setPrimaryInstructor(primaryInstructor);

        CluInstructorInfo instructor1 = new CluInstructorInfo();
        instructor1.setOrgId("EXT-orgId-2");
        instructor1.setPersonId("EXT-personId-2");
        instructor1.getAttributes().add(new AttributeInfo("Inst1AttrKey1", "Inst1AttrValue1"));
        instructor1.getAttributes().add(new AttributeInfo("Inst1AttrKey2", "Inst1AttrValue2"));
        clu.getInstructors().add(instructor1);

        CluInstructorInfo instructor2 = new CluInstructorInfo();
        instructor2.setOrgId("EXT-orgId-3");
        instructor2.setPersonId("EXT-personId-3");
        instructor2.getAttributes().add(new AttributeInfo("Inst2AttrKey1", "Inst2AttrValue1"));
        instructor2.getAttributes().add(new AttributeInfo("Inst2AttrKey2", "Inst2AttrValue2"));
        clu.getInstructors().add(instructor2);

        LuCodeInfo luCode1 = new LuCodeInfo();
        luCode1.setId("luCode1.key");
        RichTextInfo loCode1desc = new RichTextInfo();
        loCode1desc.setPlain("luCode1-desc");
        luCode1.setDescr(loCode1desc);
        luCode1.setValue("luCode1-value");
        luCode1.getAttributes().add(new AttributeInfo("luCode1AttrKey1", "luCode1AttrValue1"));
        luCode1.getAttributes().add(new AttributeInfo("luCode1AttrKey2", "luCode1AttrValue2"));
        luCode1.setType("kuali.someKindOfLuCode");
        clu.getLuCodes().add(luCode1);

        LuCodeInfo luCode2 = new LuCodeInfo();
        luCode2.setId("luCode2.key");
        RichTextInfo loCode2desc = new RichTextInfo();
        loCode2desc.setPlain("luCode2-desc");
        luCode2.setDescr(loCode2desc);
        luCode2.setValue("luCode2-value");
        luCode2.getAttributes().add(new AttributeInfo("luCode2AttrKey1", "luCode2AttrValue1"));
        luCode2.getAttributes().add(new AttributeInfo("luCode2AttrKey2", "luCode2AttrValue2"));
        luCode2.setType("kuali.someKindOfLuCode");
        clu.getLuCodes().add(luCode2);

        RichTextInfo marketingDesc = new RichTextInfo();
        marketingDesc.setFormatted("<p>marketingDesc FORMATTED</p>");
        marketingDesc.setPlain("marketingDesc PLAIN");

        clu.setNextReviewPeriod("nextReviewPeriod");

        clu.getOfferedAtpTypes().add("offeredAtpType1");
        clu.getOfferedAtpTypes().add("offeredAtpType2");


        CluInstructorInfo pubPrimaryInstructor = new CluInstructorInfo();
        pubPrimaryInstructor.setOrgId("EXT-orgId-234");
        pubPrimaryInstructor.setPersonId("EXT-personId-2451");
        pubPrimaryInstructor.getAttributes().add(new AttributeInfo("PubPrimaryInstAttrKey1", "PubPrimaryInstAttrValue1"));
        pubPrimaryInstructor.getAttributes().add(new AttributeInfo("PubPrimaryInstAttrKey2", "PubPrimaryInstAttrValue2"));

        CluInstructorInfo pubInstructor1 = new CluInstructorInfo();
        pubInstructor1.setOrgId("EXT-orgId-2");
        pubInstructor1.setPersonId("EXT-personId-2");
        pubInstructor1.getAttributes().add(new AttributeInfo("PubInst1AttrKey1", "PubInst1AttrValue1"));
        pubInstructor1.getAttributes().add(new AttributeInfo("PubInst1AttrKey2", "PubInst1AttrValue2"));

        CluInstructorInfo pubInstructor2 = new CluInstructorInfo();
        pubInstructor2.setOrgId("EXT-orgId-3");
        pubInstructor2.setPersonId("EXT-personId-3");
        pubInstructor2.getAttributes().add(new AttributeInfo("PubInst2AttrKey1", "PubInst2AttrValue1"));
        pubInstructor2.getAttributes().add(new AttributeInfo("PubInst2AttrKey2", "PubInst2AttrValue2"));

        clu.setReferenceURL("http://student.kuali.org/clus");

        clu.setStateKey("Clu-state");

        TimeAmountInfo stdDuration = new TimeAmountInfo();
        stdDuration.setAtpDurationTypeKey("EXT-stdDuration-Id1");
        stdDuration.setTimeQuantity(new Integer(7867));
        clu.setStdDuration(stdDuration);

        clu.setTypeKey("kuali.SomeKindOfClu");

        createCampusLocationList(clu);

        createIntensity(clu);

        createAccreditationList(clu);

        createAdminOrgs(clu);

        return clu;
    }

    private void createAdminOrgs(CluInfo clu) {
        AdminOrgInfo adminOrg = new AdminOrgInfo();
        adminOrg.setOrgId("PRIMARY-ADMIN-ORG-ID");
        adminOrg.getAttributes().add(new AttributeInfo("PrimaryAdminOrgAttrKey1", "PrimaryAdminOrgAttrValue1"));
        adminOrg.getAttributes().add(new AttributeInfo("PrimaryAdminOrgAttrKey2", "PrimaryAdminOrgAttrValue2"));
        adminOrg.setTypeKey("kuali.altadminType1");
        adminOrg.setStateKey("kuali.altAdminState1");

        clu.getAdminOrgs().add(adminOrg);
        AdminOrgInfo altAdminOrg1 = new AdminOrgInfo();
        altAdminOrg1.setOrgId("ALT-ADMIN-ORG-ID1");
        altAdminOrg1.getAttributes().add(new AttributeInfo("AltAdminOrg1AttrKey1", "AltAdminOrg1AttrValue1"));
        altAdminOrg1.getAttributes().add(new AttributeInfo("AltAdminOrg1AttrKey2", "AltAdminOrg1AttrValue2"));
        altAdminOrg1.getAttributes().add(new AttributeInfo("AltAdminOrg1AttrKey3", "AltAdminOrg1AttrValue3"));
        altAdminOrg1.setTypeKey("kuali.altadminType1");
        altAdminOrg1.setStateKey("kuali.altadminState1");

        AdminOrgInfo altAdminOrg2 = new AdminOrgInfo();
        altAdminOrg2.setOrgId("ALT-ADMIN-ORG-ID2");
        altAdminOrg2.getAttributes().add(new AttributeInfo("AltAdminOrg2AttrKey1", "AltAdminOrg2AttrValue1"));
        altAdminOrg2.getAttributes().add(new AttributeInfo("AltAdminOrg2AttrKey2", "AltAdminOrg2AttrValue2"));
        altAdminOrg2.setTypeKey("kuali.altadminType1");
        altAdminOrg2.setStateKey("kuali.altadminState1");

        clu.getAdminOrgs().add(altAdminOrg1);
        clu.getAdminOrgs().add(altAdminOrg2);

    }

    private void checkAdminOrgsCreate(CluInfo clu) {

        assertEquals("PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
                .getOrgId());
        assertEquals(2, clu.getAdminOrgs().get(0).getAttributes().size());
        assertEquals("PrimaryAdminOrgAttrValue1", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey1"));
        assertEquals("PrimaryAdminOrgAttrValue2", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey2"));

        assertEquals("ALT-ADMIN-ORG-ID1", clu.getAdminOrgs().get(1).getOrgId());
        assertEquals(3, clu.getAdminOrgs().get(1).getAttributes().size());
        assertEquals("AltAdminOrg1AttrValue1", clu.getAdminOrgs().get(1).getAttributeValue("AltAdminOrg1AttrKey1"));
        assertEquals("AltAdminOrg1AttrValue2", clu.getAdminOrgs().get(1).getAttributeValue("AltAdminOrg1AttrKey2"));
        assertEquals("AltAdminOrg1AttrValue3", clu.getAdminOrgs().get(1).getAttributeValue("AltAdminOrg1AttrKey3"));

        assertEquals("ALT-ADMIN-ORG-ID2", clu.getAdminOrgs().get(2).getOrgId());
        assertEquals(2, clu.getAdminOrgs().get(2).getAttributes().size());
        assertEquals("AltAdminOrg2AttrValue1", clu.getAdminOrgs().get(2).getAttributeValue("AltAdminOrg2AttrKey1"));
        assertEquals("AltAdminOrg2AttrValue2", clu.getAdminOrgs().get(2).getAttributeValue("AltAdminOrg2AttrKey2"));
    }

    private void updateAdminOrgs(CluInfo clu) {
        clu.getAdminOrgs().get(0).setId("adminOrg121");
        clu.getAdminOrgs().get(0).setOrgId("UPD-PRIMARY-ADMIN-ORG-ID");
        clu.getAdminOrgs().get(0).getAttributes().add(new AttributeInfo("PrimaryAdminOrgAttrKey3", "PrimaryAdminOrgAttrValue3"));
        clu.getAdminOrgs().get(0).getAttributes().remove(1);
        clu.getAdminOrgs().get(0).getAttributes().add(new AttributeInfo("PrimaryAdminOrgAttrKey4", "PrimaryAdminOrgAttrValue4"));

        AdminOrgInfo altAdminOrg3 = new AdminOrgInfo();
        altAdminOrg3.setOrgId("UPD-ADMIN-ORG-ID3");
        altAdminOrg3.getAttributes().add(new AttributeInfo("UPDAdminOrg3AttrKey1", "UPDAdminOrg3AttrKey1"));
        altAdminOrg3.getAttributes().add(new AttributeInfo("UPDAdminOrg3AttrKey2", "UPDAdminOrg3AttrKey1"));
        altAdminOrg3.setTypeKey("kuali.testType");

        clu.getAdminOrgs().remove(1);
        clu.getAdminOrgs().add(altAdminOrg3);
    }

    private void checkAdminOrgUpdate(CluInfo clu) {

        assertEquals("UPD-PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
                .getOrgId());
        assertEquals(3, clu.getAdminOrgs().get(0).getAttributes().size());
        assertEquals("PrimaryAdminOrgAttrValue4", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey4"));
        assertNull(clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey2"));
        assertEquals(3, clu.getAdminOrgs().size());
        assertEquals("UPD-PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
                .getOrgId());
        assertEquals(3, clu.getAdminOrgs().get(0).getAttributes()
                .size());
        assertEquals("PrimaryAdminOrgAttrValue1", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey1"));
        assertEquals("PrimaryAdminOrgAttrValue4", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey4"));
        assertEquals("PrimaryAdminOrgAttrValue3", clu.getAdminOrgs().get(0).getAttributeValue("PrimaryAdminOrgAttrKey3"));
        assertEquals("ALT-ADMIN-ORG-ID2", clu.getAdminOrgs().get(1)
                .getOrgId());
        assertEquals(2, clu.getAdminOrgs().get(1).getAttributes()
                .size());
        assertEquals("AltAdminOrg2AttrValue2", clu.getAdminOrgs().get(1).getAttributeValue("AltAdminOrg2AttrKey2"));
        assertEquals("AltAdminOrg2AttrValue1", clu.getAdminOrgs().get(1).getAttributeValue("AltAdminOrg2AttrKey1"));
    }

    private void createAccreditationList(CluInfo clu) throws ParseException {
        AccreditationInfo accreditationOrg1 = new AccreditationInfo();
        accreditationOrg1.setOrgId("EXT-orgId-1");
        accreditationOrg1.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100203"));
        accreditationOrg1.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21001231"));
        accreditationOrg1.getAttributes().add(new AttributeInfo("Accred1AttrKey1", "Accred1AttrValue1"));
        accreditationOrg1.getAttributes().add(new AttributeInfo("Accred1AttrKey2", "Accred1AttrValue2"));

        AccreditationInfo accreditationOrg2 = new AccreditationInfo();
        accreditationOrg2.setOrgId("EXT-orgId-2");
        accreditationOrg2.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20110203"));
        accreditationOrg2.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21011231"));
        accreditationOrg2.getAttributes().add(new AttributeInfo("Accred2AttrKey1", "Accred2AttrValue1"));
        accreditationOrg2.getAttributes().add(new AttributeInfo("Accred2AttrKey2", "Accred2AttrValue2"));

        clu.getAccreditations().add(accreditationOrg1);
        clu.getAccreditations().add(accreditationOrg2);
    }

    private void checkAccreditationListCreate(CluInfo clu)
            throws ParseException {

        assertEquals(2, clu.getAccreditations().size());

        assertEquals("EXT-orgId-1", clu.getAccreditations().get(0)
                .getOrgId());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100203"), clu.getAccreditations().get(0)
                .getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21001231"), clu.getAccreditations().get(0)
                .getExpirationDate());
        assertEquals(2, clu.getAccreditations().get(0).getAttributes()
                .size());
        assertEquals("Accred1AttrValue1", clu.getAccreditations().get(0).getAttributeValue("Accred1AttrKey1"));
        assertEquals("Accred1AttrValue2", clu.getAccreditations().get(0).getAttributeValue("Accred1AttrKey2"));

        assertEquals("EXT-orgId-2", clu.getAccreditations().get(1)
                .getOrgId());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20110203"), clu.getAccreditations().get(1)
                .getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21011231"), clu.getAccreditations().get(1)
                .getExpirationDate());
        assertEquals(2, clu.getAccreditations().get(1).getAttributes()
                .size());
        assertEquals("Accred2AttrValue1", clu.getAccreditations().get(1).getAttributeValue("Accred2AttrKey1"));
        assertEquals("Accred2AttrValue2", clu.getAccreditations().get(1).getAttributeValue("Accred2AttrKey2"));
    }

    private void updateAccreditationList(CluInfo clu) throws ParseException {

        AccreditationInfo accreditationOrg3 = new AccreditationInfo();
        accreditationOrg3.setOrgId("EXT-orgId-3");
        accreditationOrg3.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20120203"));
        accreditationOrg3.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21021231"));

        AccreditationInfo accreditationOrg4 = new AccreditationInfo();
        accreditationOrg4.setOrgId("EXT-orgId-4");
        accreditationOrg4.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20130203"));
        accreditationOrg4.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21031231"));
        accreditationOrg4.getAttributes().add(new AttributeInfo("Accred4AttrKey1", "Accred4AttrValue1"));

        clu.getAccreditations().add(accreditationOrg3);
        clu.getAccreditations().add(accreditationOrg4);

        for (int i = 0; i < clu.getAccreditations().get(0).getAttributes().size(); i++) {
            String attributeKey = clu.getAccreditations().get(0).getAttributes().get(i).getKey();
            if (attributeKey.equals("Accred1AttrKey1")) {
                clu.getAccreditations().get(0).getAttributes().set(i, new AttributeInfo("Accred1AttrKey1", "Accred1AttrValue1-UPD"));
            } else if (attributeKey.equals("Accred1AttrKey2")) {
                clu.getAccreditations().get(0).getAttributes().remove(i);
            }
        }

        clu.getAccreditations().remove(1);
    }

    private void checkAccreditationListUpdate(CluInfo clu)
            throws ParseException {

        assertEquals(3, clu.getAccreditations().size());

        assertEquals("EXT-orgId-1", clu.getAccreditations().get(0)
                .getOrgId());
        assertEquals(1, clu.getAccreditations().get(0).getAttributes()
                .size());
        assertEquals("Accred1AttrValue1-UPD", clu.getAccreditations().get(0).getAttributeValue("Accred1AttrKey1"));

        assertEquals("EXT-orgId-3", clu.getAccreditations().get(1)
                .getOrgId());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20120203"), clu.getAccreditations().get(1)
                .getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21021231"), clu.getAccreditations().get(1)
                .getExpirationDate());
        assertEquals(0, clu.getAccreditations().get(1).getAttributes()
                .size());

        assertEquals("EXT-orgId-4", clu.getAccreditations().get(2)
                .getOrgId());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20130203"), clu.getAccreditations().get(2)
                .getEffectiveDate());
        assertEquals(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("21031231"), clu.getAccreditations().get(2)
                .getExpirationDate());
        assertEquals(1, clu.getAccreditations().get(2).getAttributes()
                .size());
        assertEquals("Accred4AttrValue1", clu.getAccreditations().get(2).getAttributeValue("Accred4AttrKey1"));

    }

    private void createIntensity(CluInfo clu) {
        AmountInfo intensity = new AmountInfo();
        intensity.setUnitTypeKey("EXT-intensity-Id1");
        intensity.setUnitQuantity("123");
        clu.setIntensity(intensity);
    }

    private void checkIntensityCreate(CluInfo clu) {
        assertEquals("EXT-intensity-Id1", clu.getIntensity()
                .getUnitTypeKey());
        assertEquals("123", clu.getIntensity().getUnitQuantity());
    }

    private void updateIntensity(CluInfo clu) {
        clu.getIntensity().setUnitTypeKey("UPD-intensity-Id1");
        clu.getIntensity().setUnitQuantity("456");
    }

    private void checkIntensityUpdate(CluInfo clu) {
        assertEquals("UPD-intensity-Id1", clu.getIntensity()
                .getUnitTypeKey());
        assertEquals("456", clu.getIntensity().getUnitQuantity());

    }

    private void createCampusLocationList(CluInfo clu) {
        clu.getCampusLocations().add("EXT-Campus-Location-1");
        clu.getCampusLocations().add("EXT-Campus-Location-2");
    }

    private void checkCampusLocationCreate(CluInfo clu) {
        assertEquals(2, clu.getCampusLocations().size());
        assertEquals("EXT-Campus-Location-1", clu.getCampusLocations()
                .get(0));
        assertEquals("EXT-Campus-Location-2", clu.getCampusLocations()
                .get(1));
    }

    private void updateCampusLocationList(CluInfo clu) {
        clu.getCampusLocations().add("EXT-Campus-Location-3");
        clu.getCampusLocations().add("EXT-Campus-Location-4");
        clu.getCampusLocations().remove(0);
    }

    private void checkCampusLocationUpdate(CluInfo clu) {
        assertEquals(3, clu.getCampusLocations().size());
        assertEquals("EXT-Campus-Location-2", clu.getCampusLocations()
                .get(0));
        assertEquals("EXT-Campus-Location-3", clu.getCampusLocations()
                .get(1));
        assertEquals("EXT-Campus-Location-4", clu.getCampusLocations()
                .get(2));
    }

    @Test
    public void testSearchForCluInCluSets() throws Exception {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("cluset.queryParam.luOptionalId");
        searchParam.getValues().add("CLU-5");
        queryParamValueList.add(searchParam);

        searchRequest.setParams(queryParamValueList);
        SearchResultInfo cluSets = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());

        Assert.assertEquals(2, cluSets.getRows().size());
    }

    @Test
    public void testSearchForCluInCluSets_EmptyResult() throws Exception {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("lu.queryParam.luOptionalId");
        searchParam.getValues().add("XXX");
        queryParamValueList.add(searchParam);

        searchRequest.setParams(queryParamValueList);
        SearchResultInfo cluSets = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());

        Assert.assertEquals(0, cluSets.getRows().size());
    }

    @Test
    public void testSearchForCluInCluRelations() throws Exception {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.cluCluRelation");

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("lu.queryParam.luOptionalId");
        searchParam.getValues().add("CLU-1");
        queryParamValueList.add(searchParam);

        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());

        Assert.assertEquals(2, clus.getRows().size());
    }

    @Test
    public void testSearchForCluInCluRelations_EmptyResult() throws Exception {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.cluCluRelation");

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("lu.queryParam.luOptionalId");
        searchParam.getValues().add("XXX");
        queryParamValueList.add(searchParam);

        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = client.search(searchRequest, ContextInfoTestUtility.getEnglishContextInfo());

        Assert.assertEquals(0, clus.getRows().size());
    }

    @Test
    public void testCluPublicationCrud() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        //Setup
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

        FieldInfo variant;

        CluPublicationInfo cluPublication1 = new CluPublicationInfo();
        cluPublication1.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20120203"));
        cluPublication1.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20130203"));
        cluPublication1.setStartCycle("StartCycle1");
        cluPublication1.setEndCycle("EndCycle1");
        cluPublication1.setStateKey("clupubState1");
        cluPublication1.setTypeKey("cluPublication.Test.Type.1");
        cluPublication1.setCluId("CLU-1");
        variant = new FieldInfo();
        variant.setId("field1.one");
        variant.setValue("value1.one");
        cluPublication1.getVariants().add(variant);
        variant = new FieldInfo();
        variant.setId("field1.two");
        variant.setValue("value1.two");
        cluPublication1.getVariants().add(variant);

        CluPublicationInfo cluPublication2 = new CluPublicationInfo();
        cluPublication2.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20120203"));
        cluPublication2.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20130203"));
        cluPublication2.setStartCycle("StartCycle2");
        cluPublication2.setEndCycle("EndCycle2");
        cluPublication2.setStateKey("clupubState2");
        cluPublication2.setTypeKey("cluPublication.Test.Type.1");
        cluPublication2.setCluId("CLU-2");
        variant = new FieldInfo();
        variant.setId("field2.one");
        variant.setValue("value2.one");
        cluPublication2.getVariants().add(variant);
        variant = new FieldInfo();
        variant.setId("field2.two");
        variant.setValue("value2.two");
        cluPublication2.getVariants().add(variant);

        CluPublicationInfo cluPublication3 = new CluPublicationInfo();
        cluPublication3.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20120203"));
        cluPublication3.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20130203"));
        cluPublication3.setStartCycle("StartCycle3");
        cluPublication3.setEndCycle("EndCycle3");
        cluPublication3.setStateKey("clupubState3");
        cluPublication3.setTypeKey("cluPublication.Test.Type.2");
        cluPublication3.setCluId("CLU-2");
        variant = new FieldInfo();
        variant.setId("field3.one");
        variant.setValue("value3.one");
        cluPublication3.getVariants().add(variant);
        variant = new FieldInfo();
        variant.setId("field3.two");
        variant.setValue("value3.two");
        cluPublication3.getVariants().add(variant);


        CluPublicationInfo createdCluPub1 = client.createCluPublication(cluPublication1.getCluId(), cluPublication1.getTypeKey(), cluPublication1, contextInfo);
        CluPublicationInfo createdCluPub2 = client.createCluPublication(cluPublication2.getCluId(), cluPublication2.getTypeKey(), cluPublication2, contextInfo);
        CluPublicationInfo createdCluPub3 = client.createCluPublication(cluPublication3.getCluId(), cluPublication3.getTypeKey(), cluPublication3, contextInfo);


        assertNotNull(createdCluPub1.getId());
        assertEquals(cluPublication1.getEffectiveDate(), createdCluPub1.getEffectiveDate());
        assertEquals(cluPublication1.getExpirationDate(), createdCluPub1.getExpirationDate());
        assertEquals(cluPublication1.getStartCycle(), createdCluPub1.getStartCycle());
        assertEquals(cluPublication1.getEndCycle(), createdCluPub1.getEndCycle());
        assertEquals(cluPublication1.getStateKey(), createdCluPub1.getStateKey());
        assertEquals(cluPublication1.getTypeKey(), createdCluPub1.getTypeKey());
        assertEquals(cluPublication1.getCluId(), createdCluPub1.getCluId());
        assertNotNull(createdCluPub3);

        //Test gets
        CluPublicationInfo gotCluPub1 = client.getCluPublication(createdCluPub1.getId(), contextInfo);
        assertEquals(createdCluPub1.getId(), gotCluPub1.getId());

        List<CluPublicationInfo> gotCluPubsByType = client.getCluPublicationsByType("cluPublication.Test.Type.1", contextInfo);
        assertEquals(2, gotCluPubsByType.size());

        List<CluPublicationInfo> gotCluPubsByClu = client.getCluPublicationsByClu("CLU-2", contextInfo);
        assertEquals(2, gotCluPubsByClu.size());

        //Change some values
        createdCluPub2.setEffectiveDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20020203"));
        createdCluPub2.setExpirationDate(DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20030203"));
        createdCluPub2.setStartCycle("StartCycle2U");
        createdCluPub2.setEndCycle("EndCycle2U");
        createdCluPub2.setStateKey("clupubState2U");
        createdCluPub2.setTypeKey("cluPublication.Test.Type.2");
        createdCluPub2.setCluId("CLU-1");
        createdCluPub2.getVariants().clear();
        variant = new FieldInfo();
        variant.setId("field2.oneU");
        variant.setValue("value2.oneU");
        createdCluPub2.getVariants().add(variant);
        variant = new FieldInfo();
        variant.setId("field2.twoU");
        variant.setValue("value2.twoU");
        createdCluPub2.getVariants().add(variant);

        CluPublicationInfo updated = client.updateCluPublication(createdCluPub2.getId(), createdCluPub2, contextInfo);
        //Test version mismatch
        try {
            client.updateCluPublication(createdCluPub2.getId(), createdCluPub2, contextInfo);
            assertTrue(false);
        } catch (VersionMismatchException e) {
            assertTrue(true);
        }


        //check that updated has correct values
        assertEquals(createdCluPub2.getId(), updated.getId());
        assertEquals(createdCluPub2.getEffectiveDate(), updated.getEffectiveDate());
        assertEquals(createdCluPub2.getExpirationDate(), updated.getExpirationDate());
        assertEquals(createdCluPub2.getStartCycle(), updated.getStartCycle());
        assertEquals(createdCluPub2.getEndCycle(), updated.getEndCycle());
        assertEquals(createdCluPub2.getStateKey(), updated.getStateKey());
        assertEquals(createdCluPub2.getTypeKey(), updated.getTypeKey());
        assertEquals(createdCluPub2.getCluId(), updated.getCluId());

        assertEquals(2, updated.getVariants().size());
        assertTrue(("field2.oneU".equals(updated.getVariants().get(0).getId())
                && "value2.oneU".equals(updated.getVariants().get(0).getValue()))
                ||
                ("field2.twoU".equals(updated.getVariants().get(0).getId())
                        && "value2.twoU".equals(updated.getVariants().get(0).getValue())));
    }
}
