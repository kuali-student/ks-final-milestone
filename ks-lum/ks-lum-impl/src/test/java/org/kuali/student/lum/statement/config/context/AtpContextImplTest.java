package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.atp.dto.AtpDurationTypeInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.AtpSeasonalTypeInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.dto.DateRangeInfo;
import org.kuali.student.core.atp.dto.DateRangeTypeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.dto.MilestoneTypeInfo;
import org.kuali.student.core.atp.service.AtpService;
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
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class AtpContextImplTest {
	private AtpService atpService = new AtpServiceMock();
	private AtpContextImpl atpContext = new AtpContextImpl();
	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType("kuali.reqComponent.field.type.duration");
        reqCompField1.setValue("2");
        reqCompFieldList.add(reqCompField1);
        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType("kuali.reqComponent.field.type.durationType.id");
        reqCompField2.setValue("kuali.atp.duration.Year");
        reqCompFieldList.add(reqCompField2);

		reqComponent1.setReqCompFields(reqCompFieldList);
	}
	
	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType("kuali.reqComponent.field.type.duration");
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType("kuali.reqComponent.field.type.durationType.id");
        reqCompField2.setValue(null);
        reqCompFieldList.add(reqCompField2);

		reqComponent2.setReqCompFields(reqCompFieldList);
	}
	
	@Before
	public void beforeMethod() {
		atpContext.setAtpService(atpService);
		setupReqComponent1();
		setupReqComponent2();
	}

	@Test
    public void testCreateContextMap_ContainsTokens() throws OperationFailedException {
		Map<String, Object> contextMap = atpContext.createContextMap(reqComponent1);
		Assert.assertNotNull(contextMap);
		Assert.assertTrue(contextMap.containsKey(AtpContextImpl.DURATION_TOKEN));
		Assert.assertTrue(contextMap.containsKey(AtpContextImpl.DURATION_TYPE_TOKEN));
    }

	@Test
    public void testCreateContextMap_TokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = atpContext.createContextMap(reqComponent1);
		String duration = (String) contextMap.get(AtpContextImpl.DURATION_TOKEN);
		AtpDurationTypeInfo type = (AtpDurationTypeInfo) contextMap.get(AtpContextImpl.DURATION_TYPE_TOKEN);
		
		Assert.assertEquals("2", duration);
		Assert.assertEquals("kuali.atp.duration.Year", type.getId());
    }

	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = atpContext.createContextMap(reqComponent2);
		String duration = (String) contextMap.get(AtpContextImpl.DURATION_TOKEN);
		AtpDurationTypeInfo type = (AtpDurationTypeInfo) contextMap.get(AtpContextImpl.DURATION_TYPE_TOKEN);
		
		Assert.assertEquals(null, duration);
		Assert.assertEquals(null, type);
    }

	private static class AtpServiceMock implements AtpService {

		private Map<String, AtpDurationTypeInfo> durationTypeMap = new HashMap<String, AtpDurationTypeInfo>();

		public AtpServiceMock() {
			AtpDurationTypeInfo type1 = new AtpDurationTypeInfo();
			type1.setId("kuali.atp.duration.Year");
			type1.setName("Year");
			durationTypeMap.put("kuali.atp.duration.Year", type1);

			AtpDurationTypeInfo type2 = new AtpDurationTypeInfo();
			type2.setId("kuali.atp.duration.Term");
			type2.setName("Term");
			durationTypeMap.put("kuali.atp.duration.Term", type2);
		}
		
		@Override
		public DateRangeInfo addDateRange(String atpKey, String dateRangeKey,
				DateRangeInfo dateRangeInfo) throws AlreadyExistsException,
				DataValidationErrorException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MilestoneInfo addMilestone(String atpKey, String milestoneKey,
				MilestoneInfo milestoneInfo) throws AlreadyExistsException,
				DataValidationErrorException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpInfo createAtp(String atpTypeKey, String atpKey,
				AtpInfo atpInfo) throws AlreadyExistsException,
				DataValidationErrorException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo deleteAtp(String atpKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpInfo getAtp(String atpKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpDurationTypeInfo getAtpDurationType(String atpDurationTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			return durationTypeMap.get(atpDurationTypeKey);
		}

		@Override
		public List<AtpDurationTypeInfo> getAtpDurationTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpSeasonalTypeInfo getAtpSeasonalType(String atpSeasonalTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AtpSeasonalTypeInfo> getAtpSeasonalTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpTypeInfo getAtpType(String atpTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AtpTypeInfo> getAtpTypes() throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AtpInfo> getAtpsByAtpType(String atpTypeKey)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AtpInfo> getAtpsByDate(Date searchDate)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DateRangeInfo getDateRange(String dateRangeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DateRangeTypeInfo getDateRangeType(String dateRangeTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DateRangeTypeInfo> getDateRangeTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DateRangeTypeInfo> getDateRangeTypesForAtpType(
				String atpTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DateRangeInfo> getDateRangesByAtp(String atpKey)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DateRangeInfo> getDateRangesByDate(Date searchDate)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MilestoneInfo getMilestone(String milestoneKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MilestoneTypeInfo getMilestoneType(String milestoneTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<MilestoneTypeInfo> getMilestoneTypes()
				throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<MilestoneTypeInfo> getMilestoneTypesForAtpType(
				String atpTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<MilestoneInfo> getMilestonesByAtp(String atpKey)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<MilestoneInfo> getMilestonesByDates(Date startDate,
				Date endDate) throws InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<MilestoneInfo> getMilestonesByDatesAndType(
				String milestoneTypeKey, Date startDate, Date endDate)
				throws InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo removeDateRange(String dateRangeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusInfo removeMilestone(String milestoneKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException,
				PermissionDeniedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DateRangeInfo updateDateRange(String dateRangeKey,
				DateRangeInfo dateRangeInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MilestoneInfo updateMilestone(String milestoneKey,
				MilestoneInfo milestoneInfo)
				throws DataValidationErrorException, DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException, PermissionDeniedException,
				VersionMismatchException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateAtp(String validationType,
				AtpInfo atpInfo) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateDateRange(
				String validationType, DateRangeInfo dateRangeInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ValidationResultInfo> validateMilestone(
				String validationType, MilestoneInfo milestoneInfo)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
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
