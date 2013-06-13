package org.kuali.student.ap.framework.context.support;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

/**
 * Data Storage for the Term and Year of a single atp. Formats different output
 * forms of the data
 */
public class DefaultYearTerm implements YearTerm, Comparable<YearTerm> {

	private static final Logger LOG = Logger.getLogger(DefaultYearTerm.class);

	private static String[] termTypes;

	private static String[] getTermTypes() {
		if (termTypes == null) {
			try {
				List<TypeInfo> termTypeInfos = KsapFrameworkServiceLocator
						.getAcademicCalendarService().getTermTypes(
								KsapFrameworkServiceLocator.getContext()
										.getContextInfo());
				if (termTypeInfos == null || termTypeInfos.isEmpty())
					throw new IllegalStateException(
							"No term types available using AcademicCalendarService.getTermTypes()");
				String[] tto = new String[termTypeInfos.size()];
				for (int i = 0; i < tto.length; i++)
					tto[i] = termTypeInfos.get(i).getKey();
				termTypes = tto;
				LOG.info("Set term types to " + Arrays.toString(termTypes));
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Type lookup error", e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("Type lookup error", e);
			} catch (PermissionDeniedException e) {
				throw new IllegalStateException("Type lookup error", e);
			}
		}
		return termTypes;
	}

	private final String termId;
	private final String termType;
	private final int year;

	public DefaultYearTerm(String termId, String termType, int year) {
		if (!Arrays.asList(getTermTypes()).contains(termType))
			throw new IllegalArgumentException("Term type " + termType
					+ " not supported");
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
