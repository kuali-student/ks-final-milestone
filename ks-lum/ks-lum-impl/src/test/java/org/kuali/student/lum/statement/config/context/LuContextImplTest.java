package org.kuali.student.lum.statement.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.r2.lum.statement.config.context.util.NLCluSet;
import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.clu.dto.*;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.statement.config.context.CluContextImpl;

import java.util.*;

public class LuContextImplTest {

	private CluService cluService = new LuServiceMock();
	private CluContextImpl cluContext = new CluContextImpl();

	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;


	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.CLU_KEY.getId());
        reqCompField1.setValue("CLU-NL-1");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        reqCompField2.setValue("CLU-NL-1");
        reqCompFieldList.add(reqCompField2);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField3 = new ReqCompFieldInfo();
        reqCompField3.setType(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        reqCompField3.setValue("CLU-NL-1");
        reqCompFieldList.add(reqCompField3);
		reqComponent1.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField4 = new ReqCompFieldInfo();
        reqCompField4.setType(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        reqCompField4.setValue("CLU-NL-1");
        reqCompFieldList.add(reqCompField4);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField5 = new ReqCompFieldInfo();
        reqCompField5.setType(ReqComponentFieldTypes.CLUSET_KEY.getId());
        reqCompField5.setValue("CLUSET-NL-1");
        reqCompFieldList.add(reqCompField5);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField6 = new ReqCompFieldInfo();
        reqCompField6.setType(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        reqCompField6.setValue("CLUSET-NL-1");
        reqCompFieldList.add(reqCompField6);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField7 = new ReqCompFieldInfo();
        reqCompField7.setType(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        reqCompField7.setValue("CLUSET-NL-1");
        reqCompFieldList.add(reqCompField7);
		reqComponent1.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField8 = new ReqCompFieldInfo();
        reqCompField8.setType(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        reqCompField8.setValue("CLUSET-NL-1");
        reqCompFieldList.add(reqCompField8);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.CLU_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.COURSE_CLU_KEY.getId());
        reqCompField2.setValue(null);
        reqCompFieldList.add(reqCompField2);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField3 = new ReqCompFieldInfo();
        reqCompField3.setType(ReqComponentFieldTypes.PROGRAM_CLU_KEY.getId());
        reqCompField3.setValue(null);
        reqCompFieldList.add(reqCompField3);
		reqComponent2.setReqCompFields(reqCompFieldList);

        ReqCompFieldInfo reqCompField4 = new ReqCompFieldInfo();
        reqCompField4.setType(ReqComponentFieldTypes.TEST_CLU_KEY.getId());
        reqCompField4.setValue(null);
        reqCompFieldList.add(reqCompField4);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField5 = new ReqCompFieldInfo();
        reqCompField5.setType(ReqComponentFieldTypes.CLUSET_KEY.getId());
        reqCompField5.setValue(null);
        reqCompFieldList.add(reqCompField5);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField6 = new ReqCompFieldInfo();
        reqCompField6.setType(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId());
        reqCompField6.setValue(null);
        reqCompFieldList.add(reqCompField6);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField7 = new ReqCompFieldInfo();
        reqCompField7.setType(ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId());
        reqCompField7.setValue(null);
        reqCompFieldList.add(reqCompField7);
		reqComponent2.setReqCompFields(reqCompFieldList);

		ReqCompFieldInfo reqCompField8 = new ReqCompFieldInfo();
        reqCompField8.setType(ReqComponentFieldTypes.TEST_CLUSET_KEY.getId());
        reqCompField8.setValue(null);
        reqCompFieldList.add(reqCompField8);
		reqComponent2.setReqCompFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		cluContext.setCluService(cluService);
		setupReqComponent1();
		setupReqComponent2();
	}

	@Test
    public void testCreateContextMap_Clu() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent1, new ContextInfo());
		CluInfo clu = (CluInfo) contextMap.get(CluContextImpl.CLU_TOKEN);
		CluInfo courseClu = (CluInfo) contextMap.get(CluContextImpl.COURSE_CLU_TOKEN);
		CluInfo programClu = (CluInfo) contextMap.get(CluContextImpl.PROGRAM_CLU_TOKEN);
		CluInfo testClu = (CluInfo) contextMap.get(CluContextImpl.TEST_CLU_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLU-NL-1", clu.getId());
		Assert.assertEquals("CLU-NL-1", courseClu.getId());
		Assert.assertEquals("CLU-NL-1", programClu.getId());
		Assert.assertEquals("CLU-NL-1", testClu.getId());

		Assert.assertEquals("kuali.lu.type.CreditCourse", clu.getTypeKey());
		Assert.assertEquals("MATH 152", clu.getOfficialIdentifier().getShortName());
		Assert.assertEquals("MATH 152 Linear Systems", clu.getOfficialIdentifier().getLongName());
	}

	@Test
    public void testCreateContextMap_CluSet() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent1, new ContextInfo());
		NLCluSet cluSet = (NLCluSet) contextMap.get(CluContextImpl.CLU_SET_TOKEN);
		NLCluSet courseCluSet = (NLCluSet) contextMap.get(CluContextImpl.COURSE_CLU_SET_TOKEN);
		NLCluSet programCluSet = (NLCluSet) contextMap.get(CluContextImpl.PROGRAM_CLU_SET_TOKEN);
		NLCluSet testCluSet = (NLCluSet) contextMap.get(CluContextImpl.TEST_CLU_SET_TOKEN);


		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLUSET-NL-1", cluSet.getCluSetId());
		Assert.assertEquals("CLUSET-NL-1", courseCluSet.getCluSetId());
		Assert.assertEquals("CLUSET-NL-1", programCluSet.getCluSetId());
		Assert.assertEquals("CLUSET-NL-1", testCluSet.getCluSetId());

		Assert.assertEquals("(MATH152, MATH180)", cluSet.getCluSetAsCode());
		Assert.assertEquals("(MATH 152, MATH 180)", cluSet.getCluSetAsShortName());
		Assert.assertEquals("(MATH 152 Linear Systems, MATH 180 Differential Calculus with Physical Applications)", cluSet.getCluSetAsLongName());

		Assert.assertEquals("MATH152", cluSet.getCluAsCode(0));
		Assert.assertEquals("MATH 152", cluSet.getCluAsShortName(0));
		Assert.assertEquals("MATH180", cluSet.getCluAsCode(1));
		Assert.assertEquals("MATH 180", cluSet.getCluAsShortName(1));
	}

	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = cluContext.createContextMap(reqComponent2, new ContextInfo());
		CluInfo clu = (CluInfo) contextMap.get(CluContextImpl.CLU_TOKEN);
		CluInfo courseClu = (CluInfo) contextMap.get(CluContextImpl.COURSE_CLU_TOKEN);
		CluInfo programClu = (CluInfo) contextMap.get(CluContextImpl.PROGRAM_CLU_TOKEN);
		CluInfo testClu = (CluInfo) contextMap.get(CluContextImpl.TEST_CLU_TOKEN);
		NLCluSet cluSet = (NLCluSet) contextMap.get(CluContextImpl.CLU_SET_TOKEN);
		NLCluSet courseCluSet = (NLCluSet) contextMap.get(CluContextImpl.COURSE_CLU_SET_TOKEN);
		NLCluSet programCluSet = (NLCluSet) contextMap.get(CluContextImpl.PROGRAM_CLU_SET_TOKEN);
		NLCluSet testCluSet = (NLCluSet) contextMap.get(CluContextImpl.TEST_CLU_SET_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals(null, clu);
		Assert.assertEquals(null, courseClu);
		Assert.assertEquals(null, programClu);
		Assert.assertEquals(null, testClu);
		Assert.assertEquals(null, cluSet);
		Assert.assertEquals(null, courseCluSet);
		Assert.assertEquals(null, programCluSet);
		Assert.assertEquals(null, testCluSet);

	}

	private static class LuServiceMock implements CluService {

		private Map<String, CluInfo> cluMap = new HashMap<String, CluInfo>();
		private Map<String, CluSetInfo> cluSetMap = new HashMap<String, CluSetInfo>();
		private Map<String, CluSetTreeViewInfo> cluSetTreeViewMap = new HashMap<String, CluSetTreeViewInfo>();

		public LuServiceMock() {
			CluInfo clu1 = new CluInfo();
			clu1.setId("CLU-NL-1");
			clu1.setTypeKey("kuali.lu.type.CreditCourse");
			CluIdentifierInfo cluIdent1 = new CluIdentifierInfo();
			cluIdent1.setId("IDENT-NL-1");
			cluIdent1.setCode("MATH152");
			cluIdent1.setShortName("MATH 152");
			cluIdent1.setLongName("MATH 152 Linear Systems");
			clu1.setOfficialIdentifier(cluIdent1);
			cluMap.put("CLU-NL-1", clu1);

			CluInfo clu2 = new CluInfo();
			clu2.setId("CLU-NL-3");
			clu2.setTypeKey("kuali.lu.type.CreditCourse");
			CluIdentifierInfo cluIdent2 = new CluIdentifierInfo();
			cluIdent2.setId("IDENT-NL-3");
			cluIdent2.setCode("MATH180");
			cluIdent2.setShortName("MATH 180");
			cluIdent2.setLongName("MATH 180 Differential Calculus with Physical Applications");
			clu2.setOfficialIdentifier(cluIdent2);
			cluMap.put("CLU-NL-3", clu1);

			CluSetInfo cluSet = new CluSetInfo();
			cluSet.setId("CLUSET-NL-1");
			cluSet.setCluIds(Arrays.asList(new String[] {"CLU-NL-1", "CLU-NL-3"}));
			cluSetMap.put("CLUSET-NL-1", cluSet);

			CluSetTreeViewInfo treeView = new CluSetTreeViewInfo();
			treeView.setClus(Arrays.asList(new CluInfo[] {clu1, clu2}));
			cluSetTreeViewMap.put("CLUSET-NL-1", treeView);
		}

        @Override
        public List<org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo> getVersions(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getLuTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getLuCodeTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getCluCluRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey, String relatedLuTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getLuPublicationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getLuPublicationTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getCluResultTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getResultUsageTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllowedResultComponentTypesForResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getCluLoRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<TypeInfo> getCluSetTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public TypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluInfo getClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluIdsByLuType(String luTypeKey, String luState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllowedCluCluRelationTypesByClu(String cluId, String relatedCluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId, String cluCLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluIdsByRelatedCluAndRelationType(String relatedCluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getRelatedClusByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getRelatedCluIdsByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluPublicationInfo> getCluPublicationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluPublicationInfo> getCluPublicationsByType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluPublicationInfo getCluPublication(String cluPublicationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluResultInfo getCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluResultInfo> getCluResultByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluIdsByResultComponent(String resultComponentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluLoRelationInfo getCluLoRelation(String cluLoRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getResourceRequirementsForClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluSetTreeViewInfo getCluSetTreeView(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluSetInfo> getCluSetsByIds(List<String> cluSetIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluSetIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public Boolean isCluSetDynamic(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getClusFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<CluInfo> getAllClusInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public Boolean isCluInCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateClu(String validationTypeKey, CluInfo cluInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteClu(String cluId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluInfo createNewCluVersion(String cluId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo setCurrentCluVersion(String cluVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluInfo updateCluState(String cluId, String luState, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateCluCluRelation(String validationTypeKey, String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateCluPublication(String validationTypeKey, String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluPublicationInfo createCluPublication(String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluPublicationInfo updateCluPublication(String cluPublicationId, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteCluPublication(String cluPublicationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateCluResult(String validationTypeKey, String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluResultInfo createCluResult(String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluResultInfo updateCluResult(String cluResultId, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateCluLoRelation(String validationTypeKey, String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluLoRelationInfo createCluLoRelation(String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteCluLoRelation(String cluLoRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo addCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo removeCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<ValidationResultInfo> validateCluSet(String validationTypeKey, String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluSetInfo createCluSet(String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo deleteCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId, ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo addCluSetsToCluSet(String cluSetId, List<String> addedCluSetIds, ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo removeCluSetFromCluSet(String cluSetId, String removedCluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo addCluToCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo addClusToCluSet(List<String> cluSetIds, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public org.kuali.student.r2.common.dto.StatusInfo removeCluFromCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public SearchResult search(SearchRequest request) throws MissingParameterException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }

        @Override
        public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
            // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
            return null;
        }
	}
}
