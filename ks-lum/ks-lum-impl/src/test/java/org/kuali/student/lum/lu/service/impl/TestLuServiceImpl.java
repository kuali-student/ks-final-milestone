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

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.CluSetTreeViewInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.dto.ResultUsageTypeInfo;
import org.kuali.student.lum.lu.service.LuService;

import edu.emory.mathcs.backport.java.util.Collections;

@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql" /*
																										 * ,
																										 * testDataFile
																										 * =
																										 * "classpath:test-beans.xml"
																										 */) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", additionalContextFile = "classpath:lu-additional-context.xml")
	public LuService client;
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMdd");
	final Logger LOG = Logger.getLogger(TestLuServiceImpl.class);

	@Test
	public void testClu() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
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
		ids = client.getCluIdsByLuType("luType.shell.program", "STATE2");
		assertTrue(null != ids);
		assertEquals(1, ids.size());
		assertEquals("CLU-2", ids.get(0));

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
		clus = client.getClusByLuType("luType.shell.program", "STATE2");
		assertTrue(null != clus);
		assertEquals(1, clus.size());
		assertEquals("CLU-2", clus.get(0).getId());

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
	public void testCluSet() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
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
	public void testCluSetCrud() throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DoesNotExistException, ParseException,
			VersionMismatchException, UnsupportedActionException,
			CircularRelationshipException {
		CluSetInfo cluSetInfo = new CluSetInfo();

		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("<p>Formatted Desc</p>");
		desc.setPlain("plain");
		cluSetInfo.setType("kuali.cluSet.type.creditCourse");
		cluSetInfo.setAdminOrg("uuid-1234");
		cluSetInfo.setDescr(desc);
		cluSetInfo.setEffectiveDate(DF.parse("20080101"));
		cluSetInfo.setExpirationDate(DF.parse("20180101"));
		cluSetInfo.setName("testCluSet1");
		cluSetInfo.getCluIds().add("CLU-1");
		cluSetInfo.getCluIds().add("CLU-2");
//		cluSetInfo.getCluSetIds().add("CLUSET-1");
//		cluSetInfo.getCluSetIds().add("CLUSET-2");
		cluSetInfo.getAttributes().put("cluSet1ArrtKey1", "cluSet1ArrtValue1");
		cluSetInfo.getAttributes().put("cluSet1ArrtKey2", "cluSet1ArrtValue2");

		CluSetInfo createdSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", cluSetInfo);

		assertEquals("kuali.cluSet.type.creditCourse", createdSet1.getType());
		assertEquals("uuid-1234", createdSet1.getAdminOrg());
		assertEquals("<p>Formatted Desc</p>", createdSet1.getDescr().getFormatted());
		assertEquals("plain", createdSet1.getDescr().getPlain());
		assertEquals(DF.parse("20080101"), createdSet1.getEffectiveDate());
		assertEquals(DF.parse("20180101"), createdSet1.getExpirationDate());
		assertEquals("testCluSet1", createdSet1.getName());
		assertEquals("CLU-1", createdSet1.getCluIds().get(0));
		assertEquals("CLU-2", createdSet1.getCluIds().get(1));
//		assertEquals("CLUSET-1", createdSet1.getCluSetIds().get(0));
//		assertEquals("CLUSET-2", createdSet1.getCluSetIds().get(1));
		assertEquals("cluSet1ArrtValue1", createdSet1.getAttributes().get("cluSet1ArrtKey1"));
		assertEquals("cluSet1ArrtValue2", createdSet1.getAttributes().get("cluSet1ArrtKey2"));
		assertNotNull(createdSet1.getMetaInfo().getCreateTime());
		assertNotNull(createdSet1.getMetaInfo().getUpdateTime());
		assertNotNull(createdSet1.getId());

		createdSet1.setAdminOrg("uuid-1234-5678");
		createdSet1.getDescr().setFormatted("UP<p>Formatted Desc</p>");
		createdSet1.getDescr().setPlain("UPplain");
		createdSet1.setEffectiveDate(DF.parse("20090101"));
		createdSet1.setExpirationDate(DF.parse("20190101"));
		createdSet1.setName("UPtestCluSet1");
		createdSet1.getCluIds().remove(1);
		createdSet1.getCluIds().add("CLU-3");
//		createdSet1.getCluSetIds().remove(1);
//		createdSet1.getCluSetIds().add("CLUSET-3");
		createdSet1.getAttributes().put("cluSet1ArrtKey1", "UPcluSet1ArrtValue1");
		createdSet1.getAttributes().remove("cluSet1ArrtKey2");
		createdSet1.getAttributes().put("cluSet1ArrtKey3", "cluSet1ArrtValue3");

		CluSetInfo updatedSet1 = client.updateCluSet(createdSet1.getId(), createdSet1);

		assertEquals("uuid-1234-5678", updatedSet1.getAdminOrg());
		assertEquals("UP<p>Formatted Desc</p>", updatedSet1.getDescr().getFormatted());
		assertEquals("UPplain", updatedSet1.getDescr().getPlain());
		assertEquals(DF.parse("20090101"), updatedSet1.getEffectiveDate());
		assertEquals(DF.parse("20190101"), updatedSet1.getExpirationDate());
		assertEquals("UPtestCluSet1", updatedSet1.getName());
		assertEquals("CLU-1", updatedSet1.getCluIds().get(0));
		assertEquals("CLU-3", updatedSet1.getCluIds().get(1));
		assertEquals(2, updatedSet1.getCluIds().size());
//		assertEquals("CLUSET-1", updatedSet1.getCluSetIds().get(0));
//		assertEquals("CLUSET-3", updatedSet1.getCluSetIds().get(1));
//		assertEquals(2, updatedSet1.getCluSetIds().size());
		assertEquals("UPcluSet1ArrtValue1", updatedSet1.getAttributes().get(
				"cluSet1ArrtKey1"));
		assertEquals("cluSet1ArrtValue3", updatedSet1.getAttributes().get(
				"cluSet1ArrtKey3"));
		assertEquals(2, updatedSet1.getAttributes().size());
		assertNotNull(updatedSet1.getMetaInfo().getUpdateTime());
	}

	@Test
	public void testRemoveCluFromCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluIds().add("CLU-1");
		createCluSet.getCluIds().add("CLU-2");
		createCluSet.getCluIds().add("CLU-3");

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(3, createdCluSet1.getCluIds().size());

		StatusInfo status = client.removeCluFromCluSet("CLU-2", createdCluSet1.getId());
		assertTrue(status.getSuccess());

		createdCluSet1 = client.getCluSetInfo(createdCluSet1.getId());

		assertEquals(2, createdCluSet1.getCluIds().size());
		assertTrue(createdCluSet1.getCluIds().contains("CLU-1"));
		assertFalse(createdCluSet1.getCluIds().contains("CLU-2"));
		assertTrue(createdCluSet1.getCluIds().contains("CLU-3"));
	}

	@Test
	public void testRemoveCluSetFromCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluSetIds().add("CLUSET-1");
		createCluSet.getCluSetIds().add("CLUSET-2");
		createCluSet.getCluSetIds().add("CLUSET-3");

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(3, createdCluSet1.getCluSetIds().size());

		StatusInfo status = client.removeCluSetFromCluSet(createdCluSet1.getId(), "CLUSET-2");
		assertTrue(status.getSuccess());

		createdCluSet1 = client.getCluSetInfo(createdCluSet1.getId());

		assertEquals(2, createdCluSet1.getCluSetIds().size());
		assertTrue(createdCluSet1.getCluSetIds().contains("CLUSET-1"));
		assertFalse(createdCluSet1.getCluSetIds().contains("CLUSET-2"));
		assertTrue(createdCluSet1.getCluSetIds().contains("CLUSET-3"));
	}

	@Test
	public void testDeleteCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createCluSet = createCluSetInfo();

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);

		StatusInfo status = client.deleteCluSet(createdCluSet1.getId());
		assertTrue(status.getSuccess());

		try {
			client.getCluSetInfo(createdCluSet1.getId());
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
		List<CluCluRelationInfo> ccrs = client.getCluCluRelationsByClu("CLU-1");
		assertNotNull(ccrs);
		assertEquals(2, ccrs.size());

		ccrs = client.getCluCluRelationsByClu("CLUXX-42");
		assertTrue(ccrs == null || ccrs.size() == 0);

		try {
			ccrs = client.getCluCluRelationsByClu(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testCluCrud() throws ParseException, AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException, DependentObjectsExistException {

		CluInfo clu = createCluInfo();

		// Do the actual create call
		CluInfo createdClu = client.createClu("luType.shell.course", clu);
		createdClu = client.getClu(createdClu.getId());
		// Validate Results
		assertNotNull(createdClu);

		assertEquals("AccountingAttrValue1", createdClu.getAccountingInfo()
				.getAttributes().get("AccountingAttrKey1"));
		assertEquals("AccountingAttrValue2", createdClu.getAccountingInfo()
				.getAttributes().get("AccountingAttrKey2"));

		assertEquals("offId-divisionoffId-suffixcode", createdClu
				.getOfficialIdentifier().getCode());
		assertEquals("offId-division", createdClu.getOfficialIdentifier()
				.getDivision());
		assertEquals("offId-level", createdClu.getOfficialIdentifier()
				.getLevel());
		assertEquals("offId-longName", createdClu.getOfficialIdentifier()
				.getLongName());
		assertEquals("offId-shortName", createdClu.getOfficialIdentifier()
				.getShortName());
		assertEquals("offId-state", createdClu.getOfficialIdentifier()
				.getState());
		assertEquals("offId-type", createdClu.getOfficialIdentifier().getType());
		assertEquals("offId-variation", createdClu.getOfficialIdentifier()
				.getVariation());
		assertEquals("offId-suffixcode", createdClu.getOfficialIdentifier()
				.getSuffixCode());
		assertEquals("offId-orgid", createdClu.getOfficialIdentifier()
				.getOrgId());

		assertEquals("cluId1-divisioncluId1-suffixcode", createdClu
				.getAlternateIdentifiers().get(0).getCode());
		assertEquals("cluId1-division", createdClu.getAlternateIdentifiers()
				.get(0).getDivision());
		assertEquals("cluId1-level", createdClu.getAlternateIdentifiers()
				.get(0).getLevel());
		assertEquals("cluId1-longName", createdClu.getAlternateIdentifiers()
				.get(0).getLongName());
		assertEquals("cluId1-shortName", createdClu.getAlternateIdentifiers()
				.get(0).getShortName());
		assertEquals("cluId1-state", createdClu.getAlternateIdentifiers()
				.get(0).getState());
		assertEquals("cluId1-type", createdClu.getAlternateIdentifiers().get(0)
				.getType());
		assertEquals("cluId1-variation", createdClu.getAlternateIdentifiers()
				.get(0).getVariation());
		assertEquals("cluId1-suffixcode", createdClu.getAlternateIdentifiers()
				.get(0).getSuffixCode());
		assertEquals("cluId1-orgid", createdClu.getAlternateIdentifiers()
				.get(0).getOrgId());

		assertEquals("cluId2-divisioncluId2-suffixcode", createdClu
				.getAlternateIdentifiers().get(1).getCode());
		assertEquals("cluId2-division", createdClu.getAlternateIdentifiers()
				.get(1).getDivision());
		assertEquals("cluId2-level", createdClu.getAlternateIdentifiers()
				.get(1).getLevel());
		assertEquals("cluId2-longName", createdClu.getAlternateIdentifiers()
				.get(1).getLongName());
		assertEquals("cluId2-shortName", createdClu.getAlternateIdentifiers()
				.get(1).getShortName());
		assertEquals("cluId2-state", createdClu.getAlternateIdentifiers()
				.get(1).getState());
		assertEquals("cluId2-type", createdClu.getAlternateIdentifiers().get(1)
				.getType());
		assertEquals("cluId2-variation", createdClu.getAlternateIdentifiers()
				.get(1).getVariation());
		assertEquals("cluId2-suffixcode", createdClu.getAlternateIdentifiers()
				.get(1).getSuffixCode());
		assertEquals("cluId2-orgid", createdClu.getAlternateIdentifiers()
				.get(1).getOrgId());

		assertEquals("cluAttrValue1", createdClu.getAttributes().get(
				"cluAttrKey1"));
		assertEquals("cluAttrValue2", createdClu.getAttributes().get(
				"cluAttrKey2"));

		assertTrue(createdClu.isCanCreateLui());

		assertEquals(545, createdClu.getDefaultEnrollmentEstimate());
		assertEquals(999, createdClu.getDefaultMaximumEnrollment());

		assertEquals("<p>DESC FORMATTED</p>", createdClu.getDescr()
				.getFormatted());
		assertEquals("DESC PLAIN", createdClu.getDescr().getPlain());

		assertEquals(DF.parse("20100203"), createdClu.getEffectiveDate());
		assertEquals(DF.parse("21001231"), createdClu.getExpirationDate());

		assertTrue(createdClu.isEnrollable());

		assertEquals("FeeAttrValue1", createdClu.getFeeInfo().getAttributes()
				.get("FeeAttrKey1"));
		assertEquals("FeeAttrValue2", createdClu.getFeeInfo().getAttributes()
				.get("FeeAttrKey2"));

		assertEquals("Clu Fee", createdClu.getFeeInfo().getDescr().getPlain());
		assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().size());
		assertEquals("FEE-TYPE-X", createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeType());
		assertEquals("RATE-TYPE-X", createdClu.getFeeInfo().getCluFeeRecords().get(0).getRateType());
		assertEquals("Clu Fee Record", createdClu.getFeeInfo().getCluFeeRecords().get(0).getDescr().getPlain());
		assertEquals(3, createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().size());
		assertEquals(Integer.valueOf(200), createdClu.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyQuantity());
		assertEquals(0, createdClu.getFeeInfo().getCluFeeRecords().get(1).getFeeAmounts().size());

		assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().size());
		assertEquals(35l, (long)createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getPercentage());

		assertTrue(createdClu.isHasEarlyDropDeadline());
		assertTrue(createdClu.isHazardousForDisabledStudents());

		assertEquals("EXT-orgId-1", createdClu.getPrimaryInstructor()
				.getOrgId());
		assertEquals("EXT-personId-1", createdClu.getPrimaryInstructor()
				.getPersonId());
		assertEquals("PrimaryInstAttrValue1", createdClu.getPrimaryInstructor()
				.getAttributes().get("PrimaryInstAttrKey1"));
		assertEquals("PrimaryInstAttrValue2", createdClu.getPrimaryInstructor()
				.getAttributes().get("PrimaryInstAttrKey2"));

		assertEquals("EXT-orgId-2", createdClu.getInstructors().get(0)
				.getOrgId());
		assertEquals("EXT-personId-2", createdClu.getInstructors().get(0)
				.getPersonId());
		assertEquals("Inst1AttrValue1", createdClu.getInstructors().get(0)
				.getAttributes().get("Inst1AttrKey1"));
		assertEquals("Inst1AttrValue2", createdClu.getInstructors().get(0)
				.getAttributes().get("Inst1AttrKey2"));

		assertEquals("EXT-orgId-3", createdClu.getInstructors().get(1)
				.getOrgId());
		assertEquals("EXT-personId-3", createdClu.getInstructors().get(1)
				.getPersonId());
		assertEquals("Inst2AttrValue1", createdClu.getInstructors().get(1)
				.getAttributes().get("Inst2AttrKey1"));
		assertEquals("Inst2AttrValue2", createdClu.getInstructors().get(1)
				.getAttributes().get("Inst2AttrKey2"));

		assertEquals("luCode1.key", createdClu.getLuCodes().get(0).getId());
		assertEquals("luCode1-desc", createdClu.getLuCodes().get(0).getDescr());
		assertEquals("luCode1-value", createdClu.getLuCodes().get(0).getValue());
		assertEquals("luCode1AttrValue1", createdClu.getLuCodes().get(0)
				.getAttributes().get("luCode1AttrKey1"));
		assertEquals("luCode1AttrValue2", createdClu.getLuCodes().get(0)
				.getAttributes().get("luCode1AttrKey2"));
		assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo());
		assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo()
				.getVersionInd());
		assertNotNull(createdClu.getLuCodes().get(0).getMetaInfo()
				.getCreateTime());

		assertEquals("luCode2.key", createdClu.getLuCodes().get(1).getId());
		assertEquals("luCode2-desc", createdClu.getLuCodes().get(1).getDescr());
		assertEquals("luCode2-value", createdClu.getLuCodes().get(1).getValue());
		assertEquals("luCode2AttrValue1", createdClu.getLuCodes().get(1)
				.getAttributes().get("luCode2AttrKey1"));
		assertEquals("luCode2AttrValue2", createdClu.getLuCodes().get(1)
				.getAttributes().get("luCode2AttrKey2"));
		assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo());
		assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo()
				.getVersionInd());
		assertNotNull(createdClu.getLuCodes().get(1).getMetaInfo()
				.getCreateTime());


		assertEquals("nextReviewPeriod", createdClu.getNextReviewPeriod());

		assertEquals("offeredAtpType1", createdClu.getOfferedAtpTypes().get(0));
		assertEquals("offeredAtpType2", createdClu.getOfferedAtpTypes().get(1));


		assertEquals("http://student.kuali.org/clus", createdClu
				.getReferenceURL());

		assertEquals("Clu-state", createdClu.getState());

		assertEquals("EXT-stdDuration-Id1", createdClu.getStdDuration()
				.getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(7867), createdClu.getStdDuration()
				.getTimeQuantity());

		assertEquals("luType.shell.course", createdClu.getType());

		assertNotNull(createdClu.getMetaInfo());
		assertNotNull(createdClu.getMetaInfo().getVersionInd());
		assertNotNull(createdClu.getMetaInfo().getCreateTime());

		assertNotNull(createdClu.getId());

		checkCampusLocationCreate(createdClu);

		checkIntensityCreate(createdClu);

		checkAccreditationListCreate(createdClu);

		checkAdminOrgsCreate(createdClu);

		// Now Update the Clu!
		createdClu.getAccountingInfo().getAttributes().put(
				"AccountingAttrKey1", "AccountingAttrValue1");
		createdClu.getAccountingInfo().getAttributes().remove(
				"AccountingAttrKey2");
		createdClu.getAccountingInfo().getAttributes().put(
				"AccountingAttrKey3", "AccountingAttrValue3");

		createdClu.getOfficialIdentifier().setCode("UPoffId-code");
		createdClu.getOfficialIdentifier().setDivision("UPoffId-division");
		createdClu.getOfficialIdentifier().setLevel("UPoffId-level");
		createdClu.getOfficialIdentifier().setSuffixCode("UPoffId-suffixcode");
		createdClu.getOfficialIdentifier().setLongName("UPoffId-longName");
		createdClu.getOfficialIdentifier().setShortName("UPoffId-shortName");
		createdClu.getOfficialIdentifier().setState("UPoffId-state");
		createdClu.getOfficialIdentifier().setType("UPoffId-type");
		createdClu.getOfficialIdentifier().setVariation("UPoffId-variation");

		createdClu.getAlternateIdentifiers().get(0).setCode("UPcluId1-code");
		createdClu.getAlternateIdentifiers().get(0).setDivision(
				"UPcluId1-division");
		createdClu.getAlternateIdentifiers().get(0).setLevel("UPcluId1-level");
		createdClu.getAlternateIdentifiers().get(0).setSuffixCode(
				"UPcluId1-suffixcode");
		createdClu.getAlternateIdentifiers().get(0).setLongName(
				"UPcluId1-longName");
		createdClu.getAlternateIdentifiers().get(0).setShortName(
				"UPcluId1-shortName");
		createdClu.getAlternateIdentifiers().get(0).setState("UPcluId1-state");
		createdClu.getAlternateIdentifiers().get(0).setType("UPcluId1-type");
		createdClu.getAlternateIdentifiers().get(0).setVariation(
				"UPcluId1-variation");

		createdClu.getAlternateIdentifiers().remove(1);

		CluIdentifierInfo cluId3 = new CluIdentifierInfo();
		cluId3.setCode("cluId3-code");
		cluId3.setDivision("cluId3-division");
		cluId3.setSuffixCode("cluId3-suffixcode");
		cluId3.setLevel("cluId3-level");
		cluId3.setLongName("cluId3-longName");
		cluId3.setShortName("cluId3-shortName");
		cluId3.setState("cluId3-state");
		cluId3.setType("cluId3-type");
		cluId3.setVariation("cluId3-variation");
		createdClu.getAlternateIdentifiers().add(cluId3);

		createdClu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
		createdClu.getAttributes().remove("cluAttrKey2");
		createdClu.getAttributes().put("cluAttrKey3", "cluAttrValue3");

		createdClu.setCanCreateLui(false);

		createdClu.setDefaultEnrollmentEstimate(9545);
		createdClu.setDefaultMaximumEnrollment(9999);

		createdClu.getDescr().setFormatted("UP<p>DESC FORMATTED</p>");
		createdClu.getDescr().setPlain("UPDESC PLAIN");

		createdClu.setEffectiveDate(DF.parse("20190203"));
		createdClu.setExpirationDate(DF.parse("21091231"));

		createdClu.setEnrollable(false);

		createdClu.getFeeInfo().getAttributes().put("FeeAttrKey1",
				"FeeAttrValue1");
		createdClu.getFeeInfo().getAttributes().remove("FeeAttrKey2");
		createdClu.getFeeInfo().getAttributes().put("FeeAttrKey3",
				"FeeAttrValue3");

		createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().remove(0);
		createdClu.getFeeInfo().getCluFeeRecords().get(1).setFeeType("FEE-TYPE-Z");

		createdClu.setHasEarlyDropDeadline(false);

		createdClu.setHazardousForDisabledStudents(false);

		createdClu.getPrimaryInstructor().setOrgId("UPEXT-orgId-1");
		createdClu.getPrimaryInstructor().setPersonId("UPEXT-personId-1");
		createdClu.getPrimaryInstructor().getAttributes().put(
				"PrimaryInstAttrKey1", "PrimaryInstAttrValue1");
		createdClu.getPrimaryInstructor().getAttributes().remove(
				"PrimaryInstAttrKey2");
		createdClu.getPrimaryInstructor().getAttributes().put(
				"PrimaryInstAttrKey3", "PrimaryInstAttrValue3");

		createdClu.getInstructors().get(0).setOrgId("UPEXT-orgId-2");
		createdClu.getInstructors().get(0).setPersonId("UPEXT-personId-2");
		createdClu.getInstructors().get(0).getAttributes().put("Inst1AttrKey1",
				"Inst1AttrValue1");
		createdClu.getInstructors().get(0).getAttributes().remove(
				"Inst1AttrKey2");
		createdClu.getInstructors().get(0).getAttributes().put("Inst1AttrKey3",
				"Inst1AttrValue3");

		createdClu.getInstructors().remove(1);

		CluInstructorInfo instructor3 = new CluInstructorInfo();
		instructor3.setOrgId("EXT-orgId-3");
		instructor3.setPersonId("EXT-personId-3");
		instructor3.getAttributes().put("Inst3AttrKey1", "Inst3AttrValue1");
		instructor3.getAttributes().put("Inst3AttrKey2", "Inst3AttrValue2");
		createdClu.getInstructors().add(instructor3);

		createdClu.getLuCodes().get(0).setDescr("UPluCode1-desc");
		createdClu.getLuCodes().get(0).setValue("UPluCode1-value");
		createdClu.getLuCodes().get(0).getAttributes().put("luCode1AttrKey1",
				"luCode1AttrValue1");
		createdClu.getLuCodes().get(0).getAttributes()
				.remove("luCode1AttrKey2");
		createdClu.getLuCodes().get(0).getAttributes().put("luCode1AttrKey3",
				"luCode1AttrValue3");
		createdClu.getLuCodes().get(0).setType("updatedType");

		createdClu.getLuCodes().remove(1);

		LuCodeInfo luCode3 = new LuCodeInfo();
		luCode3.setId("luCode3.key");
		luCode3.setDescr("luCode3-desc");
		luCode3.setValue("luCode3-value");
		luCode3.getAttributes().put("luCode3AttrKey1", "luCode3AttrValue1");
		luCode3.getAttributes().put("luCode3AttrKey2", "luCode3AttrValue2");
		luCode3.setType("updatedType");
		createdClu.getLuCodes().add(luCode3);


		createdClu.setNextReviewPeriod("UPnextReviewPeriod");

		createdClu.getOfferedAtpTypes().remove(1);
		createdClu.getOfferedAtpTypes().add("offeredAtpType3");


		CluInstructorInfo pubInstructor3 = new CluInstructorInfo();
		pubInstructor3.setOrgId("EXT-orgId-3");
		pubInstructor3.setPersonId("EXT-personId-3");
		pubInstructor3.getAttributes().put("PubInst3AttrKey1",
				"PubInst3AttrValue1");
		pubInstructor3.getAttributes().put("PubInst3AttrKey2",
				"PubInst3AttrValue2");

		createdClu.setReferenceURL("UPhttp://student.kuali.org/clus");

		createdClu.setState("UPClu-state");

		createdClu.getStdDuration().setAtpDurationTypeKey(
				"UPEXT-stdDuration-Id1");
		createdClu.getStdDuration().setTimeQuantity(new Integer(97867));

		createdClu.setType("luType.shell.program");

		updateAdminOrgs(createdClu);

		updateAccreditationList(createdClu);

		updateIntensity(createdClu);

		updateCampusLocationList(createdClu);

		// Do Update
		CluInfo updatedClu = client.updateClu(createdClu.getId(), createdClu);

		// Validate Results
		assertNotNull(updatedClu);

		assertEquals("AccountingAttrValue1", updatedClu.getAccountingInfo()
				.getAttributes().get("AccountingAttrKey1"));
		assertEquals("AccountingAttrValue3", updatedClu.getAccountingInfo()
				.getAttributes().get("AccountingAttrKey3"));
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
				.getState());
		assertEquals("UPoffId-type", updatedClu.getOfficialIdentifier()
				.getType());
		assertEquals("UPoffId-variation", updatedClu.getOfficialIdentifier()
				.getVariation());

		assertEquals("UPcluId1-divisionUPcluId1-suffixcode", updatedClu
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
				.get(0).getState());
		assertEquals("UPcluId1-type", updatedClu.getAlternateIdentifiers().get(
				0).getType());
		assertEquals("UPcluId1-variation", updatedClu.getAlternateIdentifiers()
				.get(0).getVariation());

		assertEquals("cluId3-divisioncluId3-suffixcode", updatedClu
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
				.get(1).getState());
		assertEquals("cluId3-type", updatedClu.getAlternateIdentifiers().get(1)
				.getType());
		assertEquals("cluId3-variation", updatedClu.getAlternateIdentifiers()
				.get(1).getVariation());

		assertEquals(2, updatedClu.getAlternateIdentifiers().size());

		assertEquals("cluAttrValue1", updatedClu.getAttributes().get(
				"cluAttrKey1"));
		assertEquals("cluAttrValue3", updatedClu.getAttributes().get(
				"cluAttrKey3"));
		assertEquals(2, updatedClu.getAttributes().size());

		assertFalse(updatedClu.isCanCreateLui());

		assertEquals(9545, updatedClu.getDefaultEnrollmentEstimate());
		assertEquals(9999, updatedClu.getDefaultMaximumEnrollment());

		assertEquals("UP<p>DESC FORMATTED</p>", updatedClu.getDescr()
				.getFormatted());
		assertEquals("UPDESC PLAIN", updatedClu.getDescr().getPlain());

		assertEquals(DF.parse("20190203"), updatedClu.getEffectiveDate());
		assertEquals(DF.parse("21091231"), updatedClu.getExpirationDate());

		assertFalse(updatedClu.isEnrollable());

		assertEquals("FeeAttrValue1", updatedClu.getFeeInfo().getAttributes()
				.get("FeeAttrKey1"));
		assertEquals("FeeAttrValue3", updatedClu.getFeeInfo().getAttributes()
				.get("FeeAttrKey3"));
		assertEquals(2, updatedClu.getFeeInfo().getAttributes().size());

		assertEquals(2, createdClu.getFeeInfo().getCluFeeRecords().size());
		assertEquals("FEE-TYPE-Z", createdClu.getFeeInfo().getCluFeeRecords().get(1).getFeeType());

		assertEquals(1, createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().size());
		assertEquals(65l, (long)createdClu.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getPercentage());

		assertFalse(updatedClu.isHasEarlyDropDeadline());
		assertFalse(updatedClu.isHazardousForDisabledStudents());

		assertEquals("UPEXT-orgId-1", updatedClu.getPrimaryInstructor()
				.getOrgId());
		assertEquals("UPEXT-personId-1", updatedClu.getPrimaryInstructor()
				.getPersonId());
		assertEquals("PrimaryInstAttrValue1", updatedClu.getPrimaryInstructor()
				.getAttributes().get("PrimaryInstAttrKey1"));
		assertEquals("PrimaryInstAttrValue3", updatedClu.getPrimaryInstructor()
				.getAttributes().get("PrimaryInstAttrKey3"));
		assertEquals(2, updatedClu.getPrimaryInstructor().getAttributes()
				.size());

		assertEquals(2, updatedClu.getInstructors().size());

		assertEquals("UPEXT-orgId-2", updatedClu.getInstructors().get(0)
				.getOrgId());
		assertEquals("UPEXT-personId-2", updatedClu.getInstructors().get(0)
				.getPersonId());
		assertEquals("Inst1AttrValue1", updatedClu.getInstructors().get(0)
				.getAttributes().get("Inst1AttrKey1"));
		assertEquals("Inst1AttrValue3", updatedClu.getInstructors().get(0)
				.getAttributes().get("Inst1AttrKey3"));
		assertEquals(2, updatedClu.getInstructors().get(0).getAttributes()
				.size());

		assertEquals("EXT-orgId-3", updatedClu.getInstructors().get(1)
				.getOrgId());
		assertEquals("EXT-personId-3", updatedClu.getInstructors().get(1)
				.getPersonId());
		assertEquals("Inst3AttrValue1", updatedClu.getInstructors().get(1)
				.getAttributes().get("Inst3AttrKey1"));
		assertEquals("Inst3AttrValue2", updatedClu.getInstructors().get(1)
				.getAttributes().get("Inst3AttrKey2"));
		assertEquals(2, updatedClu.getInstructors().get(1).getAttributes()
				.size());

		assertEquals(2, updatedClu.getLuCodes().size());

		assertEquals("luCode1.key", updatedClu.getLuCodes().get(0).getId());
		assertEquals("UPluCode1-desc", updatedClu.getLuCodes().get(0).getDescr());
		assertEquals("UPluCode1-value", updatedClu.getLuCodes().get(0)
				.getValue());
		assertEquals("luCode1AttrValue1", updatedClu.getLuCodes().get(0)
				.getAttributes().get("luCode1AttrKey1"));
		assertEquals("luCode1AttrValue3", updatedClu.getLuCodes().get(0)
				.getAttributes().get("luCode1AttrKey3"));
		assertEquals(2, updatedClu.getLuCodes().get(0).getAttributes().size());
		assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo());
		assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo()
				.getVersionInd());
		assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo()
				.getCreateTime());
		assertNotNull(updatedClu.getLuCodes().get(0).getMetaInfo()
				.getUpdateTime());

		assertEquals("luCode3.key", updatedClu.getLuCodes().get(1).getId());
		assertEquals("luCode3-desc", updatedClu.getLuCodes().get(1).getDescr());
		assertEquals("luCode3-value", updatedClu.getLuCodes().get(1).getValue());
		assertEquals("luCode3AttrValue1", updatedClu.getLuCodes().get(1)
				.getAttributes().get("luCode3AttrKey1"));
		assertEquals("luCode3AttrValue2", updatedClu.getLuCodes().get(1)
				.getAttributes().get("luCode3AttrKey2"));
		assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo());
		assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo()
				.getVersionInd());
		assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo()
				.getCreateTime());
		assertNotNull(updatedClu.getLuCodes().get(1).getMetaInfo()
				.getUpdateTime());

		assertEquals("UPnextReviewPeriod", updatedClu.getNextReviewPeriod());

		assertEquals("offeredAtpType1", updatedClu.getOfferedAtpTypes().get(0));
		assertEquals("offeredAtpType3", updatedClu.getOfferedAtpTypes().get(1));
		assertEquals(2, updatedClu.getOfferedAtpTypes().size());


		assertEquals("UPhttp://student.kuali.org/clus", updatedClu
				.getReferenceURL());

		assertEquals("UPClu-state", updatedClu.getState());

		assertEquals("UPEXT-stdDuration-Id1", updatedClu.getStdDuration()
				.getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(97867), updatedClu.getStdDuration()
				.getTimeQuantity());

		assertEquals("luType.shell.program", updatedClu.getType());

		assertNotNull(updatedClu.getMetaInfo());
		assertNotNull(updatedClu.getMetaInfo().getVersionInd());
		assertNotNull(updatedClu.getMetaInfo().getCreateTime());
		assertNotNull(updatedClu.getMetaInfo().getUpdateTime());

		assertEquals(createdClu.getId(), updatedClu.getId());

		checkAdminOrgUpdate(updatedClu);

		checkAccreditationListUpdate(updatedClu);

		checkCampusLocationUpdate(updatedClu);

		checkIntensityUpdate(updatedClu);

		// Test Optimistic locking
		try {
			updatedClu = client.updateClu(createdClu.getId(), createdClu);
			fail("Should have thrown VersionMismatchException");
		} catch (VersionMismatchException e) {

		}

		// Test Delete
		try {
			client.getClu(createdClu.getId());
		} catch (DoesNotExistException e) {
			fail("Should not have thrown DoesNotExistException");
		}

		StatusInfo status = client.deleteClu(createdClu.getId());
		assertTrue(status.getSuccess());

		try {
			client.getClu(createdClu.getId());
			fail("Should have thrown DoesNotExistException");
		} catch (DoesNotExistException e) {

		}

	}

	@Test
	public void testCluCluRelationCrud() throws Exception {

		final CluCluRelationInfo cluCluRelationInfo = new CluCluRelationInfo();

		final Date effectiveDate = DF.parse("20080101"), expirationDate = DF
				.parse("20100101");
		cluCluRelationInfo.setEffectiveDate(effectiveDate);
		cluCluRelationInfo.setExpirationDate(expirationDate);
		cluCluRelationInfo.setIsCluRelationRequired(true);
		cluCluRelationInfo.setState("hello");
		cluCluRelationInfo.getAttributes().put("clucluAttrKey1",
				"clucluAttrValue1");
		cluCluRelationInfo.getAttributes().put("clucluAttrKey2",
				"clucluAttrValue2");
		cluCluRelationInfo.getAttributes().put("clucluAttrKey3",
				"clucluAttrValue3");

		CluCluRelationInfo created = client.createCluCluRelation("CLU-1",
				"CLU-2", "luLuType.type1", cluCluRelationInfo);

		assertEquals(effectiveDate, created.getEffectiveDate());
		assertEquals(expirationDate, created.getExpirationDate());
		assertEquals(true, created.getIsCluRelationRequired());
		assertEquals("hello", created.getState());
		assertEquals("CLU-1", created.getCluId());
		assertEquals("CLU-2", created.getRelatedCluId());
		assertEquals("luLuType.type1", created.getType());
		assertEquals("clucluAttrValue1", created.getAttributes().get(
				"clucluAttrKey1"));
		assertEquals("clucluAttrValue2", created.getAttributes().get(
				"clucluAttrKey2"));
		assertEquals("clucluAttrValue3", created.getAttributes().get(
				"clucluAttrKey3"));
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

		CluCluRelationInfo updated = client.updateCluCluRelation(created
				.getId(), created);

		assertEquals(expirationDate, updated.getEffectiveDate());
		assertEquals(effectiveDate, updated.getExpirationDate());
		assertEquals(false, updated.getIsCluRelationRequired());
		assertEquals("updated hello", updated.getState());
		assertEquals("CLU-2", updated.getCluId());
		assertEquals("CLU-3", updated.getRelatedCluId());
		assertEquals("luLuType.type2", updated.getType());
		assertEquals("clucluAttrValue1", updated.getAttributes().get(
				"clucluAttrKey1"));
		assertNull(updated.getAttributes().get("clucluAttrKey2"));
		assertEquals("clucluAttrValue3-A", updated.getAttributes().get(
				"clucluAttrKey3"));
		assertEquals("clucluAttrValue4", updated.getAttributes().get(
				"clucluAttrKey4"));
		assertNotNull(created.getId());
		assertNotNull(created.getMetaInfo().getCreateTime());
		assertNotNull(created.getMetaInfo().getVersionInd());

		// Test Delete
		try {
			client.getCluCluRelation(created.getId());
		} catch (DoesNotExistException e) {
			fail("Should not have thrown DoesNotExistException");
		}

		StatusInfo status = client.deleteCluCluRelation(created.getId());
		assertTrue(status.getSuccess());

		try {
			client.getCluCluRelation(created.getId());
			fail("Should have thrown DoesNotExistException");
		} catch (DoesNotExistException e) {

		}

		List<String> relatedCluIdsByCluId = client.getRelatedCluIdsByCluId(
				"CLU-1", "luLuType.type1");

		assertEquals(2, relatedCluIdsByCluId.size());
		assertTrue(relatedCluIdsByCluId.contains("CLU-2"));
		assertTrue(relatedCluIdsByCluId.contains("CLU-3"));

		List<CluInfo> relatedClusByCluId = client.getRelatedClusByCluId(
				"CLU-1", "luLuType.type1");
		assertEquals(2, relatedClusByCluId.size());
	}

	@Test
	public void testLuiLuiRelationCrud() throws Exception {

		LuiLuiRelationInfo luiLuiRelationInfo = new LuiLuiRelationInfo();

		luiLuiRelationInfo.setEffectiveDate(DF.parse("20080101"));
		luiLuiRelationInfo.setExpirationDate(DF.parse("20100101"));
		luiLuiRelationInfo.setState("hello");
		luiLuiRelationInfo.setType("goodbye");
		luiLuiRelationInfo.getAttributes().put("luiluiAttrKey1",
				"luiluiAttrValue1");
		luiLuiRelationInfo.getAttributes().put("luiluiAttrKey2",
				"luiluiAttrValue2");

		LuiLuiRelationInfo created = client.createLuiLuiRelation("LUI-1",
				"LUI-2", "luLuType.type1", luiLuiRelationInfo);

		assertEquals(DF.parse("20080101"), created.getEffectiveDate());
		assertEquals(DF.parse("20100101"), created.getExpirationDate());
		assertEquals("hello", created.getState());
		assertEquals("luLuType.type1", created.getType());
		assertEquals("LUI-1", created.getLuiId());
		assertEquals("LUI-2", created.getRelatedLuiId());
		assertEquals("luiluiAttrValue1", created.getAttributes().get(
				"luiluiAttrKey1"));
		assertEquals("luiluiAttrValue2", created.getAttributes().get(
				"luiluiAttrKey2"));
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

		LuiLuiRelationInfo updated = client.updateLuiLuiRelation(created
				.getId(), created);

		assertEquals(DF.parse("20980101"), updated.getEffectiveDate());
		assertEquals(DF.parse("20190101"), updated.getExpirationDate());
		assertEquals("sawyer", updated.getState());
		assertEquals("luLuType.type2", updated.getType());
		assertEquals("LUI-2", updated.getLuiId());
		assertEquals("LUI-3", updated.getRelatedLuiId());
		assertEquals("UPluiluiAttrValue1", updated.getAttributes().get(
				"luiluiAttrKey1"));
		assertEquals("luiluiAttrValue3", updated.getAttributes().get(
				"luiluiAttrKey3"));
		assertEquals(2, updated.getAttributes().size());
		assertEquals(created.getId(), updated.getId());
		assertNotNull(updated.getMetaInfo().getUpdateTime());

		try {
			updated = client.updateLuiLuiRelation(created.getId(), created);
			fail("Should have thrown VersionMismatchException");
		} catch (VersionMismatchException e) {
		}

		try {
			client.getLuiLuiRelation(created.getId());
		} catch (DoesNotExistException e) {
			fail("Should not have thrown DoesNotExistException");
		}

		StatusInfo status = client.deleteLuiLuiRelation(updated.getId());

		assertTrue(status.getSuccess());

		try {
			client.getLuiLuiRelation(created.getId());
			fail("Should have thrown DoesNotExistException");
		} catch (DoesNotExistException e) {

		}

		// TestFind
		List<LuiLuiRelationInfo> relations = client
				.getLuiLuiRelationsByLui("LUI-1");
		assertEquals(2, relations.size());
		relations = client.getLuiLuiRelationsByLui("LUI-2");
		assertEquals(1, relations.size());
		relations = client.getLuiLuiRelationsByLui("LUI-3");
		assertTrue(relations == null || relations.size() == 0);

		List<String> relatedLuiIdsByLuiId = client.getRelatedLuiIdsByLuiId(
				"LUI-1", "luLuType.type1");
		assertEquals(2, relatedLuiIdsByLuiId.size());
		assertTrue(relatedLuiIdsByLuiId.contains("LUI-2"));
		assertTrue(relatedLuiIdsByLuiId.contains("LUI-3"));

		List<LuiInfo> relatedLuisByLuiId = client.getRelatedLuisByLuiId(
				"LUI-1", "luLuType.type1");
		assertEquals(2, relatedLuisByLuiId.size());

	}

	@Test
	public void testGetLuisByIdList() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<LuiInfo> luiInfos;
		try {
			luiInfos = client.getLuisByIdList(null);
			fail("LuService.getLuiByIdList() did not throw MissingParameterException for null Lui ID");
		} catch (MissingParameterException mpe) {
		} catch (Exception e) {
			fail("LuService.getLuiByIdList() threw unexpected "
					+ e.getClass().getSimpleName() + " for null Lui ID");
		}
		luiInfos = client.getLuisByIdList(Arrays.asList("Not a LUI ID",
				"Another one that ain't"));
		assertTrue(luiInfos == null || luiInfos.size() == 0);

		luiInfos = client.getLuisByIdList(Arrays.asList("LUI-1", "LUI-4"));
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

		LuiInfo luiInfo;

		// Read
		try {
			luiInfo = client.getLui("notARealLui");
			fail("LuService.getLui() did not throw DoesNotExistException for non-existent Lui");
		} catch (DoesNotExistException dnee) {
		} catch (Exception e) {
			fail("LuService.getLui() threw unexpected "
					+ e.getClass().getSimpleName() + " for null Lui ID");
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
		assertEquals("luiAttrValue1", createdLui.getAttributes().get(
				"luiAttrKey1"));
		assertEquals("luiAttrValue2", createdLui.getAttributes().get(
				"luiAttrKey2"));

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
		assertEquals("luiAttrValue1Updated", updatedLui.getAttributes().get(
				"luiAttrKey1"));
		assertEquals("luiAttrValue2Updated", updatedLui.getAttributes().get(
				"luiAttrKey2"));

		// optimistic locking working?
		try {
			client.updateLui(createdLui.getId(), createdLui);
			fail("LuService.updateLui did not throw expected VersionMismatchException");
		} catch (VersionMismatchException e) {
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
	public void testGetLuiIdsByCluId() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
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
		assertTrue(null == luiIds || luiIds.size() == 0);
	}

	@Test
	public void testGetLuiIdsInAtpByCluId() throws DoesNotExistException,
			InvalidParameterException, OperationFailedException,
			MissingParameterException {
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
		assertTrue(null == luiIds || luiIds.size() == 0);
		luiIds = client.getLuiIdsInAtpByCluId("CLU-2", "Non-existent ATP");
		assertTrue(null == luiIds || luiIds.size() == 0);
	}

	@Test
	public void testGetLuLuRelationTypeInfo() throws OperationFailedException,
			DoesNotExistException, MissingParameterException {
		LuLuRelationTypeInfo luLuRelTypeInfo;

		try {
			client.getLuLuRelationType(null);
			fail("LuService.getLuLuRelationTypeInfo() did not throw MissingParameterException for null LuLuRelationType key");
		} catch (MissingParameterException e) {
		}
		luLuRelTypeInfo = client.getLuLuRelationType("luLuType.type1");
		assertEquals("bob", luLuRelTypeInfo.getName());
		luLuRelTypeInfo = client.getLuLuRelationType("luLuType.type2");
		assertEquals("my desc2", luLuRelTypeInfo.getDescr());
		assertEquals("rev name2", luLuRelTypeInfo.getRevName());
		assertEquals("rev desc2", luLuRelTypeInfo.getRevDesc());
		try {
			client.getLuLuRelationType("Non-existent LuLuRelationType");
			fail("LuService.getLuLuRelationTypeInfo() did not throw DoesNotExistException when retrieving non-existent LuLuRelationType");
		} catch (DoesNotExistException dnee) {
		}
	}

	@Test
	public void testGetLuLuRelationTypeInfos() throws OperationFailedException,
			DoesNotExistException, MissingParameterException {
		List<LuLuRelationTypeInfo> luLuRelTypeInfos;
		luLuRelTypeInfos = client.getLuLuRelationTypes();
		Collections.sort(luLuRelTypeInfos,
				new Comparator<LuLuRelationTypeInfo>() {
					@Override
                    public int compare(LuLuRelationTypeInfo o1,
							LuLuRelationTypeInfo o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});
        assertEquals(13, luLuRelTypeInfos.size());
        assertEquals("kuali.lu.lu.relation.type.hasCoreProgram", luLuRelTypeInfos.get(0).getId());
	}

	@Test
	public void testUpdateLuiState() throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			OperationFailedException, PermissionDeniedException,
			ParseException, AlreadyExistsException, MissingParameterException,
			DependentObjectsExistException {
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

		// create a Lui whose state we'll update. Create a new one so its
		// MetaInfo gets created in prePersist()
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
		LuiInfo updatedLui = client.updateLuiState(createdLui.getId(),
				"Activated");
		assertEquals("Activated", updatedLui.getState());

		// and now explicitly retrieve it without a call to updateLuiState and
		// confirm same
		updatedLui = client.getLui(createdLui.getId());
		assertEquals("Activated", updatedLui.getState());

		// and delete it to keep db consistent for other tests
		client.deleteLui(updatedLui.getId());
	}

	@Test
	public void testGetLuisByRelation() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<LuiInfo> luis = client
				.getLuisByRelation("LUI-1", "luLuType.type1");
		assertTrue(luis == null || luis.size() == 0);
		luis = client.getLuisByRelation("LUI-2", "luLuType.type1");
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
		List<String> luis = client.getLuiIdsByRelation("LUI-1",
				"luLuType.type1");
		assertTrue(luis == null || luis.size() == 0);
		luis = client.getLuiIdsByRelation("LUI-2", "luLuType.type1");
		Collections.sort(luis);
		assertEquals(1, luis.size());
		assertEquals("LUI-1", luis.get(0));
	}

	@Test
	public void testOutcomeLO() throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DependentObjectsExistException,
			ParseException, DataValidationErrorException,
			VersionMismatchException {

		CluLoRelationInfo reltnInfo = new CluLoRelationInfo();
		reltnInfo.setCluId("CLU-1");
		reltnInfo.setLoId("LO-1");
		reltnInfo.setState("FINAL");
		reltnInfo.setType("GRADE");
		reltnInfo.setEffectiveDate(DF.parse("20101203"));
		reltnInfo.setExpirationDate(DF.parse("20801231"));

		CluLoRelationInfo crReltnInfo = client.createCluLoRelation("CLU-1",
				"LO-1", "GRADE", reltnInfo);

		assertEquals(crReltnInfo.getCluId(), "CLU-1");
		assertEquals(crReltnInfo.getLoId(), "LO-1");
		assertEquals(crReltnInfo.getState(), "FINAL");
		assertEquals(crReltnInfo.getType(), "GRADE");

		try {
			client.createCluLoRelation("CLU-1", "LO-1", "GRADE", reltnInfo);
			fail("Should have thrown AlreadyExistsException");
		} catch (AlreadyExistsException e) {

		}

		try {
			reltnInfo.setCluId("MISSING CLU");
			client.createCluLoRelation("MISSING CLU", "LO-1", "GRADE",
					reltnInfo);
			fail("Should have thrown DoesNotExistException");
		} catch (DoesNotExistException e) {

		}

		CluLoRelationInfo gtReltnInfo = client.getCluLoRelation(crReltnInfo
				.getId());

		assertEquals(gtReltnInfo.getCluId(), "CLU-1");
		assertEquals(gtReltnInfo.getLoId(), "LO-1");
		assertEquals(gtReltnInfo.getState(), "FINAL");
		assertEquals(gtReltnInfo.getType(), "GRADE");

		CluLoRelationInfo reltnInfo1 = new CluLoRelationInfo();
		reltnInfo1.setCluId("CLU-1");
		reltnInfo1.setLoId("LO-2");
		reltnInfo1.setState("FINAL");
		reltnInfo1.setType("GRADE");
		reltnInfo1.setEffectiveDate(DF.parse("20101203"));
		reltnInfo1.setExpirationDate(DF.parse("20801231"));

		CluLoRelationInfo crReltnInfo1 = client.createCluLoRelation("CLU-1",
				"LO-2", "GRADE", reltnInfo1);

		List<CluLoRelationInfo> reltns = client.getCluLoRelationsByClu("CLU-1");
		assertEquals(2, reltns.size());

		List<CluLoRelationInfo> reltns1 = client.getCluLoRelationsByLo("LO-1");
		assertEquals(1, reltns1.size());
		assertEquals(reltns1.get(0).getCluId(), "CLU-1");

		// Test update relation
		crReltnInfo1.setType("NEW");
		CluLoRelationInfo updateReltn = client.updateCluLoRelation(crReltnInfo1
				.getId(), crReltnInfo1);
		assertEquals("NEW", updateReltn.getType());

		StatusInfo status = client.deleteCluLoRelation(crReltnInfo.getId());
		assertTrue(status.getSuccess());

		status = client.deleteCluLoRelation(updateReltn.getId());
		assertTrue(status.getSuccess());

	}

	@Test
	public void testResultUsageType() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		ResultUsageTypeInfo lrType = client.getResultUsageType("lrType.finalGrade");
		assertEquals("Final Grade", lrType.getName());
		List<ResultUsageTypeInfo> lrTypes = client.getResultUsageTypes();
		assertEquals(2, lrTypes.size());

	}

	@Test
	public void testGetClusByRelation() throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ParseException, VersionMismatchException {
		List<CluInfo> clus = client
				.getClusByRelation("CLU-1", "luLuType.type1");
		assertNotNull(clus);
		assertEquals(2, clus.size());

		clus = client.getClusByRelation("CLUXX-2", "luLuType.type1");
		assertTrue(clus == null || clus.size() == 0);

		clus = client.getClusByRelation("CLU-2", "luLuType.type1XX");
		assertTrue(clus == null || clus.size() == 0);

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
		assertTrue(null == ids || ids.size() == 0);

		ids = client.getCluIdsByRelation("CLU-2", "luLuType.type1XX");
		assertTrue(null == ids || ids.size() == 0);

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

	//@Test
	public void testSearchForResults() throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ParseException, VersionMismatchException {
		List<SearchParam> queryParamValues = new ArrayList<SearchParam>(
				0);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("lu.search.clus");
		searchRequest.setParams(queryParamValues);
		SearchResult clus = client.search(searchRequest);
		Collections.sort(clus.getRows(), new Comparator<SearchResultRow>() {
			@Override
            public int compare(SearchResultRow o1, SearchResultRow o2) {
				return o1.getCells().get(0).getValue().compareTo(
						o2.getCells().get(0).getValue());
			}
		});
		assertNotNull(clus);
		assertEquals(103, clus.getRows().size());
		SearchResultRow result = clus.getRows().get(0);
		assertNotNull(result);

		List<SearchResultCell> resultCells = result.getCells();
		assertNotNull(resultCells);
		assertEquals(2, resultCells.size());

		SearchResultCell resultCell = resultCells.get(0);
		assertEquals("lu.resultColumn.cluId", resultCell.getKey());
		assertEquals("00000000-a389-4fd0-b349-1e649c20fd08", resultCell
				.getValue());
		resultCell = resultCells.get(1);
		assertEquals("lu.resultColumn.cluOfficialIdentifier.longName",
				resultCell.getKey());
		assertEquals("Advanced Applied Linear Algebra", resultCell.getValue());
	}

	@Test
	public void testSearchCourseLevelRanges() throws MissingParameterException {
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam courseLevelsParam = new SearchParam();
        courseLevelsParam.setKey("lu.queryParam.luOptionalCrsNoRange");
        courseLevelsParam.setValue("100 -200");
        queryParamValues.add(courseLevelsParam);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValues);
        SearchResult clus = client.search(searchRequest);
        Collections.sort(clus.getRows(), new Comparator<SearchResultRow>() {
            @Override
            public int compare(SearchResultRow o1, SearchResultRow o2) {
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

		CluInfo clu = new CluInfo();

		CluAccountingInfo accountingInfo = new CluAccountingInfo();
		accountingInfo.getAttributes().put("AccountingAttrKey1",
				"AccountingAttrValue1");
		accountingInfo.getAttributes().put("AccountingAttrKey2",
				"AccountingAttrValue2");
		clu.setAccountingInfo(accountingInfo);

		CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
		officialIdentifier.setType("kuali.lu.type.CreditCourse.identifier.official");
		officialIdentifier.setState("active");
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
		cluId1.setType("kuali.lu.type.CreditCourse.identifier.cross-listed");
		cluId1.setState("active");
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
		cluId2.setType("kuali.lu.type.CreditCourse.identifier.version");
		cluId2.setState("active");
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

		clu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
		clu.getAttributes().put("cluAttrKey2", "cluAttrValue2");

		clu.setCanCreateLui(true);

		// ERROR non negative integer
		clu.setDefaultEnrollmentEstimate(-545);
		clu.setDefaultMaximumEnrollment(999);

		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("<p>DESC FORMATTED</p>");
		desc.setPlain("DESC PLAIN");
		clu.setDescr(desc);

		clu.setEffectiveDate(DF.parse("20100203"));
		clu.setExpirationDate(DF.parse("21001231"));

		clu.setEnrollable(true);

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
		feeInfo.getAttributes().put("FeeAttrKey1", "FeeAttrValue1");
		feeInfo.getAttributes().put("FeeAttrKey2", "FeeAttrValue2");
		feeInfo.setCluFeeRecords(feeRecList);
		feeInfo.setDescr(cfDesc);
		clu.setFeeInfo(feeInfo);

		clu.setHasEarlyDropDeadline(true);

		clu.setHazardousForDisabledStudents(true);

		CluInstructorInfo primaryInstructor = new CluInstructorInfo();
		primaryInstructor.setOrgId("EXTorgIdone");
		primaryInstructor.setPersonId("EXTpersonIdone");
		primaryInstructor.getAttributes().put("PrimaryInstAttrKey1",
				"PrimaryInstAttrValue1");
		primaryInstructor.getAttributes().put("PrimaryInstAttrKey2",
				"PrimaryInstAttrValue2");
		clu.setPrimaryInstructor(primaryInstructor);

		CluInstructorInfo instructor1 = new CluInstructorInfo();
		instructor1.setOrgId("EXTorgIdtwo");
		instructor1.setPersonId("EXTpersonIdtwo");
		instructor1.getAttributes().put("Inst1AttrKey1", "Inst1AttrValue1");
		instructor1.getAttributes().put("Inst1AttrKey2", "Inst1AttrValue2");
		clu.getInstructors().add(instructor1);

		CluInstructorInfo instructor2 = new CluInstructorInfo();
		instructor2.setOrgId("EXTorgIdthree");
		instructor2.setPersonId("EXTpersonIdthree");
		instructor2.getAttributes().put("Inst2AttrKey1", "Inst2AttrValue1");
		instructor2.getAttributes().put("Inst2AttrKey2", "Inst2AttrValue2");
		clu.getInstructors().add(instructor2);

		LuCodeInfo luCode1 = new LuCodeInfo();
		luCode1.setId("luCode1.key");
		luCode1.setDescr("luCode1desc");
		luCode1.setValue("luCode1value");
		luCode1.getAttributes().put("luCode1AttrKey1", "luCode1AttrValue1");
		luCode1.getAttributes().put("luCode1AttrKey2", "luCode1AttrValue2");
		clu.getLuCodes().add(luCode1);

		LuCodeInfo luCode2 = new LuCodeInfo();
		luCode2.setId("luCode2.key");
		luCode2.setDescr("luCodetwodesc");
		luCode2.setValue("luCodetwovalue");
		luCode2.getAttributes().put("luCode2AttrKey1", "luCode2AttrValue1");
		luCode2.getAttributes().put("luCode2AttrKey2", "luCode2AttrValue2");
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
		pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey1",
				"PubPrimaryInstAttrValue1");
		pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey2",
				"PubPrimaryInstAttrValue2");

		CluInstructorInfo pubInstructor1 = new CluInstructorInfo();
		pubInstructor1.setOrgId("EXTorgIdtwo");
		pubInstructor1.setPersonId("EXT-personId-two");
		pubInstructor1.getAttributes().put("PubInst1AttrKey1",
				"PubInst1AttrValue1");
		pubInstructor1.getAttributes().put("PubInst1AttrKey2",
				"PubInst1AttrValue2");

		CluInstructorInfo pubInstructor2 = new CluInstructorInfo();
		pubInstructor2.setOrgId("EXTorgIdthree");
		pubInstructor2.setPersonId("EXTpersonIdthree");
		pubInstructor2.getAttributes().put("PubInst2AttrKey1",
				"PubInst2AttrValue1");
		pubInstructor2.getAttributes().put("PubInst2AttrKey2",
				"PubInst2AttrValue2");

		clu.setReferenceURL("http://student.kuali.org/clus");

		TimeAmountInfo stdDuration = new TimeAmountInfo();
		stdDuration.setAtpDurationTypeKey("EXTstdDurationId");
		stdDuration.setTimeQuantity(new Integer(7867));
		clu.setStdDuration(stdDuration);

		createCampusLocationList(clu);

		createIntensity(clu);

		createAccreditationList(clu);

		createAdminOrgs(clu);

		clu.setType("kuali.lu.type.CreditCourse");
		clu.setState("template");

		List<ValidationResultInfo> valerros = client.validateClu("SYSTEM", clu);

		assertEquals(2, valerros.size());
	}

	@Test
	public void testAddCluToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		StatusInfo status = client.addCluToCluSet("CLU-1", createdCluSet.getId());
		CluSetInfo getCluSetInfo = client.getCluSetInfo(createdCluSet.getId());

		assertTrue(status.getSuccess());
		assertEquals(1, getCluSetInfo.getCluIds().size());
		assertEquals("CLU-1", getCluSetInfo.getCluIds().get(0));
	}

	@Test
	public void testAddCluToCluSet_DuplicateCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		StatusInfo status = client.addCluToCluSet("CLU-1", createdCluSet.getId());
		assertTrue(status.getSuccess());

		try {
			client.addCluToCluSet("CLU-1", createdCluSet.getId());
			fail("Adding the same CLU more than once should have failed");
		} catch(OperationFailedException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddClusToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		// Somehow cannot add 2 CLUs in sequence (JTA rollback exception) but adding a single CLU works
		//CluInfo clu1 = createCluInfo();
		//CluInfo clu2 = createCluInfo();
		//CluInfo createdClu1 = client.createClu("luType.shell.course", clu1);
		//CluInfo createdClu2 = client.createClu("luType.shell.course", clu2);
		//List<String> cluIdList = Arrays.asList(new String[] {createdClu1.getId(), createdClu2.getId()});

		List<String> cluIdList = Arrays.asList(new String[] {"CLU-1", "CLU-2", "CLU-3", "CLU-4"});

		StatusInfo status = client.addClusToCluSet(cluIdList, createdCluSet.getId());
		CluSetInfo getCluSetInfo = client.getCluSetInfo(createdCluSet.getId());
		assertTrue(status.getSuccess());
		assertEquals(4, getCluSetInfo.getCluIds().size());
		assertTrue(getCluSetInfo.getCluIds().contains("CLU-1"));
		assertTrue(getCluSetInfo.getCluIds().contains("CLU-2"));
		assertTrue(getCluSetInfo.getCluIds().contains("CLU-3"));
		assertTrue(getCluSetInfo.getCluIds().contains("CLU-4"));
	}

	@Test
	public void testAddClusToCluSet_InvalidCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList = Arrays.asList(new String[] {"CLU-1", "CLU-2", "CLU-INVALID-ID", "CLU-4"});

		try {
			client.addClusToCluSet(cluIdList, createdCluSet.getId());
			fail("Adding a non-existent CLU (id='CLU-INVALID-ID') to CluSet should have failed");
		} catch(DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddClusToCluSet_DuplicateCluId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList = Arrays.asList(new String[] {"CLU-1", "CLU-2", "CLU-2", "CLU-4"});

		try {
			client.addClusToCluSet(cluIdList, createdCluSet.getId());
			fail("Adding a duplicate CLU (id='CLU-2') to CluSet should have failed");
		} catch(OperationFailedException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddClusToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		List<String> cluIdList = Arrays.asList(new String[] {"CLU-1", "CLU-2", "CLU-3", "CLU-4"});

		try {
			client.addClusToCluSet(cluIdList, "CLUSET-INVALID-ID");
			fail("Adding CLUs to a non-existent CluSet (id='CLUSET-INVALID-ID') should have failed");
		} catch(DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		StatusInfo status = client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1");
		CluSetInfo getCluSetInfo = client.getCluSetInfo(createdCluSet.getId());

		assertTrue(status.getSuccess());
		assertEquals(1, getCluSetInfo.getCluSetIds().size());
		assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-1"));
	}

	@Test
	public void testAddCluSetToCluSet_CircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		try {
			client.addCluSetToCluSet(createdCluSet.getId(), createdCluSet.getId());
			fail("Adding a CluSet to itself should have failed");
		} catch(CircularRelationshipException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetToCluSet_NestedCircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());
		CluSetInfo createdCluSet2 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());
		CluSetInfo createdCluSet3 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		client.addCluSetToCluSet(createdCluSet3.getId(), createdCluSet1.getId());
		client.addCluSetToCluSet(createdCluSet2.getId(), createdCluSet3.getId());

		try {
			client.addCluSetToCluSet(createdCluSet1.getId(), createdCluSet2.getId());
			fail("Adding CluSet should have thrown a CircularRelationshipException");
		} catch(CircularRelationshipException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetToCluSet_DuplicateCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		StatusInfo status = client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1");
		assertTrue(status.getSuccess());

		try {
			client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-1");
			fail("Adding a duplicate CluSet (id='CLUSET-1') to CluSet should have failed");
		} catch(OperationFailedException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		try {
			client.addCluSetToCluSet(createdCluSet.getId(), "CLUSET-INVALID-ID");
			fail("Adding a non-existent CluSet (id='CLUSET-INVALID-ID') to CluSet should have failed");
		} catch(DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetsToCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList = Arrays.asList(new String[] {"CLUSET-1", "CLUSET-2", "CLUSET-3", "CLUSET-4"});

		StatusInfo status = client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList);
		CluSetInfo getCluSetInfo = client.getCluSetInfo(createdCluSet.getId());

		assertTrue(status.getSuccess());
		assertEquals(4, getCluSetInfo.getCluSetIds().size());
		assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-1"));
		assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-2"));
		assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-3"));
		assertTrue(getCluSetInfo.getCluSetIds().contains("CLUSET-4"));
	}

	@Test
	public void testAddCluSetsToCluSet_InvalidCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList = Arrays.asList(new String[] {"CLUSET-1", "CLUSET-INVALID-ID", "CLUSET-3", "CLUSET-4"});

		try {
			client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList);
			fail("Adding a non-existent CluSet (id='CLUSET-INVALID-ID') to CluSet should have failed");
		} catch(DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetsToCluSet_DuplicateCluSetId() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList = Arrays.asList(new String[] {"CLUSET-1", "CLUSET-2", "CLUSET-3", "CLUSET-2"});

		try {
			client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList);
			fail("Adding a duplicate CluSet (id='CLUSET-2') to CluSet should have failed");
		} catch(OperationFailedException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetsToCluSet_CircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());
		// Adding createdCluSet to itself
		List<String> cluIdList = Arrays.asList(new String[] {"CLUSET-1", "CLUSET-2", createdCluSet.getId(), "CLUSET-4"});

		try {
			client.addCluSetsToCluSet(createdCluSet.getId(), cluIdList);
			fail("Adding a CluSet to itself should have failed");
		} catch(CircularRelationshipException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddCluSetsToCluSet_NestedCircularRelationshipException() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());
		CluSetInfo createdCluSet2 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());
		CluSetInfo createdCluSet3 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSetInfo());

		List<String> cluIdList1 = Arrays.asList(new String[] {"CLUSET-1", "CLUSET-2", createdCluSet1.getId()});
		client.addCluSetsToCluSet(createdCluSet3.getId(), cluIdList1);

		// Adding createdCluSet to itself
		List<String> cluIdList2 = Arrays.asList(new String[] {"CLUSET-1",createdCluSet3.getId()});
		client.addCluSetsToCluSet(createdCluSet2.getId(), cluIdList2);

		try {
			List<String> cluIdList3 = Arrays.asList(new String[] {createdCluSet2.getId(),});
			client.addCluSetsToCluSet(createdCluSet1.getId(), cluIdList3);
			fail("Adding CluSet should have thrown a CircularRelationshipException");
		} catch(CircularRelationshipException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testCreateDynamicCluSet_Simple() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.clus");

		cluSet.setMembershipQuery(query);
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);

		assertNotNull(createdCluSet);
		assertNotNull(createdCluSet.getMembershipQuery());
		assertNotNull(createdCluSet.getMembershipQuery().getId());
		assertNotNull(createdCluSet.getMembershipQuery().getSearchTypeKey());
		assertEquals(query.getSearchTypeKey(), createdCluSet.getMembershipQuery().getSearchTypeKey());
		assertNotNull(createdCluSet.getMembershipQuery().getQueryParamValueList());
		assertNotNull(createdCluSet.getCluIds());
        assertEquals(109, createdCluSet.getCluIds().size());
	}

	private MembershipQueryInfo getMembershipQueryInfo() {
		List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
		SearchParam sp1 = new SearchParam();
		sp1.setKey("lu.queryParam.startsWith.cluCode");
		sp1.setValue("AAST");
		queryParamValues.add(sp1);
		SearchParam sp2 = new SearchParam();
		sp2.setKey("lu.queryParam.cluState");
		sp2.setValue("activated");
		queryParamValues.add(sp2);

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.cluByCodeAndState");
		query.setQueryParamValueList(queryParamValues);

		return query;
	}

	@Test
	public void testCreateDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();

		MembershipQueryInfo query = getMembershipQueryInfo();
		cluSet.setMembershipQuery(query);

		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);

		assertNotNull(createdCluSet);
		assertNotNull(createdCluSet.getMembershipQuery());
		assertNotNull(createdCluSet.getMembershipQuery().getId());
		assertNotNull(createdCluSet.getMembershipQuery().getSearchTypeKey());
		assertEquals(query.getSearchTypeKey(), createdCluSet.getMembershipQuery().getSearchTypeKey());
		assertNotNull(createdCluSet.getMembershipQuery().getQueryParamValueList());
		assertEquals(query.getQueryParamValueList().size(), createdCluSet.getMembershipQuery().getQueryParamValueList().size());
		assertNotNull(createdCluSet.getCluIds());
		assertEquals(10, createdCluSet.getCluIds().size());

		CluSetInfo getCluSet = client.getCluSetInfo(createdCluSet.getId());

		assertNotNull(getCluSet);
		assertNotNull(getCluSet.getMembershipQuery());
		assertNotNull(getCluSet.getMembershipQuery().getId());
		assertNotNull(getCluSet.getMembershipQuery().getSearchTypeKey());
		assertEquals(query.getSearchTypeKey(), getCluSet.getMembershipQuery().getSearchTypeKey());
		assertNotNull(getCluSet.getMembershipQuery().getQueryParamValueList());
		assertEquals(query.getQueryParamValueList().size(), getCluSet.getMembershipQuery().getQueryParamValueList().size());
		assertNotNull(getCluSet.getCluIds());
		assertEquals(10, getCluSet.getCluIds().size());
	}

	@Test
	public void testCreateCluSet_InvalidCluSet1() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();
		cluSet.getCluIds().add("CLU-1");

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.clus");

		cluSet.setMembershipQuery(query);

		try {
			client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
			fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CLUs and Dynamic CluSets into one CluSet");
		} catch (UnsupportedActionException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testCreateCluSet_InvalidCluSet2() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();
		cluSet.getCluSetIds().add("CLUSET-1");

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.clus");

		cluSet.setMembershipQuery(query);

		try {
			client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
			fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CluSets and Dynamic CluSets into one CluSet");
		} catch (UnsupportedActionException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testCreateCluSet_InvalidCluSet3() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();
		cluSet.getCluIds().add("CLU-1");
		cluSet.getCluSetIds().add("CLUSET-1");

		try {
			client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
			fail("Creating CluSet should have thrown an UnsupportedActionException. Cannot add CLUs and CluSets into one CluSet");
		} catch (UnsupportedActionException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testGetDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();

		MembershipQueryInfo query = getMembershipQueryInfo();
		cluSet.setMembershipQuery(query);
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
		assertNotNull(createdCluSet);
		assertNotNull(createdCluSet.getCluIds());

		CluSetInfo getCluSet = client.getCluSetInfo(createdCluSet.getId());

		assertNotNull(getCluSet);
		assertNotNull(getCluSet.getMembershipQuery());
		assertNotNull(getCluSet.getMembershipQuery().getId());
		assertNotNull(getCluSet.getMembershipQuery().getSearchTypeKey());
		assertEquals(query.getSearchTypeKey(), getCluSet.getMembershipQuery().getSearchTypeKey());
		assertNotNull(getCluSet.getMembershipQuery().getQueryParamValueList());
		assertEquals(query.getQueryParamValueList().size(), getCluSet.getMembershipQuery().getQueryParamValueList().size());
		assertEquals(createdCluSet.getCluIds().size(), getCluSet.getCluIds().size());
		assertNotNull(getCluSet.getCluIds());
		assertEquals(10, getCluSet.getCluIds().size());
	}

	@Test
	public void testGetDynamicCluSet_Simple() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException {
		CluSetInfo cluSet = createCluSetInfo();

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.clus");

		cluSet.setMembershipQuery(query);
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
		assertNotNull(createdCluSet);
		assertNotNull(createdCluSet.getCluIds());

		CluSetInfo getCluSet = client.getCluSetInfo(createdCluSet.getId());
		assertNotNull(getCluSet);
		assertNotNull(getCluSet.getCluIds());
		assertEquals(createdCluSet.getCluIds().size(), getCluSet.getCluIds().size());
	}

	@Test
	public void testGetCluSetTreeView() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		CluSetTreeViewInfo treeView = client.getCluSetTreeView("CLUSET-2");
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
	public void testGetCluSetTreeView_dynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DoesNotExistException {
		CluSetInfo cluSet = createCluSetInfo();

		MembershipQueryInfo query = new MembershipQueryInfo();
		query.setSearchTypeKey("lu.search.clus");

		cluSet.setMembershipQuery(query);
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet);
		assertNotNull(createdCluSet);
		assertNotNull(createdCluSet.getCluIds());

		CluSetTreeViewInfo treeView = client.getCluSetTreeView(createdCluSet.getId());
		assertNotNull(treeView);
		assertEquals(createdCluSet.getCluIds().size(), treeView.getClus().size());
	}

	@Test
	public void testGetCluSetTreeView_invalidCluSet()
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			client.getCluSetTreeView("CLUSET-XX");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
	}

	@Test
	public  void testGetCluSetTreeView_nullCluSet()
			throws DoesNotExistException, InvalidParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			client.getCluSetTreeView(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateDynamicCluSet() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, UnsupportedActionException, CircularRelationshipException {
		CluSetInfo cluSet1 = createCluSetInfo();

		//Create clu set
		List<SearchParam> queryParamValues1 = new ArrayList<SearchParam>();
		MembershipQueryInfo query1 = new MembershipQueryInfo();
		query1.setSearchTypeKey("lu.search.clus");
		query1.setQueryParamValueList(queryParamValues1);

		cluSet1.setMembershipQuery(query1);
		// Version 0
		CluSetInfo createdCluSet = client.createCluSet("kuali.cluSet.type.creditCourse", cluSet1);
		// createdCluSet should be version 1 but is 0

		//Update clu set
		MembershipQueryInfo query2 = getMembershipQueryInfo();

		createdCluSet.setMembershipQuery(query2);

		// Dynamic CluSet so we can't have any CluSet ids or Clu ids
		createdCluSet.getCluSetIds().clear();
		createdCluSet.getCluIds().clear();
		CluSetInfo updatedCluSet = client.updateCluSet(createdCluSet.getId(), createdCluSet);

		assertNotNull(updatedCluSet);
		assertNotNull(updatedCluSet.getCluIds());
		assertEquals(10, updatedCluSet.getCluIds().size());
		assertTrue(updatedCluSet.getCluSetIds().isEmpty());
	}

	@Test
	public void testUpdateCluSet_VersionMismatch() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		createdCluSet1.getCluSetIds().add("CLUSET-1");
		createdCluSet1.getCluSetIds().add("CLUSET-2");

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);
		assertEquals(2, updatedCluSet1.getCluSetIds().size());

		try {
			client.updateCluSet(updatedCluSet1.getId(), createdCluSet1);
			fail("Should have thrown VersionMismatchException.");
		} catch (VersionMismatchException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testUpdateCluSet_ClearCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluSetIds().add("CLUSET-1");
		createCluSet.getCluSetIds().add("CLUSET-2");
		createCluSet.getCluSetIds().add("CLUSET-3");

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(3, createdCluSet1.getCluSetIds().size());

		createdCluSet1.getCluSetIds().clear();

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);
		assertEquals(0, updatedCluSet1.getCluSetIds().size());

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());
		assertEquals(0, getCluSet1.getCluSetIds().size());
	}

	@Test
	public void testUpdateCluSet_AddCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(0, createdCluSet1.getCluSetIds().size());

		createdCluSet1.getCluSetIds().add("CLUSET-1");
		createdCluSet1.getCluSetIds().add("CLUSET-2");

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);
		assertEquals(2, updatedCluSet1.getCluSetIds().size());

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());
		assertEquals(2, getCluSet1.getCluSetIds().size());
		assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-1"));
		assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-2"));
	}

	@Test
	public void testUpdateCluSet_removeCluSets() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluSetIds().add("CLUSET-1");
		createCluSet.getCluSetIds().add("CLUSET-2");
		createCluSet.getCluSetIds().add("CLUSET-3");

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(3, createdCluSet1.getCluSetIds().size());

		createdCluSet1.getCluSetIds().remove("CLUSET-2");

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);
		assertEquals(2, updatedCluSet1.getCluSetIds().size());

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());
		assertEquals(2, getCluSet1.getCluSetIds().size());
		assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-1"));
		assertTrue(getCluSet1.getCluSetIds().contains("CLUSET-3"));
	}

	@Test
	public void testUpdateCluSet_ClearClus() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluIds().add("CLU-1");
		createCluSet.getCluIds().add("CLU-2");
		assertEquals(2, createCluSet.getCluIds().size());

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(2, createdCluSet1.getCluIds().size());

		assertNotNull(createdCluSet1);
		assertNotNull(createdCluSet1.getCluIds());
		assertEquals(2, createdCluSet1.getCluIds().size());

		// Remove all CLUs
		createdCluSet1.getCluIds().clear();

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);

		assertNotNull(updatedCluSet1);
		assertNotNull(updatedCluSet1.getCluIds());
		assertEquals(0, updatedCluSet1.getCluIds().size());

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());

		assertNotNull(getCluSet1);
		assertNotNull(getCluSet1.getCluIds());
		assertEquals(0, getCluSet1.getCluIds().size());
	}

	@Test
	public void testUpdateCluSet_AddClu() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluIds().add("CLU-1");
		createCluSet.getCluIds().add("CLU-2");
		assertEquals(2, createCluSet.getCluIds().size());

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(2, createdCluSet1.getCluIds().size());

		createdCluSet1.getCluIds().add("CLU-3");

		assertNotNull(createdCluSet1);
		assertNotNull(createdCluSet1.getCluIds());
		assertEquals(3, createdCluSet1.getCluIds().size());

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);

		assertNotNull(updatedCluSet1);
		assertNotNull(updatedCluSet1.getCluIds());
		assertEquals(3, updatedCluSet1.getCluIds().size());

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());

		assertNotNull(getCluSet1);
		assertNotNull(getCluSet1.getCluIds());
		assertEquals(3, getCluSet1.getCluIds().size());
	}

	@Test
	public void testUpdateCluSet_RemoveClu() throws ParseException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, UnsupportedActionException, VersionMismatchException, CircularRelationshipException {
		CluSetInfo createCluSet = createCluSetInfo();
		createCluSet.getCluIds().add("CLU-1");
		createCluSet.getCluIds().add("CLU-2");
		assertEquals(2, createCluSet.getCluIds().size());

		CluSetInfo createdCluSet1 = client.createCluSet("kuali.cluSet.type.creditCourse", createCluSet);
		assertEquals(2, createdCluSet1.getCluIds().size());

		createdCluSet1.getCluIds().remove("CLU-1");

		assertNotNull(createdCluSet1);
		assertNotNull(createdCluSet1.getCluIds());
		assertEquals(1, createdCluSet1.getCluIds().size());

		CluSetInfo updatedCluSet1 = client.updateCluSet(createdCluSet1.getId(), createdCluSet1);

		assertNotNull(updatedCluSet1);
		assertNotNull(updatedCluSet1.getCluIds());
		assertEquals(1, updatedCluSet1.getCluIds().size());
		assertTrue(updatedCluSet1.getCluIds().contains("CLU-2"));

		CluSetInfo getCluSet1 = client.getCluSetInfo(updatedCluSet1.getId());

		assertNotNull(getCluSet1);
		assertNotNull(getCluSet1.getCluIds());
		assertEquals(1, getCluSet1.getCluIds().size());
	}

	@Test
	public void testCreateCluResult() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		CluResultInfo dto = new CluResultInfo();
		RichTextInfo desc = new RichTextInfo();
		desc.setPlain("Plain description");
		dto.setDesc(desc);
		dto.setCluId("CLU-1");
		dto.setState("active");
		dto.setType("kuali.resultType.gradeCourseResult");
		dto.setEffectiveDate(new Date());
		dto.setExpirationDate(new Date());

		CluResultInfo cluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto);

		assertNotNull(cluResult);
		assertNotNull(cluResult.getDesc());
		assertEquals(dto.getDesc().getPlain(), cluResult.getDesc().getPlain());
		assertNotNull(cluResult.getId());
		assertNotNull(cluResult.getCluId());
		assertEquals(dto.getCluId(), cluResult.getCluId());
		assertNotNull(cluResult.getType());
		assertEquals(dto.getType(), cluResult.getType());
		assertEquals(dto.getEffectiveDate(), cluResult.getEffectiveDate());
		assertEquals(dto.getExpirationDate(), cluResult.getExpirationDate());
	}

	@Test
	public void testUpdateCluResult() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
		CluResultInfo dto = new CluResultInfo();
		RichTextInfo desc1 = new RichTextInfo();
		desc1.setPlain("Plain description");
		dto.setDesc(desc1);
		dto.setCluId("CLU-1");
		dto.setState("inactive");
		dto.setType("kuali.resultType.gradeCourseResult");
		dto.setEffectiveDate(new Date());
		dto.setExpirationDate(new Date());

        List<ResultOptionInfo> resultOptions = new ArrayList<ResultOptionInfo>();
        ResultOptionInfo option = new ResultOptionInfo();
		RichTextInfo desc2 = new RichTextInfo();
		desc2.setPlain("Plain description");
		option.setDesc(desc2);
		option.setEffectiveDate(new Date());
		option.setExpirationDate(new Date());
		option.setResultComponentId("kuali.resultComponent.grade.letter");
		option.setResultUsageTypeKey(null);
		option.setState("inactive");
		resultOptions.add(option);

        dto.setResultOptions(resultOptions);

		CluResultInfo createCluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto);
		createCluResult = client.getCluResult(createCluResult.getId());

		assertNotNull(createCluResult);

		createCluResult.setCluId("CLU-2");
		RichTextInfo desc3 = new RichTextInfo();
		desc3.setPlain("Plain description again");
		createCluResult.setDesc(desc3);
		createCluResult.setEffectiveDate(new Date());
		createCluResult.setExpirationDate(new Date());
		createCluResult.setState("active");
		createCluResult.setType("kuali.resultType.creditCourseResult");

		RichTextInfo desc4 = new RichTextInfo();
		desc4.setPlain("Some more plain description");
		createCluResult.getResultOptions().get(0).setDesc(desc4);
		createCluResult.getResultOptions().get(0).setEffectiveDate(new Date());
		createCluResult.getResultOptions().get(0).setExpirationDate(new Date());
		createCluResult.getResultOptions().get(0).setResultComponentId("kuali.resultComponent.grade.passFail");
		createCluResult.getResultOptions().get(0).setResultUsageTypeKey("lrType.finalGrade");
		createCluResult.getResultOptions().get(0).setState("active");

		CluResultInfo updateCluResult = client.updateCluResult(createCluResult.getId(), createCluResult);
		updateCluResult = client.getCluResult(updateCluResult.getId());

		assertNotNull(updateCluResult);
		assertEquals(createCluResult.getId(), updateCluResult.getId());
		assertEquals(createCluResult.getDesc().getPlain(), updateCluResult.getDesc().getPlain());
		assertEquals(createCluResult.getEffectiveDate(), updateCluResult.getEffectiveDate());
		assertEquals(createCluResult.getExpirationDate(), updateCluResult.getExpirationDate());
		assertEquals(createCluResult.getState(), updateCluResult.getState());
		assertEquals(createCluResult.getType(), updateCluResult.getType());
		assertEquals(createCluResult.getResultOptions().get(0).getId(), updateCluResult.getResultOptions().get(0).getId());
		assertEquals(createCluResult.getResultOptions().get(0).getDesc().getPlain(), updateCluResult.getResultOptions().get(0).getDesc().getPlain());
		assertEquals(createCluResult.getResultOptions().get(0).getEffectiveDate(), updateCluResult.getResultOptions().get(0).getEffectiveDate());
		assertEquals(createCluResult.getResultOptions().get(0).getExpirationDate(), updateCluResult.getResultOptions().get(0).getExpirationDate());
		assertEquals(createCluResult.getResultOptions().get(0).getResultComponentId(), updateCluResult.getResultOptions().get(0).getResultComponentId());
		assertEquals(createCluResult.getResultOptions().get(0).getResultUsageTypeKey(), updateCluResult.getResultOptions().get(0).getResultUsageTypeKey());
		assertEquals(createCluResult.getResultOptions().get(0).getState(), updateCluResult.getResultOptions().get(0).getState());
	}

	@Test
	public void testUpdateCluResult_RemoveAllCluResultOptions() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
		CluResultInfo dto = new CluResultInfo();
		RichTextInfo desc1 = new RichTextInfo();
		desc1.setPlain("Plain description");
		dto.setDesc(desc1);
		dto.setCluId("CLU-1");
		dto.setState("inactive");
		dto.setType("kuali.resultType.gradeCourseResult");
		dto.setEffectiveDate(new Date());
		dto.setExpirationDate(new Date());

        List<ResultOptionInfo> resultOptions = new ArrayList<ResultOptionInfo>();
        ResultOptionInfo option = new ResultOptionInfo();
		RichTextInfo desc2 = new RichTextInfo();
		desc2.setPlain("Plain description");
		option.setDesc(desc2);
		option.setEffectiveDate(new Date());
		option.setExpirationDate(new Date());
		option.setResultComponentId("kuali.resultComponent.grade.letter");
		//option.setResultUsageTypeKey("lrType.finalGrade");
		option.setState("inactive");
		resultOptions.add(option);

        dto.setResultOptions(resultOptions);

		CluResultInfo createCluResult = client.createCluResult("CLU-1", "kuali.resultType.gradeCourseResult", dto);
		createCluResult = client.getCluResult(createCluResult.getId());

		assertNotNull(createCluResult);

		// Clear all cluResultOptions
		createCluResult.getResultOptions().clear();

		CluResultInfo updateCluResult = client.updateCluResult(createCluResult.getId(), createCluResult);
		updateCluResult = client.getCluResult(updateCluResult.getId());

		assertNotNull(updateCluResult);
		assertEquals(createCluResult.getId(), updateCluResult.getId());
		assertEquals(createCluResult.getResultOptions().isEmpty(), updateCluResult.getResultOptions().isEmpty());
	}

	private CluSetInfo createCluSetInfo() throws ParseException {
		CluSetInfo cluSetInfo = new CluSetInfo();

		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("<p>Formatted Desc</p>");
		desc.setPlain("plain");
		cluSetInfo.setDescr(desc);
		cluSetInfo.setEffectiveDate(DF.parse("20080101"));
		cluSetInfo.setExpirationDate(DF.parse("20180101"));
		cluSetInfo.setName("Clu set name");

		return cluSetInfo;
	}

	private CluInfo createCluInfo() throws ParseException {
		CluInfo clu = new CluInfo();

		CluAccountingInfo accountingInfo = new CluAccountingInfo();
		accountingInfo.getAttributes().put("AccountingAttrKey1",
				"AccountingAttrValue1");
		accountingInfo.getAttributes().put("AccountingAttrKey2",
				"AccountingAttrValue2");
		clu.setAccountingInfo(accountingInfo);

		CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
		officialIdentifier.setCode("offId-code");
		officialIdentifier.setDivision("offId-division");
		officialIdentifier.setLevel("offId-level");
		officialIdentifier.setLongName("offId-longName");
		officialIdentifier.setShortName("offId-shortName");
		officialIdentifier.setState("offId-state");
		officialIdentifier.setType("offId-type");
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
		cluId1.setState("cluId1-state");
		cluId1.setType("cluId1-type");
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
		cluId2.setState("cluId2-state");
		cluId2.setType("cluId2-type");
		cluId2.setVariation("cluId2-variation");
		cluId2.setSuffixCode("cluId2-suffixcode");
		cluId2.setOrgId("cluId2-orgid");
		clu.getAlternateIdentifiers().add(cluId2);

		clu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
		clu.getAttributes().put("cluAttrKey2", "cluAttrValue2");

		clu.setCanCreateLui(true);

		clu.setDefaultEnrollmentEstimate(545);
		clu.setDefaultMaximumEnrollment(999);

		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("<p>DESC FORMATTED</p>");
		desc.setPlain("DESC PLAIN");
		clu.setDescr(desc);

		clu.setEffectiveDate(DF.parse("20100203"));
		clu.setExpirationDate(DF.parse("21001231"));

		clu.setEnrollable(true);

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
		feeInfo.getAttributes().put("FeeAttrKey1", "FeeAttrValue1");
		feeInfo.getAttributes().put("FeeAttrKey2", "FeeAttrValue2");
		feeInfo.setCluFeeRecords(feeRecList);
		feeInfo.setDescr(cfDesc);
		clu.setFeeInfo(feeInfo);

		clu.setHasEarlyDropDeadline(true);

		clu.setHazardousForDisabledStudents(true);

		CluInstructorInfo primaryInstructor = new CluInstructorInfo();
		primaryInstructor.setOrgId("EXT-orgId-1");
		primaryInstructor.setPersonId("EXT-personId-1");
		primaryInstructor.getAttributes().put("PrimaryInstAttrKey1",
				"PrimaryInstAttrValue1");
		primaryInstructor.getAttributes().put("PrimaryInstAttrKey2",
				"PrimaryInstAttrValue2");
		clu.setPrimaryInstructor(primaryInstructor);

		CluInstructorInfo instructor1 = new CluInstructorInfo();
		instructor1.setOrgId("EXT-orgId-2");
		instructor1.setPersonId("EXT-personId-2");
		instructor1.getAttributes().put("Inst1AttrKey1", "Inst1AttrValue1");
		instructor1.getAttributes().put("Inst1AttrKey2", "Inst1AttrValue2");
		clu.getInstructors().add(instructor1);

		CluInstructorInfo instructor2 = new CluInstructorInfo();
		instructor2.setOrgId("EXT-orgId-3");
		instructor2.setPersonId("EXT-personId-3");
		instructor2.getAttributes().put("Inst2AttrKey1", "Inst2AttrValue1");
		instructor2.getAttributes().put("Inst2AttrKey2", "Inst2AttrValue2");
		clu.getInstructors().add(instructor2);

		LuCodeInfo luCode1 = new LuCodeInfo();
		luCode1.setId("luCode1.key");
		luCode1.setDescr("luCode1-desc");
		luCode1.setValue("luCode1-value");
		luCode1.getAttributes().put("luCode1AttrKey1", "luCode1AttrValue1");
		luCode1.getAttributes().put("luCode1AttrKey2", "luCode1AttrValue2");
		luCode1.setType("kuali.someKindOfLuCode");
		clu.getLuCodes().add(luCode1);

		LuCodeInfo luCode2 = new LuCodeInfo();
		luCode2.setId("luCode2.key");
		luCode2.setDescr("luCode2-desc");
		luCode2.setValue("luCode2-value");
		luCode2.getAttributes().put("luCode2AttrKey1", "luCode2AttrValue1");
		luCode2.getAttributes().put("luCode2AttrKey2", "luCode2AttrValue2");
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
		pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey1",
				"PubPrimaryInstAttrValue1");
		pubPrimaryInstructor.getAttributes().put("PubPrimaryInstAttrKey2",
				"PubPrimaryInstAttrValue2");

		CluInstructorInfo pubInstructor1 = new CluInstructorInfo();
		pubInstructor1.setOrgId("EXT-orgId-2");
		pubInstructor1.setPersonId("EXT-personId-2");
		pubInstructor1.getAttributes().put("PubInst1AttrKey1",
				"PubInst1AttrValue1");
		pubInstructor1.getAttributes().put("PubInst1AttrKey2",
				"PubInst1AttrValue2");

		CluInstructorInfo pubInstructor2 = new CluInstructorInfo();
		pubInstructor2.setOrgId("EXT-orgId-3");
		pubInstructor2.setPersonId("EXT-personId-3");
		pubInstructor2.getAttributes().put("PubInst2AttrKey1",
				"PubInst2AttrValue1");
		pubInstructor2.getAttributes().put("PubInst2AttrKey2",
				"PubInst2AttrValue2");

		clu.setReferenceURL("http://student.kuali.org/clus");

		clu.setState("Clu-state");

		TimeAmountInfo stdDuration = new TimeAmountInfo();
		stdDuration.setAtpDurationTypeKey("EXT-stdDuration-Id1");
		stdDuration.setTimeQuantity(new Integer(7867));
		clu.setStdDuration(stdDuration);

		clu.setType("kuali.SomeKindOfClu");

		createCampusLocationList(clu);

		createIntensity(clu);

		createAccreditationList(clu);

		createAdminOrgs(clu);

		return clu;
	}

	private void createAdminOrgs(CluInfo clu) {
		AdminOrgInfo adminOrg = new AdminOrgInfo();
		adminOrg.setOrgId("PRIMARY-ADMIN-ORG-ID");
		adminOrg.getAttributes().put("PrimaryAdminOrgAttrKey1",
				"PrimaryAdminOrgAttrValue1");
		adminOrg.getAttributes().put("PrimaryAdminOrgAttrKey2",
				"PrimaryAdminOrgAttrValue2");
		adminOrg.setType("kuali.altadminType1");
		
		clu.getAdminOrgs().add(adminOrg);
		AdminOrgInfo altAdminOrg1 = new AdminOrgInfo();
		altAdminOrg1.setOrgId("ALT-ADMIN-ORG-ID1");
		altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey1",
				"AltAdminOrg1AttrValue1");
		altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey2",
				"AltAdminOrg1AttrValue2");
		altAdminOrg1.getAttributes().put("AltAdminOrg1AttrKey3",
				"AltAdminOrg1AttrValue3");
		altAdminOrg1.setType("kuali.altadminType1");
		
		AdminOrgInfo altAdminOrg2 = new AdminOrgInfo();
		altAdminOrg2.setOrgId("ALT-ADMIN-ORG-ID2");
		altAdminOrg2.getAttributes().put("AltAdminOrg2AttrKey1",
				"AltAdminOrg2AttrValue1");
		altAdminOrg2.getAttributes().put("AltAdminOrg2AttrKey2",
				"AltAdminOrg2AttrValue2");
		altAdminOrg2.setType("kuali.altadminType1");

		clu.getAdminOrgs().add(altAdminOrg1);
		clu.getAdminOrgs().add(altAdminOrg2);

	}

	private void checkAdminOrgsCreate(CluInfo clu) {

		assertEquals("PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
				.getOrgId());
		assertEquals(2,  clu.getAdminOrgs().get(0).getAttributes().size());
		assertEquals("PrimaryAdminOrgAttrValue1",  clu.getAdminOrgs().get(0)
				.getAttributes().get("PrimaryAdminOrgAttrKey1"));
		assertEquals("PrimaryAdminOrgAttrValue2",  clu.getAdminOrgs().get(0)
				.getAttributes().get("PrimaryAdminOrgAttrKey2"));
		
		assertEquals("ALT-ADMIN-ORG-ID1", clu.getAdminOrgs().get(1)
				.getOrgId());
		assertEquals(3, clu.getAdminOrgs().get(1).getAttributes()
				.size());
		assertEquals("AltAdminOrg1AttrValue1", clu.getAdminOrgs().get(
				1).getAttributes().get("AltAdminOrg1AttrKey1"));
		assertEquals("AltAdminOrg1AttrValue2", clu.getAdminOrgs().get(
				1).getAttributes().get("AltAdminOrg1AttrKey2"));
		assertEquals("AltAdminOrg1AttrValue3", clu.getAdminOrgs().get(
				1).getAttributes().get("AltAdminOrg1AttrKey3"));

		assertEquals("ALT-ADMIN-ORG-ID2", clu.getAdminOrgs().get(2)
				.getOrgId());
		assertEquals(2, clu.getAdminOrgs().get(2).getAttributes()
				.size());
		assertEquals("AltAdminOrg2AttrValue1", clu.getAdminOrgs().get(
				2).getAttributes().get("AltAdminOrg2AttrKey1"));
		assertEquals("AltAdminOrg2AttrValue2", clu.getAdminOrgs().get(
				2).getAttributes().get("AltAdminOrg2AttrKey2"));
	}

	private void updateAdminOrgs(CluInfo clu) {
//		clu.getAdminOrgs().get(0).setId("adminOrg121");
		clu.getAdminOrgs().get(0).setOrgId("UPD-PRIMARY-ADMIN-ORG-ID");
		clu.getAdminOrgs().get(0).getAttributes().put("PrimaryAdminOrgAttrKey3",
				"PrimaryAdminOrgAttrValue3");
		clu.getAdminOrgs().get(0).getAttributes().remove(
				"PrimaryAdminOrgAttrKey2");
		clu.getAdminOrgs().get(0).getAttributes().put("PrimaryAdminOrgAttrKey4",
				"PrimaryAdminOrgAttrValue4");
		
		AdminOrgInfo altAdminOrg3 = new AdminOrgInfo();
		altAdminOrg3.setOrgId("UPD-ADMIN-ORG-ID3");
		altAdminOrg3.getAttributes().put("UPDAdminOrg3AttrKey1",
				"UPDAdminOrg3AttrKey1");
		altAdminOrg3.getAttributes().put("UPDAdminOrg3AttrKey2",
				"UPDAdminOrg3AttrKey1");
		altAdminOrg3.setType("kuali.testType");

		clu.getAdminOrgs().remove(1);
		clu.getAdminOrgs().add(altAdminOrg3);
	}

	private void checkAdminOrgUpdate(CluInfo clu) {

		assertEquals("UPD-PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
				.getOrgId());
		assertEquals(3, clu.getAdminOrgs().get(0).getAttributes().size());
		assertEquals("PrimaryAdminOrgAttrValue4", clu.getAdminOrgs().get(0)
				.getAttributes().get("PrimaryAdminOrgAttrKey4"));
		assertNull(clu.getAdminOrgs().get(0).getAttributes().get(
				"PrimaryAdminOrgAttrKey2"));
		assertEquals(3, clu.getAdminOrgs().size());
		assertEquals("UPD-PRIMARY-ADMIN-ORG-ID", clu.getAdminOrgs().get(0)
				.getOrgId());
		assertEquals(3, clu.getAdminOrgs().get(0).getAttributes()
				.size());
		assertEquals("PrimaryAdminOrgAttrValue1", clu.getAdminOrgs().get(
				0).getAttributes().get("PrimaryAdminOrgAttrKey1"));
		assertEquals("PrimaryAdminOrgAttrValue4", clu.getAdminOrgs().get(
				0).getAttributes().get("PrimaryAdminOrgAttrKey4"));
		assertEquals("PrimaryAdminOrgAttrValue3", clu.getAdminOrgs().get(
				0).getAttributes().get("PrimaryAdminOrgAttrKey3"));
		assertEquals("ALT-ADMIN-ORG-ID2", clu.getAdminOrgs().get(1)
				.getOrgId());
		assertEquals(2, clu.getAdminOrgs().get(1).getAttributes()
				.size());
		assertEquals("AltAdminOrg2AttrValue2", clu.getAdminOrgs().get(
				1).getAttributes().get("AltAdminOrg2AttrKey2"));
		assertEquals("AltAdminOrg2AttrValue1", clu.getAdminOrgs().get(
				1).getAttributes().get("AltAdminOrg2AttrKey1"));
	}

	private void createAccreditationList(CluInfo clu) throws ParseException {
		AccreditationInfo accreditationOrg1 = new AccreditationInfo();
		accreditationOrg1.setOrgId("EXT-orgId-1");
		accreditationOrg1.setEffectiveDate(DF.parse("20100203"));
		accreditationOrg1.setExpirationDate(DF.parse("21001231"));
		accreditationOrg1.getAttributes().put("Accred1AttrKey1",
				"Accred1AttrValue1");
		accreditationOrg1.getAttributes().put("Accred1AttrKey2",
				"Accred1AttrValue2");

		AccreditationInfo accreditationOrg2 = new AccreditationInfo();
		accreditationOrg2.setOrgId("EXT-orgId-2");
		accreditationOrg2.setEffectiveDate(DF.parse("20110203"));
		accreditationOrg2.setExpirationDate(DF.parse("21011231"));
		accreditationOrg2.getAttributes().put("Accred2AttrKey1",
				"Accred2AttrValue1");
		accreditationOrg2.getAttributes().put("Accred2AttrKey2",
				"Accred2AttrValue2");

		clu.getAccreditations().add(accreditationOrg1);
		clu.getAccreditations().add(accreditationOrg2);
	}

	private void checkAccreditationListCreate(CluInfo clu)
			throws ParseException {

		assertEquals(2, clu.getAccreditations().size());

		assertEquals("EXT-orgId-1", clu.getAccreditations().get(0)
				.getOrgId());
		assertEquals(DF.parse("20100203"), clu.getAccreditations().get(0)
				.getEffectiveDate());
		assertEquals(DF.parse("21001231"), clu.getAccreditations().get(0)
				.getExpirationDate());
		assertEquals(2, clu.getAccreditations().get(0).getAttributes()
				.size());
		assertEquals("Accred1AttrValue1", clu.getAccreditations().get(0)
				.getAttributes().get("Accred1AttrKey1"));
		assertEquals("Accred1AttrValue2", clu.getAccreditations().get(0)
				.getAttributes().get("Accred1AttrKey2"));

		assertEquals("EXT-orgId-2", clu.getAccreditations().get(1)
				.getOrgId());
		assertEquals(DF.parse("20110203"), clu.getAccreditations().get(1)
				.getEffectiveDate());
		assertEquals(DF.parse("21011231"), clu.getAccreditations().get(1)
				.getExpirationDate());
		assertEquals(2, clu.getAccreditations().get(1).getAttributes()
				.size());
		assertEquals("Accred2AttrValue1", clu.getAccreditations().get(1)
				.getAttributes().get("Accred2AttrKey1"));
		assertEquals("Accred2AttrValue2", clu.getAccreditations().get(1)
				.getAttributes().get("Accred2AttrKey2"));
	}

	private void updateAccreditationList(CluInfo clu) throws ParseException {

		AccreditationInfo accreditationOrg3 = new AccreditationInfo();
		accreditationOrg3.setOrgId("EXT-orgId-3");
		accreditationOrg3.setEffectiveDate(DF.parse("20120203"));
		accreditationOrg3.setExpirationDate(DF.parse("21021231"));

		AccreditationInfo accreditationOrg4 = new AccreditationInfo();
		accreditationOrg4.setOrgId("EXT-orgId-4");
		accreditationOrg4.setEffectiveDate(DF.parse("20130203"));
		accreditationOrg4.setExpirationDate(DF.parse("21031231"));
		accreditationOrg4.getAttributes().put("Accred4AttrKey1",
				"Accred4AttrValue1");

		clu.getAccreditations().add(accreditationOrg3);
		clu.getAccreditations().add(accreditationOrg4);

		clu.getAccreditations().get(0).getAttributes().remove(
				"Accred1AttrKey2");
		clu.getAccreditations().get(0).getAttributes().put(
				"Accred1AttrKey1", "Accred1AttrValue1-UPD");
		clu.getAccreditations().remove(1);
	}

	private void checkAccreditationListUpdate(CluInfo clu)
			throws ParseException {

		assertEquals(3, clu.getAccreditations().size());

		assertEquals("EXT-orgId-1", clu.getAccreditations().get(0)
				.getOrgId());
		assertEquals(1, clu.getAccreditations().get(0).getAttributes()
				.size());
		assertEquals("Accred1AttrValue1-UPD", clu.getAccreditations().get(0)
				.getAttributes().get("Accred1AttrKey1"));

		assertEquals("EXT-orgId-3", clu.getAccreditations().get(1)
				.getOrgId());
		assertEquals(DF.parse("20120203"), clu.getAccreditations().get(1)
				.getEffectiveDate());
		assertEquals(DF.parse("21021231"), clu.getAccreditations().get(1)
				.getExpirationDate());
		assertEquals(0, clu.getAccreditations().get(1).getAttributes()
				.size());

		assertEquals("EXT-orgId-4", clu.getAccreditations().get(2)
				.getOrgId());
		assertEquals(DF.parse("20130203"), clu.getAccreditations().get(2)
				.getEffectiveDate());
		assertEquals(DF.parse("21031231"), clu.getAccreditations().get(2)
				.getExpirationDate());
		assertEquals(1, clu.getAccreditations().get(2).getAttributes()
				.size());
		assertEquals("Accred4AttrValue1", clu.getAccreditations().get(2)
				.getAttributes().get("Accred4AttrKey1"));

	}

	private void createIntensity(CluInfo clu) {
		AmountInfo intensity = new AmountInfo();
		intensity.setUnitType("EXT-intensity-Id1");
		intensity.setUnitQuantity("123");
		clu.setIntensity(intensity);
	}

	private void checkIntensityCreate(CluInfo clu) {
		assertEquals("EXT-intensity-Id1", clu.getIntensity()
				.getUnitType());
		assertEquals("123", clu.getIntensity().getUnitQuantity());
	}

	private void updateIntensity(CluInfo clu) {
		clu.getIntensity().setUnitType("UPD-intensity-Id1");
		clu.getIntensity().setUnitQuantity("456");
	}

	private void checkIntensityUpdate(CluInfo clu) {
		assertEquals("UPD-intensity-Id1", clu.getIntensity()
				.getUnitType());
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

}
