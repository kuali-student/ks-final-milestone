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

package org.kuali.student.brms.internal.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TemplatePatternMatcherTest {

	@Test
	public void testMatch1() throws Exception {
		String s1 = "${count} of ${courses} is still required";
		Map<String,String> map = new HashMap<String, String>();
		map.put("count", "3");
		map.put("courses", "(MATH100, CHEM100, PHYS100, ENGL100)");
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher();
		String actual = patternMatcher.match(s1, map);
		
		Assert.assertEquals("3 of (MATH100, CHEM100, PHYS100, ENGL100) is still required", actual);
	}

	@Test
	public void testMatch2() throws Exception {
		String s1 = "@count of @courses is still required";
		Map<String,String> map = new HashMap<String, String>();
		map.put("count", "2");
		map.put("courses", "(MATH100, CHEM100, PHYS100, ENGL100)");
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("@(\\w*)");
		String actual = patternMatcher.match(s1, map);
		
		Assert.assertEquals("2 of (MATH100, CHEM100, PHYS100, ENGL100) is still required", actual);
	}

	@Test
	public void testMatch3() throws Exception {
		String s1 = "#count# of #courses# is still required";
		Map<String,String> map = new HashMap<String, String>();
		map.put("count", "1");
		map.put("courses", "(MATH100, CHEM100, PHYS100, ENGL100)");
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("#", "#");
		String actual = patternMatcher.match(s1, map);
		
		Assert.assertEquals("1 of (MATH100, CHEM100, PHYS100, ENGL100) is still required", actual);
	}

	@Test
	public void testMatch4_EmptyMap() throws Exception {
		String s1 = "${count} of ${courses} is still required";
		Map<String,String> map = new HashMap<String, String>();
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher();
		String actual = patternMatcher.match(s1, map);
		
		Assert.assertEquals("${count} of ${courses} is still required", actual);
	}

	@Test
	public void testMatch5_NullString_EmptyMap() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher();
		String actual = patternMatcher.match(null, map);
		
		Assert.assertEquals(null, actual);
	}

	@Test
	public void testMatch6_NullString() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		map.put("count", "1");
		TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher();
		String actual = patternMatcher.match(null, map);
		
		Assert.assertEquals(null, actual);
	}
}
