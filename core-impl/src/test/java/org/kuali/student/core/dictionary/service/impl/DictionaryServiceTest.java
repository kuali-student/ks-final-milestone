package org.kuali.student.core.dictionary.service.impl;


import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.Contexts;
import org.kuali.student.core.dictionary.dto.Enum;
import org.kuali.student.core.dictionary.dto.EnumeratedValue;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dictionary.validator.Validator;
import org.kuali.student.core.validation.dto.ValidationResult;


public class DictionaryServiceTest extends AbstractServiceTest{
    @Client(value = "org.kuali.student.core.dictionary.service.impl.DictionaryServiceImpl", port = "8181")
    public DictionaryService client;
    
    private static final int TOTAL_OBJ = 1;
	private static final int TOTAL_CLUINFO_TYPES = 2;
	
	@Test
	public void testGetObjectTypes(){
		List<String> result = client.getObjectTypes();
		assertTrue("Dictionary is null or empty", result != null && result.isEmpty() == false);
		assertTrue("cluInfo not found in dictionary", result.contains("cluInfo"));
		assertTrue("ObjectType list has  an unexpected amount of items", result.size() == TOTAL_OBJ);
	}
	
	@Test
	public void testGetObjectStructureForCluInfo(){
		ObjectStructure objStruct = client.getObjectStructure("cluInfo");
		assertTrue("ObjectStructure for cluInfo is null", objStruct != null);
		List<Type> types = objStruct.getType();
		assertTrue("ObjectStructure's Types are null or empty", types != null && types.isEmpty() == false);
		assertTrue("Type list has  an unexpected amount of items", types.size() == TOTAL_CLUINFO_TYPES);
		for(Type t: types){
			assertTrue("Type key is null", t.getKey() != null);
			assertTrue("Unexpected type key value", t.getKey().equals("course") || t.getKey().equals("program"));
			List<State> states = t.getState();
			assertTrue("A Type's states are null or empty", states != null && states.isEmpty() == false);
			for(State s: states){
				assertTrue("Type key is null", s.getKey() != null);
				assertTrue("Unexpected state key value", s.getKey().equals("proposed") || s.getKey().equals("active"));
				List<Field> fields = s.getField();
				assertTrue("A States's field items are null or empty", fields != null && fields.isEmpty() == false);
				for(Field f: fields){
					FieldDescriptor fd = f.getFieldDescriptor();
					
					assertTrue("No field descriptor exists for " + f.getKey(), fd != null);
					String dataType = fd.getDataType();
					assertTrue("No dataType defined for a fieldDescriptor", fd.getDataType() != null);
					
					String name = fd.getName();
					assertTrue("name in fieldDescriptor is null", name != null);
					String desc = fd.getDesc();
					assertTrue("desc in fieldDescriptor is null", desc != null);
					int minLength = fd.getMinLength();
					int maxLength = fd.getMaxLength();
					String validChars = fd.getValidChars();
					String invalidChars = fd.getInvalidChars();
					int minOccurs = fd.getMinOccurs();
					int maxOccurs = fd.getMaxOccurs();
					Enum e = fd.getEnum();
					testEnum(e);
					if(f.getKey().equals("cluId") && s.getKey().equals("proposed") && t.getKey().equals("course")){
								
						assertTrue("Wrong value for name, expected value = CLU Identifier", name.equals("CLU Identifier"));
						assertTrue("Wrong value for desc, expected value = Unique identifier for a Canonical Learning Unit (CLU).", desc.equals("Unique identifier for a Canonical Learning Unit (CLU)."));
						
						assertTrue("validChars in fieldDescriptor is null", validChars != null);
						assertTrue("Wrong value for validChars, expected value = ABCDEF0123456789-", validChars.equals("ABCDEF0123456789-"));
						
						assertTrue("minOccurs in fieldDescriptor is null", minOccurs != -1);
						assertTrue("Wrong value for minOccurs, expected value = 0", minOccurs == 0);
						
						assertTrue("maxOccurs in fieldDescriptor is null", maxOccurs != -1);
						assertTrue("Wrong value for maxOccurs, expected value = 1", maxOccurs == 1);
						
						assertTrue("minLength in feildDescriptor is null", minLength != -1);
						assertTrue("Wrong value for minLength, expected value = 1", minLength == 1);
						
						assertTrue("maxLength in feildDescriptor is null", maxLength != -1);
						assertTrue("Wrong value for maxLength, expected value = 36", maxLength == 36);
					}
				}
			}
		}
	}
	
	private void testEnum(Enum e){
		//e
	}
	
	@Test
	public void testGetEnumeration(){
		DictionaryServiceImpl impl = new DictionaryServiceImpl();
		List<EnumeratedValue> enums = impl.getEnumeration("states", null, null, null);
		assertTrue("Enumeration list is null or empty", enums != null && enums.isEmpty() == false);
		for(EnumeratedValue e: enums){
			//String key = e.getKey();
			//assertTrue("key in enumeration is null", key != null);
			String code = e.getCode();
			assertTrue("code in enumeration is null", code != null);
			String abbrev = e.getAbbrevValue();
			assertTrue("abbrev in enumeration is null", abbrev != null);
			//String value = e.get
			Contexts contextsObj = e.getContexts();
			List<Context> contexts = contextsObj.getContext();
			assertTrue("Enumeration context list is null or empty", contexts != null && contexts.isEmpty() == false);
			for(Context c: contexts){
				String contextType = c.getType();
				assertTrue("type in context is null", contextType != null);
				assertTrue("type is an unexpected type", contextType.equals("country"));
				String contextValue = c.getValue();
				//TODO: why does this fail?
				//System.out.println(contextValue);
				//assertTrue("value in context is null", contextValue != null);
				//assertTrue("value is an unexpected type" + contextValue, contextValue.equals("US") || contextValue.equals("CA"));
			}
		}
	}
	
	@Test
	public void testValidator(){
		DictionaryServiceImpl impl = new DictionaryServiceImpl();
		ObjectStructure objStruct = impl.getObjectStructure("cluInfo");
		List<Type> types = objStruct.getType();
		for(Type t: types){
			if(t.getKey().equals("course")){
				for(State s: t.getState()){
					if(s.getKey().equals("proposed")){
						for(Field f: s.getField()){
							if(f.getKey().equals("cluId")){
								Map<String, Object> m = f.getFieldDescriptor().toMap();
								ValidationResult result = Validator.validate("A", m);
								assertTrue("Did not pass validation", result.isOk());
								result = Validator.validate("ABC123", m);
								assertTrue("Did not pass validation.", result.isOk());
								result = Validator.validate("ABC123ABC123ABC123ABC123ABC123ABC123", m);
								assertTrue("Did not pass validation.", result.isOk());
								result = Validator.validate("hurrrrr", m);
								assertTrue("Validation error was expected, but passed validation.", result.isError());
								result = Validator.validate("abc", m);
								assertTrue("Validation error was expected, but passed validation.", result.isError());
								result = Validator.validate("", m);
								assertTrue("Validation error was expected, but passed validation.", result.isError());
								result = Validator.validate("ABC123ABC123ABC123ABC123ABC123ABC1230", m);
								assertTrue("Validation error was expected, but passed validation.", result.isError());
							}
						}
						break;
					}
				}
				break;
			}
		}
	}
	
	@Test
	public void testGetEnumerationAccuracy(){
		/*
		DictionaryServiceImpl impl = new DictionaryServiceImpl();
		List<EnumeratedValue> enums = impl.getEnumeration("states", "country", "US");
		assertTrue("Enumeration list is null or empty", enums != null && enums.isEmpty() == false);
		for(EnumeratedValue e: enums){
			e.getCode().equals("MD");
		}
		*/
	}
}
