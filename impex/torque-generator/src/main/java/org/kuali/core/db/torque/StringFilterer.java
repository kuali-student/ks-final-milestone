package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringFilterer {
	private static final Log log = LogFactory.getLog(StringFilterer.class);
	List<String> includePatterns;
	List<String> excludePatterns;
	List<Pattern> includes;
	List<Pattern> excludes;

	public StringFilterer() {
		this(null, null);
	}

	public StringFilterer(List<String> includePatterns, List<String> excludePatterns) {
		super();
		this.includePatterns = includePatterns;
		this.excludePatterns = excludePatterns;
	}

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

	protected List<Pattern> getPatterns(List<String> patterns) {
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

	protected boolean isMatch(String s, List<Pattern> patterns) {
		if (isEmpty(patterns)) {
			return false;
		}
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(s);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	protected boolean isInclude(String s) {
		return isEmpty(includePatterns) || isMatch(s, includes);
	}

	protected boolean isExclude(String s) {
		return isMatch(s, excludes);
	}

	protected boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	public void filterStrings(Iterator<String> itr) {
		compilePatterns();
		while (itr.hasNext()) {
			String s = itr.next();
			boolean include = isInclude(s);
			if (!include) {
				log.info("Skipping " + s + ".  No match on an inclusion pattern");
				itr.remove();
				continue;
			}
			boolean exclude = isExclude(s);
			if (exclude) {
				log.info("Skipping " + s + ". Matched an exclusion pattern");
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

}
