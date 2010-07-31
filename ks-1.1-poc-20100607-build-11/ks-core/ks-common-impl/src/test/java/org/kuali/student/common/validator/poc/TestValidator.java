package org.kuali.student.common.validator.poc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.impl.poc.DictionaryServiceImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class TestValidator {
	Validator val = null;
	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl("classpath:poc/test-validator-context.xml");
	
	@Before
	public void init() {
		val = new Validator();
		val.setDateParser(new ServerDateParser());
		val.setMessageService(null);
	}
		
	@Test     
    public void testRequired() {
    	    	
    	List<ValidationResultInfo> results = val.validateObject( buildTestPerson1(), buildObjectStructure1());    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.required");
    }
    

    @Test     
    public void testLengthRange() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("thisisaveryveryverylo");
    	
    	List<ValidationResultInfo> results = val.validateObject( p, buildObjectStructure1());    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.lengthOutOfRange");
    }
    
    @Test     
    public void testMinLength() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("t");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	o1.getAttributes().get(0).setMaxLength(null);
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.minLengthFailed");
    }
    

    @Test
    public void testMinDateValue() {
    	ConstraintMockPerson p = buildTestPerson1();
    	ServerDateParser sp = new ServerDateParser();
    	p.setDob(sp.parseDate("1960-01-01"));
    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.minValueFailed");    	
    }
    
    @Test     
    public void testMaxLength() {
    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("thisisaveryveryverylo");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	o1.getAttributes().get(0).setMinLength(0);
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.maxLengthFailed");
    }
    
    @Test     
    public void testValidChars() {
    	    	
    	ConstraintMockPerson p = buildTestPerson1();
    	p.setFirstName("in$#valid");

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 2);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.validCharsFailed");
    }


    @Test     
    public void testDoubleValueRange() {
    	
    	ConstraintMockPerson p = buildTestPerson2();
    	p.setGpa(5.0);

    	ObjectStructureDefinition o1 = buildObjectStructure1();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o1);    
    	assertEquals(results.size(), 1);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.outOfRange");
    }
    
    @Test
    public void testNestedStructures() {    	
    	ConstraintMockPerson p = buildTestPerson3();

    	ObjectStructureDefinition o = buildObjectStructure2();
    	
    	List<ValidationResultInfo> results = val.validateObject( p, o);    
    	assertEquals(results.size(), 3);

    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.required");

    	assertEquals(results.get(1).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(1).getMessage(), "validation.validCharsFailed");    	
    	assertEquals(results.get(2).getMessage(), "validation.requiresField");
    	
    	val.setSearchService(new MockSearchService());
    	p.getAddress().get(0).setLine1("something");
    	results = val.validateObject( p, o);    
    	assertEquals(results.size(), 1);

    	p.getAddress().get(0).setLine2("notrightlookupvalue");
    	results = val.validateObject( p, o);    
    	assertEquals(results.size(), 1);
    	assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(0).getMessage(), "validation.lookup");   
    }
    
    public class MockSearchService implements SearchService{

		@Override
		public SearchCriteriaTypeInfo getSearchCriteriaType(
				String searchCriteriaTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			return null;
		}

		@Override
		public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
				throws OperationFailedException {
			return null;
		}

		@Override
		public SearchResultTypeInfo getSearchResultType(
				String searchResultTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			return null;
		}

		@Override
		public List<SearchResultTypeInfo> getSearchResultTypes()
				throws OperationFailedException {
			return null;
		}

		@Override
		public SearchTypeInfo getSearchType(String searchTypeKey)
				throws DoesNotExistException, InvalidParameterException,
				MissingParameterException, OperationFailedException {
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypes()
				throws OperationFailedException {
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypesByCriteria(
				String searchCriteriaTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			return null;
		}

		@Override
		public List<SearchTypeInfo> getSearchTypesByResult(
				String searchResultTypeKey) throws DoesNotExistException,
				InvalidParameterException, MissingParameterException,
				OperationFailedException {
			return null;
		}

		@Override
		public SearchResult search(SearchRequest searchRequest)
				throws MissingParameterException {
			if(searchRequest!=null && searchRequest.getParams()!=null
					&&"param1".equals(searchRequest.getParams().get(0).getKey())
					&&"line2value".equals(searchRequest.getParams().get(0).getValue())
					){
				SearchResult result = new SearchResult();
				SearchResultRow row = new SearchResultRow();
				result.getRows().add(row);
				return result;
			}
			return null;
		}
    	
    }
    
    public ConstraintMockPerson buildTestPerson1() {
    	return ValidatorMockObjectGenerator.buildTestPerson1();
    }

    
    public ConstraintMockPerson buildTestPerson2() {
    	return ValidatorMockObjectGenerator.buildTestPerson2();
    }

    
    public ConstraintMockPerson buildTestPerson3() {
    	return ValidatorMockObjectGenerator.buildTestPerson3();
    }
    
    public ObjectStructureDefinition buildObjectStructure1() { 
    	return dictionaryDelegate.getObjectStructure("objectStructure1");
    }

    public ObjectStructureDefinition buildObjectStructure2() {
    	return dictionaryDelegate.getObjectStructure("objectStructure2");
    }
}
