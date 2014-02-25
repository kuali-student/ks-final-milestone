package org.kuali.student.ap.framework.context.support;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.r2.core.acal.infc.Term;

/**
 * Data Storage for the Term and Year of a single atp. Formats different output
 * forms of the data
 */
public class DefaultYearTerm implements YearTerm, Comparable<YearTerm> {

	private static final Logger LOG = Logger.getLogger(DefaultYearTerm.class);

	private final String termId;
	private final String termType;
	private final int year;

	public DefaultYearTerm(String termId, String termType, int year) {
		this.termId = termId;
		this.termType = termType;
		this.year = year;
	}

	@Override
	public String getTermId() {
		return termId;
	}

	@Override
	public String getTermType() {
		return termType;
	}

	@Override
	public int getYear() {
		return year;
	}

	@Override
	public int compareTo(YearTerm o) {
		Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(this);
		Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o);
		return t1.getStartDate().compareTo(t2.getStartDate());
	}

	@Override
	public String getTermName() {
		String rv = (rv = getLongName()) == null ? null : rv.trim();
		if (rv != null && rv.length() > 7
				&& rv.endsWith(" " + Integer.toString(year)))
			return rv.substring(0, rv.length() - 5);
		LOG.warn("Not sure how to extract term name " + rv);
		return rv;
	}

	@Override
	public String getShortName() {
		String rv = (rv = getLongName()) == null ? null : rv.trim();
		if (rv != null && rv.length() > 7
				&& rv.endsWith(" " + Integer.toString(year)))
			return rv.substring(0, 2).toUpperCase() + " " + year;
		LOG.warn("Not sure how to shorten term name " + rv);
		return rv;
	}

	@Override
	public String getLongName() {
		return KsapFrameworkServiceLocator.getTermHelper().getTerm(this)
				.getName();
	}

    /**
     * Get the abbreviation of the term name for display (i.e. &quot;SP 13&quot; for Spring 2013).
     *
     * @return The abbreviation of the term name for display (i.e. &quot;SP 13&quot; for Spring 2013).
     */
    @Override
    public String getAbbrivation() {
        String rv = (rv = getLongName()) == null ? null : rv.trim();
        if (rv != null && rv.length() > 7
                && rv.endsWith(" " + Integer.toString(year)))
            return rv.substring(0, 2).toUpperCase() + " " + Integer.toString(year).substring(2);
        LOG.warn("Not sure how to shorten term name " + rv);
        return rv;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((termType == null) ? 0 : termType.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultYearTerm other = (DefaultYearTerm) obj;
		if (termType == null) {
			if (other.termType != null)
				return false;
		} else if (!termType.equals(other.termType))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultYearTerm [termType=" + termType + ", year=" + year + "]";
	}

}
