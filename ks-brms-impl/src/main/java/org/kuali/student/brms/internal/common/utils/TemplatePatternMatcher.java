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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplatePatternMatcher {
	private final static String DEFAULT_REGEX = Pattern.quote("${") + "(.*?)" + Pattern.quote("}");
	private Pattern pattern;

	/**
	 * Constructor.
	 * <p>Default pattern match is <code>${variable}</code></p>
	 * Example:
	 * <blockquote><pre>
	 * String s1 = "${count} of ${courses} is still required";
	 * TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("#", "#");</pre></blockquote>
	 */
	public TemplatePatternMatcher() {
		this.pattern = Pattern.compile(DEFAULT_REGEX);
	}
	
	/**
	 * Example:
	 * <blockquote><pre>
	 * String s = "%count of %courses is still required";
	 * TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("%(\\w*)");</pre></blockquote>
	 * 
	 * @param regex regular expression
	 */
	public TemplatePatternMatcher(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
	/**
	 * Example:
	 * <blockquote><pre>
	 * String s = "#count# of #courses# is still required";
	 * TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("#", "#");</pre></blockquote>
	 * 
	 * @param left Left regular expression
	 * @param right Right regular expression
	 */
	public TemplatePatternMatcher(String left, String right) {
		left = Pattern.quote(left);
		right = Pattern.quote(right);
		this.pattern = Pattern.compile(left + "(.*?)" + right);
	}
	
	/**
	 * <p>Matches (replaces) a string <code>s</code> with a map of 
	 * key/value pairs.</p>
	 * <blockquote><pre>
	 * String s1 = "${count} of ${courses} is still required";
	 * Map<String,String> map = new HashMap<String, String>();
	 * map.put("count", "1");
	 * map.put("courses", "(MATH100, CHEM100)");
	 * TemplatePatternMatcher patternMatcher = new TemplatePatternMatcher("#", "#");
	 * String actual = patternMatcher.match(s1, map);</pre></blockquote>
	 * <p>Resulting string: 1 of (MATH100, CHEM100) is still required</p>
	 * 
	 * @param s
	 * @param variableMap
	 * @return
	 */
	public String match(String s, Map<String, String> variableMap) {
		if(s == null) {
			return null;
		}

		Matcher matcher = this.pattern.matcher(s);
		StringBuilder sb = new StringBuilder();
        int index = 0;
        while(index<s.length() && matcher.find(index)){
        	sb.append(s.subSequence(index, matcher.start()));
            String value = variableMap.get(matcher.group(1));
            sb.append(value == null ? matcher.group() : value);
            index = matcher.end();
        }
        sb.append(s.subSequence(index, s.length()));
        return sb.toString();
	}
}
