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
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * @author Kuali Student Team
 *
 */
public class TestKSCollectionUtils {

	/**
	 * 
	 */
	public TestKSCollectionUtils() {
	}
	
	@Test
	public void testRequiredZeroElement () {

		boolean failed = false;
		try {
			String results = KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String [] {}));
			
			Assert.assertNull("results should be null", results);
			
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertFalse("an empty list should succeed.", failed);
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String[] {"A", "B"}));
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertTrue("two element list should fail", failed);
		
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String[] {"A"}));
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertFalse("single element list should not fail", failed);
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(null);
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertTrue("null list should fail", failed);
		
	}
	
	@Test
	public void testOptionalZeroElement () {
		
		boolean failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String [] {}));
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertFalse("an empty list should fail.", failed);
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String[] {"A", "B"}));
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertTrue("two element list should fail", failed);
		
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String[] {"A"}));
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertFalse("single element list should not fail", failed);
		
		failed = false;
		try {
			KSCollectionUtils.getOptionalZeroElement(null);
		} catch (OperationFailedException e) {
			failed = true;
		}
		
		Assert.assertTrue("null list should fail", failed);
		
	}
	
	@Test
	public void testCollectionEquality() {
		
		List<String>nullList = null;
		
		Assert.assertTrue (KSCollectionUtils.areCollectionContentsEqual(nullList, null));
		
		List<String>abcList = Arrays.asList(new String[]{"A", "B", "C"});
		
		Assert.assertFalse("null is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(nullList, abcList));
		Assert.assertFalse("abc is not equal to null", KSCollectionUtils.areCollectionContentsEqual(abcList, nullList));
		
		List<String>xyzList = Arrays.asList(new String[]{"X", "Y", "Z"});
		
		Assert.assertFalse("xyz is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(xyzList, abcList));
		Assert.assertFalse("abc is not equal to xyz", KSCollectionUtils.areCollectionContentsEqual(abcList, xyzList));
		
		List<String>abList = Arrays.asList(new String[]{"A", "B"});
		
		Assert.assertFalse("ab is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(abList, abcList));
		Assert.assertFalse("abc is not equal to ab", KSCollectionUtils.areCollectionContentsEqual(abcList, abList));
		
		List<String>abcCopy = new ArrayList<String>(abcList);
		
		for (int i = 0; i < abcCopy.size(); i++) {
			abcCopy.set(i, new String (abcList.get(i)));
		}
		
		Assert.assertTrue ("abc should be equal to abc", KSCollectionUtils.areCollectionContentsEqual(abcList, abcCopy));
		
		List<String>xyzCopy = new ArrayList<String>(xyzList);
		
		for (int i = 0; i < xyzCopy.size(); i++) {
			xyzCopy.set(i, new String (xyzList.get(i)));
		}
		
		Assert.assertTrue ("xyz should be equal to xyz", KSCollectionUtils.areCollectionContentsEqual(xyzList, xyzCopy));
		
		List<String>aabList = Arrays.asList(new String[] {"A", "A", "B"});
		List<String>abaList = Arrays.asList(new String[] {"A", "B", "A"});
		List<String>baaList = Arrays.asList(new String[] {"B", "A", "A"});
		
		Assert.assertTrue("aab should be equal to aba", KSCollectionUtils.areCollectionContentsEqual(aabList, abaList));
		Assert.assertTrue("aab should be equal to baa", KSCollectionUtils.areCollectionContentsEqual(aabList, baaList));
		
		Assert.assertTrue("aba should be equal to aab", KSCollectionUtils.areCollectionContentsEqual(abaList, aabList));
		Assert.assertTrue("aba should be equal to baa", KSCollectionUtils.areCollectionContentsEqual(abaList, baaList));
		
		
	}
}
