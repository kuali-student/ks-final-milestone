package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
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
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
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
		ids = client.getCluIdsByLuType("LUTYPE-1", "STATE1");
		assertEquals(2, ids.size());
		assertEquals("CLU-1", ids.get(0));


		ids = client.getCluIdsByLuType("LUTYPE-1X", "STATE1");
		assertTrue(ids == null || ids.size() == 0);
		ids = client.getCluIdsByLuType("LUTYPE-1", "STATE1X");
		assertTrue(ids == null || ids.size() == 0);

		try {
			ids = client.getCluIdsByLuType(null, "STATE1");
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			ids = client.getCluIdsByLuType("LUTYPE-1", null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		// getClusByLuType
		clus = client.getClusByLuType("LUTYPE-1", "STATE1");
		assertEquals(2, clus.size());
		assertEquals("CLU-1", clus.get(0).getId());

		clus = client.getClusByLuType("LUTYPE-1X", "STATE1");
		assertTrue(clus == null || clus.size() == 0);
		clus = client.getClusByLuType("LUTYPE-1", "STATE1X");
		assertTrue(clus == null || clus.size() == 0);

		try {
			clus = client.getClusByLuType(null, "STATE1");
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}

		try {
			clus = client.getClusByLuType("LUTYPE-1", null);
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

	}

	@Test
	public void testGetLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		LuiInfo luiInfo;
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
	public void testCluCrud() throws ParseException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
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

	}


}
