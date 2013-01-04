/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.core.enumerationmanagement.dto.EnumContextValueInfo;

/**
 * Creates a Contexts (List) of Context DTOs, use to initialize database for testing 
 * @author  garystruthers Kuali Student Team 
 * @Immutable all fields are private, all mutator methods are private
 * @Threadsafe immutable classes are threadsafe
 * Contexts (List) and its Context objects returned by this class are NOT immutable or threadsafe
 */
public class MockContextDTOs {
	
	private String contextType = "Type";
	private String contextValue = "Value";

	private List<EnumContextValueInfo> contexts;

	/**
	 * MockContextDTOs Default Constructor
	 * 
	 */
	public MockContextDTOs() {
		this(0,10);
	}
	
	/**
	 * MockContextDTOs Constructor
	 * @param initial index value
	 * @param last index value
	 */
	public MockContextDTOs(int initial, int last) {
		this.contexts = createContexts(initial, last);
	}

	/**
	 * @return the contexts
	 */
	public List<EnumContextValueInfo> getContexts() {
		return copyContexts(contexts);
	}
	
	/**
	 * @return a newly created Context
	 * @param i index value for setting unique field values
	 */
	private EnumContextValueInfo createContext(int i) {
		EnumContextValueInfo context = new EnumContextValueInfo();
		context.setKey(contextType);
		context.setValue(contextValue + i);
		return context;
	}
	
	/**
	 * @return a newly created Contexts
	 * @param i initial index value for setting unique field values
	 * @param j last index value for setting unique field values
	 */
	private List<EnumContextValueInfo> createContexts(int initial, int last) {
		List<EnumContextValueInfo> context = new ArrayList<EnumContextValueInfo>();		
		for(int i = initial; i < last; i++) {
			context.add(createContext(i));
		}
		return context;
	}
	
	/**
	 * @return a copy of Context
	 */
	private EnumContextValueInfo copyContext(EnumContextValueInfo src) {
		EnumContextValueInfo copy = new EnumContextValueInfo();
		copy.setKey(src.getKey());
		copy.setValue(src.getValue());
		return copy;
	}
	
	
	/**
	 * @return a copy of Contexts
	 * 
	 */
	private List<EnumContextValueInfo> copyContexts(List<EnumContextValueInfo> contexts) {
		List<EnumContextValueInfo> src = contexts;
		List<EnumContextValueInfo> copy = new ArrayList<EnumContextValueInfo>();		
		for(EnumContextValueInfo e : src) {
			copy.add(copyContext(e));
		}
		return copy;
	}
}
