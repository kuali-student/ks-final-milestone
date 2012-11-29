package org.kuali.student.r2.common.validator;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryServiceImpl;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.common.class1.search.SearchServiceDispatcherImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestValidator {
	DefaultValidatorImpl val = null;
	ValidatorFactory valFactory = new ValidatorFactory();
	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl(
			"classpath:test-validator-context.xml");

	@Before
	public void init() {

		val = new DefaultValidatorImpl();
		val.setDateParser(new ServerDateParser());
		val.setMessageService(null);
		valFactory.setDefaultValidator(val);

	}

	@Test
	public void testRequired() {

		List<ValidationResultInfo> results = val.validateObject(buildTestPerson1(), getSimpleStudentObjectStructure(), ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 1);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.required");
	}

	@Test
	public void testLengthRange() {

		ConstraintMockPerson p = buildTestPerson1();
		p.setFirstName("thisisaveryveryverylo");

		List<ValidationResultInfo> results = val.validateObject(p, getSimpleStudentObjectStructure(), ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 2);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.lengthOutOfRange");
	}

	@Test
	public void testMinLength() {

		ConstraintMockPerson p = buildTestPerson1();
		p.setFirstName("t");

		ObjectStructureDefinition o1 = getSimpleStudentObjectStructure();
		o1.getAttributes().get(0).setMaxLength(null);

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 2);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.minLengthFailed");
	}

	@Test
	public void testMinDateValue() {
		ConstraintMockPerson p = buildTestPerson1();
		ServerDateParser sp = new ServerDateParser();
		p.setDob(sp.parseDate("1960-01-01"));
		ObjectStructureDefinition o1 = getSimpleStudentObjectStructure();

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 1);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.minValueFailed");
	}

	@Test
	public void testMaxLength() {

		ConstraintMockPerson p = buildTestPerson1();
		p.setFirstName("thisisaveryveryverylo");

		ObjectStructureDefinition o1 = getSimpleStudentObjectStructure();
		o1.getAttributes().get(0).setMinLength(0);

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 2);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.maxLengthFailed");
	}

	@Test
	public void testValidChars() {

		ConstraintMockPerson p = buildTestPerson1();
		p.setFirstName("in$#valid");

		ObjectStructureDefinition o1 = getSimpleStudentObjectStructure();

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 2);

		assertEquals(results.get(0).getErrorLevel(),
				ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.validCharsFailed");
	}

	@Test
	public void testDoubleValueRange() {

		ConstraintMockPerson p = buildTestPerson2();
		p.setGpa(5.0);

		ObjectStructureDefinition o1 = getSimpleStudentObjectStructure();

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(results.size(), 1);

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.outOfRange");
	}

	@Test
	public void testNestedCaseConstraint() {

		ConstraintMockPerson p = buildTestPerson4();

		ObjectStructureDefinition o1 = getStudentWithAddressObjectStructure();

		List<ValidationResultInfo> results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		assertEquals(2, results.size());				
	
		p.getAddress().get(0).setState("ACTIVE");
		results = val.validateObject(p, o1, ContextInfoTestUtility.getEnglishContextInfo());
		
		assertEquals(4, results.size());
	}
		

	@Test
	public void testNestedStructures() {
		ConstraintMockPerson p = buildTestPerson3();

		ObjectStructureDefinition o = getStudentWithAddressObjectStructure();

		List<ValidationResultInfo> results = val.validateObject(p, o, ContextInfoTestUtility.getEnglishContextInfo());
		// ERROR address/0/line1 validation.required
		// ERROR address/0/line2 validation.validCharsFailed
		// ERROR address/0/line2 validation.requiresField
		System.out.println(results.size() + " errors found");
		for (ValidationResultInfo vri : results) {
			System.out.println(vri.getErrorLevel() + " " + vri.getElement()
					+ " " + vri.getMessage());
		}
		assertEquals(4, results.size());

		assertEquals(results.get(0).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.required");

		assertEquals(results.get(1).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(1).getMessage(), "validation.validCharsFailed");

		assertEquals(results.get(2).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);		
		assertEquals(results.get(2).getMessage(), "validation.requiresField");

        SearchServiceDispatcherImpl searchDispatcher = new SearchServiceDispatcherImpl();
        List<SearchService> searchServices = new ArrayList<SearchService>();
        searchServices.add(new MockSearchService());
        searchDispatcher.setSearchServices(searchServices);

		val.setSearchDispatcher(searchDispatcher);
		p.getAddress().get(0).setLine1("something");
		results = val.validateObject(p, o, ContextInfoTestUtility.getEnglishContextInfo());
		System.out.println(results.size() + " errors found");
		for (ValidationResultInfo vri : results) {
			System.out.println(vri.getErrorLevel() + " " + vri.getElement()
					+ " " + vri.getMessage());
		}
		assertEquals(3, results.size());

		p.getAddress().get(0).setLine2("notrightlookupvalue");
		results = val.validateObject(p, o, ContextInfoTestUtility.getEnglishContextInfo());
		System.out.println(results.size() + " errors found");
		for (ValidationResultInfo vri : results) {
			System.out.println(vri.getErrorLevel() + " " + vri.getElement()
					+ " " + vri.getMessage());
		}
		assertEquals(2, results.size());
		assertEquals(results.get(0).getErrorLevel(),
				ValidationResultInfo.ErrorLevel.ERROR);
		assertEquals(results.get(0).getMessage(), "validation.lookup");
	}

	public class MockSearchService implements SearchService {

		@Override
        public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


        @Override
        public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
            if (searchRequestInfo != null
                    && searchRequestInfo.getParams() != null
                    && "param1".equals(searchRequestInfo.getParams().get(0)
                    .getKey())
                    && "line2value".equals(searchRequestInfo.getParams().get(0)
                    .getValues().get(0))) {
                SearchResultInfo result = new SearchResultInfo();
                SearchResultRowInfo row = new SearchResultRowInfo();
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

	public ConstraintMockPerson buildTestPerson4() {
		return ValidatorMockObjectGenerator.buildTestPerson4();
	}

	public ObjectStructureDefinition getSimpleStudentObjectStructure() {
		return dictionaryDelegate.getObjectStructure("simpleStudent");
	}

	public ObjectStructureDefinition getStudentWithAddressObjectStructure() {
		return dictionaryDelegate.getObjectStructure("studentWithAddress");
	}
}
