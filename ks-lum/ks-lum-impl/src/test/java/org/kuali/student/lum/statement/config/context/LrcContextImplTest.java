package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class LrcContextImplTest {
	private LrcService lrcService = new LrcServiceMock();
	private LrcContextImpl lrcContext = new LrcContextImpl();
	private ReqComponent reqComponent;
	
	private void setupReqComponent() {
		reqComponent = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.GRADE_KEY.getId());
        reqCompField1.setValue("A");
        reqCompFieldList.add(reqCompField1);
        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.GRADE_TYPE_KEY.getId());
        reqCompField2.setValue("kuali.resultComponent.grade.letter");
        reqCompFieldList.add(reqCompField2);

		reqComponent.setReqComponentFields(reqCompFieldList);
	}
	
	@Before
	public void beforeMethod() {
		lrcContext.setLrcService(lrcService);
		setupReqComponent();
	}

	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = lrcContext.createContextMap(reqComponent);
		Assert.assertNotNull(contextMap);
		Assert.assertTrue(contextMap.containsKey(LrcContextImpl.GRADE_TOKEN));
		Assert.assertTrue(contextMap.containsKey(LrcContextImpl.GRADE_TYPE_TOKEN));
    }

	@Test
    public void testCreateContextMap_TokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = lrcContext.createContextMap(reqComponent);
		ResultComponentInfo gradeTypeId = (ResultComponentInfo) contextMap.get(LrcContextImpl.GRADE_TYPE_TOKEN);
		String gradeId = (String) contextMap.get(LrcContextImpl.GRADE_TOKEN);
		
		Assert.assertEquals("kuali.resultComponent.grade.letter", gradeTypeId.getId());
		Assert.assertEquals("A", gradeId);
    }

	private static class LrcServiceMock implements LrcService {

		Map<String, ResultComponentInfo> resultComponentMap = new HashMap<String, ResultComponentInfo>();
		
		public LrcServiceMock() {
			ResultComponentInfo rc1 = new ResultComponentInfo();
			rc1.setId("kuali.resultComponent.grade.letter");
			rc1.setName("Letter");
			rc1.setResultValues(Arrays.asList(new String[] {"A", "B", "C", "D", "F"}));
			resultComponentMap.put("kuali.resultComponent.grade.letter", rc1);

			ResultComponentInfo rc2 = new ResultComponentInfo();
			rc2.setId("kuali.resultComponent.grade.passFail");
			rc2.setName("Pass-Fail");
			rc2.setResultValues(Arrays.asList(new String[] {"Pass", "Fail"}));
			resultComponentMap.put("kuali.resultComponent.grade.passFail", rc2);
		}

		@Override
		public String compareGrades(String gradeKey, String scaleKey,
				String compareGradeKey, String compareScaleKey)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultComponentInfo createResultComponent(
				String resultComponentTypeKey,
				ResultComponentInfo resultComponentInfo)
				throws AlreadyExistsException, DataValidationErrorException,
				DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteResultComponent(String resultComponentId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CredentialInfo getCredential(String credentialKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCredentialKeysByCredentialType(
				String credentialTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CredentialTypeInfo getCredentialType(String credentialTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CredentialTypeInfo> getCredentialTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CredentialInfo> getCredentialsByKeyList(
				List<String> credentialKeyList) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CreditInfo getCredit(String creditKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCreditKeysByCreditType(String creditTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CreditTypeInfo getCreditType(String creditTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CreditTypeInfo> getCreditTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public GradeInfo getGrade(String gradeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getGradeKeysByGradeType(String gradeTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public GradeTypeInfo getGradeType(String gradeTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<GradeTypeInfo> getGradeTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<GradeInfo> getGradesByScale(String scale)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultComponentInfo getResultComponent(String resultComponentId)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			return resultComponentMap.get(resultComponentId);
		}

		@Override
		public List<String> getResultComponentIdsByResult(String resultValueId,
				String resultComponentTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getResultComponentIdsByResultComponentType(
				String resultComponentTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultComponentTypeInfo getResultComponentType(
				String resultComponentTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ResultComponentTypeInfo> getResultComponentTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ScaleInfo getScale(String scaleKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<GradeInfo> translateGrade(String gradeKey, String scaleKey,
				String translateScaleKey) throws InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ResultComponentInfo updateResultComponent(
				String resultComponentId,
				ResultComponentInfo resultComponentInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
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
		
	}
}
