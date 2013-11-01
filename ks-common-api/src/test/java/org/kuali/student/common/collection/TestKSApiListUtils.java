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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kuali Student Team
 *
 */
public class TestKSApiListUtils {

	/**
	 * 
	 */
	public TestKSApiListUtils() {
	}
	
	@Test
	public void testCollectionEquality() {
		
		List<String>nullList = null;
		
		Assert.assertTrue (KSApiListUtils.areListContentsEquals(nullList, null));
		
		List<String>abcList = Arrays.asList(new String[]{"A", "B", "C"});
		
		Assert.assertFalse("null is not equal to abc", KSApiListUtils.areListContentsEquals(nullList, abcList));
		Assert.assertFalse("abc is not equal to null", KSApiListUtils.areListContentsEquals(abcList, nullList));
		
		List<String>xyzList = Arrays.asList(new String[]{"X", "Y", "Z"});
		
		Assert.assertFalse("xyz is not equal to abc", KSApiListUtils.areListContentsEquals(xyzList, abcList));
		Assert.assertFalse("abc is not equal to xyz", KSApiListUtils.areListContentsEquals(abcList, xyzList));
		
		List<String>abList = Arrays.asList(new String[]{"A", "B"});
		
		Assert.assertFalse("ab is not equal to abc", KSApiListUtils.areListContentsEquals(abList, abcList));
		Assert.assertFalse("abc is not equal to ab", KSApiListUtils.areListContentsEquals(abcList, abList));
		
		List<String>abcCopy = new ArrayList<String>(abcList);
		
		for (int i = 0; i < abcCopy.size(); i++) {
			abcCopy.set(i, new String (abcList.get(i)));
		}
		
		Assert.assertTrue ("abc should be equal to abc", KSApiListUtils.areListContentsEquals(abcList, abcCopy));
		
		List<String>xyzCopy = new ArrayList<String>(xyzList);
		
		for (int i = 0; i < xyzCopy.size(); i++) {
			xyzCopy.set(i, new String (xyzList.get(i)));
		}
		
		Assert.assertTrue ("xyz should be equal to xyz", KSApiListUtils.areListContentsEquals(xyzList, xyzList));
		
		
	}
}
