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
package org.kuali.student.r2.common.permutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * Tests against the PermutationUtils class which provides the permutation generation support that is used 
 * to Generate Unconstrained Registration Groups in @see {@link CourseOfferingService}.
 * 
 * @author ocleirig
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestPermutationUtils {

	
	public TestPermutationUtils() {
	}

	@Test
	public void testGeneratePermutations() throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		/*
		 * This tests the permutation generation logic that is used when generating registration groups.
		 * 
		 * We want to make a group of each ao for each activity type.
		 * 
		 * Such that each activity type is a certain column in the results list and each permutation exists only once.
		 * 
		 * e.g. Lec A, Lab X, Lab Y -> A, X and A, Y never X, A or Y, A.
		 * 
		 * So only 1/2 of the available permutations are
		 * 
		 */
		Map<String, List<String>> typeToListMap = new HashMap<String, List<String>>();

		List<String> lecList = new ArrayList<String>(2);

		lecList.add("A");
		lecList.add("B");

		// lecture activity type
		typeToListMap.put("LEC", lecList);

		List<String> labList = new ArrayList<String>(3);

		labList.add("X");
		labList.add("Y");
		labList.add("Z");

		// lab activity type
		typeToListMap.put("LAB", labList);

		Set<String> nextStateSet = new HashSet<String>();

		nextStateSet.addAll(typeToListMap.keySet());

		int expectedPermutations = 0;

		for (String key : typeToListMap.keySet()) {

			int length = typeToListMap.get(key).size();

			if (expectedPermutations == 0)
				expectedPermutations = length;
			else
				expectedPermutations *= length;
		}

		assertEquals(6, expectedPermutations);

		List<List<String>> permutations = new ArrayList<List<String>>();

		ArrayList<String> keyList = new ArrayList<String>(
				typeToListMap.keySet());

		PermutationUtils.generatePermutations(keyList, new ArrayList<String>(), typeToListMap,
				permutations);

		assertEquals(6, permutations.size());

		List<String> discussionList = new ArrayList<String>();

		discussionList.add("Q");
		discussionList.add("R");
		discussionList.add("S");
		discussionList.add("T");

		// now add discussion activity type
		typeToListMap.put("DIS", discussionList);

		permutations.clear();

		keyList = new ArrayList<String>(typeToListMap.keySet());

		PermutationUtils.generatePermutations(keyList, new ArrayList<String>(), typeToListMap,
				permutations);

		assertEquals(24, permutations.size());

	}
}
