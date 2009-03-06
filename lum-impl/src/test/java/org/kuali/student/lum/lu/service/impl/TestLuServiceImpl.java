package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.service.LuService;


@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl",testSqlFile="classpath:ks-lu.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181")
	public LuService client;

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
	public void testCluCrud() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		CluInfo clu = new CluInfo();

		CluAccountingInfo accountingInfo = new CluAccountingInfo();
		accountingInfo.getAttributes().put("AccountingAttrKey1", "AccountingAttrValue1");
		accountingInfo.getAttributes().put("AccountingAttrKey2", "AccountingAttrValue2");
		clu.setAccountingInfo(accountingInfo);

		clu.setAccreditingOrg("EXT_ACCREDITING_ORG_ID");

		clu.setAdminOrg("EXT_ADMIN_ORG_ID");

		CluIdentifierInfo officialIdentifier = new CluIdentifierInfo();
		officialIdentifier.setCode("offId_code");
		officialIdentifier.setDivision("offId_division");
		officialIdentifier.setLevel("offId_level");
		officialIdentifier.setLongName("offId_longName");
		officialIdentifier.setShortName("offId_shortName");
		officialIdentifier.setState("offId_state");
		officialIdentifier.setType("offId_type");
		officialIdentifier.setVariation("offId_variation");
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
		clu.getAlternateIdentifiers().add(cluId2);

		clu.getAttributes().put("cluAttrKey1", "cluAttrValue1");
		clu.getAttributes().put("cluAttrKey2", "cluAttrValue2");

		clu.setCanCreateLui(true);

		//CreditInfo
		CluCreditInfo creditInfo = new CluCreditInfo();

		TimeAmountInfo maxAllowableInactivity = new TimeAmountInfo();
		maxAllowableInactivity.setAtpDurationTypeKey("EXT_MXAI_ID");
		maxAllowableInactivity.setTimeQuantity(new Integer(1111));
		creditInfo.setMaxAllowableInactivity(maxAllowableInactivity);

		TimeAmountInfo maxTimeResultsRecognized = new TimeAmountInfo();
		maxTimeResultsRecognized.setAtpDurationTypeKey("EXT_MXTRR_ID");
		maxTimeResultsRecognized.setTimeQuantity(new Integer(2222));
		creditInfo.setMaxTimeResultsRecognized(maxTimeResultsRecognized);

		TimeAmountInfo maxTimeToComplete = new TimeAmountInfo();
		maxTimeToComplete.setAtpDurationTypeKey("EXT_MXTTC_ID");
		maxTimeToComplete.setTimeQuantity(new Integer(3333));
		creditInfo.setMaxTimeToComplete(maxTimeToComplete);

		TimeAmountInfo minTimeToComplete = new TimeAmountInfo();
		minTimeToComplete.setAtpDurationTypeKey("EXT_MNTTC_ID");
		minTimeToComplete.setTimeQuantity(new Integer(4444));
		creditInfo.setMinTimeToComplete(minTimeToComplete);

		TimeAmountInfo repeatTime = new TimeAmountInfo();
		repeatTime.setAtpDurationTypeKey("EXT_RT_ID");
		repeatTime.setTimeQuantity(new Integer(5555));
		creditInfo.setRepeatTime(repeatTime);

		creditInfo.setInstructorUnits(new Integer(54321));
		creditInfo.setMaxTotalUnits(new Integer(20202));
		creditInfo.setMinTotalUnits(new Integer(3131));
		creditInfo.setRepeatCount("unbounded");//RepeatCount is integer or unbounded
		creditInfo.setRepeatUnits("4");

		clu.setCreditInfo(creditInfo);

		clu.setDefaultEnrollmentEstimate(545);
		clu.setDefaultMaximumEnrollment(999);

		RichTextInfo desc = new RichTextInfo();
		desc.setFormatted("<p>DESC FORMATTED</p>");
		desc.setPlain("DESC PLAIN");
		clu.setDesc(desc);

		clu.setEffectiveDate(df.parse("20100203"));
		clu.setExpirationDate(df.parse("21001231"));

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

		clu.getParticipatingOrgs().add("EXT_Participating_ORG_ID1");
		clu.getParticipatingOrgs().add("EXT_Participating_ORG_ID2");


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

		//Do the actual create call
		CluInfo createdClu = client.createClu("luType.shell.course", clu);

		//Validate Results
		assertNotNull(createdClu);

		assertEquals("AccountingAttrValue1", createdClu.getAccountingInfo().getAttributes().get("AccountingAttrKey1"));
		assertEquals("AccountingAttrValue2", createdClu.getAccountingInfo().getAttributes().get("AccountingAttrKey2"));
		assertEquals("EXT_ACCREDITING_ORG_ID", createdClu.getAccreditingOrg());
		assertEquals("EXT_ADMIN_ORG_ID", createdClu.getAdminOrg());

		assertEquals("offId_code",createdClu.getOfficialIdentifier().getCode());
		assertEquals("offId_division",createdClu.getOfficialIdentifier().getDivision());
		assertEquals("offId_level",createdClu.getOfficialIdentifier().getLevel());
		assertEquals("offId_longName",createdClu.getOfficialIdentifier().getLongName());
		assertEquals("offId_shortName",createdClu.getOfficialIdentifier().getShortName());
		assertEquals("offId_state",createdClu.getOfficialIdentifier().getState());
		assertEquals("offId_type",createdClu.getOfficialIdentifier().getType());
		assertEquals("offId_variation",createdClu.getOfficialIdentifier().getVariation());

		assertEquals("cluId1_code",createdClu.getAlternateIdentifiers().get(0).getCode());
		assertEquals("cluId1_division",createdClu.getAlternateIdentifiers().get(0).getDivision());
		assertEquals("cluId1_level",createdClu.getAlternateIdentifiers().get(0).getLevel());
		assertEquals("cluId1_longName",createdClu.getAlternateIdentifiers().get(0).getLongName());
		assertEquals("cluId1_shortName",createdClu.getAlternateIdentifiers().get(0).getShortName());
		assertEquals("cluId1_state",createdClu.getAlternateIdentifiers().get(0).getState());
		assertEquals("cluId1_type",createdClu.getAlternateIdentifiers().get(0).getType());
		assertEquals("cluId1_variation",createdClu.getAlternateIdentifiers().get(0).getVariation());

		assertEquals("cluId2_code",createdClu.getAlternateIdentifiers().get(1).getCode());
		assertEquals("cluId2_division",createdClu.getAlternateIdentifiers().get(1).getDivision());
		assertEquals("cluId2_level",createdClu.getAlternateIdentifiers().get(1).getLevel());
		assertEquals("cluId2_longName",createdClu.getAlternateIdentifiers().get(1).getLongName());
		assertEquals("cluId2_shortName",createdClu.getAlternateIdentifiers().get(1).getShortName());
		assertEquals("cluId2_state",createdClu.getAlternateIdentifiers().get(1).getState());
		assertEquals("cluId2_type",createdClu.getAlternateIdentifiers().get(1).getType());
		assertEquals("cluId2_variation",createdClu.getAlternateIdentifiers().get(1).getVariation());

		assertEquals("cluAttrValue1",createdClu.getAttributes().get("cluAttrKey1"));
		assertEquals("cluAttrValue2",createdClu.getAttributes().get("cluAttrKey2"));

		assertTrue(createdClu.isCanCreateLui());

		assertEquals("EXT_MXAI_ID",createdClu.getCreditInfo().getMaxAllowableInactivity().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(1111),createdClu.getCreditInfo().getMaxAllowableInactivity().getTimeQuantity());
		assertEquals("EXT_MXTRR_ID",createdClu.getCreditInfo().getMaxTimeResultsRecognized().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(2222),createdClu.getCreditInfo().getMaxTimeResultsRecognized().getTimeQuantity());
		assertEquals("EXT_MXTTC_ID",createdClu.getCreditInfo().getMaxTimeToComplete().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(3333),createdClu.getCreditInfo().getMaxTimeToComplete().getTimeQuantity());
		assertEquals("EXT_MNTTC_ID",createdClu.getCreditInfo().getMinTimeToComplete().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(4444),createdClu.getCreditInfo().getMinTimeToComplete().getTimeQuantity());
		assertEquals("EXT_RT_ID",createdClu.getCreditInfo().getRepeatTime().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(5555),createdClu.getCreditInfo().getRepeatTime().getTimeQuantity());
		assertEquals(Integer.valueOf(54321), createdClu.getCreditInfo().getInstructorUnits());
		assertEquals(Integer.valueOf(20202), createdClu.getCreditInfo().getMaxTotalUnits());
		assertEquals(Integer.valueOf(3131), createdClu.getCreditInfo().getMinTotalUnits());
		assertEquals("unbounded",createdClu.getCreditInfo().getRepeatCount());
		assertEquals("4",createdClu.getCreditInfo().getRepeatUnits());

		assertEquals(545,createdClu.getDefaultEnrollmentEstimate());
		assertEquals(999,createdClu.getDefaultMaximumEnrollment());

		assertEquals("<p>DESC FORMATTED</p>",createdClu.getDesc().getFormatted());
		assertEquals("DESC PLAIN",createdClu.getDesc().getPlain());

		assertEquals(df.parse("20100203"),createdClu.getEffectiveDate());
		assertEquals(df.parse("21001231"),createdClu.getExpirationDate());

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

		assertEquals("EXT_Participating_ORG_ID1",createdClu.getParticipatingOrgs().get(0));
		assertEquals("EXT_Participating_ORG_ID2",createdClu.getParticipatingOrgs().get(1));

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

		//Now Update the Clu!
		createdClu.getAccountingInfo().getAttributes().put("AccountingAttrKey1", "AccountingAttrValue1");
		createdClu.getAccountingInfo().getAttributes().remove("AccountingAttrKey2");
		createdClu.getAccountingInfo().getAttributes().put("AccountingAttrKey3", "AccountingAttrValue3");

		createdClu.setAccreditingOrg("UPEXT_ACCREDITING_ORG_ID");

		createdClu.setAdminOrg("UPEXT_ADMIN_ORG_ID");

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

		//CreditInfo
		createdClu.getCreditInfo().getMaxAllowableInactivity().setAtpDurationTypeKey("UPEXT_MXAI_ID");
		createdClu.getCreditInfo().getMaxAllowableInactivity().setTimeQuantity(new Integer(91111));

		createdClu.getCreditInfo().getMaxTimeResultsRecognized().setAtpDurationTypeKey("UPEXT_MXTRR_ID");
		createdClu.getCreditInfo().getMaxTimeResultsRecognized().setTimeQuantity(new Integer(92222));

		createdClu.getCreditInfo().getMaxTimeToComplete().setAtpDurationTypeKey("UPEXT_MXTTC_ID");
		createdClu.getCreditInfo().getMaxTimeToComplete().setTimeQuantity(new Integer(93333));

		createdClu.getCreditInfo().getMinTimeToComplete().setAtpDurationTypeKey("UPEXT_MNTTC_ID");
		createdClu.getCreditInfo().getMinTimeToComplete().setTimeQuantity(new Integer(94444));

		createdClu.getCreditInfo().getRepeatTime().setAtpDurationTypeKey("UPEXT_RT_ID");
		createdClu.getCreditInfo().getRepeatTime().setTimeQuantity(new Integer(95555));

		createdClu.getCreditInfo().setInstructorUnits(new Integer(954321));
		createdClu.getCreditInfo().setMaxTotalUnits(new Integer(920202));
		createdClu.getCreditInfo().setMinTotalUnits(new Integer(93131));
		createdClu.getCreditInfo().setRepeatCount("4");//RepeatCount is integer or unbounded
		createdClu.getCreditInfo().setRepeatUnits("unbounded");

		createdClu.setDefaultEnrollmentEstimate(9545);
		createdClu.setDefaultMaximumEnrollment(9999);

		createdClu.getDesc().setFormatted("UP<p>DESC FORMATTED</p>");
		createdClu.getDesc().setPlain("UPDESC PLAIN");

		createdClu.setEffectiveDate(df.parse("20190203"));
		createdClu.setExpirationDate(df.parse("21091231"));

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

		createdClu.getParticipatingOrgs().remove(1);
		createdClu.getParticipatingOrgs().add("EXT_Participating_ORG_ID3");

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

		//Do Update
		CluInfo updatedClu = client.updateClu(createdClu.getId(), createdClu);

		//Validate Results
		assertNotNull(updatedClu);

		assertEquals("AccountingAttrValue1", updatedClu.getAccountingInfo().getAttributes().get("AccountingAttrKey1"));
		assertEquals("AccountingAttrValue3", updatedClu.getAccountingInfo().getAttributes().get("AccountingAttrKey3"));
		assertEquals(2,updatedClu.getAccountingInfo().getAttributes().size());

		assertEquals("UPEXT_ACCREDITING_ORG_ID", updatedClu.getAccreditingOrg());
		assertEquals("UPEXT_ADMIN_ORG_ID", updatedClu.getAdminOrg());

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

		assertEquals("UPEXT_MXAI_ID",updatedClu.getCreditInfo().getMaxAllowableInactivity().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(91111),updatedClu.getCreditInfo().getMaxAllowableInactivity().getTimeQuantity());
		assertEquals("UPEXT_MXTRR_ID",updatedClu.getCreditInfo().getMaxTimeResultsRecognized().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(92222),updatedClu.getCreditInfo().getMaxTimeResultsRecognized().getTimeQuantity());
		assertEquals("UPEXT_MXTTC_ID",updatedClu.getCreditInfo().getMaxTimeToComplete().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(93333),updatedClu.getCreditInfo().getMaxTimeToComplete().getTimeQuantity());
		assertEquals("UPEXT_MNTTC_ID",updatedClu.getCreditInfo().getMinTimeToComplete().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(94444),updatedClu.getCreditInfo().getMinTimeToComplete().getTimeQuantity());
		assertEquals("UPEXT_RT_ID",updatedClu.getCreditInfo().getRepeatTime().getAtpDurationTypeKey());
		assertEquals(Integer.valueOf(95555),updatedClu.getCreditInfo().getRepeatTime().getTimeQuantity());
		assertEquals(Integer.valueOf(954321), updatedClu.getCreditInfo().getInstructorUnits());
		assertEquals(Integer.valueOf(920202), updatedClu.getCreditInfo().getMaxTotalUnits());
		assertEquals(Integer.valueOf(93131), updatedClu.getCreditInfo().getMinTotalUnits());
		assertEquals("4",updatedClu.getCreditInfo().getRepeatCount());
		assertEquals("unbounded",updatedClu.getCreditInfo().getRepeatUnits());

		assertEquals(9545,updatedClu.getDefaultEnrollmentEstimate());
		assertEquals(9999,updatedClu.getDefaultMaximumEnrollment());

		assertEquals("UP<p>DESC FORMATTED</p>",updatedClu.getDesc().getFormatted());
		assertEquals("UPDESC PLAIN",updatedClu.getDesc().getPlain());

		assertEquals(df.parse("20190203"),updatedClu.getEffectiveDate());
		assertEquals(df.parse("21091231"),updatedClu.getExpirationDate());

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

		assertEquals("EXT_Participating_ORG_ID1",updatedClu.getParticipatingOrgs().get(0));
		assertEquals("EXT_Participating_ORG_ID3",updatedClu.getParticipatingOrgs().get(1));
		assertEquals(2,updatedClu.getParticipatingOrgs().size());

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
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	    CluCluRelationInfo cluCluRelationInfo = new CluCluRelationInfo();

	    cluCluRelationInfo.setEffectiveDate(df.parse("20080101"));
	    cluCluRelationInfo.setExpirationDate(df.parse("20100101"));
	    cluCluRelationInfo.setIsCluRelationRequired(true);
	    cluCluRelationInfo.setState("hello");
	    cluCluRelationInfo.setType("goodbye");

	    client.createCluCluRelation("CLU-1", "CLU-2", "luLuType.type1", cluCluRelationInfo);
	}

	@Test
	public void testLuiLuiRelationCrud() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        
	    LuiLuiRelationInfo luiLuiRelationInfo = new LuiLuiRelationInfo();
	    
	    luiLuiRelationInfo.setEffectiveDate(df.parse("20080101"));
	    luiLuiRelationInfo.setExpirationDate(df.parse("20100101"));
	    luiLuiRelationInfo.setState("hello");
	    luiLuiRelationInfo.setType("goodbye");
	    luiLuiRelationInfo.getAttributes().put("luiluiAttrKey1", "luiluiAttrValue1");
	    luiLuiRelationInfo.getAttributes().put("luiluiAttrKey2", "luiluiAttrValue2");
	    
	    LuiLuiRelationInfo created = client.createLuiLuiRelation("LUI-1", "LUI-2", "luLuType.type1", luiLuiRelationInfo);

	    assertEquals(df.parse("20080101"),created.getEffectiveDate());
	    assertEquals(df.parse("20100101"),created.getExpirationDate());
	    assertEquals("hello",created.getState());
	    assertEquals("luLuType.type1",created.getType());
	    assertEquals("LUI-1", created.getLuiId());
	    assertEquals("LUI-2", created.getRelatedLuiId());
	    assertEquals("luiluiAttrValue1", created.getAttributes().get("luiluiAttrKey1"));
	    assertEquals("luiluiAttrValue2", created.getAttributes().get("luiluiAttrKey2"));
	    assertNotNull(created.getId());
	    assertNotNull(created.getMetaInfo().getCreateTime());
	    assertNotNull(created.getMetaInfo().getVersionInd());
	 
	    created.setEffectiveDate(df.parse("20980101"));
	    created.setExpirationDate(df.parse("20190101"));
	    created.setState("sawyer");
	    created.setType("luLuType.type2");
	    created.setLuiId("LUI-2");
	    created.setRelatedLuiId("LUI-3");
	    created.getAttributes().put("luiluiAttrKey1", "UPluiluiAttrValue1");
	    created.getAttributes().remove("luiluiAttrKey2");
	    created.getAttributes().put("luiluiAttrKey3", "luiluiAttrValue3");
	    
	    LuiLuiRelationInfo updated = client.updateLuiLuiRelation(created.getId(), created);
	    
	    assertEquals(df.parse("20980101"),updated.getEffectiveDate());
	    assertEquals(df.parse("20190101"),updated.getExpirationDate());
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

		luiInfos = client.getLuisByIdList(Arrays.asList("LUI-1", "LUI-3"));
		assertEquals("CLU-1", luiInfos.get(0).getCluId());
		assertEquals("CLU-2", luiInfos.get(1).getCluId());
	}

	@Test
	public void testLuiCrud() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException, DependentObjectsExistException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

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
		
		luiInfo.setAtpId("ATP-1");
		luiInfo.setLuiCode("LUI Test Code");
		luiInfo.setMaxSeats(100);
		luiInfo.setState("Test Lui State");
		luiInfo.setEffectiveDate(df.parse("20101203"));
		luiInfo.setExpirationDate(df.parse("20801231"));
		luiInfo.getAttributes().put("luiAttrKey1", "luiAttrValue1");
		luiInfo.getAttributes().put("luiAttrKey2", "luiAttrValue2");
		
		LuiInfo createdLui = client.createLui("CLU-2", "ATP-3", luiInfo);

		assertEquals("ATP-1", createdLui.getAtpId());
		assertEquals("LUI Test Code", createdLui.getLuiCode());
		assertEquals(100L, (long) createdLui.getMaxSeats());
		assertEquals(df.parse("20101203"), luiInfo.getEffectiveDate());
		assertEquals(df.parse("20801231"), luiInfo.getExpirationDate());
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
		createdLui.setEffectiveDate(df.parse("20111203"));
		createdLui.setExpirationDate(df.parse("20811231"));
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
		assertEquals(df.parse("20111203"), updatedLui.getEffectiveDate());
		assertEquals(df.parse("20811231"), updatedLui.getExpirationDate());
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
}
