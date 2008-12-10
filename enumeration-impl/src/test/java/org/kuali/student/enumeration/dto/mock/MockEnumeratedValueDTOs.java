/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enumeration.dto.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValue;

/**
 * Creates a List of EnumeratedValue DTOs, use to initialize database for testing
 * 
 *   last index value minus initial index value determines size of List
 *   EnumeratedValues are created with current index value to give each object field pseudo unique values 
 * 
 * @author garystruthers Kuali Student Team 
 * @Immutable all fields are private, all mutator methods are private
 * @Threadsafe immutable classes are threadsafe
 * The List and its EnumeratedValue objects returned by this class are NOT immutable or threadsafe
 */
public class MockEnumeratedValueDTOs {
	
	private static final long TEN_M = 10000000L; 
	private String abbrev = "Abbrev";
	private String code = "Code";
	private String value = "Value";
	private MockContextDTOs mockContextDTOs = null;
	private List<EnumeratedValue> enumeratedValues = null;

	/**
	 * MockEnumeratedValueDTOs Default Constructor
	 * 
	 */
	public MockEnumeratedValueDTOs() {
		this(0,10);
	}
	
	/**
	 * MockEnumeratedValueDTOs Constructor
	 * @param initial index value
	 * @param last index value
	 */
	public MockEnumeratedValueDTOs(int initial, int last) {
		mockContextDTOs = new MockContextDTOs();
		enumeratedValues = createEnumeratedValues(initial, last);
	}
	
	/**
	 * @return the enumeratedValues
	 */
	public List<EnumeratedValue> getEnumeratedValues() {
		return copyEnumeratedValues(enumeratedValues);
	}
	
	/**
	 * @return a newly created enumeratedValue
	 * @param i index value for setting unique field values
	 */
	private EnumeratedValue createEnumeratedValue(int i) {
		long now = System.currentTimeMillis();
		EnumeratedValue enumeratedValue = new EnumeratedValue();
		enumeratedValue.setAbbrevValue(abbrev + i);
		enumeratedValue.setCode(code + i);
		enumeratedValue.setContexts(mockContextDTOs.getContexts());
		enumeratedValue.setEffectiveDate(new Date(now - (TEN_M * i)));
		enumeratedValue.setExpirationDate(new Date(now + (TEN_M * i)));
		enumeratedValue.setSortKey(i);
		enumeratedValue.setValue(value + i);
		return enumeratedValue;
	}
	
	/**
	 * @return a newly created enumeratedValue
	 * @param i initial index value for setting unique field values
	 * @param j last index value for setting unique field values
	 */
	private List<EnumeratedValue> createEnumeratedValues(int initial, int last) {
		List<EnumeratedValue> enumeratedValues = new ArrayList<EnumeratedValue>();		
		for(int i = initial; i < last; i++) {
			enumeratedValues.add(createEnumeratedValue(i));
		}
		return enumeratedValues;
	}
	
	/**
	 * @return a copy of enumeratedValue
	 */
	private EnumeratedValue copyEnumeratedValue(EnumeratedValue src) {
		EnumeratedValue copy = new EnumeratedValue();
		copy.setAbbrevValue(src.getAbbrevValue());
		copy.setCode(src.getCode());
		copy.setContexts(src.getContexts());
		copy.setEffectiveDate(src.getEffectiveDate());
		copy.setExpirationDate(src.getExpirationDate());
		copy.setSortKey(src.getSortKey());
		copy.setValue(src.getValue());
		return copy;
	}
	
	/**
	 * @return a copy of enumeratedValues
	 * 
	 */
	private List<EnumeratedValue> copyEnumeratedValues(List<EnumeratedValue> src) {
		List<EnumeratedValue> copy = new ArrayList<EnumeratedValue>();		
		for(EnumeratedValue e : src) {
			copy.add(copyEnumeratedValue(e));
		}
		return copy;
	}

}
