/**
 * Copyright 2012 The Kuali Foundation
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
package org.kuali.student.common.mock;

/**
 * Mock implementations that implement this interface can be cleared.  i.e. reset back to their initial conditions.
 * 
 * Typically this will occur and be coordinated by the data loader used by the test case.
 * 
 * This needs to be here because some of the mock imnpl's are not located in the main code instead of the test code.
 * 
 * @author ocleirig
 *
 */
public interface MockService {

	/**
	 * Clear all cached service data.  This should place the service back to its initial configuration.
	 * 
	 */
	public void clear();
	
}
