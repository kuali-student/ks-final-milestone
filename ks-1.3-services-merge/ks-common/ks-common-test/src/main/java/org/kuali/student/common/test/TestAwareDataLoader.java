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
package org.kuali.student.common.test;

/**
 * Provides a before and after test callback mechanism intended to be used by data loaders. 
 * i.e. the class that coordinates loading of the test data is made aware of the test execution sequence
 * so that it can properly clear and reset the reference data.
 * 
 * @author ocleirig
 *
 */
public interface TestAwareDataLoader {

	/**
	 * Called before the next test is to be run.
	 * 
	 * @throws Exception
	 */
	public void beforeTest() throws Exception;
	
	/**
	 * Called after the last test was run.
	 * 
	 * @throws Exception
	 */
	
	public void afterTest() throws Exception;
}
