package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.IllegalVersionSequencingException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationTypeInfo;
import org.kuali.student.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.CluResultTypeInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.CluSetTreeViewInfo;
import org.kuali.student.lum.lu.dto.CluSetTypeInfo;
import org.kuali.student.lum.lu.dto.DeliveryMethodTypeInfo;
import org.kuali.student.lum.lu.dto.InstructionalFormatTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuPublicationTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ResultUsageTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.statement.config.context.util.NLCluSet;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class LuContextImplTest {

	private LuService luService = new LuServiceMock();
	private LuContextImpl luContext = new LuContextImpl();

	private ReqComponent reqComponent1;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.CLU_KEY.getId());
        reqCompField1.setValue("CLU-NL-1");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqComponentFields(reqCompFieldList);

        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.CLUSET_KEY.getId());
        reqCompField2.setValue("CLUSET-NL-1");
        reqCompFieldList.add(reqCompField2);
		reqComponent1.setReqComponentFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		luContext.setLuService(luService);
		setupReqComponent1();
	}

	@Test
    public void testCreateContextMap_Clu() throws OperationFailedException {
		Map<String, Object> contextMap = luContext.createContextMap(reqComponent1);
		CluInfo clu = (CluInfo) contextMap.get(LuContextImpl.CLU_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLU-NL-1", clu.getId());
		Assert.assertEquals("kuali.lu.type.CreditCourse", clu.getType());
		Assert.assertEquals("MATH 152", clu.getOfficialIdentifier().getShortName());
		Assert.assertEquals("MATH 152 Linear Systems", clu.getOfficialIdentifier().getLongName());
	}

	@Test
    public void testCreateContextMap_CluSet() throws OperationFailedException {
		Map<String, Object> contextMap = luContext.createContextMap(reqComponent1);
		NLCluSet cluSet = (NLCluSet) contextMap.get(LuContextImpl.CLU_SET_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("CLUSET-NL-1", cluSet.getCluSetId());
		Assert.assertEquals("(MATH152, MATH180)", cluSet.getCluSetAsCode());
		Assert.assertEquals("(MATH 152, MATH 180)", cluSet.getCluSetAsShortName());
		Assert.assertEquals("(MATH 152 Linear Systems, MATH 180 Differential Calculus with Physical Applications)", cluSet.getCluSetAsLongName());

		Assert.assertEquals("MATH152", cluSet.getCluAsCode(0));
		Assert.assertEquals("MATH 152", cluSet.getCluAsShortName(0));
		Assert.assertEquals("MATH180", cluSet.getCluAsCode(1));
		Assert.assertEquals("MATH 180", cluSet.getCluAsShortName(1));
	}
	
	private static class LuServiceMock implements LuService {

		private Map<String, CluInfo> cluMap = new HashMap<String, CluInfo>();
		private Map<String, CluSetInfo> cluSetMap = new HashMap<String, CluSetInfo>();
		private Map<String, CluSetTreeViewInfo> cluSetTreeViewMap = new HashMap<String, CluSetTreeViewInfo>();
		
		public LuServiceMock() {
			CluInfo clu1 = new CluInfo();
			clu1.setId("CLU-NL-1");
			clu1.setType("kuali.lu.type.CreditCourse");
			CluIdentifierInfo cluIdent1 = new CluIdentifierInfo();
			cluIdent1.setId("IDENT-NL-1");
			cluIdent1.setCode("MATH152");
			cluIdent1.setShortName("MATH 152");
			cluIdent1.setLongName("MATH 152 Linear Systems");
			clu1.setOfficialIdentifier(cluIdent1);
			cluMap.put("CLU-NL-1", clu1);

			CluInfo clu2 = new CluInfo();
			clu2.setId("CLU-NL-3");
			clu2.setType("kuali.lu.type.CreditCourse");
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
		public StatusInfo addCluResourceRequirement(String resourceTypeKey,
				String cluId) throws AlreadyExistsException,
				DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo addCluSetToCluSet(String cluSetId,
				String addedCluSetId) throws CircularRelationshipException,
				DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException, UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo addCluSetsToCluSet(String cluSetId,
				List<String> addedCluSetIdList)
				throws CircularRelationshipException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo addCluToCluSet(String cluId, String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException, UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo addClusToCluSet(List<String> cluIdList,
				String cluSetId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluInfo createClu(String luTypeKey, CluInfo cluInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluCluRelationInfo createCluCluRelation(String cluId,
				String relatedCluId, String luLuRelationTypeKey,
				CluCluRelationInfo cluCluRelationInfo)
				throws AlreadyExistsException, CircularRelationshipException,
				DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluLoRelationInfo createCluLoRelation(String cluId, String loId,
				String cluLoRelationType, CluLoRelationInfo cluLoRelationInfo)
				throws AlreadyExistsException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				DataValidationErrorException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluPublicationInfo createCluPublication(String cluId,
				String luPublicationType, CluPublicationInfo cluPublicationInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluResultInfo createCluResult(String cluId,
				String cluResultType, CluResultInfo cluResultInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				DoesNotExistException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluSetInfo createCluSet(String cluSetType, CluSetInfo cluSetInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
				String relatedLuiId, String luLuRelationType,
				LuiLuiRelationInfo luiLuiRelationInfo)
				throws AlreadyExistsException, CircularRelationshipException,
				DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteClu(String cluId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				DependentObjectsExistException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteCluCluRelation(String cluCluRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteCluLoRelation(String cluLoRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteCluPublication(String cluPublicationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, DependentObjectsExistException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteCluResult(String cluResultId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, DependentObjectsExistException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteLui(String luiId)
				throws DependentObjectsExistException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllCluIdsInCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getAllClusInCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedCluLoRelationTypesForLuType(
				String luTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedLuLuRelationTypesByCluId(String cluId,
				String relatedCluId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId,
				String relatedLuiId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedLuLuRelationTypesForLuType(
				String luTypeKey, String relatedLuTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedResultComponentTypesForResultUsageType(
				String resultUsageTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluInfo getClu(String cluId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			return cluMap.get(cluId);
		}

		@Override
		public CluCluRelationInfo getCluCluRelation(String cluCluRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluIdsByLuType(String luTypeKey, String luState)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluIdsByRelation(String relatedCluId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluIdsByResultComponent(String resultComponentId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluIdsFromCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluLoRelationInfo getCluLoRelation(String cluLoRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluLoRelationTypeInfo getCluLoRelationType(
				String cluLoRelationTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluLoRelationTypeInfo> getCluLoRelationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluPublicationInfo getCluPublication(String cluPublicationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluPublicationInfo> getCluPublicationsByCluId(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluPublicationInfo> getCluPublicationsByType(
				String luPublicationTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluResultInfo getCluResult(String cluResultId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluResultInfo> getCluResultByClu(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluResultTypeInfo getCluResultType(String cluResultTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluResultTypeInfo> getCluResultTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluResultTypeInfo> getCluResultTypesForLuType(
				String luTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCluSetIdsFromCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluSetInfo getCluSetInfo(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			return cluSetMap.get(cluSetId);
		}

		@Override
		public List<CluSetInfo> getCluSetInfoByIdList(List<String> cluSetIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluSetTreeViewInfo getCluSetTreeView(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return cluSetTreeViewMap.get(cluSetId);
		}

		@Override
		public CluSetTypeInfo getCluSetType(String cluSetTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluSetTypeInfo> getCluSetTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getClusByIdList(List<String> cluIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getClusByLuType(String luTypeKey, String luState)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getClusByRelation(String relatedCluId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getClusFromCluSet(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DeliveryMethodTypeInfo getDeliveryMethodType(
				String deliveryMethodTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public InstructionalFormatTypeInfo getInstructionalFormatType(
				String instructionalFormatTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuCodeTypeInfo getLuCodeType(String luCodeTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuCodeTypeInfo> getLuCodeTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuLuRelationTypeInfo getLuLuRelationType(
				String luLuRelationTypeKey) throws OperationFailedException,
				MissingParameterException, DoesNotExistException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuLuRelationTypeInfo> getLuLuRelationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuPublicationTypeInfo getLuPublicationType(
				String luPublicationTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuPublicationTypeInfo> getLuPublicationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getLuPublicationTypesForLuType(String luTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuTypeInfo getLuType(String luTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiInfo getLui(String luiId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getLuiIdsByCluId(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getLuiIdsByRelation(String relatedLuiId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuiInfo> getLuisByRelation(String relatedLuiId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getRelatedCluIdsByCluId(String cluId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CluInfo> getRelatedClusByCluId(String cluId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getRelatedLuiIdsByLuiId(String luiId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
				String luLuRelationType) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getResourceRequirementsForCluId(String cluId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultUsageTypeInfo getResultUsageType(String resultUsageTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ResultUsageTypeInfo> getResultUsageTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Boolean isCluInCluSet(String cluId, String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Boolean isCluSetDynamic(String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException, UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo removeCluResourceRequirement(String resourceTypeKey,
				String cluId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo removeCluSetFromCluSet(String cluSetId,
				String removedCluSetId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				UnsupportedActionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluInfo updateClu(String cluId, CluInfo cluInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId,
				CluCluRelationInfo cluCluRelationInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId,
				CluLoRelationInfo cluLoRelationInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluPublicationInfo updateCluPublication(String cluPublicationId,
				CluPublicationInfo cluPublicationInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluResultInfo updateCluResult(String cluResultId,
				CluResultInfo cluResultInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException, UnsupportedActionException,
				CircularRelationshipException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluInfo updateCluState(String cluId, String luState)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
				LuiLuiRelationInfo luiLuiRelationInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LuiInfo updateLuiState(String luiId, String luState)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateClu(String validationType,
				CluInfo cluInfo) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateCluCluRelation(
				String validationType, CluCluRelationInfo cluCluRelationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateCluLoRelation(
				String validationType, CluLoRelationInfo cluLoRelationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateCluPublication(
				String validationType, CluPublicationInfo cluPublicationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateCluResult(
				String validationType, CluResultInfo cluResultInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateCluSet(String validationType,
				CluSetInfo cluSetInfo) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateLui(String validationType,
				LuiInfo luiInfo) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateLuiLuiRelation(
				String validationType, LuiLuiRelationInfo luiLuiRelationInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getObjectTypes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SearchCriteriaTypeInfo getSearchCriteriaType(
				String searchCriteriaTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SearchResultTypeInfo getSearchResultType(
				String searchResultTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SearchResultTypeInfo> getSearchResultTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SearchTypeInfo getSearchType(String searchTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypesByCriteria(
				String searchCriteriaTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypesByResult(
				String searchResultTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SearchResult search(SearchRequest searchRequest)
				throws MissingParameterException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CluInfo createNewCluVersion(String cluId, String versionComment)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo setCurrentCluVersion(String cluVersionId,
				Date currentVersionStart) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				IllegalVersionSequencingException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
				String refObjectId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VersionDisplayInfo getCurrentVersionOnDate(
				String refObjectTypeURI, String refObjectId, Date date)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VersionDisplayInfo getFirstVersion(String refObjectTypeURI,
				String refObjectId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VersionDisplayInfo getVersionBySequenceNumber(
				String refObjectTypeURI, String refObjectId, Long sequence)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
				String refObjectId) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<VersionDisplayInfo> getVersionsInDateRange(
				String refObjectTypeURI, String refObjectId, Date from, Date to)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
