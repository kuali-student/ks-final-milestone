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
package org.kuali.student.common.test.util;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;

/**
 * @author ocleirig
 *
 */
public class TimeTester {

	/**
	 * 
	 */
	public TimeTester() {
	}
	
	public void check (Date expected, Date actual) {
		if (expected == null && actual == null) {
                    return;
                }
		Assert.assertNotNull(expected);
		Assert.assertNotNull(actual);
		
		Timestamp ts1 = new Timestamp (expected.getTime());
		Timestamp ts2 = new Timestamp (actual.getTime());
		
		Assert.assertEquals(ts1, ts2);
		
		
	}

}
