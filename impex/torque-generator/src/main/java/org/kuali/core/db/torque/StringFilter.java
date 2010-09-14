package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class provides logic for filtering strings based on regular expressions. Given a list of includePatterns and
 * excludePatterns the filter method will remove any strings from an Iterator<String> if there is not a match on an
 * inclusion pattern or there is a match on an exclusion pattern,
 */
public class StringFilter {
	private static final Log log = LogFactory.getLog(StringFilter.class);

	/**
	 * List of regular expressions. A string must match one of these to be included
	 */
	List<String> includePatterns;

	/**
	 * List of regular expressions. If there is a match on any of these the string is filtered out
	 */
	List<String> excludePatterns;
	List<Pattern> includes;
	List<Pattern> excludes;

	public StringFilter() {
		this(null, null);
	}

	public StringFilter(List<String> includePatterns, List<String> excludePatterns) {
		super();
		this.includePatterns = includePatterns;
		this.excludePatterns = excludePatterns;
	}

	/**
	 * Compile the string patterns into Pattern objects
	 */
	protected void compilePatterns() {
		includes = getPatterns(includePatterns);
		excludes = getPatterns(excludePatterns);
	}

	public List<String> getIncludePatterns() {
		return includePatterns;
	}

	public void setIncludePatterns(List<String> includePatterns) {
		this.includePatterns = includePatterns;
	}

	public List<String> getExcludePatterns() {
		return excludePatterns;
	}

	public void setExcludePatterns(List<String> excludePatterns) {
		this.excludePatterns = excludePatterns;
	}

	/**
	 * Convert a List<String> into List<Pattern>
	 */
	protected List<Pattern> getPatterns(List<String> patterns) {
		// If List<String> is empty return an empty List<Pattern>
		if (isEmpty(patterns)) {
			return new ArrayList<Pattern>();
		}
		List<Pattern> regexPatterns = new ArrayList<Pattern>();
		for (String pattern : patterns) {
			Pattern regexPattern = Pattern.compile(pattern);
			regexPatterns.add(regexPattern);
		}
		return regexPatterns;
	}

	/**
	 * Return true if the string matches any of the patterns
	 */
	protected boolean isMatch(String s, List<Pattern> patterns) {
		// If patterns is null or zero size, there is no match
		if (isEmpty(patterns)) {
			return false;
		}
		// Loop through the patterns looking for a match
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(s);
			// We found a match, return true
			if (matcher.matches()) {
				return true;
			}
		}
		// We cycled through all of the patterns without finding a match
		return false;
	}

	/**
	 * Return true if no inclusion patterns have been specified.<br>
	 * Return true if there have been inclusion patterns specified and there is a match on at least one of them<br>
	 * Return false otherwise.<br>
	 */
	protected boolean isInclude(String s) {
		return isEmpty(includePatterns) || isMatch(s, includes);
	}

	/**
	 * Return true if the string matches one or more of the exclude patterns
	 */
	protected boolean isExclude(String s) {
		return isMatch(s, excludes);
	}

	/**
	 * Return true if the List is null or has zero elements
	 */
	protected boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * Invoke Iterator.remove() on any strings that match an exclusion pattern or do not match an inclusion pattern
	 */
	public void filter(Iterator<String> itr) {
		// Compile our Strings into Patterns
		compilePatterns();
		while (itr.hasNext()) {
			String s = itr.next();
			if (!isInclude(s)) {
				// This string does not match an inclusion pattern
				log.debug("No inclusion pattern match. Removing '" + s + "'");
				itr.remove();
				continue;
			}
			if (isExclude(s)) {
				// This string matches an exclusion pattern
				log.debug("Exclusion pattern matched.  Removing '" + s + "'");
				itr.remove();
				continue;
			}
		}
	}

	public List<Pattern> getIncludes() {
		return includes;
	}

	public List<Pattern> getExcludes() {
		return excludes;
	}

	public static List<String> getListFromCSV(String csv) {
		if (StringUtils.isEmpty(csv)) {
			return new ArrayList<String>();
		}
		String[] tokens = csv.split(",");
		List<String> list = new ArrayList<String>();
		for (String token : tokens) {
			list.add(token.trim());
		}
		return list;
	}

}
