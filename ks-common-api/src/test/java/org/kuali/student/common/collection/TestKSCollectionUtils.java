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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
	public void testRequiredZeroElement () throws OperationFailedException {

        String results;
        try {
            KSCollectionUtils.getRequiredZeroElement(Arrays.asList(new String[]{}));
            fail("OperationFailedException should have been thrown as empty list should fail");
        } catch (OperationFailedException ofe) {
            assertNotNull(ofe.getMessage());
            assertEquals("list cannot be empty", ofe.getMessage());
        }

		try {
			KSCollectionUtils.getRequiredZeroElement(Arrays.asList("A", "B"));
            fail("OperationFailedException should have been thrown as two element list should fail");
		} catch (OperationFailedException ofe) {
            assertNotNull(ofe.getMessage());
            assertEquals("list size exceeds limit of 1", ofe.getMessage());
		}

	    results = KSCollectionUtils.getRequiredZeroElement(Arrays.asList("A"));
        Assert.assertEquals("A", results);

		try {
			KSCollectionUtils.getRequiredZeroElement(null);
            fail("OperationFailedException should have been thrown as null list should fail");
        } catch (OperationFailedException ofe) {
            assertNotNull(ofe.getMessage());
            assertEquals("list cannot be null", ofe.getMessage());
		}
	}
	
	@Test
	public void testOptionalZeroElement () throws OperationFailedException {
        String result = KSCollectionUtils.getOptionalZeroElement(Arrays.asList(new String [] {}));
        Assert.assertNull("An empty list should not fail", result);

		try {
			KSCollectionUtils.getOptionalZeroElement(Arrays.asList("A", "B"));
            fail("OperationFailedException should have been thrown as two element list should fail");
        } catch (OperationFailedException ofe) {
            assertNotNull(ofe.getMessage());
            assertEquals("list size exceeds limit of 1", ofe.getMessage());
        }

        result = KSCollectionUtils.getOptionalZeroElement(Arrays.asList("A"));
		Assert.assertEquals("single element list should not fail", "A", result);

        try {
            KSCollectionUtils.getOptionalZeroElement(null);
            fail("OperationFailedException should have been thrown as null list should fail");
        } catch (OperationFailedException ofe) {
            assertNotNull(ofe.getMessage());
            assertEquals("list cannot be null", ofe.getMessage());
        }
	}
	
	@Test
	public void testCollectionEquality() {
		
		List<String>nullList = null;
		
		Assert.assertTrue (KSCollectionUtils.areCollectionContentsEqual(nullList, null));
		
		List<String>abcList = Arrays.asList("A", "B", "C");
		
		Assert.assertFalse("null is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(nullList, abcList));
		Assert.assertFalse("abc is not equal to null", KSCollectionUtils.areCollectionContentsEqual(abcList, nullList));
		
		List<String>xyzList = Arrays.asList("X", "Y", "Z");
		
		Assert.assertFalse("xyz is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(xyzList, abcList));
		Assert.assertFalse("abc is not equal to xyz", KSCollectionUtils.areCollectionContentsEqual(abcList, xyzList));
		
		List<String>abList = Arrays.asList("A", "B");
		
		Assert.assertFalse("ab is not equal to abc", KSCollectionUtils.areCollectionContentsEqual(abList, abcList));
		Assert.assertFalse("abc is not equal to ab", KSCollectionUtils.areCollectionContentsEqual(abcList, abList));
		
		List<String>abcCopy = new ArrayList<String>(abcList);
		
		for (int i = 0; i < abcCopy.size(); i++) {
			abcCopy.set(i, abcList.get(i));
		}
		
		Assert.assertTrue ("abc should be equal to abc", KSCollectionUtils.areCollectionContentsEqual(abcList, abcCopy));
		
		List<String>xyzCopy = new ArrayList<String>(xyzList);
		
		for (int i = 0; i < xyzCopy.size(); i++) {
			xyzCopy.set(i, xyzList.get(i));
		}
		
		Assert.assertTrue ("xyz should be equal to xyz", KSCollectionUtils.areCollectionContentsEqual(xyzList, xyzCopy));
		
		List<String>aabList = Arrays.asList("A", "A", "B");
		List<String>abaList = Arrays.asList("A", "B", "A");
		List<String>baaList = Arrays.asList("B", "A", "A");
		
		Assert.assertTrue("aab should be equal to aba", KSCollectionUtils.areCollectionContentsEqual(aabList, abaList));
		Assert.assertTrue("aab should be equal to baa", KSCollectionUtils.areCollectionContentsEqual(aabList, baaList));
		
		Assert.assertTrue("aba should be equal to aab", KSCollectionUtils.areCollectionContentsEqual(abaList, aabList));
		Assert.assertTrue("aba should be equal to baa", KSCollectionUtils.areCollectionContentsEqual(abaList, baaList));
		
		
	}
}
