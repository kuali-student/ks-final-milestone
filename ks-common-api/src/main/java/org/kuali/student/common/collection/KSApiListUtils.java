/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.collection;

import java.util.List;

/**
 * List utilities for use in the api.  
 * 
 * Originally created as a place to hold resuable logic for null safe list comparisons in the .equals methods of the service DTO's.
 * 
 * @author Kuali Student Team
 *
 */
public final class KSApiListUtils {

	private KSApiListUtils() {
		// intentionally hidden
	}
	
	/**
	 * A Null-safe test of the contents of each list that returns true if they are exactly identical.  
	 * 
	 * With the same size and the values are equal for each list element.
	 * 
	 * Equality as determined by the Object.equals method.
	 * 
	 * @param source the first list in the comparison
	 * @param target the second list in the comparison
	 * @return true if the source and target list are of the same size and each element within them are equal.
	 */
	public static <T> boolean areListContentsEquals (List<T>source, List<T>target) {
		if (source == null && target == null)
			return true;
		else if (source == null || target == null)
			return false;
		else {
			// both are not null
			
			if (source.size() != target.size())
				return false;
			else {
				// collections are both the same size
				
				for (int i = 0; i < source.size(); i++) {
					
					T sourceElement = source.get(i);
					T targetElement = target.get(i);
					
					if (sourceElement != null) {
						if (targetElement == null)
							return false;
						
						if (!sourceElement.equals(targetElement))
							return false;
						
						// else they are correctly equals, continue
					}
					else {
						// sourceElement is null
						if (targetElement != null)
							return false;
						
						// at this point targetElement could be null which is allowed, continue
					}
					
				}
			}
		}
		
		// at this point we've tested the contents of both lists and they are equal.
		return true;
	}

}
